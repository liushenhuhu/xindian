<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="设备号" prop="equipmentCode">
        <el-input
          v-model="queryParams.equipmentCode"
          placeholder="请输入设备号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
<!--      <el-form-item label="设备版本号" prop="equipmentVersion">-->
<!--        <el-input-->
<!--          v-model="queryParams.equipmentVersion"-->
<!--          placeholder="请输入设备版本号"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
      <el-form-item label="设备状态" prop="equipmentStatus">
        <el-select v-model="queryParams.equipmentStatus" placeholder="请选择设备状态" clearable>
          <el-option
            v-for="dict in dict.type.equipment_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="医院" prop="hospitalCode">
        <el-select v-model="queryParams.hospitalCode" placeholder="请选择医院" clearable>
          <el-option
            v-for="item in options"
            :key="item.hospitalId"
            :label="item.hospitalName"
            :value="item.hospitalName">
          </el-option>
        </el-select>
      </el-form-item>
<!--      <el-form-item label="科室代号" prop="departmentCode">-->
<!--        <el-input-->
<!--          v-model="queryParams.departmentCode"-->
<!--          placeholder="请输入医院代号"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
      <el-form-item label="科室" prop="departmentCode">
<!--        <el-cascader :options="option" clearable v-model="queryParams.departmentCode"></el-cascader>-->
        <el-select v-model="queryParams.departmentName" placeholder="请选择科室" clearable>
          <el-option
            v-for="item in option"
            :key="item.label"
            :label="item.label"
            :value="item.label">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="设备种类" prop="equipmentType">
        <el-select v-model="queryParams.equipmentType" placeholder="请选择设备种类" clearable>
          <el-option
            v-for="dict in dict.type.ecg_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
<!--      <el-form-item label="患者电话" prop="patientPhone">
        <el-input
          v-model="queryParams.patientPhone"
          placeholder="请输入患者电话"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>-->
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
          v-hasPermi="['equipment:equipment:add']"
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
          v-hasPermi="['equipment:equipment:edit']"
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
          v-hasPermi="['equipment:equipment:remove']"
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
          v-hasPermi="['equipment:equipment:export']"
        >导出
        </el-button>
      </el-col>

      <right-toolbar :showSearch.sync="showSearch" @queryTable="refreshList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="equipmentList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="设备号" align="center" prop="equipmentCode"/>
      <el-table-column label="设备状态" align="center" prop="equipmentStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equipment_status" :value="scope.row.equipmentStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="医院" align="center" prop="hospitalCode"/>
      <el-table-column label="科室" align="center" prop="equipmentName"/>
      <el-table-column label="设备种类" align="center" prop="equipmentType">
        <template slot-scope="scope">
          <el-tag >
            {{scope.row.equipmentType}}
          </el-tag>
        </template>
      </el-table-column>
<!--      <el-table-column label="患者电话" align="center" prop="patientPhone"/>-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['equipment:equipment:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['equipment:equipment:remove']"
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

    <!-- 添加或修改设备对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="设备号" prop="equipmentCode">
          <el-input v-model="form.equipmentCode" placeholder="请输入设备号"/>
        </el-form-item>
<!--        <el-form-item label="设备版本号" prop="equipmentVersion">-->
<!--          <el-input v-model="form.equipmentVersion" placeholder="请输入设备版本号"/>-->
<!--        </el-form-item>-->
        <el-form-item label="设备状态" prop="equipmentStatus">
          <el-select v-model="form.equipmentStatus" placeholder="请选择设备状态">
            <el-option
              v-for="dict in dict.type.equipment_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="医院代号" prop="hospitalCode">
          <el-select v-model="form.hospitalCode" placeholder="请选择医院代号"  @change="historyId(form.hospitalCode)">
            <el-option
              v-for="item in options"
              :key="item.hospitalId"
              :label="item.hospitalName"
              :value="item.hospitalCode">
            </el-option>
          </el-select>
        </el-form-item>
<!--        <el-form-item label="科室代号" prop="departmentCode">-->
<!--          <el-input v-model="form.departmentCode" placeholder="请输入科室代号"/>-->
<!--        </el-form-item>-->
        <el-form-item label="科室" prop="equipmentName">

          <el-select v-model="form.departmentCode" placeholder="请选择科室" clearable>
            <el-option
              v-for="item in options1"
              :key="item.departmentId"
              :label="item.departmentName"
              :value="item.departmentCode">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="设备种类" prop="equipmentType">
          <el-select v-model="form.equipmentType" placeholder="请选择设备种类">
            <el-option
              v-for="dict in dict.type.ecg_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="患者电话" prop="patientPhone">
          <el-input v-model="form.patientPhone" placeholder="请输入患者电话"/>
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
  listEquipment,
  getEquipment,
  delEquipment,
  addEquipment,
  updateEquipment,
  updateEquipmentStatus
} from "@/api/equipment/equipment";
import $ from "jquery";
import {updateMonitoringStatus} from "@/api/patient/patient";
import {getUserInfo, updateStatus} from "@/api/patient_management/patient_management";
import {updateOnline1, updateOnline2, updateOnlineAll} from "@/api/online/online";
import {addDict, listHospitalId} from "@/api/hospital/hospital";
import {depList, listDepartment} from "@/api/department/department";
import {hospitalCodeFind, hospitalCodeName} from "@/api/doctor/doctor";

