//package com.mkl.util;
//
//
//import java.awt.image.BufferedImage;
//import java.io.*;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.commons.lang3.StringUtils;
//import org.apache.poi.hssf.usermodel.*;
//import org.apache.poi.poifs.filesystem.POIFSFileSystem;
//import org.apache.poi.ss.usermodel.*;
//
//import javax.imageio.ImageIO;
//
//public class text {
//
//    public static void main(String[] args) {
//        String url="C:\\Users\\mkl\\Desktop\\起重机械专项安全检查表.xls";
//        FileInputStream fs;
//        FileOutputStream out=null;
//        try {
//            fs = new FileInputStream(url);
//            //使用POI提供的方法得到excel的信息
//            POIFSFileSystem ps=new POIFSFileSystem(fs);
//            HSSFWorkbook wb=new HSSFWorkbook(ps);
//            HSSFSheet sheet=wb.getSheetAt(0);
//
//            // 设置字体
//            HSSFFont font = wb.createFont();
//            //设置字体大小
//            font.setFontHeightInPoints((short)10);
//            font.setFontName("宋体");
//
//            HSSFCellStyle style = wb.createCellStyle();
//            // 设置单元格格式为允许换行
//            style.setWrapText(true);
//            //设置水平对齐的样式为居中对齐;
////            style.setAlignment(HorizontalAlignment.CENTER);
////            style.setVerticalAlignment(VerticalAlignment.CENTER);
//            style.setFont(font);
//
//            //设置单元格边框
//            style.setBorderBottom(BorderStyle.THIN);
//            style.setBorderLeft(BorderStyle.THIN);
//            style.setBorderRight(BorderStyle.THIN);
//            style.setBorderTop(BorderStyle.THIN);
//
//            sheet.getRow(38).getCell(0).setCellValue("检查人员：                            负责人签字：");
//
//            out=new FileOutputStream("C:\\Users\\mkl\\Desktop\\222.xls");
//
//            out.flush();
//            wb.write(out);
//            out.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            if (out != null) {
//                try {
//                    out.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//
//    /**
//     * excel插入图片图片
//     * @param image
//     * @param sheet
//     * @return
//     */
//    public static void insertImg(String image,HSSFWorkbook wb, HSSFSheet sheet,int dx1, int dy1, int dx2, int dy2, short col1, int row1, short col2, int row2){
//        try {
//            if(StringUtils.isNotEmpty(image)){
//                ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
//                //将图片读到BufferedImage
//                BufferedImage  bufferImg = ImageIO.read(new File(image));
//                // 将图片写入流中
//                ImageIO.write(bufferImg, "png", byteArrayOut);
//                // 利用HSSFPatriarch将图片写入EXCEL
//                HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
//                //图片
//                HSSFClientAnchor anchor = new HSSFClientAnchor(dx1, dy1, dx2, dy2,
//                        col1, row1, col2, row2);
//                patriarch.createPicture(anchor, wb.addPicture(byteArrayOut
//                        .toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//}
