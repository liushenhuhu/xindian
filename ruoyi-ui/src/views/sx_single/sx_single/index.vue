<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="110px">
      <el-form-item label="报告编号" prop="orderId">
        <el-input
          v-model="queryParams.orderId"
          placeholder="请输入报告编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备号" prop="sn">
        <el-input
          v-model="queryParams.sn"
          placeholder="请输入设备号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
<!--      <el-form-item label="患者的id,唯一标识" prop="userId">-->
<!--        <el-input-->
<!--          v-model="queryParams.userId"-->
<!--          placeholder="请输入患者的id,唯一标识"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="上传开始时间" prop="uploadStart">-->
<!--        <el-input-->
<!--          v-model="queryParams.uploadStart"-->
<!--          placeholder="请输入上传开始时间"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="开始采集时间" prop="gatherStartTime">-->
<!--        <el-input-->
<!--          v-model="queryParams.gatherStartTime"-->
<!--          placeholder="请输入开始采集时间"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
      <el-form-item label="患者手机号" prop="phone">
        <el-input
          v-model="queryParams.phone"
          placeholder="请输入患者手机号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
<!--      <el-form-item label="下载url文件地址" prop="pdfUrl">-->
<!--        <el-input-->
<!--          v-model="queryParams.pdfUrl"-->
<!--          placeholder="请输入下载url文件地址"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
      <el-form-item label="数据文件名" prop="fileName">
        <el-input
          v-model="queryParams.fileName"
          placeholder="请输入数据文件名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入用户名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户生日" prop="birthday">
        <el-input
          v-model="queryParams.birthday"
          placeholder="请输入用户生日"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="采集时长" prop="gatherDuration">
        <el-input
          v-model="queryParams.gatherDuration"
          placeholder="请输入采集时长"
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
<!--          v-hasPermi="['sx_single:sx_single:add']"-->
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
<!--          v-hasPermi="['sx_single:sx_single:edit']"-->
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
          v-hasPermi="['sx_single:sx_single:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['sx_single:sx_single:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="sx_singleList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
<!--      <el-table-column label="善行生成报告id" align="center" prop="sxReportId" />-->
<!--      <el-table-column label="报告编号" align="center" prop="orderId" />-->
      <el-table-column label="设备号" align="center" prop="sn" />
      <el-table-column label="患者的id,唯一标识" align="center" prop="userId" />
      <el-table-column label="上传开始时间" align="center" prop="uploadStart" width="100px"/>
      <el-table-column label="开始采集时间" align="center" prop="gatherStartTime" width="100px"/>
      <el-table-column label="患者手机号" align="center" prop="phone" />
<!--      <el-table-column label="下载url文件地址" align="center" prop="pdfUrl" />-->
      <el-table-column label="数据文件名" align="center" prop="fileName" />
      <el-table-column label="用户名称" align="center" prop="name" />
      <el-table-column label="用户性别" align="center" prop="sex" />
      <el-table-column label="用户生日" align="center" prop="birthday" />
      <el-table-column label="采集时长" align="center" prop="gatherDuration" />
      <el-table-column label="关联患者id" align="center" prop="pid" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-edit"-->
<!--            @click="handleUpdate(scope.row)"-->
<!--            v-hasPermi="['sx_single:sx_single:edit']"-->
<!--          >修改</el-button>-->
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['sx_single:sx_single:remove']"
          >删除</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-share"
            @click="uploadReport(scope.row)"
            v-hasPermi="['sx_single:sx_single:remove']"
          >上传文件</el-button>
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

    <!-- 添加或修改善行单导心电贴对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="报告编号" prop="orderId">
          <el-input v-model="form.orderId" placeholder="请输入报告编号" />
        </el-form-item>
        <el-form-item label="设备号" prop="sn">
          <el-input v-model="form.sn" placeholder="请输入设备号" />
        </el-form-item>
        <el-form-item label="患者的id,唯一标识" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入患者的id,唯一标识" />
        </el-form-item>
        <el-form-item label="上传开始时间" prop="uploadStart">
          <el-input v-model="form.uploadStart" placeholder="请输入上传开始时间" />
        </el-form-item>
        <el-form-item label="开始采集时间" prop="gatherStartTime">
          <el-input v-model="form.gatherStartTime" placeholder="请输入开始采集时间" />
        </el-form-item>
        <el-form-item label="患者手机号" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入患者手机号" />
        </el-form-item>
        <el-form-item label="下载url文件地址" prop="pdfUrl">
          <el-input v-model="form.pdfUrl" placeholder="请输入下载url文件地址" />
        </el-form-item>
        <el-form-item label="数据文件名" prop="fileName">
          <el-input v-model="form.fileName" placeholder="请输入数据文件名" />
        </el-form-item>
        <el-form-item label="用户名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入用户名称" />
        </el-form-item>
        <el-form-item label="用户生日" prop="birthday">
          <el-input v-model="form.birthday" placeholder="请输入用户生日" />
        </el-form-item>
        <el-form-item label="采集时长" prop="gatherDuration">
          <el-input v-model="form.gatherDuration" placeholder="请输入采集时长" />
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
  listSx_single,
  getSx_single,
  delSx_single,
  addSx_single,
  updateSx_single,
  getFilePath
} from "@/api/sx_single/sx_single";

export default {
  name: "Sx_single",
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
      // 善行单导心电贴表格数据
      sx_singleList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderId: null,
        sn: null,
        userId: null,
        uploadStart: null,
        gatherStartTime: null,
        phone: null,
        pdfUrl: null,
        fileName: null,
        name: null,
        sex: null,
        birthday: null,
        gatherDuration: null
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
    /** 查询善行单导心电贴列表 */
    getList() {
      this.loading = true;
      listSx_single(this.queryParams).then(response => {
        this.sx_singleList = response.rows;
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
        sxReportId: null,
        orderId: null,
        sn: null,
        userId: null,
        uploadStart: null,
        gatherStartTime: null,
        phone: null,
        createTime: null,
        pdfUrl: null,
        fileName: null,
        name: null,
        sex: null,
        birthday: null,
        gatherDuration: null
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
      this.ids = selection.map(item => item.sxReportId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加善行单导心电贴";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const sxReportId = row.sxReportId || this.ids
      getSx_single(sxReportId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改善行单导心电贴";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.sxReportId != null) {
            updateSx_single(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSx_single(this.form).then(response => {
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
      const sxReportIds = row.sxReportId || this.ids;
      this.$modal.confirm('是否确认删除善行单导心电贴编号为"' + sxReportIds + '"的数据项？').then(function() {
        return delSx_single(sxReportIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    uploadReport(row){
      const sxReportIds = row.sxReportId
      let obj = {
        sxReportId:sxReportIds
      }
      this.$modal.confirm('是否确认上传善行单导心电贴编号为"' + sxReportIds + '"的数据项？').then(function() {
        return getFilePath(obj);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("上传成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('sx_single/sx_single/export', {
        ...this.queryParams
      }, `sx_single_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
