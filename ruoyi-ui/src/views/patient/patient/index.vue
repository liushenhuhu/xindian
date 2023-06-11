<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="患者姓名" prop="patientName">
        <el-input
          v-model="queryParams.patientName"
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
      <el-form-item label="患者年龄" prop="patientAge">
        <el-input
          v-model="queryParams.patientAge"
          placeholder="请输入患者年龄"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="患者性别" prop="patientSex">
        <el-select v-model="queryParams.patientSex" placeholder="请选择患者性别" clearable>
          <el-option
            v-for="dict in dict.type.sex"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="医院" prop="patientSource">
        <el-select v-model="queryParams.patientSource" placeholder="请选择医院" clearable>
          <el-option
            v-for="dict in dict.type.hospital_name_name_list"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
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
      <el-form-item label="家属电话" prop="familyPhone">
        <el-input
          v-model="queryParams.familyPhone"
          placeholder="请输入家属电话"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="最近连接设备号" prop="equipmentId">
        <el-input
          v-model="queryParams.equipmentId"
          placeholder="请输入最近连接设备号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="监测状态" prop="monitoringStatus">
        <el-select v-model="queryParams.monitoringStatus" placeholder="请选择监测状态" clearable>
          <el-option
            v-for="dict in dict.type.monitoring_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="绑定状态" prop="bindingState">
        <el-select v-model="queryParams.bindingState" placeholder="请选择绑定状态" clearable>
          <el-option
            v-for="dict in dict.type.binding_state"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
          v-hasPermi="['patient:patient:add']"
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
          v-hasPermi="['patient:patient:edit']"
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
          v-hasPermi="['patient:patient:remove']"
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
          v-hasPermi="['patient:patient:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="refreshList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="patientList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <!--      <el-table-column label="患者id" align="center" prop="patientId"/>-->
      <el-table-column label="患者姓名" align="center" prop="patientName"/>
      <!--      <el-table-column label="患者身份证号" align="center" prop="patientCode" />-->
      <el-table-column label="患者年龄" align="center" prop="patientAge"/>
      <el-table-column label="患者性别" align="center" prop="patientSex">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sex" :value="scope.row.patientSex"/>
        </template>
      </el-table-column>
      <el-table-column label="医院" align="center" prop="patientSource">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.hospital_name_name_list" :value="scope.row.patientSource"/>
        </template>
      </el-table-column>
      <el-table-column label="患者电话" align="center" prop="patientPhone"/>
      <el-table-column label="家属电话" align="center" prop="familyPhone"/>
      <el-table-column label="最近连接设备号" align="center" prop="equipmentId"/>
      <el-table-column label="监测状态" align="center" prop="monitoringStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.monitoring_status" :value="scope.row.monitoringStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="绑定状态" align="center" prop="bindingState">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.binding_state" :value="scope.row.bindingState"/>
        </template>
      </el-table-column>

      <!--  隐藏的患者的个人信息    -->
      <el-table-column type="expand">
        <template slot-scope="scope">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-divider content-position="left">其他信息</el-divider>

            <el-form-item label="患者身份证号" width="200" style="padding-left: 40px">
              <span>{{ scope.row.patientCode }}</span>
            </el-form-item>
            <el-form-item label="每日诊断次数" width="200" style="padding-left: 40px">
              <span>{{ scope.row.detectionNum }}</span>
            </el-form-item>
            <!--            <el-form-item label="医院" width="200" style="padding-left: 40px">
                          <span>{{ scope.row.patientSource }}</span>
                        </el-form-item>-->

          </el-form>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-s-order"
            @click="lookList(scope.row)"
            v-hasPermi="['patient:patient:lookList']"
          >监测列表
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['patient:patient:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['patient:patient:remove']"
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

    <!-- 添加或修改患者对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="患者姓名" prop="patientName">
          <el-input v-model="form.patientName" placeholder="请输入患者姓名"/>
        </el-form-item>
        <el-form-item label="患者电话" prop="patientPhone">
          <el-input v-model="form.patientPhone" placeholder="请输入患者电话"/>
        </el-form-item>

        <el-form-item label="患者年龄" prop="patientAge">
          <el-input v-model="form.patientAge" placeholder="请输入患者年龄"/>
        </el-form-item>
        <el-form-item label="患者性别" prop="patientSex">
          <el-select v-model="form.patientSex" placeholder="请选择患者性别">
            <el-option
              v-for="dict in dict.type.sex"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="医院" prop="patientSource">
          <el-select v-model="form.patientSource" placeholder="请选择医院">
            <el-option
              v-for="dict in dict.type.hospital_name_name_list"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="患者身份证号" prop="patientCode">
          <el-input v-model="form.patientCode" placeholder="请输入患者身份证号"/>
        </el-form-item>
        <el-form-item label="家属电话" prop="familyPhone">
          <el-input v-model="form.familyPhone" placeholder="请输入家属电话"/>
        </el-form-item>
        <el-form-item label="最近连接设备号" prop="equipmentId">
          <el-input v-model="form.equipmentId" placeholder="请输入最近连接设备号"/>
        </el-form-item>
        <el-form-item label="监测状态" prop="monitoringStatus">
          <el-select v-model="form.monitoringStatus" placeholder="请选择监测状态">
            <el-option
              v-for="dict in dict.type.monitoring_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="绑定状态" prop="bindingState">
          <el-select v-model="form.bindingState" placeholder="请选择绑定状态">
            <el-option
              v-for="dict in dict.type.binding_state"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="诊断次数" prop="detectionNum">
          <el-input v-model="form.detectionNum" placeholder="请输入每日诊断次数"/>
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
  listPatient,
  getPatient,
  delPatient,
  addPatient,
  updatePatient,
  updateMonitoringStatus, addDict
} from "@/api/patient/patient";
import $ from "jquery";
import {getUserInfo, updateStatus} from "@/api/patient_management/patient_management";
import {updateEquipmentStatus} from "@/api/equipment/equipment";
import {updateOnlineAll} from "@/api/online/online";

