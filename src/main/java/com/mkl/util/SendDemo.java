package com.mkl.util;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SendDemo {

    //发送端
    public static void send() throws IOException, InterruptedException {
        //得到目标的机器地址
        InetAddress inetAddress=InetAddress.getByName("192.168.31.126");
        //设置发送端端口，需与接受端端口一致，以便正确接收
        DatagramSocket ds = new DatagramSocket();
        String data="##,G1,7200513204,2021-01-18 11:35:01,30690";
        System.out.println("===="+data.length());
        //将数据转化为byte[]形式,用于发送
        byte[] bytes=data.getBytes();
        DatagramPacket packet=new DatagramPacket(bytes,bytes.length,inetAddress,19225);
        //设置循环，模拟生产真实发送数据的频率
        int num=1;
        ds.send(packet);
//        while (true){
//            Thread.sleep(5000);
//            System.out.println("发送消息"+num+": "+bytes.toString());
//
//            num+=1;
//        }
        ds.close();//由于发送处于死循环状态，所以输出流不用关闭。
    }

    public static void main(String[] args) {
        try {
            send();
        } catch (IOException e) {
            System.out.println("发送信息报错："+e);
        } catch (InterruptedException e) {
            System.out.println("发送信息报错："+e);
        }
    }

    /**
     * 将字符串转成ASCII
     */
    public static String stringToAscii(String value) {
        StringBuffer sbu = new StringBuffer();
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            sbu.append((int) chars[i]).append(",");
        }
        return sbu.toString();
    }

}

