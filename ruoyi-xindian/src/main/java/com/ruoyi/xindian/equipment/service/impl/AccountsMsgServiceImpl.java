package com.ruoyi.xindian.equipment.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.xindian.equipment.domain.AccountsMsg;
import com.ruoyi.xindian.equipment.service.AccountsMsgService;
import com.ruoyi.xindian.equipment.mapper.AccountsMsgMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 13401
* @description 针对表【accounts_msg】的数据库操作Service实现
* @createDate 2023-09-08 14:50:14
*/
@Service
public class AccountsMsgServiceImpl extends ServiceImpl<AccountsMsgMapper, AccountsMsg>
    implements AccountsMsgService{


    @Resource
    private AccountsMsgMapper accountsMsgMapper;

    @Override
    public List<AccountsMsg> selectByList() {
        return accountsMsgMapper.selectList(null);
    }
}




