<template>
  <div class="main">
    <div class="top">
      <div class="topLeft">
        <div class="message">患者信息</div>
        <div class="messageDetail">
          <span id="pID">患者ID：{{ message.pid }}</span>
          <span id="logID">日志ID：{{ message.logid }}</span>
          <span id="psex">性别：{{ message.sex }}</span>
          <span id="pAge">年龄：{{ message.age }}</span>
          <span id="clock">时间：{{ message.time }}</span>
        </div>
      </div>
      <div class="topMiddle">
        <div class="warning">预警类型</div>
        <div class="warningDetail">
          <div class="warningValue">{{ value }}</div>
        </div>
      </div>
    </div>
    <div class="bottom">
      <div class="bottomLeft">
        <div class="xinDian">心电图</div>
        <div class="echarts">
          <div class="container">
            <div class="chart" id="I" @dblclick="showchart(this,Iy,timex)"></div>
            <!--            <button class="btn" id="I导联" onclick="showchart(this,Iy,timex)">展开</button>-->
            <span class="light" id="Ilight" onclick="changeColor(this)"></span>
          </div>
          <div class="container">
            <div class="chart" id="II"></div>
            <!--            <button class="btn" id="II导联" onclick="showchart(this,IIy,timex)">展开</button>-->
            <span class="light" id="IIlight" onclick="changeColor(this)"></span>
          </div>
          <div class="container">
            <div class="chart" id="III"></div>
            <!--            <button class="btn" id="III导联" onclick="showchart(this,IIIy,timex)">展开</button>-->
            <span class="light" id="IIIlight" onclick="changeColor(this)"></span>
          </div>
          <div class="container">
            <div class="chart" id="aVR"></div>
            <!--            <button class="btn" id="aVR导联" onclick="showchart(this,aVRy,timex)">展开</button>-->
            <span class="light" id="aVRlight" onclick="changeColor(this)"></span>
          </div>
          <div class="container">
            <div class="chart" id="aVL"></div>
            <!--            <button class="btn" id="aVL导联" onclick="showchart(this,aVLy,timex)">展开</button>-->
            <span class="light" id="aVLlight" onclick="changeColor(this)"></span>
          </div>
          <div class="container">
            <div class="chart" id="aVF"></div>
            <!--            <button class="btn" id="aVF导联" onclick="showchart(this,aVFy,timex)">展开</button>-->
            <span class="light" id="aVFlight" onclick="changeColor(this)"></span>
          </div>
          <div class="container">
            <div class="chart" id="V1"></div>
            <!--            <button class="btn" id="V1导联" onclick="showchart(this,V1y,timex)">展开</button>-->
            <span class="light" id="V1light" onclick="changeColor(this)"></span>
          </div>
          <div class="container">
            <div class="chart" id="V2"></div>
            <!--            <button class="btn" id="V2导联" onclick="showchart(this,V2y,timex)">展开</button>-->
            <span class="light" id="V2light" onclick="changeColor(this)"></span>
          </div>
          <div class="container">
            <div class="chart" id="V3"></div>
            <!--            <button class="btn" id="V3导联" onclick="showchart(this,V3y,timex)">展开</button>-->
            <span class="light" id="V3light" onclick="changeColor(this)"></span>
          </div>
          <div class="container">
            <div class="chart" id="V4"></div>
            <!--            <button class="btn" id="V4导联" onclick="showchart(this,V4y,timex)">展开</button>-->
            <span class="light" id="V4light" onclick="changeColor(this)"></span>
          </div>
          <div class="container">
            <div class="chart" id="V5"></div>
            <!--            <button class="btn" id="V5导联" onclick="showchart(this,V5y,timex)">展开</button>-->
            <span class="light" id="V5light" onclick="changeColor(this)"></span>
          </div>
          <div class="container">
            <div class="chart" id="V6"></div>
            <!--            <button class="btn" id="V6导联" onclick="showchart(this,V6y,timex)">展开</button>-->
            <span class="light" id="V6light" onclick="changeColor(this)"></span>
          </div>
        </div>
      </div>
      <div class="bottomRight">
        <div class="xinDian">质量评估</div>
        <div class="quality">
          <div class="lights">
            <div class="abcd">
              <el-radio v-model="noise_level.Ilevel" label="A" border size="mini" class="radios">A</el-radio>
              <el-radio v-model="noise_level.Ilevel" label="B" border size="mini" class="radios">B</el-radio>
              <el-radio v-model="noise_level.Ilevel" label="C" border size="mini" class="radios">C</el-radio>
              <el-radio v-model="noise_level.Ilevel" label="D" border size="mini" class="radios">D</el-radio>
            </div>
            <div class="abcd">
              <el-radio v-model="noise_level.IIlevel" label="A" border size="mini" class="radios">A</el-radio>
              <el-radio v-model="noise_level.IIlevel" label="B" border size="mini" class="radios">B</el-radio>
              <el-radio v-model="noise_level.IIlevel" label="C" border size="mini" class="radios">C</el-radio>
              <el-radio v-model="noise_level.IIlevel" label="D" border size="mini" class="radios">D</el-radio>
            </div>
            <div class="abcd">
              <el-radio v-model="noise_level.IIIlevel" label="A" border size="mini" class="radios">A</el-radio>
              <el-radio v-model="noise_level.IIIlevel" label="B" border size="mini" class="radios">B</el-radio>
              <el-radio v-model="noise_level.IIIlevel" label="C" border size="mini" class="radios">C</el-radio>
              <el-radio v-model="noise_level.IIIlevel" label="D" border size="mini" class="radios">D</el-radio>
            </div>
            <div class="abcd">
              <el-radio v-model="noise_level.aVRlevel" label="A" border size="mini" class="radios">A</el-radio>
              <el-radio v-model="noise_level.aVRlevel" label="B" border size="mini" class="radios">B</el-radio>
              <el-radio v-model="noise_level.aVRlevel" label="C" border size="mini" class="radios">C</el-radio>
              <el-radio v-model="noise_level.aVRlevel" label="D" border size="mini" class="radios">D</el-radio>
            </div>
            <div class="abcd">
              <el-radio v-model="noise_level.aVLlevel" label="A" border size="mini" class="radios">A</el-radio>
              <el-radio v-model="noise_level.aVLlevel" label="B" border size="mini" class="radios">B</el-radio>
              <el-radio v-model="noise_level.aVLlevel" label="C" border size="mini" class="radios">C</el-radio>
              <el-radio v-model="noise_level.aVLlevel" label="D" border size="mini" class="radios">D</el-radio>
            </div>
            <div class="abcd">
              <el-radio v-model="noise_level.aVFlevel" label="A" border size="mini" class="radios">A</el-radio>
              <el-radio v-model="noise_level.aVFlevel" label="B" border size="mini" class="radios">B</el-radio>
              <el-radio v-model="noise_level.aVFlevel" label="C" border size="mini" class="radios">C</el-radio>
              <el-radio v-model="noise_level.aVFlevel" label="D" border size="mini" class="radios">D</el-radio>
            </div>
            <div class="abcd">
              <el-radio v-model="noise_level.V1level" label="A" border size="mini" class="radios">A</el-radio>
              <el-radio v-model="noise_level.V1level" label="B" border size="mini" class="radios">B</el-radio>
              <el-radio v-model="noise_level.V1level" label="C" border size="mini" class="radios">C</el-radio>
              <el-radio v-model="noise_level.V1level" label="D" border size="mini" class="radios">D</el-radio>
            </div>
            <div class="abcd">
              <el-radio v-model="noise_level.V2level" label="A" border size="mini" class="radios">A</el-radio>
              <el-radio v-model="noise_level.V2level" label="B" border size="mini" class="radios">B</el-radio>
              <el-radio v-model="noise_level.V2level" label="C" border size="mini" class="radios">C</el-radio>
              <el-radio v-model="noise_level.V2level" label="D" border size="mini" class="radios">D</el-radio>
            </div>
            <div class="abcd">
              <el-radio v-model="noise_level.V3level" label="A" border size="mini" class="radios">A</el-radio>
              <el-radio v-model="noise_level.V3level" label="B" border size="mini" class="radios">B</el-radio>
              <el-radio v-model="noise_level.V3level" label="C" border size="mini" class="radios">C</el-radio>
              <el-radio v-model="noise_level.V3level" label="D" border size="mini" class="radios">D</el-radio>
            </div>
            <div class="abcd">
              <el-radio v-model="noise_level.V4level" label="A" border size="mini" class="radios">A</el-radio>
              <el-radio v-model="noise_level.V4level" label="B" border size="mini" class="radios">B</el-radio>
              <el-radio v-model="noise_level.V4level" label="C" border size="mini" class="radios">C</el-radio>
              <el-radio v-model="noise_level.V4level" label="D" border size="mini" class="radios">D</el-radio>
            </div>
            <div class="abcd">
              <el-radio v-model="noise_level.V5level" label="A" border size="mini" class="radios">A</el-radio>
              <el-radio v-model="noise_level.V5level" label="B" border size="mini" class="radios">B</el-radio>
              <el-radio v-model="noise_level.V5level" label="C" border size="mini" class="radios">C</el-radio>
              <el-radio v-model="noise_level.V5level" label="D" border size="mini" class="radios">D</el-radio>
            </div>
            <div class="abcd">
              <el-radio v-model="noise_level.V6level" label="A" border size="mini" class="radios">A</el-radio>
              <el-radio v-model="noise_level.V6level" label="B" border size="mini" class="radios">B</el-radio>
              <el-radio v-model="noise_level.V6level" label="C" border size="mini" class="radios">C</el-radio>
              <el-radio v-model="noise_level.V6level" label="D" border size="mini" class="radios">D</el-radio>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {getReportByPId} from "@/api/report/report";
