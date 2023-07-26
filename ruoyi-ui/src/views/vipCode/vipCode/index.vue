<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true"  label-width="100px">
      <el-form-item label="激活码序列号" prop="code">
        <el-input
          v-model="queryParams.code"
          placeholder="请输入序列号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否为会员" prop="isVip">
        <el-select v-model="queryParams.isVip" placeholder="请选择是否为会员">
          <el-option label="否" value="0"></el-option>
          <el-option label="是" value="1"></el-option>
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
    </el-row>

    <el-table v-loading="loading" :data="codeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序列号" align="center" prop="code" />
      <el-table-column label="服务次数" align="center" prop="num" />
      <el-table-column label="是否为会员" align="center" prop="isVip" >
      <template slot-scope="scope">
        <div v-if="scope.row.state==0">否</div>
        <div v-if="scope.row.state==1">是</div>
      </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="state" >
        <template slot-scope="scope">
          <div v-if="scope.row.state==0">未使用</div>
          <div v-if="scope.row.state==1">已使用</div>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
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

    <!-- 添加或修改商品信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body @close="cancel">
      <el-form ref="form" :model="form" :rules="rules" label-width="100px" >
        <el-form-item label="服务次数" prop="num">
          <el-input v-model="form.num" placeholder="请输入服务次数" style="width: 180px"/>
        </el-form-item>
        <el-form-item label="是否为会员" prop="isVip">
          <el-select v-model="form.isVip" placeholder="请选择是否为会员">
            <el-option label="非会员" value="0"></el-option>
            <el-option label="是会员" value="1"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="新增个数" prop="n">
          <el-input v-model="n" placeholder="请输入新增序列号个数" style="width:180px"/>
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
  listTable,
  insertCode
} from "@/api/vipCode/vipCode";


export default {
  name: "VipCode",
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
      codeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,

      //模态框获取当前id
      id:null,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        code: null,
        num: null,
        isVip: null,
        state: null,
      },
      // 表单参数
      form: {
        id:null,
        code: null,
        num: null,
        isVip: null,
        state: null,
      },
      //新增激活码个数
      n:1,
      // 表单校验
      rules: {
        code: [
          { required: true, message: '请输入激活码序列号', trigger: 'blur' },
        ],
        num: [
          { required: true, message: '请输入服务次数', trigger: 'blur' }
        ],
        isVip: [
          { required: true, message: '请选择是否为会员', trigger: 'change' }
        ],
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
      listTable(this.queryParams).then(res => {
        this.codeList = res.rows;
        this.total = res.total;
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
        id:null,
        code: null,
        num: null,
        isVip: null,
        state: null,
      };
      this.n=1
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
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加商品信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const Id = row.Id || this.ids
      getProduct(productId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改商品信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {

          }else {
            insertCode(this.form,this.n).then(res=>{
              console.log(res)
              this.getList();
              this.open=false
              this.$modal.msgSuccess(res.msg);
            })
          }
        }
      });
    },

    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除商品信息编号为"' + ids + '"的数据项？').then(function() {
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

</style>
