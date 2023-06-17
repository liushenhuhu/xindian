<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="医生姓名" prop="doctorName">
        <el-input
          v-model="queryParams.doctorName"
          placeholder="请输入医生姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="医生电话" prop="doctorPhone">
        <el-input
          v-model="queryParams.doctorPhone"
          placeholder="请输入医生电话"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="科室" prop="departmentCode">
<!--        <el-input-->
<!--          v-model="queryParams.departmentCode"-->
<!--          placeholder="请输入科室代号"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
        <el-autocomplete
          popper-class="my-autocomplete"
          v-model="state"
          :fetch-suggestions="querySearch"
          placeholder="请输入科室"
          @select="handleSelect">
          <i
            class="el-icon-circle-close"
            slot="suffix"
            @click="handleIconClick">
          </i>
          <template slot-scope="{ item }">
            <div class="name">{{ item.value }}</div>
          </template>
        </el-autocomplete>
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
          v-hasPermi="['doctor:doctor:add']"
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
          v-hasPermi="['doctor:doctor:edit']"
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
          v-hasPermi="['doctor:doctor:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['doctor:doctor:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="doctorList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="医生姓名" align="center" prop="doctorName" />
      <el-table-column label="医生电话" align="center" prop="doctorPhone" />
      <el-table-column label="医院" align="center" prop="hospital" />
      <el-table-column label="科室" align="center" prop="departmentName" />
      <el-table-column label="职称" align="center" prop="professional" />
<!--      <el-table-column label="关联设备" align="center" prop="equipmentList" />-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['doctor:doctor:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['doctor:doctor:remove']"
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

    <!-- 添加或修改医生对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="医生姓名" prop="doctorName">
          <el-input v-model="form.doctorName" placeholder="请输入医生姓名" />
        </el-form-item>
        <el-form-item label="医生电话" prop="doctorPhone">
          <el-input v-model="form.doctorPhone" placeholder="请输入医生电话" />
        </el-form-item>
        <el-form-item label="医院" prop="hospital">
<!--          <el-input v-model="form.hospital" placeholder="请输入医院" />-->
          <el-select v-model="form.hospital" placeholder="请选择医院名称" clearable>
            <el-option
              v-for="dict in dict.type.hospital_name_list"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="科室" prop="departmentCode">
<!--          <el-input v-model="form.departmentCode" placeholder="请输入科室代号" />-->
          <el-autocomplete
            popper-class="my-autocomplete"
            v-model="form.departmentName"
            :fetch-suggestions="querySearch"
            placeholder="请输入科室"
            @select="addSelect">
            <template slot-scope="{ item }">
              <div class="name">{{ item.value }}</div>
            </template>
          </el-autocomplete>
        </el-form-item>
        <el-form-item label="职称" prop="professional">
          <el-input v-model="form.professional" type="textarea" autosize placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="擅长" prop="skill">
          <el-input v-model="form.skill" type="textarea" autosize placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="简介" prop="introduction">
          <el-input v-model="form.introduction" type="textarea" autosize placeholder="请输入内容" />
<!--          <editor v-model="form.introduction" :min-height="100"/>-->
        </el-form-item>
        <el-form-item label="服务内容" prop="serviceContent">
          <el-input v-model="form.serviceContent" type="textarea" autosize placeholder="请输入内容" />
<!--          <editor v-model="form.serviceContent" :min-height="100"/>-->
        </el-form-item>
        <el-form-item label="适用人群" prop="applicablePopulation">
          <el-input v-model="form.applicablePopulation" placeholder="请输入适用人群" />
        </el-form-item>
        <el-form-item label="收费价格" prop="chargePrice">
          <el-input v-model="form.chargePrice" placeholder="请输入收费价格" />
        </el-form-item>
<!--        <el-form-item label="关联设备" prop="chargePrice">-->
<!--          <el-input v-model="form.equipmentList" placeholder="请输入逗号分割的设备号" />-->
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
import { listDoctor, getDoctor, delDoctor, addDoctor, updateDoctor } from "@/api/doctor/doctor";
import { listDepartment } from "@/api/department/department";
import item from "@/layout/components/Sidebar/Item";

export default {
  name: "Doctor",
  dicts: ['hospital_name_list'],
  data() {
    return {
      //科室
      restaurants: [],
      state: '',
      addState: '',
      dirRestaurants:{},
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
      // 医生表格数据
      doctorList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        doctorName: null,
        doctorPhone: null,
        departmentCode: null,
        departmentName: null,
        skill: null,
        introduction: null,
        serviceContent: null,
        applicablePopulation: null,
        chargePrice: null,
        onlineState: null,
        hospital: null,
        img: null,
        professional:null,
        // equipmentList:null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        doctorName: [
          { required: true, message: "医生姓名不能为空", trigger: "blur" }
        ],
        doctorPhone: [
          { required: true, message: "医生电话不能为空", trigger: "blur" }
        ],
        hospital: [
          { required: true, message: "医院不能为空", trigger: "blur" }
        ],
        departmentCode: [
          { required: true, message: "科室不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
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
    createFilter(queryString) {
      return (restaurant) => {
        return (restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
      };
    },
    loadAll() {
        listDepartment(this.queryParams).then(response => {
          response.rows.forEach(item => this.restaurants.push({"id": item.departmentCode, "value": item.departmentName}));
        });
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
    /** 查询医生列表 */
    getList() {
      this.loading = true;
      listDoctor(this.queryParams).then(response => {
        this.doctorList = response.rows;
        // for(var i=0;i<response.rows.length;i++){
        //   this.restaurants.find(item => console.log(item))
        //   this.doctorList[i].departmentCode=this.restaurants.find(item => item.id === response.rows[i].departmentCode);
        // }
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
      this.state='';
      this.form = {
        doctorId: null,
        doctorName: null,
        doctorPhone: null,
        departmentCode: null,
        departmentName:null,
        skill: null,
        introduction: null,
        serviceContent: null,
        applicablePopulation: null,
        chargePrice: null,
        onlineState: null,
        hospital: null,
        img: null,
        professional:null,
        // equipmentList:null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      console.log(this.state);
      if(typeof this.state  != 'string')
        this.queryParams.departmentCode=null
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.doctorId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加医生";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const doctorId = row.doctorId || this.ids
      getDoctor(doctorId).then(response => {
        this.form = response.data;
        console.log(response.data);
        this.open = true;
        this.title = "修改医生";
      });
      console.log(this.form.departmentName);
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.doctorId != null) {
            updateDoctor(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDoctor(this.form).then(response => {
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
      const doctorIds = row.doctorId || this.ids;
      this.$modal.confirm('是否确认删除医生编号为"' + doctorIds + '"的数据项？').then(function() {
        return delDoctor(doctorIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('doctor/doctor/export', {
        ...this.queryParams
      }, `doctor_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
