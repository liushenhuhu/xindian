<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="订单id" prop="orderId">
        <el-input
          v-model="queryParams.orderId"
          placeholder="请输入订单id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="反馈状态" prop="feedbackCause">
        <el-select v-model="queryParams.status" clearable placeholder="请选择反馈状态">
          <el-option
            v-for="item in options"
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
          v-hasPermi="['payOrder:feedback:edit']"
        >处理反馈</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['payOrder:feedback:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="feedbackList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="订单id" align="center" prop="orderId" />
      <el-table-column label="反馈原因" align="center" prop="feedbackCause" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="反馈信息状态" align="center" prop="status" >
        <template slot-scope="scope">
          <el-tag
            :type="scope.row.status === '退款成功' || scope.row.status === '拒接退款' ? 'primary' : 'danger'"
            disable-transitions>{{scope.row.status}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="申请时间" align="center" prop="createTime" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">

          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            v-if="!(scope.row.status === '退款成功' || scope.row.status === '拒接退款')"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['payOrder:feedback:edit']"
          >处理反馈</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['payOrder:feedback:remove']"
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
    <el-dialog :title="title" :visible.sync="open" width="1200px" append-to-body>
      <el-row>
        <el-col :span="8"><div class="grid-content bg-purple-dark">订单详情</div></el-col>
      </el-row>
      <el-row>
        <el-col :span="8"><div class="grid-content bg-purple-dark"> 订单编号：{{from.id}}</div></el-col>
        <el-col :span="8"><div class="grid-content bg-purple-dark"> 订单描述：{{from.title}}</div></el-col>
        <el-col :span="8"><div class="grid-content bg-purple-dark"> 订单金额：{{from.totalFee}}</div><span></span></el-col>
      </el-row>


      <el-row>
        <el-col :span="8"><div class="grid-content bg-purple-dark"> 下单时间：{{from.createTime}}</div><span></span></el-col>
        <el-col :span="8"><div class="grid-content bg-purple-dark"> 订单状态：{{from.orderStatus}}</div><span></span></el-col>
        <el-col :span="8"><div class="grid-content bg-purple-dark"> 退款原因：<span style="color: darkred">{{feedbackId.feedbackCause}}</span> </div><span></span></el-col>
      </el-row>
      <el-row>
        <el-col :span="8"><div class="grid-content bg-purple-dark">订单地址</div></el-col>
      </el-row>
      <el-row>
        <el-col :span="12"><div class="grid-content bg-purple-dark"> 收货人姓名：{{from.shipAddress.patientName}}</div></el-col>
        <el-col :span="12"><div class="grid-content bg-purple-dark"> 联系电话：{{from.shipAddress.patientPhone}}</div></el-col>
      </el-row>
      <el-row>
        <el-col :span="8"><div class="grid-content bg-purple-dark"> 省市县街地址：{{from.shipAddress.state+' / '+from.shipAddress.city+' / '+from.shipAddress.country+' / '+from.shipAddress.street}}</div></el-col>
        <el-col :span="8"><div class="grid-content bg-purple-dark"> 详情地址：{{from.shipAddress.streetAddress}}</div></el-col>
        <el-col :span="8"><div class="grid-content bg-purple-dark"> 邮政编码：{{from.shipAddress.postalCode}}</div></el-col>
      </el-row>
      <el-row>
        <el-col :span="8"><div class="grid-content bg-purple-dark">订单商品详情</div></el-col>
      </el-row>
      <el-row>
        <el-table v-loading="loading" :data="infoList" >
          <el-table-column label="商品编号" align="center" prop="product.productId" />
          <el-table-column label="商品名称" align="center" prop="product.productName" />
          <el-table-column label="商品价格" align="center" prop="product.price" ><template slot-scope="item">
            {{item.row.product.discount}}￥/<span style="text-decoration: line-through;">{{item.row.product.price}}￥</span>
          </template></el-table-column>
          <el-table-column label="用户图片" align="center" prop="product.productUrl" >
            <template slot-scope="item">
              <img :src="item.row.product.productUrl" alt="" width="80">
            </template>
          </el-table-column>
          <el-table-column label="购买数量" align="center" prop="sum" />
        </el-table>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定 退 款</el-button>
        <el-button @click="cancelPay">取 消 退 款</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listFeedback, getFeedback, delFeedback, addFeedback, updateFeedback,Payback } from "@/api/payOrder/feedback";
import {listOrder} from "@/api/payOrder/orderDetails";

export default {
  name: "Feedback",
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
      feedbackList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      feedbackId:{
        feedbackCause:null,
        orderId:null,
        feedbackiId:null,
      },
      pay:{
        id:null,
        reason:null
      },
      stateUpdate:{
        feedbackiId:null,
        status:null,
        orderId:null
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderId: null,
        feedbackCause: null,
        status: null,
      },
      infoList:[],
      options: [{
        value: '退款成功',
        label: '退款成功'
      }, {
        value: '拒接退款',
        label: '拒接退款'
      }, {
        value: '未处理',
        label: '未处理'
      }, ],
      // 表单参数
      form: {
        orderId: null,
        feedbackCause: null,
        remark: null,
        status: "0",
        createTime: null,
        updateTime: null
      },
      from:{
        id:null,
        title:null,
        createTime:null,
        orderStatus:null,
        totalFee:null,
        shipAddress:{
          patientName:null,
          patientPhone:null,
          state:null,
          city:null,
          country:null,
          street:null,
          streetAddress:null,
          postalCode:null,
        }
      },
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    clfk(val){
      if (val.status=='拒绝退款'){
        return false
      }else if( val.status=='退款成功'){
        return false
      }else{
        return true
      }
    },
    /** 查询【请填写功能名称】列表 */
    getList() {
      this.loading = true;
      listFeedback(this.queryParams).then(response => {
        console.log(response)
        this.feedbackList = response.rows;
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
      this.form = {
        orderId: null,
        feedbackCause: null,
        remark: null,
        status: "0",
        createTime: null,
        updateTime: null
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
      this.ids = selection.map(item => item.feedbackiId)
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
      const orderId = row.orderId || this.orderId
      console.log(row)
      const feedbackiId = row.feedbackiId || this.feedbackiId
      getFeedback(feedbackiId).then(r=>{
        console.log(r)
        this.feedbackId = r.data
      })

      listOrder(orderId).then(r=>{
        console.log(r)
        this.from = r.data
        this.infoList = r.data.suborderOrderInfos
        this.open = true;
        this.title = "处理反馈信息";
        this.loading=false
      })

    },
    /** 提交按钮 */
    submitForm() {
      console.log(this.feedbackId)
      const id = this.feedbackId.orderId
      this.stateUpdate.orderId=id
      this.pay.id =id
      this.pay.reason = this.feedbackId.feedbackCause
      console.log(this.pay)
      this.$modal.confirm('是否确认为订单号"' + id + '"退款？').then(res => {
        return Payback(this.pay);
      }).then(() => {
        this.stateUpdate.feedbackiId =this.feedbackId.feedbackiId
        this.$modal.msgSuccess("退款成功");
        this.stateUpdate.status ='退款成功'
        updateFeedback(this.stateUpdate).then(response => {
          this.open = false;
          this.getList();
        });
      }).catch(() => {});

    },
    cancelPay(){
      this.stateUpdate.feedbackiId =this.feedbackId.feedbackiId
      this.stateUpdate.status ='拒接退款'
      updateFeedback(this.stateUpdate).then(response => {
        this.$modal.msgSuccess("修改成功");
        this.open = false;
        this.getList();
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const feedbackiIds = row.feedbackiId || this.feedbackiId;
      this.$modal.confirm('是否确认删除【请填写功能名称】编号为"' + feedbackiIds + '"的数据项？').then(function() {
        return delFeedback(feedbackiIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('/feedback/export', {
        ...this.queryParams
      }, `feedback_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
