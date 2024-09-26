<template>
  <div class="wrap">
    <div id="pdfDom" style="padding: 10px">
      <div id="app">
        <div class="page">
          <div class="box">
            <div class="touzuo">
              <div class="touzuo-top">
                <el-tabs
                  style="height: 100%; width: 100%"
                  v-model="tabsStatus"
                  type="card"
                  @tab-click="switchTabs"
                >
                  <el-tab-pane label="基本信息" name="userInfo">
                    <div class="tabBox">
                      <table>
                        <tr>
                          <td>姓名</td>
                          <td v-if="isShowName.status === true">
                            {{ data.name }}
                          </td>
                          <td v-else>{{ hideMiddleName(data.name) }}</td>
                          <td style="width: 10%">性别</td>
                          <td>{{ data.gender }}</td>
                          <td>心率</td>
                          <td>{{ data.hr }} bpm</td>
                        </tr>
                        <tr>
                          <td>报告编码</td>
                          <td>{{ data.pId }}</td>
                          <td>年龄</td>
                          <td>{{ data.age }}</td>
                          <td>患者症状</td>
                          <td>{{  data.patientSymptom!='' ? data.patientSymptom : "暂无症状" }}</td>
                        </tr>
                        <tr>
                          <td>AI分析结果</td>
                          <td colspan="3">
                            <textarea
                              type="area"
                              v-model="aiResult"
                              @blur="editAiResult"
                              @focus="recordAiResult(aiResult)"
                              style="
                                width: 100%;
                                height: 100%;
                                display: flex;
                                justify-content: center;
                                align-items: center;
                                border: 0;
                                font-size: 16px;
                              "
                            />
                            <!-- {{ data.result }} -->
                          </td>
                          <td>患者病史</td>
                          <td>{{ getMH(dict.type.medical_history) }}</td>
                        </tr>

                        <tr>
                          <td>P波</td>
                          <td>{{ data.p }} ms</td>
                          <td>QTc间期</td>
                          <td>{{ data.qtc }} ms</td>
                          <td>HRV</td>
                          <td>{{ data.hrv }} ms</td>
                        </tr>
                        <tr>
                          <td>QRS时限</td>
                          <td>{{ data.qrs }} ms</td>
                          <td>QT间期</td>
                          <td>{{data.qt}}ms</td>
                          <td>PR间期</td>
                          <td>{{data.pr}}ms</td>
                        </tr>
<!--                        <tr>-->
<!--                          <td>RV5</td>-->
<!--                          <td>{{data.rv5_sv1- data.sv1 }} mV</td>-->
<!--                          <td>SV1</td>-->
<!--                          <td>{{data.sv1}}mV</td>-->
<!--                          <td>申请单号</td>-->
<!--                          <td>-</td>-->
<!--                        </tr>-->
                      </table>
                    </div>
                  </el-tab-pane>
<!--                  <el-tab-pane-->
<!--                    v-if="!isDoctorUser"-->
<!--                    label="心电参数"-->
<!--                    name="ecgInfo"-->
<!--                  >-->
<!--                    <div class="tabBox">-->
<!--                      <table>-->
<!--                        <tr>-->
<!--                          <td>P波</td>-->
<!--                          <td>{{ data.p }} ms</td>-->
<!--                          <td>QTc</td>-->
<!--                          <td>{{ data.qtc }} ms</td>-->
<!--                          <td>HRV</td>-->
<!--                          <td>{{ data.hrv }} ms</td>-->
<!--                        </tr>-->
<!--                        <tr>-->
<!--                          <td>QRS时限</td>-->
<!--                          <td>{{ data.qrs }} ms</td>-->
<!--                          <td>住院号</td>-->
<!--                          <td>-</td>-->
<!--                          <td>申请单号</td>-->
<!--                          <td></td>-->
<!--                        </tr>-->
<!--                      </table>-->
<!--                    </div>-->
<!--                  </el-tab-pane>-->
                </el-tabs>
              </div>
              <div class="touzuo-btm">
                <div class="yujinclass">
                  <div class="yujinclass_tou" v-if="!isDoctorUser">
                    预警类型:
                  </div>
                  <div class="yujinclass_zhi">
                    {{ xianshizifuchuan }}
                  </div>
                  <div v-if="isShowBtn">
                    <el-button type="success" round @click="xianshi"
                      >选择预警类型</el-button
                    >
                    <el-button type="success" round @click="tijiao()"
                      >提交</el-button
                    >
                  </div>
                </div>
              </div>
            </div>

            <div class="touyou">
              <div class="touzuobiaoti">
                <div>医师诊断</div>
                <el-button
                  type="text"
                  @click="dialogVisible"
                  style="
                    padding: 0;
                    line-height: 4vh;
                    margin-right: 1vw;
                    font-size: 2.5vh;
                  "
                  >新增术语
                </el-button>
                <el-dialog title="新增术语" :visible.sync="dialogVisibleTag">
                  <el-tag
                    :key="tag"
                    v-for="tag in dynamicTags"
                    closable
                    :disable-transitions="false"
                    @close="handleCloseTag(tag)"
                  >
                    {{ tag }}
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
                  <el-button
                    v-else
                    class="button-new-tag"
                    size="small"
                    @click="showInput"
                    >+ 单击新增标签术语</el-button
                  >
                  <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogVisibleTag = false"
                      >取 消</el-button
                    >
                    <el-button type="primary" @click="termTag">确 定</el-button>
                  </div>
                </el-dialog>

                <el-button
                  type="text"
                  @click="Camera"
                  style="
                    padding: 0;
                    line-height: 4vh;
                    margin-right: 1vw;
                    font-size: 2.5vh;
                  "
                  >常用术语
                </el-button>
                <el-dialog title="常用术语" :visible.sync="dialogFormVisible">
                  <div v-for="item in items">
                    <div>{{ item.name }}</div>
                    <button
                      class="commentLabelBtn"
                      :class="{ selected: isSelected }"
                      type="primary"
                      v-for="itemc in item.label"
                      :key="itemc"
                      @click="putDown(itemc, $event)"
                    >
                      {{ itemc }}
                    </button>
                  </div>
                  <div slot="footer" class="dialog-footer">
                    <el-button type="primary" @click="dialogForm"
                      >确 定</el-button
                    >
                  </div>
                </el-dialog>
                <div>
                  <el-button
                    type="success"
                    plain
                    icon="el-icon-view"
                    size="mini"
                    @click="isShowNameClick"
                    v-hasPermi="['ecg:show:conceal']"
                    >{{ isShowName.name }}
                  </el-button>
                </div>
              </div>
              <div class="mt">
                <el-input
                  type="textarea"
                  v-model="data.resultByDoctor"
                  placeholder="请输入"
                  data-value="1111"
                  :rows="5"
                  class="font"
                  >{{ data.resultByDoctor }}
                </el-input>
              </div>

              <div class="doctor">
                <div class="input yishi">
                  <strong>医师:</strong>
                  <!--<el-select v-model="data.doctorName" clearable style="width: 66%">-->
                  <!--  <el-option-->
                  <!--    v-for="item in options"-->
                  <!--    :label="item.doctorName"-->
                  <!--    :value="item.doctorName">-->
                  <!--  </el-option>-->
                  <!--</el-select>-->
                  <el-cascader
                    v-model="data.doctorName"
                    :options="doctorList"
                    @change="selectDoctorChange"
                    :show-all-levels="false"

                  >
                  </el-cascader>
                </div>
                <div class="input">
                  <strong>日期:</strong>
                  <el-input
                    v-if="data.diagnosisData != null"
                    v-model="data.diagnosisData"
                    clearable
                    style="width: 66%"
                  ></el-input>
                  <el-input
                    v-else
                    v-model="data.dataTime"
                    clearable
                    style="width: 34%"
                  ></el-input>
                </div>
              </div>

              <div class="oder">
                <el-button
                  type="success"
                  plain
                  class="anNiu"
                  @click="sendMsgWarning()"
                >
                  <el-tooltip
                    content="请注意20个字数限制，每次用户授权，仅有一次发送的机会"
                    placement="top"
                  >
                    <i class="el-icon-question"></i>
                  </el-tooltip>
                  发送预警</el-button
                >
