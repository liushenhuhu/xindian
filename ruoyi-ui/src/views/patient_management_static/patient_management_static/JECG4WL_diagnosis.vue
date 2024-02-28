<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">

      <el-form-item label="患者姓名" prop="patientName">
        <el-input
            v-model="queryParams.patientName"
            placeholder="请输入患者姓名"
            clearable
            @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="患者性别" prop="patientSex">
        <el-select placeholder="请选择性别" v-model="queryParams.patientSex">
          <el-option label="男" value="男"></el-option>
          <el-option label="女" value="女"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="患者电话" prop="patientPhone">
        <el-input
            v-model="queryParams.PatPhone"
            placeholder="请输入患者电话"
            clearable
            @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="设备类型" prop="patientCode">
        <el-select v-model="queryParams.ecgType" placeholder="请选择设备类型" >
          <el-option
              v-for="item in ecgList"
              :key="item.label"
              :label="item.label"
              :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="患者管理编号" prop="pId">
        <el-input
            v-model="queryParams.pId"
            placeholder="请输入患者管理编号"
            clearable
            @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="医院名称" prop="hospitalName">
        <el-select v-model="queryParams.hospitalCode" placeholder="请选择医院代号" >
          <el-option
              v-for="item in options"
              :key="item.hospitalId"
              :label="item.hospitalName"
              :value="item.hospitalCode">
          </el-option>
        </el-select>
      </el-form-item>
      <!--      <el-form-item label="设备号" prop="equipmentCode">
              <el-input
                v-model="queryParams.equipmentCode"
                placeholder="请输入设备号"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="连接时间">
              <el-date-picker
                v-model="daterangeConnectionTime"
                style="width: 205px"
                value-format="yyyy-MM-dd HH:mm:ss"
                type="datetimerange"
                range-separator="-"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
              ></el-date-picker>
            </el-form-item>-->
      <!--      <el-form-item label="在线状态" prop="onlineStatus">
              <el-select v-model="queryParams.onlineStatus" placeholder="请选择在线状态" clearable>
                <el-option
                  v-for="dict in dict.type.monitoring_status"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>-->
      <el-form-item label="智能诊断" prop="intelligentDiagnosis">
        <el-input
            v-model="queryParams.intelligentDiagnosis"
            placeholder="请输入智能诊断"
            clearable
            @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!--      <el-form-item label="诊断状态" prop="diagnosisStatus">-->
      <!--        <el-select v-model="queryParams.diagnosisStatus" placeholder="请选择诊断状态" clearable>-->
      <!--          <el-option-->
      <!--            v-for="dict in dict.type.diagnosis_status"-->
      <!--            :key="dict.value"-->
      <!--            :label="dict.label"-->
      <!--            :value="dict.value"-->
      <!--          />-->
      <!--        </el-select>-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="诊断结论" prop="diagnosisConclusion">
              <el-input
                v-model="queryParams.diagnosisConclusion"
                placeholder="请输入诊断结论"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>-->
      <el-form-item label="诊断医生" prop="diagnosisDoctor">
        <el-select v-model="queryParams.diagnosisDoctor" placeholder="请选择诊断医生" >
          <el-option
              v-for="item in option2"
              :key="item.doctorId"
              :label="item.doctorName"
              :value="item.doctorName">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="风险等级" prop="ecgLevel">
        <el-select v-model="queryParams.ecgLevel" placeholder="请选择风险等级" clearable>
          <el-option
              v-for="dict in dict.type.ecg_level"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="医生电话" prop="doctorPhone">
        <el-input
            v-model="queryParams.doctorPhone"
            placeholder="请输入医生电话"
            clearable
            @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!--      <el-form-item label="pId" prop="pId">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.pId"-->
      <!--          placeholder="请输入pId"-->
      <!--          clearable-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <el-form-item>
        <el-form-item label="名称模糊查询" prop="isSelect">
          <el-radio-group v-model="queryParams.isSelect">
            <el-radio  label="1">开启</el-radio>
            <el-radio  label="2">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="primary"-->
      <!--          plain-->
      <!--          icon="el-icon-plus"-->
      <!--          size="mini"-->
      <!--          @click="handleAdd"-->
      <!--          v-hasPermi="['patient_management:patient_management:add']"-->
      <!--        >新增-->
      <!--        </el-button>-->
      <!--      </el-col>-->
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="success"-->
      <!--          plain-->
      <!--          icon="el-icon-edit"-->
      <!--          size="mini"-->
      <!--          :disabled="single"-->
      <!--          @click="handleUpdate"-->
      <!--          v-hasPermi="['patient_management:patient_management:edit']"-->
      <!--        >修改-->
      <!--        </el-button>-->
      <!--      </el-col>-->
      <el-col :span="1.5">
        <el-button
            type="danger"
            plain
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['patient_management:patient_management:export']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
            v-hasPermi="['patient_management:patient_management:export']"
        >导出
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="success"
            plain
            icon="el-icon-view"
            size="mini"
            @click="isShowNameClick"
        >{{isShowName.name}}
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getUpdateList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="patient_managementList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="上传时间" align="center" prop="connectionTime" width="100">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.connectionTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>

      <!--      <el-table-column label="患者管理id" align="center" prop="pId" show-overflow-tooltip/>
            <el-table-column label="患者姓名" align="center" prop="patientName"/>
            <el-table-column label="患者身份证号" align="center" prop="patientCode"/>
            <el-table-column label="患者年龄" align="center" prop="patientAge"/>
            <el-table-column label="患者性别" align="center" prop="patientSex">
              <template slot-scope="scope">
                <dict-tag :options="dict.type.sex" :value="scope.row.patientSex"/>
              </template>
            </el-table-column>
            <el-table-column label="患者来源" align="center" prop="patientSource"/>
            <el-table-column label="患者电话" align="center" prop="patientPhone"/>
            <el-table-column label="家属电话" align="center" prop="familyPhone"/>
            <el-table-column label="监测状态" align="center" prop="monitoringStatus">
              <template slot-scope="scope">
                <dict-tag :options="dict.type.monitoring_status" :value="scope.row.monitoringStatus"/>
              </template>
            </el-table-column>
            <el-table-column label="医院代号" align="center" prop="hospitalCode"/>
            <el-table-column label="医院名称" align="center" prop="hospitalName" width="150"/>
            <el-table-column label="设备号" align="center" prop="equipmentCode"/>
            <el-table-column label="在线状态" align="center" prop="onlineStatus">
              <template slot-scope="scope">
                <dict-tag :options="dict.type.monitoring_status" :value="scope.row.onlineStatus"/>
              </template>
            </el-table-column>
            <el-table-column label="心电种类" align="center" prop="ecgType">
              <template slot-scope="scope">
                <dict-tag :options="dict.type.ecg_type" :value="scope.row.ecgType"/>
              </template>
            </el-table-column>-->


      <el-table-column label="智能诊断" align="center" prop="intelligentDiagnosis" show-overflow-tooltip/>
      <!--      <el-table-column label="诊断状态" align="center" prop="diagnosisStatus">-->
      <!--        <template slot-scope="scope">-->
      <!--          <dict-tag :options="dict.type.diagnosis_status" :value="scope.row.diagnosisStatus"/>-->
      <!--        </template>-->
      <!--      </el-table-column>-->
      <el-table-column label="患者名称" align="center" prop="patientName">
        <template slot-scope="scope">
          <span v-if="isShowName.status===true">{{scope.row.patientName}}</span>
          <span v-else>***</span>
        </template>
      </el-table-column>
      <el-table-column label="患者症状" align="center" prop="patientSymptom" show-overflow-tooltip/>
      <el-table-column label="诊断结论" align="center" prop="diagnosisConclusion" show-overflow-tooltip/>
      <!--      <el-table-column label="医院名称" align="center" prop="hospitalName"/>-->
      <el-table-column label="诊断医生" align="center" prop="diagnosisDoctor">
        <template slot-scope="scope">
          <span v-if="isShowName.status===true">{{scope.row.diagnosisDoctor}}</span>
          <span v-else>***</span>
        </template>
      </el-table-column>
      <el-table-column label="报告时间" align="center" prop="reportTime" width="100">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.reportTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="风险等级" align="center" prop="ecgLevel">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ecg_level" :value="scope.row.ecgLevel"/>
        </template>
      </el-table-column>
      <!--      <el-table-column label="心电类型" align="center" prop="ecgType"/>-->
      <el-table-column label="心电种类" align="center" prop="ecgType" width="120">
        <template slot-scope="scope">
          <el-tag >
            {{scope.row.ecgType}}
          </el-tag>
        </template>
      </el-table-column>
      <!--  隐藏的患者的个人信息    -->
      <el-table-column type="expand">
        <template slot-scope="scope">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-divider content-position="left">其他信息</el-divider>
            <el-form-item label="医院代号" width="200" style="padding-left: 40px">
              <span>{{ scope.row.hospitalCode }}</span>
            </el-form-item>
            <el-form-item label="医院名称" width="200" style="padding-left: 40px">
              <span>{{ scope.row.hospitalName }}</span>
            </el-form-item>
            <el-form-item label="患者管理id" width="200" style="padding-left: 40px">
              <span>{{ scope.row.pId }}</span>
            </el-form-item>
            <!--            <el-form-item label="患者姓名" width="200" style="padding-left: 40px">-->
            <!--              <span>{{ scope.row.patientName }}</span>-->
            <!--            </el-form-item>-->
            <el-form-item label="患者身份证号" width="200" style="padding-left: 40px">
              <span>{{ scope.row.patientCode }}</span>
            </el-form-item>
            <el-form-item label="患者年龄" width="200" style="padding-left: 40px">
              <span>{{ scope.row.patientAge }}</span>
            </el-form-item>
            <el-form-item label="患者性别" width="200" style="padding-left: 40px">
              <span>{{ scope.row.patientSex }}</span>
            </el-form-item>
            <!--            <el-form-item label="患者来源" width="200" style="padding-left: 40px">
                          <span>{{ scope.row.patientSource }}</span>
                        </el-form-item>-->
            <el-form-item label="患者电话" width="200" style="padding-left: 40px">
              <span>{{ scope.row.patientPhone }}</span>
            </el-form-item>
            <el-form-item label="家属电话" width="200" style="padding-left: 40px">
              <span>{{ scope.row.familyPhone }}</span>
            </el-form-item>

            <!--            <el-form-item label="医院名称" width="200" style="padding-left: 40px">
                          <span>{{ scope.row.hospitalName }}</span>
                        </el-form-item>-->
            <el-form-item label="设备号" width="200" style="padding-left: 40px">
              <span>{{ scope.row.equipmentCode }}</span>
            </el-form-item>
            <el-form-item label="医生电话" width="200" style="padding-left: 40px">
              <span>{{ scope.row.doctorPhone }}</span>
            </el-form-item>

          </el-form>
        </template>
      </el-table-column>


      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
              size="mini"
              type="text"
              icon="el-icon-s-order"
              @click="lookECG(scope.row)"
              v-hasPermi="['patient:patient:alert']"
          >查看报告
          </el-button>
          <el-button
              size="mini"
              type="text"
              icon="el-icon-s-order"
              @click="lookHistoryData(scope.row)"
          >查看历史数据
          </el-button>
          <el-button
              size="mini"
              type="text"
              icon="el-icon-eleme"
              @click="lookHistoryData30(scope.row)"
          >30天趋势图
          </el-button>
          <el-button
              size="mini"
              type="text"
              icon="el-icon-position"
              @click="sendMsg(scope.row)"
          >发送短信
          </el-button>
          <!--          <el-button-->
          <!--            size="mini"-->
          <!--            type="text"-->
          <!--            icon="el-icon-edit"-->
          <!--            @click="handleUpdate(scope.row)"-->
          <!--            v-hasPermi="['patient_management:patient_management:export']"-->
          <!--          >修改-->
          <!--          </el-button>-->
          <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['patient_management:patient_management:export']"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
    />

    <!-- 添加或修改患者管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="患者电话" prop="patientPhone">
          <el-input v-model="form.patientPhone" placeholder="请输入患者电话"/>
        </el-form-item>
        <el-form-item label="医院代号" prop="hospitalCode">
          <el-input v-model="form.hospitalCode" placeholder="请输入医院代号"/>
        </el-form-item>
        <el-form-item label="设备号" prop="equipmentCode">
          <el-input v-model="form.equipmentCode" placeholder="请输入设备号"/>
        </el-form-item>
        <el-form-item label="连接时间" prop="connectionTime">
          <el-date-picker clearable
                          v-model="form.connectionTime"
                          type="datetime"
                          value-format="yyyy-MM-dd HH:mm:ss"
                          placeholder="请选择连接时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="心电种类" prop="ecgType">
          <el-select v-model="form.ecgType" placeholder="请选择心电种类">
            <el-option
                v-for="dict in dict.type.ecg_type"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="在线状态" prop="onlineStatus">
          <el-select v-model="form.onlineStatus" placeholder="请选择在线状态">
            <el-option
                v-for="dict in dict.type.monitoring_status"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="智能诊断" prop="intelligentDiagnosis">
          <el-input v-model="form.intelligentDiagnosis" placeholder="请输入智能诊断"/>
        </el-form-item>
        <el-form-item label="风险等级">
          <el-radio-group v-model="form.ecgLevel">
            <el-radio
                v-for="dict in dict.type.ecg_level"
                :key="dict.value"
                :label="parseInt(dict.value)"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="医生电话" prop="doctorPhone">
          <el-input v-model="form.doctorPhone" placeholder="请输入医生电话" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog title="密码验证" :visible.sync="dialogFormVisibleVerifyAuthority">
      <el-form :model="verifyForm" :rules="rules" ref="verifyForm">
        <el-form-item label="验证密码" prop="password">
          <el-input placeholder="请输入密码" v-model="verifyForm.password" show-password></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisibleVerifyAuthority = false">取 消</el-button>
        <el-button type="primary" @click="dialogFormVisibleVerify">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listPatient_management,
  getPatient_management,
  delPatient_management,
  addPatient_management,
  updatePatient_management, updateStatus, getUserInfo, sendMsgToPatient, getEcgType, redisAddData
} from "@/api/patient_management/patient_management";
import axios from "axios";
import $ from "jquery";
import {updateEquipmentStatus} from "@/api/equipment/equipment";
import {updateOnlineAll} from "@/api/online/online";
import {listHospitalId} from "@/api/hospital/hospital";
import {docList} from "@/api/doctor/doctor";
import {getVerify} from "@/api/verify/verify";

