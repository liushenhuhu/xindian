<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="订单价格" prop="price">
        <el-input
          v-model="queryParams.orderId"
          placeholder="请输入订单编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="抬头类型" prop="userAuthInfo">
        <el-select v-model="queryParams.userAuthInfo" clearable placeholder="请选择抬头类型">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>

      </el-form-item>
      <el-form-item label="名称" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="税号" prop="taxNo">
        <el-input
          v-model="queryParams.taxNo"
          placeholder="请输入税号"
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
      <el-form-item label="发票状态" prop="state">
        <el-select v-model="queryParams.state" clearable placeholder="请选择发票状态">
          <el-option
            v-for="item in options1"
            :key="item.value"
            :label="item.label"
            :value="item.value">
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
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['invoice:invoice:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['invoice:invoice:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="invoiceList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="订单编号" align="center" prop="orderId" />
      <el-table-column label="订单价格" align="center" prop="price" />
      <el-table-column label="抬头类型" align="center" prop="userAuthInfo" />
      <el-table-column label="名称" align="center" prop="title" />
      <el-table-column label="税号" align="center" prop="taxNo" />
      <el-table-column label="openid" align="center" prop="openId" />
      <el-table-column label="发票状态" align="center" prop="state" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.state==='待处理'"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="dialogForm(scope.row)"
            v-hasPermi="['system:invoice:edit']"
          >上传pdf格式发票</el-button>
          <el-button
            v-if="scope.row.state==='已处理'"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['invoice:invoice:edit']"
          >查看pdf格式发票</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['invoice:invoice:edit']"
          >查看抬头详情</el-button>
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
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="订单编号" prop="orderId">
          <el-input v-model="form.orderId" placeholder="请输入订单编号"  :disabled="true"/>
        </el-form-item>
        <el-form-item label="订单价格" prop="price">
          <el-input v-model="form.price" placeholder="请输入订单价格"  :disabled="true"/>
        </el-form-item>
        <el-form-item label="抬头类型" prop="userAuthInfo">
          <el-input v-model="form.userAuthInfo" placeholder="请输入抬头类型"  />
        </el-form-item>
        <el-form-item label="名称" prop="title">
          <el-input v-model="form.title" placeholder="请输入名称"  />
        </el-form-item>
        <el-form-item label="税号" prop="taxNo">
          <el-input v-model="form.taxNo" placeholder="请输入税号"  />
        </el-form-item>
        <el-form-item label="地址" prop="addr">
          <el-input v-model="form.addr" placeholder="请输入地址"  />
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入手机号"  />
        </el-form-item>
        <el-form-item label="银行卡类型" prop="bankType">
          <el-input v-model="form.bankType" placeholder="请输入银行卡类型" />
        </el-form-item>
        <el-form-item label="银行卡号" prop="bankNo">
          <el-input v-model="form.bankNo" placeholder="请输入银行卡号" />
        </el-form-item>
        <el-form-item label="openid" prop="openId">
          <el-input v-model="form.openId" placeholder="请输入openid"  :disabled="true"/>
        </el-form-item>
        <el-form-item label="发票状态" prop="state">
          <el-input v-model="form.state" placeholder="请输入发票状态"  :disabled="true"/>
        </el-form-item>
        <el-form-item label="创建时间" prop="createTime">
          <el-input v-model="form.createTime" placeholder="请输入创建时间"  :disabled="true"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">关 闭</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog title="上传发票pdf" :visible.sync="dialogFormVisible" width="60%">
      <el-form ref="invoicePdf" :model="invoicePdf" :rules="rules" label-width="170px">
      <el-form-item label="发票的号码" prop="billingNo">
        <el-input v-model="invoicePdf.billingNo" placeholder="请输入发票的号码" />
      </el-form-item>
      <el-form-item label="发票的代码" prop="billingCode">
        <el-input v-model="invoicePdf.billingCode" placeholder="请输入发票的代码" />
      </el-form-item>
      <el-form-item label="税额" prop="tax">
        <el-input-number v-model="invoicePdf.tax" :min="1"  label="描述文字"></el-input-number>
      </el-form-item>
      <el-form-item label="校验码，发票pdf右上角" prop="checkCode">
        <el-input v-model="invoicePdf.checkCode" placeholder="请输入校验码，发票pdf右上角" />
      </el-form-item>
      <el-form-item label="发票的开票时间" prop="bilTime">
        <el-date-picker clearable
                        v-model="invoicePdf.bilTime"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择发票的开票时间">
        </el-date-picker>
      </el-form-item>
      </el-form>
      <el-upload
        class="upload-demo"
        ref="upload"
        action="#"
        :limit="1"
        :on-preview="handlePreview"
        :on-remove="handleRemove"
        :on-change="addHandle"
        :file-list="fileList"
        :auto-upload="false">
        <el-button slot="trigger" v-if="this.isWHow==='1'" size="small" type="primary">选取文件</el-button>
        <el-button  v-if="this.isWHow==='2'" size="small" type="primary" disabled >请先删除选取文件</el-button>
        <div slot="tip" class="el-upload__tip">只能上传pdf文件</div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitUpload">上 传</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listInvoice, getInvoice, delInvoice, addInvoice, updateInvoice,uploadInvoice } from "@/api/invoice/invoice";
import da from "element-ui/src/locale/lang/da";
import log from "@/views/monitor/job/log.vue";

export default {
  name: "Invoice",
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
      invoiceList: [],
      // 弹出层标题
      title: "",
      isWHow:'1',
      // 是否显示弹出层
      open: false,
      dialogFormVisible:false,
      orderId:null,
      fileList:[],
      file:null,
      invoicePdf:{
        billingNo: null,
        billingCode: null,
        tax: null,
        checkCode: null,
        status: null,
        bilTime: null
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderId:null,
        price: null,
        userAuthInfo: null,
        title: null,
        taxNo: null,
        addr: null,
        phone: null,
        bankType: null,
        bankNo: null,
        customFieldKey: null,
        customFieldValue: null,
        openId: null,
        state: null
      },
      options: [{
        value: 'user_field',
        label: '个人'
      }, {
        value: 'biz_field',
        label: '企业'
      }],
      options1: [{
        value: '待处理',
        label: '待处理'
      }, {
        value: '已处理',
        label: '已处理'
      },],
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        billingNo: [
          { required: true, message: '请输入发票的号码', trigger: 'blur' },
        ],
        billingCode: [
          { required: true, message: '请输入发票的代码', trigger: 'blur' }
        ],
        tax: [
          {  required: true, message: '请输入税额', trigger: 'change' }
        ],
        checkCode: [
          {  required: true, message: '请选择发票的开票时间', trigger: 'blur' }
        ],
        bilTime: [
          {  required: true, message: '请选择发票的开票时间', trigger: 'change' }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    submitUpload() {
      if (this.file != null && this.file.type === "application/pdf"){
        this.$refs["invoicePdf"].validate((valid) => {
          if (valid) {
            let data=new FormData();
            console.log(this.invoicePdf)
            data.append("invoice",JSON.stringify(this.invoicePdf))
            data.append("file",this.file)
            uploadInvoice(data,this.orderId).then(r =>{
              this.dialogFormVisible = false
              this.isWHow='1'
              this.file=null
            })
          }
        });

      }else {
        this.$modal.alertError("请选择pdf格式文件")
      }
    },
    handleRemove(file, fileList) {
      this.isWHow='1'
      this.file=null
    },
    handlePreview(file) {
    },
    addHandle(file, fileList){
      this.file=file.raw;
      this.isWHow='2'
    },
    /** 查询【请填写功能名称】列表 */
    getList() {
      this.loading = true;
      listInvoice(this.queryParams).then(response => {
        this.invoiceList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    dialogForm(row){
     this.orderId = row.orderId || this.ids
      this.dialogFormVisible = true
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        orderId: null,
        price: null,
        userAuthInfo: null,
        title: null,
        taxNo: null,
        addr: null,
        phone: null,
        bankType: null,
        bankNo: null,
        customFieldKey: null,
        customFieldValue: null,
        openId: null,
        createTime: null,
        updateTime: null,
        state: null
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
      this.ids = selection.map(item => item.orderId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加【请填写功能名称】";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const orderId = row.orderId || this.ids
      getInvoice(orderId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改【请填写功能名称】";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.orderId != null) {
            updateInvoice(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");

              this.getList();
            });
          } else {
            addInvoice(this.form).then(response => {
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
      const orderIds = row.orderId || this.ids;
      this.$modal.confirm('是否确认删除【请填写功能名称】编号为"' + orderIds + '"的数据项？').then(function() {
        return delInvoice(orderIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('invoice/invoice/export', {
        ...this.queryParams
      }, `invoice_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
