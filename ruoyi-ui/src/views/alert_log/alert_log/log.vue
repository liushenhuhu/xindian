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
      <el-form-item label="发生时间" prop="logTime">
        <el-form-item >
          <el-date-picker
            v-model="daterangeLogTime"
            style="width: 240px"
            value-format="yyyy-MM-dd HH:mm:ss"
            type="datetimerange"
            range-separator="-"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          ></el-date-picker>
        </el-form-item>
      </el-form-item>
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
      <el-form-item label="患者姓名" prop="patientName">
        <el-input
          v-model="queryParams.patientName"
          placeholder="请输入患者姓名"
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
<!--          v-hasPermi="['alert_log:alert_log:add']"-->
<!--        >新增-->
<!--        </el-button>-->
<!--      </el-col>-->
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="success"-->
<!--          plain-->
<!--          icon="el-icon-edit"-->
<!--          size="mini"-->
<!--          :disabled="single"-->
<!--          @click="handleUpdate"-->
<!--          v-hasPermi="['alert_log:alert_log:edit']"-->
<!--        >修改-->
<!--        </el-button>-->
<!--      </el-col>-->
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="danger"-->
<!--          plain-->
<!--          icon="el-icon-delete"-->
<!--          size="mini"-->
<!--          :disabled="multiple"-->
<!--          @click="handleDelete"-->
<!--          v-hasPermi="['alert_log:alert_log:remove']"-->
<!--        >删除-->
<!--        </el-button>-->
<!--      </el-col>-->
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="warning"-->
<!--          plain-->
<!--          icon="el-icon-download"-->
<!--          size="mini"-->
<!--          @click="handleExport"-->
<!--          v-hasPermi="['alert_log:alert_log:export']"-->
<!--        >导出-->
<!--        </el-button>-->
<!--      </el-col>-->
        <el-button
          type="success"
          plain
          icon="el-icon-view"
          size="mini"
          v-hasPermi="['ecg:show:conceal']"
          @click="isShowNameClick">{{isShowName.name}}
        </el-button>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="alert_logList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <!--      <el-table-column label="日志id" align="center" prop="logId" />-->
      <el-table-column label="发生时间" align="center" prop="logTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.logTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="预警类型" align="center" prop="logType"/>
      <el-table-column label="事件名称" align="center" prop="eventName"/>
      <el-table-column label="事件说明" align="center" prop="eventDescription"/>
      <el-table-column label="患者id" align="center" prop="pId" show-overflow-tooltip/>
      <el-table-column label="患者姓名 " align="center" prop="patientName">
        <template slot-scope="scope">
          <span v-if="isShowName.status===true">{{ scope.row.patientName }}</span>
          <span v-else>{{hideMiddleName(scope.row.patientName)}}</span>
        </template>
      </el-table-column>
      <!--      <el-table-column label="患者身份证号" align="center" prop="patientCode"/>
            <el-table-column label="患者电话" align="center" prop="patientPhone"/>
            <el-table-column label="家属电话" align="center" prop="familyPhone"/>-->
      <el-table-column label="医院名称" align="center" prop="hospitalName">
        <template slot-scope="scope">
              <span v-if="isShowName.status===true">{{ scope.row.hospitalName }}</span>
              <span v-else>***************</span>
            </template>
      </el-table-column>
      <!--      <el-table-column label="医院名称" align="center" prop="hospitalName" width="150"/>-->
      <el-table-column label="是否标注" align="center" prop="anoStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.if_status" :value="scope.row.anoStatus"/>
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
            <el-form-item label="患者电话" width="200" style="padding-left: 40px">
              <span>{{ scope.row.patientPhone }}</span>
            </el-form-item>
            <el-form-item label="家属电话" width="200" style="padding-left: 40px">
              <span>{{ scope.row.familyPhone }}</span>
            </el-form-item>
            <br>
            <!--            <el-form-item label="医院名称" width="200" style="padding-left: 40px">
                          <span>{{ scope.row.hospitalName }}</span>
                        </el-form-item>-->
          </el-form>
        </template>
      </el-table-column>


      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleLook(scope.row)"
          >查看日志
          </el-button>
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-edit"-->
<!--            @click="handleUpdate(scope.row)"-->
<!--            v-hasPermi="['alert_log:alert_log:edit']"-->
<!--          >修改-->
<!--          </el-button>-->
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-delete"-->
<!--            @click="handleDelete(scope.row)"-->
<!--            v-hasPermi="['alert_log:alert_log:remove']"-->
<!--          >删除-->
<!--          </el-button>-->
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

    <!-- 添加或修改预警日志对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">

        <el-form-item label="发生时间" prop="logTime">
          <el-date-picker clearable
                          v-model="form.logTime"
                          type="datetime"
                          value-format="yyyy-MM-dd HH:mm:ss"
                          placeholder="请选择发生时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="预警类型" prop="logType">
          <el-input v-model="form.logType" placeholder="请输入预警类型"/>
        </el-form-item>
        <el-form-item label="事件名称" prop="eventName">
          <el-input v-model="form.eventName" placeholder="请输入事件名称"/>
        </el-form-item>
        <el-form-item label="事件说明" prop="eventDescription">
          <el-input v-model="form.eventDescription" placeholder="请输入事件说明"/>
        </el-form-item>
        <el-form-item label="患者管理id" prop="pId">
          <el-input v-model="form.pId" placeholder="请输入患者管理id"/>
        </el-form-item>
        <el-form-item label="标注状态">
          <el-radio-group v-model="form.anoStatus">
            <el-radio
              v-for="dict in dict.type.if_status"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog title="密码验证" :visible.sync="dialogFormVisibleVerifyAuthority">
      <el-form :model="verifyForm" :rules="rules" ref="verifyForm">
        <el-form-item label="验证密码" prop="password">
          <el-input placeholder="请输入密码" v-model="verifyForm.password" show-password></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisibleVerifyAuthority = false">取 消</el-button>
        <el-button type="primary" @click="dialogFormVisibleVerify">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {listAlert_log, getAlert_log, delAlert_log, addAlert_log, updateAlert_log} from "@/api/alert_log/alert_log";
