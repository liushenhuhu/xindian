package com.ruoyi.xindian.pdf.utils;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;
import com.ruoyi.xindian.pdf.domain.ReportData;
import com.ruoyi.xindian.pdf.domain.WeekPdfData;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.List;

public class PdfGenerator {


    public byte[] createWeekPdf(String fileName, LinkedList<WeekPdfData> weekPdfData, String patientName, String gender, String patientAge, String height, String weight) throws IOException {
        String title = "心电报告";
//        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(fileName));
//        Document doc = new Document(pdfDoc, PageSize.A4);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, PageSize.A4);

        // 设置字体  simhei.ttf黑体  SimSun宋体
//        PdfFont font = PdfFontFactory.createFont("./ruoyi-xindian/src/main/java/com/ruoyi/xindian/pdf/utils/STXIHEI.TTF", PdfEncodings.IDENTITY_H, true);
        PdfFont font = PdfFontFactory.createFont("/home/chenpeng/workspace/system/xindian/ttf/STXIHEI.TTF", PdfEncodings.IDENTITY_H, true);
        //设置标题
        titleSet(doc, title, font);
        //基本信息
        Table table_info = new Table(6);
        table_info.setWidth(UnitValue.createPercentValue(100));
        contextCell(table_info, "姓名：" + patientName, font, 1, 0, 1, 0);
        contextCell(table_info, "性别：" + gender, font, 1, 0, 0, 0);
        contextCell(table_info, "年龄：" + patientAge, font, 1, 0, 0, 1);
        contextCell(table_info, "身高：" + height, font, 0, 1, 1, 0);
        contextCell(table_info, "体重：" + weight, font, 0, 1, 0, 0);
        contextCell(table_info, "", font, 0, 1, 0, 1);
        if (weekPdfData == null || weekPdfData.isEmpty()) {
            PdfCanvas pdfCanvas = new PdfCanvas(doc.getPdfDocument().getLastPage());
            pdfCanvas.setFontAndSize(font, 20);
            pdfCanvas.setFillColor(ColorConstants.RED);
            pdfCanvas.beginText()
                    .moveText(35, 700)
                    .showText("最近7天未做检测！")
                    .endText();
        } else {
            int len = weekPdfData.size();
            Div div;
            for (int i = 0; i < len; i += 1) {
                // 使用Div容器来居中表格
                div = new Div();
                div.setHorizontalAlignment(HorizontalAlignment.CENTER); // 设置Div水平居中
                div.add(table_info);
                // 将Div添加到文档中
                doc.add(div);
                drawWeekEcg30(doc, font, weekPdfData.get(i), 35, 700, 525, 210);
                i += 1;
                if (i < len)
                    drawWeekEcg30(doc, font, weekPdfData.get(i), 35, 400, 525, 210);
                if (i < len - 1)
                    doc.add(new AreaBreak());
            }
        }
        doc.close();
        return baos.toByteArray();
    }

