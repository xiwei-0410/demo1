//package com.mkl.util;
//
//
//import com.aspose.cells.License;
//import com.aspose.cells.PdfSaveOptions;
//import com.aspose.cells.Workbook;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.InputStream;
//
///**
// * @ClassName ExcelToPdfUtil
// * 功能详细描述：
// * @Author GuoZiYue
// * @Date 2021/2/18 11:08
// * @Version 1.0
// */
//@RequestMapping("/demo/")
//@Controller
//public class SaoLei {
//
//    public static void main(String[] args) {
//        //System.out.println("啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊".length());
//        String sourceFilePath="C:\\Users\\mkl\\Desktop\\222.xls";
//        String desFilePath="C:\\Users\\mkl\\Desktop\\text.pdf";
//        SaoLei.excel2pdf(sourceFilePath,desFilePath);
//    }
//
//    /**
//     * excel 转为pdf 输出。
//     *
//     * @param sourceFilePath  excel文件
//     * @param desFilePathd  pad 输出文件目录
//     */
//    public static void excel2pdf(String sourceFilePath, String desFilePathd ){
//        // 验证License 若不验证则转化出的pdf文档会有水印产生
//        if (!getLicense()) {
//            return;
//        }
//        try {
//            // 原始excel路径
//            Workbook wb = new Workbook(sourceFilePath);
//
//            FileOutputStream fileOS = new FileOutputStream(desFilePathd);
//            PdfSaveOptions pdfSaveOptions = new PdfSaveOptions();
//            pdfSaveOptions.setOnePagePerSheet(true);
//            int sheetCount = wb.getWorksheets().getCount();
//            int[] showSheets=new int[sheetCount];
//            for (int i = 0; i < sheetCount; i++) {
//                showSheets[i]=i;
//            }
//            //隐藏workbook中不需要的sheet页。
//            autoDraw(wb,showSheets);
//            printSheetPage(wb,showSheets);
//            wb.save(fileOS, pdfSaveOptions);
//            fileOS.flush();
//            fileOS.close();
//            System.out.println("完毕");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static boolean getLicense() {
//        boolean result = false;
//        try {
//            InputStream is = SaoLei.class.getClassLoader().getResourceAsStream("\\templates\\license.xml");
//            License aposeLic = new License();
//            aposeLic.setLicense(is);
//            result = true;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//
//
//    /**
//     * 设置打印的sheet 自动拉伸比例
//     * @param wb
//     * @param page 自动拉伸的页的sheet数组
//     */
//    public static void autoDraw(Workbook wb,int[] page){
//        if(null!=page&&page.length>0){
//            for (int i = 0; i < page.length; i++) {
//                wb.getWorksheets().get(i).getHorizontalPageBreaks().clear();
//                wb.getWorksheets().get(i).getVerticalPageBreaks().clear();
//            }
//        }
//    }
//
//
//    /**
//     * 隐藏workbook中不需要的sheet页。
//     * @param wb
//     * @param page 显示页的sheet数组
//     */
//    public static void printSheetPage(Workbook wb,int[] page){
//        for (int i= 1; i < wb.getWorksheets().getCount(); i++)  {
//            wb.getWorksheets().get(i).setVisible(false);
//        }
//        if(null==page||page.length==0){
//            wb.getWorksheets().get(0).setVisible(true);
//        }else{
//            for (int i = 0; i < page.length; i++) {
//                wb.getWorksheets().get(i).setVisible(true);
//            }
//        }
//    }
//}
//
//
