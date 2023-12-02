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
      <el-form-item label="设备类型" prop="ecgType">
        <el-select
          v-model="queryParams.ecgType"
          placeholder="设备类型"
          clearable
          style="width: 240px"
        >
          <el-option
            v-for="dict in dict.type.equipment_source"
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
      <el-col :span="12">
        <span class="table-title">设备总数量：{{total}}</span>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="shipAddressList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="设备号" align="center" prop="equipmentCode" />
      <el-table-column label="设备种类" align="center" prop="equipmentType">
        <template slot-scope="scope">
          <el-tag >
            {{scope.row.equipmentType}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="使用次数" align="center" prop="count" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-thumb"
            @click="fandPatient(scope.row.equipmentCode)"
          >查看使用用户</el-button>
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


    <!--查看查看设备使用人员情况 -->
    <el-dialog
      title="查看设备使用人员"
      :visible.sync="dialogVisiblePatientList"
      width="80%">
      <el-table v-loading="patientListLoading" :data="patientTeamLists" @selection-change="handleSelectionChange">
        <!--      <el-table-column label="患者id" align="center" prop="patientId"/>-->
        <el-table-column label="用户姓名" align="center" prop="patientName"/>
        <!--      <el-table-column label="患者身份证号" align="center" prop="patientCode" />-->
        <el-table-column label="用户年龄" align="center" prop="patientAge"/>
        <el-table-column label="用户性别" align="center" prop="patientSex">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.sex" :value="scope.row.patientSex"/>
          </template>
        </el-table-column>
        <el-table-column label="医院" align="center" prop="patientSource"/>
        <el-table-column label="用户电话" align="center" prop="patientPhone"/>
        <el-table-column label="使用次数" align="center" prop="count"/>
      </el-table>
      <pagination
        v-show="totalPatient>0"
        :total="totalPatient"
        :page.sync="queryParamsPatient.pageNum"
        :limit.sync="queryParamsPatient.pageSize"
        @pagination=" fandPatient(equipmentCode)"
      />
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisiblePatientList = false">取 消</el-button>
    <el-button type="primary" @click="dialogVisiblePatientList = false">确 定</el-button>
  </span>
    </el-dialog>
  </div>
</template>

<script>
import { listShipAddress, getShipAddress, delShipAddress, addShipAddress, updateShipAddress } from "@/api/shipAddress/shipAddress";
import {ecg_use} from "@/api/equipment/equipment";
import {getPatientByCode} from "@/api/patient/patient";

export default {
  name: "ShipAddress",
  dicts: ['sex', 'monitoring_status', 'binding_state', 'hospital_name_name_list','equipment_source'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      totalPatient: 0,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      dialogVisiblePatientList:false,
      patientListLoading:false,
      // 地址管理表格数据
      shipAddressList: [],
      patientTeamLists:[],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,

      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        patientPhone: null,
        patientName: null,
        ecgType: '',
        equipmentCode:null,
      },
      queryParamsPatient: {
        pageNum: 1,
        pageSize: 10,
        code:null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      equipmentCode:null,
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询地址管理列表 */
    getList() {
      this.loading = true;

      ecg_use(this.queryParams).then(response => {
        this.shipAddressList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    fandPatient(equipmentCode){
      this.dialogVisiblePatientList=true
      this.patientListLoading=true
      this.queryParamsPatient.code = equipmentCode
      this.equipmentCode = equipmentCode
      getPatientByCode(this.queryParamsPatient).then(r=>{
        console.log(r)
        this.patientTeamLists=r.rows
        this.totalPatient = r.total;
        this.patientListLoading=false
      })

    },

    // getPatientByCode(){
    //
    // },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        addressId: null,
        patientPhone: null,
        patientName: null,
        streetAddress: null,
        city: null,
        state: null,
        country: null,
        postalCode: null,
        defaultFlag: null
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
      this.ids = selection.map(item => item.addressId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加地址管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const addressId = row.addressId || this.ids
      getShipAddress(addressId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改地址管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.addressId != null) {
            updateShipAddress(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addShipAddress(this.form).then(response => {
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
      const addressIds = row.addressId || this.ids;
      this.$modal.confirm('是否确认删除地址管理编号为"' + addressIds + '"的数据项？').then(function() {
        return delShipAddress(addressIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/shipAddress/export', {
        ...this.queryParams
      }, `shipAddress_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
