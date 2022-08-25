/*
package com.ruoyi.xindian.pdf.controller;

import com.ruoyi.xindian.pdf.domain.AdmissionCard;
import com.ruoyi.xindian.pdf.service.IPdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

*/
/**
 * @Author: han
 * @Date: 2022-07-24
 *//*


@RestController
@RequestMapping("/pdf")
public class PdfController {
    @Autowired
    private IPdfService pdfService;

    @Autowired
    private HttpServletResponse response;

    @PostMapping("/download")
    public void pdfDownload(@RequestBody AdmissionCard admissionCard, HttpServletResponse response){
        try {
            pdfService.generatorPdf(admissionCard, response);
        } catch (UnsupportedEncodingException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}
*/
