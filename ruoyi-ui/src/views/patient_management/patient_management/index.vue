<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">

      <el-form-item label="患者姓名" prop="patient.patientName">
        <el-input
          v-model="queryParams.patient.patientName"
          placeholder="请输入患者姓名"
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
      <el-form-item label="医院代号" prop="hospitalCode">
        <el-input
          v-model="queryParams.hospitalCode"
          placeholder="请输入医院代号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备号" prop="equipmentCode">
        <el-input
          v-model="queryParams.equipmentCode"
          placeholder="请输入设备号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="连接时间" prop="connectionTime">
        <el-date-picker clearable
                        v-model="queryParams.connectionTime"
                        type="date"
                        value-format="yyyy-MM-dd HH:mm:ss"
                        placeholder="请选择连接时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['patient_management:patient_management:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['patient_management:patient_management:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['patient_management:patient_management:remove']"
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
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="patient_managementList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="患者管理id" align="center" prop="pId"/>
      <el-table-column label="患者姓名" align="center" prop="patient.patientName"/>
      <!--      <el-table-column label="患者身份证号" align="center" prop="patientCode" />-->
      <el-table-column label="患者年龄" align="center" prop="patient.patientAge"/>
      <el-table-column label="患者性别" align="center" prop="patient.patientSex">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sex" :value="scope.row.patient.patientSex"/>
        </template>
      </el-table-column>
      <el-table-column label="患者来源" align="center" prop="patient.patientSource"/>
      <el-table-column label="患者电话" align="center" prop="patient.patientPhone"/>
      <el-table-column label="家属电话" align="center" prop="patient.familyPhone"/>
      <el-table-column label="监测状态" align="center" prop="patient.monitoringStatus"/>
      <!--      <el-table-column label="床位号" align="center" prop="bedNumber" />
            <el-table-column label="病历号" align="center" prop="caseHistoryNumber" />-->
      <el-table-column label="医院代号" align="center" prop="hospitalCode"/>
      <el-table-column label="医院名称" align="center" prop="hospital.hospitalName"/>
      <el-table-column label="设备号" align="center" prop="equipmentCode"/>
      <el-table-column label="连接时间" align="center" prop="connectionTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.connectionTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-download"
            @click="handleInform(scope.row)"
            v-hasPermi="['patient:patient:downloadInform']"
          >生成报告
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-magic-stick"
            @click="downloadInform(scope.row)"
            v-hasPermi="['patient:patient:inform']"
          >下载报告
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-s-order"
            @click="handleAlert(scope.row)"
            v-hasPermi="['patient:patient:alert']"
          >查看预警日志
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['patient_management:patient_management:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['patient_management:patient_management:remove']"
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
        <el-form-item label="患者身份证号" prop="patientCode">
          <el-input v-model="form.patientCode" placeholder="请输入患者身份证号"/>
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
                          type="date"
                          value-format="yyyy-MM-dd HH:mm:ss"
                          placeholder="请选择连接时间">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
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
  updatePatient_management
} from "@/api/patient_management/patient_management";
import axios from "axios";
import {getPatientByPatientName, getPatientByPatientNumber} from "@/api/patient/patient";

export default {
  name: "Patient_management",
  dicts: ['if', 'sex'],
  data() {
    return {
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
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        patientCode: null,
        hospitalCode: null,
        equipmentCode: null,
        connectionTime: null,
        patient: {
          patientName: null,
        }

      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        patientCode: [
          {required: true, message: "患者身份证号不能为空", trigger: "blur"}
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
  created() {
    this.getList();
  },
  methods: {
    /** 查询患者管理列表 */
    getList() {
      this.loading = true;
      console.log(this.queryParams);

      listPatient_management(this.queryParams).then(response => {
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
        patientName: null,
        pId: null,
        patientCode: null,
        hospitalCode: null,
        equipmentCode: null,
        connectionTime: null
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
    /** 跳转到预警日志*/
    handleAlert(row) {
      this.$router.push({path: "/alert_log", query: {pId: row.pId}});
    },
    /** 生成报告*/
    handleInform(row) {
      this.$router.push({path: "/ExportPDF", query: {pId: row.pId}});

    },
    /** 下载报告*/
    downloadInform(row) {

      // window.open("http://localhost:83/dev-api/download/test.pdf?token=" + this.getToken(), '_blank');

      // axios.get('http://localhost:83/dev-api/download/test.pdf',
      //   {responseType: 'blob'}
      // ).then((res)=>{
      //   console.log('文件下载成功');
      //   const blob = new Blob([res.data]);
      //   const fileName = "test.pdf";
      //
      //   if ('download' in document.createElement('a')) {
      //
      //     const link = document.createElement('a');//创建a标签
      //     link.download = fileName;//a标签添加属性
      //     link.style.display = 'none';
      //     link.href = URL.createObjectURL(blob);
      //     document.body.appendChild(link);
      //     link.click();//执行下载
      //     URL.revokeObjectURL(link.href); //释放url
      //     document.body.removeChild(link);//释放标签
      //   } else {
      //     navigator.msSaveBlob(blob, fileName);
      //   }
      // }).catch((res)=>{
      //   console.log('文件下载失败');
      // });


    }
  }
};
</script>
