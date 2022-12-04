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
                  <strong>HR:</strong> - bpm
                </div>
                <div class="header-1-1">
                  <strong>PR:</strong> - ms
                </div>
              </div>
              <div class="header-1">
                <div class="header-1-1">
                  <strong>QRS:</strong> - ms
                </div>
                <div class="header-1-1">
                  <strong>QT/QTc:</strong> - ms/ - ms
                </div>
                <div class="header-1-1">
                  <strong>P/QRS/T:</strong> -/-/- deg
                </div>
                <div class="header-1-1">
                  <strong>PV5/SV1:</strong> -/- mV
                </div>
                <div class="header-1-1">
                  <strong>RV5+SV1:</strong> - mV
                </div>
              </div>
            </div>
            <div class="header-left">
              <div class="header-1">
                <div class="header-1-1">
                  <strong>自动分析结果，仅供参考</strong>
                </div>
                <div class="header-1-1">
                  <div
                    class="box8-1-1"
                  >{{ data.result }}
                  </div>
                </div>

                <div class="header-1-1"></div>

                <div class="header-1-1">
                  <strong>医师诊断</strong>
                </div>
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
                <div id="I" class="line"></div>
                <button @click="clicktrueI"  class="btn1">展开</button>
              </div>
              <div>
                <div id="II" class="line"></div>
                <button @click="clicktrueII"  class="btn1">展开</button>
              </div>
              <div>
                <div id="III" class="line"></div>
                <button @click="clicktrueIII"  class="btn1">展开</button>
              </div>
              <div>
                <div id="aVR" class="line"></div>
                <button @click="clicktrueaVR"  class="btn1">展开</button>
              </div>
              <div>
                <div id="aVL" class="line"></div>
                <button @click="clicktrueaVL"  class="btn1">展开</button>
              </div>
              <div>
                <div id="aVF" class="line"></div>
                <button @click="clicktrueaVF"  class="btn1">展开</button>
              </div>
            </div>
            <div class="body-1">
              <div>
                <div id="V1" class="line"></div>
                <button @click="clicktrueV1"  class="btn1">展开</button>
              </div>
              <div>
                <div id="V2" class="line"></div>
                <button @click="clicktrueV2"  class="btn1">展开</button>
              </div>
              <div>
                <div id="V3" class="line"></div>
                <button @click="clicktrueV3"  class="btn1">展开</button>
              </div>
              <div>
                <div id="V4" class="line"></div>
                <button @click="clicktrueV4"  class="btn1">展开</button>
              </div>
              <div>
                <div id="V5" class="line"></div>
                <button @click="clicktrueV5"  class="btn1">展开</button>
              </div>
              <div>
                <div id="V6" class="line"></div>
                <button @click="clicktrueV6"  class="btn1">展开</button>
              </div>

            </div>
          </div>
          <div class="bottom">
            <strong>医师:</strong><input class="box8-2" v-model="data.doctorName"></input>
            <strong>日期:</strong><input class="box8-2" v-if="data.diagnosisData!=null"
                                       v-model="data.diagnosisData"></input>
            <input class="box8-2" v-else v-model="data.dataTime"></input>
          </div>
        </div>
      </div>

      <div  class="lineI" v-show="openI">
        <div id="I1" class="lineshow"></div>
        <button @click="clickclose" style="margin-left:49%;margin-top: 2%">关闭</button>
      </div>
      <div  class="lineI" v-show="openII">
        <div id="II1" class="lineshow"></div>
        <button @click="clickclose" style="margin-left:49%;margin-top: 2%">关闭</button>
      </div>
      <div  class="lineI" v-show="openIII">
        <div id="III1" class="lineshow"></div>
        <button @click="clickclose" style="margin-left:49%;margin-top: 2%">关闭</button>
      </div>
      <div  class="lineI" v-show="openaVR">
        <div id="aVR1" class="lineshow"></div>
        <button @click="clickclose" style="margin-left:49%;margin-top: 2%">关闭</button>
      </div>
      <div  class="lineI" v-show="openaVL">
        <div id="aVL1" class="lineshow"></div>
        <button @click="clickclose" style="margin-left:49%;margin-top: 2%">关闭</button>
      </div>
      <div  class="lineI" v-show="openaVF">
        <div id="aVF1" class="lineshow"></div>
        <button @click="clickclose" style="margin-left:49%;margin-top: 2%">关闭</button>
      </div>
      <div  class="lineI" v-show="openV1">
        <div id="V11" class="lineshow"></div>
        <button @click="clickclose" style="margin-left:49%;margin-top: 2%">关闭</button>
      </div>
      <div  class="lineI" v-show="openV2">
        <div id="V22" class="lineshow"></div>
        <button @click="clickclose" style="margin-left:49%;margin-top: 2%">关闭</button>
      </div>
      <div  class="lineI" v-show="openV3">
        <div id="V33" class="lineshow"></div>
        <button @click="clickclose" style="margin-left:49%;margin-top: 2%">关闭</button>
      </div>
      <div  class="lineI" v-show="openV4">
      <div id="V44" class="lineshow"></div>
      <button @click="clickclose" style="margin-left:49%;margin-top: 2%">关闭</button>
    </div>
      <div  class="lineI" v-show="openV5">
        <div id="V55" class="lineshow"></div>
        <button @click="clickclose" style="margin-left:49%;margin-top: 2%">关闭</button>
      </div>
      <div  class="lineI" v-show="openV6">
        <div id="V66" class="lineshow"></div>
        <button @click="clickclose" style="margin-left:49%;margin-top: 2%">关闭</button>
      </div>
    </div>
    <el-button type="primary" round style="margin-top: 20px; margin-left: 42.5% ;margin-bottom: 15px" @click="btnClick">
      导出PDF
    </el-button>
    <el-button type="primary" round style="margin-top: 20px; margin-left: 2% ;margin-bottom: 15px" @click="btnClickPDF">
      上传PDF
    </el-button>
    <el-button type="primary" round style="margin-top: 20px; margin-left: 5% ;margin-bottom: 15px" @click="btnUpload">
      保存数据
    </el-button>

  </div>
