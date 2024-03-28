<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      size="small"
      :inline="true"
      v-show="showSearch"
      label-width="100px"
    >
      <!--      <el-form-item label="日志id" prop="logId">
        <el-input
          v-model="queryParams.logId"
          placeholder="请输入日志id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>-->
      <el-form-item label="发生时间" prop="logTime">
        <el-form-item>
          <el-date-picker
            v-model="daterangeLogTime"
            style="width: 240px"
            value-format="yyyy-MM-dd HH:mm:ss"
            type="datetimerange"
            range-separator="-"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          ></el-date-picker>
        </el-form-item>
      </el-form-item>
      <el-form-item label="预警类型" prop="logType">
        <el-input
          v-model="queryParams.logType"
          placeholder="请输入预警类型"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="事件名称" prop="eventName">
        <el-input
          v-model="queryParams.eventName"
          placeholder="请输入事件名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="事件说明" prop="eventDescription">
        <el-input
          v-model="queryParams.eventDescription"
          placeholder="请输入事件说明"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="患者管理编号" prop="pId">
        <el-input
          v-model="queryParams.pId"
          placeholder="请输入患者管理编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="患者姓名" prop="patientName">
        <el-input
          v-model="queryParams.patientName"
          placeholder="请输入患者姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="患者电话" prop="patientPhone">
        <el-input
          v-model="queryParams.patientPhone"
          placeholder="请输入患者电话"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="医院名称" prop="hospitalCode">
        <el-select
          v-model="queryParams.hospitalCode"
          placeholder="请选择医院代号"
        >
          <el-option
            v-for="item in options"
            :key="item.hospitalId"
            :label="item.hospitalName"
            :value="item.hospitalCode"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="心电类型" prop="type">
        <el-input
          v-model="queryParams.type"
          placeholder="请输入心电类型"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否标注" prop="anoStatus">
        <el-select
          v-model="queryParams.anoStatus"
          placeholder="请选择是否标注"
          clearable
        >
          <el-option
            v-for="dict in dict.type.if_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <!--      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['alert_log:alert_log:add']"
        >新增
        </el-button>
      </el-col>-->
      <!--      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['alert_log:alert_log:edit']"
        >修改
        </el-button>
      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['alert_log_s:alert_log:remove']"
          >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['alert_log_s:alert_log:export']"
          >导出
        </el-button>
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="alert_logList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <!--      <el-table-column label="日志id" align="center" prop="logId" />-->
      <el-table-column
        label="发生时间"
        align="center"
        prop="logTime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{
            parseTime(scope.row.logTime, "{y}-{m}-{d} {h}:{i}:{s}")
          }}</span>
        </template>
      </el-table-column>
      <el-table-column label="预警类型" align="center" prop="logType" />
      <el-table-column label="事件名称" align="center" prop="eventName" />
      <el-table-column
        label="事件说明"
        align="center"
        prop="eventDescription"
      />
      <el-table-column
        label="患者管理编号"
        align="center"
        prop="pId"
        show-overflow-tooltip
      />
      <el-table-column label="患者姓名" align="center" prop="patientName" />
      <!--      <el-table-column label="患者身份证号" align="center" prop="patientCode"/>
            <el-table-column label="患者电话" align="center" prop="patientPhone"/>
            <el-table-column label="家属电话" align="center" prop="familyPhone"/>-->
      <!--      <el-table-column label="医院代号" align="center" prop="hospitalCode"/>-->
      <!--            <el-table-column label="医院名称" align="center" prop="hospitalName" width="150"/>-->
      <el-table-column label="是否标注" align="center" prop="anoStatus">
        <template slot-scope="scope">
          <dict-tag
            :options="dict.type.if_status"
            :value="scope.row.anoStatus"
          />
        </template>
      </el-table-column>
      <el-table-column
        label="心电种类"
        align="center"
        prop="ecgType"
        width="150"
      >
        <template slot-scope="scope">
          <el-tag>
            {{ scope.row.ecgType }}
          </el-tag>
        </template>
      </el-table-column>
      <!--  隐藏的患者的个人信息    -->
      <el-table-column type="expand">
        <template slot-scope="scope">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-divider content-position="left">其他信息</el-divider>
            <el-form-item
              label="患者身份证号"
              width="200"
              style="padding-left: 40px"
            >
              <span>{{ scope.row.patientCode }}</span>
            </el-form-item>
            <el-form-item
              label="患者电话"
              width="200"
              style="padding-left: 40px"
            >
              <span>{{ scope.row.patientPhone }}</span>
            </el-form-item>
            <el-form-item
              label="家属电话"
              width="200"
              style="padding-left: 40px"
            >
              <span>{{ scope.row.familyPhone }}</span>
            </el-form-item>
            <br />
            <!--            <el-form-item label="医院名称" width="200" style="padding-left: 40px">
              <span>{{ scope.row.hospitalName }}</span>
            </el-form-item>-->
          </el-form>
        </template>
      </el-table-column>

      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <!-- <el-popover
            placement="left"
            trigger="hover"
            visible-arrow="true"
            width="500"
            
            popper-class="wrap"
          >
            <div>
              
              <div class="echarts">
                <div class="container">
                  <div
                    class="chart"
                    id="I"
                    
                  >1</div>
                 
                </div>
                <div class="container">
                  <div
                    class="chart"
                    id="II"
                    
                  >2</div>
                  
                </div>
                <div class="container">
                  <div
                    class="chart"
                    id="III"
                   
                  ></div>
                  
                </div>
                <div class="container">
                  <div
                    class="chart"
                    id="aVR"
                    
                  ></div>
                  
                </div>
                <div class="container">
                  <div
                    class="chart"
                    id="aVL"
                    
                  ></div>
                  
                </div>
                <div class="container">
                  <div
                    class="chart"
                    id="aVF"
                    
                  ></div>
                  
                </div>
                <div class="container">
                  <div
                    class="chart"
                    id="V1"
                    
                  ></div>
                  
                </div>
                <div class="container">
                  <div
                    class="chart"
                    id="V2"
                   
                  ></div>
                  
                </div>
                <div class="container">
                  <div
                    class="chart"
                    id="V3"
                    
                  ></div>
                  
                </div>
                <div class="container">
                  <div
                    class="chart"
                    id="V4"
                    
                  ></div>
                  
                </div>
                <div class="container">
                  <div
                    class="chart"
                    id="V5"
                    
                  ></div>
                 
                </div>
                <div class="container">
                  <div
                    class="chart"
                    id="V6"
                  ></div>
                </div>
              </div>
            </div> -->

            <el-button
              size="mini"
              type="text"
              icon="el-icon-view"
              @click="handleLook(scope.row)"
              v-hasPermi="['alert_log:alert_log:look']"
              slot="reference"
              @mouseover="getMessage(scope.row.logId,scope.row.userId)"
             
              >查看日志
            </el-button>
          <!-- </el-popover> -->


           <!-- <el-popover
            placement="left"
            trigger="hover"
            visible-arrow="true"
            width="500"
            
            popper-class="wrap"
          >
            <div>
              
              <div class="echarts">
                <div class="container">
                  <div
                    class="chart"
                    id="I"
                    
                  >1</div>
                 
                </div>
                <div class="container">
                  <div
                    class="chart"
                    id="II"
                    
                  >2</div>
                  
                </div>
                <div class="container">
                  <div
                    class="chart"
                    id="III"
                   
                  ></div>
                  
                </div>
                <div class="container">
                  <div
                    class="chart"
                    id="aVR"
                    
                  ></div>
                  
                </div>
                <div class="container">
                  <div
                    class="chart"
                    id="aVL"
                    
                  ></div>
                  
                </div>
                <div class="container">
                  <div
                    class="chart"
                    id="aVF"
                    
                  ></div>
                  
                </div>
                <div class="container">
                  <div
                    class="chart"
                    id="V1"
                    
                  ></div>
                  
                </div>
                <div class="container">
                  <div
                    class="chart"
                    id="V2"
                   
                  ></div>
                  
                </div>
                <div class="container">
                  <div
                    class="chart"
                    id="V3"
                    
                  ></div>
                  
                </div>
                <div class="container">
                  <div
                    class="chart"
                    id="V4"
                    
                  ></div>
                  
                </div>
                <div class="container">
                  <div
                    class="chart"
                    id="V5"
                    
                  ></div>
                 
                </div>
                <div class="container">
                  <div
                    class="chart"
                    id="V6"
                  ></div>
                </div>
              </div>
            </div>

            <el-button
              size="mini"
              type="text"
              icon="el-icon-view"
              @click="handleLook(scope.row)"
              v-hasPermi="['alert_log:alert_log:look']"
              slot="reference"
              @mouseover="getMessage(scope.row.logId,scope.row.userId)"
             
              >查看日志
            </el-button>
          </el-popover> -->
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['alert_log_s:alert_log:remove']"
            >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改预警日志对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="发生时间" prop="logTime">
          <el-date-picker
            clearable
            v-model="form.logTime"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="请选择发生时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="预警类型" prop="logType">
          <el-input v-model="form.logType" placeholder="请输入预警类型" />
        </el-form-item>
        <el-form-item label="事件名称" prop="eventName">
          <el-input v-model="form.eventName" placeholder="请输入事件名称" />
        </el-form-item>
        <el-form-item label="事件说明" prop="eventDescription">
          <el-input
            v-model="form.eventDescription"
            placeholder="请输入事件说明"
          />
        </el-form-item>
        <el-form-item label="患者管理id" prop="pId">
          <el-input v-model="form.pId" placeholder="请输入患者管理id" />
        </el-form-item>
        <el-form-item label="标注状态">
          <el-radio-group v-model="form.anoStatus">
            <el-radio
              v-for="dict in dict.type.if_status"
              :key="dict.value"
              :label="parseInt(dict.value)"
              >
              {{ dict.label }}
            </el-radio>
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
import {
  listAlert_log,
  getAlert_log,
  delAlert_log,
  addAlert_log,
  updateAlert_log,
} from "@/api/alert_log/alert_log";
import { listHospitalId } from "@/api/hospital/hospital";
import * as echarts from "@/views/ECGScreen/detail/echarts.min";

