package com.wxw.poiUtil;

import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.PictureType;
import com.deepoove.poi.data.Pictures;
import com.deepoove.poi.data.style.PictureStyle;
import com.deepoove.poi.exception.RenderException;
import com.deepoove.poi.policy.AbstractRenderPolicy;
import com.deepoove.poi.render.RenderContext;
import com.deepoove.poi.util.BufferedImageUtils;
import com.deepoove.poi.util.SVGConvertor;
import com.deepoove.poi.util.UnitUtils;
import com.deepoove.poi.xwpf.BodyContainer;
import com.deepoove.poi.xwpf.BodyContainerFactory;
import com.deepoove.poi.xwpf.WidthScalePattern;
import com.deepoove.poi.xwpf.XWPFRunWrapper;
import io.swagger.models.auth.In;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.drawingml.x2006.main.CTGraphicalObject;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTAnchor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDrawing;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 自定义图片渲染
 */
public class MyPictureRenderPolicy extends AbstractRenderPolicy<Object> {

    @Override
    protected boolean validate(Object data) {
        if (null == data) {
            return false;
        } else if (data instanceof PictureRenderData) {
            return null != ((PictureRenderData) data).getPictureSupplier();
        } else {
            return true;
        }
    }

    @Override
    public void doRender(RenderContext<Object> context) throws Exception {
        MyPictureRenderPolicy.Helper.renderPicture(context.getRun(), wrapper(context.getData()));
    }

    @Override
    protected void afterRender(RenderContext<Object> context) {
        this.clearPlaceholder(context, false);
    }

    @Override
    protected void reThrowException(RenderContext<Object> context, Exception e) {
        this.logger.info("Render picture " + context.getEleTemplate() + " error: {}", e.getMessage());
        String alt = "";
        if (context.getData() instanceof PictureRenderData) {
            alt = ((PictureRenderData) context.getData()).getAltMeta();
        }

        context.getRun().setText(alt, 0);
    }

    private static PictureRenderData wrapper(Object object) {
        return object instanceof PictureRenderData ? (PictureRenderData) object : Pictures.of(object.toString()).fitSize().create();
    }


    public static class Helper {
        public static void renderPicture(XWPFRun run, PictureRenderData picture) throws Exception {
            Supplier<byte[]> supplier = picture.getPictureSupplier();
            byte[] imageBytes = (byte[]) supplier.get();
            if (null == imageBytes) {
                throw new IllegalStateException("Can't read picture byte arrays!");
            } else {
                PictureType pictureType = picture.getPictureType();
                if (null == pictureType) {
                    pictureType = PictureType.suggestFileType(imageBytes);
                }

                if (null == pictureType) {
                    throw new RenderException("PictureRenderData must set picture type!");
                } else {
                    PictureStyle style = picture.getPictureStyle();
                    if (null == style) {
                        style = new PictureStyle();
                    }

                    int width = style.getWidth();
                    int height = style.getHeight();
                    if (pictureType == PictureType.SVG) {
                        imageBytes = SVGConvertor.toPng(imageBytes, (float) width, (float) height);
                        pictureType = PictureType.PNG;
                    }

                    if (!isSetSize(style)) {
                        BufferedImage original = BufferedImageUtils.readBufferedImage(imageBytes);
                        width = original.getWidth();
                        height = original.getHeight();
                        if (style.getScalePattern() == WidthScalePattern.FIT) {
                            BodyContainer bodyContainer = BodyContainerFactory.getBodyContainer(((IBodyElement) run.getParent()).getBody());
                            int pageWidth = UnitUtils.twips2Pixel(bodyContainer.elementPageWidth((IBodyElement) run.getParent()));
                            if (width > pageWidth) {
                                double ratio = (double) pageWidth / (double) width;
                                width = pageWidth;
                                height = (int) ((double) height * ratio);
                            }
                        }
                    }

                    InputStream stream = new ByteArrayInputStream(imageBytes);
                    Throwable var25 = null;

                    try {
                        PictureStyle.PictureAlign align = style.getAlign();
                        if (null != align && run.getParent() instanceof XWPFParagraph) {
                            ((XWPFParagraph) run.getParent()).setAlignment(ParagraphAlignment.valueOf(align.ordinal() + 1));
                        }

                        XWPFRunWrapper wrapper = new XWPFRunWrapper(run, false);
                        wrapper.addPicture(stream, pictureType.type(), "Generated", Units.pixelToEMU(width), Units.pixelToEMU(height));

                        CTDrawing drawing = run.getCTR().getDrawingArray(0);
                        CTGraphicalObject graphicalobject = drawing.getInlineArray(0).getGraphic();
                        Integer num = getNumeric(run.toString())+1;
                        //拿到新插入的图片替换添加CTAnchor 设置浮动属性 删除inline属性
                        CTAnchor anchor = ExportUtil.getAnchorWithGraphic(graphicalobject, "Generated",
                                Units.toEMU(width), Units.toEMU(height),//图片大小
                                Units.toEMU(5)+(Units.toEMU(width)*num), Units.toEMU(-10), false);//相对当前段落位置 需要计算段落已有内容的左偏移

                        drawing.setAnchorArray(new CTAnchor[]{anchor});//添加浮动属性
                        drawing.removeInline(0);//删除行内属性
                    } catch (Throwable var20) {
                        var25 = var20;
                        throw var20;
                    } finally {
                        if (stream != null) {
                            if (var25 != null) {
                                try {
                                    stream.close();
                                } catch (Throwable var19) {
                                    var25.addSuppressed(var19);
                                }
                            } else {
                                stream.close();
                            }
                        }

                    }

                }
            }
        }

    }

    private static boolean isSetSize(PictureStyle style) {
        return (style.getWidth() != 0 || style.getHeight() != 0) && style.getScalePattern() == WidthScalePattern.NONE;
    }


    public static Integer getNumeric(String str) {
        String regEx="[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return Integer.valueOf(m.replaceAll("").trim());
    }

}

