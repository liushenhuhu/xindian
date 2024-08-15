<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" class="biaodan">
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

      <el-form-item label="医院" prop="hospital">
        <el-select v-model="queryParams.hospital" filterable placeholder="请选择医院名称" clearable>
        <el-option
          v-for="item in hospitaloptions"
          :key="item.value"
          :label="item.hospitalName"
          :value="item.hospitalName">
        </el-option>
      </el-select>
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
      <el-form-item class="kaoyou">
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
      <el-table-column label="状态" align="center" prop="accountStatus" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.accountStatus === '0'" size="small">正常</el-tag>
          <el-tag v-else-if="scope.row.accountStatus === '1'" size="small" type="danger">停用</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="用户编号" align="center" prop="userId" />
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
          <el-dropdown  size="mini" @command="(command) => handleCommand(command, scope.row)"
                       v-hasPermi="['system:user:resetPwd', 'system:user:edit']">
                <span class="el-dropdown-link">
                  <i class="el-icon-d-arrow-right el-icon--right"></i>更多
                </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item v-if="scope.row.userId!=null&&scope.row.userId!=''" command="handleResetPwd" icon="el-icon-key"
                                v-hasPermi="['system:user:resetPwd']">重置密码
              </el-dropdown-item>
              <el-dropdown-item v-if="scope.row.userId!=null&&scope.row.userId!=''" command="handleUpdateUser" icon="el-icon-circle-check"
                                v-hasPermi="['system:user:edit']">修改角色信息
              </el-dropdown-item>
              <el-dropdown-item v-if="scope.row.userId==null||scope.row.userId==''" command="handleUserAdd" icon="el-icon-circle-check"
                                v-hasPermi="['system:user:add']">新增账号信息
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
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
      <el-form ref="form" :model="form" :rules="rules" label-width="130px">
        <el-form-item label="医生姓名" prop="doctorName">
          <el-input v-model="form.doctorName" placeholder="请输入医生姓名" />
        </el-form-item>
        <el-form-item label="医生电话" prop="doctorPhone">
          <el-input v-model="form.doctorPhone" placeholder="请输入医生电话" />
        </el-form-item>
        <el-form-item label="医院" prop="hospital">
