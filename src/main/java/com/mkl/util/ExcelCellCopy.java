package com.mkl.util;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.Iterator;
import java.util.List;

/**
 * Description: Excel 单元格 测试
 * @author w
 * @version 1.0
 * @date 2022/8/23 14:14
 *
 */
public class ExcelCellCopy {


    /**
     * @Description:  复制单元格 cell 样式
     * @param workbook
     * @param fromStyle
     * @param toStyle
     * @return  void
     * @version v1.0
     * @author wu
     * @date 2022/8/24 22:39
     */
    public static void copyCellStyle(HSSFWorkbook workbook, CellStyle fromStyle, CellStyle toStyle) {
        // 水平垂直对齐方式
        toStyle.setAlignment(fromStyle.getAlignment());
        toStyle.setVerticalAlignment(fromStyle.getVerticalAlignment());

        //边框和边框颜色
        toStyle.setBorderBottom(fromStyle.getBorderBottom());
        toStyle.setBorderLeft(fromStyle.getBorderLeft());
        toStyle.setBorderRight(fromStyle.getBorderRight());
        toStyle.setBorderTop(fromStyle.getBorderTop());
        toStyle.setTopBorderColor(fromStyle.getTopBorderColor());
        toStyle.setBottomBorderColor(fromStyle.getBottomBorderColor());
        toStyle.setRightBorderColor(fromStyle.getRightBorderColor());
        toStyle.setLeftBorderColor(fromStyle.getLeftBorderColor());
        toStyle.setFillBackgroundColor(fromStyle.getFillBackgroundColor());
        toStyle.setFillForegroundColor(fromStyle.getFillForegroundColor());

        toStyle.setDataFormat(fromStyle.getDataFormat());
        toStyle.setFillPattern(fromStyle.getFillPattern());

        if (fromStyle instanceof HSSFCellStyle) {
            // 处理字体获取：03版 xls
            HSSFCellStyle style = (HSSFCellStyle) fromStyle;
            toStyle.setFont(style.getFont(workbook));
        }

        toStyle.setHidden(fromStyle.getHidden());
        toStyle.setIndention(fromStyle.getIndention());
        toStyle.setLocked(fromStyle.getLocked());
        toStyle.setRotation(fromStyle.getRotation());
        toStyle.setWrapText(fromStyle.getWrapText());
    }

    /**
     * @Description: 复制 行row 数据
     * @return  void
     * @version v1.0
     * @author wu
     * @date 2022/8/24 22:40
     */
    public static void copyRow(HSSFWorkbook wb,Row row,Row fromRow, Row toRow) {
        short lastCellNum = row.getLastCellNum();
        System.out.println(lastCellNum);
        for (int i = 0; i < lastCellNum; i++) {
            Cell fromCel = fromRow.getCell(i);
            Cell destCell = toRow.createCell(i);
            CellStyle newstyle = wb.createCellStyle();
            copyCellStyle(wb, fromCel.getCellStyle(), newstyle);
            destCell.setCellStyle(newstyle);
        }
    }

}
