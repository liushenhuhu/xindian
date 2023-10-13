<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="日志id" prop="province">
        <el-input
          v-model="queryParams.logId"
          placeholder="请输入日志id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="标注差异" prop="isLabel">
        <el-select v-model="queryParams.isLabel" placeholder="请选择是否查看标注差异" clearable>
          <el-option
            v-for="dict in dict.type.if"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>

<!--      <el-form-item label="标注标签差异" prop="isLabel">-->
<!--        <el-select v-model="queryParams.isLabel" placeholder="请选择查看某个便签差异" clearable>-->
<!--          <el-option-->
<!--            v-for="dict in dict.type.if"-->
<!--            :key="dict.value"-->
<!--            :label="dict.label"-->
<!--            :value="dict.value"-->
<!--          />-->
<!--        </el-select>-->
<!--      </el-form-item>-->

      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
    </el-row>

    <el-table v-loading="loading" :data="hospitalList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column
        v-for="(item, index) in columnData"
        :key="index"
        :prop="item.name"
        :label="item.label"
        align="center"
      >
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

    <!-- 添加或修改医院对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="省份" prop="province">
          <el-input v-model="form.province" placeholder="请输入省份" />
        </el-form-item>
        <el-form-item label="医院名称" prop="hospitalName">
          <el-input v-model="form.hospitalName" placeholder="请输入医院名称" />
        </el-form-item>
        <el-form-item label="医院代号" prop="hospitalCode">
          <el-input v-model="form.hospitalCode" placeholder="请输入医院代号" />
        </el-form-item>
        <el-form-item label="医院账号" prop="hospitalAccount">
          <el-input v-model="form.hospitalAccount" placeholder="请输入医院账号" />
        </el-form-item>
        <el-form-item label="医院密码" prop="hospitalPassword">
          <el-input v-model="form.hospitalPassword" placeholder="请输入医院密码" />
        </el-form-item>
        <el-form-item label="设备数量" prop="equipmentNumber">
          <el-input v-model="form.equipmentNumber" placeholder="请输入设备数量" />
        </el-form-item>
        <el-form-item label="患者总数" prop="patientNumber">
          <el-input v-model="form.patientNumber" placeholder="请输入患者总数" />
        </el-form-item>
        <el-form-item label="正在监测患者数" prop="monitoringPatientNumber">
          <el-input v-model="form.monitoringPatientNumber" placeholder="请输入正在监测患者数" />
        </el-form-item>
        <el-form-item label="账号总数" prop="accountNumber">
          <el-input v-model="form.accountNumber" placeholder="请输入账号总数" />
        </el-form-item>
        <el-form-item label="首次收到心电数据时间" prop="firstEcgTime">
          <el-date-picker clearable
                          v-model="form.firstEcgTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择首次收到心电数据时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="是否开通数据统计">
          <el-radio-group v-model="form.isLabel">
            <el-radio
              v-for="dict in dict.type.if"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
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
import {listHospital, getHospital, delHospital, addHospital, updateHospital, addDict} from "@/api/hospital/hospital";
import {getLists, getUsers, getUserLists, getNotS} from "@/api/mark_info/mark_info";

