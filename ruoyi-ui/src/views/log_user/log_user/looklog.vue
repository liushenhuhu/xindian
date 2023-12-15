<template>
  <div class="main">
    <div class="showbox" id="jump" v-show="show" @contextmenu.prevent>
      <div class="noName" v-show="lead">
        <el-radio-group  v-model="radio">
          <el-radio-button @click.native.prevent="clickitem('P1')" label="P1">P1</el-radio-button>
          <el-radio-button @click.native.prevent="clickitem('P2')" label="P2">P2</el-radio-button>
          <el-radio-button @click.native.prevent="clickitem('P3')" label="P3">P3</el-radio-button>
          <el-radio-button @click.native.prevent="clickitem('R1')" label="R1">R1</el-radio-button>
          <el-radio-button @click.native.prevent="clickitem('R2')" label="R2">R2</el-radio-button>
          <el-radio-button @click.native.prevent="clickitem('R3')" label="R3">R3</el-radio-button>
          <el-radio-button @click.native.prevent="clickitem('T1')" label="T1">T1</el-radio-button>
          <el-radio-button @click.native.prevent="clickitem('T2')" label="T2">T2</el-radio-button>
          <el-radio-button @click.native.prevent="clickitem('T3')" label="T3">T3</el-radio-button>
        </el-radio-group>
<!--        <el-button :class="{btn3:true,istap:tap,nottap:!tap.P}" @click="labelSelectionP()">P</el-button>-->
<!--        <el-button :class="{btn3:true,istap:tap,nottap:!tap.Q}" @click="labelSelectionQ()">Q</el-button>-->
<!--        <el-button :class="{btn3:true,istap:tap,nottap:!tap.R}" @click="labelSelectionR()">R</el-button>-->
<!--        <el-button :class="{btn3:true,istap:tap,nottap:!tap.S}" @click="labelSelectionS()">S</el-button>-->
<!--        <el-button :class="{btn3:true,istap:tap,nottap:!tap.T}" @click="labelSelectionT()">T</el-button>-->
<!--        <el-button :class="{btn3:true,istap:tap,nottap:!tap.noise}" @click="labelSelectionnoise()">选框</el-button>-->
<!--        <el-button class="btn3" @click="labelSelectionAllNoise()">AllNoise</el-button>-->
        <el-button class="btn3" @click="clearData()">清空</el-button>
        <el-button class="btn3" @click="submitData()">提交</el-button>
        <el-popover
          placement="bottom-start"
          title="提示"
          width="220"
          class="popo"
          trigger="hover">
          <p class="tipck">1. 选择标点类型，在框内单击左键标点</p>
          <p class="tipck">2. 右键单击点可以进行删除</p>
          <p class="tipck">3. 标点完成后点击提交</p>
          <p class="tipck">也可选择清空所有数据</p>
          <div slot="reference" ><i class="el-icon-info icon"></i></div>
        </el-popover>
      </div>
      <div id="chartjump"></div>
      <div id="rightMenu" class="menu" style="display: none;">
        <el-button class="button" @click="del">删除</el-button>
      </div>
      <button class="btn" style="right: 0;top: 0;" @click="Off">关闭</button>
    </div>
    <div class="bottom">
      <div class="bottomLeft">
        <div class="xinDian">心电图</div>
        <div class="echarts">
          <div class="container">
            <div class="chart" id="I" @dblclick="showchart('I',data.I)"></div>
<!--            <button class="btn" id="I导联" @click="showchart('I',data.I)">展开</button>-->
            <span class="light" id="Ilight" @click="changeColor($event)"></span>
          </div>
          <div class="container">
            <div class="chart" id="II" @dblclick="showchart('II',data.II)"></div>
<!--            <button class="btn" id="II导联" @click="showchart('II',data.II)">展开</button>-->
            <span class="light" id="IIlight" @click="changeColor($event)"></span>
          </div>
          <div class="container">
            <div class="chart" id="III" @dblclick="showchart('III',data.III)"></div>
<!--            <button class="btn" id="III导联" @click="showchart('III',data.III)">展开</button>-->
            <span class="light" id="IIIlight" @click="changeColor($event)"></span>
          </div>
          <div class="container">
            <div class="chart" id="aVR" @dblclick="showchart('aVR',data.aVR)"></div>
<!--            <button class="btn" id="aVR导联" @click="showchart('aVR',data.aVR)">展开</button>-->
            <span class="light" id="aVRlight" @click="changeColor($event)"></span>
          </div>
          <div class="container">
            <div class="chart" id="aVL" @dblclick="showchart('aVL',data.aVL)"></div>
<!--            <button class="btn" id="aVL导联" @click="showchart('aVL',data.aVL)">展开</button>-->
            <span class="light" id="aVLlight" @click="changeColor($event)"></span>
          </div>
          <div class="container">
            <div class="chart" id="aVF" @dblclick="showchart('aVF',data.aVF)"></div>
<!--            <button class="btn" id="aVF导联" @click="showchart('aVF',data.aVF)">展开</button>-->
            <span class="light" id="aVFlight" @click="changeColor($event)"></span>
          </div>
          <div class="container">
            <div class="chart" id="V1" @dblclick="showchart('V1',data.V1)"></div>
<!--            <button class="btn" id="V1导联" @click="showchart('V1',data.V1)">展开</button>-->
            <span class="light" id="V1light" @click="changeColor($event)"></span>
          </div>
          <div class="container">
            <div class="chart" id="V2" @dblclick="showchart('V2',data.V2)"></div>
