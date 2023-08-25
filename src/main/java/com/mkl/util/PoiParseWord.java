package com.mkl.util;

import org.apache.poi.xwpf.usermodel.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * @author mkl
 */
public class PoiParseWord {


    public static void main(String[] args) {
        PoiParseWord wordUtil=new PoiParseWord();
        try{
            List<Map<String,Object>> list = new ArrayList<>();
            Map<String,Object> jpeg1 = new HashMap<String, Object>(16);
            jpeg1.put("type", "jpg");
            jpeg1.put("content","C:\\Users\\mkl\\Desktop\\photo.jpg");
            jpeg1.put("tagging","评委签字：");

            Map<String,Object> jpeg2 = new HashMap<String, Object>(16);
            jpeg2.put("type", "jpg");
            jpeg2.put("content","C:\\Users\\mkl\\Desktop\\photo.jpg");
            jpeg2.put("tagging","评委签字：");

            Map<String,Object> jpeg3 = new HashMap<String, Object>(16);
            jpeg3.put("type", "jpg");
            jpeg3.put("content","C:\\Users\\mkl\\Desktop\\photo.jpg");
            jpeg3.put("tagging","评委签字：");

            Map<String,Object> jpeg4 = new HashMap<String, Object>(16);
            jpeg4.put("type", "jpg");
            jpeg4.put("content","C:\\Users\\mkl\\Desktop\\photo.jpg");
            jpeg4.put("tagging","评委签字：");

            list.add(jpeg1);
            list.add(jpeg2);
            list.add(jpeg3);
            list.add(jpeg4);

            //模板文件位置
            String path="C:\\Users\\mkl\\Desktop\\11.docx";
            //生成文件位置
            String fileName= new String("C:\\Users\\mkl\\Desktop\\22.docx".getBytes("UTF-8"),"iso-8859-1");
            getWordFile(path,list,fileName);
            //System.out.println(doesItExistStr("评委签字：","C:\\\\Users\\\\mkl\\\\Desktop\\\\11.docx"));

//            System.out.println(saveUrlAs("http://fileimg.makalu.cc/990c06c1-9539-412f-aebc-21a1b8dd7e90-中铁一局集团数控钢筋加工设备集采评标报告.docx","C:\\Users\\mkl\\Desktop\\","POST",".docx"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 判断字符是否存在于word文档
     * @param str 确认字符
     * @param file 文档地址
     * @return
     */
    public static boolean doesItExistStr(String str,String file){
        boolean b = false;
        try {
            //创建输入流
            InputStream is = new FileInputStream(file);
            //创建doc对象
            CustomXWPFDocument doc = new CustomXWPFDocument(is);
            //遍历word字符
            Iterator<XWPFParagraph> iterator = doc.getParagraphsIterator();
            while (iterator.hasNext()) {
                List<XWPFRun> runs = iterator.next().getRuns();
                for (int i = 0; i < runs.size(); i++) {
                    XWPFRun run = runs.get(i);
                    String runText = run.toString();
                    System.out.println(runText);
                    if (str.equals(runText)) {
                        b = true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }


    /**
     * 根据模板生成word
     * @param path     模板的路径
     * @param params   需要替换的参数
     * @param fileName 生成word文件的文件名
     */
    public static void getWordFile (String path, List<Map<String,Object>>  params,String fileName) throws Exception {
        File file = new File(path);
        //创建输入流
        InputStream is = new FileInputStream(file);
        //创建doc对象
        CustomXWPFDocument doc = new CustomXWPFDocument(is);
        //替换文本里面的变量
        replaceInPara(doc, params);
        //输出流写入文件
        FileOutputStream out = new FileOutputStream(fileName);
        doc.write(out);
        //关闭输入输出流
        close(is);
        out.close();
    }

    /**
     * 替换段落里面的变量
     * @param doc    要替换的文档
     * @param params 参数
     */
    private static void replaceInPara(CustomXWPFDocument doc, List<Map<String,Object>> params) {
        Iterator<XWPFParagraph> iterator = doc.getParagraphsIterator();
        XWPFParagraph para;
        while (iterator.hasNext()) {
            para = iterator.next();
            replaceInPara(para, params, doc);
        }
    }

    /**
     * word文档里面插入图片
     * @param para   要替换的段落
     * @param params 参数
     */
    private static void replaceInPara(XWPFParagraph para, List<Map<String,Object>> params, CustomXWPFDocument doc) {
        List<XWPFRun> runs = para.getRuns();
        System.out.println(runs);
        int start = -1;
        int end = -1;
        String str = "";
        for (int i = 0; i < runs.size(); i++) {
            XWPFRun run = runs.get(i);
            String runText = run.toString();
            System.out.println(runText);
            start = i;
            if ((start != -1)) {
                str += runText;
            }
            if (start != -1) {
                end = i;
                break;
            }
        }

        for (int i = start; i <= end; i++) {
            //para.removeRun(i);
            i--;
            end--;
        }

        //更新文字
        for(Map<String,Object> entryMap:params){
            if (str.equals(entryMap.get("tagging").toString())) {
                System.out.println("----------------------"+str);
                para.createRun().setText(str, 0);
                break;
            }
        }

        //插入图片
        for(Map<String,Object> entryMap:params){
            if (str.equals(entryMap.get("tagging").toString())) {
                System.out.println("============"+str);
                int height = 40;
                int width = 60;
                int picType = getPictureType(entryMap.get("type").toString());
                try {
                    doc.addPictureData(new FileInputStream(entryMap.get("content").toString()), picType);
                    doc.createPicture(doc.getAllPictures().size() - 1, width, height, para);
                    System.out.println("===============图片插入成功==============");
                    continue;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 根据图片类型，取得对应的图片类型代码
     * @param picType
     * @return int
     */
    private static int getPictureType(String picType) {
        int res = CustomXWPFDocument.PICTURE_TYPE_PICT;
        if (picType != null) {
            if (picType.equalsIgnoreCase("png")) {
                res = CustomXWPFDocument.PICTURE_TYPE_PNG;
            } else if (picType.equalsIgnoreCase("dib")) {
                res = CustomXWPFDocument.PICTURE_TYPE_DIB;
            } else if (picType.equalsIgnoreCase("emf")) {
                res = CustomXWPFDocument.PICTURE_TYPE_EMF;
            } else if (picType.equalsIgnoreCase("jpg") || picType.equalsIgnoreCase("jpeg")) {
                res = CustomXWPFDocument.PICTURE_TYPE_JPEG;
            } else if (picType.equalsIgnoreCase("wmf")) {
                res = CustomXWPFDocument.PICTURE_TYPE_WMF;
            }
        }
        return res;
    }

    /**
     * 关闭输入流
     * @param is
     */
    private static void close(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 下载文件到本地
     * @param url 地址
     * @param filePath 返回地址
     * @param method 请求方式
     * @param suffix 文件名后缀
     * @return
     */
    public static File saveUrlAs(String url,String filePath,String method,String suffix){
        //创建不同的文件夹目录
        File file=new File(filePath);
        //判断文件夹是否存在
        if (!file.exists()) {
            //如果文件夹不存在，则创建新的的文件夹
            file.mkdirs();
        }
        FileOutputStream fileOut = null;
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        try
        {
            // 建立链接
            URL httpUrl=new URL(url);
            conn=(HttpURLConnection) httpUrl.openConnection();
            //以Post方式提交表单，默认get方式
            conn.setRequestMethod(method);
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            // post方式不能使用缓存
            conn.setUseCaches(false);
            //连接指定的资源
            conn.connect();
            //获取网络输入流
            inputStream=conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            //写入到文件（注意文件保存路径的后面一定要加上文件的名称）
            fileOut = new FileOutputStream(filePath+UUID.randomUUID()+suffix);
            BufferedOutputStream bos = new BufferedOutputStream(fileOut);

            byte[] buf = new byte[4096];
            int length = bis.read(buf);
            //保存文件
            while(length != -1)
            {
                bos.write(buf, 0, length);
                length = bis.read(buf);
            }
            bos.close();
            bis.close();
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("抛出异常！！");
        }
        return file;
    }
}
