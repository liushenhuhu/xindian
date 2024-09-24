package com.ruoyi.xindian.pdf.utils;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class JecgPdfCreateUtil {



    public void createPdf(Map<String,Object> map,Map<String,Double[]> ecgDataMap,String filePath) throws IOException {
        //字体
//        PdfFont font = PdfFontFactory.createFont("./ruoyi-xindian/src/main/java/com/ruoyi/xindian/pdf/utils/STXIHEI.TTF", PdfEncodings.IDENTITY_H, true);
        PdfFont font = PdfFontFactory.createFont("/home/chenpeng/workspace/system/xindian/ttf/STXIHEI.TTF", PdfEncodings.IDENTITY_H, true);

        try {
            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdf = new PdfDocument(writer);
            PageSize rotate = PageSize.A4.rotate();
            Document document = new Document(pdf, rotate);


            //设置标题
            Paragraph title = new Paragraph("静态心电图报告").setFont(font).setBold().setTextAlignment(TextAlignment.CENTER).setMarginTop(-25).setFontSize(20);
            document.add(title);

            // 获取当前页面
            com.itextpdf.kernel.pdf.PdfPage page = pdf.getFirstPage();

            // 创建一个 PdfCanvas 对象来绘制线条
            PdfCanvas canvas = new PdfCanvas(page);

            // 设置线条的颜色为黑色
            Color redColor = new DeviceRgb(0, 0, 0);

            // 绘制两条紧密相邻的线条
            float lineWidth = 1f; // 线条宽度
            canvas
                    .newPath()
                    .setLineWidth(lineWidth)
                    .setColor(redColor,false)
                    .moveTo(0, 555)  // 起点坐标 (x1, y1)
                    .lineTo(rotate.getWidth(), 555) // 终点坐标 (x2, y2)
                    .stroke();


            //信息
            Table table_info = new Table(3);
            table_info.setWidth(UnitValue.createPercentValue(100));

            contextCell(table_info,"姓名:"+map.get("patientName"),font,false);
            contextCell(table_info,"住院号:",font,false);
            contextCell(table_info,"报告日期:"+map.get("reportTime"),font,false);


            contextCell(table_info,"性别:"+map.get("sex"),font,false);
            contextCell(table_info,"病床:",font,false);
            contextCell(table_info,"设备编号:",font,false);


            contextCell(table_info,"年龄:"+map.get("age"),font,false);
            contextCell(table_info,"床号:",font,false);
            contextCell(table_info,"数据来源:"+map.get("source"),font,false);

            contextCellPId(table_info,"编号:"+map.get("pId"),font,true);



            //指标
            Table table_info1 = new Table(2);
            table_info1.setWidth(UnitValue.createPercentValue(100));

            contextCell(table_info1,"心率(bpm):",font,false);
            contextCell(table_info1,map.get("heart")!=null?map.get("heart").toString():"75",font,true);


            contextCell(table_info1,"PR间期(ms):",font,false);
            contextCell(table_info1,map.get("pr")!=null?map.get("pr").toString():"0",font,false);

            contextCell(table_info1,"QT/QTc(ms):",font,false);
            contextCell(table_info1,map.get("qtqtc")!=null?map.get("qtqtc").toString():"0",font,false);


            contextCell(table_info1,"QRS时限(ms):",font,false);
            contextCell(table_info1,map.get("qrs")!=null?map.get("qrs").toString():"0",font,false);


            //结论
            Table table_info2 = new Table(new float[]{30,70});
            table_info2.setWidth(UnitValue.createPercentValue(100));
            Cell table_info2_cell = new Cell(1, 1).add(new Paragraph("心电图结论:").setBold().setTextAlignment(TextAlignment.LEFT).setFont(font).setFontSize(10));
            table_info2_cell.setBorder(Border.NO_BORDER);
            Cell table_info2_cell1 = new Cell(1, 1).add(new Paragraph(map.get("conclusion")!=null?map.get("conclusion").toString():"信号干扰较大, 建议重新采集").setTextAlignment(TextAlignment.LEFT).setMultipliedLeading(1.5f).setFont(font).setFontSize(9));
            table_info2_cell1.setBorder(Border.NO_BORDER);
            table_info2.addCell(table_info2_cell);
            table_info2.addCell(table_info2_cell1);

            // 创建一个横向排列的表格组合
            Table combinedTable = new Table(3);
            combinedTable.setWidth(UnitValue.createPercentValue(95));

            // 将两个表格放入一个单元格中
            Cell cell1 = new Cell().add(table_info).setBorder(Border.NO_BORDER);
            Cell cell2 = new Cell().add(table_info1).setBorder(Border.NO_BORDER);
            Cell cell3 = new Cell().add(table_info2).setBorder(Border.NO_BORDER);

            // 设置单元格的宽度比例
            cell1.setWidth(UnitValue.createPercentValue(45));
            cell2.setWidth(UnitValue.createPercentValue(20));
            cell3.setWidth(UnitValue.createPercentValue(30));

            // 将单元格添加到组合表格中
            combinedTable.addCell(cell1);
            combinedTable.addCell(cell2);
            combinedTable.addCell(cell3);

            // 创建一个 Div 元素，并将其居中
            Div div1 = new Div();
            div1.setHorizontalAlignment(HorizontalAlignment.CENTER); // 设置Div水平居中
            div1.add(combinedTable);
            div1.setMarginTop(2);
            // 将Div添加到文档中
            document.add(div1);



            //审核时间 + 审核医生
            Paragraph paragraph = new Paragraph("审核时间    "+map.get("getDataTime")+"           审核医生").setFont(font).setFontSize(9).setMarginLeft(500);

            document.add(paragraph);










            String ecgType = map.get("ecgType").toString();




            if (ecgType.contains("JECGsingle")){
                //单导
                drawWeekEcg30(document, font , 35,400, rotate.getWidth()-65, 18,ecgDataMap);
            }else if (ecgType.contains("JECG12")){
                //12导
                drawWeekEcg12(document, font , 35, 420, rotate.getWidth()-65, 24,ecgDataMap);
            }else if (ecgType.contains("JECG4")){
                //4导

                Double[] iis = ecgDataMap.get("II");
                if (iis.length>700){
                    //四行
                    drawWeekEcg44(document, font , 35, 420, rotate.getWidth()-65, 24,ecgDataMap);
                }else {
                    //两行
                    drawWeekEcg42(document, font , 35, 420, rotate.getWidth()-65, 12,ecgDataMap);
                }

            }


            Object o = map.get("status");
            if (o==null||!o.toString().equals("1")){
                String text1 = "(本报告由互联网医疗与健康服务河南省协同创新中心人工智能平台自动生成, 未经临床验证, 仅供参考, 请根据医生诊断进一步确认.)";
                //审核时间 + 审核医生
                Paragraph paragraph1 = new Paragraph(text1).setFont(font).setFontSize(9).setFixedPosition(35,45,550);
                document.add(paragraph1);
            }

            if (o!=null&&o.toString().equals("1")){
                if (map.get("doctorPhoto")!=null){
                    String wdir = map.get("doctorPhoto").toString();
                    ImageData imageData = ImageDataFactory.create(wdir);

                    Image image = new Image(imageData)
                            .setFixedPosition(725, 430)
                            .scaleToFit(100, 100)
                            .setHorizontalAlignment(HorizontalAlignment.CENTER)
                            .setRotationAngle(Math.toRadians(90));
                    document.add(image);
                }



            }



            String text2 = map.get("reportType")!=null?map.get("reportType").toString():"静态II导联+解析结果";

            Paragraph paragraph2 = new Paragraph("报告格式："+text2).setFont(font).setFontSize(9).setFixedPosition(35,25,200);
            document.add(paragraph2);

            Paragraph paragraph3= new Paragraph("仅对此报告内容负责").setFont(font).setFontSize(9).setFixedPosition(730,25,200);
            document.add(paragraph3);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void contextCell(Table table, String data, PdfFont font,Boolean type) {
        Cell cell;
        if (type){
            cell  = new Cell(1, 2).add(new Paragraph(data).setTextAlignment(TextAlignment.LEFT).setFont(font).setFontSize(10));
        }else {
            cell  = new Cell(1, 1).add(new Paragraph(data).setTextAlignment(TextAlignment.LEFT).setFont(font).setFontSize(10));
        }
        cell.setBorder(Border.NO_BORDER);
        table.addCell(cell);
    }
    public static void contextCellPId(Table table, String data, PdfFont font,Boolean type) {
        Cell cell;
        if (type){
            cell  = new Cell(1, 3).add(new Paragraph(data).setTextAlignment(TextAlignment.LEFT).setFont(font).setFontSize(10));
        }else {
            cell  = new Cell(1, 1).add(new Paragraph(data).setTextAlignment(TextAlignment.LEFT).setFont(font).setFontSize(10));
        }
        cell.setBorder(Border.NO_BORDER);
        table.addCell(cell);
    }

    private static void drawWeekEcg30(Document doc, PdfFont font,float x, float y, float width,int type,Map<String,Double[]> ecgDataMap) {
        PdfCanvas pdfCanvas = new PdfCanvas(doc.getPdfDocument().getLastPage());

        Double[] ecgData = ecgDataMap.get("II");

        //一大格
        double pigK = (width/52);
        float height = (float) (pigK * type)+1f;
        //一小格
        double smallK = pigK / 5;
        pdfCanvas.setLineDash(0);
        for (int i = 0; i < (height / pigK); i++) {
            if (i == 0 || i == (height / pigK) - 1) {
                pdfCanvas.setLineDash(0); // 实线
            } else {
                pdfCanvas.setLineDash(1, 1); // 虚线
            }
            pdfCanvas.moveTo(x, y - height + i * pigK);
            pdfCanvas.lineTo(x + width, y - height + i * pigK);
        }

        // 绘制竖线
        for (int i = 0; i < (width / pigK); i++) {
            if (i == 0 || i == (width / pigK) - 1) {
                pdfCanvas.setLineDash(0); // 实线
            } else {
                pdfCanvas.setLineDash(1, 1); // 虚线
            }
            pdfCanvas.moveTo(x + i * pigK, y - height);
            pdfCanvas.lineTo(x + i * pigK, y);
        }
        pdfCanvas.stroke();

        pdfCanvas.setLineWidth(0.1f);
        int index = 0;
        int indey = 0;
        for (double i = y - height + smallK; i < y; i += smallK) {
            index++;
            if (index % 5 == 0) continue;
            indey = 0;
            for (double j = x + smallK; j < x + width; j += smallK) {
                indey++;
                if (indey % 5 == 0) continue;
                pdfCanvas.rectangle(j - 0.25, i - 0.25, 0.5, 0.5);
                pdfCanvas.fill();
            }

        }        //画线


        pdfCanvas.stroke();
        pdfCanvas.setLineDash(0);
        //画心电图
        pdfCanvas.setStrokeColor(ColorConstants.BLACK);
        pdfCanvas.setFontAndSize(font, 6);
        pdfCanvas.setLineWidth(1f);
        pdfCanvas.setFillColor(ColorConstants.BLACK);
        double dx;
        dx = x;

        int sampleRate =100;
        int flag = 0;
        for (int j = 0; j < 3; j++) {
            double dy = y - (3 + 6 * j) * pigK;


            int a = 0;
            for (int i = 0; i < voltageData1.length; i++) {
                if ( voltageData1[i][0]!=null){
                    double vx =   x;
                    vx+=5 * pigK / sampleRate  *(voltageData1[i][1]);
                    a++;
                    if (a==1){
                        pdfCanvas.moveTo(vx, dy);
                    }


                    pdfCanvas.lineTo(vx, dy + voltageData1[i][0] * 2 * pigK);
                }
            }
        }

        pdfCanvas.stroke();

        for (int j = 0; j < 3; j++) {
            double dy = y - (3 + 6 * j) * pigK;
            pdfCanvas.moveTo(x+2*pigK, dy);


            for (double i = x+2*pigK; i < x + width; i += 5 * pigK / sampleRate) {

                if (flag >= ecgData.length) break;
                pdfCanvas.lineTo(i, dy + ecgData[flag] * 2 * pigK);
                flag++;
            }

        }

        pdfCanvas.stroke();

        Paragraph paragraph2= new Paragraph("10mm/mV    25mm/s").setFont(font).setBold().setFontSize(10).setFixedPosition(680,385,100);
        doc.add(paragraph2);

        Paragraph paragraph3= new Paragraph("II").setFont(font).setBold().setFontSize(10).setFixedPosition(61,290,30);
        doc.add(paragraph3);

        Paragraph paragraph4= new Paragraph("II").setFont(font).setBold().setFontSize(10).setFixedPosition(61,385,30);
        doc.add(paragraph4);

        Paragraph paragraph5= new Paragraph("II").setFont(font).setBold().setFontSize(10).setFixedPosition(61,200,30);
        doc.add(paragraph5);



    }





    private static void drawWeekEcg12(Document doc, PdfFont font,float x, float y, float width,int type,Map<String,Double[]> ecgDataMap) {
        PdfCanvas pdfCanvas = new PdfCanvas(doc.getPdfDocument().getLastPage());

        Double[] ecgData = ecgDataMap.get("II");

        //一大格
        double pigK = (width/52);
        float height = (float) (pigK * type)+1f;
        //一小格
        double smallK = pigK / 5;
        pdfCanvas.setLineDash(0);
        for (int i = 0; i < (height / pigK); i++) {
            if (i == 0 || i == (height / pigK) - 1) {
                pdfCanvas.setLineDash(0); // 实线
            } else {
                pdfCanvas.setLineDash(1, 1); // 虚线
            }
            pdfCanvas.moveTo(x, y - height + i * pigK);
            pdfCanvas.lineTo(x + width, y - height + i * pigK);
        }

        // 绘制竖线
        for (int i = 0; i < (width / pigK); i++) {
            if (i == 0 || i == (width / pigK) - 1) {
                pdfCanvas.setLineDash(0); // 实线
            } else {
                pdfCanvas.setLineDash(1, 1); // 虚线
            }
            pdfCanvas.moveTo(x + i * pigK, y - height);
            pdfCanvas.lineTo(x + i * pigK, y);
        }
        pdfCanvas.stroke();

        pdfCanvas.setLineWidth(0.1f);
        int index = 0;
        int indey = 0;
        for (double i = y - height + smallK; i < y; i += smallK) {
            index++;
            if (index % 5 == 0) continue;
            indey = 0;
            for (double j = x + smallK; j < x + width; j += smallK) {
                indey++;
                if (indey % 5 == 0) continue;
                pdfCanvas.rectangle(j - 0.25, i - 0.25, 0.5, 0.5);
                pdfCanvas.fill();
            }

        }        //画线


        pdfCanvas.stroke();
        pdfCanvas.setLineDash(0);
        //画心电图
        pdfCanvas.setStrokeColor(ColorConstants.BLACK);
        pdfCanvas.setFontAndSize(font, 6);
        pdfCanvas.setLineWidth(1f);
        pdfCanvas.setFillColor(ColorConstants.BLACK);
        double dx;
        dx = x;

        int sampleRate =100;

        for (int j = 0; j < 4; j++) {
            double dy = y - (3 + 6 * j) * pigK;

            int a = 0;

            Integer[][] data = voltageData12;
            if (j==3){
                data = voltageData1;
            }
            for (int i = 0; i < data.length; i++) {
                if ( data[i][0]!=null){
                    double vx =   x;
                    vx+=5 * pigK / sampleRate  *(data[i][1]);
                    a++;
                    if (a==1){
                        pdfCanvas.moveTo(vx, dy);
                    }
                    pdfCanvas.lineTo(vx, dy + data[i][0] * 2 * pigK);
                }
            }
        }

        pdfCanvas.stroke();



        Double px =(5 * pigK / sampleRate) *250 ;

        int flag = 0;
        //第一行  I  aVR  V1  V4
        double dy = y - (3) * pigK;
        pdfCanvas.moveTo(x+2*pigK, dy);
        for (double i = x+2*pigK; i < x + width; i += 5 * pigK / sampleRate) {
            if (flag >= ecgDataMap.get("I").length) break;
            pdfCanvas.lineTo(i, dy + ecgDataMap.get("I")[flag] * 2 * pigK);
            flag++;
        }

        flag = 0;
        pdfCanvas.moveTo(x+2*pigK+px, dy);
        for (double i = x+2*pigK+px; i < x + width; i += 5 * pigK / sampleRate) {
            if (flag >= ecgDataMap.get("aVR").length) break;
            pdfCanvas.lineTo(i, dy + ecgDataMap.get("aVR")[flag] * 2 * pigK);
            flag++;
        }

        flag = 0;
        pdfCanvas.moveTo(x+2*pigK+px*2, dy);
        for (double i = x+2*pigK+px*2; i < x + width; i += 5 * pigK / sampleRate) {
            if (flag >= ecgDataMap.get("V1").length) break;
            pdfCanvas.lineTo(i, dy + ecgDataMap.get("V1")[flag] * 2 * pigK);
            flag++;
        }

        flag = 0;
        pdfCanvas.moveTo(x+2*pigK+px*3, dy);
        for (double i = x+2*pigK+px*3; i < x + width; i += 5 * pigK / sampleRate) {
            if (flag >= ecgDataMap.get("V4").length) break;
            pdfCanvas.lineTo(i, dy + ecgDataMap.get("V4")[flag] * 2 * pigK);
            flag++;
        }



        Double[] doubles = Arrays.copyOfRange(ecgDataMap.get("II"), 0, 250);
        flag = 0;
        //第一行  I  aVR  V1  V4
        dy = y - (3+6) * pigK;
        pdfCanvas.moveTo(x+2*pigK, dy);
        for (double i = x+2*pigK; i < x + width; i += 5 * pigK / sampleRate) {
            if (flag >= doubles.length) break;
            pdfCanvas.lineTo(i, dy + doubles[flag] * 2 * pigK);
            flag++;
        }

        flag = 0;
        pdfCanvas.moveTo(x+2*pigK+px, dy);
        for (double i = x+2*pigK+px; i < x + width; i += 5 * pigK / sampleRate) {
            if (flag >= ecgDataMap.get("aVL").length) break;
            pdfCanvas.lineTo(i, dy + ecgDataMap.get("aVL")[flag] * 2 * pigK);
            flag++;
        }

        flag = 0;
        pdfCanvas.moveTo(x+2*pigK+px*2, dy);
        for (double i = x+2*pigK+px*2; i < x + width; i += 5 * pigK / sampleRate) {
            if (flag >= ecgDataMap.get("V2").length) break;
            pdfCanvas.lineTo(i, dy + ecgDataMap.get("V2")[flag] * 2 * pigK);
            flag++;
        }

        flag = 0;
        pdfCanvas.moveTo(x+2*pigK+px*3, dy);
        for (double i = x+2*pigK+px*3; i < x + width; i += 5 * pigK / sampleRate) {
            if (flag >= ecgDataMap.get("V5").length) break;
            pdfCanvas.lineTo(i, dy + ecgDataMap.get("V5")[flag] * 2 * pigK);
            flag++;
        }




        flag = 0;
        //第一行  I  aVR  V1  V4
        dy = y - (3+6+6) * pigK;
        pdfCanvas.moveTo(x+2*pigK, dy);
        for (double i = x+2*pigK; i < x + width; i += 5 * pigK / sampleRate) {
            if (flag >= ecgDataMap.get("III").length) break;
            pdfCanvas.lineTo(i, dy + ecgDataMap.get("III")[flag] * 2 * pigK);
            flag++;
        }

        flag = 0;
        pdfCanvas.moveTo(x+2*pigK+px, dy);
        for (double i = x+2*pigK+px; i < x + width; i += 5 * pigK / sampleRate) {
            if (flag >= ecgDataMap.get("aVF").length) break;
            pdfCanvas.lineTo(i, dy + ecgDataMap.get("aVF")[flag] * 2 * pigK);
            flag++;
        }

        flag = 0;
        pdfCanvas.moveTo(x+2*pigK+px*2, dy);
        for (double i = x+2*pigK+px*2; i < x + width; i += 5 * pigK / sampleRate) {
            if (flag >= ecgDataMap.get("V3").length) break;
            pdfCanvas.lineTo(i, dy + ecgDataMap.get("V3")[flag] * 2 * pigK);
            flag++;
        }

        flag = 0;
        pdfCanvas.moveTo(x+2*pigK+px*3, dy);
        for (double i = x+2*pigK+px*3; i < x + width; i += 5 * pigK / sampleRate) {
            if (flag >= ecgDataMap.get("V6").length) break;
            pdfCanvas.lineTo(i, dy + ecgDataMap.get("V6")[flag] * 2 * pigK);
            flag++;
        }



        flag = 0;
        //第一行  I  aVR  V1  V4
        dy = y - (3+6+6+6) * pigK;
        Double[] doubles1 = Arrays.copyOfRange(ecgDataMap.get("All_II"), 0, 1000);
        pdfCanvas.moveTo(x+2*pigK, dy);
        for (double i = x+2*pigK; i < x + width; i += 5 * pigK / sampleRate) {
            if (flag >= doubles1.length) break;
            pdfCanvas.lineTo(i, dy + doubles1[flag] * 2 * pigK);
            flag++;
        }

        pdfCanvas.stroke();




        Paragraph paragraph2= new Paragraph("10mm/mV    25mm/s").setFont(font).setBold().setFontSize(10).setFixedPosition(680,145,100);
        doc.add(paragraph2);

        Paragraph paragraph1= new Paragraph("10mm/mV    25mm/s").setFont(font).setBold().setFontSize(10).setFixedPosition(70,420,100);
        doc.add(paragraph1);

        Paragraph paragraph4= new Paragraph("I").setFont(font).setBold().setFontSize(10).setFixedPosition(70,400,30);
        doc.add(paragraph4);

        Paragraph paragraph5= new Paragraph("II").setFont(font).setBold().setFontSize(10).setFixedPosition(70,310,30);
        doc.add(paragraph5);

        Paragraph paragraph6= new Paragraph("III").setFont(font).setBold().setFontSize(10).setFixedPosition(70,220,30);
        doc.add(paragraph6);



        Paragraph paragraph7= new Paragraph("aVR").setFont(font).setBold().setFontSize(10).setFixedPosition(265,400,30);
        doc.add(paragraph7);


        Paragraph paragraph8= new Paragraph("aVL").setFont(font).setBold().setFontSize(10).setFixedPosition(265,310,30);
        doc.add(paragraph8);


        Paragraph paragraph9= new Paragraph("aVF").setFont(font).setBold().setFontSize(10).setFixedPosition(265,220,30);
        doc.add(paragraph9);



        Paragraph paragraph10= new Paragraph("V1").setFont(font).setBold().setFontSize(10).setFixedPosition(445,400,30);
        doc.add(paragraph10);


        Paragraph paragraph11= new Paragraph("V2").setFont(font).setBold().setFontSize(10).setFixedPosition(445,310,30);
        doc.add(paragraph11);


        Paragraph paragraph12= new Paragraph("V3").setFont(font).setBold().setFontSize(10).setFixedPosition(445,220,30);
        doc.add(paragraph12);




        Paragraph paragraph13= new Paragraph("V4").setFont(font).setBold().setFontSize(10).setFixedPosition(630,400,30);
        doc.add(paragraph13);


        Paragraph paragraph14= new Paragraph("V5").setFont(font).setBold().setFontSize(10).setFixedPosition(630,310,30);
        doc.add(paragraph14);


        Paragraph paragraph15= new Paragraph("V6").setFont(font).setBold().setFontSize(10).setFixedPosition(630,220,30);
        doc.add(paragraph15);


        Paragraph paragraph3= new Paragraph("II").setFont(font).setBold().setFontSize(10).setFixedPosition(70,130,30);
        doc.add(paragraph3);


    }


    private static void drawWeekEcg44(Document doc, PdfFont font,float x, float y, float width,int type,Map<String,Double[]> ecgDataMap) {
        PdfCanvas pdfCanvas = new PdfCanvas(doc.getPdfDocument().getLastPage());



        //一大格
        double pigK = (width/52);
        float height = (float) (pigK * type)+1f;
        //一小格
        double smallK = pigK / 5;
        pdfCanvas.setLineDash(0);
        for (int i = 0; i < (height / pigK); i++) {
            if (i == 0 || i == (height / pigK) - 1) {
                pdfCanvas.setLineDash(0); // 实线
            } else {
                pdfCanvas.setLineDash(1, 1); // 虚线
            }
            pdfCanvas.moveTo(x, y - height + i * pigK);
            pdfCanvas.lineTo(x + width, y - height + i * pigK);
        }

        // 绘制竖线
        for (int i = 0; i < (width / pigK); i++) {
            if (i == 0 || i == (width / pigK) - 1) {
                pdfCanvas.setLineDash(0); // 实线
            } else {
                pdfCanvas.setLineDash(1, 1); // 虚线
            }
            pdfCanvas.moveTo(x + i * pigK, y - height);
            pdfCanvas.lineTo(x + i * pigK, y);
        }
        pdfCanvas.stroke();

        pdfCanvas.setLineWidth(0.1f);
        int index = 0;
        int indey = 0;
        for (double i = y - height + smallK; i < y; i += smallK) {
            index++;
            if (index % 5 == 0) continue;
            indey = 0;
            for (double j = x + smallK; j < x + width; j += smallK) {
                indey++;
                if (indey % 5 == 0) continue;
                pdfCanvas.rectangle(j - 0.25, i - 0.25, 0.5, 0.5);
                pdfCanvas.fill();
            }
        }        //画线


        pdfCanvas.stroke();
        pdfCanvas.setLineDash(0);
        //画心电图
        pdfCanvas.setStrokeColor(ColorConstants.BLACK);
        pdfCanvas.setFontAndSize(font, 6);
        pdfCanvas.setLineWidth(1f);
        pdfCanvas.setFillColor(ColorConstants.BLACK);
        double dx;
        dx = x;

        int sampleRate =100;

        for (int j = 0; j < 4; j++) {
            double dy = y - (3 + 6 * j) * pigK;
            int a = 0;
            Integer[][] data = voltageData1;
            for (int i = 0; i < data.length; i++) {
                if ( data[i][0]!=null){
                    double vx =   x;
                    vx+=5 * pigK / sampleRate  *(data[i][1]);
                    a++;
                    if (a==1){
                        pdfCanvas.moveTo(vx, dy);
                    }
                    pdfCanvas.lineTo(vx, dy + data[i][0] * 2 * pigK);
                }
            }
        }

        pdfCanvas.stroke();




        Double[] iis =  Arrays.copyOfRange(ecgDataMap.get("II"), 0, 1000) ;

        int flag = 0;
        //第一行  I  aVR  V1  V4
        double dy = y - (3) * pigK;
        pdfCanvas.moveTo(x+2*pigK, dy);
        for (double i = x+2*pigK; i < x + width; i += 5 * pigK / sampleRate) {
            if (flag >= iis.length) break;
            pdfCanvas.lineTo(i, dy + iis[flag] * 2 * pigK);
            flag++;
        }

        flag = 0;
        dy = y - (3+6) * pigK;
        pdfCanvas.moveTo(x+2*pigK, dy);
        for (double i = x+2*pigK; i < x + width; i += 5 * pigK / sampleRate) {
            if (flag >= ecgDataMap.get("V2").length) break;
            pdfCanvas.lineTo(i, dy + ecgDataMap.get("V2")[flag] * 2 * pigK);
            flag++;
        }
        flag = 0;
        dy = y - (3+6+6) * pigK;
        pdfCanvas.moveTo(x+2*pigK, dy);
        for (double i = x+2*pigK; i < x + width; i += 5 * pigK / sampleRate) {
            if (flag >= ecgDataMap.get("V4").length) break;
            pdfCanvas.lineTo(i, dy + ecgDataMap.get("V4")[flag] * 2 * pigK);
            flag++;
        }

        flag = 0;
        dy = y - (3+6+6+6) * pigK;
        pdfCanvas.moveTo(x+2*pigK, dy);
        for (double i = x+2*pigK; i < x + width; i += 5 * pigK / sampleRate) {
            if (flag >= ecgDataMap.get("V6").length) break;
            pdfCanvas.lineTo(i, dy + ecgDataMap.get("V6")[flag] * 2 * pigK);
            flag++;
        }


        pdfCanvas.stroke();





        Paragraph paragraph1= new Paragraph("10mm/mV    25mm/s").setFont(font).setBold().setFontSize(10).setFixedPosition(70,420,100);
        doc.add(paragraph1);

        Paragraph paragraph4= new Paragraph("II").setFont(font).setBold().setFontSize(10).setFixedPosition(70,400,30);
        doc.add(paragraph4);

        Paragraph paragraph5= new Paragraph("V2").setFont(font).setBold().setFontSize(10).setFixedPosition(70,310,30);
        doc.add(paragraph5);

        Paragraph paragraph6= new Paragraph("V4").setFont(font).setBold().setFontSize(10).setFixedPosition(70,220,30);
        doc.add(paragraph6);


        Paragraph paragraph3= new Paragraph("V6").setFont(font).setBold().setFontSize(10).setFixedPosition(70,130,30);
        doc.add(paragraph3);


    }

    private static void drawWeekEcg42(Document doc, PdfFont font,float x, float y, float width,int type,Map<String,Double[]> ecgDataMap) {
        PdfCanvas pdfCanvas = new PdfCanvas(doc.getPdfDocument().getLastPage());



        //一大格
        double pigK = (width/52);
        float height = (float) (pigK * type)+1f;
        //一小格
        double smallK = pigK / 5;
        pdfCanvas.setLineDash(0);
        for (int i = 0; i < (height / pigK); i++) {
            if (i == 0 || i == (height / pigK) - 1) {
                pdfCanvas.setLineDash(0); // 实线
            } else {
                pdfCanvas.setLineDash(1, 1); // 虚线
            }
            pdfCanvas.moveTo(x, y - height + i * pigK);
            pdfCanvas.lineTo(x + width, y - height + i * pigK);
        }

        // 绘制竖线
        for (int i = 0; i < (width / pigK); i++) {
            if (i == 0 || i == (width / pigK) - 1) {
                pdfCanvas.setLineDash(0); // 实线
            } else {
                pdfCanvas.setLineDash(1, 1); // 虚线
            }
            pdfCanvas.moveTo(x + i * pigK, y - height);
            pdfCanvas.lineTo(x + i * pigK, y);
        }
        pdfCanvas.stroke();

        pdfCanvas.setLineWidth(0.1f);
        int index = 0;
        int indey = 0;
        for (double i = y - height + smallK; i < y; i += smallK) {
            index++;
            if (index % 5 == 0) continue;
            indey = 0;
            for (double j = x + smallK; j < x + width; j += smallK) {
                indey++;
                if (indey % 5 == 0) continue;
                pdfCanvas.rectangle(j - 0.25, i - 0.25, 0.5, 0.5);
                pdfCanvas.fill();
            }
        }        //画线


        pdfCanvas.stroke();
        pdfCanvas.setLineDash(0);
        //画心电图
        pdfCanvas.setStrokeColor(ColorConstants.BLACK);
        pdfCanvas.setFontAndSize(font, 6);
        pdfCanvas.setLineWidth(1f);
        pdfCanvas.setFillColor(ColorConstants.BLACK);
        double dx;
        dx = x;

        int sampleRate =100;

        for (int j = 0; j < 2; j++) {
            double dy = y - (3 + 6 * j) * pigK;
            int a = 0;
            Integer[][] data = voltageData1;
            for (int i = 0; i < data.length; i++) {
                if ( data[i][0]!=null){
                    double vx =   x;
                    vx+=5 * pigK / sampleRate  *(data[i][1]);
                    a++;
                    if (a==1){
                        pdfCanvas.moveTo(vx, dy);
                    }
                    pdfCanvas.lineTo(vx, dy + data[i][0] * 2 * pigK);
                }
            }
        }

        pdfCanvas.stroke();




        Double px =(5 * pigK / sampleRate) *500 ;

        int flag = 0;
        //第一行  I  aVR  V1  V4
        double dy = y - (3) * pigK;
        pdfCanvas.moveTo(x+2*pigK, dy);
        for (double i = x+2*pigK; i < x + width; i += 5 * pigK / sampleRate) {
            if (flag >= ecgDataMap.get("II").length) break;
            pdfCanvas.lineTo(i, dy + ecgDataMap.get("II")[flag] * 2 * pigK);
            flag++;
        }
        flag = 0;
        pdfCanvas.moveTo(x+2*pigK+px, dy);
        for (double i = x+2*pigK+px; i < x + width; i += 5 * pigK / sampleRate) {
            if (flag >= ecgDataMap.get("V2").length) break;
            pdfCanvas.lineTo(i, dy + ecgDataMap.get("V2")[flag] * 2 * pigK);
            flag++;
        }



        flag = 0;
        //第一行  I  aVR  V1  V4
        dy = y - (3+6) * pigK;
        pdfCanvas.moveTo(x+2*pigK, dy);
        for (double i = x+2*pigK; i < x + width; i += 5 * pigK / sampleRate) {
            if (flag >= ecgDataMap.get("V4").length) break;
            pdfCanvas.lineTo(i, dy + ecgDataMap.get("V4")[flag] * 2 * pigK);
            flag++;
        }
        flag = 0;
        pdfCanvas.moveTo(x+2*pigK+px, dy);
        for (double i = x+2*pigK+px; i < x + width; i += 5 * pigK / sampleRate) {
            if (flag >= ecgDataMap.get("V6").length) break;
            pdfCanvas.lineTo(i, dy + ecgDataMap.get("V6")[flag] * 2 * pigK);
            flag++;
        }

        pdfCanvas.stroke();

        Paragraph paragraph1= new Paragraph("10mm/mV    25mm/s").setFont(font).setBold().setFontSize(10).setFixedPosition(70,420,100);
        doc.add(paragraph1);

        Paragraph paragraph4= new Paragraph("II").setFont(font).setBold().setFontSize(10).setFixedPosition(70,400,30);
        doc.add(paragraph4);

        Paragraph paragraph5= new Paragraph("V4").setFont(font).setBold().setFontSize(10).setFixedPosition(70,310,30);
        doc.add(paragraph5);

        Paragraph paragraph6= new Paragraph("V2").setFont(font).setBold().setFontSize(10).setFixedPosition(450,400,30);
        doc.add(paragraph6);


        Paragraph paragraph3= new Paragraph("V6").setFont(font).setBold().setFontSize(10).setFixedPosition(450,310,30);
        doc.add(paragraph3);


    }



    private static Integer[][] voltageData12 = {
            {null, 0},
            {null, 1},
            {null, 2},
            {null, 3},
            {null, 4},
            {null, 5},
            {null, 6},
            {null, 7},
            {0, 8},
            {0, 9},
            {0, 10},
            {0, 11},
            {1, 11},
            {1, 13},
            {1, 14},
            {1, 15},
            {1, 17},
            {0, 17},
            {0, 18},
            {0, 19},
            {0, 20},
            {0, 21},
            {0, 23},
            {1, 23},
            {1, 24},
            {1, 25},
            {1, 26},
            {1, 28},
            {0, 28},
            {0, 29},
            {0, 30},
            {0, 31},
            {null, 32},
            {null, 33},
            {null, 34},
            {null, 35},
            {null, 36},
            {null, 37},
            {null, 38},
            {null, 39},
    };


    private static Integer[][] voltageData1 = {
            {null, 0},
            {null, 1},
            {null, 2},
            {null, 3},
            {null, 4},
            {null, 5},
            {null, 6},
            {null, 7},
            {null, 8},
            {0, 9},
            {0, 10},
            {0, 11},
            {0, 12},
            {0, 13},
            {1, 13},
            {1, 15},
            {1, 16},
            {1, 17},
            {1, 18},
            {1, 19},
            {1, 20},
            {1, 21},
            {1, 22},
            {1, 23},
            {1, 24},
            {1, 25},
            {0, 25},
            {0, 27},
            {0, 28},
            {0, 29},
            {0, 30},
            {null, 31},
            {null, 32},
            {null, 33},
            {null, 34},
            {null, 35},
            {null, 36},
            {null, 37},
            {null, 38},
            {null, 39},
    };




}
