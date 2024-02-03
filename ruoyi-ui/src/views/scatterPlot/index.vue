<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="患者手机号" prop="patientPhone" label-width="80">
        <el-input
          v-model="queryParams.patientPhone"
          placeholder="请输入患者手机号"
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
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getData"></right-toolbar>
    </el-row>
    <div class="main-flex" id="main">
      <div class="row">
        <el-card>
          <div class="chart" id="chart1" style="width: 100%; height:100%"></div>
        </el-card>
        <el-card>
          <div class="chart" id="chart2" ></div>
        </el-card>
      </div>
      <div class="row">
        <el-card>
          <div class="chart" id="chart3"></div>
        </el-card>
        <el-card>
          <div class="chart" id="chart4"></div>
        </el-card>
      </div>
      <div class="row">
        <el-card>
          <div class="chart" id="chart5"></div>
        </el-card>
        <el-card>
          <div class="chart" id="chart6"></div>
        </el-card>
      </div>
    </div>
  </div>
</template>

<script>
import * as echarts from "@/views/ECGScreen/detail/echarts.min"
import {getHrCount} from "@/api/scatterPlot/scatterPlot";

export default {
  name: "",
  data() {
    return {
      queryParams:{
        patientPhone:13856850666,
        startTime:null,
        endTime:null
      },
      showSearch:true,
      chart1:null,
      chart2:null,
      chart3:null,
      chart4:null,
      chart5:null,
      chart6:null,
    }
  },
  created() {
  },
  mounted() {
    this.chart1 = echarts.init(document.getElementById('chart1'));
    this.chart2 = echarts.init(document.getElementById('chart2'));
    this.chart3 = echarts.init(document.getElementById('chart3'));
    this.chart4 = echarts.init(document.getElementById('chart4'));
    this.chart5 = echarts.init(document.getElementById('chart5'));
    this.chart6 = echarts.init(document.getElementById('chart6'));
    window.addEventListener("resize", () => {
      this.chart1.resize()
      this.chart2.resize()
      this.chart3.resize()
      this.chart4.resize()
      this.chart5.resize()
      this.chart6.resize()
    });
    // 获取目标元素
    const resizeBox = document.getElementById('main');

    // 创建 ResizeObserver 实例
    const resizeObserver = new ResizeObserver(entries => {
      for (let entry of entries) {
        // const {width, height} = entry.contentRect;
        // console.log(`尺寸变化：宽度=${width}, 高度=${height}`);
        // 在此处根据尺寸变化执行操作
        setTimeout(()=>{
          this.chart1.resize()
          this.chart2.resize()
          this.chart3.resize()
          this.chart4.resize()
          this.chart5.resize()
          this.chart6.resize()
        },50)
      }
    });
    // 开始观察
    resizeObserver.observe(resizeBox);
    this.queryParams.endTime=this.GetDateStr(0)
    this.queryParams.startTime=this.GetDateStr(-30)
    this.getData()
  },
  methods: {
    getData(){
      getHrCount(this.queryParams).then(res=>{
        this.setChart1(res.data.PR_interval)
        this.setChart2(res.data.P_time)
        this.setChart3(res.data.QRS_interval)
        this.setChart4(res.data.QTc)
        this.setChart5(res.data.RMSSD)
        this.setChart6(res.data.hr_mean)
      })
    },
    GetDateStr(AddDayCount){
      var dd = new Date();
      dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期
      var y = dd.getFullYear();
      var m = (dd.getMonth()+1)<10?"0"+(dd.getMonth()+1):(dd.getMonth()+1);//获取当前月份的日期，不足10补0
      var d = dd.getDate()<10?"0"+dd.getDate():dd.getDate();//获取当前几号，不足10补0
      return y+"-"+m+"-"+d;
    },
    setChart1(data){
      let datax=[]
      let datay=[]
      if(data && data.length!=0){
        data.forEach(item=>{
          datax.push(item[0])
          datay.push(item[1])
        })
      }
      var option = {
        title:{
          text:'PR间期'
        },
        grid:{
          left:'2%',
          top:'16%',
          right:'2%',
          bottom:'2%',
          containLabel:true
        },
        tooltip:{
          show:true
        },
        xAxis: {
          type: 'category',
          data:datax,
          boundaryGap: false
        },
        yAxis: {},
        series: [
          {
            symbolSize: 5,
            data:datay,
            type: 'scatter',
            itemStyle: {
              color: "rgba(224, 67, 67, 1)"
            },
          }
        ]
      };
      this.chart1.clear()
      this.chart1.setOption(option);
      this.chart1.resize()
    },
    setChart2(data){
      let datax=[]
      let datay=[]
      if(data && data.length!=0){
        data.forEach(item=>{
          datax.push(item[0])
          datay.push(item[1])
        })
      }
      var option = {
        title:{
          text:'P波'
        },
        grid:{
          left:'2%',
          top:'16%',
          right:'2%',
          bottom:'2%',
          containLabel:true
        },
        tooltip:{
          show:true
        },
        xAxis: {
          type: 'category',
          data:datax,
          boundaryGap: false
        },
        yAxis: {},
        series: [
          {
            symbolSize: 5,
            data:datay,
            type: 'scatter',
            itemStyle: {
              color: "rgba(224, 67, 67, 1)"
            },
          }
        ]
      };
      this.chart2.clear()
      this.chart2.setOption(option);
      this.chart2.resize()
    },
    setChart3(data){
      let datax=[]
      let datay=[]
      if(data && data.length!=0){
        data.forEach(item=>{
          datax.push(item[0])
          datay.push(item[1])
        })
      }
      var option = {
        title:{
          text:'QRS波群'
        },
        grid:{
          left:'2%',
          top:'16%',
          right:'2%',
          bottom:'2%',
          containLabel:true
        },
        tooltip:{
          show:true
        },
        xAxis: {
          type: 'category',
          data:datax,
          boundaryGap: false
        },
        yAxis: {},
        series: [
          {
            symbolSize: 5,
            data:datay,
            type: 'scatter',
            itemStyle: {
              color: "rgba(224, 67, 67, 1)"
            },
          }
        ]
      };
      this.chart3.clear()
      this.chart3.setOption(option);
      this.chart3.resize()
    },
    setChart4(data){
      let datax=[]
      let datay=[]
      if(data && data.length!=0){
        data.forEach(item=>{
          datax.push(item[0])
          datay.push(item[1])
        })
      }
      var option = {
        title:{
          text:'QTc'
        },
        grid:{
          left:'2%',
          top:'16%',
          right:'2%',
          bottom:'2%',
          containLabel:true
        },
        tooltip:{
          show:true
        },
        xAxis: {
          type: 'category',
          data:datax,
          boundaryGap: false
        },
        yAxis: {},
        series: [
          {
            symbolSize: 5,
            data:datay,
            type: 'scatter',
            itemStyle: {
              color: "rgba(224, 67, 67, 1)"
            },
          }
        ]
      };
      this.chart4.clear()
      this.chart4.setOption(option);
      this.chart4.resize()
    },
    setChart5(data){
      let datax=[]
      let datay=[]
      if(data && data.length!=0){
        data.forEach(item=>{
          datax.push(item[0])
          datay.push(item[1])
        })
      }
      var option = {
        title:{
          text:'HRV'
        },
        grid:{
          left:'2%',
          top:'16%',
          right:'2%',
          bottom:'2%',
          containLabel:true
        },
        tooltip:{
          show:true
        },
        xAxis: {
          type: 'category',
          data:datax,
          boundaryGap: false
        },
        yAxis: {},
        series: [
          {
            symbolSize: 5,
            data:datay,
            type: 'scatter',
            itemStyle: {
              color: "rgba(224, 67, 67, 1)"
            },
          }
        ]
      };
      this.chart5.clear()
      this.chart5.setOption(option);
      this.chart5.resize()
    },
    setChart6(data){
      let datax=[]
      let datay=[]
      if(data && data.length!=0){
        data.forEach(item=>{
          datax.push(item[0])
          datay.push(item[1])
        })
      }
      var option = {
        title:{
          text:'心率'
        },
        grid:{
          left:'2%',
          top:'16%',
          right:'2%',
          bottom:'2%',
          containLabel:true
        },
        tooltip:{
          show:true
        },
        xAxis: {
          type: 'category',
          data:datax,
          boundaryGap: false
        },
        yAxis: {},
        series: [
          {
            symbolSize: 5,
            data:datay,
            type: 'scatter',
            itemStyle: {
              color: "rgba(224, 67, 67, 1)"
            },
          }
        ]
      };
      this.chart6.clear()
      this.chart6.setOption(option);
      this.chart6.resize()
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getData();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeReportTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
  },
}
</script>

<style lang="scss" scoped>
.main-flex{
  display: flex;
  flex-direction: column;
  .row{
    display:flex;
    justify-content: space-between;
    margin-bottom:25px;
    ::v-deep .el-card{
      width: 49%;
      height: 35vh;
      .el-card__body{
        height: 100%;
      }
    }
    .chart{
      width: 100%;
      height: 100%;
    }
  }
}
</style>
