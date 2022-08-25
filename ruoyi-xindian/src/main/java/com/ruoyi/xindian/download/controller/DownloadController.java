package com.ruoyi.xindian.download.controller;


import com.ruoyi.common.core.controller.BaseController;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
    public void download(@PathVariable String path ,
                         HttpServletResponse response){
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
                while(bis.read(buffer) != -1){
                    os.write(buffer);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(bis != null) {
                    bis.close();
                }
                if(os != null) {
                    os.flush();
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
