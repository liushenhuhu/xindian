<template>
  <div class="app-container">
    <div style="flex: 1;margin-right: 10px">
      <div class="box">

        <div class="touzuo">
          <div class="touzuobiaoti">
            <div>基本信息</div>
          </div>
              <table class="tabBox">
                <tr>
                  <td>姓名</td>
                  <td v-if="isShowName.status === true">{{ data.name }}</td>
                  <td v-else>{{ hideMiddleName(data.name) }}</td>


                </tr>
                <tr>
                  <td>性别</td>
                  <td>{{ data.gender }}</td>
                </tr>
                <tr>
                  <td>患者病史</td>
                  <td>{{ getMH(dict.type.medical_history) }}</td>
                </tr>
                <tr>
                  <td>报告编码</td>
                  <td>{{ data.pId }}</td>


                </tr>
                <tr>
                  <td>年龄</td>
                  <td>{{ data.age }}</td>
                </tr>
                <tr>
                  <td>患者症状</td>
                  <td>{{ data.patientSymptom }}</td>
                </tr>

              </table>

        </div>

        <div class="touyou">
          <div class="touzuobiaoti">
            <div>医师诊断</div>
            <el-button type="text" @click="dialogVisible"
                       style="padding:0;line-height: 4vh;margin-right: 1vw;font-size:2.5vh">新增术语
            </el-button>
            <el-dialog title="新增术语" :visible.sync="dialogVisibleTag">
              <el-tag
                :key="tag"
                v-for="tag in dynamicTags"
                closable
                :disable-transitions="false"
                @close="handleCloseTag(tag)">
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
              <el-button v-else class="button-new-tag" size="small" @click="showInput">+ 单机新增标签术语</el-button>
              <div slot="footer" class="dialog-footer">
                <el-button @click="dialogVisibleTag=false">取 消</el-button>
                <el-button type="primary" @click="termTag">确 定</el-button>
              </div>
            </el-dialog>

            <el-button type="text" @click="Camera"
                       style="padding:0;line-height: 4vh;margin-right: 1vw;font-size:2.5vh">常用术语
            </el-button>
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
          </div>
          <div class="mt">
            <el-input type="textarea" v-model="data.diagnosisConclusion" placeholder="请输入" :rows="5"
                      class="font">
            </el-input>
          </div>

          <div class="doctor">
            <div class="input yishi">
              <strong>医师:</strong>
              <el-cascader v-model="data.doctorName" disabled :options="doctorList" @change="selectDoctorChange"
                           :show-all-levels="false">
              </el-cascader>
            </div>
            <div class="input">
              <strong>日期:</strong>
              <el-input v-if="data.diagnosisData != null" v-model="data.diagnosisData" clearable
                        style="width: 66%"></el-input>
              <el-input v-else v-model="data.dataTime" clearable ></el-input>
            </div>
          </div>

          <div class="oder">
            <el-button type="success" plain class="anNiu" @click="sendMsg()" v-if="isTrue">发送短信</el-button>
            <el-button type="success" plain class="anNiu" @click="btnUpload" v-if="isTrue">医生诊断</el-button>
          </div>
        </div>

      </div>
    </div>



<!--    PDF -->
    <div style="flex: 2">
      <iframe
        width="100%"
        height="100%"
        allowfullscreen="true"
        :src=src  ref="myFrame">
      </iframe>
    </div>


  </div>
</template>

<script>
import $ from "jquery";
import {getPdf, listDoc, sendMsgToPatient} from "@/api/patient_management/patient_management";
import {getMedicalHistoryByPhone, getWeekReport, updateWeekReport} from "@/api/medicalHistory/medicalHistory";
import { addOrUpdateTerm, getTerm } from "@/api/staticECG/staticECG";
import {
  getCommonTerms,
  addReport,
  getReportByPId,
  updateReport,
  reportEarlyWarningMsg,
} from "@/api/report/report";

