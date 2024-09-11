package com.ruoyi.xindian.util;


import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class RoleUtils {


    /**
     * 判断是否是管理员
     * @param loginUser
     * @return
     */
    public static boolean isAdmin(LoginUser loginUser) {

        if (loginUser == null){
            return false;
        }

        return loginUser.getUser().isAdmin();
    }


    /**
     * 判断是否是某个角色
     * @param loginUser 登录信息
     * @param roleCode 角色编号
     * @return
     */
    public static boolean isRole(LoginUser loginUser, String roleCode) {
        if (loginUser == null){
            return false;
        }

        Long[] roleIds = loginUser.getUser().getRoleIds();
        if (roleIds == null){
            return false;
        }
        try {
            for (Long roleId : roleIds) {
                if (roleId == null){
                    continue;
                }
                if (roleId.equals(Long.valueOf(roleCode))){
                    return true;
                }
            }
        }catch (Exception e){
            return false;
        }

        return false;

    }

    /**
     * 判断是否拥有一些角色中的其中一个角色
     */
    public static boolean isRoleListOne(LoginUser loginUser, List<String> roleCodes) {

        if (loginUser == null){
            return false;
        }
        Long[] roleIds = loginUser.getUser().getRoleIds()!=null&&loginUser.getUser().getRoleIds().length>0? loginUser.getUser().getRoleIds():new Long[]{loginUser.getUser().getRoleId()};
        if (roleIds == null){
            return false;
        }
        try {
            for (Long roleId : roleIds) {
                if (roleId == null){
                    continue;
                }
                for (String roleCode : roleCodes) {
                    if (roleId.equals(Long.valueOf(roleCode))){
                        return true;
                   }
                }
            }
        }
        catch (Exception e){
            return false;
        }
        return false;

    }

    /**
     * 判断是否拥有一些角色中的其中一个角色
     */
    public static boolean isRoleListOne(SysUser user, List<String> roleCodes) {

        if (user == null){
            return false;
        }
        Long[] roleIds = user.getRoleIds()!=null&&user.getRoleIds().length>0? user.getRoleIds():new Long[]{user.getRoleId()};
        if (roleIds == null){
            return false;
        }
        try {
            for (Long roleId : roleIds) {
                if (roleId == null){
                    continue;
                }
                for (String roleCode : roleCodes) {
                    if (roleId.equals(Long.valueOf(roleCode))){
                        return true;
                    }
                }
            }
        }
        catch (Exception e){
            return false;
        }
        return false;

    }


}
