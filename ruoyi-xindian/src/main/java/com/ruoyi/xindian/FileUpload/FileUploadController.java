package com.ruoyi.xindian.FileUpload;

import com.ruoyi.common.core.domain.AjaxResult;

import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.xindian.util.FileUploadUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/fileUpload")
public class FileUploadController {

    @Resource
    private FileUploadUtils fileUploadUtils;

    /**
     * 通用上传请求（单个）
     */
    @PostMapping("/upload_new")
    public AjaxResult upload_new(MultipartFile file) throws Exception
    {
        try
        {
            // 上传文件路径
//            String filePath = RuoYiConfig.getUploadPath();
//            // 上传并返回新文件名称
//            String fileName = FileUploadUtils.upload(filePath, file);
            String url = fileUploadUtils.uploadImgUrl(file, "image");
            AjaxResult ajax = AjaxResult.success();
            ajax.put("url", url);
            ajax.put("fileName", file.getOriginalFilename());
            ajax.put("newFileName", file.getOriginalFilename());
            ajax.put("originalFilename", file.getOriginalFilename());
            return ajax;
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 通用上传请求（单个）
     */
    @PostMapping("/file")
    public AjaxResult file(MultipartFile file) throws Exception
    {
        try
        {
            // 上传文件路径
//            String filePath = RuoYiConfig.getUploadPath();
//            // 上传并返回新文件名称
//            String fileName = FileUploadUtils.upload(filePath, file);
            String url = fileUploadUtils.uploadFileUrl(file, "file");
            AjaxResult ajax = AjaxResult.success();
            ajax.put("url", url);
            ajax.put("fileName", file.getOriginalFilename());
            ajax.put("newFileName", file.getOriginalFilename());
            ajax.put("originalFilename", file.getOriginalFilename());
            return ajax;
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }
    }
}