    private void drawWeekEcg30(Document doc, PdfFont font, WeekPdfData weekPdfData, float x, float y, float width, float height) {
        PdfCanvas pdfCanvas = new PdfCanvas(doc.getPdfDocument().getLastPage());
//        float[] ecgData = {-0.094f, -0.065f, -0.041f, -0.061f, -0.052f, -0.03f, 0.0f, -0.026f, 0.004f, 0.015f, 0.087f, 0.068f, 0.05f, -0.006f, -0.008f, -0.032f, -0.03f, -0.046f, -0.046f, -0.035f, -0.017f, -0.039f, -0.021f, 0.037f, 0.14f, 0.32f, 0.709f, 0.129f, -0.727f, -0.217f, -0.032f, 0.004f, 0.039f, 0.054f, 0.054f, 0.098f, 0.116f, 0.114f, 0.145f, 0.131f, 0.134f, 0.186f, 0.232f, 0.285f, 0.342f, 0.419f, 0.522f, 0.617f, 0.674f, 0.718f, 0.67f, 0.549f, 0.34f, 0.103f, -0.096f, -0.241f, -0.329f, -0.375f, -0.355f, -0.371f, -0.36f, -0.327f, -0.325f, -0.287f, -0.276f, -0.263f, -0.21f, -0.215f, -0.206f, -0.186f, -0.184f, -0.191f, -0.145f, -0.147f, -0.112f, -0.118f, -0.127f, -0.127f, -0.136f, -0.14f, -0.127f, -0.153f, -0.118f, -0.127f, -0.107f, -0.112f, -0.116f, -0.12f, -0.114f, -0.109f, -0.103f, -0.107f, -0.085f, -0.098f, -0.081f, -0.079f, -0.074f, -0.079f, -0.068f, -0.05f, -0.052f, -0.048f, 0.008f, 0.017f, 0.061f, 0.039f, 0.054f, 0.035f, -0.008f, -0.061f, -0.03f, -0.041f, -0.024f, -0.032f, -0.026f, -0.024f, -0.026f, 0.072f, 0.09f, 0.463f, 0.626f, -0.054f, -0.494f, 0.0f, 0.048f, 0.039f, 0.052f, 0.068f, 0.065f, 0.054f, 0.063f, 0.09f, 0.134f, 0.142f, 0.169f, 0.191f, 0.199f, 0.259f, 0.318f, 0.404f, 0.494f, 0.617f, 0.703f, 0.672f, 0.643f, 0.492f, 0.274f, 0.03f, -0.166f, -0.29f, -0.34f, -0.358f, -0.362f, -0.364f, -0.347f, -0.349f, -0.305f, -0.303f, -0.281f, -0.265f, -0.235f, -0.21f, -0.197f, -0.217f, -0.173f, -0.182f, -0.158f, -0.156f, -0.16f, -0.166f, -0.131f, -0.149f, -0.156f, -0.147f, -0.145f, -0.129f, -0.136f, -0.149f, -0.136f, -0.145f, -0.12f, -0.09f, -0.094f, -0.105f, -0.085f, -0.116f, -0.09f, -0.101f, -0.087f, -0.094f, -0.048f, -0.068f, -0.074f, -0.05f, -0.035f, -0.019f, 0.006f, 0.054f, 0.061f, -0.008f, 0.021f, -0.008f, -0.052f, -0.072f, -0.05f, -0.074f, -0.065f, -0.068f, -0.05f, -0.068f, 0.057f, 0.054f, 0.446f, 0.61f, -0.039f, -0.577f, -0.017f, 0.002f, 0.019f, 0.032f, 0.07f, 0.046f, 0.085f, 0.07f, 0.098f, 0.123f, 0.127f, 0.12f, 0.188f, 0.219f, 0.261f, 0.32f, 0.406f, 0.507f, 0.593f, 0.659f, 0.7f, 0.639f, 0.465f, 0.237f, 0.021f, -0.158f, -0.274f, -0.342f, -0.349f, -0.373f, -0.36f, -0.362f, -0.307f, -0.312f, -0.327f, -0.283f, -0.281f, -0.268f, -0.226f, -0.215f, -0.204f, -0.18f, -0.191f, -0.171f, -0.16f, -0.142f, -0.153f, -0.164f, -0.162f, -0.147f, -0.153f, -0.182f, -0.151f, -0.164f, -0.129f, -0.171f, -0.153f, -0.12f, -0.114f, -0.103f, -0.103f, -0.105f, -0.079f, -0.09f, -0.063f, -0.092f, -0.087f, -0.037f, 0.006f, 0.013f, 0.07f, 0.004f, 0.019f, -0.03f, -0.046f, -0.063f, -0.037f, -0.054f, -0.052f, -0.057f, -0.021f, -0.01f, 0.074f, 0.118f, 0.509f, 0.533f, -0.208f, -0.505f, -0.074f, 0.026f, 0.05f, 0.028f, 0.048f, 0.043f, 0.074f, 0.068f, 0.101f, 0.107f, 0.145f, 0.162f, 0.195f, 0.23f, 0.279f, 0.336f, 0.461f, 0.553f, 0.643f, 0.705f, 0.718f, 0.635f, 0.45f, 0.206f, -0.017f, -0.16f, -0.276f, -0.329f, -0.355f, -0.342f, -0.351f, -0.349f, -0.342f, -0.322f, -0.296f, -0.29f, -0.252f, -0.241f, -0.226f, -0.226f, -0.206f, -0.18f, -0.173f, -0.184f, -0.158f, -0.153f, -0.158f, -0.131f, -0.149f, -0.166f, -0.125f, -0.123f, -0.118f, -0.123f, -0.107f, -0.094f, -0.076f, -0.118f, -0.105f, -0.114f, -0.101f, -0.083f, -0.065f, -0.092f, -0.048f, -0.074f, -0.039f, -0.052f, -0.015f, 0.019f, 0.039f, 0.05f, 0.048f, 0.028f, -0.013f, -0.043f, -0.041f, -0.057f, -0.048f, -0.065f, -0.037f, -0.052f, -0.03f, 0.07f, 0.076f, 0.494f, 0.45f, -0.375f, -0.362f, -0.03f, 0.026f, 0.028f, 0.061f, 0.063f, 0.094f, 0.085f, 0.083f, 0.105f, 0.125f, 0.136f, 0.175f, 0.18f, 0.239f, 0.307f, 0.366f, 0.459f, 0.553f, 0.635f, 0.698f, 0.672f, 0.621f, 0.435f, 0.202f, -0.026f, -0.16f, -0.272f, -0.305f, -0.344f, -0.338f, -0.336f, -0.318f, -0.336f, -0.274f, -0.272f, -0.272f, -0.276f, -0.241f, -0.215f, -0.21f, -0.204f, -0.169f, -0.175f, -0.145f, -0.191f, -0.129f, -0.164f, -0.195f, -0.213f, -0.158f, -0.16f, -0.191f, -0.162f, -0.147f, -0.224f, -0.006f, -0.114f, 0.046f, 0.0f, 0.039f, 0.043f, -0.054f, -0.032f, -0.072f, -0.017f, -0.083f, -0.03f, -0.008f, -0.039f, 0.037f, -0.07f, 0.107f, 0.063f, 0.024f, 0.037f, -0.041f, -0.068f, -0.063f, -0.026f, -0.048f, -0.037f, -0.063f, -0.083f, 0.123f, 0.074f, 0.395f, 0.47f, -0.226f, -0.476f, 0.0f, 0.048f, 0.063f, 0.035f, 0.085f, 0.107f, 0.123f, 0.123f, 0.14f, 0.116f, 0.14f, 0.173f, 0.184f, 0.204f, 0.283f, 0.369f, 0.432f, 0.516f, 0.61f, 0.667f, 0.674f, 0.624f, 0.435f, 0.202f, -0.032f, -0.204f, -0.279f, -0.333f, -0.38f, -0.404f, -0.404f, -0.38f, -0.369f, -0.369f, -0.371f, -0.318f, -0.281f, -0.285f, -0.279f, -0.252f, -0.204f, -0.237f, -0.21f, -0.184f, -0.177f, -0.177f, -0.18f, -0.149f, -0.142f, -0.166f, -0.147f, -0.158f, -0.156f, -0.147f, -0.131f, -0.12f, -0.118f, -0.12f, -0.14f, -0.134f, -0.116f, -0.081f, -0.09f, -0.085f, -0.087f, -0.074f, -0.037f, -0.021f, 0.017f, -0.004f, -0.01f, -0.03f, -0.026f, -0.068f, -0.057f, -0.057f, -0.072f, -0.09f, -0.076f, -0.07f, -0.057f, 0.085f, 0.153f, 0.586f, 0.369f, -0.58f, -0.432f, -0.032f, -0.015f, -0.015f, 0.008f, -0.008f, 0.068f, 0.072f, 0.09f, 0.085f, 0.101f, 0.134f, 0.164f, 0.193f, 0.246f, 0.318f, 0.377f, 0.459f, 0.573f, 0.635f, 0.685f, 0.709f, 0.584f, 0.382f, 0.171f, -0.024f, -0.147f, -0.25f, -0.301f, -0.305f, -0.279f, -0.301f, -0.272f, -0.261f, -0.257f, -0.246f, -0.241f, -0.237f, -0.206f, -0.184f, -0.173f, -0.171f, -0.142f, -0.151f, -0.142f, -0.145f, -0.123f, -0.12f, -0.171f, -0.173f, -0.118f, -0.12f, -0.138f, -0.138f, -0.136f, -0.138f, -0.098f, -0.112f, -0.101f, -0.14f, -0.09f, -0.046f, 0.032f, 0.035f, 0.043f, 0.008f, 0.026f, -0.024f, -0.063f, -0.094f, -0.096f, -0.107f, -0.087f, -0.037f, -0.041f, -0.074f, 0.024f, -0.019f, 0.191f, 0.678f, 0.05f, -0.628f, -0.092f, 0.01f, 0.032f, 0.05f, 0.059f, 0.068f, 0.063f, 0.074f, 0.114f, 0.136f, 0.149f, 0.16f, 0.199f, 0.217f, 0.248f, 0.349f, 0.424f, 0.511f, 0.599f, 0.678f, 0.689f, 0.628f, 0.457f, 0.239f, -0.013f, -0.14f, -0.263f, -0.333f, -0.353f, -0.366f, -0.347f, -0.347f, -0.316f, -0.309f, -0.29f, -0.23f, -0.241f, -0.219f, -0.197f, -0.197f, -0.173f, -0.175f, -0.166f, -0.14f, -0.142f, -0.136f, -0.123f, -0.134f, -0.114f, -0.131f, -0.123f, -0.134f, -0.092f, -0.101f, -0.112f, -0.109f, -0.101f, -0.109f, -0.109f, -0.085f, -0.081f, -0.087f, -0.085f, -0.098f, -0.043f, 0.002f, 0.037f, 0.037f, -0.006f, -0.002f, -0.03f, -0.035f, -0.061f, -0.081f, -0.076f, -0.081f, -0.074f, -0.081f, -0.041f, 0.07f, -0.002f, 0.454f, 0.441f, -0.316f, -0.485f, -0.059f, 0.002f, -0.004f, 0.006f, 0.052f, 0.087f, 0.085f, 0.094f, 0.105f, 0.116f, 0.151f, 0.162f, 0.202f, 0.228f, 0.27f, 0.358f, 0.443f, 0.555f, 0.652f, 0.683f, 0.696f, 0.571f, 0.406f, 0.175f, -0.041f, -0.215f, -0.316f, -0.34f, -0.351f, -0.364f, -0.353f, -0.349f, -0.325f, -0.274f, -0.27f, -0.276f, -0.265f, -0.248f, -0.243f, -0.202f, -0.162f, -0.195f, -0.175f, -0.177f, -0.134f, -0.142f, -0.166f, -0.182f, -0.188f, -0.136f, -0.059f, -0.054f, -0.072f, -0.09f, -0.065f, -0.068f, -0.101f, -0.041f, -0.09f, -0.008f, -0.054f, -0.063f, -0.074f, -0.046f, -0.019f, -0.013f, -0.048f, -0.019f, 0.057f, 0.098f, 0.054f, 0.026f, 0.015f, -0.041f, -0.043f, -0.07f, -0.07f, -0.081f, -0.07f, -0.054f, -0.068f, -0.052f, 0.039f, 0.004f, 0.404f, 0.522f, -0.129f, -0.542f, -0.035f, 0.013f, 0.024f, 0.013f, 0.061f, 0.063f, 0.068f, 0.074f, 0.087f, 0.098f, 0.12f, 0.145f, 0.184f, 0.206f, 0.268f, 0.336f, 0.406f, 0.505f, 0.604f, 0.637f, 0.659f, 0.602f, 0.417f, 0.193f, -0.041f, -0.197f, -0.303f, -0.382f, -0.373f, -0.393f, -0.371f, -0.369f, -0.366f, -0.353f, -0.316f, -0.309f, -0.298f, -0.294f, -0.263f, -0.25f, -0.204f, -0.202f, -0.182f, -0.197f, -0.184f, -0.156f, -0.175f, -0.171f, -0.171f, -0.171f, -0.175f, -0.173f, -0.156f, -0.171f, -0.153f, -0.16f, -0.149f, -0.123f, -0.109f, -0.101f, -0.112f, -0.105f, -0.079f, -0.105f, -0.098f, -0.07f, -0.083f, -0.061f, -0.039f, 0.017f, 0.035f, 0.035f, -0.01f, -0.01f, -0.024f, -0.037f, -0.074f, -0.035f, -0.061f, -0.061f, -0.046f, -0.032f, -0.039f, 0.021f, 0.101f, 0.318f, 0.76f, 0.206f, -0.736f, -0.265f, -0.046f, 0.008f, 0.006f, 0.026f, 0.05f, 0.037f, 0.063f, 0.079f, 0.094f, 0.12f, 0.149f, 0.173f, 0.217f, 0.252f, 0.312f, 0.397f, 0.5f, 0.595f, 0.665f, 0.683f, 0.67f, 0.544f, 0.307f, 0.098f, -0.105f, -0.219f, -0.301f, -0.331f, -0.369f, -0.349f, -0.36f, -0.329f, -0.294f, -0.285f, -0.287f, -0.252f, -0.257f, -0.217f, -0.219f, -0.195f, -0.18f, -0.166f, -0.151f, -0.14f, -0.138f, -0.136f, -0.136f, -0.134f, -0.142f, -0.142f, -0.134f, -0.105f, -0.094f, -0.096f, -0.09f, -0.087f, -0.083f, -0.083f, -0.065f, -0.063f, -0.061f, -0.081f, -0.05f, -0.013f, -0.01f, 0.026f, 0.0f, -0.008f, 0.002f, -0.039f, -0.052f, -0.07f, -0.057f, -0.054f, -0.046f, -0.037f, -0.035f, 0.006f, 0.116f, 0.114f, 0.606f, 0.465f, -0.461f, -0.408f, -0.03f, 0.024f, 0.046f};
        float[] ecgData = weekPdfData.getEcgData();
        //信息
        int co = 80;
        int lo = 15;
        pdfCanvas.setFontAndSize(font, 10);
        pdfCanvas.setFillColor(ColorConstants.BLACK);
        pdfCanvas.beginText()
                .moveText(x, y)
                .showText("AI分析结果：" + weekPdfData.getAiConclusion() + "    检测时间：" + weekPdfData.getDetectionTime())
                .endText();
        pdfCanvas.beginText()
                .moveText(x, y - lo)
                .showText("心率(bpm)：" + (weekPdfData.getHr() == null ? "-" : weekPdfData.getHr()))
                .endText();
        pdfCanvas.beginText()
                .moveText(x + co, y - lo)
                .showText("P波(ms)：" + (weekPdfData.getP() == null ? "-" : weekPdfData.getP()))
                .endText();
        pdfCanvas.beginText()
                .moveText(x + 2 * co, y - lo)
                .showText("RR间期(ms)：" + (weekPdfData.getRr() == null ? "-" : weekPdfData.getRr()))
                .endText();
        pdfCanvas.beginText()
                .moveText(x + 3 * (co + 5), y - lo)
                .showText("QRS波群(ms)：" + (weekPdfData.getQrs() == null ? "-" : weekPdfData.getQrs()))
                .endText();
        pdfCanvas.beginText()
                .moveText(x + 4 * (co + 10), y - lo)
                .showText("QTc(ms)：" + (weekPdfData.getQtc() == null ? "-" : weekPdfData.getQtc()))
                .endText();
        pdfCanvas.beginText()
                .moveText(x + 5 * (co + 10), y - lo)
                .showText("HRV(ms)：" + (weekPdfData.getHrv() == null ? "-" : weekPdfData.getHrv()))
                .endText();
        pdfCanvas.stroke();
        // 设置绘图区域
        y = y - lo - 3;
        com.itextpdf.kernel.geom.Rectangle rectangle = new com.itextpdf.kernel.geom.Rectangle(x, y - height, width, height);
        // 设置画布边框
        pdfCanvas.setLineWidth(0.1f);
        pdfCanvas.setStrokeColor(ColorConstants.BLACK);
        pdfCanvas.rectangle(rectangle);
        pdfCanvas.stroke();
        pdfCanvas.setLineWidth(0.2f);

        //一大格
        double pigK = 10.5;
        //一小格
        double smallK = 10.5 / 5;
        for (double i = y - height; i < y; i += pigK) {
            pdfCanvas.moveTo(x, i);
            pdfCanvas.lineTo(x + width, i);
            pdfCanvas.stroke();
        }
        for (double i = x; i < x + width; i += pigK) {
            pdfCanvas.moveTo(i, y - height);
            pdfCanvas.lineTo(i, y);
            pdfCanvas.stroke();
        }
        int index = 0;
        int indey = 0;
        for (double i = y - height + smallK; i < y; i += smallK) {
            index++;
            if (index % 5 == 0) continue;
            indey = 0;
            for (double j = x + smallK; j < x + width; j += smallK) {
                indey++;
                if (indey % 5 == 0) continue;
                pdfCanvas.circle(j, i, 0.2);
                pdfCanvas.fill();
            }
        }

        //画心电图
        pdfCanvas.setFontAndSize(font, 8);
        pdfCanvas.setLineWidth(0.4f);
        pdfCanvas.setFillColor(ColorConstants.BLACK);
        double dx, dy;
        dx = x;
        dy = y - height + 16 * pigK;
        pdfCanvas.moveTo(dx, dy);
        int flag = 0;
        int sampleRate = 250;
        for (double i = x; i < x + width; i += 5 * pigK / sampleRate) {
            pdfCanvas.lineTo(i, dy + ecgData[flag] * 2 * pigK);
            flag++;
            if (flag >= ecgData.length / 3) break;
        }
        dy = y - height + 10 * pigK;
        pdfCanvas.moveTo(dx, dy);
        for (double i = x; i < x + width; i += 5 * pigK / sampleRate) {
            pdfCanvas.lineTo(i, dy + ecgData[flag] * 2 * pigK);
            flag++;
            if (flag >= ecgData.length / 3 * 2) break;
        }
        dy = y - height + 4 * pigK;
        pdfCanvas.moveTo(dx, dy);
        for (double i = x; i < x + width; i += 5 * pigK / sampleRate) {
            pdfCanvas.lineTo(i, dy + ecgData[flag] * 2 * pigK);
            flag++;
            if (flag >= ecgData.length) break;
        }
        pdfCanvas.stroke();
    }

