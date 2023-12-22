<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="98px">
      <el-form-item label="患者管理id" prop="pId">
        <el-input
          v-model="queryParams.pId"
          placeholder="请输入患者管理id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="事件名称" prop="eventName">
        <el-input
          v-model="queryParams.eventName"
          placeholder="请输入事件名称"
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
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="primary"-->
<!--          plain-->
<!--          icon="el-icon-plus"-->
<!--          size="mini"-->
<!--          @click="handleAdd"-->
<!--          v-hasPermi="['label:audit:add']"-->
<!--        >新增</el-button>-->
<!--      </el-col>-->
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="success"-->
<!--          plain-->
<!--          icon="el-icon-edit"-->
<!--          size="mini"-->
<!--          :disabled="single"-->
<!--          @click="handleUpdate"-->
<!--          v-hasPermi="['label:audit:edit']"-->
<!--        >修改</el-button>-->
<!--      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['label:audit:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['label:audit:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="auditList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="日志id" align="center" prop="logId" />
<!--      <el-table-column label="噪声" align="center" prop="logNoise" />-->
      <el-table-column label="噪声等级" align="center" prop="logNoiseLevel" />
      <el-table-column label="患者管理id" align="center" prop="pId" />
<!--      <el-table-column label="账号id" align="center" prop="userId" />-->
      <el-table-column label="预警类型" align="center" prop="logType" />
<!--      <el-table-column label="事件名称" align="center" prop="eventName" />-->
<!--      <el-table-column label="事件说明" align="center" prop="eventDescription" />-->
      <el-table-column label="是否标注" align="center" prop="anoStatus" />
<!--      <el-table-column label="数据标签" align="center" prop="dataLabel" />-->
<!--      <el-table-column label="点标签" align="center" prop="pointLabel" />-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-edit"-->
<!--            @click="handleUpdate(scope.row)"-->
<!--            v-hasPermi="['label:audit:edit']"-->
<!--          >修改</el-button>-->
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['label:audit:remove']"
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

    <!-- 添加或修改标注数据审核对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="噪声" prop="logNoise">
          <el-input v-model="form.logNoise" placeholder="请输入噪声" />
        </el-form-item>
        <el-form-item label="噪声等级" prop="logNoiseLevel">
          <el-input v-model="form.logNoiseLevel" placeholder="请输入噪声等级" />
        </el-form-item>
        <el-form-item label="患者管理id" prop="pId">
          <el-input v-model="form.pId" placeholder="请输入患者管理id" />
        </el-form-item>
        <el-form-item label="账号id" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入账号id" />
        </el-form-item>
        <el-form-item label="事件名称" prop="eventName">
          <el-input v-model="form.eventName" placeholder="请输入事件名称" />
        </el-form-item>
        <el-form-item label="事件说明" prop="eventDescription">
          <el-input v-model="form.eventDescription" placeholder="请输入事件说明" />
        </el-form-item>
        <el-form-item label="数据标签" prop="dataLabel">
          <el-input v-model="form.dataLabel" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="点标签" prop="pointLabel">
          <el-input v-model="form.pointLabel" type="textarea" placeholder="请输入内容" />
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
import { listAudit, getAudit, delAudit, addAudit, updateAudit } from "@/api/label/audit";

export default {
  name: "Audit",
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
      // 标注数据审核表格数据
      auditList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        logNoise: null,
        logNoiseLevel: null,
        pId: null,
        userId: null,
        logType: null,
        eventName: null,
        eventDescription: null,
        anoStatus: null,
        dataLabel: null,
        pointLabel: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询标注数据审核列表 */
    getList() {
      this.loading = true;
      listAudit(this.queryParams).then(response => {
        this.auditList = response.rows;
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
        logId: null,
        logNoise: null,
        logNoiseLevel: null,
        pId: null,
        userId: null,
        logType: null,
        eventName: null,
        eventDescription: null,
        anoStatus: 0,
        dataLabel: null,
        pointLabel: null
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
      this.ids = selection.map(item => item.logId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加标注数据审核";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const logId = row.logId || this.ids
      getAudit(logId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改标注数据审核";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.logId != null) {
            updateAudit(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAudit(this.form).then(response => {
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
      const logIds = row.logId || this.ids;
      this.$modal.confirm('是否确认删除标注数据审核编号为"' + logIds + '"的数据项？').then(function() {
        return delAudit(logIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('label/audit/export', {
        ...this.queryParams
      }, `audit_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
