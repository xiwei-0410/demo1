package com.wxw.websocket;

import com.alibaba.fastjson.JSONObject;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * websocket工具类
 * @author wxw
 */

@Component
@ServerEndpoint(value = "/websocket/{sendUser}",configurator = MySpringConfigurator.class)
public class WebSocket {
/**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */

    private static int onlineCount = 0;

/**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     * 若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
     */

    private static ConcurrentHashMap<Integer,WebSocket> webSocketMap = new ConcurrentHashMap<Integer,WebSocket>();

/**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */

    private Session session;

    /**
     * 当前用户
     */
    private Integer sendUser;
    /**
     * 接收人
     */
    private Integer toUser;
    /**
     * 消息
     */
    private String message;

/**
     * 连接建立成功调用的方法
     * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(@PathParam("sendUser") Integer sendUser, Session session) throws IOException {
        this.session = session;
        this.sendUser = sendUser;
        //在线数加1
        addOnlineCount();
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount()+ " 当前session是" + session.hashCode());
        //加入webSocket中
        webSocketMap.put(sendUser,this);
        // 刷新在线人数
        for (WebSocket chat : webSocketMap.values()) {
            //使用if判断是要统计人数还是发送消息
            chat.sendMessage(session,getOnlineCount() + "");
        }

    }

/**
     * 连接关闭调用的方法
     */

    @OnClose
    public void onClose() throws IOException {
        //从set中删除
        webSocketMap.remove(sendUser);
        //在线数减1
        subOnlineCount();
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
        for (WebSocket chat : webSocketMap.values()) {
            //说明当前的session已经被关闭
            if(chat.session != this.session){
                chat.sendMessage(session,getOnlineCount() + "");
            }
        }
    }

/**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */

    @OnMessage
    public void onMessage(String message,Session session) throws IOException {
        JSONObject jsonOject = JSONObject.parseObject(message);
        sendUser = Integer.parseInt(jsonOject.getString("sendUser"));
        toUser = Integer.parseInt(jsonOject.getString("toUser"));
        message = jsonOject.getString("message");
        message = "来自:" + sendUser + "用户发给" + toUser + "用户的信息:" + message + " \r\n";
        System.out.println("来自客户端的消息:" + message);

        // 得到接收人
        WebSocket user = webSocketMap.get(toUser);
        //如果接收人不存在则保持到数据库
        if (user == null) {
            System.out.println("信息已保存到数据库");
            return;
        }
        user.sendMessage(session,message);
    }

    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param message
     * @throws IOException
     */
    public void sendMessage(Session session,String message) throws IOException {
        //阻塞式（同步）
        System.out.println(session.hashCode());
        synchronized (session) {
            this.session.getBasicRemote().sendText(message);
        }
        //非阻塞式（异步）
//      synchronized (this.session) {
//          this.session.getAsyncRemote().sendText(message);
//      }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocket.onlineCount--;
    }
}
