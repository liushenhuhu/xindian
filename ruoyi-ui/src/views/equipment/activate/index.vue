<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="120px">
      <el-form-item label="设备编号" prop="equipmentCode">
        <el-input
          v-model="queryParams.equipmentCode"
          placeholder="请输入设备编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备激活状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择设备激活状态" clearable>
          <el-option
            v-for="dict in dict.type.equipment_activate_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="设备归属" prop="affiliation">
        <el-select v-model="queryParams.affiliation" placeholder="请选择设备归属" clearable>
          <el-option
            v-for="dict in dict.type.equipment_affiliation"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="绑定手机号" prop="patientPhone">
        <el-input
          v-model="queryParams.patientPhone"
          placeholder="请输入绑定手机号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="在线状态" prop="onlineStatus">
        <el-select v-model="queryParams.onlineStatus" placeholder="请选择在线状态" clearable>
          <el-option
            v-for="dict in dict.type.equipment_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="激活手机号" prop="activatePhone">
        <el-input
          v-model="queryParams.activatePhone"
          placeholder="请输入激活手机号"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['equipment:activate:add']"
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
          v-hasPermi="['equipment:activate:edit']"
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
          v-hasPermi="['equipment:activate:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['equipment:activate:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="activateList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
<!--      <el-table-column label="设备id" align="center" prop="equipmentId" />-->
      <el-table-column label="设备编号" align="center" prop="equipmentCode" />
      <el-table-column label="设备激活状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equipment_activate_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="设备归属" align="center" prop="affiliation">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equipment_affiliation" :value="scope.row.affiliation"/>
        </template>
      </el-table-column>
      <el-table-column label="绑定手机号" align="center" prop="patientPhone" />
      <el-table-column label="在线状态" align="center" prop="onlineStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equipment_status" :value="scope.row.onlineStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="激活手机号" align="center" prop="activatePhone" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['equipment:activate:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['equipment:activate:remove']"
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

    <!-- 添加或修改设备归属激活对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="设备编号" prop="equipmentCode">
          <el-input v-model="form.equipmentCode" placeholder="请输入设备编号" />
        </el-form-item>
        <el-form-item label="设备激活状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择设备激活状态">
            <el-option
              v-for="dict in dict.type.equipment_activate_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="设备归属" prop="affiliation">
          <el-select v-model="form.affiliation" placeholder="请选择设备归属">
            <el-option
              v-for="dict in dict.type.equipment_affiliation"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="绑定手机号" prop="patientPhone">
          <el-input v-model="form.patientPhone" placeholder="请输入绑定手机号" />
        </el-form-item>
        <el-form-item label="在线状态" prop="onlineStatus">
          <el-select v-model="form.onlineStatus" placeholder="请选择在线状态">
            <el-option
              v-for="dict in dict.type.equipment_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="激活手机号" prop="activatePhone">
          <el-input v-model="form.activatePhone" placeholder="请输入激活手机号" />
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
import { listActivate, getActivate, delActivate, addActivate, updateActivate } from "@/api/equipment/activate";

export default {
  name: "Activate",
  dicts: ['equipment_status', 'equipment_activate_status', 'equipment_affiliation'],
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
      // 设备归属激活表格数据
      activateList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        equipmentCode: null,
        status: null,
        affiliation: null,
        patientPhone: null,
        onlineStatus: null,
        activatePhone: null
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
    /** 查询设备归属激活列表 */
    getList() {
      this.loading = true;
      listActivate(this.queryParams).then(response => {
        this.activateList = response.rows;
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
        equipmentId: null,
        equipmentCode: null,
        status: null,
        affiliation: null,
        patientPhone: null,
        onlineStatus: null,
        createTime: null,
        updateTime: null,
        activatePhone: null
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
      this.ids = selection.map(item => item.equipmentId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加设备归属激活";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const equipmentId = row.equipmentId || this.ids
      getActivate(equipmentId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改设备归属激活";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.equipmentId != null) {
            updateActivate(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addActivate(this.form).then(response => {
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
      const equipmentIds = row.equipmentId || this.ids;
      this.$modal.confirm('是否确认删除设备归属激活编号为"' + equipmentIds + '"的数据项？').then(function() {
        return delActivate(equipmentIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('equipment/activate/export', {
        ...this.queryParams
      }, `activate_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
