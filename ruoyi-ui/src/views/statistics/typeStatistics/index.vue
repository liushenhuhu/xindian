<template>
  <div>
  <el-row>
    <el-col :span="12">
        <div id="myChart1" style="width: 100%;height: 400px"> </div>
    </el-col>
    <el-col :span="12">
      <div id="myChart2" :style="{width: '75%', height: '350%'}"> </div>
    </el-col>
  </el-row>
  <el-row>
    <el-col :span="12">
      <div id="myChart3" :style="{width: '75%', height: '350%'}"> </div>
    </el-col>
    <el-col :span="12">
      <div id="myChart4" :style="{width: '75%', height: '350%'}"> </div>
    </el-col>
  </el-row>
  </div>
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
    this.drawLine1();
  },
  methods: {
    drawLine1() {
      // 基于准备好的dom，初始化echarts实例
      let myChart = this.$echarts.init(document.getElementById('myChart1'))
      // 绘制图表
      let status = this;
      myChart.off('click');
      myChart.setOption({
        title: {
          text: 'JECGsingle'
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
        status.queryParams.month = params.name;
        status.show = false;
        document.getElementById("myChart").style.display='none';
        document.getElementById("table1").style.display='';
        status.getListData();
      })
    },
    drawLine2() {
      // 基于准备好的dom，初始化echarts实例
      let myChart = this.$echarts.init(document.getElementById('myChart2'))
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
        status.queryParams.month = params.name;
        status.show = false;
        document.getElementById("myChart").style.display='none';
        document.getElementById("table1").style.display='';
        status.getListData();
      })
    },
    drawLine3() {
      // 基于准备好的dom，初始化echarts实例
      let myChart = this.$echarts.init(document.getElementById('myChart3'))
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
        status.queryParams.month = params.name;
        status.show = false;
        document.getElementById("myChart").style.display='none';
        document.getElementById("table1").style.display='';
        status.getListData();
      })
    },
    drawLine4() {
      // 基于准备好的dom，初始化echarts实例
      let myChart = this.$echarts.init(document.getElementById('myChart4'))
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
        this.drawLine1();
        this.drawLine2();
        this.drawLine3();
        this.drawLine4();
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
