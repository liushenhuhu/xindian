<template>
  <div class="app-container">

    <el-divider content-position="left">预警统计图</el-divider>

    <div  id="main" style="width: 700px;height:400px; float: left;" >12导预警</div>

    <div id="main1" style="width: 600px;height:400px;float: right">单导预警</div>
  </div>
</template>
<script>
import {listAlert_log} from "@/api/DRearly/DRearly";

export default {
  name: 'hello',
  data () {
    return {
      form:{},
      opName:null,
      DOpName:null,
      statistics: [],
      options: [],
      DOptions: [],
      countArr: [],
      DCountArr: [],
      query:{
        ecgType:null,
      },
      query1:{
        ecgType:null,
      },
    }
  },

  created() {
    this.getList();
  },
  mounted(){
    this.drawLine();
    //饼状图点击事件


  },
  methods: {
    drawLine() {
      // 基于准备好的dom，初始化echarts实例
      let main = this.$echarts.init(document.getElementById('main'))
      let th = this
      var option = {
        tooltip: {
          trigger: 'item'
        },
        series : [
          {
            name: this.opName,
            type: 'pie',
            radius: '70%',
            roseType: 'radius',
            data: this.countArr,
            label: {//饼图文字的显示
              show: true, //默认  显示文字
              formatter: function (arg) {
                console.log(arg);
                return arg.name + '：预警' + arg.value + "次"
              }
            },
          }
        ]
      };
      main.off('click')
      // 绘制图表
      main.setOption(option);
      main.on('click', function(params) {
        th.$router.push({path: "/statistics/earlyAll" , query: {logType: params.data.name,type:'12'}});
      });
    },
    take01(val){
      console.log(val)
    },
    DDrawLine() {
      // 基于准备好的dom，初始化echarts实例
      let main1 = this.$echarts.init(document.getElementById('main1'))
      let th = this
      var option = {
        tooltip: {
          trigger: 'item'
        },
        series : [
          {
            name: this.DOpName,
            type: 'pie',
            radius: '70%',
            roseType: 'radius',
            data: this.DCountArr,
            label: {//饼图文字的显示
              show: true, //默认  显示文字
              formatter: function (arg) {
                console.log(arg);
                return arg.name + '：预警' + arg.value + "次"
              }
            },
          }
        ]
      };
      main1.off('click')
      // 绘制图表
      main1.setOption(option);
      main1.on('click', function(params) {
        th.$router.push({path: "/statistics/earlyAll" , query: {logType: params.data.name,type:'single'}});
      });
    },
    /** 查询 */
    getList() {

      this.query.ecgType='12'
      listAlert_log(this.query).then(r=>{
        this.countArr=r.data
        this.opName = '12导预警'
        this.drawLine();
      })
      this.query1.ecgType='single'
      listAlert_log(this.query1).then(r=>{
        this.DCountArr=r.data
        this.DOpName = '单导预警'
        this.DDrawLine();
      })

    },
    /** 搜索按钮操作 */

  }
}
</script>
