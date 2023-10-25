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
<!--      <el-form-item label="科室" prop="departmentCode">-->
<!--&lt;!&ndash;        <el-input&ndash;&gt;-->
<!--&lt;!&ndash;          v-model="queryParams.departmentCode"&ndash;&gt;-->
<!--&lt;!&ndash;          placeholder="请输入科室代号"&ndash;&gt;-->
<!--&lt;!&ndash;          clearable&ndash;&gt;-->
<!--&lt;!&ndash;          @keyup.enter.native="handleQuery"&ndash;&gt;-->
<!--&lt;!&ndash;        />&ndash;&gt;-->

<!--&lt;!&ndash;          <el-cascader&ndash;&gt;-->
<!--&lt;!&ndash;            v-model="state"&ndash;&gt;-->
<!--&lt;!&ndash;            :options="options"&ndash;&gt;-->
<!--&lt;!&ndash;            @change="handleChange"></el-cascader>&ndash;&gt;-->

<!--        <el-autocomplete-->
<!--          popper-class="my-autocomplete"-->
<!--          v-model="state"-->
<!--          :fetch-suggestions="querySearch"-->
<!--          placeholder="请输入科室"-->
<!--          @select="handleSelect">-->
<!--          <i-->
<!--            class="el-icon-circle-close"-->
<!--            slot="suffix"-->
<!--            @click="handleIconClick">-->
<!--          </i>-->
<!--          <template slot-scope="{ item }">-->
<!--            <div class="name">{{ item.value }}</div>-->
<!--          </template>-->
<!--        </el-autocomplete>-->
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
          <el-select v-model="form.hospital" placeholder="请选择医院名称" clearable @change="historyId(form.hospital)">
            <el-option
              v-for="item in options"
              :key="item.hospitalId"
              :label="item.hospitalName"
              :value="item.hospitalCode">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="科室" prop="departmentCode">
<!--          <el-input v-model="form.departmentCode" placeholder="请输入科室代号" />-->
          <el-select v-model="form.departmentCode" placeholder="请选择科室" clearable >
            <el-option
              v-for="item in options1"
              :key="item.departmentId"
              :label="item.departmentName"
              :value="item.departmentCode">
            </el-option>
          </el-select>
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
        <el-form-item label="医生图片">
          <el-upload
            action="#"
            :auto-upload="false"
            list-type="picture-card"
            :file-list="imgFileList"
            :limit="1"
            :on-preview="handlePictureCardPreview"
            :on-change="handleAvatarSuccess"
            :on-remove="handleRemove">
            <img v-if="form.img" :src="form.img" class="avatar">

            <i v-else class="el-icon-plus"></i>
          </el-upload>
          <el-dialog :visible.sync="dialogVisible">
            <img width="100%" :src="dialogImageUrl" alt="">
          </el-dialog>
        </el-form-item>

        <el-form-item label="医生类型" prop="isDoc">
          <el-radio-group v-model="form.isDoc">
            <el-radio  label="0">测试医生账号</el-radio>
            <el-radio label="1">心电医生账号</el-radio>
            <el-radio label="2">门诊医生账号</el-radio>
          </el-radio-group>
          <span style="color: red">  (测试用的医生账号,不会作为专业的医生去随机推送患者诊断请求)</span>
        </el-form-item>

<!--       <div v-if="form.isDoc==='2'">-->
<!--         <el-form-item label="所属专科" prop="specialId">-->
<!--           <el-select v-model="form.specialId" placeholder="请输入专科名称" clearable @change="getSpecialList(form.specialId,form.hospital,1)" >-->
<!--             <el-option-->
<!--               v-for="item in specialList"-->
<!--               :key="item.hospitalSpecial.specialId"-->
<!--               :label="item.hospitalSpecial.specialName"-->
<!--               :value="item.hospitalSpecial.specialId">-->
<!--             </el-option>-->
<!--           </el-select>-->
<!--         </el-form-item>-->
<!--         <el-form-item label="所属门诊" prop="outpatientId">-->
<!--           <el-select v-model="form.outpatientId" placeholder="请输入门诊名称" clearable >-->
<!--             <el-option-->
<!--               v-for="item in outpatientList"-->
<!--               :key="item.hospitalOutpatient.outpatientId"-->
<!--               :label="item.hospitalOutpatient.outpatientName"-->
<!--               :value="item.hospitalOutpatient.outpatientId">-->
<!--             </el-option>-->
<!--           </el-select>-->
<!--         </el-form-item>-->
<!--       </div>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listDoctor, getDoctor, delDoctor, addDoctor, updateDoctor ,hospitalCodeFind} from "@/api/doctor/doctor";
import { listDepartment } from "@/api/department/department";
import item from "@/layout/components/Sidebar/Item";
import {listHospital, listHospitalId} from "@/api/hospital/hospital";
import {getHospitalSpecialList} from "@/api/visit/hospitalRelation";
import {getHospitalOutpatientList} from "@/api/visit/hospitalOutpatient";

