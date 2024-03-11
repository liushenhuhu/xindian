<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="识别码" prop="headingCode">
        <el-input
          v-model="queryParams.headingCode"
          placeholder="请输入识别码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="sn码" prop="equipmentCode">
        <el-input
          v-model="queryParams.equipmentCode"
          placeholder="请输入sn码"
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
          v-hasPermi="['headingCode:headingCode:add']"
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
          v-hasPermi="['headingCode:headingCode:edit']"
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
          v-hasPermi="['headingCode:headingCode:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['headingCode:headingCode:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="headingCodeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="识别码" align="center" prop="headingCode" />
      <el-table-column label="sn码" align="center" prop="equipmentCode" />
      <el-table-column label="类型" align="center" prop="type" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.type == 0">公共</el-tag>
          <el-tag type="success" v-if="scope.row.type == 1">个人</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['headingCode:headingCode:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['headingCode:headingCode:remove']"
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

    <!-- 添加或修改善行设备管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="识别码" prop="headingCode">
          <el-input v-model="form.headingCode" placeholder="请输入识别码" />
        </el-form-item>
        <el-form-item label="sn码" prop="equipmentCode">
          <el-input v-model="form.equipmentCode" placeholder="请输入sn码" />
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-select v-model="form.type" clearable placeholder="请选择类型">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
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
import { listHeadingCode, getHeadingCode, delHeadingCode, addHeadingCode, updateHeadingCode } from "@/api/headingCode/headingCode";

export default {
  name: "HeadingCode",
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
      // 善行设备管理表格数据
      headingCodeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        headingCode: null,
        equipmentCode: null,
        type: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        headingCode: [
          { required: true, message: "识别码不能为空", trigger: "blur" }
        ],
        equipmentCode: [
          { required: true, message: "sn码不能为空", trigger: "blur" }
        ],
      },
      options: [{
        value: '0',
        label: '公共'
      }, {
        value: '1',
        label: '个人'
      }],
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询善行设备管理列表 */
    getList() {
      this.loading = true;
      listHeadingCode(this.queryParams).then(response => {
        this.headingCodeList = response.rows;
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
        codeId: null,
        headingCode: null,
        equipmentCode: null,
        type: null
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
      this.ids = selection.map(item => item.codeId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加善行设备管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const codeId = row.codeId || this.ids
      getHeadingCode(codeId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改善行设备管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.codeId != null) {
            updateHeadingCode(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addHeadingCode(this.form).then(response => {
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
      const codeIds = row.codeId || this.ids;
      this.$modal.confirm('是否确认删除善行设备管理编号为"' + codeIds + '"的数据项？').then(function() {
        return delHeadingCode(codeIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('headingCode/headingCode/export', {
        ...this.queryParams
      }, `headingCode_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