    public void createPdf(String fileName, ReportData reportData) throws IOException, ParseException {
        String title = "心电报告";
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(fileName));
        Document doc = new Document(pdfDoc, PageSize.A4);
        // 设置字体  simhei.ttf黑体  SimSun宋体
        System.out.println(System.getProperty("user.dir"));
//        PdfFont font = PdfFontFactory.createFont("./ruoyi-xindian/src/main/java/com/ruoyi/xindian/pdf/utils/STXIHEI.TTF", PdfEncodings.IDENTITY_H, true);
        PdfFont font = PdfFontFactory.createFont("/home/chenpeng/workspace/system/xindian/ttf/STXIHEI.TTF", PdfEncodings.IDENTITY_H, true);
        //设置标题
        titleSet(doc, title, font);
        // 创建表格
        int numColumns = 6;
        Table table = new Table(numColumns);
        table.setWidth(UnitValue.createPercentValue(100)); // 表格宽度设置为100%
//        table.setFixedPosition(1, 1, 1); // 设置表格在页面上的位置（可选）
        //添加内容
        contextCell(table, "姓名：" + reportData.getPatientName(), font, 1, 0, 1, 0);
        contextCell(table, "性别：" + reportData.getGender(), font, 1, 0, 0, 0);
        contextCell(table, "年龄：" + reportData.getAge(), font, 1, 0, 0, 1);
        contextCell(table, "病历号：" + reportData.getPid(), font, 0, 0, 1, 0);
        contextCell(table, "科室：" + reportData.getDepartment(), font, 0, 0, 0, 0);
        contextCell(table, "病房/病床：" + reportData.getHospitalBed(), font, 0, 0, 0, 1);

        Cell headerCell1 = new Cell(1, 3).add(new Paragraph("临床诊断：" + reportData.getClinicalDiagnosis()).setTextAlignment(TextAlignment.LEFT).setFont(font).setFontSize(10));
        vanishLine(headerCell1, 0, 0, 1, 0);
        Cell headerCell2 = new Cell(1, 3).add(new Paragraph("用药：" + reportData.getMedicate()).setTextAlignment(TextAlignment.LEFT).setFont(font).setFontSize(10));
        vanishLine(headerCell2, 0, 0, 0, 1);
        table.addCell(headerCell1);
        table.addCell(headerCell2);

        Cell headerCell3 = new Cell(1, 4).add(new Paragraph("心电检测时间：" + reportData.getDetectionTime()).setTextAlignment(TextAlignment.LEFT).setFont(font).setFontSize(10));
        Cell headerCell4 = new Cell(1, 2).add(new Paragraph("分析时长：" + reportData.getAnalysisTime()).setTextAlignment(TextAlignment.LEFT).setFont(font).setFontSize(10));
        vanishLine(headerCell3, 0, 1, 1, 0);
        vanishLine(headerCell4, 0, 1, 0, 1);
        table.addCell(headerCell3);
        table.addCell(headerCell4);


        Cell hrHeadCell = new Cell(1, 3).add(new Paragraph("心率").setTextAlignment(TextAlignment.CENTER).setFont(font).setFontSize(12));
        vanishLine(hrHeadCell, 1, 0, 1, 1);
        table.addCell(hrHeadCell);
        Cell afHeadCell = new Cell(1, 3).add(new Paragraph("房扑/房颤事件").setTextAlignment(TextAlignment.CENTER).setFont(font).setFontSize(12));
        vanishLine(afHeadCell, 1, 0, 1, 1);
        table.addCell(afHeadCell);
        //心率
        Cell hrCell = new Cell(1, 3)
                .add(new Paragraph(
                        "总心博数：" + reportData.getBeatCount() + "\n" +
                                "平均心率(bpm)：" + reportData.getMeanHr() + "\n" +
                                "最快心率(bpm)：" + reportData.getMaxHr() + "\n" +
                                "最慢心率(bpm)：" + reportData.getMinHr() + "\n" +
                                "停搏次数(>2.0s)：" + reportData.getCardiacArrestcount() + "\n" +
                                "最长停搏(s)：" + reportData.getCardiacArrestTime()).setTextAlignment(TextAlignment.LEFT).setFont(font).setFontSize(10));
        vanishLine(hrCell, 0, 1, 1, 1);
        table.addCell(hrCell);
        //房颤、房扑
        Cell afCell = new Cell(1, 3)
                .add(new Paragraph(
                        "房扑/房颤总阵数：" + reportData.getAfCount() + "\n" +
                                "总时长：" + reportData.getAfTime() + "\n" +
                                "最快(bpm)：" + reportData.getMaxAf() + "\n" +
                                "最慢(bpm)：" + reportData.getMinAf() + "\n" +
                                "最长持续时间：" + reportData.getAfLongTime()).setTextAlignment(TextAlignment.LEFT).setFont(font).setFontSize(10));
        vanishLine(afCell, 0, 1, 1, 1);
        table.addCell(afCell);

        //房性事件
        Cell cell3 = new Cell(1, 3).add(new Paragraph("房性事件").setTextAlignment(TextAlignment.CENTER).setFont(font).setFontSize(12));
        vanishLine(cell3, 1, 0, 1, 1);
        table.addCell(cell3);
        Cell cell4 = new Cell(1, 3).add(new Paragraph("室性事件").setTextAlignment(TextAlignment.CENTER).setFont(font).setFontSize(12));
        vanishLine(cell4, 1, 0, 1, 1);
        table.addCell(cell4);
        //房性事件
        Cell cell3_1 = new Cell(1, 3)
                .add(new Paragraph(
                        "房性心博总数：" + reportData.getAbCount() + "\n" +
                                "成对房早：" + reportData.getPairAb() + "\n" +
                                "二联律总阵数：" + reportData.getAbBigeminy() + "\n" +
                                "三联率总阵数：" + reportData.getAbTrigeminy() + "\n" +
                                "房速总阵数：" + reportData.getAtCount() + "\n" +
                                "最快房速心博数：" + reportData.getMaxAtCount() + "\n" +
                                "最快房速心率(bpm)：" + reportData.getMaxAt() + "\n" +
                                "最长持续时间：" + reportData.getAtLongTime()).setTextAlignment(TextAlignment.LEFT).setFont(font).setFontSize(10));
        vanishLine(cell3_1, 0, 1, 1, 1);
        table.addCell(cell3_1);
        //室性事件
        Cell cell4_1 = new Cell(1, 3)
                .add(new Paragraph(
                        "室性心博总数：" + reportData.getVbCount() + "\n" +
                                "成对室早：" + reportData.getPairVb() + "\n" +
                                "二联律总阵数：" + reportData.getVbBigeminy() + "\n" +
                                "三联率总阵数：" + reportData.getVbTrigeminy() + "\n" +
                                "室速总阵数：" + reportData.getVtCount() + "\n" +
                                "最快室速心博数：" + reportData.getMaxVtCount() + "\n" +
                                "最快室速心率(bpm)：" + reportData.getMaxVt() + "\n" +
                                "最长持续时间：" + reportData.getVtLongTime()).setTextAlignment(TextAlignment.LEFT).setFont(font).setFontSize(10));
        vanishLine(cell4_1, 0, 1, 1, 1);
        table.addCell(cell4_1);