export default {
  name: "lookPdf",
  dicts:['medical_history'],
  data() {
    return {
      dynamicTags: ["标签一", "标签二", "标签三"],
      dialogVisibleTag: null,
      items: [], //常用术语
      dialogFormVisible: false,
      arr: [],
      inputValue: "",
      inputVisible: false,
      isSelected: false, //术语按钮没有被按下
      datae: {
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
        patientSymptom: "无",
        hr: "",
        p: "",
        pr: "",
        qrs: "",
        qtc: "",
        hrv: "",
        p_xingeng: "", //心梗率
      },
      // 版本号
      version: "3.8.3",
      TableHeight: 100,
      src: null,
      flagCre:0,
      num:1,
      loading:null,
      isTrue:false,
      data:{
        pId:null,
        pphone:null,
        name:null,
        gender:null,
        age:null,
        patientSymptom:null,
        diagnosisConclusion:null,
        doctorName:null,
        dataTime:null,
        diagnosisData:null,
      },
      tabsStatus: "userInfo",
      isShowName:{
        status:true,
        name:"显示姓名"
      },
      form:{},
      doctorList: [],
    };
  },
  created() {
    this.getPatientdetails()
    this.loading = this.$loading({
      lock: true,
      text: 'Loading',
      spinner: 'el-icon-loading',
      background: 'rgba(0, 0, 0, 0.7)'
    });
    this.TableHeight=document.documentElement.clientHeight || document.bodyclientHeight;
    const iframe = this.$refs.myFrame;
    iframe.contentWindow.scrollTo(0, 0);

  },
  activated(){
    this.loading = this.$loading({
      lock: true,
      text: 'Loading',
      spinner: 'el-icon-loading',
      background: 'rgba(0, 0, 0, 0.7)'
    });
    this.num +=1
    if(this.$route.query.pId!=null&&this.num>2){
      this.getPatientdetails()
      window.scrollTo(0, 0)
      this.src=null
      this.TableHeight=document.documentElement.clientHeight || document.bodyclientHeight;
      const iframe = this.$refs.myFrame;
      iframe.contentWindow.scrollTo(0, 0);
    }

  },

  methods: {
    // 新增术语
    dialogVisible() {
      getTerm().then(r => {
        if (r.rows.length > 0) {
          this.dynamicTags = JSON.parse(r.rows[0].termText)
        }
        this.dialogVisibleTag = true
      })
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
      console.log(index)
      if (index !== -1) {
        this.arr.splice(index, 1);
        this.data.diagnosisConclusion = this.arr.toString();
      } else {
        this.arr.push(key);
        this.data.diagnosisConclusion = this.arr.toString();
        console.log(this.arr)
      }
    },
    dialogForm() {
      this.data.diagnosisConclusion = this.arr.toString();
      this.dialogFormVisible = false;
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
    getPdf(pId,type){
      let _this=this;
      if (type==1){
        $.ajax({
          type: "get",
          url: "https://ecg.mindyard.cn/data/weekpdf/"+_this.data.pphone+"/"+pId+"_md.pdf",
          success: function () {
            _this.src="https://ecg.mindyard.cn/data/weekpdf/"+_this.data.pphone+"/"+pId+"_md.pdf";
            _this.loading.close();
          },
          error: function () {
            this.$message({
              type: 'info',
              message: '正在生成报告，请稍等'
            });
          }
        })
      }else {
        $.ajax({
          type: "get",
          url: "https://ecg.mindyard.cn/data/weekpdf/"+_this.data.pphone+"/"+pId+".pdf",
          success: function () {
            _this.src="https://ecg.mindyard.cn/data/weekpdf/"+_this.data.pphone+"/"+pId+".pdf";
            _this.loading.close();
          },
          error: function () {
            // _this.getJEcgPdf()
            this.$message({
              type: 'info',
              message: '正在生成报告，请稍等'
            });
          }
        })
      }

      this.src=_this.src
    },
    btnUpload(){
      if (this.data.diagnosisConclusion==null||this.data.diagnosisConclusion=='')  {
        this.$message({
          type: 'info',
          message: '请输入诊断内容'
        });
        return
      }
      if (this.data.doctorName==null||this.data.doctorName==''){
        this.$message({
          type: 'info',
          message: '请选择医生'
        });
      }
      console.log("this.data.diagnosisConclusion")
      console.log(this.data.diagnosisConclusion)
      console.log("this.data.doctorName")
      console.log(this.data.doctorName)
      let obj = {
        weekid: this.data.pId,
        diagnosisStatus:'1',
        diagnosisConclusion:this.data.diagnosisConclusion
      }
      console.log(obj)
      updateWeekReport(obj).then(r=>{
        this.$message({
          type: "success",
          message: "诊断成功!",
        });
        this.$router.push({path: '/doctor_diagnose'})
      })
    },
    sendMsg() {
      console.log("用户电话: " + this.data.pphone);
      let patientPhone = this.data.pphone;
      if (patientPhone.length === 14 || patientPhone.length === 15) {
        patientPhone = patientPhone.substring(0, 11);
      }
      console.log(patientPhone);
      let SMSverification = sessionStorage.getItem('SMSverification')
      this.on_off = true
      // if (SMSverification) {
        if (patientPhone) {
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
      // } else {
      //   this.verifyForm.password = ''
      //   this.dialogFormVisibleVerifyAuthority = true
      // }
    },
    getJEcgPdf(){
      let _this=this;
      let obj = {
        pId:this.$route.query.pId
      }
      getPdf(obj).then(res=>{
        this.src = res.msg
        this.loading.close();
      }).catch(err=>{
        this.loading.close();
      })
    },
    isShowNameClick(){

    },
    getMH(zdList, ecgType = this.data.pastMedicalHistory) {
      let str = ''
      if (ecgType) {
        ecgType.split(",").forEach(item => {
          if (this.canConvertToInt(item)) {
            zdList.forEach(zd => {
              if (zd.value == item) {
                str += zd.label + ','
              }
            })
          } else {
            str += item + ','
          }
        })
        if (str.endsWith(",")) {
          str = str.substring(0, str.length - 1)
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


    getPatientdetails(){
      listDoc().then(r => {
        console.log(r)
        this.doctorList = r.data
      })
      let _this = this
      getWeekReport(this.$route.query.id).then(r1=>{
        if (r1.data){
          this.form = r1.data
          this.data.pId = r1.data.weekid
          this.data.pphone = r1.data.patientPhone
          this.data.doctorName = r1.data.doctorPhone
          getMedicalHistoryByPhone(_this.data.pphone).then(r=>{
            this.data.name = r.data.userName
            this.data.gender = r.data.gender
            this.data.pastMedicalHistory = r.data.pastMedicalHistory
            this.data.patientSymptom = r.data.livingHabit
            this.data.age = _this.calculateAge(r.data.birthDay)

            if (r1.data.updateTime){
              // 创建 Date 对象
              const date = new Date(r1.data.updateTime);
              const year = date.getFullYear();
              const month = String(date.getMonth() + 1).padStart(2, '0'); // 月份从 0 开始，需要加 1
              const day = String(date.getDate()).padStart(2, '0');
              const hours = String(date.getHours()).padStart(2, '0');
              const minutes = String(date.getMinutes()).padStart(2, '0');
              const seconds = String(date.getSeconds()).padStart(2, '0');
              _this.data.diagnosisData =`${year}-${month}-${day} ${hours}:${minutes}:${seconds}`
            }
            const date = new Date();
            const year = date.getFullYear().toString().padStart(4, "0");
            const month = (date.getMonth() + 1).toString().padStart(2, "0");
            const day = date.getDate().toString().padStart(2, "0");
            const hour = date.getHours().toString().padStart(2, "0");
            const minute = date.getMinutes().toString().padStart(2, "0");
            const second = date.getSeconds().toString().padStart(2, "0");
            _this.data.dataTime = `${year}-${month}-${day} ${hour}:${minute}:${second}`;
            if (r1.data.diagnosisStatus == 2){
              _this.data.diagnosisConclusion = ''
            }else {
              _this.data.diagnosisConclusion = r1.data.diagnosisConclusion
            }

            _this.isTrue = r1.data.diagnosisStatus == 2 ? true : false
            _this.getPdf( r1.data.weekid,r1.data.diagnosisStatus)
          })
        }else {
          this.$message({
            type: "info",
            message: "数据不存在",
          });
        }

      })


    },



    hideMiddleName(patientName) {
      if (patientName.length <= 1) {
        return "*"; // 一个字的则用一个 * 代替
      } else if (patientName.length === 2) {
        return patientName.charAt(0) + "*"; // 两个字的保留第一个字，后面用 * 代替
      } else {
        // let visibleChars = patientName.charAt(0) + "*".repeat(patientName.length - 2) + patientName.charAt(patientName.length - 1);
        let visibleChars = patientName.charAt(0) + "*".repeat(patientName.length - 1);
        return visibleChars; // 大于两个字的保留第一个字和最后一个字，中间用 * 代替
      }
    },
    selectDoctorChange(e) {
      console.log(e)
      this.data.doctorName = e[1]
    },
    calculateAge(birthDateStr){
      const birthDate = new Date(birthDateStr);

      // 获取当前日期
      const today = new Date();

      // 计算年龄
      let age = today.getFullYear() - birthDate.getFullYear();
      const monthDiff = today.getMonth() - birthDate.getMonth();

      // 如果当前月份小于出生月份，或者当前月份等于出生月份但当前日期小于出生日期，年龄需减一
      if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birthDate.getDate())) {
        age--;
      }

      return age;
    },


  }
}
</script>

<style lang="scss" scoped>
::v-deep .el-descriptions-item__content {
  background-color: #f8f8f8;
  // text-align: center;
}

.chongxie {
  margin-top: 10px;
  display: flex;

  .chongxie_left {
    width: 30%;
    margin-right: 25px;
    border-radius: 5px;

    .chongxie_left_top {
      padding: 10px;
      border-radius: 5px;
      background-color: #ffffff;
    }

    .chongxie_left_botton {
      background-color: #ffffff;
      border-radius: 5px;
      margin-top: 10px;
      padding: 10px;

      .chongxie_left_botton_yujingleixing {
        .chongxie_left_botton_yujingleixing_zhi {
          margin: 10px;
          background-color: #f4f4f4;
          font-size: 1vw;
          border-radius: 5px;
          padding: 10px;
          height: 10vh;
          overflow-y: hidden;
          /* y 轴溢出隐藏 */
          overflow-y: scroll;

          /* 允许向下滚动 */
          ::-webkit-scrollbar {
            display: none;
            /* Chrome Safari */
          }

          -ms-overflow-style: none;
          scrollbar-width: none;
        }

        .chongxie_left_botton_yujingleixing_anniu {
          display: flex;
          justify-content: flex-end;
        }
      }

      .chongxie_left_botton_yishizenduan {
        .chongxie_left_botton_yishizenduan_input {
          padding: 10px;
        }

        .chongxie_left_botton_yishizenduan_fasong {
          font-size: 15px;
        }
      }
    }
  }

  .chongxie_right {
    background-color: #ffffff;
    border-radius: 5px;
    padding: 10px;
  }
}

::v-deep .cc {
  white-space: nowrap;
  /* 禁止换行 */
  overflow: hidden;
  /* 设置溢出隐藏 */
  text-overflow: ellipsis;
  /* 显示省略号 */
  width: 20px;
}






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

  .box {
    width: 100%;
    //display: flex;
    margin-top: 1.5vh;
    margin-bottom: 1.5vh;
    border-radius: 1vh;
    //justify-content: space-between;
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

.el-tag+.el-tag {
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
  // padding: 0 0 3.5px 3.5px;
  width: 25%;
  display: block;
  float: left;
  align-items: center;
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
  font-size: 15px;
  color: #3f4042;
  // font-weight: 700;
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
.tablex tr> :nth-child(odd) {
  /* 样式设置 */
  background-color: #f2f6fe;
}

// .tablex td {
//   height: 8vh;
//   width: 90px;
// }
/* 选择父元素中的偶数子元素 */
.parentElement> :nth-child(even) {
  /* 样式设置 */
}

.wrap {
  background-color: #f4f4f4;
}

.box {
  background-color: #ffffff;
}

.touzuo {
  //width: 66%;
  display: flex;
  flex-direction:column;
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
  align-items: center;
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

  overflow: hidden;
  /* 内容超出宽度时隐藏超出部分的内容 */
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
  //width: 32%;
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
  margin: 3vh 0;
  margin-bottom: 0;
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
    //display: block;
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


// ::v-deep .el-tabs__nav-scroll::after {
//        content: 'aafaf';
//       //  width: 30px;
//        height: 100%;
//        background-color: red;
//        display: inline-block;
//       padding: 10px;
//       cursor: pointer;
//     }
::v-deep .el-tabs__nav {
  z-index: 1;
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
  border: none
}

.xiaoli ::v-deep .el-checkbox.is-bordered.el-checkbox--medium .el-checkbox__label {
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

.wubiankuang ::v-deep .el-input__inner {
  border: 0 solid #dcdfe6;
}

.app-container{
  display: flex;
  padding: 20px 20px 0 20px;
  height: calc(100vh - 104px);
}


.box{
  //height: 100%;
  display: flex;
  flex-direction: column;
  justify-content:space-between;
}









</style>
