<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
<!--      <el-form-item label="出诊编号" prop="planId">-->
<!--        <el-input-->
<!--          v-model="queryParams.planId"-->
<!--          placeholder="请输入出诊编号"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
      <el-form-item label="患者名称" prop="patientName" label-width="150">
        <el-input
          v-model="queryParams.patientPhone"
          placeholder="请输入患者名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
<!--      <el-form-item label="陪诊人手机号" prop="accompanyPhone">-->
<!--        <el-input-->
<!--          v-model="queryParams.accompanyPhone"-->
<!--          placeholder="请输入陪诊人手机号"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="出诊开始时间" prop="periodStart">-->
<!--        <el-input-->
<!--          v-model="queryParams.periodStart"-->
<!--          placeholder="请输入出诊开始时间"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="出诊结束时间" prop="periodEnd">-->
<!--        <el-input-->
<!--          v-model="queryParams.periodEnd"-->
<!--          placeholder="请输入出诊结束时间"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
      <el-form-item label="专科名称" prop="specialName">
        <el-select v-model="queryParams.specialName" placeholder="请输入专科名称" clearable >
          <el-option
            v-for="item in specialList"
            :key="item.hospitalSpecial.specialId"
            :label="item.hospitalSpecial.specialName"
            :value="item.hospitalSpecial.specialName">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="门诊名称" prop="outpatientName">
        <el-select v-model="queryParams.outpatientName" placeholder="请输入门诊名称" clearable >
          <el-option
            v-for="item in outpatientList"
            :key="item.hospitalOutpatient.outpatientId"
            :label="item.hospitalOutpatient.outpatientName"
            :value="item.hospitalOutpatient.outpatientName">
          </el-option>
        </el-select>
      </el-form-item>
<!--      <el-form-item label="科室地址" prop="clinicAddress">-->
<!--        <el-input-->
<!--          v-model="queryParams.clinicAddress"-->
<!--          placeholder="请输入科室地址"-->
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
      <el-form-item label="医生名称" prop="doctorPhone">
        <el-select v-model="queryParams.doctorPhone" placeholder="请输入医生名称" clearable >
          <el-option
            v-for="item in hospitalDocList"
            :key="item.doctorId"
            :label="item.doctorName"
            :value="item.doctorPhone">
          </el-option>
        </el-select>
      </el-form-item>
<!--      <el-form-item label="医生电话" prop="doctorPhone">-->
<!--        <el-input-->
<!--          v-model="queryParams.doctorPhone"-->
<!--          placeholder="请输入医生电话"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
      <el-form-item label="预约时间" prop="periodDay">
        <el-date-picker clearable
                        v-model="queryParams.periodDay"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择预约时间">
        </el-date-picker>
      </el-form-item>
<!--      <el-form-item label="挂号支付金额" prop="payPrice">-->
<!--        <el-input-->
<!--          v-model="queryParams.payPrice"-->
<!--          placeholder="请输入挂号支付金额"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="医生职称" prop="doctorProfessional">-->
<!--        <el-input-->
<!--          v-model="queryParams.doctorProfessional"-->
<!--          placeholder="请输入医生职称"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="出诊时间" prop="timePeriod">-->
<!--        <el-input-->
<!--          v-model="queryParams.timePeriod"-->
<!--          placeholder="请输入出诊时间"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="预约支付标题" prop="title">-->
<!--        <el-input-->
<!--          v-model="queryParams.title"-->
<!--          placeholder="请输入预约支付标题"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="商户id" prop="orderNo">-->
<!--        <el-input-->
<!--          v-model="queryParams.orderNo"-->
<!--          placeholder="请输入商户id"-->
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
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['hospital:visitAppointment:add']"
        >新增</el-button>
      </el-col>
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="success"-->
<!--          plain-->
<!--          icon="el-icon-edit"-->
<!--          size="mini"-->
<!--          :disabled="single"-->
<!--          @click="handleUpdate"-->
<!--          v-hasPermi="['hospital:visitAppointment:edit']"-->
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
          v-hasPermi="['hospital:visitAppointment:remove']"
        >删除</el-button>
      </el-col>
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="warning"-->
<!--          plain-->
<!--          icon="el-icon-download"-->
<!--          size="mini"-->
<!--          @click="handleExport"-->
<!--          v-hasPermi="['hospital:visitAppointment:export']"-->
<!--        >导出</el-button>-->
<!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="visitAppointmentList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
<!--      <el-table-column label="预约编号" align="center" prop="appointmentId" />-->
<!--      <el-table-column label="出诊编号" align="center" prop="planId" />-->
      <el-table-column label="患者名称" align="center" prop="patientName" />
