<template>
  <el-row>
    <el-col :span="24">
    <div id="appc" class="app-container">
      <el-form v-if="show" id="add1" :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
        <el-form-item label="医生名称" prop="doctor_name">
          <el-select v-model="queryParams.doctorPhone" clearable placeholder="请选择">
            <el-option
              v-for="item in options"
              :label="item.doctorName"
              :value="item.doctorPhone">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="心电图类型" prop="report_type" label-width="100px">
          <el-select v-model="queryParams.reportType" clearable placeholder="请选择">
            <el-option label="JECGsingle" value="JECGsingle"></el-option>
            <el-option label="JECG12" value="JECG12"></el-option>
            <el-option label="DECGsingle" value="DECGsingle"></el-option>
            <el-option label="DECG12" value="DECG12"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        </el-form-item>
      </el-form>
      <div id="myChart" :style="{width: '80%', height: '500%'}"> </div>
      <div id="table1" style="align-content: center;display: none">
        <el-button type="primary" icon="el-icon-back" size="mini" @click="backQuery">返回</el-button>
        <el-table
          :data="tableData"
          style="width: 100%;text-align: center">
          <el-table-column label="医生姓名" align="center" prop="doctorName" />
          <el-table-column label="诊断月份" align="center" prop="month" />
          <el-table-column label="诊断时长" align="center" prop="timeCount" />
        </el-table>
        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getListData"
        />
      </div>
    </div>
    </el-col>
  </el-row>
</template>
<script>
import { listStatistics, selectDoctor, dateList } from "@/api/statistics/statistics";
export default {
  name: 'hello',
  data () {
    return {
      showSearch: true,
      statistics: [],
      options: [],
      countArr: [],
      show: true,
      // 总条数
      total: 0,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        doctorName: null,
        doctorPhone: null,
        month: null,
        reportType:null,
      },
      tableData: []
    }
  },
  created() {
    this.getList();
    this.selectDoctor();
  },
  mounted(){
    this.drawLine();
  },
  methods: {
    drawLine() {
      // 基于准备好的dom，初始化echarts实例
      let myChart = this.$echarts.init(document.getElementById('myChart'))
      // 绘制图表
      let status = this;
      myChart.off('click');
      myChart.setOption({
        title: {
          text: ''
        },
        tooltip: {},
        xAxis: {
          data: ["一月", "二月", "三月", "四月", "五月",
            "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"]
        },
        yAxis: {},
        series: [{
          name: '诊断次数',
          type: 'bar',
          data: this.countArr
        }],
        grid: {
          bottom: '10%',
          top: '25%',
          right: 0,
          left: '40%',
        },
      });
      myChart.on('click', function (params) {
        console.log(params)
        status.queryParams.month = params.name;
        status.show = false;
        document.getElementById("myChart").style.display='none';
        document.getElementById("table1").style.display='';
        status.getListData();
      })
    },
    selectDoctor() {
      selectDoctor().then(response => {
        this.options = response;
      })
    },
    getListData(){
      dateList(this.queryParams).then(response => {
        //console.log(response.rows);
        this.tableData = response.rows;
        this.total = response.total;
      })
    },
    /** 查询 */
    getList() {
      this.loading = true;
      listStatistics(this.queryParams).then(response => {
        //console.log(response.rows);
        let data = response.rows;
        let countArr = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
        for (let j = 0; j < data.length; j++) {
          countArr[data[j].month - 1] = parseInt(data[j].count);
        }
        this.countArr = countArr;
        this.drawLine();
        //console.log(countArr);
        this.loading = false;
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    backQuery(){
      this.show = true;
      document.getElementById("myChart").style.display='';
      document.getElementById("table1").style.display='none';
    }
  },
}
</script>
