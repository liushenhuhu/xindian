<template>
  <div class="wrap">
    <div id="pdfDom" style="padding: 10px;">
      <div id="app">
        <div class="page">
          <div class="header">
            <div class="header-left">
              <div class="header-1">
                <div class="header-1-1"><strong>姓名:</strong>{{ data.name }}</div>
                <div class="header-1-1"><strong>性别:</strong>{{ data.gender }}</div>
                <div class="header-1-1"><strong>年龄:</strong>{{ data.age }}岁</div>
                <div class="header-1-1"><strong>送检科室:</strong> -</div>
                <div class="header-1-1"><strong>申请单号:</strong> -</div>
              </div>
              <div class="header-2">
                <div class="header-2-1"><strong>门诊号:</strong> -</div>
                <div class="header-2-1"><strong>住院号:</strong> -</div>
                <div class="header-2-1"><strong>病人编号:</strong> -</div>
                <div class="header-2-1"><strong>HR:</strong>{{ data.hr }}bpm</div>
                <div class="header-2-1"><strong>PR:</strong>{{ data.pr }}ms</div>
              </div>
              <div class="header-3">
                <div class="header-3-1"><strong>QRS:</strong>{{ data.qrs }}ms</div>
                <div class="header-3-1"><strong>QT/QTc:</strong>{{ data.qt }}ms/{{ data.qtc }}ms</div>
                <div class="header-3-1"><strong>P/QRS/T:</strong>{{ data.p }}/{{ data.qrs_deg }}/{{ data.t }}deg</div>
                <div class="header-3-1"><strong>PV5/SV1:</strong>{{ data.pv5 }}/{{ data.sv1 }}mV</div>
                <div class="header-3-1"><strong>RV5/SV1:</strong>{{ data.rv5_sv1 }}mV</div>
              </div>
            </div>
            <div class="header-right">
              <div class="header-4">
                <div class="header-4-1"><strong>自动分析结果，仅供参考</strong></div>
                <div class="header-4-1">
                  <div style="width: 100%;height: 6vw;border: 1px solid #c4c4c4;padding: 0.5vw;">{{ data.result }}</div>
                </div>
                <div class="header-4-1"><strong>医师诊断</strong></div>
                <div class="header-4-1-1">
                  <el-input
                    type="textarea"
                    v-model="data.resultByDoctor"
                    placeholder="请在这里输入医生诊断结果"
                    data-value="1111"
                    :rows="4"
                    class="font">
                  </el-input>
                </div>
              </div>
            </div>
          </div>
          <div style="font-size: 1vw">10mm/mV 25mm/s</div>
          <div class="body">
            <div class="body-1">
              <div>
                <div id="I" class="line" @dblclick="clicktrueI"></div>
              </div>
              <div>
                <div id="II" class="line" @dblclick="clicktrueII"></div>
              </div>
              <div>
                <div id="III" class="line" @dblclick="clicktrueIII"></div>
              </div>
              <div>
                <div id="aVR" class="line" @dblclick="clicktrueaVR"></div>
              </div>
              <div>
                <div id="aVL" class="line" @dblclick="clicktrueaVL"></div>
              </div>
              <div>
                <div id="aVF" class="line" @dblclick="clicktrueaVF"></div>
              </div>
            </div>
          </div>
          <div class="bottom">
            <div class="bottom-left">
              <strong>医师:</strong>
              <el-input v-model="data.doctorName" clearable
                        :style="{width: '15%',border: '1px solid black',}"></el-input>
              <span v-html="'\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0'"></span>
              <strong>日期:</strong>
              <el-input v-if="data.diagnosisData!=null" v-model="data.diagnosisData" clearable
                        :style="{width: '15%',border: '1px solid black',}"></el-input>
              <el-input v-else v-model="data.dataTime" clearable
                        :style="{width: '15%',border: '1px solid black',}"></el-input>
            </div>
            <el-button type="success" plain class="anNiu" @click="btnUpload">保存数据</el-button>
          </div>
        </div>
      </div>
      <div class="lineI" v-show="openI">
        <div id="I1" class="lineshow"></div>
        <button @click="clickclose" class="noName">关闭</button>
      </div>
      <div class="lineI" v-show="openII">
        <div id="II1" class="lineshow"></div>
        <button @click="clickclose" class="noName">关闭</button>
      </div>
      <div class="lineI" v-show="openIII">
        <div id="III1" class="lineshow"></div>
        <button @click="clickclose" class="noName">关闭</button>
      </div>
      <div class="lineI" v-show="openaVR">
        <div id="aVR1" class="lineshow"></div>
        <button @click="clickclose" class="noName">关闭</button>
      </div>
      <div class="lineI" v-show="openaVL">
        <div id="aVL1" class="lineshow"></div>
        <button @click="clickclose" class="noName">关闭</button>
      </div>
      <div class="lineI" v-show="openaVF">
        <div id="aVF1" class="lineshow"></div>
        <button @click="clickclose" class="noName">关闭</button>
      </div>
    </div>
  </div>
</template>

<script>
import echarts from 'echarts'
import $ from 'jquery';
import {addReport, getReportByPId, updateReport} from "@/api/report/report";

