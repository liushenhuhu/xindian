<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="省份" prop="province">
        <el-input
          v-model="queryParams.province"
          placeholder="请输入省份"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="医院名称" prop="hospitalName">
        <el-input
          v-model="queryParams.hospitalName"
          placeholder="请输入医院名称"
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
      <el-form-item label="医院账号" prop="hospitalAccount">
        <el-input
          v-model="queryParams.hospitalAccount"
          placeholder="请输入医院账号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="医院密码" prop="hospitalPassword">
        <el-input
          v-model="queryParams.hospitalPassword"
          placeholder="请输入医院密码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备数量" prop="equipmentNumber">
        <el-input
          v-model="queryParams.equipmentNumber"
          placeholder="请输入设备数量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="患者总数" prop="patientNumber">
        <el-input
          v-model="queryParams.patientNumber"
          placeholder="请输入患者总数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="正在监测患者数" prop="monitoringPatientNumber">
        <el-input
          v-model="queryParams.monitoringPatientNumber"
          placeholder="请输入正在监测患者数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="账号总数" prop="accountNumber">
        <el-input
          v-model="queryParams.accountNumber"
          placeholder="请输入账号总数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="首次收到心电数据时间" prop="firstEcgTime">
        <el-date-picker clearable
                        v-model="queryParams.firstEcgTime"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择首次收到心电数据时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="是否开通数据统计" prop="ifStatistics">
        <el-select v-model="queryParams.ifStatistics" placeholder="请选择是否开通数据统计" clearable>
          <el-option
            v-for="dict in dict.type.if"
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
          v-hasPermi="['hospital:hospital:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['hospital:hospital:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['hospital:hospital:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['hospital:hospital:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="hospitalList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="医院id" align="center" prop="hospitalId" />
      <el-table-column label="省份" align="center" prop="province" />
      <el-table-column label="医院名称" align="center" prop="hospitalName" />
      <el-table-column label="医院代号" align="center" prop="hospitalCode" />
      <el-table-column label="医院账号" align="center" prop="hospitalAccount" />
      <el-table-column label="医院密码" align="center" prop="hospitalPassword" />
      <el-table-column label="设备数量" align="center" prop="equipmentNumber" />
      <el-table-column label="患者总数" align="center" prop="patientNumber" />
      <el-table-column label="正在监测患者数" align="center" prop="monitoringPatientNumber" />
      <el-table-column label="账号总数" align="center" prop="accountNumber" />
      <el-table-column label="首次收到心电数据时间" align="center" prop="firstEcgTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.firstEcgTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="是否开通数据统计" align="center" prop="ifStatistics">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.if" :value="scope.row.ifStatistics"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['hospital:hospital:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['hospital:hospital:remove']"
          >删除</el-button>
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

    <!-- 添加或修改医院对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="省份" prop="province">
          <el-input v-model="form.province" placeholder="请输入省份" />
        </el-form-item>
        <el-form-item label="医院名称" prop="hospitalName">
          <el-input v-model="form.hospitalName" placeholder="请输入医院名称" />
        </el-form-item>
        <el-form-item label="医院代号" prop="hospitalCode">
          <el-input v-model="form.hospitalCode" placeholder="请输入医院代号" />
        </el-form-item>
        <el-form-item label="医院账号" prop="hospitalAccount">
          <el-input v-model="form.hospitalAccount" placeholder="请输入医院账号" />
        </el-form-item>
        <el-form-item label="医院密码" prop="hospitalPassword">
          <el-input v-model="form.hospitalPassword" placeholder="请输入医院密码" />
        </el-form-item>
        <el-form-item label="设备数量" prop="equipmentNumber">
          <el-input v-model="form.equipmentNumber" placeholder="请输入设备数量" />
        </el-form-item>
        <el-form-item label="患者总数" prop="patientNumber">
          <el-input v-model="form.patientNumber" placeholder="请输入患者总数" />
        </el-form-item>
        <el-form-item label="正在监测患者数" prop="monitoringPatientNumber">
          <el-input v-model="form.monitoringPatientNumber" placeholder="请输入正在监测患者数" />
        </el-form-item>
        <el-form-item label="账号总数" prop="accountNumber">
          <el-input v-model="form.accountNumber" placeholder="请输入账号总数" />
        </el-form-item>
        <el-form-item label="首次收到心电数据时间" prop="firstEcgTime">
          <el-date-picker clearable
                          v-model="form.firstEcgTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择首次收到心电数据时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="是否开通数据统计">
          <el-radio-group v-model="form.ifStatistics">
            <el-radio
              v-for="dict in dict.type.if"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
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
import { listHospital, getHospital, delHospital, addHospital, updateHospital } from "@/api/hospital/hospital";

export default {
  name: "Hospital",
  dicts: ['if'],
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
      showSearch: true,
      // 总条数
      total: 0,
      // 医院表格数据
      hospitalList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        province: null,
        hospitalName: null,
        hospitalCode: null,
        hospitalAccount: null,
        hospitalPassword: null,
        equipmentNumber: null,
        patientNumber: null,
        monitoringPatientNumber: null,
        accountNumber: null,
        firstEcgTime: null,
        ifStatistics: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        hospitalName: [
          { required: true, message: "医院名称不能为空", trigger: "blur" }
        ],
        hospitalCode: [
          { required: true, message: "医院代号不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询医院列表 */
    getList() {
      this.loading = true;
      listHospital(this.queryParams).then(response => {
        this.hospitalList = response.rows;
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
        hospitalId: null,
        province: null,
        hospitalName: null,
        hospitalCode: null,
        hospitalAccount: null,
        hospitalPassword: null,
        equipmentNumber: null,
        patientNumber: null,
        monitoringPatientNumber: null,
        accountNumber: null,
        firstEcgTime: null,
        ifStatistics: "0"
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
      this.ids = selection.map(item => item.hospitalId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加医院";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const hospitalId = row.hospitalId || this.ids
      getHospital(hospitalId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改医院";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.hospitalId != null) {
            updateHospital(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.form.hospitalId = this.form.hospitalCode
            addHospital(this.form).then(response => {
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
      const hospitalIds = row.hospitalId || this.ids;
      this.$modal.confirm('是否确认删除医院编号为"' + hospitalIds + '"的数据项？').then(function() {
        return delHospital(hospitalIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('hospital/hospital/export', {
        ...this.queryParams
      }, `hospital_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
