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
          v-model="queryParams.patientPhone"
          placeholder="请输入患者电话"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="患者身份证号" prop="patientCode">
        <el-input
          v-model="queryParams.patientCode"
          placeholder="请输入患者身份证号"
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
      <el-form-item label="患者id" prop="pId">
        <el-input
          v-model="queryParams.pId"
          placeholder="请输入患者id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="医院名称" prop="hospitalName">
<!--        <el-select v-model="queryParams.hospitalName" placeholder="请选择医院名称" clearable>-->
<!--          <el-option-->
<!--            v-for="dict in dict.type.hospital_name_name_list"-->
<!--            :key="dict.value"-->
<!--            :label="dict.label"-->
<!--            :value="dict.value"-->
<!--          />-->
<!--        </el-select>-->
        <el-select v-model="queryParams.hospitalName" placeholder="请选择医院名称" clearable>
          <el-option
            v-for="item in options"
            :key="item.hospitalId"
            :label="item.hospitalName"
            :value="item.hospitalName">
          </el-option>
        </el-select>
      </el-form-item>
    <el-form-item label="设备号" prop="equipmentCode">
        <el-input
          v-model="queryParams.equipmentCode"
          placeholder="请输入设备号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
<!--      <el-form-item label="连接时间">
        <el-date-picker
          v-model="daterangeConnectionTime"
          style="width: 240px"
          value-format="yyyy-MM-dd HH:mm:ss"
          type="datetimerange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>-->
      <el-form-item label="在线状态" prop="onlineStatus">
        <el-select v-model="queryParams.onlineStatus" placeholder="请选择在线状态" clearable>
          <el-option
            v-for="dict in dict.type.monitoring_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="医生电话" prop="dPhone">
        <el-input
          v-model="queryParams.dPhone"
          placeholder="请输入医生电话"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="社区医生" prop="doctorPhone">
        <el-select v-model="queryParams.doctorPhone" placeholder="请选择社区医生" >
          <el-option
            v-for="item in option2"
            :key="item.doctorId"
            :label="item.doctorName"
            :value="item.doctorPhone">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
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
          v-hasPermi="['DECGsingle:delete:report']"
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
          v-hasPermi="['DECGsingle:export:report']"
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
      <right-toolbar :showSearch.sync="showSearch" @queryTable="refreshList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="patient_managementList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>

      <el-table-column label="连接时间" align="center" prop="connectionTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.connectionTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="患者名称" align="center" prop="patientName">
        <template slot-scope="scope">
          <span v-if="isShowName.status===true">{{scope.row.patientName}}</span>
          <span v-else>***</span>
        </template>
      </el-table-column>
      <!--            <el-table-column label="患者身份证号" align="center" prop="patientCode" />
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
                  <el-table-column label="医院名称" align="center" prop="hospitalName" width="150"/>-->
<!--      <el-table-column label="医院名称" align="center" prop="hospitalName"/>-->
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
            <el-form-item label="患者管理Id" width="200" style="padding-left: 40px">
              <span>{{ scope.row.pId }}</span>
            </el-form-item>
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
            <el-form-item label="医生电话" width="200" style="padding-left: 40px">
              <span>{{ scope.row.doctorPhone }}</span>
            </el-form-item>
<!--            <el-form-item label="医院名称" width="200" style="padding-left: 40px">
              <span>{{ scope.row.hospitalName }}</span>
            </el-form-item>-->

          </el-form>
        </template>
      </el-table-column>



      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-loading"
            @click="monitoring(scope.row)"
            v-hasPermi="['DECGsingle:monitor:monitor']"
          >实时监测
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-download"
            @click="handleInform(scope.row)"
            v-hasPermi="['DECGsingle:create:report']"
          >生成报告
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-magic-stick"
            @click="downloadInform(scope.row)"
            v-hasPermi="['DECGsingle:find:report']"
          >查看报告
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-download"
            @click="downloadData(scope.row)"
            v-hasPermi="['DECGsingle:download:report']"
          >下载数据</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-s-order"
            @click="handleAlert(scope.row)"
            v-hasPermi="['DECGsingle:log:list']"
          >预警日志
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-s-data"
            @click="DRyujing(scope.row)"
            v-hasPermi="['DECGsingle:log:count']"
          >预警统计
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
            v-hasPermi="['DECGsingle:delete:report']"
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
  updatePatient_management, updateStatus, getUserInfo, getEcgType
} from "@/api/patient_management/patient_management";
import axios from "axios";
import $ from "jquery";
import {getToken} from "@/utils/auth";
import {updateMonitoringStatus} from "@/api/patient/patient";
import {listEquipment, updateEquipmentStatus} from "@/api/equipment/equipment";
import {updateOnlineAll} from "@/api/online/online";
import pdf from "vue-pdf"
import {listHospitalId} from "@/api/hospital/hospital";
import {getVerify} from "@/api/verify/verify";
import {docList} from "@/api/doctor/doctor";

