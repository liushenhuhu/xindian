<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="门诊编号" prop="outpatientId">
        <el-select v-model="queryParams.outpatientId" placeholder="请输入门诊名称" clearable  >
          <el-option
            v-for="item in outpatientList"
            :key="item.outpatientId"
            :label="item.outpatientName"
            :value="item.outpatientId">
          </el-option>
        </el-select>
      </el-form-item>
<!--      <el-form-item label="创建时间" prop="gmtCreate">-->
<!--        <el-date-picker clearable-->
<!--                        v-model="queryParams.gmtCreate"-->
<!--                        type="date"-->
<!--                        value-format="yyyy-MM-dd"-->
<!--                        placeholder="请选择创建时间">-->
<!--        </el-date-picker>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="更新时间" prop="gmtModified">-->
<!--        <el-date-picker clearable-->
<!--                        v-model="queryParams.gmtModified"-->
<!--                        type="date"-->
<!--                        value-format="yyyy-MM-dd"-->
<!--                        placeholder="请选择更新时间">-->
<!--        </el-date-picker>-->
<!--      </el-form-item>-->
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
          v-hasPermi="['system:relation:add']"
        >新增</el-button>
      </el-col>
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="success"-->
<!--          plain-->
<!--          icon="el-icon-edit"-->
<!--          size="mini"-->
<!--          :disabled="single"-->
<!--          @click="handleUpdate"-->
<!--          v-hasPermi="['system:relation:edit']"-->
<!--        >修改</el-button>-->
<!--      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:relation:remove']"
        >删除</el-button>
      </el-col>
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="warning"-->
<!--          plain-->
<!--          icon="el-icon-download"-->
<!--          size="mini"-->
<!--          @click="handleExport"-->
<!--          v-hasPermi="['system:relation:export']"-->
<!--        >导出</el-button>-->
<!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="relationList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="关系编号" align="center" prop="id" />
      <el-table-column label="医院名称" align="center" prop="hospital.hospitalName" />
      <el-table-column label="专科名称" align="center" prop="hospitalOutpatient.hospitalSpecial.specialName" />
      <el-table-column label="门诊名称" align="center" prop="hospitalOutpatient.outpatientName" />
      <el-table-column label="创建时间" align="center" prop="gmtCreate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.gmtCreate, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
<!--      <el-table-column label="更新时间" align="center" prop="gmtModified" width="180">-->
<!--        <template slot-scope="scope">-->
<!--          <span>{{ parseTime(scope.row.gmtModified, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>-->
<!--        </template>-->
<!--      </el-table-column>-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-edit"-->
<!--            @click="handleUpdate(scope.row)"-->
<!--            v-hasPermi="['system:relation:edit']"-->
<!--          >修改</el-button>-->
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:relation:remove']"
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

    <!-- 添加或修改医院门诊关系 对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
<!--        <el-form-item label="医院编号" prop="hospitalId">-->
<!--          <el-input v-model="form.hospitalId" placeholder="请输入医院编号" />-->
<!--        </el-form-item>-->
        <el-form-item label="门诊名称" prop="outpatientId">
          <el-select v-model="form.outpatientId" placeholder="请输入门诊名称" clearable  >
            <el-option
              v-for="item in outpatientList"
              :key="item.outpatientId"
              :label="item.outpatientName"
              :value="item.outpatientId"
              :disabled="item.disabled">
            </el-option>
          </el-select>
        </el-form-item>
<!--        <el-form-item label="创建时间" prop="gmtCreate">-->
<!--          <el-date-picker clearable-->
<!--                          v-model="form.gmtCreate"-->
<!--                          type="date"-->
<!--                          value-format="yyyy-MM-dd"-->
<!--                          placeholder="请选择创建时间">-->
<!--          </el-date-picker>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="更新时间" prop="gmtModified">-->
<!--          <el-date-picker clearable-->
<!--                          v-model="form.gmtModified"-->
<!--                          type="date"-->
<!--                          value-format="yyyy-MM-dd"-->
<!--                          placeholder="请选择更新时间">-->
<!--          </el-date-picker>-->
<!--        </el-form-item>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listRelation, getRelation, delRelation, addRelation, updateRelation } from "@/api/visit/hospitalOutpatient";
import {getOutpatientList} from "@/api/visit/outpatient";

export default {
  name: "Relation",
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
      // 医院门诊关系 表格数据
      relationList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      outpatientList:[],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        hospitalId: null,
        outpatientId: null,
        gmtCreate: null,
        gmtModified: null,
        specialId:null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        hospitalId: [
          { required: true, message: "医院编号不能为空", trigger: "blur" }
        ],
        outpatientId: [
          { required: true, message: "门诊编号不能为空", trigger: "blur" }
        ],
        gmtCreate: [
          { required: true, message: "创建时间不能为空", trigger: "blur" }
        ],
        gmtModified: [
          { required: true, message: "更新时间不能为空", trigger: "blur" }
        ]
      }
    };
  },
  activated() {
    this.getList();
  },
  created() {
    this.getList();
    let obj = {
      specialId:this.$route.query.specialId
    }
    getOutpatientList(obj).then(r=>{
      this.outpatientList = r.data
    })
  },
  methods: {
    /** 查询医院门诊关系 列表 */
    getList() {
      this.loading = true;
      if (this.$route.query.specialId){
        this.queryParams.specialId=this.$route.query.specialId
      }
      if (this.$route.query.hospitalId){
        this.queryParams.hospitalId=this.$route.query.hospitalId
      }
      listRelation(this.queryParams).then(response => {
        console.log(response)
        this.relationList = response.rows;
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
        id: null,
        hospitalId: null,
        outpatientId: null,
        gmtCreate: null,
        gmtModified: null
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
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      let _th = this
      _th.outpatientList.forEach(function(value) {
        value.disabled=false
        _th.relationList.forEach(function(a) {
          if (value.outpatientId===a.outpatientId){
            value.disabled=true
          }
        });
      });
      this.open = true;
      this.title = "添加医院门诊关系 ";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getRelation(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改医院门诊关系 ";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.$route.query.specialId){
            this.form.specialId=this.$route.query.specialId
          }
          if (this.$route.query.hospitalId){
            this.form.hospitalId=this.$route.query.hospitalId
          }
          if (this.form.id != null) {
            updateRelation(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addRelation(this.form).then(response => {
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
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除医院门诊关系 编号为"' + ids + '"的数据项？').then(function() {
        return delRelation(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/relation/export', {
        ...this.queryParams
      }, `relation_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