        //房性事件
        Cell cell5 = new Cell(1, 3).add(new Paragraph("交界性事件").setTextAlignment(TextAlignment.CENTER).setFont(font).setFontSize(12));
        vanishLine(cell5, 1, 0, 1, 1);
        table.addCell(cell5);
        Cell cell6 = new Cell(1, 3).add(new Paragraph("心率变异性").setTextAlignment(TextAlignment.CENTER).setFont(font).setFontSize(12));
        vanishLine(cell6, 1, 0, 1, 1);
        table.addCell(cell6);
        //房性事件
        Cell cell5_1 = new Cell(1, 3)
                .add(new Paragraph(
                        "交界性心博总数：" + reportData.getBorderLineCount() + "\n" +
                                "成对交界性早搏：" + reportData.getPairborderLine() + "\n" +
                                "二联律总阵数：" + reportData.getBorderLineBigeminy() + "\n" +
                                "三联率总阵数：" + reportData.getBorderLineTrigeminy() + "\n" +
                                "逸搏心搏总数：" + reportData.getEscape()).setTextAlignment(TextAlignment.LEFT).setFont(font).setFontSize(10));
        vanishLine(cell5_1, 0, 1, 1, 1);
        table.addCell(cell5_1);
        //室性事件
        Cell cell6_1 = new Cell(1, 3)
                .add(new Paragraph(
                        "SDNN(ms)：" + reportData.getSdnn() + "\n" +
                                "rMSSD(ms)：" + reportData.getRmssd() + "\n" +
                                "NN20(ms)：" + reportData.getNn20() + "\n" +
                                "PNN20(%)：" + reportData.getPnn20() + "\n" +
                                "NN50(ms)：" + reportData.getNn50() + "\n" +
                                "PNN50(%)：" + reportData.getPnn50()).setTextAlignment(TextAlignment.LEFT).setFont(font).setFontSize(10));
        vanishLine(cell6_1, 0, 1, 1, 1);
        table.addCell(cell6_1);


        //结论
        Table table1 = new Table(numColumns);
        table1.setWidth(UnitValue.createPercentValue(100));
        Cell con = new Cell(1, 6).add(new Paragraph("结论").setTextAlignment(TextAlignment.CENTER).setFont(font).setFontSize(16));
        table1.addCell(con);
        Cell text = new Cell(4, 6).add(
                new Paragraph(reportData.getConclusion()).setPaddingLeft(10).setHeight(180).setPaddingTop(5).setTextAlignment(TextAlignment.LEFT).setFont(font).setFontSize(10));
        table1.addCell(text);
        Cell name = new Cell(1, 3).add(new Paragraph("签名：").setTextAlignment(TextAlignment.LEFT).setFont(font).setFontSize(8));
        name.setBorderRight(Border.NO_BORDER);
        table1.addCell(name);
        Cell time = new Cell(1, 3).add(new Paragraph("日期：" + reportData.getReportTime()).setPaddingRight(5).setTextAlignment(TextAlignment.RIGHT).setFont(font).setFontSize(8));
        time.setBorderLeft(Border.NO_BORDER);
        table1.addCell(time);

