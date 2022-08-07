package com.ruoyi.xindian.pdf.service;

import com.ruoyi.xindian.pdf.domain.AdmissionCard;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public interface IPdfService {


    void generatorPdf(AdmissionCard admissionCard, HttpServletResponse response) throws UnsupportedEncodingException, FileNotFoundException;
}
