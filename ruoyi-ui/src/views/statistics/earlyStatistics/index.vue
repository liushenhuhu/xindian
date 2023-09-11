<template>
<div class="mains">

  <div class="top">
    <el-card class="card1" >
      <p class="title"><span class="title-left"></span>心电类型统计图</p>
      <typeChild></typeChild>
    </el-card>
    <el-card class="card1">
      <ageChild></ageChild>
    </el-card>
  </div>
  <el-divider content-position="left"><span class="title-left"></span>预警分类统计图</el-divider>
  <div class="circle">
    <el-card class="card2">
      <div id="main0" class="chart2">静态12导预警</div>
    </el-card>
    <el-card class="card2">
      <div id="main1" class="chart2">静态单导预警</div>
    </el-card>
    <el-card class="card2">
      <div id="main2" class="chart2">动态12导预警</div>
    </el-card>
    <el-card class="card2">
      <div id="main3" class="chart2">动态单导预警</div>
    </el-card>
  </div>

</div>
</template>
<script>
import {listAlert_log} from "@/api/DRearly/DRearly";
import typeChild from '../typeStatistics/child'
import ageChild from '../ageStatistics/child1'

export default {
  components:{
    typeChild,
    ageChild
  },
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
      let main = this.$echarts.init(document.getElementById('main0'))
      let th = this
      var option = {
        title: {
          text: "静态12导预警",
          top: "bottom",
          left: "center",
          textStyle: {
            color: "black ", //标签文字颜色改为白色
          },

        },
        tooltip: {
          trigger: 'item'
        },
        series : [
          {
            name: this.opName,
            type: 'pie',
            radius: '70%',
            roseType: 'radius',
            left:'23%',
            right:'23%',
            data: this.countArr,
            label: {//饼图文字的显示
              show: true, //默认  显示文字
              // position:'outer',
              // alignTo:'edge',
              // margin:10,
              // edgeDistance: "34%",
              formatter: function (arg) {
                //console.log(arg);
                return arg.name + '(' + arg.value + ")"
              }
            },
          }
        ]
      };
      main.off('click')
      // 绘制图表
      main.setOption(option);
      window.addEventListener("resize", function () {
        main.resize();
      });
      setTimeout(function (){
        main.resize();
      })
      // main.on('click', function(params) {
      //   th.$router.push({path: "/statistics/earlyAll" , query: {logType: params.data.name,type:'12'}});
      // });
    },

    DDrawLine() {
      // 基于准备好的dom，初始化echarts实例
      let main1 = this.$echarts.init(document.getElementById('main1'))
      let th = this
      var option = {
        title: {
          text: "静态单导预警",
          top: "bottom",
          left: "center",
          textStyle: {
            color: "black ", //标签文字颜色改为白色
          },
        },
        tooltip: {
          trigger: 'item'
        },
        series : [
          {
            name: this.DOpName,
            type: 'pie',
            radius: '70%',
            roseType: 'radius',
            left:'23%',
            right:'23%',
            data: this.DCountArr,
            label: {//饼图文字的显示
              show: true, //默认  显示文字
              // position:'outer',
              // alignTo:'edge',
              // margin:10,
              formatter: function (arg) {
                //console.log(arg);
                return arg.name + '(' + arg.value + ")"
              }
            },
          }
        ]
      };
      main1.off('click')
      // 绘制图表
      main1.setOption(option);
      window.addEventListener("resize", function () {
        main1.resize();
      });
      setTimeout(function (){
        main1.resize();
      })
      main1.on('click', function(params) {
        th.$router.push({path: "/statistics/earlyAll" , query: {logType: params.data.name,type:'single'}});
      });
    },


    //动态
    drawLine1() {
      // 基于准备好的dom，初始化echarts实例
      let main = this.$echarts.init(document.getElementById('main2'))
      let th = this
      var option = {
        title: {
          text: "动态12导预警",
          top: "bottom",
          left: "center",
          textStyle: {
            color: "black ", //标签文字颜色改为白色
          },
        },
        tooltip: {
          trigger: 'item'
        },
        series : [
          {
            name: this.opName,
            type: 'pie',
            radius: '70%',
            roseType: 'radius',
            left:'23%',
            right:'23%',
            data: this.countArr,
            label: {//饼图文字的显示
              show: true, //默认  显示文字
              // position:'outer',
              // alignTo:'edge',
              // margin:10,
              formatter: function (arg) {
                //console.log(arg);
                return arg.name + '(' + arg.value + ")"
              }
            },
          }
        ]
      };
      main.off('click')
      // 绘制图表
      main.setOption(option);
      window.addEventListener("resize", function () {
        main.resize();
      });
      setTimeout(function (){
        main.resize();
      })
      main.on('click', function(params) {
        th.$router.push({path: "/statistics/earlyAll" , query: {logType: params.data.name,type:'12'}});
      });
    },
    DDrawLine1() {
      // 基于准备好的dom，初始化echarts实例
      let main1 = this.$echarts.init(document.getElementById('main3'))
      let th = this
      var option = {
        title: {
          text: "动态单导预警",
          top: "bottom",
          left: "center",
          textStyle: {
            color: "black ", //标签文字颜色改为白色
          },
        },
        tooltip: {
          trigger: 'item'
        },
        series : [
          {
            name: this.DOpName,
            type: 'pie',
            radius: '70%',
            roseType: 'radius',
            left:'23%',
            right:'23%',
            data: this.DCountArr,
            label: {//饼图文字的显示
              show: true, //默认  显示文字
              // position:'outer',
              // alignTo:'edge',
              // margin:10,
              formatter: function (arg) {
                //console.log(arg);
                return arg.name + '(' + arg.value + ")"
              }
            },
          }
        ]
      };
      main1.off('click')
      // 绘制图表
      main1.setOption(option);
      window.addEventListener("resize", function () {
        main1.resize();
      });
      setTimeout(function (){
        main1.resize();
      })
      main1.on('click', function(params) {
        th.$router.push({path: "/statistics/earlyAll" , query: {logType: params.data.name,type:'single'}});
      });
    },

    /** 查询 */
    getList() {

      //静态
      this.query.ecgType='J12'
      listAlert_log(this.query).then(r=>{
        if(r.data.length === 0){
          // 无数据时：展示暂无数据
          const dom = document.getElementById('main0');
          dom.innerHTML = '-静态12导预警暂无相关数据-';
          dom.style.cssText = 'color: #999; border: none;float: right;margin-top: 5%;margin-right: 10%';
          dom.removeAttribute('_echarts_instance_');
        }else {
          this.countArr=r.data
          this.opName = '12导预警'
          this.drawLine();
        }

      })
      this.query1.ecgType='Jsingle'
      listAlert_log(this.query1).then(r=>{
        if(r.data.length === 0){
          // 无数据时：展示暂无数据
          const dom = document.getElementById('main1');
          dom.innerHTML = '-静态单导预警暂无相关数据-';
          dom.style.cssText = 'color: #999; border: none;float: left;margin-top: 5%;margin-left: 10%';
          dom.removeAttribute('_echarts_instance_');
        }else{
          this.DCountArr=r.data
          this.DOpName = '单导预警'
          this.DDrawLine();
        }

      })

      //动态
      this.query.ecgType='D12'
      listAlert_log(this.query).then(r=>{
        if(r.data.length === 0){
          // 无数据时：展示暂无数据
          const dom = document.getElementById('main2');
          dom.innerHTML = '-动态12导预警暂无相关数据-';
          dom.style.cssText = 'color: #999; border: none;float: left;margin-top: 5%;margin-left: 10%';
          dom.removeAttribute('_echarts_instance_');
        }else {
          this.countArr=r.data
          this.opName = '12导预警'
          this.drawLine1();
        }

      })
      this.query1.ecgType='Dsingle'
      listAlert_log(this.query1).then(r=>{
        if(r.data.length === 0){
          // 无数据时：展示暂无数据
          const dom = document.getElementById('main3');
          dom.innerHTML = '-动态单导预警暂无相关数据-';
          dom.style.cssText = 'color: #999; border: none;float: right;margin-top: 5%;margin-right: 10%';
          dom.removeAttribute('_echarts_instance_');
        }else {
          this.DCountArr=r.data
          this.DOpName = '单导预警'
          this.DDrawLine1();
        }

      })

    },
    /** 搜索按钮操作 */

  }
}
</script>
<style lang="scss" scoped>
::v-deep .el-card__body{
  padding: 10px;
  height: 100%;
}
.mains {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  .title-left{
    display: inline-block;
    width: 0.25rem;
    height: 1rem;
    background: linear-gradient(180deg, #3ea2ff 0%, #2746fc 100%);
    border-radius: 0.13rem;
    margin-right: 1rem;
  }
  .top{
    width: 100%;
    height: 40%;
    display: flex;
    justify-content: space-around;
    text-align: center;
    .card1{
      margin-top: 5px;
      .title {
        text-align: left;
        font-size: 1.13rem;
        font-weight: bold;
        color: #333333;
        .title-left {
          display: inline-block;
          width: 0.25rem;
          height: 1rem;
          background: linear-gradient(180deg, #3ea2ff 0%, #2746fc 100%);
          border-radius: 0.13rem;
          margin-right: 1rem;
        }
      }
    }
  }
  .circle {
    margin-top: 5px;
    align-items: center;
    display: flex;
    justify-content: space-around;
    .card2{
        min-height: 35vh;
        width: 24.5%;
      .chart2{
        width: 100%;
        height: 30vh;
      }
    }
  }
}

   .el-divider {
     ::v-deep .el-divider__text {
       font-weight: 700 !important;
       font-size: 20px;
     }
   }
</style>
