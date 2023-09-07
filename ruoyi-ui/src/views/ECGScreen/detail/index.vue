<template>
  <div class="home"  id="home">

    <div class="showbox" v-show="isShow">
      <div class="chart" id="chartshow" ></div>
      <el-button  @click="closeShow">关闭</el-button>
    </div>

    <el-dialog
      title="电话预警"
      :visible.sync="dialog"
      width="30%"
      :modal="false"
      center>
      <div slot="title" class="header-title">
        <span class="title-name">电话预警</span>
      </div>
      <div class="callbody">
        <div class="text1"><span>{{label}}({{value}})</span></div>
        <el-select v-model="label" placeholder="请选择拨号人" @change="select">
          <el-option
            v-for="item in options"
            :key="item.label"
            :label="item.label"
            :value="{value:item.value,label:item.label}">
          </el-option>
        </el-select>
        <img class="icon" @click="callPhone" src="~@/assets/images/call.png">
      </div>
      <div class="text2">
        <div class="in">
          <span>姓名</span>
          <span>通话时长</span>
          <span>通话时间</span>
        </div>
        <div class="in" v-for="item in calldata" :key="item.id">
          <span>{{item.role}}</span>
          <span>{{item.duration}}</span>
          <span>{{item.startTime}}</span>
        </div>
      </div>
