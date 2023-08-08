<template>
  <div class="home"  id="home">

    <div class="showbox" v-show="isShow">
      <div class="chart" id="chartshow" ></div>
      <el-button  @click="closeShow">关闭</el-button>
    </div>

    <div class="top">
      <el-button @click="back">动态检测大屏</el-button>
      <el-button @click="inScreen">全屏切换</el-button>
      <!--      <el-button @click="outScreen">退出全屏</el-button>-->
      <div class="text">
        <span >姓名:{{data.patientName}}</span>
        <span >性别:{{data.gender}}</span>
        <span >年龄:{{data.age}}</span>
        <span >设备号:{{data.deviceSn}}</span>
        <span >所属医院:{{data.hospName}}</span>
      </div>
    </div>

    <div class="main">
      <div class="left">
        <div class="container">
          <el-button @click="show(0)">展开</el-button>
          <span :style="color(0)"></span>
          <div id="chart_0" ></div>
        </div>
        <div class="container">
          <el-button @click="show(1)">展开</el-button>
          <span :style="color(1)"></span>
          <div id="chart_1" ></div>
        </div>
        <div class="container">
          <el-button @click="show(2)">展开</el-button>
          <span :style="color(2)"></span>
          <div id="chart_2" ></div>
        </div>
        <div class="container">
          <el-button @click="show(3)">展开</el-button>
          <span :style="color(3)"></span>
          <div id="chart_3" ></div>
        </div>
        <div class="container">
          <el-button @click="show(4)">展开</el-button>
          <span :style="color(4)"></span>
          <div id="chart_4" ></div>
        </div>
        <div class="container">
          <el-button @click="show(5)">展开</el-button>
          <span :style="color(5)"></span>
          <div id="chart_5" ></div>
        </div>
        <div class="container">
          <el-button @click="show(6)">展开</el-button>
          <span :style="color(6)"></span>
          <div id="chart_6" ></div>
        </div>
        <div class="container">
          <el-button @click="show(7)">展开</el-button>
          <span :style="color(7)"></span>
          <div id="chart_7" ></div>
        </div>
        <div class="container">
          <el-button @click="show(8)">展开</el-button>
          <span :style="color(8)"></span>
          <div id="chart_8" ></div>
        </div>
        <div class="container">
          <el-button @click="show(9)">展开</el-button>
          <span :style="color(9)"></span>
          <div id="chart_9" ></div>
        </div>
        <div class="container">
          <el-button @click="show(10)">展开</el-button>
          <span :style="color(10)"></span>
          <div id="chart_10" ></div>
        </div>
        <div class="container">
          <el-button @click="show(11)">展开</el-button>
          <span :style="color(11)"></span>
          <div id="chart_11" ></div>
        </div>
      </div>
      <div class="right">
        <div class="top" id="anaoption"></div>
        <div class="center">
          <div class="chart" id="sdnnrmssd"></div>
          <div class="text">
            <span >SDNN:{{data.SDNN}}</span>
            <span >RMSSD:{{data.rMSSD}}</span>
            <span >NN20:{{data.NN20}}</span>
            <span >NN50:{{data.NN50}}</span>
          </div>
        </div>
        <div class="bottom">
          <div class="chart" id="meanChart"></div>
          <div class="chart" id="pnn20"></div>
          <div class="chart" id="pnn50"></div>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
