<template>
  <div class="wrap">
    <div id="pdfDom" style="padding: 10px;">
      <div id="app">
        <div class="page">
          <div class="box">
            <div class="patientMessage">
              <div class="h11">
                <span></span>
                <p>患者信息</p>
              </div>
              <div class="info">
                <div class="textbox"><strong>姓名:</strong>{{ data.name }}</div>
                <div class="textbox"><strong>性别:</strong>{{ data.gender }}</div>
                <div class="textbox"><strong>年龄:</strong>{{ data.age }}岁</div>
                <div class="textbox"><strong>送检科室:</strong> -</div>
                <div class="textbox"><strong>申请单号:</strong> -</div>
                <div class="textbox"><strong>门诊号:</strong> -</div>
                <div class="textbox"><strong>住院号:</strong> -</div>
                <div class="textbox"><strong>病人编号:</strong> -</div>
                <div class="textbox"><strong>心率:</strong>{{ data.hr }}bpm</div>
                <div class="textbox"><strong>P波:</strong>{{ data.p }}ms</div>
                <div class="textbox"><strong>PR间期:</strong>{{ data.pr }}ms</div>
                <div class="textbox"><strong>QRS波群:</strong>{{ data.qrs }}ms</div>
                <div class="textbox"><strong>QTc:</strong>{{ data.qtc }}ms</div>
                <div class="textbox"><strong>HRV:</strong>{{ data.hrv }}ms</div>
              </div>
            </div>
            <div class="result1 size mmargin">
                <div class="h11">
                  <span></span>
                  <p>自动分析结果，仅供参考</p>
                </div>
                <div class="text">
                  <div class="ml">{{ data.result }}</div>
                </div>
                <div class="h11">
                  <span></span>
                  <p>患者症状</p>
                </div>
                <div class="text">
                  <div class="ml">{{ data.patientSymptom }}</div>
                </div>
            </div>
            <div class="result2 size mmargin">
              <div class="h11">
                <span></span>
                <div class="between">
                <p>医师诊断</p>
                  <el-button type="text" @click="dialogFormVisible = true" style="padding:0;line-height: 4vh;margin-right: 1vw;font-size:2vh">常用术语</el-button>
                </div>
              </div>
              <div class="mt">
                <el-input
                  type="textarea"
                  v-model="data.resultByDoctor"
                  placeholder="请在这里输入医生诊断结果"
                  data-value="1111"
                  :rows="5"
                  class="font">{{ data.resultByDoctor }}
                </el-input>
                <el-dialog title="常用术语" :visible.sync="dialogFormVisible">
                  <div v-for="(item,index) in items">
                    <div>{{ item.name }}</div>
                    <button class="commentLabelBtn" :class="{ 'selected': isSelected}" type="primary"
                            v-for="itemc in item.label"
                            :key="itemc"
                            @click="putDown(itemc,$event)">{{ itemc }}
                    </button>
                  </div>
                  <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogFormVisible = false">取 消</el-button>
                    <el-button type="primary" @click="dialogForm">确 定</el-button>
                  </div>
                </el-dialog>
              </div>
              <div class="doctor">
                <div class="input">
                  <strong>医师:</strong>
                  <el-input v-model="data.doctorName" clearable>{{ data.doctorName }}
                  </el-input>
                </div>
                <div class="input">
                  <strong>日期:</strong>
                  <el-input v-if="data.diagnosisData!=null" v-model="data.diagnosisData" clearable></el-input>
                  <el-input v-else v-model="data.dataTime" clearable></el-input>
                </div>
              </div>
              <div class="oder">
                <el-button type="success" plain class="anNiu" @click="sendMsg()">发送短信</el-button>
                <el-button type="success" plain class="anNiu" @click="btnUpload">医生诊断</el-button>
              </div>
            </div>
          </div>

          <div style="padding-left: 2vw;font-size: 1vw">10mm/mV 25mm/s</div>
          <div class="body">
          <!--            <div class="demo-image__preview">-->
          <!--              <el-image :src="baseImage"></el-image>-->
          <!--            </div>-->
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
        </div>
      </div>
      <div class="lineI" v-show="openI">
        <div id="I1" class="lineshow"></div>
        <!--        <el-image style="width: 60vw;height: 10vw" :src="baseImage"></el-image>-->
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
import {getCommonTerms, addReport, getReportByPId, updateReport} from "@/api/report/report";
import {sendMsgToPatient} from "@/api/patient_management/patient_management";
import html2canvas from "html2canvas";