<!--      <el-input type="textarea" v-model="textarea"></el-input>-->
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialog = false">确 定</el-button>
      </span>
    </el-dialog>

    <div class="top">
      <el-button @click="back">动态检测大屏</el-button>
      <el-button @click="inScreen">全屏切换</el-button>
      <el-button @click="call">电话预警</el-button>
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
          <span id="span0" ></span>
          <div id="chart_0" ref="chart"></div>
        </div>
        <div class="container">
          <el-button @click="show(1)">展开</el-button>
          <span id="span1" ></span>
          <div id="chart_1" ></div>
        </div>
        <div class="container">
          <el-button @click="show(2)">展开</el-button>
          <span id="span2" ></span>
          <div id="chart_2" ></div>
        </div>
        <div class="container">
          <el-button @click="show(3)">展开</el-button>
          <span id="span3" ></span>
          <div id="chart_3" ></div>
        </div>
        <div class="container">
          <el-button @click="show(4)">展开</el-button>
          <span id="span4" ></span>
          <div id="chart_4" ></div>
        </div>
        <div class="container">
          <el-button @click="show(5)">展开</el-button>
          <span id="span5" ></span>
          <div id="chart_5" ></div>
        </div>
        <div class="container">
          <el-button @click="show(6)">展开</el-button>
          <span id="span6"  ></span>
          <div id="chart_6" ></div>
        </div>
        <div class="container">
          <el-button @click="show(7)">展开</el-button>
          <span id="span7" ></span>
          <div id="chart_7" ></div>
        </div>
        <div class="container">
          <el-button @click="show(8)">展开</el-button>
          <span id="span8" ></span>
          <div id="chart_8" ></div>
        </div>
        <div class="container">
          <el-button @click="show(9)">展开</el-button>
          <span id="span9" ></span>
          <div id="chart_9" ></div>
        </div>
        <div class="container">
          <el-button @click="show(10)">展开</el-button>
          <span id="span10" ></span>
          <div id="chart_10" ></div>
        </div>
        <div class="container">
          <el-button @click="show(11)">展开</el-button>
          <span id="span11" ></span>
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
import request from '@/utils/request'
let meanChart;
let Anachart;
let sdnnchart;
let pnn20chart;
let pnn50chart;
let chart;
let data1,newData1,time=[];
let I=[],II=[],III=[],aVR=[],aVL=[],aVF=[],V1=[],V2=[],V3=[],V4=[],V5=[],V6=[];
let chartI,chartII,chartIII,chartaVR,chartaVL,chartaVF,chartV1,chartV2,chartV3,chartV4,chartV5,chartV6;
export default {
  name: "Index",
  data() {
    return {
      // 版本号
      options: [],
      value:'',
      label:'',
      textarea:'',
      isFullFlag:false,
      isShow:false,
      dialog: false,
      deviceSn:null,
      data: {},//前10秒数据
      newData: null,//最新10秒数据
      ts:0,//时间段
      timer:null,
      chartjump:null,
      index:0,
      calldata:[],
      pid:null,
    };
  },
  created() {
    this.deviceSn=this.$route.query.deviceSn;
    if(this.deviceSn!==null){
      this.index++
      if(this.index!==1){
        return
      }
      this.getlist()
      //this.getPhoneList()
      // this.getsdkURL()
    }
  },
  activated() {
    this.deviceSn=this.$route.query.deviceSn;
    this.data= {}
    newData1=null
    data1=null
    if(this.deviceSn!==null){
      this.index++
      if(this.index!==1){
        return
      }
      this.getlist()
      //this.getPhoneList()
      // this.getsdkURL()
    }
  },
  mounted(){
    chart = echarts.init(document.getElementById('chartshow'))
    pnn20chart = this.$echarts.init(document.getElementById("pnn20"))
    pnn50chart = this.$echarts.init(document.getElementById("pnn50"))
    sdnnchart = this.$echarts.init(document.getElementById("sdnnrmssd"))
    Anachart = this.$echarts.init(document.getElementById("anaoption"))
    meanChart=echarts.init(document.getElementById("meanChart"))
    window.addEventListener('resize', () => {
      meanChart.resize();
      Anachart.resize();
      sdnnchart.resize();
      pnn20chart.resize();
      pnn50chart.resize();
      chart.resize();
    });
  },
  beforeDestroy(){
   // //console.log("关闭页面")
    window.clearInterval(this.timer)
    this.timer=null
    this.data={}
    newData1=null
    data1=null
    this.disposeList()
  },
  deactivated(){//keep-alive的隐藏的钩子函数
  //  //console.log("离开页面")
    window.clearInterval(this.timer)
    this.timer=null
    this.data={}
    newData1=null
    this.index=0
    this.disposeList()
  },
  methods: {
    getsdkURL(){
      request({ url: '/callPhone/web/getsdkURL',
        method: 'post',
        }).then(res=>{
        this.injectTcccWebSDK(res.msg)
      })
    },
    injectTcccWebSDK(SdkURL) {
      if (window.tccc) {
        // console.warn('已经初始化SDK了，请确认是否重复执行初始化');
        return;
      }
      return new Promise((resolve, reject) => {
        const script = document.createElement('script');
        script.setAttribute('crossorigin', 'anonymous');
        script.src = SdkURL;
        document.body.appendChild(script)

        script.addEventListener('load', () => {
          // 加载JS SDK文件成功，此时可使用全局变量"tccc"
          //隐藏悬浮按钮
          window.tccc.UI.hidefloatButton()
          window.tccc.on(window.tccc.events.ready, () => {
            /**
             * Tccc SDK初始化成功，此时可调用外呼、监听呼入事件等功能。
             * 注意⚠️：请确保只初始化一次SDK
             * */
            resolve('初始化成功')
          });
          window.tccc.on(window.tccc.events.tokenExpired, ({message}) => {
            // console.error('初始化失败', message)
            reject(message)
          })
        })
      })
  },
    getPhoneList(){
      this.options=[]
      this.calldata=[]
      request({ url: '/patient_management/patient_management/getPhone',
        method: 'get',
        params:{'deviceSn':this.deviceSn}
      }).then(res=>{
        //console.log(res)
        if(res.data===undefined){
          return
        }
        res.data.forEach(item=>{
          if(item.role=="患者"){
            this.value=item.phone
            this.label=item.role
          }
          if(item.phone!==null||item.phone==""){
            this.options.push({value:item.phone?item.phone:"", label: item.role})
          }

        })
        //console.log(this.options)
      })
      request({ url: '/callLog/callLog/web/list',
        method: 'get',
        params:{'deviceSn':this.deviceSn}
      }).then(res=>{
        ////console.log(res)
        this.pid=res.data.pid
          res.data.list.forEach(item=>{
            this.calldata.push(item)
          })

      })
    },
    select(val){
      ////console.log(val)
      this.options.forEach(item=>{
        if(item.label===val.label){
          this.label=item.label
          this.value=item.value
          return
        }
      })
      ////console.log(this.value)
    },
    callPhone(){
      // window.tccc.Call.startOutboundCall({
      //   phoneNumber: '18336826103',
      // }).then((res) => {
      //   this.sessionId = res.data.sessionId;
      // }).catch((err) => {
      //   const error = err.errorMsg;
      // })
    },
    call(){
      this.getPhoneList()
      this.dialog=true
    },
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
        if(!res.data.result){
          this.$message.error("设备"+this.deviceSn+"数据获取失败")
          return
        }
        res.data.result.hr_mean = res.data.result.hr_mean.toFixed()
        this.data=res.data.result
        data1=res.data.result
        data1.noise.forEach((item,index)=>{
          if(item===true){
            document.getElementById('span'+index).style.backgroundColor="red"
          }else
            document.getElementById('span'+index).style.backgroundColor="greenyellow"
        })
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
        this.timex()
        chartI=echarts.init(document.getElementById("chart_0"))
        chartII=echarts.init(document.getElementById("chart_1"))
        chartIII=echarts.init(document.getElementById("chart_2"))
        chartaVR=echarts.init(document.getElementById("chart_3"))
        chartaVL=echarts.init(document.getElementById("chart_4"))
        chartaVF=echarts.init(document.getElementById("chart_5"))
        chartV1=echarts.init(document.getElementById("chart_6"))
        chartV2=echarts.init(document.getElementById("chart_7"))
        chartV3=echarts.init(document.getElementById("chart_8"))
        chartV4=echarts.init(document.getElementById("chart_9"))
        chartV5=echarts.init(document.getElementById("chart_10"))
        chartV6=echarts.init(document.getElementById("chart_11"))
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
            data: time,
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
            data: time,
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
            data: time,
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
            name: 'III',
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
            data: time,
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
            data: time,
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
            data: time,
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
            data: time,
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
            data: time,
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
            data: time,
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
            data: time,
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
            data: time,
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
            data: time,
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
           //console.log(res.data)
           newData1=null
           newData1=res.data.result
           newData1.hr_mean=newData1.hr_mean.toFixed()
         }).catch(err=>{
             // //console.log("错误信息"+err)
           })
        let ts=2
        if(this.timer){
          window.clearInterval(this.timer)
          this.timer=null
        }
        this.timer=window.setInterval(()=>{
            if(this.$route.path!=='/Screen/detail'){
              window.clearInterval(this.timer)
              this.timer=null
            }
          //console.log(newData1)
          if(newData1){
            ts++
            this.data=null
            this.data=newData1
            I=[]
            II=[]
            III=[]
            aVR=[]
            aVL=[]
            aVF=[]
            V1=[]
            V2=[]
            V3=[]
            V4=[]
            V5=[]
            V6=[]
            data1=[]
            data1=newData1
            data1.noise.forEach((item,index)=>{
              if(item===true){
                document.getElementById('span'+index).style.backgroundColor="red"
              }else
                document.getElementById('span'+index).style.backgroundColor="greenyellow"
            })
            I=newData1.data.I
            II=newData1.data.II
            III=newData1.data.III
            aVR=newData1.data.aVR
            aVL=newData1.data.aVL
            aVF=newData1.data.aVF
            V1=newData1.data.V1
            V2=newData1.data.V2
            V3=newData1.data.V3
            V4=newData1.data.V4
            V5=newData1.data.V5
            V6=newData1.data.V6
            this.setMeanChart()
            this.setsdnn()
            this.setpnn50()
            this.setpnn20()
            this.setAna()
            this.timex()
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
                data: time,
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
                data: time,
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
                data: time,
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
                data: time,
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
                data: time,
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
                data: time,
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
                data: time,
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
                data: time,
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
                data: time,
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
                data: time,
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
                data: time,
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
                data: time,
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
          newData1=null
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
            newData1=res.data.result
            newData1.hr_mean=newData1.hr_mean.toFixed()
          }).catch(err=>{
            // //console.log("错误信息"+err)
          })
        },10300)

      }).catch(err=>{
        // //console.log("错误信息"+err)
      })

    },
    disposeList(){
      if(chartI!=null){
        chartI.dispose()
        chartII.dispose()
        chartIII.dispose()
        chartaVR.dispose()
        chartaVL.dispose()
        chartaVF.dispose()
        chartV1.dispose()
        chartV2.dispose()
        chartV3.dispose()
        chartV4.dispose()
        chartV5.dispose()
        chartV6.dispose()
      }
      // meanChart.dispose()
      // Anachart.dispose()
      // sdnnchart.dispose()
      // pnn20chart.dispose()
      // pnn50chart.dispose()
      // chart.dispose()
    },
    timex(){
      time=[]
      let now = new Date();
      let lenth = 2500;
      while (lenth--) {
        time.push(now.toLocaleTimeString());
        now = new Date(now.valueOf() - 4);
      }
      const datenow = new Date()
      for (let b = 0; b < 2500; b++) {
        time.unshift((new Date(datenow.valueOf() - (b * 4))).toLocaleTimeString());//datenow.valueOf()返回datenow数组的值
        time.pop();
      }
    },
    setMeanChart(){

      // if(meanChart== null){
      //   meanChart=echarts.init(document.getElementById("meanChart"))
      // }
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
          data: [data1.hr_mean],
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
      },true)
    },
    setAna(){
      // Anachart = this.$echarts.getInstanceByDom(document.getElementById("anaoption"))
      // if(Anachart== null) {
      //   Anachart = this.$echarts.init(document.getElementById("anaoption"))
      // }
      Anachart.setOption({
        dataset: {
          source: [
            ['score', 'product'],
            [0, 'QTc间期过长'],
            [0, '窦室传导'],
            [(Number(data1.ShiChan).toFixed(2)) * 100, '室颤室扑'],
            [0, '室性心动过速'],
            [(Number(data1.XDGS_pr).toFixed(2)) * 100, '心动过速'],
            [(Number(data1.XDGH_pr).toFixed(2)) * 100, '心动过缓'],
            [(Number(data1.RRGC_pr).toFixed(2)) * 100, '长RR间期'],
            [(Number(data1.XSL_pr).toFixed(2)) * 100, '心室率过低'],
            [(Number(data1.FangChan).toFixed(2)) * 100, '房颤/房扑'],
            [(Number(data1.GengSi).toFixed(2)) * 100, '心肌梗塞'],
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
      },true)
    },
    setsdnn(){
      // sdnnchart = this.$echarts.getInstanceByDom(document.getElementById("sdnnrmssd"))
      // if(sdnnchart== null) {
      //   sdnnchart = this.$echarts.init(document.getElementById("sdnnrmssd"))
      // }
      sdnnchart.setOption({
        dataset: {
          source: [
            ['score', 'product'],
            [Number(data1.rMSSD).toFixed(0), 'RMSSD'],
            [Number(data1.SDNN).toFixed(0), 'SDNN'],
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
      },true);
    },
    setpnn20(){
      // pnn20chart = this.$echarts.getInstanceByDom(document.getElementById("pnn20"))
      // if(pnn20chart== null) {
      //   pnn20chart = this.$echarts.init(document.getElementById("pnn20"))
      // }
        pnn20chart.setOption({
          title: [{
            text: '{a|' + data1.PNN20.toFixed(2) + '}{c|%}',
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
                value: data1.PNN20,
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
                  value: 100 - data1.PNN20,
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
        },true)
    },
    setpnn50(){
      // pnn50chart = this.$echarts.getInstanceByDom(document.getElementById("pnn50"))
      // if(pnn50chart== null) {
      //   pnn50chart = this.$echarts.init(document.getElementById("pnn50"))
      // }
      pnn50chart.setOption({
        title: [{
          text: '{a|' + data1.PNN50.toFixed(2) + '}{c|%}',
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
              value: data1.PNN50,
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
                value: 100 - data1.PNN50,
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
      },true)

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
      // chart = echarts.getInstanceByDom(document.getElementById('chartshow'));
      // if(chart== null) {
      //   chart = echarts.init(document.getElementById('chartshow'));
      // }
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
          data: time,
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
      chart.clear()
      chart.setOption(option,true)
      data=[]
      this.isShow=true
      setTimeout(()=>{
        chart.resize();
      })

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
          path:'/ECGscreen',
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
::v-deep .el-dialog{
  position:absolute;
  top:50%;
  left:50%;
  z-index: 2000;
  transform:translate(-50%,-55%);
  background-color: rgb(47,49,89);
  border-radius: 10px;
}
::v-deep .el-dialog__headerbtn .el-dialog__close{
    color: white;
  }
::v-deep .el-dialog--center .el-dialog__body{
  display: flex;
  flex-direction: column;
  justify-content: center;
}
::v-deep .el-select{
  width: 200px;
}
::v-deep .el-select > .el-input{
  display: inline-block;
}
::v-deep .jss21{
  display:none;
}

.home{
  width: 100%;
  height: 100vh;
  background-color: rgb(3, 4, 73);
  position: relative;
  .callbody{
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items:center;
    text-align: center;
    color: white;
    .text1{
      font-size: 20px;
      margin-bottom: 20px;
    }
    .icon{
      width: 80px;
      height: 80px;
      margin: 20px auto;
      background-color: white;
      border-radius: 50%;
      cursor: pointer;
    }
  }
  .text2{
    display: flex;
    flex-direction: column;
    padding: 10px;
    color: white;
    border-top: 1px solid black;
    border-bottom: 1px solid black;
    margin-bottom: 20px;
    .in{
      display: flex;
      justify-content: space-between;
      padding: 6px;
      color: #85edd8;
      align-items: center;
    }
  }
  .header-title{
    text-align: left;
    color: white;
    font-size: 14px;
  }
  .showbox{
    position: absolute;
    width: 98%;
    height:52vh;
    z-index: 1000;
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

