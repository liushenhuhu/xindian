package com.ruoyi.xindian.log_user.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName alert_log_count
 */
@TableName(value ="alert_log_count")
public class AlertLogCount implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer countId;

    /**
     * 预警id
     */
    private String logId;

    /**
     * 账号id
     */
    private Long  userId;

    /**
     * 患者管理id
     */
    private String pId;

    /**
     * 记录时间
     */
    private Date recordDate;

    /**
     * 年龄
     */
    private String age;

    /**
     * 性别
     */
    private String gender;

    /**
     * 统计
     */
    private String leadCount;

    /**
     * 正常心电图
     */
    private Integer zcxdtEcg;

    /**
     * 窦性心律
     */
    private Integer dxxlEcg;

    /**
     * 异位心率
     */
    private Integer ywxlEcg;

    /**
     * 房性心律
     */
    private Integer fxxlEcg;

    /**
     * 室性心律
     */
    private Integer sxxlEcg;

    /**
     * 交界性心律
     */
    private Integer jjxxlEcg;

    /**
     * 起搏心律
     */
    private Integer qbxlEcg;

    /**
     * 室性心动过速
     */
    private Integer sxxdgsEcg;

    /**
     * 室上性心动过速
     */
    private Integer ssxxdgsEcg;

    /**
     * 交界性心动过速
     */
    private Integer jjxxdgsEcg;

    /**
     * 房性心动过速
     */
    private Integer fxxdgsEcg;

    /**
     * 房性逸博
     */
    private Integer fxybEcg;

    /**
     * 房性早博
     */
    private Integer fxzbEcg;

    /**
     * 房性停博
     */
    private Integer fxtbEcg;

    /**
     * 室性逸博
     */
    private Integer sxybEcg;

    /**
     * 室性早博
     */
    private Integer sxzbEcg;

    /**
     * 交界性逸博
     */
    private Integer jjxybEcg;

    /**
     * 交界性早博
     */
    private Integer jjxzbEcg;

    /**
     * 交界性停博
     */
    private Integer jjxtbEcg;

    /**
     * 窦性停博
     */
    private Integer dxtbEcg;

    /**
     * 心室停博
     */
    private Integer xstbEcg;

    /**
     * 全心停博
     */
    private Integer qxtbEcg;

    /**
     * 心房颤动
     */
    private Integer xfcdEcg;

    /**
     * 心房扑动
     */
    private Integer xfpdEcg;

    /**
     * 心室颤动
     */
    private Integer xscdEcg;

    /**
     * 心室扑动
     */
    private Integer xspdEcg;

    /**
     * 一度房室阻滞
     */
    private Integer ydfszdEcg;

    /**
     * 二度房室阻滞
     */
    private Integer edfszdEcg;

    /**
     * 二度I型房室阻滞
     */
    private Integer edixfszdEcg;

    /**
     * 二度II型房室阻滞
     */
    private Integer ediixfszdEcg;

    /**
     * 三度房室阻滞
     */
    private Integer sdfszdEcg;

    /**
     * 窦房阻滞
     */
    private Integer dfzdEcg;

    /**
     * 高度房室阻滞
     */
    private Integer gdfszdEcg;

    /**
     * 左前分支阻滞
     */
    private Integer zqfzzdEcg;

    /**
     * 左后分支阻滞
     */
    private Integer zhfzzdEcg;

    /**
     * 完全性左束支阻滞
     */
    private Integer wqxzszzdEcg;

    /**
     * 不完全性左束支阻滞
     */
    private Integer bwqxzszzdEcg;

    /**
     * 完全性右束支阻滞
     */
    private Integer wqxyszzdEcg;

    /**
     * 不完全性右束支阻滞
     */
    private Integer bwqxyszzdEcg;

    /**
     * 非特异性室内阻滞
     */
    private Integer ftyxsnzdEcg;

    /**
     * 室内差异性传导
     */
    private Integer sncyxcdEcg;

    /**
     * 心室预激波
     */
    private Integer xsyjbEcg;

    /**
     * 预激综合症
     */
    private Integer yjzhzEcg;

    /**
     * 左心房肥大
     */
    private Integer zxffdEcg;

    /**
     * 左心室肥大
     */
    private Integer zxsfdEcg;

    /**
     * 右心房肥大
     */
    private Integer yxffdEcg;

    /**
     * 右心室肥大
     */
    private Integer yxsfdEcg;

    /**
     * ST改变
     */
    private Integer stgbEcg;

    /**
     * ST-T改变
     */
    private Integer stTgbEcg;

    /**
     * T波异常
     */
    private Integer tbycEcg;

    /**
     * QT间期延长
     */
    private Integer qtjqycEcg;

    /**
     * QTc间期延长
     */
    private Integer qtcjqycEcg;

    /**
     * 可见U波
     */
    private Integer kjubEcg;

    /**
     * 早期复极
     */
    private Integer zqfjEcg;

    /**
     * 前间壁心肌梗死
     */
    private Integer qjbxjgsEcg;

    /**
     * 前侧壁心肌梗死
     */
    private Integer qcbxjgsEcg;

    /**
     * 广泛前壁心肌梗死
     */
    private Integer qgfbxjgsEcg;

    /**
     * 前壁心肌梗死
     */
    private Integer qbxjgsEcg;

    /**
     * 下壁心肌梗死
     */
    private Integer xbxjgsEcg;

    /**
     * 下后壁心肌梗死
     */
    private Integer xhbxjgsEcg;

    /**
     * 后壁心肌梗死
     */
    private Integer hbxjgsEcg;

    /**
     * 下侧壁心肌梗死
     */
    private Integer xcbbxjgsEcg;

    /**
     * 高侧壁心肌梗死
     */
    private Integer gcbbxjgsEcg;

    /**
     * 侧壁心肌梗死
     */
    private Integer cbbxjgsEcg;

    /**
     * 心内膜下心肌梗死
     */
    private Integer xnmxxjgsEcg;

    /**
     * 右室心肌梗死
     */
    private Integer ysxjgsEcg;

    /**
     * 非ST抬高心肌梗死
     */
    private Integer fsttgxxjgsEcg;

    /**
     * 心尖部心肌梗死
     */
    private Integer xjbxjgsEcg;

    /**
     * 间壁心肌梗死
     */
    private Integer jbxjgsEcg;

    /**
     * 亚急性
     */
    private Integer yjxEcg;

    /**
     * 急性
     */
    private Integer jxEcg;

    /**
     * 陈旧性
     */
    private Integer cjxEcg;

    /**
     * 心电图危急值
     */
    private Integer xdtwjzEcg;

    /**
     * 干扰信号
     */
    private Integer grxhEcg;

    /**
     * 长RR间期
     */
    private Integer crrjqEcg;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


    public Integer getCountId() {
        return countId;
    }

    public void setCountId(Integer countId) {
        this.countId = countId;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLeadCount() {
        return leadCount;
    }

    public void setLeadCount(String leadCount) {
        this.leadCount = leadCount;
    }

    public Integer getZcxdtEcg() {
        return zcxdtEcg;
    }

    public void setZcxdtEcg(Integer zcxdtEcg) {
        this.zcxdtEcg = zcxdtEcg;
    }

    public Integer getDxxlEcg() {
        return dxxlEcg;
    }

    public void setDxxlEcg(Integer dxxlEcg) {
        this.dxxlEcg = dxxlEcg;
    }

    public Integer getYwxlEcg() {
        return ywxlEcg;
    }

    public void setYwxlEcg(Integer ywxlEcg) {
        this.ywxlEcg = ywxlEcg;
    }

    public Integer getFxxlEcg() {
        return fxxlEcg;
    }

    public void setFxxlEcg(Integer fxxlEcg) {
        this.fxxlEcg = fxxlEcg;
    }

    public Integer getSxxlEcg() {
        return sxxlEcg;
    }

    public void setSxxlEcg(Integer sxxlEcg) {
        this.sxxlEcg = sxxlEcg;
    }

    public Integer getJjxxlEcg() {
        return jjxxlEcg;
    }

    public void setJjxxlEcg(Integer jjxxlEcg) {
        this.jjxxlEcg = jjxxlEcg;
    }

    public Integer getQbxlEcg() {
        return qbxlEcg;
    }

    public void setQbxlEcg(Integer qbxlEcg) {
        this.qbxlEcg = qbxlEcg;
    }

    public Integer getSxxdgsEcg() {
        return sxxdgsEcg;
    }

    public void setSxxdgsEcg(Integer sxxdgsEcg) {
        this.sxxdgsEcg = sxxdgsEcg;
    }

    public Integer getSsxxdgsEcg() {
        return ssxxdgsEcg;
    }

    public void setSsxxdgsEcg(Integer ssxxdgsEcg) {
        this.ssxxdgsEcg = ssxxdgsEcg;
    }

    public Integer getJjxxdgsEcg() {
        return jjxxdgsEcg;
    }

    public void setJjxxdgsEcg(Integer jjxxdgsEcg) {
        this.jjxxdgsEcg = jjxxdgsEcg;
    }

    public Integer getFxxdgsEcg() {
        return fxxdgsEcg;
    }

    public void setFxxdgsEcg(Integer fxxdgsEcg) {
        this.fxxdgsEcg = fxxdgsEcg;
    }

    public Integer getFxybEcg() {
        return fxybEcg;
    }

    public void setFxybEcg(Integer fxybEcg) {
        this.fxybEcg = fxybEcg;
    }

    public Integer getFxzbEcg() {
        return fxzbEcg;
    }

    public void setFxzbEcg(Integer fxzbEcg) {
        this.fxzbEcg = fxzbEcg;
    }

    public Integer getFxtbEcg() {
        return fxtbEcg;
    }

    public void setFxtbEcg(Integer fxtbEcg) {
        this.fxtbEcg = fxtbEcg;
    }

    public Integer getSxybEcg() {
        return sxybEcg;
    }

    public void setSxybEcg(Integer sxybEcg) {
        this.sxybEcg = sxybEcg;
    }

    public Integer getSxzbEcg() {
        return sxzbEcg;
    }

    public void setSxzbEcg(Integer sxzbEcg) {
        this.sxzbEcg = sxzbEcg;
    }

    public Integer getJjxybEcg() {
        return jjxybEcg;
    }

    public void setJjxybEcg(Integer jjxybEcg) {
        this.jjxybEcg = jjxybEcg;
    }

    public Integer getJjxzbEcg() {
        return jjxzbEcg;
    }

    public void setJjxzbEcg(Integer jjxzbEcg) {
        this.jjxzbEcg = jjxzbEcg;
    }

    public Integer getJjxtbEcg() {
        return jjxtbEcg;
    }

    public void setJjxtbEcg(Integer jjxtbEcg) {
        this.jjxtbEcg = jjxtbEcg;
    }

    public Integer getDxtbEcg() {
        return dxtbEcg;
    }

    public void setDxtbEcg(Integer dxtbEcg) {
        this.dxtbEcg = dxtbEcg;
    }

    public Integer getXstbEcg() {
        return xstbEcg;
    }

    public void setXstbEcg(Integer xstbEcg) {
        this.xstbEcg = xstbEcg;
    }

    public Integer getQxtbEcg() {
        return qxtbEcg;
    }

    public void setQxtbEcg(Integer qxtbEcg) {
        this.qxtbEcg = qxtbEcg;
    }

    public Integer getXfcdEcg() {
        return xfcdEcg;
    }

    public void setXfcdEcg(Integer xfcdEcg) {
        this.xfcdEcg = xfcdEcg;
    }

    public Integer getXfpdEcg() {
        return xfpdEcg;
    }

    public void setXfpdEcg(Integer xfpdEcg) {
        this.xfpdEcg = xfpdEcg;
    }

    public Integer getXscdEcg() {
        return xscdEcg;
    }

    public void setXscdEcg(Integer xscdEcg) {
        this.xscdEcg = xscdEcg;
    }

    public Integer getXspdEcg() {
        return xspdEcg;
    }

    public void setXspdEcg(Integer xspdEcg) {
        this.xspdEcg = xspdEcg;
    }

    public Integer getYdfszdEcg() {
        return ydfszdEcg;
    }

    public void setYdfszdEcg(Integer ydfszdEcg) {
        this.ydfszdEcg = ydfszdEcg;
    }

    public Integer getEdfszdEcg() {
        return edfszdEcg;
    }

    public void setEdfszdEcg(Integer edfszdEcg) {
        this.edfszdEcg = edfszdEcg;
    }

    public Integer getEdixfszdEcg() {
        return edixfszdEcg;
    }

    public void setEdixfszdEcg(Integer edixfszdEcg) {
        this.edixfszdEcg = edixfszdEcg;
    }

    public Integer getEdiixfszdEcg() {
        return ediixfszdEcg;
    }

    public void setEdiixfszdEcg(Integer ediixfszdEcg) {
        this.ediixfszdEcg = ediixfszdEcg;
    }

    public Integer getSdfszdEcg() {
        return sdfszdEcg;
    }

    public void setSdfszdEcg(Integer sdfszdEcg) {
        this.sdfszdEcg = sdfszdEcg;
    }

    public Integer getDfzdEcg() {
        return dfzdEcg;
    }

    public void setDfzdEcg(Integer dfzdEcg) {
        this.dfzdEcg = dfzdEcg;
    }

    public Integer getGdfszdEcg() {
        return gdfszdEcg;
    }

    public void setGdfszdEcg(Integer gdfszdEcg) {
        this.gdfszdEcg = gdfszdEcg;
    }

    public Integer getZqfzzdEcg() {
        return zqfzzdEcg;
    }

    public void setZqfzzdEcg(Integer zqfzzdEcg) {
        this.zqfzzdEcg = zqfzzdEcg;
    }

    public Integer getZhfzzdEcg() {
        return zhfzzdEcg;
    }

    public void setZhfzzdEcg(Integer zhfzzdEcg) {
        this.zhfzzdEcg = zhfzzdEcg;
    }

    public Integer getWqxzszzdEcg() {
        return wqxzszzdEcg;
    }

    public void setWqxzszzdEcg(Integer wqxzszzdEcg) {
        this.wqxzszzdEcg = wqxzszzdEcg;
    }

    public Integer getBwqxzszzdEcg() {
        return bwqxzszzdEcg;
    }

    public void setBwqxzszzdEcg(Integer bwqxzszzdEcg) {
        this.bwqxzszzdEcg = bwqxzszzdEcg;
    }

    public Integer getWqxyszzdEcg() {
        return wqxyszzdEcg;
    }

    public void setWqxyszzdEcg(Integer wqxyszzdEcg) {
        this.wqxyszzdEcg = wqxyszzdEcg;
    }

    public Integer getBwqxyszzdEcg() {
        return bwqxyszzdEcg;
    }

    public void setBwqxyszzdEcg(Integer bwqxyszzdEcg) {
        this.bwqxyszzdEcg = bwqxyszzdEcg;
    }

    public Integer getFtyxsnzdEcg() {
        return ftyxsnzdEcg;
    }

    public void setFtyxsnzdEcg(Integer ftyxsnzdEcg) {
        this.ftyxsnzdEcg = ftyxsnzdEcg;
    }

    public Integer getSncyxcdEcg() {
        return sncyxcdEcg;
    }

    public void setSncyxcdEcg(Integer sncyxcdEcg) {
        this.sncyxcdEcg = sncyxcdEcg;
    }

    public Integer getXsyjbEcg() {
        return xsyjbEcg;
    }

    public void setXsyjbEcg(Integer xsyjbEcg) {
        this.xsyjbEcg = xsyjbEcg;
    }

    public Integer getYjzhzEcg() {
        return yjzhzEcg;
    }

    public void setYjzhzEcg(Integer yjzhzEcg) {
        this.yjzhzEcg = yjzhzEcg;
    }

    public Integer getZxffdEcg() {
        return zxffdEcg;
    }

    public void setZxffdEcg(Integer zxffdEcg) {
        this.zxffdEcg = zxffdEcg;
    }

    public Integer getZxsfdEcg() {
        return zxsfdEcg;
    }

    public void setZxsfdEcg(Integer zxsfdEcg) {
        this.zxsfdEcg = zxsfdEcg;
    }

    public Integer getYxffdEcg() {
        return yxffdEcg;
    }

    public void setYxffdEcg(Integer yxffdEcg) {
        this.yxffdEcg = yxffdEcg;
    }

    public Integer getYxsfdEcg() {
        return yxsfdEcg;
    }

    public void setYxsfdEcg(Integer yxsfdEcg) {
        this.yxsfdEcg = yxsfdEcg;
    }

    public Integer getStgbEcg() {
        return stgbEcg;
    }

    public void setStgbEcg(Integer stgbEcg) {
        this.stgbEcg = stgbEcg;
    }

    public Integer getStTgbEcg() {
        return stTgbEcg;
    }

    public void setStTgbEcg(Integer stTgbEcg) {
        this.stTgbEcg = stTgbEcg;
    }

    public Integer getTbycEcg() {
        return tbycEcg;
    }

    public void setTbycEcg(Integer tbycEcg) {
        this.tbycEcg = tbycEcg;
    }

    public Integer getQtjqycEcg() {
        return qtjqycEcg;
    }

    public void setQtjqycEcg(Integer qtjqycEcg) {
        this.qtjqycEcg = qtjqycEcg;
    }

    public Integer getQtcjqycEcg() {
        return qtcjqycEcg;
    }

    public void setQtcjqycEcg(Integer qtcjqycEcg) {
        this.qtcjqycEcg = qtcjqycEcg;
    }

    public Integer getKjubEcg() {
        return kjubEcg;
    }

    public void setKjubEcg(Integer kjubEcg) {
        this.kjubEcg = kjubEcg;
    }

    public Integer getZqfjEcg() {
        return zqfjEcg;
    }

    public void setZqfjEcg(Integer zqfjEcg) {
        this.zqfjEcg = zqfjEcg;
    }

    public Integer getQjbxjgsEcg() {
        return qjbxjgsEcg;
    }

    public void setQjbxjgsEcg(Integer qjbxjgsEcg) {
        this.qjbxjgsEcg = qjbxjgsEcg;
    }

    public Integer getQcbxjgsEcg() {
        return qcbxjgsEcg;
    }

    public void setQcbxjgsEcg(Integer qcbxjgsEcg) {
        this.qcbxjgsEcg = qcbxjgsEcg;
    }

    public Integer getQgfbxjgsEcg() {
        return qgfbxjgsEcg;
    }

    public void setQgfbxjgsEcg(Integer qgfbxjgsEcg) {
        this.qgfbxjgsEcg = qgfbxjgsEcg;
    }

    public Integer getQbxjgsEcg() {
        return qbxjgsEcg;
    }

    public void setQbxjgsEcg(Integer qbxjgsEcg) {
        this.qbxjgsEcg = qbxjgsEcg;
    }

    public Integer getXbxjgsEcg() {
        return xbxjgsEcg;
    }

    public void setXbxjgsEcg(Integer xbxjgsEcg) {
        this.xbxjgsEcg = xbxjgsEcg;
    }

    public Integer getXhbxjgsEcg() {
        return xhbxjgsEcg;
    }

    public void setXhbxjgsEcg(Integer xhbxjgsEcg) {
        this.xhbxjgsEcg = xhbxjgsEcg;
    }

    public Integer getHbxjgsEcg() {
        return hbxjgsEcg;
    }

    public void setHbxjgsEcg(Integer hbxjgsEcg) {
        this.hbxjgsEcg = hbxjgsEcg;
    }

    public Integer getXcbbxjgsEcg() {
        return xcbbxjgsEcg;
    }

    public void setXcbbxjgsEcg(Integer xcbbxjgsEcg) {
        this.xcbbxjgsEcg = xcbbxjgsEcg;
    }

    public Integer getGcbbxjgsEcg() {
        return gcbbxjgsEcg;
    }

    public void setGcbbxjgsEcg(Integer gcbbxjgsEcg) {
        this.gcbbxjgsEcg = gcbbxjgsEcg;
    }

    public Integer getCbbxjgsEcg() {
        return cbbxjgsEcg;
    }

    public void setCbbxjgsEcg(Integer cbbxjgsEcg) {
        this.cbbxjgsEcg = cbbxjgsEcg;
    }

    public Integer getXnmxxjgsEcg() {
        return xnmxxjgsEcg;
    }

    public void setXnmxxjgsEcg(Integer xnmxxjgsEcg) {
        this.xnmxxjgsEcg = xnmxxjgsEcg;
    }

    public Integer getYsxjgsEcg() {
        return ysxjgsEcg;
    }

    public void setYsxjgsEcg(Integer ysxjgsEcg) {
        this.ysxjgsEcg = ysxjgsEcg;
    }

    public Integer getFsttgxxjgsEcg() {
        return fsttgxxjgsEcg;
    }

    public void setFsttgxxjgsEcg(Integer fsttgxxjgsEcg) {
        this.fsttgxxjgsEcg = fsttgxxjgsEcg;
    }

    public Integer getXjbxjgsEcg() {
        return xjbxjgsEcg;
    }

    public void setXjbxjgsEcg(Integer xjbxjgsEcg) {
        this.xjbxjgsEcg = xjbxjgsEcg;
    }

    public Integer getJbxjgsEcg() {
        return jbxjgsEcg;
    }

    public void setJbxjgsEcg(Integer jbxjgsEcg) {
        this.jbxjgsEcg = jbxjgsEcg;
    }

    public Integer getYjxEcg() {
        return yjxEcg;
    }

    public void setYjxEcg(Integer yjxEcg) {
        this.yjxEcg = yjxEcg;
    }

    public Integer getJxEcg() {
        return jxEcg;
    }

    public void setJxEcg(Integer jxEcg) {
        this.jxEcg = jxEcg;
    }

    public Integer getCjxEcg() {
        return cjxEcg;
    }

    public void setCjxEcg(Integer cjxEcg) {
        this.cjxEcg = cjxEcg;
    }

    public Integer getXdtwjzEcg() {
        return xdtwjzEcg;
    }

    public void setXdtwjzEcg(Integer xdtwjzEcg) {
        this.xdtwjzEcg = xdtwjzEcg;
    }

    public Integer getGrxhEcg() {
        return grxhEcg;
    }

    public void setGrxhEcg(Integer grxhEcg) {
        this.grxhEcg = grxhEcg;
    }

    public Integer getCrrjqEcg() {
        return crrjqEcg;
    }

    public void setCrrjqEcg(Integer crrjqEcg) {
        this.crrjqEcg = crrjqEcg;
    }
}