<!--                <el-button type="success" plain class="anNiu" @click="sendMsg()"-->
<!--                  >发送短信</el-button-->
<!--                >-->
                <el-button type="success" plain class="anNiu" @click="btnUpload"
                  >医生诊断</el-button
                >
                <el-button type="success" plain class="anNiu" @click="findPdfReport()"
                >报告预览</el-button
                >
                <el-button type="success" plain class="anNiu" @click="findLabel()"
                >报告截取预览</el-button
                >
                <el-button
                  class="next"
                  v-if="isShowBtn"
                  @click="prev()"
                  :loading="loading"
                  >上一个</el-button
                >
                <el-button
                  class="next"
                  v-if="isShowBtn"
                  @click="next()"
                  :loading="loading"
                  >下一个</el-button
                >
              </div>

              <!-- <div class="updown shangbianju">
                <el-button
                  class="next"
                  @click="prev()"
                  :loading="loading"
                >上一个</el-button>
                <el-button class="next"  @click="next()" :loading="loading">下一个</el-button>
              </div> -->
            </div>
          </div>

          <!-- 医生诊断弹窗 -->
          <div class="shangbianju">
            <div style="padding: 15px; font-size: 1vw; font-weight: 700">
              患者心电图
            </div>
            <div class="body" id="body">
              <!--            <div class="demo-image__preview">-->
              <!--              <el-image :src="baseImage"></el-image>-->
              <!--            </div>-->
              <div class="body-1">
                <div>
                  <div
                    id="I"
                    class="line"
                    @dblclick="clicktrue('I', data12.dataI)"
                  ></div>
                </div>
                <div>
                  <div
                    id="II"
                    class="line"
                    @dblclick="clicktrue('II', data12.dataII)"
                  ></div>
                </div>
                <div>
                  <div
                    id="III"
                    class="line"
                    @dblclick="clicktrue('III', data12.dataIII)"
                  ></div>
                </div>
                <div>
                  <div
                    id="aVR"
                    class="line"
                    @dblclick="clicktrue('aVR', data12.dataaVR)"
                  ></div>
                </div>
                <div>
                  <div
                    id="aVL"
                    class="line"
                    @dblclick="clicktrue('aVL', data12.dataaVL)"
                  ></div>
                </div>
                <div>
                  <div
                    id="aVF"
                    class="line"
                    @dblclick="clicktrue('aVF', data12.dataaVF)"
                  ></div>
                </div>
              </div>
              <div class="body-1">
                <div>
                  <div
                    id="V1"
                    class="line"
                    @dblclick="clicktrue('V1', data12.dataV1)"
                  ></div>
                </div>
                <div>
                  <div
                    id="V2"
                    class="line"
                    @dblclick="clicktrue('V2', data12.dataV2)"
                  ></div>
                </div>
                <div>
                  <div
                    id="V3"
                    class="line"
                    @dblclick="clicktrue('V3', data12.dataV3)"
                  ></div>
                </div>
                <div>
                  <div
                    id="V4"
                    class="line"
                    @dblclick="clicktrue('V4', data12.dataV4)"
                  ></div>
                </div>
                <div>
                  <div
                    id="V5"
                    class="line"
                    @dblclick="clicktrue('V5', data12.dataV5)"
                  ></div>
                </div>
                <div>
                  <div
                    id="V6"
                    class="line"
                    @dblclick="clicktrue('V6', data12.dataV6)"
                  ></div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <child ref="drawShow" @closeMain="closeMain"></child>

    </div>
    <el-dialog
      title="密码验证"
      :visible.sync="dialogFormVisibleVerifyAuthority"
    >
      <el-form :model="verifyForm" :rules="rules" ref="verifyForm">
        <el-form-item label="验证密码" prop="password">
          <el-input
            placeholder="请输入密码"
            v-model="verifyForm.password"
            show-password
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisibleVerifyAuthority = false"
          >取 消</el-button
        >
        <el-button type="primary" @click="dialogFormVisibleVerify"
          >确 定</el-button
        >
      </div>
    </el-dialog>
    <el-dialog title="选择类型" :visible.sync="tanchuang">
      <form id="loginForm" name="loginForm" class="biaodan">
        <div class="duoxuan">
          <el-checkbox-group v-model="zhi" @change="zhong">
            <div v-for="(group, index) in yujingzhi" :key="index">
              <div class="fenzuzhuti">
                {{ group.label }}
              </div>
              <div class="fenzuzhutizi">
                <ul class="xiaoul">
                  <li
                    v-for="(item, i) in group.options"
                    class="xiaoli"
                    :key="i"
                  >
                    <el-checkbox :label="item.value" border size="mini">
                      {{ item.value }}
                    </el-checkbox>
                  </li>
                </ul>
              </div>
            </div>
          </el-checkbox-group>
        </div>
      </form>
      <div class="biaodananniu">
        <el-button plain @click="quxiao">取消</el-button>
        <el-button type="primary" plain @click="queren">完成</el-button>
      </div>
    </el-dialog>

    <el-dialog title="pdf报告预览" :visible.sync="dpfFindReport" width="90%"
               v-loading="loading1"
    >
      <div>
        <iframe
          width="100%"
          :height="TableHeight"
          allowfullscreen="true"
          :src=src  ref="myFrame">
        </iframe>
      </div>
      <div class="biaodananniu">
        <el-button type="primary" plain @click="dpfFindReport=false">关闭</el-button>
      </div>
    </el-dialog>


    <el-dialog title="预警消息" :visible.sync="Early_warning_message" width="40%">
      <div>
        <el-input
          type="textarea"
          :rows="2"
          placeholder="请输入内容"
          v-model="textarea">
        </el-input>
      </div>
      <div class="biaodananniu" style="margin-top: 10px">
        <el-button plain @click="Early_warning_message = false">取消</el-button>
        <el-button type="primary" plain @click="sendWarnMsg">发 送</el-button>
      </div>
    </el-dialog>

    <el-dialog title="报告打印截取"  style="margin-top: 10px" :visible.sync="Report_printing" width="90%"  @open="open()">

      <el-select v-model="report_printing_type" placeholder="请选择类型" @change="report_printing_init">
        <el-option
          v-for="item in report_printing_options"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>

      <div class="canvas-div-parent">
        <div class="canvas-div" id="chart" ref="chart"></div>
      </div>
      <div class="biaodananniu" style="margin-top: 10px">
        <el-button plain @click="Report_printing = false">取消</el-button>
        <el-button type="primary" plain @click="findPdfReport">预览</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import * as echarts from "@/views/ECGScreen/detail/echarts.min";
import $ from "jquery";
import {
  getCommonTerms,
  addReport,
  getReportByPId,
  updateReport,
  reportEarlyWarningMsg,
} from "@/api/report/report";
import {
  getPdf,
  listDoc,
  sendMsgToPatient,
} from "@/api/patient_management/patient_management";
import { mixins } from "../../../mixins/ecg.js";
import html2canvas from "html2canvas";
import { addOrUpdateTerm, getTerm } from "@/api/staticECG/staticECG";
// 发送信息时获取密码
import { getlogin_password } from "@/api/jecg4_ecg/jecg4_ecg";
import { addLabel } from "@/api/log_user/log_user";
import child from "@/views/staticECG/staticECG/child.vue";
import childLabel from "@/views/ExportPDF/restingECG/childLabel.vue";
import childLabelOne from "@/views/ExportPDF/restingECG/childLabel1.vue";
import JecgPdf from "@/views/jecg/report/index.vue";
import { selectDoctor, getDoctorList } from "@/api/statistics/statistics";
var elementResizeDetectorMaker = require("element-resize-detector");

// 获取预警类型选项
import { selectList } from "@/api/log_user/log_user";
// 存储选择的预警类型
import { addReport as addReportyujing } from "@/api/alert_log_count/count";

import { listPatient_management } from "@/api/patient_management/patient_management";

import { getVerify } from "@/api/verify/verify";

