/*
package com.mkl.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelUtil {

    public static void main(String[] args) {
        List<Map<String,Object>> list =  readExcel();
        //System.out.println(list);
        System.out.println(list.size());
        String[] header = {"姓名","时间","设备id","状态"};
        String[] sheets = {"name","time","ids","scrapState"};
        exportExcel1(list,header,sheets);
        //JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(list));
        //System.out.println(jsonArray);
    }

    */
/**
     * 读取Excel测试，兼容 Excel 2003/2007/2010
     *//*

    public static List<Map<String,Object>> readExcel()
    {
        List<Map<String,Object>> list = new ArrayList<>();
        try {
            //创建文件对象
            File excelFile = new File("C:\\Users\\mkl\\Desktop\\333.xlsx");
            //文件流
            FileInputStream is = new FileInputStream(excelFile);
            //这种方式 Excel 2003/2007/2010 都是可以处理的
            Workbook workbook = WorkbookFactory.create(is);
            //Sheet的数量
            int sheetCount = workbook.getNumberOfSheets();
            //遍历每个Sheet
            for (int s = 0; s < sheetCount; s++) {
                Sheet sheet = workbook.getSheetAt(s);
                //获取总行数
                int rowCount = sheet.getPhysicalNumberOfRows();
                System.out.println("总行数===="+rowCount);
                //遍历每一行
                for (int r = 1; r < rowCount; r++) {
                    Row row = sheet.getRow(r);
                    //获取总列数
                    int cellCount = row.getPhysicalNumberOfCells();
                    //遍历每一个单元格
                    Map<String,Object> map = new HashMap<>(16);
                    for (int c = 0; c < cellCount; c++) {
                        Cell cell = row.getCell(c);
                        int cellType = cell.getCellType();
                        String cellValue = "";
                        switch(cellType){
                            case Cell.CELL_TYPE_STRING:
                                cellValue = cell.getStringCellValue();
                                break;
                            case Cell.CELL_TYPE_NUMERIC:
                                //格式转换
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                cellValue = sdf.format(cell.getDateCellValue());
                                break;
                        }
                        if(c == 0){
                            map.put("name",cellValue);
                        }else if(c == 1){
                            map.put("time",cellValue);
                        }else {
                            String bb = cellValue.substring(cellValue.lastIndexOf("scrapState"),cellValue.length());
                            String cc = bb.substring(0,bb.indexOf(","));
                            String dd = "{"+'"'+cc+"}";
                            JSONObject jsonObject = JSON.parseObject(dd);
                            map.put("scrapState",jsonObject.getJSONArray("scrapState").getString(0));
                            if("2".equals(jsonObject.getJSONArray("scrapState").getString(0))){
                                JSONObject object = JSONObject.parseObject(cellValue.substring(0,cellValue.indexOf(","))+"}");
                                String ids = object.getJSONArray("id").getString(0);
                                map.put("ids",ids);
                            }

                            //pc端
//                            JSONObject jsonObject = JSON.parseObject(cellValue);
//                            JSONArray array = jsonObject.getJSONArray("ids") == null?jsonObject.getJSONArray("id"):jsonObject.getJSONArray("ids");
//                            for(int i = 0;i<array.size();i++){
//                                String[] aa = array.get(i).toString().split(",");
//                                for (int j = 0; j <aa.length ; j++) {
//                                    Map<String,Object> data = new HashMap<>(16);
//                                    data.put("ids",aa[j]);
//                                    data.put("name",map.get("name"));
//                                    data.put("time",map.get("time"));
//                                    list.add(data);
//                                }
//                            }
                        }
                    }
                    list.add(map);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //单sheet
    public static void exportExcel1(List<Map<String, Object>> list, String[] titles,String[] keys) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFCellStyle style2 = workbook.createCellStyle();
        //设置居中
        Sheet sheet = workbook.createSheet();
        sheet.setDefaultColumnWidth(30);
        Row row = sheet.createRow(0);
        for (int q = 0; q < titles.length; q++) {
            Cell cell = row.createCell(q);
            cell.setCellValue(titles[q]);
        }

        for (int w = 0; w < list.size(); w++) {
            Row contentRow = sheet.createRow(w + 1);
            Map<String, Object> map = list.get(w);
            for (int j = 0; j < keys.length; j++) {
                if (map.get(keys[j]) == null || StringUtils.isBlank(map.get(keys[j]).toString())) {
                    map.put(keys[j], "");
                }
                Cell cell = contentRow.createCell(j);
                cell.setCellValue(map.get(keys[j]).toString());
                cell.setCellStyle(style2);
            }
        }
        try {
            OutputStream outputStream = new FileOutputStream("C:\\Users\\mkl\\Desktop\\"+UUID.randomUUID()+".xls");
            workbook.write(outputStream);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}
*/
