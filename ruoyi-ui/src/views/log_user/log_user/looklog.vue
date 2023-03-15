<template>
  <div class="main">
    <div class="top">
      <div class="topLeft">
        <div class="message">患者信息</div>
        <div class="messageDetail">
          <span id="pID">患者ID：{{ message.pid }}</span>
          <span id="logID">日志ID：{{ message.lid }}</span>
          <span id="psex">性别：{{ message.sex }}</span>
          <span id="pAge">年龄：{{ message.age }}</span>
          <span id="clock">时间：{{ message.time }}</span>
        </div>
      </div>
      <div class="topMiddle">
        <div class="warning">预警类型</div>
        <div class="warningDetail">
          <form id="loginForm" name="loginForm" style="padding-left:1vw">
            <template>
              <el-select v-model="value" placeholder="请选择">
                <el-option-group
                  v-for="group in options"
                  :key="group.label"
                  :label="group.label">
                  <el-option
                    v-for="item in group.options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-option-group>
              </el-select>
            </template>
            <input type="button" id="btn1" value="提交" @click="uploadSelect()">
          </form>
        </div>
      </div>
      <div class="topRight">
        <div class="warning">质量评估</div>
        <div class="allin">
          <button class="green" onclick="allA()">全A</button>
          <button class="red" onclick="allB()">全B</button>
          <button class="red" onclick="allC()">全C</button>
          <button class="red" onclick="allD()">全D</button>
        </div>
      </div>
    </div>
    <div class="bottom">
      <div class="bottomLeft">
        <div class="xinDian">心电图</div>
        <div class="echarts">
          <div class="container">
            <div class="chart" id="I"></div>
            <button class="btn" id="I导联" onclick="showchart(this,Iy,timex)">展开</button>
            <span class="light" id="Ilight" onclick="changeColor(this)"></span>
          </div>
          <div class="container">
            <div class="chart" id="II"></div>
            <button class="btn" id="II导联" onclick="showchart(this,IIy,timex)">展开</button>
            <span class="light" id="IIlight" onclick="changeColor(this)"></span>
          </div>
          <div class="container">
            <div class="chart" id="III"></div>
            <button class="btn" id="III导联" onclick="showchart(this,IIIy,timex)">展开</button>
            <span class="light" id="IIIlight" onclick="changeColor(this)"></span>
          </div>
          <div class="container">
            <div class="chart" id="aVR"></div>
            <button class="btn" id="aVR导联" onclick="showchart(this,aVRy,timex)">展开</button>
            <span class="light" id="aVRlight" onclick="changeColor(this)"></span>
          </div>
          <div class="container">
            <div class="chart" id="aVL"></div>
            <button class="btn" id="aVL导联" onclick="showchart(this,aVLy,timex)">展开</button>
            <span class="light" id="aVLlight" onclick="changeColor(this)"></span>
          </div>
          <div class="container">
            <div class="chart" id="aVF"></div>
            <button class="btn" id="aVF导联" onclick="showchart(this,aVFy,timex)">展开</button>
            <span class="light" id="aVFlight" onclick="changeColor(this)"></span>
          </div>
          <div class="container">
            <div class="chart" id="V1"></div>
            <button class="btn" id="V1导联" onclick="showchart(this,V1y,timex)">展开</button>
            <span class="light" id="V1light" onclick="changeColor(this)"></span>
          </div>
          <div class="container">
            <div class="chart" id="V2"></div>
            <button class="btn" id="V2导联" onclick="showchart(this,V2y,timex)">展开</button>
            <span class="light" id="V2light" onclick="changeColor(this)"></span>
          </div>
          <div class="container">
            <div class="chart" id="V3"></div>
            <button class="btn" id="V3导联" onclick="showchart(this,V3y,timex)">展开</button>
            <span class="light" id="V3light" onclick="changeColor(this)"></span>
          </div>
          <div class="container">
            <div class="chart" id="V4"></div>
            <button class="btn" id="V4导联" onclick="showchart(this,V4y,timex)">展开</button>
            <span class="light" id="V4light" onclick="changeColor(this)"></span>
          </div>
          <div class="container">
            <div class="chart" id="V5"></div>
            <button class="btn" id="V5导联" onclick="showchart(this,V5y,timex)">展开</button>
            <span class="light" id="V5light" onclick="changeColor(this)"></span>
          </div>
          <div class="container">
            <div class="chart" id="V6"></div>
            <button class="btn" id="V6导联" onclick="showchart(this,V6y,timex)">展开</button>
            <span class="light" id="V6light" onclick="changeColor(this)"></span>
          </div>
        </div>
      </div>
      <div class="bottomRight">
        <div class="xinDian"></div>
        <div class="quality">
          <div class="lights">
            <div class="abcd">
              <el-radio v-model="noise_level.Ilevel" label="A">A</el-radio>
              <el-radio v-model="noise_level.Ilevel" label="B">B</el-radio>
              <el-radio v-model="noise_level.Ilevel" label="C">C</el-radio>
              <el-radio v-model="noise_level.Ilevel" label="D">D</el-radio>
            </div>
            <div class="abcd">
              <el-radio v-model="noise_level.IIlevel" label="A">A</el-radio>
              <el-radio v-model="noise_level.IIlevel" label="B">B</el-radio>
              <el-radio v-model="noise_level.IIlevel" label="C">C</el-radio>
              <el-radio v-model="noise_level.IIlevel" label="D">D</el-radio>
            </div>
            <div class="abcd">
              <el-radio v-model="noise_level.IIIlevel" label="A">A</el-radio>
              <el-radio v-model="noise_level.IIIlevel" label="B">B</el-radio>
              <el-radio v-model="noise_level.IIIlevel" label="C">C</el-radio>
              <el-radio v-model="noise_level.IIIlevel" label="D">D</el-radio>
            </div>
            <div class="abcd">
              <el-radio v-model="noise_level.aVRlevel" label="A">A</el-radio>
              <el-radio v-model="noise_level.aVRlevel" label="B">B</el-radio>
              <el-radio v-model="noise_level.aVRlevel" label="C">C</el-radio>
              <el-radio v-model="noise_level.aVRlevel" label="D">D</el-radio>
            </div>
            <div class="abcd">
              <el-radio v-model="noise_level.aVLlevel" label="A">A</el-radio>
              <el-radio v-model="noise_level.aVLlevel" label="B">B</el-radio>
              <el-radio v-model="noise_level.aVLlevel" label="C">C</el-radio>
              <el-radio v-model="noise_level.aVLlevel" label="D">D</el-radio>
            </div>
            <div class="abcd">
              <el-radio v-model="noise_level.aVFlevel" label="A">A</el-radio>
              <el-radio v-model="noise_level.aVFlevel" label="B">B</el-radio>
              <el-radio v-model="noise_level.aVFlevel" label="C">C</el-radio>
              <el-radio v-model="noise_level.aVFlevel" label="D">D</el-radio>
            </div>
            <div class="abcd">
              <el-radio v-model="noise_level.V1level" label="A">A</el-radio>
              <el-radio v-model="noise_level.V1level" label="B">B</el-radio>
              <el-radio v-model="noise_level.V1level" label="C">C</el-radio>
              <el-radio v-model="noise_level.V1level" label="D">D</el-radio>
            </div>
            <div class="abcd">
              <el-radio v-model="noise_level.V2level" label="A">A</el-radio>
              <el-radio v-model="noise_level.V2level" label="B">B</el-radio>
              <el-radio v-model="noise_level.V2level" label="C">C</el-radio>
              <el-radio v-model="noise_level.V2level" label="D">D</el-radio>
            </div>
            <div class="abcd">
              <el-radio v-model="noise_level.V3level" label="A">A</el-radio>
              <el-radio v-model="noise_level.V3level" label="B">B</el-radio>
              <el-radio v-model="noise_level.V3level" label="C">C</el-radio>
              <el-radio v-model="noise_level.V3level" label="D">D</el-radio>
            </div>
            <div class="abcd">
              <el-radio v-model="noise_level.V4level" label="A">A</el-radio>
              <el-radio v-model="noise_level.V4level" label="B">B</el-radio>
              <el-radio v-model="noise_level.V4level" label="C">C</el-radio>
              <el-radio v-model="noise_level.V4level" label="D">D</el-radio>
            </div>
            <div class="abcd">
              <el-radio v-model="noise_level.V5level" label="A">A</el-radio>
              <el-radio v-model="noise_level.V5level" label="B">B</el-radio>
              <el-radio v-model="noise_level.V5level" label="C">C</el-radio>
              <el-radio v-model="noise_level.V5level" label="D">D</el-radio>
            </div>
            <div class="abcd">
              <el-radio v-model="noise_level.V6level" label="A">A</el-radio>
              <el-radio v-model="noise_level.V6level" label="B">B</el-radio>
              <el-radio v-model="noise_level.V6level" label="C">C</el-radio>
              <el-radio v-model="noise_level.V6level" label="D">D</el-radio>
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
  name: "Index",
  data() {
    return {
      a: "",//获取到导联的id
      b: "",//获取到导联的id
      noise: {
        list: [],//12导联噪声数据
        lists: [],//12导联质量评估数据
        //通过字典将radio和light一一对应
//红绿框显示
      },
      noise_level: {
        Ilevel: "B",
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
        user_id: "146",
        pid: "",
        logid: "4d71efb8-441c-58e4-ae17-2bcfe89771c9",
        sex: "",
        age: "",
        time: "",
      },
      options: [
        {
          label: '正常心电图',
          options: [{
            value: '正常心电图',
            label: '正常心电图'
          },]
        }, {
          label: '心动过速',
          options: [{
            value: '室性心动过速',
            label: '室性心动过速'
          }, {
            value: '室上性心动过速',
            label: '室上性心动过速'
          }, {
            value: '交界性心动过速',
            label: '交界性心动过速'
          }, {
            value: '房性心动过速',
            label: '房性心动过速'
          },]
        },
        {
          label: '心律',
          options: [{
            value: '窦性心律',
            label: '窦性心律'
          }, {
            value: '异位心律',
            label: '异位心律'
          }, {
            value: '房性心律',
            label: '房性心律'
          }, {
            value: '室性心律',
            label: '室性心律'
          }, {
            value: '交界性心律',
            label: '交界性心律'
          }, {
            value: '起搏心律',
            label: '起搏心律'
          }]
        }, {
          label: '早搏/逸搏',
          options: [{
            value: '房性逸搏',
            label: '房性逸搏'
          }, {
            value: '房性早搏',
            label: '房性早搏'
          }, {
            value: '房性停搏',
            label: '房性停搏'
          }, {
            value: '室性逸搏',
            label: '室性逸搏'
          }, {
            value: '室性早搏',
            label: '室性早搏'
          }, {
            value: '交界性逸搏',
            label: '交界性逸搏'
          },
            {
              value: '交界性早搏',
              label: '交界性早搏'
            }, {
              value: '交界性停搏',
              label: '交界性停搏'
            }, {
              value: '窦性停搏',
              label: '窦性停搏'
            }, {
              value: '心室停搏',
              label: '心室停搏'
            }, {
              value: '全心停搏',
              label: '全心停搏'
            }]
        }, {
          label: '房室肥大',
          options: [{
            value: '左心室肥大',
            label: '左心室肥大'
          }, {
            value: '左心房肥大',
            label: '左心房肥大'
          }, {
            value: '右心室肥大',
            label: '右心室肥大'
          }, {
            value: '右心房肥大',
            label: '右心房肥大'
          },]
        },
        {
          label: '颤动/扑动',
          options: [{
            value: '心房颤动',
            label: '心房颤动'
          }, {
            value: '心房扑动',
            label: '心房扑动'
          }, {
            value: '心室颤动',
            label: '心室颤动'
          }, {
            value: '心室扑动',
            label: '心室扑动'
          }],
        }, {
          label: '其他',
          options: [{
            value: '室内差异性传导',
            label: '室内差异性传导'
          }, {
            value: '心室预激波',
            label: '心室预激波'
          }, {
            value: '早期复极',
            label: '早期复极'
          }, {
            value: '亚急性',
            label: '亚急性'
          }, {
            value: '急性',
            label: '急性'
          }, {
            value: '陈旧性',
            label: '陈旧性'
          }, {
            value: '心电图危急值',
            label: '心电图危急值'
          }],
        }, {
          label: '预测猝死',
          options: [{
            value: '预激综合症',
            label: '预激综合症'
          }, {
            value: 'ST改变',
            label: 'ST改变'
          }, {
            value: 'ST-T改变',
            label: 'ST-T改变'
          }, {
            value: 'T波异常',
            label: 'T波异常'
          }, {
            value: 'QT间期延长',
            label: 'QT间期延长'
          }, {
            value: 'QTc间期延长',
            label: 'QTc间期延长'
          }, {
            value: '可见U波',
            label: '可见U波'
          }],
        }, {
          label: '心肌梗死',
          options: [{
            value: '前间壁心肌梗死',
            label: '前间壁心肌梗死'
          }, {
            value: '前侧壁心肌梗死',
            label: '前侧壁心肌梗死'
          }, {
            value: '广泛前壁心肌梗死',
            label: '广泛前壁心肌梗死'
          }, {
            value: '前壁心肌梗死',
            label: '前壁心肌梗死'
          }, {
            value: '下壁心肌梗死',
            label: '下壁心肌梗死'
          }, {
            value: '下后壁心肌梗死',
            label: '下后壁心肌梗死'
          }, {
            value: '后壁心肌梗死',
            label: '后壁心肌梗死'
          }, {
            value: '下侧壁心肌梗死',
            label: '下侧壁心肌梗死'
          }, {
            value: '高侧壁心肌梗死',
            label: '高侧壁心肌梗死'
          }, {
            value: '侧壁心肌梗死',
            label: '侧壁心肌梗死'
          }, {
            value: '心内膜下心肌梗死',
            label: '心内膜下心肌梗死'
          }, {
            value: '右室心肌梗死',
            label: '右室心肌梗死'
          }, {
            value: '非ST抬高型心肌梗死',
            label: '非ST抬高型心肌梗死'
          }, {
            value: '心尖部心肌梗死',
            label: '心尖部心肌梗死'
          }, {
            value: '间壁心肌梗死',
            label: '间壁心肌梗死'
          }],
        }, {
          label: '传导阻滞',
          options: [{
            value: 'I度房室传导阻滞',
            label: 'I度房室传导阻滞'
          }, {
            value: 'II度房室传导阻滞',
            label: 'II度房室传导阻滞'
          }, {
            value: 'II度I型房室传导阻滞',
            label: 'II度I型房室传导阻滞'
          }, {
            value: 'II度II型房室传导阻滞',
            label: 'II度II型房室传导阻滞'
          }, {
            value: 'III度房室传导阻滞',
            label: 'III度房室传导阻滞'
          }, {
            value: '高度房室传导阻滞',
            label: '高度房室传导阻滞'
          }, {
            value: '左前分支传导阻滞',
            label: '左前分支传导阻滞'
          }, {
            value: '下侧壁心肌梗死',
            label: '下侧壁心肌梗死'
          }, {
            value: '左后分支传导阻滞',
            label: '左后分支传导阻滞'
          }, {
            value: '完全性左束支传导阻滞',
            label: '完全性左束支传导阻滞'
          }, {
            value: '不完全性左束支传导阻滞',
            label: '不完全性左束支传导阻滞'
          }, {
            value: '完全性右束支传导阻滞',
            label: '完全性右束支传导阻滞'
          }, {
            value: '不完全性右束支传导阻滞',
            label: '不完全性右束支传导阻滞'
          }, {
            value: '非特异性室内传导阻滞',
            label: '非特异性室内传导阻滞'
          },],
        }
      ],
      value: '不正常',
    };
  },

  created() {

  },
  mounted() {
    this.getMessage()
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank");
    },
    getMessage() {
      var Iy = [];
      var IIy = [];
      var IIIy = [];
      var aVLy = [];
      var aVFy = [];
      var aVRy = [];
      var V1y = [];
      var V2y = [];
      var V3y = [];
      var V4y = [];
      var V5y = [];
      var V6y = [];
      var timex = [];
      var seriesdata = []
      var seriesdata1 = []
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
        success: function (jsonResult) {
          console.log(jsonResult);
          _th.message.pid = jsonResult.result.patientid;
          _th.message.age = Number(jsonResult.result.age).toFixed(0);
          _th.message.sex = jsonResult.result.sex;
          _th.message.time = jsonResult.result.clockTime;
          _th.light(jsonResult)
          _th.level(jsonResult)
          if (_th.message.devicesn != null) {
            (function () {
              var i;
              for (var k = 0; k <= jsonResult.result.I.length; k++) {
                timex.push(k / 100 + '秒')
              }
              console.log(jsonResult.result.I.length)
              for (i = 0; i < jsonResult.result.I.length; i++) {
                Iy.push(jsonResult.result.I[i]);
                IIy.push(jsonResult.result.II[i]);
                IIIy.push(jsonResult.result.III[i]);
                aVLy.push(jsonResult.result.aVL[i]);
                aVRy.push(jsonResult.result.aVR[i]);
                aVFy.push(jsonResult.result.aVF[i]);
                V1y.push(jsonResult.result.V1[i]);
                V2y.push(jsonResult.result.V2[i]);
                V3y.push(jsonResult.result.V3[i]);
                V4y.push(jsonResult.result.V4[i]);
                V5y.push(jsonResult.result.V5[i]);
                V6y.push(jsonResult.result.V6[i]);
              }

              seriesdata = [
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

                {yAxis: -1}, {yAxis: -0.5}, {yAxis: 0}, {yAxis: 0.5}, {yAxis: 1},]
              seriesdata1 = [{xAxis: 0},
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
              if (jsonResult.result.I.length === 1000) seriesdata = seriesdata1
              chartI.clear()
              chartI.setOption({
                animation: false,
                animationDuration: 9900,
                animationEasing: "linear",
                animationEasingUpdate: 'linear',
                animationDurationUpdate: 10000,
                animationDelayUpdate: 0,
                animationThreshold: 10000,
                backgroundColor: "#ffffff",
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
                  right: 2,
                  top: 2,
                  bottom: 2
                },
                xAxis: {
                  boundaryGap: true,
                  data: timex,
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
                  axisLabel: { //修改坐标系字体颜色
                    show: false,
                    textStyle: {
                      color: "#000000"
                    }
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
                  name: 'I导联',
                  type: 'line',
                  data: Iy,
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
                  right: 2,
                  top: 2,
                  bottom: 2
                },

                xAxis: {
                  boundaryGap: true,
                  data: timex,
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
                  axisLabel: { //修改坐标系字体颜色
                    show: false,
                    textStyle: {
                      color: "#000000"
                    }
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
                  data: IIy,
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
                  right: 2,
                  top: 2,
                  bottom: 2
                },
                xAxis: {
                  boundaryGap: true,
                  data: timex,
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
                  axisLabel: { //修改坐标系字体颜色
                    show: false,
                    textStyle: {
                      color: "#000000"
                    }
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
                  data: V1y,
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
                  right: 2,
                  top: 2,
                  bottom: 2
                },
                xAxis: {
                  boundaryGap: true,
                  data: timex,
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
                  axisLabel: { //修改坐标系字体颜色
                    show: false,
                    textStyle: {
                      color: "#000000"
                    }
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
                  data: V2y,
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
                  right: 2,
                  top: 2,
                  bottom: 2
                },
                xAxis: {
                  boundaryGap: true,
                  data: timex,
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
                  axisLabel: { //修改坐标系字体颜色
                    show: false,
                    textStyle: {
                      color: "#000000"
                    }
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
                  data: V3y,
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
                  right: 2,
                  top: 2,
                  bottom: 2
                },

                xAxis: {
                  boundaryGap: true,
                  data: timex,
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
                  axisLabel: { //修改坐标系字体颜色
                    show: false,
                    textStyle: {
                      color: "#000000"
                    }
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
                  data: V4y,
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
                  right: 2,
                  top: 2,
                  bottom: 2
                },
                xAxis: {
                  boundaryGap: true,
                  data: timex,
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
                  axisLabel: { //修改坐标系字体颜色
                    show: false,
                    textStyle: {
                      color: "#000000"
                    }
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
                  data: V5y,
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
                  right: 2,
                  top: 2,
                  bottom: 2
                },
                xAxis: {
                  boundaryGap: true,
                  data: timex,
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
                  axisLabel: { //修改坐标系字体颜色
                    show: false,
                    textStyle: {
                      color: "#000000"
                    }
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
                  data: V6y,
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
                  right: 2,
                  top: 2,
                  bottom: 2
                },

                xAxis: {
                  boundaryGap: true,
                  data: timex,
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
                  axisLabel: { //修改坐标系字体颜色
                    show: false,
                    textStyle: {
                      color: "#000000"
                    }
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
                  data: IIIy,
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
                  right: 2,
                  top: 2,
                  bottom: 2
                },
                xAxis: {
                  boundaryGap: true,
                  data: timex,
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
                  axisLabel: { //修改坐标系字体颜色
                    show: false,
                    textStyle: {
                      color: "#000000"
                    }
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
                  data: aVLy,
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
                  right: 2,
                  top: 2,
                  bottom: 2
                },
                xAxis: {
                  boundaryGap: true,
                  data: timex,
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
                  axisLabel: { //修改坐标系字体颜色
                    show: false,
                    textStyle: {
                      color: "#000000"
                    }
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
                  data: aVFy,
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
                  right: 2,
                  top: 2,
                  bottom: 2
                },
                xAxis: {
                  boundaryGap: true,
                  data: timex,
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
                  axisLabel: { //修改坐标系字体颜色
                    show: false,
                    textStyle: {
                      color: "#000000"
                    }
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
                  data: aVRy,
                  smooth: 0 //显示为平滑的曲线*/
                }
              });
              $(window).resize(function () {
                chartaVR.resize();
              });
            })();
          } else {
            this.$message.error("设备未连接");
          }
          console.log(jsonResult.result)
        },
        error: function (data) {
          this.$message.error("数据获取失败");
        }
      });
    },
    //判断红绿颜色
    light(data) {
      this.noise.list = data.result.noise
      console.log(this.noise.list)
      for (var key in this.noise.list) {
        if (this.noise.list[key] === 1) {
          let temp = document.getElementById(key)
          temp.style.backgroundColor = "red"
        } else {
          let temp = document.getElementById(key)
          temp.style.backgroundColor = "greenyellow"
        }
      }
    },
    //ABCD等级的判断
    level(data) {
      this.noise_level = data.result.noise_level
      console.log("传的ABCD的等级", this.noise_level)
    },
    //修改红绿颜色框的颜色
    changeColor(tid) {
      this.b = tid.id;
      let temp = document.getElementById(this.b)
      if (this.noise.list[this.b] === 0) {
        this.noise.list[this.b] = 1;
        temp.style.backgroundColor = "red";
        console.log(this.noise.list)
      } else {
        this.noise.list[this.b] = 0;
        temp.style.backgroundColor = "greenyellow";
        console.log(this.noise.list)
      }

    },
    //全为A
    allA() {
      var vueA = this
      $("input:radio").each(function () {
        $("input:radio[value='A']").prop('checked', 'checked')
        vueA.noise.lists[$(this)[0].id] = 'A';
      })
      for (var key in this.noise.list) {
        this.noise.list[key] = 0
        let temp = document.getElementById(key)
        temp.style.backgroundColor = "greenyellow"
      }
      console.log(this.noise.list)
    },
    //全为B
    allB() {
      var vueB = this
      $("input:radio").each(function () {
        $("input:radio[value='B']").prop('checked', 'checked')
        vueB.noise.list[$(this)[0].id] = 'B';
      })
      for (var key in this.noise.list) {
        this.noise.list[key] = 1
        let temp = document.getElementById(key)
        temp.style.backgroundColor = "red"
      }
      console.log(this.noise.list)
    },
    //全为C
    allC() {
      var vueC = this
      $("input:radio").each(function () {
        $("input:radio[value='C']").prop('checked', 'checked')
        vueC.noise.lists[$(this)[0].id] = 'C';
      })
      for (var key in this.noise.list) {
        this.noise.list[key] = 1
        let temp = document.getElementById(key)
        temp.style.backgroundColor = "red"
      }
      console.log(this.noise.list)
    },
//全为D
    allD() {
      var vueD = this
      $("input:radio").each(function () {
        $("input:radio[value='D']").prop('checked', 'checked')
        vueD.noise.list[$(this)[0].id] = 'D';
      })
      for (var key in this.noise.list) {
        this.noise.list[key] = 1
        let temp = document.getElementById(key)
        temp.style.backgroundColor = "red"
      }
      console.log(this.noise.list)
    },
    //修改等级的程度
    radios() {
      var vue = this
      $("input:radio:checked").each(function () {
        console.log($(this)[0].id);
        console.log($(this).val());
        vue.noise.lists[$(this)[0].id] = $(this).val();
        if ($(this).val() === 'A') {
          console.log(vue.lightlevel[$(this)[0].id])
          vue.noise.lists[vue.lightlevel[$(this)[0].id]] = 0
          let temp = document.getElementById(vue.lightlevel[$(this)[0].id])
          temp.style.backgroundColor = "greenyellow"
        } else if ($(this).val() === 'B') {
          console.log(vue.lightlevel[$(this)[0].id])
          vue.noise.lists[vue.lightlevel[$(this)[0].id]] = 1
          let temp = document.getElementById(vue.lightlevel[$(this)[0].id])
          temp.style.backgroundColor = "red"
        } else if ($(this).val() === 'C') {
          console.log(vue.lightlevel[$(this)[0].id])
          vue.noise.lists[vue.lightlevel[$(this)[0].id]] = 1
          let temp = document.getElementById(vue.lightlevel[$(this)[0].id])
          temp.style.backgroundColor = "red"
        } else if ($(this).val() === 'D') {
          console.log(vue.lightlevel[$(this)[0].id])
          vue.noise.lists[vue.lightlevel[$(this)[0].id]] = 1
          let temp = document.getElementById(vue.lightlevel[$(this)[0].id])
          temp.style.backgroundColor = "red"
        }
      })
      console.log(vue.noise.lists);
    },
    uploadSelect() {

    },
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
  width: 25%;
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

form input {
  width: 3vw;
  height: 2vw;
  margin: 1vw;
}


.bottom {
  height: 70vw;
  width: 100%;
  border-top: 1px solid #000000;
  display: flex;
  flex-direction: row;
  flex-wrap: nowrap;
  justify-content: flex-start;
}

.bottomLeft {
  width: 75%;
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
  height: 5vw;
  position: absolute;
  top: 0;
  right: 0;
  background-color: greenyellow;
}

.light:hover {
  cursor: pointer;
  border: 1px solid #000000;
}

.bottomRight {
  width: 25%;
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
  //background-color: #fff;
}

.abcd {
  display: flex;
  justify-content: flex-start;
  flex-grow: 1;
  width: 100%;
  border-top: 1px solid #000000;
  background-color: #71f9d5;
}

.allin {
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 0 auto;
  height: 80%;
  width: 100%;
  //background-color: #62be38;
}

.allin .red {
  display: inline-block;
  outline: none;
  text-align: center;
  text-decoration: none;
  border-radius: .5em;
  width: 4vw;
  height: 4vw;
  font-size: 1.5vw;
  background-color: red;
  margin: 0.5vw;
}

.allin .green {
  display: inline-block;
  outline: none;
  text-align: center;
  text-decoration: none;
  border-radius: .5em;
  width: 4vw;
  height: 4vw;
  font-size: 1.5vw;
  margin: 0.5vw;
  background-color: greenyellow;
}
</style>





