<!--            <button class="btn" id="V2导联" @click="showchart('V2',data.V2)">展开</button>-->
            <span class="light" id="V2light" @click="changeColor($event)"></span>
          </div>
          <div class="container">
            <div class="chart" id="V3" @dblclick="showchart('V3',data.V3)"></div>
<!--            <button class="btn" id="V3导联" @click="showchart('V3',data.V3)">展开</button>-->
            <span class="light" id="V3light" @click="changeColor($event)"></span>
          </div>
          <div class="container">
            <div class="chart" id="V4" @dblclick="showchart('V4',data.V4)"></div>
<!--            <button class="btn" id="V4导联" @click="showchart('V4',data.V4)">展开</button>-->
            <span class="light" id="V4light" @click="changeColor($event)"></span>
          </div>
          <div class="container">
            <div class="chart" id="V5" @dblclick="showchart('V5',data.V5)"></div>
<!--            <button class="btn" id="V5导联" @click="showchart('V5',data.V5)">展开</button>-->
            <span class="light" id="V5light" @click="changeColor($event)"></span>
          </div>
          <div class="container">
            <div class="chart" id="V6" @dblclick="showchart('V6',data.V6)"></div>
<!--            <button class="btn" id="V6导联" @click="showchart('V6',data.V6)">展开</button>-->
            <span class="light" id="V6light" @click="changeColor($event)"></span>
          </div>
        </div>
      </div>
      <div class="bottomRight">
        <div class="xinDian">质量评估</div>
        <div class="quality">
          <div class="lights">
            <div class="abcd">
              <el-radio-group v-model="noise_level.Ilevel" @change="changeRadios(noise_level.Ilevel,'Ilevel')">
                <el-radio label="A" border size="mini" class="radios">A</el-radio>
                <el-radio label="B" border size="mini" class="radios">B</el-radio>
                <el-radio label="C" border size="mini" class="radios">C</el-radio>
                <el-radio label="D" border size="mini" class="radios">D</el-radio>
              </el-radio-group>
            </div>
            <div class="abcd">
              <el-radio-group v-model="noise_level.IIlevel" @change="changeRadios(noise_level.IIlevel,'IIlevel')">
                <el-radio label="A" border size="mini" class="radios">A</el-radio>
                <el-radio label="B" border size="mini" class="radios">B</el-radio>
                <el-radio label="C" border size="mini" class="radios">C</el-radio>
                <el-radio label="D" border size="mini" class="radios">D</el-radio>
              </el-radio-group>
            </div>
            <div class="abcd">
              <el-radio-group v-model="noise_level.IIIlevel" @change="changeRadios(noise_level.IIIlevel,'IIIlevel')">
                <el-radio label="A" border size="mini" class="radios">A</el-radio>
                <el-radio label="B" border size="mini" class="radios">B</el-radio>
                <el-radio label="C" border size="mini" class="radios">C</el-radio>
                <el-radio label="D" border size="mini" class="radios">D</el-radio>
              </el-radio-group>
            </div>
            <div class="abcd">
              <el-radio-group v-model="noise_level.aVRlevel" @change="changeRadios(noise_level.aVRlevel,'aVRlevel')">
                <el-radio label="A" border size="mini" class="radios">A</el-radio>
                <el-radio label="B" border size="mini" class="radios">B</el-radio>
                <el-radio label="C" border size="mini" class="radios">C</el-radio>
                <el-radio label="D" border size="mini" class="radios">D</el-radio>
              </el-radio-group>
            </div>
            <div class="abcd">
              <el-radio-group v-model="noise_level.aVLlevel" @change="changeRadios(noise_level.aVLlevel,'aVLlevel')">
                <el-radio label="A" border size="mini" class="radios">A</el-radio>
                <el-radio label="B" border size="mini" class="radios">B</el-radio>
                <el-radio label="C" border size="mini" class="radios">C</el-radio>
                <el-radio label="D" border size="mini" class="radios">D</el-radio>
              </el-radio-group>
            </div>
            <div class="abcd">
              <el-radio-group v-model="noise_level.aVFlevel" @change="changeRadios(noise_level.aVFlevel,'aVFlevel')">
                <el-radio label="A" border size="mini" class="radios">A</el-radio>
                <el-radio label="B" border size="mini" class="radios">B</el-radio>
                <el-radio label="C" border size="mini" class="radios">C</el-radio>
                <el-radio label="D" border size="mini" class="radios">D</el-radio>
              </el-radio-group>
            </div>
            <div class="abcd">
              <el-radio-group v-model="noise_level.V1level" @change="changeRadios(noise_level.V1level,'V1level')">
                <el-radio label="A" border size="mini" class="radios">A</el-radio>
                <el-radio label="B" border size="mini" class="radios">B</el-radio>
                <el-radio label="C" border size="mini" class="radios">C</el-radio>
                <el-radio label="D" border size="mini" class="radios">D</el-radio>
              </el-radio-group>
            </div>
            <div class="abcd">
              <el-radio-group v-model="noise_level.V2level" @change="changeRadios(noise_level.V2level,'V2level')">
                <el-radio label="A" border size="mini" class="radios">A</el-radio>
                <el-radio label="B" border size="mini" class="radios">B</el-radio>
                <el-radio label="C" border size="mini" class="radios">C</el-radio>
                <el-radio label="D" border size="mini" class="radios">D</el-radio>
              </el-radio-group>
            </div>
            <div class="abcd">
              <el-radio-group v-model="noise_level.V3level" @change="changeRadios(noise_level.V3level,'V3level')">
                <el-radio label="A" border size="mini" class="radios">A</el-radio>
                <el-radio label="B" border size="mini" class="radios">B</el-radio>
                <el-radio label="C" border size="mini" class="radios">C</el-radio>
                <el-radio label="D" border size="mini" class="radios">D</el-radio>
              </el-radio-group>
            </div>
            <div class="abcd">
              <el-radio-group v-model="noise_level.V4level" @change="changeRadios(noise_level.V4level,'V4level')">
                <el-radio label="A" border size="mini" class="radios">A</el-radio>
                <el-radio label="B" border size="mini" class="radios">B</el-radio>
                <el-radio label="C" border size="mini" class="radios">C</el-radio>
                <el-radio label="D" border size="mini" class="radios">D</el-radio>
              </el-radio-group>
            </div>
            <div class="abcd">
              <el-radio-group v-model="noise_level.V5level" @change="changeRadios(noise_level.V5level,'V5level')">
                <el-radio label="A" border size="mini" class="radios">A</el-radio>
                <el-radio label="B" border size="mini" class="radios">B</el-radio>
                <el-radio label="C" border size="mini" class="radios">C</el-radio>
                <el-radio label="D" border size="mini" class="radios">D</el-radio>
              </el-radio-group>
            </div>
            <div class="abcd">
              <el-radio-group v-model="noise_level.V6level" @change="changeRadios(noise_level.V6level,'V6level')">
                <el-radio label="A" border size="mini" class="radios">A</el-radio>
                <el-radio label="B" border size="mini" class="radios">B</el-radio>
                <el-radio label="C" border size="mini" class="radios">C</el-radio>
                <el-radio label="D" border size="mini" class="radios">D</el-radio>
              </el-radio-group>
            </div>
          </div>
          <div class="allin">
            <button class="green" @click="allA">全A
            </button>
            <button class="red" @click="allB">全B
            </button>
            <button class="red" @click="allC">全C
            </button>
            <button class="red" @click="allD">全D
            </button>
          </div>
        </div>
      </div>
      <div class="topLeft">
        <div class="topMiddle">
          <div class="warning">预警类型</div>
          <div class="warningDetail">
            <form id="loginForm" name="loginForm" style="padding:3vw;display: flex;justify-content: space-between;width: 100%">
              <template>
                <el-select v-model="value" placeholder="请选择" style="width: 14vw">
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
              <el-button type="success" id="btn1" class="btn1" @click="submit()">提交</el-button>
            </form>
          </div>
        </div>
        <div class="topMiddle">
          <div class="warning">患者信息</div>
          <div class="messageDetail">
            <span id="pID">患者ID：{{ message.pid }}</span>
            <span id="logID">日志ID：{{ message.logid }}</span>
            <span id="psex">性别：{{ message.sex }}</span>
            <span id="pAge">年龄：{{ message.age }}</span>
            <span id="clock">时间：{{ message.time }}</span>
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
import * as echarts from "@/views/ECGScreen/detail/echarts.min";
import {selectList,getLabel,addLabel} from "@/api/log_user/log_user"
import {islabel} from "@/api/alert_log/alert_log"
import {param} from "@/utils";
import de from "element-ui/src/locale/lang/de";
export default {
  name: "lookLog1",
  computed: {
    de() {
      return de
    }
  },
  data() {
    return {
      lead:false,
      tap:{
        P1:false,
        P2:false,
        P3:false,
        R1:false,
        R2:false,
        R3:false,
        T1:false,
        T2:false,
        T3:false,
        //P:false,
        // Q:false,
        // R:false,
        // S:false,
        // T:false,
        // noise:false
      },
      radio:'',
      //通过字典将radio和light一一对应
      levellight : {
        'V1level': 'V1light',
        'V2level': 'V2light',
        'V3level': 'V3light',
        'V4level': 'V4light',
        'V5level': 'V5light',
        'V6level': 'V6light',
        'aVLlevel': 'aVLlight',
        'aVFlevel': 'aVFlight',
        'aVRlevel': 'aVRlight',
        'Ilevel': 'Ilight',
        'IIlevel': 'IIlight',
        'IIIlevel': 'IIIlight',
      },
      lightlevel: {
        'V1light': 'V1level',
        'V2light': 'V2level',
        'V3light': 'V3level',
        'V4light': 'V4level',
        'V5light': 'V5level',
        'V6light': 'V6level',
        'aVLlight': 'aVLlevel',
        'aVFlight': 'aVFlevel',
        'aVRlight': 'aVRlevel',
        'Ilight': 'Ilevel',
        'IIlight': 'IIlevel',
        'IIIlight': 'IIIlevel',
      },
      noise_list: {
        //判断红绿颜色
        Ilight: "",
        IIlight: "",
        IIIlight: "",
        aVLlight: "",
        aVFlight: "",
        aVRlight: "",
        V1light: "",
        V2light: "",
        V3light: "",
        V4light: "",
        V5light: "",
        V6light: "",
      },
      //ABCD等级的判断
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
        devicesn: "",
        user_id: "",
        pid: "",
        logid: "",
        sex: "",
        age: "",
        time: "",
        logType:'',
      },
      query:{
        userId:'',
        logId: '',
        waveLabel:''
      },
      value: '正常心电图',
      options:[],
      timex:[],
      show:false,
      seriesdata:[
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

        {yAxis: -3}, {yAxis: -2.5}, {yAxis: -2}, {yAxis: -1.5}, {yAxis: -1}, {yAxis: -0.5}, {yAxis: 0}, {yAxis: 0.5}, {yAxis: 1}, {yAxis: 1.5}, {yAxis: 2}, {yAxis: 2.5}, {yAxis: 3},],
      seriesdata1:[{xAxis: 0},
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
        {yAxis: -1}, {yAxis: -0.5}, {yAxis: 0}, {yAxis: 0.5}, {yAxis: 1},],
      data:{},
      chartjump:null,
      xIndex:null,
      delX:{key:null,value:null},//想要删除的点
      area:[],//想要删除的区间
      markArea:[],
      title:'',
      subData:{
          P1:[],P2:[],P3:[],
          R1:[],R2:[],R3:[],
          T1:[],T2:[],T3:[],
      },//提交标注信息

      pointdata:[],//画点的option
      point:[[],{P:[], Q:[], R:[], S:[], T:[]}],
      arr:[]//要删除的点所在的区间
    };
  },

  created() {
    console.log('created')
    if (this.$route.query.logId) {
      this.message.logid = this.$route.query.logId;
      this.message.logType = this.$route.query.logType;
      this.message.user_id = this.$route.query.userId;
      this.value=this.$route.query.logType;
      this.query.logId=this.$route.query.logId;
      this.query.userId=this.$route.query.userId;
      this.getSelectList()
      this.getLabel()
    }
  },
  mounted() {
    this.getMessage()
    this.chartjump = echarts.init(document.getElementById('chartjump'));
  },
  // watch:{
  //   'tap.noise':{
  //     handler(new_value,old_value){
  //         if(new_value){
  //           this.chartjump.dispatchAction({
  //             type: 'takeGlobalCursor',
  //             // 如果想变为“可刷选状态”，必须设置。不设置则会关闭“可刷选状态”。
  //             key: 'brush',
  //             brushOption: {
  //               // 参见 brush 组件的 brushType。如果设置为 false 则关闭“可刷选状态”。
  //               brushType: 'lineX',
  //               // 参见 brush 组件的 brushMode。如果不设置，则取 brush 组件的 brushMode 设置。
  //               brushMode: 'multiple',
  //             }
  //           });
  //         }else {
  //           this.chartjump.dispatchAction({
  //             type: 'takeGlobalCursor',
  //           });
  //           this.chartjump.dispatchAction({
  //             type: 'brush',
  //             areas: []
  //           });
  //         }
  //     },
  //     deep: true
  //   }
  // },
  methods: {
    goTarget(href) {
      window.open(href, "_blank");
    },
    getSelectList(){
      selectList().then(res=>{
        console.log(res)
        this.options=res.data
      })
    },
    //获取心电数据
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
      var timex=[];
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
          _th.data=jsonResult.result
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
              for (var k = 0; k <1001; k++) {
                timex.push(k / 100+"秒")
              }
              _th.timex=timex
              //console.log(timex)
              //console.log(jsonResult.result.I.length)
              for (i = 0; i < 1000; i++) {
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
              var seriesdata=_th.seriesdata
              if (jsonResult.result.II.length === 1000) seriesdata = _th.seriesdata1
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
                  right: 0,
                  top: 2,
                  bottom: 2
                },
                xAxis: {
                  type:'category',
                  boundaryGap: false,
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
                      width: 0.5, //网格的宽度
                      type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
                    },
                  } /*网格线*/
                },
                yAxis: {
                  min: -1,
                  max: 1,
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
                        width:1.5,
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
                  right: 0,
                  top: 2,
                  bottom: 2
                },

                xAxis: {
                  type:'category',
                  boundaryGap: false,
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
                      width: 0.5, //网格的宽度
                      type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
                    },
                  } /*网格线*/
                },
                yAxis: {
                  min: -1,
                  max: 1,
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
                        width:1.5,
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
                  right: 0,
                  top: 2,
                  bottom: 2
                },
                xAxis: {
                  type:'category',
                  boundaryGap: false,
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
                      width: 0.5, //网格的宽度
                      type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
                    },
                  } /*网格线*/
                },
                yAxis: {
                  min: -1,
                  max: 1,
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
                        width:1.5,
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
                  right: 0,
                  top: 2,
                  bottom: 2
                },
                xAxis: {
                  type:'category',
                  boundaryGap: false,
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
                      width: 0.5, //网格的宽度
                      type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
                    },
                  } /*网格线*/
                },
                yAxis: {
                  min: -1,
                  max: 1,
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
                        width:1.5,
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
                  right: 0,
                  top: 2,
                  bottom: 2
                },
                xAxis: {
                  type:'category',
                  boundaryGap: false,
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
                      width: 0.5, //网格的宽度
                      type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
                    },
                  } /*网格线*/
                },
                yAxis: {
                  min: -1,
                  max: 1,
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
                        width:1.5,
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
                  right: 0,
                  top: 2,
                  bottom: 2
                },

                xAxis: {
                  type:'category',
                  boundaryGap: false,
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
                      width: 0.5, //网格的宽度
                      type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
                    },
                  } /*网格线*/
                },
                yAxis: {
                  min: -1,
                  max: 1,
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
                        width:1.5,
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
                  right: 0,
                  top: 2,
                  bottom: 2
                },
                xAxis: {
                  type:'category',
                  boundaryGap: false,
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
                      width: 0.5, //网格的宽度
                      type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
                    },
                  } /*网格线*/
                },
                yAxis: {
                  min: -1,
                  max: 1,
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
                        width:1.5,
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
                  right: 0,
                  top: 2,
                  bottom: 2
                },
                xAxis: {
                  type:'category',
                  boundaryGap: false,
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
                      width: 0.5, //网格的宽度
                      type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
                    },
                  } /*网格线*/
                },
                yAxis: {
                  min: -1,
                  max: 1,
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
                        width:1.5,
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
                  right: 0,
                  top: 2,
                  bottom: 2
                },

                xAxis: {
                  type:'category',
                  boundaryGap: false,
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
                      width: 0.5, //网格的宽度
                      type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
                    },
                  } /*网格线*/
                },
                yAxis: {
                  min: -1,
                  max: 1,
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
                        width:1.5,
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
                  right: 0,
                  top: 2,
                  bottom: 2
                },
                xAxis: {
                  type:'category',
                  boundaryGap: false,
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
                      width: 0.5, //网格的宽度
                      type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
                    },
                  } /*网格线*/
                },
                yAxis: {
                  min: -1,
                  max: 1,
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
                        width:1.5,
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
                  right: 0,
                  top: 2,
                  bottom: 2
                },
                xAxis: {
                  type:'category',
                  boundaryGap: false,
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
                      width: 0.5, //网格的宽度
                      type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
                    },
                  } /*网格线*/
                },
                yAxis: {
                  min: -1,
                  max: 1,
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
                        width:1.5,
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
                  right: 0,
                  top: 2,
                  bottom: 2
                },
                xAxis: {
                  type:'category',
                  boundaryGap: false,
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
                      width: 0.5, //网格的宽度
                      type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
                    },
                  } /*网格线*/
                },
                yAxis: {
                  min: -1,
                  max: 1,
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
                        width:1.5,
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
            _th.$modal.msgError("设备未连接")
          }
          console.log(jsonResult.result)
        },
        error: function (data) {
          console.log(data)
          _th.$modal.msgError("数据获取失败")
        }
      });
    },
    //获取标注数据
    getLabel(){
      getLabel(this.query).then(res=>{
        console.log(res)
        if(res.data.waveLabel!=null){
          this.subData=JSON.parse(res.data.waveLabel)
        }
      }).catch(err=>{
      })
    },
    //判断红绿颜色
    light(data) {
      this.noise_list = data.result.noise
      console.log(this.noise_list)
      for (var key in this.noise_list) {
        if (this.noise_list[key] === 1) {
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
      console.log("红绿颜色修改点击事件：",tid)
      var b = tid.target.id;
      console.log("点击获取到的导联id：",b)
      let temp = document.getElementById(b)
      if (this.noise_list[b] === 0) {
        this.noise_list[b] = 1;
        temp.style.backgroundColor = "red";
        this.noise_level[this.lightlevel[b]]="B"
        console.log("点击修改之后的噪声数据：",this.noise_list)
      } else {
        this.noise_list[b] = 0;
        temp.style.backgroundColor = "greenyellow";
        this.noise_level[this.lightlevel[b]]="A"
        console.log("点击修改之后的噪声数据：",this.noise_list)
      }
    },
    //全为A
    allA() {
      for (var k in this.noise_level) {
        console.log(this.noise_level[k])
        this.noise_level[k] = "A"
        this.noise_list[this.levellight[k]]=0
        let temp = document.getElementById(this.levellight[k])
        temp.style.backgroundColor = "greenyellow"
      }

    },
    //全为B
    allB() {
      for (var k in this.noise_level) {
        this.noise_level[k] = "B"
        this.noise_list[this.levellight[k]]=1
        let temp = document.getElementById(this.levellight[k])
        temp.style.backgroundColor = "red"
      }

    },
    //全为C
    allC() {
      for (var k in this.noise_level) {
        this.noise_level[k] = "C"
        this.noise_list[this.levellight[k]]=1
        let temp = document.getElementById(this.levellight[k])
        temp.style.backgroundColor = "red"
      }

    },
    //全为D
    allD() {
      for (var k in this.noise_level) {
        this.noise_level[k] = "D"
        this.noise_list[this.levellight[k]]=1
        let temp = document.getElementById(this.levellight[k])
        temp.style.backgroundColor = "red"
      }

    },
    //修改等级的程度
    changeRadios(value,lab) {
      // console.log(value)
      // console.log(lab)
      let dom = document.getElementById(this.levellight[lab])
      if(value==='A'){
        dom.style.backgroundColor = "greenyellow"
        this.noise_list[this.levellight[lab]]=0
      }else {
        dom.style.backgroundColor = "red"
        this.noise_list[this.levellight[lab]]=1
      }

      console.log("修改之后的等级：",this.noise_level);
      console.log("修改之后的等级：", this.noise_list)
    },
    //重置按钮
    cleartap(){
      for (let tapKey in this.tap) {
        this.tap[tapKey]=false
      }
    },
    //合并重复框选区域
    drawMarkArea(newitem,a){
      if(this.markArea.length!==0){
        var result= this.markArea.findIndex((v) => {
          return (v[0].xAxis <= newitem[0] && v[1].xAxis >= newitem[0])
            || (v[0].xAxis <=newitem[1] && v[1].xAxis >= newitem[1])
            ||(v[0].xAxis >=newitem[0] && v[1].xAxis <= newitem[1]);
        });
        console.log("是否有交叉",result)
        if(result==-1){
          this.markArea.push(a)
        }else {
          var max=Math.max(this.markArea[result][0].xAxis,this.markArea[result][1].xAxis,newitem[0],newitem[1])
          var min=Math.min(this.markArea[result][0].xAxis,this.markArea[result][1].xAxis,newitem[0],newitem[1])
          a[0].xAxis=min
          a[1].xAxis=max
          this.markArea.splice(result,1,a)
        }
      }else {
        this.markArea.push(a)
      }
      this.chartjump.setOption({
        series: [{
          id: 'series1',
          markArea: {
            label: {
              show: true,
              position: 'inside'
            },
            data: this.markArea
          },
        }]
      });
    },

    clearData(){
      this.pointdata=[]
      this.subData={
        P1:[],P2:[],P3:[],
        R1:[],R2:[],R3:[],
        T1:[],T2:[],T3:[],
      }
      this.chartjump.setOption({
        series:{
          markPoint:{
            symbol: "pin",
            symbolSize: 24,
            animation:false,
            data: [],
          }
        }
      });
    },
    submit() {
      console.log(this.message.logid)
      console.log(this.value)
      console.log(this.noise_list)
      console.log(this.noise_level)
      console.log(this.message.user_id)
      var that=this
      $.ajax({
        cache: true,
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        url: "https://screen.mindyard.cn:84/write_logType",
        data: JSON.stringify(
          {
            "id": this.message.logid,
            "Type": this.value,
            "list": this.noise_list,
            "lists": this.noise_level,
            "user_id": this.message.user_id,
          }),
        async: false,
        success: function (data) {
          console.log("success:", data);
          that.$modal.msgSuccess("数据提交成功");
        },
        error: function (data) {
          console.log("error:", data);
//            cocoMessage.error("提交失败", 3000);
        }
      });
      islabel({logType:this.value,logId:this.message.logid})
    },
    submitData(){
      this.query.waveLabel=JSON.stringify(this.subData)
      console.log(this.subData)
      addLabel(this.query).then(res=>{
        this.$modal.msgSuccess("标注提交成功");
      }).catch(err=>{})
    },
    showchart(title, data) {
      this.title=title
      if(title=='II'){
        this.lead=true
      }
      let y=[]
      for (let i = -2; i < 2.1; i+=0.1) {
        y.push(i)
      }
      //console.log(y)
      let detailoption = {
        animation: false,
        backgroundColor: "#ffffff",
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross'
          }
        },
        toolbox:{
          show:false
        },
        dataZoom: [
          {
            show: true,       // 滑动条组件
            type: 'slider',
            brushSelect:false,
            // y: '90%',
            startValue: 0,
            endValue:252,
            // minSpan: 52.5,
            // maxSpan: 52.6,
            minValueSpan:252,
            maxValueSpan:724
          },
          {
            show: true,       // 滑动条组件
            type: 'slider',
            orient: 'vertical',
            brushSelect:false,
            startValue: -1,
            endValue:1,
            minValueSpan:2.13,
            maxValueSpan:6
          },
          {
            type: 'inside',
            orient: 'vertical',// 鼠标滚轮缩放
            start: 0,
            end: 100
          },
          {
            type: 'inside',   // 鼠标滚轮缩放
            start: 0,
            end: 100
          },
        ],
        grid: {
          left: '3%',
          right: '3%',
          top: '5%',
          bottom: '10%'
        },
        legend: {
          show: false,
          data: ['当前电位'],
          textStyle: {color: "#000000"} /*图例(legend)说明文字的颜色*/,
          left: "right",
        },
        xAxis: {
          type:'category',
          boundaryGap: false,
          data: this.timex,
          axisTick: {
            show: false
          },
          axisLabel: { //修改坐标系字体颜色
            interval: 4,
            show: false,
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
          min: -3,
          max: 3,
          //type:'value',
          boundaryGap: false,
          //interval: 0.1,
          splitNumber: 41,
          minInterval:0.1,
          //data:y,
          axisLabel: { //修改坐标系字体颜色
            show: false,
            textStyle: {
              color: "#000000"
            }
          },
          splitLine: {
            show: true,
            lineStyle: {
              color: "pink",
              width: 1, //网格的宽度
              type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
            },
          } /*网格线*/
        },
        series: {
          id: 'series1',
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
              show: true,
              position: 'start', // 表现内容展示的位置
              color: '#b33939'  // 展示内容颜色
            },
            data: this.seriesdata,
          },
          itemStyle: {
            normal: {
              lineStyle: {
                width:1.5,
                color: '#000000',/*折线的颜色*/
              },
              color: "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/
            }
          },
          symbol: "none",
          name: '当前电位',
          type: 'line',
          data: data,
          smooth: true //显示为平滑的曲线*/
        },
      };
      this.chartjump.clear()
      this.chartjump.setOption(detailoption)
      setTimeout(()=>{
        this.chartjump.resize()
      })
      //console.log(this.subData)
      this.markArea.length=0
      this.pointdata.length=0
      var colorList= {
        P1:'#fe0101',P2:'#fe0101',P3:'#fe0101',
        R1:'#ff00cf',R2:'#ff00cf',R3:'#ff00cf',
        T1:'#0021da',T2:'#0021da',T3:'#0021da',
      }
      //回显
      if(this.lead){
        console.log("有数据")
        console.log(this.subData)
        for (const key in this.subData) {
          for (let j = 0; j < this.subData[key].length; j++) {
            let pointdata={
              name: key,
              xAxis:this.subData[key][j],
              yAxis: data[this.subData[key][j]],
              itemStyle: {
                color:colorList[key]
              },
              label: {
                color: '#ffffff',
                show: true,
                formatter: key,
                fontSize:9
              },
            }
            this.pointdata.push(pointdata)
          }
        }
        //console.log(this.pointdata)
        this.chartjump.setOption({
          series:{
            markPoint:{
              symbol: "pin",
              symbolSize: 24,
              animation:false,
              data: this.pointdata,
            }
          }
        })
      }else {
        this.pointdata=[]
        this.chartjump.setOption({
          series:{
            markPoint:{
              symbol: "pin",
              symbolSize: 24,
              animation:false,
              data: this.pointdata,
            }
          }
        })
      }
      var width=window.screen.width
      var height=window.screen.height
      this.chartjump.off('contextmenu')
      this.chartjump.on('contextmenu',(params)=>{
        console.log(params)
        $('#rightMenu').css({
          'display': 'block',
          'left': params.event.offsetX/width*100 + 'vw',
          'top' : params.event.offsetY/height*100+11 +'vh'
        });
        if(params.componentType==="markPoint"){
          this.delX.value=params.data.xAxis
          this.delX.key=params.data.name
        }
        console.log(this.delX)
      });
      this.chartjump.getZr().off('click')
      this.chartjump.getZr().on('click',params=>{
        $('#rightMenu').css({
          'display': 'none',
        });
        const pointInPixel= [params.offsetX, params.offsetY];
        // console.log(pointInPixel)
        if (this.chartjump.containPixel('grid',pointInPixel)) {
          this.xIndex=this.chartjump.convertFromPixel({seriesIndex:0},[params.offsetX, params.offsetY])[0];
          //this.yIndex=this.chartjump.convertFromPixel({seriesIndex:0},[params.offsetX, params.offsetY])[1];
          console.log(this.xIndex)
          let temp=false
          this.pointdata.forEach(i=>{
            if(this.xIndex==i.xAxis){
              console.log("已存在")
              temp=true
              return
            }
          })
          if(temp){
            return
          }
          if(this.radio==''){
            return
          }
          this.subData[this.radio].push(this.xIndex)
          //console.log(this.subData)
          let pointdata={
            name: this.radio,
            xAxis:this.xIndex,
            yAxis: data[this.xIndex],
            itemStyle: {
              color:colorList[this.radio]
            },
            label: {
              color: '#ffffff',
              show: true,
              formatter: this.radio,
              fontSize:9
            },
          }
          this.pointdata.push(pointdata)
          this.chartjump.setOption({
              series:{
                markPoint:{
                  symbol: "pin",
                  symbolSize: 24,
                  animation:false,
                  data: this.pointdata,
                }
              }
            })
        }
      });
      this.show=true
    },
    //区间合并
    getMerge(arr) {
      arr.sort((a, b) => {
        if(a[0] !== b[0]){
          return a[0] - b[0]
        }
        return a[1] - b[1]
      })
      let len = arr.length, ans = [],start, end; // 遍历当前区间的最小值与最大值
      for(let i = 0; i < len; i++){
        let s = arr[i][0];
        let e = arr[i][1];
        if(start === undefined){
          start = s;
          end = e;
        }
        else if(s <= end){
          end = Math.max(e, end)
        }
        else{
          let part = [start, end];
          ans.push(part);
          start = s;
          end = e;
        }
      }

      if (start !== undefined) {
        let part = [start, end];
        ans.push(part);
      }
      return ans;
    },
    Off(){
      for (let tapKey in this.tap) {
        this.tap[tapKey]=false
      }
      this.lead=false
      this.show=false
    },
    del(){
      console.log(this.delX.key,this.delX.value)
      this.pointdata.some((item,index)=>{
        if(item.xAxis==this.delX.value){
          this.pointdata.splice(index,1)
          return true
        }
      })
      this.subData[this.delX.key].some((item,index)=>{
        if(item==this.delX.value){
          this.subData[this.delX.key].splice(index,1)
          console.log("删除成功")
          return true
        }
      })
      this.delX={key:null,value:null}
      this.chartjump.setOption({
          series:{
            markPoint:{
              symbol: "pin",
              symbolSize: 24,
              animation:false,
              data: this.pointdata,
            }
          }
        })
      $('#rightMenu').css({
        'display': 'none',
      });

    },
    //判断是一维数组还是二维
    isArray(a) {
      for (let i of a) {
        if (Array.isArray(i))
          return 2
        }
        return 1
      },
    clickitem(e){
      e === this.radio ? this.radio = '' : this.radio = e
    }
  },
};
</script>

