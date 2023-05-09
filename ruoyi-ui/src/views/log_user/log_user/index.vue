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
<!--      <el-form-item label="发生时间" prop="logTime">-->
<!--        <el-form-item>-->
<!--          <el-date-picker-->
<!--            v-model="daterangeLogTime"-->
<!--            style="width: 240px"-->
<!--            value-format="yyyy-MM-dd HH:mm:ss"-->
<!--            type="datetimerange"-->
<!--            range-separator="-"-->
<!--            start-placeholder="开始日期"-->
<!--            end-placeholder="结束日期"-->
<!--          ></el-date-picker>-->
<!--        </el-form-item>-->
<!--      </el-form-item>-->
      <el-form-item label="预警类型" prop="logType">
        <el-input
          v-model="queryParams.logType"
          placeholder="请输入预警类型"
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
      <el-form-item label="事件说明" prop="eventDescription">
        <el-input
          v-model="queryParams.eventDescription"
          placeholder="请输入事件说明"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="患者id" prop="pId">
        <el-input
          v-model="queryParams.pId"
          placeholder="请输入患者id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
<!--      <el-form-item label="患者性别" prop="patientSex">-->
<!--        <el-select v-model="queryParams.patientSex" placeholder="请选择患者性别" clearable>-->
<!--          <el-option-->
<!--            v-for="dict in dict.type.sex"-->
<!--            :key="dict.value"-->
<!--            :label="dict.label"-->
<!--            :value="dict.value"-->
<!--          />-->
<!--        </el-select>-->
<!--      </el-form-item>-->
      <el-form-item label="是否标注" prop="anoStatus">
        <el-select v-model="queryParams.anoStatus" placeholder="请选择是否标注" clearable>
          <el-option
            v-for="dict in dict.type.if_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="账号id" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入账号id"
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
          v-hasPermi="['log_user:log_user:add']"
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
          v-hasPermi="['log_user:log_user:edit']"
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
          v-hasPermi="['log_user:log_user:remove']"
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
          v-hasPermi="['log_user:log_user:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="log_userList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
<!--      <el-table-column label="发生时间" align="center" prop="logTime" width="180">-->
<!--        <template slot-scope="scope">-->
<!--          <span>{{ parseTime(scope.row.logTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>-->
<!--        </template>-->
<!--      </el-table-column>-->
      <el-table-column label="预警类型" align="center" prop="logType"/>
      <el-table-column label="事件名称" align="center" prop="eventName"/>
      <el-table-column label="事件说明" align="center" prop="eventDescription"/>
      <el-table-column label="患者id" align="center" prop="pId" show-overflow-tooltip/>
      <el-table-column label="日志id" align="center" prop="logId" show-overflow-tooltip/>
<!--      <el-table-column label="患者年龄" align="center" prop="patientAge"/>-->
<!--      <el-table-column label="患者性别" align="center" prop="patientSex">-->
<!--        <template slot-scope="scope">-->
<!--          <dict-tag :options="dict.type.sex" :value="scope.row.patientSex"/>-->
<!--        </template>-->
<!--      </el-table-column>-->
      <el-table-column label="账号id" align="center" prop="userId"/>
      <el-table-column label="是否标注" align="center" prop="anoStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.if_status" :value="scope.row.anoStatus"/>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleLook(scope.row)"
            v-hasPermi="['alert_log:alert_log:look']"
          >查看日志
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['log_user:log_user:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['log_user:log_user:remove']"
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

    <!-- 添加或修改标注分配对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="日志id" prop="logId">
          <el-input v-model="form.logId" placeholder="请输入日志id"/>
        </el-form-item>
        <el-form-item label="噪声" prop="logNoise">
          <el-input v-model="form.logNoise" placeholder="请输入噪声"/>
        </el-form-item>
        <el-form-item label="噪声等级" prop="logNoiseLevel">
          <el-input v-model="form.logNoiseLevel" placeholder="请输入噪声等级"/>
        </el-form-item>
        <el-form-item label="账号id" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入账号id"/>
        </el-form-item>
        <el-form-item label="事件名称" prop="eventName">
          <el-input v-model="form.eventName" placeholder="请输入事件名称"/>
        </el-form-item>
        <el-form-item label="事件说明" prop="eventDescription">
          <el-input v-model="form.eventDescription" placeholder="请输入事件说明"/>
        </el-form-item>
        <el-form-item label="是否标注">
          <el-radio-group v-model="form.anoStatus">
            <el-radio
              v-for="dict in dict.type.if_status"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{ dict.label }}
            </el-radio>
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
import {listLog_user, getLog_user, delLog_user, addLog_user, updateLog_user} from "@/api/log_user/log_user";
import {listAlert_log_ano} from "@/api/alert_log/alert_log";

export default {
  name: "Log_user",
  dicts: ['sex', 'if_status'],
  data() {
    return {
      daterangeLogTime:[],
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
      // 标注分配表格数据
      log_userList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        logId: null,
        logTime: null,
        logType: null,
        eventName: null,
        eventDescription: null,
        pId: null,
        hospitalName: null,
        anoStatus: null,
        userId: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {}
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询标注分配列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeLogTime && '' != this.daterangeLogTime) {
        this.queryParams.params["beginLogTime"] = this.daterangeLogTime[0];
        this.queryParams.params["endLogTime"] = this.daterangeLogTime[1];
      }
      listLog_user(this.queryParams).then(response => {
        this.log_userList = response.rows;
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
        logTime: null,
        logType: null,
        eventName: null,
        eventDescription: null,
        pId: null,
        hospitalName: null,
        anoStatus: null,
        userId: null
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
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加标注分配";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const logId = row.logId || this.ids
      getLog_user(logId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改标注分配";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.logId != null) {
            updateLog_user(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addLog_user(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除标注分配编号为"' + logIds + '"的数据项？').then(function () {
        return delLog_user(logIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('log_user/log_user/export', {
        ...this.queryParams
      }, `log_user_${new Date().getTime()}.xlsx`)
    },
    /** 查看日志*/
    handleLook(row) {
      console.log(row.logId);
      this.$router.push({
        name: "lookLog",
        params: {logId: row.logId, logType: row.logType, userId: row.userId}
      });
    }
  }
};
</script>