export default {
  name: "Doctor",
  dicts: ['hospital_name_list'],
  data() {
    return {
      dialogImageUrl: '',
      dialogVisible: false,
      disabled: false,
      //科室
      restaurants: [],
      state: '',
      addState: '',
      dirRestaurants:{},
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      imgFileList:[],
      outpatientList:[],
      specialList:[],
      uploadFile:{

      },
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      options:[],
      options1:[],
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
        ],
        isDoc: [
          { required: true, message: "请选择医生类型", trigger: "change" }
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
    this.getList();
    this.loadAll();
  },
  methods: {

    querySearch(queryString, cb) {
      var restaurants = this.restaurants;
      var results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants;
      // 调用 callback 返回建议列表的数据
      cb(results);
      console.log(results)
    },
    createFilter(queryString) {
      return (restaurant) => {
        return (restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
      };
    },
    handleAvatarSuccess(file,fileList){
      if (!/\.(jpg|JPG)$/.test(file.name)) {
        this.$message({
          type: 'warning',
          message: '只支持格式为jpg/JPG的文件！'
        })
        fileList.pop()
        return false
      }
      this.form.img=null
      this.imgFileList = fileList
      this.form.imgFile=file
    },
    handleRemove(file,fileList) {
      this.imgFileList = fileList
      this.form.imgFile=null
    },
    handlePictureCardPreview(file) {

      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    handleDownload(file) {
      console.log(file);
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
    historyId(val){
      if (val!==""){
        hospitalCodeFind(val).then(r=>{
         this.options1=r.data
        })
        // let _th = this
        // _th.options.forEach(function(value) {
        //   if (value.hospitalCode===val){
        //     let obj = {
        //       hospitalId:value.hospitalId
        //     }
        //     getHospitalSpecialList(obj).then(r=>{
        //       _th.specialList = r.data
        //     })
        //     return;
        //   }
        // })
      }
    },
    getSpecialList(val,hospital,id){
      if (id===1){
        this.form.outpatientId=null
      }
      if (val!==""){
        let _th = this
        _th.options.forEach(function(value) {
          if (value.hospitalCode===hospital){
            let obj = {
              hospitalId:value.hospitalId,
              specialId:val
            }
            getHospitalOutpatientList(obj).then(r=>{
              _th.outpatientList = r.data
            })
            return;
          }
        })

      }
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
        outpatientId:null,
        specialId:null,

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
      this.imgFileList=[]
      this.reset();
      this.open = true;
      this.title = "添加医生";
      listHospitalId(null).then(r=>{
        this.options=r.rows
      })

    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.imgFileList=[]
      this.reset();
      const doctorId = row.doctorId || this.ids
      getDoctor(doctorId).then(response => {
        this.historyId(response.data.hospitalCode)
        // if (response.data.specialId&&response.data.hospitalCode){
        //   this.getSpecialList(response.data.specialId,response.data.hospitalCode,2)
        // }
        this.form = response.data;

        this.form.hospital = response.data.hospitalCode
        console.log(response.data)
        this.open = true;
        this.title = "修改医生";
      });
      listHospitalId(null).then(r=>{
        this.options=r.rows
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          let data=new FormData();
          data.append("doctor",JSON.stringify(this.form))
          if(this.form.imgFile!==undefined){
            data.append("imgFile",this.form.imgFile.raw)
          }

          if (this.form.doctorId != null) {
            updateDoctor(data).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDoctor(data).then(response => {
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
<style>
.avatar {
  width: 160px;
  height: 150px;
  display: flex;
}
</style>
