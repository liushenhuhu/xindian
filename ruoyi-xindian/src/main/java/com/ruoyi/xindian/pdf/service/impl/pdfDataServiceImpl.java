package com.ruoyi.xindian.pdf.service.impl;

import com.ruoyi.xindian.pdf.domain.JECGSingnalData;
import com.ruoyi.xindian.pdf.mapper.PdfDataMapper;
import com.ruoyi.xindian.pdf.service.IPdfDataService;
import com.ruoyi.xindian.pdfPath.mapper.PdfPathMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class pdfDataServiceImpl implements IPdfDataService {

    @Autowired
    private PdfDataMapper pdfDataMapper;

    @Override
    public JECGSingnalData getJECGSingnalByPid(String pid) {
        return pdfDataMapper.getJECGSingnalByPid(pid);
    }
}
