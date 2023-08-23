<template>
  <div>
    <div class="body">
      <div class="noright">
        <div class="ecg">
          <!--          <canvas id="grids" width="750px" height="750px"></canvas>-->
          <div>
            <div id="1" class="line" @dblclick="showChart1()"></div>
          </div>
          <div>
            <div id="2" class="line" @dblclick="showChart2()"></div>
          </div>
          <div>
            <div id="3" class="line" @dblclick="showChart3()"></div>
          </div>
          <div>
            <div id="4" class="line" @dblclick="showChart4()"></div>
          </div>
          <div>
            <div id="5" class="line" @dblclick="showChart5()"></div>
          </div>
          <div>
            <div id="6" class="line" @dblclick="showChart6()"></div>
          </div>
          <div>
            <div id="7" class="line" @dblclick="showChart7()"></div>
          </div>
          <div>
            <div id="8" class="line" @dblclick="showChart8()"></div>
          </div>
          <div>
            <div id="9" class="line" @dblclick="showChart9()"></div>
          </div>
        </div>
      </div>
      <div class="noleft">
        <div class="patientMessage">
          <el-descriptions class="patientMessage1" title="患者信息" :column="1" border>
            <el-descriptions-item>
              <template slot="label"><strong>姓名:</strong></template>{{ data.name }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label"><strong>性别:</strong></template>{{ data.gender }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label"><strong>年龄:</strong></template>{{ data.age }}岁
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label"><strong>送检科室:</strong></template> -
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label"><strong>申请单号:</strong></template> -
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label"><strong>门诊号:</strong></template> -
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label"><strong>住院号:</strong></template> -
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label"><strong>病人编号:</strong></template> -
            </el-descriptions-item>
          </el-descriptions>
<!--          <div class="patientMessage-1">-->
<!--            <div class="patientMessage-1-1"><strong>姓名:</strong>{{ data.name }}</div>-->
<!--            <div class="patientMessage-1-1"><strong>性别:</strong>{{ data.gender }}</div>-->
<!--            <div class="patientMessage-1-1"><strong>年龄:</strong>{{ data.age }}岁</div>-->
<!--            <div class="patientMessage-1-1"><strong>送检科室:</strong> -</div>-->
<!--            <div class="patientMessage-1-1"><strong>申请单号:</strong> -</div>-->
<!--            <div class="patientMessage-1-1"><strong>门诊号:</strong> -</div>-->
<!--            <div class="patientMessage-1-1"><strong>住院号:</strong> -</div>-->
<!--            <div class="patientMessage-1-1"><strong>病人编号:</strong> -</div>-->
<!--          </div>-->
<!--          <div class="patientMessage-2">-->
<!--            <div class="patientMessage-2-1"><strong>HR:</strong>{{ data.hr }}bpm</div>-->
<!--            <div class="patientMessage-2-1"><strong>PR:</strong>{{ data.pr }}ms</div>-->
<!--            <div class="patientMessage-2-1"><strong>QRS:</strong>{{ data.qrs }}ms</div>-->
<!--            <div class="patientMessage-2-1"><strong>QT/QTc:</strong>{{ data.qt }}ms/{{ data.qtc }}ms</div>-->
<!--            <div class="patientMessage-2-1"><strong>P/QRS/T:</strong>{{ data.p }}/{{ data.qrs_deg }}/{{ data.t }}deg-->
<!--            </div>-->
<!--            <div class="patientMessage-2-1"><strong>PV5/SV1:</strong>{{ data.pv5 }}/{{ data.sv1 }}mV</div>-->
<!--            <div class="patientMessage-2-1"><strong>RV5/SV1:</strong>{{ data.rv5_sv1 }}mV</div>-->
<!--          </div>-->
        </div>
        <div class="automaticResult">
          <div class="doctor-1"><strong>自动分析结果，仅供参考</strong></div>
          <div>
            <div style="width: 95%;height: 10vw;border: 1px solid #c4c4c4;padding: 0.5vw;">{{ data.result }}</div>
          </div>
          <div class="doctor-1"><strong>患者症状</strong></div>
          <div>
            <div style="width: 95%;height: 10vw;border: 1px solid #c4c4c4;padding: 0.5vw;">{{ data.patientSymptom }}</div>
          </div>
          <div class="doctor-1">
            <strong>医师诊断</strong>
            <el-button type="text" @click="dialogFormVisible = true" style="margin-left: 20vw">常用术语</el-button>
          </div>
          <el-dialog title="常用术语" :visible.sync="dialogFormVisible">
            <div v-for="(item,index) in items">
              <div>{{ item.name }}</div>
              <button class="commentLabelBtn" :class="{ 'selected': isSelected}" type="primary"
                      v-for="(itemc,indexc) in item.label"
                      :key="itemc"
                      @click="putDown(itemc)">{{ itemc }}
              </button>
            </div>
            <div slot="footer" class="dialog-footer">
              <el-button @click="dialogFormVisible = false">取 消</el-button>
              <el-button type="primary" @click="dialogFormVisible = false">确 定</el-button>
            </div>
          </el-dialog>
          <div>
            <el-input
              style="width: 95%;"
              type="textarea"
              v-model="data.resultByDoctor"
              placeholder="请在这里输入医生诊断结果"
              data-value="1111"
              :rows="6"
              class="font">{{ data.resultByDoctor }}
            </el-input>
          </div>
        </div>
        <div class="upload">
          <el-button class="anNiu" type="success" plain @click="sendMsg()">发送短信</el-button>
          <el-button class="anNiu" type="success" plain @click="btnUpload">医生诊断</el-button>
        </div>
        <div class="doctor">
          <div>
            <strong>医师:</strong>
            <el-input v-model="data.doctorName" clearable
                      :style="{width: '90%',}">{{ data.doctorName }}
            </el-input>
          </div>
          <div>
            <strong>日期:</strong>
            <el-input v-if="data.diagnosisData!=null" v-model="data.diagnosisData" clearable
                      :style="{width: '90%',}"></el-input>
            <el-input v-else v-model="data.dataTime" clearable
                      :style="{width: '90%',}"></el-input>
          </div>
        </div>
      </div>

    </div>
    <div class="lineI" v-show="open1">
      <div id="I1" class="lineshow"></div>
      <button @click="clickClose" class="noName">关闭</button>
    </div>
    <div class="lineI" v-show="open2">
      <div id="I2" class="lineshow"></div>
      <button @click="clickClose" class="noName">关闭</button>
    </div>
    <div class="lineI" v-show="open3">
      <div id="I3" class="lineshow"></div>
      <button @click="clickClose" class="noName">关闭</button>
    </div>
    <div class="lineI" v-show="open4">
      <div id="I4" class="lineshow"></div>
      <button @click="clickClose" class="noName">关闭</button>
    </div>
    <div class="lineI" v-show="open5">
      <div id="I5" class="lineshow"></div>
      <button @click="clickClose" class="noName">关闭</button>
    </div>
    <div class="lineI" v-show="open6">
      <div id="I6" class="lineshow"></div>
      <button @click="clickClose" class="noName">关闭</button>
    </div>
    <div class="lineI" v-show="open7">
      <div id="I7" class="lineshow"></div>
      <button @click="clickClose" class="noName">关闭</button>
    </div>
    <div class="lineI" v-show="open8">
      <div id="I8" class="lineshow"></div>
      <button @click="clickClose" class="noName">关闭</button>
    </div>
    <div class="lineI" v-show="open9">
      <div id="I9" class="lineshow"></div>
      <button @click="clickClose" class="noName">关闭</button>
    </div>
  </div>
</template>

<script>
import echarts from 'echarts'
import $ from 'jquery';
import {getCommonTerms, addReport, getReportByPId, updateReport} from "@/api/report/report";
import {sendMsgToPatient} from "@/api/patient_management/patient_management";

export default {
  name: "index",
  data() {
    return {
      videoVisible: false,//echarts弹出框显示
      markdata: [
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
        {yAxis: -1},
        {yAxis: -0.5},
        {yAxis: 0},
        {yAxis: 0.5},
        {yAxis: 1},
      ],//没放大之前标记线
      dialogFormVisible: false,//弹出框
      items: "",//常用术语
      isSelected: false,//术语按钮没有被按下
      selectedButtons: [],//选中的按钮
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
        resultByDoctor: [],
        dataTime: "",
        doctorName: "",
        diagnosisData: null,
        bSuggest: "",
        cSuggest: "",
        patientSymptom:"无",
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
      pphone: "",
      nArr: [],//导联数据
      x: [],//x轴坐标
      open1: false,
      open2: false,
      open3: false,
      open4: false,
      open5: false,
      open6: false,
      open7: false,
      open8: false,
      open9: false,
    };
  },
  created() {
    var pId = this.$route.query.pId;
    if (pId) {
      this.pId = pId;
      getReportByPId(this.pId).then(response => {
        console.log( response.data)
        this.data.resultByDoctor = response.diagnosisConclusion
        this.data.doctorName = response.diagnosisDoctor
        this.data.diagnosisData = response.reportTime
        this.data.pphone = response.data.pphone
        if (response.data.patientSymptom!=null){
          this.data.patientSymptom = response.data.patientSymptom
        }

      });
      // var show = sessionStorage.getItem(pId + "show");
      // if (!show) {
      //   this.get();
      // }
    }
  },
  mounted() {
    this.get();
    this.Camera();
    // this.drawgrid();//canvas 画图
  },
  methods: {
    showChart1() {
      this.open1 = !this.open1;
    },
    showChart2() {
      this.open2 = !this.open2;
    },
    showChart3() {
      this.open3 = !this.open3;
    },
    showChart4() {
      this.open4 = !this.open4;
    },
    showChart5() {
      this.open5 = !this.open5;
    },
    showChart6() {
      this.open6 = !this.open6;
    },
    showChart7() {
      this.open7 = !this.open7;
    },
    showChart8() {
      this.open8 = !this.open8;
    },
    showChart9() {
      this.open9 = !this.open9;
    },
    clickClose() {
      this.open1 = false;
      this.open2 = false;
      this.open3 = false;
      this.open4 = false;
      this.open5 = false;
      this.open6 = false;
      this.open7 = false;
      this.open8 = false;
      this.open9 = false;
    },
    //按下常用术语按钮
    putDown(key) {
      console.log(key)
      if (this.data.resultByDoctor === null)
        this.data.resultByDoctor = []
      console.log(this.data.resultByDoctor)
      let index = this.data.resultByDoctor.indexOf(key);
      console.log("if", index2, this.isSelected)
      if (index2 !== -1) {
        this.data.resultByDoctor.splice(index2, 1);
      } else {
        this.data.resultByDoctor.push(key)
      }
      // if (this.isSelected) {
      //   this.isSelected = false
      //   let index = this.data.resultByDoctor.indexOf(key);
      //   console.log("if", index, this.isSelected)
      //   if (index !== -1) {
      //     // this.selectedButtons.splice(index, 1);
      //     // this.data.resultByDoctor=this.selectedButtons
      //     this.data.resultByDoctor.splice(index, 1);
      //   } else {
      //     // this.data.resultByDoctor += key + ';';
      //     this.data.resultByDoctor.push(key)
      //     //this.selectedButtons.push(key)
      //     // this.data.resultByDoctor=this.selectedButtons
      //   }
      // } else {
      //   this.isSelected = true
      //   console.log("else", this.isSelected)
      //   // this.data.resultByDoctor += key + ';';
      //   this.data.resultByDoctor.push(key)
      //   // this.selectedButtons.push(key)
      //   // this.data.resultByDoctor=this.selectedButtons
      // }
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
      console.log("pId:", this.pId)
      this.data.dataTime = this.$options.methods.getData();
      $.ajax({
        type: "post",
        url: "https://screen.mindyard.cn:84/get_jecg_single_web",
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
          console.log("请求成功：", data)
          loading.close()
          _th.data.resultByDoctor = data.result.diagnosis_conclusion
          _th.data.doctorName = data.result.diagnosis_doctor
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
          _th.nArr = _th.getNewArray(_th.data.datas, 600);
          console.log("数据以600一条分好组", _th.nArr)
          for (var i = 0; i < 600; i++) {
            _th.x.push(i);
          }
          var chart1 = echarts.init(document.getElementById("1"));
          chart1.clear()
          chart1.setOption({
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
              data: _th.x,
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
                data: _th.nArr[0],
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
                  data: _th.markdata
                }
              }
            ],
          });
          $(window).resize(function () {
            chart1.resize();
          });
          var chart2 = echarts.init(document.getElementById("2"));
          chart2.clear()
          chart2.setOption({
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
              data: _th.x,
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
                data: _th.nArr[1],
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
                  data: _th.markdata
                }

              }
            ],

          });
          $(window).resize(function () {
            chart2.resize();
          });
          var chart3 = echarts.init(document.getElementById("3"));
          chart3.clear()
          chart3.setOption({
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
              data: _th.x,
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
                data: _th.nArr[2],
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
                  data: _th.markdata
                }
              }
            ],
          });
          $(window).resize(function () {
            chart3.resize();
          });
          var chart4 = echarts.init(document.getElementById("4"));
          chart4.clear()
          chart4.setOption({
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
              data: _th.x,
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
                data: _th.nArr[3],
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
                  data: _th.markdata
                }
              }
            ],
          });
          $(window).resize(function () {
            chart4.resize();
          });
          var chart5 = echarts.init(document.getElementById("5"));
          chart5.clear()
          chart5.setOption({
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
              data: _th.x,
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
                data: _th.nArr[4],
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
                  data: _th.markdata
                }
              }
            ],
          });
          $(window).resize(function () {
            chart5.resize();
          });
          var chart6 = echarts.init(document.getElementById("6"));
          chart6.clear()
          chart6.setOption({
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
              data: _th.x,
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
                data: _th.nArr[5],
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
                  data: _th.markdata
                }
              }
            ],
          });
          $(window).resize(function () {
            chart6.resize();
          });
          var chart7 = echarts.init(document.getElementById("7"));
          chart7.clear()
          chart7.setOption({
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
              data: _th.x,
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
                data: _th.nArr[6],
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
                  data: _th.markdata
                }
              }
            ],
          });
          $(window).resize(function () {
            chart7.resize();
          });
          var chart8 = echarts.init(document.getElementById("8"));
          chart8.clear()
          chart8.setOption({
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
              data: _th.x,
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
                data: _th.nArr[7],
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
                  data: _th.markdata
                }
              }
            ],
          });
          $(window).resize(function () {
            chart8.resize();
          });
          var chart9 = echarts.init(document.getElementById("9"));
          chart9.clear()
          chart9.setOption({
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
              data: _th.x,
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
                data: _th.nArr[8],
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
                  data: _th.markdata
                }
              }
            ],
          });
          $(window).resize(function () {
            chart9.resize();
          });
          //放大之后
          var chart11 = echarts.init(document.getElementById("I1"));
          chart11.clear()
          chart11.setOption({
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
              data: _th.x,
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
                data: _th.nArr[0],
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
                  data: _th.markdata
                }
              }
            ],
          });
          $(window).resize(function () {
            chart11.resize();
          });
          var chart22 = echarts.init(document.getElementById("I2"));
          chart22.clear()
          chart22.setOption({
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
              data: _th.x,
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
                data: _th.nArr[1],
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
                  data: _th.markdata
                }

              }
            ],

          });
          $(window).resize(function () {
            chart22.resize();
          });
          var chart33 = echarts.init(document.getElementById("I3"));
          chart33.clear()
          chart33.setOption({
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
              data: _th.x,
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
                data: _th.nArr[2],
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
                  data: _th.markdata
                }
              }
            ],
          });
          $(window).resize(function () {
            chart33.resize();
          });
          var chart44 = echarts.init(document.getElementById("I4"));
          chart44.clear()
          chart44.setOption({
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
              data: _th.x,
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
                data: _th.nArr[3],
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
                  data: _th.markdata
                }
              }
            ],
          });
          $(window).resize(function () {
            chart44.resize();
          });
          var chart55 = echarts.init(document.getElementById("I5"));
          chart55.clear()
          chart55.setOption({
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
              data: _th.x,
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
                data: _th.nArr[4],
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
                  data: _th.markdata
                }
              }
            ],
          });
          $(window).resize(function () {
            chart55.resize();
          });
          var chart66 = echarts.init(document.getElementById("I6"));
          chart66.clear()
          chart66.setOption({
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
              data: _th.x,
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
                data: _th.nArr[5],
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
                  data: _th.markdata
                }
              }
            ],
          });
          $(window).resize(function () {
            chart66.resize();
          });
          var chart77 = echarts.init(document.getElementById("I7"));
          chart77.clear()
          chart77.setOption({
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
              data: _th.x,
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
                data: _th.nArr[6],
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
                  data: _th.markdata
                }
              }
            ],
          });
          $(window).resize(function () {
            chart77.resize();
          });
          var chart88 = echarts.init(document.getElementById("I8"));
          chart88.clear()
          chart88.setOption({
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
              data: _th.x,
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
                data: _th.nArr[7],
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
                  data: _th.markdata
                }
              }
            ],
          });
          $(window).resize(function () {
            chart88.resize();
          });
          var chart99 = echarts.init(document.getElementById("I9"));
          chart99.clear()
          chart99.setOption({
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
              data: _th.x,
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
                data: _th.nArr[8],
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
                  data: _th.markdata
                }
              }
            ],
          });
          $(window).resize(function () {
            chart99.resize();
          });
        },
        error: function (data) {
          alert("数据请求错误,请刷新页面或联系管理员")
          loading.close()
          console.log("请求失败：", data)
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
    //发送短信
    sendMsg() {
      console.log("患者电话: " + this.data.pphone)
      let patientPhone = this.data.pphone
      if (patientPhone.length===14||patientPhone.length===15){
        patientPhone=patientPhone.substring(0,11)
      }
      console.log(patientPhone)
      if (patientPhone) {
        // console.log("患者姓名: " + row.patientName)
        this.$confirm('向该患者发送短信提示采集存在较大干扰?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          sendMsgToPatient(patientPhone).then(response => {
            this.$message({
              type: 'success',
              message: '发送成功!'
            });
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消'
          });
        });
      } else {
        this.$message.error('该患者手机号不合法！！！');
      }
    },
    //医生诊断
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
            // this.getList();
            console.log("新增成功！")
          });
        } else {
          form["reportId"] = res.data.reportId
          console.log("保存的数据：", form)
          updateReport(form).then(response => {
            this.$modal.msgSuccess("修改成功");
            // this.getList();
            console.log("修改成功！")
          })
        }
      })
    },
    //常用术语
    Camera() {
      var _th = this
      getCommonTerms().then(response => {
        console.log("常用术语：", response.data)
        const result = Object.entries(response.data).map(([name, label]) => ({
          name,
          label
        }));
        _th.items = result
        console.log("格式过的常用术语：", _th.items);
      })
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done();
        })
        .catch(_ => {
        });
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
    //canvas画图
    // drawgrid() {
    //   var c_canvas = document.getElementById("grids");
    //   this.drawSmallGrid(c_canvas);
    //   this.drawMediumGrid(c_canvas);
    //   this.drawBigGrid(c_canvas);
    // },
    // drawSmallGrid(c_canvas) {
    //   this.ctx = c_canvas.getContext("2d");
    //   this.ctx.strokeStyle = "#f1dedf";
    //   this.ctx.strokeWidth = 1;
    //   this.ctx.beginPath();
    //   for (var x = 0.5; x < 750; x += 3) {
    //     this.ctx.moveTo(x, 0);
    //     this.ctx.lineTo(x, 750);
    //     this.ctx.stroke();
    //   }
    //   for (var y = 0.5; y < 750; y += 3) {
    //     this.ctx.moveTo(0, y);
    //     this.ctx.lineTo(750, y);
    //     this.ctx.stroke();
    //   }
    //   this.ctx.closePath();
    // },
    // drawMediumGrid(c_canvas) {
    //   this.ctx = c_canvas.getContext("2d");
    //   this.ctx.strokeStyle = "#fdbeb9";
    //   this.ctx.strokeWidth = 2
    //   //宽度是小网格的2倍
    //   this.ctx.beginPath();
    //   for (var x = 0.5; x < 750; x += 15) {
    //     this.ctx.moveTo(x, 0);
    //     this.ctx.lineTo(x, 750);
    //     this.ctx.stroke();
    //   }
    //   for (var y = 0.5; y < 750; y += 15) {
    //     this.ctx.moveTo(0, y);
    //     this.ctx.lineTo(750, y);
    //     this.ctx.stroke();
    //   }
    //   this.ctx.closePath();
    //   return;
    // },
    //
    // drawBigGrid(c_canvas) {
    //   this.ctx = c_canvas.getContext("2d");
    //   this.ctx.strokeStyle = "#e0514b";
    //   this.ctx.strokeWidth = 3;
    //   this.ctx.beginPath();
    //   for (var x = 0.5; x < 750; x += 75) {
    //     this.ctx.moveTo(x, 0);
    //     this.ctx.lineTo(x, 750);
    //     this.ctx.stroke();
    //   }
    //   for (var y = 0.5; y < 750; y += 75) {
    //     this.ctx.moveTo(0, y);
    //     this.ctx.lineTo(750, y);
    //     this.ctx.stroke();
    //   }
    //   this.ctx.closePath();
    //   return;
    // }

  },
}
</script>