export default {
  name: "JECG4WL_diagnosis",
  dicts: ['if', 'sex', 'monitoring_status', 'ecg_type', 'diagnosis_status', 'ecg_level', 'hospital_name_list'],
  data() {
    return {
      currentScrollPos: 0,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: false,
      verifyForm:{
        password:null,
        status:false
      },
      dialogFormVisibleVerifyAuthority:false,
      // 总条数
      total: 0,
      options:[],
      ecgList:[],
      ecgType:'JECG4',
      // 患者管理表格数据
      patient_managementList: [],
      // 弹出层标题
      title: "",
      option2:[],
      // 是否显示弹出层
      open: false,
      isShowName:{
        status:false,
        name:"显示姓名"
      },
      // 时间范围
      daterangeConnectionTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        pId: null,
        patientCode: null,
        hospitalCode: null,
        equipmentCode: null,
        connectionTime: null,
        patientName: null,
        ecgType: null,
        PatPhone: null,
        intelligentDiagnosis: null,
        diagnosisStatus: 1,
        diagnosisConclusion: null,
        diagnosisDoctor: null,
        reportTime: null,
        ecgLevel: null,
        doctorPhone: null,
        patientSex:null,
        isSelect:'2'
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        patientPhone: [
          {required: true, message: "患者电话不能为空", trigger: "blur"}
        ],
        hospitalCode: [
          {required: true, message: "医院代号不能为空", trigger: "blur"}
        ],
        equipmentCode: [
          {required: true, message: "设备号不能为空", trigger: "blur"}
        ],
        password: [
          {required: true, message: "密码不能为空", trigger: "blur"}
        ],
      }
    };
  },

  beforeCreate() {
    // updateOnlineAll();
  },
  activated() {
    document.documentElement.scrollTop=this.currentScrollPos || 0
  },

  beforeRouteLeave(to,from,next) {
    this.currentScrollPos = document.documentElement.scrollTop || 0
    next()
  },

  created() {
    getEcgType(this.ecgType).then(r=>{
      this.ecgList = r.data
    })
    listHospitalId(null).then(r=>{
      this.options=r.rows
    })
    docList().then(q=>{
      this.option2=q.data
    })
    this.getList();
  },
  methods: {

    refreshList() {
      console.log("refresh======")
      updateOnlineAll().then(res => {
        this.getList();
      })
    },

    getUpdateList() {
      redisAddData(this.ecgType).then(r=>{
        this.getList()
      })
    },
    sendMsg(row){
      const reg = /^1[3|4|5|7|8][0-9]\d{8}$/;
      let patientPhone = row.patientPhone
      if (row.patientPhone.length===14||row.patientPhone.length===15){
        patientPhone=row.patientPhone.substring(0,11)
      }

      var isPhoneNumberValid = reg.test(patientPhone);
      if(isPhoneNumberValid) {
        console.log("患者电话: " + row.patientPhone)
        console.log("患者姓名: " + row.patientName)
        this.$confirm('向该患者发送短信提示采集存在较大干扰?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          sendMsgToPatient(patientPhone).then(response => {
            this.$message({
              type: 'success',
              message: '发送成功!'
            });
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消'
          });
        });
      }
      else{
        this.$message.error('该患者手机号不合法！！！');
      }
    },

    /** 查询患者管理列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeConnectionTime && '' != this.daterangeConnectionTime) {
        this.queryParams.params["beginConnectionTime"] = this.daterangeConnectionTime[0];
        this.queryParams.params["endConnectionTime"] = this.daterangeConnectionTime[1];
      }
      if (this.queryParams.ecgType==null){
        this.queryParams.ecgType = this.ecgType
      }
      listPatient_management(this.queryParams).then(response => {
        this.patient_managementList = response.rows;
        this.total = response.total;
        this.loading = false;
        if ( this.queryParams.ecgType==='JECG4'){
          this.queryParams.ecgType=null
        }
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        pId: null,
        patientCode: null,
        hospitalCode: null,
        equipmentCode: null,
        connectionTime: null,
        patientName: null,
        ecgType: null,
        patientPhone: null,
        diagnosisStatus: null,
        diagnosisConclusion: null,
        diagnosisDoctor: null,
        reportTime: null,
        ecgLevel: null,
        doctorPhone: null,
        patientSex:null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;

      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams={
        pId: null,
        patientCode: null,
        hospitalCode: null,
        equipmentCode: null,
        connectionTime: null,
        patientName: null,
        ecgType: null,
        PatPhone: null,
        intelligentDiagnosis: null,
        diagnosisStatus: 1,
        diagnosisConclusion: null,
        diagnosisDoctor: null,
        reportTime: null,
        ecgLevel: null,
        doctorPhone: null,
        patientSex:null,
        isSelect:'2',
        pageNum: 1,
        pageSize: 10,
      }
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.pId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加患者管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const pId = row.pId || this.ids
      getPatient_management(pId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改患者管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.pId != null) {
            updatePatient_management(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPatient_management(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    dialogFormVisibleVerify(){
      this.$refs["verifyForm"].validate(valid => {
        if (valid) {
          let obj = {
            accountPwd:this.verifyForm.password
          }
          getVerify(obj).then(r=>{
            this.$modal.msgSuccess("密码正确");
            this.verifyForm.status=true
            this.dialogFormVisibleVerifyAuthority = false
          })
        }
      })
    },

    isShowNameClick(){
      if (this.verifyForm.status){
        if (this.isShowName.status){
          this.isShowName.status = !this.isShowName.status;
          this.isShowName.name = "显示姓名"

        }else {
          this.isShowName.status =!this.isShowName.status;
          this.isShowName.name = "隐藏姓名"
        }
      }else {
        this.verifyForm.password=''
        this.dialogFormVisibleVerifyAuthority = true
      }

    },
    lookHistoryData30(row){
      this.$router.push({path: "/scatterPlot", query: {patientPhone: row.patientPhone}});
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      if (this.verifyForm.status){
        const pIds = row.pId || this.ids;
        this.$modal.confirm('是否确认删除患者管理编号为"' + pIds + '"的数据项？').then(function () {
          return delPatient_management(pIds);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {
        });
      }else {
        this.verifyForm.password=''
        this.dialogFormVisibleVerifyAuthority = true
      }

    },
    /** 导出按钮操作 */
    handleExport() {
      if (this.verifyForm.status){
        this.download('patient_management/patient_management/export', {
          ...this.queryParams
        }, `patient_management_${new Date().getTime()}.xlsx`)
      }else {
        this.verifyForm.password=''
        this.dialogFormVisibleVerifyAuthority = true
      }

    },
    lookHistoryData(val){
      console.log(val)
      this.$router.push({path: "/historyData", query: {patientPhone: val.patientPhone,ecgType:"JECG4"}});
    },
    /** 查看心电图*/
    lookECG(row) {
      this.$router.push({path: "/JECG4_ECG", query: {pId: row.pId,}});
    },
    /** 生成报告*/
    handleInform(row) {
      let routeUrl = this.$router.resolve({path: "/restingECG", query: {pId: row.pId, hospitalName: row.hospitalName}});
      window.open(routeUrl.href, '_blank');

    },
    /** 下载报告*/
    downloadInform(row) {
      let routeUrl = this.$router.resolve({path: "/restingECG", query: {pId: row.pId, hospitalName: row.hospitalName}});
      window.open(routeUrl.href, '_blank');

    }
  }
};
</script>
