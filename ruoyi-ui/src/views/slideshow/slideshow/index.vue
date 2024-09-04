<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="是否跳转" prop="isSkip">
        <el-select v-model="queryParams.isSkip" placeholder="请选择是否跳转" clearable>
          <el-option
            v-for="dict in dict.type.confirm_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="跳转路径" prop="path">
        <el-input
          v-model="queryParams.path"
          placeholder="请输入跳转路径"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="轮播图状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择轮播图状态" clearable>
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择类型" clearable>
          <el-option
            v-for="dict in dict.type.slideshow_type"
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
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['slideshow:slideshow:add']"
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
          v-hasPermi="['slideshow:slideshow:edit']"
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
          v-hasPermi="['slideshow:slideshow:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['slideshow:slideshow:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="slideshowList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="轮播图id" align="center" prop="slideshowId" />
      <el-table-column label="图片路径" align="center" prop="imagePath" width="100">
        <template slot-scope="scope" >
          <image-preview v-if="scope.row.type === '0'" :src="scope.row.imagePath" :width="50" :height="50"/>
          <span v-else>{{scope.row.imagePath}}</span>
        </template>
      </el-table-column>
      <el-table-column label="封面" align="center" prop="cover" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.cover" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="是否跳转" align="center" prop="isSkip">
        <template slot-scope="scope">
          <dict-tag  :options="dict.type.confirm_status" :value="scope.row.isSkip"/>
        </template>
      </el-table-column>
      <el-table-column label="跳转路径" align="center" prop="path" />
      <el-table-column label="轮播图状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="类型" align="center" prop="type">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.slideshow_type" :value="scope.row.type"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['slideshow:slideshow:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['slideshow:slideshow:remove']"
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

    <!-- 添加或修改首页轮播图对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-form-item label="类型">
          <el-radio-group v-model="form.type">
            <el-radio
              v-for="dict in dict.type.slideshow_type"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <div v-if="form.type == 0">
          <el-form-item label="图片路径">
            <image-upload v-model="form.imagePath"/>
          </el-form-item>
        </div>

        <div v-else>
          <el-form-item label="图片路径">
            <el-input v-model="form.imagePath" placeholder="请输入图片路径"></el-input>
          </el-form-item>
        </div>
        <el-form-item label="封面">
          <image-upload v-model="form.cover"/>
        </el-form-item>
        <el-form-item label="是否跳转">
          <el-radio-group v-model="form.isSkip">
            <el-radio
              v-for="dict in dict.type.confirm_status"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="跳转路径" prop="path" v-if="form.isSkip==1">
          <el-input v-model="form.path" placeholder="请输入跳转路径" />
        </el-form-item>
        <el-form-item label="轮播图状态">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in dict.type.sys_normal_disable"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
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
import { listSlideshow, getSlideshow, delSlideshow, addSlideshow, updateSlideshow } from "@/api/slideshow/slideshow";

export default {
  name: "Slideshow",
  dicts: ['slideshow_type', 'sys_normal_disable', 'confirm_status'],
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
      // 首页轮播图表格数据
      slideshowList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        imagePath: null,
        isSkip: null,
        path: null,
        status: null,
        type: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        type: [
          { required: true, message: "类型不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询首页轮播图列表 */
    getList() {
      this.loading = true;
      listSlideshow(this.queryParams).then(response => {
        this.slideshowList = response.rows;
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
        slideshowId: null,
        imagePath: null,
        isSkip: "0",
        path: null,
        status: "0",
        createTime: null,
        updateTime: null,
        type: "0"
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
      this.ids = selection.map(item => item.slideshowId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加首页轮播图";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const slideshowId = row.slideshowId || this.ids
      getSlideshow(slideshowId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改首页轮播图";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.slideshowId != null) {
            updateSlideshow(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSlideshow(this.form).then(response => {
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
      const slideshowIds = row.slideshowId || this.ids;
      this.$modal.confirm('是否确认删除首页轮播图编号为"' + slideshowIds + '"的数据项？').then(function() {
        return delSlideshow(slideshowIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('slideshow/slideshow/export', {
        ...this.queryParams
      }, `slideshow_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
