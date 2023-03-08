<template>
  <div class="wrap">
    <div id="pdfDom" style="padding: 10px;">
      <div id="app">
        <div class="page">
          <div class="header">
            <div class="header-left">
              <div class="header-1">
                <div class="header-1-1">
                  <strong>姓名:</strong> {{ data.name }}
                </div>
                <div class="header-1-1">
                  <strong>性别:</strong> {{ data.gender }}
                </div>
                <div class="header-1-1">
                  <strong>年龄:</strong> {{ data.age }} 岁
                </div>
                <div class="header-1-1">
                  <strong>送检科室:</strong> -
                </div>
                <div class="header-1-1">
                  <strong>申请单号:</strong> -
                </div>
              </div>
              <div class="header-1">
                <div class="header-1-1">
                  <strong>门诊号:</strong> -
                </div>
                <div class="header-1-1">
                  <strong>住院号:</strong> -
                </div>
                <div class="header-1-1">
                  <strong>病人编号:</strong> -
                </div>
                <div class="header-1-1">
                  <strong>HR:</strong> {{ data.hr }} bpm
                </div>
                <div class="header-1-1">
                  <strong>PR:</strong> {{ data.pr }} ms
                </div>
              </div>
              <div class="header-1">
                <div class="header-1-1">
                  <strong>QRS:</strong> {{ data.qrs }} ms
                </div>
                <div class="header-1-1">
                  <strong>QT/QTc:</strong> {{ data.qt }} ms/ - ms
                </div>
                <div class="header-1-1">
                  <strong>P/QRS/T:</strong> {{ data.p }}/-/- deg
                </div>
                <div class="header-1-1">
                  <strong>PV5/SV1:</strong> {{ data.pv5 }}/- mV
                </div>
                <div class="header-1-1">
                  <strong>RV5+SV1:</strong> {{ data.rv5 }} mV
                </div>
              </div>
            </div>
            <div class="header-left">
              <div class="header-1">
                <div class="header-1-1"><strong>自动分析结果，仅供参考</strong></div>
                <div class="box8-1-1">{{ data.result }}</div>
                <div class="header-1-1"><strong>医师诊断</strong></div>
                <div>
                    <textarea
                      v-model="data.resultByDoctor"
                      placeholder="请在这里输入医生诊断结果"
                      data-value="1111"
                      class="box-1-1"
                    />
                </div>
              </div>
            </div>
          </div>
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
            <strong>医师:</strong><input class="box8-2" v-model="data.doctorName"></input>
            <strong>日期:</strong><input class="box8-2" v-if="data.diagnosisData!=null" v-model="data.diagnosisData"></input>
            <input class="box8-2" v-else v-model="data.dataTime"></input>
            <el-button type="primary" round style="margin-right: 1vw;" @click="btnUpload">
              保存数据
            </el-button>
          </div>
        </div>
      </div>
    </div>
<!--    <el-button type="primary" round style="margin-top: 20px; margin-left: 10% ;margin-bottom: 15px"-->
<!--               @click="click_close">-->
<!--      刷新-->
<!--    </el-button>-->

  </div>
</template>

<script>
import html2Canvas from 'html2canvas'
import JsPDF from 'jspdf'
import echarts from 'echarts'
import $ from 'jquery';
import {addReport, getLookLog, getReportByPId, updateReport} from "@/api/report/report";
import {pdfDownload2} from "@/api/pdf/pdf";

