<template>
  <div class="dashboard-editor-container">

    <panel-group @handleSetLineChartData="handleSetLineChartData" :usershuju="usershuju"  />


    <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
      <line-chart :chart-data="zhou" />
    </el-row>
    <el-form :model="queryParams" style="margin-top: 20px" ref="queryForm" size="small" :inline="true" label-width="68px">
      <el-form-item label="开始时间" prop="startTime">
        <el-date-picker
          v-model="queryParams.startTime"
          type="year"
          value-format="yyyy"
          :picker-options="pickerOptions"
          placeholder="请选择年份">
        </el-date-picker>
      </el-form-item>
<!--      <el-form-item label="结束时间" prop="endTime">-->
<!--        <el-date-picker-->
<!--          v-model="queryParams.endTime"-->
<!--          type="year"-->
<!--          value-format="yyyy"-->
<!--          placeholder="请选择结束时间">-->
<!--        </el-date-picker>-->
<!--      </el-form-item>-->
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
      <line-chart :chart-data="nian" :yue="true" />
    </el-row>
<!--    <el-row :gutter="32">-->
<!--      <el-col :xs="24" :sm="24" :lg="8">-->
<!--        <div class="chart-wrapper">-->
<!--          <raddar-chart />-->
<!--        </div>-->
<!--      </el-col>-->
<!--      <el-col :xs="24" :sm="24" :lg="8">-->
<!--        <div class="chart-wrapper">-->
<!--          <pie-chart />-->
<!--        </div>-->
<!--      </el-col>-->
<!--      <el-col :xs="24" :sm="24" :lg="8">-->
<!--        <div class="chart-wrapper">-->
<!--          <bar-chart />-->
<!--        </div>-->
<!--      </el-col>-->
<!--    </el-row>-->


  </div>
</template>

<script>
import PanelGroup from './dashboard/PanelGroup'
import LineChart from './dashboard/LineChart'
import RaddarChart from './dashboard/RaddarChart'
import PieChart from './dashboard/PieChart'
import BarChart from './dashboard/BarChart'
import {userNumber} from '@/api/Active_user/active_user'

const lineChartData = {
  newVisitis: {
    expectedData: [100, 120, 161, 134, 105, 160, 165],
    actualData: [120, 82, 91, 154, 162, 140, 145]
  },
  messages: {
    expectedData: [200, 192, 120, 144, 160, 130, 140],
    actualData: [180, 160, 151, 106, 145, 150, 130]
  },
  purchases: {
    expectedData: [80, 100, 121, 104, 105, 90, 100],
    actualData: [120, 90, 100, 138, 142, 130, 130]
  },
  shoppings: {
    expectedData: [130, 140, 141, 142, 145, 150, 160],
    actualData: [120, 82, 91, 154, 162, 140, 130]
  }
}

export default {
  name: 'Index',
  components: {
    PanelGroup,
    LineChart,
    RaddarChart,
    PieChart,
    BarChart
  },
  data() {
    return {
      lineChartData: lineChartData.newVisitis,
      messages: lineChartData.messages,
      usershuju:null,
      //周在线
      zhou:{
        expectedData: [],
        actualData: []
      },
      // 年中月在线数
      nian:{
        expectedData: [],
        actualData: []
      },
      queryParams:{
        patientPhone:null,
        startTime:null,
        endTime:null,
        patientName:null
      },
      pickerOptions: {
        disabledDate(time) {
          // 禁止选择当前年之后的年份
          return time.getTime() > Date.now()
        }
      },
    }
  },
  created() {
    this.shuju()
      // this.queryParams.endTime=this.GetDateStr(0)
    this.queryParams.startTime=this.GetDateStr(-30)
  },
  methods: {
    handleSetLineChartData(type) {
      this.lineChartData = lineChartData[type]
    },
    shuju(){
      userNumber().then(response => {
        if (response.code == 200){
          this.usershuju = response.data
          console.log("获取用户量成功")
          console.log(this.usershuju)

          this.zhou.actualData = Object.keys(response.data.sevenDays.onLine);
          this.zhou.expectedData = Object.values(response.data.sevenDays.onLine);
          this.zhou.xinzeng = Object.values(response.data.sevenDays.user);
          console.log("天数")
          console.log(this.zhou.actualData);
          console.log("当周在线数")
          console.log(this.zhou.expectedData);
          console.log("新增数")
          console.log(this.zhou.xinzeng)


          this.nian.actualData = Object.keys(response.data.year.onLine);
          this.nian.expectedData = Object.values(response.data.year.onLine);
          this.nian.xinzeng = Object.values(response.data.year.user);
          console.log("当年月")
          console.log(this.nian.actualData);
          console.log("当年月中在线数")
          console.log(this.nian.expectedData);
          console.log("新增数")
          console.log(this.nian.xinzeng)

        }
      }).catch(response => {
        console.log("用户量请求失败")
        console.log(response)
      })
    },
    GetDateStr(AddDayCount){
      var dd = new Date();
      console.log(dd)
      dd.setDate(dd.getDate());//获取AddDayCount天后的日期
      var y = dd.getFullYear();
      var m = (dd.getMonth()+1)<10?"0"+(dd.getMonth()+1):(dd.getMonth()+1);//获取当前月份的日期，不足10补0
      var d = dd.getDate()<10?"0"+dd.getDate():dd.getDate();//获取当前几号，不足10补0
      console.log(y+"-"+m+"-"+d)
      return y+"-"+m+"-"+d;
    },
    /** 搜索按钮操作 */
    handleQuery() {
      console.log(this.queryParams)
      userNumber(this.queryParams.startTime).then(response => {
        if (response.code == 200){
          this.usershuju = response.data
          console.log("获取用户量成功")
          console.log(this.usershuju)

          this.nian.actualData = Object.keys(response.data.year.onLine);
          this.nian.expectedData = Object.values(response.data.year.onLine);
          this.nian.xinzeng = Object.values(response.data.year.user);
          console.log("当年月")
          console.log(this.nian.actualData);
          console.log("当年月中在线数")
          console.log(this.nian.expectedData);
          console.log("新增数")
          console.log(this.nian.xinzeng)
        }
      }).catch(response => {
        console.log("搜索失败")
        console.log(response)
      })
      // if (this.queryParams.endTime&&this.queryParams.startTime){
      //   const date1String = this.queryParams.startTime;
      //   const date2String = this.queryParams.endTime;
      //
      //   const date1 = new Date(date1String);
      //   const date2 = new Date(date2String);
      //
      //   if (date1 > date2) {
      //     console.log(`${date1String} 大于 ${date2String}`);
      //     this.$modal.msgError("开始时间不能比结束时间大");
      //     return
      //   } else if (date1 < date2) {
      //     console.log(`${date1String} 小于 ${date2String}`);
      //   } else {
      //     console.log(`${date1String} 等于 ${date2String}`);
      //   }
      // }

    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      // this.queryParams.endTime=this.GetDateStr(0)
      let dd = new Date();
      let y = dd.getFullYear();
      this.queryParams.startTime = y+''
      this.handleQuery();
    },
  }
}
</script>

<style lang="scss" scoped>
.dashboard-editor-container {
  padding: 32px;
  background-color: rgb(240, 242, 245);
  position: relative;

  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
}

@media (max-width:1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}
</style>
