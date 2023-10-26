<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
<!--      <el-form-item label="医院编号" prop="hospitalCode">-->
<!--        <el-input-->
<!--          v-model="queryParams.hospitalCode"-->
<!--          placeholder="请输入医院编号"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
      <el-form-item label="专科名称" prop="specialId">
        <el-select v-model="queryParams.specialId" placeholder="请输入专科名称" clearable @change="getSpecialList(form.specialId)" >
          <el-option
            v-for="item in specialList"
            :key="item.hospitalSpecial.specialId"
            :label="item.hospitalSpecial.specialName"
            :value="item.hospitalSpecial.specialId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="门诊名称" prop="outpatientId">
        <el-select v-model="queryParams.outpatientId" placeholder="请输入门诊名称" clearable >
          <el-option
            v-for="item in outpatientList"
            :key="item.hospitalOutpatient.outpatientId"
            :label="item.hospitalOutpatient.outpatientName"
            :value="item.hospitalOutpatient.outpatientId">
          </el-option>
        </el-select>
      </el-form-item>
<!--      <el-form-item label="诊室编号" prop="clinicId">-->
<!--        <el-input-->
<!--          v-model="queryParams.clinicId"-->
<!--          placeholder="请输入诊室编号"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
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
      <el-form-item label="会诊时间段" prop="time">
        <el-select v-model="queryParams.time" clearable placeholder="请选择就诊时间段">
          <el-option label="上午" value="1"></el-option>
          <el-option label="下午" value="2"></el-option>
          <el-option label="全天" value="3"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="出诊日期" prop="day">
        <el-date-picker clearable
                        v-model="queryParams.day"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择出诊日期">
        </el-date-picker>
      </el-form-item>
<!--      <el-form-item label="创建时间" prop="gmtCreate">-->
<!--        <el-date-picker clearable-->
<!--                        v-model="queryParams.gmtCreate"-->
<!--                        type="date"-->
<!--                        value-format="yyyy-MM-dd"-->
<!--                        placeholder="请选择创建时间">-->
<!--        </el-date-picker>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="更新时间" prop="gmtModified">-->
<!--        <el-date-picker clearable-->
<!--                        v-model="queryParams.gmtModified"-->
<!--                        type="date"-->
<!--                        value-format="yyyy-MM-dd"-->
<!--                        placeholder="请选择更新时间">-->
<!--        </el-date-picker>-->
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
          v-hasPermi="['system:plan:add']"
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
          v-hasPermi="['system:plan:edit']"
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
          v-hasPermi="['system:plan:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:plan:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="planList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="出诊编号" align="center" prop="planId" />
      <el-table-column label="医院名称" align="center" prop="hospital.hospitalName" show-overflow-tooltip/>
      <el-table-column label="专科名称" align="center" prop="hospitalSpecial.specialName" />
      <el-table-column label="门诊名称" align="center" prop="hospitalOutpatient.outpatientName" />
      <el-table-column label="诊室地址" align="center" prop="hospitalClinic.address" show-overflow-tooltip/>
      <el-table-column label="医生名称" align="center" prop="doctor.doctorName" />
      <el-table-column label="医生手机号" align="center" prop="doctorPhone" />
      <el-table-column label="排班类型" align="center" prop="time">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.time===1" type="success">上午</el-tag>
          <el-tag v-if="scope.row.time===2" type="warning">下午</el-tag>
          <el-tag v-if="scope.row.time===3">全天</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="出诊日期" align="center" prop="day" >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.day, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="gmtCreate" width="100">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.gmtCreate, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:plan:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:plan:remove']"
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

    <!-- 添加或修改【请填写功能名称】对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
<!--        <el-form-item label="医院编号" prop="hospitalCode">-->
<!--          <el-input v-model="form.hospitalCode" placeholder="请输入医院编号" />-->
<!--        </el-form-item>-->
        <el-form-item label="专科名称" prop="specialCode">
          <el-input v-model="form.specialName" placeholder="请输入专科名称" disabled/>
        </el-form-item>
        <el-form-item label="门诊名称" prop="outpatientId">
          <el-input v-model="form.outpatientName" placeholder="请输入门诊名称" disabled/>
        </el-form-item>

        <el-form-item label="医生" prop="doctorPhone">
          <el-select v-model="form.doctorPhone" placeholder="请选择医生名称" clearable @change="docIsSpecial(form.doctorPhone,1)">
            <el-option
              v-for="item in hospitalDocList"
              :key="item.doctorId"
              :label="item.doctorName"
              :value="item.doctorPhone">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="诊室" prop="clinicId">
          <el-select v-model="form.clinicId" placeholder="请选择诊室" clearable >
            <el-option
              v-for="item in clinicList"
              :key="item.id"
              :label="item.address"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="会诊时间段" prop="time">
          <el-select v-model="form.time" clearable placeholder="请选择就诊时间段">
            <el-option
              v-for="item in teamOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="出诊日期" prop="day">
          <el-date-picker clearable
                          v-model="form.day"
                          type="date"
                          value-format="yyyy-MM-dd"
                          :picker-options="pickerOption"
                          placeholder="请选择出诊日期">
          </el-date-picker>
        </el-form-item>
