<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="商品名称" prop="productName">
        <el-input
          v-model="queryParams.productName"
          placeholder="请输入商品名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="state">
        <el-select v-model="queryParams.state" placeholder="请选择状态">
          <el-option label="上架" value="1"></el-option>
          <el-option label="下架" value="2"></el-option>
          <el-option label="缺货" value="3"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="商品类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择商品类型">
          <el-option label="商品" value="商品"></el-option>
          <el-option label="服务" value="服务"></el-option>
          <el-option label="钱包" value="钱包"></el-option>
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
          v-hasPermi="['product:product:add']"
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
          v-hasPermi="['product:product:edit']"
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
          v-hasPermi="['product:product:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['product:product:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="productList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
<!--      <el-table-column label="商Bid" align="center" prop="productId" />-->
      <el-table-column label="商品名称" align="center" prop="productName" />
      <el-table-column label="价格(分)" align="center" prop="price" />
      <el-table-column label="商品描述" align="center" prop="productDescription" />
      <el-table-column label="商品类型" align="center" prop="type" />
      <el-table-column label="商品图片" align="center" prop="productUrl" >
        <template slot-scope="scope">
          <div class="demo-image__preview">
            <el-image  v-if="scope.row.productUrl" style="width: 100px; height: 100px; margin-left:10px;"
                       :src="scope.row.productUrl"
                       :preview-src-list="scope.row.productUrl"
            ></el-image>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="商品折扣" align="center" prop="discount" />
      <el-table-column label="商品折扣" align="center" prop="discount" />
      <el-table-column label="商品折扣" align="center" prop="discount" />
      <el-table-column label="状态" align="center" prop="state" >
        <template slot-scope="scope">
          <div v-if="scope.row.state==1">上架</div>
          <div v-if="scope.row.state==2">下架</div>
          <div v-if="scope.row.state==3">缺货</div>
        </template>
      </el-table-column>
      <el-table-column label="服务次数" align="center" prop="frequency" />
      <el-table-column label="是否会员" align="center" prop="isVip">
      <template slot-scope="scope">
        <div v-if="scope.row.isVip==1">否</div>
        <div v-if="scope.row.isVip==2">是</div>
      </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">

          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit-outline"
            @click="handleImgs(scope.row)"
            v-hasPermi="['product:product:add']"
          >添加图片</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['product:product:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['product:product:remove']"
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
    <!-- 添加多张介绍图 -->
    <el-dialog title="添加商品介绍图" :visible.sync="open2" width="700px" append-to-body @close="cancel2">
      <el-form label-width="120px">
        <el-form-item label="商品介绍图片"  prop="img">
          <el-upload
            action="#"
            list-type="picture-card"
            :file-list="fileList"
            :on-change="handleChange3"
            :on-remove="handleRemove"
            :auto-upload="false"
            multiple><!--不进行默认上传-->
            <i class="el-icon-plus"></i>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitImgs">确 定</el-button>
        <el-button @click="cancel2">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 添加或修改商品信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="650px" append-to-body @close="cancel">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px" >
        <el-form-item label="商品名称" prop="productName">
          <el-input v-model="form.productName" placeholder="请输入商品名称" />
        </el-form-item>
        <el-form-item label="价格(分)" prop="price">
          <el-input v-model="form.price" placeholder="请输入价格(分)" />
        </el-form-item>
        <el-form-item label="商品类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择商品类型">
            <el-option label="商品" value="商品"></el-option>
            <el-option label="服务" value="服务"></el-option>
            <el-option label="钱包" value="钱包"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="商品描述" prop="productDescription">
          <el-input v-model="form.productDescription" placeholder="请输入商品描述" />
        </el-form-item>
        <el-form-item label="商品图片" prop="productUrl">
          <el-input v-if="false" v-model="form.productUrl"/>
          <el-upload
            class="avatar-uploader"
            action="#"
            :on-change="handleChange1"
            :auto-upload="false"><!--不进行默认上传-->
            <img v-if="form.productUrl" :src="form.productUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="商品折扣(分)" prop="discount">
          <el-input v-model="form.discount" placeholder="请输入商品折扣" />
        </el-form-item>
        <el-form-item label="状态" prop="state">
          <el-select v-model="form.state" placeholder="请选择商品类型">
            <el-option label="上架" value="1"></el-option>
            <el-option label="下架" value="2"></el-option>
            <el-option label="缺货" value="3"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="数量" prop="productNum">
          <el-input v-model="form.productNum" placeholder="请输入数量" />
        </el-form-item>
        <el-form-item label="销量" prop="sales">
          <el-input v-model="form.sales" placeholder="请输入销量" />
        </el-form-item>
        <el-form-item label="商品详情图片" prop="urlOne">
          <el-input v-if="false" v-model="form.urlOne"/>
          <el-upload
            class="avatar-uploader"
            action="#"
            :on-change="handleChange2"
            :auto-upload="false"><!--不进行默认上传-->
            <img v-if="form.urlOne" :src="form.urlOne" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
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
import {
  listProduct,
  getProduct,
  delProduct,
  updateProduct,
  addProductAndUpload,
  batchUploadFile,
  getImgs
} from "@/api/product/product";
import {forEach} from "core-js/internals/array-iteration";
import {enableTopologicalTravel} from "echarts/lib/util/component";

export default {
  name: "Product",
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
      // 商品信息表格数据
      productList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      open2:false,
      //图片临时路径
      productUrl:null,
      urlOne:null,
      imgs:[],
      fileList:[],
      list:[],
      delImgs:[],
      //模态框获取当前商品id
      productId:null,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        productName: null,
        price: null,
        productDescription: null,
        productUrl: null,
        discount: null,
        state: null,
        productNum: null,
        sales: null,
        type:null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        productName: [
          { required: true, message: '请输入商品名称', trigger: 'blur' },
        ],
        price: [
          { required: true, message: '请输入价格', trigger: 'blur' }
        ],
        type: [
          { required: true, message: '请选择商品类型', trigger: 'change' }
        ],
        productUrl:[
          { required: true, message: '请上传图片', trigger: 'blur'}
        ],
        discount: [
          { required: true, message: '请输入商品折扣', trigger: 'blur' }
        ],
        urlOne:[
          { required: true, message: '请上传图片', trigger: 'blur'}
        ],
        sales:[
          {required: true, message: '请输入商品销量', trigger: 'blur'}
        ],
        state: [
          { required: true, message: '请选择商品状态', trigger: 'change' }
        ],
        productNum:[
          {required: true, message: '请输入商品数量', trigger: 'blur'}
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询商品信息列表 */
    getList() {
      this.loading = true;
      listProduct(this.queryParams).then(response => {
        this.productList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 取消按钮
    cancel2() {
      this.fileList=[]
      this.open2 = false;
      this.delImgs=[]
    },
    // 表单重置
    reset() {
      this.form = {
        productId: null,
        productName: null,
        price: null,
        productDescription: null,
        productUrl: null,
        discount: null,
        state: null,
        updateTime: null,
        createTime: null,
        productNum: null,
        sales: null,
        type:null,
        urlOne:null
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
    //选择图片
    handleChange1(file, fileList) {
      this.form.productUrl = URL.createObjectURL(file.raw);
      this.list[0]=file.raw;
      //console.log(this.list)
    },
    handleChange2(file, fileList) {
      this.form.urlOne = URL.createObjectURL(file.raw);
      this.list[1]=file.raw;
      //console.log(this.list)
    },
    handleRemove(file, fileList) {
      if(!file.raw){
        this.delImgs.push(file.name)
      }
    },
    //选择多张介绍图
    handleChange3(file, fileList) {
      //console.log(fileList)
      for (let i = 0; i <fileList.length; i++) {
        this.imgs[i]=fileList[i].raw
      }
      //console.log(this.imgs)
    },

    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.productId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加商品信息";
    },
    //添加商品介绍图弹框
    handleImgs(row){
      this.productId=row.productId
      this.delImgs=[]
      getImgs(row.productId).then(res=>{
        let data = res.data;
        for (let i = 0; i <data.length ; i++) {
          var img={}
          img.name=data[i].id
          img.url=data[i].img
          console.log(img)
          this.fileList.push(img)
        }
        console.log(this.fileList)
        this.open2=true;
      })
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const productId = row.productId || this.ids
      getProduct(productId).then(response => {
        this.form = response.data;
        this.form.discount = response.data.discount*100
        this.open = true;
        this.title = "修改商品信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          let data=new FormData();
          data.append("product",JSON.stringify(this.form))
          if (this.form.productId != null) {
            if(this.list[0]!=undefined){
              data.append('file1', this.list[0])
            }
            if(this.list[1]!=undefined){
              data.append('file2', this.list[1])
            }
            console.log(data.get("file1"))
            console.log(data.get("file2"))
            for (var [key, value] of data) {
              console.log(key, value);
            }
            updateProduct(data).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
              this.list=[]
            });
          } else {
            for (let i = 0; i < this.list.length; i++) {
              data.append('files', this.list[i])
            }
            addProductAndUpload(data).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
              this.list=[]
            });
          }
        }
      });
    },
    //上传多个商品介绍图片
    submitImgs(){
      //console.log(this.imgs)
      let data=new FormData();
      for (let i = 0; i < this.imgs.length; i++) {
        data.append('files', this.imgs[i])
      }
      for (let i = 0; i < this.delImgs.length; i++) {
        data.append("delImgs",this.delImgs[i])
      }
      console.log(this.imgs)
      batchUploadFile(data,this.productId).then(response=>{
        this.imgs=[];
        this.fileList=[]
        this.$modal.msgSuccess("添加商品介绍图成功");
        this.open2 = false;
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const productIds = row.productId || this.ids;
      this.$modal.confirm('是否确认删除商品信息编号为"' + productIds + '"的数据项？').then(function() {
        return delProduct(productIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('product/product/export', {
        ...this.queryParams
      }, `product_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

<style>
.avatar-uploader {
  margin-top: 20px;
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  width: 200px;
  height: 150px;
}

.avatar-uploader:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 200px;
  height: 150px;
  line-height: 150px;
  text-align: center;
}

.avatar {
  width: 200px;
  height: 150px;
  display: flex;
}
</style>
