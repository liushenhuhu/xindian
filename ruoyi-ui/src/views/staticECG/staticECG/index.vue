<template>
  <div>
    <div class="body">
      <div class="noleft">
        <div class="box" v-if="xuanzheyujingleixing">

          <!-- <div class="box1">
            <div class="h11">
              <span></span>
              <p>用户信息</p>
            </div>
            <div class="patientMessage">
              <div class="textBoxBottom"><strong>报告编号:</strong>{{ data.pId }}</div>
             <div class="textbox "><strong>姓名:</strong>{{ data.name }}</div>
              <div class="textbox "><strong>性别:</strong>{{ data.gender }}</div>
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
              <div class="textbox" v-if="false"><strong>心梗机率:</strong>{{ data.p_xingeng>0.7?(data.p_xingeng*100).toFixed(1)+'%':'暂无风险' }}</div>
            </div>
          </div>
           上面中心
          <div class="box2">
            <div class="h11">
              <span></span>
              <p>自动分析结果，仅供参考</p>
            </div>
            <div class="result size mmargin">
              <div class="ml">{{ data.result }}</div>
            </div>
            <div class="h11">
              <span></span>
              <p>用户症状</p>
            </div>
            <div class="result size mmargin">
              <div class="ml">{{ data.patientSymptom }}</div>
            </div>


            <div class="h11" style="display: flex;justify-content:space-between;">
              <div>
                <span></span>
                <p>预警类型</p>
              </div>
              <div>
                <el-button type="success" round size="mini" class="kangbian"  @click="xianshi">选择预警类型</el-button>
                <el-button type="success" round size="mini" class="kangbian" @click="tijiao()">提交</el-button>
              </div>
            </div>
            <div class="result size mmargin">
              <div class="ml">{{ xianshizifuchuan }}</div>
            </div>
          </div> -->

          <!-- <div class="touzuo">
              <div class="touzuobiaoti">患者信息</div>
              <table class="tablex">
                <tr>
                  <td>报告编码</td>
                  <td>{{ data.pId }}</td>
                  <td>性别</td>
                  <td>{{ data.gender }}</td>
                  <td>p波</td>
                  <td>{{ data.p }}ms</td>
                  <td>Qtc</td>
                  <td>{{ data.qtc }}ms</td>
                </tr>
                <tr>
                  <td>患者编码</td>
                  <td>{{ data.pId }}</td>
                  <td>门诊号</td>
                  <td>-</td>
                  <td>QRS区间</td>
                  <td>{{ data.qrs }}ms</td>
                  <td>HRV</td>
                  <td>{{ data.hrv }}ms</td>
                </tr>
                <tr>
                  <td>申请单号</td>
                  <td>-</td>
                  <td>住院号</td>
                  <td>-</td>
                  <td>AI分析结果</td>
                  <td colspan="3">{{ data.result }}</td>

                </tr>
                <tr>
                  <td>年龄</td>
                  <td>{{ data.age }}岁</td>
                  <td>心律</td>
                  <td>{{ data.hr }}bpm</td>
                  <td>患者症状</td>
                  <td colspan="3">{{ data.patientSymptom }}</td>

                </tr>
              </table>
              <div class="touzuoxia">
                <div class="touzuoyujing">
                  <div class="touzuoyujing-left">预警类型：</div>
                  <div style="" class="touzuoyujingzhi">{{ xianshizifuchuan }}</div>
                </div>
                <div class="touzuoanniu">
                    <el-button type="success" round @click="xianshi">选择预警类型</el-button>
                    <el-button type="success" round @click="tijiao()">提交</el-button>
                </div>
              </div>
             </div> -->
          <div class="touzuo">
            <div class="touzuo-top">
              <el-tabs style="height: 100%;width: 100%" v-model="tabsStatus" type="card" @tab-click="switchTabs">
                <el-tab-pane label="基本信息" name="userInfo">
                  <div class="tabBox">
                    <table>
                      <tr>
                        <td>姓名</td>
                        <td>{{ data.name }}</td>
                        <td>性别</td>
                        <td>{{ data.gender }}</td>
                        <td>住院号</td>
                        <td>-</td>
                      </tr>
                      <tr>
                        <td>报告编码</td>
                        <td>{{ data.pId }}</td>
                        <td>年龄</td>
                        <td>{{ data.age }}岁</td>
                        <td></td>
                        <td></td>
                      </tr>
                      <tr>
                        <td>申请单号</td>
                        <td></td>
                        <td>门诊</td>
                        <td></td>
                        <td></td>
                        <td></td>
                      </tr>
                    </table>
                  </div>
                </el-tab-pane>
                <el-tab-pane label="心电参数" name="ecgInfo">
                  <div class="tabBox">
                    <table>
                      <tr>
                        <td>心律</td>
                        <td>{{ data.hr }}</td>
                        <td>Qtc</td>
                        <td>{{ data.qtc }}</td>
                        <td>患者症状</td>
                        <td>{{ data.patientSymptom }}</td>
                      </tr>
                      <tr>
                        <td>p波</td>
                        <td>{{ data.p }}</td>
                        <td>HRV</td>
                        <td>{{ data.hrv }}ms</td>
                        <td></td>
                        <td></td>
                      </tr>
                      <tr>
                        <td>QRS区间</td>
                        <td>{{ data.qrs }}ms</td>
                        <td>AI分析结果</td>
                        <td>{{ data.result }}</td>
                        <td></td>
                        <td></td>
                      </tr>
                    </table>
                  </div>
                </el-tab-pane>
              </el-tabs>
            </div>
            <div class="touzuo-btm">
              <table>
                <tr>
                  <td>预警类型:</td>
                  <td style="flex:1;overflow: hidden;" :title="xianshizifuchuan">{{xianshizifuchuan}}</td>
                  <td>
                    <el-button type="success" round @click="xianshi">选择预警类型</el-button>
                  </td>
                  <td>
                    <el-button type="success" round @click="tijiao()">提交</el-button>
                  </td>
                </tr>
              </table>
            </div>
          </div>


          <!-- <div class="box3">
            <div class="h11">
              <span></span>
              <div class="between">
                <p>医师诊断</p>
                <el-button type="text" @click="dialogVisible" style="padding:0;line-height: 4vh;margin-right: 1vw;font-size:2.5vh">新增术语</el-button>
                <el-button type="text" @click="Camera" style="padding:0;line-height: 4vh;margin-right: 1vw;font-size:2.5vh">常用术语</el-button>
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
              <el-button v-else class="button-new-tag" size="small" @click="showInput">+ 单机新增标签术语</el-button>
              <div slot="footer" class="dialog-footer">
                <el-button  @click="dialogVisibleTag=false">取 消</el-button>
                <el-button type="primary" @click="termTag">确 定</el-button>
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
                <el-select v-model="data.doctorName" clearable>
                  <el-option
                    v-for="item in options"
                    :label="item.doctorName"
                    :value="item.doctorName">
                  </el-option>
                </el-select>
              </div>
              <div class="input">
                <strong>日期:</strong>
                <el-input v-if="data.diagnosisData!=null" v-model="data.diagnosisData" clearable style="width: 80%"></el-input>
                <el-input v-else v-model="data.dataTime" clearable style="width: 80%"></el-input>
              </div>
            </div>
            <div class="upload">
              <el-button class="anNiu" type="success" plain @click="sendWarnMsg()">
                <el-tooltip content="请注意20个字数限制，每次用户授权，仅有一次发送的机会" placement="top">
                  <i class="el-icon-question"></i>
                </el-tooltip>
                发送预警
              </el-button>
              <el-button class="anNiu" type="success" plain @click="sendMsg()">发送短信</el-button>
              <el-button class="anNiu" type="success" plain @click="btnUpload">医生诊断</el-button>
            </div>
            <div class="updown">
              <el-button
                class="next"
                @click="prev()"
                type="primary"
                :loading="loading"
              >上一个</el-button>
              <el-button class="next"  @click="next()" :loading="loading">下一个</el-button>
            </div>
          </div> -->

          <div class="touyou">
            <div class="touzuobiaoti">医师诊断</div>
            <div class="mt">
              <el-input
                type="textarea"
                v-model="data.resultByDoctor"
                placeholder="请输入"
                data-value="1111"
                :rows="5"
                class="font">{{ data.resultByDoctor }}
              </el-input>
            </div>

            <div class="doctor">
              <div class="input yishi">
                <strong>医师:</strong>
                <!--<el-select v-model="data.doctorName" clearable style="width: 66%">
                  <el-option
                    v-for="item in options"
                    :label="item.doctorName"
                    :value="item.doctorName">
                  </el-option>
                </el-select>-->
                <el-cascader v-model="selectDoctor" :options="doctorList" @change="selectDoctorChange"
                :show-all-levels="false">
                </el-cascader>
              </div>
              <div class="input">
                <strong>日期:</strong>
                <el-input v-if="data.diagnosisData!=null" v-model="data.diagnosisData" clearable
                          style="width: 66%"></el-input>
                <el-input v-else v-model="data.dataTime" clearable style="width: 34%"></el-input>
              </div>
            </div>

            <div class="oder">


              <el-button type="success" plain class="anNiu" @click="sendWarnMsg()">
                <el-tooltip content="请注意20个字数限制，每次用户授权，仅有一次发送的机会" placement="top">
                  <i class="el-icon-question"></i>
                </el-tooltip>
                发送预警
              </el-button>
              <el-button type="success" plain class="anNiu" @click="sendMsg()">发送短信</el-button>
              <el-button type="success" plain class="anNiu" @click="btnUpload">医生诊断</el-button>
              <el-button class="next" @click="prev()" :loading="loading">上一个</el-button>
              <el-button class="next" @click="next()" :loading="loading">下一个</el-button>


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


        <!-- 预警类型弹窗 -->
        <div class="xuanzheyujing" v-else>
          <div class="wancheng">
            <div>选中的值为：{{ xianshizifuchuan }}</div>
            <div>
              <el-button type="success" round size="mini" class="kangbian" @click="xianshi">完成</el-button>
            </div>
          </div>
          <form id="loginForm" name="loginForm" class="biaodan">
            <div class="duoxuan">
              <el-checkbox-group v-model="zhi" @change="zhong">
                <div v-for="(group,index) in yujingzhi" :key="index">
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
                          :label="item.value"
                          border
                          size="mini"
                        >
                          {{ item.value }}
                        </el-checkbox>
                      </li>
                    </ul>
                  </div>
                  <div class="xian"></div>
                </div>
              </el-checkbox-group>
            </div>
          </form>
        </div>
      </div>

      <div class="shangbianju">
        <div>
          <div style="padding: 15px ;font-size: 1vw;font-weight: 700;">患者心电图</div>
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
          <!-- <div>-->
          <!--            <div id="5" class="line" @dblclick="showChart5()"></div>-->
          <!--          </div>-->
          <!--          <div>-->
          <!--            <div id="6" class="line" @dblclick="showChart6()"></div>-->
          <!--          </div>-->
          <!--          <div>-->
          <!--            <div id="7" class="line" @dblclick="showChart7()"></div>-->
          <!--          </div>-->
          <!--          <div>-->
          <!--            <div id="8" class="line" @dblclick="showChart8()"></div>-->
          <!--          </div>-->
          <!--          <div>-->
          <!--            <div id="9" class="line" @dblclick="showChart9()"></div>-->
          <!--          </div> -->
        </div>
      </div>
    </div>
    <div class="nobottom"></div>

    <!--    <div>-->
    <!--      <div class="lineI" v-show="open1">-->
    <!--        <div id="I1" class="lineshow"></div>-->
    <!--        <button @click="clickClose" class="noName">关闭</button>-->
    <!--      </div>-->
    <!--      <div class="lineI" v-show="open2">-->
    <!--        <div id="I2" class="lineshow"></div>-->
    <!--        <button @click="clickClose" class="noName">关闭</button>-->
    <!--      </div>-->
    <!--      <div class="lineI" v-show="open3">-->
    <!--        <div id="I3" class="lineshow"></div>-->
    <!--        <button @click="clickClose" class="noName">关闭</button>-->
    <!--      </div>-->
    <!--      <div class="lineI" v-show="open4">-->
    <!--        <div id="I4" class="lineshow"></div>-->
    <!--        <button @click="clickClose" class="noName">关闭</button>-->
    <!--      </div>-->
    <!--      <div class="lineI" v-show="open5">-->
    <!--        <div id="I5" class="lineshow"></div>-->
    <!--        <button @click="clickClose" class="noName">关闭</button>-->
    <!--      </div>-->
    <!--      <div class="lineI" v-show="open6">-->
    <!--        <div id="I6" class="lineshow"></div>-->
    <!--        <button @click="clickClose" class="noName">关闭</button>-->
    <!--      </div>-->
    <!--      <div class="lineI" v-show="open7">-->
    <!--        <div id="I7" class="lineshow"></div>-->
    <!--        <button @click="clickClose" class="noName">关闭</button>-->
    <!--      </div>-->
    <!--      <div class="lineI" v-show="open8">-->
    <!--        <div id="I8" class="lineshow"></div>-->
    <!--        <button @click="clickClose" class="noName">关闭</button>-->
    <!--      </div>-->
    <!--      <div class="lineI" v-show="open9">-->
    <!--        <div id="I9" class="lineshow"></div>-->
    <!--        <button @click="clickClose" class="noName">关闭</button>-->
    <!--      </div>-->
    <!--    </div>-->
    <child ref="drawShow" @closeMain="closeMain"></child>

    <el-dialog title="密码验证" :visible.sync="dialogFormVisibleVerifyAuthority">
      <el-form :model="verifyForm" :rules="rules" ref="verifyForm">
        <el-form-item label="验证密码" prop="password">
          <el-input placeholder="请输入密码" v-model="verifyForm.password" show-password></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisibleVerifyAuthority = false">取 消</el-button>
        <el-button type="primary" @click="dialogFormVisibleVerify">确 定</el-button>
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
import {sendMsgToPatient} from "@/api/patient_management/patient_management";
import child from "./child.vue";
import CacheList from "@/views/monitor/cache/list.vue";
import {addOrUpdateTerm, getTerm} from "@/api/staticECG/staticECG";
import {selectDoctor, getDoctorList} from "@/api/statistics/statistics";
// 发送信息时获取密码
import {getlogin_password} from '@/api/jecg4_ecg/jecg4_ecg'
// 获取预警类型选项
import {selectList} from "@/api/log_user/log_user";
import {checkPassword} from "@/utils/verify.js"
// 存储选择的预警类型
import {addReport as addReportyujing} from "@/api/alert_log_count/count";
import {listPatient_management} from "@/api/patient_management/patient_management";