export default {
  name: "static_single",
  inject: ['reload'],
  data() {
    return {
      pId: null,
      reloads: false,
      data: {
        name: "",
        gender: "",
        age: "",
        result: "",
        resultByDoctor: "",
        dataTime: "",
        doctorName: "",
        diagnosisData: null,
        datas: [],
      },
      openI: false,
      openII: false,
      openIII: false,
      openaVR: false,
      openaVL: false,
      openaVF: false,
    };
  },
  created() {
    var pId = this.$route.query.pId;
    console.log("pId:" , pId)
    if (pId) {
      this.pId = pId;
      getReportByPId(this.pId).then(response => {
        console.log("请求成功：",response.data)
        this.data.resultByDoctor = response.data.diagnosisConclusion
        this.data.doctorName = response.data.diagnosisDoctor
        this.data.diagnosisData = response.data.reportTime
      });
      var show = sessionStorage.getItem(pId + "show");
      if (!show) {
        this.get();
      }
    }
  },
  mounted() {
    // this.get();
  },
  methods: {
    clicktrueI() {
      this.openI = !this.openI;
    },
    clicktrueII() {
      this.openII = !this.openII;
    },
    clicktrueIII() {
      this.openIII = !this.openIII;
    },
    clicktrueaVR() {
      this.openaVR = !this.openaVR;
    },
    clicktrueaVL() {
      this.openaVL = !this.openaVL;
    },
    clicktrueaVF() {
      this.openaVF = !this.openaVF;
    },
    clickclose() {
      this.openI = false;
      this.openII = false;
      this.openIII = false;
      this.openaVR = false;
      this.openaVL = false;
      this.openaVF = false;
    },
    get() {
      const loading = this.$loading({
        lock: true,//lock的修改符--默认是false
        text: '请勿刷新页面，正在获取数据，请耐心等待1-3分钟...',//显示在加载图标下方的加载文案
        spinner: 'el-icon-loading',//自定义加载图标类名
        background: 'rgba(0, 0, 0, 0.7)',//遮罩层颜色
        target: document.querySelector('#table')//loadin覆盖的dom元素节点
      });
      var _th = this
      console.log("pId:",this.pId)
      this.data.dataTime = this.$options.methods.getData();
      $.ajax({
        type: "post",
        url: "https://screen.mindyard.cn:84/get_jecg_single",
        // asynsc: false,
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({
          pid: this.pId
        }),
        beforeSend: function (request) {
          // 如果后台没有跨域处理，这个自定义
          request.setRequestHeader("user", "zzu");
          request.setRequestHeader("password", "zzu123");
        },
        success: function (data) {
          console.log("请求成功：",data)
          loading.close()
          // window.location.reload("#pdfDom");
          _th.data.age = data.result.age
          _th.data.gender = data.result.gender
          _th.data.name = data.result.patientName
          _th.data.result = data.result.intelligent_diagnosis
          _th.data.hr = data.result.ecg_analysis_data["平均心率"]
          // this.data.pr = data.result.ecg_analysis_data[""]
          // this.data.qrs = data.result.ecg_analysis_data[""]
          // this.data.qt = data.result.ecg_analysis_data[""]
          // this.data.p = data.result.ecg_analysis_data[""]
          // this.data.pv5 = data.result.ecg_analysis_data[""]
          // this.data.rv5 = data.result.ecg_analysis_data[""]
          _th.data.datas = data.result.II
          console.log("获取到的导联数据", _th.data.datas)
          console.log("获取到的导联数据长度", _th.data.datas.length)
          var x = [];
          var nArr = _th.getNewArray(_th.data.datas, 2000);
          console.log("数据以2000一条分好组", nArr)
          for (var i = 0; i < 2000; i++) {
            x.push(i);
          }
          var chartI = echarts.init(document.getElementById("I"));
          chartI.clear()
          chartI.setOption({
            title: {
              text: "",
              top: 0,
              left: 0,
            },
            grid: {
              left: '1',
              right: '1',
              top: '1',
              bottom: '1',
              containLabel: false
            },
            xAxis: {
              type: 'category',
              data: x,
              // position:"bottom",
              axisLabel: {
                show: false,//x 轴刻度数字
                interval: 4,//中间隔4个数字
              },
              axisTick: {
                show: false//x轴刻度线
              },
              axisLine: {
                show: false//x轴线
              },
              splitLine: {
                show: true, //让网格显示
                lineStyle: {//网格样式
                  color: "#f8c1bf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
                  opacity: 0.6,//透明度
                }
              }
            },
            yAxis: {
              type: 'value',
              axisLabel: {
                show: false,
              },
              axisTick: {
                show: false
              },
              axisLine: {
                show: false
              },
              //  splitNumber: 3, // 横线数
              interval: 0.1, // 刻度间隔
              splitLine: {
                show: true, //让网格显示
                lineStyle: {//网格样式
                  color: "#f8c1bf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
                  opacity: 0.6,//透明度

                }
              },
              max: 1,
              min: -1
            },
            series: [
              {
                type: 'line',
                smooth: true,
                showSymbol: false,
                data: nArr[0],
                lineStyle: {
                  normal: {
                    color: "#000000",
                    width: 1,
                    // opacity: 1,
                  }
                },
                markLine: {
                  symbol: "none",
                  silent: true,
                  lineStyle: {
                    type: "solid",
                    color: '#b33939',
                    width: 1,
                    opacity: 0.5,
                  },
                  label: {
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
                    {xAxis: 2000},

                    {yAxis: -1},
                    {yAxis: -0.5},
                    {yAxis: 0},
                    {yAxis: 0.5},
                    {yAxis: 1},
                  ]
                }
              }
            ],
          });
          $(window).resize(function () {
            chartI.resize();
          });
          var chartII = echarts.init(document.getElementById("II"));
          chartII.clear()
          chartII.setOption({
            title: {
              text: "",
              top: 5,
              left: 5,
            },
            grid: {
              left: '1',
              right: '1',
              top: '1',
              bottom: '1',
              containLabel: false
            },
            xAxis: {
              type: 'category',
              data: x,
              axisLabel: {
                show: false,
                interval: 4,
              },
              axisTick: {
                show: false
              },
              axisLine: {
                show: false
              },
              splitLine: {
                show: true, //让网格显示
                lineStyle: {//网格样式
                  color: "#f8c1bf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
                  opacity: 0.6,//透明度
                }
              }
            },
            yAxis: {
              type: 'value',
              axisLabel: {
                show: false,
              },
              axisTick: {
                show: false
              },
              axisLine: {
                show: false
              },
              //  splitNumber: 3, // 横线数
              interval: 0.1, // 刻度间隔
              splitLine: {
                show: true, //让网格显示
                lineStyle: {//网格样式
                  color: "#f8c1bf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
                  opacity: 0.6,//透明度
                }
              },
              max: 1,
              min: -1
            },
            series: [
              {
                type: 'line',
                smooth: true,
                showSymbol: false,
                data: nArr[1],
                lineStyle: {
                  normal: {
                    color: "#000000",
                    width: 1
                  }
                },
                markLine: {
                  symbol: "none",
                  silent: true,
                  lineStyle: {
                    type: "solid",
                    color: '#b33939',
                    width: 1,
                    opacity: 0.5,
                  },
                  label: {
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
                    {xAxis: 2000},

                    {yAxis: -1},
                    {yAxis: -0.5},
                    {yAxis: 0},
                    {yAxis: 0.5},
                    {yAxis: 1},
                  ]
                }

              }
            ],

          });
          $(window).resize(function () {
            chartII.resize();
          });
          var chartIII = echarts.init(document.getElementById("III"));
          chartIII.clear()
          chartIII.setOption({
            title: {
              text: "",
              top: 5,
              left: 5,
            },
            grid: {
              left: '1',
              right: '1',
              top: '1',
              bottom: '1',
              containLabel: false
            },
            xAxis: {
              type: 'category',
              data: x,
              axisLabel: {
                show: false,
                interval: 4,
              },
              axisTick: {
                show: false
              },
              axisLine: {
                show: false
              },
              splitLine: {
                show: true, //让网格显示
                lineStyle: {//网格样式
                  color: "#f8c1bf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
                  opacity: 0.6,//透明度
                }
              }
            },
            yAxis: {
              type: 'value',
              axisLabel: {
                show: false,
              },
              axisTick: {
                show: false
              },
              axisLine: {
                show: false
              },
              //  splitNumber: 3, // 横线数
              interval: 0.1, // 刻度间隔
              splitLine: {
                show: true, //让网格显示
                lineStyle: {//网格样式
                  color: "#f8c1bf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
                  opacity: 0.6,//透明度
                }
              },
              max: 1,
              min: -1
            },
            series: [
              {
                type: 'line',
                smooth: true,
                showSymbol: false,
                data: nArr[2],
                lineStyle: {
                  normal: {
                    color: "#000000",
                    width: 1
                  }
                },
                markLine: {
                  symbol: "none",
                  silent: true,
                  lineStyle: {
                    type: "solid",
                    color: '#b33939',
                    width: 1,
                    opacity: 0.5,
                  },
                  label: {
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
                    {xAxis: 2000},

                    {yAxis: -1},
                    {yAxis: -0.5},
                    {yAxis: 0},
                    {yAxis: 0.5},
                    {yAxis: 1},
                  ]
                }
              }
            ],
          });
          $(window).resize(function () {
            chartIII.resize();
          });
          var chartaVR = echarts.init(document.getElementById("aVR"));
          chartaVR.clear()
          chartaVR.setOption({
            title: {
              text: "",
              top: 5,
              left: 5,
            },
            grid: {
              left: '1',
              right: '1',
              top: '1',
              bottom: '1',
              containLabel: false
            },
            xAxis: {
              type: 'category',
              data: x,
              axisLabel: {
                show: false,
                interval: 4,
              },
              axisTick: {
                show: false
              },
              axisLine: {
                show: false
              },
              splitLine: {
                show: true, //让网格显示
                lineStyle: {//网格样式
                  color: "#f8c1bf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
                  opacity: 0.6,//透明度
                }
              }
            },
            yAxis: {
              type: 'value',
              axisLabel: {
                show: false,
              },
              axisTick: {
                show: false
              },
              axisLine: {
                show: false
              },
              //  splitNumber: 3, // 横线数
              interval: 0.1, // 刻度间隔
              splitLine: {
                show: true, //让网格显示
                lineStyle: {//网格样式
                  color: "#f8c1bf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
                  opacity: 0.6,//透明度
                }
              },
              max: 1,
              min: -1
            },
            series: [
              {
                type: 'line',
                smooth: true,
                showSymbol: false,
                data: nArr[3],
                lineStyle: {
                  normal: {
                    color: "#000000",
                    width: 1
                  }
                },
                markLine: {
                  symbol: "none",
                  silent: true,
                  lineStyle: {
                    type: "solid",
                    color: '#b33939',
                    width: 1,
                    opacity: 0.5,
                  },
                  label: {
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
                    {xAxis: 2000},

                    {yAxis: -1},
                    {yAxis: -0.5},
                    {yAxis: 0},
                    {yAxis: 0.5},
                    {yAxis: 1},
                  ]
                }
              }
            ],
          });
          $(window).resize(function () {
            chartaVR.resize();
          });
          var chartaVL = echarts.init(document.getElementById("aVL"));
          chartaVL.clear()
          chartaVL.setOption({
            title: {
              text: "",
              top: 5,
              left: 5,
            },
            grid: {
              left: '1',
              right: '1',
              top: '1',
              bottom: '1',
              containLabel: false
            },
            xAxis: {
              type: 'category',
              data: x,
              axisLabel: {
                show: false,
                interval: 4,
              },
              axisTick: {
                show: false
              },
              axisLine: {
                show: false
              },
              splitLine: {
                show: true, //让网格显示
                lineStyle: {//网格样式
                  color: "#f8c1bf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
                  opacity: 0.6,//透明度
                }
              }
            },
            yAxis: {
              type: 'value',
              axisLabel: {
                show: false,
              },
              axisTick: {
                show: false
              },
              axisLine: {
                show: false
              },
              //  splitNumber: 3, // 横线数
              interval: 0.1, // 刻度间隔
              splitLine: {
                show: true, //让网格显示
                lineStyle: {//网格样式
                  color: "#f8c1bf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
                  opacity: 0.6,//透明度
                }
              },
              max: 1,
              min: -1
            },
            series: [
              {
                type: 'line',
                smooth: true,
                showSymbol: false,
                data: nArr[4],
                lineStyle: {
                  normal: {
                    color: "#000000",
                    width: 1
                  }
                },
                markLine: {
                  symbol: "none",
                  silent: true,
                  lineStyle: {
                    type: "solid",
                    color: '#b33939',
                    width: 1,
                    opacity: 0.5,
                  },
                  label: {
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
                    {xAxis: 2000},

                    {yAxis: -1},
                    {yAxis: -0.5},
                    {yAxis: 0},
                    {yAxis: 0.5},
                    {yAxis: 1},
                  ]
                }
              }
            ],
          });
          $(window).resize(function () {
            chartaVL.resize();
          });
          var chartaVF = echarts.init(document.getElementById("aVF"));
          chartaVF.clear()
          chartaVF.setOption({
            title: {
              text: "",
              top: 5,
              left: 5,
            },
            grid: {
              left: '1',
              right: '1',
              top: '1',
              bottom: '1',
              containLabel: false
            },
            xAxis: {
              type: 'category',
              data: x,
              axisLabel: {
                show: false,
                interval: 4,
              },
              axisTick: {
                show: false
              },
              axisLine: {
                show: false
              },
              splitLine: {
                show: true, //让网格显示
                lineStyle: {//网格样式
                  color: "#f8c1bf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
                  opacity: 0.6,//透明度
                }
              }
            },
            yAxis: {
              type: 'value',
              axisLabel: {
                show: false,
              },
              axisTick: {
                show: false
              },
              axisLine: {
                show: false
              },
              //  splitNumber: 3, // 横线数
              interval: 0.1, // 刻度间隔
              splitLine: {
                show: true, //让网格显示
                lineStyle: {//网格样式
                  color: "#f8c1bf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
                  opacity: 0.6,//透明度
                }
              },
              max: 1,
              min: -1
            },
            series: [
              {
                type: 'line',
                smooth: true,
                showSymbol: false,
                data: nArr[5],
                lineStyle: {
                  normal: {
                    color: "#000000",
                    width: 1,
                  }
                },
                markLine: {
                  symbol: "none",
                  silent: true,
                  lineStyle: {
                    type: "solid",
                    color: '#b33939',
                    width: 1,
                    opacity: 0.5,
                  },
                  label: {
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
                    {xAxis: 2000},

                    {yAxis: -1},
                    {yAxis: -0.5},
                    {yAxis: 0},
                    {yAxis: 0.5},
                    {yAxis: 1},
                  ]
                }
              }
            ],
          });
          $(window).resize(function () {
            chartaVF.resize();
          });
          //放大之后的心电图
          var chartI1 = echarts.init(document.getElementById("I1"));
          chartI1.clear()
          chartI1.setOption({
            title: {
              text: "",
              top: 5,
              left: 5,
            },
            grid: {
              left: '1',
              right: '1',
              top: '1',
              bottom: '1',
              containLabel: false
            },
            tooltip: {
              trigger: 'axis',
              axisPointer: {
                type: 'cross'
              }
            },

            dataZoom: [
              {
                type: 'inside',
                start: 0,
                end: 35
              },
              {
                start: 0,
                end: 35
              }
            ],
            // dataZoom: [{
            //   type: 'inside', // 放大和缩小
            //   orient: 'vertical',
            //   filterMode: "none",//或者”empty“
            // }, {
            //   type: 'inside',
            //   filterMode: "none",//或者”empty“
            // }, {
            //     start: 0,//默认为0
            //     end: 1000,//默认为100
            //     type: 'slider',
            //     show: true,
            //     xAxisIndex: [0],
            //     handleSize: 0,//滑动条的 左右2个滑动条的大小
            //     startValue: 0, // 初始显示值
            //     endValue: 500000, // 结束显示值,自己定
            //     height: 5,//组件高度
            //     left: '10%', //左边的距离
            //     right: '10%',//右边的距离
            //     bottom: '1%',//底边的距离
            //     borderColor: "#000000",
            //     fillerColor: '#000000',
            //     borderRadius: 5,
            //     backgroundColor: '#ff1600',//两边未选中的滑动条区域的颜色
            //     showDataShadow: false,//是否显示数据阴影 默认auto
            //     showDetail: false,//即拖拽时候是否显示详细数值信息 默认true
            //     realtime: true, //是否实时更新
            //     filterMode: "none",//或者”empty“
            //   }],
            xAxis: {
              type: 'category',
              data: x,
              axisLabel: {
                show: false,
                interval: 4,
              },
              axisTick: {
                show: false
              },
              axisLine: {
                show: false
              },
              splitLine: {
                show: true, //让网格显示
                lineStyle: {//网格样式
                  color: "#f8c1bf", //网格的颜色
                  width: 1, //网格的宽度
                  type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
                }
              }
            },
            yAxis: {
              type: 'value',
              axisLabel: {
                show: false,
              },
              axisTick: {
                show: false
              },
              axisLine: {
                show: false
              },
              //  splitNumber: 3, // 横线数
              interval: 0.1, // 刻度间隔
              splitLine: {
                show: true, //让网格显示
                lineStyle: {//网格样式
                  color: "#f8c1bf", //网格的颜色
                  width: 1, //网格的宽度
                  type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
                }
              },
              max: 1,
              min: -1
            },
            series: [
              {
                type: 'line',
                smooth: true,
                showSymbol: false,
                data: nArr[0],
                lineStyle: {
                  normal: {
                    color: "#000000",
                    width: 1
                  }
                },
                markLine: {
                  symbol: "none",
                  silent: true,
                  lineStyle: {
                    type: "solid",
                    color: '#b33939',
                    width: 0.5
                  },
                  label: {
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
                    {xAxis: 2000},

                    {yAxis: -1},
                    {yAxis: -0.5},
                    {yAxis: 0},
                    {yAxis: 0.5},
                    {yAxis: 1},
                  ]
                }
              }
            ],
          });
          $(window).resize(function () {
            chartI1.resize();
          });
          var chartII1 = echarts.init(document.getElementById("II1"));
          chartII1.clear()
          chartII1.setOption({
            title: {
              text: "",
              top: 5,
              left: 5,
            },
            grid: {
              left: '1',
              right: '1',
              top: '1',
              bottom: '1',
              containLabel: false
            },
            tooltip: {
              trigger: 'axis',
              axisPointer: {
                type: 'cross'
              }
            },
            dataZoom: [
              {
                type: 'inside',
                start: 0,
                end: 35
              },
              {
                start: 0,
                end: 35
              }
            ],
            // dataZoom: [{
            //   type: 'inside', // 放大和缩小
            //   orient: 'vertical',
            //   filterMode: "none",//或者”empty“
            // }, {
            //   type: 'inside',
            //   filterMode: "none",//或者”empty“
            // },
            //   {
            //     // start: 0,//默认为0
            //     // end: 100,//默认为100
            //     type: 'slider',
            //     show: true,
            //     // xAxisIndex: [0],
            //     handleSize: 0,//滑动条的 左右2个滑动条的大小
            //     startValue: 0, // 初始显示值
            //     endValue: 500000, // 结束显示值,自己定
            //     height: 5,//组件高度
            //     left: '10%', //左边的距离
            //     right: '10%',//右边的距离
            //     bottom: '1%',//底边的距离
            //     borderColor: "#000000",
            //     fillerColor: '#000000',
            //     borderRadius: 5,
            //     backgroundColor: '#ff1600',//两边未选中的滑动条区域的颜色
            //     showDataShadow: false,//是否显示数据阴影 默认auto
            //     showDetail: false,//即拖拽时候是否显示详细数值信息 默认true
            //     realtime: true, //是否实时更新
            //     filterMode: "none",//或者”empty“
            //   }],
            xAxis: {
              type: 'category',
              data: x,
              axisLabel: {
                show: false,
                interval: 4,
              },
              axisTick: {
                show: false
              },
              axisLine: {
                show: false
              },
              splitLine: {
                show: true, //让网格显示
                lineStyle: {//网格样式
                  color: "#f8c1bf", //网格的颜色
                  width: 1, //网格的宽度
                  type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
                }
              }
            },
            yAxis: {
              type: 'value',
              axisLabel: {
                show: false,
              },
              axisTick: {
                show: false
              },
              axisLine: {
                show: false
              },
              //  splitNumber: 3, // 横线数
              interval: 0.1, // 刻度间隔
              splitLine: {
                show: true, //让网格显示
                lineStyle: {//网格样式
                  color: "#f8c1bf", //网格的颜色
                  width: 1, //网格的宽度
                  type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
                }
              },
              max: 1,
              min: -1
            },

            series: [
              {
                type: 'line',
                smooth: true,
                showSymbol: false,
                data: nArr[1],
                lineStyle: {
                  normal: {
                    color: "#000000",
                    width: 1
                  }
                },
                markLine: {
                  symbol: "none",
                  silent: true,
                  lineStyle: {
                    type: "solid",
                    color: '#b33939',
                    width: 0.5
                  },
                  label: {
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
                    {xAxis: 2000},

                    {yAxis: -1},
                    {yAxis: -0.5},
                    {yAxis: 0},
                    {yAxis: 0.5},
                    {yAxis: 1},
                  ]
                }
              }
            ],
          });
          $(window).resize(function () {
            chartII1.resize();
          });
          var chartIII1 = echarts.init(document.getElementById("III1"));
          chartIII1.clear()
          chartIII1.setOption({
            title: {
              text: "",
              top: 5,
              left: 5,
            },
            grid: {
              left: '1',
              right: '1',
              top: '1',
              bottom: '1',
              containLabel: false
            },
            tooltip: {
              trigger: 'axis',
              axisPointer: {
                type: 'cross'
              }
            },
            dataZoom: [
              {
                type: 'inside',
                start: 0,
                end: 35
              },
              {
                start: 0,
                end: 35
              }
            ],
            // dataZoom: [{
            //   type: 'inside', // 放大和缩小
            //   orient: 'vertical',
            //   filterMode: "none",//或者”empty“
            // }, {
            //   type: 'inside',
            //   filterMode: "none",//或者”empty“
            // },
            //   {
            //     // start: 0,//默认为0
            //     // end: 100,//默认为100
            //     type: 'slider',
            //     show: true,
            //     // xAxisIndex: [0],
            //     handleSize: 0,//滑动条的 左右2个滑动条的大小
            //     startValue: 0, // 初始显示值
            //     endValue: 500000, // 结束显示值,自己定
            //     height: 5,//组件高度
            //     left: '10%', //左边的距离
            //     right: '10%',//右边的距离
            //     bottom: '1%',//底边的距离
            //     borderColor: "#000000",
            //     fillerColor: '#000000',
            //     borderRadius: 5,
            //     backgroundColor: '#ff1600',//两边未选中的滑动条区域的颜色
            //     showDataShadow: false,//是否显示数据阴影 默认auto
            //     showDetail: false,//即拖拽时候是否显示详细数值信息 默认true
            //     realtime: true, //是否实时更新
            //     filterMode: "none",//或者”empty“
            //   }],
            xAxis: {
              type: 'category',
              data: x,
              axisLabel: {
                show: false,
                interval: 4,
              },
              axisTick: {
                show: false
              },
              axisLine: {
                show: false
              },
              splitLine: {
                show: true, //让网格显示
                lineStyle: {//网格样式
                  color: "#f8c1bf", //网格的颜色
                  width: 1, //网格的宽度
                  type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
                }
              }
            },
            yAxis: {
              type: 'value',
              axisLabel: {
                show: false,
              },
              axisTick: {
                show: false
              },
              axisLine: {
                show: false
              },
              //  splitNumber: 3, // 横线数
              interval: 0.1, // 刻度间隔
              splitLine: {
                show: true, //让网格显示
                lineStyle: {//网格样式
                  color: "#f8c1bf", //网格的颜色
                  width: 1, //网格的宽度
                  type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
                }
              },
              max: 1,
              min: -1
            },
            series: [
              {
                type: 'line',
                smooth: true,
                showSymbol: false,
                data: nArr[2],
                lineStyle: {
                  normal: {
                    color: "#000000",
                    width: 1
                  }
                },
                markLine: {
                  symbol: "none",
                  silent: true,
                  lineStyle: {
                    type: "solid",
                    color: '#b33939',
                    width: 0.5
                  },
                  label: {
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
                    {xAxis: 2000},

                    {yAxis: -1},
                    {yAxis: -0.5},
                    {yAxis: 0},
                    {yAxis: 0.5},
                    {yAxis: 1},
                  ]
                }
              }
            ],
          });
          $(window).resize(function () {
            chartIII1.resize();
          });
          var chartaVR1 = echarts.init(document.getElementById("aVR1"));
          chartaVR1.clear()
          chartaVR1.setOption({
            title: {
              text: "",
              top: 5,
              left: 5,
            },
            grid: {
              left: '1',
              right: '1',
              top: '1',
              bottom: '1',
              containLabel: false
            },
            tooltip: {
              trigger: 'axis',
              axisPointer: {
                type: 'cross'
              }
            },
            dataZoom: [
              {
                type: 'inside',
                start: 0,
                end: 35
              },
              {
                start: 0,
                end: 35
              }
            ],
            // dataZoom: [{
            //   type: 'inside', // 放大和缩小
            //   orient: 'vertical',
            //   filterMode: "none",//或者”empty“
            // }, {
            //   type: 'inside',
            //   filterMode: "none",//或者”empty“
            // },
            //   {
            //     // start: 0,//默认为0
            //     // end: 100,//默认为100
            //     type: 'slider',
            //     show: true,
            //     // xAxisIndex: [0],
            //     handleSize: 0,//滑动条的 左右2个滑动条的大小
            //     startValue: 0, // 初始显示值
            //     endValue: 500000, // 结束显示值,自己定
            //     height: 5,//组件高度
            //     left: '10%', //左边的距离
            //     right: '10%',//右边的距离
            //     bottom: '1%',//底边的距离
            //     borderColor: "#000000",
            //     fillerColor: '#000000',
            //     borderRadius: 5,
            //     backgroundColor: '#ff1600',//两边未选中的滑动条区域的颜色
            //     showDataShadow: false,//是否显示数据阴影 默认auto
            //     showDetail: false,//即拖拽时候是否显示详细数值信息 默认true
            //     realtime: true, //是否实时更新
            //     filterMode: "none",//或者”empty“
            //   }],
            xAxis: {
              type: 'category',
              data: x,
              axisLabel: {
                show: false,
                interval: 4,
              },
              axisTick: {
                show: false
              },
              axisLine: {
                show: false
              },
              splitLine: {
                show: true, //让网格显示
                lineStyle: {//网格样式
                  color: "#f8c1bf", //网格的颜色
                  width: 1, //网格的宽度
                  type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
                }
              }
            },
            yAxis: {
              type: 'value',
              axisLabel: {
                show: false,
              },
              axisTick: {
                show: false
              },
              axisLine: {
                show: false
              },
              //  splitNumber: 3, // 横线数
              interval: 0.1, // 刻度间隔
              splitLine: {
                show: true, //让网格显示
                lineStyle: {//网格样式
                  color: "#f8c1bf", //网格的颜色
                  width: 1, //网格的宽度
                  type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
                }
              },
              max: 1,
              min: -1
            },
            series: [
              {
                type: 'line',
                smooth: true,
                showSymbol: false,
                data: nArr[3],
                lineStyle: {
                  normal: {
                    color: "#000000",
                    width: 1
                  }
                },
                markLine: {
                  symbol: "none",
                  silent: true,
                  lineStyle: {
                    type: "solid",
                    color: '#b33939',
                    width: 0.5
                  },
                  label: {
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
                    {xAxis: 2000},

                    {yAxis: -1},
                    {yAxis: -0.5},
                    {yAxis: 0},
                    {yAxis: 0.5},
                    {yAxis: 1},
                  ]
                }
              }
            ],
          });
          $(window).resize(function () {
            chartaVR1.resize();
          });
          var chartaVL1 = echarts.init(document.getElementById("aVL1"));
          chartaVL1.clear()
          chartaVL1.setOption({
            title: {
              text: "",
              top: 5,
              left: 5,
            },
            grid: {
              left: '1',
              right: '1',
              top: '1',
              bottom: '1',
              containLabel: false
            },
            tooltip: {
              trigger: 'axis',
              axisPointer: {
                type: 'cross'
              }
            },
            dataZoom: [
              {
                type: 'inside',
                start: 0,
                end: 35
              },
              {
                start: 0,
                end: 35
              }
            ],
            // dataZoom: [{
            //   type: 'inside', // 放大和缩小
            //   orient: 'vertical',
            //   filterMode: "none",//或者”empty“
            // }, {
            //   type: 'inside',
            //   filterMode: "none",//或者”empty“
            // },
            //   {
            //     // start: 0,//默认为0
            //     // end: 100,//默认为100
            //     type: 'slider',
            //     show: true,
            //     // xAxisIndex: [0],
            //     handleSize: 0,//滑动条的 左右2个滑动条的大小
            //     startValue: 0, // 初始显示值
            //     endValue: 500000, // 结束显示值,自己定
            //     height: 5,//组件高度
            //     left: '10%', //左边的距离
            //     right: '10%',//右边的距离
            //     bottom: '1%',//底边的距离
            //     borderColor: "#000000",
            //     fillerColor: '#000000',
            //     borderRadius: 5,
            //     backgroundColor: '#ff1600',//两边未选中的滑动条区域的颜色
            //     showDataShadow: false,//是否显示数据阴影 默认auto
            //     showDetail: false,//即拖拽时候是否显示详细数值信息 默认true
            //     realtime: true, //是否实时更新
            //     filterMode: "none",//或者”empty“
            //   }],
            xAxis: {
              type: 'category',
              data: x,
              axisLabel: {
                show: false,
                interval: 4,
              },
              axisTick: {
                show: false
              },
              axisLine: {
                show: false
              },
              splitLine: {
                show: true, //让网格显示
                lineStyle: {//网格样式
                  color: "#f8c1bf", //网格的颜色
                  width: 1, //网格的宽度
                  type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
                }
              }
            },
            yAxis: {
              type: 'value',
              axisLabel: {
                show: false,
              },
              axisTick: {
                show: false
              },
              axisLine: {
                show: false
              },
              //  splitNumber: 3, // 横线数
              interval: 0.1, // 刻度间隔
              splitLine: {
                show: true, //让网格显示
                lineStyle: {//网格样式
                  color: "#f8c1bf", //网格的颜色
                  width: 1, //网格的宽度
                  type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
                }
              },
              max: 1,
              min: -1
            },
            series: [
              {
                type: 'line',
                smooth: true,
                showSymbol: false,
                data: nArr[4],
                lineStyle: {
                  normal: {
                    color: "#000000",
                    width: 1
                  }
                },
                markLine: {
                  symbol: "none",
                  silent: true,
                  lineStyle: {
                    type: "solid",
                    color: '#b33939',
                    width: 0.5
                  },
                  label: {
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
                    {xAxis: 2000},

                    {yAxis: -1},
                    {yAxis: -0.5},
                    {yAxis: 0},
                    {yAxis: 0.5},
                    {yAxis: 1},
                  ]
                }
              }
            ],
          });
          $(window).resize(function () {
            chartaVL1.resize();
          });
          var chartaVF1 = echarts.init(document.getElementById("aVF1"));
          chartaVF1.clear()
          chartaVF1.setOption({
            title: {
              text: "",
              top: 5,
              left: 5,
            },
            grid: {
              left: '1',
              right: '1',
              top: '1',
              bottom: '1',
              containLabel: false
            },
            tooltip: {
              trigger: 'axis',
              axisPointer: {
                type: 'cross'
              }
            },
            dataZoom: [
              {
                type: 'inside',
                start: 0,
                end: 35
              },
              {
                start: 0,
                end: 35
              }
            ],
            // dataZoom: [{
            //   type: 'inside', // 放大和缩小
            //   orient: 'vertical',
            //   filterMode: "none",//或者”empty“
            // }, {
            //   type: 'inside',
            //   filterMode: "none",//或者”empty“
            // },
            //   {
            //     start: 0,//默认为0
            //     end: 100,//默认为100
            //     type: 'slider',
            //     show: true,
            //     // xAxisIndex: [0],
            //     handleSize: 0,//滑动条的 左右2个滑动条的大小
            //     startValue: 0, // 初始显示值
            //     endValue:50000, // 结束显示值,自己定
            //     height: 5,//组件高度
            //     left: '10%', //左边的距离
            //     right: '10%',//右边的距离
            //     bottom: '1%',//底边的距离
            //     borderColor: "#000000",
            //     fillerColor: '#000000',
            //     borderRadius: 5,
            //     backgroundColor: '#ff1600',//两边未选中的滑动条区域的颜色
            //     showDataShadow: false,//是否显示数据阴影 默认auto
            //     showDetail: false,//即拖拽时候是否显示详细数值信息 默认true
            //     realtime: true, //是否实时更新
            //     filterMode: "none",//或者”empty“
            //   }],
            xAxis: {
              type: 'category',
              data: x,
              axisLabel: {
                show: false,
                interval: 4,
              },
              axisTick: {
                show: false
              },
              axisLine: {
                show: false
              },
              splitLine: {
                show: true, //让网格显示
                lineStyle: {//网格样式
                  color: "#f8c1bf", //网格的颜色
                  width: 1, //网格的宽度
                  type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
                }
              }
            },
            yAxis: {
              type: 'value',
              axisLabel: {
                show: false,
              },
              axisTick: {
                show: false
              },
              axisLine: {
                show: false
              },
              //  splitNumber: 3, // 横线数
              interval: 0.1, // 刻度间隔
              splitLine: {
                show: true, //让网格显示
                lineStyle: {//网格样式
                  color: "#f8c1bf", //网格的颜色
                  width: 1, //网格的宽度
                  type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
                }
              },
              max: 1,
              min: -1
            },
            series: [
              {
                type: 'line',
                smooth: true,
                showSymbol: false,
                data: nArr[5],
                lineStyle: {
                  normal: {
                    color: "#000000",
                    width: 1
                  }
                },
                markLine: {
                  symbol: "none",
                  silent: true,
                  lineStyle: {
                    type: "solid",
                    color: '#b33939',
                    width: 0.5
                  },
                  label: {
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
                    {xAxis: 2000},

                    {yAxis: -1},
                    {yAxis: -0.5},
                    {yAxis: 0},
                    {yAxis: 0.5},
                    {yAxis: 1},
                  ]
                }
              }
            ],
          });
          $(window).resize(function () {
            chartaVF1.resize();
          });
        },
        error: function (data) {
          alert("数据请求错误,请刷新页面或联系管理员")
          loading.close()
          console.log("请求失败：",data)
        }
      })
    },
    //获取当前时间
    getData() {
      var str = new Date();
      var nowTime = str.getFullYear() + "-"
        + (str.getMonth() + 1) + "-" + str.getDate() + " " + str.getHours() + ":" + str.getMinutes() + ":" + str.getSeconds();
      console.log("获取到当前的时间：",nowTime);
      return nowTime;
    },
    //截断数据（一条数据现在2000）
    getNewArray(array, subGroupLength) {
      let i = 0;
      let newArray = [];
      while (i < array.length) {
        newArray.push(array.slice(i, i += subGroupLength));
      }
      return newArray;
    },
    //保存数据
    btnUpload() {
      var _th=this
      var form = {
        pId: this.pId,
        diagnosisStatus: '1',
        reportType: "ECG",
        diagnosisConclusion: this.data.resultByDoctor,
        reportTime: this.data.dataTime,
        diagnosisDoctor: this.data.doctorName,
      }
      getReportByPId(this.pId).then(res => {
        if (res.data == null) {
          addReport(form).then(response => {
            _th.$modal.msgSuccess("新增成功");
            // _th.getList();
            console.log("新增成功！")
          });
        } else {
          form["reportId"] = res.data.reportId
          console.log("保存的数据：",form)
          updateReport(form).then(response => {
            _th.$modal.msgSuccess("修改成功");
            // _th.getList();
            console.log("修改成功！")
          })
        }
      })
    },
  },
};
</script>