import * as echarts from './echarts.min'
import screenfull from 'screenfull'
import 'default-passive-events'
let meanChart;
let Anachart;
let sdnnchart;
let pnn20chart;
let pnn50chart;
let chart;
let  I
let  II
let  III
let  aVR
let  aVL
let  aVF
let  V1
let  V2
let  V3
let  V4
let  V5
let  V6
export default {
  name: "Index",
  data() {
    return {
      // 版本号
      isFullFlag:false,
      isShow:false,
      deviceSn:null,
      data: {},//前10秒数据
      newData: null,//最新10秒数据
      newArr:[],
      ts:0,//时间段
      timer:null,
      time:[],
      chartjump:null
    };
  },
  created() {
    this.deviceSn=this.$route.query.deviceSn;
    this.data={}
    if(this.deviceSn!==null){
      this.getlist()
    }
  },
  activated() {
    console.log("activated")
    this.disposeList()
    this.deviceSn=this.$route.query.deviceSn;
    this.data={}
    if(this.deviceSn!==null){
      this.getlist()
    }
  },
  computed: {
    color() {
      return id=> {
        if(this.data.noise){
          if(this.data.noise[id]){
            return 'background-color:red;';
          }
        }
        return ;
      };

    },
  },
  watch:{
  $route(to,from){
    if(this.$route.path!=='/Screen/detail'){
      window.clearInterval(this.timer)
      this.timer=null
      console.log("路由变化")
    }
  }
},
  deactivated(){//keep-alive的隐藏的钩子函数
    window.clearInterval(this.timer)
    this.timer=null
    this.data={}
    this.newData={}
    this.disposeList()
  },
  methods: {
    getlist(){
      this.$http.post('https://server.mindyard.cn:84/detect_decg',
        JSON.stringify({
          "deviceSn": this.deviceSn,
          "ts": 0,
        }), {
          contentType: "application/json",
          dataType: "json",
          headers: {
            "user": "zzu",
            "password": "zzu123"
          },
        }
      ).then(res => {
        res.data.result.hr_mean = res.data.result.hr_mean.toFixed()
        this.data=res.data.result
        console.log(this.data)
        I=res.data.result.data.I
        II=res.data.result.data.II
        III=res.data.result.data.III
        aVR=res.data.result.data.aVR
        aVL=res.data.result.data.aVL
        aVF=res.data.result.data.aVF
        V1=res.data.result.data.V1
        V2=res.data.result.data.V2
        V3=res.data.result.data.V3
        V4=res.data.result.data.V4
        V5=res.data.result.data.V5
        V6=res.data.result.data.V6
        this.$message.success("设备"+this.deviceSn+"数据获取成功")
        this.setMeanChart()
        this.setsdnn()
        this.setpnn50()
        this.setpnn20()
        this.setAna()
        let chartI=echarts.init(document.getElementById("chart_0"))
        let chartII=echarts.init(document.getElementById("chart_1"))
        let chartIII=echarts.init(document.getElementById("chart_2"))
        let chartaVR=echarts.init(document.getElementById("chart_3"))
        let chartaVL=echarts.init(document.getElementById("chart_4"))
        let chartaVF=echarts.init(document.getElementById("chart_5"))
        let chartV1=echarts.init(document.getElementById("chart_6"))
        let chartV2=echarts.init(document.getElementById("chart_7"))
        let chartV3=echarts.init(document.getElementById("chart_8"))
        let chartV4=echarts.init(document.getElementById("chart_9"))
        let chartV5=echarts.init(document.getElementById("chart_10"))
        let chartV6=echarts.init(document.getElementById("chart_11"))
        this.time=this.timex()
        chartI.clear()
        chartI.setOption({
          animation: true,
          animationDuration: 9900,
          animationEasing: "linear",
          animationEasingUpdate: 'linear',
          animationDurationUpdate: 10100,
          animationDelayUpdate: 0,
          animationThreshold: 10000,
          title: {
            text: 'I导联',
            textStyle: {
              fontSize: 12,
              color: "yellow"
            },
            left: 30
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross'
            }
          },
          grid: {
            left: 25 /*"50px"*/,
            right: 0 /*"15px"*/,
            top: 0,
            bottom: 0.5,
            height: '98%'
          },
          legend: {
            data: ['当前电位'],
            show: true,
            textStyle: {
              color: "#000000"
            } /*图例(legend)说明文字的颜色*/,
            left: "right",
          },
          xAxis: {
            boundaryGap: false,//x轴刻度数字在两个刻度线中间显示还是刻度线下显示
            data: this.time,
            axisTick: {//x轴刻度
              show: false

            },
            axisLabel: { //x轴刻度数字
              show: false,
              textStyle: {
                color: "#8DB6DB"
              }
            },
            splitLine: {/*网格线*/
              show: false,
              interval: 9,//x轴线之间相隔的数量
              lineStyle: {
                opacity: 0.3,//x轴线的粗细
                color: "#8DB6DB"//x轴线的颜色
              },
            }
          },
          yAxis: {
            type: 'value',
            // 显式设置 `axisLine.show` 和 `axisTick.show` 为 `true`
            axisLine: {
              show: true
            },

            min: -1,
            max: 1,
            boundaryGap: false,
            splitNumber: 20,
            axisLabel: { //修改坐标系字体颜色
              show: false,
              textStyle: {
                color: "#000000",
              }
            },
            splitLine: {/*网格线*/
              lineStyle: {
                opacity: 0.3,
                color: "#8DB6DB"
              },
              show: false
            }
          },
          series: [{
            markLine: {
              animation: false,
              symbol: "none",
              silent: true,
              lineStyle: {
                type: "solid",
                color: '#8DB6DB',
                width: 1,
                opacity: 0.5,

              },
              label: {
                show: false,
                position: 'start', // 表现内容展示的位置
                color: '#8DB6DB'  // xAxis,yAxis数字的颜色
              },
              data: [
                {xAxis: 0},
                {xAxis: 50},
                {xAxis: 100},
                {xAxis: 150},
                {xAxis: 200},
                {xAxis: 250},
                {xAxis: 300},
                {xAxis: 350},
                {xAxis: 400},
                {xAxis: 450},
                {xAxis: 500},
                {xAxis: 550},
                {xAxis: 600},
                {xAxis: 650},
                {xAxis: 700},
                {xAxis: 750},
                {xAxis: 800},
                {xAxis: 850},
                {xAxis: 900},
                {xAxis: 950},
                {xAxis: 1000},
                {xAxis: 1050},
                {xAxis: 1100},
                {xAxis: 1150},
                {xAxis: 1200},
                {xAxis: 1250},
                {xAxis: 1300},
                {xAxis: 1350},
                {xAxis: 1400},
                {xAxis: 1450},
                {xAxis: 1500},
                {xAxis: 1550},
                {xAxis: 1600},
                {xAxis: 1650},
                {xAxis: 1700},
                {xAxis: 1750},
                {xAxis: 1800},
                {xAxis: 1850},
                {xAxis: 1900},
                {xAxis: 1950},
                {xAxis: 2000},
                {xAxis: 2050},
                {xAxis: 2100},
                {xAxis: 2150},
                {xAxis: 2200},
                {xAxis: 2250},
                {xAxis: 2300},
                {xAxis: 2350},
                {xAxis: 2400},
                {xAxis: 2450},
                {xAxis: 2500},
                {yAxis: -1},
                {yAxis: -0.5},
                {yAxis: 0},
                {yAxis: 0.5},
                {yAxis: 1},
              ]

            },
            /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
            itemStyle: {
              normal: {
                lineStyle: {
                  color: '#92c2ff',/*折线的颜色*/
                  opacity: 1,
                },
              },
              emphasis:{
                scale:false,
                lineStyle: {
                  width:3
                },
              },
            },

            symbol: "none",
            /*去掉小圆点*/
            name: 'I',
            type: 'line',

            data: I,
            smooth: 0 //显示为平滑的曲线*/
          },
            {
              markLine: {
                animation: false,
                symbol: "none",
                silent: true,
                lineStyle: {
                  type: "solid",
                  color: '#8DB6DB',
                  width: 1,
                  opacity: 0.5,
                },
                label: {
                  show: true,
                  position: 'start', // 表现内容展示的位置
                  color: '#8DB6DB'  // xAxis,yAxis数字的颜色
                },
                data: [
                  {yAxis: -1},
                  {yAxis: -0.5},
                  {yAxis: 0},
                  {yAxis: 0.5},
                  {yAxis: 1},
                ]

              },
              itemStyle: {
                normal: {
                  lineStyle: {
                    color: '#92c2ff',/*折线的颜色*/
                    opacity: 1,
                  },
                }
              },
              symbol: "none",
              /*去掉小圆点*/
              name: 'I',
              type: 'line',
              smooth: 0 //显示为平滑的曲线*/
            }]
        })
        chartII.clear()
        chartII.setOption({
          animation: true,
          animationDuration: 9900,
          animationEasing: "linear",
          animationEasingUpdate: 'linear',
          animationDurationUpdate: 10100,
          animationDelayUpdate: 0,
          animationThreshold: 10000,

          title: {
            text: 'II导联',
            textStyle: {
              fontSize: 12,
              color: "yellow"
            },
            left: 30
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross'
            }
          },
          grid: {
            left: 25 /*"50px"*/,
            right: 0 /*"15px"*/,
            top: 0,
            bottom: 0.5,
            height: '98%'
          },
          legend: {
            data: ['当前电位'],
            show: true,
            textStyle: {
              color: "#000000"
            } /*图例(legend)说明文字的颜色*/,
            left: "right",
          },
          xAxis: {
            boundaryGap: false,//x轴刻度数字在两个刻度线中间显示还是刻度线下显示
            data: this.time,
            axisTick: {//x轴刻度
              show: false

            },
            axisLabel: { //x轴刻度数字
              show: false,
              textStyle: {
                color: "#8DB6DB"
              }
            },
            splitLine: {/*网格线*/
              show: false,
              interval: 9,//x轴线之间相隔的数量
              lineStyle: {
                opacity: 0.3,//x轴线的粗细
                color: "#8DB6DB"//x轴线的颜色
              },
            }
          },
          yAxis: {
            type: 'value',
            // 显式设置 `axisLine.show` 和 `axisTick.show` 为 `true`
            axisLine: {
              show: true
            },

            min: -1,
            max: 1,
            boundaryGap: false,
            splitNumber: 20,
            axisLabel: { //修改坐标系字体颜色
              show: false,
              textStyle: {
                color: "#000000",

              }
            },
            splitLine: {/*网格线*/
              lineStyle: {
                opacity: 0.3,
                color: "#8DB6DB"
              },
              show: false
            }
          },
          series: [{
            markLine: {
              animation: false,
              symbol: "none",
              silent: true,
              lineStyle: {
                type: "solid",
                color: '#8DB6DB',
                width: 1,
                opacity: 0.5,

              },
              label: {
                show: false,
                position: 'start', // 表现内容展示的位置
                color: '#8DB6DB'  // xAxis,yAxis数字的颜色
              },
              data: [
                {xAxis: 0},
                {xAxis: 50},
                {xAxis: 100},
                {xAxis: 150},
                {xAxis: 200},
                {xAxis: 250},
                {xAxis: 300},
                {xAxis: 350},
                {xAxis: 400},
                {xAxis: 450},
                {xAxis: 500},
                {xAxis: 550},
                {xAxis: 600},
                {xAxis: 650},
                {xAxis: 700},
                {xAxis: 750},
                {xAxis: 800},
                {xAxis: 850},
                {xAxis: 900},
                {xAxis: 950},
                {xAxis: 1000},
                {xAxis: 1050},
                {xAxis: 1100},
                {xAxis: 1150},
                {xAxis: 1200},
                {xAxis: 1250},
                {xAxis: 1300},
                {xAxis: 1350},
                {xAxis: 1400},
                {xAxis: 1450},
                {xAxis: 1500},
                {xAxis: 1550},
                {xAxis: 1600},
                {xAxis: 1650},
                {xAxis: 1700},
                {xAxis: 1750},
                {xAxis: 1800},
                {xAxis: 1850},
                {xAxis: 1900},
                {xAxis: 1950},
                {xAxis: 2000},
                {xAxis: 2050},
                {xAxis: 2100},
                {xAxis: 2150},
                {xAxis: 2200},
                {xAxis: 2250},
                {xAxis: 2300},
                {xAxis: 2350},
                {xAxis: 2400},
                {xAxis: 2450},
                {xAxis: 2500},
                {yAxis: -1},
                {yAxis: -0.5},
                {yAxis: 0},
                {yAxis: 0.5},
                {yAxis: 1},
              ]

            },
            /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
            itemStyle: {
              normal: {
                lineStyle: {
                  color: '#92c2ff',/*折线的颜色*/
                  opacity: 1,
                },
              }
            },
            symbol: "none",
            /*去掉小圆点*/
            name: 'II',
            type: 'line',
            data: II,
            smooth: 0 //显示为平滑的曲线*/
          },
            {
              markLine: {
                animation: false,
                symbol: "none",
                silent: true,
                lineStyle: {
                  type: "solid",
                  color: '#8DB6DB',
                  width: 1,
                  opacity: 0.5,
                },
                label: {
                  show: true,
                  position: 'start', // 表现内容展示的位置
                  color: '#8DB6DB'  // xAxis,yAxis数字的颜色
                },
                data: [
                  {yAxis: -1},
                  {yAxis: -0.5},
                  {yAxis: 0},
                  {yAxis: 0.5},
                  {yAxis: 1},
                ]
              },
              itemStyle: {
                normal: {
                  lineStyle: {
                    color: '#92c2ff',/*折线的颜色*/
                    opacity: 1,
                  },
                }
              },
              symbol: "none",
              /*去掉小圆点*/
              name: 'II',
              type: 'line',
              smooth: 0 //显示为平滑的曲线*/
            }]
        })
        chartIII.clear()
        chartIII.setOption({
          animation: true,
          animationDuration: 9900,
          animationEasing: "linear",
          animationEasingUpdate: 'linear',
          animationDurationUpdate: 10100,
          animationDelayUpdate: 0,
          animationThreshold: 10000,
          title: {
            text: 'III导联',
            textStyle: {
              fontSize: 12,
              color: "yellow"
            },
            left: 30
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross'
            }
          },
          grid: {
            left: 25 /*"50px"*/,
            right: 0 /*"15px"*/,
            top: 0,
            bottom: 0.5,
            height: '98%'
          },
          legend: {
            data: ['当前电位'],
            show: true,
            textStyle: {
              color: "#000000"
            } /*图例(legend)说明文字的颜色*/,
            left: "right",
          },
          xAxis: {
            boundaryGap: false,//x轴刻度数字在两个刻度线中间显示还是刻度线下显示
            data: this.time,
            axisTick: {//x轴刻度
              show: false

            },
            axisLabel: { //x轴刻度数字
              show: false,
              textStyle: {
                color: "#8DB6DB"
              }
            },
            splitLine: {/*网格线*/
              show: false,
              interval: 9,//x轴线之间相隔的数量
              lineStyle: {
                opacity: 0.3,//x轴线的粗细
                color: "#8DB6DB"//x轴线的颜色
              },
            }
          },
          yAxis: {
            type: 'value',
            // 显式设置 `axisLine.show` 和 `axisTick.show` 为 `true`
            axisLine: {
              show: true
            },

            min: -1,
            max: 1,
            boundaryGap: false,
            splitNumber: 20,
            axisLabel: { //修改坐标系字体颜色
              show: false,
              textStyle: {
                color: "#000000",

              }
            },
            splitLine: {/*网格线*/
              lineStyle: {
                opacity: 0.3,
                color: "#8DB6DB"
              },
              show: false
            }
          },
          series: [{
            markLine: {
              animation: false,
              symbol: "none",
              silent: true,
              lineStyle: {
                type: "solid",
                color: '#8DB6DB',
                width: 1,
                opacity: 0.5,

              },
              label: {
                show: false,
                position: 'start', // 表现内容展示的位置
                color: '#8DB6DB'  // xAxis,yAxis数字的颜色
              },
              data: [
                {xAxis: 0},
                {xAxis: 50},
                {xAxis: 100},
                {xAxis: 150},
                {xAxis: 200},
                {xAxis: 250},
                {xAxis: 300},
                {xAxis: 350},
                {xAxis: 400},
                {xAxis: 450},
                {xAxis: 500},
                {xAxis: 550},
                {xAxis: 600},
                {xAxis: 650},
                {xAxis: 700},
                {xAxis: 750},
                {xAxis: 800},
                {xAxis: 850},
                {xAxis: 900},
                {xAxis: 950},
                {xAxis: 1000},
                {xAxis: 1050},
                {xAxis: 1100},
                {xAxis: 1150},
                {xAxis: 1200},
                {xAxis: 1250},
                {xAxis: 1300},
                {xAxis: 1350},
                {xAxis: 1400},
                {xAxis: 1450},
                {xAxis: 1500},
                {xAxis: 1550},
                {xAxis: 1600},
                {xAxis: 1650},
                {xAxis: 1700},
                {xAxis: 1750},
                {xAxis: 1800},
                {xAxis: 1850},
                {xAxis: 1900},
                {xAxis: 1950},
                {xAxis: 2000},
                {xAxis: 2050},
                {xAxis: 2100},
                {xAxis: 2150},
                {xAxis: 2200},
                {xAxis: 2250},
                {xAxis: 2300},
                {xAxis: 2350},
                {xAxis: 2400},
                {xAxis: 2450},
                {xAxis: 2500},
                {yAxis: -1},
                {yAxis: -0.5},
                {yAxis: 0},
                {yAxis: 0.5},
                {yAxis: 1},
              ]

            },
            /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
            itemStyle: {
              normal: {
                lineStyle: {
                  color: '#92c2ff',/*折线的颜色*/
                  opacity: 1,
                },
              }
            },
            symbol: "none",
            /*去掉小圆点*/
            name: 'II',
            type: 'line',
            data: III,
            smooth: 0 //显示为平滑的曲线*/
          },
            {
              markLine: {
                animation: false,
                symbol: "none",
                silent: true,
                lineStyle: {
                  type: "solid",
                  color: '#8DB6DB',
                  width: 1,
                  opacity: 0.5,
                },
                label: {
                  show: true,
                  position: 'start', // 表现内容展示的位置
                  color: '#8DB6DB'  // xAxis,yAxis数字的颜色
                },
                data: [
                  {yAxis: -1},
                  {yAxis: -0.5},
                  {yAxis: 0},
                  {yAxis: 0.5},
                  {yAxis: 1},
                ]
              },
              itemStyle: {
                normal: {
                  lineStyle: {
                    color: '#92c2ff',/*折线的颜色*/
                    opacity: 1,
                  },
                }
              },
              symbol: "none",
              /*去掉小圆点*/
              name: 'III',
              type: 'line',
              smooth: 0 //显示为平滑的曲线*/
            }]
        })
        chartaVR.clear()
        chartaVR.setOption({
          animation: true,
          animationDuration: 9900,
          animationEasing: "linear",
          animationEasingUpdate: 'linear',
          animationDurationUpdate: 10100,
          animationDelayUpdate: 0,
          animationThreshold: 10000,

          title: {
            text: 'aVR导联',
            textStyle: {
              fontSize: 12,
              color: "yellow"
            },
            left: 30
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross'
            }
          },
          grid: {
            left: 25 /*"50px"*/,
            right: 0 /*"15px"*/,
            top: 0,
            bottom: 0.5,
            height: '98%'
          },
          legend: {
            data: ['当前电位'],
            show: true,
            textStyle: {
              color: "#000000"
            } /*图例(legend)说明文字的颜色*/,
            left: "right",
          },
          xAxis: {
            boundaryGap: false,//x轴刻度数字在两个刻度线中间显示还是刻度线下显示
            data: this.time,
            axisTick: {//x轴刻度
              show: false

            },
            axisLabel: { //x轴刻度数字
              show: false,
              textStyle: {
                color: "#8DB6DB"
              }
            },
            splitLine: {/*网格线*/
              show: false,
              interval: 9,//x轴线之间相隔的数量
              lineStyle: {
                opacity: 0.3,//x轴线的粗细
                color: "#8DB6DB"//x轴线的颜色
              },
            }
          },
          yAxis: {
            type: 'value',
            // 显式设置 `axisLine.show` 和 `axisTick.show` 为 `true`
            axisLine: {
              show: true
            },

            min: -1,
            max: 1,
            boundaryGap: false,
            splitNumber: 20,
            axisLabel: { //修改坐标系字体颜色
              show: false,
              textStyle: {
                color: "#000000",

              }
            },
            splitLine: {/*网格线*/
              lineStyle: {
                opacity: 0.3,
                color: "#8DB6DB"
              },
              show: false
            }
          },
          series: [{
            markLine: {
              animation: false,
              symbol: "none",
              silent: true,
              lineStyle: {
                type: "solid",
                color: '#8DB6DB',
                width: 1,
                opacity: 0.5,

              },
              label: {
                show: false,
                position: 'start', // 表现内容展示的位置
                color: '#8DB6DB'  // xAxis,yAxis数字的颜色
              },
              data: [
                {xAxis: 0},
                {xAxis: 50},
                {xAxis: 100},
                {xAxis: 150},
                {xAxis: 200},
                {xAxis: 250},
                {xAxis: 300},
                {xAxis: 350},
                {xAxis: 400},
                {xAxis: 450},
                {xAxis: 500},
                {xAxis: 550},
                {xAxis: 600},
                {xAxis: 650},
                {xAxis: 700},
                {xAxis: 750},
                {xAxis: 800},
                {xAxis: 850},
                {xAxis: 900},
                {xAxis: 950},
                {xAxis: 1000},
                {xAxis: 1050},
                {xAxis: 1100},
                {xAxis: 1150},
                {xAxis: 1200},
                {xAxis: 1250},
                {xAxis: 1300},
                {xAxis: 1350},
                {xAxis: 1400},
                {xAxis: 1450},
                {xAxis: 1500},
                {xAxis: 1550},
                {xAxis: 1600},
                {xAxis: 1650},
                {xAxis: 1700},
                {xAxis: 1750},
                {xAxis: 1800},
                {xAxis: 1850},
                {xAxis: 1900},
                {xAxis: 1950},
                {xAxis: 2000},
                {xAxis: 2050},
                {xAxis: 2100},
                {xAxis: 2150},
                {xAxis: 2200},
                {xAxis: 2250},
                {xAxis: 2300},
                {xAxis: 2350},
                {xAxis: 2400},
                {xAxis: 2450},
                {xAxis: 2500},
                {yAxis: -1},
                {yAxis: -0.5},
                {yAxis: 0},
                {yAxis: 0.5},
                {yAxis: 1},
              ]

            },
            /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
            itemStyle: {
              normal: {
                lineStyle: {
                  color: '#92c2ff',/*折线的颜色*/
                  opacity: 1,
                },
              }
            },
            symbol: "none",
            /*去掉小圆点*/
            name: 'aVR',
            type: 'line',
            data: aVR,
            smooth: 0 //显示为平滑的曲线*/
          },
            {
              markLine: {
                animation: false,
                symbol: "none",
                silent: true,
                lineStyle: {
                  type: "solid",
                  color: '#8DB6DB',
                  width: 1,
                  opacity: 0.5,
                },
                label: {
                  show: true,
                  position: 'start', // 表现内容展示的位置
                  color: '#8DB6DB'  // xAxis,yAxis数字的颜色
                },
                data: [
                  {yAxis: -1},
                  {yAxis: -0.5},
                  {yAxis: 0},
                  {yAxis: 0.5},
                  {yAxis: 1},
                ]
              },
              itemStyle: {
                normal: {
                  lineStyle: {
                    color: '#92c2ff',/*折线的颜色*/
                    opacity: 1,
                  },
                }
              },
              symbol: "none",
              /*去掉小圆点*/
              name: 'aVR',
              type: 'line',
              smooth: 0 //显示为平滑的曲线*/
            }]
        })
        chartaVL.clear()
        chartaVL.setOption({
          animation: true,
          animationDuration: 9900,
          animationEasing: "linear",
          animationEasingUpdate: 'linear',
          animationDurationUpdate: 10100,
          animationDelayUpdate: 0,
          animationThreshold: 10000,

          title: {
            text: 'aVL导联',
            textStyle: {
              fontSize: 12,
              color: "yellow"
            },
            left: 30
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross'
            }
          },
          grid: {
            left: 25 /*"50px"*/,
            right: 0 /*"15px"*/,
            top: 0,
            bottom: 0.5,
            height: '98%'
          },
          legend: {
            data: ['当前电位'],
            show: true,
            textStyle: {
              color: "#000000"
            } /*图例(legend)说明文字的颜色*/,
            left: "right",
          },
          xAxis: {
            boundaryGap: false,//x轴刻度数字在两个刻度线中间显示还是刻度线下显示
            data: this.time,
            axisTick: {//x轴刻度
              show: false

            },
            axisLabel: { //x轴刻度数字
              show: false,
              textStyle: {
                color: "#8DB6DB"
              }
            },
            splitLine: {/*网格线*/
              show: false,
              interval: 9,//x轴线之间相隔的数量
              lineStyle: {
                opacity: 0.3,//x轴线的粗细
                color: "#8DB6DB"//x轴线的颜色
              },
            }
          },
          yAxis: {
            type: 'value',
            // 显式设置 `axisLine.show` 和 `axisTick.show` 为 `true`
            axisLine: {
              show: true
            },

            min: -1,
            max: 1,
            boundaryGap: false,
            splitNumber: 20,
            axisLabel: { //修改坐标系字体颜色
              show: false,
              textStyle: {
                color: "#000000",

              }
            },
            splitLine: {/*网格线*/
              lineStyle: {
                opacity: 0.3,
                color: "#8DB6DB"
              },
              show: false
            }
          },
          series: [{
            markLine: {
              animation: false,
              symbol: "none",
              silent: true,
              lineStyle: {
                type: "solid",
                color: '#8DB6DB',
                width: 1,
                opacity: 0.5,

              },
              label: {
                show: false,
                position: 'start', // 表现内容展示的位置
                color: '#8DB6DB'  // xAxis,yAxis数字的颜色
              },
              data: [
                {xAxis: 0},
                {xAxis: 50},
                {xAxis: 100},
                {xAxis: 150},
                {xAxis: 200},
                {xAxis: 250},
                {xAxis: 300},
                {xAxis: 350},
                {xAxis: 400},
                {xAxis: 450},
                {xAxis: 500},
                {xAxis: 550},
                {xAxis: 600},
                {xAxis: 650},
                {xAxis: 700},
                {xAxis: 750},
                {xAxis: 800},
                {xAxis: 850},
                {xAxis: 900},
                {xAxis: 950},
                {xAxis: 1000},
                {xAxis: 1050},
                {xAxis: 1100},
                {xAxis: 1150},
                {xAxis: 1200},
                {xAxis: 1250},
                {xAxis: 1300},
                {xAxis: 1350},
                {xAxis: 1400},
                {xAxis: 1450},
                {xAxis: 1500},
                {xAxis: 1550},
                {xAxis: 1600},
                {xAxis: 1650},
                {xAxis: 1700},
                {xAxis: 1750},
                {xAxis: 1800},
                {xAxis: 1850},
                {xAxis: 1900},
                {xAxis: 1950},
                {xAxis: 2000},
                {xAxis: 2050},
                {xAxis: 2100},
                {xAxis: 2150},
                {xAxis: 2200},
                {xAxis: 2250},
                {xAxis: 2300},
                {xAxis: 2350},
                {xAxis: 2400},
                {xAxis: 2450},
                {xAxis: 2500},
                {yAxis: -1},
                {yAxis: -0.5},
                {yAxis: 0},
                {yAxis: 0.5},
                {yAxis: 1},
              ]

            },
            /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
            itemStyle: {
              normal: {
                lineStyle: {
                  color: '#92c2ff',/*折线的颜色*/
                  opacity: 1,
                },
              }
            },
            symbol: "none",
            /*去掉小圆点*/
            name: 'aVL',
            type: 'line',
            data: aVL,
            smooth: 0 //显示为平滑的曲线*/
          },
            {
              markLine: {
                animation: false,
                symbol: "none",
                silent: true,
                lineStyle: {
                  type: "solid",
                  color: '#8DB6DB',
                  width: 1,
                  opacity: 0.5,
                },
                label: {
                  show: true,
                  position: 'start', // 表现内容展示的位置
                  color: '#8DB6DB'  // xAxis,yAxis数字的颜色
                },
                data: [
                  {yAxis: -1},
                  {yAxis: -0.5},
                  {yAxis: 0},
                  {yAxis: 0.5},
                  {yAxis: 1},
                ]
              },
              itemStyle: {
                normal: {
                  lineStyle: {
                    color: '#92c2ff',/*折线的颜色*/
                    opacity: 1,
                  },
                }
              },
              symbol: "none",
              /*去掉小圆点*/
              name: 'II',
              type: 'line',
              smooth: 0 //显示为平滑的曲线*/
            }]
        })
        chartaVF.clear()
        chartaVF.setOption({
          animation: true,
          animationDuration: 9900,
          animationEasing: "linear",
          animationEasingUpdate: 'linear',
          animationDurationUpdate: 10100,
          animationDelayUpdate: 0,
          animationThreshold: 10000,

          title: {
            text: 'aVF导联',
            textStyle: {
              fontSize: 12,
              color: "yellow"
            },
            left: 30
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross'
            }
          },
          grid: {
            left: 25 /*"50px"*/,
            right: 0 /*"15px"*/,
            top: 0,
            bottom: 0.5,
            height: '98%'
          },
          legend: {
            data: ['当前电位'],
            show: true,
            textStyle: {
              color: "#000000"
            } /*图例(legend)说明文字的颜色*/,
            left: "right",
          },
          xAxis: {
            boundaryGap: false,//x轴刻度数字在两个刻度线中间显示还是刻度线下显示
            data: this.time,
            axisTick: {//x轴刻度
              show: false

            },
            axisLabel: { //x轴刻度数字
              show: false,
              textStyle: {
                color: "#8DB6DB"
              }
            },
            splitLine: {/*网格线*/
              show: false,
              interval: 9,//x轴线之间相隔的数量
              lineStyle: {
                opacity: 0.3,//x轴线的粗细
                color: "#8DB6DB"//x轴线的颜色
              },
            }
          },
          yAxis: {
            type: 'value',
            // 显式设置 `axisLine.show` 和 `axisTick.show` 为 `true`
            axisLine: {
              show: true
            },

            min: -1,
            max: 1,
            boundaryGap: false,
            splitNumber: 20,
            axisLabel: { //修改坐标系字体颜色
              show: false,
              textStyle: {
                color: "#000000",

              }
            },
            splitLine: {/*网格线*/
              lineStyle: {
                opacity: 0.3,
                color: "#8DB6DB"
              },
              show: false
            }
          },
          series: [{
            markLine: {
              animation: false,
              symbol: "none",
              silent: true,
              lineStyle: {
                type: "solid",
                color: '#8DB6DB',
                width: 1,
                opacity: 0.5,

              },
              label: {
                show: false,
                position: 'start', // 表现内容展示的位置
                color: '#8DB6DB'  // xAxis,yAxis数字的颜色
              },
              data: [
                {xAxis: 0},
                {xAxis: 50},
                {xAxis: 100},
                {xAxis: 150},
                {xAxis: 200},
                {xAxis: 250},
                {xAxis: 300},
                {xAxis: 350},
                {xAxis: 400},
                {xAxis: 450},
                {xAxis: 500},
                {xAxis: 550},
                {xAxis: 600},
                {xAxis: 650},
                {xAxis: 700},
                {xAxis: 750},
                {xAxis: 800},
                {xAxis: 850},
                {xAxis: 900},
                {xAxis: 950},
                {xAxis: 1000},
                {xAxis: 1050},
                {xAxis: 1100},
                {xAxis: 1150},
                {xAxis: 1200},
                {xAxis: 1250},
                {xAxis: 1300},
                {xAxis: 1350},
                {xAxis: 1400},
                {xAxis: 1450},
                {xAxis: 1500},
                {xAxis: 1550},
                {xAxis: 1600},
                {xAxis: 1650},
                {xAxis: 1700},
                {xAxis: 1750},
                {xAxis: 1800},
                {xAxis: 1850},
                {xAxis: 1900},
                {xAxis: 1950},
                {xAxis: 2000},
                {xAxis: 2050},
                {xAxis: 2100},
                {xAxis: 2150},
                {xAxis: 2200},
                {xAxis: 2250},
                {xAxis: 2300},
                {xAxis: 2350},
                {xAxis: 2400},
                {xAxis: 2450},
                {xAxis: 2500},
                {yAxis: -1},
                {yAxis: -0.5},
                {yAxis: 0},
                {yAxis: 0.5},
                {yAxis: 1},
              ]

            },
            /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
            itemStyle: {
              normal: {
                lineStyle: {
                  color: '#92c2ff',/*折线的颜色*/
                  opacity: 1,
                },
              }
            },
            symbol: "none",
            /*去掉小圆点*/
            name: 'aVF',
            type: 'line',
            data: aVF,
            smooth: 0 //显示为平滑的曲线*/
          },
            {
              markLine: {
                animation: false,
                symbol: "none",
                silent: true,
                lineStyle: {
                  type: "solid",
                  color: '#8DB6DB',
                  width: 1,
                  opacity: 0.5,
                },
                label: {
                  show: true,
                  position: 'start', // 表现内容展示的位置
                  color: '#8DB6DB'  // xAxis,yAxis数字的颜色
                },
                data: [
                  {yAxis: -1},
                  {yAxis: -0.5},
                  {yAxis: 0},
                  {yAxis: 0.5},
                  {yAxis: 1},
                ]
              },
              itemStyle: {
                normal: {
                  lineStyle: {
                    color: '#92c2ff',/*折线的颜色*/
                    opacity: 1,
                  },
                }
              },
              symbol: "none",
              /*去掉小圆点*/
              name: 'II',
              type: 'line',
              smooth: 0 //显示为平滑的曲线*/
            }]
        })
        chartV1.clear()
        chartV1.setOption({
          animation: true,
          animationDuration: 9900,
          animationEasing: "linear",
          animationEasingUpdate: 'linear',
          animationDurationUpdate: 10100,
          animationDelayUpdate: 0,
          animationThreshold: 10000,

          title: {
            text: 'V1导联',
            textStyle: {
              fontSize: 12,
              color: "yellow"
            },
            left: 30
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross'
            }
          },
          grid: {
            left: 25 /*"50px"*/,
            right: 0 /*"15px"*/,
            top: 0,
            bottom: 0.5,
            height: '98%'
          },
          legend: {
            data: ['当前电位'],
            show: true,
            textStyle: {
              color: "#000000"
            } /*图例(legend)说明文字的颜色*/,
            left: "right",
          },
          xAxis: {
            boundaryGap: false,//x轴刻度数字在两个刻度线中间显示还是刻度线下显示
            data: this.time,
            axisTick: {//x轴刻度
              show: false

            },
            axisLabel: { //x轴刻度数字
              show: false,
              textStyle: {
                color: "#8DB6DB"
              }
            },
            splitLine: {/*网格线*/
              show: false,
              interval: 9,//x轴线之间相隔的数量
              lineStyle: {
                opacity: 0.3,//x轴线的粗细
                color: "#8DB6DB"//x轴线的颜色
              },
            }
          },
          yAxis: {
            type: 'value',
            // 显式设置 `axisLine.show` 和 `axisTick.show` 为 `true`
            axisLine: {
              show: true
            },

            min: -1,
            max: 1,
            boundaryGap: false,
            splitNumber: 20,
            axisLabel: { //修改坐标系字体颜色
              show: false,
              textStyle: {
                color: "#000000",

              }
            },
            splitLine: {/*网格线*/
              lineStyle: {
                opacity: 0.3,
                color: "#8DB6DB"
              },
              show: false
            }
          },
          series: [{
            markLine: {
              animation: false,
              symbol: "none",
              silent: true,
              lineStyle: {
                type: "solid",
                color: '#8DB6DB',
                width: 1,
                opacity: 0.5,

              },
              label: {
                show: false,
                position: 'start', // 表现内容展示的位置
                color: '#8DB6DB'  // xAxis,yAxis数字的颜色
              },
              data: [
                {xAxis: 0},
                {xAxis: 50},
                {xAxis: 100},
                {xAxis: 150},
                {xAxis: 200},
                {xAxis: 250},
                {xAxis: 300},
                {xAxis: 350},
                {xAxis: 400},
                {xAxis: 450},
                {xAxis: 500},
                {xAxis: 550},
                {xAxis: 600},
                {xAxis: 650},
                {xAxis: 700},
                {xAxis: 750},
                {xAxis: 800},
                {xAxis: 850},
                {xAxis: 900},
                {xAxis: 950},
                {xAxis: 1000},
                {xAxis: 1050},
                {xAxis: 1100},
                {xAxis: 1150},
                {xAxis: 1200},
                {xAxis: 1250},
                {xAxis: 1300},
                {xAxis: 1350},
                {xAxis: 1400},
                {xAxis: 1450},
                {xAxis: 1500},
                {xAxis: 1550},
                {xAxis: 1600},
                {xAxis: 1650},
                {xAxis: 1700},
                {xAxis: 1750},
                {xAxis: 1800},
                {xAxis: 1850},
                {xAxis: 1900},
                {xAxis: 1950},
                {xAxis: 2000},
                {xAxis: 2050},
                {xAxis: 2100},
                {xAxis: 2150},
                {xAxis: 2200},
                {xAxis: 2250},
                {xAxis: 2300},
                {xAxis: 2350},
                {xAxis: 2400},
                {xAxis: 2450},
                {xAxis: 2500},
                {yAxis: -1},
                {yAxis: -0.5},
                {yAxis: 0},
                {yAxis: 0.5},
                {yAxis: 1},
              ]

            },
            /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
            itemStyle: {
              normal: {
                lineStyle: {
                  color: '#92c2ff',/*折线的颜色*/
                  opacity: 1,
                },
              }
            },
            symbol: "none",
            /*去掉小圆点*/
            name: 'V1',
            type: 'line',
            data: V1,
            smooth: 0 //显示为平滑的曲线*/
          },
            {
              markLine: {
                animation: false,
                symbol: "none",
                silent: true,
                lineStyle: {
                  type: "solid",
                  color: '#8DB6DB',
                  width: 1,
                  opacity: 0.5,
                },
                label: {
                  show: true,
                  position: 'start', // 表现内容展示的位置
                  color: '#8DB6DB'  // xAxis,yAxis数字的颜色
                },
                data: [
                  {yAxis: -1},
                  {yAxis: -0.5},
                  {yAxis: 0},
                  {yAxis: 0.5},
                  {yAxis: 1},
                ]
              },
              itemStyle: {
                normal: {
                  lineStyle: {
                    color: '#92c2ff',/*折线的颜色*/
                    opacity: 1,
                  },
                }
              },
              symbol: "none",
              /*去掉小圆点*/
              name: 'V1',
              type: 'line',
              smooth: 0 //显示为平滑的曲线*/
            }]
        })
        chartV2.clear()
        chartV2.setOption({
          animation: true,
          animationDuration: 9900,
          animationEasing: "linear",
          animationEasingUpdate: 'linear',
          animationDurationUpdate: 10100,
          animationDelayUpdate: 0,
          animationThreshold: 10000,

          title: {
            text: 'V2导联',
            textStyle: {
              fontSize: 12,
              color: "yellow"
            },
            left: 30
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross'
            }
          },
          grid: {
            left: 25 /*"50px"*/,
            right: 0 /*"15px"*/,
            top: 0,
            bottom: 0.5,
            height: '98%'
          },
          legend: {
            data: ['当前电位'],
            show: true,
            textStyle: {
              color: "#000000"
            } /*图例(legend)说明文字的颜色*/,
            left: "right",
          },
          xAxis: {
            boundaryGap: false,//x轴刻度数字在两个刻度线中间显示还是刻度线下显示
            data: this.time,
            axisTick: {//x轴刻度
              show: false

            },
            axisLabel: { //x轴刻度数字
              show: false,
              textStyle: {
                color: "#8DB6DB"
              }
            },
            splitLine: {/*网格线*/
              show: false,
              interval: 9,//x轴线之间相隔的数量
              lineStyle: {
                opacity: 0.3,//x轴线的粗细
                color: "#8DB6DB"//x轴线的颜色
              },
            }
          },
          yAxis: {
            type: 'value',
            // 显式设置 `axisLine.show` 和 `axisTick.show` 为 `true`
            axisLine: {
              show: true
            },

            min: -1,
            max: 1,
            boundaryGap: false,
            splitNumber: 20,
            axisLabel: { //修改坐标系字体颜色
              show: false,
              textStyle: {
                color: "#000000",

              }
            },
            splitLine: {/*网格线*/
              lineStyle: {
                opacity: 0.3,
                color: "#8DB6DB"
              },
              show: false
            }
          },
          series: [{
            markLine: {
              animation: false,
              symbol: "none",
              silent: true,
              lineStyle: {
                type: "solid",
                color: '#8DB6DB',
                width: 1,
                opacity: 0.5,

              },
              label: {
                show: false,
                position: 'start', // 表现内容展示的位置
                color: '#8DB6DB'  // xAxis,yAxis数字的颜色
              },
              data: [
                {xAxis: 0},
                {xAxis: 50},
                {xAxis: 100},
                {xAxis: 150},
                {xAxis: 200},
                {xAxis: 250},
                {xAxis: 300},
                {xAxis: 350},
                {xAxis: 400},
                {xAxis: 450},
                {xAxis: 500},
                {xAxis: 550},
                {xAxis: 600},
                {xAxis: 650},
                {xAxis: 700},
                {xAxis: 750},
                {xAxis: 800},
                {xAxis: 850},
                {xAxis: 900},
                {xAxis: 950},
                {xAxis: 1000},
                {xAxis: 1050},
                {xAxis: 1100},
                {xAxis: 1150},
                {xAxis: 1200},
                {xAxis: 1250},
                {xAxis: 1300},
                {xAxis: 1350},
                {xAxis: 1400},
                {xAxis: 1450},
                {xAxis: 1500},
                {xAxis: 1550},
                {xAxis: 1600},
                {xAxis: 1650},
                {xAxis: 1700},
                {xAxis: 1750},
                {xAxis: 1800},
                {xAxis: 1850},
                {xAxis: 1900},
                {xAxis: 1950},
                {xAxis: 2000},
                {xAxis: 2050},
                {xAxis: 2100},
                {xAxis: 2150},
                {xAxis: 2200},
                {xAxis: 2250},
                {xAxis: 2300},
                {xAxis: 2350},
                {xAxis: 2400},
                {xAxis: 2450},
                {xAxis: 2500},
                {yAxis: -1},
                {yAxis: -0.5},
                {yAxis: 0},
                {yAxis: 0.5},
                {yAxis: 1},
              ]

            },
            /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
            itemStyle: {
              normal: {
                lineStyle: {
                  color: '#92c2ff',/*折线的颜色*/
                  opacity: 1,
                },
              }
            },
            symbol: "none",
            /*去掉小圆点*/
            name: 'V2',
            type: 'line',
            data: V2,
            smooth: 0 //显示为平滑的曲线*/
          },
            {
              markLine: {
                animation: false,
                symbol: "none",
                silent: true,
                lineStyle: {
                  type: "solid",
                  color: '#8DB6DB',
                  width: 1,
                  opacity: 0.5,
                },
                label: {
                  show: true,
                  position: 'start', // 表现内容展示的位置
                  color: '#8DB6DB'  // xAxis,yAxis数字的颜色
                },
                data: [
                  {yAxis: -1},
                  {yAxis: -0.5},
                  {yAxis: 0},
                  {yAxis: 0.5},
                  {yAxis: 1},
                ]
              },
              itemStyle: {
                normal: {
                  lineStyle: {
                    color: '#92c2ff',/*折线的颜色*/
                    opacity: 1,
                  },
                }
              },
              symbol: "none",
              /*去掉小圆点*/
              name: 'V2',
              type: 'line',
              smooth: 0 //显示为平滑的曲线*/
            }]
        })
        chartV3.clear()
        chartV3.setOption({
          animation: true,
          animationDuration: 9900,
          animationEasing: "linear",
          animationEasingUpdate: 'linear',
          animationDurationUpdate: 10100,
          animationDelayUpdate: 0,
          animationThreshold: 10000,

          title: {
            text: 'V3导联',
            textStyle: {
              fontSize: 12,
              color: "yellow"
            },
            left: 30
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross'
            }
          },
          grid: {
            left: 25 /*"50px"*/,
            right: 0 /*"15px"*/,
            top: 0,
            bottom: 0.5,
            height: '98%'
          },
          legend: {
            data: ['当前电位'],
            show: true,
            textStyle: {
              color: "#000000"
            } /*图例(legend)说明文字的颜色*/,
            left: "right",
          },
          xAxis: {
            boundaryGap: false,//x轴刻度数字在两个刻度线中间显示还是刻度线下显示
            data: this.time,
            axisTick: {//x轴刻度
              show: false

            },
            axisLabel: { //x轴刻度数字
              show: false,
              textStyle: {
                color: "#8DB6DB"
              }
            },
            splitLine: {/*网格线*/
              show: false,
              interval: 9,//x轴线之间相隔的数量
              lineStyle: {
                opacity: 0.3,//x轴线的粗细
                color: "#8DB6DB"//x轴线的颜色
              },
            }
          },
          yAxis: {
            type: 'value',
            // 显式设置 `axisLine.show` 和 `axisTick.show` 为 `true`
            axisLine: {
              show: true
            },

            min: -1,
            max: 1,
            boundaryGap: false,
            splitNumber: 20,
            axisLabel: { //修改坐标系字体颜色
              show: false,
              textStyle: {
                color: "#000000",

              }
            },
            splitLine: {/*网格线*/
              lineStyle: {
                opacity: 0.3,
                color: "#8DB6DB"
              },
              show: false
            }
          },
          series: [{
            markLine: {
              animation: false,
              symbol: "none",
              silent: true,
              lineStyle: {
                type: "solid",
                color: '#8DB6DB',
                width: 1,
                opacity: 0.5,

              },
              label: {
                show: false,
                position: 'start', // 表现内容展示的位置
                color: '#8DB6DB'  // xAxis,yAxis数字的颜色
              },
              data: [
                {xAxis: 0},
                {xAxis: 50},
                {xAxis: 100},
                {xAxis: 150},
                {xAxis: 200},
                {xAxis: 250},
                {xAxis: 300},
                {xAxis: 350},
                {xAxis: 400},
                {xAxis: 450},
                {xAxis: 500},
                {xAxis: 550},
                {xAxis: 600},
                {xAxis: 650},
                {xAxis: 700},
                {xAxis: 750},
                {xAxis: 800},
                {xAxis: 850},
                {xAxis: 900},
                {xAxis: 950},
                {xAxis: 1000},
                {xAxis: 1050},
                {xAxis: 1100},
                {xAxis: 1150},
                {xAxis: 1200},
                {xAxis: 1250},
                {xAxis: 1300},
                {xAxis: 1350},
                {xAxis: 1400},
                {xAxis: 1450},
                {xAxis: 1500},
                {xAxis: 1550},
                {xAxis: 1600},
                {xAxis: 1650},
                {xAxis: 1700},
                {xAxis: 1750},
                {xAxis: 1800},
                {xAxis: 1850},
                {xAxis: 1900},
                {xAxis: 1950},
                {xAxis: 2000},
                {xAxis: 2050},
                {xAxis: 2100},
                {xAxis: 2150},
                {xAxis: 2200},
                {xAxis: 2250},
                {xAxis: 2300},
                {xAxis: 2350},
                {xAxis: 2400},
                {xAxis: 2450},
                {xAxis: 2500},
                {yAxis: -1},
                {yAxis: -0.5},
                {yAxis: 0},
                {yAxis: 0.5},
                {yAxis: 1},
              ]

            },
            /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
            itemStyle: {
              normal: {
                lineStyle: {
                  color: '#92c2ff',/*折线的颜色*/
                  opacity: 1,
                },
              }
            },
            symbol: "none",
            /*去掉小圆点*/
            name: 'V3',
            type: 'line',
            data: V3,
            smooth: 0 //显示为平滑的曲线*/
          },
            {
              markLine: {
                animation: false,
                symbol: "none",
                silent: true,
                lineStyle: {
                  type: "solid",
                  color: '#8DB6DB',
                  width: 1,
                  opacity: 0.5,
                },
                label: {
                  show: true,
                  position: 'start', // 表现内容展示的位置
                  color: '#8DB6DB'  // xAxis,yAxis数字的颜色
                },
                data: [
                  {yAxis: -1},
                  {yAxis: -0.5},
                  {yAxis: 0},
                  {yAxis: 0.5},
                  {yAxis: 1},
                ]
              },
              itemStyle: {
                normal: {
                  lineStyle: {
                    color: '#92c2ff',/*折线的颜色*/
                    opacity: 1,
                  },
                }
              },
              symbol: "none",
              /*去掉小圆点*/
              name: 'V3',
              type: 'line',
              smooth: 0 //显示为平滑的曲线*/
            }]
        })
        chartV4.clear()
        chartV4.setOption({
          animation: true,
          animationDuration: 9900,
          animationEasing: "linear",
          animationEasingUpdate: 'linear',
          animationDurationUpdate: 10100,
          animationDelayUpdate: 0,
          animationThreshold: 10000,

          title: {
            text: 'V4导联',
            textStyle: {
              fontSize: 12,
              color: "yellow"
            },
            left: 30
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross'
            }
          },
          grid: {
            left: 25 /*"50px"*/,
            right: 0 /*"15px"*/,
            top: 0,
            bottom: 0.5,
            height: '98%'
          },
          legend: {
            data: ['当前电位'],
            show: true,
            textStyle: {
              color: "#000000"
            } /*图例(legend)说明文字的颜色*/,
            left: "right",
          },
          xAxis: {
            boundaryGap: false,//x轴刻度数字在两个刻度线中间显示还是刻度线下显示
            data: this.time,
            axisTick: {//x轴刻度
              show: false

            },
            axisLabel: { //x轴刻度数字
              show: false,
              textStyle: {
                color: "#8DB6DB"
              }
            },
            splitLine: {/*网格线*/
              show: false,
              interval: 9,//x轴线之间相隔的数量
              lineStyle: {
                opacity: 0.3,//x轴线的粗细
                color: "#8DB6DB"//x轴线的颜色
              },
            }
          },
          yAxis: {
            type: 'value',
            // 显式设置 `axisLine.show` 和 `axisTick.show` 为 `true`
            axisLine: {
              show: true
            },

            min: -1,
            max: 1,
            boundaryGap: false,
            splitNumber: 20,
            axisLabel: { //修改坐标系字体颜色
              show: false,
              textStyle: {
                color: "#000000",

              }
            },
            splitLine: {/*网格线*/
              lineStyle: {
                opacity: 0.3,
                color: "#8DB6DB"
              },
              show: false
            }
          },
          series: [{
            markLine: {
              animation: false,
              symbol: "none",
              silent: true,
              lineStyle: {
                type: "solid",
                color: '#8DB6DB',
                width: 1,
                opacity: 0.5,

              },
              label: {
                show: false,
                position: 'start', // 表现内容展示的位置
                color: '#8DB6DB'  // xAxis,yAxis数字的颜色
              },
              data: [
                {xAxis: 0},
                {xAxis: 50},
                {xAxis: 100},
                {xAxis: 150},
                {xAxis: 200},
                {xAxis: 250},
                {xAxis: 300},
                {xAxis: 350},
                {xAxis: 400},
                {xAxis: 450},
                {xAxis: 500},
                {xAxis: 550},
                {xAxis: 600},
                {xAxis: 650},
                {xAxis: 700},
                {xAxis: 750},
                {xAxis: 800},
                {xAxis: 850},
                {xAxis: 900},
                {xAxis: 950},
                {xAxis: 1000},
                {xAxis: 1050},
                {xAxis: 1100},
                {xAxis: 1150},
                {xAxis: 1200},
                {xAxis: 1250},
                {xAxis: 1300},
                {xAxis: 1350},
                {xAxis: 1400},
                {xAxis: 1450},
                {xAxis: 1500},
                {xAxis: 1550},
                {xAxis: 1600},
                {xAxis: 1650},
                {xAxis: 1700},
                {xAxis: 1750},
                {xAxis: 1800},
                {xAxis: 1850},
                {xAxis: 1900},
                {xAxis: 1950},
                {xAxis: 2000},
                {xAxis: 2050},
                {xAxis: 2100},
                {xAxis: 2150},
                {xAxis: 2200},
                {xAxis: 2250},
                {xAxis: 2300},
                {xAxis: 2350},
                {xAxis: 2400},
                {xAxis: 2450},
                {xAxis: 2500},
                {yAxis: -1},
                {yAxis: -0.5},
                {yAxis: 0},
                {yAxis: 0.5},
                {yAxis: 1},
              ]

            },
            /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
            itemStyle: {
              normal: {
                lineStyle: {
                  color: '#92c2ff',/*折线的颜色*/
                  opacity: 1,
                },
              }
            },
            symbol: "none",
            /*去掉小圆点*/
            name: 'V4',
            type: 'line',
            data: V4,
            smooth: 0 //显示为平滑的曲线*/
          },
            {
              markLine: {
                animation: false,
                symbol: "none",
                silent: true,
                lineStyle: {
                  type: "solid",
                  color: '#8DB6DB',
                  width: 1,
                  opacity: 0.5,
                },
                label: {
                  show: true,
                  position: 'start', // 表现内容展示的位置
                  color: '#8DB6DB'  // xAxis,yAxis数字的颜色
                },
                data: [
                  {yAxis: -1},
                  {yAxis: -0.5},
                  {yAxis: 0},
                  {yAxis: 0.5},
                  {yAxis: 1},
                ]
              },
              itemStyle: {
                normal: {
                  lineStyle: {
                    color: '#92c2ff',/*折线的颜色*/
                    opacity: 1,
                  },
                }
              },
              symbol: "none",
              /*去掉小圆点*/
              name: 'V4',
              type: 'line',
              smooth: 0 //显示为平滑的曲线*/
            }]
        })
        chartV5.clear()
        chartV5.setOption({
          animation: true,
          animationDuration: 9900,
          animationEasing: "linear",
          animationEasingUpdate: 'linear',
          animationDurationUpdate: 10100,
          animationDelayUpdate: 0,
          animationThreshold: 10000,

          title: {
            text: 'V5导联',
            textStyle: {
              fontSize: 12,
              color: "yellow"
            },
            left: 30
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross'
            }
          },
          grid: {
            left: 25 /*"50px"*/,
            right: 0 /*"15px"*/,
            top: 0,
            bottom: 0.5,
            height: '98%'
          },
          legend: {
            data: ['当前电位'],
            show: true,
            textStyle: {
              color: "#000000"
            } /*图例(legend)说明文字的颜色*/,
            left: "right",
          },
          xAxis: {
            boundaryGap: false,//x轴刻度数字在两个刻度线中间显示还是刻度线下显示
            data: this.time,
            axisTick: {//x轴刻度
              show: false

            },
            axisLabel: { //x轴刻度数字
              show: false,
              textStyle: {
                color: "#8DB6DB"
              }
            },
            splitLine: {/*网格线*/
              show: false,
              interval: 9,//x轴线之间相隔的数量
              lineStyle: {
                opacity: 0.3,//x轴线的粗细
                color: "#8DB6DB"//x轴线的颜色
              },
            }
          },
          yAxis: {
            type: 'value',
            // 显式设置 `axisLine.show` 和 `axisTick.show` 为 `true`
            axisLine: {
              show: true
            },

            min: -1,
            max: 1,
            boundaryGap: false,
            splitNumber: 20,
            axisLabel: { //修改坐标系字体颜色
              show: false,
              textStyle: {
                color: "#000000",

              }
            },
            splitLine: {/*网格线*/
              lineStyle: {
                opacity: 0.3,
                color: "#8DB6DB"
              },
              show: false
            }
          },
          series: [{
            markLine: {
              animation: false,
              symbol: "none",
              silent: true,
              lineStyle: {
                type: "solid",
                color: '#8DB6DB',
                width: 1,
                opacity: 0.5,

              },
              label: {
                show: false,
                position: 'start', // 表现内容展示的位置
                color: '#8DB6DB'  // xAxis,yAxis数字的颜色
              },
              data: [
                {xAxis: 0},
                {xAxis: 50},
                {xAxis: 100},
                {xAxis: 150},
                {xAxis: 200},
                {xAxis: 250},
                {xAxis: 300},
                {xAxis: 350},
                {xAxis: 400},
                {xAxis: 450},
                {xAxis: 500},
                {xAxis: 550},
                {xAxis: 600},
                {xAxis: 650},
                {xAxis: 700},
                {xAxis: 750},
                {xAxis: 800},
                {xAxis: 850},
                {xAxis: 900},
                {xAxis: 950},
                {xAxis: 1000},
                {xAxis: 1050},
                {xAxis: 1100},
                {xAxis: 1150},
                {xAxis: 1200},
                {xAxis: 1250},
                {xAxis: 1300},
                {xAxis: 1350},
                {xAxis: 1400},
                {xAxis: 1450},
                {xAxis: 1500},
                {xAxis: 1550},
                {xAxis: 1600},
                {xAxis: 1650},
                {xAxis: 1700},
                {xAxis: 1750},
                {xAxis: 1800},
                {xAxis: 1850},
                {xAxis: 1900},
                {xAxis: 1950},
                {xAxis: 2000},
                {xAxis: 2050},
                {xAxis: 2100},
                {xAxis: 2150},
                {xAxis: 2200},
                {xAxis: 2250},
                {xAxis: 2300},
                {xAxis: 2350},
                {xAxis: 2400},
                {xAxis: 2450},
                {xAxis: 2500},
                {yAxis: -1},
                {yAxis: -0.5},
                {yAxis: 0},
                {yAxis: 0.5},
                {yAxis: 1},
              ]

            },
            /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
            itemStyle: {
              normal: {
                lineStyle: {
                  color: '#92c2ff',/*折线的颜色*/
                  opacity: 1,
                },
              }
            },
            symbol: "none",
            /*去掉小圆点*/
            name: 'V5',
            type: 'line',
            data: V5,
            smooth: 0 //显示为平滑的曲线*/
          },
            {
              markLine: {
                animation: false,
                symbol: "none",
                silent: true,
                lineStyle: {
                  type: "solid",
                  color: '#8DB6DB',
                  width: 1,
                  opacity: 0.5,
                },
                label: {
                  show: true,
                  position: 'start', // 表现内容展示的位置
                  color: '#8DB6DB'  // xAxis,yAxis数字的颜色
                },
                data: [
                  {yAxis: -1},
                  {yAxis: -0.5},
                  {yAxis: 0},
                  {yAxis: 0.5},
                  {yAxis: 1},
                ]
              },
              itemStyle: {
                normal: {
                  lineStyle: {
                    color: '#92c2ff',/*折线的颜色*/
                    opacity: 1,
                  },
                }
              },
              symbol: "none",
              /*去掉小圆点*/
              name: 'V5',
              type: 'line',
              smooth: 0 //显示为平滑的曲线*/
            }]
        })
        chartV6.clear()
        chartV6.setOption({
          animation: true,
          animationDuration: 9900,
          animationEasing: "linear",
          animationEasingUpdate: 'linear',
          animationDurationUpdate: 10100,
          animationDelayUpdate: 0,
          animationThreshold: 10000,

          title: {
            text: 'V6导联',
            textStyle: {
              fontSize: 12,
              color: "yellow"
            },
            left: 30
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross'
            }
          },
          grid: {
            left: 25 /*"50px"*/,
            right: 0 /*"15px"*/,
            top: 0,
            bottom: 17.5,
          },
          legend: {
            data: ['当前电位'],
            show: true,
            textStyle: {
              color: "#000000"
            } /*图例(legend)说明文字的颜色*/,
            left: "right",
          },
          xAxis: {
            boundaryGap: false,//x轴刻度数字在两个刻度线中间显示还是刻度线下显示
            data: this.time,
            axisTick: {//x轴刻度
              show: false
            },
            axisLabel: { //x轴刻度数字
              show: true,
              textStyle: {
                color: "#8DB6DB"
              }
            },
            splitLine: {/*网格线*/
              show: false,
              interval: 9,//x轴线之间相隔的数量
              lineStyle: {
                opacity: 0.3,//x轴线的粗细
                color: "#8DB6DB"//x轴线的颜色
              },
            }
          },
          yAxis: {
            type: 'value',
            // 显式设置 `axisLine.show` 和 `axisTick.show` 为 `true`
            axisLine: {
              show: true
            },

            min: -1,
            max: 1,
            boundaryGap: false,
            splitNumber: 20,
            axisLabel: { //修改坐标系字体颜色
              show: false,
              textStyle: {
                color: "#000000",

              }
            },
            splitLine: {/*网格线*/
              lineStyle: {
                opacity: 0.3,
                color: "#8DB6DB"
              },
              show: false
            }
          },
          series: [{
            markLine: {
              animation: false,
              symbol: "none",
              silent: true,
              lineStyle: {
                type: "solid",
                color: '#8DB6DB',
                width: 1,
                opacity: 0.5,

              },
              label: {
                show: false,
                position: 'start', // 表现内容展示的位置
                color: '#8DB6DB'  // xAxis,yAxis数字的颜色
              },
              data: [
                {xAxis: 0},
                {xAxis: 50},
                {xAxis: 100},
                {xAxis: 150},
                {xAxis: 200},
                {xAxis: 250},
                {xAxis: 300},
                {xAxis: 350},
                {xAxis: 400},
                {xAxis: 450},
                {xAxis: 500},
                {xAxis: 550},
                {xAxis: 600},
                {xAxis: 650},
                {xAxis: 700},
                {xAxis: 750},
                {xAxis: 800},
                {xAxis: 850},
                {xAxis: 900},
                {xAxis: 950},
                {xAxis: 1000},
                {xAxis: 1050},
                {xAxis: 1100},
                {xAxis: 1150},
                {xAxis: 1200},
                {xAxis: 1250},
                {xAxis: 1300},
                {xAxis: 1350},
                {xAxis: 1400},
                {xAxis: 1450},
                {xAxis: 1500},
                {xAxis: 1550},
                {xAxis: 1600},
                {xAxis: 1650},
                {xAxis: 1700},
                {xAxis: 1750},
                {xAxis: 1800},
                {xAxis: 1850},
                {xAxis: 1900},
                {xAxis: 1950},
                {xAxis: 2000},
                {xAxis: 2050},
                {xAxis: 2100},
                {xAxis: 2150},
                {xAxis: 2200},
                {xAxis: 2250},
                {xAxis: 2300},
                {xAxis: 2350},
                {xAxis: 2400},
                {xAxis: 2450},
                {xAxis: 2500},
                {yAxis: -1},
                {yAxis: -0.5},
                {yAxis: 0},
                {yAxis: 0.5},
                {yAxis: 1},
              ]

            },
            /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
            itemStyle: {
              normal: {
                lineStyle: {
                  color: '#92c2ff',/*折线的颜色*/
                  opacity: 1,
                },
              }
            },
            symbol: "none",
            /*去掉小圆点*/
            name: 'V6',
            type: 'line',
            data: V6,
            smooth: 0 //显示为平滑的曲线*/
          },
            {
              markLine: {
                animation: false,
                symbol: "none",
                silent: true,
                lineStyle: {
                  type: "solid",
                  color: '#8DB6DB',
                  width: 1,
                  opacity: 0.5,
                },
                label: {
                  show: true,
                  position: 'start', // 表现内容展示的位置
                  color: '#8DB6DB'  // xAxis,yAxis数字的颜色
                },
                data: [
                  {yAxis: -1},
                  {yAxis: -0.5},
                  {yAxis: 0},
                  {yAxis: 0.5},
                  {yAxis: 1},
                ]
              },
              itemStyle: {
                normal: {
                  lineStyle: {
                    color: '#92c2ff',/*折线的颜色*/
                    opacity: 1,
                  },
                }
              },
              symbol: "none",
              /*去掉小圆点*/
              name: 'V6',
              type: 'line',
              smooth: 0 //显示为平滑的曲线*/
            }]
        })
         this.$http.post('https://server.mindyard.cn:84/detect_decg',
           JSON.stringify({
             "deviceSn": this.deviceSn,
             "ts": 1,
           }), {
             contentType: "application/json",
             dataType: "json",
             headers: {
               "user": "zzu",
               "password": "zzu123"
             },
           }
         ).then(res=>{
           res.data.result.hr_mean = res.data.result.hr_mean.toFixed()
           this.newData=res.data.result
         })
           .catch(err=>{
             console.log("错误信息"+err)
           })
        let ts=2
        if(!this.timer){
          this.timer=window.setInterval(()=>{
            if(this.$route.path!=='/Screen/detail'){
              window.clearInterval(this.timer)
              this.timer=null
            }
          if(this.newData){
            ts++
            this.data=this.newData
            I=this.newData.data.I
            II=this.newData.data.II
            III=this.newData.data.III
            aVR=this.newData.data.aVR
            aVL=this.newData.data.aVL
            aVF=this.newData.data.aVF
            V1=this.newData.data.V1
            V2=this.newData.data.V2
            V3=this.newData.data.V3
            V4=this.newData.data.V4
            V5=this.newData.data.V5
            V6=this.newData.data.V6
            this.setMeanChart()
            this.setsdnn()
            this.setpnn50()
            this.setpnn20()
            this.setAna()
            chartI.clear()
            chartI.setOption({
              animation: true,
              animationDuration: 9900,
              animationEasing: "linear",
              animationEasingUpdate: 'linear',
              animationDurationUpdate: 10100,
              animationDelayUpdate: 0,
              animationThreshold: 10000,
              title: {
                text: 'I导联',
                textStyle: {
                  fontSize: 12,
                  color: "yellow"
                },
                left: 30
              },
              tooltip: {
                trigger: 'axis',
                axisPointer: {
                  type: 'cross'
                }
              },
              grid: {
                left: 25 /*"50px"*/,
                right: 0 /*"15px"*/,
                top: 0,
                bottom: 0.5,
                height: '98%'
              },
              legend: {
                data: ['当前电位'],
                show: true,
                textStyle: {
                  color: "#000000"
                } /*图例(legend)说明文字的颜色*/,
                left: "right",
              },
              xAxis: {
                boundaryGap: false,//x轴刻度数字在两个刻度线中间显示还是刻度线下显示
                data: this.time,
                axisTick: {//x轴刻度
                  show: false

                },
                axisLabel: { //x轴刻度数字
                  show: false,
                  textStyle: {
                    color: "#8DB6DB"
                  }
                },
                splitLine: {/*网格线*/
                  show: false,
                  interval: 9,//x轴线之间相隔的数量
                  lineStyle: {
                    opacity: 0.3,//x轴线的粗细
                    color: "#8DB6DB"//x轴线的颜色
                  },
                }
              },
              yAxis: {
                type: 'value',
                // 显式设置 `axisLine.show` 和 `axisTick.show` 为 `true`
                axisLine: {
                  show: true
                },

                min: -1,
                max: 1,
                boundaryGap: false,
                splitNumber: 20,
                axisLabel: { //修改坐标系字体颜色
                  show: false,
                  textStyle: {
                    color: "#000000",
                  }
                },
                splitLine: {/*网格线*/
                  lineStyle: {
                    opacity: 0.3,
                    color: "#8DB6DB"
                  },
                  show: false
                }
              },
              series: [{
                markLine: {
                  animation: false,
                  symbol: "none",
                  silent: true,
                  lineStyle: {
                    type: "solid",
                    color: '#8DB6DB',
                    width: 1,
                    opacity: 0.5,

                  },
                  label: {
                    show: false,
                    position: 'start', // 表现内容展示的位置
                    color: '#8DB6DB'  // xAxis,yAxis数字的颜色
                  },
                  data: [
                    {xAxis: 0},
                    {xAxis: 50},
                    {xAxis: 100},
                    {xAxis: 150},
                    {xAxis: 200},
                    {xAxis: 250},
                    {xAxis: 300},
                    {xAxis: 350},
                    {xAxis: 400},
                    {xAxis: 450},
                    {xAxis: 500},
                    {xAxis: 550},
                    {xAxis: 600},
                    {xAxis: 650},
                    {xAxis: 700},
                    {xAxis: 750},
                    {xAxis: 800},
                    {xAxis: 850},
                    {xAxis: 900},
                    {xAxis: 950},
                    {xAxis: 1000},
                    {xAxis: 1050},
                    {xAxis: 1100},
                    {xAxis: 1150},
                    {xAxis: 1200},
                    {xAxis: 1250},
                    {xAxis: 1300},
                    {xAxis: 1350},
                    {xAxis: 1400},
                    {xAxis: 1450},
                    {xAxis: 1500},
                    {xAxis: 1550},
                    {xAxis: 1600},
                    {xAxis: 1650},
                    {xAxis: 1700},
                    {xAxis: 1750},
                    {xAxis: 1800},
                    {xAxis: 1850},
                    {xAxis: 1900},
                    {xAxis: 1950},
                    {xAxis: 2000},
                    {xAxis: 2050},
                    {xAxis: 2100},
                    {xAxis: 2150},
                    {xAxis: 2200},
                    {xAxis: 2250},
                    {xAxis: 2300},
                    {xAxis: 2350},
                    {xAxis: 2400},
                    {xAxis: 2450},
                    {xAxis: 2500},
                    {yAxis: -1},
                    {yAxis: -0.5},
                    {yAxis: 0},
                    {yAxis: 0.5},
                    {yAxis: 1},
                  ]

                },
                /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
                itemStyle: {
                  normal: {
                    lineStyle: {
                      color: '#92c2ff',/*折线的颜色*/
                      opacity: 1,
                    },
                  }
                },
                symbol: "none",
                /*去掉小圆点*/
                name: 'I',
                type: 'line',
                data: I,
                smooth: 0 //显示为平滑的曲线*/
              },
                {
                  markLine: {
                    animation: false,
                    symbol: "none",
                    silent: true,
                    lineStyle: {
                      type: "solid",
                      color: '#8DB6DB',
                      width: 1,
                      opacity: 0.5,
                    },
                    label: {
                      show: true,
                      position: 'start', // 表现内容展示的位置
                      color: '#8DB6DB'  // xAxis,yAxis数字的颜色
                    },
                    data: [
                      {yAxis: -1},
                      {yAxis: -0.5},
                      {yAxis: 0},
                      {yAxis: 0.5},
                      {yAxis: 1},
                    ]

                  },
                  itemStyle: {
                    normal: {
                      lineStyle: {
                        color: '#92c2ff',/*折线的颜色*/
                        opacity: 1,
                      },
                    }
                  },
                  symbol: "none",
                  /*去掉小圆点*/
                  name: 'I',
                  type: 'line',
                  smooth: 0 //显示为平滑的曲线*/
                }]
            })
            chartII.clear()
            chartII.setOption({
              animation: true,
              animationDuration: 9900,
              animationEasing: "linear",
              animationEasingUpdate: 'linear',
              animationDurationUpdate: 10100,
              animationDelayUpdate: 0,
              animationThreshold: 10000,

              title: {
                text: 'II导联',
                textStyle: {
                  fontSize: 12,
                  color: "yellow"
                },
                left: 30
              },
              tooltip: {
                trigger: 'axis',
                axisPointer: {
                  type: 'cross'
                }
              },
              grid: {
                left: 25 /*"50px"*/,
                right: 0 /*"15px"*/,
                top: 0,
                bottom: 0.5,
                height: '98%'
              },
              legend: {
                data: ['当前电位'],
                show: true,
                textStyle: {
                  color: "#000000"
                } /*图例(legend)说明文字的颜色*/,
                left: "right",
              },
              xAxis: {
                boundaryGap: false,//x轴刻度数字在两个刻度线中间显示还是刻度线下显示
                data: this.time,
                axisTick: {//x轴刻度
                  show: false

                },
                axisLabel: { //x轴刻度数字
                  show: false,
                  textStyle: {
                    color: "#8DB6DB"
                  }
                },
                splitLine: {/*网格线*/
                  show: false,
                  interval: 9,//x轴线之间相隔的数量
                  lineStyle: {
                    opacity: 0.3,//x轴线的粗细
                    color: "#8DB6DB"//x轴线的颜色
                  },
                }
              },
              yAxis: {
                type: 'value',
                // 显式设置 `axisLine.show` 和 `axisTick.show` 为 `true`
                axisLine: {
                  show: true
                },

                min: -1,
                max: 1,
                boundaryGap: false,
                splitNumber: 20,
                axisLabel: { //修改坐标系字体颜色
                  show: false,
                  textStyle: {
                    color: "#000000",

                  }
                },
                splitLine: {/*网格线*/
                  lineStyle: {
                    opacity: 0.3,
                    color: "#8DB6DB"
                  },
                  show: false
                }
              },
              series: [{
                markLine: {
                  animation: false,
                  symbol: "none",
                  silent: true,
                  lineStyle: {
                    type: "solid",
                    color: '#8DB6DB',
                    width: 1,
                    opacity: 0.5,

                  },
                  label: {
                    show: false,
                    position: 'start', // 表现内容展示的位置
                    color: '#8DB6DB'  // xAxis,yAxis数字的颜色
                  },
                  data: [
                    {xAxis: 0},
                    {xAxis: 50},
                    {xAxis: 100},
                    {xAxis: 150},
                    {xAxis: 200},
                    {xAxis: 250},
                    {xAxis: 300},
                    {xAxis: 350},
                    {xAxis: 400},
                    {xAxis: 450},
                    {xAxis: 500},
                    {xAxis: 550},
                    {xAxis: 600},
                    {xAxis: 650},
                    {xAxis: 700},
                    {xAxis: 750},
                    {xAxis: 800},
                    {xAxis: 850},
                    {xAxis: 900},
                    {xAxis: 950},
                    {xAxis: 1000},
                    {xAxis: 1050},
                    {xAxis: 1100},
                    {xAxis: 1150},
                    {xAxis: 1200},
                    {xAxis: 1250},
                    {xAxis: 1300},
                    {xAxis: 1350},
                    {xAxis: 1400},
                    {xAxis: 1450},
                    {xAxis: 1500},
                    {xAxis: 1550},
                    {xAxis: 1600},
                    {xAxis: 1650},
                    {xAxis: 1700},
                    {xAxis: 1750},
                    {xAxis: 1800},
                    {xAxis: 1850},
                    {xAxis: 1900},
                    {xAxis: 1950},
                    {xAxis: 2000},
                    {xAxis: 2050},
                    {xAxis: 2100},
                    {xAxis: 2150},
                    {xAxis: 2200},
                    {xAxis: 2250},
                    {xAxis: 2300},
                    {xAxis: 2350},
                    {xAxis: 2400},
                    {xAxis: 2450},
                    {xAxis: 2500},
                    {yAxis: -1},
                    {yAxis: -0.5},
                    {yAxis: 0},
                    {yAxis: 0.5},
                    {yAxis: 1},
                  ]

                },
                /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
                itemStyle: {
                  normal: {
                    lineStyle: {
                      color: '#92c2ff',/*折线的颜色*/
                      opacity: 1,
                    },
                  }
                },
                symbol: "none",
                /*去掉小圆点*/
                name: 'II',
                type: 'line',
                data: II,
                smooth: 0 //显示为平滑的曲线*/
              },
                {
                  markLine: {
                    animation: false,
                    symbol: "none",
                    silent: true,
                    lineStyle: {
                      type: "solid",
                      color: '#8DB6DB',
                      width: 1,
                      opacity: 0.5,
                    },
                    label: {
                      show: true,
                      position: 'start', // 表现内容展示的位置
                      color: '#8DB6DB'  // xAxis,yAxis数字的颜色
                    },
                    data: [
                      {yAxis: -1},
                      {yAxis: -0.5},
                      {yAxis: 0},
                      {yAxis: 0.5},
                      {yAxis: 1},
                    ]
                  },
                  itemStyle: {
                    normal: {
                      lineStyle: {
                        color: '#92c2ff',/*折线的颜色*/
                        opacity: 1,
                      },
                    }
                  },
                  symbol: "none",
                  /*去掉小圆点*/
                  name: 'II',
                  type: 'line',
                  smooth: 0 //显示为平滑的曲线*/
                }]
            })
            chartIII.clear()
            chartIII.setOption({
              animation: true,
              animationDuration: 9900,
              animationEasing: "linear",
              animationEasingUpdate: 'linear',
              animationDurationUpdate: 10100,
              animationDelayUpdate: 0,
              animationThreshold: 10000,
              title: {
                text: 'III导联',
                textStyle: {
                  fontSize: 12,
                  color: "yellow"
                },
                left: 30
              },
              tooltip: {
                trigger: 'axis',
                axisPointer: {
                  type: 'cross'
                }
              },
              grid: {
                left: 25 /*"50px"*/,
                right: 0 /*"15px"*/,
                top: 0,
                bottom: 0.5,
                height: '98%'
              },
              legend: {
                data: ['当前电位'],
                show: true,
                textStyle: {
                  color: "#000000"
                } /*图例(legend)说明文字的颜色*/,
                left: "right",
              },
              xAxis: {
                boundaryGap: false,//x轴刻度数字在两个刻度线中间显示还是刻度线下显示
                data: this.time,
                axisTick: {//x轴刻度
                  show: false

                },
                axisLabel: { //x轴刻度数字
                  show: false,
                  textStyle: {
                    color: "#8DB6DB"
                  }
                },
                splitLine: {/*网格线*/
                  show: false,
                  interval: 9,//x轴线之间相隔的数量
                  lineStyle: {
                    opacity: 0.3,//x轴线的粗细
                    color: "#8DB6DB"//x轴线的颜色
                  },
                }
              },
              yAxis: {
                type: 'value',
                // 显式设置 `axisLine.show` 和 `axisTick.show` 为 `true`
                axisLine: {
                  show: true
                },

                min: -1,
                max: 1,
                boundaryGap: false,
                splitNumber: 20,
                axisLabel: { //修改坐标系字体颜色
                  show: false,
                  textStyle: {
                    color: "#000000",

                  }
                },
                splitLine: {/*网格线*/
                  lineStyle: {
                    opacity: 0.3,
                    color: "#8DB6DB"
                  },
                  show: false
                }
              },
              series: [{
                markLine: {
                  animation: false,
                  symbol: "none",
                  silent: true,
                  lineStyle: {
                    type: "solid",
                    color: '#8DB6DB',
                    width: 1,
                    opacity: 0.5,

                  },
                  label: {
                    show: false,
                    position: 'start', // 表现内容展示的位置
                    color: '#8DB6DB'  // xAxis,yAxis数字的颜色
                  },
                  data: [
                    {xAxis: 0},
                    {xAxis: 50},
                    {xAxis: 100},
                    {xAxis: 150},
                    {xAxis: 200},
                    {xAxis: 250},
                    {xAxis: 300},
                    {xAxis: 350},
                    {xAxis: 400},
                    {xAxis: 450},
                    {xAxis: 500},
                    {xAxis: 550},
                    {xAxis: 600},
                    {xAxis: 650},
                    {xAxis: 700},
                    {xAxis: 750},
                    {xAxis: 800},
                    {xAxis: 850},
                    {xAxis: 900},
                    {xAxis: 950},
                    {xAxis: 1000},
                    {xAxis: 1050},
                    {xAxis: 1100},
                    {xAxis: 1150},
                    {xAxis: 1200},
                    {xAxis: 1250},
                    {xAxis: 1300},
                    {xAxis: 1350},
                    {xAxis: 1400},
                    {xAxis: 1450},
                    {xAxis: 1500},
                    {xAxis: 1550},
                    {xAxis: 1600},
                    {xAxis: 1650},
                    {xAxis: 1700},
                    {xAxis: 1750},
                    {xAxis: 1800},
                    {xAxis: 1850},
                    {xAxis: 1900},
                    {xAxis: 1950},
                    {xAxis: 2000},
                    {xAxis: 2050},
                    {xAxis: 2100},
                    {xAxis: 2150},
                    {xAxis: 2200},
                    {xAxis: 2250},
                    {xAxis: 2300},
                    {xAxis: 2350},
                    {xAxis: 2400},
                    {xAxis: 2450},
                    {xAxis: 2500},
                    {yAxis: -1},
                    {yAxis: -0.5},
                    {yAxis: 0},
                    {yAxis: 0.5},
                    {yAxis: 1},
                  ]

                },
                /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
                itemStyle: {
                  normal: {
                    lineStyle: {
                      color: '#92c2ff',/*折线的颜色*/
                      opacity: 1,
                    },
                  }
                },
                symbol: "none",
                /*去掉小圆点*/
                name: 'II',
                type: 'line',
                data: III,
                smooth: 0 //显示为平滑的曲线*/
              },
                {
                  markLine: {
                    animation: false,
                    symbol: "none",
                    silent: true,
                    lineStyle: {
                      type: "solid",
                      color: '#8DB6DB',
                      width: 1,
                      opacity: 0.5,
                    },
                    label: {
                      show: true,
                      position: 'start', // 表现内容展示的位置
                      color: '#8DB6DB'  // xAxis,yAxis数字的颜色
                    },
                    data: [
                      {yAxis: -1},
                      {yAxis: -0.5},
                      {yAxis: 0},
                      {yAxis: 0.5},
                      {yAxis: 1},
                    ]
                  },
                  itemStyle: {
                    normal: {
                      lineStyle: {
                        color: '#92c2ff',/*折线的颜色*/
                        opacity: 1,
                      },
                    }
                  },
                  symbol: "none",
                  /*去掉小圆点*/
                  name: 'III',
                  type: 'line',
                  smooth: 0 //显示为平滑的曲线*/
                }]
            })
            chartaVR.clear()
            chartaVR.setOption({
              animation: true,
              animationDuration: 9900,
              animationEasing: "linear",
              animationEasingUpdate: 'linear',
              animationDurationUpdate: 10100,
              animationDelayUpdate: 0,
              animationThreshold: 10000,

              title: {
                text: 'aVR导联',
                textStyle: {
                  fontSize: 12,
                  color: "yellow"
                },
                left: 30
              },
              tooltip: {
                trigger: 'axis',
                axisPointer: {
                  type: 'cross'
                }
              },
              grid: {
                left: 25 /*"50px"*/,
                right: 0 /*"15px"*/,
                top: 0,
                bottom: 0.5,
                height: '98%'
              },
              legend: {
                data: ['当前电位'],
                show: true,
                textStyle: {
                  color: "#000000"
                } /*图例(legend)说明文字的颜色*/,
                left: "right",
              },
              xAxis: {
                boundaryGap: false,//x轴刻度数字在两个刻度线中间显示还是刻度线下显示
                data: this.time,
                axisTick: {//x轴刻度
                  show: false

                },
                axisLabel: { //x轴刻度数字
                  show: false,
                  textStyle: {
                    color: "#8DB6DB"
                  }
                },
                splitLine: {/*网格线*/
                  show: false,
                  interval: 9,//x轴线之间相隔的数量
                  lineStyle: {
                    opacity: 0.3,//x轴线的粗细
                    color: "#8DB6DB"//x轴线的颜色
                  },
                }
              },
              yAxis: {
                type: 'value',
                // 显式设置 `axisLine.show` 和 `axisTick.show` 为 `true`
                axisLine: {
                  show: true
                },

                min: -1,
                max: 1,
                boundaryGap: false,
                splitNumber: 20,
                axisLabel: { //修改坐标系字体颜色
                  show: false,
                  textStyle: {
                    color: "#000000",

                  }
                },
                splitLine: {/*网格线*/
                  lineStyle: {
                    opacity: 0.3,
                    color: "#8DB6DB"
                  },
                  show: false
                }
              },
              series: [{
                markLine: {
                  animation: false,
                  symbol: "none",
                  silent: true,
                  lineStyle: {
                    type: "solid",
                    color: '#8DB6DB',
                    width: 1,
                    opacity: 0.5,

                  },
                  label: {
                    show: false,
                    position: 'start', // 表现内容展示的位置
                    color: '#8DB6DB'  // xAxis,yAxis数字的颜色
                  },
                  data: [
                    {xAxis: 0},
                    {xAxis: 50},
                    {xAxis: 100},
                    {xAxis: 150},
                    {xAxis: 200},
                    {xAxis: 250},
                    {xAxis: 300},
                    {xAxis: 350},
                    {xAxis: 400},
                    {xAxis: 450},
                    {xAxis: 500},
                    {xAxis: 550},
                    {xAxis: 600},
                    {xAxis: 650},
                    {xAxis: 700},
                    {xAxis: 750},
                    {xAxis: 800},
                    {xAxis: 850},
                    {xAxis: 900},
                    {xAxis: 950},
                    {xAxis: 1000},
                    {xAxis: 1050},
                    {xAxis: 1100},
                    {xAxis: 1150},
                    {xAxis: 1200},
                    {xAxis: 1250},
                    {xAxis: 1300},
                    {xAxis: 1350},
                    {xAxis: 1400},
                    {xAxis: 1450},
                    {xAxis: 1500},
                    {xAxis: 1550},
                    {xAxis: 1600},
                    {xAxis: 1650},
                    {xAxis: 1700},
                    {xAxis: 1750},
                    {xAxis: 1800},
                    {xAxis: 1850},
                    {xAxis: 1900},
                    {xAxis: 1950},
                    {xAxis: 2000},
                    {xAxis: 2050},
                    {xAxis: 2100},
                    {xAxis: 2150},
                    {xAxis: 2200},
                    {xAxis: 2250},
                    {xAxis: 2300},
                    {xAxis: 2350},
                    {xAxis: 2400},
                    {xAxis: 2450},
                    {xAxis: 2500},
                    {yAxis: -1},
                    {yAxis: -0.5},
                    {yAxis: 0},
                    {yAxis: 0.5},
                    {yAxis: 1},
                  ]

                },
                /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
                itemStyle: {
                  normal: {
                    lineStyle: {
                      color: '#92c2ff',/*折线的颜色*/
                      opacity: 1,
                    },
                  }
                },
                symbol: "none",
                /*去掉小圆点*/
                name: 'aVR',
                type: 'line',
                data: aVR,
                smooth: 0 //显示为平滑的曲线*/
              },
                {
                  markLine: {
                    animation: false,
                    symbol: "none",
                    silent: true,
                    lineStyle: {
                      type: "solid",
                      color: '#8DB6DB',
                      width: 1,
                      opacity: 0.5,
                    },
                    label: {
                      show: true,
                      position: 'start', // 表现内容展示的位置
                      color: '#8DB6DB'  // xAxis,yAxis数字的颜色
                    },
                    data: [
                      {yAxis: -1},
                      {yAxis: -0.5},
                      {yAxis: 0},
                      {yAxis: 0.5},
                      {yAxis: 1},
                    ]
                  },
                  itemStyle: {
                    normal: {
                      lineStyle: {
                        color: '#92c2ff',/*折线的颜色*/
                        opacity: 1,
                      },
                    }
                  },
                  symbol: "none",
                  /*去掉小圆点*/
                  name: 'aVR',
                  type: 'line',
                  smooth: 0 //显示为平滑的曲线*/
                }]
            })
            chartaVL.clear()
            chartaVL.setOption({
              animation: true,
              animationDuration: 9900,
              animationEasing: "linear",
              animationEasingUpdate: 'linear',
              animationDurationUpdate: 10100,
              animationDelayUpdate: 0,
              animationThreshold: 10000,

              title: {
                text: 'aVL导联',
                textStyle: {
                  fontSize: 12,
                  color: "yellow"
                },
                left: 30
              },
              tooltip: {
                trigger: 'axis',
                axisPointer: {
                  type: 'cross'
                }
              },
              grid: {
                left: 25 /*"50px"*/,
                right: 0 /*"15px"*/,
                top: 0,
                bottom: 0.5,
                height: '98%'
              },
              legend: {
                data: ['当前电位'],
                show: true,
                textStyle: {
                  color: "#000000"
                } /*图例(legend)说明文字的颜色*/,
                left: "right",
              },
              xAxis: {
                boundaryGap: false,//x轴刻度数字在两个刻度线中间显示还是刻度线下显示
                data: this.time,
                axisTick: {//x轴刻度
                  show: false

                },
                axisLabel: { //x轴刻度数字
                  show: false,
                  textStyle: {
                    color: "#8DB6DB"
                  }
                },
                splitLine: {/*网格线*/
                  show: false,
                  interval: 9,//x轴线之间相隔的数量
                  lineStyle: {
                    opacity: 0.3,//x轴线的粗细
                    color: "#8DB6DB"//x轴线的颜色
                  },
                }
              },
              yAxis: {
                type: 'value',
                // 显式设置 `axisLine.show` 和 `axisTick.show` 为 `true`
                axisLine: {
                  show: true
                },

                min: -1,
                max: 1,
                boundaryGap: false,
                splitNumber: 20,
                axisLabel: { //修改坐标系字体颜色
                  show: false,
                  textStyle: {
                    color: "#000000",

                  }
                },
                splitLine: {/*网格线*/
                  lineStyle: {
                    opacity: 0.3,
                    color: "#8DB6DB"
                  },
                  show: false
                }
              },
              series: [{
                markLine: {
                  animation: false,
                  symbol: "none",
                  silent: true,
                  lineStyle: {
                    type: "solid",
                    color: '#8DB6DB',
                    width: 1,
                    opacity: 0.5,

                  },
                  label: {
                    show: false,
                    position: 'start', // 表现内容展示的位置
                    color: '#8DB6DB'  // xAxis,yAxis数字的颜色
                  },
                  data: [
                    {xAxis: 0},
                    {xAxis: 50},
                    {xAxis: 100},
                    {xAxis: 150},
                    {xAxis: 200},
                    {xAxis: 250},
                    {xAxis: 300},
                    {xAxis: 350},
                    {xAxis: 400},
                    {xAxis: 450},
                    {xAxis: 500},
                    {xAxis: 550},
                    {xAxis: 600},
                    {xAxis: 650},
                    {xAxis: 700},
                    {xAxis: 750},
                    {xAxis: 800},
                    {xAxis: 850},
                    {xAxis: 900},
                    {xAxis: 950},
                    {xAxis: 1000},
                    {xAxis: 1050},
                    {xAxis: 1100},
                    {xAxis: 1150},
                    {xAxis: 1200},
                    {xAxis: 1250},
                    {xAxis: 1300},
                    {xAxis: 1350},
                    {xAxis: 1400},
                    {xAxis: 1450},
                    {xAxis: 1500},
                    {xAxis: 1550},
                    {xAxis: 1600},
                    {xAxis: 1650},
                    {xAxis: 1700},
                    {xAxis: 1750},
                    {xAxis: 1800},
                    {xAxis: 1850},
                    {xAxis: 1900},
                    {xAxis: 1950},
                    {xAxis: 2000},
                    {xAxis: 2050},
                    {xAxis: 2100},
                    {xAxis: 2150},
                    {xAxis: 2200},
                    {xAxis: 2250},
                    {xAxis: 2300},
                    {xAxis: 2350},
                    {xAxis: 2400},
                    {xAxis: 2450},
                    {xAxis: 2500},
                    {yAxis: -1},
                    {yAxis: -0.5},
                    {yAxis: 0},
                    {yAxis: 0.5},
                    {yAxis: 1},
                  ]

                },
                /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
                itemStyle: {
                  normal: {
                    lineStyle: {
                      color: '#92c2ff',/*折线的颜色*/
                      opacity: 1,
                    },
                  }
                },
                symbol: "none",
                /*去掉小圆点*/
                name: 'aVL',
                type: 'line',
                data: aVL,
                smooth: 0 //显示为平滑的曲线*/
              },
                {
                  markLine: {
                    animation: false,
                    symbol: "none",
                    silent: true,
                    lineStyle: {
                      type: "solid",
                      color: '#8DB6DB',
                      width: 1,
                      opacity: 0.5,
                    },
                    label: {
                      show: true,
                      position: 'start', // 表现内容展示的位置
                      color: '#8DB6DB'  // xAxis,yAxis数字的颜色
                    },
                    data: [
                      {yAxis: -1},
                      {yAxis: -0.5},
                      {yAxis: 0},
                      {yAxis: 0.5},
                      {yAxis: 1},
                    ]
                  },
                  itemStyle: {
                    normal: {
                      lineStyle: {
                        color: '#92c2ff',/*折线的颜色*/
                        opacity: 1,
                      },
                    }
                  },
                  symbol: "none",
                  /*去掉小圆点*/
                  name: 'II',
                  type: 'line',
                  smooth: 0 //显示为平滑的曲线*/
                }]
            })
            chartaVF.clear()
            chartaVF.setOption({
              animation: true,
              animationDuration: 9900,
              animationEasing: "linear",
              animationEasingUpdate: 'linear',
              animationDurationUpdate: 10100,
              animationDelayUpdate: 0,
              animationThreshold: 10000,

              title: {
                text: 'aVF导联',
                textStyle: {
                  fontSize: 12,
                  color: "yellow"
                },
                left: 30
              },
              tooltip: {
                trigger: 'axis',
                axisPointer: {
                  type: 'cross'
                }
              },
              grid: {
                left: 25 /*"50px"*/,
                right: 0 /*"15px"*/,
                top: 0,
                bottom: 0.5,
                height: '98%'
              },
              legend: {
                data: ['当前电位'],
                show: true,
                textStyle: {
                  color: "#000000"
                } /*图例(legend)说明文字的颜色*/,
                left: "right",
              },
              xAxis: {
                boundaryGap: false,//x轴刻度数字在两个刻度线中间显示还是刻度线下显示
                data: this.time,
                axisTick: {//x轴刻度
                  show: false

                },
                axisLabel: { //x轴刻度数字
                  show: false,
                  textStyle: {
                    color: "#8DB6DB"
                  }
                },
                splitLine: {/*网格线*/
                  show: false,
                  interval: 9,//x轴线之间相隔的数量
                  lineStyle: {
                    opacity: 0.3,//x轴线的粗细
                    color: "#8DB6DB"//x轴线的颜色
                  },
                }
              },
              yAxis: {
                type: 'value',
                // 显式设置 `axisLine.show` 和 `axisTick.show` 为 `true`
                axisLine: {
                  show: true
                },

                min: -1,
                max: 1,
                boundaryGap: false,
                splitNumber: 20,
                axisLabel: { //修改坐标系字体颜色
                  show: false,
                  textStyle: {
                    color: "#000000",

                  }
                },
                splitLine: {/*网格线*/
                  lineStyle: {
                    opacity: 0.3,
                    color: "#8DB6DB"
                  },
                  show: false
                }
              },
              series: [{
                markLine: {
                  animation: false,
                  symbol: "none",
                  silent: true,
                  lineStyle: {
                    type: "solid",
                    color: '#8DB6DB',
                    width: 1,
                    opacity: 0.5,

                  },
                  label: {
                    show: false,
                    position: 'start', // 表现内容展示的位置
                    color: '#8DB6DB'  // xAxis,yAxis数字的颜色
                  },
                  data: [
                    {xAxis: 0},
                    {xAxis: 50},
                    {xAxis: 100},
                    {xAxis: 150},
                    {xAxis: 200},
                    {xAxis: 250},
                    {xAxis: 300},
                    {xAxis: 350},
                    {xAxis: 400},
                    {xAxis: 450},
                    {xAxis: 500},
                    {xAxis: 550},
                    {xAxis: 600},
                    {xAxis: 650},
                    {xAxis: 700},
                    {xAxis: 750},
                    {xAxis: 800},
                    {xAxis: 850},
                    {xAxis: 900},
                    {xAxis: 950},
                    {xAxis: 1000},
                    {xAxis: 1050},
                    {xAxis: 1100},
                    {xAxis: 1150},
                    {xAxis: 1200},
                    {xAxis: 1250},
                    {xAxis: 1300},
                    {xAxis: 1350},
                    {xAxis: 1400},
                    {xAxis: 1450},
                    {xAxis: 1500},
                    {xAxis: 1550},
                    {xAxis: 1600},
                    {xAxis: 1650},
                    {xAxis: 1700},
                    {xAxis: 1750},
                    {xAxis: 1800},
                    {xAxis: 1850},
                    {xAxis: 1900},
                    {xAxis: 1950},
                    {xAxis: 2000},
                    {xAxis: 2050},
                    {xAxis: 2100},
                    {xAxis: 2150},
                    {xAxis: 2200},
                    {xAxis: 2250},
                    {xAxis: 2300},
                    {xAxis: 2350},
                    {xAxis: 2400},
                    {xAxis: 2450},
                    {xAxis: 2500},
                    {yAxis: -1},
                    {yAxis: -0.5},
                    {yAxis: 0},
                    {yAxis: 0.5},
                    {yAxis: 1},
                  ]

                },
                /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
                itemStyle: {
                  normal: {
                    lineStyle: {
                      color: '#92c2ff',/*折线的颜色*/
                      opacity: 1,
                    },
                  }
                },
                symbol: "none",
                /*去掉小圆点*/
                name: 'aVF',
                type: 'line',
                data: aVF,
                smooth: 0 //显示为平滑的曲线*/
              },
                {
                  markLine: {
                    animation: false,
                    symbol: "none",
                    silent: true,
                    lineStyle: {
                      type: "solid",
                      color: '#8DB6DB',
                      width: 1,
                      opacity: 0.5,
                    },
                    label: {
                      show: true,
                      position: 'start', // 表现内容展示的位置
                      color: '#8DB6DB'  // xAxis,yAxis数字的颜色
                    },
                    data: [
                      {yAxis: -1},
                      {yAxis: -0.5},
                      {yAxis: 0},
                      {yAxis: 0.5},
                      {yAxis: 1},
                    ]
                  },
                  itemStyle: {
                    normal: {
                      lineStyle: {
                        color: '#92c2ff',/*折线的颜色*/
                        opacity: 1,
                      },
                    }
                  },
                  symbol: "none",
                  /*去掉小圆点*/
                  name: 'II',
                  type: 'line',
                  smooth: 0 //显示为平滑的曲线*/
                }]
            })
            chartV1.clear()
            chartV1.setOption({
              animation: true,
              animationDuration: 9900,
              animationEasing: "linear",
              animationEasingUpdate: 'linear',
              animationDurationUpdate: 10100,
              animationDelayUpdate: 0,
              animationThreshold: 10000,

              title: {
                text: 'V1导联',
                textStyle: {
                  fontSize: 12,
                  color: "yellow"
                },
                left: 30
              },
              tooltip: {
                trigger: 'axis',
                axisPointer: {
                  type: 'cross'
                }
              },
              grid: {
                left: 25 /*"50px"*/,
                right: 0 /*"15px"*/,
                top: 0,
                bottom: 0.5,
                height: '98%'
              },
              legend: {
                data: ['当前电位'],
                show: true,
                textStyle: {
                  color: "#000000"
                } /*图例(legend)说明文字的颜色*/,
                left: "right",
              },
              xAxis: {
                boundaryGap: false,//x轴刻度数字在两个刻度线中间显示还是刻度线下显示
                data: this.time,
                axisTick: {//x轴刻度
                  show: false

                },
                axisLabel: { //x轴刻度数字
                  show: false,
                  textStyle: {
                    color: "#8DB6DB"
                  }
                },
                splitLine: {/*网格线*/
                  show: false,
                  interval: 9,//x轴线之间相隔的数量
                  lineStyle: {
                    opacity: 0.3,//x轴线的粗细
                    color: "#8DB6DB"//x轴线的颜色
                  },
                }
              },
              yAxis: {
                type: 'value',
                // 显式设置 `axisLine.show` 和 `axisTick.show` 为 `true`
                axisLine: {
                  show: true
                },

                min: -1,
                max: 1,
                boundaryGap: false,
                splitNumber: 20,
                axisLabel: { //修改坐标系字体颜色
                  show: false,
                  textStyle: {
                    color: "#000000",

                  }
                },
                splitLine: {/*网格线*/
                  lineStyle: {
                    opacity: 0.3,
                    color: "#8DB6DB"
                  },
                  show: false
                }
              },
              series: [{
                markLine: {
                  animation: false,
                  symbol: "none",
                  silent: true,
                  lineStyle: {
                    type: "solid",
                    color: '#8DB6DB',
                    width: 1,
                    opacity: 0.5,

                  },
                  label: {
                    show: false,
                    position: 'start', // 表现内容展示的位置
                    color: '#8DB6DB'  // xAxis,yAxis数字的颜色
                  },
                  data: [
                    {xAxis: 0},
                    {xAxis: 50},
                    {xAxis: 100},
                    {xAxis: 150},
                    {xAxis: 200},
                    {xAxis: 250},
                    {xAxis: 300},
                    {xAxis: 350},
                    {xAxis: 400},
                    {xAxis: 450},
                    {xAxis: 500},
                    {xAxis: 550},
                    {xAxis: 600},
                    {xAxis: 650},
                    {xAxis: 700},
                    {xAxis: 750},
                    {xAxis: 800},
                    {xAxis: 850},
                    {xAxis: 900},
                    {xAxis: 950},
                    {xAxis: 1000},
                    {xAxis: 1050},
                    {xAxis: 1100},
                    {xAxis: 1150},
                    {xAxis: 1200},
                    {xAxis: 1250},
                    {xAxis: 1300},
                    {xAxis: 1350},
                    {xAxis: 1400},
                    {xAxis: 1450},
                    {xAxis: 1500},
                    {xAxis: 1550},
                    {xAxis: 1600},
                    {xAxis: 1650},
                    {xAxis: 1700},
                    {xAxis: 1750},
                    {xAxis: 1800},
                    {xAxis: 1850},
                    {xAxis: 1900},
                    {xAxis: 1950},
                    {xAxis: 2000},
                    {xAxis: 2050},
                    {xAxis: 2100},
                    {xAxis: 2150},
                    {xAxis: 2200},
                    {xAxis: 2250},
                    {xAxis: 2300},
                    {xAxis: 2350},
                    {xAxis: 2400},
                    {xAxis: 2450},
                    {xAxis: 2500},
                    {yAxis: -1},
                    {yAxis: -0.5},
                    {yAxis: 0},
                    {yAxis: 0.5},
                    {yAxis: 1},
                  ]

                },
                /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
                itemStyle: {
                  normal: {
                    lineStyle: {
                      color: '#92c2ff',/*折线的颜色*/
                      opacity: 1,
                    },
                  }
                },
                symbol: "none",
                /*去掉小圆点*/
                name: 'V1',
                type: 'line',
                data: V1,
                smooth: 0 //显示为平滑的曲线*/
              },
                {
                  markLine: {
                    animation: false,
                    symbol: "none",
                    silent: true,
                    lineStyle: {
                      type: "solid",
                      color: '#8DB6DB',
                      width: 1,
                      opacity: 0.5,
                    },
                    label: {
                      show: true,
                      position: 'start', // 表现内容展示的位置
                      color: '#8DB6DB'  // xAxis,yAxis数字的颜色
                    },
                    data: [
                      {yAxis: -1},
                      {yAxis: -0.5},
                      {yAxis: 0},
                      {yAxis: 0.5},
                      {yAxis: 1},
                    ]
                  },
                  itemStyle: {
                    normal: {
                      lineStyle: {
                        color: '#92c2ff',/*折线的颜色*/
                        opacity: 1,
                      },
                    }
                  },
                  symbol: "none",
                  /*去掉小圆点*/
                  name: 'V1',
                  type: 'line',
                  smooth: 0 //显示为平滑的曲线*/
                }]
            })
            chartV2.clear()
            chartV2.setOption({
              animation: true,
              animationDuration: 9900,
              animationEasing: "linear",
              animationEasingUpdate: 'linear',
              animationDurationUpdate: 10100,
              animationDelayUpdate: 0,
              animationThreshold: 10000,

              title: {
                text: 'V2导联',
                textStyle: {
                  fontSize: 12,
                  color: "yellow"
                },
                left: 30
              },
              tooltip: {
                trigger: 'axis',
                axisPointer: {
                  type: 'cross'
                }
              },
              grid: {
                left: 25 /*"50px"*/,
                right: 0 /*"15px"*/,
                top: 0,
                bottom: 0.5,
                height: '98%'
              },
              legend: {
                data: ['当前电位'],
                show: true,
                textStyle: {
                  color: "#000000"
                } /*图例(legend)说明文字的颜色*/,
                left: "right",
              },
              xAxis: {
                boundaryGap: false,//x轴刻度数字在两个刻度线中间显示还是刻度线下显示
                data: this.time,
                axisTick: {//x轴刻度
                  show: false

                },
                axisLabel: { //x轴刻度数字
                  show: false,
                  textStyle: {
                    color: "#8DB6DB"
                  }
                },
                splitLine: {/*网格线*/
                  show: false,
                  interval: 9,//x轴线之间相隔的数量
                  lineStyle: {
                    opacity: 0.3,//x轴线的粗细
                    color: "#8DB6DB"//x轴线的颜色
                  },
                }
              },
              yAxis: {
                type: 'value',
                // 显式设置 `axisLine.show` 和 `axisTick.show` 为 `true`
                axisLine: {
                  show: true
                },

                min: -1,
                max: 1,
                boundaryGap: false,
                splitNumber: 20,
                axisLabel: { //修改坐标系字体颜色
                  show: false,
                  textStyle: {
                    color: "#000000",

                  }
                },
                splitLine: {/*网格线*/
                  lineStyle: {
                    opacity: 0.3,
                    color: "#8DB6DB"
                  },
                  show: false
                }
              },
              series: [{
                markLine: {
                  animation: false,
                  symbol: "none",
                  silent: true,
                  lineStyle: {
                    type: "solid",
                    color: '#8DB6DB',
                    width: 1,
                    opacity: 0.5,

                  },
                  label: {
                    show: false,
                    position: 'start', // 表现内容展示的位置
                    color: '#8DB6DB'  // xAxis,yAxis数字的颜色
                  },
                  data: [
                    {xAxis: 0},
                    {xAxis: 50},
                    {xAxis: 100},
                    {xAxis: 150},
                    {xAxis: 200},
                    {xAxis: 250},
                    {xAxis: 300},
                    {xAxis: 350},
                    {xAxis: 400},
                    {xAxis: 450},
                    {xAxis: 500},
                    {xAxis: 550},
                    {xAxis: 600},
                    {xAxis: 650},
                    {xAxis: 700},
                    {xAxis: 750},
                    {xAxis: 800},
                    {xAxis: 850},
                    {xAxis: 900},
                    {xAxis: 950},
                    {xAxis: 1000},
                    {xAxis: 1050},
                    {xAxis: 1100},
                    {xAxis: 1150},
                    {xAxis: 1200},
                    {xAxis: 1250},
                    {xAxis: 1300},
                    {xAxis: 1350},
                    {xAxis: 1400},
                    {xAxis: 1450},
                    {xAxis: 1500},
                    {xAxis: 1550},
                    {xAxis: 1600},
                    {xAxis: 1650},
                    {xAxis: 1700},
                    {xAxis: 1750},
                    {xAxis: 1800},
                    {xAxis: 1850},
                    {xAxis: 1900},
                    {xAxis: 1950},
                    {xAxis: 2000},
                    {xAxis: 2050},
                    {xAxis: 2100},
                    {xAxis: 2150},
                    {xAxis: 2200},
                    {xAxis: 2250},
                    {xAxis: 2300},
                    {xAxis: 2350},
                    {xAxis: 2400},
                    {xAxis: 2450},
                    {xAxis: 2500},
                    {yAxis: -1},
                    {yAxis: -0.5},
                    {yAxis: 0},
                    {yAxis: 0.5},
                    {yAxis: 1},
                  ]

                },
                /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
                itemStyle: {
                  normal: {
                    lineStyle: {
                      color: '#92c2ff',/*折线的颜色*/
                      opacity: 1,
                    },
                  }
                },
                symbol: "none",
                /*去掉小圆点*/
                name: 'V2',
                type: 'line',
                data: V2,
                smooth: 0 //显示为平滑的曲线*/
              },
                {
                  markLine: {
                    animation: false,
                    symbol: "none",
                    silent: true,
                    lineStyle: {
                      type: "solid",
                      color: '#8DB6DB',
                      width: 1,
                      opacity: 0.5,
                    },
                    label: {
                      show: true,
                      position: 'start', // 表现内容展示的位置
                      color: '#8DB6DB'  // xAxis,yAxis数字的颜色
                    },
                    data: [
                      {yAxis: -1},
                      {yAxis: -0.5},
                      {yAxis: 0},
                      {yAxis: 0.5},
                      {yAxis: 1},
                    ]
                  },
                  itemStyle: {
                    normal: {
                      lineStyle: {
                        color: '#92c2ff',/*折线的颜色*/
                        opacity: 1,
                      },
                    }
                  },
                  symbol: "none",
                  /*去掉小圆点*/
                  name: 'V2',
                  type: 'line',
                  smooth: 0 //显示为平滑的曲线*/
                }]
            })
            chartV3.clear()
            chartV3.setOption({
              animation: true,
              animationDuration: 9900,
              animationEasing: "linear",
              animationEasingUpdate: 'linear',
              animationDurationUpdate: 10100,
              animationDelayUpdate: 0,
              animationThreshold: 10000,

              title: {
                text: 'V3导联',
                textStyle: {
                  fontSize: 12,
                  color: "yellow"
                },
                left: 30
              },
              tooltip: {
                trigger: 'axis',
                axisPointer: {
                  type: 'cross'
                }
              },
              grid: {
                left: 25 /*"50px"*/,
                right: 0 /*"15px"*/,
                top: 0,
                bottom: 0.5,
                height: '98%'
              },
              legend: {
                data: ['当前电位'],
                show: true,
                textStyle: {
                  color: "#000000"
                } /*图例(legend)说明文字的颜色*/,
                left: "right",
              },
              xAxis: {
                boundaryGap: false,//x轴刻度数字在两个刻度线中间显示还是刻度线下显示
                data: this.time,
                axisTick: {//x轴刻度
                  show: false

                },
                axisLabel: { //x轴刻度数字
                  show: false,
                  textStyle: {
                    color: "#8DB6DB"
                  }
                },
                splitLine: {/*网格线*/
                  show: false,
                  interval: 9,//x轴线之间相隔的数量
                  lineStyle: {
                    opacity: 0.3,//x轴线的粗细
                    color: "#8DB6DB"//x轴线的颜色
                  },
                }
              },
              yAxis: {
                type: 'value',
                // 显式设置 `axisLine.show` 和 `axisTick.show` 为 `true`
                axisLine: {
                  show: true
                },

                min: -1,
                max: 1,
                boundaryGap: false,
                splitNumber: 20,
                axisLabel: { //修改坐标系字体颜色
                  show: false,
                  textStyle: {
                    color: "#000000",

                  }
                },
                splitLine: {/*网格线*/
                  lineStyle: {
                    opacity: 0.3,
                    color: "#8DB6DB"
                  },
                  show: false
                }
              },
              series: [{
                markLine: {
                  animation: false,
                  symbol: "none",
                  silent: true,
                  lineStyle: {
                    type: "solid",
                    color: '#8DB6DB',
                    width: 1,
                    opacity: 0.5,

                  },
                  label: {
                    show: false,
                    position: 'start', // 表现内容展示的位置
                    color: '#8DB6DB'  // xAxis,yAxis数字的颜色
                  },
                  data: [
                    {xAxis: 0},
                    {xAxis: 50},
                    {xAxis: 100},
                    {xAxis: 150},
                    {xAxis: 200},
                    {xAxis: 250},
                    {xAxis: 300},
                    {xAxis: 350},
                    {xAxis: 400},
                    {xAxis: 450},
                    {xAxis: 500},
                    {xAxis: 550},
                    {xAxis: 600},
                    {xAxis: 650},
                    {xAxis: 700},
                    {xAxis: 750},
                    {xAxis: 800},
                    {xAxis: 850},
                    {xAxis: 900},
                    {xAxis: 950},
                    {xAxis: 1000},
                    {xAxis: 1050},
                    {xAxis: 1100},
                    {xAxis: 1150},
                    {xAxis: 1200},
                    {xAxis: 1250},
                    {xAxis: 1300},
                    {xAxis: 1350},
                    {xAxis: 1400},
                    {xAxis: 1450},
                    {xAxis: 1500},
                    {xAxis: 1550},
                    {xAxis: 1600},
                    {xAxis: 1650},
                    {xAxis: 1700},
                    {xAxis: 1750},
                    {xAxis: 1800},
                    {xAxis: 1850},
                    {xAxis: 1900},
                    {xAxis: 1950},
                    {xAxis: 2000},
                    {xAxis: 2050},
                    {xAxis: 2100},
                    {xAxis: 2150},
                    {xAxis: 2200},
                    {xAxis: 2250},
                    {xAxis: 2300},
                    {xAxis: 2350},
                    {xAxis: 2400},
                    {xAxis: 2450},
                    {xAxis: 2500},
                    {yAxis: -1},
                    {yAxis: -0.5},
                    {yAxis: 0},
                    {yAxis: 0.5},
                    {yAxis: 1},
                  ]

                },
                /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
                itemStyle: {
                  normal: {
                    lineStyle: {
                      color: '#92c2ff',/*折线的颜色*/
                      opacity: 1,
                    },
                  }
                },
                symbol: "none",
                /*去掉小圆点*/
                name: 'V3',
                type: 'line',
                data: V3,
                smooth: 0 //显示为平滑的曲线*/
              },
                {
                  markLine: {
                    animation: false,
                    symbol: "none",
                    silent: true,
                    lineStyle: {
                      type: "solid",
                      color: '#8DB6DB',
                      width: 1,
                      opacity: 0.5,
                    },
                    label: {
                      show: true,
                      position: 'start', // 表现内容展示的位置
                      color: '#8DB6DB'  // xAxis,yAxis数字的颜色
                    },
                    data: [
                      {yAxis: -1},
                      {yAxis: -0.5},
                      {yAxis: 0},
                      {yAxis: 0.5},
                      {yAxis: 1},
                    ]
                  },
                  itemStyle: {
                    normal: {
                      lineStyle: {
                        color: '#92c2ff',/*折线的颜色*/
                        opacity: 1,
                      },
                    }
                  },
                  symbol: "none",
                  /*去掉小圆点*/
                  name: 'V3',
                  type: 'line',
                  smooth: 0 //显示为平滑的曲线*/
                }]
            })
            chartV4.clear()
            chartV4.setOption({
              animation: true,
              animationDuration: 9900,
              animationEasing: "linear",
              animationEasingUpdate: 'linear',
              animationDurationUpdate: 10100,
              animationDelayUpdate: 0,
              animationThreshold: 10000,

              title: {
                text: 'V4导联',
                textStyle: {
                  fontSize: 12,
                  color: "yellow"
                },
                left: 30
              },
              tooltip: {
                trigger: 'axis',
                axisPointer: {
                  type: 'cross'
                }
              },
              grid: {
                left: 25 /*"50px"*/,
                right: 0 /*"15px"*/,
                top: 0,
                bottom: 0.5,
                height: '98%'
              },
              legend: {
                data: ['当前电位'],
                show: true,
                textStyle: {
                  color: "#000000"
                } /*图例(legend)说明文字的颜色*/,
                left: "right",
              },
              xAxis: {
                boundaryGap: false,//x轴刻度数字在两个刻度线中间显示还是刻度线下显示
                data: this.time,
                axisTick: {//x轴刻度
                  show: false

                },
                axisLabel: { //x轴刻度数字
                  show: false,
                  textStyle: {
                    color: "#8DB6DB"
                  }
                },
                splitLine: {/*网格线*/
                  show: false,
                  interval: 9,//x轴线之间相隔的数量
                  lineStyle: {
                    opacity: 0.3,//x轴线的粗细
                    color: "#8DB6DB"//x轴线的颜色
                  },
                }
              },
              yAxis: {
                type: 'value',
                // 显式设置 `axisLine.show` 和 `axisTick.show` 为 `true`
                axisLine: {
                  show: true
                },

                min: -1,
                max: 1,
                boundaryGap: false,
                splitNumber: 20,
                axisLabel: { //修改坐标系字体颜色
                  show: false,
                  textStyle: {
                    color: "#000000",

                  }
                },
                splitLine: {/*网格线*/
                  lineStyle: {
                    opacity: 0.3,
                    color: "#8DB6DB"
                  },
                  show: false
                }
              },
              series: [{
                markLine: {
                  animation: false,
                  symbol: "none",
                  silent: true,
                  lineStyle: {
                    type: "solid",
                    color: '#8DB6DB',
                    width: 1,
                    opacity: 0.5,

                  },
                  label: {
                    show: false,
                    position: 'start', // 表现内容展示的位置
                    color: '#8DB6DB'  // xAxis,yAxis数字的颜色
                  },
                  data: [
                    {xAxis: 0},
                    {xAxis: 50},
                    {xAxis: 100},
                    {xAxis: 150},
                    {xAxis: 200},
                    {xAxis: 250},
                    {xAxis: 300},
                    {xAxis: 350},
                    {xAxis: 400},
                    {xAxis: 450},
                    {xAxis: 500},
                    {xAxis: 550},
                    {xAxis: 600},
                    {xAxis: 650},
                    {xAxis: 700},
                    {xAxis: 750},
                    {xAxis: 800},
                    {xAxis: 850},
                    {xAxis: 900},
                    {xAxis: 950},
                    {xAxis: 1000},
                    {xAxis: 1050},
                    {xAxis: 1100},
                    {xAxis: 1150},
                    {xAxis: 1200},
                    {xAxis: 1250},
                    {xAxis: 1300},
                    {xAxis: 1350},
                    {xAxis: 1400},
                    {xAxis: 1450},
                    {xAxis: 1500},
                    {xAxis: 1550},
                    {xAxis: 1600},
                    {xAxis: 1650},
                    {xAxis: 1700},
                    {xAxis: 1750},
                    {xAxis: 1800},
                    {xAxis: 1850},
                    {xAxis: 1900},
                    {xAxis: 1950},
                    {xAxis: 2000},
                    {xAxis: 2050},
                    {xAxis: 2100},
                    {xAxis: 2150},
                    {xAxis: 2200},
                    {xAxis: 2250},
                    {xAxis: 2300},
                    {xAxis: 2350},
                    {xAxis: 2400},
                    {xAxis: 2450},
                    {xAxis: 2500},
                    {yAxis: -1},
                    {yAxis: -0.5},
                    {yAxis: 0},
                    {yAxis: 0.5},
                    {yAxis: 1},
                  ]

                },
                /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
                itemStyle: {
                  normal: {
                    lineStyle: {
                      color: '#92c2ff',/*折线的颜色*/
                      opacity: 1,
                    },
                  }
                },
                symbol: "none",
                /*去掉小圆点*/
                name: 'V4',
                type: 'line',
                data: V4,
                smooth: 0 //显示为平滑的曲线*/
              },
                {
                  markLine: {
                    animation: false,
                    symbol: "none",
                    silent: true,
                    lineStyle: {
                      type: "solid",
                      color: '#8DB6DB',
                      width: 1,
                      opacity: 0.5,
                    },
                    label: {
                      show: true,
                      position: 'start', // 表现内容展示的位置
                      color: '#8DB6DB'  // xAxis,yAxis数字的颜色
                    },
                    data: [
                      {yAxis: -1},
                      {yAxis: -0.5},
                      {yAxis: 0},
                      {yAxis: 0.5},
                      {yAxis: 1},
                    ]
                  },
                  itemStyle: {
                    normal: {
                      lineStyle: {
                        color: '#92c2ff',/*折线的颜色*/
                        opacity: 1,
                      },
                    }
                  },
                  symbol: "none",
                  /*去掉小圆点*/
                  name: 'V4',
                  type: 'line',
                  smooth: 0 //显示为平滑的曲线*/
                }]
            })
            chartV5.clear()
            chartV5.setOption({
              animation: true,
              animationDuration: 9900,
              animationEasing: "linear",
              animationEasingUpdate: 'linear',
              animationDurationUpdate: 10100,
              animationDelayUpdate: 0,
              animationThreshold: 10000,

              title: {
                text: 'V5导联',
                textStyle: {
                  fontSize: 12,
                  color: "yellow"
                },
                left: 30
              },
              tooltip: {
                trigger: 'axis',
                axisPointer: {
                  type: 'cross'
                }
              },
              grid: {
                left: 25 /*"50px"*/,
                right: 0 /*"15px"*/,
                top: 0,
                bottom: 0.5,
                height: '98%'
              },
              legend: {
                data: ['当前电位'],
                show: true,
                textStyle: {
                  color: "#000000"
                } /*图例(legend)说明文字的颜色*/,
                left: "right",
              },
              xAxis: {
                boundaryGap: false,//x轴刻度数字在两个刻度线中间显示还是刻度线下显示
                data: this.time,
                axisTick: {//x轴刻度
                  show: false

                },
                axisLabel: { //x轴刻度数字
                  show: false,
                  textStyle: {
                    color: "#8DB6DB"
                  }
                },
                splitLine: {/*网格线*/
                  show: false,
                  interval: 9,//x轴线之间相隔的数量
                  lineStyle: {
                    opacity: 0.3,//x轴线的粗细
                    color: "#8DB6DB"//x轴线的颜色
                  },
                }
              },
              yAxis: {
                type: 'value',
                // 显式设置 `axisLine.show` 和 `axisTick.show` 为 `true`
                axisLine: {
                  show: true
                },

                min: -1,
                max: 1,
                boundaryGap: false,
                splitNumber: 20,
                axisLabel: { //修改坐标系字体颜色
                  show: false,
                  textStyle: {
                    color: "#000000",

                  }
                },
                splitLine: {/*网格线*/
                  lineStyle: {
                    opacity: 0.3,
                    color: "#8DB6DB"
                  },
                  show: false
                }
              },
              series: [{
                markLine: {
                  animation: false,
                  symbol: "none",
                  silent: true,
                  lineStyle: {
                    type: "solid",
                    color: '#8DB6DB',
                    width: 1,
                    opacity: 0.5,

                  },
                  label: {
                    show: false,
                    position: 'start', // 表现内容展示的位置
                    color: '#8DB6DB'  // xAxis,yAxis数字的颜色
                  },
                  data: [
                    {xAxis: 0},
                    {xAxis: 50},
                    {xAxis: 100},
                    {xAxis: 150},
                    {xAxis: 200},
                    {xAxis: 250},
                    {xAxis: 300},
                    {xAxis: 350},
                    {xAxis: 400},
                    {xAxis: 450},
                    {xAxis: 500},
                    {xAxis: 550},
                    {xAxis: 600},
                    {xAxis: 650},
                    {xAxis: 700},
                    {xAxis: 750},
                    {xAxis: 800},
                    {xAxis: 850},
                    {xAxis: 900},
                    {xAxis: 950},
                    {xAxis: 1000},
                    {xAxis: 1050},
                    {xAxis: 1100},
                    {xAxis: 1150},
                    {xAxis: 1200},
                    {xAxis: 1250},
                    {xAxis: 1300},
                    {xAxis: 1350},
                    {xAxis: 1400},
                    {xAxis: 1450},
                    {xAxis: 1500},
                    {xAxis: 1550},
                    {xAxis: 1600},
                    {xAxis: 1650},
                    {xAxis: 1700},
                    {xAxis: 1750},
                    {xAxis: 1800},
                    {xAxis: 1850},
                    {xAxis: 1900},
                    {xAxis: 1950},
                    {xAxis: 2000},
                    {xAxis: 2050},
                    {xAxis: 2100},
                    {xAxis: 2150},
                    {xAxis: 2200},
                    {xAxis: 2250},
                    {xAxis: 2300},
                    {xAxis: 2350},
                    {xAxis: 2400},
                    {xAxis: 2450},
                    {xAxis: 2500},
                    {yAxis: -1},
                    {yAxis: -0.5},
                    {yAxis: 0},
                    {yAxis: 0.5},
                    {yAxis: 1},
                  ]

                },
                /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
                itemStyle: {
                  normal: {
                    lineStyle: {
                      color: '#92c2ff',/*折线的颜色*/
                      opacity: 1,
                    },
                  }
                },
                symbol: "none",
                /*去掉小圆点*/
                name: 'V5',
                type: 'line',
                data: V5,
                smooth: 0 //显示为平滑的曲线*/
              },
                {
                  markLine: {
                    animation: false,
                    symbol: "none",
                    silent: true,
                    lineStyle: {
                      type: "solid",
                      color: '#8DB6DB',
                      width: 1,
                      opacity: 0.5,
                    },
                    label: {
                      show: true,
                      position: 'start', // 表现内容展示的位置
                      color: '#8DB6DB'  // xAxis,yAxis数字的颜色
                    },
                    data: [
                      {yAxis: -1},
                      {yAxis: -0.5},
                      {yAxis: 0},
                      {yAxis: 0.5},
                      {yAxis: 1},
                    ]
                  },
                  itemStyle: {
                    normal: {
                      lineStyle: {
                        color: '#92c2ff',/*折线的颜色*/
                        opacity: 1,
                      },
                    }
                  },
                  symbol: "none",
                  /*去掉小圆点*/
                  name: 'V5',
                  type: 'line',
                  smooth: 0 //显示为平滑的曲线*/
                }]
            })
            chartV6.clear()
            chartV6.setOption({
              animation: true,
              animationDuration: 9900,
              animationEasing: "linear",
              animationEasingUpdate: 'linear',
              animationDurationUpdate: 10100,
              animationDelayUpdate: 0,
              animationThreshold: 10000,

              title: {
                text: 'V6导联',
                textStyle: {
                  fontSize: 12,
                  color: "yellow"
                },
                left: 30
              },
              tooltip: {
                trigger: 'axis',
                axisPointer: {
                  type: 'cross'
                }
              },
              grid: {
                left: 25 /*"50px"*/,
                right: 0 /*"15px"*/,
                top: 0,
                bottom: 17.5,
              },
              legend: {
                data: ['当前电位'],
                show: true,
                textStyle: {
                  color: "#000000"
                } /*图例(legend)说明文字的颜色*/,
                left: "right",
              },
              xAxis: {
                boundaryGap: false,//x轴刻度数字在两个刻度线中间显示还是刻度线下显示
                data: this.time,
                axisTick: {//x轴刻度
                  show: false
                },
                axisLabel: { //x轴刻度数字
                  show: true,
                  textStyle: {
                    color: "#8DB6DB"
                  }
                },
                splitLine: {/*网格线*/
                  show: false,
                  interval: 9,//x轴线之间相隔的数量
                  lineStyle: {
                    opacity: 0.3,//x轴线的粗细
                    color: "#8DB6DB"//x轴线的颜色
                  },
                }
              },
              yAxis: {
                type: 'value',
                // 显式设置 `axisLine.show` 和 `axisTick.show` 为 `true`
                axisLine: {
                  show: true
                },

                min: -1,
                max: 1,
                boundaryGap: false,
                splitNumber: 20,
                axisLabel: { //修改坐标系字体颜色
                  show: false,
                  textStyle: {
                    color: "#000000",

                  }
                },
                splitLine: {/*网格线*/
                  lineStyle: {
                    opacity: 0.3,
                    color: "#8DB6DB"
                  },
                  show: false
                }
              },
              series: [{
                markLine: {
                  animation: false,
                  symbol: "none",
                  silent: true,
                  lineStyle: {
                    type: "solid",
                    color: '#8DB6DB',
                    width: 1,
                    opacity: 0.5,

                  },
                  label: {
                    show: false,
                    position: 'start', // 表现内容展示的位置
                    color: '#8DB6DB'  // xAxis,yAxis数字的颜色
                  },
                  data: [
                    {xAxis: 0},
                    {xAxis: 50},
                    {xAxis: 100},
                    {xAxis: 150},
                    {xAxis: 200},
                    {xAxis: 250},
                    {xAxis: 300},
                    {xAxis: 350},
                    {xAxis: 400},
                    {xAxis: 450},
                    {xAxis: 500},
                    {xAxis: 550},
                    {xAxis: 600},
                    {xAxis: 650},
                    {xAxis: 700},
                    {xAxis: 750},
                    {xAxis: 800},
                    {xAxis: 850},
                    {xAxis: 900},
                    {xAxis: 950},
                    {xAxis: 1000},
                    {xAxis: 1050},
                    {xAxis: 1100},
                    {xAxis: 1150},
                    {xAxis: 1200},
                    {xAxis: 1250},
                    {xAxis: 1300},
                    {xAxis: 1350},
                    {xAxis: 1400},
                    {xAxis: 1450},
                    {xAxis: 1500},
                    {xAxis: 1550},
                    {xAxis: 1600},
                    {xAxis: 1650},
                    {xAxis: 1700},
                    {xAxis: 1750},
                    {xAxis: 1800},
                    {xAxis: 1850},
                    {xAxis: 1900},
                    {xAxis: 1950},
                    {xAxis: 2000},
                    {xAxis: 2050},
                    {xAxis: 2100},
                    {xAxis: 2150},
                    {xAxis: 2200},
                    {xAxis: 2250},
                    {xAxis: 2300},
                    {xAxis: 2350},
                    {xAxis: 2400},
                    {xAxis: 2450},
                    {xAxis: 2500},
                    {yAxis: -1},
                    {yAxis: -0.5},
                    {yAxis: 0},
                    {yAxis: 0.5},
                    {yAxis: 1},
                  ]

                },
                /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
                itemStyle: {
                  normal: {
                    lineStyle: {
                      color: '#92c2ff',/*折线的颜色*/
                      opacity: 1,
                    },
                  }
                },
                symbol: "none",
                /*去掉小圆点*/
                name: 'V6',
                type: 'line',
                data: V6,
                smooth: 0 //显示为平滑的曲线*/
              },
                {
                  markLine: {
                    animation: false,
                    symbol: "none",
                    silent: true,
                    lineStyle: {
                      type: "solid",
                      color: '#8DB6DB',
                      width: 1,
                      opacity: 0.5,
                    },
                    label: {
                      show: true,
                      position: 'start', // 表现内容展示的位置
                      color: '#8DB6DB'  // xAxis,yAxis数字的颜色
                    },
                    data: [
                      {yAxis: -1},
                      {yAxis: -0.5},
                      {yAxis: 0},
                      {yAxis: 0.5},
                      {yAxis: 1},
                    ]
                  },
                  itemStyle: {
                    normal: {
                      lineStyle: {
                        color: '#92c2ff',/*折线的颜色*/
                        opacity: 1,
                      },
                    }
                  },
                  symbol: "none",
                  /*去掉小圆点*/
                  name: 'V6',
                  type: 'line',
                  smooth: 0 //显示为平滑的曲线*/
                }]
            })
          }
          this.$http.post('https://server.mindyard.cn:84/detect_decg',
            JSON.stringify({
              "deviceSn": this.deviceSn,
              "ts": ts,
            }), {
              contentType: "application/json",
              dataType: "json",
              headers: {
                "user": "zzu",
                "password": "zzu123"
              },
            }
          ).then(res=>{
            res.data.result.hr_mean = res.data.result.hr_mean.toFixed()
            this.newData=res.data.result
          }).catch(err=>{
            console.log("错误信息"+err)
          })
        },10300)
        }
      }).catch(err=>{
        console.log("错误信息"+err)
      })

    },
    disposeList(){
      echarts.init(document.getElementById("chart_0")).dispose()
      echarts.init(document.getElementById("chart_1")).dispose()
      echarts.init(document.getElementById("chart_2")).dispose()
      echarts.init(document.getElementById("chart_3")).dispose()
      echarts.init(document.getElementById("chart_4")).dispose()
      echarts.init(document.getElementById("chart_5")).dispose()
      echarts.init(document.getElementById("chart_6")).dispose()
      echarts.init(document.getElementById("chart_7")).dispose()
      echarts.init(document.getElementById("chart_8")).dispose()
      echarts.init(document.getElementById("chart_9")).dispose()
      echarts.init(document.getElementById("chart_10")).dispose()
      echarts.init(document.getElementById("chart_11")).dispose()
    },
    timex(){
      let timex = (function () {
        let now = new Date();
        let res = [];
        let lenth = 2500;
        while (lenth--) {
          res.push(now.toLocaleTimeString());
          now = new Date(now.valueOf() - 4);
        }
        return res;
      })();
      const datenow = new Date()
      for (let b = 0; b < 2500; b++) {
        timex.unshift((new Date(datenow.valueOf() - (b * 4))).toLocaleTimeString());//datenow.valueOf()返回datenow数组的值
        timex.pop();
      }
      return timex
    },
    setMeanChart(){
      meanChart=echarts.getInstanceByDom(document.getElementById("meanChart"))
      if(meanChart== null){
        meanChart=echarts.init(document.getElementById("meanChart"))
      }
      meanChart.setOption({
        title: {
          show: true,
          text: '心率',
          left: 'center',
          bottom: 0,
          textStyle: {
            color: '#00FCFF',
            fontFamily: 'Microsoft YaHei',
            fontSize: 14,
          },
        },
        series: [{
          data: [this.data.hr_mean],
          name: '最外层',
          type: 'gauge',
          roundCap: true,
          radius: '77%',
          center: ['50%', '50%'],
          splitNumber: 0, //刻度数量
          startAngle: 225,
          endAngle: -45,
          z: 4,
          zlevel: 0,
          axisLine: {
            show: true,
            roundCap: true,
            lineStyle: {
              // 轴线样式
              width: '5', // 宽度
              color: [
                [
                  1,
                  new this.$echarts.graphic.LinearGradient(0, 0, 1, 0, [{
                    offset: 0,
                    color: '#19fdab',
                  },
                    {
                      offset: 0.5,
                      color: '#d0ff19',
                    },
                    {
                      offset: 1,
                      color: '#ff4026',
                    },
                  ]),
                ],
              ],
            },
          },
          //分隔线样式
          splitLine: {
            show: true,
          },
          axisLabel: {
            show: false,
          },
          axisTick: {
            show: true,
          },
          anchor: {
            show: true,
            size: 10,
            showAbove: true,
            itemStyle: {
              color: '#0e1327',
            },
          },
          pointer: {
            icon: 'path://M2090.36389,615.30999 L2090.36389,615.30999 C2091.48372,615.30999 2092.40383,616.194028 2092.44859,617.312956 L2096.90698,728.755929 C2097.05155,732.369577 2094.2393,735.416212 2090.62566,735.56078 C2090.53845,735.564269 2090.45117,735.566014 2090.36389,735.566014 L2090.36389,735.566014 C2086.74736,735.566014 2083.81557,732.63423 2083.81557,729.017692 C2083.81557,728.930412 2083.81732,728.84314 2083.82081,728.755929 L2088.2792,617.312956 C2088.32396,616.194028 2089.24407,615.30999 2090.36389,615.30999 Z',
            show: true,
            length: '90%',
            width: 16,
            offsetCenter: [0, '5%'],
            itemStyle: {
              color: new this.$echarts.graphic.LinearGradient(0, 1, 0, 0, [{
                offset: 0,
                color: '#FEAD54',
              },
                {
                  offset: 1,
                  color: '#0e1327',
                },
              ]),
            },
          },
          detail: {
            color: 'yellow',
            fontStyle: 'normal',
            fontWeight: 'bold',
            fontFamily: 'D-DIN',
            fontSize: 14,
            offsetCenter: [0, '50%'],
          },
        },
          {
            name: '第二层刻度',
            type: 'gauge',
            center: ['50%', '50%'],
            radius: '75%',
            // distance : 100,
            startAngle: 225,
            endAngle: -45,
            axisLine: {
              show: false,
            }, //仪表盘轴线
            axisLabel: {
              show: false,
              color: '#FFFFFF',
              distance: 20,
            }, //刻度标签。
            axisTick: {
              show: true,
              splitNumber: 5,
              lineStyle: {
                color: '#f40', //用颜色渐变函数不起作用
                width: 1,
              },
              distance: 20,
              length: 15,
            },
            splitLine: {
              show: false,
            },
            detail: {
              show: false,
            },
            pointer: {
              show: false,
            },
          },
          {
            name: '内部细刻度',
            type: 'gauge',
            center: ['50%', '50%'],
            radius: '55%',
            startAngle: 225,
            endAngle: -45,
            axisLine: {
              show: true,
              lineStyle: {
                width: 1,
                color: [
                  [
                    1,
                    new this.$echarts.graphic.LinearGradient(0, 0, 1, 0, [{
                      offset: 0,
                      color: '#224570',
                    },
                      {
                        offset: 1,
                        color: '#224570',
                      },
                    ]),
                  ],
                ],
              },
            }, //仪表盘轴线
            axisTick: {
              show: false,
            },
            //刻度线文字
            axisLabel: {
              show: false,
            },
            splitLine: {
              show: false,
            },
            detail: {
              show: false,
            },
            pointer: {
              show: false,
            },
          },
        ],
      })
      window.addEventListener('resize', () => {
        meanChart.resize();
      });

    },
    setAna(){
      Anachart = this.$echarts.getInstanceByDom(document.getElementById("anaoption"))
      if(Anachart== null) {
        Anachart = this.$echarts.init(document.getElementById("anaoption"))
      }
      Anachart.setOption({
        dataset: {
          source: [
            ['score', 'product'],
            [0, 'QTc间期过长'],
            [0, '窦室传导'],
            [(Number(this.data.ShiChan).toFixed(2)) * 100, '室颤室扑'],
            [0, '室性心动过速'],
            [(Number(this.data.XDGS_pr).toFixed(2)) * 100, '心动过速'],
            [(Number(this.data.XDGH_pr).toFixed(2)) * 100, '心动过缓'],
            [(Number(this.data.RRGC_pr).toFixed(2)) * 100, '长RR间期'],
            [(Number(this.data.XSL_pr).toFixed(2)) * 100, '心室率过低'],
            [(Number(this.data.FangChan).toFixed(2)) * 100, '房颤/房扑'],
            [(Number(this.data.GengSi).toFixed(2)) * 100, '心肌梗塞'],
          ]
        },
        grid: {
          top: 5,
          bottom: 40,
          left: 5,
          right: 50,
          containLabel: true
        },
        xAxis: {
          name: '置信度',
          min: 0,
          max: 100,
          axisLine: {
            lineStyle:{
              color: "#add4f3"
            }
          },
          axisLabel: { //修改坐标系字体颜色
            show: true,
            textStyle: {
              color: "#add4f3"
            }
          },
        },
        yAxis: {
          type: 'category',
          axisLine: {
            lineStyle:{
              color: "#add4f3"
            }
          },
          axisLabel: { //修改坐标系字体颜色
            show: true,
            textStyle: {
              fontSize: 15,
              color: "#8DB6DB"
            },
            interval: 0
          },
        },
        visualMap: {
          orient: 'horizontal',
          left: 'center',
          min: 0,
          max: 100,
          text: ['高风险', '低风险'],
          // Map the score column to color
          dimension: 0,
          textStyle: {
            color: "#8DB6DB"
          },
          inRange: {
            color: ['#65B581', '#FFCE34', '#FD665F']
          }
        },
        series: [
          {
            type: 'bar',
            encode: {
              // Map the "amount" column to X axis.
              x: 'score',
              // Map the "product" column to Y axis
              y: 'product'
            }
          }
        ]
      })
      window.addEventListener('resize', () => {
        Anachart.resize();
      });
    },
    setsdnn(){
      sdnnchart = this.$echarts.getInstanceByDom(document.getElementById("sdnnrmssd"))
      if(sdnnchart== null) {
        sdnnchart = this.$echarts.init(document.getElementById("sdnnrmssd"))
      }
      sdnnchart.setOption({
        dataset: {
          source: [
            ['score', 'product'],
            [Number(this.data.rMSSD).toFixed(0), 'RMSSD'],
            [Number(this.data.SDNN).toFixed(0), 'SDNN'],
          ]
        },
        grid: {
          top: '20%',
          bottom: '15%',
          left: '10%',
          right: '10%',
          containLabel: true
        },
        xAxis: {
          //name: '置信度' ,
          min: 20,
          max: 140,

          axisLabel: { //修改坐标系字体颜色
            show: true,
            textStyle: {
              color: "#8DB6DB"
            }
          },
        },
        yAxis: {
          type: 'category',
          axisLine: {
            lineStyle:{
              color: "#add4f3"
            }
          },
          axisLabel: { //修改坐标系字体颜色
            show: true,
            textStyle: {
              fontSize: 13,
              color: "#8DB6DB"
            },
            interval: 0
          },
        },
        visualMap: {
          orient: 'horizontal',
          left: 'center',
          min: 20,
          max: 140,
          show: false,
          text: ['高风险', '低风险'],
          // Map the score column to color
          dimension: 0,
          textStyle: {
            color: "#8DB6DB"
          },
          inRange: {
            color: ['#65B581', '#FFCE34', '#FD665F']
          }
        },
        series: [
          {
            type: 'bar',
            encode: {
              // Map the "amount" column to X axis.
              x: 'score',
              // Map the "product" column to Y axis
              y: 'product'
            }
          }
        ]
      });
      window.addEventListener('resize', () => {
        sdnnchart.resize();
      });
    },
    setpnn20(){
      pnn20chart = this.$echarts.getInstanceByDom(document.getElementById("pnn20"))
      if(pnn20chart== null) {
        pnn20chart = this.$echarts.init(document.getElementById("pnn20"))
      }
      pnn20chart.setOption({
        title: [{
          text: '{a|' + this.data.PNN20.toFixed(2) + '}{c|%}',
          x: 'center',
          y: '40%',
          textStyle: {
            rich: {
              a: {
                fontSize: 14,
                color: '#ffffff',
                fontWeight: 'bold'
              },
              c: {
                fontSize: 14,
                color: '#ffffff',
                fontWeight: 'normal'
              }
            }
          }
        },
          {
            text: 'pNN20',
            x: 'center',
            y: '55%',
            textStyle: {
              fontSize: 14,
              color: '#ffffff',
              fontWeight: 'bold'
            }
          }
        ],
        series: [
          //内环
          {
            name: "",
            type: 'custom',
            coordinateSystem: "none",
            renderItem: function (params, api) {
              return {
                type: 'arc',
                shape: {
                  cx: api.getWidth() / 2,
                  cy: api.getHeight() / 2,
                  r: Math.min(api.getWidth(), api.getHeight()) / 2.3 * 0.65,
                  startAngle: 0 * Math.PI / 180,
                  endAngle: 360 * Math.PI / 180
                },
                style: {
                  stroke: "#0CD3DB",
                  fill: "transparent",
                  lineWidth: 0.5
                },
                silent: true
              };
            },
            data: [0]
          },
          //外环
          {
            name: '',
            type: 'pie',
            radius: ['85%', '70%'],
            silent: true,
            clockwise: true,
            startAngle: 90,
            z: 0,
            zlevel: 0,
            label: {
              normal: {
                position: "center",
              }
            },
            data: [{
              value: this.data.PNN20,
              name: "",
              itemStyle: {
                normal: {
                  //外环发光
                  borderWidth: 0.5,
                  shadowBlur: 20,
                  borderColor: '#4bf3f9',
                  shadowColor: '#9bfeff',
                  color: { // 圆环的颜色
                    colorStops: [{
                      offset: 0,
                      color: '#4bf3f9', // 0% 处的颜色
                    }, {
                      offset: 1,
                      color: '#4bf3f9', // 100% 处的颜色
                    }]
                  },
                }
              }
            },
              {
                value: 100 - this.data.PNN20,
                name: "",
                label: {
                  normal: {
                    show: false
                  }
                },
                itemStyle: {
                  normal: {
                    color: "#173164"
                  }
                }
              }
            ]
          },
        ]
      })
      window.addEventListener('resize', () => {
        pnn20chart.resize();
      });
    },
    setpnn50(){
      pnn50chart = this.$echarts.getInstanceByDom(document.getElementById("pnn50"))
      if(pnn50chart== null) {
        pnn50chart = this.$echarts.init(document.getElementById("pnn50"))
      }
      pnn50chart.setOption({
        title: [{
          text: '{a|' + this.data.PNN50.toFixed(2) + '}{c|%}',
          x: 'center',
          y: '40%',
          textStyle: {
            rich: {
              a: {
                fontSize: 14,
                color: '#ffffff',
                fontWeight: 'bold'
              },
              c: {
                fontSize: 14,
                color: '#ffffff',
                fontWeight: 'normal'
              }
            }
          }
        },
          {
            text: 'pNN50',
            x: 'center',
            y: '55%',
            textStyle: {

              fontSize: 14,
              color: '#ffffff',
              fontWeight: 'bold'
            }
          }
        ],
        series: [
          //内环
          {
            name: "",
            type: 'custom',
            coordinateSystem: "none",
            renderItem: function (params, api) {
              return {
                type: 'arc',
                shape: {
                  cx: api.getWidth() / 2,
                  cy: api.getHeight() / 2,
                  r: Math.min(api.getWidth(), api.getHeight()) / 2.3 * 0.65,
                  startAngle: (0 + -0) * Math.PI / 180,
                  endAngle: (360 + -0) * Math.PI / 180
                },
                style: {
                  stroke: "#0CD3DB",
                  fill: "transparent",
                  lineWidth: 0.5
                },
                silent: true
              };
            },
            data: [0]
          },
          //外环
          {
            name: '',
            type: 'pie',
            radius: ['85%', '70%'],
            silent: true,
            clockwise: true,
            startAngle: 90,
            z: 0,
            zlevel: 0,
            label: {
              normal: {
                position: "center",
              }
            },
            data: [{
              value: this.data.PNN50,
              name: "",
              itemStyle: {
                normal: {
                  //外环发光
                  borderWidth: 0.5,
                  shadowBlur: 20,
                  borderColor: '#4bf3f9',
                  shadowColor: '#9bfeff',
                  color: { // 圆环的颜色
                    colorStops: [{
                      offset: 0,
                      color: '#4bf3f9', // 0% 处的颜色
                    }, {
                      offset: 1,
                      color: '#4bf3f9', // 100% 处的颜色
                    }]
                  },
                }
              }
            },
              {
                value: 100 - this.data.PNN50,
                name: "",
                label: {
                  normal: {
                    show: false
                  }
                },
                itemStyle: {
                  normal: {
                    color: "#173164"
                  }
                }
              }
            ]
          },
        ]
      })
      window.addEventListener('resize', () => {
        pnn50chart.resize();
      });
    },

    inScreen(){
      this.isFullFlag=!this.isFullFlag
      const element = document.getElementById('home');//指定全屏区域元素
      if(screenfull.isEnabled && !screenfull.isFullscreen){
        // screenfull.request(element);
        screenfull.request(element)
        return
      }
      screenfull.toggle(element)

    },
    back(){
      this.$router.push(
        {
          //添加需要传值到那个页面的路径
          path:'/Screen/screen',
        })
    },
    show(index){
      let data=[]
      switch (index) {
        case 0:
          data=I
          break;
        case 1:
          data=II
          break;
        case 2:
          data=III
          break;
        case 3:
          data=aVR
          break;
        case 4:
          data=aVL
          break;
        case 5:
          data=aVF
          break;
        case 6:
          data=V1
          break;
        case 7:
          data=V2
          break;
        case 8:
          data=V3
          break;
        case 9:
          data=V4
          break;
        case 10:
          data=V5
          break;
        case 11:
          data=V6
          break;
      }
      chart = echarts.getInstanceByDom(document.getElementById('chartshow'));
      if(chart== null) {
        chart = echarts.init(document.getElementById('chartshow'));
      }
      let option={
        animation: true,
        title: {
          text: '电位（mV）',
          textStyle: {
            fontSize: 13,
            color: "#8DB6DB"
          },
          top: 5,
          left:5,
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross'
          }
        },
        dataZoom:[
          {
              type: 'inside',   // 鼠标滚轮缩放
              start: 0,
              end: 100
            },
          {
          type: 'slider',
          show: true,
          // start: 0,
          // end: 100,
          xAxisIndex: [0],
           borderRadius: 2,
          // backgroundColor: 'rgba(227,227,227,0)',
          showDataShadow: true,//是否显示数据阴影 默认auto
          dataBackground: {
            lineStyle: {
              color: "#ffffff",
              width: 1,
              shadowBlur: 0.5,
            },
            areaStyle: {
              color: "rgba(143, 192, 225, 1)"
            }
          },
            filterMode: "none",
            realtime: true,
            brushSelect:true,
            brushStyle: {
              color: "rgba(71, 154, 222, 0.36)",
              borderColor: "rgba(238, 190, 190, 1)",
              borderWidth: 1,
              borderType: "dashed",
              borderDashOffset: 1
            }

        }],
        grid: {
          left: '2%' /*"50px"*/,
          right: '3%' /*"15px"*/,
          top: '8%',
          bottom: '20%',

        },
        legend: {
          show: false,
          data: ['当前电位'],
          textStyle: {color: "#66b3ff"} /*图例(legend)说明文字的颜色*/,
          left: "right",
        },
        xAxis: {
          boundaryGap: true,
          data: this.time,
          axisLabel: { //修改坐标系字体颜色
            interval: 299,
            show: true,
            textStyle: {
              color: "#8DB6DB"
            }
          },
          splitLine: {
            show: true,
            lineStyle: {
              type: 'solid',
              opacity: 0.3,
            },
            interval: 9,
          } /*网格线*/
        },
        yAxis: {
          type:"value",
          min: -3,
          max: 3,
          boundaryGap: false,
          splitNumber: 84,
          axisLabel: { //修改坐标系字体颜色
            show: false,
            textStyle: {
              color: "#8DB6DB"
            }
          },
          splitLine: {
            show: true,
            lineStyle: {
              type: 'solid',
              opacity: 0.3,
            },
          } /*网格线*/
        },
        series: [
          {
          markLine: {
            animation: false,
            symbol: "none",
            silent: true,
            lineStyle: {
              type: "solid",
              color: '#8DB6DB',
              width: 1,
              opacity: 0.5,
            },
            label: {
              show: false,
              position: 'start', // 表现内容展示的位置
              color: '#8DB6DB'  // 展示内容颜色
            },
            data: [
              {xAxis: 0},
              {xAxis: 50},
              {xAxis: 100},
              {xAxis: 150},
              {xAxis: 200},
              {xAxis: 250},
              {xAxis: 300},
              {xAxis: 350},
              {xAxis: 400},
              {xAxis: 450},
              {xAxis: 500},
              {xAxis: 550},
              {xAxis: 600},
              {xAxis: 650},
              {xAxis: 700},
              {xAxis: 750},
              {xAxis: 800},
              {xAxis: 850},
              {xAxis: 900},
              {xAxis: 950},
              {xAxis: 1000},
              {xAxis: 1050},
              {xAxis: 1100},
              {xAxis: 1150},
              {xAxis: 1200},
              {xAxis: 1250},
              {xAxis: 1300},
              {xAxis: 1350},
              {xAxis: 1400},
              {xAxis: 1450},
              {xAxis: 1500},
              {xAxis: 1550},
              {xAxis: 1600},
              {xAxis: 1650},
              {xAxis: 1700},
              {xAxis: 1750},
              {xAxis: 1800},
              {xAxis: 1850},
              {xAxis: 1900},
              {xAxis: 1950},
              {xAxis: 2000},
              {xAxis: 2050},
              {xAxis: 2100},
              {xAxis: 2150},
              {xAxis: 2200},
              {xAxis: 2250},
              {xAxis: 2300},
              {xAxis: 2350},
              {xAxis: 2400},
              {xAxis: 2450},
              {xAxis: 2500},
              {yAxis: -3},
              {yAxis: -2.5},
              {yAxis: -2},
              {yAxis: -1.5},
              {yAxis: -1},
              {yAxis: -0.5},
              {yAxis: 0},
              {yAxis: 0.5},
              {yAxis: 1},
              {yAxis: 1.5},
              {yAxis: 2},
              {yAxis: 2.5},
              {yAxis: 3},
            ]
          },
          /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
          itemStyle: {
            normal: {
              lineStyle: {
                color: '#92c2ff' /*折线的颜色*/
              },
              color: "#66b3ff" /*图例(legend)的颜色,不是图例说明文字的颜色*/
            }
          },
          symbol: "none",
          /*去掉小圆点*/
          name: '当前电位',
          type: 'line',
          data: data,
          smooth: 0 //显示为平滑的曲线*/
        },
          {
            markLine: {
              animation: false,
              symbol: "none",
              silent: true,
              lineStyle: {
                type: "solid",
                color: '#8DB6DB',
                width: 1,
                opacity: 0.5,
              },
              label: {
                position: 'start', // 表现内容展示的位置
                color: '#8DB6DB'  // 展示内容颜色
              },
              data: [
                {yAxis: -3},
                {yAxis: -2.5},
                {yAxis: -2},
                {yAxis: -1.5},
                {yAxis: -1},
                {yAxis: -0.5},
                {yAxis: 0},
                {yAxis: 0.5},
                {yAxis: 1},
                {yAxis: 1.5},
                {yAxis: 2},
                {yAxis: 2.5},
                {yAxis: 3},
              ]
            },
            /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
            itemStyle: {
              normal: {
                lineStyle: {
                  color: '#92c2ff' /*折线的颜色*/
                },
                color: "#66b3ff" /*图例(legend)的颜色,不是图例说明文字的颜色*/
              }
            },
            symbol: "none",
            /*去掉小圆点*/
            name: '当前电位',
            type: 'line',
            smooth: 0 //显示为平滑的曲线*/
          }]
      }
      window.addEventListener('resize', () => {
        chart.resize();
      });
      chart.clear()
      chart.setOption(option)
      this.isShow=true
      setTimeout(()=>{
        chart.resize();
      })

    },
    closeShow(){
      this.isShow=false
    },

  },
};
</script>

