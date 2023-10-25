<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="门诊名称" prop="name">
        <el-input
          v-model="queryParams.outpatientName"
          placeholder="请输入门诊名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
<!--      <el-form-item label="所属专科" prop="specialId">-->
<!--        <el-select v-model="queryParams.specialId" placeholder="请输入专科名称" >-->
<!--          <el-option-->
<!--            v-for="item in specialList"-->
<!--            :key="item.hospitalId"-->
<!--            :label="item.specialName"-->
<!--            :value="item.specialId">-->
<!--          </el-option>-->
<!--        </el-select>-->
<!--      </el-form-item>-->
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
          v-hasPermi="['hospital:hospitalOutpatient:add']"
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
          v-hasPermi="['hospital:hospitalOutpatient:edit']"
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
          v-hasPermi="['hospital:hospitalOutpatient:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['hospital:hospitalOutpatient:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="outpatientList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="门诊编号" align="center" prop="outpatientId" />
      <el-table-column label="门诊名称" align="center" prop="outpatientName" />
      <el-table-column label="所属专科" align="center" prop="hospitalSpecial.specialName" />
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
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="findClinic(scope.row)"
            v-hasPermi="['hospital:hospitalOutpatient:edit']"
          >查看诊室</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['hospital:hospitalOutpatient:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['hospital:hospitalOutpatient:remove']"
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

    <!-- 添加或修改医院门诊 对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="门诊名称" prop="outpatientName">
          <el-input v-model="form.outpatientName" placeholder="请输入门诊名称" />
        </el-form-item>
<!--        <el-form-item label="所属专科" prop="specialId">-->
<!--          <el-select v-model="form.specialId" placeholder="请输入专科名称" clearable  >-->
<!--            <el-option-->
<!--              v-for="item in specialList"-->
<!--              :key="item.hospitalId"-->
<!--              :label="item.specialName"-->
<!--              :value="item.specialId">-->
<!--            </el-option>-->
<!--          </el-select>-->
<!--        </el-form-item>-->
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
import { listOutpatient, getOutpatient, delOutpatient, addOutpatient, updateOutpatient } from "@/api/visit/outpatient";
import {getSpecialList} from "@/api/visit/special";

export default {
  name: "Outpatient",
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
      // 医院门诊 表格数据
      outpatientList: [],
      specialList:[],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        outpatientName: null,
        specialId: null,
        gmtCreate: null,
        gmtModified: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        outpatientName: [
          { required: true, message: "门诊名称不能为空", trigger: "blur" }
        ],
        specialId: [
          { required: true, message: "所属专科不能为空", trigger: "blur" }
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

    getSpecialList().then(r=>{
      this.specialList = r.data
    })
    this.getList();
  },
  methods: {
    /** 查询医院门诊 列表 */
    getList() {
      this.loading = true;
      if (this.$route.query.specialId){
        this.queryParams.specialId=this.$route.query.specialId
      }
      listOutpatient(this.queryParams).then(response => {
        this.outpatientList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    findClinic(row){
      this.$router.push({path: "/visit/clinic" , query: {outpatientId: row.outpatientId,}});
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        outpatientId: null,
        outpatientName: null,
        specialId: null,
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
      this.ids = selection.map(item => item.outpatientId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加医院门诊 ";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.outpatientId || this.ids
      getOutpatient(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改医院门诊 ";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.$route.query.specialId){
            this.form.specialId=this.$route.query.specialId
          }
          if (this.form.outpatientId != null) {
            updateOutpatient(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOutpatient(this.form).then(response => {
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
      const ids = row.outpatientId || this.ids;
      this.$modal.confirm('是否确认删除医院门诊 编号为"' + ids + '"的数据项？').then(function() {
        return delOutpatient(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/outpatient/export', {
        ...this.queryParams
      }, `outpatient_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
