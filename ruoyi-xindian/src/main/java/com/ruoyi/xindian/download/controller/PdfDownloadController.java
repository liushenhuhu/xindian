package com.ruoyi.xindian.download.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.xindian.download.util.FileUtils;
import com.ruoyi.xindian.pdfPath.domain.PdfPath;
import com.ruoyi.xindian.pdfPath.service.IPdfPathService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/pdf")
@CrossOrigin
@Log4j
public class PdfDownloadController extends BaseController {


    @Autowired
    private IPdfPathService pdfPathService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(@RequestParam("file") MultipartFile file) {
        if (Objects.isNull(file) || file.isEmpty()) {
            //判断非空
            return "文件为空，请重新上传";
        }
        try {
            byte[] bytes = file.getBytes();
            //要存入本地的地址放到path里面
            Path path = Paths.get(FileUtils.UPLOAD_FOLDER + "/");
            //如果没有files文件夹，则创建
            if (!Files.isWritable(path)) {
                Files.createDirectories(path);
            }
            String extension = FileUtils.getFileExtension(file);  //获取文件后缀
            FileUtils.getFileByBytes(bytes, FileUtils.UPLOAD_FOLDER, "上传文件" + extension);
            return "上传成功";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "上传失败";
    }

    @RequestMapping(value = "/file/uploadPdf", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult upLoadPlanFile(@RequestParam("file") MultipartFile uploadFile) {
        long startTime = System.currentTimeMillis();
        String pathForDb = null;
        String type = null;//文件类型
        String fileName;
        String pdfPath;
        String originalFilename = uploadFile.getOriginalFilename();

        String[] s = originalFilename.split("_");
        String pathName = s[0];
        String[] split = s[1].split("\\.");
        String pId = split[0];
        //这是本地存入的格式，上传到服务器的话，格式类似于，"/root/images/pc/"
        String basePath = "D:/pdf/" + pathName;
//        String basePath = "/home/chenpeng/workspace/system/xindian/data/pdf/" + pathName;


        long size;
        try {
            if (uploadFile.isEmpty()) {
                return AjaxResult.error("文件不能为空");
            }
            size = uploadFile.getSize();
            fileName = uploadFile.getOriginalFilename();// 文件原名称
            // 判断文件类型
            type = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length())
                    : null;
            if (type == null) {
                // 判断文件类型是否为
                return AjaxResult.error("文件类型不能为空");
            }
//            fileName = UUID.randomUUID().toString() + "." + type;
            File targetFile = new File(basePath, fileName);

            pdfPath = basePath + "/" + fileName;

            // 保存到数据库的路径
            pathForDb = "pdf/" + fileName;
            log.debug("文件数据库保存路径:" + pathForDb);

            if (!targetFile.exists()) {
                new File(basePath).mkdirs();
            }

            // 转存文件到指定的路径
            uploadFile.transferTo(targetFile);

            if (targetFile.exists()) {
                log.debug("文件成功上传到指定目录下");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("PDF上传失败");
        }
        long endTime = System.currentTimeMillis();
        log.debug("上传用时：" + String.valueOf(endTime - startTime) + "ms");
        Map<String, Object> map = new HashMap<>();
        map.put("url", pathForDb);
        map.put("size", size);

        PdfPath pdfPath1 = new PdfPath();
        pdfPath1.setPdfPath(pdfPath);
        pdfPath1.setpId(pId);
        Date date = new Date(System.currentTimeMillis());
        pdfPath1.setPathTime(date);

        PdfPath pdfPath2 = new PdfPath();
        pdfPath2.setpId(pId);
        List<PdfPath> pdfPaths = pdfPathService.selectPdfPathList(pdfPath2);

        if (null != pdfPaths && pdfPaths.size() > 0) {
            PdfPath pdfPath3 = pdfPaths.get(0);
            pdfPath1.setPathId(pdfPath3.getPathId());
            pdfPathService.updatePdfPath(pdfPath1);
        } else {
            pdfPathService.insertPdfPath(pdfPath1);
        }

        return AjaxResult.success(map);
    }


}