<style lang="scss" scoped>
.body {
  display: flex;
}

.noleft {
  display: flex;
  flex-direction: column;
  padding-left: 0.5vw;
  width: 35%;
  //background-color: #71f9d5;
}

.patientMessage {
  display: flex;
  //justify-content:space-between;
  align-items: stretch;
  //background-color: #e01806;
  height: 30vw;
}
.patientMessage1{
  width: 15vw;
}
.patientMessage-1 {
  width: 15vw;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
}

.patientMessage-2 {
  width: 15vw;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
}

.patientMessage-1-1 {
  font-size: 1.3vw;
}

.patientMessage-2-1 {
  font-size: 1.3vw;
}

.automaticResult {
  //height: 27vw;
  margin-top: 2vw;
}

.doctor-1 {
  font-size: 1vw;
  height: 2vw;
  line-height: 2vw;

}

.doctor {
  display: flex;
  flex-direction: column;
  height: 5vw;
  line-height: 6vw;
}

.noright {
  width: 65%;
  display: flex;
  flex-direction: column;
  //border: 1px solid #000000;
}

.ecg {
  //width: 50vw;
  //  height: 50vw;
  display: flex;
  flex-direction: column;
}

.line {
  height: 9vw;
  width: 55vw;
  margin: 0;
  padding: 0;

}

