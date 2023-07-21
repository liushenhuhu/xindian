<template>
  <div style="width: 100%;height: 100%">
    <label>心电类型统计图</label>
  <el-row>
    <el-col :span="12">
        <div id="myChart1" style="width: 100%;height: 520%;margin-left: 25%;margin-top: 10%"> </div>
    </el-col>
<!--    <el-col :span="12">
      <div id="myChart2" style="width: 100%;height: 400%"> </div>
      <label style="margin-left: 70%">JECG12</label>
    </el-col>-->
  </el-row>
<!--  <el-row style="margin-right: 15%">
    <el-col :span="12">
      <div id="myChart3" style="width: 100%;height: 400%"> </div>
      <label style="margin-left: 70%">DECGsingle</label>
    </el-col>
    <el-col :span="12">
      <div id="myChart4" style="width: 100%;height: 400%"> </div>
      <label style="margin-left: 70%">DECG12</label>
    </el-col>
  </el-row>-->
  </div>
</template>
<script>
import { listTypeStatistics } from "@/api/statistics/typeStatistics";
export default {
  name: 'hello',
  data () {
    return {
      showSearch: true,
      statistics: [],
      options: [],
      arr: [],
      xArr: [],
      yArr: [],
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
    }
  },
  created() {
    this.getList();
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
        /*title: {
          text: '心电类型统计图'
        },*/
        tooltip: {},
        xAxis: {
          data: this.xArr,
          axisLabel:{
            interval: 0,
            rotate : 40,
          }
        },
        yAxis: {},
        series: [{
          name: '数量',
          type: 'bar',
          data: this.yArr
        }],
        grid: {
          bottom: '30%',
          top: '10%',
          right: 0,
          left: '40%',
        },
      });
    },
    /** 查询 */
    getList() {
      this.loading = true;
      listTypeStatistics(this.queryParams).then(response => {
        //console.log(response);
        this.arr = response;
        this.arr.forEach(item => {
            if(item.reportType === 'JECGsingleGZ' || item.reportType === 'JECG12YXD' || item.reportType === 'JECGsingleWL' || item.reportType === 'JECG12SX'){
              this.xArr.push(item.reportType)
              this.yArr.push(item.count);
            }
        })
        this.drawLine1();
        /*this.drawLine2();
        this.drawLine3();
        this.drawLine4();
        this.loading = false;*/
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
