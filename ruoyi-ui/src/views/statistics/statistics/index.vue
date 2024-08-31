<template>
  <el-row>
    <el-col :span="24">
      <div id="appc" class="app-container">
        <el-form v-if="show" id="add1" :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="医生名称" prop="doctor_name">
            <el-select v-model="queryParams.doctorPhone" clearable placeholder="请选择" >
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
          <el-form-item label="选择年份"  label-width="100px">
            <el-date-picker
              v-model="queryParams.year"
              type="year"
              placeholder="选择年"
              format="yyyy">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="诊断时间">
            <el-date-picker
              v-model="daterangeConnectionTime"
              style="width: 205px"
              value-format="yyyy-MM-dd HH:mm:ss"
              type="datetimerange"
              range-separator="-"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              @input="e=>{
          immediUpdate(daterangeConnectionTime,e)
         }"
            ></el-date-picker>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>
        <el-divider content-position="left"><span class="title-left"></span>医生每月诊断次数统计图</el-divider>
        <el-card class="box-card">
          <div id="myChart" class="myChart" > </div>
        </el-card>

<!--        <div id="table1" style="align-content: center;display: none">-->
<!--          <el-button type="primary" icon="el-icon-back" size="mini" @click="backQuery">返回</el-button>-->
<!--          <el-table-->
<!--            :data="tableData"-->
<!--            style="width: 100%;text-align: center">-->
<!--            <el-table-column label="医生姓名" align="center" prop="doctorName" />-->
<!--            <el-table-column label="诊断月份" align="center" prop="month" />-->
<!--            <el-table-column label="诊断时长" align="center" prop="timeCount" />-->
<!--          </el-table>-->
<!--          <pagination-->
<!--            v-show="total>0"-->
<!--            :total="total"-->
<!--            :page.sync="queryParams.pageNum"-->
<!--            :limit.sync="queryParams.pageSize"-->
<!--            @pagination="getListData"-->
<!--          />-->
<!--        </div>-->



        <el-divider content-position="left"><span class="title-left"></span>医生诊断平均时间排行</el-divider>
        <el-table
          :data="tableData"
          style="width: 100%;text-align: center">
          <el-table-column label="医生姓名" align="center" prop="doctorName" />
          <el-table-column label="诊断次数" align="center" prop="count" />
          <el-table-column label="诊断总时长" align="center" prop="countTime" />
          <el-table-column label="平均诊断时长" align="center" prop="averageTime" />
          <el-table-column label="主动接诊次数" align="center" prop="diagnoseTypeZhuSUM" />
          <el-table-column label="被动分配次数" align="center" prop="diagnoseTypeBIESUM" />
          <el-table-column label="分配未诊断次数" align="center" prop="diagnoseStatusWEISUM" />
        </el-table>
        <pagination
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getListData"
        />


      </div>
    </el-col>
  </el-row>



