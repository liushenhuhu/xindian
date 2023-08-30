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
        <div class="box">
          <div class="h11">
            <span></span>
            <p>患者信息</p>
          </div>
          <div class="patientMessage">
            <div class="textbox"><strong>姓名:</strong>{{ data.name }}</div>
            <div class="textbox"><strong>性别:</strong>{{ data.gender }}</div>
            <div class="textbox"><strong>年龄:</strong>{{ data.age }}岁</div>
            <div class="textbox"><strong>送检科室:</strong> -</div>
            <div class="textbox"><strong>申请单号:</strong> -</div>
            <div class="textbox"><strong>门诊号:</strong> -</div>
            <div class="textbox"><strong>住院号:</strong> -</div>
            <div class="textbox"><strong>病人编号:</strong> -</div>
            <div class="textbox"><strong>HR:</strong>{{ data.hr }}</div>
            <div class="textbox"><strong>PR:</strong>{{ data.pr }}</div>
            <div class="textbox"><strong>QRS:</strong>{{ data.qrs }}</div>
            <div class="textbox"><strong>QT/QTc:</strong>{{ data.qt }}</div>
            <div class="textbox"><strong>P/QRS/T:</strong>{{ data.p }}</div>
            <div class="textbox"><strong>PV5/SV1:</strong>{{ data.pv5 }}</div>
            <div class="textbox"><strong>RV5/SV1:</strong>{{ data.rv5_sv1 }}</div>
          </div>
          <div class="h11">
            <span></span>
            <p>自动分析结果，仅供参考</p>
          </div>
          <div class="result size mmargin">
            <div class="ml">{{ data.result }}</div>
          </div>
          <div class="h11">
            <span></span>
            <p>患者症状</p>
          </div>
          <div class="result size mmargin">
            <div class="ml">{{ data.patientSymptom }}</div>
          </div>
          <div class="h11">
            <span></span>
            <div class="between">
              <p>医师诊断</p>
              <el-button type="text" @click="dialogFormVisible = true" style="padding:0;line-height: 4vh;margin-right: 1vw;font-size:2.5vh">常用术语</el-button>
            </div>
          </div>
          <el-dialog title="常用术语" :visible.sync="dialogFormVisible">
            <div v-for="(item) in items">
              <div>{{ item.name }}</div>
              <button class="commentLabelBtn" :class="{ 'selected': isSelected}" type="primary"
                      v-for="itemc in item.label"
                      :key="itemc"
                      @click="putDown(itemc,$event)">{{ itemc }}
              </button>
            </div>
            <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="dialogForm">确 定</el-button>
            </div>
          </el-dialog>
          <div class="margin">
              <el-input
                style="width: 90%;"
                type="textarea"
                placeholder="请在这里输入医生诊断结果"
                data-value="1111"
                :rows="6"
                class="font"
                v-model="data.resultByDoctor"
              >
              </el-input>
          </div>
          <div class="doctor">
            <div class="input">
              <strong>医师:</strong>
              <el-input v-model="data.doctorName" clearable></el-input>
            </div>
            <div class="input">
              <strong>日期:</strong>
              <el-input v-if="data.diagnosisData!=null" v-model="data.diagnosisData" clearable></el-input>
              <el-input v-else v-model="data.dataTime" clearable></el-input>
            </div>
          </div>
          <div class="upload">
            <el-button class="anNiu" type="success" plain @click="sendMsg()">发送短信</el-button>
            <el-button class="anNiu" type="success" plain @click="btnUpload">医生诊断</el-button>
          </div>
        </div>
      </div>
    </div>
    <div class="nobottom"></div>

    <div>
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
    <child ref="drawShow"></child>
  </div>
</template>

<script>
import echarts from 'echarts'
import $ from 'jquery';
import {getCommonTerms, addReport, getReportByPId, updateReport} from "@/api/report/report";
import {sendMsgToPatient} from "@/api/patient_management/patient_management";
import child from './child'
import CacheList from "@/views/monitor/cache/list.vue";

