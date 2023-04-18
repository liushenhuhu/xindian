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
                    class="font">{{data.resultByDoctor}}
                  </el-input>
                </div>
              </div>
            </div>
          </div>
          <div style="padding-left: 2vw;font-size: 1vw">10mm/mV 25mm/s</div>
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
            <div class="body-1">
              <div>
                <div id="V1" class="line" @dblclick="clicktrueV1"></div>
              </div>
              <div>
                <div id="V2" class="line" @dblclick="clicktrueV2"></div>
              </div>
              <div>
                <div id="V3" class="line" @dblclick="clicktrueV3"></div>
              </div>
              <div>
                <div id="V4" class="line" @dblclick="clicktrueV4"></div>
              </div>
              <div>
                <div id="V5" class="line" @dblclick="clicktrueV5"></div>
              </div>
              <div>
                <div id="V6" class="line" @dblclick="clicktrueV6"></div>
              </div>

            </div>
          </div>
          <div class="bottom">
            <div class="bottom-left">
              <strong>医师:</strong>
              <el-input v-model="data.doctorName" clearable
                        :style="{width: '15%',border: '1px solid black',}">{{data.doctorName}}</el-input>
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
        <button @click="clickclose" class="noName">关闭
        </button>
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
      <div class="lineI" v-show="openV1">
        <div id="V11" class="lineshow"></div>
        <button @click="clickclose" class="noName">关闭</button>
      </div>
      <div class="lineI" v-show="openV2">
        <div id="V22" class="lineshow"></div>
        <button @click="clickclose" class="noName">关闭</button>
      </div>
      <div class="lineI" v-show="openV3">
        <div id="V33" class="lineshow"></div>
        <button @click="clickclose" class="noName">关闭</button>
      </div>
      <div class="lineI" v-show="openV4">
        <div id="V44" class="lineshow"></div>
        <button @click="clickclose" class="noName">关闭</button>
      </div>
      <div class="lineI" v-show="openV5">
        <div id="V55" class="lineshow"></div>
        <button @click="clickclose" class="noName">关闭</button>
      </div>
      <div class="lineI" v-show="openV6">
        <div id="V66" class="lineshow"></div>
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
  name: "index",
  data() {
    return {
      pId: null,
      data: {
        name: "",
        gender: "",
        age: "",
        result: "",
        hr: "",
        pr: "",
        qrs: "",
        qt: "",
        qtc: "",
        p: "",
        qrs_deg: "",
        t: "",
        pv5: "",
        sv1: "",
        rv5_sv1: "",
        resultByDoctor: "",
        dataTime: "",
        doctorName: "",
        diagnosisData: null,
      },
      data12: {
        x: [],
        dataI: [],
        dataII: [],
        dataIII: [],
        dataaVR: [],
        dataaVL: [],
        dataaVF: [],
        dataV1: [],
        dataV2: [],
        dataV3: [],
        dataV4: [],
        dataV5: [],
        dataV6: [],
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
        this.data.resultByDoctor = response.diagnosisConclusion
        this.data.doctorName = response.diagnosisDoctor
        this.data.diagnosisData = response.reportTime
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

  },
  methods: {
    //展开框
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
    clicktrueV1() {
      this.openV1 = !this.openV1;
    },
    clicktrueV2() {
      this.openV2 = !this.openV2;
    },
    clicktrueV3() {
      this.openV3 = !this.openV3;
    },
    clicktrueV4() {
      this.openV4 = !this.openV4;
    },
    clicktrueV5() {
      this.openV5 = !this.openV5;
    },
    clicktrueV6() {
      this.openV6 = !this.openV6;
    },
    //关闭放大框
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
    //请求数据
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
      this.data.dataTime = this.$options.methods.getData();
      $.ajax({
        type: "post",
        url: "https://screen.mindyard.cn:84/get_jecg_12",
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
          loading.close()
          _th.data.resultByDoctor=data.result.diagnosis_conclusion
          _th.data.doctorName=data.result.diagnosis_doctor
          _th.data.age = data.result.age
          _th.data.gender = data.result.gender
          _th.data.name = data.result.patientName
          _th.data.result = data.result.intelligent_diagnosis
          _th.data.hr = data.result.ecg_analysis_data["平均心率"]
          _th.data.pr = data.result.ecg_analysis_data["PR_dis_avg"]
          _th.data.qrs = data.result.ecg_analysis_data["QRS_dis_avg"]
          _th.data.qt = data.result.ecg_analysis_data["QT_dis_avg"]
          _th.data.qtc = data.result.ecg_analysis_data["QTc2"]
          _th.data.p = data.result.ecg_analysis_data["P_deg"]
          _th.data.qrs_deg = data.result.ecg_analysis_data["QRS_deg"]
          _th.data.t = data.result.ecg_analysis_data["T_deg"]
          _th.data.pv5 = data.result.ecg_analysis_data["PV5_mv"]
          _th.data.sv1 = data.result.ecg_analysis_data["SV1_mv"]
          _th.data.rv5_sv1 = data.result.ecg_analysis_data["RV5_SV1"]
          _th.data12.dataI = data.result.I
          _th.data12.dataII = data.result.II
          _th.data12.dataIII = data.result.III
          _th.data12.dataaVR = data.result.aVR
          _th.data12.dataaVL = data.result.aVL
          _th.data12.dataaVF = data.result.aVF
          _th.data12.dataV1 = data.result.V1
          _th.data12.dataV2 = data.result.V2
          _th.data12.dataV3 = data.result.V3
          _th.data12.dataV4 = data.result.V4
          _th.data12.dataV5 = data.result.V5
          _th.data12.dataV6 = data.result.V6

          for (var i = 0; i < 1000; i++) {
            _th.data12.x.push(i);
          }
          var chartI = echarts.init(document.getElementById("I"));
          chartI.clear()
          chartI.setOption({
            title: {
              text: "I",
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
              data: _th.data12.x,
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
                data: _th.data12.dataI,
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
              text: "II",
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
              data: _th.data12.x,
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
                data: _th.data12.dataII,
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
                    {xAxis: 2025},
                    {xAxis: 2050},
                    {xAxis: 2075},
                    {xAxis: 2100},
                    {xAxis: 2125},
                    {xAxis: 2150},
                    {xAxis: 2175},
                    {xAxis: 2200},
                    {xAxis: 2225},
                    {xAxis: 2250},
                    {xAxis: 2275},
                    {xAxis: 2300},
                    {xAxis: 2325},
                    {xAxis: 2350},
                    {xAxis: 2375},
                    {xAxis: 2400},
                    {xAxis: 2425},
                    {xAxis: 2450},
                    {xAxis: 2475},
                    {xAxis: 2500},
                    {xAxis: 2525},
                    {xAxis: 2550},
                    {xAxis: 2575},
                    {xAxis: 2600},
                    {xAxis: 2625},
                    {xAxis: 2650},
                    {xAxis: 2675},
                    {xAxis: 2700},
                    {xAxis: 2725},
                    {xAxis: 2750},
                    {xAxis: 2775},
                    {xAxis: 2800},
                    {xAxis: 2825},
                    {xAxis: 2850},
                    {xAxis: 2875},
                    {xAxis: 2900},
                    {xAxis: 2925},
                    {xAxis: 2950},
                    {xAxis: 2975},

                    {xAxis: 3000},
                    {xAxis: 3025},
                    {xAxis: 3050},
                    {xAxis: 3075},
                    {xAxis: 3100},
                    {xAxis: 3125},
                    {xAxis: 3150},
                    {xAxis: 3175},
                    {xAxis: 3200},
                    {xAxis: 3225},
                    {xAxis: 3250},
                    {xAxis: 3275},
                    {xAxis: 3300},
                    {xAxis: 3325},
                    {xAxis: 3350},
                    {xAxis: 3375},
                    {xAxis: 3400},
                    {xAxis: 3425},
                    {xAxis: 3450},
                    {xAxis: 3475},
                    {xAxis: 3500},
                    {xAxis: 3525},
                    {xAxis: 3550},
                    {xAxis: 3575},
                    {xAxis: 3600},
                    {xAxis: 3625},
                    {xAxis: 3650},
                    {xAxis: 3675},
                    {xAxis: 3700},
                    {xAxis: 3725},
                    {xAxis: 3750},
                    {xAxis: 3775},
                    {xAxis: 3800},
                    {xAxis: 3825},
                    {xAxis: 3850},
                    {xAxis: 3875},
                    {xAxis: 3900},
                    {xAxis: 3925},
                    {xAxis: 3950},
                    {xAxis: 3975},
                    {xAxis: 4000},


                    {xAxis: 4025},
                    {xAxis: 4050},
                    {xAxis: 4075},
                    {xAxis: 4100},
                    {xAxis: 4125},
                    {xAxis: 4150},
                    {xAxis: 4175},
                    {xAxis: 4200},
                    {xAxis: 4225},
                    {xAxis: 4250},
                    {xAxis: 4275},
                    {xAxis: 4300},
                    {xAxis: 4325},
                    {xAxis: 4350},
                    {xAxis: 4375},
                    {xAxis: 4400},
                    {xAxis: 4425},
                    {xAxis: 4450},
                    {xAxis: 4475},
                    {xAxis: 4500},
                    {xAxis: 4525},
                    {xAxis: 4550},
                    {xAxis: 4575},
                    {xAxis: 4600},
                    {xAxis: 4625},
                    {xAxis: 4650},
                    {xAxis: 4675},
                    {xAxis: 4700},
                    {xAxis: 4725},
                    {xAxis: 4750},
                    {xAxis: 4775},
                    {xAxis: 4800},
                    {xAxis: 4825},
                    {xAxis: 4850},
                    {xAxis: 4875},
                    {xAxis: 4900},
                    {xAxis: 4925},
                    {xAxis: 4950},
                    {xAxis: 4975},
                    {xAxis: 5000},

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
              text: "III",
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
              data: _th.data12.x,
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
                data: _th.data12.dataIII,
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
                    {xAxis: 2025},
                    {xAxis: 2050},
                    {xAxis: 2075},
                    {xAxis: 2100},
                    {xAxis: 2125},
                    {xAxis: 2150},
                    {xAxis: 2175},
                    {xAxis: 2200},
                    {xAxis: 2225},
                    {xAxis: 2250},
                    {xAxis: 2275},
                    {xAxis: 2300},
                    {xAxis: 2325},
                    {xAxis: 2350},
                    {xAxis: 2375},
                    {xAxis: 2400},
                    {xAxis: 2425},
                    {xAxis: 2450},
                    {xAxis: 2475},
                    {xAxis: 2500},
                    {xAxis: 2525},
                    {xAxis: 2550},
                    {xAxis: 2575},
                    {xAxis: 2600},
                    {xAxis: 2625},
                    {xAxis: 2650},
                    {xAxis: 2675},
                    {xAxis: 2700},
                    {xAxis: 2725},
                    {xAxis: 2750},
                    {xAxis: 2775},
                    {xAxis: 2800},
                    {xAxis: 2825},
                    {xAxis: 2850},
                    {xAxis: 2875},
                    {xAxis: 2900},
                    {xAxis: 2925},
                    {xAxis: 2950},
                    {xAxis: 2975},

                    {xAxis: 3000},
                    {xAxis: 3025},
                    {xAxis: 3050},
                    {xAxis: 3075},
                    {xAxis: 3100},
                    {xAxis: 3125},
                    {xAxis: 3150},
                    {xAxis: 3175},
                    {xAxis: 3200},
                    {xAxis: 3225},
                    {xAxis: 3250},
                    {xAxis: 3275},
                    {xAxis: 3300},
                    {xAxis: 3325},
                    {xAxis: 3350},
                    {xAxis: 3375},
                    {xAxis: 3400},
                    {xAxis: 3425},
                    {xAxis: 3450},
                    {xAxis: 3475},
                    {xAxis: 3500},
                    {xAxis: 3525},
                    {xAxis: 3550},
                    {xAxis: 3575},
                    {xAxis: 3600},
                    {xAxis: 3625},
                    {xAxis: 3650},
                    {xAxis: 3675},
                    {xAxis: 3700},
                    {xAxis: 3725},
                    {xAxis: 3750},
                    {xAxis: 3775},
                    {xAxis: 3800},
                    {xAxis: 3825},
                    {xAxis: 3850},
                    {xAxis: 3875},
                    {xAxis: 3900},
                    {xAxis: 3925},
                    {xAxis: 3950},
                    {xAxis: 3975},
                    {xAxis: 4000},


                    {xAxis: 4025},
                    {xAxis: 4050},
                    {xAxis: 4075},
                    {xAxis: 4100},
                    {xAxis: 4125},
                    {xAxis: 4150},
                    {xAxis: 4175},
                    {xAxis: 4200},
                    {xAxis: 4225},
                    {xAxis: 4250},
                    {xAxis: 4275},
                    {xAxis: 4300},
                    {xAxis: 4325},
                    {xAxis: 4350},
                    {xAxis: 4375},
                    {xAxis: 4400},
                    {xAxis: 4425},
                    {xAxis: 4450},
                    {xAxis: 4475},
                    {xAxis: 4500},
                    {xAxis: 4525},
                    {xAxis: 4550},
                    {xAxis: 4575},
                    {xAxis: 4600},
                    {xAxis: 4625},
                    {xAxis: 4650},
                    {xAxis: 4675},
                    {xAxis: 4700},
                    {xAxis: 4725},
                    {xAxis: 4750},
                    {xAxis: 4775},
                    {xAxis: 4800},
                    {xAxis: 4825},
                    {xAxis: 4850},
                    {xAxis: 4875},
                    {xAxis: 4900},
                    {xAxis: 4925},
                    {xAxis: 4950},
                    {xAxis: 4975},
                    {xAxis: 5000},

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
              text: "aVR",
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
              data: _th.data12.x,
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
                data: _th.data12.dataaVR,
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
                    {xAxis: 2025},
                    {xAxis: 2050},
                    {xAxis: 2075},
                    {xAxis: 2100},
                    {xAxis: 2125},
                    {xAxis: 2150},
                    {xAxis: 2175},
                    {xAxis: 2200},
                    {xAxis: 2225},
                    {xAxis: 2250},
                    {xAxis: 2275},
                    {xAxis: 2300},
                    {xAxis: 2325},
                    {xAxis: 2350},
                    {xAxis: 2375},
                    {xAxis: 2400},
                    {xAxis: 2425},
                    {xAxis: 2450},
                    {xAxis: 2475},
                    {xAxis: 2500},
                    {xAxis: 2525},
                    {xAxis: 2550},
                    {xAxis: 2575},
                    {xAxis: 2600},
                    {xAxis: 2625},
                    {xAxis: 2650},
                    {xAxis: 2675},
                    {xAxis: 2700},
                    {xAxis: 2725},
                    {xAxis: 2750},
                    {xAxis: 2775},
                    {xAxis: 2800},
                    {xAxis: 2825},
                    {xAxis: 2850},
                    {xAxis: 2875},
                    {xAxis: 2900},
                    {xAxis: 2925},
                    {xAxis: 2950},
                    {xAxis: 2975},

                    {xAxis: 3000},
                    {xAxis: 3025},
                    {xAxis: 3050},
                    {xAxis: 3075},
                    {xAxis: 3100},
                    {xAxis: 3125},
                    {xAxis: 3150},
                    {xAxis: 3175},
                    {xAxis: 3200},
                    {xAxis: 3225},
                    {xAxis: 3250},
                    {xAxis: 3275},
                    {xAxis: 3300},
                    {xAxis: 3325},
                    {xAxis: 3350},
                    {xAxis: 3375},
                    {xAxis: 3400},
                    {xAxis: 3425},
                    {xAxis: 3450},
                    {xAxis: 3475},
                    {xAxis: 3500},
                    {xAxis: 3525},
                    {xAxis: 3550},
                    {xAxis: 3575},
                    {xAxis: 3600},
                    {xAxis: 3625},
                    {xAxis: 3650},
                    {xAxis: 3675},
                    {xAxis: 3700},
                    {xAxis: 3725},
                    {xAxis: 3750},
                    {xAxis: 3775},
                    {xAxis: 3800},
                    {xAxis: 3825},
                    {xAxis: 3850},
                    {xAxis: 3875},
                    {xAxis: 3900},
                    {xAxis: 3925},
                    {xAxis: 3950},
                    {xAxis: 3975},
                    {xAxis: 4000},


                    {xAxis: 4025},
                    {xAxis: 4050},
                    {xAxis: 4075},
                    {xAxis: 4100},
                    {xAxis: 4125},
                    {xAxis: 4150},
                    {xAxis: 4175},
                    {xAxis: 4200},
                    {xAxis: 4225},
                    {xAxis: 4250},
                    {xAxis: 4275},
                    {xAxis: 4300},
                    {xAxis: 4325},
                    {xAxis: 4350},
                    {xAxis: 4375},
                    {xAxis: 4400},
                    {xAxis: 4425},
                    {xAxis: 4450},
                    {xAxis: 4475},
                    {xAxis: 4500},
                    {xAxis: 4525},
                    {xAxis: 4550},
                    {xAxis: 4575},
                    {xAxis: 4600},
                    {xAxis: 4625},
                    {xAxis: 4650},
                    {xAxis: 4675},
                    {xAxis: 4700},
                    {xAxis: 4725},
                    {xAxis: 4750},
                    {xAxis: 4775},
                    {xAxis: 4800},
                    {xAxis: 4825},
                    {xAxis: 4850},
                    {xAxis: 4875},
                    {xAxis: 4900},
                    {xAxis: 4925},
                    {xAxis: 4950},
                    {xAxis: 4975},
                    {xAxis: 5000},

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
              text: "aVL",
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
              data: _th.data12.x,
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
                data: _th.data12.dataaVL,
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
                    {xAxis: 2025},
                    {xAxis: 2050},
                    {xAxis: 2075},
                    {xAxis: 2100},
                    {xAxis: 2125},
                    {xAxis: 2150},
                    {xAxis: 2175},
                    {xAxis: 2200},
                    {xAxis: 2225},
                    {xAxis: 2250},
                    {xAxis: 2275},
                    {xAxis: 2300},
                    {xAxis: 2325},
                    {xAxis: 2350},
                    {xAxis: 2375},
                    {xAxis: 2400},
                    {xAxis: 2425},
                    {xAxis: 2450},
                    {xAxis: 2475},
                    {xAxis: 2500},
                    {xAxis: 2525},
                    {xAxis: 2550},
                    {xAxis: 2575},
                    {xAxis: 2600},
                    {xAxis: 2625},
                    {xAxis: 2650},
                    {xAxis: 2675},
                    {xAxis: 2700},
                    {xAxis: 2725},
                    {xAxis: 2750},
                    {xAxis: 2775},
                    {xAxis: 2800},
                    {xAxis: 2825},
                    {xAxis: 2850},
                    {xAxis: 2875},
                    {xAxis: 2900},
                    {xAxis: 2925},
                    {xAxis: 2950},
                    {xAxis: 2975},

                    {xAxis: 3000},
                    {xAxis: 3025},
                    {xAxis: 3050},
                    {xAxis: 3075},
                    {xAxis: 3100},
                    {xAxis: 3125},
                    {xAxis: 3150},
                    {xAxis: 3175},
                    {xAxis: 3200},
                    {xAxis: 3225},
                    {xAxis: 3250},
                    {xAxis: 3275},
                    {xAxis: 3300},
                    {xAxis: 3325},
                    {xAxis: 3350},
                    {xAxis: 3375},
                    {xAxis: 3400},
                    {xAxis: 3425},
                    {xAxis: 3450},
                    {xAxis: 3475},
                    {xAxis: 3500},
                    {xAxis: 3525},
                    {xAxis: 3550},
                    {xAxis: 3575},
                    {xAxis: 3600},
                    {xAxis: 3625},
                    {xAxis: 3650},
                    {xAxis: 3675},
                    {xAxis: 3700},
                    {xAxis: 3725},
                    {xAxis: 3750},
                    {xAxis: 3775},
                    {xAxis: 3800},
                    {xAxis: 3825},
                    {xAxis: 3850},
                    {xAxis: 3875},
                    {xAxis: 3900},
                    {xAxis: 3925},
                    {xAxis: 3950},
                    {xAxis: 3975},
                    {xAxis: 4000},


                    {xAxis: 4025},
                    {xAxis: 4050},
                    {xAxis: 4075},
                    {xAxis: 4100},
                    {xAxis: 4125},
                    {xAxis: 4150},
                    {xAxis: 4175},
                    {xAxis: 4200},
                    {xAxis: 4225},
                    {xAxis: 4250},
                    {xAxis: 4275},
                    {xAxis: 4300},
                    {xAxis: 4325},
                    {xAxis: 4350},
                    {xAxis: 4375},
                    {xAxis: 4400},
                    {xAxis: 4425},
                    {xAxis: 4450},
                    {xAxis: 4475},
                    {xAxis: 4500},
                    {xAxis: 4525},
                    {xAxis: 4550},
                    {xAxis: 4575},
                    {xAxis: 4600},
                    {xAxis: 4625},
                    {xAxis: 4650},
                    {xAxis: 4675},
                    {xAxis: 4700},
                    {xAxis: 4725},
                    {xAxis: 4750},
                    {xAxis: 4775},
                    {xAxis: 4800},
                    {xAxis: 4825},
                    {xAxis: 4850},
                    {xAxis: 4875},
                    {xAxis: 4900},
                    {xAxis: 4925},
                    {xAxis: 4950},
                    {xAxis: 4975},
                    {xAxis: 5000},

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
              text: "aVF",
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
              data: _th.data12.x,
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
                data: _th.data12.dataaVF,
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
                    {xAxis: 2025},
                    {xAxis: 2050},
                    {xAxis: 2075},
                    {xAxis: 2100},
                    {xAxis: 2125},
                    {xAxis: 2150},
                    {xAxis: 2175},
                    {xAxis: 2200},
                    {xAxis: 2225},
                    {xAxis: 2250},
                    {xAxis: 2275},
                    {xAxis: 2300},
                    {xAxis: 2325},
                    {xAxis: 2350},
                    {xAxis: 2375},
                    {xAxis: 2400},
                    {xAxis: 2425},
                    {xAxis: 2450},
                    {xAxis: 2475},
                    {xAxis: 2500},
                    {xAxis: 2525},
                    {xAxis: 2550},
                    {xAxis: 2575},
                    {xAxis: 2600},
                    {xAxis: 2625},
                    {xAxis: 2650},
                    {xAxis: 2675},
                    {xAxis: 2700},
                    {xAxis: 2725},
                    {xAxis: 2750},
                    {xAxis: 2775},
                    {xAxis: 2800},
                    {xAxis: 2825},
                    {xAxis: 2850},
                    {xAxis: 2875},
                    {xAxis: 2900},
                    {xAxis: 2925},
                    {xAxis: 2950},
                    {xAxis: 2975},

                    {xAxis: 3000},
                    {xAxis: 3025},
                    {xAxis: 3050},
                    {xAxis: 3075},
                    {xAxis: 3100},
                    {xAxis: 3125},
                    {xAxis: 3150},
                    {xAxis: 3175},
                    {xAxis: 3200},
                    {xAxis: 3225},
                    {xAxis: 3250},
                    {xAxis: 3275},
                    {xAxis: 3300},
                    {xAxis: 3325},
                    {xAxis: 3350},
                    {xAxis: 3375},
                    {xAxis: 3400},
                    {xAxis: 3425},
                    {xAxis: 3450},
                    {xAxis: 3475},
                    {xAxis: 3500},
                    {xAxis: 3525},
                    {xAxis: 3550},
                    {xAxis: 3575},
                    {xAxis: 3600},
                    {xAxis: 3625},
                    {xAxis: 3650},
                    {xAxis: 3675},
                    {xAxis: 3700},
                    {xAxis: 3725},
                    {xAxis: 3750},
                    {xAxis: 3775},
                    {xAxis: 3800},
                    {xAxis: 3825},
                    {xAxis: 3850},
                    {xAxis: 3875},
                    {xAxis: 3900},
                    {xAxis: 3925},
                    {xAxis: 3950},
                    {xAxis: 3975},
                    {xAxis: 4000},


                    {xAxis: 4025},
                    {xAxis: 4050},
                    {xAxis: 4075},
                    {xAxis: 4100},
                    {xAxis: 4125},
                    {xAxis: 4150},
                    {xAxis: 4175},
                    {xAxis: 4200},
                    {xAxis: 4225},
                    {xAxis: 4250},
                    {xAxis: 4275},
                    {xAxis: 4300},
                    {xAxis: 4325},
                    {xAxis: 4350},
                    {xAxis: 4375},
                    {xAxis: 4400},
                    {xAxis: 4425},
                    {xAxis: 4450},
                    {xAxis: 4475},
                    {xAxis: 4500},
                    {xAxis: 4525},
                    {xAxis: 4550},
                    {xAxis: 4575},
                    {xAxis: 4600},
                    {xAxis: 4625},
                    {xAxis: 4650},
                    {xAxis: 4675},
                    {xAxis: 4700},
                    {xAxis: 4725},
                    {xAxis: 4750},
                    {xAxis: 4775},
                    {xAxis: 4800},
                    {xAxis: 4825},
                    {xAxis: 4850},
                    {xAxis: 4875},
                    {xAxis: 4900},
                    {xAxis: 4925},
                    {xAxis: 4950},
                    {xAxis: 4975},
                    {xAxis: 5000},

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
          var chartV1 = echarts.init(document.getElementById("V1"));
          chartV1.clear()
          chartV1.setOption({
            title: {
              text: "V1",
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
              data: _th.data12.x,
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
                data: _th.data12.dataV1,
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
                    {xAxis: 2025},
                    {xAxis: 2050},
                    {xAxis: 2075},
                    {xAxis: 2100},
                    {xAxis: 2125},
                    {xAxis: 2150},
                    {xAxis: 2175},
                    {xAxis: 2200},
                    {xAxis: 2225},
                    {xAxis: 2250},
                    {xAxis: 2275},
                    {xAxis: 2300},
                    {xAxis: 2325},
                    {xAxis: 2350},
                    {xAxis: 2375},
                    {xAxis: 2400},
                    {xAxis: 2425},
                    {xAxis: 2450},
                    {xAxis: 2475},
                    {xAxis: 2500},
                    {xAxis: 2525},
                    {xAxis: 2550},
                    {xAxis: 2575},
                    {xAxis: 2600},
                    {xAxis: 2625},
                    {xAxis: 2650},
                    {xAxis: 2675},
                    {xAxis: 2700},
                    {xAxis: 2725},
                    {xAxis: 2750},
                    {xAxis: 2775},
                    {xAxis: 2800},
                    {xAxis: 2825},
                    {xAxis: 2850},
                    {xAxis: 2875},
                    {xAxis: 2900},
                    {xAxis: 2925},
                    {xAxis: 2950},
                    {xAxis: 2975},

                    {xAxis: 3000},
                    {xAxis: 3025},
                    {xAxis: 3050},
                    {xAxis: 3075},
                    {xAxis: 3100},
                    {xAxis: 3125},
                    {xAxis: 3150},
                    {xAxis: 3175},
                    {xAxis: 3200},
                    {xAxis: 3225},
                    {xAxis: 3250},
                    {xAxis: 3275},
                    {xAxis: 3300},
                    {xAxis: 3325},
                    {xAxis: 3350},
                    {xAxis: 3375},
                    {xAxis: 3400},
                    {xAxis: 3425},
                    {xAxis: 3450},
                    {xAxis: 3475},
                    {xAxis: 3500},
                    {xAxis: 3525},
                    {xAxis: 3550},
                    {xAxis: 3575},
                    {xAxis: 3600},
                    {xAxis: 3625},
                    {xAxis: 3650},
                    {xAxis: 3675},
                    {xAxis: 3700},
                    {xAxis: 3725},
                    {xAxis: 3750},
                    {xAxis: 3775},
                    {xAxis: 3800},
                    {xAxis: 3825},
                    {xAxis: 3850},
                    {xAxis: 3875},
                    {xAxis: 3900},
                    {xAxis: 3925},
                    {xAxis: 3950},
                    {xAxis: 3975},
                    {xAxis: 4000},


                    {xAxis: 4025},
                    {xAxis: 4050},
                    {xAxis: 4075},
                    {xAxis: 4100},
                    {xAxis: 4125},
                    {xAxis: 4150},
                    {xAxis: 4175},
                    {xAxis: 4200},
                    {xAxis: 4225},
                    {xAxis: 4250},
                    {xAxis: 4275},
                    {xAxis: 4300},
                    {xAxis: 4325},
                    {xAxis: 4350},
                    {xAxis: 4375},
                    {xAxis: 4400},
                    {xAxis: 4425},
                    {xAxis: 4450},
                    {xAxis: 4475},
                    {xAxis: 4500},
                    {xAxis: 4525},
                    {xAxis: 4550},
                    {xAxis: 4575},
                    {xAxis: 4600},
                    {xAxis: 4625},
                    {xAxis: 4650},
                    {xAxis: 4675},
                    {xAxis: 4700},
                    {xAxis: 4725},
                    {xAxis: 4750},
                    {xAxis: 4775},
                    {xAxis: 4800},
                    {xAxis: 4825},
                    {xAxis: 4850},
                    {xAxis: 4875},
                    {xAxis: 4900},
                    {xAxis: 4925},
                    {xAxis: 4950},
                    {xAxis: 4975},
                    {xAxis: 5000},

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
            chartV1.resize();
          });
          var chartV2 = echarts.init(document.getElementById("V2"));
          chartV2.clear()
          chartV2.setOption({
            title: {
              text: "V2",
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
              data: _th.data12.x,
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
                data: _th.data12.dataV2,
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
                    {xAxis: 2025},
                    {xAxis: 2050},
                    {xAxis: 2075},
                    {xAxis: 2100},
                    {xAxis: 2125},
                    {xAxis: 2150},
                    {xAxis: 2175},
                    {xAxis: 2200},
                    {xAxis: 2225},
                    {xAxis: 2250},
                    {xAxis: 2275},
                    {xAxis: 2300},
                    {xAxis: 2325},
                    {xAxis: 2350},
                    {xAxis: 2375},
                    {xAxis: 2400},
                    {xAxis: 2425},
                    {xAxis: 2450},
                    {xAxis: 2475},
                    {xAxis: 2500},
                    {xAxis: 2525},
                    {xAxis: 2550},
                    {xAxis: 2575},
                    {xAxis: 2600},
                    {xAxis: 2625},
                    {xAxis: 2650},
                    {xAxis: 2675},
                    {xAxis: 2700},
                    {xAxis: 2725},
                    {xAxis: 2750},
                    {xAxis: 2775},
                    {xAxis: 2800},
                    {xAxis: 2825},
                    {xAxis: 2850},
                    {xAxis: 2875},
                    {xAxis: 2900},
                    {xAxis: 2925},
                    {xAxis: 2950},
                    {xAxis: 2975},

                    {xAxis: 3000},
                    {xAxis: 3025},
                    {xAxis: 3050},
                    {xAxis: 3075},
                    {xAxis: 3100},
                    {xAxis: 3125},
                    {xAxis: 3150},
                    {xAxis: 3175},
                    {xAxis: 3200},
                    {xAxis: 3225},
                    {xAxis: 3250},
                    {xAxis: 3275},
                    {xAxis: 3300},
                    {xAxis: 3325},
                    {xAxis: 3350},
                    {xAxis: 3375},
                    {xAxis: 3400},
                    {xAxis: 3425},
                    {xAxis: 3450},
                    {xAxis: 3475},
                    {xAxis: 3500},
                    {xAxis: 3525},
                    {xAxis: 3550},
                    {xAxis: 3575},
                    {xAxis: 3600},
                    {xAxis: 3625},
                    {xAxis: 3650},
                    {xAxis: 3675},
                    {xAxis: 3700},
                    {xAxis: 3725},
                    {xAxis: 3750},
                    {xAxis: 3775},
                    {xAxis: 3800},
                    {xAxis: 3825},
                    {xAxis: 3850},
                    {xAxis: 3875},
                    {xAxis: 3900},
                    {xAxis: 3925},
                    {xAxis: 3950},
                    {xAxis: 3975},
                    {xAxis: 4000},


                    {xAxis: 4025},
                    {xAxis: 4050},
                    {xAxis: 4075},
                    {xAxis: 4100},
                    {xAxis: 4125},
                    {xAxis: 4150},
                    {xAxis: 4175},
                    {xAxis: 4200},
                    {xAxis: 4225},
                    {xAxis: 4250},
                    {xAxis: 4275},
                    {xAxis: 4300},
                    {xAxis: 4325},
                    {xAxis: 4350},
                    {xAxis: 4375},
                    {xAxis: 4400},
                    {xAxis: 4425},
                    {xAxis: 4450},
                    {xAxis: 4475},
                    {xAxis: 4500},
                    {xAxis: 4525},
                    {xAxis: 4550},
                    {xAxis: 4575},
                    {xAxis: 4600},
                    {xAxis: 4625},
                    {xAxis: 4650},
                    {xAxis: 4675},
                    {xAxis: 4700},
                    {xAxis: 4725},
                    {xAxis: 4750},
                    {xAxis: 4775},
                    {xAxis: 4800},
                    {xAxis: 4825},
                    {xAxis: 4850},
                    {xAxis: 4875},
                    {xAxis: 4900},
                    {xAxis: 4925},
                    {xAxis: 4950},
                    {xAxis: 4975},
                    {xAxis: 5000},

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
            chartV2.resize();
          });
          var chartV3 = echarts.init(document.getElementById("V3"));
          chartV3.clear()
          chartV3.setOption({
            title: {
              text: "V3",
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
              data: _th.data12.x,
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
                data: _th.data12.dataV3,
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
                    {xAxis: 2025},
                    {xAxis: 2050},
                    {xAxis: 2075},
                    {xAxis: 2100},
                    {xAxis: 2125},
                    {xAxis: 2150},
                    {xAxis: 2175},
                    {xAxis: 2200},
                    {xAxis: 2225},
                    {xAxis: 2250},
                    {xAxis: 2275},
                    {xAxis: 2300},
                    {xAxis: 2325},
                    {xAxis: 2350},
                    {xAxis: 2375},
                    {xAxis: 2400},
                    {xAxis: 2425},
                    {xAxis: 2450},
                    {xAxis: 2475},
                    {xAxis: 2500},
                    {xAxis: 2525},
                    {xAxis: 2550},
                    {xAxis: 2575},
                    {xAxis: 2600},
                    {xAxis: 2625},
                    {xAxis: 2650},
                    {xAxis: 2675},
                    {xAxis: 2700},
                    {xAxis: 2725},
                    {xAxis: 2750},
                    {xAxis: 2775},
                    {xAxis: 2800},
                    {xAxis: 2825},
                    {xAxis: 2850},
                    {xAxis: 2875},
                    {xAxis: 2900},
                    {xAxis: 2925},
                    {xAxis: 2950},
                    {xAxis: 2975},

                    {xAxis: 3000},
                    {xAxis: 3025},
                    {xAxis: 3050},
                    {xAxis: 3075},
                    {xAxis: 3100},
                    {xAxis: 3125},
                    {xAxis: 3150},
                    {xAxis: 3175},
                    {xAxis: 3200},
                    {xAxis: 3225},
                    {xAxis: 3250},
                    {xAxis: 3275},
                    {xAxis: 3300},
                    {xAxis: 3325},
                    {xAxis: 3350},
                    {xAxis: 3375},
                    {xAxis: 3400},
                    {xAxis: 3425},
                    {xAxis: 3450},
                    {xAxis: 3475},
                    {xAxis: 3500},
                    {xAxis: 3525},
                    {xAxis: 3550},
                    {xAxis: 3575},
                    {xAxis: 3600},
                    {xAxis: 3625},
                    {xAxis: 3650},
                    {xAxis: 3675},
                    {xAxis: 3700},
                    {xAxis: 3725},
                    {xAxis: 3750},
                    {xAxis: 3775},
                    {xAxis: 3800},
                    {xAxis: 3825},
                    {xAxis: 3850},
                    {xAxis: 3875},
                    {xAxis: 3900},
                    {xAxis: 3925},
                    {xAxis: 3950},
                    {xAxis: 3975},
                    {xAxis: 4000},


                    {xAxis: 4025},
                    {xAxis: 4050},
                    {xAxis: 4075},
                    {xAxis: 4100},
                    {xAxis: 4125},
                    {xAxis: 4150},
                    {xAxis: 4175},
                    {xAxis: 4200},
                    {xAxis: 4225},
                    {xAxis: 4250},
                    {xAxis: 4275},
                    {xAxis: 4300},
                    {xAxis: 4325},
                    {xAxis: 4350},
                    {xAxis: 4375},
                    {xAxis: 4400},
                    {xAxis: 4425},
                    {xAxis: 4450},
                    {xAxis: 4475},
                    {xAxis: 4500},
                    {xAxis: 4525},
                    {xAxis: 4550},
                    {xAxis: 4575},
                    {xAxis: 4600},
                    {xAxis: 4625},
                    {xAxis: 4650},
                    {xAxis: 4675},
                    {xAxis: 4700},
                    {xAxis: 4725},
                    {xAxis: 4750},
                    {xAxis: 4775},
                    {xAxis: 4800},
                    {xAxis: 4825},
                    {xAxis: 4850},
                    {xAxis: 4875},
                    {xAxis: 4900},
                    {xAxis: 4925},
                    {xAxis: 4950},
                    {xAxis: 4975},
                    {xAxis: 5000},
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
            chartV3.resize();
          });
          var chartV4 = echarts.init(document.getElementById("V4"));
          chartV4.clear()
          chartV4.setOption({
            title: {
              text: "V4",
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
              data: _th.data12.x,
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
                data: _th.data12.dataV4,
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
                    {xAxis: 2025},
                    {xAxis: 2050},
                    {xAxis: 2075},
                    {xAxis: 2100},
                    {xAxis: 2125},
                    {xAxis: 2150},
                    {xAxis: 2175},
                    {xAxis: 2200},
                    {xAxis: 2225},
                    {xAxis: 2250},
                    {xAxis: 2275},
                    {xAxis: 2300},
                    {xAxis: 2325},
                    {xAxis: 2350},
                    {xAxis: 2375},
                    {xAxis: 2400},
                    {xAxis: 2425},
                    {xAxis: 2450},
                    {xAxis: 2475},
                    {xAxis: 2500},
                    {xAxis: 2525},
                    {xAxis: 2550},
                    {xAxis: 2575},
                    {xAxis: 2600},
                    {xAxis: 2625},
                    {xAxis: 2650},
                    {xAxis: 2675},
                    {xAxis: 2700},
                    {xAxis: 2725},
                    {xAxis: 2750},
                    {xAxis: 2775},
                    {xAxis: 2800},
                    {xAxis: 2825},
                    {xAxis: 2850},
                    {xAxis: 2875},
                    {xAxis: 2900},
                    {xAxis: 2925},
                    {xAxis: 2950},
                    {xAxis: 2975},

                    {xAxis: 3000},
                    {xAxis: 3025},
                    {xAxis: 3050},
                    {xAxis: 3075},
                    {xAxis: 3100},
                    {xAxis: 3125},
                    {xAxis: 3150},
                    {xAxis: 3175},
                    {xAxis: 3200},
                    {xAxis: 3225},
                    {xAxis: 3250},
                    {xAxis: 3275},
                    {xAxis: 3300},
                    {xAxis: 3325},
                    {xAxis: 3350},
                    {xAxis: 3375},
                    {xAxis: 3400},
                    {xAxis: 3425},
                    {xAxis: 3450},
                    {xAxis: 3475},
                    {xAxis: 3500},
                    {xAxis: 3525},
                    {xAxis: 3550},
                    {xAxis: 3575},
                    {xAxis: 3600},
                    {xAxis: 3625},
                    {xAxis: 3650},
                    {xAxis: 3675},
                    {xAxis: 3700},
                    {xAxis: 3725},
                    {xAxis: 3750},
                    {xAxis: 3775},
                    {xAxis: 3800},
                    {xAxis: 3825},
                    {xAxis: 3850},
                    {xAxis: 3875},
                    {xAxis: 3900},
                    {xAxis: 3925},
                    {xAxis: 3950},
                    {xAxis: 3975},
                    {xAxis: 4000},


                    {xAxis: 4025},
                    {xAxis: 4050},
                    {xAxis: 4075},
                    {xAxis: 4100},
                    {xAxis: 4125},
                    {xAxis: 4150},
                    {xAxis: 4175},
                    {xAxis: 4200},
                    {xAxis: 4225},
                    {xAxis: 4250},
                    {xAxis: 4275},
                    {xAxis: 4300},
                    {xAxis: 4325},
                    {xAxis: 4350},
                    {xAxis: 4375},
                    {xAxis: 4400},
                    {xAxis: 4425},
                    {xAxis: 4450},
                    {xAxis: 4475},
                    {xAxis: 4500},
                    {xAxis: 4525},
                    {xAxis: 4550},
                    {xAxis: 4575},
                    {xAxis: 4600},
                    {xAxis: 4625},
                    {xAxis: 4650},
                    {xAxis: 4675},
                    {xAxis: 4700},
                    {xAxis: 4725},
                    {xAxis: 4750},
                    {xAxis: 4775},
                    {xAxis: 4800},
                    {xAxis: 4825},
                    {xAxis: 4850},
                    {xAxis: 4875},
                    {xAxis: 4900},
                    {xAxis: 4925},
                    {xAxis: 4950},
                    {xAxis: 4975},
                    {xAxis: 5000},

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
            chartV4.resize();
          });
          var chartV5 = echarts.init(document.getElementById("V5"));
          chartV5.clear()
          chartV5.setOption({
            title: {
              text: "V5",
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
              data: _th.data12.x,
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
                data: _th.data12.dataV5,
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
                    {xAxis: 2025},
                    {xAxis: 2050},
                    {xAxis: 2075},
                    {xAxis: 2100},
                    {xAxis: 2125},
                    {xAxis: 2150},
                    {xAxis: 2175},
                    {xAxis: 2200},
                    {xAxis: 2225},
                    {xAxis: 2250},
                    {xAxis: 2275},
                    {xAxis: 2300},
                    {xAxis: 2325},
                    {xAxis: 2350},
                    {xAxis: 2375},
                    {xAxis: 2400},
                    {xAxis: 2425},
                    {xAxis: 2450},
                    {xAxis: 2475},
                    {xAxis: 2500},
                    {xAxis: 2525},
                    {xAxis: 2550},
                    {xAxis: 2575},
                    {xAxis: 2600},
                    {xAxis: 2625},
                    {xAxis: 2650},
                    {xAxis: 2675},
                    {xAxis: 2700},
                    {xAxis: 2725},
                    {xAxis: 2750},
                    {xAxis: 2775},
                    {xAxis: 2800},
                    {xAxis: 2825},
                    {xAxis: 2850},
                    {xAxis: 2875},
                    {xAxis: 2900},
                    {xAxis: 2925},
                    {xAxis: 2950},
                    {xAxis: 2975},

                    {xAxis: 3000},
                    {xAxis: 3025},
                    {xAxis: 3050},
                    {xAxis: 3075},
                    {xAxis: 3100},
                    {xAxis: 3125},
                    {xAxis: 3150},
                    {xAxis: 3175},
                    {xAxis: 3200},
                    {xAxis: 3225},
                    {xAxis: 3250},
                    {xAxis: 3275},
                    {xAxis: 3300},
                    {xAxis: 3325},
                    {xAxis: 3350},
                    {xAxis: 3375},
                    {xAxis: 3400},
                    {xAxis: 3425},
                    {xAxis: 3450},
                    {xAxis: 3475},
                    {xAxis: 3500},
                    {xAxis: 3525},
                    {xAxis: 3550},
                    {xAxis: 3575},
                    {xAxis: 3600},
                    {xAxis: 3625},
                    {xAxis: 3650},
                    {xAxis: 3675},
                    {xAxis: 3700},
                    {xAxis: 3725},
                    {xAxis: 3750},
                    {xAxis: 3775},
                    {xAxis: 3800},
                    {xAxis: 3825},
                    {xAxis: 3850},
                    {xAxis: 3875},
                    {xAxis: 3900},
                    {xAxis: 3925},
                    {xAxis: 3950},
                    {xAxis: 3975},
                    {xAxis: 4000},


                    {xAxis: 4025},
                    {xAxis: 4050},
                    {xAxis: 4075},
                    {xAxis: 4100},
                    {xAxis: 4125},
                    {xAxis: 4150},
                    {xAxis: 4175},
                    {xAxis: 4200},
                    {xAxis: 4225},
                    {xAxis: 4250},
                    {xAxis: 4275},
                    {xAxis: 4300},
                    {xAxis: 4325},
                    {xAxis: 4350},
                    {xAxis: 4375},
                    {xAxis: 4400},
                    {xAxis: 4425},
                    {xAxis: 4450},
                    {xAxis: 4475},
                    {xAxis: 4500},
                    {xAxis: 4525},
                    {xAxis: 4550},
                    {xAxis: 4575},
                    {xAxis: 4600},
                    {xAxis: 4625},
                    {xAxis: 4650},
                    {xAxis: 4675},
                    {xAxis: 4700},
                    {xAxis: 4725},
                    {xAxis: 4750},
                    {xAxis: 4775},
                    {xAxis: 4800},
                    {xAxis: 4825},
                    {xAxis: 4850},
                    {xAxis: 4875},
                    {xAxis: 4900},
                    {xAxis: 4925},
                    {xAxis: 4950},
                    {xAxis: 4975},
                    {xAxis: 5000},
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
            chartV5.resize();
          });
          var chartV6 = echarts.init(document.getElementById("V6"));
          chartV6.clear()
          chartV6.setOption({
            title: {
              text: "V6",
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
              data: _th.data12.x,
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
                data: _th.data12.dataV6,
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
                    {xAxis: 2025},
                    {xAxis: 2050},
                    {xAxis: 2075},
                    {xAxis: 2100},
                    {xAxis: 2125},
                    {xAxis: 2150},
                    {xAxis: 2175},
                    {xAxis: 2200},
                    {xAxis: 2225},
                    {xAxis: 2250},
                    {xAxis: 2275},
                    {xAxis: 2300},
                    {xAxis: 2325},
                    {xAxis: 2350},
                    {xAxis: 2375},
                    {xAxis: 2400},
                    {xAxis: 2425},
                    {xAxis: 2450},
                    {xAxis: 2475},
                    {xAxis: 2500},
                    {xAxis: 2525},
                    {xAxis: 2550},
                    {xAxis: 2575},
                    {xAxis: 2600},
                    {xAxis: 2625},
                    {xAxis: 2650},
                    {xAxis: 2675},
                    {xAxis: 2700},
                    {xAxis: 2725},
                    {xAxis: 2750},
                    {xAxis: 2775},
                    {xAxis: 2800},
                    {xAxis: 2825},
                    {xAxis: 2850},
                    {xAxis: 2875},
                    {xAxis: 2900},
                    {xAxis: 2925},
                    {xAxis: 2950},
                    {xAxis: 2975},

                    {xAxis: 3000},
                    {xAxis: 3025},
                    {xAxis: 3050},
                    {xAxis: 3075},
                    {xAxis: 3100},
                    {xAxis: 3125},
                    {xAxis: 3150},
                    {xAxis: 3175},
                    {xAxis: 3200},
                    {xAxis: 3225},
                    {xAxis: 3250},
                    {xAxis: 3275},
                    {xAxis: 3300},
                    {xAxis: 3325},
                    {xAxis: 3350},
                    {xAxis: 3375},
                    {xAxis: 3400},
                    {xAxis: 3425},
                    {xAxis: 3450},
                    {xAxis: 3475},
                    {xAxis: 3500},
                    {xAxis: 3525},
                    {xAxis: 3550},
                    {xAxis: 3575},
                    {xAxis: 3600},
                    {xAxis: 3625},
                    {xAxis: 3650},
                    {xAxis: 3675},
                    {xAxis: 3700},
                    {xAxis: 3725},
                    {xAxis: 3750},
                    {xAxis: 3775},
                    {xAxis: 3800},
                    {xAxis: 3825},
                    {xAxis: 3850},
                    {xAxis: 3875},
                    {xAxis: 3900},
                    {xAxis: 3925},
                    {xAxis: 3950},
                    {xAxis: 3975},
                    {xAxis: 4000},


                    {xAxis: 4025},
                    {xAxis: 4050},
                    {xAxis: 4075},
                    {xAxis: 4100},
                    {xAxis: 4125},
                    {xAxis: 4150},
                    {xAxis: 4175},
                    {xAxis: 4200},
                    {xAxis: 4225},
                    {xAxis: 4250},
                    {xAxis: 4275},
                    {xAxis: 4300},
                    {xAxis: 4325},
                    {xAxis: 4350},
                    {xAxis: 4375},
                    {xAxis: 4400},
                    {xAxis: 4425},
                    {xAxis: 4450},
                    {xAxis: 4475},
                    {xAxis: 4500},
                    {xAxis: 4525},
                    {xAxis: 4550},
                    {xAxis: 4575},
                    {xAxis: 4600},
                    {xAxis: 4625},
                    {xAxis: 4650},
                    {xAxis: 4675},
                    {xAxis: 4700},
                    {xAxis: 4725},
                    {xAxis: 4750},
                    {xAxis: 4775},
                    {xAxis: 4800},
                    {xAxis: 4825},
                    {xAxis: 4850},
                    {xAxis: 4875},
                    {xAxis: 4900},
                    {xAxis: 4925},
                    {xAxis: 4950},
                    {xAxis: 4975},
                    {xAxis: 5000},
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
            chartV6.resize();
          });
          //放大之后的心电图
          var chartI1 = echarts.init(document.getElementById("I1"));
          chartI1.clear()
          chartI1.setOption({
            title: {
              text: "I",
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
              data: _th.data12.x,
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
                data: _th.data12.dataI,
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
                    {xAxis: 2025},
                    {xAxis: 2050},
                    {xAxis: 2075},
                    {xAxis: 2100},
                    {xAxis: 2125},
                    {xAxis: 2150},
                    {xAxis: 2175},
                    {xAxis: 2200},
                    {xAxis: 2225},
                    {xAxis: 2250},
                    {xAxis: 2275},
                    {xAxis: 2300},
                    {xAxis: 2325},
                    {xAxis: 2350},
                    {xAxis: 2375},
                    {xAxis: 2400},
                    {xAxis: 2425},
                    {xAxis: 2450},
                    {xAxis: 2475},
                    {xAxis: 2500},
                    {xAxis: 2525},
                    {xAxis: 2550},
                    {xAxis: 2575},
                    {xAxis: 2600},
                    {xAxis: 2625},
                    {xAxis: 2650},
                    {xAxis: 2675},
                    {xAxis: 2700},
                    {xAxis: 2725},
                    {xAxis: 2750},
                    {xAxis: 2775},
                    {xAxis: 2800},
                    {xAxis: 2825},
                    {xAxis: 2850},
                    {xAxis: 2875},
                    {xAxis: 2900},
                    {xAxis: 2925},
                    {xAxis: 2950},
                    {xAxis: 2975},

                    {xAxis: 3000},
                    {xAxis: 3025},
                    {xAxis: 3050},
                    {xAxis: 3075},
                    {xAxis: 3100},
                    {xAxis: 3125},
                    {xAxis: 3150},
                    {xAxis: 3175},
                    {xAxis: 3200},
                    {xAxis: 3225},
                    {xAxis: 3250},
                    {xAxis: 3275},
                    {xAxis: 3300},
                    {xAxis: 3325},
                    {xAxis: 3350},
                    {xAxis: 3375},
                    {xAxis: 3400},
                    {xAxis: 3425},
                    {xAxis: 3450},
                    {xAxis: 3475},
                    {xAxis: 3500},
                    {xAxis: 3525},
                    {xAxis: 3550},
                    {xAxis: 3575},
                    {xAxis: 3600},
                    {xAxis: 3625},
                    {xAxis: 3650},
                    {xAxis: 3675},
                    {xAxis: 3700},
                    {xAxis: 3725},
                    {xAxis: 3750},
                    {xAxis: 3775},
                    {xAxis: 3800},
                    {xAxis: 3825},
                    {xAxis: 3850},
                    {xAxis: 3875},
                    {xAxis: 3900},
                    {xAxis: 3925},
                    {xAxis: 3950},
                    {xAxis: 3975},
                    {xAxis: 4000},


                    {xAxis: 4025},
                    {xAxis: 4050},
                    {xAxis: 4075},
                    {xAxis: 4100},
                    {xAxis: 4125},
                    {xAxis: 4150},
                    {xAxis: 4175},
                    {xAxis: 4200},
                    {xAxis: 4225},
                    {xAxis: 4250},
                    {xAxis: 4275},
                    {xAxis: 4300},
                    {xAxis: 4325},
                    {xAxis: 4350},
                    {xAxis: 4375},
                    {xAxis: 4400},
                    {xAxis: 4425},
                    {xAxis: 4450},
                    {xAxis: 4475},
                    {xAxis: 4500},
                    {xAxis: 4525},
                    {xAxis: 4550},
                    {xAxis: 4575},
                    {xAxis: 4600},
                    {xAxis: 4625},
                    {xAxis: 4650},
                    {xAxis: 4675},
                    {xAxis: 4700},
                    {xAxis: 4725},
                    {xAxis: 4750},
                    {xAxis: 4775},
                    {xAxis: 4800},
                    {xAxis: 4825},
                    {xAxis: 4850},
                    {xAxis: 4875},
                    {xAxis: 4900},
                    {xAxis: 4925},
                    {xAxis: 4950},
                    {xAxis: 4975},
                    {xAxis: 5000},

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
              text: "II",
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
              data: _th.data12.x,
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
                data: _th.data12.dataII,
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
                    {xAxis: 2025},
                    {xAxis: 2050},
                    {xAxis: 2075},
                    {xAxis: 2100},
                    {xAxis: 2125},
                    {xAxis: 2150},
                    {xAxis: 2175},
                    {xAxis: 2200},
                    {xAxis: 2225},
                    {xAxis: 2250},
                    {xAxis: 2275},
                    {xAxis: 2300},
                    {xAxis: 2325},
                    {xAxis: 2350},
                    {xAxis: 2375},
                    {xAxis: 2400},
                    {xAxis: 2425},
                    {xAxis: 2450},
                    {xAxis: 2475},
                    {xAxis: 2500},
                    {xAxis: 2525},
                    {xAxis: 2550},
                    {xAxis: 2575},
                    {xAxis: 2600},
                    {xAxis: 2625},
                    {xAxis: 2650},
                    {xAxis: 2675},
                    {xAxis: 2700},
                    {xAxis: 2725},
                    {xAxis: 2750},
                    {xAxis: 2775},
                    {xAxis: 2800},
                    {xAxis: 2825},
                    {xAxis: 2850},
                    {xAxis: 2875},
                    {xAxis: 2900},
                    {xAxis: 2925},
                    {xAxis: 2950},
                    {xAxis: 2975},

                    {xAxis: 3000},
                    {xAxis: 3025},
                    {xAxis: 3050},
                    {xAxis: 3075},
                    {xAxis: 3100},
                    {xAxis: 3125},
                    {xAxis: 3150},
                    {xAxis: 3175},
                    {xAxis: 3200},
                    {xAxis: 3225},
                    {xAxis: 3250},
                    {xAxis: 3275},
                    {xAxis: 3300},
                    {xAxis: 3325},
                    {xAxis: 3350},
                    {xAxis: 3375},
                    {xAxis: 3400},
                    {xAxis: 3425},
                    {xAxis: 3450},
                    {xAxis: 3475},
                    {xAxis: 3500},
                    {xAxis: 3525},
                    {xAxis: 3550},
                    {xAxis: 3575},
                    {xAxis: 3600},
                    {xAxis: 3625},
                    {xAxis: 3650},
                    {xAxis: 3675},
                    {xAxis: 3700},
                    {xAxis: 3725},
                    {xAxis: 3750},
                    {xAxis: 3775},
                    {xAxis: 3800},
                    {xAxis: 3825},
                    {xAxis: 3850},
                    {xAxis: 3875},
                    {xAxis: 3900},
                    {xAxis: 3925},
                    {xAxis: 3950},
                    {xAxis: 3975},
                    {xAxis: 4000},


                    {xAxis: 4025},
                    {xAxis: 4050},
                    {xAxis: 4075},
                    {xAxis: 4100},
                    {xAxis: 4125},
                    {xAxis: 4150},
                    {xAxis: 4175},
                    {xAxis: 4200},
                    {xAxis: 4225},
                    {xAxis: 4250},
                    {xAxis: 4275},
                    {xAxis: 4300},
                    {xAxis: 4325},
                    {xAxis: 4350},
                    {xAxis: 4375},
                    {xAxis: 4400},
                    {xAxis: 4425},
                    {xAxis: 4450},
                    {xAxis: 4475},
                    {xAxis: 4500},
                    {xAxis: 4525},
                    {xAxis: 4550},
                    {xAxis: 4575},
                    {xAxis: 4600},
                    {xAxis: 4625},
                    {xAxis: 4650},
                    {xAxis: 4675},
                    {xAxis: 4700},
                    {xAxis: 4725},
                    {xAxis: 4750},
                    {xAxis: 4775},
                    {xAxis: 4800},
                    {xAxis: 4825},
                    {xAxis: 4850},
                    {xAxis: 4875},
                    {xAxis: 4900},
                    {xAxis: 4925},
                    {xAxis: 4950},
                    {xAxis: 4975},
                    {xAxis: 5000},

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
              text: "III",
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
              data: _th.data12.x,
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
                data: _th.data12.dataIII,
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
                    {xAxis: 2025},
                    {xAxis: 2050},
                    {xAxis: 2075},
                    {xAxis: 2100},
                    {xAxis: 2125},
                    {xAxis: 2150},
                    {xAxis: 2175},
                    {xAxis: 2200},
                    {xAxis: 2225},
                    {xAxis: 2250},
                    {xAxis: 2275},
                    {xAxis: 2300},
                    {xAxis: 2325},
                    {xAxis: 2350},
                    {xAxis: 2375},
                    {xAxis: 2400},
                    {xAxis: 2425},
                    {xAxis: 2450},
                    {xAxis: 2475},
                    {xAxis: 2500},
                    {xAxis: 2525},
                    {xAxis: 2550},
                    {xAxis: 2575},
                    {xAxis: 2600},
                    {xAxis: 2625},
                    {xAxis: 2650},
                    {xAxis: 2675},
                    {xAxis: 2700},
                    {xAxis: 2725},
                    {xAxis: 2750},
                    {xAxis: 2775},
                    {xAxis: 2800},
                    {xAxis: 2825},
                    {xAxis: 2850},
                    {xAxis: 2875},
                    {xAxis: 2900},
                    {xAxis: 2925},
                    {xAxis: 2950},
                    {xAxis: 2975},

                    {xAxis: 3000},
                    {xAxis: 3025},
                    {xAxis: 3050},
                    {xAxis: 3075},
                    {xAxis: 3100},
                    {xAxis: 3125},
                    {xAxis: 3150},
                    {xAxis: 3175},
                    {xAxis: 3200},
                    {xAxis: 3225},
                    {xAxis: 3250},
                    {xAxis: 3275},
                    {xAxis: 3300},
                    {xAxis: 3325},
                    {xAxis: 3350},
                    {xAxis: 3375},
                    {xAxis: 3400},
                    {xAxis: 3425},
                    {xAxis: 3450},
                    {xAxis: 3475},
                    {xAxis: 3500},
                    {xAxis: 3525},
                    {xAxis: 3550},
                    {xAxis: 3575},
                    {xAxis: 3600},
                    {xAxis: 3625},
                    {xAxis: 3650},
                    {xAxis: 3675},
                    {xAxis: 3700},
                    {xAxis: 3725},
                    {xAxis: 3750},
                    {xAxis: 3775},
                    {xAxis: 3800},
                    {xAxis: 3825},
                    {xAxis: 3850},
                    {xAxis: 3875},
                    {xAxis: 3900},
                    {xAxis: 3925},
                    {xAxis: 3950},
                    {xAxis: 3975},
                    {xAxis: 4000},


                    {xAxis: 4025},
                    {xAxis: 4050},
                    {xAxis: 4075},
                    {xAxis: 4100},
                    {xAxis: 4125},
                    {xAxis: 4150},
                    {xAxis: 4175},
                    {xAxis: 4200},
                    {xAxis: 4225},
                    {xAxis: 4250},
                    {xAxis: 4275},
                    {xAxis: 4300},
                    {xAxis: 4325},
                    {xAxis: 4350},
                    {xAxis: 4375},
                    {xAxis: 4400},
                    {xAxis: 4425},
                    {xAxis: 4450},
                    {xAxis: 4475},
                    {xAxis: 4500},
                    {xAxis: 4525},
                    {xAxis: 4550},
                    {xAxis: 4575},
                    {xAxis: 4600},
                    {xAxis: 4625},
                    {xAxis: 4650},
                    {xAxis: 4675},
                    {xAxis: 4700},
                    {xAxis: 4725},
                    {xAxis: 4750},
                    {xAxis: 4775},
                    {xAxis: 4800},
                    {xAxis: 4825},
                    {xAxis: 4850},
                    {xAxis: 4875},
                    {xAxis: 4900},
                    {xAxis: 4925},
                    {xAxis: 4950},
                    {xAxis: 4975},
                    {xAxis: 5000},

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
              text: "aVR",
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
              data: _th.data12.x,
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
                data: _th.data12.dataaVR,
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
                    {xAxis: 2025},
                    {xAxis: 2050},
                    {xAxis: 2075},
                    {xAxis: 2100},
                    {xAxis: 2125},
                    {xAxis: 2150},
                    {xAxis: 2175},
                    {xAxis: 2200},
                    {xAxis: 2225},
                    {xAxis: 2250},
                    {xAxis: 2275},
                    {xAxis: 2300},
                    {xAxis: 2325},
                    {xAxis: 2350},
                    {xAxis: 2375},
                    {xAxis: 2400},
                    {xAxis: 2425},
                    {xAxis: 2450},
                    {xAxis: 2475},
                    {xAxis: 2500},
                    {xAxis: 2525},
                    {xAxis: 2550},
                    {xAxis: 2575},
                    {xAxis: 2600},
                    {xAxis: 2625},
                    {xAxis: 2650},
                    {xAxis: 2675},
                    {xAxis: 2700},
                    {xAxis: 2725},
                    {xAxis: 2750},
                    {xAxis: 2775},
                    {xAxis: 2800},
                    {xAxis: 2825},
                    {xAxis: 2850},
                    {xAxis: 2875},
                    {xAxis: 2900},
                    {xAxis: 2925},
                    {xAxis: 2950},
                    {xAxis: 2975},

                    {xAxis: 3000},
                    {xAxis: 3025},
                    {xAxis: 3050},
                    {xAxis: 3075},
                    {xAxis: 3100},
                    {xAxis: 3125},
                    {xAxis: 3150},
                    {xAxis: 3175},
                    {xAxis: 3200},
                    {xAxis: 3225},
                    {xAxis: 3250},
                    {xAxis: 3275},
                    {xAxis: 3300},
                    {xAxis: 3325},
                    {xAxis: 3350},
                    {xAxis: 3375},
                    {xAxis: 3400},
                    {xAxis: 3425},
                    {xAxis: 3450},
                    {xAxis: 3475},
                    {xAxis: 3500},
                    {xAxis: 3525},
                    {xAxis: 3550},
                    {xAxis: 3575},
                    {xAxis: 3600},
                    {xAxis: 3625},
                    {xAxis: 3650},
                    {xAxis: 3675},
                    {xAxis: 3700},
                    {xAxis: 3725},
                    {xAxis: 3750},
                    {xAxis: 3775},
                    {xAxis: 3800},
                    {xAxis: 3825},
                    {xAxis: 3850},
                    {xAxis: 3875},
                    {xAxis: 3900},
                    {xAxis: 3925},
                    {xAxis: 3950},
                    {xAxis: 3975},
                    {xAxis: 4000},


                    {xAxis: 4025},
                    {xAxis: 4050},
                    {xAxis: 4075},
                    {xAxis: 4100},
                    {xAxis: 4125},
                    {xAxis: 4150},
                    {xAxis: 4175},
                    {xAxis: 4200},
                    {xAxis: 4225},
                    {xAxis: 4250},
                    {xAxis: 4275},
                    {xAxis: 4300},
                    {xAxis: 4325},
                    {xAxis: 4350},
                    {xAxis: 4375},
                    {xAxis: 4400},
                    {xAxis: 4425},
                    {xAxis: 4450},
                    {xAxis: 4475},
                    {xAxis: 4500},
                    {xAxis: 4525},
                    {xAxis: 4550},
                    {xAxis: 4575},
                    {xAxis: 4600},
                    {xAxis: 4625},
                    {xAxis: 4650},
                    {xAxis: 4675},
                    {xAxis: 4700},
                    {xAxis: 4725},
                    {xAxis: 4750},
                    {xAxis: 4775},
                    {xAxis: 4800},
                    {xAxis: 4825},
                    {xAxis: 4850},
                    {xAxis: 4875},
                    {xAxis: 4900},
                    {xAxis: 4925},
                    {xAxis: 4950},
                    {xAxis: 4975},
                    {xAxis: 5000},
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
              text: "aVL",
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
              data: _th.data12.x,
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
                data: _th.data12.dataaVL,
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
                    {xAxis: 2025},
                    {xAxis: 2050},
                    {xAxis: 2075},
                    {xAxis: 2100},
                    {xAxis: 2125},
                    {xAxis: 2150},
                    {xAxis: 2175},
                    {xAxis: 2200},
                    {xAxis: 2225},
                    {xAxis: 2250},
                    {xAxis: 2275},
                    {xAxis: 2300},
                    {xAxis: 2325},
                    {xAxis: 2350},
                    {xAxis: 2375},
                    {xAxis: 2400},
                    {xAxis: 2425},
                    {xAxis: 2450},
                    {xAxis: 2475},
                    {xAxis: 2500},
                    {xAxis: 2525},
                    {xAxis: 2550},
                    {xAxis: 2575},
                    {xAxis: 2600},
                    {xAxis: 2625},
                    {xAxis: 2650},
                    {xAxis: 2675},
                    {xAxis: 2700},
                    {xAxis: 2725},
                    {xAxis: 2750},
                    {xAxis: 2775},
                    {xAxis: 2800},
                    {xAxis: 2825},
                    {xAxis: 2850},
                    {xAxis: 2875},
                    {xAxis: 2900},
                    {xAxis: 2925},
                    {xAxis: 2950},
                    {xAxis: 2975},

                    {xAxis: 3000},
                    {xAxis: 3025},
                    {xAxis: 3050},
                    {xAxis: 3075},
                    {xAxis: 3100},
                    {xAxis: 3125},
                    {xAxis: 3150},
                    {xAxis: 3175},
                    {xAxis: 3200},
                    {xAxis: 3225},
                    {xAxis: 3250},
                    {xAxis: 3275},
                    {xAxis: 3300},
                    {xAxis: 3325},
                    {xAxis: 3350},
                    {xAxis: 3375},
                    {xAxis: 3400},
                    {xAxis: 3425},
                    {xAxis: 3450},
                    {xAxis: 3475},
                    {xAxis: 3500},
                    {xAxis: 3525},
                    {xAxis: 3550},
                    {xAxis: 3575},
                    {xAxis: 3600},
                    {xAxis: 3625},
                    {xAxis: 3650},
                    {xAxis: 3675},
                    {xAxis: 3700},
                    {xAxis: 3725},
                    {xAxis: 3750},
                    {xAxis: 3775},
                    {xAxis: 3800},
                    {xAxis: 3825},
                    {xAxis: 3850},
                    {xAxis: 3875},
                    {xAxis: 3900},
                    {xAxis: 3925},
                    {xAxis: 3950},
                    {xAxis: 3975},
                    {xAxis: 4000},


                    {xAxis: 4025},
                    {xAxis: 4050},
                    {xAxis: 4075},
                    {xAxis: 4100},
                    {xAxis: 4125},
                    {xAxis: 4150},
                    {xAxis: 4175},
                    {xAxis: 4200},
                    {xAxis: 4225},
                    {xAxis: 4250},
                    {xAxis: 4275},
                    {xAxis: 4300},
                    {xAxis: 4325},
                    {xAxis: 4350},
                    {xAxis: 4375},
                    {xAxis: 4400},
                    {xAxis: 4425},
                    {xAxis: 4450},
                    {xAxis: 4475},
                    {xAxis: 4500},
                    {xAxis: 4525},
                    {xAxis: 4550},
                    {xAxis: 4575},
                    {xAxis: 4600},
                    {xAxis: 4625},
                    {xAxis: 4650},
                    {xAxis: 4675},
                    {xAxis: 4700},
                    {xAxis: 4725},
                    {xAxis: 4750},
                    {xAxis: 4775},
                    {xAxis: 4800},
                    {xAxis: 4825},
                    {xAxis: 4850},
                    {xAxis: 4875},
                    {xAxis: 4900},
                    {xAxis: 4925},
                    {xAxis: 4950},
                    {xAxis: 4975},
                    {xAxis: 5000},

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
              text: "aVF",
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
              data: _th.data12.x,
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
                data: _th.data12.dataaVF,
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
                    {xAxis: 2025},
                    {xAxis: 2050},
                    {xAxis: 2075},
                    {xAxis: 2100},
                    {xAxis: 2125},
                    {xAxis: 2150},
                    {xAxis: 2175},
                    {xAxis: 2200},
                    {xAxis: 2225},
                    {xAxis: 2250},
                    {xAxis: 2275},
                    {xAxis: 2300},
                    {xAxis: 2325},
                    {xAxis: 2350},
                    {xAxis: 2375},
                    {xAxis: 2400},
                    {xAxis: 2425},
                    {xAxis: 2450},
                    {xAxis: 2475},
                    {xAxis: 2500},
                    {xAxis: 2525},
                    {xAxis: 2550},
                    {xAxis: 2575},
                    {xAxis: 2600},
                    {xAxis: 2625},
                    {xAxis: 2650},
                    {xAxis: 2675},
                    {xAxis: 2700},
                    {xAxis: 2725},
                    {xAxis: 2750},
                    {xAxis: 2775},
                    {xAxis: 2800},
                    {xAxis: 2825},
                    {xAxis: 2850},
                    {xAxis: 2875},
                    {xAxis: 2900},
                    {xAxis: 2925},
                    {xAxis: 2950},
                    {xAxis: 2975},

                    {xAxis: 3000},
                    {xAxis: 3025},
                    {xAxis: 3050},
                    {xAxis: 3075},
                    {xAxis: 3100},
                    {xAxis: 3125},
                    {xAxis: 3150},
                    {xAxis: 3175},
                    {xAxis: 3200},
                    {xAxis: 3225},
                    {xAxis: 3250},
                    {xAxis: 3275},
                    {xAxis: 3300},
                    {xAxis: 3325},
                    {xAxis: 3350},
                    {xAxis: 3375},
                    {xAxis: 3400},
                    {xAxis: 3425},
                    {xAxis: 3450},
                    {xAxis: 3475},
                    {xAxis: 3500},
                    {xAxis: 3525},
                    {xAxis: 3550},
                    {xAxis: 3575},
                    {xAxis: 3600},
                    {xAxis: 3625},
                    {xAxis: 3650},
                    {xAxis: 3675},
                    {xAxis: 3700},
                    {xAxis: 3725},
                    {xAxis: 3750},
                    {xAxis: 3775},
                    {xAxis: 3800},
                    {xAxis: 3825},
                    {xAxis: 3850},
                    {xAxis: 3875},
                    {xAxis: 3900},
                    {xAxis: 3925},
                    {xAxis: 3950},
                    {xAxis: 3975},
                    {xAxis: 4000},


                    {xAxis: 4025},
                    {xAxis: 4050},
                    {xAxis: 4075},
                    {xAxis: 4100},
                    {xAxis: 4125},
                    {xAxis: 4150},
                    {xAxis: 4175},
                    {xAxis: 4200},
                    {xAxis: 4225},
                    {xAxis: 4250},
                    {xAxis: 4275},
                    {xAxis: 4300},
                    {xAxis: 4325},
                    {xAxis: 4350},
                    {xAxis: 4375},
                    {xAxis: 4400},
                    {xAxis: 4425},
                    {xAxis: 4450},
                    {xAxis: 4475},
                    {xAxis: 4500},
                    {xAxis: 4525},
                    {xAxis: 4550},
                    {xAxis: 4575},
                    {xAxis: 4600},
                    {xAxis: 4625},
                    {xAxis: 4650},
                    {xAxis: 4675},
                    {xAxis: 4700},
                    {xAxis: 4725},
                    {xAxis: 4750},
                    {xAxis: 4775},
                    {xAxis: 4800},
                    {xAxis: 4825},
                    {xAxis: 4850},
                    {xAxis: 4875},
                    {xAxis: 4900},
                    {xAxis: 4925},
                    {xAxis: 4950},
                    {xAxis: 4975},
                    {xAxis: 5000},

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
          var chartV11 = echarts.init(document.getElementById("V11"));
          chartV11.clear()
          chartV11.setOption({
            title: {
              text: "V1",
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
              data: _th.data12.x,
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
                data: _th.data12.dataV1,
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
                    {xAxis: 2025},
                    {xAxis: 2050},
                    {xAxis: 2075},
                    {xAxis: 2100},
                    {xAxis: 2125},
                    {xAxis: 2150},
                    {xAxis: 2175},
                    {xAxis: 2200},
                    {xAxis: 2225},
                    {xAxis: 2250},
                    {xAxis: 2275},
                    {xAxis: 2300},
                    {xAxis: 2325},
                    {xAxis: 2350},
                    {xAxis: 2375},
                    {xAxis: 2400},
                    {xAxis: 2425},
                    {xAxis: 2450},
                    {xAxis: 2475},
                    {xAxis: 2500},
                    {xAxis: 2525},
                    {xAxis: 2550},
                    {xAxis: 2575},
                    {xAxis: 2600},
                    {xAxis: 2625},
                    {xAxis: 2650},
                    {xAxis: 2675},
                    {xAxis: 2700},
                    {xAxis: 2725},
                    {xAxis: 2750},
                    {xAxis: 2775},
                    {xAxis: 2800},
                    {xAxis: 2825},
                    {xAxis: 2850},
                    {xAxis: 2875},
                    {xAxis: 2900},
                    {xAxis: 2925},
                    {xAxis: 2950},
                    {xAxis: 2975},

                    {xAxis: 3000},
                    {xAxis: 3025},
                    {xAxis: 3050},
                    {xAxis: 3075},
                    {xAxis: 3100},
                    {xAxis: 3125},
                    {xAxis: 3150},
                    {xAxis: 3175},
                    {xAxis: 3200},
                    {xAxis: 3225},
                    {xAxis: 3250},
                    {xAxis: 3275},
                    {xAxis: 3300},
                    {xAxis: 3325},
                    {xAxis: 3350},
                    {xAxis: 3375},
                    {xAxis: 3400},
                    {xAxis: 3425},
                    {xAxis: 3450},
                    {xAxis: 3475},
                    {xAxis: 3500},
                    {xAxis: 3525},
                    {xAxis: 3550},
                    {xAxis: 3575},
                    {xAxis: 3600},
                    {xAxis: 3625},
                    {xAxis: 3650},
                    {xAxis: 3675},
                    {xAxis: 3700},
                    {xAxis: 3725},
                    {xAxis: 3750},
                    {xAxis: 3775},
                    {xAxis: 3800},
                    {xAxis: 3825},
                    {xAxis: 3850},
                    {xAxis: 3875},
                    {xAxis: 3900},
                    {xAxis: 3925},
                    {xAxis: 3950},
                    {xAxis: 3975},
                    {xAxis: 4000},


                    {xAxis: 4025},
                    {xAxis: 4050},
                    {xAxis: 4075},
                    {xAxis: 4100},
                    {xAxis: 4125},
                    {xAxis: 4150},
                    {xAxis: 4175},
                    {xAxis: 4200},
                    {xAxis: 4225},
                    {xAxis: 4250},
                    {xAxis: 4275},
                    {xAxis: 4300},
                    {xAxis: 4325},
                    {xAxis: 4350},
                    {xAxis: 4375},
                    {xAxis: 4400},
                    {xAxis: 4425},
                    {xAxis: 4450},
                    {xAxis: 4475},
                    {xAxis: 4500},
                    {xAxis: 4525},
                    {xAxis: 4550},
                    {xAxis: 4575},
                    {xAxis: 4600},
                    {xAxis: 4625},
                    {xAxis: 4650},
                    {xAxis: 4675},
                    {xAxis: 4700},
                    {xAxis: 4725},
                    {xAxis: 4750},
                    {xAxis: 4775},
                    {xAxis: 4800},
                    {xAxis: 4825},
                    {xAxis: 4850},
                    {xAxis: 4875},
                    {xAxis: 4900},
                    {xAxis: 4925},
                    {xAxis: 4950},
                    {xAxis: 4975},
                    {xAxis: 5000},

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
            chartV11.resize();
          });
          var chartV22 = echarts.init(document.getElementById("V22"));
          chartV22.clear()
          chartV22.setOption({
            title: {
              text: "V2",
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
              data: _th.data12.x,
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
                data: _th.data12.dataV2,
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
                    {xAxis: 2025},
                    {xAxis: 2050},
                    {xAxis: 2075},
                    {xAxis: 2100},
                    {xAxis: 2125},
                    {xAxis: 2150},
                    {xAxis: 2175},
                    {xAxis: 2200},
                    {xAxis: 2225},
                    {xAxis: 2250},
                    {xAxis: 2275},
                    {xAxis: 2300},
                    {xAxis: 2325},
                    {xAxis: 2350},
                    {xAxis: 2375},
                    {xAxis: 2400},
                    {xAxis: 2425},
                    {xAxis: 2450},
                    {xAxis: 2475},
                    {xAxis: 2500},
                    {xAxis: 2525},
                    {xAxis: 2550},
                    {xAxis: 2575},
                    {xAxis: 2600},
                    {xAxis: 2625},
                    {xAxis: 2650},
                    {xAxis: 2675},
                    {xAxis: 2700},
                    {xAxis: 2725},
                    {xAxis: 2750},
                    {xAxis: 2775},
                    {xAxis: 2800},
                    {xAxis: 2825},
                    {xAxis: 2850},
                    {xAxis: 2875},
                    {xAxis: 2900},
                    {xAxis: 2925},
                    {xAxis: 2950},
                    {xAxis: 2975},

                    {xAxis: 3000},
                    {xAxis: 3025},
                    {xAxis: 3050},
                    {xAxis: 3075},
                    {xAxis: 3100},
                    {xAxis: 3125},
                    {xAxis: 3150},
                    {xAxis: 3175},
                    {xAxis: 3200},
                    {xAxis: 3225},
                    {xAxis: 3250},
                    {xAxis: 3275},
                    {xAxis: 3300},
                    {xAxis: 3325},
                    {xAxis: 3350},
                    {xAxis: 3375},
                    {xAxis: 3400},
                    {xAxis: 3425},
                    {xAxis: 3450},
                    {xAxis: 3475},
                    {xAxis: 3500},
                    {xAxis: 3525},
                    {xAxis: 3550},
                    {xAxis: 3575},
                    {xAxis: 3600},
                    {xAxis: 3625},
                    {xAxis: 3650},
                    {xAxis: 3675},
                    {xAxis: 3700},
                    {xAxis: 3725},
                    {xAxis: 3750},
                    {xAxis: 3775},
                    {xAxis: 3800},
                    {xAxis: 3825},
                    {xAxis: 3850},
                    {xAxis: 3875},
                    {xAxis: 3900},
                    {xAxis: 3925},
                    {xAxis: 3950},
                    {xAxis: 3975},
                    {xAxis: 4000},


                    {xAxis: 4025},
                    {xAxis: 4050},
                    {xAxis: 4075},
                    {xAxis: 4100},
                    {xAxis: 4125},
                    {xAxis: 4150},
                    {xAxis: 4175},
                    {xAxis: 4200},
                    {xAxis: 4225},
                    {xAxis: 4250},
                    {xAxis: 4275},
                    {xAxis: 4300},
                    {xAxis: 4325},
                    {xAxis: 4350},
                    {xAxis: 4375},
                    {xAxis: 4400},
                    {xAxis: 4425},
                    {xAxis: 4450},
                    {xAxis: 4475},
                    {xAxis: 4500},
                    {xAxis: 4525},
                    {xAxis: 4550},
                    {xAxis: 4575},
                    {xAxis: 4600},
                    {xAxis: 4625},
                    {xAxis: 4650},
                    {xAxis: 4675},
                    {xAxis: 4700},
                    {xAxis: 4725},
                    {xAxis: 4750},
                    {xAxis: 4775},
                    {xAxis: 4800},
                    {xAxis: 4825},
                    {xAxis: 4850},
                    {xAxis: 4875},
                    {xAxis: 4900},
                    {xAxis: 4925},
                    {xAxis: 4950},
                    {xAxis: 4975},
                    {xAxis: 5000},

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
            chartV22.resize();
          });
          var chartV33 = echarts.init(document.getElementById("V33"));
          chartV33.clear()
          chartV33.setOption({
            title: {
              text: "V3",
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
              data: _th.data12.x,
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
                data: _th.data12.dataV3,
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
                    {xAxis: 2025},
                    {xAxis: 2050},
                    {xAxis: 2075},
                    {xAxis: 2100},
                    {xAxis: 2125},
                    {xAxis: 2150},
                    {xAxis: 2175},
                    {xAxis: 2200},
                    {xAxis: 2225},
                    {xAxis: 2250},
                    {xAxis: 2275},
                    {xAxis: 2300},
                    {xAxis: 2325},
                    {xAxis: 2350},
                    {xAxis: 2375},
                    {xAxis: 2400},
                    {xAxis: 2425},
                    {xAxis: 2450},
                    {xAxis: 2475},
                    {xAxis: 2500},
                    {xAxis: 2525},
                    {xAxis: 2550},
                    {xAxis: 2575},
                    {xAxis: 2600},
                    {xAxis: 2625},
                    {xAxis: 2650},
                    {xAxis: 2675},
                    {xAxis: 2700},
                    {xAxis: 2725},
                    {xAxis: 2750},
                    {xAxis: 2775},
                    {xAxis: 2800},
                    {xAxis: 2825},
                    {xAxis: 2850},
                    {xAxis: 2875},
                    {xAxis: 2900},
                    {xAxis: 2925},
                    {xAxis: 2950},
                    {xAxis: 2975},

                    {xAxis: 3000},
                    {xAxis: 3025},
                    {xAxis: 3050},
                    {xAxis: 3075},
                    {xAxis: 3100},
                    {xAxis: 3125},
                    {xAxis: 3150},
                    {xAxis: 3175},
                    {xAxis: 3200},
                    {xAxis: 3225},
                    {xAxis: 3250},
                    {xAxis: 3275},
                    {xAxis: 3300},
                    {xAxis: 3325},
                    {xAxis: 3350},
                    {xAxis: 3375},
                    {xAxis: 3400},
                    {xAxis: 3425},
                    {xAxis: 3450},
                    {xAxis: 3475},
                    {xAxis: 3500},
                    {xAxis: 3525},
                    {xAxis: 3550},
                    {xAxis: 3575},
                    {xAxis: 3600},
                    {xAxis: 3625},
                    {xAxis: 3650},
                    {xAxis: 3675},
                    {xAxis: 3700},
                    {xAxis: 3725},
                    {xAxis: 3750},
                    {xAxis: 3775},
                    {xAxis: 3800},
                    {xAxis: 3825},
                    {xAxis: 3850},
                    {xAxis: 3875},
                    {xAxis: 3900},
                    {xAxis: 3925},
                    {xAxis: 3950},
                    {xAxis: 3975},
                    {xAxis: 4000},


                    {xAxis: 4025},
                    {xAxis: 4050},
                    {xAxis: 4075},
                    {xAxis: 4100},
                    {xAxis: 4125},
                    {xAxis: 4150},
                    {xAxis: 4175},
                    {xAxis: 4200},
                    {xAxis: 4225},
                    {xAxis: 4250},
                    {xAxis: 4275},
                    {xAxis: 4300},
                    {xAxis: 4325},
                    {xAxis: 4350},
                    {xAxis: 4375},
                    {xAxis: 4400},
                    {xAxis: 4425},
                    {xAxis: 4450},
                    {xAxis: 4475},
                    {xAxis: 4500},
                    {xAxis: 4525},
                    {xAxis: 4550},
                    {xAxis: 4575},
                    {xAxis: 4600},
                    {xAxis: 4625},
                    {xAxis: 4650},
                    {xAxis: 4675},
                    {xAxis: 4700},
                    {xAxis: 4725},
                    {xAxis: 4750},
                    {xAxis: 4775},
                    {xAxis: 4800},
                    {xAxis: 4825},
                    {xAxis: 4850},
                    {xAxis: 4875},
                    {xAxis: 4900},
                    {xAxis: 4925},
                    {xAxis: 4950},
                    {xAxis: 4975},
                    {xAxis: 5000},

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
            chartV33.resize();
          });
          var chartV44 = echarts.init(document.getElementById("V44"));
          chartV44.clear()
          chartV44.setOption({
            title: {
              text: "V4",
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
              data: _th.data12.x,
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
                data: _th.data12.dataV4,
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
                    {xAxis: 2025},
                    {xAxis: 2050},
                    {xAxis: 2075},
                    {xAxis: 2100},
                    {xAxis: 2125},
                    {xAxis: 2150},
                    {xAxis: 2175},
                    {xAxis: 2200},
                    {xAxis: 2225},
                    {xAxis: 2250},
                    {xAxis: 2275},
                    {xAxis: 2300},
                    {xAxis: 2325},
                    {xAxis: 2350},
                    {xAxis: 2375},
                    {xAxis: 2400},
                    {xAxis: 2425},
                    {xAxis: 2450},
                    {xAxis: 2475},
                    {xAxis: 2500},
                    {xAxis: 2525},
                    {xAxis: 2550},
                    {xAxis: 2575},
                    {xAxis: 2600},
                    {xAxis: 2625},
                    {xAxis: 2650},
                    {xAxis: 2675},
                    {xAxis: 2700},
                    {xAxis: 2725},
                    {xAxis: 2750},
                    {xAxis: 2775},
                    {xAxis: 2800},
                    {xAxis: 2825},
                    {xAxis: 2850},
                    {xAxis: 2875},
                    {xAxis: 2900},
                    {xAxis: 2925},
                    {xAxis: 2950},
                    {xAxis: 2975},

                    {xAxis: 3000},
                    {xAxis: 3025},
                    {xAxis: 3050},
                    {xAxis: 3075},
                    {xAxis: 3100},
                    {xAxis: 3125},
                    {xAxis: 3150},
                    {xAxis: 3175},
                    {xAxis: 3200},
                    {xAxis: 3225},
                    {xAxis: 3250},
                    {xAxis: 3275},
                    {xAxis: 3300},
                    {xAxis: 3325},
                    {xAxis: 3350},
                    {xAxis: 3375},
                    {xAxis: 3400},
                    {xAxis: 3425},
                    {xAxis: 3450},
                    {xAxis: 3475},
                    {xAxis: 3500},
                    {xAxis: 3525},
                    {xAxis: 3550},
                    {xAxis: 3575},
                    {xAxis: 3600},
                    {xAxis: 3625},
                    {xAxis: 3650},
                    {xAxis: 3675},
                    {xAxis: 3700},
                    {xAxis: 3725},
                    {xAxis: 3750},
                    {xAxis: 3775},
                    {xAxis: 3800},
                    {xAxis: 3825},
                    {xAxis: 3850},
                    {xAxis: 3875},
                    {xAxis: 3900},
                    {xAxis: 3925},
                    {xAxis: 3950},
                    {xAxis: 3975},
                    {xAxis: 4000},


                    {xAxis: 4025},
                    {xAxis: 4050},
                    {xAxis: 4075},
                    {xAxis: 4100},
                    {xAxis: 4125},
                    {xAxis: 4150},
                    {xAxis: 4175},
                    {xAxis: 4200},
                    {xAxis: 4225},
                    {xAxis: 4250},
                    {xAxis: 4275},
                    {xAxis: 4300},
                    {xAxis: 4325},
                    {xAxis: 4350},
                    {xAxis: 4375},
                    {xAxis: 4400},
                    {xAxis: 4425},
                    {xAxis: 4450},
                    {xAxis: 4475},
                    {xAxis: 4500},
                    {xAxis: 4525},
                    {xAxis: 4550},
                    {xAxis: 4575},
                    {xAxis: 4600},
                    {xAxis: 4625},
                    {xAxis: 4650},
                    {xAxis: 4675},
                    {xAxis: 4700},
                    {xAxis: 4725},
                    {xAxis: 4750},
                    {xAxis: 4775},
                    {xAxis: 4800},
                    {xAxis: 4825},
                    {xAxis: 4850},
                    {xAxis: 4875},
                    {xAxis: 4900},
                    {xAxis: 4925},
                    {xAxis: 4950},
                    {xAxis: 4975},
                    {xAxis: 5000},

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
            chartV44.resize();
          });
          $(window).resize(function () {
            chartV55.resize();
          });
          var chartV66 = echarts.init(document.getElementById("V66"));
          chartV66.clear()
          chartV66.setOption({
            title: {
              text: "V6",
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
              data: _th.data12.x,
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
                data: _th.data12.dataV6,
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
                    {xAxis: 2025},
                    {xAxis: 2050},
                    {xAxis: 2075},
                    {xAxis: 2100},
                    {xAxis: 2125},
                    {xAxis: 2150},
                    {xAxis: 2175},
                    {xAxis: 2200},
                    {xAxis: 2225},
                    {xAxis: 2250},
                    {xAxis: 2275},
                    {xAxis: 2300},
                    {xAxis: 2325},
                    {xAxis: 2350},
                    {xAxis: 2375},
                    {xAxis: 2400},
                    {xAxis: 2425},
                    {xAxis: 2450},
                    {xAxis: 2475},
                    {xAxis: 2500},
                    {xAxis: 2525},
                    {xAxis: 2550},
                    {xAxis: 2575},
                    {xAxis: 2600},
                    {xAxis: 2625},
                    {xAxis: 2650},
                    {xAxis: 2675},
                    {xAxis: 2700},
                    {xAxis: 2725},
                    {xAxis: 2750},
                    {xAxis: 2775},
                    {xAxis: 2800},
                    {xAxis: 2825},
                    {xAxis: 2850},
                    {xAxis: 2875},
                    {xAxis: 2900},
                    {xAxis: 2925},
                    {xAxis: 2950},
                    {xAxis: 2975},

                    {xAxis: 3000},
                    {xAxis: 3025},
                    {xAxis: 3050},
                    {xAxis: 3075},
                    {xAxis: 3100},
                    {xAxis: 3125},
                    {xAxis: 3150},
                    {xAxis: 3175},
                    {xAxis: 3200},
                    {xAxis: 3225},
                    {xAxis: 3250},
                    {xAxis: 3275},
                    {xAxis: 3300},
                    {xAxis: 3325},
                    {xAxis: 3350},
                    {xAxis: 3375},
                    {xAxis: 3400},
                    {xAxis: 3425},
                    {xAxis: 3450},
                    {xAxis: 3475},
                    {xAxis: 3500},
                    {xAxis: 3525},
                    {xAxis: 3550},
                    {xAxis: 3575},
                    {xAxis: 3600},
                    {xAxis: 3625},
                    {xAxis: 3650},
                    {xAxis: 3675},
                    {xAxis: 3700},
                    {xAxis: 3725},
                    {xAxis: 3750},
                    {xAxis: 3775},
                    {xAxis: 3800},
                    {xAxis: 3825},
                    {xAxis: 3850},
                    {xAxis: 3875},
                    {xAxis: 3900},
                    {xAxis: 3925},
                    {xAxis: 3950},
                    {xAxis: 3975},
                    {xAxis: 4000},


                    {xAxis: 4025},
                    {xAxis: 4050},
                    {xAxis: 4075},
                    {xAxis: 4100},
                    {xAxis: 4125},
                    {xAxis: 4150},
                    {xAxis: 4175},
                    {xAxis: 4200},
                    {xAxis: 4225},
                    {xAxis: 4250},
                    {xAxis: 4275},
                    {xAxis: 4300},
                    {xAxis: 4325},
                    {xAxis: 4350},
                    {xAxis: 4375},
                    {xAxis: 4400},
                    {xAxis: 4425},
                    {xAxis: 4450},
                    {xAxis: 4475},
                    {xAxis: 4500},
                    {xAxis: 4525},
                    {xAxis: 4550},
                    {xAxis: 4575},
                    {xAxis: 4600},
                    {xAxis: 4625},
                    {xAxis: 4650},
                    {xAxis: 4675},
                    {xAxis: 4700},
                    {xAxis: 4725},
                    {xAxis: 4750},
                    {xAxis: 4775},
                    {xAxis: 4800},
                    {xAxis: 4825},
                    {xAxis: 4850},
                    {xAxis: 4875},
                    {xAxis: 4900},
                    {xAxis: 4925},
                    {xAxis: 4950},
                    {xAxis: 4975},
                    {xAxis: 5000},
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
            chartV66.resize();
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
    //获取当前时间
    getData() {
      var str = new Date();
      var nowTime = str.getFullYear() + "-"
        + (str.getMonth() + 1) + "-" + str.getDate() + " " + str.getHours() + ":" + str.getMinutes() + ":" + str.getSeconds();
      return nowTime;
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
  margin-left: 2vw;
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


.anNiu {
  height: 2vw;
  width: 5vw;
  float: right;
  margin-right: 4vw;
  font-size: 1vw;
}

.line {
  height: 4vw;
  width: 40vw;
  margin: 0;
  padding: 0;

}

.lineI {
  position: absolute;
  display: inline-block;
  top: 22vw;
  left: 0.5vw;
  height: 13vw;
  width: 83vw;
  background: white;
  border: 1px solid black;
  z-index: 3;
}

.lineshow {
  height: 8.3vw;
  width: 83vw;
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
  margin-left: 40.5vw;
  margin-top: 10.8vw;
  height: 2vw;
  width: 3vw;
  font-size: 1vw;
}

</style>