export default {
  name: "index",
  components: { child ,JecgPdf,childLabel,childLabelOne},
  mixins: [mixins],
  dicts: ["medical_history"],
  data() {
    return {
      version: "3.8.3",
      TableHeight: 100,
      src: null,
      flagCre:0,
      report_printing_type:"II",
      report_printing_options:[
        {
          value: 'I',
          label: 'I'
        },
        {
          value: 'II',
          label: 'II'
        },
        {
          value: 'III',
          label: 'III'
        },
        {
          value: 'aVR',
          label: 'aVR'
        },
        {
          value: 'aVL',
          label: 'aVL'
        },
        {
          value: 'aVF',
          label: 'aVF'
        },
        {
          value: 'V1',
          label: 'V1'
        },
        {
          value: 'V2',
          label: 'V2'
        },{
          value: 'V3',
          label: 'V3'
        },
        {
          value: 'V4',
          label: 'V4'
        },
        {
          value: 'V5',
          label: 'V5'
        },
        {
          value: 'V6',
          label: 'V6'
        },

      ],
      dataZoomData:{
        startValue:0, //开始位置
        endValue:500, //结束位置
        maxValueSpan:500, //长度
        minValueSpan:500,
        value:250,
      },
      num:1,
      loading1:false,
      tanchuang: false,
      dpfFindReport: false,
      textarea: null,
      Early_warning_message: false,
      Report_printing: false,
      name: null,
      isShowName: {
        status: false,
        name: "显示姓名",
      },
      on_off: false,
      selectDoctor: [],
      tabsStatus: "userInfo",
      doctorList: [],
      // 输入密码弹窗
      dialogFormVisibleVerifyAuthority: false,
      verifyForm: {
        password: null,
        status: false,
      },
      // 路由
      luyou: "",
      ecgType: "",
      pageNum: 1,
      pageSize: 10,
      queryParams: {},
      loading: false,
      index: 0,
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
      // 总条数
      total: 0,
      // 患者表格数据
      patient_managementList: [],
      // 时间范围
      daterangeConnectionTime: [],
      // ===================================上面是上下页的数据
      // 原先提交过的预警类型
      logDataType: "",
      tijiaoshuju: {},
      zhi: [],
      // xuanzheyujingleixing: true,
      yujingzhi: [],
      xianshizifuchuan: "",

      isSelected: false, //术语按钮没有被按下
      dialogFormVisible: false,
      items: [], //常用术语
      checkButton: [],
      pId: null,
      dynamicTags: ["标签一", "标签二", "标签三"],
      inputVisible: false,
      inputValue: "",
      dialogVisibleTag: null,
      options: [],
      data: {
        pastMedicalHistory: "",
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
        patientSymptom: "",
        hr: "",
        p: "",
        pr: "",
        qrs: "",
        qtc: "",
        hrv: "",
        p_xingeng: "", //心梗率
      },
      markData: [
        { xAxis: 0 },
        { xAxis: 25 },
        { xAxis: 50 },
        { xAxis: 75 },
        { xAxis: 100 },
        { xAxis: 125 },
        { xAxis: 150 },
        { xAxis: 175 },
        { xAxis: 200 },
        { xAxis: 225 },
        { xAxis: 250 },
        { xAxis: 275 },
        { xAxis: 300 },
        { xAxis: 325 },
        { xAxis: 350 },
        { xAxis: 375 },
        { xAxis: 400 },
        { xAxis: 425 },
        { xAxis: 450 },
        { xAxis: 475 },
        { xAxis: 500 },
        { xAxis: 525 },
        { xAxis: 550 },
        { xAxis: 575 },
        { xAxis: 600 },
        { xAxis: 625 },
        { xAxis: 650 },
        { xAxis: 675 },
        { xAxis: 700 },
        { xAxis: 725 },
        { xAxis: 750 },
        { xAxis: 775 },
        { xAxis: 800 },
        { xAxis: 825 },
        { xAxis: 850 },
        { xAxis: 875 },
        { xAxis: 900 },
        { xAxis: 925 },
        { xAxis: 950 },
        { xAxis: 975 },
        { xAxis: 1000 },
        { xAxis: 1000 },
        { xAxis: 1025 },
        { xAxis: 1050 },
        { xAxis: 1075 },
        { xAxis: 1100 },
        { xAxis: 1125 },
        { xAxis: 1150 },
        { xAxis: 1175 },
        { xAxis: 1200 },
        { xAxis: 1225 },
        { xAxis: 1250 },
        { xAxis: 1275 },
        { xAxis: 1300 },
        { xAxis: 1325 },
        { xAxis: 1350 },
        { xAxis: 1375 },
        { xAxis: 1400 },
        { xAxis: 1425 },
        { xAxis: 1450 },
        { xAxis: 1475 },
        { xAxis: 1500 },
        { xAxis: 1525 },
        { xAxis: 1550 },
        { xAxis: 1575 },
        { xAxis: 1600 },
        { xAxis: 1625 },
        { xAxis: 1650 },
        { xAxis: 1675 },
        { xAxis: 1700 },
        { xAxis: 1725 },
        { xAxis: 1750 },
        { xAxis: 1775 },
        { xAxis: 1800 },
        { xAxis: 1825 },
        { xAxis: 1850 },
        { xAxis: 1875 },
        { xAxis: 1900 },
        { xAxis: 1925 },
        { xAxis: 1950 },
        { xAxis: 1975 },
        { xAxis: 2000 },
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
      ], //放大之后标记线
      markdata: [
        { yAxis: -1 },
        { yAxis: -0.5 },
        { yAxis: 0 },
        { yAxis: 0.5 },
        { yAxis: 1 },
      ], //没放大标记线
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
      baseImage: "",
      arr: [],
      datalabel: {
        waveLabel: "",
        beatLabel: "",
      },
      dataLabel:{},
      graphic2: [],
      chartII: null,
      // 表单校验
      rules: {
        password: [
          { required: true, message: "密码不能为空", trigger: "blur" },
        ],
      },
      isShowBtn: true,
      isDoctorUser: false,
      seriesdata: [
        { yAxis: -2 },
        { yAxis: -1.5 },
        { yAxis: -1 },
        { yAxis: -0.5 },
        { yAxis: 0 },
        { yAxis: 0.5 },
        { yAxis: 1 },
        { yAxis: 1.5 },
        { yAxis: 2 },
        { yAxis: -3 },
        { yAxis: -2.5 },
        { yAxis: 3 },
        { yAxis: 2.5 },
      ], //标线
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
      // 判断是不是管理员
      isDoctor: false,
      shangxiakuzhiqi:null,
      myChart: null,
    };
  },
  created() {
    if (!this.$auth.hasRole("admin")) {
      this.isDoctor = true;
    }
    if (this.$route.query.findType){
      let item =  localStorage.getItem('ecgItemData12')
      if (item){
        const itemData = JSON.parse(item);
        if (itemData.pId!=this.$route.query.pId){
          let data = {
            queryParams: this.$route.query.queryParams,
            pId: this.$route.query.pId,
            ecgType: this.$route.query.ecgType,
          }
          localStorage.setItem('ecgItemData12',JSON.stringify(data))
        }
      }else {
        let data = {
          queryParams: this.$route.query.queryParams,
          pId: this.$route.query.pId,
          ecgType: this.$route.query.ecgType,
        }
        localStorage.setItem('ecgItemData12',JSON.stringify(data))
      }
      let ecgDate = JSON.parse(localStorage.getItem('ecgItemData12'))
      this.queryParams = ecgDate.queryParams;
      this.ecgType = ecgDate.ecgType;
      this.pId = ecgDate.pId;
      this.shangxiakuzhiqi  = ecgDate.queryParams.indexzhi
    }else {
      this.pId = this.$route.query.pId;
      this.ecgType = this.$route.query.ecgType;
      this.queryParams = this.$route.query.queryParams;
      this.isShowBtn = false
    }

    this.getList();
  },
  mounted() {
    // var show = sessionStorage.getItem(this.pId + "show");
    // if (show) {
    // this.get();
    // }
    //预警的类型
    // this.getyujingleixing()
    this.getShowBnt();
  },
  methods: {
    // 新增术语
    dialogVisible() {
      getTerm().then((r) => {
        if (r.rows.length > 0) {
          this.dynamicTags = JSON.parse(r.rows[0].termText);
        }
        this.dialogVisibleTag = true;
      });
    },
    //常用术语
    Camera() {
      let _th = this;
      getCommonTerms().then((response) => {
        console.log("常用术语：", response.data);
        const result = Object.entries(response.data).map(([name, label]) => ({
          name,
          label,
        }));
        _th.items = result;
        _th.dialogFormVisible = true;
        console.log("格式过的常用术语：", _th.items);
      });
    },
    //按下常用术语按钮
    putDown(key, event) {
      //console.log(event.currentTarget.classList.toggle('selected'))
      event.currentTarget.classList.toggle("selected");
      let index = this.arr.indexOf(key);
      console.log(index);
      if (index !== -1) {
        this.arr.splice(index, 1);
        this.data.resultByDoctor = this.arr.toString();
      } else {
        this.arr.push(key);
        this.data.resultByDoctor = this.arr.toString();
        console.log(this.arr);
      }
    },
    dialogForm() {
      this.data.resultByDoctor = this.arr.toString();
      this.dialogFormVisible = false;
    },
    getShowBnt() {
      if (this.$route.query.typeStatus && this.$route.query.typeStatus == "1") {
        this.isShowBtn = false;
        return;
      }
      if (this.$auth.hasRole("admin")) {
        this.isShowBtn = true;
      } else if (
        !this.$auth.hasRole("admin") &&
        (this.$auth.hasRole("hospitalUser") || this.$auth.hasRole("doctorUser"))
      ) {
        this.isShowBtn = false;
      }

      if (this.$auth.hasRole("doctorUser") && !this.$auth.hasRole("admin")) {
        this.isDoctorUser = true;
      }
      if (!this.$route.query.findType){
        this.isShowBtn = false;
      }
    },
    // 病史
    getMH(zdList, ecgType = this.data.pastMedicalHistory) {
      let str = "";
      if (ecgType) {
        ecgType.split(",").forEach((item) => {
          if (this.canConvertToInt(item)) {
            zdList.forEach((zd) => {
              if (zd.value == item) {
                str += zd.label + ",";
              }
            });
          } else {
            str += item + ",";
          }
        });
        if (str.endsWith(",")) {
          str = str.substring(0, str.length - 1);
        }
      }
      return str&&str.length>0?str:'无';
    },
    canConvertToInt(value) {
      // 尝试将值转换为整数
      const parsedInt = Number(value);
      // 判断转换后的值是否为整数且不为NaN
      return Number.isInteger(parsedInt);
    },
    // 选择预警类型弹窗按钮 取消
    quxiao() {
      if (this.logDataType) {
        this.xianshizifuchuan = this.logDataType;
        this.zhi = this.logDataType.split(",").map((str) => str.trim());
      }
      // 如果没有原先提交过的值，就将自动分析的结果处理后放入预警类型中默认选中
      else {
        this.zhi = [];
        this.xianshizifuchuan = "";
      }
      this.tanchuang = false;
    },
    // 选择预警类型弹窗按钮 完成
    queren() {
      this.tanchuang = false;
    },
    hideMiddleName(patientName) {
      if (patientName.length <= 1) {
        return "*"; // 一个字的则用一个 * 代替
      } else if (patientName.length === 2) {
        return patientName.charAt(0) + "*"; // 两个字的保留第一个字，后面用 * 代替
      } else {
        // let visibleChars = patientName.charAt(0) + "*".repeat(patientName.length - 2) + patientName.charAt(patientName.length - 1);
        let visibleChars =
          patientName.charAt(0) + "*".repeat(patientName.length - 1);
        return visibleChars; // 大于两个字的保留第一个字和最后一个字，中间用 * 代替
      }
    },
    isShowNameClick() {
        if (this.isShowName.status) {
          this.isShowName.status = !this.isShowName.status;
          this.isShowName.name = "显示姓名";
        } else {
          this.isShowName.status = !this.isShowName.status;
          this.isShowName.name = "隐藏姓名";
        }

    },
    /** 切换顶部tabs **/
    switchTabs(value) {
      console.log(value);
    },
    // 密码弹出框点击确认时
    dialogFormVisibleVerify() {
      this.$refs["verifyForm"].validate((valid) => {
        if (valid) {
          if (this.name) {
            // 显示姓名
            let obj = {
              accountPwd: this.verifyForm.password,
            };
            getVerify(obj).then((r) => {
              this.$modal.msgSuccess("密码正确");
              this.verifyForm.status = true;
              sessionStorage.setItem("isShowName", true);
              this.dialogFormVisibleVerifyAuthority = false;
              this.isShowName.status = !this.isShowName.status;
              this.isShowName.name = "隐藏姓名";
              this.name = false;
            });
          } else {
            let objj = {
              password: this.verifyForm.password,
            };
            getlogin_password(objj).then((res) => {
              if (res.code == 200) {
                this.$modal.msgSuccess("密码正确");
                this.verifyForm.status = true;
                this.dialogFormVisibleVerifyAuthority = false;
                sessionStorage.setItem("SMSverification", true);
                if (this.on_off) {
                  this.sendMsg();
                }
              } else {
                this.$modal.msgSuccess("密码错误请重试");
              }
            });
          }
        }
      });
    },
    /** 查询用户管理列表 */
    async getList(val) {
      this.loading = true;
      await listPatient_management(this.queryParams).then((response) => {
        this.patient_managementList = response.rows;
        this.total = response.total;
        this.loading = false;
        if (val){
         this.pId = this.patient_managementList[val].pId
        }
      });
      this.getPatientdetails();
    },
    // 患者用户信息
    getPatientdetails() {

      getReportByPId(this.pId).then((response) => {
        console.log(response.data);
        console.log("请求成功：", response.data)
        this.data.resultByDoctor = response.data.diagnosisConclusion;
        this.data.doctorName = response.data.dPhone;
        this.data.diagnosisData = response.data.reportTime;
        this.data.pphone = response.data.pphone;
        this.data.pId = response.data.pId;
        this.data.result = response.data.intelligentDiagnosis;
        this.aiResult = response.data.intelligentDiagnosis;
        this.data.pastMedicalHistory = response.data.pastMedicalHistory;
        // 原先提交过的预警类型
        this.logDataType = response.data.logDataType;
        if (!this.data.doctorName) {
          const date = new Date();
          const year = date.getFullYear().toString().padStart(4, "0");
          const month = (date.getMonth() + 1).toString().padStart(2, "0");
          const day = date.getDate().toString().padStart(2, "0");
          const hour = date.getHours().toString().padStart(2, "0");
          const minute = date.getMinutes().toString().padStart(2, "0");
          const second = date.getSeconds().toString().padStart(2, "0");
          this.data.diagnosisData = `${year}-${month}-${day} ${hour}:${minute}:${second}`;
        }
        if (response.data.patientSymptom != null) {
          this.data.patientSymptom = response.data.patientSymptom;
        }
      });
      listDoc().then((r) => {
        this.doctorList = r.data;
      });
      this.getyujingleixing();
      this.get();
    },
    //选择医生
    selectDoctorChange(e) {
      this.data.doctorName = e[1];
    },
    // 获取预警类型选项
    getyujingleixing() {
      selectList().then((res) => {
        this.yujingzhi = res.data;
        // console.log("这是预警值");
        // console.log(this.yujingzhi);

        if (this.logDataType) {
          this.xianshizifuchuan = this.logDataType;
          // 已逗号分隔，并去除每一项中的空格
          this.zhi = this.logDataType.split(",").map((str) => str.trim());
          // console.log("如果有logDataType就放入zhi中");
          // console.log(this.zhi);
        } else {
          this.xianshizifuchuan = "";
          this.zhi = [];
          // // console.log(this.data.result);
          // let zuanhua = ''
          // zuanhua = this.data.result.replace(/\([^()]*\)/g, ""); // 去掉括号及其内容
          // // console.log("去掉括号的内容："+zuanhua);
          // let a =zuanhua.split(/[,]/).map(value => value.trim()).filter(item => item !== "");
          // // console.log("原先没有提交过预警类型，下面是智能判断的值，去掉括号总的，变成了数组");
          // // console.log(a);
          // let matchedValues = [];

          //   a.forEach(logValue => {
          //     // 遍历yujingzhi数组中的每个对象
          //     this.yujingzhi.forEach(item => {
          //       // 在options中查找匹配项
          //       item.options.forEach(options => {
          //         if (options.value == logValue) {
          //           // 如果找到匹配项，则将其加入matchedValues数组
          //           matchedValues.push(options.value);
          //         }
          //       });
          //     });
          //   });

          // // console.log(matchedValues);
          // // this.zhi= zuanhua.split(/[,]/).map(value => value.trim()).filter(item => item !== ""); // 使用逗号或中文逗号分隔并去除空格
          // // this.zhi=matchedValues
          // // console.log("去除空格的内容");
          // // console.log(this.zhi);
          // this.xianshizifuchuan = matchedValues.map(item => item.toString()).join(",")
          // this.zhi=matchedValues
          // // console.log("智能推荐中的值，并且预警类型中的有的：");
          // // console.log(this.zhi); // 输出结果
        }
      });
    },
    // 上一个
    async prev() {
      this.loading = true;
      if (this.queryParams.pageNum == 1 && this.index == 0) {
        this.$message.warning("已经是第一页！！！");
        this.loading = false;
        return;
      }
      // this.index--;
      this.shangxiakuzhiqi--;
      if (this.shangxiakuzhiqi == -1 && this.queryParams.pageNum > 1 ){
        this.shangxiakuzhiqi = this.patient_managementList.length-1
        this.pId = this.patient_managementList[this.shangxiakuzhiqi].pId;
        this.queryParams.pageNum--
        this.queryParams.indexzhi = this.patient_managementList.length-1
        await this.getList(this.queryParams.indexzhi);
        // this.get();
        this.loading = false;
        return
      }
      this.pId = this.patient_managementList[this.shangxiakuzhiqi].pId;
      this.getPatientdetails();
      // this.get();
      this.loading = false;
    },
    // 点击下一个触发事件
    async next() {
      this.loading = true;
      this.shangxiakuzhiqi++;

      if ((this.queryParams.pageNum-1) * this.queryParams.pageSize +this.patient_managementList.length >= this.total
        &&
        this.shangxiakuzhiqi+1 ==  this.patient_managementList.length ){
        this.$message.warning("已经是最后一页！！！");
        this.loading = false;
        return;
      }

      if (this.shangxiakuzhiqi>this.patient_managementList.length-1){
        this.shangxiakuzhiqi = 0
        this.pId = this.patient_managementList[this.shangxiakuzhiqi].pId;
        this.queryParams.pageNum++
        this.queryParams.indexzhi = 0
        await this.getList(this.queryParams.indexzhi);
        // this.get();
        this.loading = false;
      }else {
        this.pId = this.patient_managementList[this.shangxiakuzhiqi].pId;
        this.getPatientdetails();
        // this.get();
        this.loading = false;
      }
    },
    // 打印选中的值
    zhong(data) {
      // console.log(data);
      this.zhi = data;
      this.xianshizifuchuan = this.zhi
        .map((item) => item.toString())
        .join(", ");
    },
    // 选择预警类型的开关
    xianshi() {
      this.tanchuang = true;
      // this.xuanzheyujingleixing = !this.xuanzheyujingleixing;
    },
    // 提交预警类型
    tijiao() {
      // console.log(this.zhi);
      let selectedValues = [];

      // 遍历trueValues数组
      this.zhi.forEach((zhii) => {
        // 遍历options数组
        this.yujingzhi.forEach((yujingzhi) => {
          // 遍历当前option对象中的zhong数组
          yujingzhi.options.forEach((options) => {
            // 如果当前zhongItem对象的label等于trueValue，则将其value添加到selectedValues数组中
            if (options.value === zhii) {
              selectedValues.push(options.label + "Ecg");
            }
          });
        });
      });
      let dataObject = {
        pId: this.data.pId,
        logId: this.data.logid ? this.data.logid : this.data.pId,
        leadCount: this.$route.query.state,
        logType: this.zhi.join(","),
      };
      for (let i = 0; i < selectedValues.length; i++) {
        // 将数组中的每个字符串作为对象的键，值为1，并放入dataObject对象中
        dataObject[selectedValues[i]] = 1;
      }
      this.tijiaoshuju = dataObject;
      // console.log("这是要提交的值：")
      // console.log(this.tijiaoshuju)
      // return
      if (dataObject.logType != "") {
        addReportyujing(this.tijiaoshuju);
        this.$modal.msgSuccess("数据提交成功");
      } else {
        this.$modal.msgError("数据提交失败，请选择预警类型");
      }
    },
    termTag() {
      let obj = {
        termText: JSON.stringify(this.dynamicTags),
      };
      addOrUpdateTerm(obj).then((r) => {
        this.$modal.msgSuccess("添加成功");
        this.dialogVisibleTag = false;
      });
    },
    handleCloseTag(tag) {
      this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1);
    },

    showInput() {
      this.inputVisible = true;
      this.$nextTick((_) => {
        this.$refs.saveTagInput.$refs.input.focus();
      });
    },

    handleInputConfirm() {
      let inputValue = this.inputValue;
      if (inputValue) {
        this.dynamicTags.push(inputValue);
      }
      this.inputVisible = false;
      this.inputValue = "";
    },

    //截断数据（一条数据现在2000）
    getNewArray(array, subGroupLength) {
      let i = 0;
      let newArray = [];
      while (i < array.length) {
        newArray.push(array.slice(i, (i += subGroupLength)));
      }
      return newArray;
    },

    //展开框
    clicktrue(title, data) {
      console.log(this.datalabel);
      this.$refs.drawShow.getchart(
        data,
        this.pId,
        1,
        title,
        12,
        this.datalabel
      );
    },

    clickitem(e) {
      e === this.radio ? (this.radio = "") : (this.radio = e);
    },
    //请求数据
    get() {
      const loading = this.$loading({
        lock: true, //lock的修改符--默认是false
        text: "请勿刷新页面，正在获取数据，请耐心等待1-3分钟...", //显示在加载图标下方的加载文案
        spinner: "el-icon-loading", //自定义加载图标类名
        background: "rgba(0, 0, 0, 0.7)", //遮罩层颜色
        target: document.querySelector("#table"), //loadin覆盖的dom元素节点
      });
      var _th = this;

      // console.log("请求数据了！")
      // console.log("pId", this.pId)
      this.data.dataTime = this.$options.methods.getData();
      $.ajax({
        type: "post",
        url: "https://screen.mindyard.cn:84/get_jecg_12_web",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({
          pid:  _th.pId,
        }),
        beforeSend: function (request) {
          // 如果后台没有跨域处理，这个自定义
          request.setRequestHeader("user", "zzu");
          request.setRequestHeader("password", "zzu123");
        },
        success: function (data) {
          // console.log("请求成功：", data)
          loading.close();
          _th.data.resultByDoctor = data.result.diagnosis_conclusion;
          // _th.data.doctorName = data.result.diagnosis_doctor;
          _th.data.age = data.result.age;
          _th.data.gender = data.result.gender;
          _th.data.name = data.result.patientName;
          _th.data.result = data.result.intelligent_diagnosis;
          _th.data.hr = data.result.ecg_analysis_data["平均心率"];
          _th.data.p = data.result.ecg_analysis_data["P波时限"];
          _th.data.pr = data.result.ecg_analysis_data["PR间期"];
          _th.data.qrs = data.result.ecg_analysis_data["QRS波时限"];
          _th.data.qt = data.result.ecg_analysis_data["QT间期"];
          _th.data.qtc = data.result.ecg_analysis_data["QTc"];
          _th.data.hrv = data.result.ecg_analysis_data["RMSSD"];
          _th.data.hr = data.result.ecg_analysis_data["平均心率"];
          _th.data.qrs_deg = data.result.ecg_analysis_data["QRS_deg"];
          _th.data.t = data.result.ecg_analysis_data["T波时限"];
          _th.data.pv5 = data.result.ecg_analysis_data["PV5_mv"];
          _th.data.sv1 = data.result.ecg_analysis_data["SV1_mv"];
          _th.data.rv5_sv1 = data.result.ecg_analysis_data["RV5_SV1"];
          _th.data.p_xingeng = data.result.p_xingeng;
          _th.data12.dataI = data.result.I;
          _th.dataLabel = data.result;
          _th.nArrI = _th.getNewArray(_th.data12.dataI, 1000);
          _th.data12.dataII = data.result.II;
          _th.nArrII = _th.getNewArray(_th.data12.dataII, 1000);
          _th.data12.dataIII = data.result.III;
          _th.nArrIII = _th.getNewArray(_th.data12.dataIII, 1000);
          _th.data12.dataaVR = data.result.aVR;
          _th.nArraVR = _th.getNewArray(_th.data12.dataaVR, 1000);
          _th.data12.dataaVL = data.result.aVL;
          _th.nArraVL = _th.getNewArray(_th.data12.dataaVL, 1000);
          _th.data12.dataaVF = data.result.aVF;
          _th.nArraVF = _th.getNewArray(_th.data12.dataaVF, 1000);
          _th.data12.dataV1 = data.result.V1;
          _th.nArrV1 = _th.getNewArray(_th.data12.dataV1, 1000);
          _th.data12.dataV2 = data.result.V2;
          _th.nArrV2 = _th.getNewArray(_th.data12.dataV2, 1000);
          _th.data12.dataV3 = data.result.V3;
          _th.nArrV3 = _th.getNewArray(_th.data12.dataV3, 1000);
          _th.data12.dataV4 = data.result.V4;
          _th.nArrV4 = _th.getNewArray(_th.data12.dataV4, 1000);
          _th.data12.dataV5 = data.result.V5;
          _th.nArrV5 = _th.getNewArray(_th.data12.dataV5, 1000);
          _th.data12.dataV6 = data.result.V6;
          _th.nArrV6 = _th.getNewArray(_th.data12.dataV6, 1000);
          _th.datalabel.waveLabel = data.result.waveLabel;
          _th.datalabel.beatLabel = data.result.beatLabel;
          _th.data12.x = [];
          for (var i = 0; i < _th.data12.dataII.length + 1; i++) {
            _th.data12.x.push(i);
          }
          if (_th.data12.dataII.length == 1000) {
            $(".line").css({
              height: "15.5vh",
            });
          } else if (_th.data12.dataII.length == 2000) {
            $(".line").css({
              height: "7.6vh",
            });
          } else if (_th.data12.dataII.length == 3000) {
            $(".line").css({
              height: "7.6vh",
            });
          }
          for (var i = 0; i < 2000; i += 20) {
            _th.markdata.push({ xAxis: i });
          }
          var chartI = echarts.init(document.getElementById("I"));
          chartI.clear();
          chartI.setOption({
            title: {
              text: "I",
              top: 5,
              left: 5,
            },
            grid: {
              left: "1",
              right: "2",
              top: "1",
              bottom: "1",
              containLabel: false,
            },
            xAxis: {
              type: "category",
              data: _th.data12.x,
              axisLabel: {
                show: false,
                interval: 3,
              },
              axisTick: {
                show: false,
              },
              axisLine: {
                show: false,
              },
              splitLine: {
                show: true, //让网格显示
                lineStyle: {
                  //网格样式
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
                },
              },
            },
            yAxis: {
              type: "value",
              axisLabel: {
                show: false,
              },
              axisTick: {
                show: false,
              },
              axisLine: {
                show: false,
              },
              splitNumber: 51, // 横线数
              minInterval: 0.1, // 刻度间隔
              splitLine: {
                show: true, //让网格显示
                lineStyle: {
                  //网格样式
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
                },
              },
              max: 1,
              min: -1,
            },
            series: [
              {
                type: "line",
                smooth: true,
                showSymbol: false,
                data: _th.data12.dataI,
                zlevel: 99,
                lineStyle: {
                  normal: {
                    color: "#000000",
                    width: 1.5,
                  },
                },
                markLine: {
                  symbol: "none",
                  silent: true,
                  lineStyle: {
                    type: "solid",
                    color: "#df8989",
                    width: 1,
                  },
                  label: {
                    position: "start", // 表现内容展示的位置
                    color: "#df8989", // 展示内容颜色
                  },

                  data: _th.markdata,
                },
              },
            ],
          });
          chartI.resize();
          $(window).resize(function () {
            chartI.resize();
          });
          _th.chartII = echarts.init(document.getElementById("II"));
          _th.chartII.clear();
          _th.chartII.setOption({
            animation: false,
            title: {
              text: "II",
              top: 5,
              left: 5,
            },
            grid: {
              left: "1",
              right: "2",
              top: "1",
              bottom: "1",
              containLabel: false,
            },
            xAxis: {
              type: "category",
              data: _th.data12.x,
              axisLabel: {
                show: false,
                interval: 3,
              },
              axisTick: {
                show: false,
              },
              axisLine: {
                show: false,
              },
              splitLine: {
                show: true, //让网格显示
                lineStyle: {
                  //网格样式
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
                },
              },
            },
            yAxis: {
              type: "value",
              axisLabel: {
                show: false,
              },
              axisTick: {
                show: false,
              },
              axisLine: {
                show: false,
              },
              //  splitNumber: 3, // 横线数
              interval: 0.1, // 刻度间隔
              splitLine: {
                show: true, //让网格显示
                lineStyle: {
                  //网格样式
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
                },
              },
              max: 1,
              min: -1,
            },
            series: [
              {
                type: "line",
                smooth: true,
                showSymbol: false,
                data: _th.data12.dataII,
                zlevel: 99,
                lineStyle: {
                  normal: {
                    color: "#000000",
                    width: 1.5,
                  },
                },
                markLine: {
                  symbol: "none",
                  silent: true,
                  lineStyle: {
                    type: "solid",
                    color: "#df8989",
                    width: 1,
                  },
                  label: {
                    position: "start", // 表现内容展示的位置
                    color: "#df8989", // 展示内容颜色
                  },

                  data: _th.markdata,
                },
              },
            ],
          });
          //绘制文本
          // _th.addtext()
          $(window).resize(function () {
            _th.chartII.resize();
            // _th.addtext()
          });
          var chartIII = echarts.init(document.getElementById("III"));
          chartIII.clear();
          chartIII.setOption({
            animation: false,
            title: {
              text: "III",
              top: 5,
              left: 5,
            },
            grid: {
              left: "1",
              right: "2",
              top: "1",
              bottom: "1",
              containLabel: false,
            },
            xAxis: {
              type: "category",
              data: _th.data12.x,
              axisLabel: {
                show: false,
                interval: 3,
              },
              axisTick: {
                show: false,
              },
              axisLine: {
                show: false,
              },
              splitLine: {
                show: true, //让网格显示
                lineStyle: {
                  //网格样式
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
                },
              },
            },
            yAxis: {
              type: "value",
              axisLabel: {
                show: false,
              },
              axisTick: {
                show: false,
              },
              axisLine: {
                show: false,
              },
              //  splitNumber: 3, // 横线数
              interval: 0.1, // 刻度间隔
              splitLine: {
                show: true, //让网格显示
                lineStyle: {
                  //网格样式
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
                },
              },
              max: 1,
              min: -1,
            },

            series: [
              {
                type: "line",
                smooth: true,
                showSymbol: false,
                data: _th.data12.dataIII,
                zlevel: 99,
                lineStyle: {
                  normal: {
                    color: "#000000",
                    width: 1.5,
                  },
                },
                markLine: {
                  symbol: "none",
                  silent: true,
                  lineStyle: {
                    type: "solid",
                    color: "#df8989",
                    width: 1,
                  },
                  label: {
                    position: "start", // 表现内容展示的位置
                    color: "#df8989", // 展示内容颜色
                  },

                  data: _th.markdata,
                },
              },
            ],
          });
          $(window).resize(function () {
            chartIII.resize();
          });
          var chartaVR = echarts.init(document.getElementById("aVR"));
          chartaVR.clear();
          chartaVR.setOption({
            animation: false,
            title: {
              text: "aVR",
              top: 5,
              left: 5,
            },
            grid: {
              left: "1",
              right: "2",
              top: "1",
              bottom: "1",
              containLabel: false,
            },
            xAxis: {
              type: "category",
              data: _th.data12.x,
              axisLabel: {
                show: false,
                interval: 3,
              },
              axisTick: {
                show: false,
              },
              axisLine: {
                show: false,
              },
              splitLine: {
                show: true, //让网格显示
                lineStyle: {
                  //网格样式
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
                },
              },
            },
            yAxis: {
              type: "value",
              axisLabel: {
                show: false,
              },
              axisTick: {
                show: false,
              },
              axisLine: {
                show: false,
              },
              //  splitNumber: 3, // 横线数
              interval: 0.1, // 刻度间隔
              splitLine: {
                show: true, //让网格显示
                lineStyle: {
                  //网格样式
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
                },
              },
              max: 1,
              min: -1,
            },

            series: [
              {
                type: "line",
                smooth: true,
                showSymbol: false,
                data: _th.data12.dataaVR,
                zlevel: 99,
                lineStyle: {
                  normal: {
                    color: "#000000",
                    width: 1.5,
                  },
                },
                markLine: {
                  symbol: "none",
                  silent: true,
                  lineStyle: {
                    type: "solid",
                    color: "#df8989",
                    width: 1,
                  },
                  label: {
                    position: "start", // 表现内容展示的位置
                    color: "#df8989", // 展示内容颜色
                  },

                  data: _th.markdata,
                },
              },
            ],
          });
          $(window).resize(function () {
            chartaVR.resize();
          });
          var chartaVL = echarts.init(document.getElementById("aVL"));
          chartaVL.clear();
          chartaVL.setOption({
            animation: false,
            title: {
              text: "aVL",
              top: 5,
              left: 5,
            },
            grid: {
              left: "1",
              right: "2",
              top: "1",
              bottom: "1",
              containLabel: false,
            },
            xAxis: {
              type: "category",
              data: _th.data12.x,
              axisLabel: {
                show: false,
                interval: 3,
              },
              axisTick: {
                show: false,
              },
              axisLine: {
                show: false,
              },
              splitLine: {
                show: true, //让网格显示
                lineStyle: {
                  //网格样式
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
                },
              },
            },
            yAxis: {
              type: "value",
              axisLabel: {
                show: false,
              },
              axisTick: {
                show: false,
              },
              axisLine: {
                show: false,
              },
              //  splitNumber: 3, // 横线数
              interval: 0.1, // 刻度间隔
              splitLine: {
                show: true, //让网格显示
                lineStyle: {
                  //网格样式
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
                },
              },
              max: 1,
              min: -1,
            },

            series: [
              {
                type: "line",
                smooth: true,
                showSymbol: false,
                data: _th.data12.dataaVL,
                zlevel: 99,
                lineStyle: {
                  normal: {
                    color: "#000000",
                    width: 1.5,
                  },
                },
                markLine: {
                  symbol: "none",
                  silent: true,
                  lineStyle: {
                    type: "solid",
                    color: "#df8989",
                    width: 1,
                  },
                  label: {
                    position: "start", // 表现内容展示的位置
                    color: "#df8989", // 展示内容颜色
                  },

                  data: _th.markdata,
                },
              },
            ],
          });
          $(window).resize(function () {
            chartaVL.resize();
          });
          var chartaVF = echarts.init(document.getElementById("aVF"));
          chartaVF.clear();
          chartaVF.setOption({
            animation: false,
            title: {
              text: "aVF",
              top: 5,
              left: 5,
            },
            grid: {
              left: "1",
              right: "2",
              top: "1",
              bottom: "1",
              containLabel: false,
            },
            xAxis: {
              type: "category",
              data: _th.data12.x,
              axisLabel: {
                show: false,
                interval: 3,
              },
              axisTick: {
                show: false,
              },
              axisLine: {
                show: false,
              },
              splitLine: {
                show: true, //让网格显示
                lineStyle: {
                  //网格样式
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
                },
              },
            },
            yAxis: {
              type: "value",
              axisLabel: {
                show: false,
              },
              axisTick: {
                show: false,
              },
              axisLine: {
                show: false,
              },
              //  splitNumber: 3, // 横线数
              interval: 0.1, // 刻度间隔
              splitLine: {
                show: true, //让网格显示
                lineStyle: {
                  //网格样式
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
                },
              },
              max: 1,
              min: -1,
            },

            series: [
              {
                type: "line",
                smooth: true,
                showSymbol: false,
                data: _th.data12.dataaVF,
                zlevel: 99,
                lineStyle: {
                  normal: {
                    color: "#000000",
                    width: 1.5,
                  },
                },
                markLine: {
                  symbol: "none",
                  silent: true,
                  lineStyle: {
                    type: "solid",
                    color: "#df8989",
                    width: 1,
                  },
                  label: {
                    position: "start", // 表现内容展示的位置
                    color: "#df8989", // 展示内容颜色
                  },

                  data: _th.markdata,
                },
              },
            ],
          });
          $(window).resize(function () {
            chartaVF.resize();
          });
          var chartV1 = echarts.init(document.getElementById("V1"));
          chartV1.clear();
          chartV1.setOption({
            animation: false,
            title: {
              text: "V1",
              top: 5,
              left: 5,
            },
            grid: {
              left: "1",
              right: "2",
              top: "1",
              bottom: "1",
              containLabel: false,
            },
            xAxis: {
              type: "category",
              data: _th.data12.x,
              axisLabel: {
                show: false,
                interval: 3,
              },
              axisTick: {
                show: false,
              },
              axisLine: {
                show: false,
              },
              splitLine: {
                show: true, //让网格显示
                lineStyle: {
                  //网格样式
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
                },
              },
            },
            yAxis: {
              type: "value",
              axisLabel: {
                show: false,
              },
              axisTick: {
                show: false,
              },
              axisLine: {
                show: false,
              },
              //  splitNumber: 3, // 横线数
              interval: 0.1, // 刻度间隔
              splitLine: {
                show: true, //让网格显示
                lineStyle: {
                  //网格样式
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
                },
              },
              max: 1,
              min: -1,
            },

            series: [
              {
                type: "line",
                smooth: true,
                showSymbol: false,
                data: _th.data12.dataV1,
                zlevel: 99,
                lineStyle: {
                  normal: {
                    color: "#000000",
                    width: 1.5,
                  },
                },
                markLine: {
                  symbol: "none",
                  silent: true,
                  lineStyle: {
                    type: "solid",
                    color: "#df8989",
                    width: 1,
                  },
                  label: {
                    position: "start", // 表现内容展示的位置
                    color: "#df8989", // 展示内容颜色
                  },

                  data: _th.markdata,
                },
              },
            ],
          });
          $(window).resize(function () {
            chartV1.resize();
          });
          var chartV2 = echarts.init(document.getElementById("V2"));
          chartV2.clear();
          chartV2.setOption({
            animation: false,
            title: {
              text: "V2",
              top: 5,
              left: 5,
            },
            grid: {
              left: "1",
              right: "2",
              top: "1",
              bottom: "1",
              containLabel: false,
            },
            xAxis: {
              type: "category",
              data: _th.data12.x,
              axisLabel: {
                show: false,
                interval: 3,
              },
              axisTick: {
                show: false,
              },
              axisLine: {
                show: false,
              },
              splitLine: {
                show: true, //让网格显示
                lineStyle: {
                  //网格样式
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
                },
              },
            },
            yAxis: {
              type: "value",
              axisLabel: {
                show: false,
              },
              axisTick: {
                show: false,
              },
              axisLine: {
                show: false,
              },
              //  splitNumber: 3, // 横线数
              interval: 0.1, // 刻度间隔
              splitLine: {
                show: true, //让网格显示
                lineStyle: {
                  //网格样式
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
                },
              },
              max: 1,
              min: -1,
            },

            series: [
              {
                type: "line",
                smooth: true,
                showSymbol: false,
                data: _th.data12.dataV2,
                zlevel: 99,
                lineStyle: {
                  normal: {
                    color: "#000000",
                    width: 1.5,
                  },
                },
                markLine: {
                  symbol: "none",
                  silent: true,
                  lineStyle: {
                    type: "solid",
                    color: "#df8989",
                    width: 1,
                  },
                  label: {
                    position: "start", // 表现内容展示的位置
                    color: "#df8989", // 展示内容颜色
                  },

                  data: _th.markdata,
                },
              },
            ],
          });
          $(window).resize(function () {
            chartV2.resize();
          });
          var chartV3 = echarts.init(document.getElementById("V3"));
          chartV3.clear();
          chartV3.setOption({
            animation: false,
            title: {
              text: "V3",
              top: 5,
              left: 5,
            },
            grid: {
              left: "1",
              right: "2",
              top: "1",
              bottom: "1",
              containLabel: false,
            },
            xAxis: {
              type: "category",
              data: _th.data12.x,
              axisLabel: {
                show: false,
                interval: 3,
              },
              axisTick: {
                show: false,
              },
              axisLine: {
                show: false,
              },
              splitLine: {
                show: true, //让网格显示
                lineStyle: {
                  //网格样式
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
                },
              },
            },
            yAxis: {
              type: "value",
              axisLabel: {
                show: false,
              },
              axisTick: {
                show: false,
              },
              axisLine: {
                show: false,
              },
              //  splitNumber: 3, // 横线数
              interval: 0.1, // 刻度间隔
              splitLine: {
                show: true, //让网格显示
                lineStyle: {
                  //网格样式
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
                },
              },
              max: 1,
              min: -1,
            },

            series: [
              {
                type: "line",
                smooth: true,
                showSymbol: false,
                data: _th.data12.dataV3,
                zlevel: 99,
                lineStyle: {
                  normal: {
                    color: "#000000",
                    width: 1.5,
                  },
                },
                markLine: {
                  symbol: "none",
                  silent: true,
                  lineStyle: {
                    type: "solid",
                    color: "#df8989",
                    width: 1,
                  },
                  label: {
                    position: "start", // 表现内容展示的位置
                    color: "#df8989", // 展示内容颜色
                  },

                  data: _th.markdata,
                },
              },
            ],
          });
          $(window).resize(function () {
            chartV3.resize();
          });
          var chartV4 = echarts.init(document.getElementById("V4"));
          chartV4.clear();
          chartV4.setOption({
            animation: false,
            title: {
              text: "V4",
              top: 5,
              left: 5,
            },
            grid: {
              left: "1",
              right: "2",
              top: "1",
              bottom: "1",
              containLabel: false,
            },
            xAxis: {
              type: "category",
              data: _th.data12.x,
              axisLabel: {
                show: false,
                interval: 3,
              },
              axisTick: {
                show: false,
              },
              axisLine: {
                show: false,
              },
              splitLine: {
                show: true, //让网格显示
                lineStyle: {
                  //网格样式
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
                },
              },
            },
            yAxis: {
              type: "value",
              axisLabel: {
                show: false,
              },
              axisTick: {
                show: false,
              },
              axisLine: {
                show: false,
              },
              //  splitNumber: 3, // 横线数
              interval: 0.1, // 刻度间隔
              splitLine: {
                show: true, //让网格显示
                lineStyle: {
                  //网格样式
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
                },
              },
              max: 1,
              min: -1,
            },

            series: [
              {
                type: "line",
                smooth: true,
                showSymbol: false,
                data: _th.data12.dataV4,
                zlevel: 99,
                lineStyle: {
                  normal: {
                    color: "#000000",
                    width: 1.5,
                  },
                },
                markLine: {
                  symbol: "none",
                  silent: true,
                  lineStyle: {
                    type: "solid",
                    color: "#df8989",
                    width: 1,
                  },
                  label: {
                    position: "start", // 表现内容展示的位置
                    color: "#df8989", // 展示内容颜色
                  },

                  data: _th.markdata,
                },
              },
            ],
          });
          $(window).resize(function () {
            chartV4.resize();
          });
          var chartV5 = echarts.init(document.getElementById("V5"));
          chartV5.clear();
          chartV5.setOption({
            animation: false,
            title: {
              text: "V5",
              top: 5,
              left: 5,
            },
            grid: {
              left: "1",
              right: "2",
              top: "1",
              bottom: "1",
              containLabel: false,
            },
            xAxis: {
              type: "category",
              data: _th.data12.x,
              axisLabel: {
                show: false,
                interval: 3,
              },
              axisTick: {
                show: false,
              },
              axisLine: {
                show: false,
              },
              splitLine: {
                show: true, //让网格显示
                lineStyle: {
                  //网格样式
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
                },
              },
            },
            yAxis: {
              type: "value",
              axisLabel: {
                show: false,
              },
              axisTick: {
                show: false,
              },
              axisLine: {
                show: false,
              },
              //  splitNumber: 3, // 横线数
              interval: 0.1, // 刻度间隔
              splitLine: {
                show: true, //让网格显示
                lineStyle: {
                  //网格样式
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
                },
              },
              max: 1,
              min: -1,
            },

            series: [
              {
                type: "line",
                smooth: true,
                showSymbol: false,
                data: _th.data12.dataV5,
                zlevel: 99,
                lineStyle: {
                  normal: {
                    color: "#000000",
                    width: 1.5,
                  },
                },
                markLine: {
                  symbol: "none",
                  silent: true,
                  lineStyle: {
                    type: "solid",
                    color: "#df8989",
                    width: 1,
                  },
                  label: {
                    position: "start", // 表现内容展示的位置
                    color: "#df8989", // 展示内容颜色
                  },

                  data: _th.markdata,
                },
              },
            ],
          });
          $(window).resize(function () {
            chartV5.resize();
          });
          var chartV6 = echarts.init(document.getElementById("V6"));
          chartV6.clear();
          chartV6.setOption({
            animation: false,
            title: {
              text: "V6",
              top: 5,
              left: 5,
            },
            grid: {
              left: "1",
              right: "2",
              top: "1",
              bottom: "1",
              containLabel: false,
            },
            xAxis: {
              type: "category",
              data: _th.data12.x,
              axisLabel: {
                show: false,
                interval: 3,
              },
              axisTick: {
                show: false,
              },
              axisLine: {
                show: false,
              },
              splitLine: {
                show: true, //让网格显示
                lineStyle: {
                  //网格样式
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
                },
              },
            },
            yAxis: {
              type: "value",
              axisLabel: {
                show: false,
              },
              axisTick: {
                show: false,
              },
              axisLine: {
                show: false,
              },
              //  splitNumber: 3, // 横线数
              interval: 0.1, // 刻度间隔
              splitLine: {
                show: true, //让网格显示
                lineStyle: {
                  //网格样式
                  color: "#f8bfbf", //网格的颜色
                  width: 0.5, //网格的宽度
                  type: "solid", //网格是实实线，可以修改成虚线以及其他的类型
                },
              },
              max: 1,
              min: -1,
            },

            series: [
              {
                type: "line",
                smooth: true,
                showSymbol: false,
                data: _th.data12.dataV6,
                zlevel: 99,
                lineStyle: {
                  normal: {
                    color: "#000000",
                    width: 1.5,
                  },
                },
                markLine: {
                  symbol: "none",
                  silent: true,
                  lineStyle: {
                    type: "solid",
                    color: "#df8989",
                    width: 1,
                  },
                  label: {
                    position: "start", // 表现内容展示的位置
                    color: "#df8989", // 展示内容颜色
                  },

                  data: _th.markdata,
                },
              },
            ],
          });
          $(window).resize(function () {
            chartV6.resize();
          });
          var erd = elementResizeDetectorMaker();
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
            });
          });
        },
        error: function (data) {
          alert("数据请求错误,请刷新页面或联系管理员");
          loading.close();
          console.log("请求失败：", data);
        },
      });
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
    closeMain(val) {
      console.log(val);
      // this.datalabel.beatLabel=val
      // this.addtext()
    },
    //获取当前时间
    getData() {
      var str = new Date();
      var nowTime =
        str.getFullYear() +
        "-" +
        (str.getMonth() + 1) +
        "-" +
        str.getDate() +
        " " +
        str.getHours() +
        ":" +
        str.getMinutes() +
        ":" +
        str.getSeconds();
      return nowTime;
    },
    sendMsgWarning() {
      this.textarea = ""
      this.Early_warning_message = true
    },
    sendWarnMsg() {
      if (
        this.textarea == "" ||
        this.textarea == null ||
        this.textarea.length > 20
      ) {
        this.$message({
          type: "error",
          message: "预警消息不能为空或长度最多20个字",
        });
        return;
      }
      let obj = {
        pId: this.data.pId,
        warningText: this.textarea,
      };
      reportEarlyWarningMsg(obj).then((r) => {
        this.$message({
          type: "success",
          message: "发送成功!",
        });
        this.Early_warning_message = false
      });
    },
    //发送短信
    sendMsg() {
      // console.log("用户电话: " + this.data.pphone);
      let patientPhone = this.data.pphone;
      if (patientPhone.length === 14 || patientPhone.length === 15) {
        patientPhone = patientPhone.substring(0, 11);
      }
      // console.log(patientPhone);
      let SMSverification = sessionStorage.getItem("SMSverification");
      this.on_off = true;
      if (SMSverification) {
        if (patientPhone) {
          // console.log("用户姓名: " + row.patientName)
          this.$confirm("向该用户发送短信提示采集存在较大干扰?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          })
            .then(() => {
              sendMsgToPatient(patientPhone).then((response) => {
                this.$message({
                  type: "success",
                  message: "发送成功!",
                });
              });
            })
            .catch(() => {
              this.$message({
                type: "info",
                message: "已取消",
              });
            });
        } else {
          this.$message.error("该用户手机号不合法！！！");
        }
      } else {
        this.verifyForm.password = "";
        this.dialogFormVisibleVerifyAuthority = true;
      }
    },
    //保存数据
    btnUpload() {
      if (this.data.resultByDoctor == "" || this.data.resultByDoctor == null) {
        this.$message({
          type: "error",
          message: "诊断结果不能为空!",
        });
        return;
      }
      if (this.data.doctorName == "" || this.data.doctorName == null) {
        this.$message({
          type: "error",
          message: "诊断医生不能为空!",
        });
        return;
      }
      const date = new Date();
      const year = date.getFullYear().toString().padStart(4, "0");
      const month = (date.getMonth() + 1).toString().padStart(2, "0");
      const day = date.getDate().toString().padStart(2, "0");
      const hour = date.getHours().toString().padStart(2, "0");
      const minute = date.getMinutes().toString().padStart(2, "0");
      const second = date.getSeconds().toString().padStart(2, "0");

      var form = {
        pId: this.pId,
        diagnosisStatus: "1",
        startDateTime: `${year}-${month}-${day} ${hour}:${minute}:${second}`,
        diagnosisConclusion: this.data.resultByDoctor,
        reportTime: this.data.dataTime,
        dPhone: this.data.doctorName,
      };
      getReportByPId(this.pId).then((res) => {
        if (res.data == null) {
          addReport(form).then((response) => {
            this.$modal.msgSuccess("新增成功");
            // this.getList();
            console.log("新增成功！");
          });
        } else {
          form["reportId"] = res.data.reportId;
          console.log("保存的数据：", form);
          updateReport(form).then((response) => {
            this.$modal.msgSuccess("修改成功");
            // this.getList();
            console.log("修改成功！");
          });
        }
      });
    },


    findLabel(){
      this.Report_printing = true
    },

    open() {
      this.$nextTick(() => {
        this.initChart();
      })
    },

    initChart(){
      let data = this.dataLabel[this.report_printing_type]
       this.myChart = echarts.init(this.$refs.chart)
      let x = []
      for (var i = 0; i <= this.dataLabel[this.report_printing_type].length; i++) {
        x.push(i);
      }
      for (let i = 0; i < 1000; i += 20) {
        this.seriesdata.push({ xAxis: i });
      }
      //标线
      let seriesdata = this.seriesdata;
      this.myChart.setOption(
        {
        title: {
          text: "",
          top: 10,
          left: 50,
        },

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
            startValue: this.dataZoomData.startValue,
            endValue: this.dataZoomData.endValue,
            maxValueSpan: this.dataZoomData.maxValueSpan,
            minValueSpan:this.dataZoomData.minValueSpan
          }
        ],
        grid: {
          left: "3%",
          right: "3%",
          top: "2%",
          bottom: "13%",
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
          data: x,
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
          boundaryGap: false,
          splitNumber: 51,
          minInterval: 0.1,
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
            data: seriesdata,
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
          zlevel: 9999,
          smooth: true, //显示为平滑的曲线*/
        },

      }
      )
      let _th = this
      window.addEventListener('resize', function() {
        _th.myChart.resize();
      });
      this.myChart.on('dataZoom', function (params) {
        let data =  _th.getSmallValue(params.end)
        _th.dataZoomData.value = data
        _th.dataZoomData.startValue = params.start*10
        _th.dataZoomData.endValue = params.end*10
      });

    },
    report_printing_init(){
      console.log(this.report_printing_type)
      this.initChart()
    },

    getSmallValue(largeValue) {
      let data = Math.floor(largeValue*10)
      console.log(data)
      // 定义映射关系
      const minLarge = 500;
      const maxLarge = 1000;
      const minSmall = 250;

      // 检查大值是否在范围内
      if (data < minLarge || data > maxLarge) {
        return null; // 超出范围
      }

      // 线性插值公式
      let ratio = data-minLarge;

      let smallValue = (ratio*1.5)+minSmall
      return Math.floor(smallValue);
    },

    findPdfReport(){
      console.log(this.data.pId)
      this.dpfFindReport = true
      this.loading1 = true
      let pId= this.$route.query.pId ||this.dataId
      this.TableHeight=document.documentElement.clientHeight || document.bodyclientHeight;
      this.getPdf(pId)
    },
    getPdf(pId){
      this.getJEcgPdf()
    },
    getJEcgPdf(val){
      let _this=this;
      let obj = {
        pId:this.$route.query.pId,
        endValue:this.dataZoomData.value
      }
      getPdf(obj).then(res=>{
        this.src = res.msg + '?t=' + new Date().getTime()
        this.loading1= false;
      }).catch(err=>{
        this.loading1= false;
      })
    }
  },
};
</script>

