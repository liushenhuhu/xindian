<template>
<div class="mains">
  <div class="count">
    <child></child>
  </div>
  <div class="type">
    <typeChild></typeChild>
  </div>
  <div class="age">
    <ageChild></ageChild>
  </div>
  <div class="circle">
    <div id="main0" style="width: 20vw;height:20vh;text-align: right">静态12导预警</div>

    <div id="main1" style="width: 20vw;height:20vh;">静态单导预警</div>

    <div id="main2" style="width: 20vw;height:20vh;float: right">动态12导预警</div>

    <div id="main3" style="width: 20vw;height:20vh;">动态单导预警</div>
  </div>

</div>
</template>
<script>
import {listAlert_log} from "@/api/DRearly/DRearly";
import child from '../statistics/child'
import typeChild from '../typeStatistics/child'
import ageChild from '../ageStatistics/child1'

export default {
  components:{
    child,
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
            data: this.countArr,
            label: {//饼图文字的显示
              show: true, //默认  显示文字
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
      main.on('click', function(params) {
        th.$router.push({path: "/statistics/earlyAll" , query: {logType: params.data.name,type:'12'}});
      });
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
            data: this.DCountArr,
            label: {//饼图文字的显示
              show: true, //默认  显示文字
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
            data: this.countArr,
            label: {//饼图文字的显示
              show: true, //默认  显示文字
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
            data: this.DCountArr,
            label: {//饼图文字的显示
              show: true, //默认  显示文字
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
.mains {
  width: 100%;
  height: 100%;
  //display: flex;
}
.circle {
  margin-top: 8%;
  display: flex;
  //justify-content: flex-end;
  flex-direction: row-reverse;
  align-items: center;
  div{
    margin-left: -60px;
  }
}
.type {
  height: 100%;
  margin-left: 1%;
}
.age {
  margin-top: -18%;
}
</style>