<style scoped lang="scss">
::v-deep .el-button{
  width: 7vw;
  height: 3.8vh;
  font-size: 0.9vw;
  padding: 0;
  margin-left: 5px;
  margin-top: 5px;
  text-align: center;
  color: #4cc9f0;
  border-color: #4cc9f0;
  background-color: rgba(0,0,0,0);
}
::v-deep .el-button:hover{
  background-color: #4cc9f0;
  color: white;
}
.home{
  width: 100%;
  height: 100vh;
  background-color: rgb(3,4,74);
  position: relative;
  .showbox{
    position: absolute;
    width: 98%;
    height:52vh;
    z-index: 2000;
    border: 1px solid #6EDDF1;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    .chart {
      border: 1px solid #6EDDF1;
      height: 52vh;
      background-color: rgb(3,4,74);
    }
    .el-button{
      position: absolute;
      top: 0;
      right: 0;
      margin: 0;
      width: 2.6vw;
      height: 3.2vh;
      font-size: 1vw;
      border-radius: 0.1vw;
    }
  }
  .top{
    height: 5%;
    display: flex;
    flex-direction: row;
    .text{
      display: flex;
      justify-content: center;
      width: 100%;
      span{
        color: #4cc9f0;
        font-size: 1.2vw;
        line-height: 6vh;
        margin-right: 70px;
      }
    }
  }
  .main{
    display: flex;
    height: 95vh;
    .left{
      display: flex;
      flex-direction: column;
      justify-content: space-around;
      height: 100%;
      width: 53vw;
      margin-left: 2.2vw;
      .container{
        position: relative;;
        width: 47.5vw;
        height: 8.2vh;
        margin: 0;
        padding: 0;
        div{
          width: 47vw;
          height: 100%;
          z-index: 1;
        }
        span{
          position: absolute;
          right: 0;
          height: 95%;
          width: 0.4vw;
          z-index: 3;
          background-color: greenyellow;
        }
        .el-button{
          position: absolute;
          top: 0;
          right: 0;
          width: 2.8vw;
          height: 3.2vh;
          margin: 0;
          padding: 0;
          z-index: 2;
          text-align: center;
          color: #4cc9f0;
          border-color: #4cc9f0;
          border-radius: 0.2vw;
          background-color: rgba(0,0,0,0);
        }
        .el-button:hover{
          background-color: #4cc9f0;
          color: white;
        }
      }
      .container:last-child{
        height: 10.5vh;
      }
    }
    .right{
      display: flex;
      flex-direction: column;
      justify-content: space-around;
      align-items: center;
      width: 46vw;
      .top{
        width: 80%;
        height: 40vh;

      }
      .center{
        display: flex;
        width: 80%;
        height: 20vh;
        .chart{
          width: 50%;
          height: 100%;
        }
        .text{
          display: flex;
          flex-direction: row;
          justify-self: center;
          align-items: center;
          flex-wrap: wrap;
          width: 50%;
          span{
            font-weight:bold;
            text-align: center;
            width: 50%;
            font-size:1.1vw;
            color: #5bd1fc;
            text-shadow: 0 0 5px rgba(76, 201, 240, 0.98),0 0 15px rgba(76, 201, 240, 0.84),0 0 25px rgba(76, 201, 240, 0.84),0 0 35px rgba(76, 201, 240, 0.84);
          }
        }
      }
      .bottom{
        display: flex;
        width: 80%;
        height: 20vh;
        .chart{
          width: 33%;
          height: 20vh;

        }
      }
    }
  }
}

</style>

