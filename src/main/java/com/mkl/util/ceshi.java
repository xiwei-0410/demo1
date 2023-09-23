package com.mkl.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ceshi {


        public static List<Map<String, Object>> calculateSumWorkingHours(List<Map<String, Object>> list) {
            Map<Integer, Double> sumMap = new HashMap<>();

            list.forEach(map -> {
                Integer deviceId = Integer.valueOf(map.get("deviceId").toString());
                Double workingHours = Double.valueOf(map.get("workingHours").toString());

                sumMap.put(deviceId, sumMap.getOrDefault(deviceId, 0.0) + workingHours);
            });

            list.forEach(map -> map.put("sumWorkingHours", sumMap.get(Integer.valueOf(map.get("deviceId").toString()))));

            return list;
        }

    /**
     * 100被n平均分，如：
     * 100被3平均分：34,33,33
     * 100被13平均分：8,8,8,8,8,8,8,8,8,7,7,7,7
     */
    public static Double[] avg100(Double total, Integer number) {

        Double[] result = new Double[number];
        Double value = total / number;

        Double index = total - (value * number);

        for (int i = 0; i < number; i++) {
            if (i < index) {
                result[i] = value + 1;
            } else {
                result[i] = value;
            }
        }

        return result;
    }


    public static void main(String[] args) {
        Double[] result = avg100(102.00,13);
        Double total = 0.00;

        for (Double integer : result) {
            System.out.print(integer);
            System.out.print(",");
        }

        System.out.println();
        for (Double value : result) {
            total += value;
        }
        System.out.println(total);
//            List<Map<String, Object>> inputList = new ArrayList<>();
//            Map<String, Object> entry1 = new HashMap<>();
//            entry1.put("deviceId", 2297);
//            entry1.put("workingHours", "2.0");
//            inputList.add(entry1);
//
//            Map<String, Object> entry2 = new HashMap<>();
//            entry2.put("deviceId", 2297);
//            entry2.put("workingHours", "25.0");
//            inputList.add(entry2);
//
//            Map<String, Object> entry3 = new HashMap<>();
//            entry3.put("deviceId", 2297);
//            entry3.put("workingHours", "18.0");
//            inputList.add(entry3);
//
//            Map<String, Object> entry4 = new HashMap<>();
//            entry4.put("deviceId", 2403);
//            entry4.put("workingHours", 0);
//            inputList.add(entry4);
//
//            Map<String, Object> entry5 = new HashMap<>();
//            entry5.put("deviceId", 2406);
//            entry5.put("workingHours", 0);
//            inputList.add(entry5);
//
//            List<Map<String, Object>> result = calculateSumWorkingHours(inputList);
//            System.out.println(result);
        }

//    public static void main(String[] args) {
//        String generateFile = null;
//        FileInputStream fs;
//        FileOutputStream out=null;
//        try {
//            fs = new FileInputStream("C:\\Users\\mkl\\Desktop\\111.xls");
//            //使用POI提供的方法得到excel的信息
//            HSSFWorkbook wb=new HSSFWorkbook(new POIFSFileSystem(fs));
//            HSSFSheet sheet=wb.getSheetAt(0);
//            HSSFCellStyle style = wb.createCellStyle();
//            //设置单元格格式为允许换行
//            style.setWrapText(true);
//            //设置水平对齐的样式为居中对齐;
//            style.setAlignment(HorizontalAlignment.CENTER);
//            style.setVerticalAlignment(VerticalAlignment.CENTER);
//            //设置单元格边框
//            style.setBorderLeft(BorderStyle.THIN);
//            style.setBorderBottom(BorderStyle.THIN);
//            style.setBorderTop(BorderStyle.THIN);
//            style.setBorderRight(BorderStyle.THIN);
//            // 设置字体
//            HSSFFont font = wb.createFont();
//            //设置字体大小
//            font.setFontHeightInPoints((short)11);
//            font.setFontName("宋体");
//            style.setFont(font);
//            //鉴定小组意见
//            sheet.getRow(8).getCell(1).setCellValue("同意上报集团公司物设部，申请报废。");
//            sheet.getRow(8).getCell(1).setCellStyle(style);
//            //鉴定小组签章
//            List<String> list = new ArrayList<>();
//            list.add("C:\\Users\\mkl\\Desktop\\tupian\\dzqz.png");
//            list.add("C:\\Users\\mkl\\Desktop\\tupian\\dzqz.png");
//            list.add("C:\\Users\\mkl\\Desktop\\tupian\\dzqz.png");
//            list.add("C:\\Users\\mkl\\Desktop\\tupian\\111.jpg");
//            list.add("C:\\Users\\mkl\\Desktop\\tupian\\111.jpg");
//            list.add("C:\\Users\\mkl\\Desktop\\tupian\\111.jpg");
//
//            createExcelInsertPicture(wb,list);
//            //输出
//            generateFile = "C:\\Users\\mkl\\Desktop\\aaa.xls";
//            out=new FileOutputStream(generateFile);
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

    /**
     * 报废申请数插入图片
     * @param wb
     * @param list
     */
    public static void createExcelInsertPicture(HSSFWorkbook wb,List<String> list){
        HSSFSheet sheet= wb.getSheetAt(0);
        int a = 1;
        int b = 9;
        int c = 2;
        int d = 10;
        int num = 1;
        moveRow(wb,list);
        for(int i = 0;i<list.size();i++){
            //下载拍照图片
            //String imgPath = DownloadUtil.download(list.get(i));
            String imgPath = list.get(i);
            if(imgPath != null){
                insertImg(imgPath,wb,sheet,0, 0, 0, 0,  (short) a, b, (short)c, d);
                if(num > 2){
                    num = 1;
                    a = 1;
                    b += 1;
                    c = 2;
                    d += 1;
                }else {
                    num++;
                    a += 1;
                    c += 1;
                }
            }
        }
    }

    /**
     * 移动excel行
     * 1  2  3  4  5  6  7  8  9  10  11  12  13
     * 0  0  0  1  1  1  2  2  2  3   3   3   4
     *
     * @param wb
     * @param list
     */
    private static void moveRow(HSSFWorkbook wb,List<String> list){
        HSSFSheet sheet= wb.getSheetAt(0);
        int count = list.size();
        int num = 3;
        int move = 0;
        int c = count % num;
        int d = count / num;
        if(c > 0){
            move = d;
        }else {
            move = d - 1;
        }
        int startRow = 9+move;
        int endRow = 10+move;
        sheet.shiftRows(startRow,endRow,1);
        //ExcelOperationUtil.copyRow(sheet.getRow(startRow),sheet.getRow(endRow),true);
    }

    /**
     * excel插入图片图片
     * @param image
     * @param sheet
     * @return
     */
    public static void insertImg(String image,HSSFWorkbook wb, HSSFSheet sheet,int dx1, int dy1, int dx2, int dy2, short col1, int row1, short col2, int row2){
        try {
            if(StringUtils.isNotEmpty(image)){
                ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
                //将图片读到BufferedImage
                BufferedImage bufferImg = ImageIO.read(new File(image));
                // 将图片写入流中
                ImageIO.write(bufferImg, "png", byteArrayOut);
                // 利用HSSFPatriarch将图片写入EXCEL
                HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
                //图片
                HSSFClientAnchor anchor = new HSSFClientAnchor(dx1, dy1, dx2, dy2,
                        col1, row1, col2, row2);
                patriarch.createPicture(anchor, wb.addPicture(byteArrayOut
                        .toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
                //DownloadUtil.delete(image);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
