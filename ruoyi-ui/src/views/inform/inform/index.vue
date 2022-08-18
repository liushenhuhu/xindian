<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="报告号" prop="informNumber">
        <el-input
          v-model="queryParams.informNumber"
          placeholder="请输入报告号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="患者id" prop="patientId">
        <el-input
          v-model="queryParams.patientId"
          placeholder="请输入患者id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="开始遥测时间" prop="startTelemetryTime">
        <el-date-picker clearable
          v-model="queryParams.startTelemetryTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择开始遥测时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="报告日期" prop="informTime">
        <el-date-picker clearable
          v-model="queryParams.informTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择报告日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="报告状态" prop="informStatus">
        <el-input
          v-model="queryParams.informStatus"
          placeholder="请输入报告状态"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['inform:inform:add']"
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
          v-hasPermi="['inform:inform:edit']"
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
          v-hasPermi="['inform:inform:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['inform:inform:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="informList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="报告id" align="center" prop="informId" />
      <el-table-column label="报告号" align="center" prop="informNumber" />
      <el-table-column label="患者id" align="center" prop="patientId" />
      <el-table-column label="患者姓名" align="center" prop="patientName" />
      <el-table-column label="患者身份证号" align="center" prop="patientNumber" />
      <el-table-column label="患者年龄" align="center" prop="patientAge" />
      <el-table-column label="患者性别" align="center" prop="patientSex">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sex" :value="scope.row.patientSex"/>
        </template>
      </el-table-column>
      <el-table-column label="患者电话" align="center" prop="patientPhone" />
      <el-table-column label="床位号" align="center" prop="bedNumber" />
      <el-table-column label="病历号" align="center" prop="caseHistoryNumber" />
      <el-table-column label="患者来源" align="center" prop="patientSource" />
      <el-table-column label="医院代号" align="center" prop="hospitalCode" />
      <el-table-column label="医院名称" align="center" prop="hospitalName" />
      <el-table-column label="开始遥测时间" align="center" prop="startTelemetryTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTelemetryTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="报告日期" align="center" prop="informTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.informTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="报告状态" align="center" prop="informStatus" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['inform:inform:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['inform:inform:remove']"
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
        <el-form-item label="报告号" prop="informNumber">
          <el-input v-model="form.informNumber" placeholder="请输入报告号" :disabled="judge"/>
        </el-form-item>
        <el-form-item label="患者ID" prop="patientId">
          <el-input v-model="form.patientId" placeholder="请输入患者ID" />
        </el-form-item>
        <el-form-item label="开始遥测时间" prop="startTelemetryTime">
          <el-date-picker clearable
            v-model="form.startTelemetryTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择开始遥测时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="报告日期" prop="informTime">
          <el-date-picker clearable
            v-model="form.informTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择报告日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="报告状态" prop="informStatus">
          <el-input v-model="form.informStatus" placeholder="请输入报告状态" />
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
import { listInform, getInform, delInform, addInform, updateInform } from "@/api/inform/inform";

export default {
  name: "Inform",
  dicts: ['sex'],
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
      informList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //是否不可编辑
      judge: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        informNumber: null,
        patientId: null,
        startTelemetryTime: null,
        informTime: null,
        informStatus: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        informNumber: [
          { required: true, message: "报告号不能为空", trigger: "blur" }
        ],
        patientId: [
          { required: true, message: "患者ID不能为空", trigger: "blur" }
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
    /** 查询报告列表 */
    getList() {
      this.loading = true;
      listInform(this.queryParams).then(response => {
        this.informList = response.rows;
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
        informId: null,
        informNumber: null,
        patientId: null,
        startTelemetryTime: null,
        informTime: null,
        informStatus: null,
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
      this.ids = selection.map(item => item.informId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加报告";
      this.judge = false;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const informId = row.informId || this.ids
      getInform(informId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改报告";
        this.judge = true;
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.informId != null) {
            updateInform(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.form.informId = this.form.informNumber
            addInform(this.form).then(response => {
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
      const informIds = row.informId || this.ids;
      this.$modal.confirm('是否确认删除报告编号为"' + informIds + '"的数据项？').then(function() {
        return delInform(informIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('inform/inform/export', {
        ...this.queryParams
      }, `inform_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