export default {
  name: "Equipment",
  dicts: ['equipment_status', 'ecg_type', 'hospital_name_list'],
  data() {
    return {
      //科室
      departmentDir: {},

      restaurants: [],
      state: '',
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
      options:[],
      options1:[],
      // 设备表格数据
      equipmentList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,

      // 状态列表
      statusList: [],
      option:[],

      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        equipmentCode: null,
        equipmentVersion: null,
        equipmentStatus: null,
        hospitalCode: null,
        departmentCode: null,
        departmentName: null,
        equipmentType: null,
        patientPhone: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        equipmentCode: [
          {required: true, message: "设备号不能为空", trigger: "blur"}
        ],
        hospitalCode: [
          {required: true, message: "医院代号不能为空", trigger: "blur"}
        ],
        departmentCode: [
          {required: true, message: "科室代号不能为空", trigger: "blur"}
        ],
        equipmentStatus: [
          {required: true, message: "设备状态不能为空", trigger: "blur"}
        ],
        equipmentType: [
          {required: true, message: "设备种类不能为空", trigger: "blur"}
        ],
        // patientPhone: [
        //   {required: true, message: "患者电话不能为空", trigger: "blur"}
        // ]
      }
    };
  },
  beforeCreate() {
    updateOnlineAll();
  },

  created() {
    listHospitalId(null).then(r=>{
      this.options=r.rows
    })
    depList().then(r=>{
      console.log(r)
      this.option=r.data
    })
    this.getList();
    this.loadAll();
  },
  methods: {
    querySearch(queryString, cb) {
      var restaurants = this.restaurants;
      var results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants;
      // 调用 callback 返回建议列表的数据
      cb(results);
    },
    historyId(val){
      if (val!==""){
        hospitalCodeFind(val).then(r=>{
          console.log(val)
          this.options1=r.data
        })
      }

    },
    handleSelect(item) {
      this.queryParams.departmentCode=item.id;
    },
    handleIconClick() {
      this.state=null
    },
    addSelect(item) {
      this.form.departmentCode=item.id;
    },
    refreshList() {
      console.log("refresh======")
      updateOnlineAll().then(res => {
        this.getList();
      })
    },
    loadAll() {
      listDepartment(this.queryParams).then(response => {
        response.rows.forEach(item => this.restaurants.push({"id": item.departmentCode, "value": item.departmentName}));
      });
    },

    /** 查询设备列表 */
    getList() {
      this.loading = true;
      listEquipment(this.queryParams).then(response => {
        console.log(response)
        this.equipmentList = response.rows;
        this.total = response.total;

        this.loading = false;
      })

      addDict()
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.state='';
      this.form = {
        equipmentId: null,
        equipmentCode: null,
        equipmentName: null,
        equipmentVersion: null,
        equipmentStatus: null,
        hospitalCode: null,
        departmentCode: null,
        equipmentType: null,
        patientPhone: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      if(typeof this.state  != 'string')
        this.queryParams.departmentCode=null
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams= {
        pageNum: 1,
          equipmentCode: null,
          equipmentVersion: null,
          equipmentStatus: null,
          hospitalCode: null,
          departmentCode: null,
          departmentName: null,
          equipmentType: null,
          patientPhone: null
      },
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.equipmentId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加设备";
      listHospitalId(null).then(r=>{
        this.options=r.rows
      })
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const equipmentId = row.equipmentId || this.ids
      getEquipment(equipmentId).then(response => {
        console.log(response)
        this.form = response.data;
        this.open = true;
        this.title = "修改设备";
        hospitalCodeFind(response.data.hospitalCode).then(r=>{
          this.options1=r.data
        })
      });
      listHospitalId(null).then(r=>{
        this.options=r.rows
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.equipmentId != null) {
            console.log(this.form)
            updateEquipment(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addEquipment(this.form).then(response => {
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
      const equipmentIds = row.equipmentId || this.ids;
      this.$modal.confirm('是否确认删除设备编号为"' + equipmentIds + '"的数据项？').then(function () {
        return delEquipment(equipmentIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('equipment/equipment/export', {
        ...this.queryParams
      }, `equipment_${new Date().getTime()}.xlsx`)
    },


  }
};
</script>
