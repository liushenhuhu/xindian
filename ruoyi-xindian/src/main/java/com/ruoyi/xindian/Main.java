package com.ruoyi.xindian;

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
public class Main {


    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException {



        Map<String,Object> map = new HashMap<>();
        map.put("patientName","王金辉");
        map.put("pId","YXDJ6a685ab4-2eb7-4ab0-8baa-bdeed4c94f39");
        map.put("sex","男");
        map.put("age","22");
        map.put("source","郑大");
        map.put("recordTime","2024-12-12");
        map.put("reportTime","2024-12-12 12:12:12");
        map.put("getDataTime","2024/12/12 12:12");
        map.put("heart","78");
        map.put("conclusion","窦性心率");
        map.put("pr","78");
        map.put("qtqtc","200.00/200.0");
        map.put("ecgType","JECG4WL");
        map.put("qrs","200");
        map.put("status","2");
        map.put("reportType","12导联+解析结果");
        map.put("doctorPhoto","D:\\home\\chenpeng\\workspace\\system\\xindian\\uploadPath\\doctorDzVisa\\2024-08-08\\1723106644234-doctor.png");




        Map<String,Double[]> ecgDataMap = new HashMap<>();
        ecgDataMap.put("I",Arrays.copyOfRange(data_II, 200, 450));
        ecgDataMap.put("II",Arrays.copyOfRange(data_II, 200, 450));
//        ecgDataMap.put("II",data_II);
        ecgDataMap.put("III",Arrays.copyOfRange(data_II, 200, 450));
        ecgDataMap.put("aVR",Arrays.copyOfRange(data_II, 200, 450));
        ecgDataMap.put("aVL",Arrays.copyOfRange(data_II, 200, 450));
        ecgDataMap.put("aVF",Arrays.copyOfRange(data_II, 200, 450));
        ecgDataMap.put("V1",Arrays.copyOfRange(data_II, 200, 450));
        ecgDataMap.put("V2",Arrays.copyOfRange(data_II, 200, 450));
        ecgDataMap.put("V3",Arrays.copyOfRange(data_II, 200, 450));
        ecgDataMap.put("V4",Arrays.copyOfRange(data_II, 200, 450));
        ecgDataMap.put("V5",Arrays.copyOfRange(data_II, 200, 450));
        ecgDataMap.put("V6",Arrays.copyOfRange(data_II, 200, 450));
        ecgDataMap.put("all_II",Arrays.copyOfRange(data_II, 0, 1000));
        new Main().createPdf(map,ecgDataMap);
    }