.upload {
  margin-top: 1vw;
  //height: 5vw;
  display: flex;
  justify-content: space-around;
}

.anNiu {
  height: 3vw;
  width: 8vw;
  //font-size: 1.5vw;
  line-height: 1vw;
  text-align: center;
}

.bigDiv {
  border: 1px solid #157a0c;
  height: 40vw;
  width: 100%;
  z-index: 1000;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  //background-color: #03044A;
}

.commentLabelBtn {
  width: auto;
  height: 28px;
  margin: 8px 8px 8px 0;
  background: #eaeff5;
  color: #000000 !important;
  border-radius: 0.5vw;
  border: 1px solid #000000;
}

.selected {
  //background-color: #435bf7;
  //color: #fff !important;
}
.font {
  font-size: 1vw;
  font-weight: 700;
  color: #6f0600;
  background-color: #fff;
}
.lineI {
  position: absolute;
  display: inline-block;
  top: 50%;
  left: 50%;
  -webkit-transform: translate(-50%, -50%);
  transform: translate(-50%, -50%);
  height: 35%;
  width: 100%;
  background: white;
  border: 1px solid black;
  z-index: 3;
}
.noName {
  position: absolute;
  top: 0;
  right: 0;
  //margin-left: 40.5vw;
  //margin-top: 10.8vw;
  height: 2vw;
  width: 3vw;
  font-size: 1vw;
  z-index: 2000;
}

.lineshow {
  height: 15vw;
  width: 84vw;
  position: absolute;
  top: 50%;
  left: 50%;
  -webkit-transform: translate(-50%, -50%);
  transform: translate(-50%, -50%);
  border: 1px solid #fff;
}

</style>
