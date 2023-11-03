<template>
  <div class="mains">
    <el-card class="card2">
      <div id="main1" class="chart2">正常心电图 统计</div>
    </el-card>
    <el-card class="card2">
      <div id="main3" class="chart2">危机心电 统计</div>
    </el-card>
    <el-card class="card2">
      <div id="main0" class="chart2">心动过速 统计</div>
    </el-card>
    <el-card class="card2">
      <div id="main8" class="chart2">心律类型 统计</div>
    </el-card>
    <el-card class="card2">
      <div id="main2" class="chart2">早搏/逸搏 统计</div>
    </el-card>
    <el-card class="card2">
      <div id="main9" class="chart2">颤动/扑动 统计</div>
    </el-card>
    <el-card class="card2">
      <div id="main4" class="chart2">心肌梗死 统计</div>
    </el-card>
    <el-card class="card2">
      <div id="main10" class="chart2">心梗分期 统计</div>
    </el-card>
    <el-card class="card2">
      <div id="main6" class="chart2">ST-T异常 统计</div>
    </el-card>
    <el-card class="card2">
      <div id="main7" class="chart2">房室肥大 统计</div>
    </el-card>
    <el-card class="card2">
      <div id="main5" class="chart2">传导阻滞类型 统计</div>
    </el-card>




  </div>
</template>
<script>
import {listAlert_log} from "@/api/DRearly/DRearly";
import ageChild from '../ecgAgeAndType/index.vue'
import {getTypeList, getZCXDTCount} from "@/api/ecgCount/ecgCount";