<!--          <el-input v-model="form.hospital" placeholder="请输入医院" />-->
          <el-select v-model="form.hospital" placeholder="请选择医院名称" filterable clearable @change="historyId(form.hospital)">
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

        <el-form-item label="医生账号状态" prop="accountStatus">
          <el-radio-group v-model="form.accountStatus">
            <el-radio  label="0">正常</el-radio>
            <el-radio label="1">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="医生类型" prop="isDoc">
          <el-radio-group v-model="form.isDoc">
            <el-radio
              v-for="dict in dict.type.docker_type"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>

       <div v-if="form.isDoc==='2'">
         <el-form-item label="所属专科" prop="specialId">
           <el-select v-model="form.specialId" placeholder="请输入专科名称" clearable @change="getSpecialList(form.specialId,form.hospital,1)" >
             <el-option
               v-for="item in specialList"
               :key="item.hospitalSpecial.specialId"
               :label="item.hospitalSpecial.specialName"
               :value="item.hospitalSpecial.specialId">
             </el-option>
           </el-select>
         </el-form-item>
         <el-form-item label="所属门诊" prop="outpatientId">
           <el-select v-model="form.outpatientId" placeholder="请输入门诊名称" clearable >
             <el-option
               v-for="item in outpatientList"
               :key="item.hospitalOutpatient.outpatientId"
               :label="item.hospitalOutpatient.outpatientName"
               :value="item.hospitalOutpatient.outpatientId">
             </el-option>
           </el-select>
         </el-form-item>
       </div>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>




    <!-- 添加或修改用户配置对话框 -->
    <el-dialog :title="titleUser" :visible.sync="openUser" width="600px" append-to-body>
      <el-form ref="formUser" :model="formUser" :rules="rulesUser" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="用户昵称" prop="nickName">
              <el-input v-model="formUser.nickName" placeholder="请输入用户昵称" maxlength="30"/>
            </el-form-item>
          </el-col>


          <el-col :span="12">
            <el-form-item label="归属部门" >
              <treeselect v-model="formUser.deptId" :options="deptOptions" :show-count="true" @input="inputSelect" placeholder="请选择归属部门"/>
            </el-form-item>
          </el-col>


        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="手机号码" prop="phonenumber">
              <el-input v-model="formUser.phonenumber" disabled placeholder="请输入手机号码" maxlength="11"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="formUser.email" placeholder="请输入邮箱" maxlength="50"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="医院代号" prop="hospitalCode">
              <el-select v-model="formUser.hospitalCode" placeholder="请选择医院代号">
                <el-option
                  v-for="item in hospitaloptions"
                  :key="item.hospitalId"
                  :label="item.hospitalName"
                  :value="item.hospitalCode">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="科室代号" prop="departmentCode">
              <el-input v-model="formUser.departmentCode" placeholder="请输入科室代号" maxlength="50"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item v-if="formUser.userId == undefined" label="用户名称" prop="userName">
              <el-input v-model="formUser.userName" disabled placeholder="请输入用户名称" maxlength="30"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item v-if="formUser.userId == undefined" label="用户密码" prop="password">
              <el-input v-model="formUser.password" placeholder="请输入用户密码" type="password" maxlength="20" show-password/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="用户性别">
              <el-select v-model="formUser.sex" placeholder="请选择性别">
                <el-option
                  v-for="dict in dict.type.sys_user_sex"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态">
              <el-radio-group v-model="formUser.status">
                <el-radio
                  v-for="dict in dict.type.sys_normal_disable"
                  :key="dict.value"
                  :label="dict.value"
                >{{ dict.label }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="岗位">
              <el-select v-model="formUser.postIds" multiple placeholder="请选择岗位">
                <el-option
                  v-for="item in postOptions"
                  :key="item.postId"
                  :label="item.postName"
                  :value="item.postId"
                  :disabled="item.status == 1"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="角色">
              <el-select v-model="formUser.roleIds" multiple placeholder="请选择角色">
                <el-option
                  v-for="item in roleOptions"
                  :key="item.roleId"
                  :label="item.roleName"
                  :value="item.roleId"
                  :disabled="item.status == 1"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="备注">
              <el-input v-model="formUser.remark" type="textarea" placeholder="请输入内容"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFormUser">确 定</el-button>
        <el-button @click="cancelUser">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { listDoctor, getDoctor, delDoctor, addDoctor, updateDoctor ,hospitalCodeFind} from "@/api/doctor/doctor";
// 在页面上新增了根据医院名称搜索
import {listHospital as yiyuansousuo} from "@/api/hospital/hospital";
import { listDepartment } from "@/api/department/department";
import item from "@/layout/components/Sidebar/Item";
import {listHospital, listHospitalId} from "@/api/hospital/hospital";
import {getHospitalSpecialList} from "@/api/visit/hospitalRelation";
import {getHospitalOutpatientList} from "@/api/visit/hospitalOutpatient";
import {addUser, getUser, resetUserPwd, updateUser} from "@/api/system/user";
import Treeselect from "@riophae/vue-treeselect";
import {treeselect} from "@/api/system/dept";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Doctor",
  components: {Treeselect},
  dicts: ['hospital_name_list','docker_type','sys_user_sex','sys_normal_disable'],
  data() {
    return {
      hospitalvalue:null,
      hospitaloptions:[],
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
      optionsUser:[],
      options1:[],
      // 医生表格数据
      doctorList: [],
      deptOptions: undefined,
      // 弹出层标题
      title: "",
      titleUser: "",
      // 是否显示弹出层
      open: false,
      openUser: false,
      // 岗位选项
      postOptions: [],
      // 角色选项
      roleOptions: [],
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
        initPassword: undefined,
        // equipmentList:null
      },
      // 表单参数
      form: {},
      formUser: {},
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

      },
      // 表单校验
      rulesUser: {
        userName: [
          {required: true, message: "用户名称不能为空", trigger: "blur"},
          {min: 2, max: 20, message: '用户名称长度必须介于 2 和 20 之间', trigger: 'blur'}
        ],
        nickName: [
          {required: true, message: "用户昵称不能为空", trigger: "blur"}
        ],
        password: [
          {required: true, message: "用户密码不能为空", trigger: "blur"},
          {min: 5, max: 20, message: '用户密码长度必须介于 5 和 20 之间', trigger: 'blur'}
        ],
        email: [
          {
            type: "email",
            message: "请输入正确的邮箱地址",
            trigger: ["blur", "change"]
          }
        ],
        phonenumber: [
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "请输入正确的手机号码",
            trigger: "blur"
          }
        ],

        deptId: [
          {required: true, message: '所属模块不能为空', trigger: 'input'}
        ]
      }
    };
  },
  created() {
    this.getList();
    listHospitalId(null).then(r=>{
      // console.log(r.rows);
      this.hospitaloptions=r.rows
    })
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
          this.total = response.total;
          this.loading = false;
        });

    },
    inputSelect(data){
      this.$refs.formUser.validateField('deptId');
    },
    historyId(val){
      if (val!==""){
        this.form.departmentCode=null
        hospitalCodeFind(val).then(r=>{
         this.options1=r.data
        })
        let _th = this
        _th.options.forEach(function(value) {
          if (value.hospitalCode===val){
            let obj = {
              hospitalId:value.hospitalId
            }
            getHospitalSpecialList(obj).then(r=>{
              _th.specialList = r.data
            })
          }
        })
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
          }
        })

      }
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    cancelUser(){
      this.openUser = false;
      this.resetUser();
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
      // console.log(this.state);
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
        let is = this.historyId(response.data.hospitalCode)
        if (is){
          console.log(1111)
        }
        if (response.data.specialId&&response.data.hospitalCode){
          this.getSpecialList(response.data.specialId,response.data.hospitalCode,2)
        }

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
    },
    // 表单重置
    resetUser() {
      this.formUser = {
        userId: undefined,
        deptId: undefined,
        userName: undefined,
        nickName: undefined,
        password: undefined,
        phonenumber: undefined,
        email: undefined,
        sex: undefined,
        status: "0",
        remark: undefined,
        postIds: [],
        roleIds: []
      };
      this.resetForm("form");
    },
    /** 查询部门下拉树结构 */
    getTreeselectUser() {
      treeselect().then(response => {
        this.deptOptions = response.data;
      });
    },
    /** 修改按钮操作 */
    handleUpdateUser(row) {
      this.resetUser();
      this.getTreeselectUser();
      const userId = row.userId || this.ids;
      getUser(userId).then(response => {
        this.formUser = response.data;
        this.postOptions = response.posts;
        this.roleOptions = response.roles;
        this.formUser.postIds = response.postIds;
        this.formUser.roleIds = response.roleIds;
        this.openUser = true;
        this.titleUser = "修改用户";
        this.formUser.password = "";
      });
    },
    submitFormUser(){
      this.$refs["formUser"].validate(valid => {
        if (valid) {
          if (this.formUser.userId != undefined) {
            updateUser(this.formUser).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.openUser = false;
              this.getList();
            });
          } else {
            addUser(this.formUser).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.openUser = false;
              this.getList();
            });
          }
        }
      });
    },
    // 更多操作触发
    handleCommand(command, row) {
      switch (command) {
        case "handleResetPwd":
          this.handleResetPwd(row);
          break;
        case "handleUpdateUser":
          this.handleUpdateUser(row);
          break;
        case "handleUserAdd":
          this.handleAddUser(row);
          break;
        default:
          break;
      }
    },
    /** 新增按钮操作 */
    handleAddUser(row) {
      this.resetUser();
      this.getTreeselectUser();
      getUser().then(response => {
        this.postOptions = response.posts;
        this.roleOptions = response.roles;
        this.openUser = true;
        this.titleUser = "添加用户";
        this.formUser.password = '123456';
        this.formUser.phonenumber = row.doctorPhone;
        this.formUser.userName = row.doctorPhone;
        this.formUser.nickName = row.doctorName;
      });
    },
    /** 重置密码按钮操作 */
    handleResetPwd(row) {
      this.$prompt('请输入"' + row.doctorName + '"的新密码', "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        closeOnClickModal: false,
        inputPattern: /^.{5,20}$/,
        inputErrorMessage: "用户密码长度必须介于 5 和 20 之间"
      }).then(({value}) => {
        resetUserPwd(row.userId, value).then(response => {
          this.$modal.msgSuccess("修改成功，新密码是：" + value);
        });
      }).catch(() => {
      });
    },
  }
};
</script>
<!--<style>-->
<!--.avatar {-->
<!--  width: 160px;-->
<!--  height: 150px;-->
<!--  display: flex;-->
<!--}-->
<!--</style>-->
<!--<style lang="scss" scoped>-->
<!--::v-deep .biaodan{-->
<!--  display: flex;-->
<!--}-->
<!--::v-deep .kaoyou{-->
<!--  margin-left: auto;-->
<!--}-->
<!--</style>-->
