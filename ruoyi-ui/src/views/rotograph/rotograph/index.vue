<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="标题" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入标题"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="开始时间" prop="startTime">
        <el-date-picker clearable
          v-model="queryParams.startTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择开始时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="结束时间" prop="endTime">
        <el-date-picker clearable
          v-model="queryParams.endTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择结束时间">
        </el-date-picker>
      </el-form-item>
<!--      <el-form-item label="不同端标识(医生，用户)" prop="position">-->
<!--        <el-input-->
<!--          v-model="queryParams.position"-->
<!--          placeholder="请输入不同端标识(医生，用户)"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="摘要" prop="abstract">-->
<!--        <el-input-->
<!--          v-model="queryParams.abstract"-->
<!--          placeholder="请输入摘要"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="显示位置" prop="showPosition">-->
<!--        <el-input-->
<!--          v-model="queryParams.showPosition"-->
<!--          placeholder="请输入显示位置"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="图片位置" prop="imageUrl">-->
<!--        <el-input-->
<!--          v-model="queryParams.imageUrl"-->
<!--          placeholder="请输入图片位置"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="创建时间" prop="createDate">-->
<!--        <el-date-picker clearable-->
<!--          v-model="queryParams.createDate"-->
<!--          type="date"-->
<!--          value-format="yyyy-MM-dd"-->
<!--          placeholder="请选择创建时间">-->
<!--        </el-date-picker>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="更新人" prop="updateby">-->
<!--        <el-input-->
<!--          v-model="queryParams.updateby"-->
<!--          placeholder="请输入更新人"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="更新时间" prop="updateDate">-->
<!--        <el-date-picker clearable-->
<!--          v-model="queryParams.updateDate"-->
<!--          type="date"-->
<!--          value-format="yyyy-MM-dd"-->
<!--          placeholder="请选择更新时间">-->
<!--        </el-date-picker>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="删除标志位" prop="deleteflag">-->
<!--        <el-input-->
<!--          v-model="queryParams.deleteflag"-->
<!--          placeholder="请输入删除标志位"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
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
          v-hasPermi="['rotograph:rotograph:add']"
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
          v-hasPermi="['rotograph:rotograph:edit']"
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
          v-hasPermi="['rotograph:rotograph:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['rotograph:rotograph:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="rotographList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
<!--      <el-table-column label="唯一标识" align="center" prop="id" />-->
      <el-table-column label="标题" align="center" prop="title" />
      <el-table-column label="图片" align="center" prop="imageUrl" >
        <template slot-scope="scope">
          <div class="demo-image__preview">
            <el-image  v-if="scope.row.imageUrl" style="width: 100px; height: 100px; margin-left:10px;"
                       :src="scope.row.imageUrl"
                       :preview-src-list="scope.row.imageUrl"
            ></el-image>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="开始时间" align="center" prop="startTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束时间" align="center" prop="endTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>