export default {
  name: "index",
  components:{
    CacheList,
    child
  },
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
      arr: [],
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
        console.log(response)
        this.data.result = response.data.intelligentDiagnosis
        this.data.resultByDoctor = response.data.diagnosisConclusion
        this.arr[0]=response.data.diagnosisConclusion
        this.data.doctorName = response.data.diagnosisDoctor
        this.data.diagnosisData = response.data.reportTime
        this.data.pphone = response.data.pphone
      });
    }
  },
  mounted() {
    this.get();
    this.Camera();
    // this.drawgrid();//canvas 画图
  },
  methods: {
    showChart1() {
      var pId = this.pId;
      // 找到对应的canvas
      var canvas = $("#1").find("canvas")[0];
      // 将图片转化为图片数据  toDataURL返回的base64
      var base64 = canvas.toDataURL("image/png",1);
      var level = 1;
      this.$refs.drawShow.openDrawShow(base64,pId,level);
    },
    showChart2() {
      var pId = this.pId;
      var canvas = $("#2").find("canvas")[0];
      // 将图片转化为图片数据  toDataURL返回的base64
      var base64 = canvas.toDataURL("image/png",1);
      var level = 2;
      this.$refs.drawShow.openDrawShow(base64,pId,level);
    },
    showChart3() {
      var pId = this.pId;
      var canvas = $("#3").find("canvas")[0];
      // 将图片转化为图片数据  toDataURL返回的base64
      var base64 = canvas.toDataURL("image/png",1);
      var level = 3;
      this.$refs.drawShow.openDrawShow(base64,pId,level);
    },
    showChart4() {
      var pId = this.pId;
      var canvas = $("#4").find("canvas")[0];
      // 将图片转化为图片数据  toDataURL返回的base64
      var base64 = canvas.toDataURL("image/png",1);
      var level = 4;
      this.$refs.drawShow.openDrawShow(base64,pId,level);
    },
    showChart5() {
      var pId = this.pId;
      var canvas = $("#5").find("canvas")[0];
      // 将图片转化为图片数据  toDataURL返回的base64
      var base64 = canvas.toDataURL("image/png",1);
      var level = 5;
      this.$refs.drawShow.openDrawShow(base64,pId,level);
    },
    showChart6() {
      var pId = this.pId;
      var canvas = $("#6").find("canvas")[0];
      // 将图片转化为图片数据  toDataURL返回的base64
      var base64 = canvas.toDataURL("image/png",1);
      var level = 6;
      this.$refs.drawShow.openDrawShow(base64,pId,level);
    },
    showChart7() {
      var pId = this.pId;
      var canvas = $("#7").find("canvas")[0];
      // 将图片转化为图片数据  toDataURL返回的base64
      var base64 = canvas.toDataURL("image/png",1);
      var level = 7;
      this.$refs.drawShow.openDrawShow(base64,pId,level);
    },
    showChart8() {
      var pId = this.pId;
      var canvas = $("#8").find("canvas")[0];
      // 将图片转化为图片数据  toDataURL返回的base64
      var base64 = canvas.toDataURL("image/png",1);
      var level = 8;
      this.$refs.drawShow.openDrawShow(base64,pId,level);
    },
    showChart9() {
      var pId = this.pId;
      var canvas = $("#9").find("canvas")[0];
      // 将图片转化为图片数据  toDataURL返回的base64
      var base64 = canvas.toDataURL("image/png",1);
      var level = 9;
      this.$refs.drawShow.openDrawShow(base64,pId,level);
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
    putDown(key,event) {
      //console.log(event.currentTarget.classList.toggle('selected'))
      event.currentTarget.classList.toggle('selected')
      if(this.arr.length > 0){
        let index = this.arr.indexOf(key);
        //console.log(index)
        if(index !== -1){
          this.arr.splice(index,1);
        }else {
          this.arr.push(key);
        }
      }else {
        this.arr.push(key);
      }
    },
    dialogForm(){
      this.data.resultByDoctor = this.arr.toString()
      this.dialogFormVisible=false;
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
      //console.log("pId:", this.pId)
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
          _th.arr[0]=data.result.diagnosis_conclusion
          _th.data.resultByDoctor = data.result.diagnosis_conclusion
          _th.data.doctorName = data.result.diagnosis_doctor
          _th.data.age = data.result.age
          _th.data.gender = data.result.gender
          _th.data.name = data.result.patientName
          //_th.data.result = data.result.intelligent_diagnosis
          _th.data.hr = data.result.ecg_analysis_data["平均心率"]
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
              //interval: 0.1, // 刻度间隔
              splitLine: {
                show: true, //让网格显示
                lineStyle: {//网格样式
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
                  ////opacity: 100,//透明度
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
              minInterval: 1,
              splitLine: {
                show: true, //让网格显示
                lineStyle: {//网格样式
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
                  ////opacity: 0.6,//透明度

                }
              },
              max: 1,
              min: -1
            },
            series: [
              {
                type: 'line',
                smooth: true,
                showSymbol: true,
                data: _th.nArr[0],
                lineStyle: {
                  normal: {
                    color: "#000000",
                    width: 1,
                    // //opacity: 1,
                  }
                },
                markLine: {
                  symbol: "none",
                  silent: true,
                  lineStyle: {
                    type: "solid",
                    color: '#b33939',
                    width: 1,
                    ////opacity: 0.5,
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
                  ////opacity: 0.6,//透明度
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
                  ////opacity: 0.6,//透明度
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
                    ////opacity: 0.5,
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
                  ////opacity: 0.6,//透明度
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
                  ////opacity: 0.6,//透明度
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
                    //opacity: 0.5,
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
                  //opacity: 0.6,//透明度
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
                  //opacity: 0.6,//透明度
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
                    //opacity: 0.5,
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
                  //opacity: 0.6,//透明度
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
                  //opacity: 0.6,//透明度
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
                    //opacity: 0.5,
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
                  //opacity: 0.6,//透明度
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
                  //opacity: 0.6,//透明度
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
                    //opacity: 0.5,
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
                  //opacity: 0.6,//透明度
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
                  //opacity: 0.6,//透明度
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
                    //opacity: 0.5,
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
                  //opacity: 0.6,//透明度
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
                  //opacity: 0.6,//透明度
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
                    //opacity: 0.5,
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
                  //opacity: 0.6,//透明度
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
                  //opacity: 0.6,//透明度
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
                    //opacity: 0.5,
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
                  //opacity: 0.6,//透明度
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
                  //opacity: 0.6,//透明度

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
                    // //opacity: 1,
                  }
                },
                markLine: {
                  symbol: "none",
                  silent: true,
                  lineStyle: {
                    type: "solid",
                    color: '#b33939',
                    width: 1,
                    //opacity: 0.5,
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
                  //opacity: 0.6,//透明度
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
                  //opacity: 0.6,//透明度
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
                    //opacity: 0.5,
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
                  //opacity: 0.6,//透明度
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
                  //opacity: 0.6,//透明度
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
                    //opacity: 0.5,
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
                  //opacity: 0.6,//透明度
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
                  //opacity: 0.6,//透明度
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
                    //opacity: 0.5,
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
                  //opacity: 0.6,//透明度
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
                  //opacity: 0.6,//透明度
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
                    //opacity: 0.5,
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
                  //opacity: 0.6,//透明度
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
                  //opacity: 0.6,//透明度
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
                    //opacity: 0.5,
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
                  //opacity: 0.6,//透明度
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
                  //opacity: 0.6,//透明度
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
                    //opacity: 0.5,
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
                  //opacity: 0.6,//透明度
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
                  //opacity: 0.6,//透明度
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
                    //opacity: 0.5,
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
                  //opacity: 0.6,//透明度
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
                  //opacity: 0.6,//透明度
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
                    //opacity: 0.5,
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
    /*cancleDialog(){
      this.dialogFormVisible = false;
      this.arr = [];
    },*/
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
  },
}
</script>

<style lang="scss" scoped>
.body {
  display: flex;
}
::v-deep el-button{
  background-color: #1890ff;
}
.noleft {
  flex: 1;
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  color: #000000;
  //background-color: rgba(108, 176, 245, 0.98);
  //background: linear-gradient(to left,#ffffff, rgba(158, 173, 189, 0.98));
  .box{
    overflow: hidden;
    width: 90%;
    margin: 0 auto;
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-top:1.5vh;
    margin-bottom: 1.5vh;
    border-radius: 2vh;
    background-color: #e8e8e8;
    //opacity: 0.6;
    .h11:first-child{
      margin-top: 1.5vh;
    }
    .h11{
      width: 100%;
      font-size: 2.5vh;
      background-color: #e2e2e3;
      font-weight: 700;
      height: 4vh;
      display: flex;
      span{
        width: 6px;
        height: 100%;
        background-color: #00afff;
      }
      p{
        height: 40px;
        line-height: 4vh;
        margin: 0;
        margin-left: 1vw;
      }
      .between{
        width: 100%;
        display: flex;
        justify-content: space-between;
        p{
          height: 4vh;
          line-height: 4vh;
          margin: 0;
          margin-left: 1vw;
        }
      }
    }
    .result{
      height: 12vh;
      width: 100%;
    }
  }
}
.size{
  font-size: 2.3vh;
}
.mmargin{
  margin: 1.5vh 0 1.5vh 0;
}
.margin{
  width: 100%;
  display: flex;
  justify-content: center;
  margin-bottom: 1.5vw;
  margin-top: 1vw;
}
.ml{
  margin-left: 2vw;
  margin-right: 2vw;
}
.patientMessage {
  display: flex;
  justify-content:space-between;
  flex-wrap: wrap;
  //background-color: #e01806;
  //height: 20vh;
  margin: 1.5vh 0 1.5vh 0;
  width: 90%;
  .textbox{
    width:50%;
    margin-bottom: 1.5vh;
    font-size: 2.1vh;
  }
}




.automaticResult {
  //height: 27vw;
  //margin-top: 2vw;
  //display: flex;
}

.doctor-1 {
  font-size: 1vw;
  height: 2vw;
  line-height: 2vw;

}

.doctor {
  display: flex;
  width: 100%;
  justify-content: center;
  .input{
    display: flex;
    flex-direction: row;
    //margin-left: 1vw;
    strong{
      white-space: nowrap;
      line-height: 36px;
      margin-right: .5vw;
    }
  }
}

.noright {
  width: 55vw;
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
  background-color: #435bf7;
  color: #fff !important;
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
