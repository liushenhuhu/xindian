<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="110px">
      <el-form-item label="租赁用户名称" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入租赁用户名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="手机号" prop="phone">
        <el-input
          v-model="queryParams.phone"
          placeholder="请输入手机号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="身份证号" prop="identityCard">
        <el-input
          v-model="queryParams.identityCard"
          placeholder="请输入身份证号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="单位" prop="unitLeader">
        <el-input
          v-model="queryParams.unitLeader"
          placeholder="请输入单位"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="位置" prop="place">
        <el-input
          v-model="queryParams.place"
          placeholder="请输入位置"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否归还" prop="giveBack">
        <el-select v-model="queryParams.giveBack" placeholder="请选择是否归还">
          <el-option label="已归还" value="1"></el-option>
          <el-option label="未归还" value="0"></el-option>
        </el-select>
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
          v-hasPermi="['lease:lease:add']"
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
          v-hasPermi="['lease:lease:edit']"
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
          v-hasPermi="['lease:lease:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['lease:lease:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="leaseList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="租赁编号" align="center" prop="leaseId" />
      <el-table-column label="租赁用户名称" align="center" prop="userName" />
      <el-table-column label="手机号" align="center" prop="phone" />
      <el-table-column label="身份证号" align="center" prop="identityCard" />
      <el-table-column label="单位" align="center" prop="unitLeader" />
      <el-table-column label="位置" align="center" prop="place" />
      <el-table-column label="是否归还" align="center" prop="giveBack" >
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.giveBack"
            active-value="1"
            inactive-value="0"
            @change="handleStatusChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['lease:lease:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-share"
            @click="handleUpdateByAll(scope.row)"
            v-hasPermi="['lease:lease:edit']"
          >查看租赁设备情况</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['lease:lease:remove']"
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

    <!-- 添加或修改租赁对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1100px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-form-item label="租赁用户名称" prop="userName">
          <el-input v-model="form.userName" placeholder="请输入租赁用户名称" />
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入手机号" />
        </el-form-item>
        <el-form-item label="身份证号" prop="identityCard">
          <el-input v-model="form.identityCard" placeholder="请输入身份证号" />
        </el-form-item>
        <el-form-item label="单位" prop="unitLeader">
          <el-input v-model="form.unitLeader" placeholder="请输入单位" />
        </el-form-item>
        <el-form-item label="位置" prop="place">
          <el-input v-model="form.place" placeholder="请输入位置" />
        </el-form-item>
        <div v-for="(item, index) in form.details" :key="index">
          <el-row :gutter="24">
            <el-col :span="8">
              <el-form-item label="设备号" >
                <el-select v-model="item.equipmentCode" filterable placeholder="请选择设备号" @change="equipmentCodeChange(item.equipmentCode)">
                  <el-option
                    v-for="item1 in equipmentList"
                    :key="item1.equipmentCode"
                    :label="item1.equipmentCode"
                    :value="item1.equipmentCode">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="设备类型" >
                <el-select v-model="item.equipmentType" placeholder="请选择设备类型">
                  <el-option label="心电卡" value="心电卡"></el-option>
                  <el-option label="心电衣" value="心电衣"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item>
                <el-button v-if="index+1 == form.details.length" type="primary" size="mini" @click="addItem(form.details.length)">+</el-button>
                <el-button v-if="index !== 0" type="danger" size="mini" @click="deleteItem(item, index)">-</el-button>
              </el-form-item>
            </el-col>
          </el-row>
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 添加或修改租赁对话框 -->
    <el-dialog :title="titleByDetails" :visible.sync="openByDetails" width="1100px" append-to-body>
      <el-table  :data="detailsList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="设备号" align="center" prop="equipmentCode" />
        <el-table-column label="设备类型" align="center" prop="equipmentType" />
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="openByDetails=false">确 定</el-button>
        <el-button @click="openByDetails=false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listLease, getLease, delLease, addLease, updateLease } from "@/api/lease/lease";
import {getEquipmentList} from "@/api/equipment/equipment";

export default {
  name: "Lease",
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
      // 租赁表格数据
      leaseList: [],
      equipmentList: [],
      // 弹出层标题
      title: "",
      titleByDetails: "",
      // 是否显示弹出层
      open: false,
      detailsList:[],
      openByDetails: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userName: null,
        phone: null,
        identityCard: null,
        unitLeader: null,
        place: null,
        giveBack: null,
      },
      // 表单参数
      form: {
        leaseId: null,
        userName: null,
        phone: null,
        identityCard: null,
        unitLeader: null,
        place: null,
        giveBack: null,
        details:[
          {
            equipmentCode:"",
            equipmentType:""
          },
        ]
      },
      formByDetails: {
        leaseId: null,
        userName: null,
        phone: null,
        identityCard: null,
        unitLeader: null,
        place: null,
        giveBack: null,
        details:[
          {
            equipmentCode:"",
            equipmentType:""
          },
        ]
      },
      // 表单校验
      rules: {
        userName: [
          { required: true, message: "租赁用户名称不能为空", trigger: "blur" }
        ],
        phone: [
          { required: true, message: "手机号不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询租赁列表 */
    getList() {
      this.loading = true;
      listLease(this.queryParams).then(response => {
        this.leaseList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    equipmentCodeChange(code){
      if (code){
        this.equipmentList =  this.equipmentList.filter(item => item.equipmentCode!=code)
      }
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    handleUpdateByAll(row){
      const leaseId = row.leaseId
      // this.openByDetails = true
      // getLease(leaseId).then(response => {
      //   this.detailsList = response.data.details;
      // });
      this.$router.push({
        path: '/tool/details',
        query: {
          leaseId: leaseId
        }
      })
    },
    // 表单重置
    reset() {
      this.form = {
        leaseId: null,
        userName: null,
        phone: null,
        identityCard: null,
        unitLeader: null,
        place: null,
        giveBack: null,
        createTime: null,
        updateTime: null,
        details:[
          {
            equipmentCode:"",
            equipmentType:""
          },
        ]
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
      this.ids = selection.map(item => item.leaseId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    //新增方法
    addItem() {
        this.form.details.push({
          equipmentCode:"",
          equipmentType:""
        })
    },
    //删除方法
    deleteItem(item, index) {
      this.form.details.splice(index, 1)
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加租赁";
      getEquipmentList().then(r=>{
        this.equipmentList = r.data
      })
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const leaseId = row.leaseId || this.ids
      getLease(leaseId).then(response => {
        getEquipmentList().then(r=>{
          this.equipmentList = r.data
        })
        this.form = response.data;
        this.open = true;
        this.title = "修改租赁";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.leaseId != null) {
            updateLease(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addLease(this.form).then(response => {
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
      const leaseIds = row.leaseId || this.ids;
      this.$modal.confirm('是否确认删除租赁编号为"' + leaseIds + '"的数据项？').then(function() {
        return delLease(leaseIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('lease/lease/export', {
        ...this.queryParams
      }, `lease_${new Date().getTime()}.xlsx`)
    },
    // 用户状态修改
    handleStatusChange(row) {
      let text = row.giveBack === "0" ? "未归还" : "已归还";
      this.$modal.confirm('确认设备"' + text + '""' + row.userName + '"用户吗？').then(function () {
        return updateLease({leaseId: row.leaseId, giveBack:row.giveBack});
      }).then(() => {
        this.$modal.msgSuccess(text + "成功");
      }).catch(function () {
        row.giveBack = row.giveBack === "0" ? "1" : "0";
      });
    },
  }
};
</script>
