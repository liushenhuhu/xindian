<template>
  <div class="app-container">

    <el-row :gutter="10" class="mb8">
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
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="patient_managementList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="上传时间" align="center" prop="connectionTime" width="95">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.connectionTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>

      <el-table-column label="智能诊断" align="center" prop="intelligentDiagnosis" show-overflow-tooltip/>
      <el-table-column label="患者姓名" align="center" prop="patientName" />
      <el-table-column label="诊断状态" align="center" prop="diagnosisStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.diagnosis_status" :value="scope.row.diagnosisStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="诊断结论" align="center" prop="diagnosisConclusion" show-overflow-tooltip/>
      <el-table-column label="诊断医生" align="center" prop="diagnosisDoctor"/>
      <el-table-column label="诊断时间（分钟）" align="center" prop="avgTime"/>
      <el-table-column label="报告时间" align="center" prop="reportTime" width="95" >
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
            <el-form-item label="患者管理id" width="200" style="padding-left: 40px">
              <span>{{ scope.row.pId }}</span>
            </el-form-item>
            <el-form-item label="患者姓名" width="200" style="padding-left: 40px">
              <span>{{ scope.row.patientName }}</span>
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

  </div>
</template>

<script>
import {
  listPatient_management,
  getPatient_management,
  delPatient_management,
  addPatient_management,
  updatePatient_management, updateStatus, getUserInfo, sendMsgToPatient, listPatient_TimeList
} from "@/api/patient_management/patient_management";
import axios from "axios";
import $ from "jquery";
import {updateEquipmentStatus} from "@/api/equipment/equipment";
import {updateOnlineAll} from "@/api/online/online";

export default {
  name: "index",
  dicts: ['if', 'sex', 'monitoring_status', 'ecg_type', 'diagnosis_status', 'ecg_level', 'hospital_name_name_list'],
  data() {
    return {
      labelPosition:'left',
      cil: 0,
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
      // 患者管理表格数据
      patient_managementList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 时间范围
      daterangeConnectionTime: [],
      //报告时间范围
      daterangeReportTime: [],
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
        patientPhone: null,
        intelligentDiagnosis: null,
        diagnosisStatus: 1,
        diagnosisConclusion: null,
        diagnosisDoctor: null,
        reportTime: null,
        ecgLevel: null,
        doctorPhone: null,
        countTime:null,
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

    this.queryParams.countTime=this.$route.query.countTime
    this.queryParams.doctorPhone=this.$route.query.doctorPhone
    this.queryParams.ecgType = this.$route.query.ecgtype
    this.getList();
  },
  methods: {

    refreshList() {
      console.log("refresh======")
      updateOnlineAll().then(res => {
        this.getList();
      })
    },

    sendMsg(row){
      const reg = /^1[3|4|5|7|8][0-9]\d{8}$/;
      var isPhoneNumberValid = reg.test(row.patientPhone);
      if(isPhoneNumberValid) {
        console.log("患者电话: " + row.patientPhone)
        console.log("患者姓名: " + row.patientName)
        this.$confirm('向该患者发送短信提示采集存在较大干扰?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          sendMsgToPatient(row.patientPhone).then(response => {
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
      listPatient_TimeList(this.queryParams).then(response => {
        console.log(response)
        this.patient_managementList = response.rows;
        this.total = response.total;
        this.loading = false;
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
        doctorPhone: null
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
      this.resetForm("queryForm");
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
    /** 删除按钮操作 */
    handleDelete(row) {
      const pIds = row.pId || this.ids;
      this.$modal.confirm('是否确认删除患者管理编号为"' + pIds + '"的数据项？').then(function () {
        return delPatient_management(pIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('patient_management/patient_management/export', {
        ...this.queryParams
      }, `patient_management_${new Date().getTime()}.xlsx`)
    },
    /** 查看心电图*/
    lookECG(row) {
      let a = row.ecgType.substring(0,5)
      if (a==='JECG1'){
        this.$router.push({path: "/restingECG", query: {pId: row.pId}});
      }else if (a==='JECGs'){
        this.$router.push({path: "/staticECG", query: {pId: row.pId,}});
      }else if (a==="JECG4"){
        this.$router.push({path: "/JECG4_ECG", query: {pId: row.pId,}})
      }else {
        this.$modal.msgError("类型不匹配，请稍后再试");
      }
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