import {PatientInformation} from "@/api/log_user/log_user";
import $ from "jquery";
import * as echarts from "echarts";

export default {
  name: "OnlyLookLog",
  data() {
    return {
      a: "",//获取到导联的id
      b: "",//获取到导联的id
      noise: {
        list: [],//12导联噪声数据
        lists: [],//12导联质量评估数据
      },
      lead: {
        I: [],
        II: [],
        III: [],
        aVR: [],
        aVL: [],
        aVF: [],
        V1: [],
        V2: [],
        V3: [],
        V4: [],
        V5: [],
        V6: [],
      },
      noise_level: {
        Ilevel: "",
        IIlevel: "",
        IIIlevel: "",
        aVRlevel: "",
        aVLlevel: "",
        aVFlevel: "",
        V1level: "",
        V2level: "",
        V3level: "",
        V4level: "",
        V5level: "",
        V6level: "",
      },
      message: {
        devicesn: "type-2",
        user_id: "0",
        pid: "",
        logid: "",
        sex: "",
        age: "",
        time: "",
      },
      value: '',
    };
  },

  created() {
    this.message.user_id=this.$route.params.userId;
    this.message.logid=this.$route.params.logId;
    this.value=this.$route.params.logType;
    console.log("患者信息：",this.message)
  },
  mounted() {
    this.getMessage()
  },
  methods: {
    // goTarget(href) {
    //   window.open(href, "_blank");
    // },
    getMessage() {
      var x = []//x轴
      var seriesdata = []//标记线
      var chartI = echarts.init(document.getElementById('I'));
      var chartII = echarts.init(document.getElementById('II'));
      var chartIII = echarts.init(document.getElementById('III'));
      var chartV1 = echarts.init(document.getElementById('V1'));
      var chartV2 = echarts.init(document.getElementById('V2'));
      var chartV3 = echarts.init(document.getElementById('V3'));
      var chartV4 = echarts.init(document.getElementById('V4'));
      var chartV5 = echarts.init(document.getElementById('V5'));
      var chartV6 = echarts.init(document.getElementById('V6'));
      var chartaVR = echarts.init(document.getElementById('aVR'));
      var chartaVL = echarts.init(document.getElementById('aVL'));
      var chartaVF = echarts.init(document.getElementById('aVF'));
      var _th = this
      $.ajax({
        type: "POST",
        url: "https://screen.mindyard.cn:84/getId",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(
          {
            "log_id": this.message.logid,
            "user_id": this.message.user_id,
          }),
        beforeSend: function (request) {
          // 如果后台没有跨域处理，这个自定义
          request.setRequestHeader("user", "zzu");
          request.setRequestHeader("password", "zzu123");
        },
        success: function (jsonResult) {
          console.log("请求数据成功：", jsonResult);
          _th.message.pid = jsonResult.result.patientid;
          _th.message.age = Number(jsonResult.result.age).toFixed(0);
          _th.message.sex = jsonResult.result.sex;
          _th.message.time = jsonResult.result.clockTime;
          _th.lead.I = jsonResult.result.I;
          _th.lead.II = jsonResult.result.II;
          _th.lead.III = jsonResult.result.III;
          _th.lead.aVR = jsonResult.result.aVR;
          _th.lead.aVL = jsonResult.result.aVL;
          _th.lead.aVF = jsonResult.result.aVF;
          _th.lead.V1 = jsonResult.result.V1;
          _th.lead.V2 = jsonResult.result.V2;
          _th.lead.V3 = jsonResult.result.V3;
          _th.lead.V4 = jsonResult.result.V4;
          _th.lead.V5 = jsonResult.result.V5;
          _th.lead.V6 = jsonResult.result.V6;
          _th.light(jsonResult)
          _th.level(jsonResult)
          if (_th.message.devicesn != null) {
            (function () {
              for (var i = 0; i < 1000; i++) {
                x.push(i);
              }
              seriesdata = [{xAxis: 0},
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
                {yAxis: -1}, {yAxis: -0.5}, {yAxis: 0}, {yAxis: 0.5}, {yAxis: 1},]
              chartI.clear()
              chartI.setOption({
                title: {
                  text: 'I导联',
                  textStyle: {
                    fontSize: 12,
                    color: "#000000"
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
                  left: 20,
                  right: 5,
                  top: 2,
                  bottom: 2
                },

                xAxis: {
                  boundaryGap: true,
                  data: x,
                  axisTick: {
                    show: false
                  },
                  axisLabel: { //修改坐标系字体颜色
                    show: false,
                    interval: 4,
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
                  min: 1,
                  max: -1,
                  boundaryGap: true,
                  interval: 0.1,
                  axisLabel: {
                    show: false,
                  },
                  axisTick: {
                    show: false
                  },
                  axisLine: {
                    show: false
                  },
                  splitLine: {
                    lineStyle: {
                      color: "pink",
                      width: 1, //网格的宽度
                      type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
                    },
                    show: true
                  } /*网格线*/
                },
                series: {
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
                      show: false,
                      position: 'start', // 表现内容展示的位置
                      color: '#b33939'  // 展示内容颜色
                    },
                    data: seriesdata
                  },
                  itemStyle: {
                    normal: {
                      lineStyle: {
                        color: '#000000' /*折线的颜色*/
                      },
                      color: "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/
                    }
                  },
                  symbol: "none",
                  /*去掉小圆点*/
                  type: 'line',
                  data: _th.lead.I,
                  smooth: 0 //显示为平滑的曲线*/
                }
              })
              $(window).resize(function () {
                chartI.resize();
              });
              chartII.clear()
              chartII.setOption({
                animation: false,
                animationDuration: 9900,
                animationEasing: "linear",
                animationEasingUpdate: 'linear',
                animationDurationUpdate: 10000,
                animationDelayUpdate: 0,
                animationThreshold: 10000,
                backgroundColor: "#ffffff",
                title: {
                  text: 'II导联',
                  textStyle: {
                    fontSize: 12,
                    color: "#000000"
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
                  left: 20,
                  right: 5,
                  top: 2,
                  bottom: 2
                },

                xAxis: {
                  boundaryGap: true,
                  data: x,
                  axisTick: {
                    show: false
                  },
                  axisLabel: { //修改坐标系字体颜色
                    show: false,
                    interval: 4,
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
                  min: 1,
                  max: -1,
                  boundaryGap: true,
                  interval: 0.1,
                  axisLabel: {
                    show: false,
                  },
                  axisTick: {
                    show: false
                  },
                  axisLine: {
                    show: false
                  },
                  splitLine: {
                    lineStyle: {
                      color: "pink",
                      width: 1, //网格的宽度
                      type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
                    },
                    show: true
                  } /*网格线*/
                },
                series: {
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
                      show: false,
                      position: 'start', // 表现内容展示的位置
                      color: '#b33939'  // 展示内容颜色
                    },
                    data: seriesdata
                  },
                  /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
                  itemStyle: {
                    normal: {
                      lineStyle: {
                        color: '#000000' /*折线的颜色*/
                      },
                      color: "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/
                    }
                  },
                  symbol: "none",
                  /*去掉小圆点*/
                  name: 'II导联',
                  type: 'line',
                  data: _th.lead.II,
                  smooth: 0 //显示为平滑的曲线*/
                }
              })
              $(window).resize(function () {
                chartII.resize();
              });
              chartV1.clear()
              chartV1.setOption({
                animation: false,
                animationDuration: 9900,
                animationEasing: "linear",
                animationEasingUpdate: 'linear',
                animationDurationUpdate: 10000,
                animationDelayUpdate: 0,
                animationThreshold: 10000,
                backgroundColor: "#ffffff",
                title: {
                  text: 'V1导联',
                  textStyle: {
                    fontSize: 12,
                    color: "#000000"
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
                  left: 20,
                  right: 5,
                  top: 2,
                  bottom: 2
                },
                xAxis: {
                  boundaryGap: true,
                  data: x,
                  axisTick: {
                    show: false
                  },
                  axisLabel: { //修改坐标系字体颜色
                    show: false,
                    interval: 4,
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
                  min: 1,
                  max: -1,
                  boundaryGap: true,
                  interval: 0.1,
                  // splitNumber: 20,
                  axisLabel: {
                    show: false,
                  },
                  axisTick: {
                    show: false
                  },
                  axisLine: {
                    show: false
                  },
                  splitLine: {
                    lineStyle: {
                      color: "pink",
                      width: 1, //网格的宽度
                      type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
                    },
                    show: true
                  } /*网格线*/
                },
                series: {
                  markLine: {
                    animation: false,
                    symbol: "none",
                    silent: true,
                    lineStyle: {
                      type: "solid",
                      color: '#b33939',
                      width: 0.5,
                      // opacity: 0.5,
                    },
                    label: {

                      show: false,
                      position: 'start', // 表现内容展示的位置
                      color: '#b33939'  // 展示内容颜色
                    },
                    data: seriesdata
                  },
                  /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
                  itemStyle: {
                    normal: {
                      lineStyle: {
                        color: '#000000' /*折线的颜色*/
                      },
                      color: "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/
                    }
                  },
                  symbol: "none",
                  /*去掉小圆点*/
                  name: 'V1导联',
                  type: 'line',
                  data: _th.lead.V1,
                  smooth: 0 //显示为平滑的曲线*/
                }
              })
              $(window).resize(function () {
                chartV1.resize();
              });
              chartV2.clear()
              chartV2.setOption({
                animation: false,
                animationDuration: 9900,
                animationEasing: "linear",
                animationEasingUpdate: 'linear',
                animationDurationUpdate: 10000,
                animationDelayUpdate: 0,
                animationThreshold: 10000,
                backgroundColor: "#ffffff",
                title: {
                  text: 'V2导联',
                  textStyle: {
                    fontSize: 12,
                    color: "#000000",
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
                  left: 20,
                  right: 5,
                  top: 2,
                  bottom: 2
                },
                xAxis: {
                  boundaryGap: true,
                  data: x,
                  axisTick: {
                    show: false
                  },
                  axisLabel: { //修改坐标系字体颜色
                    show: false,
                    interval: 4,
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
                  min: 1,
                  max: -1,
                  boundaryGap: true,
                  interval: 0.1,
                  axisLabel: {
                    show: false,
                  },
                  axisTick: {
                    show: false
                  },
                  axisLine: {
                    show: false
                  },
                  splitLine: {
                    lineStyle: {
                      color: "pink",
                      width: 1, //网格的宽度
                      type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
                    },
                    show: true
                  } /*网格线*/
                },
                series: {
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
                      show: false,
                      position: 'start', // 表现内容展示的位置
                      color: '#b33939'  // 展示内容颜色
                    },
                    data: seriesdata
                  },
                  /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
                  itemStyle: {
                    normal: {
                      lineStyle: {
                        color: '#000000' /*折线的颜色*/
                      },
                      color: "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/
                    }
                  },
                  symbol: "none",
                  /*去掉小圆点*/
                  name: 'V2导联',
                  type: 'line',
                  data: _th.lead.V2,
                  smooth: 0 //显示为平滑的曲线*/
                }
              })
              $(window).resize(function () {
                chartV2.resize();
              });
              chartV3.clear()
              chartV3.setOption({
                animation: false,
                animationDuration: 9900,
                animationEasing: "linear",
                animationEasingUpdate: 'linear',
                animationDurationUpdate: 10000,
                animationDelayUpdate: 0,
                animationThreshold: 10000,
                backgroundColor: "#ffffff",
                title: {
                  text: 'V3导联',
                  textStyle: {
                    fontSize: 12,
                    color: "#000000"
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
                  left: 20,
                  right: 5,
                  top: 2,
                  bottom: 2
                },
                xAxis: {
                  boundaryGap: true,
                  data: x,
                  axisTick: {
                    show: false
                  },
                  axisLabel: { //修改坐标系字体颜色
                    show: false,
                    interval: 4,
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
                  min: 1,
                  max: -1,
                  boundaryGap: true,
                  interval: 0.1,
                  axisLabel: {
                    show: false,
                  },
                  axisTick: {
                    show: false
                  },
                  axisLine: {
                    show: false
                  },
                  splitLine: {
                    lineStyle: {
                      color: "pink",
                      width: 1, //网格的宽度
                      type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
                    },
                    show: true
                  } /*网格线*/
                },
                series: {
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
                      show: false,
                      position: 'start', // 表现内容展示的位置
                      color: '#b33939'  // 展示内容颜色
                    },
                    data: seriesdata
                  },
                  /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
                  itemStyle: {
                    normal: {
                      lineStyle: {
                        color: '#000000' /*折线的颜色*/
                      },
                      color: "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/
                    }
                  },
                  symbol: "none",
                  /*去掉小圆点*/
                  name: 'V3导联',
                  type: 'line',
                  data: _th.lead.V3,
                  smooth: 0 //显示为平滑的曲线*/
                }
              })
              $(window).resize(function () {
                chartV3.resize();
              });
              chartV4.clear()
              chartV4.setOption({
                animation: false,
                animationDuration: 9900,
                animationEasing: "linear",
                animationEasingUpdate: 'linear',
                animationDurationUpdate: 10000,
                animationDelayUpdate: 0,
                animationThreshold: 10000,
                backgroundColor: "#ffffff",
                title: {
                  text: 'V4导联',
                  textStyle: {
                    fontSize: 12,
                    color: "#000000"
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
                  left: 20,
                  right: 5,
                  top: 2,
                  bottom: 2
                },

                xAxis: {
                  boundaryGap: true,
                  data: x,
                  axisTick: {
                    show: false
                  },
                  axisLabel: { //修改坐标系字体颜色
                    show: false,
                    interval: 4,
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
                  min: 1,
                  max: -1,
                  boundaryGap: true,
                  interval: 0.1,
                  // splitNumber: 20,
                  axisLabel: {
                    show: false,
                  },
                  axisTick: {
                    show: false
                  },
                  axisLine: {
                    show: false
                  },
                  splitLine: {
                    lineStyle: {
                      color: "pink",
                      width: 1, //网格的宽度
                      type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
                    },
                    show: true
                  } /*网格线*/
                },
                series: {
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
                      show: false,
                      position: 'start', // 表现内容展示的位置
                      color: '#b33939'  // 展示内容颜色
                    },
                    data: seriesdata
                  },
                  /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
                  itemStyle: {
                    normal: {
                      lineStyle: {
                        color: '#000000' /*折线的颜色*/
                      },
                      color: "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/
                    }
                  },
                  symbol: "none",
                  /*去掉小圆点*/
                  name: 'V4导联',
                  type: 'line',
                  data: _th.lead.V4,
                  smooth: 0 //显示为平滑的曲线*/
                }
              })
              $(window).resize(function () {
                chartV4.resize();
              });
              chartV5.clear()
              chartV5.setOption({
                animation: false,
                animationDuration: 9900,
                animationEasing: "linear",
                animationEasingUpdate: 'linear',
                animationDurationUpdate: 10000,
                animationDelayUpdate: 0,
                animationThreshold: 10000,
                backgroundColor: "#ffffff",
                title: {
                  text: 'V5导联',
                  textStyle: {
                    fontSize: 12,
                    color: "#000000"
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
                  left: 20,
                  right: 5,
                  top: 2,
                  bottom: 2
                },
                xAxis: {
                  boundaryGap: true,
                  data: x,
                  axisTick: {
                    show: false
                  },
                  axisLabel: { //修改坐标系字体颜色
                    show: false,
                    interval: 4,
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
                  min: 1,
                  max: -1,
                  boundaryGap: true,
                  interval: 0.1,
                  axisLabel: {
                    show: false,
                  },
                  axisTick: {
                    show: false
                  },
                  axisLine: {
                    show: false
                  },
                  splitLine: {
                    lineStyle: {
                      color: "pink",
                      width: 1, //网格的宽度
                      type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
                    },
                    show: true
                  } /*网格线*/
                },
                series: {
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
                      show: false,
                      position: 'start', // 表现内容展示的位置
                      color: '#b33939'  // 展示内容颜色
                    },
                    data: seriesdata
                  },
                  /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
                  itemStyle: {
                    normal: {
                      lineStyle: {
                        color: '#000000' /*折线的颜色*/
                      },
                      color: "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/
                    }
                  },
                  symbol: "none",
                  /*去掉小圆点*/
                  name: 'V5导联',
                  type: 'line',
                  data: _th.lead.V5,
                  smooth: 0 //显示为平滑的曲线*/
                }
              })
              $(window).resize(function () {
                chartV5.resize();
              });
              chartV6.clear()
              chartV6.setOption({
                animation: false,
                animationDuration: 9900,
                animationEasing: "linear",
                animationEasingUpdate: 'linear',
                animationDurationUpdate: 10000,
                animationDelayUpdate: 0,
                animationThreshold: 10000,
                backgroundColor: "#ffffff",
                title: {
                  text: 'V6导联',
                  textStyle: {
                    fontSize: 12,
                    color: "#000000"

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
                  left: 20,
                  right: 5,
                  top: 2,
                  bottom: 2
                },
                xAxis: {
                  boundaryGap: true,
                  data: x,
                  axisTick: {
                    show: false
                  },
                  axisLabel: { //修改坐标系字体颜色
                    show: true,
                    interval: 4,
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
                  min: 1,
                  max: -1,
                  boundaryGap: true,
                  interval: 0.1,
                  axisLabel: {
                    show: false,
                  },
                  axisTick: {
                    show: false
                  },
                  axisLine: {
                    show: false
                  },
                  splitLine: {
                    lineStyle: {
                      color: "pink",
                      width: 1, //网格的宽度
                      type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
                    },
                    show: true
                  } /*网格线*/
                },
                series: [{
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
                      show: false,
                      position: 'start', // 表现内容展示的位置
                      color: '#b33939'  // 展示内容颜色
                    },
                    data: seriesdata
                  },
                  /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
                  itemStyle: {
                    normal: {
                      lineStyle: {
                        color: '#000000' /*折线的颜色*/
                      },
                      color: "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/
                    }
                  },
                  symbol: "none",
                  /*去掉小圆点*/
                  name: '当前电位',
                  type: 'line',
                  data: _th.lead.V6,
                  smooth: 0 //显示为平滑的曲线*/
                },]
              })
              $(window).resize(function () {
                chartV6.resize();
              });
              chartIII.clear()
              chartIII.setOption({
                animation: false,
                animationDuration: 9900,
                animationEasing: "linear",
                animationEasingUpdate: 'linear',
                animationDurationUpdate: 10000,
                animationDelayUpdate: 0,
                animationThreshold: 10000,
                backgroundColor: "#ffffff",
                title: {
                  text: 'III导联',
                  textStyle: {
                    fontSize: 12,
                    color: "#000000"
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
                  left: 20,
                  right: 5,
                  top: 2,
                  bottom: 2
                },

                xAxis: {
                  boundaryGap: true,
                  data: x,
                  axisTick: {
                    show: false
                  },
                  axisLabel: { //修改坐标系字体颜色
                    show: false,
                    interval: 4,
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
                  min: 1,
                  max: -1,
                  boundaryGap: true,
                  interval: 0.1,
                  // splitNumber: 20,
                  axisLabel: {
                    show: false,
                  },
                  axisTick: {
                    show: false
                  },
                  axisLine: {
                    show: false
                  },
                  splitLine: {
                    lineStyle: {
                      color: "pink",
                      width: 1, //网格的宽度
                      type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
                    },
                    show: true
                  } /*网格线*/
                },
                series: {
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
                      show: false,
                      position: 'start', // 表现内容展示的位置
                      color: '#b33939'  // 展示内容颜色
                    },
                    data: seriesdata
                  },
                  /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
                  itemStyle: {
                    normal: {
                      lineStyle: {
                        color: '#000000' /*折线的颜色*/
                      },
                      color: "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/
                    }
                  },
                  symbol: "none",
                  /*去掉小圆点*/
                  name: 'III导联',
                  type: 'line',
                  data: _th.lead.III,
                  smooth: 0 //显示为平滑的曲线*/
                }
              });
              $(window).resize(function () {
                chartIII.resize();
              });
              chartaVL.clear();
              chartaVL.setOption({
                animation: false,
                animationDuration: 9900,
                animationEasing: "linear",
                animationEasingUpdate: 'linear',
                animationDurationUpdate: 10000,
                animationDelayUpdate: 0,
                animationThreshold: 10000,
                backgroundColor: "#ffffff",
                title: {
                  text: 'aVL导联',
                  textStyle: {
                    fontSize: 12,
                    color: "#000000"
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
                  left: 20,
                  right: 5,
                  top: 2,
                  bottom: 2
                },
                xAxis: {
                  boundaryGap: true,
                  data: x,
                  axisTick: {
                    show: false
                  },
                  axisLabel: { //修改坐标系字体颜色
                    show: false,
                    interval: 4,
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
                  min: 1,
                  max: -1,
                  boundaryGap: true,
                  interval: 0.1,
                  axisLabel: {
                    show: false,
                  },
                  axisTick: {
                    show: false
                  },
                  axisLine: {
                    show: false
                  },
                  splitLine: {
                    lineStyle: {
                      color: "pink",
                      width: 1, //网格的宽度
                      type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
                    },
                    show: true
                  } /*网格线*/
                },
                series: {
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
                      show: false,
                      position: 'start', // 表现内容展示的位置
                      color: '#b33939'  // 展示内容颜色
                    },
                    data: seriesdata
                  },
                  /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
                  itemStyle: {
                    normal: {
                      lineStyle: {
                        color: '#000000' /*折线的颜色*/
                      },
                      color: "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/
                    }
                  },
                  symbol: "none",
                  /*去掉小圆点*/
                  name: 'aVL导联',
                  type: 'line',
                  data: _th.lead.aVL,
                  smooth: 0 //显示为平滑的曲线*/
                }
              });
              $(window).resize(function () {
                chartaVL.resize();
              });
              chartaVF.clear()
              chartaVF.setOption({
                animation: false,
                animationDuration: 9900,
                animationEasing: "linear",
                animationEasingUpdate: 'linear',
                animationDurationUpdate: 10000,
                animationDelayUpdate: 0,
                animationThreshold: 10000,
                backgroundColor: "#ffffff",
                title: {
                  text: 'aVF导联',
                  textStyle: {
                    fontSize: 12,
                    color: "#000000"
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
                  left: 20,
                  right: 5,
                  top: 2,
                  bottom: 2
                },
                xAxis: {
                  boundaryGap: true,
                  data: x,
                  axisTick: {
                    show: false
                  },
                  axisLabel: { //修改坐标系字体颜色
                    show: false,
                    interval: 4,
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
                  min: 1,
                  max: -1,
                  boundaryGap: true,
                  interval: 0.1,
                  axisLabel: {
                    show: false,
                  },
                  axisTick: {
                    show: false
                  },
                  axisLine: {
                    show: false
                  },
                  splitLine: {
                    lineStyle: {
                      color: "pink",
                      width: 1, //网格的宽度
                      type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
                    },
                    show: true
                  } /*网格线*/
                },
                series: {
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
                      show: false,
                      position: 'start', // 表现内容展示的位置
                      color: '#b33939'  // 展示内容颜色
                    },
                    data: seriesdata
                  },
                  /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
                  itemStyle: {
                    normal: {
                      lineStyle: {
                        color: '#000000' /*折线的颜色*/
                      },
                      color: "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/
                    }
                  },
                  symbol: "none",
                  /*去掉小圆点*/
                  name: 'aVF导联',
                  type: 'line',
                  data: _th.lead.aVF,
                  smooth: 0 //显示为平滑的曲线*/
                }
              });
              $(window).resize(function () {
                chartaVF.resize();
              });
              chartaVR.clear();
              chartaVR.setOption({
                animation: false,
                animationDuration: 9900,
                animationEasing: "linear",
                animationEasingUpdate: 'linear',
                animationDurationUpdate: 10000,
                animationDelayUpdate: 0,
                animationThreshold: 10000,
                backgroundColor: "#ffffff",
                title: {
                  text: 'aVR导联',
                  textStyle: {
                    fontSize: 12,
                    color: "#000000"
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
                  left: 20,
                  right: 5,
                  top: 2,
                  bottom: 2
                },
                xAxis: {
                  boundaryGap: true,
                  data: x,
                  axisTick: {
                    show: false
                  },
                  axisLabel: { //修改坐标系字体颜色
                    show: false,
                    interval: 4,
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
                  min: 1,
                  max: -1,
                  boundaryGap: true,
                  interval: 0.1,
                  // splitNumber: 20,
                  axisLabel: {
                    show: false,
                  },
                  axisTick: {
                    show: false
                  },
                  axisLine: {
                    show: false
                  },
                  splitLine: {
                    lineStyle: {
                      color: "pink",
                      width: 1, //网格的宽度
                      type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
                    },
                    show: true
                  } /*网格线*/
                },
                series: {
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
                      show: false,
                      position: 'start', // 表现内容展示的位置
                      color: '#b33939'  // 展示内容颜色
                    },
                    data: seriesdata
                  },
                  /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
                  itemStyle: {
                    normal: {
                      lineStyle: {
                        color: '#000000' /*折线的颜色*/
                      },
                      color: "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/
                    }
                  },
                  symbol: "none",
                  /*去掉小圆点*/
                  name: 'aVR导联',
                  type: 'line',
                  data: _th.lead.aVR,
                  smooth: 0 //显示为平滑的曲线*/
                }
              });
              $(window).resize(function () {
                chartaVR.resize();
              });
            })();
          } else {
            _th.$message.error("设备未连接");
          }
        },
        error: function (data) {
          console.log("请求数据失败：",data)
          _th.$message.error("数据获取失败");
        }
      });
    },
    //红绿颜色的判断
    light(data) {
      var lightlevel = {
        'V1': 'V1light',
        'V2': 'V2light',
        'V3': 'V3light',
        'V4': 'V4light',
        'V5': 'V5light',
        'V6': 'V6light',
        'aVL': 'aVLlight',
        'aVF': 'aVFlight',
        'aVR': 'aVRlight',
        'I': 'Ilight',
        'II': 'IIlight',
        'III': 'IIIlight',
      }
      this.noise.list=data.result.noise
      for(var k in lightlevel){
        if(data.result[k].length===0)this.noise.list[lightlevel[k]]=null
      }
      console.log("红绿颜色的判断值：",this.noise.list)
      for (var key in this.noise.list) {
        if (this.noise.list[key] === 1) {
          let temp = document.getElementById(key)
          temp.style.backgroundColor = "red"
        } else if(this.noise.list[key] === 0) {
          let temp = document.getElementById(key)
          temp.style.backgroundColor = "greenyellow"
        }else {
          let temp = document.getElementById(key)
          temp.style.backgroundColor = "transparent"
        }
      }
    },
    //ABCD等级的判断
    level(data) {
      var level = {
        'V1': 'V1level',
        'V2': 'V2level',
        'V3': 'V3level',
        'V4': 'V4level',
        'V5': 'V5level',
        'V6': 'V6level',
        'aVL': 'aVLlevel',
        'aVF': 'aVFlevel',
        'aVR': 'aVRlevel',
        'I': 'Ilevel',
        'II': 'IIlevel',
        'III': 'IIIlevel',
      }
      this.noise_level = data.result.noise_level
      for(var k in level){
        if(data.result[k].length===0)this.noise_level[level[k]]=null
      }
      console.log("传的ABCD的等级：", this.noise_level)
    },
    //展开框
    // showchart(t, data, timex) {
    //   a = t.id//导联的id
    //   $('#jump').css('display', 'block');
    //   chartjump = echarts.init(document.getElementById('chartjump'));
    //   detailoption = {
    //     animation: true,
    //     backgroundColor: "#ffffff",
    //     title: {
    //       text: a,
    //       textStyle: {
    //         fontSize: 13,
    //         color: "#000000"
    //       },
    //     },
    //     tooltip: {
    //       trigger: 'axis',
    //       axisPointer: {
    //         type: 'cross'
    //       }
    //     },
    //     toolbox: {
    //       left: 'center',
    //       right: 50,
    //       top: 0,
    //       itemSize: 30,
    //     },
    //     dataZoom: [{
    //       type: 'inside', // 放大和缩小
    //       orient: 'vertical',
    //       filterMode: "none",//或者”empty“
    //     }, {
    //       type: 'inside',
    //       filterMode: "none",//或者”empty“
    //     },
    //       {
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
    //       }],
    //     grid: {
    //       left: 50,
    //       right: 15,
    //       top: 60,
    //       bottom: 30
    //     },
    //     legend: {
    //       show: false,
    //       data: ['当前电位'],
    //       textStyle: {color: "#000000"} /*图例(legend)说明文字的颜色*/,
    //       left: "right",
    //     },
    //     brush: {
    //       toolbox: ['lineX', 'keep', 'clear'],
    //       xAxisIndex: 0,//表示这个 index 所对应的坐标系
    //       throttleType: 'debounce',//开启选中延迟后调用回调延迟
    //       throttleDelay: 600,//选中延迟后调用回调延迟时间
    //       brushStyle: {
    //         borderWidth: 1,
    //         color: 'rgba(255,36,36,0.2)',
    //         borderColor: '#ff2424'
    //       }
    //
    //     },
    //     xAxis: {
    //       boundaryGap: true,
    //       data: timex,
    //       axisTick: {
    //         show: false
    //       },
    //       axisLabel: { //修改坐标系字体颜色
    //         interval: 4,
    //         show: false,
    //         textStyle: {
    //           color: "#000000"
    //         }
    //       },
    //       splitLine: {
    //         show: true,
    //         lineStyle: {
    //           color: 'pink',
    //           width: 1, //网格的宽度
    //           type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
    //         },
    //
    //       } /*网格线*/
    //     },
    //     yAxis: {
    //       min: 1,
    //       max: -1,
    //       boundaryGap: true,
    //       interval: 0.1,
    //       axisLabel: {
    //         show: false,
    //       },
    //       axisTick: {
    //         show: false
    //       },
    //       axisLine: {
    //         show: false
    //       },
    //
    //       splitLine: {
    //         show: true,
    //         lineStyle: {
    //           color: "pink",
    //           width: 1, //网格的宽度
    //           type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
    //         },
    //       } /*网格线*/
    //     },
    //     series: {
    //       markLine: {
    //         animation: false,
    //         symbol: "none",
    //         silent: true,
    //         lineStyle: {
    //           type: "solid",
    //           color: '#b33939',
    //           width: 0.5,
    //
    //         },
    //         label: {
    //           show: false,
    //           position: 'start', // 表现内容展示的位置
    //           color: '#b33939'  // 展示内容颜色
    //         },
    //         data: seriesdata,
    //       },
    //       itemStyle: {
    //         normal: {
    //           lineStyle: {
    //             color: '#000000',/*折线的颜色*/
    //           },
    //           color: "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/
    //         }
    //       },
    //       markArea: {
    //         z: 0,
    //         //  显示标签，且标签位于区域内部
    //         label: {
    //           show: false,
    //           position: 'inside'
    //         },
    //         itemStyle: {
    //           color: 'rgb(191,250,141)',
    //           borderColor: "rgb(6,115,43)",
    //           borderWidth: 2,
    //           borderType: "dotted",
    //         },
    //         data: xdata
    //       },
    //       symbol: "none",
    //       /*去掉小圆点*/
    //       name: '当前电位',
    //       type: 'line',
    //       data: data,
    //       smooth: 0 //显示为平滑的曲线*/
    //     },
    //   };
    //   chartjump.setOption(detailoption, true);
    // }
  },
};
</script>

<style scoped lang="scss">

.main {
  width: 100%;
  //background: #03044A;
}

.top {
  height: 15vw;
  width: 100%;
  //border: 4px solid #62be38;
  display: flex;
  flex-direction: row;
  flex-wrap: nowrap;
  justify-content: flex-start;
}

.topLeft {
  width: 50%;
  height: 100%;
  border-right: 1px solid rgba(0, 0, 0,);

}

.topMiddle {
  width: 50%;
  height: 100%;
  border-right: 1px solid rgba(0, 0, 0,);
}

.topRight {
  width: 25%;
  height: 100%;
}

.message {
  width: 100%;
  height: 20%;
  font-size: 1vw;
  font-weight: 700;
  color: #000000;
  text-align: center;
  padding: 0.8vw 0;
}

.messageDetail {
  //border-right: 1px solid #ffffff;
  width: 100%;
  height: 80%;
  font-size: 1vw;
  color: #000000;
  //text-align: center;
  display: flex;
  flex-direction: column;
  flex-wrap: nowrap;
  justify-content: space-around;
}

.messageDetail span {
  padding-left: 2vw;
}

.warning {
  width: 100%;
  height: 20%;
  font-size: 1vw;
  font-weight: 700;
  color: #000000;
  text-align: center;
  padding: 0.8vw 0;
}

.warningDetail {
  //border: 1px solid #ff5a00;
  width: 100%;
  height: 80%;
  font-size: 1vw;
  color: #000000;

}

.warningValue {
  width: 20vw;
  height: 2vw;
  border: 1px solid #000000;
  margin: 2vw auto;
  font-size: 1vw;
  padding: 0.2vw;
  //text-align: center;
}

.bottom {
  height: 80vw;
  width: 100%;
  border-top: 1px solid #000000;
  display: flex;
  flex-direction: row;
  flex-wrap: nowrap;
  justify-content: flex-start;
  margin-bottom: 2vw;
}

.bottomLeft {
  width: 70%;
  height: 100%;
  //border-right: 1px solid #FFFFFF;
}

.xinDian {
  width: 100%;
  height: 5%;
  font-size: 1vw;
  font-weight: 700;
  color: #000000;
  text-align: center;
  padding: 0.8vw 0;
  border-right: 1px solid #000000;
}

.echarts {
  width: 100%;
  height: 95%;
  border-right: 1px solid #000000;
}

.bottomLeft .echarts .container {
  width: 100%;
  height: 8.4%;
  border: 1px solid rgba(25, 186, 139, 0.17);
  position: relative;
}

.bottomLeft .echarts .container .chart {
  height: 100%;
  width: 98%;
  //background-color: #ffffff;
}

.btn {
  color: #4cc9f0;
  border: 1px solid;
  background-color: transparent;
  text-transform: uppercase;
  font-weight: 500;
  position: absolute;
  top: 0;
  right: 2%;
}

.btn:hover {
  cursor: pointer;
  color: #000000;
  border: 0;
  background-color: #4cc9f0;
}

.light {
  display: inline-block;
  width: 1.8%;
  height: 95%;
  position: absolute;
  top: 0;
  right: 0;
  //background-color: greenyellow;
  border: 1px solid #157a0c;
}

.light:hover {
  cursor: pointer;
  border: 1px solid #000000;
}

.bottomRight {
  width: 30%;
  height: 100%;
  //border: 1px solid #FFFFFF;
}

.quality {
  width: 100%;
  height: 95%;
  //background-color: #fff;
  display: flex;
  flex-direction: row;
  flex-wrap: nowrap;
  justify-content: flex-start;
}

.lights {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: stretch;
  flex-grow: 1;
  height: 100%;
  width: 80%;
}

.abcd {
  display: flex;
  justify-content: space-around;
  flex-grow: 1;
  width: 100%;
  padding: 1.5vw;
  margin-top: 0.3vw;
  border-top: 0.5px solid #000000;
  border-bottom: 0.5px solid #000000;
}

.radios{
  border: 0.5px solid rgb(0, 0, 0);
  box-sizing: border-box;
  color: rgb(96, 98, 102);
  cursor: pointer;
  display: block;
  font-family: Helvetica Neue, Helvetica, PingFang SC, Hiragino Sans GB, Microsoft YaHei, Arial, sans-serif;
  font-size: 1vw;
  font-weight: 500;
  height: 2vw;
  width: 3vw;
  line-height: 1vw;
  margin: 0.5vw 0;
  padding: 0.3vw 0.3vw 0 0.2vw;
  position: relative;
  text-rendering: optimizelegibility;
  user-select: none;
  white-space: nowrap
}
</style>





