<!--      <el-table-column label="位置" align="center" prop="position" />-->
<!--      <el-table-column label="显示顺序" align="center" prop="linkId" />-->
<!--      <el-table-column label="摘要" align="center" prop="abstract" />-->
<!--      <el-table-column label="描述" align="center" prop="description" />-->
<!--      <el-table-column label="显示位置" align="center" prop="showPosition" />-->
<!--      <el-table-column label="图片位置" align="center" prop="imageUrl" />-->
<!--      <el-table-column label="创建时间" align="center" prop="createDate" width="180">-->
<!--        <template slot-scope="scope">-->
<!--          <span>{{ parseTime(scope.row.createDate, '{y}-{m}-{d}') }}</span>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--      <el-table-column label="更新人" align="center" prop="updateby" />-->
<!--      <el-table-column label="更新时间" align="center" prop="updateDate" width="180">-->
<!--        <template slot-scope="scope">-->
<!--          <span>{{ parseTime(scope.row.updateDate, '{y}-{m}-{d}') }}</span>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--      <el-table-column label="删除标志位" align="center" prop="deleteflag" />-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['rotograph:rotograph:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['rotograph:rotograph:remove']"
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

    <!-- 添加或修改轮播广告对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入标题" />
        </el-form-item>
        <el-form-item label="开始时间" prop="startTime">
          <el-date-picker clearable
            v-model="form.startTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择开始时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-date-picker clearable
            v-model="form.endTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择结束时间">
          </el-date-picker>
        </el-form-item>
<!--        <el-form-item label="位置" prop="position">-->
<!--          <el-input v-model="form.position" placeholder="请输入不同端标识(医生，用户)" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="摘要" prop="abstract">-->
<!--          <el-input v-model="form.abstract" placeholder="请输入摘要" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="描述" prop="description">-->
<!--          <el-input v-model="form.description" type="textarea" placeholder="请输入内容" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="显示位置" prop="showPosition">-->
<!--          <el-input v-model="form.showPosition" placeholder="请输入显示位置" />-->
<!--        </el-form-item>-->
        <el-form-item label="上传图片" prop="imageUrl">
          <el-upload
            class="avatar-uploader"
            action="#"
            :on-change="handleChange"
            :auto-upload="false"><!--不进行默认上传-->
            <img v-if="imageUrl" :src="imageUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
<!--        <el-form-item label="创建时间" prop="createDate">-->
<!--          <el-date-picker clearable-->
<!--            v-model="form.createDate"-->
<!--            type="date"-->
<!--            value-format="yyyy-MM-dd"-->
<!--            placeholder="请选择创建时间">-->
<!--          </el-date-picker>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="更新时间" prop="updateDate">-->
<!--          <el-date-picker clearable-->
<!--            v-model="form.updateDate"-->
<!--            type="date"-->
<!--            value-format="yyyy-MM-dd"-->
<!--            placeholder="请选择更新时间">-->
<!--          </el-date-picker>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="删除标志位" prop="deleteflag">-->
<!--          <el-input v-model="form.deleteflag" placeholder="请输入删除标志位" />-->
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
import { listRotograph, getRotograph, delRotograph, addRotograph, updateRotograph } from "@/api/rotograph/rotograph";

export default {
  name: "Rotograph",
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
      // 轮播广告表格数据
      rotographList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      imageUrl:'',
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        title: null,
        startTime: null,
        endTime: null,
        position: null,
        linkId: null,
        abstract: null,
        description: null,
        showPosition: null,
        imageUrl: null,
        createDate: null,
        updateby: null,
        updateDate: null,
        deleteflag: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询轮播广告列表 */
    getList() {
      this.loading = true;
      listRotograph(this.queryParams).then(response => {
        this.rotographList = response.rows;
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
        title: null,
        startTime: null,
        endTime: null,
        position: null,
        linkId: null,
        abstract: null,
        description: null,
        showPosition: null,
        imageUrl: null,
        createBy: null,
        createDate: null,
        updateby: null,
        updateDate: null,
        deleteflag: null
      };
      this.imageUrl=''
      this.resetForm("form");
    },
    handleChange(file, fileList) {
      this.imageUrl=URL.createObjectURL(file.raw)
      this.form.file=file.raw;
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
      // this.$router.push({path: "/addAD"});
      // window.open(routeUrl.href, '_blank');
      this.open = true;
      this.reset();
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getRotograph(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改轮播广告";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          var formData = new FormData();
          if(this.form.file){
            formData.append("file",this.form.file)
          }
          formData.append("rotograph",JSON.stringify(this.form))

          if (this.form.id != null) {
            updateRotograph(formData).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
              this.reset()
            });
          } else {
            console.log(this.form)

            addRotograph(formData).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
              this.reset()
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除轮播广告编号为"' + ids + '"的数据项？').then(function() {
        return delRotograph(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('rotograph/rotograph/export', {
        ...this.queryParams
      }, `rotograph_${new Date().getTime()}.xlsx`)
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
