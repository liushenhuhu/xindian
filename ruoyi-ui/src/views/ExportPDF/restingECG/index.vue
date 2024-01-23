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
                <div class="textBoxBottom"><strong>报告编号:</strong>{{ data.pId }}</div>
<!--                <div class="textbox"><strong>姓名:</strong>{{ data.name }}</div>-->
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
                  <el-button type="text" @click="dialogVisible" style="padding:0;line-height: 4vh;margin-right: 1vw;font-size:2vh">新增术语</el-button>
                  <el-button type="text" @click="Camera" style="padding:0;line-height: 4vh;margin-right: 1vw;font-size:2vh">常用术语</el-button>
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
                <div class="tag-button-panging">
                  <el-dialog title="常用术语" :visible.sync="dialogFormVisible" width="1200px">
                    <div v-for="(item) in items">
                      <div>{{ item.name }}</div>
                      <button class="commentLabelBtn" :class="{ 'selected': isSelected}" type="primary"
                              v-for="itemc in item.label"
                              :key="itemc"
                              @click="putDown(itemc,$event)">{{ itemc }}
                      </button>
                    </div>
                    <div slot="footer" class="dialog-footer">
                      <el-button  @click="dialogFormVisible=false">取 消</el-button>
                      <el-button type="primary" @click="dialogForm">确 定</el-button>
                    </div>
                  </el-dialog>
                  <el-dialog title="新增术语" :visible.sync="dialogVisibleTag">
                    <el-tag
                      :key="tag"
                      v-for="tag in dynamicTags"
                      closable
                      :disable-transitions="false"
                      @close="handleCloseTag(tag)">
                      {{tag}}
                    </el-tag>
                    <el-input
                      class="input-new-tag"
                      v-if="inputVisible"
                      v-model="inputValue"
                      ref="saveTagInput"
                      size="small"
                      @keyup.enter.native="handleInputConfirm"
                      @blur="handleInputConfirm"
                    >
                    </el-input>
                    <el-button v-else class="button-new-tag" size="small" @click="showInput">+ 单击回车新增标签术语</el-button>
                    <div slot="footer" class="dialog-footer">
                      <el-button  @click="dialogVisibleTag=false">取 消</el-button>
                      <el-button type="primary" @click="termTag">确 定</el-button>
                    </div>
                  </el-dialog>
                </div>
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
          <div class="body" id="body">
          <!--            <div class="demo-image__preview">-->
          <!--              <el-image :src="baseImage"></el-image>-->
          <!--            </div>-->
          <div class="body-1">
            <div>
              <div id="I" class="line" @dblclick="clicktrue('I',data12.dataI)"></div>
            </div>
            <div>
              <div id="II" class="line" @dblclick="clicktrue('II',data12.dataII)"></div>
            </div>
            <div>
              <div id="III" class="line" @dblclick="clicktrue('III',data12.dataIII)"></div>
            </div>
            <div>
              <div id="aVR" class="line" @dblclick="clicktrue('aVR',data12.dataaVR)"></div>
            </div>
            <div>
              <div id="aVL" class="line" @dblclick="clicktrue('aVL',data12.dataaVL)"></div>
            </div>
            <div>
              <div id="aVF" class="line" @dblclick="clicktrue('aVF',data12.dataaVF)"></div>
            </div>
          </div>
          <div class="body-1">
            <div>
              <div id="V1" class="line" @dblclick="clicktrue('V1',data12.dataV1)"></div>
            </div>
            <div>
              <div id="V2" class="line" @dblclick="clicktrue('V2',data12.dataV2)"></div>
            </div>
            <div>
              <div id="V3" class="line" @dblclick="clicktrue('V3',data12.dataV3)"></div>
            </div>
            <div>
              <div id="V4" class="line" @dblclick="clicktrue('V4',data12.dataV4)"></div>
            </div>
            <div>
              <div id="V5" class="line" @dblclick="clicktrue('V5',data12.dataV5)"></div>
            </div>
            <div>
              <div id="V6" class="line" @dblclick="clicktrue('V6',data12.dataV6)"></div>
            </div>
          </div>
        </div>
        </div>
      </div>
      <child ref="drawShow" @closeMain="closeMain"></child>
    </div>
  </div>