export default {
  name: "static_single",
  inject: ['reload'],
  data() {
    return {
      // exportPDFtitle: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.patientName + "静态心电报告_" + this.$route.query.pId,
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
    console.log("页面加载")
    var pId = this.$route.query.pId;
    console.log("pId" + pId)
    if (pId) {
      this.pId = pId;
      getReportByPId(this.pId).then(response => {
        console.log(response.data)
        this.data.resultByDoctor = response.data.diagnosisConclusion
        this.data.doctorName = response.data.diagnosisDoctor
        this.data.diagnosisData = response.data.reportTime
        console.log("-------------------------------")
        console.log(this.diagnosisData)
      });
      var show = sessionStorage.getItem(pId + "show");
      if (!show) {
        this.get();
      }
    }
  },
  mounted() {
    this.click_close()
    this.get();
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
      this.openV1 = false;
      this.openV2 = false;
      this.openV3 = false;
      this.openV4 = false;
      this.openV5 = false;
      this.openV6 = false;
    },

    click_close() {
      // this.reload()
      console.log(this.$route)
      if (this.$route.query.reload.indexOf('one') === -1) {
        this.$route.query.reload = 'one'
        this.reload()
      }
      console.log("重新刷新！")
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
      console.log("执行")
      console.log(this.pId)
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
          console.log(_th.pId)
          console.log(data)
          console.log("成功")
          // sessionStorage.removeItem("data");
          // sessionStorage.setItem(_th.pId + 'data', JSON.stringify(data))
          // sessionStorage.setItem(_th.pId + 'show', true)
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
          console.log("获取到的导联数据",_th.data.datas)
          console.log("获取到的导联数据长度",_th.data.datas.length)
          var x = [];
          var nArr = _th.getNewArray(_th.data.datas, 2000);
          console.log("数据以2000一条分好组",nArr)
          for (var i = 0; i < 2000; i++) {
            x.push(i);
          }
          var chartI = echarts.init(document.getElementById("I"));
          chartI.clear()
          chartI.setOption({
            title: {
              text: "",
              top: 5,
              left: 5,
            },
            grid: {
              left: '5',
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
              left: '5',
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
              left: '5',
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
              left: '5',
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
              left: '5',
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
              left: '5',
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
        },
        error: function (data) {
          alert("数据请求错误,请刷新页面或联系管理员")
          loading.close()
          console.log("错误")
          console.log(_th.pId)
          console.log(data)
        }
      })
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
            this.$modal.msgSuccess("新增成功");
            this.getList();
            console.log("新增成功！")
          });
        } else {
          form["reportId"] = res.data.reportId
          console.log(form)
          updateReport(form).then(response => {
            this.$modal.msgSuccess("修改成功");
            this.getList();
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
  height:60vw;
  //border: 3px solid #0000ff;
}

.header {
  display: flex;
  justify-content: space-between;
  font-size: 1.2vw;
  //margin-bottom: 3vw;
}

.header-left {
  display: flex;
  flex: 1;
}

.header-1 {
  flex: 1;
  float: left;
  padding-left: 2vw;
  width: 4vw;
  //border: 1px solid #000000;
}

.header-1 .header-1-1 {
  height: 2.5vw;
  width: 100%;
  //border: 1px solid #00afff;
}

.box-1-1 {
  wrap: "physical";
  border: 1px solid #000000;
  //resize:none;
  overflow-y: auto;
  width: 100%;
  height: 7vw;
  font-size: 1.2vw;
  //border: none;
  overflow: hidden;
  font-family: Helvetica Neue, Helvetica, PingFang SC, Hiragino Sans GB, Microsoft YaHei, Arial, sans-serif;
}

.body {
  display: flex;
}

.body .body-1 {
  flex: 1;
}

.bottom {
  position: absolute;
  right: 0;
  width: 50%;
  height: 4vw;
  margin-top: 1vw;
  font-size: 1.2vw;
}

.box8-2 {
  display: inline-block;
  width: 9vw;
  height:2vw;
  border: none;
  font-size: 1.2vw;
  text-align: center;
  border-bottom: 1px solid black;
  margin-right: 0.2vw;
  margin-top: 0.5vw;
  outline: none

}
.box8-1-1{
  border: 1px solid #000000;
  width: 100%;
  height: 7vw;
  font-size: 1.2vw;
}

.line {
  height: 4.2vw;
  width: 84vw;
  margin: 1vw;
  padding: 0;

}

.lineI {
  position: absolute;
  display: inline-block;
  top: 500px;
  left: 70px;
  height: 300px;
  width: 90%;
  background: white;
  border: 2px solid black;
  z-index: 3;
}

.lineshow {
  height: 200px;
  width: 78em;
  max-width: 97%;
  margin-left: 1.5em;
  margin-top: 1.5em;
  padding: 0
}

.btn1 {
  position: relative;
  float: right;
  bottom: 1.6em;
  right: 1.3em;
  z-index: 2;
}
</style>
