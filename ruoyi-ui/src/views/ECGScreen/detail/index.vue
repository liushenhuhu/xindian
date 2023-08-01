<template>
  <div class="home"  id="home">

    <div class="showbox" v-show="isShow">
      <div class="chart" id="chartshow"></div>
      <el-button  @click="closeShow">关闭</el-button>
    </div>

    <div class="top">
      <el-button @click="back">动态检测大屏</el-button>
      <el-button @click="inScreen">进入全屏</el-button>
      <el-button @click="outScreen">退出全屏</el-button>
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
        <div class="container" v-for="(item,index) in 12" :key="index">
          <el-button @click="show(index)">展开</el-button>
          <span ></span>
          <div :id="getId(index)" ref="cahrt"></div>
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
import {
  get_device,
  list
} from "@/api/ECGScreen/equipment";
import 'default-passive-events'

export default {
  name: "Index",
  data() {
    return {
      // 版本号
      isFullFlag:false,
      isShow:false,
      deviceSn:null,
      data: {},//前10秒数据
      newData:[],//最新10秒数据
      arr:[],//12个图 点位  二位数组
      newArr:[],
      ts:0,//时间段
      arrList:['I','II','III','aVR','aVL','aVF','V1','V2','V3','V4','V5','V6'],
      timer:null,
      time:[],
      chartjump:null
    };
  },
  created() {
    this.deviceSn=this.$route.query.deviceSn;

  },
  activated() {
    this.getlist(this.deviceSn);
  },
  mounted() {
    if(this.deviceSn!==null){
      this.getlist(this.deviceSn)
    }

    // 监听页面全屏
    window.addEventListener("fullscreenchange", (e)=> {
      if(screenfull.isFullscreen){
        this.isFullFlag = true
      }else{
        this.isFullFlag = false
      }
    })
  },

  beforeRouteLeave(to, from, next){
    next();
    if (this.timer) {
      clearInterval(this.timer);
    }
  },

  methods: {
    getId:function (val){
      return 'cahrt_'+val
    },
    async getlist(){
      let res = await list(this.deviceSn,this.ts)
        console.log(res.result)
        res.result.hr_mean=res.result.hr_mean.toFixed()
        this.data=res.result
        this.arrList.forEach(item=>{
          this.arr.push(res.result.data[item])
        })
      this.$message.success("设备"+this.deviceSn+"数据获取成功")
      this.setChartList()
      this.timer=setInterval(this.setChartList,10100)

    },
    timex(){
      let now = new Date();
      let res = [];
      let lenth = 2500;
      while (lenth--) {
        res.push(now.toLocaleTimeString());
        now = new Date(now.valueOf() - 4);
      }
      return res;
    },
    setChartList(){

       if(this.newData!=null){
         Object.assign(this.data,this.newData)
         Object.assign(this.arr,this.newArr)
         // this.data=this.newData
         // this.arr=this.newArr
       }
       this.ts++
       this.newData=[]
       this.newArr=[]
       list(this.deviceSn,this.ts).then(res=>{
         res.result.hr_mean=res.result.hr_mean.toFixed()
         this.newData=res.result
         this.arrList.forEach(item=>{
           this.newArr.push(res.result.data[item])
         })
       })
      this.setMeanChart()
      this.setAna()
      this.setsdnn()
      this.setpnn20()
      this.setpnn50()
      this.arr.forEach((item,index)=>{
         let chart = this.$echarts.init(document.getElementById('cahrt_'+index))
         chart.clear();
         chart.setOption(this.getchartOption(index))
       })



    },
    getchartOption(id){
      let show=false
      let bottom=0.5
      if(id==11){
        show=true
        bottom=18
      }
      let datenow=new Date();
      let timex=this.timex()
      timex.unshift(datenow.toLocaleTimeString());
      timex.pop();
      for (let j = 1; j < 2500; j++) {
        timex.unshift((new Date(datenow.valueOf() - (j * 4))).toLocaleTimeString());
        timex.pop();
      }
      this.time=timex
      let option={
        animation: true,
        animationDuration: 9600,
        animationEasing: "linear",
        animationEasingUpdate: 'linear',
        animationDurationUpdate: 10000,
        animationDelayUpdate: 0,
        animationThreshold: 10000,

        title: {
          text: this.arrList[id]+'导联',
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
          left: 30 /*"50px"*/,
          right: 5 /*"15px"*/,
          top: 1,
          bottom: bottom
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
          data: timex,
          axisTick: {//x轴刻度
            show: false

          },
          axisLabel: { //x轴刻度数字
            show: show,
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
          min: 1,
          max: -1,
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
          name: this.arrList[id],
          type: 'line',
          data: this.arr[id],
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
            name: this.arrList[id],
            type: 'line',
            smooth: 0 //显示为平滑的曲线*/
          }]
      }


      return option;
    },

    setMeanChart(){
      let meanChart=this.$echarts.init(document.getElementById("meanChart"))
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
    },
    setAna(){
      let chart=this.$echarts.init(document.getElementById("anaoption"))
      chart.setOption({
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
          axisLabel: { //修改坐标系字体颜色
            show: true,
            textStyle: {
              color: "#8DB6DB"
            }
          },
        },
        yAxis: {
          type: 'category',
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

    },
    setsdnn(){
      let chart=this.$echarts.init(document.getElementById("sdnnrmssd"))
      chart.setOption({
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
    },
    setpnn20(){
      let chart=this.$echarts.init(document.getElementById("pnn20"))
      chart.setOption({
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

    },
    setpnn50(){
      let chart=this.$echarts.init(document.getElementById("pnn50"))
      chart.setOption({
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
    },

    setchartshow(data){

      this.chartjump = this.$echarts.init(document.getElementById('chartshow'));
      let option = {
        animation: true,
        backgroundColor: "#ffffff",
        title: {
          text: "111",
          textStyle: {
            fontSize: 13,
            color: "#000000"
          },
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross'
          }
        },
        toolbox: {
          left: 'center',
          right: '50%',
          top: 0,
          itemSize: 30,
        },
        dataZoom: [
          {
            type: 'inside',   // 鼠标滚轮缩放
            start: 0,
            end: 100
          },
          {
            show: true,       // 滑动条组件
            type: 'slider',
            y: '90%',
            start: 0,
            end: 100
          }
        ],
        // dataZoom: [{
        //     type: 'inside', // 放大和缩小
        //     orient: 'vertical',
        //     filterMode: "none",//或者”empty“
        // }, {
        //     type: 'inside',
        //     filterMode: "none",//或者”empty“
        // }, {
        //         // start: 0,//默认为0
        //         // end: 100,//默认为100
        //         type: 'slider',
        //         show: true,
        //         // xAxisIndex: [0],
        //         handleSize: 0,//滑动条的 左右2个滑动条的大小
        //         startValue: 0, // 初始显示值
        //         endValue: 500000, // 结束显示值,自己定
        //         height: 5,//组件高度
        //         left: '10%', //左边的距离
        //         right: '10%',//右边的距离
        //         bottom: '1%',//底边的距离
        //         borderColor: "#ccc",
        //         fillerColor: '#4cccfe',
        //         borderRadius: 5,
        //         backgroundColor: '#efefef',//两边未选中的滑动条区域的颜色
        //         showDataShadow: false,//是否显示数据阴影 默认auto
        //         showDetail: false,//即拖拽时候是否显示详细数值信息 默认true
        //         realtime: true, //是否实时更新
        //         filterMode: "none",//或者”empty“
        //     }],
        grid: {
          left: '1%',
          right: '3%',
          top: '5%',
          bottom: '10%'
        },
        legend: {
          show: false,
          data: ['当前电位'],
          textStyle: {color: "#000000"} /*图例(legend)说明文字的颜色*/,
          left: "right",
        },
        brush: {
          toolbox: ['lineX'],
          xAxisIndex: 0,//表示这个 index 所对应的坐标系
          throttleType: 'debounce',//开启选中延迟后调用回调延迟
          throttleDelay: 600,//选中延迟后调用回调延迟时间
          // brushStyle: {
          //     borderWidth: 1,
          //     color: 'rgba(255,36,36,0.2)',
          //     borderColor: '#ff2424'
          // }
        },
        xAxis: {
          boundaryGap: true,
          data: this.time,
          axisTick: {
            show: false
          },
          axisLabel: { //修改坐标系字体颜色
            interval: 4,
            show: false,
            textStyle: {
              color: "#000000"
            }
          },
          splitLine: {
            show: true,
            lineStyle: {
              color: 'pink',
              width: 1, //网格的宽度
              type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
            },

          } /*网格线*/
        },
        yAxis: {
          min: 3,
          max: -3,
          boundaryGap: true,
          interval: 0.1,
          axisLabel: { //修改坐标系字体颜色
            show: false,
            textStyle: {
              color: "#000000"
            }
          },
          splitLine: {
            show: true,
            lineStyle: {
              color: "pink",
              width: 1, //网格的宽度
              type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
            },
          } /*网格线*/
        },
        series: {
          id: 'series1',
          markLine: {
            animation: false,
            symbol: "none",
            silent: true,
            lineStyle: {
              type: "solid",
              color: '#b33939',
              width: 0.5,

            },
            label: {
              show: true,
              position: 'start', // 表现内容展示的位置
              color: '#b33939'  // 展示内容颜色
            },
            data: [
              {xAxis: 0},
              {xAxis: 25},
              {xAxis: 50},
              {xAxis: 75},
              {xAxis: 100},
              {xAxis: 125},
              {xAxis: 150},
              {xAxis: 175},
              {xAxis: 200},
              {xAxis: 225},
              {xAxis: 250},
              {xAxis: 275},
              {xAxis: 300},
              {xAxis: 325},
              {xAxis: 350},
              {xAxis: 375},
              {xAxis: 400},
              {xAxis: 425},
              {xAxis: 450},
              {xAxis: 475},
              {xAxis: 500},
              {xAxis: 525},
              {xAxis: 550},
              {xAxis: 575},
              {xAxis: 600},
              {xAxis: 625},
              {xAxis: 650},
              {xAxis: 675},
              {xAxis: 700},
              {xAxis: 725},
              {xAxis: 750},
              {xAxis: 775},
              {xAxis: 800},
              {xAxis: 825},
              {xAxis: 850},
              {xAxis: 875},
              {xAxis: 900},
              {xAxis: 925},
              {xAxis: 950},
              {xAxis: 975},
              {xAxis: 1000},
              {xAxis: 1025},
              {xAxis: 1050},
              {xAxis: 1075},
              {xAxis: 1100},
              {xAxis: 1125},
              {xAxis: 1150},
              {xAxis: 1175},
              {xAxis: 1200},
              {xAxis: 1225},
              {xAxis: 1250},
              {xAxis: 1275},
              {xAxis: 1300},
              {xAxis: 1325},
              {xAxis: 1350},
              {xAxis: 1375},
              {xAxis: 1400},
              {xAxis: 1425},
              {xAxis: 1450},
              {xAxis: 1475},
              {xAxis: 1500},
              {xAxis: 1525},
              {xAxis: 1550},
              {xAxis: 1575},
              {xAxis: 1600},
              {xAxis: 1625},
              {xAxis: 1650},
              {xAxis: 1675},
              {xAxis: 1700},
              {xAxis: 1725},
              {xAxis: 1750},
              {xAxis: 1775},
              {xAxis: 1800},
              {xAxis: 1825},
              {xAxis: 1850},
              {xAxis: 1875},
              {xAxis: 1900},
              {xAxis: 1925},
              {xAxis: 1950},
              {xAxis: 1975},
              {xAxis: 2000}, {xAxis: 2025}, {xAxis: 2050}, {xAxis: 2100}, {xAxis: 2125}, {xAxis: 2150}, {xAxis: 2175}, {xAxis: 2200}, {xAxis: 2225}, {xAxis: 2250}, {xAxis: 2275}, {xAxis: 2300}, {xAxis: 2325}, {xAxis: 2350},
              {xAxis: 2375}, {xAxis: 2400}, {xAxis: 2425}, {xAxis: 2450}, {xAxis: 2475}, {xAxis: 2500},

              {yAxis: -3}, {yAxis: -2.5}, {yAxis: -2}, {yAxis: -1.5}, {yAxis: -1}, {yAxis: -0.5}, {yAxis: 0}, {yAxis: 0.5}, {yAxis: 1}, {yAxis: 1.5}, {yAxis: 2}, {yAxis: 2.5}, {yAxis: 3},],
          },
          itemStyle: {
            normal: {
              lineStyle: {
                color: '#000000',/*折线的颜色*/
              },
              color: "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/
            }
          },
          // markArea: {
          //     z: 0,
          //     //  显示标签，且标签位于区域内部
          //     label: {
          //         show: true,
          //         position: 'inside'
          //     },
          //     itemStyle: {
          //         color: 'rgb(191,250,141)',
          //         borderColor: "rgb(6,115,43)",
          //         borderWidth: 2,
          //         borderType: "dotted",
          //     },
          //     data: xdata
          // },
          symbol: "none",
          name: '当前电位',
          type: 'line',
          data: data,
          smooth: 0 //显示为平滑的曲线*/
        },
      };
      this.chartjump.setOption(option, true);
      //brush框选数据
      // this.chartjump.on('brushSelected', this.renderBrushed);
    },
    renderBrushed(params) {
      var checkBrush=0
      var firstBrush = []//画请求的数据
      var secendBrush = []//画框选的数据
      var firstBrushoff = []//画删除之后请求的数据
      var secendBrushoff = []//画删除之后框选的数据
      var allselectData = []//两个区域加在一起
      var Xselectbrush=[]
      var selectData=[]
      if (params.batch[0].areas[0]) {
        checkBrush = 1
        for (var k = 0; k < params.batch[0].areas.length; k++) {
          console.log("params框选的区域", params.batch[0].areas[k].coordRange)
          if (params.batch[0].areas[k].coordRange !== undefined) {
            Xselectbrush.push(params.batch[0].areas[k].coordRange);
          }
        }
      }
      for (var i = 0; i < selectData.length; i++) {
        firstBrush[i] = []
        firstBrush[i][0] = {xAxis: selectData[i][0]}
        firstBrush[i][1] = {xAxis: selectData[i][1]}
        // allbrush.unshift(xdata)
      }
      console.log("处理后的请求数据：", firstBrush)
      Xselectbrush = Xselectbrush.filter(item => item.length !== 0);//去掉[]空值
      Xselectbrush = Xselectbrush.filter(value => value !== null);//去掉null值
      Xselectbrush = Xselectbrush.filter((item, index) => {
        return Xselectbrush.indexOf(item) === index;
      });//数组去重
      console.log("未处理框选的区域:", Xselectbrush)
      for (var i = 0; i < Xselectbrush.length; i++) {
        secendBrush[i] = []
        secendBrush[i][0] = {xAxis: Xselectbrush[i][0]}
        secendBrush[i][1] = {xAxis: Xselectbrush[i][1]}
      }
      console.log("处理过框选的区域:", firstBrush)
      allselectData = firstBrush.concat(secendBrush)
      allselectData = allselectData.filter((item, index) => {
        return allselectData.indexOf(item) === index;
      });//数组去重
      console.log("所有处理过的数据：", allselectData)
      chartjump.setOption({
        series: [{
          id: 'series1',
          markArea: {
            // z: 0,
            //  显示标签，且标签位于区域内部
            label: {
              show: true,
              position: 'inside'
            },
            itemStyle: {
              color: 'rgba(102,2,2,0.5)',
              // borderColor: "rgb(6,115,43)",
              // borderWidth: 2,
              // borderType: "dotted",
            },
            data: allselectData
          },
        }]
      });
      chartjump.getZr().on('dblclick', function (param) {
        checkBrush = 1
        console.log("数据请求未处理数据：", selectData)
        console.log("框选区域未处理数据：", Xselectbrush)
        const pointInPixel = [param.offsetX, param.offsetY]
        // console.log('pointInPixel', pointInPixel)
        // 使用 convertFromPixel方法 转换像素坐标值到逻辑坐标系上的点。获取点击位置对应的x轴数据的索引值，借助于索引值的获取到其它的信息
        // 转换X轴坐标
        let pointInGrid = chartjump.convertFromPixel({seriesIndex: 0}, pointInPixel);
        // x轴数据的索引值
        console.log('对应的x轴坐标：', pointInGrid[0])
        // 所点击点的X轴坐标点所在X轴data的下标
        let xIndex = pointInGrid[0];
        selectData = selectData.filter(item => item.length !== 0);//去掉[]空值
        Xselectbrush = Xselectbrush.filter(item => item.length !== 0);//去掉[]空值
        selectData = selectData.filter(value => value !== null);//去掉null值
        Xselectbrush = Xselectbrush.filter(value => value !== null);//去掉null值
        selectData = selectData.filter((item, index) => {
          return selectData.indexOf(item) === index;
        });//数组去重
        Xselectbrush = Xselectbrush.filter((item, index) => {
          return Xselectbrush.indexOf(item) === index;
        });//数组去重
        console.log("过滤之后:", selectData, Xselectbrush)
        for (var i = 0; i < selectData.length; i++) {
          if (xIndex >= selectData[i][0] && xIndex <= selectData[i][1]) {
            console.log('删除的数据:', selectData[i])
            selectData.splice(i, 1)
            console.log("删除之后未处理的框选区域：", selectData)
            for (var i = 0; i < selectData.length; i++) {
              firstBrushoff[i] = []
              firstBrushoff[i][0] = {xAxis: selectData[i][0]}
              firstBrushoff[i][1] = {xAxis: selectData[i][1]}
            }
            console.log("删除之后处理过的框选区域：", firstBrushoff)
            chartjump.setOption({
              series: [{
                id: 'series1',
                markArea: {
                  // z: 0,
                  //  显示标签，且标签位于区域内部
                  label: {
                    show: true,
                    position: 'inside'
                  },
                  itemStyle: {
                    color: 'rgba(102,2,2,0.5)',
                    // borderColor: "rgb(6,115,43)",
                    // borderWidth: 2,
                    // borderType: "dotted",
                  },
                  data: firstBrushoff
                },
              }]
            });
            chartjump.dispatchAction({
              type: 'brush',
              command: 'clear',
              areas: [
                {
                  brushType: 'lineX',
                  // xAxisIndex: 0,
                  coordRange: selectData[i],
                },
              ],
            });
            // i = i - 1
          }
        }
        for (var i = 0; i < Xselectbrush.length; i++) {
          if (xIndex >= Xselectbrush[i][0] && xIndex <= Xselectbrush[i][1]) {
            Xselectbrush.splice(i, 1)
            console.log('删除的数据:', Xselectbrush[i])
            console.log("删除之后未处理的框选区域：", Xselectbrush)
            for (var i = 0; i < Xselectbrush.length; i++) {
              secendBrushoff[i] = []
              secendBrushoff[i][0] = {xAxis: Xselectbrush[i][0]}
              secendBrushoff[i][1] = {xAxis: Xselectbrush[i][1]}
            }
            console.log("删除之后处理过的框选区域：", secendBrushoff)
            chartjump.setOption({
              series: [{
                id: 'series1',
                markArea: {
                  // z: 0,
                  //  显示标签，且标签位于区域内部
                  label: {
                    show: true,
                    position: 'inside'
                  },
                  itemStyle: {
                    color: 'rgba(102,2,2,0.5)',
                    // borderColor: "rgb(6,115,43)",
                    // borderWidth: 2,
                    // borderType: "dotted",
                  },
                  data: secendBrushoff
                },
              }]
            });
            chartjump.dispatchAction({
              type: 'brush',
              command: 'clear',
              areas: [
                {
                  brushType: 'lineX',
                  // xAxisIndex: 0,
                  coordRange: Xselectbrush[i],
                },
              ],
            });
          }
        }
      });
    },
    inScreen(){
      this.isFullFlag=true
      const element = document.getElementById('home');//指定全屏区域元素
      if(this.isFullFlag){
        // screenfull.request(element);
        element.requestFullscreen()
      }
    },
    outScreen(){
      this.isFullFlag=false
      document.exitFullscreen();
    },
    back(){
      this.$router.push(
        {
          //添加需要传值到那个页面的路径
          path:'/Screen/screen',
        })
    },
    show(index){
      let chart=this.$echarts.init(document.getElementById('chartshow'));
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
        dataZoom:[{
          type: 'slider',
          show: true,
          start: 0,
          end: 100,
          xAxisIndex: [0],
          backgroundColor: 'rgba(227,227,227,0)',
          showDataShadow: true,//是否显示数据阴影 默认auto
          dataBackground: {
            lineStyle: {
              color: "#70deff",
              width: 1
            }
          },


        }],
        // dataZoom: [
        //   // {
        //   //   type: 'inside',   // 鼠标滚轮缩放
        //   //   start: 0,
        //   //   end: 100
        //   // },
        //   {
        //     show: true,       // 滑动条组件
        //     type: 'slider',
        //     // y: '90%',
        //     start: 0,
        //     end: 100,
        //     // borderColor: "#ccc",
        //     fillerColor: 'rgba(13,76,245,0.23)',
        //     // borderRadius: 5,
        //     // backgroundColor: '#EFEFEF00',//两边未选中的滑动条区域的颜色
        //     showDetail: true,
        //     showDataShadow: true,//是否显示数据阴影 默认auto
        //     realtime: true, //是否实时更新
        //     filterMode: "empty",//或者”empty“
        //     brushSelect: true,//是否开启刷选功能。在下图的brush区域你可以按住鼠标左键后框选出选中部分
        //     // brushStyle: {
        //     //   borderWidth: 1,
        //     //   color: 'rgba(255,36,36,0.2)',
        //     //   borderColor: '#ff2424'
        //     // }
        //   },
        // ],
      // dataZoom: [{
      //   type: 'inside', // 放大和缩小
      //   orient: 'vertical',
      //   filterMode: "none",//或者”empty“
      // }, {
      //   type: 'inside',
      //   filterMode: "none",//或者”empty“
      // }, {
      //   start: 0,//默认为0
      //   end: 100,//默认为100
      //   type: 'slider',
      //   show: true,
      //   // xAxisIndex: [0],
      //   handleSize: "100%",//滑动条的 左右2个滑动条的大小
      //   // startValue: 0, // 初始显示值
      //   // endValue: 100, // 结束显示值,自己定
      //   height: 10,//组件高度
      //   left: '10%', //左边的距离
      //   right: '15%',//右边的距离
      //   bottom: '1%',//底边的距离
      //   borderColor: "#ccc",
      //   fillerColor: '#4cccfe',
      //   borderRadius: 5,
      //   backgroundColor: '#efefef',//两边未选中的滑动条区域的颜色
      //   showDataShadow: false,//是否显示数据阴影 默认auto
      //   showDetail: false,//即拖拽时候是否显示详细数值信息 默认true
      //   realtime: true, //是否实时更新
      //   filterMode: "none",//或者”empty“
      // }],
      grid: {
        left: '2%' /*"50px"*/,
          right: '3%' /*"15px"*/,
          top: '8%',
          bottom: '15%'
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
        min: 3,
          max: -3,
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
        data: this.arr[index],
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
      // let option = {
      //   animation: true,
      //   backgroundColor: "#ffffff",
      //   title: {
      //     text: this.arrList[index],
      //     textStyle: {
      //       fontSize: 13,
      //       color: "#000000"
      //     },
      //   },
      //   tooltip: {
      //     trigger: 'axis',
      //     axisPointer: {
      //       type: 'cross'
      //     }
      //   },
      //   toolbox: {
      //     left: 'center',
      //     right: '50%',
      //     top: 0,
      //     itemSize: 30,
      //   },
      //   // dataZoom: [
      //   //   {
      //   //     type: 'inside',   // 鼠标滚轮缩放
      //   //     start: 0,
      //   //     end: 100
      //   //   },
      //   //   {
      //   //     show: true,       // 滑动条组件
      //   //     type: 'slider',
      //   //     y: '90%',
      //   //     start: 0,
      //   //     end: 100
      //   //   }
      //   // ],
      //   dataZoom: [{
      //       type: 'inside', // 放大和缩小
      //       orient: 'vertical',
      //       filterMode: "none",//或者”empty“
      //   }, {
      //       type: 'inside',
      //       filterMode: "none",//或者”empty“
      //   }, {
      //           // start: 0,//默认为0
      //           // end: 100,//默认为100
      //           type: 'slider',
      //           show: true,
      //           // xAxisIndex: [0],
      //           handleSize: 0,//滑动条的 左右2个滑动条的大小
      //           startValue: 0, // 初始显示值
      //           endValue: 500000, // 结束显示值,自己定
      //           height: 5,//组件高度
      //           left: '10%', //左边的距离
      //           right: '10%',//右边的距离
      //           bottom: '1%',//底边的距离
      //           borderColor: "#ccc",
      //           fillerColor: '#4cccfe',
      //           borderRadius: 5,
      //           backgroundColor: '#efefef',//两边未选中的滑动条区域的颜色
      //           showDataShadow: false,//是否显示数据阴影 默认auto
      //           showDetail: false,//即拖拽时候是否显示详细数值信息 默认true
      //           realtime: true, //是否实时更新
      //           filterMode: "none",//或者”empty“
      //       }],
      //   grid: {
      //     left: '1%',
      //     right: '3%',
      //     top: '5%',
      //     bottom: '10%'
      //   },
      //   legend: {
      //     show: false,
      //     data: ['当前电位'],
      //     textStyle: {color: "#000000"} /*图例(legend)说明文字的颜色*/,
      //     left: "right",
      //   },
      //   brush: {
      //     toolbox: ['lineX'],
      //     xAxisIndex: 0,//表示这个 index 所对应的坐标系
      //     throttleType: 'debounce',//开启选中延迟后调用回调延迟
      //     throttleDelay: 600,//选中延迟后调用回调延迟时间
      //     // brushStyle: {
      //     //     borderWidth: 1,
      //     //     color: 'rgba(255,36,36,0.2)',
      //     //     borderColor: '#ff2424'
      //     // }
      //   },
      //   xAxis: {
      //     boundaryGap: true,
      //     data: this.time,
      //     axisTick: {
      //       show: false
      //     },
      //     axisLabel: { //修改坐标系字体颜色
      //       interval: 4,
      //       show: false,
      //       textStyle: {
      //         color: "#000000"
      //       }
      //     },
      //     splitLine: {
      //       show: true,
      //       lineStyle: {
      //         color: 'pink',
      //         width: 1, //网格的宽度
      //         type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
      //       },
      //
      //     } /*网格线*/
      //   },
      //   yAxis: {
      //     min: 3,
      //     max: -3,
      //     boundaryGap: true,
      //     interval: 0.1,
      //     axisLabel: { //修改坐标系字体颜色
      //       show: false,
      //       textStyle: {
      //         color: "#000000"
      //       }
      //     },
      //     splitLine: {
      //       show: true,
      //       lineStyle: {
      //         color: "pink",
      //         width: 1, //网格的宽度
      //         type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
      //       },
      //     } /*网格线*/
      //   },
      //   series: {
      //     id: 'series1',
      //     markLine: {
      //       animation: false,
      //       symbol: "none",
      //       silent: true,
      //       lineStyle: {
      //         type: "solid",
      //         color: '#b33939',
      //         width: 0.5,
      //
      //       },
      //       label: {
      //         show: true,
      //         position: 'start', // 表现内容展示的位置
      //         color: '#b33939'  // 展示内容颜色
      //       },
      //       data: [
      //         {xAxis: 0},
      //         {xAxis: 25},
      //         {xAxis: 50},
      //         {xAxis: 75},
      //         {xAxis: 100},
      //         {xAxis: 125},
      //         {xAxis: 150},
      //         {xAxis: 175},
      //         {xAxis: 200},
      //         {xAxis: 225},
      //         {xAxis: 250},
      //         {xAxis: 275},
      //         {xAxis: 300},
      //         {xAxis: 325},
      //         {xAxis: 350},
      //         {xAxis: 375},
      //         {xAxis: 400},
      //         {xAxis: 425},
      //         {xAxis: 450},
      //         {xAxis: 475},
      //         {xAxis: 500},
      //         {xAxis: 525},
      //         {xAxis: 550},
      //         {xAxis: 575},
      //         {xAxis: 600},
      //         {xAxis: 625},
      //         {xAxis: 650},
      //         {xAxis: 675},
      //         {xAxis: 700},
      //         {xAxis: 725},
      //         {xAxis: 750},
      //         {xAxis: 775},
      //         {xAxis: 800},
      //         {xAxis: 825},
      //         {xAxis: 850},
      //         {xAxis: 875},
      //         {xAxis: 900},
      //         {xAxis: 925},
      //         {xAxis: 950},
      //         {xAxis: 975},
      //         {xAxis: 1000},
      //         {xAxis: 1025},
      //         {xAxis: 1050},
      //         {xAxis: 1075},
      //         {xAxis: 1100},
      //         {xAxis: 1125},
      //         {xAxis: 1150},
      //         {xAxis: 1175},
      //         {xAxis: 1200},
      //         {xAxis: 1225},
      //         {xAxis: 1250},
      //         {xAxis: 1275},
      //         {xAxis: 1300},
      //         {xAxis: 1325},
      //         {xAxis: 1350},
      //         {xAxis: 1375},
      //         {xAxis: 1400},
      //         {xAxis: 1425},
      //         {xAxis: 1450},
      //         {xAxis: 1475},
      //         {xAxis: 1500},
      //         {xAxis: 1525},
      //         {xAxis: 1550},
      //         {xAxis: 1575},
      //         {xAxis: 1600},
      //         {xAxis: 1625},
      //         {xAxis: 1650},
      //         {xAxis: 1675},
      //         {xAxis: 1700},
      //         {xAxis: 1725},
      //         {xAxis: 1750},
      //         {xAxis: 1775},
      //         {xAxis: 1800},
      //         {xAxis: 1825},
      //         {xAxis: 1850},
      //         {xAxis: 1875},
      //         {xAxis: 1900},
      //         {xAxis: 1925},
      //         {xAxis: 1950},
      //         {xAxis: 1975},
      //         {xAxis: 2000}, {xAxis: 2025}, {xAxis: 2050}, {xAxis: 2100}, {xAxis: 2125}, {xAxis: 2150}, {xAxis: 2175}, {xAxis: 2200}, {xAxis: 2225}, {xAxis: 2250}, {xAxis: 2275}, {xAxis: 2300}, {xAxis: 2325}, {xAxis: 2350},
      //         {xAxis: 2375}, {xAxis: 2400}, {xAxis: 2425}, {xAxis: 2450}, {xAxis: 2475}, {xAxis: 2500},
      //
      //         {yAxis: -3}, {yAxis: -2.5}, {yAxis: -2}, {yAxis: -1.5}, {yAxis: -1}, {yAxis: -0.5}, {yAxis: 0}, {yAxis: 0.5}, {yAxis: 1}, {yAxis: 1.5}, {yAxis: 2}, {yAxis: 2.5}, {yAxis: 3},],
      //     },
      //     itemStyle: {
      //       normal: {
      //         lineStyle: {
      //           color: '#000000',/*折线的颜色*/
      //         },
      //         color: "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/
      //       }
      //     },
      //     // markArea: {
      //     //     z: 0,
      //     //     //  显示标签，且标签位于区域内部
      //     //     label: {
      //     //         show: true,
      //     //         position: 'inside'
      //     //     },
      //     //     itemStyle: {
      //     //         color: 'rgb(191,250,141)',
      //     //         borderColor: "rgb(6,115,43)",
      //     //         borderWidth: 2,
      //     //         borderType: "dotted",
      //     //     },
      //     //     data: xdata
      //     // },
      //     symbol: "none",
      //     name: '当前电位',
      //     type: 'line',
      //     data: this.arr[index],
      //     smooth: 0 //显示为平滑的曲线*/
      //   },
      // }
      chart.setOption(option, true)
      this.isShow=true
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
    width: 98vw;
    height:50vh;
    z-index: 2000;
    border: 1px solid #6EDDF1;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    .chart {
      position: absolute;
      border: 1px solid #f0f0f0;
      width: 98vw;
      height: 50vh;
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
    width: 100%;
    height: 95%;
    margin-left: 0.5vw;
    .left{
      display: flex;
      flex-direction: column;
      justify-content: space-around;
      height: 100%;
      width: 54vw;
      .container{
        position: relative;;
        width: 55vw;
        height: 8.1%;
        div{
          width: 55vw;
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
        height: 10%;
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
            text-align: center;
            width: 50%;
            font-size:1vw;
            color: #4cc9f0;
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

