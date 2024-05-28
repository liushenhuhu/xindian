package com.ruoyi.xindian.pdf.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.ruoyi.common.exception.base.BaseException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

public class CreatePdf {

//
//    public static void createPdf(ByteArrayOutputStream outputStream,
//                                 Map<String, Map<String, BigDecimal>> linePorts
//                                 ) throws DocumentException, IOException {
//        // 创建一个文档（默认大小A4，边距36, 36, 36, 36）
//        Document document = new Document();
//        // 设置文档大小
//        document.setPageSize(PageSize.A4);
//        // 设置边距，单位都是像素，换算大约1厘米=28.33像素
//        document.setMargins(50, 50, 50, 50);
//        // 创建writer，通过writer将文档写入磁盘
//        PdfWriter writer = PdfWriter.getInstance(document, outputStream);
//
//        // 打开文档，只有打开后才能往里面加东西
//        document.open();
//        // 设置作者
//        //document.addAuthor("作者");
//        // 设置创建者
//        //document.addCreator("创建者");
//
//
//        //设置封面图片
//        //Image image = Image.getInstance("src/main/resources/static/萌猫.jpg");
//        //image.setAlignment(Image.ALIGN_LEFT);
//        //image.scaleAbsolute(500, 300);
//        //document.add(image);
//
//        //设置标题
//        //document.add(CreateEchartsPdfUtils.createHead2("生成pdf图表demo", Font.NORMAL, Element.ALIGN_CENTER));
//
//        //document.add(CreateEchartsPdfUtils.createHead3("01  表格", Font.BOLD, Element.ALIGN_LEFT));
//        //第一个表格
//        //oneForm(document, tableValue);
//        //日历图
//        //document.add(CreateEchartsPdfUtils.createHead3("02  日历图", Font.BOLD, Element.ALIGN_LEFT));
//        //CreateEchartsPdfUtils.calendarTwoForm(document, calendar);
//        //折线图
//        document.add(CreateEchartsPdfUtils.createHead3("血压测定折线图", Font.NORMAL, Element.ALIGN_CENTER));
//        JFreeChartUtils.createLinePortImage(document,  linePorts, "", "");
//        //饼状图
//        //document.add(CreateEchartsPdfUtils.createHead3("04  饼状图", Font.BOLD, Element.ALIGN_LEFT));
//        //JFreeChartUtils.createPiePortImage(document,  piePort);
//        //柱状图
//        //document.add(CreateEchartsPdfUtils.createHead3("05  柱状图", Font.BOLD, Element.ALIGN_LEFT));
//        //JFreeChartUtils.createBarChartImage(document,  barChartPort);
//        //堆叠柱状图
//        //document.add(CreateEchartsPdfUtils.createHead3("06  堆叠柱状图", Font.BOLD, Element.ALIGN_LEFT));
//        //JFreeChartUtils.createStackedBarChart(document,  stackedBarChartsPort);
//        //document.newPage();
//        //柱状图和折线图组合图1
//        //document.add(CreateEchartsPdfUtils.createHead3("07  柱状图和折线图组合图1", Font.BOLD, Element.ALIGN_LEFT));
//        //JFreeChartUtils.createBarChartAndLineImage(document,  linePorts);
//        //柱状图和折线图组合图2 双y轴
//        //document.add(CreateEchartsPdfUtils.createHead3("08  柱状图和折线图组合图2", Font.BOLD, Element.ALIGN_LEFT));
//        //JFreeChartUtils.createChart(document,  linePorts);
//
//        document.close();
//        writer.close();
//    }



    /**
     * 根据模板创建生成pdf
     * @param map 模板中的表单数据 key-表单属性值；value-值
     * @param templatePath 模板路径
     * @return 返回生成的pdf文件路径
     */
    public static  byte[] createPdfByTemplate(Map<String,Object> map,String templatePath,String outPath) {
        PdfReader reader;
        ByteArrayOutputStream bos;
        PdfStamper stamper;
        //生成的pdf文件存放地址 要确保文件夹的存在
        //String newPdfPath = "src/main/resources/static/temporary/"+(System.currentTimeMillis())+".pdf";
        try {
            //设置字体是必须要的，不然没法向模板pdf里写值
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            // 读取pdf模板
            reader = new PdfReader(templatePath);
            bos = new ByteArrayOutputStream();
            stamper = new PdfStamper(reader, bos);
            //拿到pdf模板中的表单属性
            AcroFields form = stamper.getAcroFields();
            //设置字体
            form.addSubstitutionFont(bfChinese);
            // 文字类的内容处理
            Map<String, String> datemap = (Map<String, String>) map.get("dataMap");
            form.addSubstitutionFont(bfChinese);
            for (String key : datemap.keySet()) {
                String value = datemap.get(key);
                form.setField(key, value);
            }
            // 如果为false那么生成的PDF文件还能编辑，一定要设为true
            stamper.setFormFlattening(true);
            stamper.close();
            // 将生成的 PDF 转换成二进制数据
            return bos.toByteArray();
        } catch (IOException e) {
            throw new BaseException("生成报告单，请联系管理员");
        } catch (DocumentException e) {
            e.printStackTrace();
            throw new BaseException("生成报告单，请联系管理员");
        }
    }

}