<!--        <el-form-item label="创建时间" prop="gmtCreate">-->
<!--          <el-date-picker clearable-->
<!--                          v-model="form.gmtCreate"-->
<!--                          type="date"-->
<!--                          value-format="yyyy-MM-dd"-->
<!--                          placeholder="请选择创建时间">-->
<!--          </el-date-picker>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="更新时间" prop="gmtModified">-->
<!--          <el-date-picker clearable-->
<!--                          v-model="form.gmtModified"-->
<!--                          type="date"-->
<!--                          value-format="yyyy-MM-dd"-->
<!--                          placeholder="请选择更新时间">-->
<!--          </el-date-picker>-->
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
import { listPlan, getPlan, delPlan, addPlan, updatePlan } from "@/api/visit/plan";
import {getSpecialList} from "@/api/visit/special";
import {getOutpatientList} from "@/api/visit/outpatient";
import {getHospitalOutpatientList} from "@/api/visit/hospitalOutpatient";
import {getHospitalSpecialList} from "@/api/visit/hospitalRelation";
import {getHospitalVisitDoc} from "@/api/doctor/doctor";
import {listClinic} from "@/api/visit/clinic";

export default {
  name: "Plan",
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
      // 【请填写功能名称】表格数据
      planList: [],
      outpatientList:[],
      // 弹出层标题
      title: "",
      pickerOption: {
        disabledDate: (time) => {
          return Date.now() - 3600 * 1000 * 24 > time.getTime();
        },
      },
      // 是否显示弹出层
      open: false,
      specialList:[],
      hospitalDocList:[],
      clinicList:[],
      teamOptions : [
        {
          label: "上午（08：00-12：00）",
          value: 1
        },
        {
          label: "下午（14：00-17：30）",
          value: 2
        },
        {
          label: "全天（08：00-17：30）",
          value: 3
        },
      ],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        hospitalId: null,
        specialId: null,
        outpatientId: null,
        clinicId: null,
        doctorPhone: null,
        time: null,
        day: null,
        gmtCreate: null,
        gmtModified: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        hospitalCode: [
          { required: true, message: "医院编号不能为空", trigger: "blur" }
        ],
        doctorPhone: [
          { required: true, message: "医生手机号不能为空", trigger: "blur" }
        ],
        time: [
          { required: true, message: "时间段 不能为空", trigger: "blur" }
        ],
        day: [
          { required: true, message: "出诊日期不能为空", trigger: "blur" }
        ],
        clinicId: [
          { required: true, message: "诊室地址不能为空", trigger: "blur" }
        ],
      }
    };
  },
  activated() {
    this.getList();
  },
  created() {
    this.getList();

    let obj = {
      hospitalId:this.$route.query.hospitalId
    }
    getHospitalSpecialList(obj).then(r=>{
      this.specialList = r.data
    })
    getHospitalOutpatientList(obj).then(r=>{
      this.outpatientList = r.data
    })
    getHospitalVisitDoc(this.$route.query.hospitalId).then(r=>{
      this.hospitalDocList = r.data
    })
  },
  methods: {
    /** 查询【请填写功能名称】列表 */
    getList() {
      this.loading = true;
      if (this.$route.query.hospitalId){
        this.queryParams.hospitalId=this.$route.query.hospitalId
      }
      listPlan(this.queryParams).then(response => {
        console.log(response)
        this.planList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    docIsSpecial(val,id){

      console.log(val)
      if (id===1){
        this.form.clinicId = null;
      }
      let _th = this
      this.hospitalDocList.forEach(function (doc) {
        if (doc.doctorPhone===val){
          console.log(222)
          _th.specialList.forEach(function (special) {
            if (doc.specialId===special.hospitalSpecial.specialId){
              console.log(333)
              _th.form.specialName=special.hospitalSpecial.specialName
            }
          })
          _th.outpatientList.forEach(function (outpatient) {
            console.log(444)
            console.log(outpatient)
            console.log(doc)
            if (doc.outpatientId===outpatient.hospitalOutpatient.outpatientId){
              _th.form.outpatientName=outpatient.hospitalOutpatient.outpatientName

              let obj = {
                outpatientId:outpatient.hospitalOutpatient.outpatientId
              }
              console.log(555)
              listClinic(obj).then(r=>{
                console.log(r.rows)
                _th.clinicList = r.rows
              })
            }
          })
        }
      })


    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        planId: null,
        hospitalId: null,
        specialId: null,
        outpatientId: null,
        clinicId: null,
        doctorPhone: null,
        time: null,
        day: null,
        gmtCreate: null,
        gmtModified: null,
        specialName:null,
        outpatientName:null,
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
      this.ids = selection.map(item => item.planId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加排班";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const planId = row.planId || this.ids
      getPlan(planId).then(response => {
        console.log(response)
        this.form = response.data;
        this.docIsSpecial(response.data.doctorPhone,2)
        this.open = true;
        this.title = "修改排班";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.$route.query.hospitalId){
            this.form.hospitalId=this.$route.query.hospitalId
          }
          if (this.form.planId != null) {
            updatePlan(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPlan(this.form).then(response => {
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
      const planIds = row.planId || this.ids;
      this.$modal.confirm('是否确认删除【请填写功能名称】编号为"' + planIds + '"的数据项？').then(function() {
        return delPlan(planIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/plan/export', {
        ...this.queryParams
      }, `plan_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
