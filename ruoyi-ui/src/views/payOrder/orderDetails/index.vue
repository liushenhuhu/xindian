<template>
 <div class="app-container">
   <el-divider content-position="left">订单详情</el-divider>
   <el-row>
     <el-col :span="12"><div class="grid-content bg-purple-dark">订单编号：{{form.id}}</div></el-col>
     <el-col :span="12"><div class="grid-content bg-purple-dark">订单描述：{{form.title}}</div></el-col>
   </el-row>

   <el-row>
     <el-col :span="8"><div class="grid-content bg-purple-dark">下单时间：{{form.createTime}}</div><span></span></el-col>
     <el-col :span="8"><div class="grid-content bg-purple-dark">订单状态：{{form.orderStatus}}</div><span></span></el-col>
     <el-col :span="8"><div class="grid-content bg-purple-dark">订单金额：{{form.totalFee}}</div><span></span></el-col>
   </el-row>
   <el-row>
     <el-col :span="12">
       <div class="grid-content bg-purple-dark">用户备注：{{form.remark||'无'}}</div>
     </el-col>
   </el-row>
   <el-divider content-position="left">订单地址</el-divider>
   <el-row>
     <el-col :span="12"><div class="grid-content bg-purple-dark">收货人姓名：{{form.patientName}}</div></el-col>
     <el-col :span="12"><div class="grid-content bg-purple-dark">联系电话：{{form.patientPhone}}</div></el-col>
   </el-row>
   <el-row>
     <el-col :span="8"><div class="grid-content bg-purple-dark">省市县街地址：{{form.state+' / '+form.city+' / '+form.country+' / '+form.street}}</div></el-col>
     <el-col :span="8"><div class="grid-content bg-purple-dark">详情地址：{{form.streetAddress}}</div></el-col>
     <el-col :span="8"><div class="grid-content bg-purple-dark">邮政编码：{{form.postalCode}}</div></el-col>
   </el-row>
   <el-divider content-position="left">订单商品详情</el-divider>
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
  </div>
</template>
<script>

import { listOrder} from "@/api/payOrder/orderDetails";

export default {
  name: "orderDetails",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 商品表表格数据
      infoList: [],
      // 表单参数
      form: {},


    };
  },
  created() {
    let id = this.$route.query.id
    listOrder(id).then(r=>{
      console.log(r)
      this.form = r.data
      console.log('数据')
      console.log(this.form)
      this.infoList = r.data.suborderOrderInfos
      this.loading=false
    })




  },
  methods: {
    /** 查询【请填写功能名称】列表 */




  }


}
</script>
<style>
.el-row{
  margin-top: 20px;
}
</style>