<!--      <el-table-column label="陪诊人手机号" align="center" prop="accompanyPhone" />-->
      <el-table-column label="预约时间" align="center" prop="periodStart" width="100">
        <template slot-scope="scope">
          <span v-if="scope.row.periodStart!==null">{{ parseTime(scope.row.periodDay, '{y}-{m}-{d} ')+scope.row.periodStart+'-'+scope.row.periodEnd }}</span>
          <span v-else>{{ parseTime(scope.row.periodDay, '{y}-{m}-{d} ')+ '线下预约' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="预约状态" align="center" prop="status" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status==0" type="success">待就诊</el-tag>
          <el-tag v-if="scope.row.status==3" type="warning">未按时就诊</el-tag>
          <el-tag v-if="scope.row.status==2" type="danger">取消预约</el-tag>
          <el-tag v-if="scope.row.status==1" >已就诊</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="支付状态" align="center" prop="payStatus" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.payStatus==0" type="info">未支付</el-tag>
          <el-tag v-if="scope.row.payStatus==2" type="danger">已退款</el-tag>
          <el-tag v-if="scope.row.payStatus==1" >已支付</el-tag>
        </template>
      </el-table-column>
<!--      <el-table-column label="出诊结束时间" align="center" prop="periodEnd" />-->
      <el-table-column label="专科名称" align="center" prop="specialName" />
      <el-table-column label="门诊名称" align="center" prop="outpatientName" />
<!--      <el-table-column label="科室地址" align="center" prop="clinicAddress" />-->
      <el-table-column label="医院名称" align="center" prop="hospitalName" />
      <el-table-column label="医生名称" align="center" prop="doctorName" />
<!--      <el-table-column label="医生电话" align="center" prop="doctorPhone" />-->
<!--      <el-table-column label="预约时间" align="center" prop="periodDay" width="110">-->
<!--        <template slot-scope="scope">-->
<!--          <span>{{ parseTime(scope.row.periodDay, '{y}-{m}-{d}') }}</span>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--      <el-table-column label="挂号支付金额" align="center" prop="payPrice" />-->
      <el-table-column label="就诊类型" align="center" prop="outpatientType" />
<!--      <el-table-column label="医生职称" align="center" prop="doctorProfessional" />-->
<!--      <el-table-column label="出诊时间" align="center" prop="timePeriod" />-->
<!--      <el-table-column label="预约支付标题" align="center" prop="title" />-->
<!--      <el-table-column label="商户id" align="center" prop="orderNo" />-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
<!--          <el-button-->
<!--            v-if="scope.row.payStatus==1&&scope.row.status==3"-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-edit"-->
<!--            @click="payTui(scope.row)"-->
<!--            v-hasPermi="['hospital:visitAppointment:edit']"-->
<!--          >退款</el-button>-->
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['hospital:visitAppointment:remove']"
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

    <!-- 添加或修改挂号预约对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="180px">
<!--        <el-form-item label="出诊编号" prop="planId">-->
<!--          <el-input v-model="form.planId" placeholder="请输入出诊编号" />-->
<!--        </el-form-item>-->
        <el-form-item label="患者姓名" prop="patientName">
          <el-input v-model="form.patientName" placeholder="请输入患者姓名"/>
        </el-form-item>
        <el-form-item label="患者电话" prop="patientPhone">
          <el-input v-model="form.patientPhone" placeholder="请输入患者电话"/>
        </el-form-item>
        <el-form-item label="患者出生年月" prop="birthDay">
          <el-date-picker
            v-model="form.birthBay"
            type="date"
            placeholder="选择日期" value-format="yyyy-MM-dd">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="患者性别" prop="patientSex">
          <el-select v-model="form.patientSex" placeholder="请选择患者性别">
            <el-option
              v-for="dict in dict.type.sex"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="陪诊人手机号" prop="accompanyPhone">
          <el-input v-model="form.accompanyPhone" placeholder="请输入陪诊人手机号" />
        </el-form-item>

<!--        <el-form-item label="出诊开始时间" prop="periodStart">-->
<!--          <el-input v-model="form.periodStart" placeholder="请输入出诊开始时间" />-->
<!--        </el-form-item>-->
        <el-form-item label="医院名称" prop="hospitalId">
          <el-select v-model="form.hospitalId" placeholder="请选择医院名称" @change="historyId(form.hospitalId,1)">
            <el-option
              v-for="item in options"
              :key="item.hospitalId"
              :label="item.hospital.hospitalName"
              :value="item.hospital.hospitalId">
            </el-option>
          </el-select>
        </el-form-item>
           <el-form-item label="所属专科" prop="specialId">
             <el-select v-model="form.specialId" placeholder="请输入专科名称" clearable @change="getSpecialList(form.specialId,form.hospitalId,1)" >
               <el-option
                 v-for="item in specialListType"
                 :key="item.hospitalSpecial.specialId"
                 :label="item.hospitalSpecial.specialName"
                 :value="item.hospitalSpecial.specialId">
               </el-option>
             </el-select>
           </el-form-item>
           <el-form-item label="所属门诊" prop="outpatientId">
             <el-select v-model="form.outpatientId" placeholder="请输入门诊名称" clearable @change="getOutpatientIdList(form.outpatientId,form.specialId,form.hospitalId,1)" >
               <el-option
                 v-for="item in outpatientListType"
                 :key="item.hospitalOutpatient.outpatientId"
                 :label="item.hospitalOutpatient.outpatientName"
                 :value="item.hospitalOutpatient.outpatientId">
               </el-option>
             </el-select>
           </el-form-item>
<!--        <el-form-item label="科室地址" prop="clinicAddress">-->
<!--          <el-input v-model="form.clinicAddress" placeholder="请输入科室地址" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="医院id" prop="hospitalId">-->
<!--          <el-input v-model="form.hospitalId" placeholder="请输入医院id" />-->
<!--        </el-form-item>-->
        <el-form-item label="医生名称" prop="doctorPhone">
          <el-select v-model="form.doctorPhone" placeholder="请输入医生名称" clearable @change="getDocPriceList(form.doctorPhone)">
            <el-option
              v-for="item in hospitalDocListType"
              :key="item.doctorId"
              :label="item.doctorName"
              :value="item.doctorPhone">
            </el-option>
          </el-select>
        </el-form-item>
<!--        <el-form-item label="医生电话" prop="doctorPhone">-->
<!--          <el-input v-model="form.doctorPhone" placeholder="请输入医生电话" />-->
<!--        </el-form-item>-->

        <el-form-item label="挂号支付金额" prop="payPrice">
          <el-input v-model="form.payPrice" placeholder="请输入挂号支付金额" disabled/>
        </el-form-item>
<!--        <el-form-item label="医生职称" prop="doctorProfessional">-->
<!--          <el-input v-model="form.doctorProfessional" placeholder="请输入医生职称" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="出诊时间" prop="timePeriod">-->
<!--          <el-input v-model="form.timePeriod" placeholder="请输入出诊时间" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="预约支付标题" prop="title">-->
<!--          <el-input v-model="form.title" placeholder="请输入预约支付标题" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="商户id" prop="orderNo">-->
<!--          <el-input v-model="form.orderNo" placeholder="请输入商户id" />-->
<!--        </el-form-item>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listVisitAppointment, getVisitAppointment, delVisitAppointment, addVisitAppointment, updateVisitAppointment } from "@/api/visit/appointment";
import {getHospitalSpecialList} from "@/api/visit/hospitalRelation";
import {getHospitalOutpatientList} from "@/api/visit/hospitalOutpatient";
import {getHospitalVisitDoc, hospitalCodeFind} from "@/api/doctor/doctor";
import {listHospitalId} from "@/api/hospital/hospital";
import {getVisitHospitalList} from "@/api/visit/subscribe";

export default {
  name: "VisitAppointment",
  dicts: ['sex', 'monitoring_status', 'binding_state', 'hospital_name_name_list'],
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
      outpatientList:[],
      specialList:[],
      outpatientListType:[],
      specialListType:[],
      // 挂号预约表格数据
      visitAppointmentList: [],
      // 弹出层标题
      title: "",
      options:[],
      hospitalDocList:[],
      hospitalDocListType:[],
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        planId: null,
        patientPhone: null,
        accompanyPhone: null,
        periodStart: null,
        status: null,
        payStatus: null,
        periodEnd: null,
        specialName: null,
        outpatientName: null,
        clinicAddress: null,
        hospitalId: null,
        doctorName: null,
        doctorPhone: null,
        periodDay: null,
        payPrice: null,
        outpatientType: null,
        doctorProfessional: null,
        timePeriod: null,
        title: null,
        orderNo: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        patientName: [
          {required: true, message: "患者姓名不能为空", trigger: "blur"}
        ],
        patientPhone: [
          {required: true, message: "患者电话不能为空", trigger: "blur"}
        ],
        birthBay: [
          {required: true, message: "患者年龄不能为空", trigger: "blur"}
        ],
        patientSex: [
          {required: true, message: "患者性别不能为空", trigger: "change"}
        ],
        hospitalId: [
          {required: true, message: "医院名称", trigger: "change"}
        ],
        doctorName: [
          { required: true, message: "医生姓名不能为空", trigger: "blur" }
        ],
        outpatientId: [
          { required: true, message: "专科不能为空", trigger: "blur" }
        ],
        specialId: [
          { required: true, message: "门诊不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {

    getHospitalSpecialList().then(r=>{
      this.specialList = r.data
    })
    getHospitalOutpatientList().then(r=>{
      this.outpatientList = r.data
    })
    getVisitHospitalList().then(r=>{
      this.options=r.data
    })
    getHospitalVisitDoc().then(r=>{
      this.hospitalDocList = r.data
    })
    this.getList();
  },
  methods: {
    /** 查询挂号预约列表 */
    getList() {
      this.loading = true;
      listVisitAppointment(this.queryParams).then(response => {
        this.visitAppointmentList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    historyId(val,id){
      if (id===1){
        this.form.specialId=null
      }
      if (val!==""){
        let obj = {
          hospitalId:val
        }
        getHospitalSpecialList(obj).then(r=>{
          this.specialListType=r.data
        })
      }
    },
    payTui(row){

      console.log(row)
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        appointmentId: null,
        planId: null,
        patientPhone: null,
        accompanyPhone: null,
        periodStart: null,
        status: 0,
        createTime: null,
        updateTime: null,
        payStatus: "0",
        periodEnd: null,
        specialName: null,
        outpatientName: null,
        clinicAddress: null,
        hospitalId: null,
        doctorName: null,
        doctorPhone: null,
        periodDay: null,
        payPrice: null,
        outpatientType: null,
        doctorProfessional: null,
        timePeriod: null,
        title: null,
        orderNo: null,
        outpatientId:null,
        birthBay:null,
      };
      this.resetForm("form");
    },
    getSpecialList(val,hospital,id){
      if (id===1){
        this.form.outpatientId=null
      }
      if (val!==""){
        let _th = this
        let obj = {
          hospitalId:hospital,
          specialId:val
        }
        getHospitalOutpatientList(obj).then(r=>{
          _th.outpatientListType = r.data
        })
      }
    },
    getOutpatientIdList(outpatient,special,hospital,id){

      if (id===1){
        this.form.doctorPhone=null
      }
      let obj = {
        hospital:hospital,
        specialId:special,
        outpatientId:outpatient
      }
      getHospitalVisitDoc(obj).then(r=>{
        this.hospitalDocListType = r.data
      })

    },
    //获取医生的门诊价格
    getDocPriceList(value){

      let _th = this
      this.hospitalDocListType.forEach(function (phone) {

        if (value===phone.doctorPhone){
          _th.form.payPrice = parseInt(phone.chargePrice)/100
        }

      })

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
      this.ids = selection.map(item => item.appointmentId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加挂号预约";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const appointmentId = row.appointmentId || this.ids
      getVisitAppointment(appointmentId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改挂号预约";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {

          this.$confirm('请确保患者已支付门诊金额, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            if (this.form.appointmentId != null) {
              updateVisitAppointment(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addVisitAppointment(this.form).then(response => {
                this.$modal.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              });
            }
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消'
            });
          });



        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const appointmentIds = row.appointmentId || this.ids;
      this.$modal.confirm('是否确认删除挂号预约编号为"' + appointmentIds + '"的数据项？').then(function() {
        return delVisitAppointment(appointmentIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('hospital/visitAppointment/export', {
        ...this.queryParams
      }, `visitAppointment_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
