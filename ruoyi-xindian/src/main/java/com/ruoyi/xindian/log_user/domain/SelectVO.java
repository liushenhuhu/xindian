package com.ruoyi.xindian.log_user.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SelectVO {


    private String label;


    private List<OptionVO>  options= new ArrayList<>();
}