export default {
  name: "Alert_log_single",
  dicts: ["sex", "if_status", "hospital_name_list"],
  data() {
    return {
      currentScrollPos: 0,
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
      // 预警日志表格数据
      alert_logList: [],
      // 弹出层标题
      title: "",
      options: [],
      // 是否显示弹出层
      open: false,
      // 时间范围
      daterangeLogTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        logId: null,
        logTime: null,
        logType: null,
        eventName: null,
        eventDescription: null,
        pId: null,
        patientName: null,
        hospitalName: null,
        anoStatus: null,
        ecgType: "single",
        type: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        logNumber: [
          { required: true, message: "日志号不能为空", trigger: "blur" },
        ],
        pId: [
          { required: true, message: "患者管理编号不能为空", trigger: "blur" },
        ],
      },
      num: 1,









    //   lead: false,
    //   tap: {
    //     P1: false,
    //     P2: false,
    //     P3: false,
    //     R1: false,
    //     R2: false,
    //     R3: false,
    //     T1: false,
    //     T2: false,
    //     T3: false,
    //     //P:false,
    //     // Q:false,
    //     // R:false,
    //     // S:false,
    //     // T:false,
    //     // noise:false
    //   },
    //   radio: "",
    //   //通过字典将radio和light一一对应
    //   levellight: {
    //     V1level: "V1light",
    //     V2level: "V2light",
    //     V3level: "V3light",
    //     V4level: "V4light",
    //     V5level: "V5light",
    //     V6level: "V6light",
    //     aVLlevel: "aVLlight",
    //     aVFlevel: "aVFlight",
    //     aVRlevel: "aVRlight",
    //     Ilevel: "Ilight",
    //     IIlevel: "IIlight",
    //     IIIlevel: "IIIlight",
    //   },
    //   lightlevel: {
    //     V1light: "V1level",
    //     V2light: "V2level",
    //     V3light: "V3level",
    //     V4light: "V4level",
    //     V5light: "V5level",
    //     V6light: "V6level",
    //     aVLlight: "aVLlevel",
    //     aVFlight: "aVFlevel",
    //     aVRlight: "aVRlevel",
    //     Ilight: "Ilevel",
    //     IIlight: "IIlevel",
    //     IIIlight: "IIIlevel",
    //   },
    //   noise_list: {
    //     //判断红绿颜色
    //     Ilight: "",
    //     IIlight: "",
    //     IIIlight: "",
    //     aVLlight: "",
    //     aVFlight: "",
    //     aVRlight: "",
    //     V1light: "",
    //     V2light: "",
    //     V3light: "",
    //     V4light: "",
    //     V5light: "",
    //     V6light: "",
    //   },
    //   //ABCD等级的判断
    //   noise_level: {
    //     Ilevel: "",
    //     IIlevel: "",
    //     IIIlevel: "",
    //     aVRlevel: "",
    //     aVLlevel: "",
    //     aVFlevel: "",
    //     V1level: "",
    //     V2level: "",
    //     V3level: "",
    //     V4level: "",
    //     V5level: "",
    //     V6level: "",
    //   },
    //   message: {
    //     devicesn: "",
    //     user_id: "",
    //     pid: "",
    //     logid: "",
    //     sex: "",
    //     age: "",
    //     time: "",
    //     logType: "",
    //   },
    //   query: {
    //     userId: "",
    //     logId: "",
    //     waveLabel: "",
    //   },
    //   value: "正常心电图",
    //   options: [],
    //   timex: [],
    //   state: true,
    //   show: false,
    //   seriesdata: [
    //     { yAxis: -3 },
    //     { yAxis: -2.5 },
    //     { yAxis: -2 },
    //     { yAxis: -1.5 },
    //     { yAxis: -1 },
    //     { yAxis: -0.5 },
    //     { yAxis: 0 },
    //     { yAxis: 0.5 },
    //     { yAxis: 1 },
    //     { yAxis: 1.5 },
    //     { yAxis: 2 },
    //     { yAxis: 2.5 },
    //     { yAxis: 3 },
    //   ],
    //   seriesdata1: [
    //     { yAxis: -1 },
    //     { yAxis: -0.5 },
    //     { yAxis: 0 },
    //     { yAxis: 0.5 },
    //     { yAxis: 1 },
    //   ],
    //   data: {},
    //   chartjump: null,
    //   xIndex: null,
    //   delX: { key: null, value: null }, //想要删除的点
    //   area: [], //想要删除的区间
    //   markArea: [],
    //   title: "",
    //   subData: {
    //     P1: [],
    //     P2: [],
    //     P3: [],
    //     R1: [],
    //     R2: [],
    //     R3: [],
    //     T1: [],
    //     T2: [],
    //     T3: [],
    //   }, //提交标注信息

    //   pointdata: [], //画点的option
    //   point: [[], { P: [], Q: [], R: [], S: [], T: [] }],
    //   arr: [], //要删除的点所在的区间
    //   isSuspected: false,
    //   pageNum: 1,
    //   logUserList: [],
    //   index: 0,
    //   loading: false,
    //   logUserListTotal: 0,
    //   anoStatus: null,
    //   typeObj: {},
    //   pageSize: 10,
    
    };
  },
  // mounted() {
  //   console.log("触发获取图标数据之前");
  //   this.getMessage();
  //   console.log("触发了获取图标数据");
  //   // console.log(this.options);
  // },
  activated() {
    document.documentElement.scrollTop = this.currentScrollPos || 0;
    if (this.num > 1) {
      this.getList();
    }
    this.num++;
  },

  beforeRouteLeave(to, from, next) {
    this.currentScrollPos = document.documentElement.scrollTop || 0;
    next();
  },
  created() {
    if (this.$route.query.pId) {
      this.queryParams.pId = this.$route.query.pId;
    }
    listHospitalId(null).then((r) => {
      this.options = r.rows;
    });
    this.getList();
    console.log("数据帆帆");

    console.log(this.message);
  },
  /*  activated() {
    if (this.$route.query.pId) {
      this.queryParams.pId = this.$route.query.pId;
    }
    this.getList();
  },*/
  methods: {
    /** 查询预警日志列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeLogTime && "" != this.daterangeLogTime) {
        this.queryParams.params["beginLogTime"] = this.daterangeLogTime[0];
        this.queryParams.params["endLogTime"] = this.daterangeLogTime[1];
      }
      console.log("单导预警获得患者数据所需要的值");
      console.log(this.queryParams);
      listAlert_log(this.queryParams).then((response) => {
        this.alert_logList = response.rows;
        this.total = response.total;
        console.log("单导预警获得的患者列表数据");
        console.log(this.alert_logList);
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
        logId: null,
        logTime: null,
        logType: null,
        eventName: null,
        eventDescription: null,
        pId: null,
        patientName: null,
        hospitalName: null,
        anoStatus: null,
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
      this.daterangeLogTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.logId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加预警日志";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const logId = row.logId || this.ids;
      getAlert_log(logId).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改预警日志";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.logId != null) {
            updateAlert_log(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAlert_log(this.form).then((response) => {
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
      const logIds = row.logId || this.ids;
      this.$modal
        .confirm('是否确认删除预警日志编号为"' + logIds + '"的数据项？')
        .then(function () {
          return delAlert_log(logIds);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        "alert_log/alert_log/export",
        {
          ...this.queryParams,
        },
        `alert_log_${new Date().getTime()}.xlsx`
      );
    },

    /** 查看日志*/
    handleLook(row) {
      console.log(row);
      console.log(this.queryParams);
      // console.log("点击这是查看日志时传的值" + row.logId);

      // this.$router.push({
      //   name: "lookLog",
      //   params: {logId: row.logId, logType: row.logType, userId: 0}
      // });

      // console.log(this.queryParams);
      // console.log("上面是queryParams。。。下面是alert_logList");
      // console.log(this.alert_logList);
      // return
      this.$router.push({
        path: "/lookLog1", // name: "lookLog"
        // query: {logId: row.logId, logType: row.logType, userId: 0,state:2}
        // query: {
        //   logId: row.logId,
        //   logType: row.logType,
        //   userId: 0,
        //   state: 1,
        //   queryParams: this.queryParams,
        // },
        query: {
          logId: row.logId,
          // userId: row.userId,
          userId: 0,
          ecgType: this.queryParams.ecgType,
          pageNum: this.queryParams.pageNum,
          pageSize: this.queryParams.pageSize,
          isSuspected: this.queryParams.isSuspected,
          anoStatus: this.queryParams.anoStatus,
          queryParams: this.queryParams,
          state: 1,
        },
        

        // query: {logId: row.logId, logType: row.logType, userId: 0,state:2,queryParams:this.alert_logList}
      });
    },
    // chufa(){
    //   console.log("111");
    // },
     //获取心电数据
    // getMessage(logid,userid) {
    //   console.log("进到了获取心电数据的函数中");
    //   console.log(document.getElementById("I"));
    //   var Iy = [];
    //   var IIy = [];
    //   var IIIy = [];
    //   var aVLy = [];
    //   var aVFy = [];
    //   var aVRy = [];
    //   var V1y = [];
    //   var V2y = [];
    //   var V3y = [];
    //   var V4y = [];
    //   var V5y = [];
    //   var V6y = [];
    //   var timex = [];
    //   var chartI = echarts.init(document.getElementById("I"));
    //   var chartII = echarts.init(document.getElementById("II"));
    //   var chartIII = echarts.init(document.getElementById("III"));
    //   var chartV1 = echarts.init(document.getElementById("V1"));
    //   var chartV2 = echarts.init(document.getElementById("V2"));
    //   var chartV3 = echarts.init(document.getElementById("V3"));
    //   var chartV4 = echarts.init(document.getElementById("V4"));
    //   var chartV5 = echarts.init(document.getElementById("V5"));
    //   var chartV6 = echarts.init(document.getElementById("V6"));
    //   var chartaVR = echarts.init(document.getElementById("aVR"));
    //   var chartaVL = echarts.init(document.getElementById("aVL"));
    //   var chartaVF = echarts.init(document.getElementById("aVF"));
    //   var _th = this;
    //   console.log("进到了获取心电数据的函数中");
    //   $.ajax({
    //     type: "POST",
    //     url: "https://screen.mindyard.cn:84/getId",
    //     dataType: "json",
    //     contentType: "application/json",
    //     data: JSON.stringify({
    //       log_id:logid,
    //       // user_id: this.alert_logList.user_id,
    //        user_id:userid,
    //     }),
    //     success: function (jsonResult) {
    //       _th.data = jsonResult.result;
    //       console.log(jsonResult);
    //       _th.message.pid = jsonResult.result.patientid;
    //       _th.message.age = Number(jsonResult.result.age).toFixed(0);
    //       _th.message.sex = jsonResult.result.sex;
    //       _th.message.time = jsonResult.result.clockTime;
    //       if (jsonResult.result.isSuspected == 1) {
    //         _th.isSuspected = true;
    //       } else {
    //         _th.isSuspected = false;
    //       }
    //       _th.value = jsonResult.result.logType;
    //       _th.loading = false;
    //       _th.light(jsonResult);
    //       _th.level(jsonResult);
    //       if (_th.message.devicesn != null) {
    //         (function () {
    //           var i;
    //           for (var k = 0; k < 1001; k++) {
    //             timex.push(k / 100 + "秒");
    //           }
    //           _th.timex = timex;
    //           //console.log(timex)
    //           //console.log(jsonResult.result.I.length)
    //           for (i = 0; i < 1000; i++) {
    //             Iy.push(jsonResult.result.I[i]);
    //             IIy.push(jsonResult.result.II[i]);
    //             IIIy.push(jsonResult.result.III[i]);
    //             aVLy.push(jsonResult.result.aVL[i]);
    //             aVRy.push(jsonResult.result.aVR[i]);
    //             aVFy.push(jsonResult.result.aVF[i]);
    //             V1y.push(jsonResult.result.V1[i]);
    //             V2y.push(jsonResult.result.V2[i]);
    //             V3y.push(jsonResult.result.V3[i]);
    //             V4y.push(jsonResult.result.V4[i]);
    //             V5y.push(jsonResult.result.V5[i]);
    //             V6y.push(jsonResult.result.V6[i]);
    //           }
    //           for (var i = 0; i < 2500; i += 20) {
    //             _th.seriesdata.push({ xAxis: i });
    //           }
    //           var seriesdata = _th.seriesdata;
    //           if (jsonResult.result.II.length === 1000) {
    //             for (var i = 0; i < 1000; i += 20) {
    //               _th.seriesdata1.push({ xAxis: i });
    //             }
    //             seriesdata = _th.seriesdata1;
    //           }
    //           chartI.clear();
    //           chartI.setOption({
    //             animation: false,
    //             animationDuration: 9900,
    //             animationEasing: "linear",
    //             animationEasingUpdate: "linear",
    //             animationDurationUpdate: 10000,
    //             animationDelayUpdate: 0,
    //             animationThreshold: 10000,
    //             backgroundColor: "#ffffff",
    //             title: {
    //               text: "I导联",
    //               textStyle: {
    //                 fontSize: 12,
    //                 color: "#000000",
    //               },
    //               left: 30,
    //             },
    //             tooltip: {
    //               trigger: "axis",
    //               axisPointer: {
    //                 type: "cross",
    //               },
    //             },
    //             grid: {
    //               left: 20,
    //               right: 0,
    //               top: 2,
    //               bottom: 2,
    //             },
    //             xAxis: {
    //               type: "category",
    //               boundaryGap: false,
    //               data: timex,
    //               axisTick: {
    //                 show: false,
    //               },
    //               axisLabel: {
    //                 //修改坐标系字体颜色
    //                 show: false,
    //                 interval: 3,
    //                 textStyle: {
    //                   color: "#000000",
    //                 },
    //               },
    //               splitLine: {
    //                 show: true,

    //                 lineStyle: {
    //                   color: "pink",
    //                   width: 0.5, //网格的宽度
    //                   type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
    //                 },
    //               } /*网格线*/,
    //             },
    //             yAxis: {
    //               min: -1,
    //               max: 1,
    //               boundaryGap: true,
    //               interval: 0.1,
    //               // splitNumber: 20,
    //               axisLabel: {
    //                 //修改坐标系字体颜色
    //                 show: false,
    //                 textStyle: {
    //                   color: "#000000",
    //                 },
    //               },
    //               splitLine: {
    //                 lineStyle: {
    //                   color: "pink",
    //                   width: 1, //网格的宽度
    //                   type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
    //                 },
    //                 show: true,
    //               } /*网格线*/,
    //             },
    //             series: {
    //               markLine: {
    //                 animation: false,
    //                 symbol: "none",
    //                 silent: true,
    //                 lineStyle: {
    //                   type: "solid",
    //                   color: "#b33939",
    //                   width: 0.5,
    //                   // opacity: 0.5,
    //                 },
    //                 label: {
    //                   position: "start", // 表现内容展示的位置
    //                   color: "#b33939", // 展示内容颜色
    //                 },
    //                 data: seriesdata,
    //               },
    //               /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
    //               itemStyle: {
    //                 normal: {
    //                   lineStyle: {
    //                     width: 1.5,
    //                     color: "#000000" /*折线的颜色*/,
    //                   },
    //                   color:
    //                     "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/,
    //                 },
    //               },
    //               symbol: "none",
    //               /*去掉小圆点*/
    //               name: "I导联",
    //               type: "line",
    //               data: Iy,
    //               smooth: 0, //显示为平滑的曲线*/
    //             },
    //           });
    //           $(window).resize(function () {
    //             chartI.resize();
    //           });
    //           chartII.clear();
    //           chartII.setOption({
    //             animation: false,
    //             animationDuration: 9900,
    //             animationEasing: "linear",
    //             animationEasingUpdate: "linear",
    //             animationDurationUpdate: 10000,
    //             animationDelayUpdate: 0,
    //             animationThreshold: 10000,
    //             backgroundColor: "#ffffff",
    //             title: {
    //               text: "II导联",
    //               textStyle: {
    //                 fontSize: 12,
    //                 color: "#000000",
    //               },
    //               left: 30,
    //             },
    //             tooltip: {
    //               trigger: "axis",
    //               axisPointer: {
    //                 type: "cross",
    //               },
    //             },
    //             grid: {
    //               left: 20,
    //               right: 0,
    //               top: 2,
    //               bottom: 2,
    //             },

    //             xAxis: {
    //               type: "category",
    //               boundaryGap: false,
    //               data: timex,
    //               axisTick: {
    //                 show: false,
    //               },
    //               axisLabel: {
    //                 //修改坐标系字体颜色
    //                 show: false,
    //                 interval: 3,
    //                 textStyle: {
    //                   color: "#000000",
    //                 },
    //               },
    //               splitLine: {
    //                 show: true,

    //                 lineStyle: {
    //                   color: "pink",
    //                   width: 0.5, //网格的宽度
    //                   type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
    //                 },
    //               } /*网格线*/,
    //             },
    //             yAxis: {
    //               min: -1,
    //               max: 1,
    //               boundaryGap: true,
    //               interval: 0.1,
    //               axisLabel: {
    //                 //修改坐标系字体颜色
    //                 show: false,
    //                 textStyle: {
    //                   color: "#000000",
    //                 },
    //               },
    //               splitLine: {
    //                 lineStyle: {
    //                   color: "pink",
    //                   width: 1, //网格的宽度
    //                   type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
    //                 },
    //                 show: true,
    //               } /*网格线*/,
    //             },
    //             series: {
    //               markLine: {
    //                 animation: false,
    //                 symbol: "none",
    //                 silent: true,
    //                 lineStyle: {
    //                   type: "solid",
    //                   color: "#b33939",
    //                   width: 0.5,
    //                 },
    //                 label: {
    //                   position: "start", // 表现内容展示的位置
    //                   color: "#b33939", // 展示内容颜色
    //                 },
    //                 data: seriesdata,
    //               },
    //               /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
    //               itemStyle: {
    //                 normal: {
    //                   lineStyle: {
    //                     width: 1.5,
    //                     color: "#000000" /*折线的颜色*/,
    //                   },
    //                   color:
    //                     "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/,
    //                 },
    //               },
    //               symbol: "none",
    //               /*去掉小圆点*/
    //               name: "II导联",
    //               type: "line",
    //               data: IIy,
    //               smooth: 0, //显示为平滑的曲线*/
    //             },
    //           });
    //           $(window).resize(function () {
    //             chartII.resize();
    //           });
    //           chartV1.clear();
    //           chartV1.setOption({
    //             animation: false,
    //             animationDuration: 9900,
    //             animationEasing: "linear",
    //             animationEasingUpdate: "linear",
    //             animationDurationUpdate: 10000,
    //             animationDelayUpdate: 0,
    //             animationThreshold: 10000,
    //             backgroundColor: "#ffffff",
    //             title: {
    //               text: "V1导联",
    //               textStyle: {
    //                 fontSize: 12,
    //                 color: "#000000",
    //               },
    //               left: 30,
    //             },
    //             tooltip: {
    //               trigger: "axis",
    //               axisPointer: {
    //                 type: "cross",
    //               },
    //             },
    //             grid: {
    //               left: 20,
    //               right: 0,
    //               top: 2,
    //               bottom: 2,
    //             },
    //             xAxis: {
    //               type: "category",
    //               boundaryGap: false,
    //               data: timex,
    //               axisTick: {
    //                 show: false,
    //               },
    //               axisLabel: {
    //                 //修改坐标系字体颜色
    //                 show: false,
    //                 interval: 3,
    //                 textStyle: {
    //                   color: "#000000",
    //                 },
    //               },
    //               splitLine: {
    //                 show: true,
    //                 lineStyle: {
    //                   color: "pink",
    //                   width: 0.5, //网格的宽度
    //                   type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
    //                 },
    //               } /*网格线*/,
    //             },
    //             yAxis: {
    //               min: -1,
    //               max: 1,
    //               boundaryGap: true,
    //               interval: 0.1,
    //               // splitNumber: 20,
    //               axisLabel: {
    //                 //修改坐标系字体颜色
    //                 show: false,
    //                 textStyle: {
    //                   color: "#000000",
    //                 },
    //               },
    //               splitLine: {
    //                 lineStyle: {
    //                   color: "pink",
    //                   width: 1, //网格的宽度
    //                   type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
    //                 },
    //                 show: true,
    //               } /*网格线*/,
    //             },
    //             series: {
    //               markLine: {
    //                 animation: false,
    //                 symbol: "none",
    //                 silent: true,
    //                 lineStyle: {
    //                   type: "solid",
    //                   color: "#b33939",
    //                   width: 0.5,
    //                   // opacity: 0.5,
    //                 },
    //                 label: {
    //                   position: "start", // 表现内容展示的位置
    //                   color: "#b33939", // 展示内容颜色
    //                 },
    //                 data: seriesdata,
    //               },
    //               /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
    //               itemStyle: {
    //                 normal: {
    //                   lineStyle: {
    //                     width: 1.5,
    //                     color: "#000000" /*折线的颜色*/,
    //                   },
    //                   color:
    //                     "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/,
    //                 },
    //               },
    //               symbol: "none",
    //               /*去掉小圆点*/
    //               name: "V1导联",
    //               type: "line",
    //               data: V1y,
    //               smooth: 0, //显示为平滑的曲线*/
    //             },
    //           });
    //           $(window).resize(function () {
    //             chartV1.resize();
    //           });
    //           chartV2.clear();
    //           chartV2.setOption({
    //             animation: false,
    //             animationDuration: 9900,
    //             animationEasing: "linear",
    //             animationEasingUpdate: "linear",
    //             animationDurationUpdate: 10000,
    //             animationDelayUpdate: 0,
    //             animationThreshold: 10000,
    //             backgroundColor: "#ffffff",
    //             title: {
    //               text: "V2导联",
    //               textStyle: {
    //                 fontSize: 12,
    //                 color: "#000000",
    //               },
    //               left: 30,
    //             },
    //             tooltip: {
    //               trigger: "axis",
    //               axisPointer: {
    //                 type: "cross",
    //               },
    //             },
    //             grid: {
    //               left: 20,
    //               right: 0,
    //               top: 2,
    //               bottom: 2,
    //             },
    //             xAxis: {
    //               type: "category",
    //               boundaryGap: false,
    //               data: timex,
    //               axisTick: {
    //                 show: false,
    //               },
    //               axisLabel: {
    //                 //修改坐标系字体颜色
    //                 show: false,
    //                 interval: 3,
    //                 textStyle: {
    //                   color: "#000000",
    //                 },
    //               },
    //               splitLine: {
    //                 show: true,
    //                 lineStyle: {
    //                   color: "pink",
    //                   width: 0.5, //网格的宽度
    //                   type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
    //                 },
    //               } /*网格线*/,
    //             },
    //             yAxis: {
    //               min: -1,
    //               max: 1,
    //               boundaryGap: true,
    //               interval: 0.1,
    //               axisLabel: {
    //                 //修改坐标系字体颜色
    //                 show: false,
    //                 textStyle: {
    //                   color: "#000000",
    //                 },
    //               },
    //               splitLine: {
    //                 lineStyle: {
    //                   color: "pink",
    //                   width: 1, //网格的宽度
    //                   type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
    //                 },
    //                 show: true,
    //               } /*网格线*/,
    //             },
    //             series: {
    //               markLine: {
    //                 animation: false,
    //                 symbol: "none",
    //                 silent: true,
    //                 lineStyle: {
    //                   type: "solid",
    //                   color: "#b33939",
    //                   width: 0.5,
    //                 },
    //                 label: {
    //                   position: "start", // 表现内容展示的位置
    //                   color: "#b33939", // 展示内容颜色
    //                 },
    //                 data: seriesdata,
    //               },
    //               /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
    //               itemStyle: {
    //                 normal: {
    //                   lineStyle: {
    //                     width: 1.5,
    //                     color: "#000000" /*折线的颜色*/,
    //                   },
    //                   color:
    //                     "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/,
    //                 },
    //               },
    //               symbol: "none",
    //               /*去掉小圆点*/
    //               name: "V2导联",
    //               type: "line",
    //               data: V2y,
    //               smooth: 0, //显示为平滑的曲线*/
    //             },
    //           });
    //           $(window).resize(function () {
    //             chartV2.resize();
    //           });
    //           chartV3.clear();
    //           chartV3.setOption({
    //             animation: false,
    //             animationDuration: 9900,
    //             animationEasing: "linear",
    //             animationEasingUpdate: "linear",
    //             animationDurationUpdate: 10000,
    //             animationDelayUpdate: 0,
    //             animationThreshold: 10000,
    //             backgroundColor: "#ffffff",
    //             title: {
    //               text: "V3导联",
    //               textStyle: {
    //                 fontSize: 12,
    //                 color: "#000000",
    //               },
    //               left: 30,
    //             },
    //             tooltip: {
    //               trigger: "axis",
    //               axisPointer: {
    //                 type: "cross",
    //               },
    //             },
    //             grid: {
    //               left: 20,
    //               right: 0,
    //               top: 2,
    //               bottom: 2,
    //             },
    //             xAxis: {
    //               type: "category",
    //               boundaryGap: false,
    //               data: timex,
    //               axisTick: {
    //                 show: false,
    //               },
    //               axisLabel: {
    //                 //修改坐标系字体颜色
    //                 show: false,
    //                 interval: 3,
    //                 textStyle: {
    //                   color: "#000000",
    //                 },
    //               },
    //               splitLine: {
    //                 show: true,
    //                 lineStyle: {
    //                   color: "pink",
    //                   width: 0.5, //网格的宽度
    //                   type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
    //                 },
    //               } /*网格线*/,
    //             },
    //             yAxis: {
    //               min: -1,
    //               max: 1,
    //               boundaryGap: true,
    //               interval: 0.1,
    //               axisLabel: {
    //                 //修改坐标系字体颜色
    //                 show: false,
    //                 textStyle: {
    //                   color: "#000000",
    //                 },
    //               },
    //               splitLine: {
    //                 lineStyle: {
    //                   color: "pink",
    //                   width: 1, //网格的宽度
    //                   type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
    //                 },
    //                 show: true,
    //               } /*网格线*/,
    //             },
    //             series: {
    //               markLine: {
    //                 animation: false,
    //                 symbol: "none",
    //                 silent: true,
    //                 lineStyle: {
    //                   type: "solid",
    //                   color: "#b33939",
    //                   width: 0.5,
    //                 },
    //                 label: {
    //                   position: "start", // 表现内容展示的位置
    //                   color: "#b33939", // 展示内容颜色
    //                 },
    //                 data: seriesdata,
    //               },
    //               /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
    //               itemStyle: {
    //                 normal: {
    //                   lineStyle: {
    //                     width: 1.5,
    //                     color: "#000000" /*折线的颜色*/,
    //                   },
    //                   color:
    //                     "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/,
    //                 },
    //               },
    //               symbol: "none",
    //               /*去掉小圆点*/
    //               name: "V3导联",
    //               type: "line",
    //               data: V3y,
    //               smooth: 0, //显示为平滑的曲线*/
    //             },
    //           });
    //           $(window).resize(function () {
    //             chartV3.resize();
    //           });
    //           chartV4.clear();
    //           chartV4.setOption({
    //             animation: false,
    //             animationDuration: 9900,
    //             animationEasing: "linear",
    //             animationEasingUpdate: "linear",
    //             animationDurationUpdate: 10000,
    //             animationDelayUpdate: 0,
    //             animationThreshold: 10000,
    //             backgroundColor: "#ffffff",
    //             title: {
    //               text: "V4导联",
    //               textStyle: {
    //                 fontSize: 12,
    //                 color: "#000000",
    //               },
    //               left: 30,
    //             },
    //             tooltip: {
    //               trigger: "axis",
    //               axisPointer: {
    //                 type: "cross",
    //               },
    //             },
    //             grid: {
    //               left: 20,
    //               right: 0,
    //               top: 2,
    //               bottom: 2,
    //             },

    //             xAxis: {
    //               type: "category",
    //               boundaryGap: false,
    //               data: timex,
    //               axisTick: {
    //                 show: false,
    //               },
    //               axisLabel: {
    //                 //修改坐标系字体颜色
    //                 show: false,
    //                 interval: 3,
    //                 textStyle: {
    //                   color: "#000000",
    //                 },
    //               },
    //               splitLine: {
    //                 show: true,
    //                 lineStyle: {
    //                   color: "pink",
    //                   width: 0.5, //网格的宽度
    //                   type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
    //                 },
    //               } /*网格线*/,
    //             },
    //             yAxis: {
    //               min: -1,
    //               max: 1,
    //               boundaryGap: true,
    //               interval: 0.1,
    //               // splitNumber: 20,
    //               axisLabel: {
    //                 //修改坐标系字体颜色
    //                 show: false,
    //                 textStyle: {
    //                   color: "#000000",
    //                 },
    //               },
    //               splitLine: {
    //                 lineStyle: {
    //                   color: "pink",
    //                   width: 1, //网格的宽度
    //                   type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
    //                 },
    //                 show: true,
    //               } /*网格线*/,
    //             },
    //             series: {
    //               markLine: {
    //                 animation: false,
    //                 symbol: "none",
    //                 silent: true,
    //                 lineStyle: {
    //                   type: "solid",
    //                   color: "#b33939",
    //                   width: 0.5,
    //                 },
    //                 label: {
    //                   position: "start", // 表现内容展示的位置
    //                   color: "#b33939", // 展示内容颜色
    //                 },
    //                 data: seriesdata,
    //               },
    //               /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
    //               itemStyle: {
    //                 normal: {
    //                   lineStyle: {
    //                     width: 1.5,
    //                     color: "#000000" /*折线的颜色*/,
    //                   },
    //                   color:
    //                     "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/,
    //                 },
    //               },
    //               symbol: "none",
    //               /*去掉小圆点*/
    //               name: "V4导联",
    //               type: "line",
    //               data: V4y,
    //               smooth: 0, //显示为平滑的曲线*/
    //             },
    //           });
    //           $(window).resize(function () {
    //             chartV4.resize();
    //           });
    //           chartV5.clear();
    //           chartV5.setOption({
    //             animation: false,
    //             animationDuration: 9900,
    //             animationEasing: "linear",
    //             animationEasingUpdate: "linear",
    //             animationDurationUpdate: 10000,
    //             animationDelayUpdate: 0,
    //             animationThreshold: 10000,
    //             backgroundColor: "#ffffff",
    //             title: {
    //               text: "V5导联",
    //               textStyle: {
    //                 fontSize: 12,
    //                 color: "#000000",
    //               },
    //               left: 30,
    //             },
    //             tooltip: {
    //               trigger: "axis",
    //               axisPointer: {
    //                 type: "cross",
    //               },
    //             },
    //             grid: {
    //               left: 20,
    //               right: 0,
    //               top: 2,
    //               bottom: 2,
    //             },
    //             xAxis: {
    //               type: "category",
    //               boundaryGap: false,
    //               data: timex,
    //               axisTick: {
    //                 show: false,
    //               },
    //               axisLabel: {
    //                 //修改坐标系字体颜色
    //                 show: false,
    //                 interval: 3,
    //                 textStyle: {
    //                   color: "#000000",
    //                 },
    //               },
    //               splitLine: {
    //                 show: true,
    //                 lineStyle: {
    //                   color: "pink",
    //                   width: 0.5, //网格的宽度
    //                   type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
    //                 },
    //               } /*网格线*/,
    //             },
    //             yAxis: {
    //               min: -1,
    //               max: 1,
    //               boundaryGap: true,
    //               interval: 0.1,
    //               axisLabel: {
    //                 //修改坐标系字体颜色
    //                 show: false,
    //                 textStyle: {
    //                   color: "#000000",
    //                 },
    //               },
    //               splitLine: {
    //                 lineStyle: {
    //                   color: "pink",
    //                   width: 1, //网格的宽度
    //                   type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
    //                 },
    //                 show: true,
    //               } /*网格线*/,
    //             },
    //             series: {
    //               markLine: {
    //                 animation: false,
    //                 symbol: "none",
    //                 silent: true,
    //                 lineStyle: {
    //                   type: "solid",
    //                   color: "#b33939",
    //                   width: 0.5,
    //                 },
    //                 label: {
    //                   position: "start", // 表现内容展示的位置
    //                   color: "#b33939", // 展示内容颜色
    //                 },
    //                 data: seriesdata,
    //               },
    //               /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
    //               itemStyle: {
    //                 normal: {
    //                   lineStyle: {
    //                     width: 1.5,
    //                     color: "#000000" /*折线的颜色*/,
    //                   },
    //                   color:
    //                     "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/,
    //                 },
    //               },
    //               symbol: "none",
    //               /*去掉小圆点*/
    //               name: "V5导联",
    //               type: "line",
    //               data: V5y,
    //               smooth: 0, //显示为平滑的曲线*/
    //             },
    //           });
    //           $(window).resize(function () {
    //             chartV5.resize();
    //           });
    //           chartV6.clear();
    //           chartV6.setOption({
    //             animation: false,
    //             animationDuration: 9900,
    //             animationEasing: "linear",
    //             animationEasingUpdate: "linear",
    //             animationDurationUpdate: 10000,
    //             animationDelayUpdate: 0,
    //             animationThreshold: 10000,
    //             backgroundColor: "#ffffff",
    //             title: {
    //               text: "V6导联",
    //               textStyle: {
    //                 fontSize: 12,
    //                 color: "#000000",
    //               },
    //               left: 30,
    //             },
    //             tooltip: {
    //               trigger: "axis",
    //               axisPointer: {
    //                 type: "cross",
    //               },
    //             },
    //             grid: {
    //               left: 20,
    //               right: 0,
    //               top: 2,
    //               bottom: 2,
    //             },
    //             xAxis: {
    //               type: "category",
    //               boundaryGap: false,
    //               data: timex,
    //               axisTick: {
    //                 show: false,
    //               },
    //               axisLabel: {
    //                 //修改坐标系字体颜色
    //                 show: true,
    //                 interval: 3,
    //                 textStyle: {
    //                   color: "#000000",
    //                 },
    //               },
    //               splitLine: {
    //                 show: true,
    //                 lineStyle: {
    //                   color: "pink",
    //                   width: 0.5, //网格的宽度
    //                   type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
    //                 },
    //               } /*网格线*/,
    //             },
    //             yAxis: {
    //               min: -1,
    //               max: 1,
    //               boundaryGap: true,
    //               interval: 0.1,
    //               axisLabel: {
    //                 //修改坐标系字体颜色
    //                 show: false,
    //                 textStyle: {
    //                   color: "#000000",
    //                 },
    //               },
    //               splitLine: {
    //                 lineStyle: {
    //                   color: "pink",
    //                   width: 1, //网格的宽度
    //                   type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
    //                 },
    //                 show: true,
    //               } /*网格线*/,
    //             },
    //             series: [
    //               {
    //                 markLine: {
    //                   animation: false,
    //                   symbol: "none",
    //                   silent: true,
    //                   lineStyle: {
    //                     type: "solid",
    //                     color: "#b33939",
    //                     width: 0.5,
    //                   },
    //                   label: {
    //                     position: "start", // 表现内容展示的位置
    //                     color: "#b33939", // 展示内容颜色
    //                   },
    //                   data: seriesdata,
    //                 },
    //                 /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
    //                 itemStyle: {
    //                   normal: {
    //                     lineStyle: {
    //                       width: 1.5,
    //                       color: "#000000" /*折线的颜色*/,
    //                     },
    //                     color:
    //                       "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/,
    //                   },
    //                 },
    //                 symbol: "none",
    //                 /*去掉小圆点*/
    //                 name: "当前电位",
    //                 type: "line",
    //                 data: V6y,
    //                 smooth: 0, //显示为平滑的曲线*/
    //               },
    //             ],
    //           });
    //           $(window).resize(function () {
    //             chartV6.resize();
    //           });
    //           chartIII.clear();
    //           chartIII.setOption({
    //             animation: false,
    //             animationDuration: 9900,
    //             animationEasing: "linear",
    //             animationEasingUpdate: "linear",
    //             animationDurationUpdate: 10000,
    //             animationDelayUpdate: 0,
    //             animationThreshold: 10000,
    //             backgroundColor: "#ffffff",
    //             title: {
    //               text: "III导联",
    //               textStyle: {
    //                 fontSize: 12,
    //                 color: "#000000",
    //               },
    //               left: 30,
    //             },
    //             tooltip: {
    //               trigger: "axis",
    //               axisPointer: {
    //                 type: "cross",
    //               },
    //             },
    //             grid: {
    //               left: 20,
    //               right: 0,
    //               top: 2,
    //               bottom: 2,
    //             },

    //             xAxis: {
    //               type: "category",
    //               boundaryGap: false,
    //               data: timex,
    //               axisTick: {
    //                 show: false,
    //               },
    //               axisLabel: {
    //                 //修改坐标系字体颜色
    //                 show: false,
    //                 interval: 3,
    //                 textStyle: {
    //                   color: "#000000",
    //                 },
    //               },
    //               splitLine: {
    //                 show: true,
    //                 lineStyle: {
    //                   color: "pink",
    //                   width: 0.5, //网格的宽度
    //                   type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
    //                 },
    //               } /*网格线*/,
    //             },
    //             yAxis: {
    //               min: -1,
    //               max: 1,
    //               boundaryGap: true,
    //               interval: 0.1,
    //               // splitNumber: 20,
    //               axisLabel: {
    //                 //修改坐标系字体颜色
    //                 show: false,
    //                 textStyle: {
    //                   color: "#000000",
    //                 },
    //               },
    //               splitLine: {
    //                 lineStyle: {
    //                   color: "pink",
    //                   width: 1, //网格的宽度
    //                   type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
    //                 },
    //                 show: true,
    //               } /*网格线*/,
    //             },
    //             series: {
    //               markLine: {
    //                 animation: false,
    //                 symbol: "none",
    //                 silent: true,
    //                 lineStyle: {
    //                   type: "solid",
    //                   color: "#b33939",
    //                   width: 0.5,
    //                 },
    //                 label: {
    //                   position: "start", // 表现内容展示的位置
    //                   color: "#b33939", // 展示内容颜色
    //                 },
    //                 data: seriesdata,
    //               },
    //               /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
    //               itemStyle: {
    //                 normal: {
    //                   lineStyle: {
    //                     width: 1.5,
    //                     color: "#000000" /*折线的颜色*/,
    //                   },
    //                   color:
    //                     "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/,
    //                 },
    //               },
    //               symbol: "none",
    //               /*去掉小圆点*/
    //               name: "III导联",
    //               type: "line",
    //               data: IIIy,
    //               smooth: 0, //显示为平滑的曲线*/
    //             },
    //           });
    //           $(window).resize(function () {
    //             chartIII.resize();
    //           });
    //           chartaVL.clear();
    //           chartaVL.setOption({
    //             animation: false,
    //             animationDuration: 9900,
    //             animationEasing: "linear",
    //             animationEasingUpdate: "linear",
    //             animationDurationUpdate: 10000,
    //             animationDelayUpdate: 0,
    //             animationThreshold: 10000,
    //             backgroundColor: "#ffffff",
    //             title: {
    //               text: "aVL导联",
    //               textStyle: {
    //                 fontSize: 12,
    //                 color: "#000000",
    //               },
    //               left: 30,
    //             },
    //             tooltip: {
    //               trigger: "axis",
    //               axisPointer: {
    //                 type: "cross",
    //               },
    //             },
    //             grid: {
    //               left: 20,
    //               right: 0,
    //               top: 2,
    //               bottom: 2,
    //             },
    //             xAxis: {
    //               type: "category",
    //               boundaryGap: false,
    //               data: timex,
    //               axisTick: {
    //                 show: false,
    //               },
    //               axisLabel: {
    //                 //修改坐标系字体颜色
    //                 show: false,
    //                 interval: 3,
    //                 textStyle: {
    //                   color: "#000000",
    //                 },
    //               },
    //               splitLine: {
    //                 show: true,
    //                 lineStyle: {
    //                   color: "pink",
    //                   width: 0.5, //网格的宽度
    //                   type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
    //                 },
    //               } /*网格线*/,
    //             },
    //             yAxis: {
    //               min: -1,
    //               max: 1,
    //               boundaryGap: true,
    //               interval: 0.1,
    //               axisLabel: {
    //                 //修改坐标系字体颜色
    //                 show: false,
    //                 textStyle: {
    //                   color: "#000000",
    //                 },
    //               },
    //               splitLine: {
    //                 lineStyle: {
    //                   color: "pink",
    //                   width: 1, //网格的宽度
    //                   type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
    //                 },
    //                 show: true,
    //               } /*网格线*/,
    //             },
    //             series: {
    //               markLine: {
    //                 animation: false,
    //                 symbol: "none",
    //                 silent: true,
    //                 lineStyle: {
    //                   type: "solid",
    //                   color: "#b33939",
    //                   width: 0.5,
    //                 },
    //                 label: {
    //                   position: "start", // 表现内容展示的位置
    //                   color: "#b33939", // 展示内容颜色
    //                 },
    //                 data: seriesdata,
    //               },
    //               /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
    //               itemStyle: {
    //                 normal: {
    //                   lineStyle: {
    //                     width: 1.5,
    //                     color: "#000000" /*折线的颜色*/,
    //                   },
    //                   color:
    //                     "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/,
    //                 },
    //               },
    //               symbol: "none",
    //               /*去掉小圆点*/
    //               name: "aVL导联",
    //               type: "line",
    //               data: aVLy,
    //               smooth: 0, //显示为平滑的曲线*/
    //             },
    //           });
    //           $(window).resize(function () {
    //             chartaVL.resize();
    //           });
    //           chartaVF.clear();
    //           chartaVF.setOption({
    //             animation: false,
    //             animationDuration: 9900,
    //             animationEasing: "linear",
    //             animationEasingUpdate: "linear",
    //             animationDurationUpdate: 10000,
    //             animationDelayUpdate: 0,
    //             animationThreshold: 10000,
    //             backgroundColor: "#ffffff",
    //             title: {
    //               text: "aVF导联",
    //               textStyle: {
    //                 fontSize: 12,
    //                 color: "#000000",
    //               },
    //               left: 30,
    //             },
    //             tooltip: {
    //               trigger: "axis",
    //               axisPointer: {
    //                 type: "cross",
    //               },
    //             },
    //             grid: {
    //               left: 20,
    //               right: 0,
    //               top: 2,
    //               bottom: 2,
    //             },
    //             xAxis: {
    //               type: "category",
    //               boundaryGap: false,
    //               data: timex,
    //               axisTick: {
    //                 show: false,
    //               },
    //               axisLabel: {
    //                 //修改坐标系字体颜色
    //                 show: false,
    //                 interval: 3,
    //                 textStyle: {
    //                   color: "#000000",
    //                 },
    //               },
    //               splitLine: {
    //                 show: true,
    //                 lineStyle: {
    //                   color: "pink",
    //                   width: 0.5, //网格的宽度
    //                   type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
    //                 },
    //               } /*网格线*/,
    //             },
    //             yAxis: {
    //               min: -1,
    //               max: 1,
    //               boundaryGap: true,
    //               interval: 0.1,
    //               axisLabel: {
    //                 //修改坐标系字体颜色
    //                 show: false,
    //                 textStyle: {
    //                   color: "#000000",
    //                 },
    //               },
    //               splitLine: {
    //                 lineStyle: {
    //                   color: "pink",
    //                   width: 1, //网格的宽度
    //                   type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
    //                 },
    //                 show: true,
    //               } /*网格线*/,
    //             },
    //             series: {
    //               markLine: {
    //                 animation: false,
    //                 symbol: "none",
    //                 silent: true,
    //                 lineStyle: {
    //                   type: "solid",
    //                   color: "#b33939",
    //                   width: 0.5,
    //                 },
    //                 label: {
    //                   position: "start", // 表现内容展示的位置
    //                   color: "#b33939", // 展示内容颜色
    //                 },
    //                 data: seriesdata,
    //               },
    //               /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
    //               itemStyle: {
    //                 normal: {
    //                   lineStyle: {
    //                     width: 1.5,
    //                     color: "#000000" /*折线的颜色*/,
    //                   },
    //                   color:
    //                     "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/,
    //                 },
    //               },
    //               symbol: "none",
    //               /*去掉小圆点*/
    //               name: "aVF导联",
    //               type: "line",
    //               data: aVFy,
    //               smooth: 0, //显示为平滑的曲线*/
    //             },
    //           });
    //           $(window).resize(function () {
    //             chartaVF.resize();
    //           });
    //           chartaVR.clear();
    //           chartaVR.setOption({
    //             animation: false,
    //             animationDuration: 9900,
    //             animationEasing: "linear",
    //             animationEasingUpdate: "linear",
    //             animationDurationUpdate: 10000,
    //             animationDelayUpdate: 0,
    //             animationThreshold: 10000,
    //             backgroundColor: "#ffffff",
    //             title: {
    //               text: "aVR导联",
    //               textStyle: {
    //                 fontSize: 12,
    //                 color: "#000000",
    //               },
    //               left: 30,
    //             },
    //             tooltip: {
    //               trigger: "axis",
    //               axisPointer: {
    //                 type: "cross",
    //               },
    //             },
    //             grid: {
    //               left: 20,
    //               right: 0,
    //               top: 2,
    //               bottom: 2,
    //             },
    //             xAxis: {
    //               type: "category",
    //               boundaryGap: false,
    //               data: timex,
    //               axisTick: {
    //                 show: false,
    //               },
    //               axisLabel: {
    //                 //修改坐标系字体颜色
    //                 show: false,
    //                 interval: 3,
    //                 textStyle: {
    //                   color: "#000000",
    //                 },
    //               },
    //               splitLine: {
    //                 show: true,
    //                 lineStyle: {
    //                   color: "pink",
    //                   width: 0.5, //网格的宽度
    //                   type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
    //                 },
    //               } /*网格线*/,
    //             },
    //             yAxis: {
    //               min: -1,
    //               max: 1,
    //               boundaryGap: true,
    //               interval: 0.1,
    //               // splitNumber: 20,
    //               axisLabel: {
    //                 //修改坐标系字体颜色
    //                 show: false,
    //                 textStyle: {
    //                   color: "#000000",
    //                 },
    //               },
    //               splitLine: {
    //                 lineStyle: {
    //                   color: "pink",
    //                   width: 1, //网格的宽度
    //                   type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
    //                 },
    //                 show: true,
    //               } /*网格线*/,
    //             },
    //             series: {
    //               markLine: {
    //                 animation: false,
    //                 symbol: "none",
    //                 silent: true,
    //                 lineStyle: {
    //                   type: "solid",
    //                   color: "#b33939",
    //                   width: 0.5,
    //                 },
    //                 label: {
    //                   position: "start", // 表现内容展示的位置
    //                   color: "#b33939", // 展示内容颜色
    //                 },
    //                 data: seriesdata,
    //               },
    //               /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
    //               itemStyle: {
    //                 normal: {
    //                   lineStyle: {
    //                     width: 1.5,
    //                     color: "#000000" /*折线的颜色*/,
    //                   },
    //                   color:
    //                     "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/,
    //                 },
    //               },
    //               symbol: "none",
    //               /*去掉小圆点*/
    //               name: "aVR导联",
    //               type: "line",
    //               data: aVRy,
    //               smooth: 0, //显示为平滑的曲线*/
    //             },
    //           });
    //           $(window).resize(function () {
    //             chartaVR.resize();
    //           });
    //         })();
    //       } else {
    //         _th.$modal.msgError("设备未连接");
    //       }
    //       console.log(jsonResult.result);
    //     },
    //     error: function (data) {
    //       console.log(data);
    //       _th.$modal.msgError("数据获取失败");
    //     },
    //   });
    // },
  },
};
</script>
<style lang="scss" scoped>
  .echarts{
    height: 500px;
    width: 500px;
  }
 .echarts .container {
  width: 100;
  height: 100;
  //border: 1px solid rgba(25, 186, 139, 0.17);
  position: relative;
  .btn {
    background-color: #91dff8;
  }
  .btn:hover {
    background-color: #3ec9f7;
  }
}

.echarts .container .chart {
  height: 5vh;
  width: 99;
  //background-color: #ffffff;
}

</style>