</template>

<script>
import html2Canvas from 'html2canvas'
import JsPDF from 'jspdf'
import echarts from 'echarts'
import $ from 'jquery';
import {addReport, getReportByPId, updateReport} from "@/api/report/report";
import {pdfDownload2} from "@/api/pdf/pdf";

export default {
  name: "index",
  data() {
    return {
      exportPDFtitle: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.patientName + "静态心电报告_" + this.$route.query.pId,
      pId: null,
      data: {
        name: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.patientName,
        gender: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.gender,
        age: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.age,
        result: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.result,
        resultByDoctor: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.result,
        dataTime: "",
        doctorName: "",
        diagnosisData: null,
      },
      openI: false,
      openII: false,
      openIII: false,
      openaVR: false,
      openaVL: false,
      openaVF: false,
      openV1: false,
      openV2: false,
      openV3: false,
      openV4: false,
      openV5: false,
      openV6: false,

    };
  },
  created() {
    console.log("创建")
    var pId = this.$route.query.pId;
    console.log(pId)
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
    this.I()
    this.II()
    this.III()
    this.aVR()
    this.aVL()
    this.aVF()
    this.V1()
    this.V2()
    this.V3()
    this.V4()
    this.V5()
    this.V6()
    this.getDate();
    this.I1()
    this.II1()
    this.III1()
    this.aVR1()
    this.aVL1()
    this.aVF1()
    this.V11()
    this.V22()
    this.V33()
    this.V44()
    this.V55()
    this.V66()
  },
  methods: {
    clicktrueI(){
      this.openI = !this.openI;
    },
    clicktrueII(){
      this.openII = !this.openII;
    },

    clicktrueIII(){
      this.openIII = !this.openIII;
    },
    clicktrueaVR(){
      this.openaVR = !this.openaVR;
    },
    clicktrueaVL(){
      this.openaVL = !this.openaVL;
    },
    clicktrueaVF(){
      this.openaVF = !this.openaVF;
    },
    clicktrueV1(){
      this.openV1 = !this.openV1;
    },
    clicktrueV2(){
      this.openV2 = !this.openV2;
    },
    clicktrueV3(){
      this.openV3 = !this.openV3;
    },
    clicktrueV4(){
      this.openV4 = !this.openV4;
    },
    clicktrueV5(){
      this.openV5 = !this.openV5;
    },
    clicktrueV6(){
      this.openV6 = !this.openV6;
    },

    clickclose(){
      this.openI = false;
      this.openII = false;
      this.openIII = false;
      this.openaVR= false;
      this.openaVL= false;
      this.openaVF= false;
      this.openV1= false;
      this.openV2= false;
      this.openV3= false;
      this.openV4= false;
      this.openV5= false;
      this.openV6= false;
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
        url: "https://server.mindyard.cn:83/detect_ecg",
        asynsc: false,
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({
          p_id: this.pId
        }),
        beforeSend:function (request) {
          // 如果后台没有跨域处理，这个自定义
          request.setRequestHeader("user","zzu");
          request.setRequestHeader("password","zzu123");
        },
        success: function (data) {
          console.log(_th.pId)
          console.log(data)
          sessionStorage.removeItem("data");
          sessionStorage.setItem(_th.pId + 'data', JSON.stringify(data))
          sessionStorage.setItem(_th.pId + 'show', true)
          loading.close()
          window.location.reload("#pdfDom");
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

    I() {
      var data = (JSON.parse(sessionStorage.getItem(this.pId + "data"))).result.data.I
      var x = new Array();
      for (var i = 0; i < data.length; i++) {
        x.push(i);
      }
      var ecgBc = echarts.init(document.getElementById("I"));
      var option = {
        title: {
          text: "I",
          top: 30,
          left: 20,
        },
        grid: {
          left: '5',
          right: '1',
          top: '0',
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
              color: ['pink'], //网格的颜色
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
              color: ['pink'], //网格的颜色
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
            data: data,
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
                {xAxis: 999.4},

                {yAxis: -1},
                {yAxis: -0.5},
                {yAxis: 0},
                {yAxis: 0.5},
                {yAxis: 1},
              ]
            }
          }
        ],
      };
      ecgBc.setOption(option);
      $(window).resize(function () {
        ecgBc.resize();
      });
    },
    I1() {
      var data = (JSON.parse(sessionStorage.getItem(this.pId + "data"))).result.data.I
      var x = new Array();
      for (var i = 0; i < data.length; i++) {
        x.push(i);
      }
      var ecgBc = echarts.init(document.getElementById("I1"));
      var option = {
        title: {
          text: "I",
          top: 30,
          left: 20,
        },
        grid: {
          left: '5',
          right: '1',
          top: '0',
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
              color: ['pink'], //网格的颜色
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
              color: ['pink'], //网格的颜色
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
            data: data,
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
                {xAxis: 999.4},

                {yAxis: -1},
                {yAxis: -0.5},
                {yAxis: 0},
                {yAxis: 0.5},
                {yAxis: 1},
              ]
            }
          }
        ],
      };
      ecgBc.setOption(option);
      $(window).resize(function () {
        ecgBc.resize();
      });
    },
    II() {
      var data = (JSON.parse(sessionStorage.getItem(this.pId + "data"))).result.data.II
      var x = new Array();
      for (var i = 0; i < data.length; i++) {
        x.push(i);
      }
      var ecgBc = echarts.init(document.getElementById("II"));
      var option = {
        title: {
          text: "II",
          top: 30,
          left: 20,
        },
        grid: {
          left: '5',
          right: '1',
          top: '0',
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
              color: ['pink'], //网格的颜色
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
              color: ['pink'], //网格的颜色
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
            data: data,
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
                {xAxis: 999.4},

                {yAxis: -1},
                {yAxis: -0.5},
                {yAxis: 0},
                {yAxis: 0.5},
                {yAxis: 1},
              ]
            }

          }
        ],

      };
      ecgBc.setOption(option);
      $(window).resize(function () {
        ecgBc.resize();
      });
    },
    II1() {
      var data = (JSON.parse(sessionStorage.getItem(this.pId + "data"))).result.data.II
      var x = new Array();
      for (var i = 0; i < data.length; i++) {
        x.push(i);
      }
      var ecgBc = echarts.init(document.getElementById("II1"));
      var option = {
        title: {
          text: "II",
          top: 30,
          left: 20,
        },
        grid: {
          left: '5',
          right: '1',
          top: '0',
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
              color: ['pink'], //网格的颜色
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
              color: ['pink'], //网格的颜色
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
            data: data,
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
                {xAxis: 999.4},

                {yAxis: -1},
                {yAxis: -0.5},
                {yAxis: 0},
                {yAxis: 0.5},
                {yAxis: 1},
              ]
            }

          }
        ],

      };
      ecgBc.setOption(option);
      $(window).resize(function () {
        ecgBc.resize();
      });
    },
    III() {
      var data = (JSON.parse(sessionStorage.getItem(this.pId + "data"))).result.data.III
      var x = new Array();
      for (var i = 0; i < data.length; i++) {
        x.push(i);
      }
      var ecgBc = echarts.init(document.getElementById("III"));
      var option = {
        title: {
          text: "III",
          top: 30,
          left: 20,
        },
        grid: {
          left: '5',
          right: '1',
          top: '0',
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
              color: ['pink'], //网格的颜色
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
              color: ['pink'], //网格的颜色
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
            data: data,
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
                {xAxis: 999.4},

                {yAxis: -1},
                {yAxis: -0.5},
                {yAxis: 0},
                {yAxis: 0.5},
                {yAxis: 1},
              ]
            }
          }
        ],

      };
      ecgBc.setOption(option);
      $(window).resize(function () {
        ecgBc.resize();
      });
    },
    III1() {
      var data = (JSON.parse(sessionStorage.getItem(this.pId + "data"))).result.data.III
      var x = new Array();
      for (var i = 0; i < data.length; i++) {
        x.push(i);
      }
      var ecgBc = echarts.init(document.getElementById("III1"));
      var option = {
        title: {
          text: "III",
          top: 30,
          left: 20,
        },
        grid: {
          left: '5',
          right: '1',
          top: '0',
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
              color: ['pink'], //网格的颜色
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
              color: ['pink'], //网格的颜色
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
            data: data,
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
                {xAxis: 999.4},

                {yAxis: -1},
                {yAxis: -0.5},
                {yAxis: 0},
                {yAxis: 0.5},
                {yAxis: 1},
              ]
            }
          }
        ],

      };
      ecgBc.setOption(option);
      $(window).resize(function () {
        ecgBc.resize();
      });
    },
    aVR() {
      var data = (JSON.parse(sessionStorage.getItem(this.pId + "data"))).result.data.aVR
      var x = new Array();
      for (var i = 0; i < data.length; i++) {
        x.push(i);
      }
      var ecgBc = echarts.init(document.getElementById("aVR"));
      var option = {
        title: {
          text: "aVR",
          top: 30,
          left: 20,
        },
        grid: {
          left: '5',
          right: '1',
          top: '0',
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
              color: ['pink'], //网格的颜色
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
              color: ['pink'], //网格的颜色
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
            data: data,
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
                {xAxis: 999.4},

                {yAxis: -1},
                {yAxis: -0.5},
                {yAxis: 0},
                {yAxis: 0.5},
                {yAxis: 1},
              ]
            }
          }
        ],

      };
      ecgBc.setOption(option);
      $(window).resize(function () {
        ecgBc.resize();
      });
    },
    aVR1() {
      var data = (JSON.parse(sessionStorage.getItem(this.pId + "data"))).result.data.aVR
      var x = new Array();
      for (var i = 0; i < data.length; i++) {
        x.push(i);
      }
      var ecgBc = echarts.init(document.getElementById("aVR1"));
      var option = {
        title: {
          text: "aVR",
          top: 30,
          left: 20,
        },
        grid: {
          left: '5',
          right: '1',
          top: '0',
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
              color: ['pink'], //网格的颜色
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
              color: ['pink'], //网格的颜色
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
            data: data,
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
                {xAxis: 999.4},

                {yAxis: -1},
                {yAxis: -0.5},
                {yAxis: 0},
                {yAxis: 0.5},
                {yAxis: 1},
              ]
            }
          }
        ],

      };
      ecgBc.setOption(option);
      $(window).resize(function () {
        ecgBc.resize();
      });
    },
    aVL() {
      var data = (JSON.parse(sessionStorage.getItem(this.pId + "data"))).result.data.aVL
      var x = new Array();
      for (var i = 0; i < data.length; i++) {
        x.push(i);
      }
      var ecgBc = echarts.init(document.getElementById("aVL"));
      var option = {
        title: {
          text: "aVL",
          top: 30,
          left: 20,
        },
        grid: {
          left: '5',
          right: '1',
          top: '0',
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
              color: ['pink'], //网格的颜色
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
              color: ['pink'], //网格的颜色
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
            data: data,
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
                {xAxis: 999.4},

                {yAxis: -1},
                {yAxis: -0.5},
                {yAxis: 0},
                {yAxis: 0.5},
                {yAxis: 1},
              ]
            }
          }
        ],

      };
      ecgBc.setOption(option);
      $(window).resize(function () {
        ecgBc.resize();
      });
    },
    aVL1() {
      var data = (JSON.parse(sessionStorage.getItem(this.pId + "data"))).result.data.aVL
      var x = new Array();
      for (var i = 0; i < data.length; i++) {
        x.push(i);
      }
      var ecgBc = echarts.init(document.getElementById("aVL1"));
      var option = {
        title: {
          text: "aVL",
          top: 30,
          left: 20,
        },
        grid: {
          left: '5',
          right: '1',
          top: '0',
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
              color: ['pink'], //网格的颜色
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
              color: ['pink'], //网格的颜色
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
            data: data,
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
                {xAxis: 999.4},

                {yAxis: -1},
                {yAxis: -0.5},
                {yAxis: 0},
                {yAxis: 0.5},
                {yAxis: 1},
              ]
            }
          }
        ],

      };
      ecgBc.setOption(option);
      $(window).resize(function () {
        ecgBc.resize();
      });
    },
    aVF() {
      var data = (JSON.parse(sessionStorage.getItem(this.pId + "data"))).result.data.aVF
      var x = new Array();
      for (var i = 0; i < data.length; i++) {
        x.push(i);
      }
      var ecgBc = echarts.init(document.getElementById("aVF"));
      var option = {
        title: {
          text: "aVF",
          top: 30,
          left: 20,
        },
        grid: {
          left: '5',
          right: '1',
          top: '0',
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
              color: ['pink'], //网格的颜色
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
              color: ['pink'], //网格的颜色
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
            data: data,
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
                {xAxis: 999.4},

                {yAxis: -1},
                {yAxis: -0.5},
                {yAxis: 0},
                {yAxis: 0.5},
                {yAxis: 1},
              ]
            }
          }
        ],

      };
      ecgBc.setOption(option);
      $(window).resize(function () {
        ecgBc.resize();
      });
    },
    aVF1() {
      var data = (JSON.parse(sessionStorage.getItem(this.pId + "data"))).result.data.aVF
      var x = new Array();
      for (var i = 0; i < data.length; i++) {
        x.push(i);
      }
      var ecgBc = echarts.init(document.getElementById("aVF1"));
      var option = {
        title: {
          text: "aVF",
          top: 30,
          left: 20,
        },
        grid: {
          left: '5',
          right: '1',
          top: '0',
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
              color: ['pink'], //网格的颜色
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
              color: ['pink'], //网格的颜色
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
            data: data,
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
                {xAxis: 999.4},

                {yAxis: -1},
                {yAxis: -0.5},
                {yAxis: 0},
                {yAxis: 0.5},
                {yAxis: 1},
              ]
            }
          }
        ],

      };
      ecgBc.setOption(option);
      $(window).resize(function () {
        ecgBc.resize();
      });
    },
    V1() {
      var data = (JSON.parse(sessionStorage.getItem(this.pId + "data"))).result.data.V1
      var x = new Array();
      for (var i = 0; i < data.length; i++) {
        x.push(i);
      }
      var ecgBc = echarts.init(document.getElementById("V1"));
      var option = {
        title: {
          text: "V1",
          top: 30,
          left: 20,
        },
        grid: {
          left: '5',
          right: '1',
          top: '0',
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
              color: ['pink'], //网格的颜色
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
              color: ['pink'], //网格的颜色
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
            data: data,
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
                {xAxis: 999.4},

                {yAxis: -1},
                {yAxis: -0.5},
                {yAxis: 0},
                {yAxis: 0.5},
                {yAxis: 1},
              ]
            }
          }
        ],
      };
      ecgBc.setOption(option);
      $(window).resize(function () {
        ecgBc.resize();
      });
    },
    V11() {
      var data = (JSON.parse(sessionStorage.getItem(this.pId + "data"))).result.data.V1
      var x = new Array();
      for (var i = 0; i < data.length; i++) {
        x.push(i);
      }
      var ecgBc = echarts.init(document.getElementById("V11"));
      var option = {
        title: {
          text: "V1",
          top: 30,
          left: 20,
        },
        grid: {
          left: '5',
          right: '1',
          top: '0',
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
              color: ['pink'], //网格的颜色
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
              color: ['pink'], //网格的颜色
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
            data: data,
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
                {xAxis: 999.4},

                {yAxis: -1},
                {yAxis: -0.5},
                {yAxis: 0},
                {yAxis: 0.5},
                {yAxis: 1},
              ]
            }
          }
        ],
      };
      ecgBc.setOption(option);
      $(window).resize(function () {
        ecgBc.resize();
      });
    },
    V2() {
      var data = (JSON.parse(sessionStorage.getItem(this.pId + "data"))).result.data.V2
      var x = new Array();
      for (var i = 0; i < data.length; i++) {
        x.push(i);
      }
      var ecgBc = echarts.init(document.getElementById("V2"));
      var option = {
        title: {
          text: "V2",
          top: 30,
          left: 20,
        },
        grid: {
          left: '5',
          right: '1',
          top: '0',
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
              color: ['pink'], //网格的颜色
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
              color: ['pink'], //网格的颜色
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
            data: data,
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
                {xAxis: 999.4},

                {yAxis: -1},
                {yAxis: -0.5},
                {yAxis: 0},
                {yAxis: 0.5},
                {yAxis: 1},
              ]
            }
          }
        ],
      };
      ecgBc.setOption(option);
      $(window).resize(function () {
        ecgBc.resize();
      });
    },
    V22() {
      var data = (JSON.parse(sessionStorage.getItem(this.pId + "data"))).result.data.V2
      var x = new Array();
      for (var i = 0; i < data.length; i++) {
        x.push(i);
      }
      var ecgBc = echarts.init(document.getElementById("V22"));
      var option = {
        title: {
          text: "V2",
          top: 30,
          left: 20,
        },
        grid: {
          left: '5',
          right: '1',
          top: '0',
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
              color: ['pink'], //网格的颜色
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
              color: ['pink'], //网格的颜色
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
            data: data,
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
                {xAxis: 999.4},

                {yAxis: -1},
                {yAxis: -0.5},
                {yAxis: 0},
                {yAxis: 0.5},
                {yAxis: 1},
              ]
            }
          }
        ],
      };
      ecgBc.setOption(option);
      $(window).resize(function () {
        ecgBc.resize();
      });
    },
    V3() {
      var data = (JSON.parse(sessionStorage.getItem(this.pId + "data"))).result.data.V3
      var x = new Array();
      for (var i = 0; i < data.length; i++) {
        x.push(i);
      }
      var ecgBc = echarts.init(document.getElementById("V3"));
      var option = {
        title: {
          text: "V3",
          top: 30,
          left: 20,
        },
        grid: {
          left: '5',
          right: '1',
          top: '0',
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
              color: ['pink'], //网格的颜色
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
              color: ['pink'], //网格的颜色
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
            data: data,
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
                {xAxis: 999.4},

                {yAxis: -1},
                {yAxis: -0.5},
                {yAxis: 0},
                {yAxis: 0.5},
                {yAxis: 1},
              ]
            }
          }
        ],
      };
      ecgBc.setOption(option);
      $(window).resize(function () {
        ecgBc.resize();
      });
    },
    V33() {
      var data = (JSON.parse(sessionStorage.getItem(this.pId + "data"))).result.data.V3
      var x = new Array();
      for (var i = 0; i < data.length; i++) {
        x.push(i);
      }
      var ecgBc = echarts.init(document.getElementById("V33"));
      var option = {
        title: {
          text: "V3",
          top: 30,
          left: 20,
        },
        grid: {
          left: '5',
          right: '1',
          top: '0',
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
              color: ['pink'], //网格的颜色
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
              color: ['pink'], //网格的颜色
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
            data: data,
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
                {xAxis: 999.4},

                {yAxis: -1},
                {yAxis: -0.5},
                {yAxis: 0},
                {yAxis: 0.5},
                {yAxis: 1},
              ]
            }
          }
        ],
      };
      ecgBc.setOption(option);
      $(window).resize(function () {
        ecgBc.resize();
      });
    },
    V4() {
      var data = (JSON.parse(sessionStorage.getItem(this.pId + "data"))).result.data.V4
      var x = new Array();
      for (var i = 0; i < data.length; i++) {
        x.push(i);
      }
      var ecgBc = echarts.init(document.getElementById("V4"));
      var option = {
        title: {
          text: "V4",
          top: 30,
          left: 20,
        },
        grid: {
          left: '5',
          right: '1',
          top: '0',
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
              color: ['pink'], //网格的颜色
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
              color: ['pink'], //网格的颜色
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
            data: data,
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
                {xAxis: 999.4},

                {yAxis: -1},
                {yAxis: -0.5},
                {yAxis: 0},
                {yAxis: 0.5},
                {yAxis: 1},
              ]
            }
          }
        ],
      };
      ecgBc.setOption(option);
      $(window).resize(function () {
        ecgBc.resize();
      });
    },
    V44() {
      var data = (JSON.parse(sessionStorage.getItem(this.pId + "data"))).result.data.V4
      var x = new Array();
      for (var i = 0; i < data.length; i++) {
        x.push(i);
      }
      var ecgBc = echarts.init(document.getElementById("V44"));
      var option = {
        title: {
          text: "V4",
          top: 30,
          left: 20,
        },
        grid: {
          left: '5',
          right: '1',
          top: '0',
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
              color: ['pink'], //网格的颜色
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
              color: ['pink'], //网格的颜色
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
            data: data,
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
                {xAxis: 999.4},

                {yAxis: -1},
                {yAxis: -0.5},
                {yAxis: 0},
                {yAxis: 0.5},
                {yAxis: 1},
              ]
            }
          }
        ],
      };
      ecgBc.setOption(option);
      $(window).resize(function () {
        ecgBc.resize();
      });
    },
    V5() {
      var data = (JSON.parse(sessionStorage.getItem(this.pId + "data"))).result.data.V5
      var x = new Array();
      for (var i = 0; i < data.length; i++) {
        x.push(i);
      }
      var ecgBc = echarts.init(document.getElementById("V5"));
      var option = {
        title: {
          text: "V5",
          top: 30,
          left: 20,
        },
        grid: {
          left: '5',
          right: '1',
          top: '0',
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
              color: ['pink'], //网格的颜色
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
              color: ['pink'], //网格的颜色
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
            data: data,
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
                {xAxis: 999.4},

                {yAxis: -1},
                {yAxis: -0.5},
                {yAxis: 0},
                {yAxis: 0.5},
                {yAxis: 1},
              ]
            }
          }
        ],
      };
      ecgBc.setOption(option);
      $(window).resize(function () {
        ecgBc.resize();
      });
    },
    V55() {
      var data = (JSON.parse(sessionStorage.getItem(this.pId + "data"))).result.data.V5
      var x = new Array();
      for (var i = 0; i < data.length; i++) {
        x.push(i);
      }
      var ecgBc = echarts.init(document.getElementById("V55"));
      var option = {
        title: {
          text: "V5",
          top: 30,
          left: 20,
        },
        grid: {
          left: '5',
          right: '1',
          top: '0',
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
              color: ['pink'], //网格的颜色
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
              color: ['pink'], //网格的颜色
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
            data: data,
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
                {xAxis: 999.4},

                {yAxis: -1},
                {yAxis: -0.5},
                {yAxis: 0},
                {yAxis: 0.5},
                {yAxis: 1},
              ]
            }
          }
        ],
      };
      ecgBc.setOption(option);
      $(window).resize(function () {
        ecgBc.resize();
      });
    },
    V6() {
      var data = (JSON.parse(sessionStorage.getItem(this.pId + "data"))).result.data.V6
      var x = new Array();
      for (var i = 0; i < data.length; i++) {
        x.push(i);
      }
      var ecgBc = echarts.init(document.getElementById("V6"));
      var option = {
        title: {
          text: "V6",
          top: 30,
          left: 20,
        },
        grid: {
          left: '5',
          right: '1',
          top: '0',
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
              color: ['pink'], //网格的颜色
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
              color: ['pink'], //网格的颜色
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
            data: data,
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
                {xAxis: 999.4},

                {yAxis: -1},
                {yAxis: -0.5},
                {yAxis: 0},
                {yAxis: 0.5},
                {yAxis: 1},
              ]
            }
          }
        ],

      };
      ecgBc.setOption(option);
      $(window).resize(function () {
        ecgBc.resize();
      });
    },
    V66() {
      var data = (JSON.parse(sessionStorage.getItem(this.pId + "data"))).result.data.V6
      var x = new Array();
      for (var i = 0; i < data.length; i++) {
        x.push(i);
      }
      var ecgBc = echarts.init(document.getElementById("V66"));
      var option = {
        title: {
          text: "V6",
          top: 30,
          left: 20,
        },
        grid: {
          left: '5',
          right: '1',
          top: '0',
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
              color: ['pink'], //网格的颜色
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
              color: ['pink'], //网格的颜色
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
            data: data,
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
                {xAxis: 999.4},

                {yAxis: -1},
                {yAxis: -0.5},
                {yAxis: 0},
                {yAxis: 0.5},
                {yAxis: 1},
              ]
            }
          }
        ],

      };
      ecgBc.setOption(option);
      $(window).resize(function () {
        ecgBc.resize();
      });
    },
    btnClick() {
      var _self = this
      // 当下载pdf时，若不在页面顶部会造成PDF样式不对,所以先回到页面顶部再下载
      let top = document.getElementById('pdfDom');
      if (top != null) {
        top.scrollIntoView();
        top = null;
      }
      let title = this.exportPDFtitle;
      html2Canvas(document.querySelector('#pdfDom'), {
        allowTaint: true
      }).then(function (canvas) {
        // 获取canvas画布的宽高
        let contentWidth = canvas.width;
        let contentHeight = canvas.height;
        // 一页pdf显示html页面生成的canvas高度;
        let pageHeight = contentWidth / 841.89 * 592.28;
        // 未生成pdf的html页面高度
        let leftHeight = contentHeight;
        // 页面偏移
        let position = 0;
        // html页面生成的canvas在pdf中图片的宽高（本例为：横向a4纸[841.89,592.28]，纵向需调换尺寸）
        let imgWidth = 841.89;
        let imgHeight = 841.89 / contentWidth * contentHeight;
        let pageData = canvas.toDataURL('image/jpeg', 1.0);
        let PDF = new JsPDF('l', 'pt', 'a4');
        // 两个高度需要区分: 一个是html页面的实际高度，和生成pdf的页面高度
        // 当内容未超过pdf一页显示的范围，无需分页
        if (leftHeight < pageHeight) {
          PDF.addImage(pageData, 'JPEG', 0, 0, imgWidth, imgHeight)
        } else {
          while (leftHeight > 0) {
            PDF.addImage(pageData, 'JPEG', 0, position, imgWidth, imgHeight)
            leftHeight -= pageHeight;
            position -= 592.28;
            // 避免添加空白页
            if (leftHeight > 0) {
              PDF.addPage();
            }
          }
        }
        PDF.save(title + '.pdf');
        /*        // 将pdf输入为base格式的字符串
                var buffer = PDF.output("datauristring")
                // 将base64格式的字符串转换为file文件
                var myfile = _self.dataURLtoFile(buffer, title + ".pdf")
                name = _self.upload_pdf(myfile)*/
      });
      sessionStorage.removeItem(this.$route.query.pId + 'data');
      sessionStorage.removeItem(this.$route.query.pId + 'show');
    },

    btnClickPDF() {
      var _self = this
      // 当下载pdf时，若不在页面顶部会造成PDF样式不对,所以先回到页面顶部再下载
      let top = document.getElementById("pdfDom");
      if (top != null) {
        top.scrollIntoView();
        top = null;
      }
      let title = this.exportPDFtitle;
      html2Canvas(document.querySelector("#pdfDom"), {
        //清晰度，越大越好
        scale: 2,
        allowTaint: true
      }).then(function (canvas) {
        // 获取canvas画布的宽高
        let contentWidth = canvas.width;
        let contentHeight = canvas.height;
        // 一页pdf显示html页面生成的canvas高度;
        let pageHeight = (contentWidth / 592.28) * 841.89;
        // 未生成pdf的html页面高度
        let leftHeight = contentHeight;
        // 页面偏移
        let position = 0;
        // html页面生成的canvas在pdf中图片的宽高（本例为：横向a4纸[841.89,592.28]，纵向需调换尺寸）
        let imgWidth = 592.28;
        let imgHeight = (592.28 / contentWidth) * contentHeight;
        let pageData = canvas.toDataURL("image/jpeg", 1.0);
        let PDF = new JsPDF("", "pt", "a4");
        // 两个高度需要区分: 一个是html页面的实际高度，和生成pdf的页面高度
        // 当内容未超过pdf一页显示的范围，无需分页
        if (leftHeight < pageHeight) {
          PDF.addImage(pageData, "JPEG", 0, 0, imgWidth, imgHeight);
        } else {
          while (leftHeight > 0) {
            PDF.addImage(pageData, "JPEG", 0, position, imgWidth, imgHeight);
            leftHeight -= pageHeight;
            position -= 841.89;
            // 避免添加空白页
            if (leftHeight > 0) {
              PDF.addPage();
            }
          }
        }
        // PDF.save(title + ".pdf");
        // 将pdf输入为base格式的字符串
        var buffer = PDF.output("datauristring")
        // 将base64格式的字符串转换为file文件
        var myfile = _self.dataURLtoFile(buffer, title + ".pdf")
        name = _self.upload_pdf(myfile)
      });
      sessionStorage.removeItem(this.$route.query.pId + 'data');
      sessionStorage.removeItem(this.$route.query.pId + 'show');
    },
    //上传pdf
    upload_pdf(file) {
      // var url ='';
      var formdata = new FormData()
      formdata.append("file", file); // 文件对象
      console.log("上传pdf-1")
      //多个参数的情况
      // formdata.append("name", name);
      var msg = '';
      // 之后ajax传递数据
      pdfDownload2(formdata).then(res => {
        console.log(res);
        if (res.code === 200) {
          this.$modal.msgSuccess("上传成功");
        } else {
          this.$modal.msgError("上传失败");
        }
      })
    },
    //将base64转换为文件对象
    dataURLtoFile(dataurl, filename) {
      var arr = dataurl.split(',');
      var mime = arr[0].match(/:(.*?);/)[1];
      var bstr = atob(arr[1]);
      var n = bstr.length;
      var u8arr = new Uint8Array(n);
      while (n--) {
        u8arr[n] = bstr.charCodeAt(n);
      }
      //转换成file对象
      return new File([u8arr], filename, {type: mime});
      //转换成成blob对象
      //return new Blob([u8arr],{type:mime});
    },

    getDate() {
      var str = new Date();
      this.data.dataTime = str.getFullYear() + "-"
        + (str.getMonth() + 1) + "-" + str.getDate();
      console.log(this.dataTime);
    },
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
  padding-top: 50px;
  padding-bottom: 50px;
  margin-top: 10px;
  margin-bottom: 10px;
  width: 100%;
  //heigth:1220px;
  height: 1000px;
  //border: 3px solid #0000ff;
}

