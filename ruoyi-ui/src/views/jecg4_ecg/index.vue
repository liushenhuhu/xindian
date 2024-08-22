<template>
  <div>
    <div class="body">
      <div class="noleft">
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
                        <td>性别</td>
                        <td>{{ data.gender }}</td>
                        <td>心率</td>
                        <td>{{ data.hr }}</td>
                      </tr>
                      <tr>
                        <td>报告编码</td>
                        <td>{{ data.pId }}</td>
                        <td>年龄</td>
                        <td>{{ data.age }}</td>
                        <td>患者症状</td>
                        <td>{{ data.patientSymptom }}</td>
                      </tr>
                      <tr>
                        <td>AI分析结果</td>
                        <td>{{ data.result }}</td>
                        <td>患者病史</td>
                        <td>{{ getMH(dict.type.medical_history) }}</td>
                        <td>心梗机率</td>
                        <td>{{ (data.p_xingeng * 100).toFixed(1) + "%" }}</td>
                      </tr>
                      <tr>
                        <td>肥厚型心肌病</td>
                        <td>{{ (data.p_FHXXJB * 100).toFixed(1) + "%" }}</td>
                        <td>扩张型心肌病</td>
                        <td>{{ (data.p_KZXXJB * 100).toFixed(1) + "%" }}</td>
                        <td>高血钾</td>
                        <td>{{ (data.p_GaoJiaXie * 100).toFixed(1) + "%" }}</td>
                      </tr>
                      <tr v-if="isDoctorUser">
                        <td>P波</td>
                        <td>{{ data.p }}</td>
                        <td>QTc</td>
                        <td>{{ data.qtc }}</td>
                        <td>HRV</td>
                        <td>{{ data.hrv }}ms</td>
                      </tr>
                      <tr v-if="isDoctorUser">
                        <td>QRS区间</td>
                        <td>{{ data.qrs }}ms</td>
                        <td>住院号</td>
                        <td>-</td>
                        <td>申请单号</td>
                        <td></td>
                      </tr>
                    </table>
                  </div>
                </el-tab-pane>
                <el-tab-pane
                  label="心电参数"
                  name="ecgInfo"
                  v-if="!isDoctorUser"
                >
                  <div class="tabBox">
                    <table>
                      <tr>
                        <td>P波</td>
                        <td>{{ data.p }}</td>
                        <td>QTc</td>
                        <td>{{ data.qtc }}</td>
                        <td>HRV</td>
                        <td>{{ data.hrv }}ms</td>
                      </tr>
                      <tr>
                        <td>QRS区间</td>
                        <td>{{ data.qrs }}ms</td>
                        <td>住院号</td>
                        <td>-</td>
                        <td>申请单号</td>
                        <td></td>
                      </tr>
                    </table>
                  </div>
                </el-tab-pane>
              </el-tabs>
            </div>
            <div class="touzuo-btm">
              <div class="yujinclass">
                <div class="yujinclass_tou">预警类型:</div>
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
              <div>
                <el-button
                  type="success"
                  plain
                  icon="el-icon-view"
                  size="mini"
                  @click="isShowNameClick"
                  v-if="true"
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
                v-if="isShowBtn"
                type="success"
                plain
                class="anNiu"
                @click="sendWarnMsg()"
              >
                <el-tooltip
                  content="请注意20个字数限制，每次用户授权，仅有一次发送的机会"
                  placement="top"
                >
                  <i class="el-icon-question"></i>
                </el-tooltip>
                发送预警</el-button
              >
              <el-button type="success" plain class="anNiu" @click="sendMsg()"
                >发送短信</el-button
              >
              <el-button type="success" plain class="anNiu" @click="btnUpload"
                >医生诊断</el-button
              >

              <el-button
                v-if="isShowBtn"
                class="next"
                @click="prev()"
                :loading="loading"
                >上一个</el-button
              >
              <el-button
                v-if="isShowBtn"
                class="next"
                @click="next()"
                :loading="loading"
                >下一个</el-button
              >
            </div>
          </div>
        </div>
      </div>

      <div class="shangbianju">
        <div style="padding: 15px; font-size: 1vw; font-weight: 700">
          患者心电图
        </div>
        <div class="noright">
          <!--          <canvas id="grids" width="750px" height="750px"></canvas>-->
          <div>
            <div
              id="II"
              class="line"
              @dblclick="clicktrue('I', data4.dataII)"
            ></div>
          </div>
          <div>
            <div
              id="V2"
              class="line"
              @dblclick="clicktrue('V2', data4.dataV2)"
            ></div>
          </div>
          <div>
            <div
              id="V4"
              class="line"
              @dblclick="clicktrue('V4', data4.dataV4)"
            ></div>
          </div>
          <div>
            <div
              id="V6"
              class="line"
              @dblclick="clicktrue('V6', data4.dataV6)"
            ></div>
          </div>
        </div>
      </div>
    </div>
    <div class="nobottom"></div>
    <child ref="drawShow" @closeMain="closeMain"></child>

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
    <!-- 预警类型弹窗 -->
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
import { sendMsgToPatient } from "@/api/patient_management/patient_management";
// import child from './child.vue'
// import CacheList from "@/views/monitor/cache/list.vue";
import { addOrUpdateTerm, getTerm } from "@/api/staticECG/staticECG";
import { selectDoctor, getDoctorList } from "@/api/statistics/statistics";
// 发送信息时获取密码
import { getlogin_password } from "@/api/jecg4_ecg/jecg4_ecg";
import child from "@/views/staticECG/staticECG/child.vue";
// 获取预警类型选项
import { selectList } from "@/api/log_user/log_user";
// 存储选择的预警类型
import { addReport as addReportyujing } from "@/api/alert_log_count/count";