import {getVerify} from "@/api/verify/verify";

export default {
  name: "log",
  dicts: ['sex', 'if_status'],
  data() {
    return {
      verifyForm: {
        password: null,
        status: false
      },
      isShowName: {
        status: false,
        name: "显示信息"
      },
      dialogFormVisibleVerifyAuthority: false,
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
      // 预警日志表格数据
      alert_logList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 时间范围
      daterangeLogTime: [],
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
        patientName: null,
        hospitalName: null,
        anoStatus: null,
        ecgType: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        logNumber: [
          {required: true, message: "日志号不能为空", trigger: "blur"}
        ],
        pId: [
          {required: true, message: "患者id不能为空", trigger: "blur"}
        ],
        password: [
          {required: true, message: "密码不能为空", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  activated() {
    this.getList();
  },
  methods: {
    hideMiddleName(patientName) {
      if (patientName.length <= 1) {
        return "*"; // 一个字的则用一个 * 代替
      } else if (patientName.length === 2) {
        return patientName.charAt(0) + "*"; // 两个字的保留第一个字，后面用 * 代替
      } else {
        // let visibleChars = patientName.charAt(0) + "*".repeat(patientName.length - 2) + patientName.charAt(patientName.length - 1);
        let visibleChars = patientName.charAt(0) + "*".repeat(patientName.length - 1);
        return visibleChars; // 大于两个字的保留第一个字和最后一个字，中间用 * 代替
      }
    },
    dialogFormVisibleVerify() {
      this.$refs["verifyForm"].validate(valid => {
        if (valid) {
          let obj = {
            accountPwd: this.verifyForm.password
          }
          getVerify(obj).then(r => {
            this.$modal.msgSuccess("密码正确");
            this.verifyForm.status = true
            this.dialogFormVisibleVerifyAuthority = false
            sessionStorage.setItem('isShowName', true)
            this.isShowName.status = !this.isShowName.status;
            this.isShowName.name = "隐藏信息"
          })
        }
      })
    },

    isShowNameClick() {

        if (this.isShowName.status) {
          this.isShowName.status = !this.isShowName.status;
          this.isShowName.name = "显示信息"

        } else {
          this.isShowName.status = !this.isShowName.status;
          this.isShowName.name = "隐藏信息"
        }


    },
    /** 查询预警日志列表 */
    getList() {
      this.queryParams.pId = this.$route.query.pId
      // this.queryParams.logType = this.$route.query.type
      // this.queryParams.ecgType = this.$route.query.ecgType
      // console.log(this.queryParams)
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeLogTime && '' != this.daterangeLogTime) {
        this.queryParams.params["beginLogTime"] = this.daterangeLogTime[0];
        this.queryParams.params["endLogTime"] = this.daterangeLogTime[1];
      }
      if (this.queryParams.logType==='null'){
        this.queryParams.logType=null;
      }
      if (this.queryParams.ecgType==='null'){
        this.queryParams.ecgType=null;
      }
      console.log("为获取患者列表所放入的参数");
      console.log(this.queryParams);
      listAlert_log(this.queryParams).then(response => {
        // console.log(response);z
        this.alert_logList = response.rows;
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
        patientName: null,
        hospitalName: null,
        anoStatus: null
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
      this.title = "添加预警日志";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const logId = row.logId || this.ids
      getAlert_log(logId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改预警日志";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.logId != null) {
            updateAlert_log(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAlert_log(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除预警日志编号为"' + logIds + '"的数据项？').then(function () {
        return delAlert_log(logIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('alert_log/alert_log/export', {
        ...this.queryParams
      }, `alert_log_${new Date().getTime()}.xlsx`)
    },

    /** 查看日志*/
    handleLook(row) {
      // console.log(row);
      // console.log(row.logId);
      // this.$router.push({
      //   name: "lookLog",
      //   params: {logId: row.logId, logType: row.logType}
      // });
      // console.log(this.queryParams);
      // return
      this.$router.push({
        path: "/lookLog1",// name: "lookLog"
        query: {
          logId: row.logId,
          // logType: row.logType,
          userId:0,
          state:this.$route.query.state,
          // 隐藏上一页下一页
          // kaiguan:false
          ecgType: this.queryParams.ecgType,
          pageNum: this.queryParams.pageNum,
          pageSize: this.queryParams.pageSize,
          isSuspected: this.queryParams.isSuspected,
          anoStatus: this.queryParams.anoStatus,
          queryParams: this.queryParams,
          }

        // query: {
        //   logId: row.logId,
        //   userId:0,
        //   ecgType:this.queryParams.ecgType,


        //   logType: this.queryParams.logType,
        //   isSuspected:"",

        //   pId: this.queryParams.pId,
        //   logTime:this.queryParams.logTime,

        //   eventDescription:this.queryParams.eventDescription,
        //   eventName:this.queryParams.eventName,
        //   logTime:this.queryParams.logTime,
        //   state:1,
        // }
      });
    },
  }
};
</script>