</template>

<script>
import * as echarts from '@/views/ECGScreen/detail/echarts.min'
import $ from 'jquery';
import {getCommonTerms, addReport, getReportByPId, updateReport} from "@/api/report/report";
import {sendMsgToPatient} from "@/api/patient_management/patient_management";
import html2canvas from "html2canvas";
import {addOrUpdateTerm, getTerm} from "@/api/staticECG/staticECG";
import {addLabel} from "@/api/log_user/log_user";
import child from "@/views/staticECG/staticECG/child.vue";
var elementResizeDetectorMaker = require("element-resize-detector")
export default {
  name: "index",
  components: {child},
  data() {
    return {
      isSelected: false,//术语按钮没有被按下
      dialogFormVisible: false,
      items: [],//常用术语
      checkButton: [],
      pId: null,
      dynamicTags: ['标签一', '标签二', '标签三'],
      inputVisible: false,
      inputValue: '',
      dialogVisibleTag:null,
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
      open: false,
      pphone: "",
      baseImage: "",
      arr:[],
      datalabel:{
        waveLabel:"",
        beatLabel:""
      },
      graphic2:[],
      chartII:null,
      // lead:false,
      // radio:'',
      // xIndex:null,
      // delX:{key:null,value:null},//想要删除的点
      // subData:{
      //   P1:[],P2:[],P3:[],
      //   R1:[],R2:[],R3:[],
      //   T1:[],T2:[],T3:[],
      // },//提交标注信息
      // chartjump:null,
      // pointdata:[],//画点的option
      // title:'',
      // query:{
      //   userId:'',
      //   logId: '',
      //   waveLabel:''
      // },
    };
  },
  created() {
    var pId = this.$route.query.pId;
    if (pId) {
      this.pId = pId;
      getReportByPId(this.pId).then(response => {
        console.log("请求成功：", response.data)
        this.data.resultByDoctor = response.data.diagnosisConclusion
        this.data.doctorName = response.data.diagnosisDoctor
        this.data.diagnosisData = response.data.reportTime
        this.data.pphone = response.data.pphone
        this.data.pId = response.data.pId
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
    var show = sessionStorage.getItem(this.pId + "show");
    if (show) {
      this.get();
    }
  },
  methods: {

    dialogVisible(){
      getTerm().then(r=>{
        if (r.rows.length>0){
          this.dynamicTags = JSON.parse(r.rows[0].termText)
        }
        this.dialogVisibleTag = true
      })
    },
    termTag(){
      let obj = {
        "termText": JSON.stringify(this.dynamicTags)
      }
      addOrUpdateTerm(obj).then(r=>{
        this.$modal.msgSuccess("添加成功");
        this.dialogVisibleTag = false
      })

    },
    handleCloseTag(tag) {
      this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1);
    },

    showInput() {
      this.inputVisible = true;
      this.$nextTick(_ => {
        this.$refs.saveTagInput.$refs.input.focus();
      });
    },

    handleInputConfirm() {
      let inputValue = this.inputValue;
      if (inputValue) {
        this.dynamicTags.push(inputValue);
      }
      this.inputVisible = false;
      this.inputValue = '';
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

    //展开框
    clicktrue(title,data){
      console.log(this.datalabel)
      this.$refs.drawShow.getchart(data,this.pId,1,title,12,this.datalabel);
    },

    //按下常用术语按钮
    putDown(key,event) {
      // console.log(event.currentTarget.classList.toggle('selected'))
      event.currentTarget.classList.toggle('selected')
      console.log(this.arr)
        let index = this.arr.indexOf(key);
        //console.log(index)
        if(index !== -1){
          this.arr.splice(index,1);
        }else {
          this.arr.push(key);
        }
    },

    clickitem(e){
      e === this.radio ? this.radio = '' : this.radio = e
    },
    dialogForm(){
      if (this.data.resultByDoctor){
        this.data.resultByDoctor = this.data.resultByDoctor+','+this.arr.toString()
      }else {
        this.data.resultByDoctor =this.arr.toString()
      }

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
          _th.datalabel.waveLabel=data.result.waveLabel
          _th.datalabel.beatLabel=data.result.beatLabel
          for (var i = 0; i < _th.data12.dataII.length+1; i++) {
            _th.data12.x.push(i);
          }
          if(_th.data12.dataII.length==1000){
            $('.line').css({
              height:'15.5vh'
            });
          }else if(_th.data12.dataII.length==2000){
            $('.line').css({
              height:'7.6vh'
            });
          }else if(_th.data12.dataII.length==3000){
            $('.line').css({
              height:'7.6vh'
            });
          }
          for (var i = 0; i < 2000; i+=20) {
            _th.markdata.push({xAxis: i})
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
              right: '2',
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
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
              splitNumber: 51, // 横线数
              minInterval: 0.1, // 刻度间隔
              splitLine: {
                show: true, //让网格显示
                lineStyle: {//网格样式
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
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
                zlevel:99,
                lineStyle: {
                  normal: {
                    color: "#000000",
                    width: 1.5
                  }
                },
                markLine: {
                  symbol: "none",
                  silent: true,
                  lineStyle: {
                    type: "solid",
                    color: '#df8989',
                    width: 1
                  },
                  label: {
                    position: 'start', // 表现内容展示的位置
                    color: '#df8989'  // 展示内容颜色
                  },

                  data: _th.markdata
                }
              }
            ],
          });
          chartI.resize()
          $(window).resize(function () {
            chartI.resize();
          });
          _th.chartII = echarts.init(document.getElementById("II"));
          _th.chartII.clear()
          _th.chartII.setOption({
            animation:false,
            title: {
              text: "II",
              top: 5,
              left: 5,
            },
            grid: {
              left: '1',
              right: '2',
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
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
                zlevel:99,
                lineStyle: {
                  normal: {
                    color: "#000000",
                    width: 1.5
                  }
                },
                markLine: {
                  symbol: "none",
                  silent: true,
                  lineStyle: {
                    type: "solid",
                    color: '#df8989',
                    width: 1
                  },
                  label: {
                    position: 'start', // 表现内容展示的位置
                    color: '#df8989'  // 展示内容颜色
                  },

                  data: _th.markdata
                }

              }
            ],
          });
          //绘制文本
          // _th.addtext()
          $(window).resize(function () {
            _th.chartII.resize();
            // _th.addtext()
          });
          var chartIII = echarts.init(document.getElementById("III"));
          chartIII.clear()
          chartIII.setOption({
            animation:false,
            title: {
              text: "III",
              top: 5,
              left: 5,
            },
            grid: {
              left: '1',
              right: '2',
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
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
                zlevel:99,
                lineStyle: {
                  normal: {
                    color: "#000000",
                    width: 1.5
                  }
                },
                markLine: {
                  symbol: "none",
                  silent: true,
                  lineStyle: {
                    type: "solid",
                    color: '#df8989',
                    width: 1
                  },
                  label: {

                    position: 'start', // 表现内容展示的位置
                    color: '#df8989'  // 展示内容颜色
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
            animation:false,
            title: {
              text: "aVR",
              top: 5,
              left: 5,
            },
            grid: {
              left: '1',
              right: '2',
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
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
                zlevel:99,
                lineStyle: {
                  normal: {
                    color: "#000000",
                    width: 1.5
                  }
                },
                markLine: {
                  symbol: "none",
                  silent: true,
                  lineStyle: {
                    type: "solid",
                    color: '#df8989',
                    width: 1
                  },
                  label: {

                    position: 'start', // 表现内容展示的位置
                    color: '#df8989'  // 展示内容颜色
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
            animation:false,
            title: {
              text: "aVL",
              top: 5,
              left: 5,
            },
            grid: {
              left: '1',
              right: '2',
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
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
                zlevel:99,
                lineStyle: {
                  normal: {
                    color: "#000000",
                    width: 1.5
                  }
                },
                markLine: {
                  symbol: "none",
                  silent: true,
                  lineStyle: {
                    type: "solid",
                    color: '#df8989',
                    width: 1
                  },
                  label: {

                    position: 'start', // 表现内容展示的位置
                    color: '#df8989'  // 展示内容颜色
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
            animation:false,
            title: {
              text: "aVF",
              top: 5,
              left: 5,
            },
            grid: {
              left: '1',
              right: '2',
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
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
                zlevel:99,
                lineStyle: {
                  normal: {
                    color: "#000000",
                    width: 1.5
                  }
                },
                markLine: {
                  symbol: "none",
                  silent: true,
                  lineStyle: {
                    type: "solid",
                    color: '#df8989',
                    width: 1
                  },
                  label: {

                    position: 'start', // 表现内容展示的位置
                    color: '#df8989'  // 展示内容颜色
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
            animation:false,
            title: {
              text: "V1",
              top: 5,
              left: 5,
            },
            grid: {
              left: '1',
              right: '2',
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
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
                zlevel:99,
                lineStyle: {
                  normal: {
                    color: "#000000",
                    width: 1.5
                  }
                },
                markLine: {
                  symbol: "none",
                  silent: true,
                  lineStyle: {
                    type: "solid",
                    color: '#df8989',
                    width: 1
                  },
                  label: {

                    position: 'start', // 表现内容展示的位置
                    color: '#df8989'  // 展示内容颜色
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
            animation:false,
            title: {
              text: "V2",
              top: 5,
              left: 5,
            },
            grid: {
              left: '1',
              right: '2',
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
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
                zlevel:99,
                lineStyle: {
                  normal: {
                    color: "#000000",
                    width: 1.5
                  }
                },
                markLine: {
                  symbol: "none",
                  silent: true,
                  lineStyle: {
                    type: "solid",
                    color: '#df8989',
                    width: 1
                  },
                  label: {

                    position: 'start', // 表现内容展示的位置
                    color: '#df8989'  // 展示内容颜色
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
            animation:false,
            title: {
              text: "V3",
              top: 5,
              left: 5,
            },
            grid: {
              left: '1',
              right: '2',
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
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
                zlevel:99,
                lineStyle: {
                  normal: {
                    color: "#000000",
                    width: 1.5
                  }
                },
                markLine: {
                  symbol: "none",
                  silent: true,
                  lineStyle: {
                    type: "solid",
                    color: '#df8989',
                    width: 1
                  },
                  label: {

                    position: 'start', // 表现内容展示的位置
                    color: '#df8989'  // 展示内容颜色
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
            animation:false,
            title: {
              text: "V4",
              top: 5,
              left: 5,
            },
            grid: {
              left: '1',
              right: '2',
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
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
                zlevel:99,
                lineStyle: {
                  normal: {
                    color: "#000000",
                    width: 1.5
                  }
                },
                markLine: {
                  symbol: "none",
                  silent: true,
                  lineStyle: {
                    type: "solid",
                    color: '#df8989',
                    width: 1
                  },
                  label: {

                    position: 'start', // 表现内容展示的位置
                    color: '#df8989'  // 展示内容颜色
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
            animation:false,
            title: {
              text: "V5",
              top: 5,
              left: 5,
            },
            grid: {
              left: '1',
              right: '2',
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
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
                zlevel:99,
                lineStyle: {
                  normal: {
                    color: "#000000",
                    width: 1.5
                  }
                },
                markLine: {
                  symbol: "none",
                  silent: true,
                  lineStyle: {
                    type: "solid",
                    color: '#df8989',
                    width: 1
                  },
                  label: {

                    position: 'start', // 表现内容展示的位置
                    color: '#df8989'  // 展示内容颜色
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
            animation:false,
            title: {
              text: "V6",
              top: 5,
              left: 5,
            },
            grid: {
              left: '1',
              right: '2',
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
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
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
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
                zlevel:99,
                lineStyle: {
                  normal: {
                    color: "#000000",
                    width: 1.5
                  }
                },
                markLine: {
                  symbol: "none",
                  silent: true,
                  lineStyle: {
                    type: "solid",
                    color: '#df8989',
                    width: 1
                  },
                  label: {

                    position: 'start', // 表现内容展示的位置
                    color: '#df8989'  // 展示内容颜色
                  },

                  data: _th.markdata
                }
              }
            ],

          });
          $(window).resize(function () {
            chartV6.resize();
          });
          var erd = elementResizeDetectorMaker()
          erd.listenTo(document.getElementById("body"), function (element) {
            _th.$nextTick(function () {
              chartI.resize();
              _th.chartII.resize();
              // _th.addtext()
              chartIII.resize();
              chartaVF.resize();
              chartaVR.resize();
              chartaVL.resize();
              chartV1.resize();
              chartV2.resize();
              chartV3.resize();
              chartV4.resize();
              chartV5.resize();
              chartV6.resize();
            })
          })

        },
        error: function (data) {
          alert("数据请求错误,请刷新页面或联系管理员")
          loading.close()
          console.log("请求失败：", data)
        }
      })
    },
    //重绘所有点之间的文本
    // addtext(){
    //   this.graphic2.length=0
    //   let beatLabel=JSON.parse(this.datalabel.beatLabel)
    //   var arr2=beatLabel['0']
    //   let beat2=[]
    //   // console.log(arr2)
    //   for (let key in arr2) {
    //     beat2.push(...arr2[key])
    //   }
    //   beat2.sort((a,b)=>a - b)
    //
    //   var length2=beat2.length
    //   //刻度线
    //   for (let i = 0; i <length2; i++) {
    //     var point1=this.chartII.convertToPixel({seriesIndex: 0}, [beat2[i], 3])
    //     let text1={
    //       type: 'line',
    //       style: {
    //         stroke: '#333',
    //         lineWidth:1.5,
    //         lineDash:[]
    //       },
    //       shape: {
    //         x1: point1[0],
    //         y1: 1,
    //         x2: point1[0],
    //         y2: 11
    //       },
    //       z:100
    //     }
    //     this.graphic2.push(text1)
    //     if(i==length2-1){
    //       continue
    //     }
    //     var x1=beat2[i]
    //     var x2=beat2[i+1]
    //     // console.log(x1,x2)
    //     var time=(((x2-x1)/25)*0.25); //时间 s
    //     var heart=(60/time).toFixed(1) //心率
    //     time=(time*1000).toFixed(0)
    //     //文本值
    //     var point2=this.chartII.convertToPixel({seriesIndex: 0}, [(x2-x1)/2+x1, 3])
    //     // console.log(x)
    //     let text2={
    //       type:'text',
    //       x: point2[0]-15,
    //       y:1,
    //       z: 999,
    //       style:{
    //         text: time+`\n${heart}`,
    //         fill: '#000000',
    //         fontWeight: 400,
    //         fontSize: 13,
    //       },
    //
    //     }
    //     this.graphic2.push(text2)
    //   }
    //   var chartOption2 = this.chartII.getOption();
    //   chartOption2.graphic = this.graphic2;
    //   this.chartII.setOption(chartOption2,true);
    //
    //   // console.log(this.graphic2)
    // },
    closeMain(val){
      console.log(val)
      // this.datalabel.beatLabel=val
      // this.addtext()
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
      if (this.data.doctorName==''||this.data.doctorName==null){
        this.$message({
          type: 'error',
          message: '诊断医生不能为空!'
        });
        return
      }
      const date = new Date();
      const year = date.getFullYear().toString().padStart(4, '0');
      const month = (date.getMonth() + 1).toString().padStart(2, '0');
      const day = date.getDate().toString().padStart(2, '0');
      const hour = date.getHours().toString().padStart(2, '0');
      const minute = date.getMinutes().toString().padStart(2, '0');
      const second = date.getSeconds().toString().padStart(2, '0');
      var form = {
        pId: this.pId,
        diagnosisStatus: '1',
        startDateTime: `${year}-${month}-${day} ${hour}:${minute}:${second}`,
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
        _th.dialogFormVisible=true
        console.log(_th.items);
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
      margin-left: 1.8vw;
      margin-top: 3vh;
      .textbox{
        width:45%;
        margin-bottom: 2vh;
        font-size: 2.1vh;
      }
      .textBoxBottom{
        margin-bottom: 10px;
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
      //border: 1px darkgray solid;
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
  flex-direction: column;
  .input{
    display: flex;
    flex-direction: row;
    margin-top: 1vh;
    margin-left: 2vw;
    ::v-deep .el-input--medium .el-input__inner{
      width: 60%;
    }
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
  justify-content: center;
  width: 100%;
}
.showbox {
  //display: none;
  user-select:none;
  position: absolute;
  width: 100%;
  height:82vh;
  border: 1px solid #6EDDF1;
  top: 54vh;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 2000;
  background-color: rgb(255, 255, 255);
  span{
    display: inline-block;
  }
}
.icon{
  font-size: 2vw;
}
.menu{
  /*这个样式不写，右键弹框会一直显示在画布的左下角*/
  position: absolute;
  background: rgba(255, 255, 255);
  left: -99999px;
  top: -999999px;
  padding: 0.1vw;
  border-radius: 0.5vw;
}
.button{
  height: 3vh;
  width: 2.5vw;
  display: inline-flex;
  text-align: center;
  border-radius: 0.5vw;
  border: 1px solid #000000;
  font-size: 0.5vw;
  font-weight: 700;
  justify-content: center;
  align-items: center;
}
.button:hover{
  color: #FFFFFF;
  background-color: #df0202;
}
#charts {
  /*position: absolute;*/
  width: 100%;
  height: 100%;
  //top: 50%;
  //left: 50%;
  //transform: translate(-50%, -50%);
  //border: 1px solid #000000;
  /*background-color: #06732b;*/
}
.noName{
  //display: inline-flex;

  //width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;

}

::v-deep .noName .el-radio-group{
  display: block;
  //width: 98%;
}
.btn3 {
  color: #b33939;
  border: 1px solid #b33939;
  border-radius: .5em;
  background-color: transparent;
  text-transform: uppercase;
  font-weight: 500;
  //flex: 1;
  height: 4vh;
  font-size: 1vw;
  text-align: center;
  padding: 0;
  margin: 0.5vw;
  width: 4vw;
}
.btn {
  height: 4vh;
  width: 4vw;
  color: #136d87;
  border: 1px solid #136d87;
  border-radius: 1vw;
  background-color: transparent;
  text-transform: uppercase;
  font-weight: 500;
  position: absolute;
  top: 0;
  right: 2%;
}
.btn:hover {
  cursor: pointer;
  color: #136d87;
  //border: 0 ;
  background-color: #4cc9f0;
}

.el-radio-group{
  display: flex;
  justify-content: space-around;
}
.el-radio-button:focus:not(.is-focus):not(:active):not(.is-disabled) {
  -webkit-box-shadow: 0 0px 0px #ccc;
  box-shadow: 0 0px 0px #ccc;
}

.body .body-1 {
  flex: 1;
  width: 99.5%;
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
  height: 5vw;
  width: 100%;
  margin: 0;
  padding: 0;
}



.doctordata{
  width: 58vw;
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



.commentLabelBtn {
  width: auto;
  height: 28px;
  margin: 8px 8px 8px 0;
  background: #eaeff5;
  color: #000000 !important;
  border-radius: 0.5vw;
  border: 1px solid #000000;
}
.el-tag + .el-tag {
  margin-left: 10px;
}
.button-new-tag {
  margin-left: 10px;
  height: 32px;
  padding-top: 0;
  padding-bottom: 0;
}
.input-new-tag {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
}
.tag-button-panging{
  ::v-deep .commentLabelBtn {
    padding: 10px 20px !important;
    line-height: 8px;
  }
  ::v-deep .el-button {
    padding: 10px 20px !important;
  }
  ::v-deep .button-new-tag[data-v-700a2669] {
    line-height: 10px;
  }
  ::v-deep .el-tag {
  padding: 0 10px !important;
}
}

</style>
