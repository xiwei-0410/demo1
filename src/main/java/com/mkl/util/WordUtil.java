package com.mkl.util;

/**
 *
 * @author zhoulian
 * @time    2018/6/8 15:30:22
 */
public class WordUtil {

//    /**
//     *适用于word 2007
//     * @param param   需要替换的参数
//     * @param template 模板
//     */
//    public static XWPFDocument generateWord(Map<String, Object> param, String template) {
//        XWPFDocument doc = null;
//        try {
//            OPCPackage pack = POIXMLDocument.openPackage(template);//通过路径获取word模板
//            doc = new CustomXWPFDocument(pack);
//            //通过InputStream 获取模板，此方法适用于jar包部署
//            //  doc = new CustomXWPFDocument(template);
//            if (param != null && param.size() > 0) {
//                //处理段落
//                List<XWPFParagraph> paragraphList = doc.getParagraphs();
//                processParagraphs(paragraphList, param, doc);
//                //处理表格
//                Iterator<XWPFTable> it = doc.getTablesIterator();
//                while (it.hasNext()) {
//                    XWPFTable table = it.next();
//                    List<XWPFTableRow> rows = table.getRows();
//                    for (XWPFTableRow row : rows) {
//                        List<XWPFTableCell> cells = row.getTableCells();
//                        for (XWPFTableCell cell : cells) {
//                            List<XWPFParagraph> paragraphListTable = cell.getParagraphs();
//                            processParagraphs(paragraphListTable, param, doc);
//                        }
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return doc;
//    }
//
//    /**
//     * 处理段落
//     * @param paragraphList
//     * @throws FileNotFoundException
//     * @throws InvalidFormatException
//     */
//    public static void processParagraphs(List<XWPFParagraph> paragraphList, Map<String, Object> param, XWPFDocument doc) throws InvalidFormatException, FileNotFoundException {
//        if (paragraphList != null && paragraphList.size() > 0) {
//            for (XWPFParagraph paragraph : paragraphList) {
//                List<XWPFRun> runs = paragraph.getRuns();
//                for (XWPFRun run : runs) {
//                    String text = run.getText(0);
//                    if (text != null) {
//                        boolean isSetText = false;
//                        for (Entry<String, Object> entry : param.entrySet()) {
//                            String key = "${" + entry.getKey() + "}";
//                            if (text.indexOf(key) != -1) {
//                                isSetText = true;
//                                Object value = entry.getValue();
//                                if (value instanceof String) {//文本替换
//                                    text = text.replace(key, value.toString());
//                                } else if (value instanceof Map) {    //图片替换
//                                    text = text.replace(key, "");
//                                    Map pic = (Map) value;
//                                    int width = Integer.parseInt(pic.get("width").toString());
//                                    int height = Integer.parseInt(pic.get("height").toString());
//                                    int picType = getPictureType(pic.get("type").toString());
//                                    //获取图片流，因本人项目中适用流
//                                    //InputStream is = (InputStream) pic.get("content");
//                                    String byteArray = (String) pic.get("content");
//                                    CTInline inline = run.getCTR().addNewDrawing().addNewInline();
//                                    insertPicture(doc, byteArray, inline, width, height,picType);
//
//                                }
//                            }
//                        }
//                        if (isSetText) {
//                            run.setText(text, 0);
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    /**
//     * 获取图片对应类型代码
//     * @param picType
//     * @return int
//     */
//    private static int getPictureType(String picType) {
//        int res = CustomXWPFDocument.PICTURE_TYPE_PICT;
//        if (picType != null) {
//            if (picType.equalsIgnoreCase("png")) {
//                res = CustomXWPFDocument.PICTURE_TYPE_PNG;
//            } else if (picType.equalsIgnoreCase("dib")) {
//                res = CustomXWPFDocument.PICTURE_TYPE_DIB;
//            } else if (picType.equalsIgnoreCase("emf")) {
//                res = CustomXWPFDocument.PICTURE_TYPE_EMF;
//            } else if (picType.equalsIgnoreCase("jpg") || picType.equalsIgnoreCase("jpeg")) {
//                res = CustomXWPFDocument.PICTURE_TYPE_JPEG;
//            } else if (picType.equalsIgnoreCase("wmf")) {
//                res = CustomXWPFDocument.PICTURE_TYPE_WMF;
//            }
//        }
//        return res;
//    }
//
//    /**
//     * insert Picture
//     * @param document
//     * @param filePath
//     * @param inline
//     * @param width
//     * @param height
//     * @throws InvalidFormatException
//     * @throws FileNotFoundException
//     */
//    private static void insertPicture(XWPFDocument document, String filePath,
//                                      CTInline inline, int width,
//                                      int height,int imgType) throws InvalidFormatException,
//            FileNotFoundException {
//        //通过流获取图片，因本人项目中，是通过流获取
//        //document.addPictureData(imgFile,imgType);
//        document.addPictureData(new FileInputStream(filePath),imgType);
//        int id = document.getAllPictures().size() - 1;
//        final int EMU = 9525;
//        width *= EMU;
//        height *= EMU;
//        String blipId =
//                document.getAllPictures().get(id).getPackageRelationship().getId();
//        String picXml = getPicXml(blipId, width, height);
//        XmlToken xmlToken = null;
//        try {
//            xmlToken = XmlToken.Factory.parse(picXml);
//        } catch (XmlException xe) {
//            xe.printStackTrace();
//        }
//        inline.set(xmlToken);
//        inline.setDistT(0);
//        inline.setDistB(0);
//        inline.setDistL(0);
//        inline.setDistR(0);
//        CTPositiveSize2D extent = inline.addNewExtent();
//        extent.setCx(width);
//        extent.setCy(height);
//        CTNonVisualDrawingProps docPr = inline.addNewDocPr();
//        docPr.setId(id);
//        docPr.setName("IMG_" + id);
//        docPr.setDescr("IMG_" + id);
//    }
//
//    /**
//     * get the xml of the picture
//     *
//     * @param blipId
//     * @param width
//     * @param height
//     * @return
//     */
//    private static String getPicXml(String blipId, int width, int height) {
//        String picXml =
//                "" + "<a:graphic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
//                        "   <a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">" +
//                        "      <pic:pic xmlns:pic=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">" +
//                        "         <pic:nvPicPr>" + "            <pic:cNvPr id=\"" + 0 +
//                        "\" name=\"Generated\"/>" + "            <pic:cNvPicPr/>" +
//                        "         </pic:nvPicPr>" + "         <pic:blipFill>" +
//                        "            <a:blip r:embed=\"" + blipId +
//                        "\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\"/>" +
//                        "            <a:stretch>" + "               <a:fillRect/>" +
//                        "            </a:stretch>" + "         </pic:blipFill>" +
//                        "         <pic:spPr>" + "            <a:xfrm>" +
//                        "               <a:off x=\"0\" y=\"0\"/>" +
//                        "               <a:ext cx=\"" + width + "\" cy=\"" + height +
//                        "\"/>" + "            </a:xfrm>" +
//                        "            <a:prstGeom prst=\"rect\">" +
//                        "               <a:avLst/>" + "            </a:prstGeom>" +
//                        "         </pic:spPr>" + "      </pic:pic>" +
//                        "   </a:graphicData>" + "</a:graphic>";
//        return picXml;
//    }
//
//    public static void main(String[] args) throws Exception {
//        Map<String,Object> param = new HashMap<String, Object>();
//        Map<String,Object> header = new HashMap<String, Object>();
//        header.put("width", 556);
//        header.put("height", 230);
//        header.put("type", "jpg");
//        header.put("content", "C:\\Users\\mkl\\Desktop\\photo.jpg");//图片路径
//        param.put("${aaa}",header);
//        XWPFDocument doc = WordUtil.generateWord(param, "C:\\Users\\mkl\\Desktop\\33.docx");
//        FileOutputStream fopts = new FileOutputStream("C:\\Users\\mkl\\Desktop\\44.docx");
//        doc.write(fopts);
//        fopts.close();
//    }


}
