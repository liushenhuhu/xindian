<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="90px">
      <el-form-item label="账号id" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入账号id"
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
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="primary"-->
<!--          plain-->
<!--          icon="el-icon-plus"-->
<!--          size="mini"-->
<!--          @click="handleAdd"-->
<!--          v-hasPermi="['ano:ano:add']"-->
<!--        >新增-->
<!--        </el-button>-->
<!--      </el-col>-->
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="success"-->
<!--          plain-->
<!--          icon="el-icon-edit"-->
<!--          size="mini"-->
<!--          :disabled="single"-->
<!--          @click="handleUpdate"-->
<!--          v-hasPermi="['ano:ano:edit']"-->
<!--        >修改-->
<!--        </el-button>-->
<!--      </el-col>-->
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="danger"-->
<!--          plain-->
<!--          icon="el-icon-delete"-->
<!--          size="mini"-->
<!--          :disabled="multiple"-->
<!--          @click="handleDelete"-->
<!--          v-hasPermi="['ano:ano:remove']"-->
<!--        >删除-->
<!--        </el-button>-->
<!--      </el-col>-->
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="warning"-->
<!--          plain-->
<!--          icon="el-icon-download"-->
<!--          size="mini"-->
<!--          @click="handleExport"-->
<!--          v-hasPermi="['ano:ano:export']"-->
<!--        >导出-->
<!--        </el-button>-->
<!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="anoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="账号id" align="center" prop="userId"/>
      <el-table-column label="用户名" align="center" prop="nickName"/>
<!--      <el-table-column label="患者管理id" align="center" prop="pId"/>-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-refresh-right"-->
<!--            @click="refresh(scope.row)"-->
<!--            v-hasPermi="['ano:ano:edit']"-->
<!--          >更新-->
<!--          </el-button>-->
          <el-button
            size="mini"
            type="text"
            icon="el-icon-s-promotion"
            @click="assignAno(scope.row)"
          >分配
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-search"
            @click="getList3(scope.row)"
          >查看已分配
          </el-button>
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-edit"-->
<!--            @click="handleUpdate(scope.row)"-->
<!--            v-hasPermi="['ano:ano:edit']"-->
<!--          >修改-->
<!--          </el-button>-->
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-delete"-->
<!--            @click="handleDelete(scope.row)"-->
<!--            v-hasPermi="['ano:ano:remove']"-->
<!--          >删除-->
<!--          </el-button>-->
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

    <!-- 添加或修改ano对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="90px">
        <el-form-item label="账号id" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入账号id"/>
        </el-form-item>
        <el-form-item label="患者管理id" prop="pId">
          <el-input v-model="form.pId" placeholder="请输入患者管理id"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
<!--    分配pid-->
    <el-dialog title="分配标注患者" :visible.sync="showNotAssign" width="950px" append-to-body>
      <el-form :model="queryParams2" ref="queryForm2" size="small" :inline="true" v-show="showSearch2" >
        <el-form-item label="患者管理id" prop="pId">
          <el-input
            v-model="queryParams2.pId"
            placeholder="请输入患者管理id"
            clearable
            @keyup.enter.native="handleQuery2"
          />
        </el-form-item>
        <el-form-item label="心电种类" prop="ecgType">
          <el-input
            v-model="queryParams2.ecgType"
            placeholder="请选择心电种类"
            clearable
            @keyup.enter.native="handleQuery2"
          />
        </el-form-item>
        <el-form-item label="已分配账号id" prop="assignedId" label-width="100px">
          <el-input
            v-model="queryParams2.assignedId"
            placeholder="请输入已分配账号id"
            clearable
            @keyup.enter.native="handleQuery2"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery2">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery2">重置</el-button>
        </el-form-item>
      </el-form>
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="addAno"
          >添加
          </el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch2" @queryTable="getNotAssign"></right-toolbar>
      </el-row>

      <el-table v-loading="loading2" :data="notAssignList" @selection-change="handleSelectionChange2">
        <el-table-column type="selection" width="55" align="center"/>
        <el-table-column label="患者id" align="center" prop="pId"/>
        <el-table-column label="心电种类" align="center" prop="ecgType">
          <template slot-scope="scope" >
            <el-tag >
              {{scope.row.ecgType}}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="已分配账号id" align="center" prop="assignedId"/>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-refresh-right"
              @click="addAno(scope.row)"
            >添加</el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination
        v-show="total2>0"
        :total="total2"
        :page.sync="queryParams2.pageNum"
        :limit.sync="queryParams2.pageSize"
        @pagination="getNotAssign"
      />
    </el-dialog>
    <!--    查看已经分配的-->
    <el-dialog title="已分配" :visible.sync="show3"  append-to-body>
      <el-table v-loading="loading3" :data="list3">
        <el-table-column label="账号id" align="center" prop="userId"/>
        <el-table-column label="患者管理id" align="center" prop="pId"/>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-refresh-right"
              @click="refresh(scope.row)"
            >更新
            </el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="delAno(scope.row)"
            >删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination
        v-show="total3>0"
        :total="total3"
        :page.sync="queryParams3.pageNum"
        :limit.sync="queryParams3.pageSize"
        @pagination="get3"
      />
    </el-dialog>
  </div>
