<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="订单编号" prop="payOrderName">
        <el-input
          v-model="queryParams.payOrderId"
          placeholder="请输入订单编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户电话" prop="payOrderPhone">
        <el-input
          v-model="queryParams.userPhone"
          placeholder="请输入用户电话"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="订单状态" prop="orderState">
        <el-select v-model="queryParams.orderState" clearable placeholder="请选择订单状态">
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
          v-hasPermi="['payOrder:payOrder:edit']"
        >修改</el-button>
      </el-col>
      <!--      订单暂时不需要删除，后续有想法可以打开-->
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="danger"-->
      <!--          plain-->
      <!--          icon="el-icon-delete"-->
      <!--          size="mini"-->
      <!--          :disabled="multiple"-->
      <!--          @click="handleDelete"-->
      <!--          v-hasPermi="['payOrder:payOrder:remove']"-->
      <!--        >删除</el-button>-->
      <!--      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['payOrder:payOrder:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="infoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="订单编号" align="center" prop="id" />
      <el-table-column label="用户电话" align="center" prop="sysUser.phonenumber" />
      <el-table-column label="商户订单编号" align="center" prop="orderNo" />
      <el-table-column label="用户昵称" align="center" prop="sysUser.nickName" />
      <el-table-column label="订单标题" align="center" prop="title" />
      <el-table-column label="订单状态" align="center" prop="orderStatus" >
        <template slot-scope="scope">
          <el-tag

            :type="scope.row.orderStatus === '已支付' || scope.row.orderStatus === '已发货' ||scope.row.orderStatus ==='交易完成' ? 'primary' : 'danger'"
            disable-transitions>{{scope.row.orderStatus}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" />
      <!--      <el-table-column label="关联设备" align="center" prop="equipmentList" />-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['payOrder:payOrder:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-document"
            @click="handleDelete(scope.row)"
            v-hasPermi="['payOrder:payOrder:remove']"
          >查看订单详情</el-button>
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
    <el-dialog :title="title" :visible.sync="open" width="850px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px" label-position="right">
        <el-form-item label="订单编号" prop="id">
          <el-input v-model="form.id"   :disabled="true"/>
        </el-form-item>
        <el-form-item label="订单标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入订单标题"  :disabled="true"/>
        </el-form-item>
        <el-form-item label="订单状态" prop="orderStatus">
          <el-select v-model="form.orderStatus" clearable placeholder="请选择订单状态" @change="loadOrder(form.orderStatus,form.id)">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="收件人名称" prop="patientName1">
          <el-input v-model="form.patientName" placeholder="请输入收件人名称"  :disabled="true"/>
        </el-form-item>
        <el-form-item label="收件人手机号" prop="patientPhone1">
          <el-input v-model="form.patientPhone" placeholder="请输入收件人手机号"  :disabled="true"/>
        </el-form-item>
        <el-form-item label="收件人地址" prop="patientName1">
          <el-input style="width: 550px" v-model="form.state+'/'+form.city+'/'+form.country+'/'+form.street+'/'+form.streetAddress" placeholder="请输入收件人地址"  :disabled="true"/> <el-button type="success" @click="addressUpdate()">修改地址</el-button>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
<!--修改地址-->
    <el-dialog title="修改地址" :visible.sync="addressOpen" width="800px" append-to-body>
      <el-form ref="address" :model="shipaddress" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="收货人姓名" prop="patientName">
              <el-input v-model="shipaddress.patientName" placeholder="请输入收货人姓名" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="联系电话" prop="patientPhone">
              <el-input v-model="shipaddress.patientPhone" placeholder="请输入联系电话" />
            </el-form-item>
            </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item label="地址" prop="state_id">
              <el-select v-model="shipaddress.state_id" clearable placeholder="请选择省份"  @change="loadCities(shipaddress.state_id)">
                <el-option
                  v-for="item in states"
                  :label="item.name"
                  :key="item.id" :value="item.id"
                >
                </el-option>
              </el-select>
            </el-form-item>

          </el-col>
          <el-col :span="6">
            <el-form-item label="市" prop="city_id">
              <el-select v-model="shipaddress.city_id" clearable placeholder="请选择市" @change="loadcountry(shipaddress.city_id)">
                <el-option
                  v-for="item in cities"
                  :label="item.name"
                  :key="item.id" :value="item.id"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="县" prop="country_id">
              <el-select v-model="shipaddress.country_id" clearable placeholder="请选择县" @change="loadcountryId(shipaddress.country_id)">
                <el-option
                  v-for="item in counties"
                  :label="item.name"
                  :key="item.id" :value="item.id"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="街道" prop="street_id">
              <el-select v-model="shipaddress.street_id" clearable placeholder="请选择街道" @change="loadstreet(shipaddress.street_id)">
                <el-option
                  v-for="item in streets"
                  :label="item.name"
                  :key="item.id" :value="item.id"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="详细地址" prop="streetAddress">
              <el-input v-model="shipaddress.streetAddress" placeholder="请输入详细地址" />
            </el-form-item>

          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">

            <el-form-item label="邮政编码" prop="postalCode">
              <el-input v-model="shipaddress.postalCode" placeholder="请输入邮政编码" />
            </el-form-item>
          </el-col>
        </el-row>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="addressAdd">确 定</el-button>
        <el-button @click="addressCancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listInfo, getInfo, delInfo, addInfo, updateInfo ,address,updateAddressInfo} from "@/api/payOrder/payOrder";

export default {
  name: "Info",
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
      infoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      addressOpen:false,
      addressAndOrderId:null,
      states: [],
      cities: [],
      counties: [],
      streets: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        title: null,
        orderNo: null,
        userId: null,
        totalFee: null,
        codeUrl: null,
        addressId: null,
        orderStatus: null,
        openId: null,
      },
      options: [{
      value: '未支付',
      label: '未支付'
    }, {
      value: '已支付',
      label: '已支付'
    }, {
      value: '已退款',
      label: '已退款'
    }, {
      value: '已发货',
      label: '已发货'
    },{
        value: '交易完成',
        label: '交易完成'
      },],
      // 表单参数
      form: {},
        shipaddress:{
          addressId: null,
          country: null,
          country_id: null,
          city: null,
          city_id: null,
          street:null,
          street_id:null,
          patientPhone: null,
          createdTime: null,
          streetAddress:null,
          defaultFlag:null,
          postalCode:null,
          state:null,
          state_id:null,
          patientName:null,
          updatedTime:null,
      },
      shipaddress1:{
        addressId: null,
        country: null,
        country_id: null,
        city: null,
        city_id: null,
        street:null,
        street_id:null,
        patientPhone: null,
        createdTime: null,
        streetAddress:null,
        defaultFlag:null,
        postalCode:null,
        state:null,
        state_id:null,
        patientName:null,
        updatedTime:null,
        id: null,
        orderStatus: null,
        isUpdate:null,
      },
      // 表单校验
      rules: {
        patientName: [
          { required: true, message: '请输入收货人名称', trigger: 'blur' },
        ],
        patientPhone: [
          { required: true, message: '请输入手机号', trigger: 'blur' },
          { pattern: /^1[3|4|5|7|8][0-9]\d{8}$/, message: '手机号格式不正确', trigger: 'blur' }
        ],
        state_id: [
          { required: true, message: '请选择省', trigger: 'change' }
        ],
        city_id: [
          { required: true, message: '请选择市', trigger: 'change' }
        ],
        country_id: [
          { required: true, message: '请选择县', trigger: 'change' }
        ],
        streetAddress: [
          { required: true, message: '请输入详细地址', trigger: 'blur' },
        ],
        orderStatus: [
          { required: true, message: '请选择订单状态', trigger: 'blur' },
        ],
        street_id: [
          { required: true, message: '请选择街道', trigger: 'change' }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询【请填写功能名称】列表 */
    getList() {
      this.loading = true;
      listInfo(this.queryParams).then(response => {
        this.infoList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    addressAdd(){
      this.$refs["address"].validate(valid => {
        if (valid) {
          console.log(this.shipaddress)
            this.shipaddress1 = this.shipaddress
          this.form.country= this.shipaddress.country

            this.form.city= this.shipaddress.city

            this.form.street= this.shipaddress.street

            this.form.patientPhone= this.shipaddress.patientPhone

            this.form.streetAddress= this.shipaddress.streetAddress

            this.form.postalCode= this.shipaddress.postalCode
            this.form.state= this.shipaddress.state

            this.form.patientName= this.shipaddress.patientName
            this.shipaddress1.id = this.form.id
            this.addressOpen = false;
            this.shipaddress1.isUpdate="修改"
        }
      })
    },

    loadOrder(state,id){
      this.shipaddress1.orderStatus = state
      this.shipaddress1.id=id
    },
    addressUpdate(id){

      this.addressAndOrderId=id
      address(null,1).then(response => {
        this.states = response.data
      });
      this.addressOpen = true;
    },
    loadCities(name){
      this.shipaddress.city_id = null
      this.shipaddress.country_id = null
      this.shipaddress.street_id = null
      address(name,2).then(response => {
        this.cities = response.data
      });
      this.shipaddress.state = this.states.find(item => item.id === name).name
    },
    loadcountry(name){
      this.shipaddress.country_id = null
      this.shipaddress.street_id = null
      address(name,3).then(response => {
        this.counties = response.data
      });
      this.shipaddress.city  = this.cities.find(item => item.id === name).name
    },
    loadcountryId(name){
      this.shipaddress.street_id = null
      address(name,4).then(response => {
        this.streets = response.data
      });
      this.shipaddress.country =this.counties.find(item => item.id === name).name
    },
    loadstreet(id){
      this.shipaddress.street =this.streets.find(item => item.id === id).name
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    addressCancel() {
      this.addressOpen = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.shipaddress={
        addressId: null,
          country: null,
          country_id: null,
          city: null,
          city_id: null,
          patientPhone: null,
          createdTime: null,
          streetAddress:null,
          defaultFlag:null,
          postalCode:null,
          state:null,
          state_id:null,
          patientName:null,
          updatedTime:null,
          id: null,
        street:null,
        street_id:null,
      }
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
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getInfo(id).then(response => {
        console.log(response)
        this.form = response.data;
        this.open = true;
        this.title = "修改订单状态或地址";
        this.shipaddress1.id = id
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          updateAddressInfo(this.shipaddress1).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
        }
      });
    },
    /** 查看详情 */
    handleDelete(row) {
      this.$router.push({path: "/tool/orderDetails" , query: {id: row.id,}});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('webOrder/export', {
        ...this.queryParams
      }, `info_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
