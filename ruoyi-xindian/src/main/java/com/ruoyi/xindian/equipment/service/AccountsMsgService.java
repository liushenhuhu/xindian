package com.ruoyi.xindian.equipment.service;

import com.ruoyi.xindian.equipment.domain.AccountsMsg;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 13401
* @description 针对表【accounts_msg】的数据库操作Service
* @createDate 2023-09-08 14:50:14
*/
public interface AccountsMsgService extends IService<AccountsMsg> {

    List<AccountsMsg> selectByList();

}
