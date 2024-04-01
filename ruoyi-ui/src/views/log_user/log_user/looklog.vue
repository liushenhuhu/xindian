<template>
  <div class="main">
    <!-- <div class="topMiddle">
      <div class="warning">患者信息</div>
      <div class="messageDetail">
        <span id="pID">患者ID：{{ message.pid }}</span>
        <span id="logID">日志ID：{{ message.logid }}</span>
        <span id="psex">性别：{{ message.sex }}</span>
        <span id="pAge">年龄：{{ message.age }}</span>
        <span id="clock">时间：{{ message.time }}</span>
      </div>
    </div> -->

    <div class="patientInformation">
      <div class="patientInformation-biaoti">患者信息</div>
      <div class="patientInformation-xiangxi">
        <span id="">患者ID：{{ message.pid }}</span>
        <span id="">日志ID：{{ message.logid }}</span>
        <span id="">性别：{{ message.sex }}</span>
        <span id="">年龄：{{ message.age }}</span>
        <span id="">时间：{{ message.time }}</span>
      </div>

      <div class="tijiao">
        <el-button type="success" id="btn1" class="btn1" @click="submit()">
          提交
        </el-button>
        <el-button class="btn2" id="btn2" @click="suspected()"
          >是否疑似病理</el-button
        >
        <el-button
          class="next"
          v-if="state"
          @click="prev()"
          type="primary"
          :loading="loading"
          >上一个</el-button
        >
        <el-button class="next" v-if="state" @click="next()" :loading="loading"
          >下一个</el-button
        >
      </div>

      <!-- <form id="loginForm" name="loginForm" class="biaodan">
              <el-select
                v-model="value"
                multiple
                filterable
                allow-create
                default-first-option
                placeholder="请选择啊"
              >
                <el-option-group
                  v-for="group in options"
                  :key="group.label"
                  :label="group.label"
                >
                  <el-option
                    v-for="item in group.options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-option-group>
              </el-select>
            </form> -->
    </div>

    <div class="showbox" id="jump" v-show="show" @contextmenu.prevent>
      <div class="noName" v-show="lead">
        <el-radio-group v-model="radio">
          <el-radio-button @click.native.prevent="clickitem('P1')" label="P1"
            >P1</el-radio-button
          >
          <el-radio-button @click.native.prevent="clickitem('P2')" label="P2"
            >P2</el-radio-button
          >
          <el-radio-button @click.native.prevent="clickitem('P3')" label="P3"
            >P3</el-radio-button
          >
          <el-radio-button @click.native.prevent="clickitem('R1')" label="R1"
            >R1</el-radio-button
          >
          <el-radio-button @click.native.prevent="clickitem('R2')" label="R2"
            >R2</el-radio-button
          >
          <el-radio-button @click.native.prevent="clickitem('R3')" label="R3"
            >R3</el-radio-button
          >
          <el-radio-button @click.native.prevent="clickitem('T1')" label="T1"
            >T1</el-radio-button
          >
          <el-radio-button @click.native.prevent="clickitem('T2')" label="T2"
            >T2</el-radio-button
          >
          <el-radio-button @click.native.prevent="clickitem('T3')" label="T3"
            >T3</el-radio-button
          >
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
          trigger="hover"
        >
          <p class="tipck">1. 选择标点类型，在框内单击左键标点</p>
          <p class="tipck">2. 右键单击点可以进行删除</p>
          <p class="tipck">3. 标点完成后点击提交</p>
          <p class="tipck">也可选择清空所有数据</p>
          <div slot="reference"><i class="el-icon-info icon"></i></div>
        </el-popover>
      </div>
      <div id="chartjump"></div>
      <div id="rightMenu" class="menu" style="display: none">
        <el-button class="button" @click="del">删除</el-button>
      </div>
      <button class="btn" style="right: 0; top: 0" @click="Off">关闭</button>
    </div>
    <div class="bottom">
      <div class="bottomLeft">
        <div class="xinDian">心电图</div>
        <div class="echarts">
          <div class="container">
            <div class="chart" id="I" @dblclick="showchart('I', data.I)"></div>
            <!--            <button class="btn" id="I导联" @click="showchart('I',data.I)">展开</button>-->
            <span class="light" id="Ilight" @click="changeColor($event)"></span>
          </div>
          <div class="container">
            <div
              class="chart"
              id="II"
              @dblclick="showchart('II', data.II)"
            ></div>
            <!--            <button class="btn" id="II导联" @click="showchart('II',data.II)">展开</button>-->
            <span
              class="light"
              id="IIlight"
              @click="changeColor($event)"
            ></span>
          </div>
          <div class="container">
            <div
              class="chart"
              id="III"
              @dblclick="showchart('III', data.III)"
            ></div>
            <!--            <button class="btn" id="III导联" @click="showchart('III',data.III)">展开</button>-->
            <span
              class="light"
              id="IIIlight"
              @click="changeColor($event)"
            ></span>
          </div>
          <div class="container">
            <div
              class="chart"
              id="aVR"
              @dblclick="showchart('aVR', data.aVR)"
            ></div>
            <!--            <button class="btn" id="aVR导联" @click="showchart('aVR',data.aVR)">展开</button>-->
            <span
              class="light"
              id="aVRlight"
              @click="changeColor($event)"
            ></span>
          </div>
          <div class="container">
            <div
              class="chart"
              id="aVL"
              @dblclick="showchart('aVL', data.aVL)"
            ></div>
            <!--            <button class="btn" id="aVL导联" @click="showchart('aVL',data.aVL)">展开</button>-->
            <span
              class="light"
              id="aVLlight"
              @click="changeColor($event)"
            ></span>
          </div>
          <div class="container">
            <div
              class="chart"
              id="aVF"
              @dblclick="showchart('aVF', data.aVF)"
            ></div>
            <!--            <button class="btn" id="aVF导联" @click="showchart('aVF',data.aVF)">展开</button>-->
            <span
              class="light"
              id="aVFlight"
              @click="changeColor($event)"
            ></span>
          </div>
          <div class="container">
            <div
              class="chart"
              id="V1"
              @dblclick="showchart('V1', data.V1)"
            ></div>
            <!--            <button class="btn" id="V1导联" @click="showchart('V1',data.V1)">展开</button>-->
            <span
              class="light"
              id="V1light"
              @click="changeColor($event)"
            ></span>
          </div>
          <div class="container">
            <div
              class="chart"
              id="V2"
              @dblclick="showchart('V2', data.V2)"
            ></div>
            <!--            <button class="btn" id="V2导联" @click="showchart('V2',data.V2)">展开</button>-->
            <span
              class="light"
              id="V2light"
              @click="changeColor($event)"
            ></span>
          </div>
          <div class="container">
            <div
              class="chart"
              id="V3"
              @dblclick="showchart('V3', data.V3)"
            ></div>
            <!--            <button class="btn" id="V3导联" @click="showchart('V3',data.V3)">展开</button>-->
            <span
              class="light"
              id="V3light"
              @click="changeColor($event)"
            ></span>
          </div>
          <div class="container">
            <div
              class="chart"
              id="V4"
              @dblclick="showchart('V4', data.V4)"
            ></div>
            <!--            <button class="btn" id="V4导联" @click="showchart('V4',data.V4)">展开</button>-->
            <span
              class="light"
              id="V4light"
              @click="changeColor($event)"
            ></span>
          </div>
          <div class="container">
            <div
              class="chart"
              id="V5"
              @dblclick="showchart('V5', data.V5)"
            ></div>
            <!--            <button class="btn" id="V5导联" @click="showchart('V5',data.V5)">展开</button>-->
            <span
              class="light"
              id="V5light"
              @click="changeColor($event)"
            ></span>
          </div>
          <div class="container">
            <div
              class="chart"
              id="V6"
              @dblclick="showchart('V6', data.V6)"
            ></div>
            <!--            <button class="btn" id="V6导联" @click="showchart('V6',data.V6)">展开</button>-->
            <span
              class="light"
              id="V6light"
              @click="changeColor($event)"
            ></span>
          </div>
        </div>
      </div>
      <div class="bottomRight">
        <div class="xinDian">质量评估</div>
        <div class="quality">
          <div class="lights">
            <div class="abcd">
              <el-radio-group
                v-model="noise_level.Ilevel"
                @change="changeRadios(noise_level.Ilevel, 'Ilevel')"
              >
                <el-radio label="A" border size="mini" class="radios"
                  >A</el-radio
                >
                <el-radio label="B" border size="mini" class="radios"
                  >B</el-radio
                >
                <el-radio label="C" border size="mini" class="radios"
                  >C</el-radio
                >
                <el-radio label="D" border size="mini" class="radios"
                  >D</el-radio
                >
              </el-radio-group>
            </div>
            <div class="abcd">
              <el-radio-group
                v-model="noise_level.IIlevel"
                @change="changeRadios(noise_level.IIlevel, 'IIlevel')"
              >
                <el-radio label="A" border size="mini" class="radios"
                  >A</el-radio
                >
                <el-radio label="B" border size="mini" class="radios"
                  >B</el-radio
                >
                <el-radio label="C" border size="mini" class="radios"
                  >C</el-radio
                >
                <el-radio label="D" border size="mini" class="radios"
                  >D</el-radio
                >
              </el-radio-group>
            </div>
            <div class="abcd">
              <el-radio-group
                v-model="noise_level.IIIlevel"
                @change="changeRadios(noise_level.IIIlevel, 'IIIlevel')"
              >
                <el-radio label="A" border size="mini" class="radios"
                  >A</el-radio
                >
                <el-radio label="B" border size="mini" class="radios"
                  >B</el-radio
                >
                <el-radio label="C" border size="mini" class="radios"
                  >C</el-radio
                >
                <el-radio label="D" border size="mini" class="radios"
                  >D</el-radio
                >
              </el-radio-group>
            </div>
            <div class="abcd">
              <el-radio-group
                v-model="noise_level.aVRlevel"
                @change="changeRadios(noise_level.aVRlevel, 'aVRlevel')"
              >
                <el-radio label="A" border size="mini" class="radios"
                  >A</el-radio
                >
                <el-radio label="B" border size="mini" class="radios"
                  >B</el-radio
                >
                <el-radio label="C" border size="mini" class="radios"
                  >C</el-radio
                >
                <el-radio label="D" border size="mini" class="radios"
                  >D</el-radio
                >
              </el-radio-group>
            </div>
            <div class="abcd">
              <el-radio-group
                v-model="noise_level.aVLlevel"
                @change="changeRadios(noise_level.aVLlevel, 'aVLlevel')"
              >
                <el-radio label="A" border size="mini" class="radios"
                  >A</el-radio
                >
                <el-radio label="B" border size="mini" class="radios"
                  >B</el-radio
                >
                <el-radio label="C" border size="mini" class="radios"
                  >C</el-radio
                >
                <el-radio label="D" border size="mini" class="radios"
                  >D</el-radio
                >
              </el-radio-group>
            </div>
            <div class="abcd">
              <el-radio-group
                v-model="noise_level.aVFlevel"
                @change="changeRadios(noise_level.aVFlevel, 'aVFlevel')"
              >
                <el-radio label="A" border size="mini" class="radios"
                  >A</el-radio
                >
                <el-radio label="B" border size="mini" class="radios"
                  >B</el-radio
                >
                <el-radio label="C" border size="mini" class="radios"
                  >C</el-radio
                >
                <el-radio label="D" border size="mini" class="radios"
                  >D</el-radio
                >
              </el-radio-group>
            </div>
            <div class="abcd">
              <el-radio-group
                v-model="noise_level.V1level"
                @change="changeRadios(noise_level.V1level, 'V1level')"
              >
                <el-radio label="A" border size="mini" class="radios"
                  >A</el-radio
                >
                <el-radio label="B" border size="mini" class="radios"
                  >B</el-radio
                >
                <el-radio label="C" border size="mini" class="radios"
                  >C</el-radio
                >
                <el-radio label="D" border size="mini" class="radios"
                  >D</el-radio
                >
              </el-radio-group>
            </div>
            <div class="abcd">
              <el-radio-group
                v-model="noise_level.V2level"
                @change="changeRadios(noise_level.V2level, 'V2level')"
              >
                <el-radio label="A" border size="mini" class="radios"
                  >A</el-radio
                >
                <el-radio label="B" border size="mini" class="radios"
                  >B</el-radio
                >
                <el-radio label="C" border size="mini" class="radios"
                  >C</el-radio
                >
                <el-radio label="D" border size="mini" class="radios"
                  >D</el-radio
                >
              </el-radio-group>
            </div>
            <div class="abcd">
              <el-radio-group
                v-model="noise_level.V3level"
                @change="changeRadios(noise_level.V3level, 'V3level')"
              >
                <el-radio label="A" border size="mini" class="radios"
                  >A</el-radio
                >
                <el-radio label="B" border size="mini" class="radios"
                  >B</el-radio
                >
                <el-radio label="C" border size="mini" class="radios"
                  >C</el-radio
                >
                <el-radio label="D" border size="mini" class="radios"
                  >D</el-radio
                >
              </el-radio-group>
            </div>
            <div class="abcd">
              <el-radio-group
                v-model="noise_level.V4level"
                @change="changeRadios(noise_level.V4level, 'V4level')"
              >
                <el-radio label="A" border size="mini" class="radios"
                  >A</el-radio
                >
                <el-radio label="B" border size="mini" class="radios"
                  >B</el-radio
                >
                <el-radio label="C" border size="mini" class="radios"
                  >C</el-radio
                >
                <el-radio label="D" border size="mini" class="radios"
                  >D</el-radio
                >
              </el-radio-group>
            </div>
            <div class="abcd">
              <el-radio-group
                v-model="noise_level.V5level"
                @change="changeRadios(noise_level.V5level, 'V5level')"
              >
                <el-radio label="A" border size="mini" class="radios"
                  >A</el-radio
                >
                <el-radio label="B" border size="mini" class="radios"
                  >B</el-radio
                >
                <el-radio label="C" border size="mini" class="radios"
                  >C</el-radio
                >
                <el-radio label="D" border size="mini" class="radios"
                  >D</el-radio
                >
              </el-radio-group>
            </div>
            <div class="abcd">
              <el-radio-group
                v-model="noise_level.V6level"
                @change="changeRadios(noise_level.V6level, 'V6level')"
              >
                <el-radio label="A" border size="mini" class="radios"
                  >A</el-radio
                >
                <el-radio label="B" border size="mini" class="radios"
                  >B</el-radio
                >
                <el-radio label="C" border size="mini" class="radios"
                  >C</el-radio
                >
                <el-radio label="D" border size="mini" class="radios"
                  >D</el-radio
                >
              </el-radio-group>
            </div>
          </div>
          <div class="allin">
            <button class="green" @click="allA">全A</button>
            <button class="red" @click="allB">全B</button>
            <button class="red" @click="allC">全C</button>
            <button class="red" @click="allD">全D</button>
          </div>
        </div>
      </div>

      <!-- 要修改的部分 -->
      <div class="topLeft">
        <div class="topMiddle">
          <div class="warning">预警类型</div>
          <div class="warningDetail">
            <form id="loginForm" name="loginForm" class="biaodan">
              <!-- <el-select
                v-model="value"
                multiple
                filterable
                allow-create
                default-first-option
                placeholder="请选择啊"
              >
                <el-option-group
                  v-for="group in options"
                  :key="group.label"
                  :label="group.label"
                >
                  <el-option
                    v-for="item in group.options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-option-group>
              </el-select> -->

              <div class="duoxuan">
                <el-checkbox-group v-model="trueValues">
                  <div v-for="(group,index) in options" :key="index">
                    <div class="fenzuzhuti">
                      {{ group.label }}
                    </div>
                    <div class="fenzuzhutizi">
                      <ul class="xiaoul">
                        <li
                          v-for="(item,i) in group.options"
                          class="xiaoli"
                          :key="i"
                        >
                          <el-checkbox
                            :checked="item.leixing"
                            :label="item.value"
                            border
                            size="mini"
                          >
                            {{ item.value }}
                            <!-- {{item.leixing}} -->
                            <!-- {{item.value == logUserList[index].logType}} -->
                            <!-- v-model="item.xuanzhongzhuangtai" 选定之后的状态-->
                            <!-- :checked="item.value == logUserList[index].logType" -->
                            <!-- {{item.value}} -->
                            <!-- {{item.value==logUserList[index].logType}} -->
                            <!-- {{index}} -->
                          </el-checkbox>
                        </li>
                      </ul>
                    </div>
                    <div class="xian"></div>
                  </div>
                </el-checkbox-group>
              </div>
            </form>
            <!-- <div class="tijiao">
              <el-button type="success" id="btn1" class="btn1" @click="submit()">
              提交
              </el-button>
              <el-button class="btn2" id="btn2" @click="suspected">是否疑似病理</el-button>

                <el-button
                  class="next"
                  v-if="state"
                  @click="prev"
                  type="primary"
                  :loading="loading"
                  >上一个</el-button
                >
                <el-button class="next" v-if="state" @click="next" :loading="loading"
                  >下一个</el-button>


            </div> -->
          </div>
        </div>
        <!-- <div class="page">
          <el-button
            class="next"
            v-if="state"
            @click="prev"
            type="primary"
            :loading="loading"
            >上一个</el-button
          >
          <el-button class="next" v-if="state" @click="next" :loading="loading"
            >下一个</el-button
          >
        </div> -->
      </div>

      <!-- <div class="duoxuan">
          <div>
            <div v-for="group in options" :key="group.label">
              <div class="fenzuzhuti">
                {{ group.label }}
              </div>
              <div class="fenzuzhutizi">
                <el-checkbox
                  v-for="item in group.options"
                  :key="item.value"
                  v-model="checked1"
                  :label="item.label"
                  border
                  size="mini"
                >
                  {{ item.label }}
                </el-checkbox>
              </div>
              <div class="xian"></div>
            </div>
          </div>
      </div> -->
    </div>
  </div>
