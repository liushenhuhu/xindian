package com.ruoyi.xindian.ano.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.xindian.ano.domain.AnoAdmin;
import com.ruoyi.xindian.ano.service.AnoAdminService;
import com.ruoyi.xindian.ano.mapper.AnoAdminMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 13401
* @description 针对表【ano_admin】的数据库操作Service实现
* @createDate 2023-12-22 11:54:00
*/
@Service
public class AnoAdminServiceImpl extends ServiceImpl<AnoAdminMapper, AnoAdmin>
    implements AnoAdminService{



    @Resource
    private AnoAdminMapper anoAdminMapper;

    @Override
    public List<AnoAdmin> selectAnoAdminList(AnoAdmin anoAdmin) {
        return anoAdminMapper.selectAnoAdminList(anoAdmin);
    }

    @Override
    public List<AnoAdmin> selectAnoAdminListAll(AnoAdmin anoAdmin) {
        return anoAdminMapper.selectAnoAdminListALL(anoAdmin);
    }

    /**
     * 查询审核标注分配
     *
     * @param anoAdminId 审核标注分配主键
     * @return 审核标注分配
     */
    @Override
    public AnoAdmin selectAnoAdminByAnoAdminId(Long anoAdminId)
    {
        return anoAdminMapper.selectAnoAdminByAnoAdminId(anoAdminId);
    }

    /**
     * 新增审核标注分配
     *
     * @param anoAdmin 审核标注分配
     * @return 结果
     */
    @Override
    public int insertAnoAdmin(AnoAdmin anoAdmin)
    {
        return anoAdminMapper.insertAnoAdmin(anoAdmin);
    }

    /**
     * 修改审核标注分配
     *
     * @param anoAdmin 审核标注分配
     * @return 结果
     */
    @Override
    public int updateAnoAdmin(AnoAdmin anoAdmin)
    {
        return anoAdminMapper.updateAnoAdmin(anoAdmin);
    }

    /**
     * 批量删除审核标注分配
     *
     * @param anoAdminIds 需要删除的审核标注分配主键
     * @return 结果
     */
    @Override
    public int deleteAnoAdminByAnoAdminIds(Long[] anoAdminIds)
    {
        return anoAdminMapper.deleteAnoAdminByAnoAdminIds(anoAdminIds);
    }

    /**
     * 删除审核标注分配信息
     *
     * @param anoAdminId 审核标注分配主键
     * @return 结果
     */
    @Override
    public int deleteAnoAdminByAnoAdminId(Long anoAdminId)
    {
        return anoAdminMapper.deleteAnoAdminByAnoAdminId(anoAdminId);
    }
}