</template>
<script>
import {listStatistics, selectDoctor, dateList, countList} from "@/api/statistics/statistics";
import Da from "element-ui/src/locale/lang/da";
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
      daterangeConnectionTime: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        doctorName: null,
        doctorPhone: null,
        month: null,
        reportType:null,
        year:'',
        startTime:null,
        endTime:null,
      },
      tableData: []
    }
  },
  created() {
    let date = new Date()
    this.queryParams.year=date.getFullYear()+''

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
        // title: {
        //   text: '医生每月诊断次数统计图'
        // },
        tooltip: {},
        xAxis: {
          data: ["一月", "二月", "三月", "四月", "五月",
            "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
          axisLabel:{
            interval: 0,
            //rotate : 40,
          },
          axisLine: {
            show: true,
            lineStyle:{
              width:1,
              type:"dotted"
            }
          },
          axisTick: {
            show: false
          }
        },
        yAxis: {
          axisLine: {
            show: false,
          },
          axisTick: {
            show: false
          },
          axisLabel: {
            show: false
          },
          splitLine: {
            show: false
          },
        },
        series: [{
          //name: '诊断次数',
          type: 'bar',
          data: this.countArr,
          label: {//饼图文字的显示
            show: true, //默认  显示文字
            distance: 5.5,
            position: 'top',
            //verticalAlign: 'middle',
            color: '#009ac7',
            fontSize: 18
          },
          itemStyle:{
            normal:{
              color:'#73C0DE',
              barBorderRadius: [10, 10, 10, 10],
              shadowBlur: 8,
              shadowOffsetX: 3,
              shadowOffsetY: -2,
              shadowColor: "#73C0DE"
            }
          }
        }],

      });
      setTimeout(()=>{
        myChart.resize()
        }
      )
      window.addEventListener('resize', () => {
        myChart.resize();
      });
      myChart.on('click', function (params) {

        status.queryParams.month = status.lowNumber(params.name)
        console.log(status.queryParams.year+'-'+status.queryParams.month)

        status.$router.push({path:'/Diagnostic_statistics',query:{countTime:status.queryParams.year+'-'+status.queryParams.month,doctorPhone:status.queryParams.doctorPhone,ecgtype:status.queryParams.reportType}})
      })
    },

    immediUpdate(data, e) {
      if (e == null) {
        this.queryParams.startTime = null
        this.queryParams.endTime = null
      }
    },
    selectDoctor() {
      selectDoctor().then(response => {
        console.log(response)
        this.options = response;
      })
    },
    getListData(){
      countList(this.queryParams).then(r=>{
        console.log(r)
        this.tableData=r.rows
        this.total = r.total;
      })
    },


    lowNumber(val){

      let str = "";
      switch(val){
        case "一月":
          str = "01";
          break;
        case "二月":
          str = "02";
          break;
        case "三月":
          str = "03";
          break;
        case "四月":
          str = "04";
          break;
        case "五月":
          str = "05";
          break;
        case "六月":
          str = "06";
          break;
        case "七月":
          str = "07";
          break;
        case "八月":
          str = "08";
          break;
        case "九月":
          str = "09";
          break;
        case "十月":
          str = "10";
          break;
        case "十一月":
          str = "11";
          break;
        case "十二月":
          str = "12";
          break;
      }
      return str;
    },
    /** 查询 */
    getList() {

      this.getListData()
      if (this.queryParams.year.length!==4){
        let dateYear = new Date(this.queryParams.year)
        this.queryParams.year= dateYear.getFullYear()+''
      }

      if (null != this.daterangeConnectionTime && '' !== this.daterangeConnectionTime) {
        this.queryParams.startTime = this.daterangeConnectionTime[0];
        this.queryParams.endTime = this.daterangeConnectionTime[1];
      }
      listStatistics(this.queryParams).then(response => {
        console.log(response.rows);
        let data = response.rows;
        let countArr = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
        for (let j = 0; j < data.length; j++) {
          countArr[data[j].month - 1] = parseInt(data[j].count);
        }
        console.log('response',response)
        this.countArr = countArr;
        this.drawLine();
        //console.log(countArr);

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
    },

    resetQuery(){
      let date = new Date()
      this.queryParams= {
        doctorName: null,
        doctorPhone: null,
        month: null,
        reportType:null,
        year:date.getFullYear()+'',
        startTime:null,
        endTime:null,
      }
      this.daterangeConnectionTime= [],
      this.queryParams.pageNum = 1;
      this.getList();
    },
  },
}
</script>
<style lang="scss" scoped>
.el-divider {
  ::v-deep .el-divider__text {
    font-weight: 700 !important;
    font-size: 20px;
  }
}
.title-left {
  display: inline-block;
  width: 0.25rem;
  height: 1rem;
  background: linear-gradient(180deg, #3ea2ff 0%, #2746fc 100%);
  border-radius: 0.13rem;
  margin-right: 1rem;
}
.box-card {
  width: 90%;
  height: 35vh;
  margin:0 auto;
  margin-bottom: 10px;
}
.myChart{
  height: 35vh;
  width: 100%;
}
::v-deep .el-card__body{
  padding: 0;
  height: 100%;
}

</style>