export default {
  name: "Patient",
  dicts: ['sex', 'monitoring_status', 'binding_state', 'hospital_name_name_list'],
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
      // 患者表格数据
      patientList: [],

      // 监测状态列表
      monitoringStatusList: [],

      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        patientName: null,
        patientCode: null,
        patientAge: null,
        patientSex: null,
        patientSource: null,
        patientPhone: null,
        familyPhone: null,
        equipmentId: null,
        monitoringStatus: null,
        bindingState: null,
        detectionNum:null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        patientName: [
          {required: true, message: "患者姓名不能为空", trigger: "blur"}
        ],
        patientPhone: [
          {required: true, message: "患者电话不能为空", trigger: "blur"}
        ],
        patientAge: [
          {required: true, message: "患者年龄不能为空", trigger: "blur"}
        ],
        patientSex: [
          {required: true, message: "患者性别不能为空", trigger: "change"}
        ],
        patientSource: [
          {required: true, message: "患者来源不能为空", trigger: "blur"}
        ],
      }
    };
  },

  beforeCreate() {
    updateOnlineAll();
  },

  created() {
    this.getList();
  },

  methods: {
    refreshList() {
      console.log("refresh======")
      updateOnlineAll().then(res => {
        this.getList();
      })
    },

    /** 查询患者列表 */
    getList() {
      this.loading = true;
      listPatient(this.queryParams).then(response => {
        this.patientList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
      addDict().then(res =>{
        console.log(res)
      })
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        patientId: null,
        patientName: null,
        patientCode: null,
        patientAge: null,
        patientSex: null,
        patientSource: null,
        patientPhone: null,
        familyPhone: null,
        equipmentId: null,
        monitoringStatus: null,
        bindingState: null,
        detectionNum:null
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
      this.ids = selection.map(item => item.patientId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加患者";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const patientId = row.patientId || this.ids
      getPatient(patientId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改患者";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.patientId != null) {
            updatePatient(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPatient(this.form).then(response => {
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
      const patientIds = row.patientId || this.ids;
      this.$modal.confirm('是否确认删除患者编号为"' + patientIds + '"的数据项？').then(function () {
        return delPatient(patientIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('patient/patient/export', {
        ...this.queryParams
      }, `patient_${new Date().getTime()}.xlsx`)
    },
    /** 跳转到动态心电列表 */
    lookList(row) {
      this.$router.push({
        name: "lookList",
        params: {patientPhone: row.patientPhone}
      });
    },


  }
};
</script>