<style scoped lang="scss">

body,html{
  color: #00afff;
  box-sizing: border-box;
}
.main {
  width: 100%;
  background: #f1faff;


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
  width: 30%;
  //height: 100%;
  border-left: 1px solid #136d87;
  display: flex;
  flex-direction: column;
}

.topMiddle {
  width: 100%;
  height: 30%;
  //border: 1px solid rgba(0, 0, 0,);
}

.topRight {
  width: 100%;
  height: 100%;
}
.showbox {
  //display: none;
  user-select:none;
  position: absolute;
  width: 100%;
  height:80%;
  border: 1px solid #6EDDF1;
  top: 45%;
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
  border: 1px solid #000000;
  .button{
    height: 0.5vh;
    width: 0.5vw;
    display: inline-flex;
    text-align: center;
    border: none;
    font-size: 0.5vw;
    font-weight: 700;
    justify-content: center;
    align-items: center;
  }
  .button:hover{
    color: #FFFFFF;
    background-color: #df0202;
  }
}

#chartjump {
  /*position: absolute;*/
  width: 100%;
  height: 78%;
  //top: 50%;
  //left: 50%;
  //transform: translate(-50%, -50%);
  border: 1px solid #000000;
  /*background-color: #06732b;*/
}
.message {
  width: 100%;
  height: 20%;
  font-size: 1vw;
  font-weight: 700;
  color: #136d87;
  text-align: center;
  padding: 0.8vw 0;
}

