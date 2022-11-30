<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="心电id" prop="pId">
        <el-input
          v-model="queryParams.pId"
          placeholder="请输入心电id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="诊断状态" prop="diagnosisStatus">
        <el-select v-model="queryParams.diagnosisStatus" placeholder="请选择诊断状态" clearable>
          <el-option
            v-for="dict in dict.type.diagnosis_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="诊断结论" prop="diagnosisConclusion">
        <el-input
          v-model="queryParams.diagnosisConclusion"
          placeholder="请输入诊断结论"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="诊断医生" prop="diagnosisDoctor">
        <el-input
          v-model="queryParams.diagnosisDoctor"
          placeholder="请输入诊断医生"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="报告时间">
        <el-date-picker
          v-model="daterangeReportTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
<!--      <el-form-item label="报告种类" prop="reportType">
        <el-select v-model="queryParams.reportType" placeholder="请选择报告种类" clearable>
          <el-option
            v-for="dict in dict.type.ecg_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>-->
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
          v-hasPermi="['report:report:add']"
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
          v-hasPermi="['report:report:edit']"
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
          v-hasPermi="['report:report:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['report:report:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="reportList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="报告id" align="center" prop="reportId" />
      <el-table-column label="心电id" align="center" prop="pId" />
      <el-table-column label="诊断状态" align="center" prop="diagnosisStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.diagnosis_status" :value="scope.row.diagnosisStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="诊断结论" align="center" prop="diagnosisConclusion" />
      <el-table-column label="诊断医生" align="center" prop="diagnosisDoctor" />
      <el-table-column label="报告时间" align="center" prop="reportTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.reportTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="智能诊断" align="center" prop="intelligentDiagnosis" show-overflow-tooltip/>
      <el-table-column label="报告种类" align="center" prop="reportType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ecg_type" :value="scope.row.reportType"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['report:report:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['report:report:remove']"
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

    <!-- 添加或修改报告对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="心电id" prop="pId">
          <el-input v-model="form.pId" placeholder="请输入心电id" />
        </el-form-item>
        <el-form-item label="诊断状态">
          <el-radio-group v-model="form.diagnosisStatus">
            <el-radio
              v-for="dict in dict.type.diagnosis_status"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="诊断结论" prop="diagnosisConclusion">
          <el-input v-model="form.diagnosisConclusion" placeholder="请输入诊断结论" />
        </el-form-item>
        <el-form-item label="诊断医生" prop="diagnosisDoctor">
          <el-input v-model="form.diagnosisDoctor" placeholder="请输入诊断医生" />
        </el-form-item>
        <el-form-item label="报告时间" prop="reportTime">
          <el-date-picker clearable
                          v-model="form.reportTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择报告时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="报告种类" prop="reportType">
          <el-select v-model="form.reportType" placeholder="请选择报告种类">
            <el-option
              v-for="dict in dict.type.ecg_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="智能诊断" prop="intelligentDiagnosis">
          <el-input v-model="form.intelligentDiagnosis" placeholder="请输入智能诊断" />
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
import { listReport, getReport, delReport, addReport, updateReport } from "@/api/report/report";

export default {
  name: "Report",
  dicts: ['diagnosis_status', 'ecg_type'],
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
      // 报告表格数据
      reportList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 报告种类时间范围
      daterangeReportTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        pId: null,
        diagnosisStatus: null,
        diagnosisConclusion: null,
        diagnosisDoctor: null,
        reportTime: null,
        reportType: 'ECG',
        intelligentDiagnosis: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        pId: [
          { required: true, message: "动态/静态心电id不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询报告列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeReportTime && '' != this.daterangeReportTime) {
        this.queryParams.params["beginReportTime"] = this.daterangeReportTime[0];
        this.queryParams.params["endReportTime"] = this.daterangeReportTime[1];
      }
      listReport(this.queryParams).then(response => {
        this.reportList = response.rows;
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
        reportId: null,
        pId: null,
        diagnosisStatus: "0",
        diagnosisConclusion: null,
        diagnosisDoctor: null,
        reportTime: null,
        reportType: 'ECG',
        intelligentDiagnosis: null
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
      this.daterangeReportTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.reportId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加报告";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const reportId = row.reportId || this.ids
      getReport(reportId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改报告";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.reportId != null) {
            updateReport(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addReport(this.form).then(response => {
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
      const reportIds = row.reportId || this.ids;
      this.$modal.confirm('是否确认删除报告编号为"' + reportIds + '"的数据项？').then(function() {
        return delReport(reportIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('report/report/export', {
        ...this.queryParams
      }, `report_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