import {getVerify} from "@/api/verify/verify";
// import {checkPassword} from "@/utils/verify.js";

export default {
  name: "index",
  components: {
    CacheList,
    child,
  },
  data() {
    return {
      dialogFormVisibleVerifyAuthority: false,
      verifyForm: {
        password: null,
        status: false
      },

      selectDoctor:[],
      tabsStatus: "userInfo",
      doctorList: [],
      // 上下页需要的信息
      // 查询参数
      // queryParams: {
      //   pageNum: 1,
      //   pageSize: 10,
      //   pId: null,
      //   patientCode: null,
      //   hospitalCode: null,
      //   equipmentCode: null,
      //   connectionTime: null,
      //   patientName: null,
      //   ecgType: null,
      //   PatPhone: null,
      //   diagnosisStatus: null,
      //   intelligentDiagnosis: null,
      //   diagnosisConclusion: null,
      //   diagnosisDoctor: null,
      //   reportTime: null,
      //   ecgLevel: null,
      //   doctorPhone: null,
      //   patientSex:null,
      //   isSelect:'2'
      // },
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
      // 原先提交过的预警类型
      logDataType: "",
      tijiaoshuju: {},
      zhi: [],
      xuanzheyujingleixing: true,
      yujingzhi: [],
      videoVisible: false, //echarts弹出框显示
      markdata: [
        {yAxis: -1},
        {yAxis: -0.5},
        {yAxis: 0},
        {yAxis: 0.5},
        {yAxis: 1},
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
      xianshizifuchuan: "",
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
        hr: "",
        p: "",
        pr: "",
        qrs: "",
        qtc: "",
        hrv: "",
        pId: "",
        patientSymptom: "暂无症状",
        p_xingeng: "", //心梗率
        logid: "",
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
      nArr: [], //导联数据
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
          {required: true, message: "密码不能为空", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    // console.log(this.$route.query.queryParams);
    this.queryParams = this.$route.query.queryParams;
    this.ecgType = this.$route.query.ecgType;
    this.pId = this.$route.query.pId;
    this.getList();
    // this.getPatientdetails()
    // this.getyujingleixing()
  },
  mounted() {
    // 心电数据
    this.get();

    // this.drawgrid();//canvas 画图
    //预警的类型
    // this.getyujingleixing()
  },
  methods: {
    // 密码弹出框点击确认时
    dialogFormVisibleVerify() {
      this.$refs["verifyForm"].validate(valid => {
        if (valid) {
          let objj = {
            password: this.verifyForm.password
          }
          getlogin_password(objj).then(res=>{
            if(res.code == 200){
              this.$modal.msgSuccess("密码正确");
              this.verifyForm.status = true
              this.dialogFormVisibleVerifyAuthority = false
              sessionStorage.setItem('SMSverification',true)
            }else{
              this.$modal.msgSuccess("密码错误请重试");
            }
          })
        }
      })
    },
    /** 切换顶部tabs **/
    switchTabs(value) {
      console.log(value)
    },
    yanzheng(value){
      //todo 将函数当作实参传递
      //checkPassword('abcd',this.yanzheng)
      console.log(value)
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
        this.patient_managementList = response.rows;
        this.total = response.total;
        this.loading = false;
        if (this.queryParams.ecgType === "JECGsingle") {
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
    //选择医生
    selectDoctorChange(e){
      this.data.doctorName = e[1]
    },
    // 患者用户信息
    getPatientdetails() {
      getReportByPId(this.pId).then((response) => {
        this.data.result = response.data.intelligentDiagnosis;
        this.data.resultByDoctor = response.data.diagnosisConclusion;
        this.data.doctorName = response.data.diagnosisDoctor;
        this.data.diagnosisData = response.data.reportTime;
        this.data.pphone = response.data.pphone;
        this.data.pId = response.data.pId;
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
      // 医生的信息
      selectDoctor().then((response) => {
        this.options = response;
      });
      getDoctorList().then(res => {
        let options = []
        let data = res.data.options
        data.forEach(e => {
          if (e.doctorList.length != 0) {
            let hospital = {
              value: e.hospitalCode,
              label: e.hospitalName,
              children: []
            }
            e.doctorList.forEach(doctorInfo => {
              hospital.children.push({label: doctorInfo.doctorName, value: doctorInfo.doctorName})
            })
            options.push(hospital)
          }
        })
        this.doctorList = options;
        console.log('医生信息')
        console.log(this.doctorList)
      })
      this.getyujingleixing();
    },
    //预警类型
    getyujingleixing() {
      selectList().then((res) => {
        this.yujingzhi = res.data;
        if (this.logDataType) {
          this.xianshizifuchuan = this.logDataType;
          this.zhi = this.logDataType.split(",").map((str) => str.trim());
        }
        // 如果没有原先提交过的值，就将自动分析的结果处理后放入预警类型中默认选中
        else {
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
          //    处理后的值放入预警类型中
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
      this.xuanzheyujingleixing = !this.xuanzheyujingleixing;
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

    showChart1() {
      this.open1 = true;
      var pId = this.pId;
      // 找到对应的canvas
      var canvas = $("#1").find("canvas")[0];
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
      var canvas = $("#2").find("canvas")[0];
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
      var canvas = $("#3").find("canvas")[0];
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
      var canvas = $("#4").find("canvas")[0];
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
    //请求数据
    get() {
      // console.log("获得的是那个数据："+ this.pId);
      // this.data =
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
        url: "https://screen.mindyard.cn:84/get_jecg_single_web",
        // asynsc: false,
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({
          pid: this.pId,
        }),
        async: false,
        beforeSend: function (request) {
          // 如果后台没有跨域处理，这个自定义
          request.setRequestHeader("user", "zzu");
          request.setRequestHeader("password", "zzu123");
        },
        success: function (data) {
          // console.log("请求成功：", data)
          loading.close();
          _th.data.resultByDoctor = data.result.diagnosis_conclusion;
          _th.data.doctorName = data.result.diagnosis_doctor;
          _th.data.age = data.result.age;
          _th.data.gender = data.result.gender;
          _th.data.name = data.result.patientName;
          //_th.data.result = data.result.intelligent_diagnosis
          _th.data.hr = data.result.ecg_analysis_data["平均心率"];
          _th.data.hr = data.result.ecg_analysis_data["平均心率"];
          _th.data.p = data.result.ecg_analysis_data["P波时限"];
          _th.data.pr = data.result.ecg_analysis_data["PR间期"];
          _th.data.qrs = data.result.ecg_analysis_data["QRS波时限"];
          _th.data.qtc = data.result.ecg_analysis_data["QTc"];
          _th.data.hrv = data.result.ecg_analysis_data["RMSSD"];
          _th.data.datas = data.result.II;
          _th.datalabel.waveLabel = data.result.waveLabel;
          _th.datalabel.beatLabel = data.result.beatLabel;
          _th.data.p_xingeng = data.result.p_xingeng;
          //console.log("获取到的导联数据", _th.data.datas)
          // console.log("获取到的导联数据长度", _th.data.datas.length)
          _th.nArr = _th.getNewArray(_th.data.datas, 1000);
          // console.log("数据以1000一条分好组", _th.nArr)
          _th.x = [];
          for (var i = 0; i < 1001; i++) {
            _th.x.push(i);
          }
          // console.log( _th.x);
          // _th.markdata = []
          for (var i = 0; i < 1000; i += 20) {
            _th.markdata.push({xAxis: i});
          }
          // console.log( _th.markdata);
          _th.chart1 = echarts.init(document.getElementById("1"));
          _th.chart2 = echarts.init(document.getElementById("2"));
          _th.chart3 = echarts.init(document.getElementById("3"));
          _th.chart4 = echarts.init(document.getElementById("4"));

          _th.chart1.clear();
          _th.chart1.setOption({
            title: {
              text: "",
              top: 5,
              left: 5,
            },
            grid: {
              left: "1",
              right: "1",
              top: "1",
              bottom: "1",
              containLabel: false,
            },
            graphic: _th.graphic1,
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
              data: _th.nArr[0],
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
              text: "",
              top: 5,
              left: 5,
            },
            grid: {
              left: "1",
              right: "1",
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
                data: _th.nArr[1],
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
              text: "",
              top: 5,
              left: 5,
            },
            grid: {
              left: "1",
              right: "1",
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
                data: _th.nArr[2],
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
              text: "",
              top: 5,
              left: 5,
            },
            grid: {
              left: "1",
              right: "1",
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
                data: _th.nArr[3],
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
          $(window).resize(function () {
            _th.chart1.resize();
            _th.chart2.resize();
            _th.chart3.resize();
            _th.chart4.resize();
            _th.addtext();
            console.log(1111111);
          });
          // var chart5 = echarts.init(document.getElementById("5"));
          // chart5.clear()
          // chart5.setOption({
          //   title: {
          //     text: "",
          //     top: 5,
          //     left: 5,
          //   },
          //   grid: {
          //     left: '1',
          //     right: '1',
          //     top: '1',
          //     bottom: '1',
          //     containLabel: false
          //   },
          //   xAxis: {
          //     type: 'category',
          //     data: _th.x,
          //     axisLabel: {
          //       show: false,
          //       interval: 3,
          //     },
          //     axisTick: {
          //       show: false
          //     },
          //     axisLine: {
          //       show: false
          //     },
          //     splitLine: {
          //       show: true, //让网格显示
          //       lineStyle: {//网格样式
          //         color: "#f8bfbf", //网格的颜色
          //         width: 0.5, //网格的宽度
          //         type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
          //         //opacity: 0.6,//透明度
          //       }
          //     }
          //   },
          //   yAxis: {
          //     type: 'value',
          //     axisLabel: {
          //       show: false,
          //     },
          //     axisTick: {
          //       show: false
          //     },
          //     axisLine: {
          //       show: false
          //     },
          //     //  splitNumber: 3, // 横线数
          //     interval: 0.1, // 刻度间隔
          //     splitLine: {
          //       show: true, //让网格显示
          //       lineStyle: {//网格样式
          //         color: "#f8bfbf", //网格的颜色
          //         width: 0.5, //网格的宽度
          //         type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
          //         //opacity: 0.6,//透明度
          //       }
          //     },
          //     max: 1,
          //     min: -1
          //   },
          //   series: [
          //     {
          //       type: 'line',
          //       smooth: true,
          //       showSymbol: false,
          //       data: _th.nArr[4],
          //       z: 5,
          //       lineStyle: {
          //         normal: {
          //           color: "#000000",
          //           width: 1.5
          //         }
          //       },
          //       markLine: {
          //         z: 1,
          //         symbol: "none",
          //         silent: true,
          //         lineStyle: {
          //           type: "solid",
          //           color: '#d77a7a',
          //           width: 1,
          //           //opacity: 0.5,
          //         },
          //         label: {
          //           position: 'start', // 表现内容展示的位置
          //           color: '#d77a7a'  // 展示内容颜色
          //         },
          //         data: _th.markdata
          //       }
          //     }
          //   ],
          // });
          // $(window).resize(function () {
          //   chart5.resize();
          // });
          // var chart6 = echarts.init(document.getElementById("6"));
          // chart6.clear()
          // chart6.setOption({
          //   title: {
          //     text: "",
          //     top: 5,
          //     left: 5,
          //   },
          //   grid: {
          //     left: '1',
          //     right: '1',
          //     top: '1',
          //     bottom: '1',
          //     containLabel: false
          //   },
          //   xAxis: {
          //     type: 'category',
          //     data: _th.x,
          //     axisLabel: {
          //       show: false,
          //       interval: 3,
          //     },
          //     axisTick: {
          //       show: false
          //     },
          //     axisLine: {
          //       show: false
          //     },
          //     splitLine: {
          //       show: true, //让网格显示
          //       lineStyle: {//网格样式
          //         color: "#f8bfbf", //网格的颜色
          //         width: 0.5, //网格的宽度
          //         type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
          //         //opacity: 0.6,//透明度
          //       }
          //     }
          //   },
          //   yAxis: {
          //     type: 'value',
          //     axisLabel: {
          //       show: false,
          //     },
          //     axisTick: {
          //       show: false
          //     },
          //     axisLine: {
          //       show: false
          //     },
          //     //  splitNumber: 3, // 横线数
          //     interval: 0.1, // 刻度间隔
          //     splitLine: {
          //       show: true, //让网格显示
          //       lineStyle: {//网格样式
          //         color: "#f8bfbf", //网格的颜色
          //         width: 0.5, //网格的宽度
          //         type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
          //         //opacity: 0.6,//透明度
          //       }
          //     },
          //     max: 1,
          //     min: -1
          //   },
          //   series: [
          //     {
          //       type: 'line',
          //       smooth: true,
          //       showSymbol: false,
          //       data: _th.nArr[5],
          //       z: 5,
          //       lineStyle: {
          //         normal: {
          //           color: "#000000",
          //           width: 1.5,
          //         }
          //       },
          //       markLine: {
          //         z: 1,
          //         symbol: "none",
          //         silent: true,
          //         lineStyle: {
          //           type: "solid",
          //           color: '#d77a7a',
          //           width: 1,
          //           //opacity: 0.5,
          //         },
          //         label: {
          //           position: 'start', // 表现内容展示的位置
          //           color: '#d77a7a'  // 展示内容颜色
          //         },
          //         data: _th.markdata
          //       }
          //     }
          //   ],
          // });
          // $(window).resize(function () {
          //   chart6.resize();
          // });
          // var chart7 = echarts.init(document.getElementById("7"));
          // chart7.clear()
          // chart7.setOption({
          //   title: {
          //     text: "",
          //     top: 5,
          //     left: 5,
          //   },
          //   grid: {
          //     left: '1',
          //     right: '1',
          //     top: '1',
          //     bottom: '1',
          //     containLabel: false
          //   },
          //   xAxis: {
          //     type: 'category',
          //     data: _th.x,
          //     axisLabel: {
          //       show: false,
          //       interval: 3,
          //     },
          //     axisTick: {
          //       show: false
          //     },
          //     axisLine: {
          //       show: false
          //     },
          //     splitLine: {
          //       show: true, //让网格显示
          //       lineStyle: {//网格样式
          //         color: "#f8bfbf", //网格的颜色
          //         width: 0.5, //网格的宽度
          //         type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
          //         //opacity: 0.6,//透明度
          //       }
          //     }
          //   },
          //   yAxis: {
          //     type: 'value',
          //     axisLabel: {
          //       show: false,
          //     },
          //     axisTick: {
          //       show: false
          //     },
          //     axisLine: {
          //       show: false
          //     },
          //     //  splitNumber: 3, // 横线数
          //     interval: 0.1, // 刻度间隔
          //     splitLine: {
          //       show: true, //让网格显示
          //       lineStyle: {//网格样式
          //         color: "#f8bfbf", //网格的颜色
          //         width: 0.5, //网格的宽度
          //         type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
          //         //opacity: 0.6,//透明度
          //       }
          //     },
          //     max: 1,
          //     min: -1
          //   },
          //   series: [
          //     {
          //       type: 'line',
          //       smooth: true,
          //       showSymbol: false,
          //       data: _th.nArr[6],
          //       z: 5,
          //       lineStyle: {
          //         normal: {
          //           color: "#000000",
          //           width: 1.5,
          //         }
          //       },
          //       markLine: {
          //         z: 1,
          //         symbol: "none",
          //         silent: true,
          //         lineStyle: {
          //           type: "solid",
          //           color: '#d77a7a',
          //           width: 1,
          //           //opacity: 0.5,
          //         },
          //         label: {
          //           position: 'start', // 表现内容展示的位置
          //           color: '#d77a7a'  // 展示内容颜色
          //         },
          //         data: _th.markdata
          //       }
          //     }
          //   ],
          // });
          // $(window).resize(function () {
          //   chart7.resize();
          // });
          // var chart8 = echarts.init(document.getElementById("8"));
          // chart8.clear()
          // chart8.setOption({
          //   title: {
          //     text: "",
          //     top: 5,
          //     left: 5,
          //   },
          //   grid: {
          //     left: '1',
          //     right: '1',
          //     top: '1',
          //     bottom: '1',
          //     containLabel: false
          //   },
          //   xAxis: {
          //     type: 'category',
          //     data: _th.x,
          //     axisLabel: {
          //       show: false,
          //       interval: 3,
          //     },
          //     axisTick: {
          //       show: false
          //     },
          //     axisLine: {
          //       show: false
          //     },
          //     splitLine: {
          //       show: true, //让网格显示
          //       lineStyle: {//网格样式
          //         color: "#f8bfbf", //网格的颜色
          //         width: 0.5, //网格的宽度
          //         type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
          //         //opacity: 0.6,//透明度
          //       }
          //     }
          //   },
          //   yAxis: {
          //     type: 'value',
          //     axisLabel: {
          //       show: false,
          //     },
          //     axisTick: {
          //       show: false
          //     },
          //     axisLine: {
          //       show: false
          //     },
          //     //  splitNumber: 3, // 横线数
          //     interval: 0.1, // 刻度间隔
          //     splitLine: {
          //       show: true, //让网格显示
          //       lineStyle: {//网格样式
          //         color: "#f8bfbf", //网格的颜色
          //         width: 0.5, //网格的宽度
          //         type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
          //         //opacity: 0.6,//透明度
          //       }
          //     },
          //     max: 1,
          //     min: -1
          //   },
          //   series: [
          //     {
          //       type: 'line',
          //       smooth: true,
          //       showSymbol: false,
          //       data: _th.nArr[7],
          //       z: 5,
          //       lineStyle: {
          //         normal: {
          //           color: "#000000",
          //           width: 1.5,
          //         }
          //       },
          //       markLine: {
          //         z: 1,
          //         symbol: "none",
          //         silent: true,
          //         lineStyle: {
          //           type: "solid",
          //           color: '#d77a7a',
          //           width: 1,
          //           //opacity: 0.5,
          //         },
          //         label: {
          //           position: 'start', // 表现内容展示的位置
          //           color: '#d77a7a'  // 展示内容颜色
          //         },
          //         data: _th.markdata
          //       }
          //     }
          //   ],
          // });
          // $(window).resize(function () {
          //   chart8.resize();
          // });
          // var chart9 = echarts.init(document.getElementById("9"));
          // chart9.clear()
          // chart9.setOption({
          //   title: {
          //     text: "",
          //     top: 5,
          //     left: 5,
          //   },
          //   grid: {
          //     left: '1',
          //     right: '1',
          //     top: '1',
          //     bottom: '1',
          //     containLabel: false
          //   },
          //   xAxis: {
          //     type: 'category',
          //     data: _th.x,
          //     axisLabel: {
          //       show: false,
          //       interval: 3,
          //     },
          //     axisTick: {
          //       show: false
          //     },
          //     axisLine: {
          //       show: false
          //     },
          //     splitLine: {
          //       show: true, //让网格显示
          //       lineStyle: {//网格样式
          //         color: "#f8bfbf", //网格的颜色
          //         width: 0.5, //网格的宽度
          //         type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
          //         //opacity: 0.6,//透明度
          //       }
          //     }
          //   },
          //   yAxis: {
          //     type: 'value',
          //     axisLabel: {
          //       show: false,
          //     },
          //     axisTick: {
          //       show: false
          //     },
          //     axisLine: {
          //       show: false
          //     },
          //     //  splitNumber: 3, // 横线数
          //     interval: 0.1, // 刻度间隔
          //     splitLine: {
          //       show: true, //让网格显示
          //       lineStyle: {//网格样式
          //         color: "#f8bfbf", //网格的颜色
          //         width: 0.5, //网格的宽度
          //         type: 'solid', //网格是实线，可以修改成虚线以及其他的类型
          //         //opacity: 0.6,//透明度
          //       }
          //     },
          //     max: 1,
          //     min: -1
          //   },
          //   series: [
          //     {
          //       type: 'line',
          //       smooth: true,
          //       showSymbol: false,
          //       data: _th.nArr[8],
          //       z: 5,
          //       lineStyle: {
          //         normal: {
          //           color: "#000000",
          //           width: 1.5,
          //         }
          //       },
          //       markLine: {
          //         z: 1,
          //         symbol: "none",
          //         silent: true,
          //         lineStyle: {
          //           type: "solid",
          //           color: '#d77a7a',
          //           width: 1,
          //           //opacity: 0.5,
          //         },
          //         label: {
          //           position: 'start', // 表现内容展示的位置
          //           color: '#d77a7a'  // 展示内容颜色
          //         },
          //         data: _th.markdata
          //       }
          //     }
          //   ],
          // });
          // $(window).resize(function () {
          //   chart9.resize();
          // });
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
        var point1 = this.chart1.convertToPixel({seriesIndex: 0}, [
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
        var point2 = this.chart1.convertToPixel({seriesIndex: 0}, [
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
        var point1 = this.chart2.convertToPixel({seriesIndex: 0}, [
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
        var point2 = this.chart2.convertToPixel({seriesIndex: 0}, [
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
        var point1 = this.chart3.convertToPixel({seriesIndex: 0}, [
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
        var point2 = this.chart3.convertToPixel({seriesIndex: 0}, [
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
      console.log("用户电话: " + this.data.pphone);
      let patientPhone = this.data.pphone;
      if (patientPhone.length === 14 || patientPhone.length === 15) {
        patientPhone = patientPhone.substring(0, 11);
      }
      // console.log(patientPhone);
      let SMSverification = sessionStorage.getItem('SMSverification')

      if (this.verifyForm.status || SMSverification) {
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
        this.verifyForm.password = ''
        this.dialogFormVisibleVerifyAuthority = true
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
        .catch((_) => {
        });
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
      if (dataObject.logType != "") {
        addReportyujing(this.tijiaoshuju);
        this.$modal.msgSuccess("数据提交成功");
      } else {
        this.$modal.msgError("数据提交失败，请选择预警类型");
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.updown {
  width: 100%;
  display: flex;
  justify-content: space-around;
}

.wancheng {
  display: flex;
  justify-content: space-between;
}

.body {
  display: flex;
  flex-direction: column;
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
    // align-items: center;
    margin-top: 1.5vh;
    margin-bottom: 1.5vh;
    border-radius: 1vh;
    justify-content: space-between;
    padding: 15px;

    //opacity: 0.6;
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
  -webkit-overflow-scrolling: touch; /* 提高移动设备上的滚动性能 */
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
  // margin: 20px 0 36px 0;
  // margin: 5vh 0 0 0;
  height: 29%;
  display: flex;
  flex-direction: column;
  width: 100%;
  justify-content: center;

  .input {
    display: flex;
    flex-direction: row;
    margin-top: 1vh;

    // margin-left: 2vw;
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
  background-color: #ffffff;
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
  width: 100%;
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
//   line-height: 30px;
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
  margin: 0 auto;
  margin-top: 1.5vh;
  margin-bottom: 1.5vh;
  border-radius: 1vh;
  background-color: #ffffff;
  align-items: center;
  padding: 10px;
  height: 62.5vh;
  overflow: hidden;
  overflow-y: auto;
  -webkit-overflow-scrolling: touch; /* 提高移动设备上的滚动性能 */
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
  -webkit-overflow-scrolling: touch; /* 提高移动设备上的滚动性能 */
  -ms-overflow-style: none;
  scrollbar-width: none;
}

/* 隐藏滚动条但仍可滚动 */
.biaodan::-webkit-scrollbar {
  display: none;
}

// ::v-deep .next {
//   background-color: rgba(255, 255, 255, 0);
//   color: #136d87;
//   border: 1px solid #136d87;
//   // margin: 0;
//   padding: 0 2px;
//   height: 30px;
// }

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
  height: 85%;
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
  max-height: 80px;

  overflow: hidden; /* 内容超出宽度时隐藏超出部分的内容 */
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
  // font-size: 1vw;
}

::v-deep .el-button--success {
  color: #ffffff;
  font-size: 1vw;
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
}

.shangbianju {
  background-color: #ffffff;
  overflow: hidden;
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
  margin: 2vh 0;
}

::v-deep .el-input--medium .el-input__inner {
  font-size: 1vw;
}

.oder ::v-deep .el-button--success {
  padding: 1px;
}

.oder ::v-deep .el-button--success {
  padding: 7px;
}

.oder ::v-deep .next {
  padding-left: 7px;
  padding-right: 7px;
}

::v-deep .el-select-dropdown__item {
  padding: 0 20px;
}

::v-deep .el-tabs {
  display: flex;
  //上下布局
  flex-direction: column;

  .el-tabs__content {
    flex: 1;
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

</style>