export default {
  name: "index",
  data() {
    return {
      isSelected: false,//术语按钮没有被按下
      dialogFormVisible: false,
      items: [],//常用术语
      checkButton: [],
      pId: null,
      data: {
        name: "",
        gender: "",
        age: "",
        result: "",
        qt: "",
        qrs_deg: "",
        t: "",
        pv5: "",
        sv1: "",
        rv5_sv1: "",
        resultByDoctor: "",
        dataTime: "",
        doctorName: "",
        diagnosisData: null,
        bSuggest: "",
        cSuggest: "",
        patientSymptom:"无",
        hr:'',
        p:'',
        pr:'',
        qrs:'',
        qtc:'',
        hrv:''
      },
      markData: [
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
      ],//放大之后标记线
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
      ],//没放大标记线
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
      pphone: "",
      baseImage: "",
      arr:[],
    };
  },
  created() {
    var pId = this.$route.query.pId;
    if (pId) {
      this.pId = pId;
      getReportByPId(this.pId).then(response => {
        console.log("请求成功：", response.data)
        this.data.resultByDoctor = response.data.diagnosisConclusion
        this.arr[0]=response.data.diagnosisConclusion
        this.data.doctorName = response.data.diagnosisDoctor
        this.data.diagnosisData = response.data.reportTime
        this.data.pphone = response.data.pphone
        if (!this.data.doctorName){
          const date = new Date();
          const year = date.getFullYear().toString().padStart(4, '0');
          const month = (date.getMonth() + 1).toString().padStart(2, '0');
          const day = date.getDate().toString().padStart(2, '0');
          const hour = date.getHours().toString().padStart(2, '0');
          const minute = date.getMinutes().toString().padStart(2, '0');
          const second = date.getSeconds().toString().padStart(2, '0');
          this.data.diagnosisData=`${year}-${month}-${day} ${hour}:${minute}:${second}`
        }
        if(response.data.patientSymptom!=null){
          this.data.patientSymptom = response.data.patientSymptom
        }

      });
      var show = sessionStorage.getItem(pId + "show");
      if (!show) {
        this.get();
      }
    }
  },
  mounted() {
    this.get();
    this.Camera();
  },
  methods: {
    //截断数据（一条数据现在2000）
    getNewArray(array, subGroupLength) {
      let i = 0;
      let newArray = [];
      while (i < array.length) {
        newArray.push(array.slice(i, i += subGroupLength));
      }
      return newArray;
    },

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
    //关闭展开框
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
    dialogForm(){
      this.data.resultByDoctor = this.data.resultByDoctor+this.arr.toString()
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
      console.log("请求数据了！")
      console.log("pId", this.pId)
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
          console.log("请求成功：", data)
          loading.close()
          _th.arr[0]=data.result.diagnosis_conclusion
          _th.data.resultByDoctor = data.result.diagnosis_conclusion
          _th.data.doctorName = data.result.diagnosis_doctor
          _th.data.age = data.result.age
          _th.data.gender = data.result.gender
          _th.data.name = data.result.patientName
          _th.data.result = data.result.intelligent_diagnosis
          _th.data.hr=data.result.ecg_analysis_data["平均心率"]
          _th.data.p=data.result.ecg_analysis_data["P波时限"]
          _th.data.pr=data.result.ecg_analysis_data["PR间期"]
          _th.data.qrs=data.result.ecg_analysis_data["QRS波时限"]
          _th.data.qtc=data.result.ecg_analysis_data["QTc"]
          _th.data.hrv=data.result.ecg_analysis_data["RMSSD"]
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
          _th.nArrI = _th.getNewArray(_th.data12.dataI, 1000);
          _th.data12.dataII = data.result.II
          _th.nArrII = _th.getNewArray(_th.data12.dataII, 1000);
          _th.data12.dataIII = data.result.III
          _th.nArrIII = _th.getNewArray(_th.data12.dataIII, 1000);
          _th.data12.dataaVR = data.result.aVR
          _th.nArraVR = _th.getNewArray(_th.data12.dataaVR, 1000);
          _th.data12.dataaVL = data.result.aVL
          _th.nArraVL = _th.getNewArray(_th.data12.dataaVL, 1000);
          _th.data12.dataaVF = data.result.aVF
          _th.nArraVF = _th.getNewArray(_th.data12.dataaVF, 1000);
          _th.data12.dataV1 = data.result.V1
          _th.nArrV1 = _th.getNewArray(_th.data12.dataV1, 1000);
          _th.data12.dataV2 = data.result.V2
          _th.nArrV2 = _th.getNewArray(_th.data12.dataV2, 1000);
          _th.data12.dataV3 = data.result.V3
          _th.nArrV3 = _th.getNewArray(_th.data12.dataV3, 1000);
          _th.data12.dataV4 = data.result.V4
          _th.nArrV4 = _th.getNewArray(_th.data12.dataV4, 1000);
          _th.data12.dataV5 = data.result.V5
          _th.nArrV5 = _th.getNewArray(_th.data12.dataV5, 1000);
          _th.data12.dataV6 = data.result.V6
          _th.nArrV6 = _th.getNewArray(_th.data12.dataV6, 1000);

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

                  data: _th.markdata
                }
              }
            ],
          });
          // _th.baseImage = chartI.getDataURL({
          //   pixelRatio: 2,
          // });
          // console.log(_th.baseImage)
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

                  data: _th.markdata
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
              }, max: 1,
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

                  data: _th.markdata
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

                  data: _th.markdata
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

                  data: _th.markdata
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

                  data: _th.markdata
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

                  data: _th.markdata
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

                  data: _th.markdata
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

                  data: _th.markdata
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

                  data: _th.markdata
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

                  data: _th.markdata
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

                  data: _th.markdata
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
            dataZoom: [
              {
                type: 'inside',   // 鼠标滚轮缩放
                xAxisIndex: 0,
                filterMode: 'none',//不过滤数据
                start: 0,
                end: 100
              }, {
                type: 'inside',   // 鼠标滚轮缩放
                yAxisIndex: 0,
                filterMode: 'none',//不过滤数据
                start: 0,
                end: 100
              },
              {
                show: true,       // 滑动条组件
                type: 'slider',
                filterMode: 'none',//不过滤数据
                xAxisIndex: 0,
                start: 0,
                end: 100
              },
              {
                show: true,       // 滑动条组件
                type: 'slider',
                filterMode: 'none',//不过滤数据
                yAxisIndex: 0,
                start: 0,
                end: 100
              }
            ],
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
              max: 3,
              min: -3
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

                  data: _th.markData
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
              max: 3,
              min: -3
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

                  data: _th.markData
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
              max: 3,
              min: -3
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

                  data: _th.markData
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
              max: 3,
              min: -3
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

                  data: _th.markData
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
              max: 3,
              min: -3
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

                  data: _th.markData
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
              max: 3,
              min: -3
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
                  data: _th.markData
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
              max: 3,
              min: -3
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

                  data: _th.markData
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
              max: 3,
              min: -3
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

                  data: _th.markData
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
              max: 3,
              min: -3
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
                  data: _th.markData
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
              max: 3,
              min: -3
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
                  data: _th.markData
                }
              }
            ],
          });
          $(window).resize(function () {
            chartV44.resize();
          });
          var chartV55 = echarts.init(document.getElementById("V44"));
          chartV55.clear()
          chartV55.setOption({
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
              max: 3,
              min: -3
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
                  data: _th.markData
                }
              }
            ],
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
              max: 3,
              min: -3
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

                  data: _th.markData
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
    //保存数据
    btnUpload() {
      if(this.data.resultByDoctor==''||this.data.resultByDoctor==null){
        this.$message({
          type: 'error',
          message: '诊断结果不能为空!'
        });
        return
      }
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
        console.log(_th.items);
      })
    },
    Yes() {
      this.dialogFormVisible = false
      console.log("提交的术语：", this.checkButton)
    },

    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done();
        })
        .catch(_ => {
        });
    },
    onOpen() {
    },
    onClose() {
      this.$refs['elForm'].resetFields()
    },
    close() {
      this.$emit('update:visible', false)
    },
    handleConfirm() {
      this.$refs['elForm'].validate(valid => {
        if (!valid) return
        this.close()
      })
    },
  },
}
</script>

