package com.ruoyi.xindian.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

@Component
public class FileUploadUtils {


    @Value("${uploadImg.url}")
    private String url;


    public String uploadImgUrl(MultipartFile file,String location,String phone){
        LocalDate now = LocalDate.now();
        String time = now.toString();
        String front = saveFile(file,location,phone,time);
        String[] fronts = front.split("uploadPath");
        return url+fronts[1];
    }

    private String saveFile(MultipartFile file, String location, String userId, String time) {


        try {
            String f = file.getOriginalFilename();
            int dotIndex = f.lastIndexOf('.');
            String extension="";
            if (dotIndex > 0 && dotIndex < f.length() - 1) {
                extension = f.substring(dotIndex + 1).toLowerCase();
                // 这里假设你只关心 JPEG 和 PNG 图片格式，如果需要支持更多格式需要自行修改判断条件
                if (extension.equals("jpg") || extension.equals("jpeg")) {
                    extension = ".jpg";
                } else if (extension.equals("png")) {
                    extension = ".png";
                } else {
                    extension = "";
                }
            }
            // Generate a unique file name based on the current time
            String fileName = System.currentTimeMillis() + "-" + location;
//            String mainDir="E:/saveImg/";
            String mainDir="/home/chenpeng/workspace/system/xindian/uploadPath/";
            File dir = new File(mainDir+userId+"/"+time+"/");
            if(!dir.exists()){
                boolean mkdirs = dir.mkdirs();
            }
            Path path = Paths.get( mainDir+userId+"/"+time+"/" + "/" + fileName + extension);
            // Save the file to disk
            Files.write(path, file.getBytes());
            // Return the file path
            return path.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to save file: " + e.getMessage());
        }
    }


}
