package com.ruoyi.xindian.hospital.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.service.ISysDictDataService;
import com.ruoyi.xindian.hospital.domain.AssociatedHospital;
import com.ruoyi.xindian.hospital.domain.Hospital;
import com.ruoyi.xindian.hospital.mapper.AssociatedHospitalMapper;
import com.ruoyi.xindian.hospital.service.IHospitalService;
import com.ruoyi.xindian.util.RoleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 医院Controller
 *
 * @author hanhan
 * @date 2022-08-20
 */
@RestController
@RequestMapping("/hospital/hospitalList")
public class HospitalListController extends BaseController
{
    @Autowired
    private IHospitalService hospitalService;


    @Autowired
    private TokenService tokenService;
    @Autowired
    private SysUserMapper sysUserMapper;

    @Resource
    private AssociatedHospitalMapper associatedHospitalMapper;


    @Resource
    private ISysDictDataService dictDataService;
    /**
     * 查询医院列表
     */
//    @PreAuthorize("@ss.hasPermi('hospital:hospital:list')")
    @GetMapping("/list")
    public TableDataInfo list(Hospital hospital, HttpServletRequest request)
    {
        LoginUser loginUser = tokenService.getLoginUser(request);
        List<Hospital> list = null;
        //判断是否为管理员
        List<String> sysDictData = dictDataService.selectDictDataByType("admin_select");
        if (SysUser.isAdmin(loginUser.getUserId())||RoleUtils.isRoleListOne(loginUser,sysDictData)){
            if (hospital.getHospitalCode()!=null&&!"".equals(hospital.getHospitalCode())){
                hospital.getHospitalCodeList().add(hospital.getHospitalCode());
            }
            startPage();
           list = hospitalService.selectHospitalList(hospital);
        }
        else
        {
            SysUser sysUser = sysUserMapper.selectUserById(loginUser.getUser().getUserId());
            if (sysUser.getHospitalCode()!=null&&!"".equals(sysUser.getHospitalCode())){
                hospital.getHospitalCodeList().add(sysUser.getHospitalCode());
            }

            Hospital hospital2 = hospitalService.selectHospitalByHospitalCode(sysUser.getHospitalCode());
            AssociatedHospital associatedHospital = new AssociatedHospital();
            associatedHospital.setHospitalId(hospital2.getHospitalId());
            List<AssociatedHospital> associatedHospitals = associatedHospitalMapper.selectAssociatedHospitalList(associatedHospital);
            if (associatedHospitals!=null&& !associatedHospitals.isEmpty()){
                for (AssociatedHospital c:associatedHospitals){
                    Hospital hospital1 = hospitalService.selectHospitalByHospitalId(c.getLowerLevelHospitalId());
                    hospital.getHospitalCodeList().add(hospital1.getHospitalCode());
                }
            }
            if (hospital.getHospitalCode()!=null&&!"".equals(hospital.getHospitalCode())){
                List<String> hospitalCodeList = hospital.getHospitalCodeList();
                if (hospitalCodeList!=null&& !hospitalCodeList.isEmpty()){
                    for (String c : hospitalCodeList){
                        if (c.equals(hospital.getHospitalCode())){

                            hospital.getHospitalCodeList().clear();
                            hospital.getHospitalCodeList().add(hospital.getHospitalCode());
                            break;
                        }
                    }
                }
            }

            startPage();
            list = hospitalService.selectUserId(hospital);
        }
        for (Hospital hospital1 : list) {
            if(hospital1.getDeviceOnlineNum()==null){
                hospital1.setDeviceOnlineNum(0);
            }
        }
        return getDataTable(list);
    }

    /**
     * 查询医院列表
     */
//    @PreAuthorize("@ss.hasPermi('hospital:hospital:list')")
    @GetMapping("/listId")
    public TableDataInfo listId(Hospital hospital, HttpServletRequest request)
    {
        LoginUser loginUser = tokenService.getLoginUser(request);
        List<Hospital> list = new ArrayList<>();
        //判断是否为管理员
        List<String> sysDictData = dictDataService.selectDictDataByType("admin_select");
        if (SysUser.isAdmin(loginUser.getUserId())||RoleUtils.isRoleListOne(loginUser,sysDictData))
        {
            list = hospitalService.selectHospitalList(hospital);
        }
        else
        {
            SysUser sysUser = sysUserMapper.selectUserById(loginUser.getUser().getUserId());
            if (sysUser.getHospitalCode()!=null&&!"".equals(sysUser.getHospitalCode())){
                hospital.getHospitalCodeList().add(sysUser.getHospitalCode());

                Hospital hospital2 = hospitalService.selectHospitalByHospitalCode(sysUser.getHospitalCode());
                if (hospital2!=null){
                    AssociatedHospital associatedHospital = new AssociatedHospital();
                    associatedHospital.setHospitalId(hospital2.getHospitalId());
                    List<AssociatedHospital> associatedHospitals = associatedHospitalMapper.selectAssociatedHospitalList(associatedHospital);
                    if (associatedHospitals!=null&&associatedHospitals.size()>0){
                        for (AssociatedHospital c:associatedHospitals){
                            Hospital hospital1 = hospitalService.selectHospitalByHospitalId(c.getLowerLevelHospitalId());
                            hospital.getHospitalCodeList().add(hospital1.getHospitalCode());
                        }
                    }
                    list = hospitalService.selectUserId(hospital);
                }
            }



        }
        return getDataTable(list);
    }

    /**
     * 获取医院详细信息
     */
    @GetMapping(value = "/{hospitalId}")
    public AjaxResult getInfo(@PathVariable("hospitalId") Long hospitalId)
    {
        return AjaxResult.success(hospitalService.selectHospitalByHospitalId(hospitalId));
    }

    @GetMapping("/getHospitalCode")
    public AjaxResult getHospitalCode()
    {
        Long userId = getUserId();
        List<String> sysDictData = dictDataService.selectDictDataByType("admin_select");
        if (SysUser.isAdmin(userId)||RoleUtils.isRoleListOne(getLoginUser(),sysDictData)){
            return AjaxResult.success("All");
        }
        SysUser sysUser = sysUserMapper.selectUserById(userId);
        return AjaxResult.success(sysUser.getHospitalCode());
    }

}
