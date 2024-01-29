<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="日志id" prop="logId">
          <el-input
            v-model="queryParams.logId"
            placeholder="请输入日志id"
            clearable
            @keyup.enter.native="handleQuery"
          />
      </el-form-item>
<!--      <el-form-item label="诊断结果" prop="diagnosticResult">-->
<!--        <el-input-->
<!--          v-model="queryParams.diagnosticResult"-->
<!--          placeholder="请输入诊断结果"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
      <el-form-item label="是否标注" prop="isLabel">
        <el-select v-model="queryParams.isLabel" placeholder="请选择是否标注" clearable>
          <el-option
            v-for="dict in dict.type.if_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
<!--      <el-form-item label="标注时间" prop="labelTime">-->
<!--        <el-date-picker clearable-->
<!--                        v-model="queryParams.labelTime"-->
<!--                        type="date"-->
<!--                        value-format="yyyy-MM-dd"-->
<!--                        placeholder="请选择标注时间">-->
<!--        </el-date-picker>-->
<!--      </el-form-item>-->
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
<!--          v-hasPermi="['stLabel:stLabel:add']"-->
<!--        >新增</el-button>-->
<!--      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['stLabel:stLabel:edit']"
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
          v-hasPermi="['stLabel:stLabel:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['stLabel:stLabel:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="stLabelList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="日志id" align="center" prop="logId" />
<!--      <el-table-column label="心电图数据" align="center" prop="ecgData" />-->
      <el-table-column label="诊断结果" align="center" prop="diagnosticResult" width="450" :show-overflow-tooltip="true"/>
      <el-table-column label="标注 标签" align="center" prop="labelJson" :show-overflow-tooltip="true"/>
      <el-table-column label="是否标注" align="center" prop="isLabel" width="80">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.if_status" :value="scope.row.isLabel"/>
        </template>
      </el-table-column>
      <el-table-column label="标注时间" align="center" prop="labelTime" width="100">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.labelTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-s-promotion"
            @click="goDetails(scope.row.logId)"
            v-hasPermi="['stLabel:stLabel:edit']"
          >查看详情</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['stLabel:stLabel:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['stLabel:stLabel:remove']"
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

    <!-- 添加或修改ST标注对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="心电图数据" prop="ecgData">
          <el-input v-model="form.ecgData" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="诊断结果" prop="diagnosticResult">
          <el-input v-model="form.diagnosticResult" placeholder="请输入诊断结果" />
        </el-form-item>
        <el-form-item label="标签" prop="labelJson">
          <el-input v-model="form.labelJson" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="标注时间" prop="labelTime">
          <el-date-picker clearable
                          v-model="form.labelTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择标注时间">
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
import { listStLabel, getStLabel, delStLabel, addStLabel, updateStLabel } from "@/api/stLabel/stLabel";

export default {
  name: "StLabel",
  dicts: ['if_status'],
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
      // ST标注表格数据
      stLabelList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        logId:null,
        ecgData: null,
        diagnosticResult: null,
        labelJson: null,
        isLabel: null,
        labelTime: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      num:1,
    };
  },
  created() {
    this.getList();
  },
  activated() {
    if(this.num>1){
      this.getList()
    }
    this.num++
  },
  methods: {
    /** 查询ST标注列表 */
    getList() {
      this.loading = true;
      listStLabel(this.queryParams).then(response => {
        this.stLabelList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    goDetails(logId){
      this.$router.push({
        path: '/label/details',
        query: { logId: logId,pageNum: this.queryParams.pageNum,isLabel:this.queryParams.isLabel}})
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
        ecgData: null,
        diagnosticResult: null,
        labelJson: null,
        isLabel: null,
        labelTime: null
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
      this.title = "添加ST标注";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const logId = row.logId || this.ids
      getStLabel(logId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改ST标注";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.logId != null) {
            updateStLabel(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addStLabel(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除ST标注编号为"' + logIds + '"的数据项？').then(function() {
        return delStLabel(logIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('stLabel/stLabel/export', {
        ...this.queryParams
      }, `stLabel_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
