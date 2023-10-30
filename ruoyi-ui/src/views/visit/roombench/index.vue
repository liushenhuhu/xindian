<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
<!--      <el-form-item label="预约管理id" prop="appointmentId">-->
<!--        <el-input-->
<!--          v-model="queryParams.appointmentId"-->
<!--          placeholder="请输入预约管理id"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
      <el-form-item label="医院名称" prop="hospitalId">

        <el-select v-model="queryParams.hospitalId" placeholder="请选择医院名称" >
          <el-option
            v-for="item in options"
            :key="item.hospitalId"
            :label="item.hospital.hospitalName"
            :value="item.hospital.hospitalId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="医生手机号" prop="doctorPhone">
        <el-select v-model="queryParams.doctorPhone" placeholder="请输入医生名称" clearable >
          <el-option
            v-for="item in hospitalDocList"
            :key="item.doctorId"
            :label="item.doctorName"
            :value="item.doctorPhone">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="患者手机号" prop="patientPhone">
        <el-input
          v-model="queryParams.patientPhone"
          placeholder="请输入患者手机号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="预约时间" prop="visitDate">
        <el-date-picker clearable
                        v-model="queryParams.visitDate"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择预约时间">
        </el-date-picker>
      </el-form-item>
<!--      <el-form-item label="排班id" prop="slotId">-->
<!--        <el-input-->
<!--          v-model="queryParams.slotId"-->
<!--          placeholder="请输入排班id"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
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
<!--          v-hasPermi="['hospital:wait:add']"-->
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
<!--          v-hasPermi="['hospital:wait:edit']"-->
<!--        >修改</el-button>-->
<!--      </el-col>-->
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="danger"-->
<!--          plain-->
<!--          icon="el-icon-delete"-->
<!--          size="mini"-->
<!--          :disabled="multiple"-->
<!--          @click="handleDelete"-->
<!--          v-hasPermi="['hospital:wait:remove']"-->
<!--        >删除</el-button>-->
<!--      </el-col>-->
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="warning"-->
<!--          plain-->
<!--          icon="el-icon-download"-->
<!--          size="mini"-->
<!--          @click="handleExport"-->
<!--          v-hasPermi="['hospital:wait:export']"-->
<!--        >导出</el-button>-->
<!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="waitList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
<!--      <el-table-column label="候诊id" align="center" prop="waitId" />-->
<!--      <el-table-column label="预约管理id" align="center" prop="appointmentId" />-->
      <el-table-column label="医院名称" align="center" prop="hospitalName" />
      <el-table-column label="医生手机号" align="center" prop="doctorPhone" />
      <el-table-column label="医生名称" align="center" prop="doctorName" />
      <el-table-column label="患者手机号" align="center" prop="patientPhone" />
      <el-table-column label="患者名称" align="center" prop="patientName" />
      <el-table-column label="患者性别" align="center" prop="patientSex" />
      <el-table-column label="患者年龄" align="center" prop="patientAge" />
      <el-table-column label="诊室" align="center" prop="hospitalClinicName" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="180">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="roomBench(scope.row)"
            v-hasPermi="['hospital:wait:edit']"
          >完成就诊</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="roomBenchCancel(scope.row)"
            v-hasPermi="['hospital:wait:edit']"
          >迟到就诊</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-phone-outline"
            @click="roomBenchCry(scope.row)"
            v-hasPermi="['hospital:wait:remove']"
          >叫号</el-button>
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

    <!-- 添加或修改门诊候诊对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="预约管理id" prop="appointmentId">
          <el-input v-model="form.appointmentId" placeholder="请输入预约管理id" />
        </el-form-item>
        <el-form-item label="医院id" prop="hospitalId">
          <el-input v-model="form.hospitalId" placeholder="请输入医院id" />
        </el-form-item>
        <el-form-item label="医生手机号" prop="doctorPhone">
          <el-input v-model="form.doctorPhone" placeholder="请输入医生手机号" />
        </el-form-item>
        <el-form-item label="患者手机号" prop="patientPhone">
          <el-input v-model="form.patientPhone" placeholder="请输入患者手机号" />
        </el-form-item>
        <el-form-item label="排班id" prop="slotId">
          <el-input v-model="form.slotId" placeholder="请输入排班id" />
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
import {listWait, getWait, delWait, addWait, updateWait, accomplishStatus} from "@/api/visit/wait";
import {getHospitalSpecialList} from "@/api/visit/hospitalRelation";
import {getHospitalOutpatientList} from "@/api/visit/hospitalOutpatient";
import {getVisitHospitalList} from "@/api/visit/subscribe";
import {getHospitalVisitDoc} from "@/api/doctor/doctor";

export default {
  name: "Wait",
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
      // 门诊候诊表格数据
      waitList: [],
      // 弹出层标题
      title: "",
      hospitalDocList:[],
      options:[],
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        appointmentId: null,
        hospitalId: null,
        doctorPhone: null,
        patientPhone: null,
        slotId: null,
        visitDate:null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    let date = new Date()
    let year = date.getFullYear();
    let month = (date.getMonth() + 1).toString().padStart(2, '0'); // 月份从0开始，需要加1，并补零
    let day = date.getDate().toString().padStart(2, '0'); // 补零
    this.queryParams.visitDate=year+'-'+month+'-'+day

    getVisitHospitalList().then(r=>{
      this.options=r.data
    })
    getHospitalVisitDoc().then(r=>{
      this.hospitalDocList = r.data
    })
    this.getList();
  },
  methods: {
    /** 查询门诊候诊列表 */
    getList() {
      this.loading = true;
      console.log(this.queryParams)
      listWait(this.queryParams).then(response => {
        console.log(response)
        this.waitList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    //已就诊
    roomBench(row){
      const array = this.waitList
      let obj = {
        waitId:row.waitId,
        appointmentId:row.appointmentId,
        status:1
      }
      console.log(row)
      this.$modal.confirm('是否确认将患者'+row.patientName+'完成诊断？').then(function() {
        return accomplishStatus(obj)
      }).then(() => {
        this.getList();

        this.$modal.msgSuccess("操作成功");
      }).catch(() => {});
    },
    //迟到就诊
    roomBenchCancel(row){
      let obj = {
        waitId:row.waitId,
        appointmentId:row.appointmentId,
        status:3
      }
      this.$modal.confirm('是否确认将患者'+row.patientName+'列为迟到？').then(function() {
        return accomplishStatus(obj)
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("操作成功");
      }).catch(() => {});
    },

    roomBenchCry(row){
      if ('speechSynthesis' in window) {
        // 创建 SpeechSynthesisUtterance 对象
        let utterance = new SpeechSynthesisUtterance();

        // 设置要播放的文字
        utterance.text = row.patientName+"患者请到"+row.hospitalClinicName+"就诊";
        // 播放语音
        window.speechSynthesis.speak(utterance);
      } else {
        console.log('Speech synthesis is not supported');
      }
    },
    // 表单重置
    reset() {
      this.form = {
        waitId: null,
        appointmentId: null,
        hospitalId: null,
        doctorPhone: null,
        patientPhone: null,
        slotId: null,
        createTime: null,
        updateTime: null
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
      this.ids = selection.map(item => item.waitId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加门诊候诊";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const waitId = row.waitId || this.ids
      getWait(waitId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改门诊候诊";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.waitId != null) {
            updateWait(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addWait(this.form).then(response => {
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
      const waitIds = row.waitId || this.ids;
      this.$modal.confirm('是否确认删除门诊候诊编号为"' + waitIds + '"的数据项？').then(function() {
        return delWait(waitIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('hospital/wait/export', {
        ...this.queryParams
      }, `wait_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
