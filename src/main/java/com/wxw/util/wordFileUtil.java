//package com.wxw.util;
//import com.spire.doc.Document;
//import com.spire.doc.FileFormat;
//import com.spire.doc.Section;
//import com.spire.doc.documents.Paragraph;
//import com.spire.doc.fields.DocPicture;
///**
// * word文件操作类
// * @author wxw
// */
//public class wordFileUtil {
//
//    public static void main(String[] args) {
//        Document doc = new Document();
//        //filePath为目标文档路径，例如：D:\123.doc
//        doc.loadFromFile("C:\\Users\\wxw\\Desktop\\111.doc");
//        //获取最后一段
//        Paragraph para = doc.getLastParagraph();
//        //添加图片到文档最后一段后面，并设置图片高、宽
//        DocPicture picture = para.appendPicture("C:\\Users\\wxw\\Desktop\\图片\\photo.jpg");
//        picture.setHeight(100);
//        picture.setWidth(100);
//        //新加一个段落，为的是多人添加签名时继续往后面拼图片
//        Section lastSection = doc.getLastSection();
//        lastSection.addParagraph();
//        //保存文档
//        doc.saveToFile("C:\\Users\\wxw\\Desktop\\222.doc",FileFormat.Docx_2013);
//        doc.dispose();
//    }
//
//
//
//
//}