<style lang="scss" scoped>
// * {
//   margin: 0;
//   padding: 0;
//   box-sizing: border-box;
// }

.page {
  width: 100%;
  //height: 100vh;
  //border: 3px solid #0000ff;
}

::v-deep .el-tabs__nav {
  z-index: 0;
}
::v-deep .el-tabs__content {
  overflow: visible;
}
.box {
  overflow: hidden;
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  // justify-content: space-around;
  margin-top: 1.5vh;
  margin-bottom: 1.5vh;
  border-radius: 2vh;
  background-color: #e8e8e8;
  padding: 15px;
  padding-bottom: 0px;

  //opacity: 0.6;
  .patientMessage {
    display: flex;
    flex-direction: column;
    flex-wrap: wrap;
    //background-color: #e01806;
    //height: 20vh;
    margin: 1.5vh 0 1.5vh 0;
    width: 35%;

    .info {
      // width: 90%;
      // display: flex;
      // flex-direction: row;
      // flex-wrap: wrap;
      // margin-left: 1.8vw;
      // margin-top: 3vh;

      flex: 1;
      display: flex;
      justify-content: space-between;
      flex-wrap: wrap;
      //background-color: #e01806;
      //height: 20vh;
      padding: 1.5vh 0 1.5vh 0;
      margin-left: 2vw;
      width: 90%;

      .textbox {
        width: 45%;
        margin-bottom: 1.5vh;
        font-size: 2.1vh;
      }

      .textBoxBottom {
        margin-bottom: 10px;
        font-size: 2.1vh;
      }
    }
  }

  .h11 {
    width: 100%;
    font-size: 2.5vh;
    background-color: #dcdcdc;
    font-weight: 700;
    height: 4vh;
    display: flex;

    span {
      width: 6px;
      height: 100%;
      background-color: #00afff;
    }

    p {
      margin-left: 1vw;
      line-height: 4vh;
    }

    .between {
      width: 100%;
      display: flex;
      justify-content: space-between;

      p {
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
  .result1 {
    width: 32%;

    //height: 12vh;
    .text {
      height: 16vh;
      //border: 1px darkgray solid;
      overflow: hidden;
      overflow-y: auto;
      -webkit-overflow-scrolling: touch;
      /* 提高移动设备上的滚动性能 */
      -ms-overflow-style: none;
      scrollbar-width: none;
    }
  }

  .selected {
    background-color: #435bf7;
    color: #fff !important;
  }

  .result2 {
    width: 32.5%;

    //height: 12vh;
    .text {
      height: 15vh;
    }
  }
}

.size {
  font-size: 2.3vh;
}

.mmargin {
  margin: 1.5vh 0 1.5vh 0;
}

.margin {
  width: 100%;
}

.mt {
  //margin-top: 2vh;
  height: 40%;
}

.doctor {
  // margin: 2vh 0 2vh 0;
  height: 29%;
  display: flex;
  width: 100%;
  flex-direction: column;
  justify-content: centern;

  .input {
    display: flex;
    flex-direction: row;
    margin-top: 1vh;

    // margin-left: 2vw;
    ::v-deep .el-input--medium .el-input__inner {
      //width: 80%;
    }

    //margin-left: 1vw;
    strong {
      white-space: nowrap;
      line-height: 36px;
      margin-right: 0.5vw;
      font-size: 1vw;
    }
  }
}

.header-left {
  width: 60%;
  height: 100%;
  display: flex;
  flex-wrap: nowrap;
}

.font {
  font-size: 1vw;
  font-weight: 700;
  color: #6f0600;
  background-color: #fff;
  height: 100%;
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
  user-select: none;
  position: absolute;
  width: 100%;
  height: 82vh;
  border: 1px solid #6eddf1;
  top: 54vh;
  left: 50%;
  transform: translate(-50%, -50%);
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
}

.button {
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

.button:hover {
  color: #ffffff;
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

.el-radio-group {
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

.ml {
  margin-left: 1vw;
  margin-right: 2vw;
}

//.bottom-left{
//  display: flex;
//  //flex-direction: row;
//}

// .anNiu {
//   height: 30px;
//   // width: 5vw;
//   // font-size: 1vw;
//   line-height: 1vw;
//   text-align: center;
//       padding: 0 3px;

// }

.line {
  height: 5vw;
  width: 100%;
  margin: 0;
  padding: 0;
}

.doctordata {
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

.tag-button-panging {
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

.xuanzheyujing {
  width: 100%;
  margin: 0 auto;
  margin-top: 1.5vh;
  margin-bottom: 1.5vh;
  border-radius: 2vh;
  background-color: #ffffff;
  align-items: center;
  padding: 10px;
  height: 62.5vh;
  overflow: hidden;
  overflow-y: auto;
  -webkit-overflow-scrolling: touch;
  /* 提高移动设备上的滚动性能 */
  -ms-overflow-style: none;
  scrollbar-width: none;
}

.xian {
  border-bottom: 1px solid #000;
}

.xiaoli {
  list-style: none;
  // motion: 1px;
  padding: 0 0 3.5px 3.5px;
  width: 25%;
  display: block;
  float: left;
}

.xiaoul {
  margin: 0.5vh 0 0 0;
  padding: 0;
  width: 100%;
}

.fenzuzhutizi {
  display: flex;
  // flex-wrap:noweap;
  flex-wrap: wrap;
}

.fenzuzhuti {
  font-size: 12px;
  color: #909399;
  font-weight: 700;
  // font-style: 20px;
  // font-size: 20px;
  margin-left: 10px;
}

.duoxuan {
  // border: 1px solid #136d87;
  width: 100%;
  // height: 100px;
  // text-align: center;
}

.biaodan {
  width: 100%;
  // height: 85%;
  // border: 1px solid red;
  overflow-y: auto;
  //  overflow: hidden;
  -webkit-overflow-scrolling: touch;
  /* 提高移动设备上的滚动性能 */
  -ms-overflow-style: none;
  scrollbar-width: none;
}

/* 隐藏滚动条但仍可滚动 */
.biaodan::-webkit-scrollbar {
  display: none;
}

.wancheng {
  display: flex;
  justify-content: space-between;
}

.updown {
  width: 100%;
  display: flex;
  justify-content: space-around;
}

// 表格
.tablex {
  border: 1px solid #ccc;
  border-collapse: collapse;
  width: 100%;
  height: 60%;
  text-align: center;
}

.tablex th,
.tablex td {
  border: 1px solid #ccc;
  // padding: 10px;
}

/* 选择父元素中的基数子元素 */
.tablex tr > :nth-child(odd) {
  /* 样式设置 */
  background-color: #f2f6fe;
}

// .tablex td {
// height: 8vh;
// width: 90px;
// }
/* 选择父元素中的偶数子元素 */
.parentElement > :nth-child(even) {
  /* 样式设置 */
}

.wrap {
  background-color: #f4f4f4;
}

.box {
  background-color: #ffffff;
}
// TODO:修改
.touzuo {
  width: 56%;
}

.touzuo-top {
  height: 73%;
  width: 100%;
}

.touzuo-btm {
  height: 15%;
  width: 100%;

  table {
    width: 100%;

    tr {
      width: 100%;
      height: 100%;
      display: flex;
      justify-content: center;
      align-items: center;

      td {
        white-space: nowrap;
        flex: 0;
        align-items: center;
      }
    }
  }
}

.touzuobiaoti {
  font-size: 1vw;
  font-weight: 700;
  margin-bottom: 1.5vh;
  display: flex;
  justify-content: space-between;
}

.touzuoxia {
  // border: 1px solid red;
  margin: 2vh 0;
  display: flex;
  justify-content: space-between;
  width: 100%;
}

.touzuoanniu {
  align-items: center;
  display: flex;
  // width: 100%;
}

.touzuoyujing {
  display: flex;
  width: 75%;

  .touzuoyujing-left {
    // font-size:100% ;
    font-weight: 700;
    display: flex;
    align-items: center;
    // width: 22%;
    // background-color: #00afff;
  }
}

.touzuoyujingzhi {
  display: flex;
  align-items: center;
  // border:1px solid red;
  width: 78%;
  overflow: hidden;
  /* 内容超出宽度时隐藏超出部分的内容 */
  max-height: 80px;
  overflow-y: scroll;
  text-overflow: ellipsis;
}

.touzuoyujingzhi::-webkit-scrollbar {
  display: none;
}

::v-deep .el-button--success {
  background-color: #517afc;
}

::v-deep .el-button {
  border-radius: 5px;
}

::v-deep .el-button--success {
  color: #ffffff;
  font-size: 1vw;
}
// TODO:修改
.touyou {
  width: 42%;
}

// ::v-deep .el-select-dropdown__list{
//   text-align: center;
//   border: 1px soild red;
// }
.yishi {
  margin-left: 0;
}

.font {
  background-color: #f4f4f4;
}

::v-deep .el-textarea__inner {
  background-color: #f4f4f4;
}

::v-deep .el-button--primary {
  background-color: #517afc;
  color: #ffffff;
}

.shangbianju {
  margin-top: 1vh;
}

.shangbianju {
  background-color: #ffffff;
}

::v-deep .el-textarea__inner {
  height: 100%;
}

.mt {
  height: 31%;
}

.font {
  height: 100%;
}

.oder {
  display: flex;
  justify-content: space-around;
  margin: 3vh 0;
  margin-bottom: 0;
}

::v-deep .el-input--medium .el-input__inner {
  font-size: 1vw;
}

.oder ::v-deep .el-button--success {
  padding: 10px 7px;
}

.oder ::v-deep .next {
  padding-left: 7px;
  padding-right: 7px;
}

::v-deep .el-select-dropdown__item {
  padding: 0 20px !important;
}

::v-deep .el-select-dropdown__list {
  padding: 0 20px !important;
}

::v-deep .el-tabs {
  display: flex;
  //上下布局
  flex-direction: column;

  .el-tabs__content {
    // flex: 1;
    height: 300px;
  }
  //TODO:修改
  .el-tab-pane {
    height: 70%;
  }
}

.tabBox {
  height: 100%;
  width: 100%;
  display: block;

  table {
    height: 100%;
    width: 100%;
    border-collapse: collapse;

    tr {
      min-height: 20%;

      td {
        border: 1px solid black;
        text-align: center;
      }

      td:nth-child(odd) {
        width: 10%;
        background-color: rgb(234, 234, 253);
      }

      td:nth-child(even) {
        width: 22%;
      }
    }
  }
}

.yujinclass {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-top: 3vh;
  font-weight: 700;

  .yujinclass_tou {
    margin-right: 5px;
    font-size: 1vw;
  }

  .yujinclass_zhi {
    // background-color: red;
    // margin-top: 3px;
    flex-grow: 1;
    white-space: nowrap;
    /* 不允许换行 */
    overflow: hidden;
    /* 隐藏溢出部分 */
    text-overflow: ellipsis;
    /* 显示省略号 */
    //TODO:修改
    width: 52%;
    padding: 0 10px;
    font-size: 0.9vw;
    color: #8c8c8e;
  }
}

.yujinclass_zhi ::v-deep .el-popover__reference-wrapper button {
  width: 95%;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

::v-deep .el-dialog__body {
  padding: 20px 20px;
  background-color: #eff6fb;
}

.biaodan {
  height: 60vh;
}

::v-deep .el-dialog__body::-webkit-scrollbar-button {
  display: none;
}

::v-deep .el-dialog {
  width: 73%;
}

.xiaoli ::v-deep .el-checkbox.is-bordered {
  border: none;
}

.xiaoli
  ::v-deep
  .el-checkbox.is-bordered.el-checkbox--medium
  .el-checkbox__label {
  line-height: 17px;
  font-size: 12px;
  display: flex;
  align-items: center;
  color: #8c8c8e;
}

.xiaoli ::v-deep .el-checkbox.is-bordered.el-checkbox--medium {
  display: flex;
  align-items: center;
}

.biaodananniu {
  display: flex;
  justify-content: flex-end;
}
.canvas-div {
  width:100%;
  height: 100%;
}
.report_printing {
  height: 800px;
}
.canvas-div-parent{
  height: 75vh !important;
}
</style>