export default {
  name: "mark_info",
  dicts: ['if'],
  data() {
    return {
      // 遮罩层
      columnData: [{name: "log_id", label: "日志id"}, {name: "user_id1", label: "标注用户1"},{name: "user_id2", label: "标注用户1"}],
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
      // 医院表格数据
      hospitalList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        province: null,
        logId:null,
        hospitalName: null,
        hospitalCode: null,
        hospitalAccount: null,
        hospitalPassword: null,
        equipmentNumber: null,
        patientNumber: null,
        monitoringPatientNumber: null,
        accountNumber: null,
        firstEcgTime: null,
        isLabel: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        hospitalName: [
          { required: true, message: "医院名称不能为空", trigger: "blur" }
        ],
        hospitalCode: [
          { required: true, message: "医院代号不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    // addDict().then(res =>{
    //   console.log(res)
    // })
  },
  methods: {
    /** 查询医院列表 */
    getList() {
          this.loading = true;
          var _this=this;
          getUsers().then(response => {
            _this.columnData=[]
            _this.columnData.push({name: "logId", label: "logId"})
            for(var i=0;i<response.length;i++){
              _this.columnData.push({name:response[i].userId,label:response[i].userId})
            }
          });
          getLists(this.queryParams).then(response => {
            console.log(response)
            this.hospitalList = response.rows;
            this.total = response.total;

            _this.hospitalList=[]
            // console.log(response)
            for(var k=0;k<response.rows.length;k++){
              var dir={}
              dir["logId"]=response.rows[k].logId
              for(var i=1;i<_this.columnData.length;i++){
                dir[_this.columnData[i].label]=""
              }
              // var left=1
              for(var j=0;j<response.rows[k].labelList.length;j++){

                for(var key in JSON.parse(JSON.stringify(response.rows[k].labelList[j]))){
                  // console.log("hell")
                  // if(JSON.parse(JSON.stringify(response.rows[k].labelList[j]))[key] !== ""){
                  //   left=0;
                  // }
                  dir[key]=JSON.parse(JSON.stringify(response.rows[k].labelList[j]))[key]
                }
                //
              }
              // if(left===0){
              //   _this.hospitalList.push(dir)
              // }
              _this.hospitalList.push(dir)
            }
          });
          this.loading = false;

    },
    /** 刷新 */
    refresh() {
      this.getList();
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
        logId:null,
        hospitalName: null,
        hospitalCode: null,
        hospitalAccount: null,
        hospitalPassword: null,
        equipmentNumber: null,
        patientNumber: null,
        monitoringPatientNumber: null,
        accountNumber: null,
        firstEcgTime: null,
        isLabel: "0"
      };
      this.resetForm("form");
    },
    getUserList(){
      this.loading = true;
      var _this=this;
      getUsers().then(response => {
        _this.columnData=[]
        _this.columnData.push({name: "logId", label: "logId"})
        for(var i=0;i<response.length;i++){
          _this.columnData.push({name:response[i].userId,label:response[i].userId})
        }
      });
      getUserLists(this.queryParams).then(response => {
        console.log(response)
        this.hospitalList = response.rows;
        this.total = response.total;
        _this.hospitalList=[]
        for(var k=0;k<response.rows.length;k++){
          var dir={}
          dir["logId"]=response.rows[k].logId
          for(var i=1;i<_this.columnData.length;i++){
            dir[_this.columnData[i].label]=""
          }
          for(var j=0;j<response.rows[k].labelList.length;j++){
            for(var key in JSON.parse(JSON.stringify(response.rows[k].labelList[j]))){
              dir[key]=JSON.parse(JSON.stringify(response.rows[k].labelList[j]))[key]
            }
          }
          _this.hospitalList.push(dir)
        }
      });
      this.loading = false;
    },
    //获取全部差异
    getNotSame(){
      this.loading = true;
      var _this=this;
      getUsers().then(response => {
        _this.columnData=[]
        _this.columnData.push({name: "logId", label: "logId"})
        for(var i=0;i<response.length;i++){
          _this.columnData.push({name:response[i].userId,label:response[i].userId})
        }
      });
      getNotS(this.queryParams).then(response => {
        this.hospitalList = response.rows;
        this.total = response.total;
        _this.hospitalList=[]
        for(var k=0;k<response.rows.length;k++){
          var dir={}
          dir["logId"]=response.rows[k].logId
          for(var i=1;i<_this.columnData.length;i++){
            dir[_this.columnData[i].label]=""
          }
          for(var j=0;j<response.rows[k].labelList.length;j++){
            for(var key in JSON.parse(JSON.stringify(response.rows[k].labelList[j]))){
              dir[key]=JSON.parse(JSON.stringify(response.rows[k].labelList[j]))[key]
            }
          }
          _this.hospitalList.push(dir)
        }
      });
      this.loading = false;
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      console.log(this.queryParams.province)
      console.log(this.queryParams.isLabel)
      if (this.queryParams.province === null || this.queryParams.province === ""){
        // if(this.queryParams.isLabel === "True"){
        //   this.getNotSame();
        // }
        // else{
          this.getList();
        // }
      }
      else{
        this.getUserList();
      }
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams= {
          province: null,
          logId:null,
          hospitalName: null,
          hospitalCode: null,
          hospitalAccount: null,
          hospitalPassword: null,
          equipmentNumber: null,
          patientNumber: null,
          monitoringPatientNumber: null,
          accountNumber: null,
          firstEcgTime: null,
          isLabel: null,
      }
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.hospitalId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加医院";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const hospitalId = row.hospitalId || this.ids
      getHospital(hospitalId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改医院";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.hospitalId != null) {
            updateHospital(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addHospital(this.form).then(response => {
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
    },

    handleLook(row) {
      console.log(row.logId);
      // this.$router.push({
      //   name: "lookLog",
      //   params: {logId: row.logId, logType: row.logType, userId:0}
      // });
      this.$router.push({
        path: "/lookLog1",// name: "lookLog"
        query: {logId: row.logId, logType: row.logType, userId: 0}
      });
    },
  }
};
</script>
