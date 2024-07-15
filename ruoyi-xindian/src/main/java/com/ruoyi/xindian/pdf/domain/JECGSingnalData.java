package com.ruoyi.xindian.pdf.domain;

import lombok.Data;

@Data
public class JECGSingnalData {
    private String pid;
    private byte[] ecgData;
}