<style lang="scss" scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.page {
  width: 100%;
  //height: 100vh;
  //border: 3px solid #0000ff;
}
.box{
  overflow: hidden;
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  margin-top:1.5vh;
  margin-bottom: 1.5vh;
  border-radius: 2vh;
  background-color: #e8e8e8;
  //opacity: 0.6;
  .patientMessage {
    display: flex;
    justify-content:space-between;
    flex-direction: column;
    flex-wrap: wrap;
    //background-color: #e01806;
    //height: 20vh;
    margin: 1.5vh 0 1.5vh 0;
    width: 35%;
    .info{
      width: 90%;
      display: flex;
      flex-direction: row;
      flex-wrap: wrap;
      margin-left: 1.6vw;
      .textbox{
        width:45%;
        margin-bottom: 2vh;
        font-size: 2.1vh;
      }
    }
  }
  .h11{
    width: 100%;
    font-size: 2.5vh;
    background-color: #dcdcdc;
    font-weight: 700;
    height: 4vh;
    display: flex;
    span{
      width: 6px;
      height: 100%;
      background-color: #00afff;
    }
    p{
      margin-left: 1vw;
      line-height: 4vh;
    }
    .between{
      width: 100%;
      display: flex;
      justify-content: space-between;
      p{
        line-height: 4vh;
      }
    }
  }
  //.h11::before{
  //  display: inline-block;
  //  content:'';
  //  width: 2px;
  //  height: 100%;
  //  background-color: #00afff;
  //}
  .result1{
    width: 32%;
    //height: 12vh;
    .text{
      height: 16vh;
      border: 1px darkgray solid;
    }
  }
  .selected {
    background-color: #435bf7;
    color: #fff !important;
  }
  .result2{
    width: 32.5%;
    //height: 12vh;
    .text{
      height: 15vh;
    }
    .oder{
      display: flex;
      justify-content: center;
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
}
.mt{
  //margin-top: 2vh;
  height: 13vh;
}
.doctor {
  margin:6vh 0 4vh 0 ;
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
      font-size: 1.8vh;
    }
  }
}
.header-left {
  width: 60%;
  height: 100%;
  display: flex;
  flex-wrap: nowrap
}


