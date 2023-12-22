package com.ruoyi.xindian.ano.service;

import com.ruoyi.xindian.ano.domain.AnoAdmin;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 13401
* @description 针对表【ano_admin】的数据库操作Service
* @createDate 2023-12-22 11:54:00
*/
public interface AnoAdminService extends IService<AnoAdmin> {




    List<AnoAdmin> selectAnoAdminList(AnoAdmin anoAdmin);


    List<AnoAdmin> selectAnoAdminListAll(AnoAdmin anoAdmin);

    /**
     * 查询审核标注分配
     *
     * @param anoAdminId 审核标注分配主键
     * @return 审核标注分配
     */
    public AnoAdmin selectAnoAdminByAnoAdminId(Long anoAdminId);

    /**
     * 新增审核标注分配
     *
     * @param anoAdmin 审核标注分配
     * @return 结果
     */
    public int insertAnoAdmin(AnoAdmin anoAdmin);

    /**
     * 修改审核标注分配
     *
     * @param anoAdmin 审核标注分配
     * @return 结果
     */
    public int updateAnoAdmin(AnoAdmin anoAdmin);

    /**
     * 批量删除审核标注分配
     *
     * @param anoAdminIds 需要删除的审核标注分配主键集合
     * @return 结果
     */
    public int deleteAnoAdminByAnoAdminIds(Long[] anoAdminIds);

    /**
     * 删除审核标注分配信息
     *
     * @param anoAdminId 审核标注分配主键
     * @return 结果
     */
    public int deleteAnoAdminByAnoAdminId(Long anoAdminId);





}
