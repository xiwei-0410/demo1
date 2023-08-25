package com.mkl.util;


import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.util.List;

public class ExcelCopyUtils {

    public static void main(String[] args) {
        ExcelWriter writer = ExcelUtil.getWriter(new File("C:\\Users\\wxw\\Desktop\\333.xls"));
        // startRow=5 行标为5的行后插入2行,样式拷贝第5行的样式
        int startRow= 10;
        int rows = 2;
        HSSFWorkbook book = (HSSFWorkbook) writer.getWorkbook();
        InsertRow(writer, startRow, rows, book,false);
    }

    /**
     *
     * @param writer ExcelWriter
     * @param startRow 插入行的行标,即在哪一行下插入
     * @param rows 插入多少行
     * @param workbook HSSFWorkbook
     * @param copyvalue 新行复制(startRow-1)行的样式,而且在拷贝行的时候可以指定是否需要拷贝值
     * @Author Wsong qzsoft
     */
    private static void InsertRow(ExcelWriter writer, int startRow, int rows, HSSFWorkbook workbook,Boolean copyvalue) {
        HSSFSheet sheet = workbook.getSheetAt(0);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setBorderRight(BorderStyle.THIN);
        if(sheet.getRow(startRow+1)==null){
            // 如果复制最后一行，首先需要创建最后一行的下一行，否则无法插入
            sheet.createRow(startRow+1);
        }

        //先获取原始的合并单元格address集合
        List<CellRangeAddress> originMerged = sheet.getMergedRegions();

        for (int i = sheet.getNumMergedRegions() - 1; i >= 0; i--) {
            CellRangeAddress region = sheet.getMergedRegion(i);
            //判断移动的行数后重新拆分
            if(region.getFirstRow()>startRow){
                sheet.removeMergedRegion(i);
            }
        }

        sheet.shiftRows(startRow,sheet.getLastRowNum(),rows,true,false);
        sheet.createRow(startRow);

        for(CellRangeAddress cellRangeAddress : originMerged) {
            //这里的是插入行的index，表示这行之后才重新合并
            if(cellRangeAddress.getFirstRow() > startRow) {
                int firstRow = cellRangeAddress.getFirstRow() + rows;
                CellRangeAddress newCellRangeAddress = new CellRangeAddress(firstRow, (firstRow + (cellRangeAddress
                        .getLastRow() - cellRangeAddress.getFirstRow())), cellRangeAddress.getFirstColumn(),
                        cellRangeAddress.getLastColumn());
                sheet.addMergedRegion(newCellRangeAddress);
            }
        }
        writer.flush();
        //复制行内容
        CellCopyPolicy cellCopyPolicy = new CellCopyPolicy();
        cellCopyPolicy.setCopyCellValue(copyvalue);
        cellCopyPolicy.isCopyCellValue();



        for (int i = 0; i < rows; i++) {
            //1pt = 20twips  pt excel twips poi
            Row row = sheet.createRow(startRow+i);
            //row.setHeight(((short)2520));
            row.createCell(0).setCellStyle(sheet.createRow(startRow).getRowStyle());

        }
        writer.close();
    }


}