<style lang="scss" scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.page {
  margin-top: 0.5vw;
  width: 100%;
  height: 52vw;
  //border: 3px solid #0000ff;
}

.header {
  width: 100%;
  height: 15vw;
  display: flex;
  flex-wrap: nowrap;
  padding-left: 2vw;
  margin-bottom: 2vw;
  font-size: 1vw;
  color: #000000;
}

.header-left {
  width: 60%;
  height: 100%;
  display: flex;
  flex-wrap: nowrap
}

.header-1 {
  width: 28%;
  height: 100%;
  display: flex;
  flex-direction: column;
  flex-wrap: nowrap;
  justify-content: space-around;
}

.header-2 {
  width: 28%;
  height: 100%;
  display: flex;
  flex-direction: column;
  flex-wrap: nowrap;
  justify-content: space-around;
}

.header-3 {
  width: 44%;
  height: 100%;
  display: flex;
  flex-direction: column;
  flex-wrap: nowrap;
  justify-content: space-around;
}

.header-right {
  width: 40%;
  height: 100%;
}

.header-4 {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  flex-wrap: nowrap;
}

.header-4-1-1 {
  width: 100%;
  height: 6vw;
}

.font {
  font-size: 1vw;
  font-weight: 700;
  color: #6f0600;
  background-color: #fff;
}


.body {
  display: flex;
}

.body .body-1 {
  flex: 1;
}

.bottom {
  width: 100%;
  height: 5vw;
  //border: 1px solid #000000;
  margin-top: 2vw;
}

.bottom-left {

}

.anNiu {
  height: 2vw;
  width: 5vw;
  float: right;
  margin-right: 4vw;
  font-size: 1vw;
}

.line {
  height: 4.2vw;
  width: 84vw;
  margin: 0;
  padding: 0;

}

.lineI {
  position: absolute;
  display: inline-block;
  top: 22vw;
  left: 0.5vw;
  height: 15vw;
  width: 83vw;
  background: white;
  border: 1px solid black;
  z-index: 3;
}

.lineshow {
  height: 11vw;
  width: 82vw;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

.chartsBig {
  position: absolute;
  top: 40%;
  left: 50%;
  transform: translate(-50%, -50%);
  height: 10vw;
  width: 80vw;
  border: 1px solid #00afff;
  //z-index: 1000;
  background-color: #00afff;
  //display: none;
}

.noName {
  margin-left: 40vw;
  margin-top: 12.8vw;
  height: 2vw;
  width: 3vw;
  font-size: 1vw;
}
</style>
