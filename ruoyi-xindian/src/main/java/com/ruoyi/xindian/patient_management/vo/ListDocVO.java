package com.ruoyi.xindian.patient_management.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ListDocVO {


    private String value;


    private String label;


    private List<DocVO> children = new ArrayList<>();
}