export default {
  components:{
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
      XJGSDCountArr:[],
      WJXDDCountArr:[],
      XLDCountArr:[],
      CDZDCountArr:[],
      ZBDCountArr:[],
      YCCSDCountArr:[],
      FSFDDCountArr:[],
      CDPDDCountArr:[],
      XGFQDCountArr:[],
      query:{
        ecgType:null,
      },
      query1:{
        ecgType:null,
      },
    }
  },
  activated() {
    this.getList();
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
          text: "心动过速 统计",
          top: "bottom",
          left: "center",
          textStyle: {
            color: "black ", //标签文字颜色改为白色
          },
        },
        // grid:{
        //   top:'40%',
        //   right:'20%',
        //   left: '40%',
        //   bottom:'20%'
        // },
        tooltip: {
          trigger: 'item'
        },
        series : [
          {
            type: 'pie',
            radius: '70%',
            roseType: 'radius',
            left:'23%',
            right:'23%',
            data: this.countArr,
            label: {//饼图文字的显示
              show: true, //默认  显示文字
              fontSize:18,
              fontWeight: "bold",
              // position:'outer',
              // alignTo:'edge',
              // margin:10,
              // edgeDistance: "34%",
              formatter: function (arg) {
                //console.log(arg);
                return  arg.name + '(' + arg.value + ")"
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
        th.$router.push({path: "/ecgCount/ageDistribution" , query: {name: params.name}});
      });
    },
    DDrawLine() {
      // 基于准备好的dom，初始化echarts实例
      let main1 = this.$echarts.init(document.getElementById('main1'))
      let th = this
      var option = {
        title: {
          text: "正常异常心电图 统计",
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

            type: 'pie',
            radius: '60%',
            roseType: 'radius',
            left:'23%',
            right:'23%',
            data: this.DCountArr,
            label: {//饼图文字的显示
              show: true, //默认  显示文字
              fontSize:18,
              fontWeight: "bold",
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
        th.$router.push({path: "/ecgCount/ageDistribution" , query: {name: params.name}});
      });
    },
    ZBDrawLine() {
      // 基于准备好的dom，初始化echarts实例
      let main1 = this.$echarts.init(document.getElementById('main2'))
      let th = this
      var option = {
        title: {
          text: "早搏/逸搏 统计",
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

            type: 'pie',
            radius: '70%',
            roseType: 'radius',
            left:'23%',
            right:'23%',
            data: this.ZBDCountArr,
            label: {//饼图文字的显示
              show: true, //默认  显示文字
              fontSize:18,
              fontWeight: "bold",
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
        th.$router.push({path: "/ecgCount/ageDistribution" , query: {name: params.name}});
      });
    },
    XJGSDrawLine() {
      // 基于准备好的dom，初始化echarts实例
      let main1 = this.$echarts.init(document.getElementById('main4'))
      let th = this
      var option = {
        title: {
          text: "心梗位置 统计",
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

            type: 'pie',
            radius: '70%',
            roseType: 'radius',
            left:'23%',
            right:'23%',
            data: this.XJGSDCountArr,
            label: {//饼图文字的显示
              show: true, //默认  显示文字
              fontSize:18,
              fontWeight: "bold",
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
        th.$router.push({path: "/ecgCount/ageDistribution" , query: {name: params.name}});
      });
    },
    XLDrawLine() {
      // 基于准备好的dom，初始化echarts实例
      let main1 = this.$echarts.init(document.getElementById('main8'))
      let th = this
      var option = {
        title: {
          text: "心律 统计",
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

            type: 'pie',
            radius: '70%',
            roseType: 'radius',
            left:'23%',
            right:'23%',
            data: this.XLDCountArr,
            label: {//饼图文字的显示
              show: true, //默认  显示文字
              fontSize:18,
              fontWeight: "bold",
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
        th.$router.push({path: "/ecgCount/ageDistribution" , query: {name: params.name}});
      });
    },
    CDZDDrawLine() {
      // 基于准备好的dom，初始化echarts实例
      let main1 = this.$echarts.init(document.getElementById('main5'))
      let th = this
      var option = {
        title: {
          text: "传导阻滞 统计",
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

            type: 'pie',
            radius: '70%',
            roseType: 'radius',
            left:'23%',
            right:'23%',
            data: this.CDZDCountArr,
            label: {//饼图文字的显示
              show: true, //默认  显示文字
              fontSize:18,
              fontWeight: "bold",
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
        th.$router.push({path: "/ecgCount/ageDistribution" , query: {name: params.name}});
      });
    },
    WJXDDrawLine() {
      // 基于准备好的dom，初始化echarts实例
      let main1 = this.$echarts.init(document.getElementById('main3'))
      let th = this
      var option = {
        title: {
          text: "危急心电 统计",
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

            type: 'pie',
            radius: '70%',
            roseType: 'radius',
            left:'23%',
            right:'23%',
            data: this.WJXDDCountArr,
            label: {//饼图文字的显示
              show: true, //默认  显示文字
              fontSize:18,
              fontWeight: "bold",
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
        th.$router.push({path: "/ecgCount/ageDistribution" , query: {name: params.name}});
      });
    },

    YCCSDrawLine() {
      // 基于准备好的dom，初始化echarts实例
      let main1 = this.$echarts.init(document.getElementById('main6'))
      let th = this
      var option = {
        title: {
          text: "ST-T异常 统计",
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

            type: 'pie',
            radius: '70%',
            roseType: 'radius',
            left:'23%',
            right:'23%',
            data: this.YCCSDCountArr,
            label: {//饼图文字的显示
              show: true, //默认  显示文字
              fontSize:18,
              fontWeight: "bold",
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
        th.$router.push({path: "/ecgCount/ageDistribution" , query: {name: params.name}});
      });
    },
    FSFDDrawLine() {
      // 基于准备好的dom，初始化echarts实例
      let main1 = this.$echarts.init(document.getElementById('main7'))
      let th = this
      var option = {
        title: {
          text: "房室肥大 统计",
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

            type: 'pie',
            radius: '70%',
            roseType: 'radius',
            left:'23%',
            right:'23%',
            data: this.FSFDDCountArr,
            label: {//饼图文字的显示
              show: true, //默认  显示文字
              fontSize:18,
              fontWeight: "bold",
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
        th.$router.push({path: "/ecgCount/ageDistribution" , query: {name: params.name}});
      });
    },
    CDPDDrawLine() {
      // 基于准备好的dom，初始化echarts实例
      let main1 = this.$echarts.init(document.getElementById('main9'))
      let th = this
      var option = {
        title: {
          text: "颤动/扑动 统计",
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

            type: 'pie',
            radius: '70%',
            roseType: 'radius',
            left:'23%',
            right:'23%',
            data: this.CDPDDCountArr,
            label: {//饼图文字的显示
              show: true, //默认  显示文字
              fontSize:18,
              fontWeight: "bold",
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
        th.$router.push({path: "/ecgCount/ageDistribution" , query: {name: params.name}});
      });
    },
    XGFQPDDrawLine() {
      // 基于准备好的dom，初始化echarts实例
      let main1 = this.$echarts.init(document.getElementById('main10'))
      let th = this
      var option = {
        title: {
          text: "心梗分期 统计",
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

            type: 'pie',
            radius: '70%',
            roseType: 'radius',
            left:'23%',
            right:'23%',
            data: this.XGFQDCountArr,
            label: {//饼图文字的显示
              show: true, //默认  显示文字
              fontSize:18,
              fontWeight: "bold",
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
        th.$router.push({path: "/ecgCount/ageDistribution" , query: {name: params.name}});
      });
    },



    /** 查询 */
    getList() {


      getTypeList().then(r=>{
        console.log(r)
        //心电过速
        if(r.data.XDGS.length === 0){
          // 无数据时：展示暂无数据
          const dom = document.getElementById('main0');
          dom.innerHTML = '-心电过速暂无相关数据-';
          dom.style.cssText = 'color: #999; border: none;float: right;margin-top: 5%;margin-right: 10%';
          dom.removeAttribute('_echarts_instance_');
        }else {
          this.countArr=r.data.XDGS
          this.opName = '数据库心动过速统计'
          this.drawLine();
        }
        //早搏逸博
        if(r.data.ZBTB.length === 0){
          // 无数据时：展示暂无数据
          const dom = document.getElementById('main2');
          dom.innerHTML = '-早搏逸博暂无相关数据-';
          dom.style.cssText = 'color: #999; border: none;float: right;margin-top: 5%;margin-right: 10%';
          dom.removeAttribute('_echarts_instance_');
        }else {
          this.ZBDCountArr=r.data.ZBTB
          this.opName = '数据库早搏逸博统计'
          this.ZBDrawLine();
        }

        //心肌梗死
        if(r.data.XJGS.length === 0){
          // 无数据时：展示暂无数据
          const dom = document.getElementById('main4');
          dom.innerHTML = '-心肌梗死暂无相关数据-';
          dom.style.cssText = 'color: #999; border: none;float: right;margin-top: 5%;margin-right: 10%';
          dom.removeAttribute('_echarts_instance_');
        }else {
          this.XJGSDCountArr=r.data.XJGS
          this.opName = '数据库心肌梗死统计'
          this.XJGSDrawLine();
        }
        //心律
        if(r.data.XL.length === 0){
          // 无数据时：展示暂无数据
          const dom = document.getElementById('main8');
          dom.innerHTML = '-心律暂无相关数据-';
          dom.style.cssText = 'color: #999; border: none;float: right;margin-top: 5%;margin-right: 10%';
          dom.removeAttribute('_echarts_instance_');
        }else {
          this.XLDCountArr=r.data.XL
          this.opName = '数据库心律统计'
          this.XLDrawLine();
        }
        //传导阻滞
        if(r.data.CDZD.length === 0){
          // 无数据时：展示暂无数据
          const dom = document.getElementById('main5');
          dom.innerHTML = '-传导阻滞暂无相关数据-';
          dom.style.cssText = 'color: #999; border: none;float: right;margin-top: 5%;margin-right: 10%';
          dom.removeAttribute('_echarts_instance_');
        }else {
          this.CDZDCountArr=r.data.CDZD
          this.opName = '数据库传导阻滞统计'
          this.CDZDDrawLine();
        }
        //危机心电
        if(r.data.WGXD.length === 0){
          // 无数据时：展示暂无数据
          const dom = document.getElementById('main3');
          dom.innerHTML = '-危机心电暂无相关数据-';
          dom.style.cssText = 'color: #999; border: none;float: right;margin-top: 5%;margin-right: 10%';
          dom.removeAttribute('_echarts_instance_');
        }else {
          this.WJXDDCountArr=r.data.WGXD
          this.opName = '数据库危机心电统计'
          this.WJXDDrawLine();
        }
        //ST-T异常
        if(r.data.YCCS.length === 0){
          // 无数据时：展示暂无数据
          const dom = document.getElementById('main6');
          dom.innerHTML = '-ST-T异常暂无相关数据-';
          dom.style.cssText = 'color: #999; border: none;float: right;margin-top: 5%;margin-right: 10%';
          dom.removeAttribute('_echarts_instance_');
        }else {
          this.YCCSDCountArr=r.data.YCCS
          this.opName = '数据库ST-T异常统计'
          this.YCCSDrawLine();
        }
        //房室肥大
        if(r.data.FSFD.length === 0){
          // 无数据时：展示暂无数据
          const dom = document.getElementById('main7');
          dom.innerHTML = '-房室肥大暂无相关数据-';
          dom.style.cssText = 'color: #999; border: none;float: right;margin-top: 5%;margin-right: 10%';
          dom.removeAttribute('_echarts_instance_');
        }else {
          this.FSFDDCountArr=r.data.FSFD
          this.opName = '数据库房室肥大统计'
          this.FSFDDrawLine();
        }
        //颤动/扑动
        if(r.data.CDPD.length === 0){
          // 无数据时：展示暂无数据
          const dom = document.getElementById('main9');
          dom.innerHTML = '-颤动/扑动暂无相关数据-';
          dom.style.cssText = 'color: #999; border: none;float: right;margin-top: 5%;margin-right: 10%';
          dom.removeAttribute('_echarts_instance_');
        }else {
          this.CDPDDCountArr=r.data.CDPD
          this.opName = '数据库颤动/扑动统计'
          this.CDPDDrawLine();
        }
        //心梗分期
        if(r.data.XGFQ.length === 0){
          // 无数据时：展示暂无数据
          const dom = document.getElementById('main9');
          dom.innerHTML = '-心梗分期暂无相关数据-';
          dom.style.cssText = 'color: #999; border: none;float: right;margin-top: 5%;margin-right: 10%';
          dom.removeAttribute('_echarts_instance_');
        }else {
          this.XGFQDCountArr=r.data.XGFQ
          this.opName = '数据库心梗分期统计'
          this.XGFQPDDrawLine();
        }
      })
      getZCXDTCount().then(r=>{
        if(r.data.length === 0){
          // 无数据时：展示暂无数据
          const dom = document.getElementById('main1');
          dom.innerHTML = '-正常心电图统计暂无相关数据-';
          dom.style.cssText = 'color: #999; border: none;float: left;margin-top: 5%;margin-left: 10%';
          dom.removeAttribute('_echarts_instance_');
        }else{
          this.DCountArr=r.data
          this.DOpName = '正常心电图统计'
          this.DDrawLine();
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
  justify-content: space-around;
  flex-wrap: wrap;
    .card2{
      margin-top: 10px;
      //min-height: 42vh;
      width: 49%;
      .chart2{
        width: 100%;
        height: 48vh;
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
