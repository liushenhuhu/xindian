<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="省份" prop="province">
        <el-input
          v-model="queryParams.province"
          placeholder="请输入省份"
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
<!--      <el-form-item label="医院代号" prop="hospitalCode">-->
<!--        <el-input-->
<!--          v-model="queryParams.hospitalCode"-->
<!--          placeholder="请输入医院代号"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->

      <el-form-item label="医院名称" prop="hospitalName">
        <el-select v-model="queryParams.hospitalCode" placeholder="请选择医院代号" >
          <el-option
            v-for="item in options"
            :key="item.hospitalId"
            :label="item.hospitalName"
            :value="item.hospitalId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button v-if="isQb" plain type="primary" icon="el-icon-edit" size="mini" @click="findXD">查看全部医院心电大屏</el-button>
      </el-col>
      <el-col :span="1.5">
        <span style="margin-left: 20px">选择医院：</span>
        <el-select v-model="hospitalId" placeholder="请选择医院" >
          <el-option
            v-for="item in options"
            :key="item.hospitalId"
            :label="item.hospitalName"
            :value="item.hospitalId">
          </el-option>
        </el-select>
        <el-button v-if="isQb" plain type="primary" icon="el-icon-edit" size="mini" @click="findXDBYOne">查看心电大屏</el-button>
      </el-col>
      <div class="texta">总在线设备数：{{num}}</div>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="refresh"></right-toolbar>
    </el-row>
    <el-table v-loading="loading" :data="hospitalList" @selection-change="handleSelectionChange">
      <el-table-column label="省份" align="center" prop="province" />
<!--      <el-table-column label="医院名称" align="center" prop="hospitalName" />-->
<!--      <el-table-column label="医院代号" align="center" prop="hospitalCode" />-->
      <el-table-column label="在线设备数量" align="center" prop="deviceOnlineNum" />
      <el-table-column label="是否开通数据统计" align="center" prop="ifStatistics">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.if" :value="scope.row.ifStatistics"/>
        </template>
      </el-table-column>
      <!--  隐藏的患者的个人信息    -->
      <el-table-column type="expand">
        <template slot-scope="scope">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-divider content-position="left">其他信息</el-divider>
            <el-form-item label="医院代号" width="200" style="padding-left: 40px">
              <span>{{ scope.row.hospitalCode }}</span>
            </el-form-item>
            <el-form-item label="医院名称" width="200" style="padding-left: 40px">
              <span>{{ scope.row.hospitalName }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            plain
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >查看心电大屏数据</el-button>
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


  </div>
</template>

<script>
import {listHospital, getHospital, delHospital, addHospital, updateHospital, addDict,onlineNum} from "@/api/hospital/hospitalList";
import {updateOnline1, updateOnlineAll} from "@/api/online/online"
import {listHospitalId} from "@/api/hospital/hospital";
export default {
  name: "HospitalList",
  dicts: ['if'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      isQb:true,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 医院表格数据
      hospitalList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      hospitalId:null,
      options:[],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        province: null,
        hospitalName: null,
        hospitalCode: null,
        hospitalAccount: null,
        hospitalPassword: null,
        equipmentNumber: null,
        patientNumber: null,
        monitoringPatientNumber: null,
        accountNumber: null,
        firstEcgTime: null,
        ifStatistics: null
      },
      // 表单参数
      form: {},
      num:'',
      // 表单校验
      rules: {
        hospitalName: [
          { required: true, message: "医院名称不能为空", trigger: "blur" }
        ],
        hospitalCode: [
          { required: true, message: "医院代号不能为空", trigger: "blur" }
        ],
      },
      timer:null
    };
  },
  created() {
    this.updateOnline()
    this.getList();
    listHospitalId(null).then(r=>{
      this.options=r.rows
    })
  },
  beforeDestroy() {
    console.log('beeeeeeeeeeeeeee')
    if(this.timer){
      clearInterval(this.timer)   //结束定时器，
    }
  },
  methods: {
    findXD(){
      const hospitalId = '1'
      console.log(hospitalId)
      this.$router.push({path:'ECGscreen',query:{hospitalId:hospitalId}})
    },
    findXDBYOne(){
      if (!this.hospitalId){
        return;
      }
      this.$router.push({path:'ECGscreen',query:{hospitalId:this.hospitalId}})
    },
    //请求设备在线设备数量修改在线状态
    updateOnline(){
      updateOnline1().then(res=>{
        // console.log(res)
        onlineNum().then(res=>{
          // console.log("在线设备数量",res)
          this.num=res.data
          listHospital(this.queryParams).then(response => {
            // console.log(response)
            this.hospitalList = response.rows;
            this.total = response.total;
          });
        })
      })
    },
    /** 查询医院列表 */
    getList() {
      this.loading = true;
      onlineNum().then(res=>{
        console.log("在线设备数量",res)
        this.num=res.data
      })
      listHospital(this.queryParams).then(response => {
        console.log(response)
        this.hospitalList = response.rows;
        this.total = response.total;
        this.loading = false;
        if(this.timer){
          clearInterval(this.timer)   //结束定时器，
        }
        this.timer=setInterval(()=>{
          this.updateOnline()
        },5000)
      });
      // JSON.parse("")
    },
    /** 刷新 */
    refresh() {
      this.updateOnline()
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        hospitalId: null,
        province: null,
        hospitalName: null,
        hospitalCode: null,
        hospitalAccount: null,
        hospitalPassword: null,
        equipmentNumber: null,
        patientNumber: null,
        monitoringPatientNumber: null,
        accountNumber: null,
        firstEcgTime: null,
        ifStatistics: "0"
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
      this.ids = selection.map(item => item.hospitalId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const hospitalId = row.hospitalId || this.ids
      console.log(hospitalId)
      this.$router.push({path:'/ECGscreen',query:{hospitalId:hospitalId}})
    },
    // /** 提交按钮 */
    // submitForm() {
    //   this.$refs["form"].validate(valid => {
    //     if (valid) {
    //       if (this.form.hospitalId != null) {
    //         updateHospital(this.form).then(response => {
    //           this.$modal.msgSuccess("修改成功");
    //           this.open = false;
    //           this.getList();
    //         });
    //       } else {
    //         addHospital(this.form).then(response => {
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
      const hospitalIds = row.hospitalId || this.ids;
      this.$modal.confirm('是否确认删除医院编号为"' + hospitalIds + '"的数据项？').then(function() {
        return delHospital(hospitalIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('hospital/hospital/export', {
        ...this.queryParams
      }, `hospital_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
<style scoped>
.mb8{
  margin-top: 10px;
  font-weight: 700;
}
.texta{
  display: inline-flex;
  margin-left: 25vw;
  font-size: 22px;
}
</style>
