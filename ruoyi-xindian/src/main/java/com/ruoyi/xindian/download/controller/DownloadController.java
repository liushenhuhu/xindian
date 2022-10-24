package com.ruoyi.xindian.download.controller;


import com.ruoyi.common.core.controller.BaseController;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;


/**
 * @Author: han
 * @Date: 2022-07-24
 */
@RestController
@RequestMapping("/download")
@CrossOrigin
public class DownloadController extends BaseController {

    @GetMapping("/{path}")
    public void download(@PathVariable String path,
                         HttpServletResponse response) {
        File file = new File("C:\\Users\\h\\Desktop\\" + path);
        byte[] buffer = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            //文件是否存在
            if (file.exists()) {
                //设置响应
                response.setContentType("application/octet-stream;charset=UTF-8");
//                response.setContentType("application/pdf;charset=UTF-8");
                response.setCharacterEncoding("UTF-8");
                os = response.getOutputStream();
                bis = new BufferedInputStream(new FileInputStream(file));
                while (bis.read(buffer) != -1) {
                    os.write(buffer);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (os != null) {
                    os.flush();
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @PostMapping("/downPdf")
    public void downPdf(HttpServletResponse response) throws Exception {
        //获取要下载的模板名称
        String fileName = "test.pdf";
        OutputStream os = null;
        response.reset();
        response.setCharacterEncoding("utf-8");
        //通知客服文件的MIME类型
        response.setContentType("application/pdf");

        response.addHeader("Access-Control-Allow-Origin", "*");

        //设置要下载的文件的名称
        response.setHeader("Content-disposition", "attachment;fileName=" + fileName);
        //获取文件的路径
        Resource resource = new ClassPathResource("C:\\Users\\h\\Desktop\\" + fileName);//改为你自己的路径
        File sourceFile = resource.getFile();
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(sourceFile));
            byte[] b = new byte[bis.available() + 1000];
            int i;
            os = response.getOutputStream();   //直接下载导出
            while ((i = bis.read(b)) != -1) {
                os.write(b, 0, i);
            }
            os.flush();
            os.close();
        } catch (Exception e) {
            logger.error("错误信息：", e);
        } finally {
            os.close();
        }
    }

    @RequestMapping(value = "uploadFile", method = RequestMethod.POST)
    public String uploadFile(HttpServletRequest request, HttpServletRequest response, HttpSession session) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartRequest.getFile("file1");//file是form-data中二进制字段对应的name

        approvalFile(multipartFile);

        System.out.println(multipartFile.getSize());
        return "success";
    }


    public void approvalFile(MultipartFile file) {
        OutputStream os = null;
        InputStream inputStream = null;
        String fileName = null;
        try {
            inputStream = file.getInputStream();
            fileName = file.getOriginalFilename();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String path = "F:\\test\\";
            // 2、保存到临时文件
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流保存到本地文件
            File tempFile = new File(path);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }
            os = new FileOutputStream(tempFile.getPath() + File.separator + fileName);
            // 开始读取
            while ((len = inputStream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 完毕，关闭所有链接
            try {
                os.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