import { listPatient_management } from "@/api/patient_management/patient_management";

import { getVerify } from "@/api/verify/verify";
import { createLogger } from "vuex";

export default {
  name: "index",
  components: {
    // CacheList,
    child,
  },
  dicts: ["medical_history"],
  data() {
    return {
      tanchuang: false,
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
      ecgType: "",
      pageNum: 1,
      anoStatus: null,
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
      // ============================上面是上下页需要的数值
      // 原先提交过的预警类型
      logDataType: "",
      tijiaoshuju: {},
      zhi: [],

      yujingzhi: [],
      xianshizifuchuan: "",
      videoVisible: false, //echarts弹出框显示
      markdata: [
        { yAxis: -1 },
        { yAxis: -0.5 },
        { yAxis: 0 },
        { yAxis: 0.5 },
        { yAxis: 1 },
      ], //没放大之前标记线
      dialogFormVisible: false, //弹出框
      items: "", //常用术语
      dynamicTags: ["标签一", "标签二", "标签三"],
      inputVisible: false,
      inputValue: "",
      isSelected: false, //术语按钮没有被按下
      selectedButtons: [], //选中的按钮
      pId: null,
      dialogVisibleTag: null,
      arr: [],
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
        hr: "",
        p: "",
        pr: "",
        qrs: "",
        qtc: "",
        hrv: "",
        pId: "",
        patientSymptom: "暂无症状",
        p_xingeng: "", //心梗率
        p_FHXXJB: "", //肥厚型心肌病
        p_GaoJiaXie: "", //高血钾
        p_KZXXJB: "", //扩张型心肌病
      },
      data4: {
        x: [],
        dataII: [],
        dataV2: [],
        dataV4: [],
        dataV6: [],
      },
      pphone: "",
      nArrII: [],
      nArrV2: [],
      nArrV4: [],
      nArrV6: [],
      x: [], //x轴坐标
      open1: false,
      open2: false,
      open3: false,
      open4: false,
      open5: false,
      open6: false,
      open7: false,
      open8: false,
      open9: false,
      datalabel: {
        waveLabel: "",
        beatLabel: "",
      },
      graphic1: [],
      graphic2: [],
      graphic3: [],
      chart1: null,
      chart2: null,
      chart3: null,
      // 表单校验
      rules: {
        password: [
          { required: true, message: "密码不能为空", trigger: "blur" },
        ],
      },
      isShowBtn: true,
      isDoctorUser: false,
    };
  },
  beforeDestroy() {
    //取消windows的resize事件
    // console.log('beforeDestory')
    window.removeEventListener("resize", this.resizeDraw);
  },
  created() {
    this.queryParams = this.$route.query.queryParams;
    this.ecgType = this.$route.query.ecgType;
    this.pId = this.$route.query.pId;
    this.getList();
  },
  mounted() {
    this.get();
    this.getShowBnt();
    // this.drawgrid();//canvas 画图
    //预警的类型
    // this.getyujingleixing()
  },
  methods: {
    getShowBnt() {
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
      return str;
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
      let isShowName = sessionStorage.getItem("isShowName");
      if (isShowName) {
        if (this.isShowName.status) {
          this.isShowName.status = !this.isShowName.status;
          this.isShowName.name = "显示姓名";
        } else {
          this.isShowName.status = !this.isShowName.status;
          this.isShowName.name = "隐藏姓名";
        }
      } else {
        this.name = true;

        this.verifyForm.password = "";
        this.dialogFormVisibleVerifyAuthority = true;
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
    async getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (
        null != this.daterangeConnectionTime &&
        "" != this.daterangeConnectionTime
      ) {
        this.queryParams.params["beginConnectionTime"] =
          this.daterangeConnectionTime[0];
        this.queryParams.params["endConnectionTime"] =
          this.daterangeConnectionTime[1];
      }
      if (this.queryParams.ecgType == null) {
        this.queryParams.ecgType = this.ecgType;
      }
      await listPatient_management(this.queryParams).then((response) => {
        // console.log(response)
        this.patient_managementList = response.rows;
        this.total = response.total;
        this.loading = false;
        if (this.queryParams.ecgType === "JECG4") {
          this.queryParams.ecgType = null;
        }
        this.patient_managementList.forEach((item, index) => {
          if (this.pId == item.pId) {
            this.index = index;
          }
        });
        if (this.index == this.patient_managementList.length) {
          this.index = 0;
        }
      });
      this.getPatientdetails();
    },
    // 患者用户信息
    getPatientdetails() {
      getReportByPId(this.pId).then((response) => {
        // console.log(response)
        this.data.result = response.data.intelligentDiagnosis;
        this.data.resultByDoctor = response.data.diagnosisConclusion;
        this.data.doctorName = response.data.diagnosisDoctor;
        this.data.diagnosisData = response.data.reportTime;
        this.data.pphone = response.data.pphone;
        this.data.pId = response.data.pId;
        // 病史
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
        // console.log(this.data)
      });
      selectDoctor().then((response) => {
        this.options = response;
      });
      getDoctorList().then((res) => {
        let options = [];
        let data = res.data.options;
        data.forEach((e) => {
          if (e.doctorList.length != 0) {
            let hospital = {
              value: e.hospitalCode,
              label: e.hospitalName,
              children: [],
            };
            e.doctorList.forEach((doctorInfo) => {
              hospital.children.push({
                label: doctorInfo.doctorName,
                value: doctorInfo.doctorName,
              });
            });
            options.push(hospital);
          }
        });
        this.doctorList = options;
        console.log("医生信息");
        console.log(this.doctorList);
      });
      this.getyujingleixing();
    },
    //选择医生
    selectDoctorChange(e) {
      this.data.doctorName = e[1];
    },
    // 上一个
    async prev() {
      this.loading = true;
      if (this.queryParams.pageNum == 1 && this.index == 0) {
        this.$message.warning("已经是第一页！！！");
        this.loading = false;
        return;
      }
      this.index--;
      if (this.index < 0) {
        if (this.queryParams.pageNum > 1) {
          this.queryParams.pageNum--;
          // this.index = 9
        }
        await this.getList();
        this.index = this.queryParams.pageSize - 1;
      }
      // console.log(this.logUserList[this.index]);
      this.pId = this.patient_managementList[this.index].pId;
      let anoStatus = "";
      if (this.anoStatus != null) {
        anoStatus = `&anoStatus=${this.anoStatus}`;
      }
      var newUrl =
        this.$route.path +
        `?pId=${this.pId}&state=${this.$route.query.state}&pageNum=${this.queryParams.pageNum}&pageSize=${this.queryParams.pageSize}` +
        anoStatus +
        `&queryParams=${this.queryParams}&ecgType=${this.queryParams.ecgType}`;
      window.history.replaceState("", "", newUrl);
      this.get();
      // await this.getLogUserList()
      await this.getList();
    },
    // 点击下一个触发事件
    async next() {
      this.loading = true;
      this.index++;

      if (this.index >= this.patient_managementList.length) {
        if (
          (this.queryParams.pageNum - 1) * this.queryParams.pageSize +
            this.patient_managementList.length >=
          this.total
        ) {
          this.$message.warning("已经是最后一页！！！");
          this.index--;
          this.loading = false;
          return;
        }
        this.queryParams.pageNum++;
        // this.index = 0;
        await this.getList();
        this.index = 0;
      }
      this.pId = this.patient_managementList[this.index].pId;
      // console.log(this.patient_managementList);
      // this.queryParams.pId = this.patient_managementList[this.index].pId;
      let anoStatus = "";
      if (this.anoStatus != null) {
        anoStatus = `&anoStatus=${this.anoStatus}`;
      }
      var newUrl =
        this.$route.path +
        `?pId=${this.pId}&state=${this.$route.query.state}&pageNum=${this.queryParams.pageNum}&pageSize=${this.queryParams.pageSize}` +
        anoStatus +
        `&queryParams=${this.queryParams}&ecgType=${this.queryParams.ecgType}`;
      window.history.replaceState("", "", newUrl);
      this.get();
      // await this.getLogUserList()
      await this.getList();
      // this.loading = false;
    },

    // 获取预警类型
    getyujingleixing() {
      selectList().then((res) => {
        this.yujingzhi = res.data;
        // console.log("这是预警值");
        // console.log(this.yujingzhi);

        if (this.logDataType) {
          this.xianshizifuchuan = this.logDataType;
          // 已逗号分隔，并去除每一项中的空格
          this.zhi = this.logDataType.split(",").map((str) => str.trim());
        } else {
          this.zhi = [];
          this.xianshizifuchuan = "";
          //   let zuanhua = ''
          //   zuanhua = this.data.result.replace(/\([^()]*\)/g, ""); // 去掉括号及其内容
          //   // console.log("去掉括号的内容："+zuanhua);
          //   let a =zuanhua.split(/[,]/).map(value => value.trim()).filter(item => item !== "");
          //   let matchedValues = [];

          //     a.forEach(logValue => {
          //       // 遍历yujingzhi数组中的每个对象
          //       this.yujingzhi.forEach(item => {
          //         // 在options中查找匹配项
          //         item.options.forEach(options => {
          //           if (options.value == logValue) {
          //             // 如果找到匹配项，则将其加入matchedValues数组
          //             matchedValues.push(options.value);
          //           }
          //         });
          //       });
          //     });
          //   this.xianshizifuchuan = matchedValues.map(item => item.toString()).join(",")
          //   this.zhi=matchedValues
        }
      });
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
    dialogVisible() {
      getTerm().then((r) => {
        if (r.rows.length > 0) {
          this.dynamicTags = JSON.parse(r.rows[0].termText);
        }
        this.dialogVisibleTag = true;
      });
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
    clicktrue(title, data) {
      // return;
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
    showChart1() {
      return;
      this.open1 = true;
      var pId = this.pId;
      // 找到对应的canvas
      var canvas = $("#II").find("canvas")[0];
      // 将图片转化为图片数据  toDataURL返回的base64
      var base64 = canvas.toDataURL("image/png", 1);
      var level = 1;
      //this.$refs.drawShow.openDrawShow(base64,pId,level);
      this.open1 = true;
      this.$refs.drawShow.getchart(
        this.nArr[0],
        pId,
        level,
        "II",
        1,
        this.datalabel
      );
    },
    showChart2() {
      var pId = this.pId;
      var canvas = $("#V2").find("canvas")[0];
      // 将图片转化为图片数据  toDataURL返回的base64
      var base64 = canvas.toDataURL("image/png", 1);
      var level = 2;
      //this.$refs.drawShow.openDrawShow(base64,pId,level);

      this.$refs.drawShow.getchart(
        this.nArr[1],
        pId,
        level,
        "II",
        1,
        this.datalabel
      );
    },
    showChart3() {
      var pId = this.pId;
      var canvas = $("#V4").find("canvas")[0];
      // 将图片转化为图片数据  toDataURL返回的base64
      var base64 = canvas.toDataURL("image/png", 1);
      var level = 3;
      //this.$refs.drawShow.openDrawShow(base64,pId,level);

      this.$refs.drawShow.getchart(
        this.nArr[2],
        pId,
        level,
        "II",
        1,
        this.datalabel
      );
    },
    showChart4() {
      var pId = this.pId;
      var canvas = $("#V6").find("canvas")[0];
      // 将图片转化为图片数据  toDataURL返回的base64
      var base64 = canvas.toDataURL("image/png", 1);
      var level = 4;
      //this.$refs.drawShow.openDrawShow(base64,pId,level);

      this.$refs.drawShow.getchart(
        this.nArr[3],
        pId,
        level,
        "II",
        1,
        this.datalabel
      );
    },
    showChart5() {
      var pId = this.pId;
      var canvas = $("#5").find("canvas")[0];
      // 将图片转化为图片数据  toDataURL返回的base64
      var base64 = canvas.toDataURL("image/png", 1);
      var level = 5;
      //this.$refs.drawShow.openDrawShow(base64,pId,level);

      this.$refs.drawShow.getchart(this.nArr[4], pId, level);
    },
    showChart6() {
      var pId = this.pId;
      var canvas = $("#6").find("canvas")[0];
      // 将图片转化为图片数据  toDataURL返回的base64
      var base64 = canvas.toDataURL("image/png", 1);
      var level = 6;
      //this.$refs.drawShow.openDrawShow(base64,pId,level);

      this.$refs.drawShow.getchart(this.nArr[5], pId, level);
    },
    showChart7() {
      var pId = this.pId;
      var canvas = $("#7").find("canvas")[0];
      // 将图片转化为图片数据  toDataURL返回的base64
      var base64 = canvas.toDataURL("image/png", 1);
      var level = 7;
      //this.$refs.drawShow.openDrawShow(base64,pId,level);

      this.$refs.drawShow.getchart(this.nArr[6], pId, level);
    },
    showChart8() {
      var pId = this.pId;
      var canvas = $("#8").find("canvas")[0];
      // 将图片转化为图片数据  toDataURL返回的base64
      var base64 = canvas.toDataURL("image/png", 1);
      var level = 8;
      //this.$refs.drawShow.openDrawShow(base64,pId,level);

      this.$refs.drawShow.getchart(this.nArr[7], pId, level);
    },
    showChart9() {
      var pId = this.pId;
      var canvas = $("#9").find("canvas")[0];
      // 将图片转化为图片数据  toDataURL返回的base64
      var base64 = canvas.toDataURL("image/png", 1);
      var level = 9;
      //this.$refs.drawShow.openDrawShow(base64,pId,level);

      this.$refs.drawShow.getchart(this.nArr[8], pId, level);
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
    putDown(key, event) {
      //console.log(event.currentTarget.classList.toggle('selected'))
      event.currentTarget.classList.toggle("selected");
      console.log(this.arr);
      let index = this.arr.indexOf(key);
      //console.log(index)
      if (index !== -1) {
        this.arr.splice(index, 1);
      } else {
        this.arr.push(key);
      }
    },
    dialogForm() {
      if (this.data.resultByDoctor) {
        this.data.resultByDoctor =
          this.data.resultByDoctor + "," + this.arr.toString();
      } else {
        this.data.resultByDoctor = this.arr.toString();
      }
      this.dialogFormVisible = false;
    },
    resizeDraw() {
      let _th = this;
      console.log("窗口宽度改变");
      console.log($("#II").width());
      $(".line").css({
        height: $("#II").width() * 0.17 + "px",
      });
      _th.chart1.resize();
      _th.chart2.resize();
      _th.chart3.resize();
      _th.chart4.resize();
      _th.addtext();
      console.log(1111111);
    },
    //请求数据
    get() {
      // console.log('开始查询')
      const loading = this.$loading({
        lock: true, //lock的修改符--默认是false
        text: "请勿刷新页面，正在获取数据，请耐心等待1-3分钟...", //显示在加载图标下方的加载文案
        spinner: "el-icon-loading", //自定义加载图标类名
        background: "rgba(0, 0, 0, 0.7)", //遮罩层颜色
        target: document.querySelector("#table"), //loadin覆盖的dom元素节点
      });
      var _th = this;
      //console.log("pId:", this.pId)
      this.data.dataTime = this.$options.methods.getData();
      $.ajax({
        type: "post",
        url: "https://screen.mindyard.cn:84/get_jecg_12",
        // asynsc: false,
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({
          pid: this.pId,
          ecgType: "4",
        }),
        async: false,
        beforeSend: function (request) {
          // 如果后台没有跨域处理，这个自定义
          request.setRequestHeader("user", "zzu");
          request.setRequestHeader("password", "zzu123");
        },
        success: function (data) {
          loading.close();
          _th.data.resultByDoctor = data.result.diagnosis_conclusion;
          _th.data.doctorName = data.result.diagnosis_doctor;
          _th.data.age = data.result.age;
          _th.data.gender = data.result.gender;
          _th.data.name = data.result.patientName;
          _th.data.p_xingeng = data.result.p_xingeng;
          _th.data.p_FHXXJB = data.result.ecg_analysis_data.p_FHXXJB;
          _th.data.p_GaoJiaXie = data.result.ecg_analysis_data.p_GaoJiaXie;
          _th.data.p_KZXXJB = data.result.ecg_analysis_data.p_KZXXJB;
          //_th.data.result = data.result.intelligent_diagnosis
          _th.data.hr = data.result.ecg_analysis_data["平均心率"];
          _th.data.hr = data.result.ecg_analysis_data["平均心率"];
          _th.data.p = data.result.ecg_analysis_data["P波时限"];
          _th.data.pr = data.result.ecg_analysis_data["PR间期"];
          _th.data.qrs = data.result.ecg_analysis_data["QRS波时限"];
          _th.data.qtc = data.result.ecg_analysis_data["QTc"];
          _th.data.hrv = data.result.ecg_analysis_data["RMSSD"];
          // _th.data.datas = data.result.II
          _th.data4.dataII = data.result["II"];
          _th.data4.dataV2 = data.result["V2"];
          _th.data4.dataV4 = data.result["V4"];
          _th.data4.dataV6 = data.result["V6"];
          _th.datalabel.waveLabel = data.result.waveLabel;
          _th.datalabel.beatLabel = data.result.beatLabel;
          //console.log("获取到的导联数据", _th.data.datas)
          // _th.nArr = _th.getNewArray(_th.data.datas, 500);
          _th.nArrII = _th.getNewArray(_th.data4.dataII, 500);
          _th.nArrV2 = _th.getNewArray(_th.data4.dataV2, 500);
          _th.nArrV4 = _th.getNewArray(_th.data4.dataV4, 500);
          _th.nArrV6 = _th.getNewArray(_th.data4.dataV6, 500);
          _th.x = [];
          for (var i = 0; i < 500; i++) {
            _th.x.push(i);
          }
          for (var i = 0; i < 1000; i += 20) {
            _th.markdata.push({ xAxis: i });
          }
          $(".line").css({
            height: $("#II").width() * 0.17 + "px",
          });
          _th.chart1 = echarts.init(document.getElementById("II"));
          _th.chart2 = echarts.init(document.getElementById("V2"));
          _th.chart3 = echarts.init(document.getElementById("V4"));
          _th.chart4 = echarts.init(document.getElementById("V6"));

          _th.chart1.clear();
          _th.chart1.setOption({
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
            // graphic:_th.graphic1,
            xAxis: {
              type: "category",
              boundaryGap: true,
              data: _th.x,
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
                  type: "solid", //网格是实线，可以修改成虚线以及其他的类型
                  ////opacity: 0.6,//透明度
                },
              },
            },
            yAxis: {
              type: "value",
              boundaryGap: true,
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
                  type: "solid", //网格是实线，可以修改成虚线以及其他的类型
                  ////opacity: 0.6,//透明度
                },
              },
              max: 1,
              min: -1,
            },
            series: {
              type: "line",
              smooth: true,
              showSymbol: true,
              data: _th.data4.dataII,
              z: 5,
              lineStyle: {
                normal: {
                  color: "#000000",
                  width: 1.5,
                  // //opacity: 1,
                },
              },
              markLine: {
                z: 1,
                symbol: "none",
                silent: true,
                lineStyle: {
                  type: "solid",
                  color: "#d77a7a",
                  width: 1,
                  ////opacity: 0.5,
                },
                label: {
                  position: "start", // 表现内容展示的位置
                  color: "#d77a7a", // 展示内容颜色
                },
                data: _th.markdata,
              },
            },
          });

          _th.chart2.clear();
          _th.chart2.setOption({
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
              data: _th.x,
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
                  type: "solid", //网格是实线，可以修改成虚线以及其他的类型
                  ////opacity: 0.6,//透明度
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
                  type: "solid", //网格是实线，可以修改成虚线以及其他的类型
                  ////opacity: 0.6,//透明度
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
                data: _th.data4.dataV2,
                z: 5,
                lineStyle: {
                  normal: {
                    color: "#000000",
                    width: 1.5,
                  },
                },
                markLine: {
                  z: 1,
                  symbol: "none",
                  silent: true,
                  lineStyle: {
                    type: "solid",
                    color: "#d77a7a",
                    width: 1,
                    ////opacity: 0.5,
                  },
                  label: {
                    position: "start", // 表现内容展示的位置
                    color: "#d77a7a", // 展示内容颜色
                  },
                  data: _th.markdata,
                },
              },
            ],
          });

          _th.chart3.clear();
          _th.chart3.setOption({
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
              data: _th.x,
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
                  type: "solid", //网格是实线，可以修改成虚线以及其他的类型
                  ////opacity: 0.6,//透明度
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
                  type: "solid", //网格是实线，可以修改成虚线以及其他的类型
                  ////opacity: 0.6,//透明度
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
                data: _th.data4.dataV4,
                z: 5,
                lineStyle: {
                  normal: {
                    color: "#000000",
                    width: 1.5,
                  },
                },
                markLine: {
                  z: 1,
                  symbol: "none",
                  silent: true,
                  lineStyle: {
                    type: "solid",
                    color: "#d77a7a",
                    width: 1,
                    //opacity: 0.5,
                  },
                  label: {
                    position: "start", // 表现内容展示的位置
                    color: "#d77a7a", // 展示内容颜色
                  },
                  data: _th.markdata,
                },
              },
            ],
          });

          _th.chart4.clear();
          _th.chart4.setOption({
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
              data: _th.x,
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
                  type: "solid", //网格是实线，可以修改成虚线以及其他的类型
                  //opacity: 0.6,//透明度
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
                  type: "solid", //网格是实线，可以修改成虚线以及其他的类型
                  //opacity: 0.6,//透明度
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
                data: _th.data4.dataV6,
                z: 5,
                lineStyle: {
                  normal: {
                    color: "#000000",
                    width: 1.5,
                  },
                },
                markLine: {
                  z: 1,
                  symbol: "none",
                  silent: true,
                  lineStyle: {
                    type: "solid",
                    color: "#d77a7a",
                    width: 1,
                    //opacity: 0.5,
                  },
                  label: {
                    position: "start", // 表现内容展示的位置
                    color: "#d77a7a", // 展示内容颜色
                  },
                  data: _th.markdata,
                },
              },
            ],
          });
          _th.addtext();

          window.addEventListener("resize", _th.resizeDraw);
        },
        error: function (data) {
          alert("数据请求错误,请刷新页面或联系管理员");
          loading.close();
          console.log("请求失败：", data);
        },
      });
    },
    //重绘所有点之间的文本
    addtext() {
      return;
      this.graphic1.length = 0;
      this.graphic2.length = 0;
      this.graphic3.length = 0;
      let beatLabel = JSON.parse(this.datalabel.beatLabel);
      var arr1 = beatLabel["0"],
        arr2 = beatLabel["1"],
        arr3 = beatLabel["2"];
      let beat1 = [],
        beat2 = [],
        beat3 = [];
      // console.log(arr1,arr2,arr3)
      let keys = Object.keys(arr1);
      for (let i = 0; i < keys.length; i++) {
        beat1.push(...arr1[keys[i]]);
        beat2.push(...arr2[keys[i]]);
        beat3.push(...arr3[keys[i]]);
      }
      // console.log(beat1,beat2,beat3)
      beat1.sort((a, b) => a - b);
      beat2.sort((a, b) => a - b);
      beat3.sort((a, b) => a - b);
      var length1 = beat1.length;
      //刻度线
      for (let i = 0; i < length1; i++) {
        var point1 = this.chart1.convertToPixel({ seriesIndex: 0 }, [
          beat1[i],
          3,
        ]);
        let text1 = {
          type: "line",
          style: {
            stroke: "#333",
            lineWidth: 1.5,
            lineDash: [],
          },
          shape: {
            x1: point1[0],
            y1: 1,
            x2: point1[0],
            y2: 11,
          },
          z: 100,
        };
        this.graphic1.push(text1);
        if (i == length1 - 1) {
          continue;
        }
        var x1 = beat1[i];
        var x2 = beat1[i + 1];
        // console.log(x1,x2)
        var time = ((x2 - x1) / 25) * 0.25; //时间 s
        var heart = (60 / time).toFixed(1); //心率
        time = (time * 1000).toFixed(0);
        //文本值
        var point2 = this.chart1.convertToPixel({ seriesIndex: 0 }, [
          (x2 - x1) / 2 + x1,
          3,
        ]);
        // console.log(x)
        let text2 = {
          type: "text",
          x: point2[0] - 15,
          y: 3,
          z: 999,
          style: {
            text: time + `\n${heart}`,
            fill: "#000000",
            fontWeight: 400,
            fontSize: 13,
          },
        };
        this.graphic1.push(text2);
      }
      var chartOption1 = this.chart1.getOption();
      chartOption1.graphic = this.graphic1;
      this.chart1.setOption(chartOption1, true);

      var length2 = beat2.length;
      //刻度线
      for (let i = 0; i < length2; i++) {
        var point1 = this.chart2.convertToPixel({ seriesIndex: 0 }, [
          beat2[i],
          3,
        ]);
        let text1 = {
          type: "line",
          style: {
            stroke: "#333",
            lineWidth: 1.5,
            lineDash: [],
          },
          shape: {
            x1: point1[0],
            y1: 1,
            x2: point1[0],
            y2: 11,
          },
          z: 100,
        };
        this.graphic2.push(text1);
        if (i == length2 - 1) {
          continue;
        }
        var x1 = beat2[i];
        var x2 = beat2[i + 1];
        // console.log(x1,x2)
        var time = ((x2 - x1) / 25) * 0.25; //时间 s
        var heart = (60 / time).toFixed(1); //心率
        time = (time * 1000).toFixed(0);
        //文本值
        var point2 = this.chart2.convertToPixel({ seriesIndex: 0 }, [
          (x2 - x1) / 2 + x1,
          3,
        ]);
        // console.log(x)
        let text2 = {
          type: "text",
          x: point2[0] - 15,
          y: 3,
          z: 999,
          style: {
            text: time + `\n${heart}`,
            fill: "#000000",
            fontWeight: 400,
            fontSize: 13,
          },
        };
        this.graphic2.push(text2);
      }
      var chartOption2 = this.chart2.getOption();
      chartOption2.graphic = this.graphic2;
      this.chart2.setOption(chartOption2, true);

      var length3 = beat3.length;
      //刻度线
      for (let i = 0; i < length3; i++) {
        var point1 = this.chart3.convertToPixel({ seriesIndex: 0 }, [
          beat3[i],
          3,
        ]);
        let text1 = {
          type: "line",
          style: {
            stroke: "#333",
            lineWidth: 1.5,
            lineDash: [],
          },
          shape: {
            x1: point1[0],
            y1: 1,
            x2: point1[0],
            y2: 11,
          },
          z: 100,
        };
        this.graphic3.push(text1);
        if (i == length3 - 1) {
          continue;
        }
        var x1 = beat3[i];
        var x2 = beat3[i + 1];
        // console.log(x1,x2)
        var time = ((x2 - x1) / 25) * 0.25; //时间 s
        var heart = (60 / time).toFixed(1); //心率
        time = (time * 1000).toFixed(0);
        //文本值
        var point2 = this.chart3.convertToPixel({ seriesIndex: 0 }, [
          (x2 - x1) / 2 + x1,
          3,
        ]);
        // console.log(x)
        let text2 = {
          type: "text",
          x: point2[0] - 15,
          y: 3,
          z: 999,
          style: {
            text: time + `\n${heart}`,
            fill: "#000000",
            fontWeight: 400,
            fontSize: 13,
          },
        };
        this.graphic3.push(text2);
      }
      var chartOption3 = this.chart3.getOption();
      chartOption3.graphic = this.graphic3;
      this.chart3.setOption(chartOption3, true);
      // console.log(this.graphic1,this.graphic2,this.graphic3)
    },
    //获取修改后的标注数据
    closeMain(val) {
      console.log();
      return;
      var value = JSON.parse(val);
      let data = JSON.parse(this.datalabel.beatLabel);
      for (let key in value) {
        data[key] = value[key];
      }
      this.datalabel.beatLabel = JSON.stringify(data);
      this.addtext();
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
    /*cancleDialog(){
      this.dialogFormVisible = false;
      this.arr = [];
    },*/
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
    sendWarnMsg() {
      if (
        this.data.resultByDoctor == "" ||
        this.data.resultByDoctor == null ||
        this.data.resultByDoctor.length > 20
      ) {
        this.$message({
          type: "error",
          message: "预警消息不能为空或长度最多20个字",
        });
        return;
      }
      let obj = {
        pId: this.data.pId,
        warningText: this.data.resultByDoctor,
      };
      reportEarlyWarningMsg(obj).then((r) => {
        this.$message({
          type: "success",
          message: "发送成功!",
        });
      });
    },
    //医生诊断
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

      console.log(this.data.resultByDoctor);
      var form = {
        pId: this.pId,
        diagnosisStatus: "1",
        startDateTime: `${year}-${month}-${day} ${hour}:${minute}:${second}`,
        diagnosisConclusion: this.data.resultByDoctor,
        reportTime: this.data.dataTime,
        diagnosisDoctor: this.data.doctorName,
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
    //常用术语
    Camera() {
      var _th = this;
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
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then((_) => {
          done();
        })
        .catch((_) => {});
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
  },
};
</script>

<style lang="scss" scoped>
::v-deep .el-tabs__nav {
  z-index: 1;
}

.body {
  display: flex;
  flex-direction: column;
  width: 100%;
  padding: 10px;
  background-color: #e8e8e8;
}

::v-deep el-button {
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
  .box {
    overflow: hidden;
    width: 100%;
    margin: 0 auto;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    margin-top: 1.5vh;
    margin-bottom: 1.5vh;
    border-radius: 2vh;
    background-color: #ffffff;
    padding: 15px;
    //opacity: 0.6;
    padding-bottom: 0;

    .box1 {
      width: 35%;
      height: 100%;
      display: flex;
      flex-direction: column;
    }

    .box2 {
      width: 32.5%;
      height: 100%;
    }

    .box3 {
      width: 32.5%;
      height: 100%;
    }

    .h11 {
      width: 100%;
      font-size: 2.5vh;
      background-color: #e2e2e3;
      font-weight: 700;
      height: 4vh;
      display: flex;
      margin-top: 1vh;

      span {
        width: 6px;
        height: 100%;
        background-color: #00afff;
      }

      p {
        height: 40px;
        line-height: 4vh;
        margin: 0;
        margin-left: 1vw;
      }

      .between {
        width: 100%;
        display: flex;
        justify-content: space-between;

        p {
          height: 4vh;
          line-height: 4vh;
          margin: 0;
          margin-left: 1vw;
        }
      }
    }

    .result {
      height: 13.5vh;
      width: 100%;
    }
  }
}

.size {
  font-size: 2.3vh;
}

.mmargin {
  margin: 1.5vh 0 1.5vh 0;
  overflow: hidden;
  overflow-y: auto;
  -webkit-overflow-scrolling: touch;
  /* 提高移动设备上的滚动性能 */
  -ms-overflow-style: none;
  scrollbar-width: none;
}

.margin {
  width: 100%;
  display: flex;
  justify-content: center;
  margin-top: 1vw;
}

.ml {
  margin-left: 2vw;
  margin-right: 2vw;
}

.patientMessage {
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
    width: 50%;
    margin-bottom: 1.5vh;
    font-size: 2.1vh;
  }

  .textBoxBottom {
    margin-bottom: 10px;
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
  height: 29%;
  // margin: 2vh 0 2vh 0;
  display: flex;
  flex-direction: column;
  width: 100%;
  justify-content: center;

  .input {
    display: flex;
    flex-direction: row;
    margin-top: 1vh;
    //margin-left: 2vw;

    strong {
      white-space: nowrap;
      line-height: 36px;
      margin-right: 0.5vw;
      font-size: 1vw;
    }

    ::v-deep .el-input--medium .el-input__inner {
      //width: 60%;
    }
  }
}

.noright {
  width: 98%;
  display: flex;
  flex-direction: column;
  margin: 0 auto;
  margin-bottom: 3vh;
  //border: 1px solid #000000;
}

.ecg {
  //width: 50vw;
  //  height: 50vw;
  display: flex;
  flex-direction: column;
}

.line {
  height: 15.5vh;
  width: 99%;
  margin: 0;
  padding: 0;
}

.upload {
  margin-top: 1vw;
  //height: 5vw;
  display: flex;
  justify-content: center;
  margin-bottom: 1.5vh;
}

// .anNiu {
//   height: 30px;
//   // width: 8vw;
//   //font-size: 1.5vw;
//   line-height: 1vw;
//   text-align: center;
//   padding: 0 3px;
// }

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

.el-tag + .el-tag {
  margin-left: 10px;
}

.button-new-tag {
  margin-left: 10px;
  height: 32px;
  line-height: 30px;
  padding-top: 0;
  padding-bottom: 0;
}

.input-new-tag {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
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
//   height: 8vh;
//   width: 90px;
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

.touzuo {
  width: 66%;
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
  display: flex;
  align-items: center;
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
}

.touyou {
  width: 32%;
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
  overflow: hidden;
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

::v-deep .el-button--success {
  color: #ffffff;
  font-size: 1vw;
}

::v-deep .el-select-dropdown__item {
  padding: 0 20px;
}

::v-deep .el-tabs {
  display: flex;
  //上下布局
  flex-direction: column;

  .el-tabs__content {
    // flex: 1;
    height: 300px;
    overflow: auto !important;
  }

  .el-tab-pane {
    height: 100%;
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
    font-size: 1vw;
    margin-right: 5px;
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
    width: 62%;
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
</style>
