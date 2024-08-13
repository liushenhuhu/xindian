<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="电话" prop="patientPhone">
        <el-input
          v-model="queryParams.patientPhone"
          placeholder="请输入电话"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="性别" prop="gender">
        <el-select v-model="queryParams.gender" placeholder="请选择性别" clearable size="small">
          <el-option
            v-for="dict in sexList"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
<!--      <el-form-item label="年龄" prop="age">-->
<!--        <el-input-->
<!--          v-model="queryParams.age"-->
<!--          placeholder="请输入年龄"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="腰围" prop="waistline">-->
<!--        <el-input-->
<!--          v-model="queryParams.waistline"-->
<!--          placeholder="请输入腰围"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="胆固醇" prop="cholesterol">-->
<!--        <el-input-->
<!--          v-model="queryParams.cholesterol"-->
<!--          placeholder="请输入胆固醇"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="高密度脂蛋白胆固醇" prop="systolicAlbumen">-->
<!--        <el-input-->
<!--          v-model="queryParams.systolicAlbumen"-->
<!--          placeholder="请输入高密度脂蛋白胆固醇"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="舒张压" prop="diastolicBlood">-->
<!--        <el-input-->
<!--          v-model="queryParams.diastolicBlood"-->
<!--          placeholder="请输入舒张压"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="收缩压" prop="systolicBlood">-->
<!--        <el-input-->
<!--          v-model="queryParams.systolicBlood"-->
<!--          placeholder="请输入收缩压"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="服用降压药" prop="hypotensiveDrugs">-->
<!--        <el-input-->
<!--          v-model="queryParams.hypotensiveDrugs"-->
<!--          placeholder="请输入服用降压药"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="是否糖尿病" prop="diabetes">-->
<!--        <el-input-->
<!--          v-model="queryParams.diabetes"-->
<!--          placeholder="请输入是否糖尿病"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="是否吸烟" prop="smoking">-->
<!--        <el-input-->
<!--          v-model="queryParams.smoking"-->
<!--          placeholder="请输入是否吸烟"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="是否心血管病家族史" prop="fammilyCd">-->
<!--        <el-input-->
<!--          v-model="queryParams.fammilyCd"-->
<!--          placeholder="请输入是否心血管病家族史"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="是否脑卒中家族史" prop="fammilyCa">-->
<!--        <el-input-->
<!--          v-model="queryParams.fammilyCa"-->
<!--          placeholder="请输入是否脑卒中家族史"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="低密度脂蛋白胆固醇" prop="lowAlbumen">-->
<!--        <el-input-->
<!--          v-model="queryParams.lowAlbumen"-->
<!--          placeholder="请输入低密度脂蛋白胆固醇"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="甘油三酯" prop="triglyceride">-->
<!--        <el-input-->
<!--          v-model="queryParams.triglyceride"-->
<!--          placeholder="请输入甘油三酯"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="身份证号" prop="patientCode">-->
<!--        <el-input-->
<!--          v-model="queryParams.patientCode"-->
<!--          placeholder="请输入身份证号"-->
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
          v-hasPermi="['system:survey:add']"
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
          v-hasPermi="['system:survey:edit']"
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
          v-hasPermi="['system:survey:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:survey:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="surveyList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
<!--      <el-table-column label="" align="center" prop="id" />-->
      <el-table-column label="电话" align="center" prop="patientPhone" />
      <el-table-column label="身份证号" align="center" prop="patientCode" />
      <el-table-column label="性别" align="center" prop="gender" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.gender == '1'" type="success">男</el-tag>
          <el-tag v-else type="danger">女</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="年龄" align="center" prop="age" />
      <el-table-column label="腰围" align="center" prop="waistline" />
      <el-table-column label="胆固醇" align="center" prop="cholesterol" />
      <el-table-column label="高密度脂蛋白胆固醇" align="center" prop="systolicAlbumen" />
      <el-table-column label="舒张压" align="center" prop="diastolicBlood" />
      <el-table-column label="收缩压" align="center" prop="systolicBlood" />
      <el-table-column label="服用降压药" align="center" prop="hypotensiveDrugs" />
      <el-table-column label="是否糖尿病" align="center" prop="diabetes" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.diabetes === '1'" type="success">是</el-tag>
          <el-tag v-else type="danger">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="是否吸烟" align="center" prop="smoking" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.smoking === '1'" type="success">是</el-tag>
          <el-tag v-else type="danger">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="是否心血管病家族史" align="center" prop="fammilyCd" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.fammilyCd === '1'" type="success">是</el-tag>
          <el-tag v-else type="danger">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="是否脑卒中家族史" align="center" prop="fammilyCa" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.fammilyCa === '1'" type="success">是</el-tag>
          <el-tag v-else type="danger">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="低密度脂蛋白胆固醇" align="center" prop="lowAlbumen" />
      <el-table-column label="甘油三酯" align="center" prop="triglyceride" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:survey:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:survey:remove']"
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

    <!-- 添加或修改wSuryvey对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="220px">
        <el-form-item label="电话" prop="patientPhone">
          <el-input v-model="form.patientPhone" placeholder="请输入电话" />
        </el-form-item>
        <el-form-item label="身份证号" prop="patientCode">
          <el-input v-model="form.patientCode" placeholder="请输入身份证号" />
        </el-form-item>