    public void createPdf(Map<String,Object> map,Map<String,Double[]> ecgDataMap) throws IOException {
        //字体
        PdfFont font = PdfFontFactory.createFont(
                "./ruoyi-xindian/src/main/java/com/ruoyi/xindian/pdf/utils/STXIHEI.TTF",
                PdfEncodings.IDENTITY_H,
                true
        );

        try {
            PdfWriter writer = new PdfWriter("landscape.pdf");
            PdfDocument pdf = new PdfDocument(writer);
            PageSize rotate = PageSize.A4.rotate();
            Document document = new Document(pdf, rotate);


            //设置标题
            Paragraph title = new Paragraph("静态心电图报告").setFont(font).setBold().setTextAlignment(TextAlignment.CENTER).setMarginTop(-35).setFontSize(20);
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
                    .moveTo(0, 558)  // 起点坐标 (x1, y1)
                    .lineTo(rotate.getWidth(), 558) // 终点坐标 (x2, y2)
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
                drawWeekEcg30(document, font , 35,410, rotate.getWidth()-65, 18,ecgDataMap);
            }else if (ecgType.contains("JECG12")){
                //12导
                drawWeekEcg12(document, font , 35, 430, rotate.getWidth()-65, 24,ecgDataMap);
            }else if (ecgType.contains("JECG4")){
                //4导

                Double[] iis = ecgDataMap.get("II");
                if (iis.length>700){
                    //四行
                    drawWeekEcg44(document, font , 35, 430, rotate.getWidth()-65, 24,ecgDataMap);
                }else {
                    //两行
                    drawWeekEcg42(document, font , 35, 430, rotate.getWidth()-65, 12,ecgDataMap);
                }

            }


            Object o = map.get("status");
            if (o==null||!o.toString().equals("1")){
                String text1 = "(本报告由互联网医疗与健康服务河南省协同创新中心人工智能平台自动生成, 未经临床验证, 仅供参考, 请根据医生诊断进一步确认.)";
                //审核时间 + 审核医生
                Paragraph paragraph1 = new Paragraph(text1).setFont(font).setFontSize(9).setFixedPosition(35,55,550);
                document.add(paragraph1);


                String wdir = map.get("doctorPhoto").toString();
                ImageData imageData = ImageDataFactory.create(wdir);


                Image image = new Image(imageData)
                        .setFixedPosition(725, 450)
                        .scaleToFit(100, 100)
                        .setHorizontalAlignment(HorizontalAlignment.CENTER)
                        .setRotationAngle(Math.toRadians(90));
                document.add(image);
            }



            String text2 = map.get("reportType")!=null?map.get("reportType").toString():"静态II导联+解析结果";

            Paragraph paragraph2 = new Paragraph("报告格式："+text2).setFont(font).setFontSize(9).setFixedPosition(35,35,200);
            document.add(paragraph2);



            Paragraph paragraph3= new Paragraph("仅对此报告内容负责").setFont(font).setFontSize(9).setFixedPosition(730,35,200);
            document.add(paragraph3);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void contextCell(Table table, String data, PdfFont font,Boolean type) {
        Cell cell;
        if (type){
            cell  = new Cell(1, 2).add(new Paragraph(data).setTextAlignment(TextAlignment.LEFT).setFont(font).setFontSize(9));
        }else {
            cell  = new Cell(1, 1).add(new Paragraph(data).setTextAlignment(TextAlignment.LEFT).setFont(font).setFontSize(9));
        }
        cell.setBorder(Border.NO_BORDER);
        table.addCell(cell);
    }

    public static void contextCellPId(Table table, String data, PdfFont font,Boolean type) {
        Cell cell;
        if (type){
            cell  = new Cell(1, 3).add(new Paragraph(data).setTextAlignment(TextAlignment.LEFT).setFont(font).setFontSize(9));
        }else {
            cell  = new Cell(1, 1).add(new Paragraph(data).setTextAlignment(TextAlignment.LEFT).setFont(font).setFontSize(9));
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
        Double[] doubles1 = Arrays.copyOfRange(ecgDataMap.get("II"), 0, 1000);
        pdfCanvas.moveTo(x+2*pigK, dy);
        for (double i = x+2*pigK; i < x + width; i += 5 * pigK / sampleRate) {
            if (flag >= doubles1.length) break;
            pdfCanvas.lineTo(i, dy + doubles1[flag] * 2 * pigK);
            flag++;
        }

        pdfCanvas.stroke();




        Paragraph paragraph2= new Paragraph("10mm/mV    25mm/s").setFont(font).setBold().setFontSize(10).setFixedPosition(680,145,100);
        doc.add(paragraph2);

        Paragraph paragraph1= new Paragraph("10mm/mV    25mm/s").setFont(font).setBold().setFontSize(10).setFixedPosition(70,430,100);
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





        Paragraph paragraph1= new Paragraph("10mm/mV    25mm/s").setFont(font).setBold().setFontSize(10).setFixedPosition(70,430,100);
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

        Paragraph paragraph1= new Paragraph("10mm/mV    25mm/s").setFont(font).setBold().setFontSize(10).setFixedPosition(70,430,100);
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



    private static final Double[] data_II =
            {
            0.013198852539062502,
            0.05084336663895497,
            0.13647460937499997,
            0.1702529303806155,
            0.18762207031250003,
            0.19653499201906696,
            0.195556640625,
            0.1950137999633286,
            0.18823242187500008,
            0.18181795074197885,
            0.16906738281250008,
            0.16645526811746725,
            0.1636962890625,
            0.16646063240707878,
            0.161376953125,
            0.1496497744309012,
            0.134521484375,
            0.12104433001108769,
            0.11572265625000003,
            0.11015983855720038,
            0.1044921875,
            0.0961863509625493,
            0.08929443359375,
            0.07806287266257446,
            0.070556640625,
            0.06622548195853575,
            0.06573486328125,
            0.05861152920130201,
            0.045867919921875,
            0.03121197712888244,
            0.013984680175781253,
            0.00570167838309523,
            -0.0017652511596679683,
            0.0005565944803254861,
            0.00015342235565185547,
            0.0012387392475901435,
            0.00023102760314941404,
            -0.001625958392433516,
            -0.001800537109375,
            0.0002627052455715009,
            0.0020942687988281246,
            -0.005957970197574822,
            -0.02890014648437501,
            -0.0365363579407864,
            -0.03726196289062499,
            -0.03543297483506614,
            -0.050842285156249986,
            -0.053001336080710997,
            -0.048370361328124986,
            -0.04929089535712537,
            -0.0462646484375,
            0.0483244412776613,
            0.305419921875,
            0.28307373979811684,
            -0.033935546875,
            -0.06855164709418722,
            -0.040802001953125,
            -0.005564986892693428,
            0.007358551025390623,
            0.0025477097533194975,
            -0.006008148193359375,
            -0.00858439461407694,
            -0.004428863525390626,
            -0.0049990882898796555,
            -0.00930023193359375,
            -0.012033111491568008,
            -0.01360321044921875,
            -0.01133973051117217,
            -0.007373809814453124,
            -0.0014974222135758586,
            0.00011044740676879886,
            0.003920783073221828,
            0.0044708251953125,
            0.011001371246802132,
            0.02532958984375,
            0.044789780494476336,
            0.058349609375,
            0.05920055673377963,
            0.04727172851562501,
            0.0293308594590351,
            0.01202392578125,
            0.0034449276213450353,
            -1.8634724839594607e-20,
            0.0000963446280778371,
            -0.0009860992431640627,
            -0.00042557037207725963,
            -0.00276947021484375,
            0.0007987683758533731,
            -0.002283096313476563,
            0.00017243196230028193,
            -0.0045318603515625,
            -0.0004473288053402108,
            -0.0002046823501586914,
            -0.0015138189411995693,
            -0.010337829589843748,
            -0.006108188531323375,
            -0.00009047985076904286,
            0.005963389819000471,
            -0.0012035369873046873,
            0.00402883532075642,
            0.005435943603515627,
            0.007149414293073159,
            0.0014801025390625,
            0.001256789141370606,
            -0.0065765380859375,
            -0.008200254506964176,
            -0.006942749023437501,
            0.006552216217891576,
            0.0018186569213867188,
            0.0004138936006320799,
            -0.00753021240234375,
            -0.010253379349321025,
            -0.011329650878906248,
            -0.008410459108713656,
            -0.0121612548828125,
            -0.019027251759864902,
            -0.025558471679687503,
            -0.02597603463238271,
            -0.023956298828125,
            -0.012440320208041986,
            -0.009307861328125,
            -0.006811526224711472,
            -0.008644104003906248,
            -0.004491722901268176,
            0.00018835067749023427,
            0.01646701713257907,
            0.023971557617187497,
            0.019204104520407207,
            0.0039825439453125,
            -0.004988448150952671,
            -0.01317596435546875,
            -0.014841162269827743,
            -0.016403198242187503,
            -0.012748057919420979,
            -0.021575927734374993,
            -0.01527616963334777,
            0.0030975341796875,
            0.22317609652326167,
            0.39013671875,
            0.047648284106334336,
            -0.13610839843749997,
            -0.09083789717531457,
            -0.04321289062500001,
            -0.018181902274271654,
            -0.017929077148437503,
            -0.029242093190487192,
            -0.037994384765625,
            -0.03852038049855184,
            -0.024032592773437493,
            -0.009258533188306132,
            -0.0010051727294921875,
            0.0015725738903275316,
            0.0005574226379394531,
            0.00017652620899777293,
            -0.001305580139160156,
            0.006540978636852856,
            0.0164947509765625,
            0.03167744712537779,
            0.048980712890624986,
            0.0712253282549139,
            0.0948486328125,
            0.11166653999373048,
            0.12225341796874999,
            0.12770905466312188,
            0.12152099609374999,
            0.10199619445290731,
            0.0712890625,
            0.04361339165659985,
            0.01436614990234375,
            -0.013172615876539862,
            -0.04107666015625,
            -0.059081332445181725,
            -0.0703125,
            -0.0687580319466662,
            -0.0660400390625,
            -0.058117606772013,
            -0.04757690429687501,
            -0.0349342036901432,
            -0.037261962890625,
            -0.040029480646345685,
            -0.03845214843749999,
            -0.02296359912706392,
            -0.01528167724609375,
            -0.006875429612549733,
            0.0016584396362304688,
            0.00566579576333946,
            0.0005302429199218752,
            -0.00293062148699116,
            -0.004318237304687501,
            -0.0007742294939348839,
            -0.00286865234375,
            0.0007162069024112294,
            0.001473426818847656,
            -0.00020910685755140426,
            -0.0004143714904785156,
            -0.0013671532010039096,
            -0.0011835098266601558,
            0.0022427818864605414,
            0.003747940063476563,
            0.002019879337089229,
            -0.003639221191406249,
            -0.004255165763067096,
            -0.0010671615600585935,
            0.004339556512963806,
            0.004562377929687499,
            -0.0008798262780376096,
            -0.005104064941406251,
            -0.0025299172618153233,
            0.00789642333984375,
            0.01665335182882917,
            0.022445678710937507,
            0.012466239978525237,
            -0.0007772445678710936,
            -0.008199493879110337,
            -0.006061553955078125,
            -0.006132874063065263,
            -0.0020751953125,
            0.002690490976242811,
            0.006649017333984374,
            -0.00008809467477336429,
            0.004035949707031249,
            0.137217225075993,
            0.36474609375,
            0.2134061212809432,
            -0.1051025390625,
            -0.09017896411303938,
            -0.05731201171875,
            -0.026892057419723253,
            -0.019744873046875,
            -0.02196838173227566,
            -0.020751953125,
            -0.014708247611884877,
            0.0003077983856201174,
            0.016734661682589854,
            0.027099609374999997,
            0.03370285168287224,
            0.04052734375000001,
            0.05251447419049414,
            0.0673828125,
            0.07998073723159004,
            0.09082031249999999,
            0.09617649059744036,
            0.106201171875,
            0.12111055362012813,
            0.139404296875,
            0.1530636332542875,
            0.15747070312500003,
            0.15517169244375115,
            0.156494140625,
            0.14957317614172502,
            0.1300048828125,
            0.0979466813377933,
            0.06094360351562501,
            0.03166835975826787,
            0.0036582946777343754,
            -0.014455136009248049,
            -0.029388427734375,
            -0.03435343367957843,
            -0.041412353515625,
            -0.037460302095047124,
            -0.03616333007812501,
            -0.03069766395947368,
            -0.025390625,
            -0.014887274815455202,
            -0.008636474609375,
            -0.0004888113949517125,
            0.00023102760314941412,
            0.004405575349192953,
            0.00559234619140625,
            0.01587457873049572,
            0.021301269531249997,
            0.023802974873434028,
            0.018646240234375003,
            0.012841315051780225,
            0.010108947753906248,
            0.012153775294041248,
            0.008247375488281248,
            0.004085233054745526,
            -0.0023326873779296866,
            0.0007375914417529113,
            0.000675201416015625,
            0.0067964174442989,
            0.015190124511718753,
            0.011517536813007052,
            -0.005275726318359376,
            -0.012496105606975207,
            -0.009086608886718752,
            -0.006543007119189504,
            -0.007419586181640625,
            -0.006792597643805424,
            -0.00795745849609375,
            -0.01896089181373426,
            -0.01462554931640625,
            0.07674292303955266,
            0.28955078125,
            0.12295670819590943,
            -0.08978271484374999,
            -0.08088201772466957,
            -0.0208740234375,
            0.007234336791118694,
            0.00888824462890625,
            -0.0006248554066136704,
            -0.006721496582031252,
            -0.0024902894546695526,
            0.0019102096557617188,
            0.0014712316149756285,
            0.00045680999755859375,
            -0.004758678878587959,
            -0.015960693359375,
            -0.028434489607823913,
            -0.03436279296874999,
            -0.029543581033898966,
            -0.016082763671874997,
            -0.0019245542575492044,
            0.010276794433593748,
            0.023685555329015968,
            0.03463745117187499,
            0.04414709866322917,
            0.04718017578125,
            0.049038019695108286,
            0.03778076171875,
            0.021469937366504196,
            0.004451751708984375,
            0.00008345946344876484,
            -4.235164736271502e-22,
            0.0000651355228638379,
            -0.0027942657470703125,
            -0.0008717199307614388,
            5.364418029785156e-7,
            0.000921190492051978,
            -0.004493713378906249,
            -0.001825611575595709,
            0.0011682510375976562,
            0.003279201204010384,
            -0.00045871734619140625,
            0.00037003540780272757,
            0.0007925033569335935,
            0.0021177335322373207,
            0.00013339519500732422,
            -0.0012439768991046609,
            -0.006900787353515625,
            -0.009039373335371479,
            -0.019607543945312503,
            -0.02833234128336748,
            -0.03756713867187501,
            -0.034432077728990734,
            -0.03076171875,
            -0.03344396886478127,
            -0.0433349609375,
            -0.04725088873000208,
            -0.0594482421875,
            -0.057209374061068775,
            -0.054656982421875,
            -0.04383962870262573,
            -0.05242919921875001,
            -0.058608521165629014,
            -0.06286621093749999,
            -0.05877400026290524,
            -0.05801391601562501,
            -0.046890349526069,
            -0.027938842773437503,
            -0.004990324188169766,
            0.013824462890625,
            0.02403439380712255,
            0.024200439453125003,
            0.026279039565157886,
            0.030288696289062507,
            0.03373460115530293,
            0.027862548828124997,
            0.03034991808740956,
            0.021453857421874993,
            0.013632787666666124,
            0.0010833740234375,
            0.0025982351204345178,
            -0.0010242462158203125,
            -0.005601772750065438,
            -0.01462554931640625,
            -0.03253039899120303,
            -0.0380859375,
            0.05936874098346993,
            0.31591796875,
            0.24778504096958454,
            -0.07299804687499999,
            -0.08537516748329255,
            -0.022735595703124993,
            0.012746930129685726,
            0.0274200439453125,
            0.028860899038185093,
            0.019378662109375003,
            0.010428734217402806,
            -0.0002315044403076173,
            -0.007218490554000379,
            -0.0161590576171875,
            -0.012348846699930053,
            -0.006431579589843749,
            0.0016737295749108744,
            0.0006146430969238282,
            -0.0041295943715613705,
            -0.011138916015625,
            -0.007372049416261152,
            0.00409698486328125,
            0.015872243662560237,
            0.01468658447265625,
            0.013024973003904814,
            0.011985778808593752,
            0.00972624132968849,
            0.0016231536865234375,
            0.0005269655794944503,
            (double) 0,
            0.002424963336075054,
            5.421010862427522e-20,
            -0.0005067170077665141,
            -0.0039520263671875,
            -0.0028811951872741654,
            -0.006427764892578124,
            0.002338320798406723,
            0.0014829635620117188,
            0.005873140143985171,
            0.0013828277587890627,
            0.0023037405070915127,
            0.00018918514251708982,
            -0.001307508819187461,
            -0.006248474121093749,
            -0.006429478284905108,
            -0.0137176513671875,
            -0.01850358081050335,
            -0.028121948242187503,
            -0.03255683800030802,
            -0.03634643554687501,
            -0.03828111651251654,
            -0.046295166015625,
            -0.05450040874442694,
            -0.0675048828125,
            -0.07771036707342521,
            -0.08428955078125,
            -0.07112565382616941,
            -0.06353759765625001,
            -0.048741891441539516,
            -0.04034423828125,
            -0.02588195116629172,
            -0.023712158203125007,
            -0.030520263968004627,
            -0.04211425781249999,
            -0.038490103112668476,
            -0.026397705078125003,
            -0.019120378480920165,
            -0.014289855957031248,
            0.00010501692983313807,
            0.0169677734375,
            0.0191631983381121,
            0.015380859375000002,
            0.01177125235056036,
            0.005138397216796876,
            -0.004595020382767909,
            -0.012168884277343747,
            -0.0182212957941937,
            -0.0170440673828125,
            -0.009859016240834016,
            0.00010585784912109375,
            0.0011096244934263462,
            -0.009361267089843752,
            -0.01129829012559555,
            0.11505126953125001,
            0.32347987833411507,
            0.07720947265625,
            -0.13354435223214955,
            -0.10174560546875,
            -0.07000893794434372,
            -0.052276611328125,
            -0.04317808802596897,
            -0.0318603515625,
            -0.02149711897359903,
            -0.0180206298828125,
            -0.011539138003422966,
            -0.005104064941406252,
            -0.0005435954238112199,
            -0.0005989074707031249,
            0.000006692306185621704,
            0.0007715225219726561,
            0.005004930672524031,
            0.016555786132812497,
            0.032380961189913036,
            0.054718017578125014,
            0.07346486767539588,
            0.0924072265625,
            0.11095067007054758,
            0.12756347656250003,
            0.13713702517452733,
            0.14135742187500003,
            0.14120314995835015,
            0.13354492187500006,
            0.11739336689426565,
            0.08947753906249999,
            0.06076240218695145,
            0.03222656250000001,
            0.009386128190009165,
            -0.0157318115234375,
            -0.025336049827354358,
            -0.022537231445312507,
            -0.015040730767125726,
            -0.0174407958984375,
            -0.020786599627217326,
            -0.025741577148437507,
            -0.02303132955700184,
            -0.018966674804687493,
            -0.01265019349847414,
            -0.006748199462890626,
            -0.0022860516841815117,
            -0.0013532638549804688,
            -0.001772311743310355,
            -0.005550384521484376,
            -0.004179155517806905,
            -0.003408432006835937,
            0.004763520151328325,
            0.00496673583984375,
            0.00736710366048695,
            0.00493621826171875,
            0.0017830469180109337,
            -0.00885009765625,
            -0.008528846817517213,
            -0.00824737548828125,
            -0.0077337643194554835,
            -0.010894775390625,
            -0.009152932808351482,
            -0.004913330078124999,
            -0.004153245440521551,
            -0.00447845458984375,
            -0.002992073835719673,
            -0.003400802612304687,
            -0.0020048455330564188,
            -0.0011520385742187498,
            0.004721444526249767,
            0.013771057128906253,
            0.023249197050808246,
            0.025802612304687503,
            0.03463432573183068,
            0.04071044921875,
            0.037170588704739334,
            0.0202178955078125,
            0.007351139811447532,
            -0.0014524459838867196,
            -0.0037480098396469783,
            -0.01141357421875,
            -0.0122850006788108,
            -0.021148681640625,
            -0.02987833181569924,
            -0.03411865234374999,
            0.13308406843616416,
            0.37500000000000006,
            0.19834933950465317,
            -0.08520507812499997,
            -0.08341907781374344,
            -0.06817626953125,
            -0.04241988199225088,
            -0.03262329101562499,
            -0.02830156975766931,
            -0.024993896484375007,
            -0.01976436540841478,
            -0.0095367431640625,
            0.0005787314375543651,
            0.0048828125,
            0.01838870870220817,
            0.0322265625,
            0.05064164927333201,
            0.05563354492187499,
            0.06905707244696481,
            0.08544921875,
            0.11784753047227098,
            0.1405029296875,
            0.1558472840857784,
            0.1634521484375,
            0.16978965324086814,
            0.1761474609375,
            0.1777047558538112,
            0.16503906250000003,
            0.14527948652947964,
            0.12225341796875,
            0.09719949924429847,
            0.06292724609375001,
            0.027525370167674902,
            0.003818511962890626,
            -0.008619992782561757,
            -0.016067504882812503,
            -0.019350228237871633,
            -0.026519775390624997,
            -0.028208634305784807,
            -0.028182983398437507,
            -0.025774422435521832,
            -0.0173797607421875,
            -0.0023838374951445944,
            0.012115478515625003,
            0.02576788696555619,
            0.035400390625,
            0.045088355876585176,
            0.04861450195312501,
            0.04532746082795621,
            0.033355712890624986,
            0.016828761091756482,
            0.004100799560546875,
            0.0005163486860630751,
            5.364418029784614e-7,
            0.0007310743019647833,
            -0.006500244140625,
            -0.014741968938211353,
            -0.032623291015625,
            -0.04389456873618187,
            -0.056121826171875,
            -0.0618171871473193,
            -0.07336425781249999,
            -0.08204991878244512,
            -0.09564208984375,
            -0.10810396177414024,
            -0.11944580078124997,
            -0.1234219300798686,
            -0.12493896484374999,
            -0.10617311596724065,
            -0.07952880859375,
            -0.04541959987159995,
            -0.0234832763671875,
            -0.005803216703995387,
            -0.006633758544921876,
            -0.017294632350895112,
            -0.03369140624999999,
            -0.038822864507253814,
            -0.0386962890625,
            -0.02929506943893298,
            -0.024688720703125,
            -0.0147018452258205,
            -0.00908660888671875,
            -0.013491060430914897,
            -0.0170135498046875,
            0.10807555401082176,
            0.38891601562500006,
            0.3263628917072106,
            -0.014030456542968747,
            -0.04770412378850821,
            0.004486083984375002,
            0.041361619723714015,
            0.06249999999999999,
            0.07050411471788826,
            0.07373046875,
            0.06955455767971407,
            0.06976318359375,
            0.06865445036448004,
            0.06781005859375,
            0.06857937208907992,
            0.07531738281249999,
            0.08523141446592596,
            0.090576171875,
            0.0922389813380946,
            0.09374999999999999,
            0.09849066462466317,
            0.10443115234375003,
            0.11680858772972272,
            0.1287841796875,
            0.14686537862640087,
            0.15368652343750003,
            0.15875927650175428,
            0.14758300781249997,
            0.1323351821501896,
            0.10040283203125,
            0.05798126131266116,
            0.020050048828125,
            0.001136248607438221,
            -1.461131834013668e-20,
            -0.00001478709441069513,
            -0.000613689422607422,
            0.00005749620129575102,
            -2.384185791015676e-7,
            0.0003759697636538669,
            -0.0005898475646972656,
            -0.0000335247882532175,
            0.00032114982604980474,
            0.0003781373211034698,
            -0.00025963783264160167,
            -0.0005004214279852893,
            -0.005847930908203124,
            -0.0016610958193690832,
            0.0009050369262695317,
            0.005768384025388066,
            0.0013036727905273438,
            0.00024134120822085238,
            -1.6940658945086007e-20,
            -0.0011861763418863425,
            -0.003742218017578125,
            -0.0022424257631577407,
            -0.001163482666015625,
            0.0018915286567580568,
            0.002277374267578125,
            0.003414167993507313,
            0.0008182525634765625,
            -0.00012468810627119342,
            -0.001839637756347656,
            0.00008038182195618751,
            0.000295877456665039,
            -0.0016436910544112078,
            -0.014251708984375,
            -0.02170972506004629,
            -0.024688720703125003,
            -0.015668980946528382,
            -0.014923095703125003,
            -0.011769991955737007,
            -0.01263427734375,
            0.00028698243128546765,
            0.0101165771484375,
            0.024258192845167764,
            0.028915405273437507,
            0.03729342540751087,
            0.02349853515625,
            0.008016179029584052,
            -0.011573791503906253,
            -0.019740868509266447,
            -0.03311157226562499,
            -0.040052598810232515,
            -0.04467773437500001,
            -0.05182061351201581,
            -0.07489013671874999,
            -0.09815332713304965,
            0.0709228515625,
            0.2899793754439338,
            0.11303710937499999,
            -0.20453282617661264,
            -0.1982421875,
            -0.15626467621045215,
            -0.13037109375,
            -0.12282377518820371,
            -0.12274169921875,
            -0.11709125256915487,
            -0.11724853515625001,
            -0.1125087788060468,
            -0.10955810546875,
            -0.09968674195064813,
            -0.09088134765625,
            -0.07301655038933967,
            -0.054626464843749986,
            -0.03601034159712053,
            -0.019515991210937497,
            -0.00654431673592866,
            0.003393173217773437,
            0.019305113899562635,
            0.042938232421875014,
            0.07050179112102002,
            0.08581542968749999,
            0.10323349503601119,
            0.11718750000000001,
            0.1328218872790951,
            0.13391113281249997,
            0.12343721694979015,
            0.10339355468750001,
            0.08083976714771743,
            0.061859130859375,
            0.04836995208716511,
            0.034942626953125,
            0.03445820177445959,
            0.03619384765625,
            0.04980184479589643,
            0.05422973632812501,
            0.06378750754915914,
            0.06256103515624999,
            0.06711876993503879,
            0.07159423828125,
            0.07515610391622468,
            0.0684814453125,
            0.05515557381975387,
            0.03778076171875,
            0.014756172445220813,
            -0.00978851318359375,
            -0.030235261142337707,
            -0.04641723632812499,
            -0.060424472297465524,
            -0.072265625,
            -0.07935177901358932,
            -0.07940673828125,
            -0.08001682484233202,
            -0.08660888671875001,
            -0.09193688377093623,
            -0.09179687499999999,
            -0.08447833623027122,
            -0.0833740234375,
            -0.08118927486793408,
            -0.07672119140624999,
            -0.06956814131903961,
            -0.06695556640625001,
            -0.0694817812409703,
            -0.06524658203124999,
            -0.0556530629722866,
            -0.0458984375,
            -0.027515003654296953,
            -0.013496398925781252,
            -0.0007972137945008868,
            -0.0003349781036376951,
            0.00936403585687828,
            0.004436492919921875,
            -0.0012099663406962996,
            -0.006587982177734375,
            0.00421988980894618,
            0.01194000244140625,
            0.018872399588005413,
            0.020324707031250003,
            0.014607738551622777,
            -0.00847625732421875,
            -0.01527406153981616,
            0.03970336914062501,
            0.23749048007994694,
            0.205078125,
            -0.15022607540540356,
            -0.16369628906250003,
            -0.10175683287095377,
            -0.054351806640625014,
            -0.026011511595060686,
            -0.0113983154296875,
            -0.0017560547160903107,
            -0.00939178466796875,
            -0.018161400139321816,
            -0.01557159423828125,
            -0.004692768981801863,
            6.776263578034403e-21,
            0.00008927567915695938,
            1.0842021724855044e-19,
            0.0010863432553988544,
            5.364418029785156e-7,
            0.0023267906765604223,
            -0.0008001327514648438,
            0.0009030057740195126,
            0.004436492919921875,
            0.02128823019088824,
            0.043243408203125,
            0.07085744081830522,
            0.091064453125,
            0.10935161930716211,
            0.11212158203124997,
            0.10402916517225057,
            0.08111572265625,
            0.04720757015213342,
            0.008178710937499998,
            -0.027214473444224157,
            -0.048980712890625,
            -0.05533645796937806,
            -0.04400634765625,
            -0.025952117981038463,
            -0.01027679443359375,
            0.003160563851081597,
            0.014816284179687498,
            0.019375950287642083,
            0.01812744140625,
            0.025240882308524133,
            0.03924560546874999,
            0.04999623917496828,
            0.04681396484375,
            0.03398792120116177,
            0.010284423828124998,
            -0.018964002193392664,
            -0.0469970703125,
            -0.055016524474139974,
            -0.04507446289062499,
            -0.02076779702295389,
            -0.005168914794921875,
            0.0021136557717330727,
            -0.00678253173828125,
            -0.020616412894498927,
            -0.03607177734374999,
            -0.035255573477922485,
            -0.01898193359375,
            0.007375176965078437,
            0.026748657226562503,
            0.024144887744148594,
            -0.001783370971679688,
            -0.015621230007098958,
            -0.009986877441406252,
            0.0034310229852343482,
            -0.008926391601562498,
            -0.024565767427753984,
            -0.034942626953124986,
            -0.04653764383258432,
            -0.047943115234375014,
            -0.024236944400801173,
            0.1746826171875,
            0.3078324145468859,
            0.008827209472656243,
            -0.0905861176537079,
            -0.04510498046875,
            -0.012765078351515522,
            -0.0003840923309326172,
            -0.004639265718198435,
            -0.0162353515625,
            -0.023666637269034518,
            -0.0174102783203125,
            -0.006068191924129011,
            -0.0005779266357421876,
            -0.00044037501593596377,
            -0.000335693359375,
            0.000004708964463625561,
            0.0003993511199951171,
            0.001118979899226014,
            8.470329472543003e-21,
            -0.0006878704338319202,
            -0.0006184577941894532,
            -0.000009092880524902639,
            0.000029146671295166022,
            -0.00006709500650003206,
            0.0008001327514648439,
            7.23470364766632e-7,
            (double) 0,
            -0.00035681505411133316,
            0.005756378173828125,
            0.033652676297968274,
            0.08404541015625,
            0.10068730362851551,
            0.081298828125,
            0.05218371052451848,
            0.024398803710937503,
            0.006464836421573965,
            -0.006313323974609375,
            -0.00899042409121844,
            -0.007717132568359377,
            -0.0006850650447280197,
            -0.0009889602661132812,
            -0.0005316514351963515,
            -0.003385543823242187,
            -0.0002668468422676858,
            -0.0013809204101562502,
            -0.004301110518326231,
            -0.004566192626953127,
            -0.0027774312420497547,
            0.0014047622680664062,
            -0.003672337277448559,
            -0.0073394775390625,
            -0.008269824303133116,
            -0.0020942687988281246,
            0.01788441619310788,
            0.041961669921874986,
            0.05760360065294894,
            0.06182861328125001,
            0.06898359663122494,
            0.072998046875,
            0.07049991636757856,
            0.06420898437500001,
            0.05962443859373958,
            0.05541992187499999,
            0.05173933595430185,
            0.04962158203125,
            0.05216795524751528,
            0.054107666015625,
            0.05465288613374343,
            0.048400878906250014,
            0.04182406351247261,
            0.038055419921875,
            0.040091341773060764,
            0.03936767578125001,
            0.0482783184749765,
            0.05194091796875,
            0.059441818428063925,
            0.05316162109375001,
            0.05447886763406107,
            0.043884277343750014,
            0.03170200865277371,
            0.013282775878906252,
            0.0029502383464170916,
            -0.004730224609375,
            -0.010679274980456017,
            -0.0161285400390625,
            -0.024307761367111984,
            -0.037139892578125,
            -0.04099320970687056,
            0.0931396484375,
            0.3250117673669096,
            0.1776123046875,
            -0.07148525675377629,
            -0.06011962890625001,
            -0.03101056939681166,
            -0.008934020996093752,
            -0.00012684846371135273,
            -0.0028762817382812496,
            -0.0050783269812447514,
            -0.01104736328125,
            -0.009876026478939466,
            -0.009063720703124998,
            -0.008457284157481012,
            -0.006168365478515625,
            -0.0034993420919153316,
            -0.0009093284606933596,
            0.0009164876980690504,
            0.0012884140014648438,
            0.0012601064515269204,
            -0.0009374618530273447,
            0.01297013931635395,
            0.024215698242187503,
            0.03466158073038962,
            0.0382080078125,
            0.0483249757855232,
            0.0501708984375,
            0.043481823353842076,
            0.0297088623046875,
            0.018657868303455016,
            0.00846099853515625,
            0.0021056735434834832,
            -8.470329472543003e-22,
            0.00010912725108609845,
            -0.0032253265380859375,
            -0.0010278105167555026,
            -0.0011510848999023442,
            0.003246883054569552,
            -0.0006909370422363281,
            -0.0003016492266487689,
            -0.002183914184570312,
            -0.002873043690230162,
            -0.00234222412109375,
            0.002439899550145415,
            0.004390716552734375,
            0.0028815038381980033,
            -0.0012931823730468752,
            -0.0002960578034446535,
            -0.005306243896484375,
            -0.0044208761024090324,
            -0.009979248046875002,
            -0.015194287302032468,
            -0.022674560546875,
            -0.020782102547034616,
            -0.021255493164062503,
            -0.02105629701424483,
            -0.0258941650390625,
            -0.02595132129747916,
            -0.02783203125,
            -0.027474986443094193,
            -0.028320312499999997,
            -0.02760260095799099,
            -0.027923583984374997,
            -0.024964103198912832,
            -0.029876708984375,
            -0.028462137741135,
            -0.027923583984375,
            -0.019229803688924966,
            -0.019561767578125,
            -0.019630419943615512,
            -0.022018432617187497,
            -0.013522472440963221,
            -0.0028934478759765625,
            0.010384266181226516,
            0.0164031982421875,
            0.016182535462219056,
            0.011749267578125002,
            0.00944009304620732,
            0.007804870605468748,
            -0.004638402617699066,
            -0.0206146240234375,
            -0.03156835251524422,
            -0.030349731445312493,
            -0.026627100993544463,
            -0.0099029541015625,
            0.06672295572863976,
            0.29199218749999994,
            0.33007751478553393,
            -0.034454345703125014,
            -0.12536319168847646,
            -0.06304931640625,
            -0.025838356480656112,
            -0.009948730468750002,
            -0.013303084112456415,
            -0.012046813964843747,
            -0.006858003303144385,
            -0.004508972167968751,
            -0.005070477520818477,
            -0.006889343261718751,
            -0.004305736192877459,
            -0.0012998580932617188,
            0.002721589026752178,
            0.005569458007812498,
            0.010028445926338553,
            0.015533447265625,
            0.029135061196464224,
            0.04071044921875,
            0.05776351888984987,
            0.0731201171875,
            0.09459821287082669,
            0.10833740234375001,
            0.12047731697747123,
            0.12115478515625001,
            0.11724800149395836,
            0.10113525390624999,
            0.07937957454182584,
            0.04510498046875,
            0.014473370676541466,
            -0.011024475097656252,
            -0.027042824990490898,
            -0.04272460937500001,
            -0.0496503113715019,
            -0.057586669921875,
            -0.06486427072793616,
            -0.06958007812499999,
            -0.06772687349813641,
            -0.06390380859375001,
            -0.058800154023448736,
            -0.049804687499999986,
            -0.04034775244108763,
            -0.03619384765625,
            -0.031257370377637594,
            -0.0279998779296875,
            -0.015829994905934383,
            -0.00753021240234375,
            0.0011310682915643287,
            0.00322723388671875,
            0.002359811548565596,
            -0.0008168220520019531,
            -0.002763715947515391,
            0.007152557373046875,
            0.030290219053111654,
            0.045257568359375,
            0.04064970580859212,
            0.0205230712890625,
            0.00856057993909276,
            0.003465652465820313,
            0.007991864059207451,
            0.00689697265625,
            0.00626034122505748,
            0.008255004882812498,
            0.004216466349066479,
            -0.0036468505859374987,
            -0.01499497200015388,
            -0.03143310546875001,
            -0.05208599176713403,
            -0.06970214843749999,
            -0.07034406500938704,
            -0.06433105468750001,
            -0.04145116970796734,
            -0.0187835693359375,
            0.018362187545107433,
            0.09631347656250003,
            0.14108725032965166,
            0.14501953125,
            0.1209571068051329,
            0.08905029296874999,
            0.06116731288890794,
            0.037811279296875,
            0.02725415630112762,
            0.017272949218750003,
            0.006648955011110735,
            -0.005985260009765625,
            -0.018012753106118613,
            -0.03378295898437501,
            -0.04204462959886654,
            0.11956787109375003,
            0.33189407121677295,
            0.12353515624999997,
            -0.11360657452852052,
            -0.08801269531250001,
            -0.05352196961681665,
            -0.03332519531249999,
            -0.032151637981982686,
            -0.029754638671875003,
            -0.021818657306767086,
            -0.020187377929687497,
            -0.02309016369102366,
            -0.0250396728515625,
            -0.02136082294530256,
            -0.0174713134765625,
            -0.009249783797791981,
            -0.004596710205078125,
            0.00028993476581870434,
            0.0010862350463867183,
            0.013470514397940152,
            0.12902832031250003,
            0.15829999206122583,
            0.15600585937499994,
            0.1336962409638961,
            0.12042236328125,
            0.11581960915829007,
            0.11303710937499997,
            0.13136070792624036,
            0.15551757812499997,
            0.1609629718704699,
            0.12432861328124999,
            0.07626729896780261,
            0.031112670898437507,
            -0.006075815867325326,
            -0.03009033203125,
            -0.0405036042036816,
            -0.04739379882812501,
            -0.053005932339736855,
            -0.052642822265625014,
            -0.028178188467033852,
            0.04782104492187499,
            0.06978281509163063,
            0.06329345703125,
            0.04872832018356763,
            0.036102294921875,
            0.030908718074718244,
            0.0289764404296875,
            0.031703299580205006,
            0.024993896484375,
            0.01265512480944944,
            0.0024204254150390634,
            -0.0012269084449512979,
            -0.0012159347534179685,
            0.000728796399896257,
            0.0007152557373046875,
            -0.0010619501871753,
            -0.005611419677734375,
            -0.0027554440782209494,
            -0.000583648681640625,
            0.0012085725344444555,
            -0.0011920928955078125,
            0.0017331169819287094,
            -0.00028753280639648443,
            -0.000451371064182781,
            -0.0038547515869140625,
            0.001291737327386319,
            -0.0009355545043945315,
            0.002922574716716802,
            0.0012302398681640625,
            0.000622279628137081,
            -0.007568359375000002,
            -0.010946354535652798,
            -0.010887145996093748,
            -0.009685128491942634,
            -0.008575439453125,
            -0.006161566547295566,
            -0.0001360177993774413,
            0.010580145420976397,
            0.019485473632812503,
            0.027153710396364942,
            0.030563354492187497,
            0.029739469383195,
            0.01580810546875,
            -0.01157968039651985,
            -0.030456542968749997,
            -0.036209515562219366,
            -0.03936767578125,
            -0.04853223434974442,
            -0.060302734375,
            -0.07301577298554351,
            -0.08099365234374997,
            -0.07472720126675164,
            0.153564453125,
            0.2958784476455155,
            -0.014106750488281248,
            -0.16080205142942755,
            -0.13159179687499997,
            -0.09624538253966995,
            -0.0745849609375,
            -0.06545419785514704,
            -0.058715820312500014,
            -0.04357519088998928,
            -0.026947021484374993,
            -0.013134155714930373,
            -0.009040832519531252,
            -0.005797315092265345,
            0.005619049072265624,
            0.01966623747713874,
            0.03128051757812501,
            0.041009326409829065,
            0.049224853515624986,
            0.05365386001951102,
            0.0657958984375,
            0.08424573870209576,
            0.10736083984375,
            0.12546598763329073,
            0.14013671874999997,
            0.15231567719906874,
            0.1629638671875,
            0.16620576613938876,
            0.1495361328125,
            0.12229173016887361,
            0.083984375,
            0.05057829570000238,
            0.023590087890625003,
            0.004557318296240525,
            -0.008712768554687498,
            -0.012490816122255654,
            -0.007175445556640625,
            -0.004029522031229304,
            -0.0017890930175781246,
            -0.0013794873800553977,
            -0.0031585693359375004,
            -0.003301765412973195,
            -0.0006375312805175781,
            0.0030162557628677106,
            -0.001614570617675782,
            -0.0038843552217985083,
            -0.0054473876953125,
            0.0030381814952406016,
            0.005622863769531252,
            0.007087241348860521,
            0.0030345916748046884,
            0.004183900959986687,
            0.001115798950195312,
            0.001078855285393721,
            -0.00000327825546264643,
            0.00016923636683265672,
            -0.001920700073242187,
            -0.00532985073912039,
            -0.006828308105468752,
            -0.008017180928514098,
            -0.00496673583984375,
            -0.002640796375228767,
            -0.0033607482910156254,
            -0.004097766614672033,
            -0.00429534912109375,
            -0.005658333644760608,
            -0.005496978759765625,
            -0.0048110959197996215,
            -0.0020351409912109375,
            0.0003432549019174605,
            -0.0018043518066406252,
            -0.0037988887134924572,
            -0.004695892333984375,
            -0.005008581049948041,
            -0.002687454223632813,
            0.012289057584476711,
            0.029693603515624997,
            0.04142692145925991,
            0.04147338867187499,
            0.04105897674836965,
            0.03320312499999999,
            0.02178899756028409,
            0.009590148925781252,
            0.004358797661503845,
            -0.004756927490234374,
            -0.014183842089703554,
            -0.023895263671875003,
            -0.025730824284954275,
            -0.039428710937499986,
            -0.0374740643236138,
            0.04705810546875002,
            0.3043990732896189,
            0.25659179687500006,
            -0.09398362284598148,
            -0.10430908203125,
            -0.07837136715851255,
            -0.04397583007812499,
            -0.028134072704492433,
            -0.0268707275390625,
            -0.021870755286770505,
            -0.01666259765625,
            -0.010431615237647102,
            -0.007083892822265625,
            -0.0033947870219402875,
            -0.000988006591796875,
            -0.00023543153882114496,
            0.00013470649719238273,
            -0.0000021231251710553985,
            2.710505431213761e-20,
            0.0015525138141533935,
            0.006175994873046875,
            0.014924914689762587,
            0.022109985351562497,
            0.0282676486623652,
            0.034698486328125,
            0.047773215171572056,
            0.05438232421875003,
            0.05576523484473253,
            0.037353515625,
            0.011955731317624965,
            -0.028518676757812507,
            -0.06555124520999366,
            -0.09478759765625,
            -0.11196698653146876,
            -0.13110351562500003,
            -0.13989701479723696,
            -0.12109374999999999,
            -0.05082504367767194,
            -0.011322021484375,
            0.02188439914590395,
            0.0484619140625,
            0.05429075295943527,
            0.037445068359375,
            0.012819869842730154,
            -0.00707244873046875,
            0.006007140266756684,
            0.016571044921875007,
            0.03373201257141619,
            0.026458740234375,
            0.042645450105920586,
            0.09521484375000001,
            0.20997303603625717,
            0.25732421874999994,
            0.29810921721295963,
            0.37890624999999994,
            0.4665678375197996,
            0.490478515625,
            0.4594802758683269,
            0.37841796875000006,
            0.3162456369304566,
            0.27148437499999994,
            0.23364684819975884,
            0.19689941406249997,
            0.16743345882697971,
            0.13720703125,
            0.11130905140126872,
            0.08679199218749999,
            0.06740701561757437,
            0.04806518554687501,
            0.03104948327035965,
            0.01363372802734375,
            0.0036513434682694324,
            5.960464477539063e-8,
            0.000542515455304694,
            0.0006842613220214845,
            -0.000033452355524080205,
            -2.710505431213761e-20,
            -0.005406262008993151,
            -0.0193634033203125,
            -0.02948643665999067,
            -0.04022216796875001,
            -0.042792828563245656,
            -0.049285888671874986,
            -0.04818280757395656,
            -0.06256103515625,
            -0.08651491880108504,
            -0.09399414062499997,
            -0.018253514010287075,
            0.16821289062500003,
            0.005729563946020839,
            -0.271240234375,
            -0.24472930156226888,
            -0.2030029296875,
            -0.1720265121578721,
            -0.1636962890625,
            -0.16672457777876143,
            -0.15686035156250006,
            -0.12094320189728684,
            -0.06164550781249999,
            -0.031265321101037906,
            -0.006717681884765625,
            0.023590988345772064,
            0.044891357421874986,
            0.04222389953992823,
            0.0384521484375,
            0.03844495067964348,
            0.03955078125000001,
            0.05195746147456373,
            0.1505126953125,
            0.18072150253129457,
            0.187744140625,
            0.17805477178580736,
            0.16662597656250003,
            0.15329022845118467,
            0.1409912109375,
            0.12055866012391762,
            0.08380126953124999,
            0.03868790091266883,
            0.01064300537109375,
            0.0007129725642512866,
            4.235164736271502e-21,
            -0.0001725102701029923,
            -0.00047469139099121115,
            -0.0008134508470661379,
            -0.0005130767822265624,
            -0.0021425359075951007,
            -0.00528717041015625,
            -0.0020946417913000983,
            0.0013685226440429688,
            0.003472949087889796,
            0.0021343231201171875,
            0.002049228340380029,
            0.0007476806640625,
            -0.00035713809252769076,
            -0.00035476684570312484,
            -0.0004394657793090952,
            -0.00007677078247070314,
            0.00009606427379023738,
            0.0011301040649414062,
            0.0009245977823610594,
            -0.0037384033203125,
            -0.006020017118021303,
            -0.009117126464843752,
            -0.011317602246247819,
            -0.019744873046874997,
            -0.02246046373953973,
            -0.0137939453125,
            -0.004356907055988987,
            0.00662994384765625,
            0.020354872302776904,
            0.0287933349609375,
            0.023127087049671393,
            0.0033702850341796875,
            -0.015649541894573276,
            -0.028900146484375,
            -0.03476434924926928,
            -0.030426025390624997,
            -0.02609651221776376,
            -0.03140258789062501,
            -0.033709743078122495,
            -0.02001953125,
            0.13114826994348364,
            0.31201171875,
            0.0059882500239986525,
            -0.1732177734375,
            -0.12205205389680004,
            -0.04721069335937499,
            -0.016295911046835004,
            -0.0070037841796875,
            -0.00470377055655364,
            -0.004306793212890624,
            -0.0032115900414728662,
            0.0005168914794921879,
            0.00609487360771182,
            0.012100219726562498,
            0.020857189244956702,
            0.026550292968750003,
            0.032037791571979564,
            0.026992797851562507,
            0.028151219477939995,
            0.02410888671875,
            0.023021817385833213,
            0.012557983398437502,
            0.009274169325828835,
            0.005962371826171876,
            0.005544947922133547,
            0.0012912750244140627,
            -0.00001883368674030321,
            -1.3234889800848443e-23,
            2.6005335498727392e-8,
            3.618915179919496e-25,
            5.8238392246300106e-9,
            -2.6469779601696886e-23,
            -0.000004242464934132336,
            -0.0002342462539672851,
            -0.0015382546361347356,
            -0.00322723388671875,
            -0.004904448437595252,
            -0.006877899169921876,
            -0.0038546342646875557,
            0.00103759765625,
            0.001231873774524357,
            -0.0020236968994140625,
            -0.0011118975811772976,
            0.0029029846191406254,
            0.004035968270531168,
            0.005207061767578125,
            0.004957321914629346,
            0.0017766952514648438,
            0.0012040051951412284,
            -0.000318765640258789,
            -0.0011961008741588806,
            -0.004409790039062501,
            -0.0011311843017511346,
            0.0012588500976562504,
            0.0034565812298541034,
            -0.003742218017578125,
            -0.005528956947410176,
            -0.0005908012390136713,
            0.00541959894529013,
            0.0037078857421874996,
            -0.0004473187515037975,
            -0.0008029937744140625,
            0.0019457851530850481,
            0.00030350685119628906,
            0.0001680382498070654,
            0.0008091926574707031,
            0.0019485471597804527,
            0.0005660057067871095,
            -0.00009539974624798543,
            -0.002611160278320312,
            -0.002595167195911776,
            -0.005516052246093751,
            -0.006734383228864934,
            -0.0036220550537109375,
            0.005521236073548071,
            0.007564544677734373,
            0.003160523318868382,
            -0.005172729492187502,
            -0.017576064063165506,
            -0.03826904296875,
            -0.06090178328140137,
            -0.07757568359375,
            -0.0921819567200876,
            -0.09692382812499999,
            -0.100290048689803,
            -0.11126708984375,
            -0.12661917858087177,
            -0.12536621093750003,
            0.015590630547707048,
            0.24829101562499994,
            0.03928952729362163,
            -0.20373535156249997,
            -0.17085270875693606,
            -0.12445068359375004,
            -0.09555234967675748,
            -0.08514404296875,
            -0.080436615994418,
            -0.07147216796875001,
            -0.06915554145781536,
            -0.0653076171875,
            -0.05812376234612948,
            -0.041290283203125,
            -0.027089335863342386,
            -0.018234252929687497,
            -0.009437703698084308,
            0.0039825439453125,
            0.017981798857466376,
            0.0233917236328125,
            0.03189726944586192,
            0.047943115234375,
            0.0695322072994201,
            0.0858154296875,
            0.10297330544043083,
            0.11785888671874999,
            0.12824478810684198,
            0.12512207031250003,
            0.11629680930763736,
            0.0953369140625,
            0.06703412692351618,
            0.028274536132812497,
            -0.0048077743914606225,
            -0.03460693359375,
            -0.05638419084608582,
            -0.07012939453125,
            -0.0712821359701341,
            -0.07244873046875,
            -0.07017450105430192,
            -0.06890869140625,
            -0.06319025794200221,
            -0.06176757812499999,
            -0.0559256117293755,
            -0.051361083984375,
            -0.037300242324818725,
            -0.012802124023437498,
            0.009230437218873205,
            0.01488494873046875,
            0.012088894228042848,
            0.006111145019531251,
            -0.00008735397359489996,
            -0.00974273681640625,
            -0.015000527837437121,
            -0.0169219970703125,
            -0.013763022315839982,
            -0.012619018554687497,
            -0.01036032458875589,
            -0.007213592529296873,
            -0.0040039241249172,
            -0.0015993118286132812,
            -0.0007705614098363616,
            -3.3881317890172014e-21,
            0.0001034575634817728,
            0.0010528564453124998,
            0.0008095315284329934,
            -2.3841857910145408e-7,
            0.0012398499075699143,
            -0.0007038116455078126,
            -0.0007813047226572129,
            -0.001852989196777344,
            0.00002496065095021324,
            9.740878893424454e-21,
            -0.00006501556819374382,
            0.016265869140624997,
            0.0590179185256859,
            0.12017822265625,
            0.08633835401824362,
            0.05078125000000001,
            0.048831246641605194,
            0.052459716796875,
            0.03557760285088267,
            0.00543975830078125,
            -0.014886670921317185,
            -0.03442382812500001,
            -0.03473366407866531,
            0.03167724609375,
            0.22546640815074487,
            0.368896484375,
            0.016441217385218556,
            -0.09020996093749999,
            -0.0796600533399761,
            -0.0511474609375,
            -0.028803749148765573,
            -0.022262573242187497,
            -0.015447856046208868,
            -0.007415771484374999,
            0.0005819786166243912,
            0.001860618591308594,
            0.007815066199498536,
            0.013305664062500005,
            0.020147526404012392,
            0.026916503906250007,
            0.039651730534125906,
            0.05084228515625001,
            0.05846807274822904,
            0.06488037109375001,
            0.07944528920106517,
            0.0989990234375,
            0.12206756398850523,
            0.13916015625000003,
            0.15186134454394912,
            0.1568603515625,
            0.1561643231060917,
            0.14428710937499997,
            0.12846859236712135,
            0.10278320312500001,
            0.07121463463737074,
            0.03594970703125,
            0.005878518338444089,
            -0.02032470703125,
            -0.03903080705878246,
            -0.050445556640625,
            -0.0546985989779936,
            -0.055450439453125,
            -0.04954074112575883,
            -0.04772949218749999,
            -0.04484202690064001,
            -0.04589843750000001,
            -0.042815181247903664,
            -0.04098510742187499,
            -0.035023085066012774,
            -0.03616333007812499,
            -0.038348885987439386,
            -0.042785644531250014,
            -0.03784683893478749,
            -0.03500366210937501,
            -0.02987718860545758,
            -0.030136108398437507,
            -0.025478934810853424,
            -0.025466918945312493,
            -0.029332565465834834,
            -0.032073974609375,
            -0.028232864700789687,
            -0.0186309814453125,
            -0.011681781971450145,
            -0.01080322265625,
            -0.006453683677947061,
            -0.004241943359374998,
            0.00035145004264884903,
            0.0037708282470703142,
            0.006064449028210521,
            0.003229141235351563,
            -0.0005514443391183023,
            -0.004360198974609376,
            -0.004417014108128596,
            -0.003622055053710937,
            0.000100572835436853,
            -0.0012302398681640623,
            -0.0006379962766563608,
            0.0007410049438476561,
            0.0004809551511010423,
            0.0021018981933593754,
            0.011781707940064624,
            0.025039672851562497,
            0.031647543607918896,
            0.03155517578125001,
            0.036423865312194235,
            0.03607177734375,
            0.025128236720751963,
            0.008384704589843752,
            -0.0004253503111731992,
            -0.008956909179687498,
            -0.013217769008853141,
            -0.0144195556640625,
            -0.009104552234235464,
            -0.0185089111328125,
            -0.01488221265947812,
            0.055145263671875,
            0.29689280950799435,
            0.34448242187499994,
            -0.05213670323980332,
            -0.1082763671875,
            -0.06636100914852196,
            -0.03173828125,
            -0.018999249759152157,
            -0.023513793945312497,
            -0.02528528412216074,
            -0.025543212890625007,
            -0.01622044954412247,
            -0.005023956298828125,
            0.008981601082126386,
            0.0199127197265625,
            0.029035612701431294,
            0.03222656249999999,
            0.03551659858276872,
            0.04208374023437501,
            0.05260321759778825,
            0.06506347656249999,
            0.08417585769028924,
            0.10614013671875003,
            0.12740497723312935,
            0.146240234375,
            0.16148280814142638,
            0.1715087890625,
            0.17515215786636684,
            0.16296386718749997,
            0.14123941809300533,
            0.11212158203124999,
            0.07662626926704158,
            0.03918457031249999,
            0.007923539847614479,
            -0.010009765625,
            -0.021851520212660035,
            -0.030548095703124997,
            -0.03011694999681359,
            -0.0288238525390625,
            -0.025268063930050585,
            -0.023773193359375003,
            -0.020363480466915267,
            -0.016265869140625,
            -0.012389109883719556,
            -0.00963592529296875,
            -0.002779135183262054,
            0.0012245178222656246,
            0.008373992849698997,
            0.008117675781249998,
            0.00786295486865801,
            0.0025501251220703125,
            0.003892382315557616,
            0.006359100341796875,
            0.008451948832613201,
            0.004116058349609374,
            -0.0004546211552559098,
            -0.0009646415710449218,
            0.0007042949440708859,
            -0.0022468566894531254,
            -0.0027917151127526915,
            -0.0008201599121093752,
            0.0015827173321342383,
            0.0005979537963867188,
            0.002238014375414214,
            0.005397796630859373,
            0.004244886884323176,
            -0.0023002624511718746,
            -0.010015653250400065,
            -0.021057128906250003,
            -0.018761464292790134,
            -0.018722534179687497,
            -0.010223665627194513,
            -0.0080108642578125,
            0.0020484169317098156,
            0.00852203369140625,
            0.009134097211268016,
            0.004669189453124999,
            -0.0013586877771832237,
            -0.0036754608154296875,
            -0.004693519608834891,
            -0.007827758789062498,
            -0.009398887613366278,
            -0.009750366210937502,
            -0.00295450864541702,
            0.00875091552734375,
            0.02633394500503256,
            0.04251098632812499,
            0.04999406272889147,
            0.04876708984375,
            0.04513649901936013,
            0.03372192382812501,
            0.024718778967950068,
            0.011871337890625,
            0.0045591801010986515,
            -0.0007338523864746092,
            -0.004913998571654561,
            -0.014076232910156252,
            -0.03363849854685704,
            -0.04260253906249999,
            -0.026813686740069762,
            0.17858886718749997,
            0.3651665826126365,
            0.06292724609375001,
            -0.12326329331266446,
            -0.08990478515624997,
            -0.046923480353607515,
            -0.024642944335937507,
            -0.024866377081753457,
            -0.027114868164062503,
            -0.025951333135710714,
            -0.021759033203124993,
            -0.012160745285879685,
            -0.0027828216552734375,
            0.0008974193457779845,
            0.0008697509765625,
            -0.00011793084507046096,
            0.0005750656127929689,
            0.005779648699061592,
            0.016906738281250003,
            0.03035619022791296,
            0.04351806640624999,
            0.05666474476259073,
            0.0726318359375,
            0.08844208798624018,
            0.10290527343750001,
            0.11660489231095418,
            0.12341308593749997,
            0.11949869138282793,
            0.10601806640624999,
            0.08426857191203614,
            0.05978393554687499,
            0.0372763136432073,
            0.018997192382812503,
            0.0011766921146104334,
            -0.021255493164062497,
            -0.04030823902962624,
            -0.0472412109375,
            -0.041950099786036914,
            -0.03332519531250001,
            -0.026217877084662097,
            -0.020538330078125003,
            -0.013937925769691521,
            -0.007827758789062502,
            -0.0003621853894701357,
            0.0008373260498046875,
            0.004169550497815692,
            0.005748748779296875,
            0.00799874373562632,
            0.008056640624999998,
            0.004518923202077739,
            -0.0013761520385742185,
            -0.008321101725255341,
            -0.01300811767578125,
            -0.014485844660020751,
            -0.0201873779296875,
            -0.022592201304363308,
            -0.025848388671875003,
            -0.019950939851094966,
            -0.01806640625,
            -0.00961191681558202,
            -0.007091522216796876,
            -0.003975310936395713,
            -0.0036487579345703125,
            -0.0007303665001099161,
            0.001953125,
            0.0015317570599841317,
            0.00014030933380126948,
            -0.0007054912561514152,
            0.00453948974609375,
            0.004773444825984905,
            -0.0007410049438476561,
            -0.004942537271668063,
            -0.003566741943359376,
            -0.0002817046084696122,
            -0.00020480155944824213,
            0.0027743052294587527,
            0.0057830810546875,
            0.008482156744180134,
            0.013923645019531248,
            0.01887132952255084,
            0.015045166015625,
            0.004689930717873945,
            -0.007534027099609373,
            -0.009385727824244097,
            -0.00452423095703125,
            0.0030863481221733474,
            0.004028320312499999,
            -0.004952960200213188,
            -0.00908660888671875,
            0.0039717583992591515,
            0.22753906250000008,
            0.3469905688030356,
            -0.013061523437500003,
            -0.1278478107006592,
            -0.07415771484374999,
            -0.038681328309294175,
            -0.021484375,
            -0.012750238857658433,
            -0.004875183105468748,
            0.007874562118177154,
            0.018402099609375,
            0.029492456221319782,
            0.030548095703124993,
            0.032233923197982246,
            0.0401611328125,
            0.05349817688255857,
            0.06872558593750004,
            0.08052211002329172,
            0.0919189453125,
            0.10076848876419946,
            0.11669921875000001,
            0.13590544022381576,
            0.154541015625,
            0.17279068958360216,
            0.18542480468749997,
            0.19495549167552434,
            0.19299316406249997,
            0.1791434203440542,
            0.15490722656249997,
            0.12345769094576926,
            0.0865478515625,
            0.04725243403456085,
            0.0108642578125,
            -0.014301478280579713,
            -0.03662109375000001,
            -0.046202681298810465,
            -0.04788208007812501,
            -0.04285463839476963,
            -0.04159545898437499,
            -0.03843209574168032,
            -0.034210205078125,
            -0.024827041821203235,
            -0.024963378906249997,
            -0.020002287450791266,
            -0.019500732421874997,
            -0.01259891320272791,
            -0.012023925781249998,
            -0.011873666628241752,
            -0.01052093505859375,
            -0.01061360981615812,
            -0.013000488281249998,
            -0.013870003597303292,
            -0.0132598876953125,
            -0.012384156829939744,
            -0.01401519775390625,
            -0.011195176563080946,
            -0.006328582763671874,
            -0.00628185793571792,
            -0.005874633789062502,
            -0.005126045774656095,
            0.0004105567932128906,
            0.00048156208180860134,
            -0.0015525817871093752,
            -0.0036798997578898914,
            -0.002412796020507812,
            0.002777084754285311,
            0.006057739257812501,
            0.010776872246887685,
            0.012718200683593753,
            0.01350586903976545,
            0.0126953125,
            0.02221032449359091,
            0.03717041015624999,
            0.04941911825476365,
            0.053741455078125,
            0.05458928525267674,
            0.050750732421875,
            0.0369242596863933,
            0.013168334960937497,
            -0.007835104386781599,
            -0.016159057617187497,
            -0.018775789524465583,
            -0.023651123046875003,
            -0.024985780205266664,
            -0.0323486328125,
            -0.02303675214104762,
            0.024093627929687507,
            0.2321244511720602,
            0.267822265625,
            -0.07335782758429596,
            -0.10906982421874997,
            -0.0663926682956665,
            -0.025955200195312503,
            -0.007969428840218742,
            -0.006496429443359375,
            0.0005519609616280529,
            0.008941650390625,
            0.01474369744456357,
            0.014564514160156253,
            0.014533355562314025,
            0.018951416015625,
            0.024339442273724077,
            0.03204345703125001,
            0.04106048251201482,
            0.0523681640625,
            0.05907040725989243,
            0.06921386718749999,
            0.08227304145165219,
            0.1005859375,
            0.11725197627268642,
            0.13256835937499994,
            0.13982207706299002,
            0.14416503906249997,
            0.14355336586455994,
            0.13208007812500003,
            0.10413863220940653,
            0.0653076171875,
            0.030303583830788555,
            0.007381439208984376,
            -0.0001859006210226037,
            -8.470329472543003e-22,
            4.0929689538975655e-7,
            -2.117582368135751e-21,
            -0.00031456878901461616,
            -0.0014400482177734375,
            -0.0036614703097905144,
            -0.0030670166015625,
            0.0011550144757416945,
            0.0019102096557617188,
            0.0003713895200905186,
            -0.0011482238769531248,
            0.0008793390268298041,
            0.0016164779663085946,
            0.0013434830656468256,
            -0.00013434886932373041,
            0.00020797526170363308,
            -0.00031065940856933605,
            0.0019204510041654567,
            -0.0001595020294189452,
            0.0005156470867962279,
            -0.000022590160369873088,
            -0.00012257999777687338,
            -0.0020141601562499996,
            -0.00016502297866372487,
            -0.0012025833129882808,
            0.0015040868222825198,
            0.0005793571472167969,
            0.0006350504170074625,
            -0.003173828125000001,
            -0.0011617734338384771,
            -0.0007853507995605471,
            -0.002020115613786877,
            -0.005199432373046876,
            -0.006446689008921504,
            -0.008964538574218748,
            -0.015327122035682804,
            -0.019287109375,
            -0.019918804232105587,
            -0.017486572265624997,
            -0.010537318753847941,
            0.004730224609374998,
            0.02191197776872738,
            0.0304107666015625,
            0.028342631299116013,
            0.025558471679687493,
            0.01591766839637641,
            0.0020751953125,
            -0.016241248886496924,
            -0.026596069335937503,
            -0.0337618058713322,
            -0.040374755859375014,
            -0.0469394725711546,
            -0.057373046874999986,
            -0.05738887951699507,
            -0.04864501953125,
            0.15338445258991085,
            0.361572265625,
            0.0715977499272944,
            -0.12268066406249999,
            -0.10078400489287126,
            -0.06732177734375,
            -0.04032163895844482,
            -0.029907226562500003,
            -0.025481240386897395,
            -0.016387939453125,
            -0.008863737370429883,
            0.001798629760742188,
            0.013368001577020859,
            0.023315429687499997,
            0.03012952062549056,
            0.03851318359375,
            0.05476080659474075,
            0.07122802734375,
            0.0859306447941063,
            0.09613037109375,
            0.1120027469925021,
            0.12292480468749999,
            0.13956362589841584,
            0.15405273437500003,
            0.17054648243989282,
            0.17846679687499997,
            0.18312293136897662,
            0.17761230468750006,
            0.16065213415516694,
            0.13232421874999994,
            0.10099201109087336,
            0.06768798828124999,
            0.031899963499631624,
            0.004154205322265626,
            -0.006376223246377658,
            -0.0100250244140625,
            -0.010540826193843196,
            -0.01351165771484375,
            -0.012588321304818665,
            -0.013183593749999998,
            -0.015343231063777591,
            -0.014160156250000002,
            -0.01118415518317273,
            -0.00855255126953125,
            -0.004573613615258093,
            -0.007770538330078127,
            -0.0064609325850371584,
            -0.007534027099609375,
            -0.0021431053328790734,
            -0.0014133453369140623,
            -0.0012692568426527489,
            -0.004749298095703126,
            -0.008652925720280888,
            -0.0142974853515625,
            -0.00956688063408798,
            -0.005916595458984373,
            0.0010238702969700073,
            -0.0027637481689453134,
            -0.0036854566341996786,
            -0.007434844970703124,
            -0.009504865852308947,
            -0.009277343750000002,
            -0.007217441822070903,
            0.000049769878387450955,
            0.006931284598744618,
            0.00933074951171875,
            0.005575526242638066,
            0.0009446144104003906,
            0.0021405986901857554,
            0.0035037994384765625,
            0.0011150507600953409,
            -0.0010337829589843748,
            0.00044298215868157235,
            0.0004291534423828125,
            0.0013755544223041745,
            0.00145721435546875,
            0.007282633233368973,
            0.00893402099609375,
            0.00990062972945616,
            0.0138702392578125,
            0.02419347465332571,
            0.037811279296875,
            0.04389657740103022,
            0.047027587890625,
            0.05070827545248138,
            0.048583984375,
            0.03510581824462543,
            0.01325225830078125,
            -0.0014083424932644127,
            -0.013824462890624997,
            -0.019440767674523286,
            -0.0238037109375,
            -0.03312313517041322,
            -0.0438232421875,
            -0.04430644841422683,
            0.15551757812500003,
            0.38406116878988145,
            0.09338378906249997,
            -0.12089728171933994,
            -0.09362792968749999,
            -0.05600925158996796,
            -0.030807495117187503,
            -0.019669673350566744,
            -0.014732360839843752,
            -0.00963623338258372,
            -0.00861358642578125,
            -0.0010417323150871845,
            0.008911132812500003,
            0.019548395129030893,
            0.025772094726562507,
            0.033860358840177775,
            0.04083251953125,
            0.04926533607634243,
            0.061676025390625,
            0.07538388658510281,
            0.091796875,
            0.10971254998872557,
            0.121826171875,
            0.13546063446620862,
            0.1495361328125,
            0.16302527909200604,
            0.16650390624999997,
            0.16736718573518033,
            0.1575927734375,
            0.13665824961328785,
            0.103271484375,
            0.06898956812081969,
            0.034271240234375,
            0.00609752444204834,
            -0.014518737792968748,
            -0.025698885791253144,
            -0.03210449218749999,
            -0.03119139552572356,
            -0.0297393798828125,
            -0.023492304488256094,
            -0.016204833984374997,
            -0.010649281869740257,
            -0.013938903808593748,
            -0.01777611730284751,
            -0.021881103515625,
            -0.018261714270296863,
            -0.014846801757812503,
            -0.00738009536184898,
            -0.0024566650390625,
            -0.003332769218059273,
            -0.008193969726562502,
            -0.01364660705045886,
            -0.01116180419921875,
            -0.00750444676951249,
            -0.0035877227783203125,
            -0.0019275814966656225,
            -0.004325866699218751,
            -0.006246427268535213,
            -0.006534576416015625,
            -0.008165780414925987,
            -0.009948730468749997,
            -0.007550333484179829,
            -0.0057830810546875,
            -0.0021727147832294484,
            -0.000537872314453125,
            0.004455432794682641,
            0.006778717041015627,
            0.010545435275671571,
            0.01024627685546875,
            0.012600740637714169,
            0.012001037597656248,
            0.014267290162855531,
            0.013046264648437498,
            0.019599359250793223,
            0.026473999023437507,
            0.0369837079047914,
            0.0357666015625,
            0.025803707402230387,
            0.006511688232421875,
            -0.00735568893958862,
            -0.021636962890625003,
            -0.026115583351331545,
            -0.026275634765625003,
            -0.026254727452814847,
            -0.03863525390624999,
            -0.03433228524467932,
            0.05450439453125,
            0.27209811972499776,
            0.2110595703125,
            -0.11418340068298073,
            -0.11682128906249999,
            -0.06690855453379062,
            -0.0261688232421875,
            -0.005710109486560354,
            -0.0015325546264648438,
            0.005845260930574062,
            0.008560180664062502,
            0.009878347468982734,
            0.009963989257812502,
            0.015474694169935995,
            0.025604248046875,
            0.03756745551644061,
            0.048828124999999986,
            0.057465618616381164,
            0.0633544921875,
            0.07389647408387341,
            0.08477783203125001,
            0.10082323474517423,
            0.119140625,
            0.14524575498251888,
            0.16723632812499997,
            0.17947495039580463,
            0.18151855468750008,
            0.17985903052627966,
            0.170166015625,
            0.15097256869463427,
            0.1151123046875,
            0.07738734295660973,
            0.03900146484375,
            0.009135165678008853,
            -0.0178070068359375,
            -0.03480798947809934,
            -0.043212890625,
            -0.0454647693564,
            -0.04281616210937501,
            -0.0373693456092465,
            -0.041320800781249986,
            -0.044919109646810444,
            -0.05126953125000001,
            -0.05167271761065129,
            -0.055450439453125,
            -0.053612045075801966,
            -0.054046630859375014,
            -0.060250562303733185,
            -0.06420898437499999,
            -0.06551276840802604,
            -0.06542968749999999,
            -0.06772828533416828,
            -0.07012939453125,
            -0.07020626928236089,
            -0.07440185546875,
            -0.07396394320021922,
            -0.0750732421875,
            -0.07251817748985462,
            -0.07226562500000003,
            -0.05869403559046143,
            -0.03622436523437499,
            -0.01321646279166331,
            0.013130187988281248,
            0.07876354727071078,
            0.23559570312500003,
            0.27593603840493175,
            0.26098632812499994,
            0.210912201268766,
            0.17480468749999997,
            0.14890490121796593,
            0.12744140625,
            0.10981559866615379,
            0.09991455078125001,
            0.09453299993379168,
            0.09747314453124999,
            0.1005141289556237,
            0.09442138671875001,
            0.08814028512014649,
            0.08111572265625,
            0.07936579415583467,
            0.06457519531250001,
            0.04984066402397693,
            0.025543212890625,
            0.005887551720146166,
            -0.011169433593749997,
            -0.018853537796956872,
            -0.030120849609374997,
            -0.04444220749657373,
            -0.07232666015624999,
            -0.0811227256466835,
            0.020736694335937497,
            0.25631474073059635,
            0.15283203125,
            -0.1532246723771454,
            -0.1444091796875,
            -0.10887813977351689,
            -0.07800292968750003,
            -0.06093709863859786,
            -0.055450439453124986,
            -0.04751519856763219,
            -0.043731689453124986,
            -0.03207006368921645,
            -0.020339965820312503,
            -0.012902023876346966,
            -0.0084991455078125,
            -0.0027887602482359573,
            0.008613586425781248,
            0.018541000759629844,
            0.029846191406250007,
            0.041816021343567086,
            0.05462646484375,
            0.06800483763567136,
            0.08276367187500001,
            0.09978342862378885,
            0.116455078125,
            0.13379253356521026,
            0.14331054687499997,
            0.14088284145274996,
            0.13037109375,
            0.1141234553288748,
            0.08837890625,
            0.053136287799249136,
            0.02117919921875,
            0.003159816775557664,
            -0.007446289062499997,
            -0.01772467840876486,
            -0.0234832763671875,
            -0.023957475773196547,
            -0.024353027343749997,
            -0.027892901774948853,
            -0.031158447265625003,
            -0.027017353885548833,
            -0.024765014648437493,
            -0.014306061052178007,
            0.0066680908203125,
            0.03006616128099363,
            0.03973388671875,
            0.03692167010544528,
            0.027008056640625,
            0.017466627565986815,
            0.01186370849609375,
            0.013147564891929562,
            0.012313842773437503,
            0.006898790884468805,
            0.0015811920166015625,
            -0.0018314729178771213,
            -0.004817962646484374,
            -0.006078981402680527,
            -0.006500244140625,
            -0.00512927347808707,
            -0.0060577392578125,
            -0.009434877212387381,
            -0.012451171875000003,
            -0.013779680294431522,
            -0.013641357421875,
            -0.011919378315493225,
            -0.009971618652343748,
            -0.008625655646806335,
            -0.016113281250000007,
            -0.011712119310736962,
            0.0000582933425903316,
            0.012054620122273766,
            0.006656646728515627,
            0.001995692778176127,
            -0.00011307001113891607,
            0.0016931335104756323,
            0.001983642578125,
            0.0002704767464366242,
            -0.0014333724975585938,
            0.006077116827986041,
            0.0279998779296875,
            0.046436505709826256,
            0.059204101562499986,
            0.06380244583886316,
            0.06591796875,
            0.06026388004953727,
            0.05419921875000001,
            0.042186056986299875,
            0.020965576171875,
            -0.0025282783363833944,
            -0.01934814453125,
            -0.02631849275377957,
            -0.032440185546875,
            -0.033958269097592905,
            -0.04461669921874999,
            -0.051814129440574196,
            -0.0548095703125,
            0.13778248099021223,
            0.34204101562499994,
            0.0646000480533464,
            -0.1356201171875,
            -0.111259540420583,
            -0.076904296875,
            -0.05359742470069808,
            -0.053466796875,
            -0.04644328804209777,
            -0.03549194335937501,
            -0.024667141463121725,
            -0.0233612060546875,
            -0.023751233454002263,
            -0.028152465820312497,
            -0.01830092182285159,
            -0.0050201416015625,
            0.012782985613701413,
            0.018157958984374997,
            0.025675959816436566,
            0.03411865234375,
            0.04792281529504692,
            0.059906005859375014,
            0.07072819699152894,
            0.08282470703124999,
            0.09686375302369499,
            0.10552978515625001,
            0.11094214070868394,
            0.1124267578125,
            0.10540991881100722,
            0.08435058593749999,
            0.057017851059398536,
            0.027740478515625,
            0.008789872776902465,
            -0.002782821655273437,
            -0.004196420135671128,
            -0.007541656494140624,
            -0.003879814453828408,
            -0.00463104248046875,
            -0.0021498511748553268,
            -0.0009918212890625,
            0.0006908283051839152,
            0.00022530555725097667,
            0.0005437323068969011,
            0.0003283023834228516,
            -0.0037687880754750723,
            -0.0012874603271484375,
            0.007524509691167494,
            0.0123291015625,
            0.007439072038152178,
            0.0023250579833984375,
            0.003466292435972094,
            0.0025959014892578125,
            0.0015882583458343698,
            -0.004116058349609374,
            -0.004289707493887443,
            -0.0005140304565429685,
            0.0043596785515478895,
            0.00482940673828125,
            0.00023065652145070774,
            -0.0010108947753906252,
            0.000023172852319731856,
            0.000709056854248047,
            0.00027572744218604,
            -0.00031256675720214833,
            -0.0029212314924376348,
            -0.0035572052001953134,
            -0.004874426899783305,
            0.0034351348876953125,
            0.0042534902580838325,
            0.004703521728515624,
            -0.0006453962443428219,
            -0.002500534057617188,
            -0.0041660865694154395,
            -0.00786590576171875,
            -0.011793765482970023,
            -0.018920898437500003,
            -0.017278299921313595,
            -0.0186614990234375,
            -0.014374020502304364,
            -0.01345062255859375,
            -0.009125970545858907,
            -0.0030059814453124996,
            0.008156447672058066,
            0.0157928466796875,
            0.026368241487692283,
            0.029769897460937507,
            0.030384721678911694,
            0.016967773437499997,
            0.0024299796309624156,
            -0.00998687744140625,
            -0.01048107595432319,
            -0.0117645263671875,
            -0.017894688232638567,
            -0.024734497070312503,
            -0.0341422986445937,
            -0.029693603515624993,
            0.051882247678441865,
            0.30224609375,
            0.30666681474524493,
            -0.03982543945312499,
            -0.09691612532401274,
            -0.063720703125,
            -0.026894750438044995,
            -0.013137817382812497,
            -0.014280925935605001,
            -0.014762878417968752,
            -0.014319616141882574,
            -0.017959594726562503,
            -0.014163354223868272,
            -0.003528594970703126,
            0.007340320125554997,
            0.01551055908203125,
            0.02687007212958462,
            0.037231445312499986,
            0.04625194840949316,
            0.05377197265625,
            0.06640830527502395,
            0.0767822265625,
            0.09175588208140838,
            0.113037109375,
            0.13171234024910367,
            0.14392089843750006,
            0.15397089817549198,
            0.1607666015625,
            0.1630869949525701,
            0.14404296875000003,
            0.11738112090943412,
            0.08343505859374999,
            0.0569151824372938,
            0.029525756835937497,
            0.0057415695801560605,
            -0.00815582275390625,
            -0.018386524897595364,
            -0.021362304687500003,
            -0.018246947101243294,
            -0.0136260986328125,
            -0.01079779571369028,
            -0.005447387695312499,
            0.005507797353378466,
            0.010009765625000003,
            0.008907240186878062,
            0.00247955322265625,
            0.002653900791439563,
            0.0028762817382812504,
            0.007169063647854621,
            0.00859832763671875,
            0.009975796497194959,
            0.006954193115234375,
            -0.0007541165170845148,
            -0.007106781005859375,
            -0.006782036331511198,
            -0.006198883056640623,
            -0.003912781709141739,
            -0.003862380981445311,
            -0.00033942291022717627,
            -0.0032825469970703134,
            -0.004422108198154691,
            -0.00830841064453125,
            -0.012128555266513298,
            -0.015098571777343753,
            -0.015106791749031082,
            -0.012908935546874998,
            -0.010596218251871868,
            -0.013069152832031248,
            -0.012736046190728097,
            -0.014862060546875,
            -0.016938499084574385,
            -0.024673461914062503,
            -0.02134558636361194,
            -0.015930175781249997,
            -0.010312776577216314,
            -0.017166137695312507,
            -0.01958370359250289,
            -0.0186920166015625,
            -0.015040519105950235,
            -0.014663696289062502,
            -0.012265957168825618,
            -0.00901031494140625,
            -0.0012332049417401478,
            0.0026264190673828116,
            0.006838022772148797,
            0.007076263427734376,
            0.0047042704425721655,
            0.0020923614501953142,
            0.015455162922040602,
            0.08978271484374999,
            0.0804720054988462,
            0.04714965820312499,
            0.02498074836564642,
            0.010772705078125,
            -0.006520146540410983,
            -0.005130767822265626,
            0.03174502584712885,
            0.255126953125,
            0.3560648727052519,
            0.0081329345703125,
            -0.09967705208528507,
            -0.07757568359375,
            -0.04007981603903632,
            -0.02337646484375,
            -0.024109087927099992,
            -0.019821166992187497,
            -0.011584773703677486,
            -0.007835388183593748,
            -0.00545210183674477,
            0.00027012825012207075,
            0.011153968163931143,
            0.021896362304687497,
            0.03296065892883938,
            0.04052734375,
            0.05569739081813713,
            0.0699462890625,
            0.08480302558047283,
            0.09661865234374999,
            0.1128605752461767,
            0.12695312499999997,
            0.14277727597057657,
            0.1593017578125,
            0.1736487021323871,
            0.17504882812499997,
            0.16992517315115171,
            0.15563964843750003,
            0.1248438685610021,
            0.08239746093750001,
            0.044798558995374195,
            0.017333984375,
            -0.0024233755537659027,
            -0.017196655273437497,
            -0.028949775561277962,
            -0.03588867187499999,
            -0.038220928840119156,
            -0.037750244140625,
            -0.03324004765911012,
            -0.0305023193359375,
            -0.02638808464121924,
            -0.022491455078125,
            -0.020544835550028156,
            -0.014663696289062498,
            -0.008089861873135047,
            -0.003322601318359375,
            -0.005286346430423388,
            -0.005012512207031251,
            -0.003598647008872374,
            -0.0022144317626953125,
            -0.002951408919410141,
            -0.0086212158203125,
            -0.017124239292736435,
            -0.019180297851562497,
            -0.012357180433853266,
            -0.0080108642578125,
            -0.004802536296832229,
            -0.0031547546386718746,
            -0.006141437509793809,
            -0.010650634765625002,
            -0.008585029061960594,
            -0.006145477294921877,
            -0.002980567971176614,
            -0.001563072204589844,
            0.0020264941000179082,
            0.0002734661102294922,
            0.0007043250688183862,
            0.0012331008911132808,
            0.006239960764798267,
            0.01392364501953125,
            0.02107512106939548,
            0.027465820312500003,
            0.030534109082151307,
            0.03463745117187501,
            0.030270903818686044,
            0.019592285156249993,
            0.004793318179850714,
            -0.00011909008026123046,
            -0.0017730856548368059,
            -0.00409698486328125,
            -0.012738301613768103,
            -0.0252838134765625,
            -0.03524437560503294,
            -0.03292846679687499,
            0.10749273959743123,
            0.31005859374999994,
            0.06671399983580675,
            -0.1385498046875,
            -0.10457177099866581,
            -0.046417236328125,
            -0.0187863083735661,
            -0.012893676757812503,
            -0.013816984297755299,
            -0.014953613281250002,
            -0.0182768287286377,
            -0.016174316406250003,
            -0.00834475911990888,
            -0.00160980224609375,
            0.001144110476862428,
            0.005168914794921875,
            0.011263226338459675,
            0.011695861816406247,
            0.019542233231339978,
            0.034454345703125,
            0.054376146469143356,
            0.06683349609375,
            0.09126514376070588,
            0.11456298828125,
            0.13437687283388214,
            0.14208984375,
            0.15146461787003532,
            0.15270996093750003,
            0.1446623966833655,
            0.12060546874999999,
            0.09364255641970287,
            0.058258056640625,
            0.029785462116523106,
            0.0032749176025390634,
            -0.012045423154947225,
            -0.021331787109374997,
            -0.018811398042329567,
            -0.0163116455078125,
            -0.012828604220815203,
            -0.016876220703125,
            -0.011716722380543686,
            -0.007663726806640625,
            -0.004216946967652511,
            -0.004947662353515626,
            -0.0023664116105082816,
            0.0019159317016601565,
            0.00017794453183128618,
            -0.008148193359375,
            -0.008328338037354282,
            -0.0057373046875,
            -0.008273676877054463,
            -0.016006469726562497,
            -0.01745024201206892,
            -0.0155181884765625,
            -0.014066909913131118,
            -0.018249511718749997,
            -0.019075227340296746,
            -0.015945434570312497,
            -0.010292104110547012,
            -0.00669097900390625,
            -0.009009878068280961,
            -0.011695861816406252,
            -0.008335215744846873,
            -0.001232147216796875,
            0.0037056386473668227,
            0.0030918121337890634,
            0.007226974455360023,
            0.012611389160156248,
            0.02108165025813439,
            0.0233154296875,
            0.030621163612006994,
            0.03631591796875,
            0.04429187243089818,
            0.04821777343749999,
            0.05620557617946709,
            0.05438232421874999,
            0.0452730122100958,
            0.029190063476562503,
            0.021234215422888923,
            0.01377105712890625,
            0.007642467449462882,
            -0.0039215087890625,
            -0.014293068153390024,
            -0.02801513671875,
            -0.022453291867883897,
            0.0031337738037109384,
            0.1908364688462288,
            0.281982421875,
            -0.04485732523422722,
            -0.11865234375,
            -0.0832564581026977,
            -0.03890991210937499,
            -0.015392919353867914,
            -0.019409179687500003,
            -0.027826036262851236,
            -0.03518676757812499,
            -0.03305298112702622,
            -0.0306854248046875,
            -0.024350036339573024,
            -0.013122558593749998,
            -0.00035380361891068125,
            0.005828857421874999,
            0.007762777099079083,
            0.012626647949218748,
            0.023711634774716064,
            0.03674316406250001,
            0.05163765799833258,
            0.07110595703124999,
            0.09154713117334343,
            0.106201171875,
            0.11564614130283506,
            0.12109375000000001,
            0.1275002032702216,
            0.12695312500000003,
            0.12329421547048865,
            0.10528564453125,
            0.07414734199415153,
            0.038818359375,
            0.012087241005710654,
            0.0015439987182617188,
            0.00005227852198691491,
            -0.0004677772521972656,
            -0.0013067278506303077,
            -0.004730224609375001,
            -0.003103050656083761,
            -0.005924224853515625,
            -0.00257114986029802,
            -0.006706237792968752,
            -0.0027953486968698375,
            -0.0017290115356445315,
            0.009011686304195727,
            0.0099334716796875,
            0.006690301327048868,
            -0.0006246566772460939,
            0.0006099437357154509,
            0.0019102096557617188,
            0.0014671010332618902,
            -0.0010452270507812498,
            0.00007299570540376761,
            -0.00004780292510986323,
            0.00043033803887815726,
            -0.0006489753723144531,
            -0.0027083073954079035,
            -0.0015697479248046877,
            -0.0032348520293076,
            -0.002122879028320312,
            0.00204355622239859,
            0.004329681396484375,
            0.004121213296908215,
            -0.002809524536132812,
            -0.003719735450119791,
            -0.000610828399658203,
            0.0011619575990890769,
            0.0032844543457031246,
            -0.00031242714926723626,
            -0.001613616943359375,
            -0.008506905432352226,
            -0.014816284179687498,
            -0.02068864302814801,
            -0.0238189697265625,
            -0.02017824894674282,
            -0.013023376464843752,
            -0.003388418216622616,
            0.011634826660156253,
            0.023753598198854566,
            0.027420043945312503,
            0.022434702307789444,
            0.015731811523437497,
            -0.0005903317641647052,
            -0.021820068359375003,
            -0.03901090733133073,
            -0.044921875000000014,
            -0.04889874286359544,
            -0.05032348632812499,
            -0.05921097207297073,
            -0.07366943359374999,
            -0.08027465160043602,
            -0.07202148437500001,
            0.1489115528579714,
            0.29443359375,
            -0.020572916206155634,
            -0.16491699218750003,
            -0.13759647691989332,
            -0.09967041015625,
            -0.07719482870200682,
            -0.07012939453124999,
            -0.06065451914785171,
            -0.05090332031249999,
            -0.04001164687901763,
            -0.03546142578125,
            -0.02496167865271247,
            -0.018875122070312503,
            -0.010476615122826726,
            -0.005199432373046875,
            0.0029200504077156447,
            0.011756896972656252,
            0.02578278457909219,
            0.042938232421875,
            0.06448192626191118,
            0.08404541015624999,
            0.09953476427255674,
            0.10772705078124999,
            0.11772914201653406,
            0.13159179687500003,
            0.14860572239542816,
            0.156494140625,
            0.15291101019603087,
            0.134033203125,
            0.10267757096954808,
            0.06536865234375001,
            0.03237900249876467,
            0.014450073242187502,
            0.004029072058203944,
            -0.0036163330078125013,
            -0.012010619141133888,
            -0.00795745849609375,
            -0.0055938026922799,
            -0.006149291992187499,
            -0.007880900074811096,
            -0.006198883056640624,
            -0.00596642943568316,
            -0.006393432617187502,
            -0.0015807064725843467,
            -0.003520965576171875,
            -0.0023671200423196303,
            0.000035703182220458984,
            0.00949087735554254,
            0.008285522460937502,
            0.0060954899599137535,
            0.000211477279663086,
            -0.0014054249607264497,
            -0.0040130615234375,
            -0.007687259903291034,
            -0.008796691894531252,
            -0.005931515312681874,
            0.004055023193359375,
            0.005311958321913239,
            0.006649017333984373,
            0.008686833214743403,
            0.014900207519531247,
            0.01679784269579121
};


}