.messageDetail {
  //border-right: 1px solid #ffffff;
  width: 100%;
  height: 75%;
  font-size: 1vw;
  font-weight: 700;
  color: #136d87;
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
  height: 18%;
  font-size: 1vw;
  font-weight: 700;
  color: #136d87;
  text-align: center;
  padding: 0.8vw 0;
  //border-bottom: 1px solid #000000;
  //border-top: 1px solid #000000;
}

.warningDetail {
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 0 auto;
  //border: 1px solid #ff5a00;
  width: 100%;
  height: 35%;
  font-size: 1vw;
  color: #136d87;

}
.btn1{
  color: #136d87;
  background-color: rgba(255, 255, 255, 0);
}
.btn1:hover{
  color: white;
  background-color: #4cc9f0;
}
form input {
  width: 3vw;
  height: 2vw;
  margin: 1vw;
}


.bottom {
  height: 100%;
  width: 98.5%;
  border: 1px solid #136d87;
  display: flex;
  flex-direction: row;
  flex-wrap: nowrap;
  margin: 1vw;
  //padding: 1vw;
  justify-content: flex-start;
}

.bottomLeft {
  width: 50.5%;
  height: 100%;
  border-top: none;
}

.xinDian {
  width: 100%;
  height: 5%;
  font-size: 1vw;
  font-weight: 700;
  color: #136d87;
  text-align: center;
  padding: 0.8vw 0;
  //border-top: 1px solid #136d87;
}
.xinDian:first-child{
  border-right: 1px solid #136d87;
}
.echarts {
  width: 100%;
  height: 95%;
  border-top: 1px solid #136d87;
  border-right: 1px solid #136d87;
}

