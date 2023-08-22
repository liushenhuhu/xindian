package com.ruoyi.xindian.patient_management.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor//有参构造
@NoArgsConstructor//无参构造
public class RolePhone {
    //角色
    private String role;
    //手机号
    private String phone;
}