        // 使用Div容器来居中表格
        Div div = new Div();
        div.setHorizontalAlignment(HorizontalAlignment.CENTER); // 设置Div水平居中
        div.add(table); // 将表格添加到Div中
        div.add(new Paragraph().setMarginBottom(5));
        div.add(table1); // 将表格添加到Div中
        // 将Div添加到文档中
        doc.add(div);
        doc.add(new AreaBreak());
        //心率失常统计表
        arrhythmiaTable(doc, font, reportData);
        doc.add(new AreaBreak());
        //趋势波形图
        waveformTable(doc, font, reportData);
        doc.add(new AreaBreak());
        //心率变异性时域分析
        HRVTimeTable(doc, font, reportData);
        doc.add(new AreaBreak());
//        //心率变异性频域分析
//        HRVFrequencyTable(doc, font);
//        doc.add(new AreaBreak());
        //心电片段
        ECGTable(doc, font, pdfDoc, reportData);
        // 关闭文档
        doc.close();
    }

    //基本信息
    public Table basicInfo(PdfFont font, ReportData reportData) {
        Table table = new Table(6);
        table.setWidth(UnitValue.createPercentValue(100));
        contextCell(table, "姓名：" + reportData.getPatientName(), font, 1, 0, 1, 0);
        contextCell(table, "性别：" + reportData.getGender(), font, 1, 0, 0, 0);
        contextCell(table, "年龄：" + reportData.getAge(), font, 1, 0, 0, 1);
        contextCell(table, "病历号：" + reportData.getPid(), font, 0, 1, 1, 0);
        contextCell(table, "科室：" + reportData.getDepartment(), font, 0, 1, 0, 0);
        contextCell(table, "病房/病床：" + reportData.getHospitalBed(), font, 0, 1, 0, 1);
        return table;
    }


    //心率失常统计表
    private void arrhythmiaTable(Document doc, PdfFont font, ReportData reportData) throws IOException, ParseException {
        //设置标题
        titleSet_s(doc, "心率失常统计表", font);

        Table table = new Table(6);
        table.setWidth(UnitValue.createPercentValue(100));

        int maxVb = Collections.max(reportData.getVbList());
        if (maxVb % 10 != 0) maxVb = (maxVb / 10 + 1) * 10;
        int minVb = Collections.min(reportData.getVbList());
        if (minVb % 10 != 0) minVb = (minVb / 10) * 10;

        int maxSVb = Collections.max(reportData.getSuperVbList());
        if (maxSVb % 10 != 0) maxSVb = (maxSVb / 10 + 1) * 10;
        int minSVb = Collections.min(reportData.getSuperVbList());
        if (minSVb % 10 != 0) minSVb = (minSVb / 10) * 10;

        int maxLPL = Collections.max(reportData.getLongPauseList());
        if (maxLPL % 10 != 0) maxLPL = (maxLPL / 10 + 1) * 10;
        int minLPL = Collections.min(reportData.getLongPauseList());
        if (minLPL % 10 != 0) minLPL = (minLPL / 10) * 10;

        int maxAF = Collections.max(reportData.getAfList());
        if (maxAF % 10 != 0) maxAF = (maxAF / 10 + 1) * 10;
        int minAF = Collections.min(reportData.getAfList());
        if (minAF % 10 != 0) minAF = (minAF / 10) * 10;


        //心率趋势
        drawLine(table, "心率趋势", reportData.getHr(), font);
        //室性早搏
        drawBar(table, "室性早搏\n(每小时)", maxVb, minVb, (maxVb - minVb) / 5, getBarDataset(reportData.getVbList()), font);
        //室上性早搏
        drawBar(table, "室性上早搏\n(每小时)", maxSVb, minSVb, (maxSVb - minSVb) / 5, getBarDataset(reportData.getSuperVbList()), font);
        //长间歇long intervals
        drawBar(table, "长间歇\n(每小时)", maxLPL, minLPL, (maxLPL - minLPL) / 5, getBarDataset(reportData.getLongPauseList()), font);
        //房扑、房颤
        drawBar(table, "房扑、房颤\n(每小时)", maxAF, minAF, (maxAF - minAF) / 5, getBarDataset(reportData.getAfList()), font);
        //信息统计表
        Table tableInfo = new Table(16);
        tableInfo.setWidth(UnitValue.createPercentValue(100));
        //表头
        LinkedList<String> headList = new LinkedList<>(Arrays.asList("时间", "平均心率", "最慢心率", "最快心率", "室早", "成对室早",
                "室速", "室性二联律", "室性三联律", "室上性早搏", "成对室上性早搏", "室上速", "室上性二联律", "室上性三联律", "长间歇"));
        getTableInfo(tableInfo, headList, font, true);
        //内容
        List<List<String>> statisticalTable = reportData.getStatisticalTable();
        for (List<String> infoList : statisticalTable) {
            getTableInfo(tableInfo, infoList, font, false);
        }

        // 使用Div容器来居中表格
        Div div = new Div();
        div.setHorizontalAlignment(HorizontalAlignment.CENTER); // 设置Div水平居中
        div.add(basicInfo(font, reportData)); // 将表格添加到Div中
        div.add(new Paragraph().setMarginBottom(10));
        div.add(table);
        div.add(new Paragraph().setMarginBottom(10));
        div.add(tableInfo);
        // 将Div添加到文档中
        doc.add(div);

        PdfCanvas pdfCanvas = new PdfCanvas(doc.getPdfDocument().getLastPage());
        pdfCanvas.setLineWidth(0.2f);
        pdfCanvas.setFontAndSize(font, 5);
        pdfCanvas.setFillColor(ColorConstants.BLACK);
        pdfCanvas.moveTo(93, 710);
        pdfCanvas.lineTo(553, 710);
        int index = 0;
        for (double i = 93; i <= 553 + 10; i += (460.0 / 24)) {
            pdfCanvas.moveTo(i, 710);
            pdfCanvas.lineTo(i, 712);
            pdfCanvas.stroke();
            pdfCanvas.beginText()
                    .moveText(i - 4, 713)
                    .showText(String.valueOf(index) + ":00")
                    .endText();
            index++;
        }
        pdfCanvas.stroke();


    }

    private void getTableInfo(Table table, List<String> infoList, PdfFont font, Boolean isTitle) {
        Cell cell = null;
        int index = 0;
        for (String integer : infoList) {
            if (index == 0)
                cell = new Cell(1, 2).add(new Paragraph(infoList.get(0)).setTextAlignment(TextAlignment.CENTER).setFont(font).setFontSize(5));
            else
                cell = new Cell(1, 1).add(new Paragraph(integer).setTextAlignment(TextAlignment.CENTER).setFont(font).setFontSize(5));

            if (isTitle)
                cell.setBackgroundColor(new DeviceRgb(0, 238, 238));
            table.addCell(cell);
            index++;
        }
    }

    //柱状图
    private void drawLine(Table table, String title, List<Float> dataSet, PdfFont font) throws IOException, ParseException {
        ByteArrayOutputStream lineBos = new ByteArrayOutputStream();
        BufferedImage bufferedImage = ChartUtil.lineChart("", "", "", dataSet).createBufferedImage(1600, 250);
        ImageIO.write(bufferedImage, "png", lineBos);
        Image image = new Image(ImageDataFactory.create(lineBos.toByteArray()));
        // 调整图像在PDF中的大小以适应页面
        image.scaleToFit(520, 100);
        image.setAutoScale(true);
        Cell cell = new Cell(1, 2).add(new Paragraph(title).setTextAlignment(TextAlignment.CENTER).setFont(font).setFontSize(7));
        cell.setTextAlignment(TextAlignment.CENTER);
        cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.setWidth(35);
        Cell cell1 = new Cell(1, 4).add(image);
        vanishLine(cell, 0, 0, 0, 0);
        vanishLine(cell1, 0, 0, 0, 0);
        table.addCell(cell);
        table.addCell(cell1);

    }

    //折线图
    private void drawBar(Table table, String title, int maxY, int minY, int LY, DefaultCategoryDataset dataSet, PdfFont font) throws IOException {
        ByteArrayOutputStream barBos = new ByteArrayOutputStream();
        BufferedImage bar = ChartUtil.barChart("", "", "", dataSet, maxY, minY, LY).createBufferedImage(1600, 250);
        ImageIO.write(bar, "png", barBos);
        Image barimage = new Image(ImageDataFactory.create(barBos.toByteArray()));
        // 调整图像在PDF中的大小以适应页面
        barimage.setAutoScale(true);
        Cell cell = new Cell(1, 2).add(new Paragraph(title).setTextAlignment(TextAlignment.CENTER).setFont(font).setFontSize(7));
        cell.setTextAlignment(TextAlignment.CENTER);
        cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.setWidth(35);
        Cell cell1 = new Cell(1, 4).add(barimage);
        vanishLine(cell, 0, 0, 0, 0);
        vanishLine(cell1, 0, 0, 0, 0);
        table.addCell(cell);
        table.addCell(cell1);
    }

    private static DefaultCategoryDataset getLineDataset(List<Float> list) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int index = 0;
        for (Float f : list) {
            dataset.addValue(f, "", String.valueOf(index));
            index++;
        }
        return dataset;
    }

    private static DefaultCategoryDataset getBarDataset(List<Integer> list) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int index = 0;
        for (Integer integer : list) {
            dataset.addValue(integer, "", String.valueOf(index));
            index++;
        }
        return dataset;
    }

    //趋势波形图
    private void waveformTable(Document doc, PdfFont font, ReportData reportData) {
        //设置标题
        titleSet_s(doc, "趋势波形图", font);
        // 使用Div容器来居中表格
        Div div = new Div();
        div.setHorizontalAlignment(HorizontalAlignment.CENTER); // 设置Div水平居中
        div.add(basicInfo(font, reportData)); // 将表格添加到Div中
        // 将Div添加到文档中
        doc.add(div);
        // 创建 PdfCanvas 对象
        PdfCanvas pdfCanvas = new PdfCanvas(doc.getPdfDocument().getLastPage());
        float x = 35, y = 720, width = 525, height = 160;
        // 设置绘图区域
        com.itextpdf.kernel.geom.Rectangle rectangle = new com.itextpdf.kernel.geom.Rectangle(x, y - height, width, height);
        // 设置画布边框
        pdfCanvas.setLineWidth(0.1f);
        pdfCanvas.setStrokeColor(ColorConstants.BLACK);
        pdfCanvas.rectangle(rectangle);
        pdfCanvas.stroke();
        pdfCanvas.setLineWidth(0.2f);

        pdfCanvas.setFontAndSize(font, 6);
        pdfCanvas.setFillColor(ColorConstants.BLACK);
        pdfCanvas.beginText()
                .moveText(x + 10, y - 6)
                .showText("（次/每分）")
                .endText();
        pdfCanvas.beginText()
                .moveText(x + 2, y - 60)
                .showText("心率")
                .endText();
        pdfCanvas.beginText()
                .moveText(x + 45, y - 30)
                .showText("180 -")
                .endText();
        pdfCanvas.beginText()
                .moveText(x + 45, y - 60)
                .showText("120 -")
                .endText();
        pdfCanvas.beginText()
                .moveText(x + 47, y - 90)
                .showText("60 -")
                .endText();
        pdfCanvas.beginText()
                .moveText(x + 42, y - 132)
                .showText("0.10 -")
                .endText();
        pdfCanvas.beginText()
                .moveText(x + 42, y - 142)
                .showText("0.00 -")
                .endText();
        pdfCanvas.beginText()
                .moveText(x + 40, y - 152)
                .showText("-0.10 -")
                .endText();

        pdfCanvas.beginText()
                .moveText(x + 13, y - 126)
                .showText("（mv）")
                .endText();
        pdfCanvas.beginText()
                .moveText(x + 2, y - 140)
                .showText("ST(II)")
                .endText();
        pdfCanvas.beginText()
                .moveText(x + 15, y - height - 10)
                .showText("07-04")
                .endText();
        for (int i = 0; i <= 24; i++) {
            String time = i + ":00";
            if (i < 10) time = "0" + time;
            pdfCanvas.beginText()
                    .moveText(x + 53 + i * 19.375, y - height - 10)
                    .showText(time)
                    .endText();
        }
        pdfCanvas.moveTo(x, y - 120);
        pdfCanvas.lineTo(x + width, y - 120);
        pdfCanvas.moveTo(x + 60, y - 140);
        pdfCanvas.lineTo(x + width, y - 140);
        pdfCanvas.moveTo(x + 60, y);
        pdfCanvas.lineTo(x + 60, y - height);
        //画心率
        List<Float> hrList = reportData.getHr();
        if (hrList == null) hrList = new LinkedList<>();
        double index = 0;
        for (int i = 0; i < hrList.size() - 1; i++) {
            pdfCanvas.moveTo(x + 60 + index, y - 120 + hrList.get(i) / 2);
            pdfCanvas.lineTo(x + 60 + index + 0.323, y - 120 + hrList.get(i + 1) / 2);
            index += 0.323;
        }
        pdfCanvas.stroke();
        pdfCanvas.setLineDash(2f, 1f);
        for (int i = 1; i < 24; i++) {
            pdfCanvas.moveTo(x + 60 + i * 19.375, y - 120);
            pdfCanvas.lineTo(x + 60 + i * 19.375, y - height);
        }
        pdfCanvas.stroke();
    }

    //心率变异性时域分析
    private void HRVTimeTable(Document doc, PdfFont font, ReportData reportData) throws IOException {
        //设置标题
        Table table = new Table(7);
        table.setWidth(UnitValue.createPercentValue(100)); // 表格宽度设置为100%
        titleSet_s(doc, "心率变异性时域分析", font);
        ByteArrayOutputStream barBos = new ByteArrayOutputStream();
        if (reportData.getNnList() == null) reportData.setNnList(new LinkedList<>());
        DefaultCategoryDataset barDataset = getBarDataset(reportData.getNnList());
        int maxNN = reportData.getNnList().isEmpty() ? 0 : Collections.max(reportData.getNnList());
        if (maxNN % 10 != 0) maxNN = (maxNN / 10 + 1) * 10;
        int minNN = reportData.getNnList().isEmpty() ? 0 : Collections.min(reportData.getNnList());
        if (minNN % 10 != 0) minNN = (minNN / 10) * 10;

        BufferedImage bar = ChartUtil.NNChart("NN间期直方图", "NN间期(ms)", "NN间期数", barDataset, maxNN, minNN, (maxNN - minNN) / 5).createBufferedImage(1600, 500);
        ImageIO.write(bar, "png", barBos);
        Image barimage = new Image(ImageDataFactory.create(barBos.toByteArray()));
        // 调整图像在PDF中的大小以适应页面
        barimage.setAutoScale(true);
        LinkedList<String> data = new LinkedList<>();
        data.add(String.valueOf(reportData.getMeanNN()));
        data.add(String.valueOf(reportData.getSdnn()));
        data.add(String.valueOf(reportData.getRmssd()));
        data.add(String.valueOf(reportData.getNn20()));
        data.add(String.valueOf(reportData.getNn50()));
        data.add(String.valueOf(reportData.getPnn20()));
        data.add(String.valueOf(reportData.getPnn50()));

        getTable(table, data, font);
        // 使用Div容器来居中表格
        Div div = new Div();
        div.setHorizontalAlignment(HorizontalAlignment.CENTER); // 设置Div水平居中
        div.add(basicInfo(font, reportData)); // 将表格添加到Div中
        div.add(new Paragraph().setPadding(150));
        div.add(barimage);
        div.add(new Paragraph().setPadding(20));
        div.add(table);
        // 将Div添加到文档中
        doc.add(div);

        getLorenz(doc, font, 70, 670, 200, 200, "HRV散点图", reportData);
        getLorenz(doc, font, 340, 670, 200, 200, "Lorenz散点图", reportData);

    }

    private void getLorenz(Document doc, PdfFont font, float x, float y, float width, float height, String title, ReportData reportData) {
        // 创建 PdfCanvas 对象
        PdfCanvas pdfCanvas = new PdfCanvas(doc.getPdfDocument().getLastPage());
        // 设置绘图区域
        com.itextpdf.kernel.geom.Rectangle rectangle = new com.itextpdf.kernel.geom.Rectangle(x, y - height, width, height);
        // 设置画布边框
        pdfCanvas.setLineWidth(0.1f);
        pdfCanvas.setStrokeColor(ColorConstants.BLACK);
        pdfCanvas.setLineDash(0f);
        pdfCanvas.rectangle(rectangle);
        pdfCanvas.stroke();
        pdfCanvas.setLineWidth(0.2f);

        pdfCanvas.setFontAndSize(font, 8);
        pdfCanvas.setFillColor(ColorConstants.BLACK);
        float maxL = 0;
        //原点坐标 x,y-height  x+width,y
        pdfCanvas.setStrokeColor(ColorConstants.BLUE);
        pdfCanvas.setFillColor(ColorConstants.BLUE);
        float dx, dy;
        List<LinkedList<Float>> hrvScatterList = null;
        if (Objects.equals(title, "HRV散点图")) {
            hrvScatterList = reportData.getHrvScatterList();
        } else if (Objects.equals(title, "Lorenz散点图")) {
            hrvScatterList = reportData.getLorenzScatterList();
        }
        if (hrvScatterList != null) {
            for (LinkedList<Float> floats : hrvScatterList) {
                if (floats.get(0) >= floats.get(1)) maxL = maxL > floats.get(0) ? maxL : floats.get(0);
                else maxL = maxL > floats.get(0) ? maxL : floats.get(1);
            }
            for (LinkedList<Float> floats : hrvScatterList) {
                dx = x + floats.get(0) * (width / maxL);
                dy = y - height + floats.get(1) * (height / maxL);
                pdfCanvas.circle(dx, dy, 0.6).fill();
            }
        }
        pdfCanvas.stroke();
        pdfCanvas.setStrokeColor(ColorConstants.BLACK);
        pdfCanvas.setFillColor(ColorConstants.BLACK);
        //坐标轴标签
        float a, b, c;
        float maxIndex = ((int) (maxL / 100) + 1) * 100;
        float d = maxIndex / 4;
        a = maxIndex - d;
        b = maxIndex - 2 * d;
        c = maxIndex - 3 * d;
        pdfCanvas.beginText()
                .moveText(x + width / 2 - 20, y + 3)
                .showText(title)
                .endText();
        pdfCanvas.setFontAndSize(font, 4);
        pdfCanvas.beginText()
                .moveText(x + 2, y - 2)
                .showText(String.valueOf(maxIndex))
                .endText();
        pdfCanvas.beginText()
                .moveText(x + 2, y - 50 - 2)
                .showText(String.valueOf(a))
                .endText();
        pdfCanvas.beginText()
                .moveText(x + 2, y - 100 - 2)
                .showText(String.valueOf(b))
                .endText();
        pdfCanvas.beginText()
                .moveText(x + 2, y - 150 - 2)
                .showText(String.valueOf(c))
                .endText();
        pdfCanvas.beginText()
                .moveText(x + 46, y - height + 5)
                .showText(String.valueOf(c))
                .endText();
        pdfCanvas.beginText()
                .moveText(x + 96, y - height + 5)
                .showText(String.valueOf(a))
                .endText();
        pdfCanvas.beginText()
                .moveText(x + 146, y - height + 5)
                .showText(String.valueOf(b))
                .endText();
        pdfCanvas.moveTo(x + 50, y - height);
        pdfCanvas.lineTo(x + 50, y - height + 2);
        pdfCanvas.moveTo(x + 100, y - height);
        pdfCanvas.lineTo(x + 100, y - height + 2);
        pdfCanvas.moveTo(x + 150, y - height);
        pdfCanvas.lineTo(x + 150, y - height + 2);
        pdfCanvas.moveTo(x, y - height + 50);
        pdfCanvas.lineTo(x + 2, y - height + 50);
        pdfCanvas.moveTo(x, y - height + 100);
        pdfCanvas.lineTo(x + 2, y - height + 100);
        pdfCanvas.moveTo(x, y - height + 150);
        pdfCanvas.lineTo(x + 2, y - height + 150);
        pdfCanvas.stroke();
        pdfCanvas.setLineDash(2f, 1f);
        pdfCanvas.moveTo(x, y - 150);
        pdfCanvas.lineTo(x + 50, y - height);
        pdfCanvas.moveTo(x, y - 100);
        pdfCanvas.lineTo(x + 100, y - height);
        pdfCanvas.moveTo(x, y - 50);
        pdfCanvas.lineTo(x + 150, y - height);
        pdfCanvas.moveTo(x, y);
        pdfCanvas.lineTo(x + 200, y - height);
        pdfCanvas.moveTo(x, y - height);
        pdfCanvas.lineTo(x + width, y);
        pdfCanvas.stroke();

    }

    private void getTable(Table table, LinkedList<String> data, PdfFont font) {
        Cell cell1 = new Cell(1, 1).add(new Paragraph("MeanNN").setTextAlignment(TextAlignment.CENTER).setFont(font).setFontSize(10)).setBackgroundColor(new DeviceRgb(0, 238, 238));
        Cell cell2 = new Cell(1, 1).add(new Paragraph("SDNN").setTextAlignment(TextAlignment.CENTER).setFont(font).setFontSize(10)).setBackgroundColor(new DeviceRgb(0, 238, 238));
        Cell cell3 = new Cell(1, 1).add(new Paragraph("rMSSD").setTextAlignment(TextAlignment.CENTER).setFont(font).setFontSize(10)).setBackgroundColor(new DeviceRgb(0, 238, 238));
        Cell cell4 = new Cell(1, 1).add(new Paragraph("NN20").setTextAlignment(TextAlignment.CENTER).setFont(font).setFontSize(10)).setBackgroundColor(new DeviceRgb(0, 238, 238));
        Cell cell5 = new Cell(1, 1).add(new Paragraph("NN50").setTextAlignment(TextAlignment.CENTER).setFont(font).setFontSize(10)).setBackgroundColor(new DeviceRgb(0, 238, 238));
        Cell cell6 = new Cell(1, 1).add(new Paragraph("PNN20(%)").setTextAlignment(TextAlignment.CENTER).setFont(font).setFontSize(10)).setBackgroundColor(new DeviceRgb(0, 238, 238));
        Cell cell7 = new Cell(1, 1).add(new Paragraph("pNN50(%)").setTextAlignment(TextAlignment.CENTER).setFont(font).setFontSize(10)).setBackgroundColor(new DeviceRgb(0, 238, 238));
        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);
        table.addCell(cell7);
        Cell cell = null;
        for (String datum : data) {
            cell = new Cell(1, 1).add(new Paragraph(datum).setTextAlignment(TextAlignment.CENTER).setFont(font).setFontSize(10));
            table.addCell(cell);
        }
    }

    //心率变异性频域分析
    private void HRVFrequencyTable(Document doc, PdfFont font) {
        //设置标题
        titleSet_s(doc, "心率变异性频域分析", font);
        // 使用Div容器来居中表格
        Div div = new Div();
        div.setHorizontalAlignment(HorizontalAlignment.CENTER); // 设置Div水平居中
//        div.add(basicInfo(font)); // 将表格添加到Div中
        // 将Div添加到文档中
        doc.add(div);
    }

    //心电片段
    private void ECGTable(Document doc, PdfFont font, PdfDocument pdf, ReportData reportData) throws IOException {
        //设置标题
        titleSet_s(doc, "心电片段", font);
        //添加心电片段
        //float[] ecgData = {-0.094f, -0.065f, -0.041f, -0.061f, -0.052f, -0.03f, 0.0f, -0.026f, 0.004f, 0.015f, 0.087f, 0.068f, 0.05f, -0.006f, -0.008f, -0.032f, -0.03f, -0.046f, -0.046f, -0.035f, -0.017f, -0.039f, -0.021f, 0.037f, 0.14f, 0.32f, 0.709f, 0.129f, -0.727f, -0.217f, -0.032f, 0.004f, 0.039f, 0.054f, 0.054f, 0.098f, 0.116f, 0.114f, 0.145f, 0.131f, 0.134f, 0.186f, 0.232f, 0.285f, 0.342f, 0.419f, 0.522f, 0.617f, 0.674f, 0.718f, 0.67f, 0.549f, 0.34f, 0.103f, -0.096f, -0.241f, -0.329f, -0.375f, -0.355f, -0.371f, -0.36f, -0.327f, -0.325f, -0.287f, -0.276f, -0.263f, -0.21f, -0.215f, -0.206f, -0.186f, -0.184f, -0.191f, -0.145f, -0.147f, -0.112f, -0.118f, -0.127f, -0.127f, -0.136f, -0.14f, -0.127f, -0.153f, -0.118f, -0.127f, -0.107f, -0.112f, -0.116f, -0.12f, -0.114f, -0.109f, -0.103f, -0.107f, -0.085f, -0.098f, -0.081f, -0.079f, -0.074f, -0.079f, -0.068f, -0.05f, -0.052f, -0.048f, 0.008f, 0.017f, 0.061f, 0.039f, 0.054f, 0.035f, -0.008f, -0.061f, -0.03f, -0.041f, -0.024f, -0.032f, -0.026f, -0.024f, -0.026f, 0.072f, 0.09f, 0.463f, 0.626f, -0.054f, -0.494f, 0.0f, 0.048f, 0.039f, 0.052f, 0.068f, 0.065f, 0.054f, 0.063f, 0.09f, 0.134f, 0.142f, 0.169f, 0.191f, 0.199f, 0.259f, 0.318f, 0.404f, 0.494f, 0.617f, 0.703f, 0.672f, 0.643f, 0.492f, 0.274f, 0.03f, -0.166f, -0.29f, -0.34f, -0.358f, -0.362f, -0.364f, -0.347f, -0.349f, -0.305f, -0.303f, -0.281f, -0.265f, -0.235f, -0.21f, -0.197f, -0.217f, -0.173f, -0.182f, -0.158f, -0.156f, -0.16f, -0.166f, -0.131f, -0.149f, -0.156f, -0.147f, -0.145f, -0.129f, -0.136f, -0.149f, -0.136f, -0.145f, -0.12f, -0.09f, -0.094f, -0.105f, -0.085f, -0.116f, -0.09f, -0.101f, -0.087f, -0.094f, -0.048f, -0.068f, -0.074f, -0.05f, -0.035f, -0.019f, 0.006f, 0.054f, 0.061f, -0.008f, 0.021f, -0.008f, -0.052f, -0.072f, -0.05f, -0.074f, -0.065f, -0.068f, -0.05f, -0.068f, 0.057f, 0.054f, 0.446f, 0.61f, -0.039f, -0.577f, -0.017f, 0.002f, 0.019f, 0.032f, 0.07f, 0.046f, 0.085f, 0.07f, 0.098f, 0.123f, 0.127f, 0.12f, 0.188f, 0.219f, 0.261f, 0.32f, 0.406f, 0.507f, 0.593f, 0.659f, 0.7f, 0.639f, 0.465f, 0.237f, 0.021f, -0.158f, -0.274f, -0.342f, -0.349f, -0.373f, -0.36f, -0.362f, -0.307f, -0.312f, -0.327f, -0.283f, -0.281f, -0.268f, -0.226f, -0.215f, -0.204f, -0.18f, -0.191f, -0.171f, -0.16f, -0.142f, -0.153f, -0.164f, -0.162f, -0.147f, -0.153f, -0.182f, -0.151f, -0.164f, -0.129f, -0.171f, -0.153f, -0.12f, -0.114f, -0.103f, -0.103f, -0.105f, -0.079f, -0.09f, -0.063f, -0.092f, -0.087f, -0.037f, 0.006f, 0.013f, 0.07f, 0.004f, 0.019f, -0.03f, -0.046f, -0.063f, -0.037f, -0.054f, -0.052f, -0.057f, -0.021f, -0.01f, 0.074f, 0.118f, 0.509f, 0.533f, -0.208f, -0.505f, -0.074f, 0.026f, 0.05f, 0.028f, 0.048f, 0.043f, 0.074f, 0.068f, 0.101f, 0.107f, 0.145f, 0.162f, 0.195f, 0.23f, 0.279f, 0.336f, 0.461f, 0.553f, 0.643f, 0.705f, 0.718f, 0.635f, 0.45f, 0.206f, -0.017f, -0.16f, -0.276f, -0.329f, -0.355f, -0.342f, -0.351f, -0.349f, -0.342f, -0.322f, -0.296f, -0.29f, -0.252f, -0.241f, -0.226f, -0.226f, -0.206f, -0.18f, -0.173f, -0.184f, -0.158f, -0.153f, -0.158f, -0.131f, -0.149f, -0.166f, -0.125f, -0.123f, -0.118f, -0.123f, -0.107f, -0.094f, -0.076f, -0.118f, -0.105f, -0.114f, -0.101f, -0.083f, -0.065f, -0.092f, -0.048f, -0.074f, -0.039f, -0.052f, -0.015f, 0.019f, 0.039f, 0.05f, 0.048f, 0.028f, -0.013f, -0.043f, -0.041f, -0.057f, -0.048f, -0.065f, -0.037f, -0.052f, -0.03f, 0.07f, 0.076f, 0.494f, 0.45f, -0.375f, -0.362f, -0.03f, 0.026f, 0.028f, 0.061f, 0.063f, 0.094f, 0.085f, 0.083f, 0.105f, 0.125f, 0.136f, 0.175f, 0.18f, 0.239f, 0.307f, 0.366f, 0.459f, 0.553f, 0.635f, 0.698f, 0.672f, 0.621f, 0.435f, 0.202f, -0.026f, -0.16f, -0.272f, -0.305f, -0.344f, -0.338f, -0.336f, -0.318f, -0.336f, -0.274f, -0.272f, -0.272f, -0.276f, -0.241f, -0.215f, -0.21f, -0.204f, -0.169f, -0.175f, -0.145f, -0.191f, -0.129f, -0.164f, -0.195f, -0.213f, -0.158f, -0.16f, -0.191f, -0.162f, -0.147f, -0.224f, -0.006f, -0.114f, 0.046f, 0.0f, 0.039f, 0.043f, -0.054f, -0.032f, -0.072f, -0.017f, -0.083f, -0.03f, -0.008f, -0.039f, 0.037f, -0.07f, 0.107f, 0.063f, 0.024f, 0.037f, -0.041f, -0.068f, -0.063f, -0.026f, -0.048f, -0.037f, -0.063f, -0.083f, 0.123f, 0.074f, 0.395f, 0.47f, -0.226f, -0.476f, 0.0f, 0.048f, 0.063f, 0.035f, 0.085f, 0.107f, 0.123f, 0.123f, 0.14f, 0.116f, 0.14f, 0.173f, 0.184f, 0.204f, 0.283f, 0.369f, 0.432f, 0.516f, 0.61f, 0.667f, 0.674f, 0.624f, 0.435f, 0.202f, -0.032f, -0.204f, -0.279f, -0.333f, -0.38f, -0.404f, -0.404f, -0.38f, -0.369f, -0.369f, -0.371f, -0.318f, -0.281f, -0.285f, -0.279f, -0.252f, -0.204f, -0.237f, -0.21f, -0.184f, -0.177f, -0.177f, -0.18f, -0.149f, -0.142f, -0.166f, -0.147f, -0.158f, -0.156f, -0.147f, -0.131f, -0.12f, -0.118f, -0.12f, -0.14f, -0.134f, -0.116f, -0.081f, -0.09f, -0.085f, -0.087f, -0.074f, -0.037f, -0.021f, 0.017f, -0.004f, -0.01f, -0.03f, -0.026f, -0.068f, -0.057f, -0.057f, -0.072f, -0.09f, -0.076f, -0.07f, -0.057f, 0.085f, 0.153f, 0.586f, 0.369f, -0.58f, -0.432f, -0.032f, -0.015f, -0.015f, 0.008f, -0.008f, 0.068f, 0.072f, 0.09f, 0.085f, 0.101f, 0.134f, 0.164f, 0.193f, 0.246f, 0.318f, 0.377f, 0.459f, 0.573f, 0.635f, 0.685f, 0.709f, 0.584f, 0.382f, 0.171f, -0.024f, -0.147f, -0.25f, -0.301f, -0.305f, -0.279f, -0.301f, -0.272f, -0.261f, -0.257f, -0.246f, -0.241f, -0.237f, -0.206f, -0.184f, -0.173f, -0.171f, -0.142f, -0.151f, -0.142f, -0.145f, -0.123f, -0.12f, -0.171f, -0.173f, -0.118f, -0.12f, -0.138f, -0.138f, -0.136f, -0.138f, -0.098f, -0.112f, -0.101f, -0.14f, -0.09f, -0.046f, 0.032f, 0.035f, 0.043f, 0.008f, 0.026f, -0.024f, -0.063f, -0.094f, -0.096f, -0.107f, -0.087f, -0.037f, -0.041f, -0.074f, 0.024f, -0.019f, 0.191f, 0.678f, 0.05f, -0.628f, -0.092f, 0.01f, 0.032f, 0.05f, 0.059f, 0.068f, 0.063f, 0.074f, 0.114f, 0.136f, 0.149f, 0.16f, 0.199f, 0.217f, 0.248f, 0.349f, 0.424f, 0.511f, 0.599f, 0.678f, 0.689f, 0.628f, 0.457f, 0.239f, -0.013f, -0.14f, -0.263f, -0.333f, -0.353f, -0.366f, -0.347f, -0.347f, -0.316f, -0.309f, -0.29f, -0.23f, -0.241f, -0.219f, -0.197f, -0.197f, -0.173f, -0.175f, -0.166f, -0.14f, -0.142f, -0.136f, -0.123f, -0.134f, -0.114f, -0.131f, -0.123f, -0.134f, -0.092f, -0.101f, -0.112f, -0.109f, -0.101f, -0.109f, -0.109f, -0.085f, -0.081f, -0.087f, -0.085f, -0.098f, -0.043f, 0.002f, 0.037f, 0.037f, -0.006f, -0.002f, -0.03f, -0.035f, -0.061f, -0.081f, -0.076f, -0.081f, -0.074f, -0.081f, -0.041f, 0.07f, -0.002f, 0.454f, 0.441f, -0.316f, -0.485f, -0.059f, 0.002f, -0.004f, 0.006f, 0.052f, 0.087f, 0.085f, 0.094f, 0.105f, 0.116f, 0.151f, 0.162f, 0.202f, 0.228f, 0.27f, 0.358f, 0.443f, 0.555f, 0.652f, 0.683f, 0.696f, 0.571f, 0.406f, 0.175f, -0.041f, -0.215f, -0.316f, -0.34f, -0.351f, -0.364f, -0.353f, -0.349f, -0.325f, -0.274f, -0.27f, -0.276f, -0.265f, -0.248f, -0.243f, -0.202f, -0.162f, -0.195f, -0.175f, -0.177f, -0.134f, -0.142f, -0.166f, -0.182f, -0.188f, -0.136f, -0.059f, -0.054f, -0.072f, -0.09f, -0.065f, -0.068f, -0.101f, -0.041f, -0.09f, -0.008f, -0.054f, -0.063f, -0.074f, -0.046f, -0.019f, -0.013f, -0.048f, -0.019f, 0.057f, 0.098f, 0.054f, 0.026f, 0.015f, -0.041f, -0.043f, -0.07f, -0.07f, -0.081f, -0.07f, -0.054f, -0.068f, -0.052f, 0.039f, 0.004f, 0.404f, 0.522f, -0.129f, -0.542f, -0.035f, 0.013f, 0.024f, 0.013f, 0.061f, 0.063f, 0.068f, 0.074f, 0.087f, 0.098f, 0.12f, 0.145f, 0.184f, 0.206f, 0.268f, 0.336f, 0.406f, 0.505f, 0.604f, 0.637f, 0.659f, 0.602f, 0.417f, 0.193f, -0.041f, -0.197f, -0.303f, -0.382f, -0.373f, -0.393f, -0.371f, -0.369f, -0.366f, -0.353f, -0.316f, -0.309f, -0.298f, -0.294f, -0.263f, -0.25f, -0.204f, -0.202f, -0.182f, -0.197f, -0.184f, -0.156f, -0.175f, -0.171f, -0.171f, -0.171f, -0.175f, -0.173f, -0.156f, -0.171f, -0.153f, -0.16f, -0.149f, -0.123f, -0.109f, -0.101f, -0.112f, -0.105f, -0.079f, -0.105f, -0.098f, -0.07f, -0.083f, -0.061f, -0.039f, 0.017f, 0.035f, 0.035f, -0.01f, -0.01f, -0.024f, -0.037f, -0.074f, -0.035f, -0.061f, -0.061f, -0.046f, -0.032f, -0.039f, 0.021f, 0.101f, 0.318f, 0.76f, 0.206f, -0.736f, -0.265f, -0.046f, 0.008f, 0.006f, 0.026f, 0.05f, 0.037f, 0.063f, 0.079f, 0.094f, 0.12f, 0.149f, 0.173f, 0.217f, 0.252f, 0.312f, 0.397f, 0.5f, 0.595f, 0.665f, 0.683f, 0.67f, 0.544f, 0.307f, 0.098f, -0.105f, -0.219f, -0.301f, -0.331f, -0.369f, -0.349f, -0.36f, -0.329f, -0.294f, -0.285f, -0.287f, -0.252f, -0.257f, -0.217f, -0.219f, -0.195f, -0.18f, -0.166f, -0.151f, -0.14f, -0.138f, -0.136f, -0.136f, -0.134f, -0.142f, -0.142f, -0.134f, -0.105f, -0.094f, -0.096f, -0.09f, -0.087f, -0.083f, -0.083f, -0.065f, -0.063f, -0.061f, -0.081f, -0.05f, -0.013f, -0.01f, 0.026f, 0.0f, -0.008f, 0.002f, -0.039f, -0.052f, -0.07f, -0.057f, -0.054f, -0.046f, -0.037f, -0.035f, 0.006f, 0.116f, 0.114f, 0.606f, 0.465f, -0.461f, -0.408f, -0.03f, 0.024f, 0.046f};

        // 使用Div容器来居中表格
        Div div = new Div();
        div.setHorizontalAlignment(HorizontalAlignment.CENTER); // 设置Div水平居中
        div.add(basicInfo(font, reportData)); // 将表格添加到Div中
        // 将Div添加到文档中
        doc.add(div);
        // 绘制心电图
        if (reportData.getEcgFrag() != null)
            drawECG(doc, reportData, 35, 700, 525, 120, font);
    }

    private void drawECG(Document document, ReportData ecgData, float x, float y, float width, float height, PdfFont font) throws IOException {
        // 创建 PdfCanvas 对象
        PdfCanvas pdfCanvas = new PdfCanvas(document.getPdfDocument().getLastPage());
        List<ECGFragment> ecgFrag = ecgData.getEcgFrag();
        int index = 0;
        for (ECGFragment ecgFragment : ecgFrag) {
            drawE(pdfCanvas, x, y - index * (height + 40), width, height, font, ecgFragment.getEvent(), ecgFragment.getOcTime(), String.valueOf(ecgFragment.getMeanHr()), ecgFragment.getEcgData());
            index++;
            if (index % 4 == 0 && ecgFrag.size() > 4) {
                index = 0;
                document.add(new AreaBreak());
                titleSet_s(document, "心电片段", font);
                // 使用Div容器来居中表格
                Div div = new Div();
                div.setHorizontalAlignment(HorizontalAlignment.CENTER); // 设置Div水平居中
                div.add(basicInfo(font, ecgData)); // 将表格添加到Div中
                // 将Div添加到文档中
                document.add(div);
                pdfCanvas = new PdfCanvas(document.getPdfDocument().getLastPage());
//                drawE(pdfCanvas, x, y - index * (height + 40), width, height, font, ecgFragment.getEvent(), ecgFragment.getTime(), String.valueOf(ecgFragment.getMeanHr()), ecgFragment.getECGData());
//                index++;
            }
        }

//        drawE(pdfCanvas, x, y, width, height, font, "最快心率", "2024-07-04 15:59:34", "120", ecgData);
//        drawE(pdfCanvas, x, y - (height + 40), width, height, font, "最慢心率", "2024-07-04 15:49:34", "60", ecgData);
//        drawE(pdfCanvas, x, y - 2 * (height + 40), width, height, font, "房性早搏", "2024-07-04 15:59:34", "78", ecgData);
//        drawE(pdfCanvas, x, y - 3 * (height + 40), width, height, font, "心房颤动", "2024-07-04 15:59:34", "89", ecgData);
    }

    private void drawE(PdfCanvas pdfCanvas, float x, float y, float width, float height, PdfFont font, String title, String time, String hr, List<Float> ecgData) {
        // 设置绘图区域
        com.itextpdf.kernel.geom.Rectangle rectangle = new com.itextpdf.kernel.geom.Rectangle(x, y - height, width, height);
        // 设置画布边框
        pdfCanvas.setLineWidth(0.1f);
        pdfCanvas.setStrokeColor(ColorConstants.BLACK);
        pdfCanvas.rectangle(rectangle);
        pdfCanvas.stroke();
        pdfCanvas.setLineWidth(0.2f);

        for (double i = y - height; i < y; i += 15) {
            pdfCanvas.moveTo(x, i);
            pdfCanvas.lineTo(x + width, i);
            pdfCanvas.stroke();
        }
        for (double i = x; i < x + width; i += 15) {
            pdfCanvas.moveTo(i, y - height);
            pdfCanvas.lineTo(i, y);
            pdfCanvas.stroke();
        }
        int index = 0;
        int indey = 0;
        for (double i = y - height + 3; i < y; i += 3) {
            index++;
            if (index % 5 == 0) continue;
            indey = 0;
            for (double j = x + 3; j < x + width; j += 3) {
                indey++;
                if (indey % 5 == 0) continue;
                pdfCanvas.circle(j, i, 0.2);
                pdfCanvas.fill();
            }
        }
        pdfCanvas.setFontAndSize(font, 6);
        pdfCanvas.setFillColor(ColorConstants.BLACK);
        pdfCanvas.beginText()
                .moveText(x, y + 2)
                .showText(time)
                .endText();
        pdfCanvas.beginText()
                .moveText(x + width - 60, y + 2)
                .showText("25mm/s 10mm/mV")
                .endText();
        pdfCanvas.setFontAndSize(font, 8);
        pdfCanvas.setFillColor(ColorConstants.BLACK);
        pdfCanvas.beginText()
                .moveText(x + width / 2 - 15, y + 4)
                .showText(title)
                .endText();
        pdfCanvas.beginText()
                .moveText(x + 30, y - height + 5)
                .showText("平均心率: " + hr + " bpm")
                .endText();
        //画心电图
        pdfCanvas.setFontAndSize(font, 6);
        pdfCanvas.setFillColor(ColorConstants.BLACK);
        pdfCanvas.moveTo(x, y - height / 2);
        int flag = 0;
        for (double i = x; i < x + width; i += 0.75) {
            pdfCanvas.lineTo(i, y - height / 2 + ecgData.get(flag) * 30);
            flag++;
        }
        pdfCanvas.stroke();
    }

    //设置标题
    public void titleSet(Document doc, String data, PdfFont font) {
        Paragraph titleText = new Paragraph(data).setTextAlignment(TextAlignment.CENTER).setFont(font).setFontSize(20);
        doc.add(titleText);
    }

    public void titleSet_s(Document doc, String data, PdfFont font) {
        Paragraph titleText = new Paragraph(data).setTextAlignment(TextAlignment.CENTER).setFont(font).setFontSize(15);
        titleText.setPaddingBottom(10);
        doc.add(titleText);
    }

    //设置内容
    public void contextCell(Table table, String data, PdfFont font, int top, int bottom, int left, int right) {
        Cell cell = new Cell(1, 2).add(new Paragraph(data).setTextAlignment(TextAlignment.LEFT).setFont(font).setFontSize(10));
        vanishLine(cell, top, bottom, left, right);
        table.addCell(cell);
    }

    private void vanishLine(Cell cell, int top, int bottom, int left, int right) {
        if (top == 0) cell.setBorderTop(Border.NO_BORDER);
        if (bottom == 0) cell.setBorderBottom(Border.NO_BORDER);
        if (left == 0) cell.setBorderLeft(Border.NO_BORDER);
        if (right == 0) cell.setBorderRight(Border.NO_BORDER);
    }

}