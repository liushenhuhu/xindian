<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true"  label-width="68px">
      <el-form-item label="选择时间">
        <el-date-picker
          v-model="daterangeReportTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="选择年">
        <el-date-picker
          v-model="queryParams.year"
          type="year"
          value-format="yyyy"
          placeholder="选择年">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="心电类型">
        <el-select v-model="queryParams.reportType" placeholder="请选择">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-card class="box-card">
      <div id="chart1" class="chart"></div>
    </el-card>

    <el-card class="box-card">
      <div id="chart2" class="chart"></div>
    </el-card>

  </div>
</template>

<script>
import { getreportcount } from "@/api/reportStic/reportStic";

var dates = [];
export default {
  name: "reportStic",
  dicts: ['diagnosis_status', 'ecg_type'],
  data() {
    return {
      date:[],
      daterangeReportTime: [],
      queryParams: {
        startTime:'',
        endTime:'',
        year:'',
        reportType:'',
      },
      // 表单参数
      form: {
        startTime:'',
        endTime:'',
        year:''
      },
      data:[],
      month:[],
      options: [{
        value: 'JECGsingle',
        label: '静态单导'
      }, {
        value: 'JECG12',
        label: '静态12导'
      },{
        value: 'DECGsingle',
        label: '动态单导'
      }, {
        value: 'DECG12',
        label: '动态12导'
      }]
    };
  },
  created() {
    this.getList()
  },
  mounted() {

  },
  methods: {
    /** 查询报告列表 */
    getList() {
      var  startTime = Date.parse(this.queryParams.startTime);
      var  endTime = Date.parse(this.queryParams.endTime);
      var days=(endTime - startTime)/(1*24*60*60*1000) + 1;
      if(days<7&&days>0){
        this.queryParams.startTime=this.getNextDate(startTime,7-days)
      }
      console.log(this.queryParams)
      getreportcount(this.queryParams).then(res=>{
        console.log(res)
        this.date=[]
        this.data=[]
        this.month=[]
        res.day.forEach(item=>{
          this.date.push(item.name)
          this.data.push(item.value)
        })
        let month=[0,0,0,0,0,0,0,0,0,0,0,0]
        for (let i = 0; i < res.month.length; i++) {
          month[parseInt(res.month[i].name)-1]=res.month[i].value
        }
        this.month=month
        this.chart1()
        this.chart2()
      })
    },

    getNextDate(date,day) {
      var dd = new Date(date);
      dd.setDate(dd.getDate() - day);
      var y = dd.getFullYear();
      var m = dd.getMonth() + 1 < 10 ? "0" + (dd.getMonth() + 1) : dd.getMonth() + 1;
      var d = dd.getDate() < 10 ? "0" + dd.getDate() : dd.getDate();

      return y + "-" + m + "-" + d;
    },
    chart1(){
      let myChart1 = this.$echarts.init(document.getElementById('chart1'));
      let option = {
        title:{
          show:true,
          text:'用户心电检测数据上传次数   每日统计'
        },
        xAxis: {
          type: 'category',
          data: this.date,
          axisLabel:{
            rotate:50
          }
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: this.data,
            type: 'bar',
            itemStyle: {        //上方显示数值
              normal: {
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
          }
        ]
      };
      myChart1.setOption(option,true)
      window.addEventListener('resize', () => {
        myChart1.resize();
      });
      myChart1.on('click', function (params) {

      })
    },
    chart2(){
      let myChart2 = this.$echarts.init(document.getElementById('chart2'));
      let option = {
        title:{
          show:true,
          text:'用户心电检测数据上传次数   每月统计'
        },
        xAxis: {
          type: 'category',
          data: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月']
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: this.month,
            type: 'bar',
            itemStyle: {        //上方显示数值
              normal: {
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
          }
        ]
      };
      myChart2.setOption(option,true)
      window.addEventListener('resize', () => {
        myChart2.resize();
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      if(this.daterangeReportTime!==null){
        this.queryParams.startTime = this.daterangeReportTime[0];
        this.queryParams.endTime = this.daterangeReportTime[1];
      }else {
        this.queryParams.startTime=''
        this.queryParams.endTime =''
      }
      console.log(this.queryParams)
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeReportTime = [];
      this.queryParams={
        startTime:'',
        endTime:'',
        year:'',
        reportType:''
      }
      this.date=[]
      this.data=[]
      this.month=[]
      this.handleQuery();
    },



  }
};
</script>
<style>

.box-card {
  width: 90%;
  height: 39vh;
  margin:0 auto;
  margin-bottom: 10px;
}
.chart{
  width: 100%;
  height: 90%;
}
.el-card__body{
  height: 100%;
}

</style>