.font {
  font-size: 1vw;
  font-weight: 700;
  color: #6f0600;
  background-color: #fff;
  height: 13vh;
}

.body {
  //margin-left: 2vw;
  display: flex;
  flex-direction: column;
}

.body .body-1 {
  flex: 1;
}

.bottom {
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  width: 100%;
  height: 4vw;
  //border: 1px solid #000000;
  margin-top: 2vw;
}
.ml{
  margin-left: 1vw;
  margin-right: 2vw;
}
//.bottom-left{
//  display: flex;
//  //flex-direction: row;
//}


.anNiu {
  height: 3vw;
  width: 8vw;
  font-size: 1vw;
  line-height: 1vw;
  text-align: center;
}


.line {
  height: 4vw;
  width: 83vw;
  margin: 0;
  padding: 0;

}

.lineI {
  position: absolute;
  display: inline-block;
  top: 35%;
  left: 50%;
  transform: translate(-50%, -50%);
  height: 20vw;
  width: 85vw;
  background: white;
  border: 1px solid black;
  z-index: 3;
}

.doctordata{
  width: 58vw;
}

.lineshow {
  height: 15vw;
  width: 85vw;
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

.commentLabelBtn {
  width: auto;
  height: 28px;
  margin: 8px 8px 8px 0;
  background: #eaeff5;
  color: #000000 !important;
  border-radius: 0.5vw;
  border: 1px solid #000000;
}

</style>