</template>

<script>
import { getReportByPId } from "@/api/report/report";
import {
  PatientInformation,
  selectList,
  getLabel,
  addLabel,
  islabel,
  listLog_user,
} from "@/api/log_user/log_user";
import {addCount} from "@/api/alert_log_count/count"
//单导预警页面数据
import { listAlert_log } from "@/api/alert_log/alert_log";
import $ from "jquery";
import * as echarts from "@/views/ECGScreen/detail/echarts.min";
import de from "element-ui/src/locale/lang/de";
export default {
  name: "lookLog1",
  computed: {
    de() {
      return de;
    },
  },
  data() {
    return {
      luyou:'',
      testArray:[],
      trueValues:[],
      lead: false,
      tap: {
        P1: false,
        P2: false,
        P3: false,
        R1: false,
        R2: false,
        R3: false,
        T1: false,
        T2: false,
        T3: false,
        //P:false,
        // Q:false,
        // R:false,
        // S:false,
        // T:false,
        // noise:false
      },
      radio: "",
      //通过字典将radio和light一一对应
      levellight: {
        V1level: "V1light",
        V2level: "V2light",
        V3level: "V3light",
        V4level: "V4light",
        V5level: "V5light",
        V6level: "V6light",
        aVLlevel: "aVLlight",
        aVFlevel: "aVFlight",
        aVRlevel: "aVRlight",
        Ilevel: "Ilight",
        IIlevel: "IIlight",
        IIIlevel: "IIIlight",
      },
      lightlevel: {
        V1light: "V1level",
        V2light: "V2level",
        V3light: "V3level",
        V4light: "V4level",
        V5light: "V5level",
        V6light: "V6level",
        aVLlight: "aVLlevel",
        aVFlight: "aVFlevel",
        aVRlight: "aVRlevel",
        Ilight: "Ilevel",
        IIlight: "IIlevel",
        IIIlight: "IIIlevel",
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
        logType: "",
      },
      query: {
        userId: "",
        logId: "",
        waveLabel: "",
      },
      value: "正常心电图",
      options: [],
      timex: [],
      state: true,
      show: false,
      seriesdata: [
        { yAxis: -3 },
        { yAxis: -2.5 },
        { yAxis: -2 },
        { yAxis: -1.5 },
        { yAxis: -1 },
        { yAxis: -0.5 },
        { yAxis: 0 },
        { yAxis: 0.5 },
        { yAxis: 1 },
        { yAxis: 1.5 },
        { yAxis: 2 },
        { yAxis: 2.5 },
        { yAxis: 3 },
      ],
      seriesdata1: [
        { yAxis: -1 },
        { yAxis: -0.5 },
        { yAxis: 0 },
        { yAxis: 0.5 },
        { yAxis: 1 },
      ],
      data: {},
      chartjump: null,
      xIndex: null,
      delX: { key: null, value: null }, //想要删除的点
      area: [], //想要删除的区间
      markArea: [],
      title: "",
      subData: {
        P1: [],
        P2: [],
        P3: [],
        R1: [],
        R2: [],
        R3: [],
        T1: [],
        T2: [],
        T3: [],
      }, //提交标注信息

      pointdata: [], //画点的option
      point: [[], { P: [], Q: [], R: [], S: [], T: [] }],
      arr: [], //要删除的点所在的区间
      isSuspected: false,
      pageNum: 1,
      logUserList: [],
      index: 0,
      loading: false,
      logUserListTotal: 0,
      anoStatus: null,
      typeObj: {},
      pageSize: 10,
    };
  },
  watch: {
    isSuspected(val) {
      if (this.isSuspected) {
        document.getElementById("btn2").style.backgroundColor = "#4cc9f0";
      } else {
        document.getElementById("btn2").style.backgroundColor =
          "rgba(255,255,255,0)";
      }
    },
  },
  created() {
     // 检查 window.history.state 是否存在值
     console.log(window.history);
    const state = window.history.state;
    if (state) {
      console.log("回到查看标注页面");
      console.log(state);
    }
    // console.log(this.typeObj);
    // console.log('created')
    console.log("刚进查看标注页面时，路由上的值");
    console.log(this.$route.query);
    // console.log(this.$route.query.queryParams);

    if (this.$route.query.logId) {
      // console.log(this.$route.query.anoStatus);//null
      this.anoStatus = this.$route.query.anoStatus; //null
      this.message.logid = this.$route.query.logId; //8eba952e-1a24-5598-ac8d-7f6ad47a0275
      // this.message.logType = this.$route.query.logType;
      this.message.user_id = this.$route.query.userId; //0
      // this.value=this.$route.query.logType;
      this.query.logId = this.$route.query.logId; //8eba952e-1a24-5598-ac8d-7f6ad47a0275
      this.query.userId = this.$route.query.userId; //0
      this.pageNum = this.$route.query.pageNum; //1
      this.pageSize = this.$route.query.pageSize; //10
      this.typeObj = this.$route.query.queryParams; //{}

      // 获取预警类选项型表
      this.getSelectList();
      // 获取数据患者列表 数据标注
      this.getLogUserList();
      // this.getSelectList();
      this.getLabel();
      // console.log("ttt"+this.options);
      // this.xunhuan()
    }
  },
  beforeRouteLeave(to, from, next){
    // 在离开页面 B 前保存当前状态
    const currentState = {
      pageNum: this.pageNum,
      pageSize: this.pageSize,
      typeObj: this.typeObj,
      state:this.$route.query.state,
      anoStatus:this.anoStatus,
      queryParams:this.$route.query.queryParams,
      userId: this.message.user_id,
      logId:this.message.logid
    };
    console.log("离开查看标注页面时存的值：");
    console.log(currentState);
    

    // 获取之前的文档标题
    const previousTitle = document.title;
     // 将当前状态存储到 window.history.state 中

    window.history.replaceState(currentState,previousTitle);
    console.log(document.title);
    // 继续路由导航
    next();
  },




//     beforeRouteEnter(to, from, next) {
//   // 获取之前存入的 newUrl
//   const newUrl = this.luyou?this.luyou:''

//   // 进入页面 B 之后，将 URL 替换为 newUrl
//   window.history.replaceState("", "", newUrl);

//   // 在进入页面 B 之后，根据 newUrl 加载对应的页面内容
//   // 这里你需要根据你的业务逻辑来加载页面内容，例如重新发起请求或者更新页面数据
//   console.log('Loading content for newUrl:', newUrl);
//   // 在这里你需要根据 newUrl 加载对应的页面内容，例如重新发起请求或者更新页面数据
//   // 在这里你需要根据 newUrl 加载对应的页面内容，例如重新发起请求或者更新页面数据
//   next();
// },


  mounted() {
    this.getMessage();
    this.chartjump = echarts.init(document.getElementById("chartjump"));
    // console.log(this.options);
  },
  methods: {
    clicktest(value){
      if(this.testArray.includes(value)){
        this.testArray.splice(this.testArray.indexOf(value),1)
      }else{
        this.testArray.push(value)
      }
    },
    goTarget(href) {
      window.open(href, "_blank");
    },
    // 1获取预警类型表
    getSelectList() {
      selectList().then((res) => {
        this.options = res.data;
        console.log("这是获取预警类型表this.options");

        //this.options[0].options[0].xuanzhongzhuangtai = true
        console.log(this.options);
        // console.log(this.logUserList);

        // for (let i = 0; i < this.options.length; i++) {
        //   let options = this.options[i].options;
        //   for (let j = 0; j < options.length; j++) {
        //     options[j].xuanzhongzhuangtai = false; // 添加新的键值对
        //   }
        // }
        
      });
    },
    // 2获取数据标注页面数据
    async getLogUserList() {
      console.log("执行了getLogUserList函数，获取患者数据");
      console.log("这是this.$route.query.state的值"+this.$route.query.state);
      // this.getSelectList();
      console.log(this.$route.query);
      if (this.$route.query.state == 1) {

        // this.typeObj = this.$route.query.queryParams;
        let queryParams = this.typeObj;
        // // console.log(this.typeObj);//undefined
        let obj = {
          logId: queryParams.logId ? queryParams.logId : "",
          userId: queryParams.userId ? queryParams.userId : "",
          ecgType: this.$route.query.ecgType,
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          anoStatus: queryParams.anoStatus,
          logTime: queryParams.logTime,
          eventDescription: queryParams.eventDescription,
          eventName: queryParams.eventName,
          eventDescription: queryParams.eventDescription,
          pId: queryParams.pId,
          isSuspected: queryParams.isSuspected,
        };
        
        console.log("查看标注页获取患者信息所需要的值");
        console.log(obj);
        await listAlert_log(obj).then((response) => {
          this.logUserList = response.rows;
          console.log("这是从单导预警页面跳转到查看标注页面获取到的数据");
          console.log(this.logUserList);
          console.log(this.options);
          this.logUserListTotal = response.total;
          this.logUserList.forEach((item, index) => {
            if (this.message.logid == item.logId) {
              this.index = index;
            }
          });
        })
        console.log(this.logUserList.length);
        if (this.index == this.logUserList.length ) {
          this.index = 0
        }
        console.log("这是this.index的值："+this.index);
        
        console.log(this.logUserList[this.index].eventDescription);
        // 假设 this.index 是你要访问的 logUserList 数组中的索引
        if (this.logUserList[this.index].eventDescription) {
          // 拆分 logType 字符串为一个数组
          let logTypesArray = this.logUserList[this.index].eventDescription.split(',');

          // 将拆分后的数组中的每个值添加到 trueValues 数组中
          this.trueValues.push(...logTypesArray);
        }
        
      } else if (this.$route.query.state == 12) {
        // console.log(this.typeObj);//undefined
        this.typeObj = this.$route.query.queryParams;
        let queryParams = this.typeObj;
        // console.log(this.typeObj);//undefined

        let obj = {
          logId: queryParams.logId ? queryParams.logId : "",
          userId: this.$route.query.userId ? this.$route.query.userId : "",
          ecgType: this.$route.query.ecgType,
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          anoStatus: this.anoStatus,
          logTime: queryParams.logTime,
          eventDescription: queryParams.eventDescription,
          eventName: queryParams.eventName,
          eventDescription: queryParams.eventDescription,
          pId: queryParams.pId,
          isSuspected: queryParams.isSuspected,
        };
        console.log("查看标注页获取患者信息所需要的值");
        console.log(obj);
        await listAlert_log(obj).then((response) => {
          this.logUserList = response.rows;

          console.log("这是从12导预警页面跳转到查看标注页面获取到的数据");
          console.log(this.logUserList);
         
          this.logUserListTotal = response.total;
          this.logUserList.forEach((item, index) => {
            if (this.message.logid == item.logId) {
              this.index = index;
            }
          });
          // console.log(this.logUserList);
        })
        if (this.index == this.logUserList.length ) {
          this.index = 0
        }
        console.log("这是this.index的值："+this.index);

        console.log(this.logUserList[this.index].eventDescription);
        // 假设 this.index 是你要访问的 logUserList 数组中的索引
        if (this.logUserList[this.index].eventDescription) {
          // 拆分 logType 字符串为一个数组
          let logTypesArray = this.logUserList[this.index].eventDescription.split(',');

          // 将拆分后的数组中的每个值添加到 trueValues 数组中
          this.trueValues.push(...logTypesArray);
        }
      } else {
        // console.log(this.typeObj);//undefined
        this.typeObj = this.$route.query.queryParams;
        let queryParams = this.typeObj;
        // console.log(this.typeObj);//undefined

        let obj = {
          logId: queryParams.logId ? queryParams.logId : "",
          userId: this.$route.query.userId ? this.$route.query.userId : "",
          ecgType: this.$route.query.ecgType,
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          anoStatus: this.anoStatus,
          logTime: queryParams.logTime,
          eventDescription: queryParams.eventDescription,
          eventName: queryParams.eventName,
          eventDescription: queryParams.eventDescription,
          pId: queryParams.pId,
          isSuspected: queryParams.isSuspected,
        };
        await listLog_user(obj).then((response) => {
          this.logUserList = response.rows;
          // 患者的数组
          console.log("这是从数据标注页面跳转到查看标注页面获取到的数据");
          console.log(this.logUserList);
          this.logUserListTotal = response.total;
          this.logUserList.forEach((item, index) => {
            if (this.message.logid == item.logId) {
              this.index = index;
            }
          });
          // console.log(this.logUserList);
        })
        if (this.index == this.logUserList.length ) {
          this.index = 0
        }
        console.log("这是this.index的值："+this.index);

        console.log(this.logUserList[this.index].eventDescription);
        // 假设 this.index 是你要访问的 logUserList 数组中的索引
        if (this.logUserList[this.index].eventDescription) {
          // 拆分 logType 字符串为一个数组
          let logTypesArray = this.logUserList[this.index].eventDescription.split(',');

          // 将拆分后的数组中的每个值添加到 trueValues 数组中
          this.trueValues.push(...logTypesArray);
        }
      }
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
      var timex = [];
      var chartI = echarts.init(document.getElementById("I"));
      var chartII = echarts.init(document.getElementById("II"));
      var chartIII = echarts.init(document.getElementById("III"));
      var chartV1 = echarts.init(document.getElementById("V1"));
      var chartV2 = echarts.init(document.getElementById("V2"));
      var chartV3 = echarts.init(document.getElementById("V3"));
      var chartV4 = echarts.init(document.getElementById("V4"));
      var chartV5 = echarts.init(document.getElementById("V5"));
      var chartV6 = echarts.init(document.getElementById("V6"));
      var chartaVR = echarts.init(document.getElementById("aVR"));
      var chartaVL = echarts.init(document.getElementById("aVL"));
      var chartaVF = echarts.init(document.getElementById("aVF"));
      var _th = this;
      $.ajax({
        type: "POST",
        url: "https://screen.mindyard.cn:84/getId",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify({
          log_id: this.message.logid,
          user_id: this.message.user_id,
        }),
        success: function (jsonResult) {
          _th.data = jsonResult.result;
          console.log(jsonResult);
          _th.message.pid = jsonResult.result.patientid;
          _th.message.age = Number(jsonResult.result.age).toFixed(0);
          _th.message.sex = jsonResult.result.sex;
          _th.message.time = jsonResult.result.clockTime;
          if (jsonResult.result.isSuspected == 1) {
            _th.isSuspected = true;
          } else {
            _th.isSuspected = false;
          }
          _th.value = jsonResult.result.logType;
          _th.loading = false;
          _th.light(jsonResult);
          _th.level(jsonResult);
          if (_th.message.devicesn != null) {
            (function () {
              var i;
              for (var k = 0; k < 1001; k++) {
                timex.push(k / 100 + "秒");
              }
              _th.timex = timex;
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
              for (var i = 0; i < 2500; i += 20) {
                _th.seriesdata.push({ xAxis: i });
              }
              var seriesdata = _th.seriesdata;
              if (jsonResult.result.II.length === 1000) {
                for (var i = 0; i < 1000; i += 20) {
                  _th.seriesdata1.push({ xAxis: i });
                }
                seriesdata = _th.seriesdata1;
              }
              chartI.clear();
              chartI.setOption({
                animation: false,
                animationDuration: 9900,
                animationEasing: "linear",
                animationEasingUpdate: "linear",
                animationDurationUpdate: 10000,
                animationDelayUpdate: 0,
                animationThreshold: 10000,
                backgroundColor: "#ffffff",
                title: {
                  text: "I导联",
                  textStyle: {
                    fontSize: 12,
                    color: "#000000",
                  },
                  left: 30,
                },
                tooltip: {
                  trigger: "axis",
                  axisPointer: {
                    type: "cross",
                  },
                },
                grid: {
                  left: 20,
                  right: 0,
                  top: 2,
                  bottom: 2,
                },
                xAxis: {
                  type: "category",
                  boundaryGap: false,
                  data: timex,
                  axisTick: {
                    show: false,
                  },
                  axisLabel: {
                    //修改坐标系字体颜色
                    show: false,
                    interval: 3,
                    textStyle: {
                      color: "#000000",
                    },
                  },
                  splitLine: {
                    show: true,

                    lineStyle: {
                      color: "pink",
                      width: 0.5, //网格的宽度
                      type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
                    },
                  } /*网格线*/,
                },
                yAxis: {
                  min: -1,
                  max: 1,
                  boundaryGap: true,
                  interval: 0.1,
                  // splitNumber: 20,
                  axisLabel: {
                    //修改坐标系字体颜色
                    show: false,
                    textStyle: {
                      color: "#000000",
                    },
                  },
                  splitLine: {
                    lineStyle: {
                      color: "pink",
                      width: 1, //网格的宽度
                      type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
                    },
                    show: true,
                  } /*网格线*/,
                },
                series: {
                  markLine: {
                    animation: false,
                    symbol: "none",
                    silent: true,
                    lineStyle: {
                      type: "solid",
                      color: "#b33939",
                      width: 0.5,
                      // opacity: 0.5,
                    },
                    label: {
                      position: "start", // 表现内容展示的位置
                      color: "#b33939", // 展示内容颜色
                    },
                    data: seriesdata,
                  },
                  /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
                  itemStyle: {
                    normal: {
                      lineStyle: {
                        width: 1.5,
                        color: "#000000" /*折线的颜色*/,
                      },
                      color:
                        "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/,
                    },
                  },
                  symbol: "none",
                  /*去掉小圆点*/
                  name: "I导联",
                  type: "line",
                  data: Iy,
                  smooth: 0, //显示为平滑的曲线*/
                },
              });
              $(window).resize(function () {
                chartI.resize();
              });
              chartII.clear();
              chartII.setOption({
                animation: false,
                animationDuration: 9900,
                animationEasing: "linear",
                animationEasingUpdate: "linear",
                animationDurationUpdate: 10000,
                animationDelayUpdate: 0,
                animationThreshold: 10000,
                backgroundColor: "#ffffff",
                title: {
                  text: "II导联",
                  textStyle: {
                    fontSize: 12,
                    color: "#000000",
                  },
                  left: 30,
                },
                tooltip: {
                  trigger: "axis",
                  axisPointer: {
                    type: "cross",
                  },
                },
                grid: {
                  left: 20,
                  right: 0,
                  top: 2,
                  bottom: 2,
                },

                xAxis: {
                  type: "category",
                  boundaryGap: false,
                  data: timex,
                  axisTick: {
                    show: false,
                  },
                  axisLabel: {
                    //修改坐标系字体颜色
                    show: false,
                    interval: 3,
                    textStyle: {
                      color: "#000000",
                    },
                  },
                  splitLine: {
                    show: true,

                    lineStyle: {
                      color: "pink",
                      width: 0.5, //网格的宽度
                      type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
                    },
                  } /*网格线*/,
                },
                yAxis: {
                  min: -1,
                  max: 1,
                  boundaryGap: true,
                  interval: 0.1,
                  axisLabel: {
                    //修改坐标系字体颜色
                    show: false,
                    textStyle: {
                      color: "#000000",
                    },
                  },
                  splitLine: {
                    lineStyle: {
                      color: "pink",
                      width: 1, //网格的宽度
                      type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
                    },
                    show: true,
                  } /*网格线*/,
                },
                series: {
                  markLine: {
                    animation: false,
                    symbol: "none",
                    silent: true,
                    lineStyle: {
                      type: "solid",
                      color: "#b33939",
                      width: 0.5,
                    },
                    label: {
                      position: "start", // 表现内容展示的位置
                      color: "#b33939", // 展示内容颜色
                    },
                    data: seriesdata,
                  },
                  /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
                  itemStyle: {
                    normal: {
                      lineStyle: {
                        width: 1.5,
                        color: "#000000" /*折线的颜色*/,
                      },
                      color:
                        "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/,
                    },
                  },
                  symbol: "none",
                  /*去掉小圆点*/
                  name: "II导联",
                  type: "line",
                  data: IIy,
                  smooth: 0, //显示为平滑的曲线*/
                },
              });
              $(window).resize(function () {
                chartII.resize();
              });
              chartV1.clear();
              chartV1.setOption({
                animation: false,
                animationDuration: 9900,
                animationEasing: "linear",
                animationEasingUpdate: "linear",
                animationDurationUpdate: 10000,
                animationDelayUpdate: 0,
                animationThreshold: 10000,
                backgroundColor: "#ffffff",
                title: {
                  text: "V1导联",
                  textStyle: {
                    fontSize: 12,
                    color: "#000000",
                  },
                  left: 30,
                },
                tooltip: {
                  trigger: "axis",
                  axisPointer: {
                    type: "cross",
                  },
                },
                grid: {
                  left: 20,
                  right: 0,
                  top: 2,
                  bottom: 2,
                },
                xAxis: {
                  type: "category",
                  boundaryGap: false,
                  data: timex,
                  axisTick: {
                    show: false,
                  },
                  axisLabel: {
                    //修改坐标系字体颜色
                    show: false,
                    interval: 3,
                    textStyle: {
                      color: "#000000",
                    },
                  },
                  splitLine: {
                    show: true,
                    lineStyle: {
                      color: "pink",
                      width: 0.5, //网格的宽度
                      type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
                    },
                  } /*网格线*/,
                },
                yAxis: {
                  min: -1,
                  max: 1,
                  boundaryGap: true,
                  interval: 0.1,
                  // splitNumber: 20,
                  axisLabel: {
                    //修改坐标系字体颜色
                    show: false,
                    textStyle: {
                      color: "#000000",
                    },
                  },
                  splitLine: {
                    lineStyle: {
                      color: "pink",
                      width: 1, //网格的宽度
                      type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
                    },
                    show: true,
                  } /*网格线*/,
                },
                series: {
                  markLine: {
                    animation: false,
                    symbol: "none",
                    silent: true,
                    lineStyle: {
                      type: "solid",
                      color: "#b33939",
                      width: 0.5,
                      // opacity: 0.5,
                    },
                    label: {
                      position: "start", // 表现内容展示的位置
                      color: "#b33939", // 展示内容颜色
                    },
                    data: seriesdata,
                  },
                  /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
                  itemStyle: {
                    normal: {
                      lineStyle: {
                        width: 1.5,
                        color: "#000000" /*折线的颜色*/,
                      },
                      color:
                        "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/,
                    },
                  },
                  symbol: "none",
                  /*去掉小圆点*/
                  name: "V1导联",
                  type: "line",
                  data: V1y,
                  smooth: 0, //显示为平滑的曲线*/
                },
              });
              $(window).resize(function () {
                chartV1.resize();
              });
              chartV2.clear();
              chartV2.setOption({
                animation: false,
                animationDuration: 9900,
                animationEasing: "linear",
                animationEasingUpdate: "linear",
                animationDurationUpdate: 10000,
                animationDelayUpdate: 0,
                animationThreshold: 10000,
                backgroundColor: "#ffffff",
                title: {
                  text: "V2导联",
                  textStyle: {
                    fontSize: 12,
                    color: "#000000",
                  },
                  left: 30,
                },
                tooltip: {
                  trigger: "axis",
                  axisPointer: {
                    type: "cross",
                  },
                },
                grid: {
                  left: 20,
                  right: 0,
                  top: 2,
                  bottom: 2,
                },
                xAxis: {
                  type: "category",
                  boundaryGap: false,
                  data: timex,
                  axisTick: {
                    show: false,
                  },
                  axisLabel: {
                    //修改坐标系字体颜色
                    show: false,
                    interval: 3,
                    textStyle: {
                      color: "#000000",
                    },
                  },
                  splitLine: {
                    show: true,
                    lineStyle: {
                      color: "pink",
                      width: 0.5, //网格的宽度
                      type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
                    },
                  } /*网格线*/,
                },
                yAxis: {
                  min: -1,
                  max: 1,
                  boundaryGap: true,
                  interval: 0.1,
                  axisLabel: {
                    //修改坐标系字体颜色
                    show: false,
                    textStyle: {
                      color: "#000000",
                    },
                  },
                  splitLine: {
                    lineStyle: {
                      color: "pink",
                      width: 1, //网格的宽度
                      type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
                    },
                    show: true,
                  } /*网格线*/,
                },
                series: {
                  markLine: {
                    animation: false,
                    symbol: "none",
                    silent: true,
                    lineStyle: {
                      type: "solid",
                      color: "#b33939",
                      width: 0.5,
                    },
                    label: {
                      position: "start", // 表现内容展示的位置
                      color: "#b33939", // 展示内容颜色
                    },
                    data: seriesdata,
                  },
                  /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
                  itemStyle: {
                    normal: {
                      lineStyle: {
                        width: 1.5,
                        color: "#000000" /*折线的颜色*/,
                      },
                      color:
                        "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/,
                    },
                  },
                  symbol: "none",
                  /*去掉小圆点*/
                  name: "V2导联",
                  type: "line",
                  data: V2y,
                  smooth: 0, //显示为平滑的曲线*/
                },
              });
              $(window).resize(function () {
                chartV2.resize();
              });
              chartV3.clear();
              chartV3.setOption({
                animation: false,
                animationDuration: 9900,
                animationEasing: "linear",
                animationEasingUpdate: "linear",
                animationDurationUpdate: 10000,
                animationDelayUpdate: 0,
                animationThreshold: 10000,
                backgroundColor: "#ffffff",
                title: {
                  text: "V3导联",
                  textStyle: {
                    fontSize: 12,
                    color: "#000000",
                  },
                  left: 30,
                },
                tooltip: {
                  trigger: "axis",
                  axisPointer: {
                    type: "cross",
                  },
                },
                grid: {
                  left: 20,
                  right: 0,
                  top: 2,
                  bottom: 2,
                },
                xAxis: {
                  type: "category",
                  boundaryGap: false,
                  data: timex,
                  axisTick: {
                    show: false,
                  },
                  axisLabel: {
                    //修改坐标系字体颜色
                    show: false,
                    interval: 3,
                    textStyle: {
                      color: "#000000",
                    },
                  },
                  splitLine: {
                    show: true,
                    lineStyle: {
                      color: "pink",
                      width: 0.5, //网格的宽度
                      type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
                    },
                  } /*网格线*/,
                },
                yAxis: {
                  min: -1,
                  max: 1,
                  boundaryGap: true,
                  interval: 0.1,
                  axisLabel: {
                    //修改坐标系字体颜色
                    show: false,
                    textStyle: {
                      color: "#000000",
                    },
                  },
                  splitLine: {
                    lineStyle: {
                      color: "pink",
                      width: 1, //网格的宽度
                      type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
                    },
                    show: true,
                  } /*网格线*/,
                },
                series: {
                  markLine: {
                    animation: false,
                    symbol: "none",
                    silent: true,
                    lineStyle: {
                      type: "solid",
                      color: "#b33939",
                      width: 0.5,
                    },
                    label: {
                      position: "start", // 表现内容展示的位置
                      color: "#b33939", // 展示内容颜色
                    },
                    data: seriesdata,
                  },
                  /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
                  itemStyle: {
                    normal: {
                      lineStyle: {
                        width: 1.5,
                        color: "#000000" /*折线的颜色*/,
                      },
                      color:
                        "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/,
                    },
                  },
                  symbol: "none",
                  /*去掉小圆点*/
                  name: "V3导联",
                  type: "line",
                  data: V3y,
                  smooth: 0, //显示为平滑的曲线*/
                },
              });
              $(window).resize(function () {
                chartV3.resize();
              });
              chartV4.clear();
              chartV4.setOption({
                animation: false,
                animationDuration: 9900,
                animationEasing: "linear",
                animationEasingUpdate: "linear",
                animationDurationUpdate: 10000,
                animationDelayUpdate: 0,
                animationThreshold: 10000,
                backgroundColor: "#ffffff",
                title: {
                  text: "V4导联",
                  textStyle: {
                    fontSize: 12,
                    color: "#000000",
                  },
                  left: 30,
                },
                tooltip: {
                  trigger: "axis",
                  axisPointer: {
                    type: "cross",
                  },
                },
                grid: {
                  left: 20,
                  right: 0,
                  top: 2,
                  bottom: 2,
                },

                xAxis: {
                  type: "category",
                  boundaryGap: false,
                  data: timex,
                  axisTick: {
                    show: false,
                  },
                  axisLabel: {
                    //修改坐标系字体颜色
                    show: false,
                    interval: 3,
                    textStyle: {
                      color: "#000000",
                    },
                  },
                  splitLine: {
                    show: true,
                    lineStyle: {
                      color: "pink",
                      width: 0.5, //网格的宽度
                      type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
                    },
                  } /*网格线*/,
                },
                yAxis: {
                  min: -1,
                  max: 1,
                  boundaryGap: true,
                  interval: 0.1,
                  // splitNumber: 20,
                  axisLabel: {
                    //修改坐标系字体颜色
                    show: false,
                    textStyle: {
                      color: "#000000",
                    },
                  },
                  splitLine: {
                    lineStyle: {
                      color: "pink",
                      width: 1, //网格的宽度
                      type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
                    },
                    show: true,
                  } /*网格线*/,
                },
                series: {
                  markLine: {
                    animation: false,
                    symbol: "none",
                    silent: true,
                    lineStyle: {
                      type: "solid",
                      color: "#b33939",
                      width: 0.5,
                    },
                    label: {
                      position: "start", // 表现内容展示的位置
                      color: "#b33939", // 展示内容颜色
                    },
                    data: seriesdata,
                  },
                  /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
                  itemStyle: {
                    normal: {
                      lineStyle: {
                        width: 1.5,
                        color: "#000000" /*折线的颜色*/,
                      },
                      color:
                        "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/,
                    },
                  },
                  symbol: "none",
                  /*去掉小圆点*/
                  name: "V4导联",
                  type: "line",
                  data: V4y,
                  smooth: 0, //显示为平滑的曲线*/
                },
              });
              $(window).resize(function () {
                chartV4.resize();
              });
              chartV5.clear();
              chartV5.setOption({
                animation: false,
                animationDuration: 9900,
                animationEasing: "linear",
                animationEasingUpdate: "linear",
                animationDurationUpdate: 10000,
                animationDelayUpdate: 0,
                animationThreshold: 10000,
                backgroundColor: "#ffffff",
                title: {
                  text: "V5导联",
                  textStyle: {
                    fontSize: 12,
                    color: "#000000",
                  },
                  left: 30,
                },
                tooltip: {
                  trigger: "axis",
                  axisPointer: {
                    type: "cross",
                  },
                },
                grid: {
                  left: 20,
                  right: 0,
                  top: 2,
                  bottom: 2,
                },
                xAxis: {
                  type: "category",
                  boundaryGap: false,
                  data: timex,
                  axisTick: {
                    show: false,
                  },
                  axisLabel: {
                    //修改坐标系字体颜色
                    show: false,
                    interval: 3,
                    textStyle: {
                      color: "#000000",
                    },
                  },
                  splitLine: {
                    show: true,
                    lineStyle: {
                      color: "pink",
                      width: 0.5, //网格的宽度
                      type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
                    },
                  } /*网格线*/,
                },
                yAxis: {
                  min: -1,
                  max: 1,
                  boundaryGap: true,
                  interval: 0.1,
                  axisLabel: {
                    //修改坐标系字体颜色
                    show: false,
                    textStyle: {
                      color: "#000000",
                    },
                  },
                  splitLine: {
                    lineStyle: {
                      color: "pink",
                      width: 1, //网格的宽度
                      type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
                    },
                    show: true,
                  } /*网格线*/,
                },
                series: {
                  markLine: {
                    animation: false,
                    symbol: "none",
                    silent: true,
                    lineStyle: {
                      type: "solid",
                      color: "#b33939",
                      width: 0.5,
                    },
                    label: {
                      position: "start", // 表现内容展示的位置
                      color: "#b33939", // 展示内容颜色
                    },
                    data: seriesdata,
                  },
                  /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
                  itemStyle: {
                    normal: {
                      lineStyle: {
                        width: 1.5,
                        color: "#000000" /*折线的颜色*/,
                      },
                      color:
                        "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/,
                    },
                  },
                  symbol: "none",
                  /*去掉小圆点*/
                  name: "V5导联",
                  type: "line",
                  data: V5y,
                  smooth: 0, //显示为平滑的曲线*/
                },
              });
              $(window).resize(function () {
                chartV5.resize();
              });
              chartV6.clear();
              chartV6.setOption({
                animation: false,
                animationDuration: 9900,
                animationEasing: "linear",
                animationEasingUpdate: "linear",
                animationDurationUpdate: 10000,
                animationDelayUpdate: 0,
                animationThreshold: 10000,
                backgroundColor: "#ffffff",
                title: {
                  text: "V6导联",
                  textStyle: {
                    fontSize: 12,
                    color: "#000000",
                  },
                  left: 30,
                },
                tooltip: {
                  trigger: "axis",
                  axisPointer: {
                    type: "cross",
                  },
                },
                grid: {
                  left: 20,
                  right: 0,
                  top: 2,
                  bottom: 2,
                },
                xAxis: {
                  type: "category",
                  boundaryGap: false,
                  data: timex,
                  axisTick: {
                    show: false,
                  },
                  axisLabel: {
                    //修改坐标系字体颜色
                    show: true,
                    interval: 3,
                    textStyle: {
                      color: "#000000",
                    },
                  },
                  splitLine: {
                    show: true,
                    lineStyle: {
                      color: "pink",
                      width: 0.5, //网格的宽度
                      type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
                    },
                  } /*网格线*/,
                },
                yAxis: {
                  min: -1,
                  max: 1,
                  boundaryGap: true,
                  interval: 0.1,
                  axisLabel: {
                    //修改坐标系字体颜色
                    show: false,
                    textStyle: {
                      color: "#000000",
                    },
                  },
                  splitLine: {
                    lineStyle: {
                      color: "pink",
                      width: 1, //网格的宽度
                      type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
                    },
                    show: true,
                  } /*网格线*/,
                },
                series: [
                  {
                    markLine: {
                      animation: false,
                      symbol: "none",
                      silent: true,
                      lineStyle: {
                        type: "solid",
                        color: "#b33939",
                        width: 0.5,
                      },
                      label: {
                        position: "start", // 表现内容展示的位置
                        color: "#b33939", // 展示内容颜色
                      },
                      data: seriesdata,
                    },
                    /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
                    itemStyle: {
                      normal: {
                        lineStyle: {
                          width: 1.5,
                          color: "#000000" /*折线的颜色*/,
                        },
                        color:
                          "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/,
                      },
                    },
                    symbol: "none",
                    /*去掉小圆点*/
                    name: "当前电位",
                    type: "line",
                    data: V6y,
                    smooth: 0, //显示为平滑的曲线*/
                  },
                ],
              });
              $(window).resize(function () {
                chartV6.resize();
              });
              chartIII.clear();
              chartIII.setOption({
                animation: false,
                animationDuration: 9900,
                animationEasing: "linear",
                animationEasingUpdate: "linear",
                animationDurationUpdate: 10000,
                animationDelayUpdate: 0,
                animationThreshold: 10000,
                backgroundColor: "#ffffff",
                title: {
                  text: "III导联",
                  textStyle: {
                    fontSize: 12,
                    color: "#000000",
                  },
                  left: 30,
                },
                tooltip: {
                  trigger: "axis",
                  axisPointer: {
                    type: "cross",
                  },
                },
                grid: {
                  left: 20,
                  right: 0,
                  top: 2,
                  bottom: 2,
                },

                xAxis: {
                  type: "category",
                  boundaryGap: false,
                  data: timex,
                  axisTick: {
                    show: false,
                  },
                  axisLabel: {
                    //修改坐标系字体颜色
                    show: false,
                    interval: 3,
                    textStyle: {
                      color: "#000000",
                    },
                  },
                  splitLine: {
                    show: true,
                    lineStyle: {
                      color: "pink",
                      width: 0.5, //网格的宽度
                      type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
                    },
                  } /*网格线*/,
                },
                yAxis: {
                  min: -1,
                  max: 1,
                  boundaryGap: true,
                  interval: 0.1,
                  // splitNumber: 20,
                  axisLabel: {
                    //修改坐标系字体颜色
                    show: false,
                    textStyle: {
                      color: "#000000",
                    },
                  },
                  splitLine: {
                    lineStyle: {
                      color: "pink",
                      width: 1, //网格的宽度
                      type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
                    },
                    show: true,
                  } /*网格线*/,
                },
                series: {
                  markLine: {
                    animation: false,
                    symbol: "none",
                    silent: true,
                    lineStyle: {
                      type: "solid",
                      color: "#b33939",
                      width: 0.5,
                    },
                    label: {
                      position: "start", // 表现内容展示的位置
                      color: "#b33939", // 展示内容颜色
                    },
                    data: seriesdata,
                  },
                  /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
                  itemStyle: {
                    normal: {
                      lineStyle: {
                        width: 1.5,
                        color: "#000000" /*折线的颜色*/,
                      },
                      color:
                        "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/,
                    },
                  },
                  symbol: "none",
                  /*去掉小圆点*/
                  name: "III导联",
                  type: "line",
                  data: IIIy,
                  smooth: 0, //显示为平滑的曲线*/
                },
              });
              $(window).resize(function () {
                chartIII.resize();
              });
              chartaVL.clear();
              chartaVL.setOption({
                animation: false,
                animationDuration: 9900,
                animationEasing: "linear",
                animationEasingUpdate: "linear",
                animationDurationUpdate: 10000,
                animationDelayUpdate: 0,
                animationThreshold: 10000,
                backgroundColor: "#ffffff",
                title: {
                  text: "aVL导联",
                  textStyle: {
                    fontSize: 12,
                    color: "#000000",
                  },
                  left: 30,
                },
                tooltip: {
                  trigger: "axis",
                  axisPointer: {
                    type: "cross",
                  },
                },
                grid: {
                  left: 20,
                  right: 0,
                  top: 2,
                  bottom: 2,
                },
                xAxis: {
                  type: "category",
                  boundaryGap: false,
                  data: timex,
                  axisTick: {
                    show: false,
                  },
                  axisLabel: {
                    //修改坐标系字体颜色
                    show: false,
                    interval: 3,
                    textStyle: {
                      color: "#000000",
                    },
                  },
                  splitLine: {
                    show: true,
                    lineStyle: {
                      color: "pink",
                      width: 0.5, //网格的宽度
                      type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
                    },
                  } /*网格线*/,
                },
                yAxis: {
                  min: -1,
                  max: 1,
                  boundaryGap: true,
                  interval: 0.1,
                  axisLabel: {
                    //修改坐标系字体颜色
                    show: false,
                    textStyle: {
                      color: "#000000",
                    },
                  },
                  splitLine: {
                    lineStyle: {
                      color: "pink",
                      width: 1, //网格的宽度
                      type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
                    },
                    show: true,
                  } /*网格线*/,
                },
                series: {
                  markLine: {
                    animation: false,
                    symbol: "none",
                    silent: true,
                    lineStyle: {
                      type: "solid",
                      color: "#b33939",
                      width: 0.5,
                    },
                    label: {
                      position: "start", // 表现内容展示的位置
                      color: "#b33939", // 展示内容颜色
                    },
                    data: seriesdata,
                  },
                  /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
                  itemStyle: {
                    normal: {
                      lineStyle: {
                        width: 1.5,
                        color: "#000000" /*折线的颜色*/,
                      },
                      color:
                        "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/,
                    },
                  },
                  symbol: "none",
                  /*去掉小圆点*/
                  name: "aVL导联",
                  type: "line",
                  data: aVLy,
                  smooth: 0, //显示为平滑的曲线*/
                },
              });
              $(window).resize(function () {
                chartaVL.resize();
              });
              chartaVF.clear();
              chartaVF.setOption({
                animation: false,
                animationDuration: 9900,
                animationEasing: "linear",
                animationEasingUpdate: "linear",
                animationDurationUpdate: 10000,
                animationDelayUpdate: 0,
                animationThreshold: 10000,
                backgroundColor: "#ffffff",
                title: {
                  text: "aVF导联",
                  textStyle: {
                    fontSize: 12,
                    color: "#000000",
                  },
                  left: 30,
                },
                tooltip: {
                  trigger: "axis",
                  axisPointer: {
                    type: "cross",
                  },
                },
                grid: {
                  left: 20,
                  right: 0,
                  top: 2,
                  bottom: 2,
                },
                xAxis: {
                  type: "category",
                  boundaryGap: false,
                  data: timex,
                  axisTick: {
                    show: false,
                  },
                  axisLabel: {
                    //修改坐标系字体颜色
                    show: false,
                    interval: 3,
                    textStyle: {
                      color: "#000000",
                    },
                  },
                  splitLine: {
                    show: true,
                    lineStyle: {
                      color: "pink",
                      width: 0.5, //网格的宽度
                      type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
                    },
                  } /*网格线*/,
                },
                yAxis: {
                  min: -1,
                  max: 1,
                  boundaryGap: true,
                  interval: 0.1,
                  axisLabel: {
                    //修改坐标系字体颜色
                    show: false,
                    textStyle: {
                      color: "#000000",
                    },
                  },
                  splitLine: {
                    lineStyle: {
                      color: "pink",
                      width: 1, //网格的宽度
                      type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
                    },
                    show: true,
                  } /*网格线*/,
                },
                series: {
                  markLine: {
                    animation: false,
                    symbol: "none",
                    silent: true,
                    lineStyle: {
                      type: "solid",
                      color: "#b33939",
                      width: 0.5,
                    },
                    label: {
                      position: "start", // 表现内容展示的位置
                      color: "#b33939", // 展示内容颜色
                    },
                    data: seriesdata,
                  },
                  /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
                  itemStyle: {
                    normal: {
                      lineStyle: {
                        width: 1.5,
                        color: "#000000" /*折线的颜色*/,
                      },
                      color:
                        "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/,
                    },
                  },
                  symbol: "none",
                  /*去掉小圆点*/
                  name: "aVF导联",
                  type: "line",
                  data: aVFy,
                  smooth: 0, //显示为平滑的曲线*/
                },
              });
              $(window).resize(function () {
                chartaVF.resize();
              });
              chartaVR.clear();
              chartaVR.setOption({
                animation: false,
                animationDuration: 9900,
                animationEasing: "linear",
                animationEasingUpdate: "linear",
                animationDurationUpdate: 10000,
                animationDelayUpdate: 0,
                animationThreshold: 10000,
                backgroundColor: "#ffffff",
                title: {
                  text: "aVR导联",
                  textStyle: {
                    fontSize: 12,
                    color: "#000000",
                  },
                  left: 30,
                },
                tooltip: {
                  trigger: "axis",
                  axisPointer: {
                    type: "cross",
                  },
                },
                grid: {
                  left: 20,
                  right: 0,
                  top: 2,
                  bottom: 2,
                },
                xAxis: {
                  type: "category",
                  boundaryGap: false,
                  data: timex,
                  axisTick: {
                    show: false,
                  },
                  axisLabel: {
                    //修改坐标系字体颜色
                    show: false,
                    interval: 3,
                    textStyle: {
                      color: "#000000",
                    },
                  },
                  splitLine: {
                    show: true,
                    lineStyle: {
                      color: "pink",
                      width: 0.5, //网格的宽度
                      type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
                    },
                  } /*网格线*/,
                },
                yAxis: {
                  min: -1,
                  max: 1,
                  boundaryGap: true,
                  interval: 0.1,
                  // splitNumber: 20,
                  axisLabel: {
                    //修改坐标系字体颜色
                    show: false,
                    textStyle: {
                      color: "#000000",
                    },
                  },
                  splitLine: {
                    lineStyle: {
                      color: "pink",
                      width: 1, //网格的宽度
                      type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
                    },
                    show: true,
                  } /*网格线*/,
                },
                series: {
                  markLine: {
                    animation: false,
                    symbol: "none",
                    silent: true,
                    lineStyle: {
                      type: "solid",
                      color: "#b33939",
                      width: 0.5,
                    },
                    label: {
                      position: "start", // 表现内容展示的位置
                      color: "#b33939", // 展示内容颜色
                    },
                    data: seriesdata,
                  },
                  /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
                  itemStyle: {
                    normal: {
                      lineStyle: {
                        width: 1.5,
                        color: "#000000" /*折线的颜色*/,
                      },
                      color:
                        "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/,
                    },
                  },
                  symbol: "none",
                  /*去掉小圆点*/
                  name: "aVR导联",
                  type: "line",
                  data: aVRy,
                  smooth: 0, //显示为平滑的曲线*/
                },
              });
              $(window).resize(function () {
                chartaVR.resize();
              });
            })();
          } else {
            _th.$modal.msgError("设备未连接");
          }
          console.log(jsonResult.result);
        },
        error: function (data) {
          console.log(data);
          _th.$modal.msgError("数据获取失败");
        },
      });
    },
    //获取标注数据
    getLabel() {
      console.log("这是this.query");
      console.log(this.query);
      getLabel(this.query)
        .then((res) => {
          console.log("2400");
          console.log(res);
          if (res.data.waveLabel != null) {
            this.subData = JSON.parse(res.data.waveLabel);
          }
        })
        .catch((err) => {});
    },
    //判断红绿颜色
    light(data) {
      this.noise_list = data.result.noise;
      // console.log(this.noise_list)
      for (var key in this.noise_list) {
        if (this.noise_list[key] === 1) {
          let temp = document.getElementById(key);
          temp.style.backgroundColor = "red";
        } else {
          let temp = document.getElementById(key);
          temp.style.backgroundColor = "greenyellow";
        }
      }
    },
    //ABCD等级的判断
    level(data) {
      this.noise_level = data.result.noise_level;
      // console.log("传的ABCD的等级", this.noise_level)
    },
    //修改红绿颜色框的颜色
    changeColor(tid) {
      console.log("红绿颜色修改点击事件：", tid);
      var b = tid.target.id;
      console.log("点击获取到的导联id：", b);
      let temp = document.getElementById(b);
      if (this.noise_list[b] === 0) {
        this.noise_list[b] = 1;
        temp.style.backgroundColor = "red";
        this.noise_level[this.lightlevel[b]] = "B";
        console.log("点击修改之后的噪声数据：", this.noise_list);
      } else {
        this.noise_list[b] = 0;
        temp.style.backgroundColor = "greenyellow";
        this.noise_level[this.lightlevel[b]] = "A";
        console.log("点击修改之后的噪声数据：", this.noise_list);
      }
    },
    //全为A
    allA() {
      for (var k in this.noise_level) {
        console.log(this.noise_level[k]);
        this.noise_level[k] = "A";
        this.noise_list[this.levellight[k]] = 0;
        let temp = document.getElementById(this.levellight[k]);
        temp.style.backgroundColor = "greenyellow";
      }
    },
    //全为B
    allB() {
      for (var k in this.noise_level) {
        this.noise_level[k] = "B";
        this.noise_list[this.levellight[k]] = 1;
        let temp = document.getElementById(this.levellight[k]);
        temp.style.backgroundColor = "red";
      }
    },
    //全为C
    allC() {
      for (var k in this.noise_level) {
        this.noise_level[k] = "C";
        this.noise_list[this.levellight[k]] = 1;
        let temp = document.getElementById(this.levellight[k]);
        temp.style.backgroundColor = "red";
      }
    },
    //全为D
    allD() {
      for (var k in this.noise_level) {
        this.noise_level[k] = "D";
        this.noise_list[this.levellight[k]] = 1;
        let temp = document.getElementById(this.levellight[k]);
        temp.style.backgroundColor = "red";
      }
    },
    //修改等级的程度
    changeRadios(value, lab) {
      // console.log(value)
      // console.log(lab)
      let dom = document.getElementById(this.levellight[lab]);
      if (value === "A") {
        dom.style.backgroundColor = "greenyellow";
        this.noise_list[this.levellight[lab]] = 0;
      } else {
        dom.style.backgroundColor = "red";
        this.noise_list[this.levellight[lab]] = 1;
      }

      console.log("修改之后的等级：", this.noise_level);
      console.log("修改之后的等级：", this.noise_list);
    },
    //重置按钮
    cleartap() {
      for (let tapKey in this.tap) {
        this.tap[tapKey] = false;
      }
    },
    //合并重复框选区域
    drawMarkArea(newitem, a) {
      if (this.markArea.length !== 0) {
        var result = this.markArea.findIndex((v) => {
          return (
            (v[0].xAxis <= newitem[0] && v[1].xAxis >= newitem[0]) ||
            (v[0].xAxis <= newitem[1] && v[1].xAxis >= newitem[1]) ||
            (v[0].xAxis >= newitem[0] && v[1].xAxis <= newitem[1])
          );
        });
        console.log("是否有交叉", result);
        if (result == -1) {
          this.markArea.push(a);
        } else {
          var max = Math.max(
            this.markArea[result][0].xAxis,
            this.markArea[result][1].xAxis,
            newitem[0],
            newitem[1]
          );
          var min = Math.min(
            this.markArea[result][0].xAxis,
            this.markArea[result][1].xAxis,
            newitem[0],
            newitem[1]
          );
          a[0].xAxis = min;
          a[1].xAxis = max;
          this.markArea.splice(result, 1, a);
        }
      } else {
        this.markArea.push(a);
      }
      this.chartjump.setOption({
        series: [
          {
            id: "series1",
            markArea: {
              label: {
                show: true,
                position: "inside",
              },
              data: this.markArea,
            },
          },
        ],
      });
    },

    clearData() {
      this.pointdata = [];
      this.subData = {
        P1: [],
        P2: [],
        P3: [],
        R1: [],
        R2: [],
        R3: [],
        T1: [],
        T2: [],
        T3: [],
      };
      this.chartjump.setOption({
        series: {
          markPoint: {
            symbol: "pin",
            symbolSize: 24,
            animation: false,
            data: [],
          },
        },
      });
    },
    suspected() {
      this.isSuspected = !this.isSuspected;
    },
    async prev() {
      this.loading = true;
      
      if (this.pageNum == 1 && this.index == 0) {
        this.$message.warning("已经是第一页！！！");
        this.loading = false;
        return;
      }
      this.index--;
      if (this.index < 0) {
        if (this.pageNum > 1) {
          this.pageNum--;
        }
        await this.getLogUserList();
        this.index = this.pageSize - 1;
      }
      console.log(this.logUserList[this.index]);
      this.message.logid = this.logUserList[this.index].logId;
      this.message.user_id = this.logUserList[this.index].userId;
      let anoStatus = "";
      if (this.anoStatus != null) {
        anoStatus = `&anoStatus=${this.anoStatus}`;
      }
      this.trueValues=[]
            // 假设 this.index 是你要访问的 logUserList 数组中的索引
              if (this.logUserList[this.index].eventDescription) {
                // 拆分 logType 字符串为一个数组
                let logTypesArray = this.logUserList[this.index].eventDescription.split(',');
                // 将拆分后的数组中的每个值添加到 trueValues 数组中
                this.trueValues.push(...logTypesArray);
              }
              // this.trueValues=["心房颤动","干扰信号"]
              console.log("logType中的值，经过处理后放到this.trueValues值为：");
              console.log(this.trueValues);
      var newUrl =
        this.$route.path +
        `?logId=${this.message.logid}&userId=${this.message.user_id}&pageNum=${this.pageNum}&pageSize=${this.pageSize}` +
        anoStatus +
        `&queryParams=${this.typeObj}`;
      window.history.replaceState("", "", newUrl);
      this.getMessage();
      
    },
    // 点击下一页触发事件
    async next() {
      this.loading = true;
      this.index++;
      console.log("点击了下一页1");
      console.log(this.index);
      console.log(this.logUserList.length); //0

      if (this.index >= this.logUserList.length) {
        console.log("获取新的患者数据10");
        if (
          (this.pageNum - 1) * this.pageSize + this.logUserList.length >=
          this.logUserListTotal
        ) {
          this.$message.warning("已经是最后一页！！！");
          this.index--;
          this.loading = false;
          return;
        }
        console.log("点击了下一页");
        this.pageNum++;
        await this.getLogUserList();
        this.index = 0;
      }
      console.log(
        "点击下一页，触发获取getLogUserList函数，获得10条患者数据，下面是患者数据"
      );
      // console.log(this.logUserList[this.index]);
      console.log(this.logUserList);

      console.log("查看logUserList");
      console.log(this.logUserList[this.index].logId);
      console.log(this.logUserList[this.index].userId);
      console.log("查看logUserList");

      this.message.logid = this.logUserList[this.index].logId;
      this.message.user_id = this.logUserList[this.index].userId;
      let anoStatus = "";

      if (this.anoStatus != null) {
        anoStatus = `&anoStatus=${this.anoStatus}`;
      }

      this.trueValues=[]
            // 假设 this.index 是你要访问的 logUserList 数组中的索引
              if (this.logUserList[this.index].logType) {
                // 拆分 logType 字符串为一个数组
                let logTypesArray = this.logUserList[this.index].logType.split(',');

                // 将拆分后的数组中的每个值添加到 trueValues 数组中
                this.trueValues.push(...logTypesArray);
              }
              // this.trueValues=["心房颤动","干扰信号"]
              console.log("logType中的值，经过处理后放到this.trueValues值为：");
              console.log(this.trueValues);
      var newUrl =
        this.$route.path +
        `?logId=${this.message.logid}&userId=${this.message.user_id}&pageNum=${this.pageNum}&pageSize=${this.pageSize}` +
        anoStatus +
        `&queryParams=${this.typeObj}&state=${this.$route.query.state}`;
        console.log(newUrl);
        console.log(this.typeObj);


// let currentState = {
//       pageNum: this.pageNum,
//       pageSize: this.pageSize,
//       typeObj: this.typeObj,
//       state:this.$route.query.state,
//       anoStatus:anoStatus,
//       queryParams:this.typeObj,
//       userId: this.message.user_id,
//       logId:this.message.logid
//     };
//     console.log("离开查看标注页面时存的值：");
//     console.log(currentState);
    

//     // 获取之前的文档标题
//     let previousTitle = document.title;


      this.luyou = newUrl

      window.history.replaceState("", "", newUrl);
      this.getMessage();
      
    },

    // 点击提交
    submit() {
      console.log(this.trueValues);
      console.log("点击提交时的this.options的值");
      console.log(this.options);
      // let trueValues = [];

      for (let i = 0; i < this.options.length; i++) {
        let options = this.options[i].options;
        for (let j = 0; j < options.length; j++) {
          if (options[j].xuanzhongzhuangtai == true) {
            this.trueValues.push(options[j].value);
          }
        }
      }

      this.value = this.trueValues.join();
      console.log("这是选中的值" + this.trueValues);
      console.log("this.message.logid"+this.message.logid);
      console.log("this.message.pid"+this.message.pid);
      console.log("this.$route.query.state"+this.$route.query.state);
      console.log("this.value"+this.value);
      console.log("this.noise_list"+this.noise_list);
      console.log("this.noise_level"+this.noise_level);
      // 数据标注中有
      // 单导预警中没有
      console.log(this.message.user_id?this.message.user_id:0);
      console.log(this.options);



      var that = this;
      // return;
      $.ajax({
        cache: true,
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        url: "https://screen.mindyard.cn:84/write_logType",
        data: JSON.stringify({
          id: this.message.logid,
          Type: this.value,//中文
          list: this.noise_list,
          lists: this.noise_level,
          user_id: this.message.user_id,
        }),
        async: false,
        success: function (data) {
          console.log("success:", data);
          that.$modal.msgSuccess("数据提交成功");
        },
        error: function (data) {
          console.log("error:", data);
          //            cocoMessage.error("提交失败", 3000);
        },
      });


      console.log(this.value);
      console.log(this.message.logid);
      console.log(this.message.user_id?this.message.user_id:0);
      console.log(this.isSuspected ? 1 : 0);
      //标注成功
      islabel({
        logType: this.value, //预警类型
        logId: this.message.logid,//日志id
        userId: this.message.user_id,
        isSuspected: this.isSuspected ? 1 : 0, //是否是疑似病例 1
      });

       let selectedValues = [];

      // 遍历trueValues数组
      this.trueValues.forEach((trueValue) => {
        // 遍历options数组
        this.options.forEach((option) => {
          // 遍历当前option对象中的zhong数组
          option.options.forEach((options) => {
            // 如果当前zhongItem对象的label等于trueValue，则将其value添加到selectedValues数组中
            if (options.value === trueValue) {
              selectedValues.push(options.label + "Ecg");
            }
          });
        });
      });
      let dataObject = {
        pId:this.message.pid,
        logId: this.message.logid,
        leadCount:this.$route.query.state
      };
      for (let i = 0; i < selectedValues.length; i++) {
        // 将数组中的每个字符串作为对象的键，值为1，并放入dataObject对象中
        dataObject[selectedValues[i]] = 1;
      }
      // 接口
      console.log(dataObject);
      addCount(dataObject)
      
    },

    submitData() {
      this.query.waveLabel = JSON.stringify(this.subData);
      console.log(this.subData);
      // addLabel(this.query).then(res=>{
      //   this.$modal.msgSuccess("标注提交成功");
      // }).catch(err=>{})
    },
    showchart(title, data) {
      this.title = title;
      if (title == "II") {
        this.lead = true;
      }
      let y = [];
      for (let i = -2; i < 2.1; i += 0.1) {
        y.push(i);
      }
      //console.log(y)
      let detailoption = {
        animation: false,
        backgroundColor: "#ffffff",
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "cross",
          },
        },
        toolbox: {
          show: false,
        },
        dataZoom: [
          {
            show: true, // 滑动条组件
            type: "slider",
            brushSelect: false,
            // y: '90%',
            startValue: 0,
            endValue: 252,
            // minSpan: 52.5,
            // maxSpan: 52.6,
            minValueSpan: 252,
            maxValueSpan: 724,
          },
          {
            show: true, // 滑动条组件
            type: "slider",
            orient: "vertical",
            brushSelect: false,
            startValue: -1,
            endValue: 1,
            minValueSpan: 2.13,
            maxValueSpan: 6,
          },
          {
            type: "inside",
            orient: "vertical", // 鼠标滚轮缩放
            start: 0,
            end: 100,
          },
          {
            type: "inside", // 鼠标滚轮缩放
            start: 0,
            end: 100,
          },
        ],
        grid: {
          left: "3%",
          right: "3%",
          top: "5%",
          bottom: "10%",
        },
        legend: {
          show: false,
          data: ["当前电位"],
          textStyle: { color: "#000000" } /*图例(legend)说明文字的颜色*/,
          left: "right",
        },
        xAxis: {
          type: "category",
          boundaryGap: false,
          data: this.timex,
          axisTick: {
            show: false,
          },
          axisLabel: {
            //修改坐标系字体颜色
            interval: 3,
            show: false,
            textStyle: {
              color: "#000000",
            },
          },
          splitLine: {
            show: true,
            lineStyle: {
              color: "pink",
              width: 1, //网格的宽度
              type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
            },
          } /*网格线*/,
        },
        yAxis: {
          min: -3,
          max: 3,
          //type:'value',
          boundaryGap: false,
          //interval: 0.1,
          splitNumber: 41,
          minInterval: 0.1,
          //data:y,
          axisLabel: {
            //修改坐标系字体颜色
            show: false,
            textStyle: {
              color: "#000000",
            },
          },
          splitLine: {
            show: true,
            lineStyle: {
              color: "pink",
              width: 1, //网格的宽度
              type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
            },
          } /*网格线*/,
        },
        series: {
          id: "series1",
          markLine: {
            animation: false,
            symbol: "none",
            silent: true,
            lineStyle: {
              type: "solid",
              color: "#b33939",
              width: 0.5,
            },
            label: {
              show: true,
              position: "start", // 表现内容展示的位置
              color: "#b33939", // 展示内容颜色
            },
            data: this.seriesdata,
          },
          itemStyle: {
            normal: {
              lineStyle: {
                width: 1.5,
                color: "#000000" /*折线的颜色*/,
              },
              color: "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/,
            },
          },
          symbol: "none",
          name: "当前电位",
          type: "line",
          data: data,
          smooth: true, //显示为平滑的曲线*/
        },
      };
      this.chartjump.clear();
      this.chartjump.setOption(detailoption);
      setTimeout(() => {
        this.chartjump.resize();
      });
      //console.log(this.subData)
      this.markArea.length = 0;
      this.pointdata.length = 0;
      var colorList = {
        P1: "#fe0101",
        P2: "#fe0101",
        P3: "#fe0101",
        R1: "#ff00cf",
        R2: "#ff00cf",
        R3: "#ff00cf",
        T1: "#0021da",
        T2: "#0021da",
        T3: "#0021da",
      };
      //回显
      if (this.lead) {
        console.log("有数据");
        console.log(this.subData);
        for (const key in this.subData) {
          for (let j = 0; j < this.subData[key].length; j++) {
            let pointdata = {
              name: key,
              xAxis: this.subData[key][j],
              yAxis: data[this.subData[key][j]],
              itemStyle: {
                color: colorList[key],
              },
              label: {
                color: "#ffffff",
                show: true,
                formatter: key,
                fontSize: 9,
              },
            };
            this.pointdata.push(pointdata);
          }
        }
        //console.log(this.pointdata)
        this.chartjump.setOption({
          series: {
            markPoint: {
              symbol: "pin",
              symbolSize: 24,
              animation: false,
              data: this.pointdata,
            },
          },
        });
      } else {
        this.pointdata = [];
        this.chartjump.setOption({
          series: {
            markPoint: {
              symbol: "pin",
              symbolSize: 24,
              animation: false,
              data: this.pointdata,
            },
          },
        });
      }
      var width = window.screen.width;
      var height = window.screen.height;
      this.chartjump.off("contextmenu");
      this.chartjump.on("contextmenu", (params) => {
        console.log(params);
        $("#rightMenu").css({
          display: "block",
          left: (params.event.offsetX / width) * 100 + "vw",
          top: (params.event.offsetY / height) * 100 + 11 + "vh",
        });
        if (params.componentType === "markPoint") {
          this.delX.value = params.data.xAxis;
          this.delX.key = params.data.name;
        }
        console.log(this.delX);
      });
      this.chartjump.getZr().off("click");
      this.chartjump.getZr().on("click", (params) => {
        $("#rightMenu").css({
          display: "none",
        });
        const pointInPixel = [params.offsetX, params.offsetY];
        // console.log(pointInPixel)
        if (this.chartjump.containPixel("grid", pointInPixel)) {
          this.xIndex = this.chartjump.convertFromPixel({ seriesIndex: 0 }, [
            params.offsetX,
            params.offsetY,
          ])[0];
          //this.yIndex=this.chartjump.convertFromPixel({seriesIndex:0},[params.offsetX, params.offsetY])[1];
          console.log(this.xIndex);
          let temp = false;
          this.pointdata.forEach((i) => {
            if (this.xIndex == i.xAxis) {
              console.log("已存在");
              temp = true;
              return;
            }
          });
          if (temp) {
            return;
          }
          if (this.radio == "") {
            return;
          }
          this.subData[this.radio].push(this.xIndex);
          //console.log(this.subData)
          let pointdata = {
            name: this.radio,
            xAxis: this.xIndex,
            yAxis: data[this.xIndex],
            itemStyle: {
              color: colorList[this.radio],
            },
            label: {
              color: "#ffffff",
              show: true,
              formatter: this.radio,
              fontSize: 9,
            },
          };
          this.pointdata.push(pointdata);
          this.chartjump.setOption({
            series: {
              markPoint: {
                symbol: "pin",
                symbolSize: 24,
                animation: false,
                data: this.pointdata,
              },
            },
          });
        }
      });
      this.show = true;
    },
    //区间合并
    getMerge(arr) {
      arr.sort((a, b) => {
        if (a[0] !== b[0]) {
          return a[0] - b[0];
        }
        return a[1] - b[1];
      });
      let len = arr.length,
        ans = [],
        start,
        end; // 遍历当前区间的最小值与最大值
      for (let i = 0; i < len; i++) {
        let s = arr[i][0];
        let e = arr[i][1];
        if (start === undefined) {
          start = s;
          end = e;
        } else if (s <= end) {
          end = Math.max(e, end);
        } else {
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
    Off() {
      for (let tapKey in this.tap) {
        this.tap[tapKey] = false;
      }
      this.lead = false;
      this.show = false;
    },
    del() {
      console.log(this.delX.key, this.delX.value);
      this.pointdata.some((item, index) => {
        if (item.xAxis == this.delX.value) {
          this.pointdata.splice(index, 1);
          return true;
        }
      });
      this.subData[this.delX.key].some((item, index) => {
        if (item == this.delX.value) {
          this.subData[this.delX.key].splice(index, 1);
          console.log("删除成功");
          return true;
        }
      });
      this.delX = { key: null, value: null };
      this.chartjump.setOption({
        series: {
          markPoint: {
            symbol: "pin",
            symbolSize: 24,
            animation: false,
            data: this.pointdata,
          },
        },
      });
      $("#rightMenu").css({
        display: "none",
      });
    },
    //判断是一维数组还是二维
    isArray(a) {
      for (let i of a) {
        if (Array.isArray(i)) return 2;
      }
      return 1;
    },
    clickitem(e) {
      e === this.radio ? (this.radio = "") : (this.radio = e);
    },
  },
};
</script>


<style scoped lang="scss">
body,
html {
  color: #00afff;
  box-sizing: border-box;
}
.main {
  width: 100%;
  background: #f1faff;
}

.top {
  height: 9vw;
  width: 100%;
  //border: 4px solid #62be38;
  display: flex;
  flex-direction: row;
  flex-wrap: nowrap;
  justify-content: flex-start;
}
// 患者信息
.patientInformation {
  // border: 1px solid red;
  font-size: 1vw;
  color: #136d87;
  width: 98.5%;
  margin: 1vw 1vw 0 1vw;
}
.patientInformation-biaoti {
  padding-bottom: 0.5vw;
  font-weight: 700;
}
.patientInformation-xiangxi {
  display: flex;
  justify-content: space-between;
  // border: 1px solid red;
}
.topLeft {
  width: 29.5%;
  //height: 100%;
  border-left: 1px solid #136d87;
  display: flex;
  flex-direction: column;
}

.topMiddle {
  width: 100%;
  height: 100%;
  // background-color: #00afff;
}

.topRight {
  width: 100%;
  height: 100%;
}
.showbox {
  //display: none;
  user-select: none;
  position: absolute;
  width: 100%;
  height: 80%;
  border: 1px solid #6eddf1;
  top: 45%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 2000;
  background-color: rgb(255, 255, 255);
  span {
    display: inline-block;
  }
}
.icon {
  font-size: 2vw;
}
.menu {
  /*这个样式不写，右键弹框会一直显示在画布的左下角*/
  position: absolute;
  background: rgba(255, 255, 255);
  left: -99999px;
  top: -999999px;
  padding: 0.1vw;
  border-radius: 0.5vw;
  border: 1px solid #000000;
  .button {
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
  .button:hover {
    color: #ffffff;
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
  // height: 18%;
  font-size: 1vw;
  font-weight: 700;
  color: #136d87;
  text-align: center;
  padding: 0.8vw 0;
  border-bottom: 1px solid #000000;
  //border-top: 1px solid #000000;
}

.warningDetail {
  display: flex;
  flex-direction: column;
  justify-content: center;
  // align-items: center;
  margin: 0 auto;
  //border: 1px solid #ff5a00;
  width: 100%;
  // height: 35%;
  font-size: 1vw;
  ::v-deep .btn2 {
    background-color: rgba(255, 255, 255, 0);
    color: #136d87;
    border: 1px solid #136d87;
    width: 30%;
    margin: 0;
    margin-left: 0;
    padding: 10px 0;
  }
}

.page {
  display: flex;
  justify-content: center;
}
::v-deep .next {
  background-color: rgba(255, 255, 255, 0);
  color: #136d87;
  border: 1px solid #136d87;
  width: 5vw;
  margin: 0;
  padding: 10px 0;
}
.btn1 {
  color: #136d87;
  background-color: rgba(255, 255, 255, 0);
  width: 5vw;
  margin: 0;
  padding: 10px 0;
}
.btn1:hover {
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
.xinDian:first-child {
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
  .btn {
    background-color: #91dff8;
  }
  .btn:hover {
    background-color: #3ec9f7;
  }
}

.bottomLeft .echarts .container .chart {
  height: 9vh;
  width: 99%;
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
  border-radius: 0.5em;
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
.noName {
  //display: inline-flex;

  //width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}
::v-deep .noName .el-radio-group {
  display: block;
  //width: 98%;
}
.popo {
  height: 100%;
}
::v-deep .el-radio-button .el-radio-button__inner {
  display: inline-block;
  width: 4vw;
  height: 4vh;
  padding: 0;
  line-height: 4vh;
  font-size: 1vw;
  text-align: center;
  color: #b33939;
  border: 1px solid #b33939;
  border-radius: 0.5em;
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
.istap {
  background-color: #4cc9f0;
}
.nottap {
  background-color: transparent;
}
.light {
  display: inline-block;
  width: 1%;
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

.radios {
  //border: 0.5px solid rgb(0, 0, 0);
  box-sizing: border-box;
  color: rgb(96, 98, 102);
  cursor: pointer;
  display: block;
  font-family: Helvetica Neue, Helvetica, PingFang SC, Hiragino Sans GB,
    Microsoft YaHei, Arial, sans-serif;
  //font-size: 1.5vw;
  font-weight: 500;
  height: 9vh;
  width: 3vw;
  line-height: 9vh;
  margin: 0;
  padding: 0;
  position: relative;
  text-rendering: optimizelegibility;
  user-select: none;
  white-space: nowrap;
  border: none;
}
::v-deep .el-radio--medium.is-bordered .el-radio__inner {
  height: 1vw;
  width: 1vw;
}
::v-deep .el-radio__inner::after {
  height: 0.3vw;
  width: 0.3vw;
}
::v-deep .el-radio--medium.is-bordered .el-radio__label {
  font-size: 1vw;
  padding: 5px;
}
::v-deep .el-input--medium .el-input__inner {
  color: #136d87;
  background-color: rgba(255, 255, 255, 0);
  border-radius: 0.8vw;
  border-color: #136d87;
}
::v-deep .el-select-dropdown__list {
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
  color: #000;
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
.allin .red:hover {
  cursor: pointer;
  background-color: rgba(231, 34, 34, 0.63);
}
.allin .green:hover {
  cursor: pointer;
  background-color: #c6ff78;
}
.el-radio-group {
  display: flex;
  justify-content: space-around;
}
.el-radio-button:focus:not(.is-focus):not(:active):not(.is-disabled) {
  -webkit-box-shadow: 0 0px 0px #ccc;
  box-shadow: 0 0px 0px #ccc;
}
.duoxuan {
  // border: 1px solid #136d87;
  width: 100%;
  // height: 100px;
  // text-align: center;
}
.warningi {
  width: 100%;
  text-align: center;
}
.fenzuzhuti {
  font-size: 12px;
  color: #909399;
  // border-bottom: 1px solid #000;
}
::v-deep .el-input .el-input--medium .el-input--suffix .is-focus {
  width: 100%;
  border: 1px solid red;
  font-size: 50px;
}
.biaodan {
  width: 100%;
  height: 109vh;
  // border: 1px solid red;
  overflow-y: auto;
  //  overflow: hidden;
  -webkit-overflow-scrolling: touch; /* 提高移动设备上的滚动性能 */
}
/* 隐藏滚动条但仍可滚动 */
.biaodan::-webkit-scrollbar {
  display: none;
}
::v-deep .el-select {
  width: 100%;
}
::v-deep .is-bordered {
  margin: 0;
}
::v-deep .el-checkbox.is-bordered + .el-checkbox.is-bordered {
  margin: 0;
}

.xian {
  border-bottom: 1px solid #000;
}
.fenzuzhuti {
  font-weight: 700;
  // font-style: 20px;
  // font-size: 20px;
  margin-left: 10px;
}
.fenzuzhutizi {
  display: flex;
  // flex-wrap:noweap;
  flex-wrap: wrap;
}
.xiaoul {
  margin: 0.5vh 0 0 0;
  padding: 0;
  width: 100%;
}
.xiaoli {
  list-style: none;
  // motion: 1px;
  padding: 0 0 3.5px 3.5px;
  width: 50%;
  display: block;
  float: left;
}
.tijiao {
  // border-top: 1px solid #136d87;
  display: flex;
  margin-top: 1vh;
  // justify-content: space-around;
  justify-content: flex-end;
  align-items: center;
  // height: 10vh;
  // border: 1px solid red;
  // height: 9vh;
  button {
    margin-right: 1vw;
    // color: #b33939;
  }
  :last-child {
    margin-right: 0;
  }
}
::v-deep .btn2 {
  background-color: rgba(255, 255, 255, 0);
  color: #136d87;
  border: 1px solid #136d87;
  width: 8vw;
  margin: 0;
  margin-left: 0;
  padding: 10px 0;
}
</style>





























