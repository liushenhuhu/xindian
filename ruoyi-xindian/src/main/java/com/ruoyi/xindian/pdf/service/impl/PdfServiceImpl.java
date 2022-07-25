package com.ruoyi.xindian.pdf.service.impl;

import com.ruoyi.xindian.pdf.domain.AdmissionCard;
import com.ruoyi.xindian.pdf.service.IPdfService;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: han
 * @Date: 2022-07-24
 */
@Service
@Slf4j
public class PdfServiceImpl implements IPdfService {
    @Override
    public void generatorPdf(AdmissionCard admissionCard, HttpServletResponse response) throws UnsupportedEncodingException {
        // 模板名称
//        String templateName = "准考证-模板.pdf";
        String path = "";

        path = "E:\\java\\xindian\\ruoyi-xindian\\src\\main\\resources\\doc\\table.pdf";
        // 获取操作系统名称，根据系统名称确定模板存放的路径
//        String systemName = System.getProperty("os.name");
//        if(systemName.toUpperCase().startsWith("WIN")){
//            path = "D:/pdf/";
//        }else {
//            path = "/usr/local/pdf/";
//        }
        // 生成导出PDF的文件名称
        String fileName = admissionCard.getName() + "-硕士准考证.pdf";
        fileName = URLEncoder.encode(fileName, "UTF-8");
        // 设置响应头
        response.setContentType("application/force-download");
//        response.setHeader("Content-Disposition",
//                "attachment;fileName=" + fileName);
        response.setHeader("Content-Disposition", "attachment;filename*=utf-8'zh_cn'" + fileName);
        OutputStream out = null;
        ByteArrayOutputStream bos = null;
        PdfStamper stamper = null;
        PdfReader reader = null;
        try {
            // 保存到本地
            // out = new FileOutputStream(fileName);
            // 输出到浏览器端
            out = response.getOutputStream();
            // 读取PDF模板表单
            reader = new PdfReader(path);
            // 字节数组流，用来缓存文件流
            bos = new ByteArrayOutputStream();
            // 根据模板表单生成一个新的PDF
            stamper = new PdfStamper(reader, bos);
            // 获取新生成的PDF表单
            AcroFields form = stamper.getAcroFields();
            // 给表单生成中文字体，这里采用系统字体，不设置的话，中文显示会有问题
            BaseFont font = BaseFont.createFont("C:/WINDOWS/Fonts/SIMSUN.TTC,1", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            form.addSubstitutionFont(font);
            // 装配数据
            Map<String, Object> data = new HashMap<>(5);
            data.put("name", admissionCard.getName());
            data.put("age", admissionCard.getAge());
            data.put("hospital", admissionCard.getHospital());
            data.put("equipment", admissionCard.getEquipment());
            data.put("image", admissionCard.getImage());

            // 遍历data，给pdf表单赋值
            for (String key : data.keySet()) {
                // 图片要单独处理
                if ("image".equals(key)) {
                    int pageNo = form.getFieldPositions(key).get(0).page;
                    Rectangle signRect = form.getFieldPositions(key).get(0).position;
                    float x = signRect.getLeft();
                    float y = signRect.getBottom();
                    String imageUrl = data.get(key).toString();
                    //根据路径或Url读取图片
                    Image image = Image.getInstance(imageUrl);
                    //获取图片页面
                    PdfContentByte under = stamper.getOverContent(pageNo);
                    //图片大小自适应
                    image.scaleToFit(signRect.getWidth(), signRect.getHeight());
                    //添加图片
                    image.setAbsolutePosition(x, y);
                    under.addImage(image);
                }
                // 设置普通文本数据
                else {
                    form.setField(key, data.get(key).toString());
                }
            }
            // 表明该PDF不可修改
            stamper.setFormFlattening(true);
            // 关闭资源
            stamper.close();
            // 将ByteArray字节数组中的流输出到out中（即输出到浏览器）
            Document doc = new Document();
            PdfCopy copy = new PdfCopy(doc, out);
            doc.open();
            PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), 1);
            copy.addPage(importPage);
            doc.close();
            log.info("*****************************PDF导出成功*********************************");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