</template>

<script>
import {
  listAno,
  getAno,
  delAno,
  addAno,
  updateAno,
  assigned_ano,
  re_assigned_ano,
  listAno2,
  getNotAssign, assignedAnoList, delAno2
} from "@/api/ano/ano";

export default {
  name: "Ano",
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
      showSearch: false,
      // 总条数
      total: 0,
      // ano表格数据
      anoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        pId: null,
        deptId: 106
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      notAssignList:[],
      total2:0,
      loading2: true,
      // 选中数组
      ids2: [],
      // 非单个禁用
      single2: true,
      // 非多个禁用
      multiple2: true,
      // 显示搜索条件
      showSearch2: false,
      showNotAssign:false,
      queryParams2: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        assignedId:null,
        ecgType:null,
        pId:null,
      },
      show3:false,
      loading3:false,
      list3:[],
      queryParams3: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
      },
      total3:0
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询ano列表 */
    getList() {
      this.loading = true;
      listAno2(this.queryParams).then(response => {
        this.anoList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    getList3(row) {
      console.log(row)
      this.show3=true
      this.queryParams3.userId=row.userId
      this.get3()
    },
    get3(){
      this.loading3 = true;
      listAno(this.queryParams3).then(response => {
        this.list3 = response.rows;
        this.total3 = response.total;
        this.loading3 = false;
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
        anoUserId: null,
        userId: null,
        pId: null
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
    /** 搜索按钮操作 */
    handleQuery2() {
      this.queryParams2.pageNum = 1;
      this.getNotAssign();
    },
    /** 重置按钮操作 */
    resetQuery2() {
      this.resetForm("queryForm2");
      this.handleQuery2();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.anoUserId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    handleSelectionChange2(selection) {
      this.ids2 = selection.map(item => item.pId)
      this.single2 = selection.length !== 1
      this.multiple2 = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加ano";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const anoUserId = row.anoUserId || this.ids
      getAno(anoUserId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改ano";
      });
    },
    addAno(row){
      console.log(row,this.ids2)
      let anoUserId=[]
      if(row.pId){
        anoUserId.push(row.pId)
      }else {
        anoUserId=this.ids2
      }
      assignedAnoList(JSON.stringify({userId:this.queryParams2.userId,pId:anoUserId})).then(res=>{
        console.log(res)
        this.$modal.msgSuccess("分配成功");
        this.getNotAssign()
      })
    },
    refresh(row) {
      this.reset();
      const anoUserId = row.anoUserId || this.ids
      getAno(anoUserId).then(response => {
        this.form = response.data;
        re_assigned_ano(this.form).then(res => {
          if (res === 0) {
            this.$modal.msgSuccess("无更新");
          } else {
            this.$modal.msgSuccess("更新成功");
          }
        })
      });
    },
    assignAno(row){
      this.showNotAssign=true
      this.queryParams2.userId=row.userId
      this.getNotAssign()
    },
    getNotAssign(){
      this.loading2=true
      getNotAssign(this.queryParams2).then(res=>{
        console.log(res)
        this.notAssignList=res.rows
        this.total2=res.total
        this.loading2=false
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.anoUserId != null) {
            updateAno(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAno(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              assigned_ano(this.form).then(res => {
                this.$modal.msgSuccess("分配成功");
              })
              this.getList();
            });

          }
        }
      });
    },
    delAno(row){
      console.log(row)
      delAno2(row).then(res=>{
        this.get3()
        this.$modal.msgSuccess("删除成功");
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const anoUserIds = row.anoUserId || this.ids;
      this.$modal.confirm('是否确认删除ano编号为"' + anoUserIds + '"的数据项？').then(function () {
        return delAno(anoUserIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('ano/ano/export', {
        ...this.queryParams
      }, `ano_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
