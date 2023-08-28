<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="用户电话" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入用户电话"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="IP地址" prop="ipaddr">
        <el-input
          v-model="queryParams.ipaddr"
          placeholder="请输入IP地址"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="操作时间">
        <el-date-picker
          v-model="daterangedetectionTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="操作状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
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
<!--          v-hasPermi="['fwLog:fwLog:add']"-->
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
<!--          v-hasPermi="['fwLog:fwLog:edit']"-->
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
          v-hasPermi="['fwLog:fwLog:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['fwLog:fwLog:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="FwlogList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="用户电话" align="center" prop="userName" />
      <el-table-column label="IP地址" align="center" prop="ipaddr" />
      <el-table-column label="IP归属地" align="center" prop="loginLocation" />
      <el-table-column label="操作内容" align="center" prop="msg" />
      <el-table-column label="操作状态" align="center" prop="status" >
        <template slot-scope="scope">
          <el-tag type="success" effect="dark" v-if="scope.row.status==1">成功</el-tag>
          <el-tag type="warning" effect="dark" v-if="scope.row.status==0">失败</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作时间" align="center" prop="logTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.logTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="服务状态" align="center" prop="fwStatus" >
        <template slot-scope="scope">
          <span v-if="scope.row.fwStatus==1">增加服务次数</span>
          <span v-if="scope.row.fwStatus==2">减少服务次数</span>
        </template>
      </el-table-column>
      <el-table-column label="服务次数" align="center" prop="fwNum" />
      <el-table-column>
        <template slot-scope="scope">
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-edit"-->
<!--            @click="handleUpdate(scope.row)"-->
<!--            v-hasPermi="['fwLog:fwLog:edit']"-->
<!--          >修改</el-button>-->
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['fwLog:fwLog:remove']"
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


<!--    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>-->
<!--      <el-form ref="form" :model="form" :rules="rules" label-width="80px">-->
<!--        <el-form-item label="用户电话" prop="userName">-->
<!--          <el-input v-model="form.userName" placeholder="请输入用户电话" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="IP" prop="ipaddr">-->
<!--          <el-input v-model="form.ipaddr" placeholder="请输入IP" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="IP地址" prop="loginLocation">-->
<!--          <el-input v-model="form.loginLocation" placeholder="请输入IP地址" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="操作内容" prop="msg">-->
<!--          <el-input v-model="form.msg" placeholder="请输入操作内容" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="操作状态" prop="status">-->
<!--        <el-input v-model="form.status" placeholder="请输入操作状态" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="服务状态" prop="fwStatus">-->
<!--          <el-input v-model="form.fwStatus" placeholder="请输入服务状态" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="服务次数" prop="fwNum">-->
<!--          <el-input v-model="form.fwNum" placeholder="请输入服务次数" />-->
<!--        </el-form-item>-->
<!--      </el-form>-->
<!--      <div slot="footer" class="dialog-footer">-->
<!--        <el-button type="primary" @click="submitForm">确 定</el-button>-->
<!--        <el-button @click="cancel">取 消</el-button>-->
<!--      </div>-->
<!--    </el-dialog>-->
  </div>
</template>

<script>
import { listFwlog, getFwlog, delFwlog, addFwlog, updateFwlog } from "@/api/Fwlog/Fwlog";

export default {
  name: "Fwlog",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      fwLogIds: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // vip用户表格数据
      FwlogList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 检测次数时间范围
      daterangedetectionTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userName: null,
        ipaddr: null,
        loginLocation: null,
        msg:null,
        status:null,
        startTime:null,
        endTime:null,
        fwStatus:null,
        fwNum:null,
      },
      options: [{
        value: '1',
        label: '成功'
      }, {
        value: '0',
        label: '失败'
      }],
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        userName: [
          { required: true, message: "用户电话不能为空", trigger: "blur" }
        ],

      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询vip用户列表 */
    getList() {
      this.loading = true;
      this.queryParams.startTime=null
      this.queryParams.endTime=null
      if (null != this.daterangedetectionTime && this.daterangedetectionTime!='') {
        this.queryParams.startTime = this.daterangedetectionTime[0];
        this.queryParams.endTime= this.daterangedetectionTime[1];
      }
      listFwlog(this.queryParams).then(response => {
        this.FwlogList = response.rows;
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
        fwLogId: null,
        phonenumberAes: null,
        detectionTime: null,
        detectionNum: null
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
      this.daterangedetectionTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.fwLogIds = selection.map(item => item.fwLogId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    // /** 新增按钮操作 */
    // handleAdd() {
    //   this.reset();
    //   this.open = true;
    //   this.title = "添加服务日志";
    // },
    // /** 修改按钮操作 */
    // handleUpdate(row) {
    //   console.log(row)
    //   this.reset();
    //   const fwLogId = row.fwLogId || this.fwLogIds
    //   console.log(fwLogId)
    //   getFwlog(fwLogId).then(response => {
    //     this.form = response.data;
    //     this.open = true;
    //     this.title = "修改vip用户";
    //   });
    // },
    /** 提交按钮 */
    // submitForm() {
    //   this.$refs["form"].validate(valid => {
    //     if (valid) {
    //       if (this.form.fwLogId != null) {
    //         updateFwlog(this.form).then(response => {
    //           this.$modal.msgSuccess("修改成功");
    //           this.open = false;
    //           this.getList();
    //         });
    //       } else {
    //         addFwlog(this.form).then(response => {
    //           this.$modal.msgSuccess("新增成功");
    //           this.open = false;
    //           this.getList();
    //         });
    //       }
    //     }
    //   });
    // },
    /** 删除按钮操作 */
    handleDelete(row) {
      const fwLogIds = row.fwLogId || this.fwLogIds;
      this.$modal.confirm('是否确认删除服务日志编号为"' + fwLogIds + '"的数据项？').then(function() {
        return delFwlog(fwLogIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('fwLog/fwLog/export', {
        ...this.queryParams
      }, `Fwlog_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
