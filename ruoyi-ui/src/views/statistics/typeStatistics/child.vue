<template>
        <div id="myChart1" style="width: 25vw;height: 500px;margin-top: 7%"> </div>
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
        tooltip: {},
        xAxis: {
          data: this.xArr,
          axisLabel:{
            interval: 0,
            rotate : 50,
          }
        },
        yAxis: {},
        series: [{
          //name: '数量',
          type: 'bar',
          data: this.yArr,
          itemStyle: {        //上方显示数值
            normal: {
              barBorderRadius: [5, 5, 0, 0],
              color:'#c76e4a',
              label: {
                show: true, //开启显示
                position: 'top', //在上方显示
                textStyle: { //数值样式
                  color: 'black',
                  fontSize: 16
                }
              }
            }
          }
        }],
        grid: {
          bottom: '35%',
          top: '10%',
          right: '10%',
          left: '12%',
          //height: '100%'
        },
      });
      window.addEventListener("resize", function () {
        myChart.resize();
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
<style>
</style>