<!--        <el-form-item label="性别" prop="gender">-->
<!--          <el-input v-model="form.gender" placeholder="请输入性别" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="年龄" prop="age">-->
<!--          <el-input v-model="form.age" placeholder="请输入年龄" />-->
<!--        </el-form-item>-->
        <el-form-item label="腰围" prop="waistline">
          <el-input-number v-model="form.waistline"  :step="10" :max="160" :min="0" placeholder="请输入腰围 (50-130)"></el-input-number>
        </el-form-item>
        <el-form-item label="胆固醇(mmol/L)" prop="cholesterol">
          <el-input-number v-model="form.cholesterol" :precision="2" :step="0.5" :max="10" :min="0" placeholder="请输入胆固醇 (2.2-5.8)"></el-input-number>
        </el-form-item>
        <el-form-item label="高密度脂蛋白胆固醇(mmol/L)" prop="systolicAlbumen">
          <el-input-number v-model="form.systolicAlbumen" :precision="2" :step="0.1" :max="10" :min="0" placeholder="请输入高密度脂蛋白胆固醇 (1-2.35)"></el-input-number>
        </el-form-item>
        <el-form-item label="低密度脂蛋白胆固醇(mmol/L)" prop="lowAlbumen">
          <el-input-number v-model="form.lowAlbumen" :precision="2"  :step="0.1" :max="10" :min="0" placeholder="请输入低密度脂蛋白胆固醇 (2.07-3.5)"></el-input-number>
        </el-form-item>
        <el-form-item label="甘油三酯(mmol/L)" prop="triglyceride">
          <el-input-number v-model="form.triglyceride" :precision="2"  :step="0.1" :max="10" :min="0" placeholder="请输入甘油三酯 (0.4-1.71)"></el-input-number>
        </el-form-item>
        <el-form-item label="舒张压(mmHg)" prop="diastolicBlood">
          <el-input-number v-model="form.diastolicBlood"  :step="10" :max="200" :min="10" placeholder="请输入舒张压 (70-200)"></el-input-number>
        </el-form-item>
        <el-form-item label="收缩压(mmHg)" prop="systolicBlood">
          <el-input-number v-model="form.systolicBlood"  :step="10" :max="200" :min="10" placeholder="请输入收缩压 (70-200)"></el-input-number>
        </el-form-item>
        <el-form-item label="服用降压药" prop="hypotensiveDrugs">
          <el-select v-model="form.hypotensiveDrugs" clearable placeholder="请选择是否服用降压药">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否糖尿病" prop="diabetes">
          <el-select v-model="form.diabetes" clearable placeholder="请选择是否糖尿病">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否吸烟" prop="smoking">
          <el-select v-model="form.smoking" clearable placeholder="请选择是否吸烟">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否心血管病家族史" prop="fammilyCd">
          <el-select v-model="form.fammilyCd" clearable placeholder="请选择是否心血管病家族史">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否脑卒中家族史" prop="fammilyCa">
          <el-select v-model="form.fammilyCa" clearable placeholder="请选择是否脑卒中家族史">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>


        <el-divider content-position="center">万人筛查信息详情信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddWSurveyHistory">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteWSurveyHistory">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="wSurveyHistories" :row-class-name="rowWSurveyHistoryIndex" @selection-change="handleWSurveyHistorySelectionChange" ref="wSurveyHistory">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="年份" prop="particularYear" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.particularYear" placeholder="请输入年份" />
            </template>
          </el-table-column>
          <el-table-column label="低密度脂蛋白胆固醇(mmol/L)" prop="hLowAlbumen" width="250">
            <template slot-scope="scope">
              <el-input-number v-model="scope.row.hLowAlbumen" :precision="2"  :step="0.1" :max="10" :min="0" placeholder="请输入低密度脂蛋白胆固醇 (2.07-3.5)"></el-input-number>
            </template>
          </el-table-column>
          <el-table-column label="甘油三酯(mmol/L)" prop="hTriglyceride" width="250">
            <template slot-scope="scope">
              <el-input-number v-model="scope.row.hTriglyceride" :precision="2"  :step="0.1" :max="10" :min="0" placeholder="请输入甘油三酯 (0.4-1.71)"></el-input-number>
            </template>
          </el-table-column>
          <el-table-column label="高密度脂蛋白胆固醇(mmol/L)" prop="hSystolicAlbumen" width="250">
            <template slot-scope="scope">
              <el-input-number v-model="scope.row.hSystolicAlbumen" :precision="2" :step="0.1" :max="10" :min="0" placeholder="请输入高密度脂蛋白胆固醇 (1-2.35)"></el-input-number>
            </template>
          </el-table-column>
          <el-table-column label="胆固醇(mmol/L)" prop="hCholesterol" width="250">
            <template slot-scope="scope">
              <el-input-number v-model="scope.row.hCholesterol" :precision="2" :step="0.5" :max="10" :min="0" placeholder="请输入胆固醇 (2.2-5.8)"></el-input-number>
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listSurvey, getSurvey, delSurvey, addSurvey, updateSurvey } from "@/api/system/survey";