.bottomLeft .echarts .container {
  width: 100%;
  height: 100%;
  //border: 1px solid rgba(25, 186, 139, 0.17);
  position: relative;
  .btn{
    background-color: #91dff8;
  }
  .btn:hover{
    background-color: #3ec9f7;
  }
}

.bottomLeft .echarts .container .chart {
  height: 10vh;
  width: 98.2%;
  //background-color: #ffffff;
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
.popo{
  height: 100%;
}
::v-deep .el-radio-button .el-radio-button__inner{
  display:inline-block;
  width: 4vw;
  height: 4vh;
  padding: 0;
  line-height: 4vh;
  font-size: 1vw;
  text-align: center;
  color: #b33939;
  border: 1px solid #b33939;
  border-radius: .5em;
  margin: 0.5vw;
}
.btn3:hover {
  cursor: pointer;
  /*color: white;*/
  /*border: 0;*/
  //background-color: #4cc9f0;
}
.btn:hover {
  cursor: pointer;
  color: #136d87;
  //border: 0 ;
  background-color: #4cc9f0;
}
.istap{
  background-color: #4cc9f0;
}
.nottap{
  background-color: transparent;
}
.light {
  display: inline-block;
  width: 1.8%;
  height: 95%;
  position: absolute;
  top: 0;
  right: 0;
  background-color: greenyellow;
}

.light:hover {
  cursor: pointer;
  border: 1px solid #136d87;
}

.bottomRight {
  width: 20%;
  height: 100%;
  //border: 1px solid #000000;
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
  height: 96%;
  width: 80%;
}

.radios{
  //border: 0.5px solid rgb(0, 0, 0);
  box-sizing: border-box;
  color: rgb(96, 98, 102);
  cursor: pointer;
  display: block;
  font-family: Helvetica Neue, Helvetica, PingFang SC, Hiragino Sans GB, Microsoft YaHei, Arial, sans-serif;
  //font-size: 1.5vw;
  font-weight: 500;
  height: 9.95vh;
  width: 3vw;
  line-height: 9.95vh;
  margin: 0;
  padding: 0;
  position: relative;
  text-rendering: optimizelegibility;
  user-select: none;
  white-space: nowrap;
  border: none;
}
::v-deep .el-radio--medium.is-bordered .el-radio__inner{
  height: 1vw;
  width: 1vw ;
}
::v-deep .el-radio__inner::after{
  height: 0.3vw;
  width: 0.3vw;
}
::v-deep .el-radio--medium.is-bordered .el-radio__label{
  font-size: 1vw;
  padding: 5px;
}
::v-deep .el-input--medium .el-input__inner{
  color: #136d87;
  background-color: rgba(255, 255, 255, 0);
  border-radius: .8vw;
  border-color: #136d87;
}
::v-deep .el-select-dropdown__list{
  color: #136d87;
  background-color: rgba(255, 255, 255, 0);
}
.abcd {
  display: flex;
  justify-content: space-between;
  flex-grow: 1;
  width: 100%;
  //padding: 1.5vw;
  //margin-top: 0.3vw;
  border-left: 1px solid #136d87;
  border-top: 0.5px solid #136d87;
  //border-bottom: 0.5px solid #000000;
  //border-right: 0.5px solid #000000;
}

.allin {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin: 0 auto;
  width: 20%;

  //background-color: #62be38;
}

.allin .red {
  display: inline-block;
  outline: none;
  text-align: center;
  text-decoration: none;
  width: 100%;
  flex: 1;
  margin: 0;
  font-size: 1.5vw;
  background-color: red;
  border: 1px solid #136d87;
}

.allin .green {
  display: inline-block;
  outline: none;
  text-align: center;
  text-decoration: none;
  width: 100%;
  flex: 1;
  font-size: 1.5vw;
  margin: 0;
  background-color: greenyellow;
  border: 1px solid #136d87;
}
.allin .red:hover{
  cursor:pointer;
  background-color: rgba(231, 34, 34, 0.63);
}
.allin .green:hover{
  cursor:pointer;
  background-color: #c6ff78;;
}
.el-radio-group{
  display: flex;
  justify-content: space-around;
}
.el-radio-button:focus:not(.is-focus):not(:active):not(.is-disabled) {
  -webkit-box-shadow: 0 0px 0px #ccc;
  box-shadow: 0 0px 0px #ccc;
}
</style>





























