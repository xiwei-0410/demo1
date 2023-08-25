package com.mkl.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;


public class blog {
    public static void main(String[] args) throws Exception {
        //测试添加图片
        String sourceFile="C:\\Users\\mkl\\Desktop\\33.docx";
        String targetFile="C:\\Users\\mkl\\Desktop\\22.docx";
        addStampImage(sourceFile, targetFile);
    }

    public static void addStampImage(String sourceFile, String targetFile) {
        XWPFDocument doc;
        try {
            doc = new XWPFDocument(new FileInputStream(sourceFile));
            for(XWPFTable table : doc.getTables()) {
                for(XWPFTableRow row : table.getRows()) {
                    for(XWPFTableCell cell : row.getTableCells()) {//遍历每一个单元格
                        if(cell.getText().contains("&评委签字：")) {//如果遇到"&章"则进行替换
                            try {
                                insertCellStamp(cell);//给带有要盖章字样的单元格 加上章的图片
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

            FileOutputStream fos = new FileOutputStream(targetFile);
            doc.write(fos);
            fos.close();

            doc.write(new FileOutputStream(targetFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void insertCellStamp(XWPFTableCell cell) throws InvalidFormatException, IOException {//给带有要盖章字样的单元格 加上章的图片
        List<String> stamps = new ArrayList<>();//存放要加入的图片
        int stampOrder = 0;//图片的序号，从0开始

        //获取需要的图片，
        for(XWPFParagraph para :cell.getParagraphs()) {
            String paraText = para.getText();//从段落中获取要盖的章的名称
//			System.out.println("para.getText():" + paraText);
            if(paraText != null) {
                String[] split = para.getText().split(" ");
                for(String s : split) {
                    s = s.trim();
                    if(!s.isEmpty() ) {
                        stamps.add(s.replace("&评委签字：", ""));//如：&章公章01.png，去掉标识符&章，只留下章的名字
                    }
                }
            }
        }

        String basedir = "E:";
        for(XWPFParagraph para :cell.getParagraphs()) {
            for (XWPFRun run : para.getRuns()) {
                //清空所有文字
                run.setText("", 0);
            }
//			for (int i =para.getRuns().size()-1 ; i>=0; i--) {
//				XWPFRun run = para.getRuns().get(i);
//				System.out.println("清空所有文字后：run.getText(0): " + run.getText(0));
//			}

            //插入图片
            for(int i = 0; i<stamps.size() && i<para.getRuns().size(); i++) {
                try {
                    XWPFRun run = para.getRuns().get(i);
                    FileInputStream is = new FileInputStream("C:\\Users\\mkl\\Desktop\\photo.jpg");
                    run.addPicture(is, XWPFDocument.PICTURE_TYPE_JPEG, "C:\\Users\\mkl\\Desktop\\photo.jpg", Units.toEMU(100), Units.toEMU(100));
                    is.close();
                    run.setText("  ",0);
                } catch (Exception e) {
                    System.out.println("Error: ========  插入单个公章图片时出错了:可能是图片路径不存在。不影响主流程");
                    e.printStackTrace();
                }
            }
        }
    }
}