export default {
  name: "Survey",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedWSurveyHistory: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // wSuryvey表格数据
      surveyList: [],
      // 万人筛查信息详情表格数据
      wSurveyHistories: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        patientPhone: null,
        gender: null,
        age: null,
        waistline: null,
        cholesterol: null,
        systolicAlbumen: null,
        diastolicBlood: null,
        systolicBlood: null,
        hypotensiveDrugs: null,
        diabetes: null,
        smoking: null,
        fammilyCd: null,
        fammilyCa: null,
        lowAlbumen: null,
        triglyceride: null,
        patientCode: null
      },
      options:[
        {
          value: 1,
          label: '是'
        },
        {
          value: 0,
          label: '否'
        }
      ],
      sexList:[
        {
          value: 1,
          label: '男'
        },
        {
          value: 0,
          label: '女'
        }
      ],
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
    /** 查询wSuryvey列表 */
    getList() {
      this.loading = true;
      listSurvey(this.queryParams).then(response => {
        this.surveyList = response.rows;
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
        patientPhone: null,
        gender: null,
        age: null,
        waistline: null,
        cholesterol: null,
        systolicAlbumen: null,
        diastolicBlood: null,
        systolicBlood: null,
        hypotensiveDrugs: null,
        diabetes: null,
        smoking: null,
        fammilyCd: null,
        fammilyCa: null,
        lowAlbumen: null,
        triglyceride: null,
        createTime: null,
        patientCode: null
      };
      this.wSurveyHistories = [];
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
      this.title = "添加万人筛查信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getSurvey(id).then(response => {
        this.form = response.data;
        this.wSurveyHistories = response.data.wSurveyHistories;
        this.open = true;
        this.title = "修改万人筛查信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          let idCardNumber =  this.form.patientCode


          let genderCode = idCardNumber.length === 15 ? idCardNumber.charAt(14) : idCardNumber.charAt(16);
          this.form.sex = parseInt(genderCode, 10) % 2 === 0 ? 0 : 1;

          this.form.wSurveyHistories = this.wSurveyHistories;
          if (this.form.id != null) {
            updateSurvey(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSurvey(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除wSuryvey编号为"' + ids + '"的数据项？').then(function() {
        return delSurvey(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 万人筛查信息详情序号 */
    rowWSurveyHistoryIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** 万人筛查信息详情添加按钮操作 */
    handleAddWSurveyHistory() {
      let obj = {};
      obj.particularYear = "";
      obj.hLowAlbumen = "";
      obj.hTriglyceride = "";
      obj.hSystolicAlbumen = "";
      obj.hCholesterol = "";
      this.wSurveyHistories.push(obj);
    },
    /** 万人筛查信息详情删除按钮操作 */
    handleDeleteWSurveyHistory() {
      if (this.checkedWSurveyHistory.length == 0) {
        this.$modal.msgError("请先选择要删除的万人筛查信息详情数据");
      } else {
        const wSurveyHistories = this.wSurveyHistories;
        const checkedWSurveyHistory = this.checkedWSurveyHistory;
        this.wSurveyHistories = wSurveyHistories.filter(function(item) {
          return checkedWSurveyHistory.indexOf(item.index) == -1
        });
      }
    },
    /** 复选框选中数据 */
    handleWSurveyHistorySelectionChange(selection) {
      this.checkedWSurveyHistory = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('survey/export', {
        ...this.queryParams
      }, `survey_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
