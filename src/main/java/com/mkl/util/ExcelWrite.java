package com.mkl.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelWrite {
    private static HSSFWorkbook workbook = null;

    /**
     * 往excel中写入(已存在的数据无法写入).
     * @param fileDir    文件路径
     * @param sheetName  表格索引
     * @throws Exception
     */
    public static void writeToExcel(String fileDir,String sheetName,List<String> mapList) throws Exception{
        //创建workbook
        File file = new File(fileDir);
        try {
            workbook = new HSSFWorkbook(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //流
        FileOutputStream out = null;
        HSSFSheet sheet = workbook.getSheet(sheetName);
        // 获取表头的列数
        try {
            // 获得表头行对象
            HSSFRow titleRow = sheet.getRow(0);
            for(int rowId=0;rowId<mapList.size();rowId++){
                String map = mapList.get(rowId);
                HSSFRow newRow=sheet.createRow(rowId+1);
                //遍历表头
                for (short columnIndex = 0; columnIndex < 2; columnIndex++) {
                    String mapKey = titleRow.getCell(columnIndex).toString().trim().toString().trim();
                    HSSFCell cell = newRow.createCell(columnIndex);
                    cell.setCellValue(map ==null ? null : map);
                }
            }
            out = new FileOutputStream(fileDir);
            workbook.write(out);
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     *
     * @Title: write
     * @Description: 向已存在的Excel写入数据
     * @param @param file
     * @param @param list
     * @param @param list2
     * @param @return
     * @return String
     * @throws
     */


    public static void main(String[] args) throws Exception {
        List<String> list=new ArrayList<>();
        list.add("张三");
        list.add("李四");
        List<String> list1=new ArrayList<>();
        list1.add("111");
        list1.add("222");
        ExcelWrite.writeToExcel("C:\\Users\\mkl\\Desktop\\1111.xlsx","aaaa",list1);
    }
}