.header {
  display: flex;
  justify-content: space-between;
  font-size: 20px;
  margin-bottom: 3vh;
}

.header-left {
  display: flex;
  flex: 1;
}

.header-1 {
  flex: 1;
  float: left;
  padding-left: 3vw;
}

.header-1 .header-1-1 {
  height: 30px;
}

.box-1-1 {
  wrap: "physical";
  //resize:none;
  overflow-y: auto;
  width: 99%;
  height: 80px;
  font-size: 20px;
  border: none;
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
  position: relative;
  top: 0;
  bottom: 0px;
  right: 3px;
  margin-top: 3px;
  margin-bottom: 1px;
  text-align: right;
  font-size: 20px;
}

.box8-2 {
  display: inline-block;
  width: 150px;
  height: 20px;
  border: none;
  font-size: 20px;
  text-align: center;
  border-bottom: 1px solid black;
  margin-right: 10px;
  margin-top: 20px;
  outline: none

}

.line {
  height: 100px;
  width: 98%;
  margin: 0;
  padding: 0;

}

.lineI{
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

.lineshow{
  height: 200px;
  width: 78em;
  max-width: 97%;
  margin-left: 1.5em;
  margin-top: 1.5em;
  padding: 0
}

.btn1{
  position: relative;
  float: right;
  bottom: 1.6em;
  right: 1.3em;
  z-index: 2;
}
</style>