export default {
  name: "DECGsingle",
  dicts: ['if', 'sex', 'monitoring_status', 'ecg_type', 'hospital_name_name_list'],
  data() {
    return {
      //滚动条
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
      // 总条数
      total: 0,
      options: [],
      // 患者管理表格数据
      patient_managementList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      isShowName:{
        status:false,
        name:"显示姓名"
      },
      // 时间范围
      daterangeConnectionTime: [],
      ecgList:[],
      option2:[],
      ecgType:'DECGsingle',
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        pId: null,
        hospitalCode: null,
        equipmentCode: null,
        connectionTime: null,
        patientName: null,
        patientPhone: null,
        patientCode: null,
        ecgType: null,
        doctorPhone: null,
        patientSex:null
      },
      verifyForm:{
        password:null,
        status:false
      },
      dialogFormVisibleVerifyAuthority:false,
      // isRed1: false,
      // isRed2: false,
      // isRed3: false,
      // isRed4: false,
      // isRed5: false,
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
    if (this.$route.params.patientName) {
      this.queryParams.patientName = this.$route.params.patientName;
      this.queryParams.patientPhone = this.$route.params.patientPhone;
    }
    this.getList();
  },
  // activated() {
  //   if (this.$route.params.patientName) {
  //     this.queryParams.patientName = this.$route.params.patientName;
  //     this.queryParams.patientPhone = this.$route.params.patientPhone;
  //   }
  //   this.getList();
  // },
  methods: {
    refreshList() {
      console.log("refresh======")
      updateOnlineAll().then(res => {
        this.getList();
      })
    },

    DRyujing(row){
      this.$router.push({path: "/DRearly" , query: {id: row.pId,type:'single'}});
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
        if ( this.queryParams.ecgType==='DECGsingle'){
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
        hospitalCode: null,
        equipmentCode: null,
        connectionTime: null,
        patientName: null,
        patientPhone: null,
        patientCode: null,
        ecgType: null,
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
      this.queryParams= {
          pId: null,
          hospitalCode: null,
          equipmentCode: null,
          connectionTime: null,
          patientName: null,
          patientPhone: null,
          patientCode: null,
          ecgType: null,
          doctorPhone: null,
          patientSex:null
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
            sessionStorage.setItem('isShowName',true)
            this.isShowName.status =!this.isShowName.status;
            this.isShowName.name = "隐藏姓名"
          })
        }
      })
    },

    downloadData(row){
      window.open(`https://ecg.mindyard.cn:84/DECGReport/DECG_single/save/${row.pId}.dat`)
    },

    isShowNameClick(){
      let isShowName =  sessionStorage.getItem('isShowName')
      if (this.verifyForm.status || isShowName){
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
    /** 删除按钮操作 */
    handleDelete(row) {
      let isShowName =  sessionStorage.getItem('isShowName')
      if (this.verifyForm.status || isShowName){
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
      let isShowName =  sessionStorage.getItem('isShowName')
      if (this.verifyForm.status || isShowName){
        this.download('patient_management/patient_management/export', {
          ...this.queryParams
        }, `patient_management_${new Date().getTime()}.xlsx`)
      }else {
        this.verifyForm.password=''
        this.dialogFormVisibleVerifyAuthority = true
      }

    },
    /** 跳转到预警日志*/
    handleAlert(row) {
      console.log(row)
      this.$router.push({
        path: "log",
        query: {pId: row.pId}});
    },
    /** 跳转到心电图实时监测*/
    monitoring(row) {
      console.log("在线状态：",row.onlineStatus)
      if(row.onlineStatus==0){
        this.$modal.msgError("设备不在线！");
      }
      else{
        // this.$router.push({path: "/monitoring", query: {equipmentCode: row.equipmentCode}});
        this.$router.push(
          {
            //添加需要传值到那个页面的路径
            path:'/Screen/detail',
            //携带需要传递的参数
            query:{deviceSn:row.equipmentCode}
          })
      }
    },
    /** 生成报告*/
    handleInform(row) {
      var name = row.patientName
      this.$confirm('是否生成'+name+'的报告', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        $.ajax({
          type: "post",
          url: "https://server.mindyard.cn:84/analysis_decg",
          contentType: "application/json",
          dataType: "json",
          data: JSON.stringify({
            pId: row.pId,
            ecg_type: row.ecgType
          }),
          beforeSend:function (request) {
            request.setRequestHeader("user","zzu");
            request.setRequestHeader("password","zzu123");
          },
          success: function (data) {
            console.log(data)
            // let code=data.code;
            // switch (code){
            //   case '300':
            //     alert(name+"的报告生成成功！");
            //     break;
            //   case '301':
            //     alert("请求参数错误！");
            //     break;
            //   case '302':
            //     alert("采集数据分析错误！");
            //     break;
            //   case '303':
            //     alert("采集时长过短！");
            //     break;
            //   default:
            //     alert("请求生成报告失败！");
            // }
          },
          error: function (data) {
            console.log(name + "动态心电报告生成出错,请重新请求或联系管理员")
          }
        })
        this.$message({
          type: 'info',
          message: '正在生成中, 稍后将通过消息提醒您。'
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        });
      });
    },
    /** 查看报告*/
    downloadInform(row) {
      // let routeUrl = this.$router.resolve({path: "/ExportPDF", query: {pId: row.pId, hospitalName: row.hospitalName ,ecg_type:row.ecgType, patientName:row.patientName}});
      // window.open(routeUrl.href, '_blank');
      // row.pId="ZHIZIsingle_D29987E8555A-1678663291710";
      // let routeUrl = this.$router.resolve({path: "/DECGReport/DECG_single/report/"+row.pId+"/"+row.pId+".pdf"});
      // window.open(routeUrl.href, '_blank');
      // // this.$router.push("/DECGReport/DECG_single/"+row.pId+"/"+row.pId+".pdf");
      this.$router.push({path: "/lookPdf", query: {pId: row.pId,ecgType:"DECG_single"}});
    }
  }
};
</script>

<style type="text/css">
.red {
  color: red;
}

.blue {
  color: blue;
}
</style>
