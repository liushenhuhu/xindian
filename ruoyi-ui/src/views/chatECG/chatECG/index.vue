<template>
  <div class="container">
    <div class="mainbox">
      <div class="box">
        <div class="title">
          <img src="@/assets/images/chatECG-logo1.png" width="" height="84%">
          <img src="@/assets/images/chatECG-logo-font.png" width="" height="84%">
          <img src="@/assets/images/chatECG-logo3.png" width="" height="40%">
        </div>

        <div id="content" class="content">
          <!--    left      -->
          <div class="left">
            <div class="left-child">
              <textarea
                placeholder="请输入您的问题..."
                style="height: 100%;width: 100%;resize:none;outline: none;border-color:#ccc;border-radius:5px;font-size: 24px"
                id="text"
                v-model="customerText"
                @keyup.enter="sentMsg()"
              ></textarea>
            </div>
            <div class="left-btn">
              <button @click="sentMsg()" style="width: 60%;height: 60%;background-color: rgba(68,114,196);">
                <span style="vertical-align: 3px;font-size: 1vw; color: white">发 送</span>
              </button>
            </div>
          </div>
          <!--    right      -->
          <div class="right" id="right">
            <div v-for="(item,index) in info" :key="index">
              <div class="info_r info_default" v-if="item.type == 'leftinfo'">
              <span class="circle circle_r">
                <img src="@/assets/images/robot.gif" width="40px" height="40px" style="margin-left: 3px" alt/>
              </span>
                <div class="con_r con_text">
                  <div>{{item.content}}</div>
                  <div v-for="(item2,index) in item.question" :key="index">
                    <div class="con_que" @click="clickRobot(item2.content,item2.id)">
                      <div class="czkj-question-msg">
                        {{item2.index}}
                        {{item2.content}}
                      </div>
                    </div>
                  </div>
                </div>
                <div class="time_r">{{item.time}}</div>
              </div>
              <div class="info_l" v-if="item.type == 'rightinfo'">
                <div class="con_r con_text">
                  <span class="con_l">{{item.content}}</span>
                  <span class="circle circle_l">
                  <img src="@/assets/images/logo1.png" class="pic_l" />
                </span>
                </div>
                <div class="time_l">{{item.time}}</div>
              </div>
            </div>


            <div class="info_r info_default" v-if="isLoading">
              <span class="circle circle_r">
                <img src="@/assets/images/robot.gif" width="40px" height="40px" style="margin-left: 3px" alt/>
              </span>
              <div class="con_r con_text">
                <div style="display:flex;height: 35px;align-items: center">
                  <loading/>
                </div>
              </div>
            </div>


          </div>

        </div>
      </div>
    </div>
  </div>
</template>
<script>
import {proxyRequest} from "@/api/chatECG/chatECG";
import loading from "./loading"
export default {
  components: {
    loading
  },
  computed: {},
  data() {
    return {
      isLoading:false,
      customerText: "",
      info: [
        {
          type: "leftinfo",
          time: this.getTodayTime(),
          name: "robot",
          content:
            "您好，我是智能AI医生小郑，请问有什么问题可以帮助您？",
          question: [],
        },
      ],
      timer: null,
      robotQuestion: [],
      robotAnswer: [],
      queryParams: {
        text: "",
        history: "",
      },

    };
  },
  created() {
    this.showTimer();
  },
  watch: {},
  methods: {
    // 用户发送消息
    sentMsg() {
      //console.log("queryParams: ====="+this.queryParams.history);
      clearTimeout(this.timer);
      this.showTimer();
      let text = this.customerText.trim();
      this.queryParams.text = text;
      if (text !== "") {
        var obj = {
          type: "rightinfo",
          time: this.getTodayTime(),
          content: text,
        };
        this.info.push(obj);
        this.customerText = "";
        this.isLoading=true;
        proxyRequest(this.queryParams).then(response => {
          //console.log(response);
          /*if(this.queryParams.history !== "" && this.queryParams.history !== null){

          }*/
          //console.log("history:  ===="+JSON.stringify(response.history));
          this.queryParams.history = JSON.stringify(response.history);
          this.customerText = "";
          let obj = {
            id: 1,
            content: response.response,
            index: 1
          }
          this.robotAnswer.push(obj)
          this.appendRobotMsg(response.response);
          this.isLoading=false;
        })
        this.$nextTick(() => {
          var contentHeight = document.getElementById("right");
          contentHeight.scrollTop = contentHeight.scrollHeight;
        });
      }
    },
    // 机器人回答消息
    appendRobotMsg(text) {
      //console.log(text)
      clearTimeout(this.timer);
      this.showTimer();
      text = text.trim();
      let answerText = "";
      let flag;
      for (let i = 0; i < this.robotAnswer.length; i++) {
        //console.log(this.robotAnswer[i].content)
        if (this.robotAnswer[i].content.indexOf(text) != -1) {
          flag = true;
          answerText = this.robotAnswer[i].content;
          break;
        }
      }
      if (flag) {
        let obj = {
          type: "leftinfo",
          time: this.getTodayTime(),
          name: "robot",
          content: answerText,
          question: [],
        };
        this.info.push(obj);
      } /*else {
        answerText = "您可能想问：";
        let obj = {
          type: "leftinfo",
          time: this.getTodayTime(),
          name: "robot",
          content: answerText,
          question: this.robotQuestion,
        };
        this.info.push(obj);
      }*/
      this.$nextTick(() => {
        var contentHeight = document.getElementById("right");
        contentHeight.scrollTop = contentHeight.scrollHeight;
      });
    },
    sentMsgById(val, id) {
      clearTimeout(this.timer);
      this.showTimer();

      let robotById = this.robotAnswer.filter((item) => {
        return item.id == id;
      });
      let obj_l = {
        type: "leftinfo",
        time: this.getTodayTime(),
        name: "robot",
        content: robotById[0].content,
        question: [],
      };
      let obj_r = {
        type: "rightinfo",
        time: this.getTodayTime(),
        name: "robot",
        content: val,
        question: [],
      };
      this.info.push(obj_r);
      this.info.push(obj_l);
      this.$nextTick(() => {
        var contentHeight = document.getElementById("right");
        contentHeight.scrollTop = contentHeight.scrollHeight;
      });
    },
    // 点击机器人的单个问题
    clickRobot(val, id) {
      this.sentMsgById(val, id);
    },
    // 结束语
    endMsg() {
      let happyEnding = {
        type: "leftinfo",
        time: this.getTodayTime(),
        content: "下次为您服务！",
        question: [],
      };
      this.info.push(happyEnding);
      this.$nextTick(() => {
        var contentHeight = document.getElementById("right");
        contentHeight.scrollTop = contentHeight.scrollHeight;
      });

    },
    showTimer() {
      this.timer = setTimeout(this.endMsg, 10000*60);
    },
    getTodayTime() {
      // 获取当前时间
      var day = new Date();
      let seconds = day.getSeconds();
      if (seconds < 10) {
        seconds = "0" + seconds;
      } else {
        seconds = seconds;
      }
      let minutes = day.getMinutes();
      if (minutes < 10) {
        minutes = "0" + minutes;
      } else {
        minutes = minutes;
      }
      let time =
        day.getFullYear() +
        "-" +
        (day.getMonth() + 1) +
        "-" +
        day.getDate() +
        " " +
        day.getHours() +
        ":" +
        minutes +
        ":" +
        seconds;
      return time;
    },
    /*selectMaxLength() {
      console.log('input')
      if(this.customerText.length > 149){
          this.$message.warning("超过限制字数！");
      }

    }*/
  },
  mounted() {},
  props: {},
  destroyed() {},
};
</script>
<style lang="scss" scoped>
.mainbox {
  width: 100%;
  .box {
    width: 100%;
    height: calc( 100vh - 84px);
    background-color: #ffffff;
    position: relative;
    background-image: url("../../../assets/images/body.png");
    background-repeat:no-repeat;
    background-size:100% 110%;
    background-position: center;
    z-index: 0;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    #content {
      height: calc(100% - 16vh);
      //overflow-y: scroll;
      font-size: 20px;
      width: 98%;
      display: flex;
      justify-content: flex-start;
      .circle {
        display: inline-block;
        width: 45px;
        height: 45px;
        border-radius: 50%;
        background-color: #eff1f3;
        margin-left: 56px;
        margin-top: 6px;
      }
      .con_text {
        color: #333;
        margin-bottom: 5px;
        display: flex;
        justify-content: flex-end;
      }
      .con_que {
        color: #1c88ff;
        height: 30px;
        line-height: 30px;
        cursor: pointer;
      }
      .info_r {
        position: relative;
        margin-top: 2%;
        .circle_r {
          position: absolute;
          left: -1%;
          top: 5%;
        }
        .con_r {
          display: inline-block;
          max-width: 39%;
          //width: 55%;
          min-height: 55px;
          /* min-height: 20px; */
          background-color: #e2e2e2;
          border-radius: 6px;
          padding: 10px;
          margin-left: 100px;
        }
        .time_r {
          margin-left: 100px;
          color: #999999;
          font-size: 24px;
        }
      }
      .info_l {
        text-align: right;
        margin-right: 35px;
        color: #ffffff;
        color: #3163C5;
        .pic_l {
          width: 40px;
          height: 40px;
          margin-top: 6px;
          margin-right: 3px;
        }
        .time_l {
          margin-right: 72px;
          color: #999999;
          font-size: 24px;
          margin-top: 5px;
        }
        .con_l {
          display: inline-block;
          max-width: 30%;
          min-height: 55px;
          background-color: #3163C5;
          border-radius: 6px;
          padding: 10px;
          text-align: left;
          color: #fff;
          margin-right: 5px;
          word-wrap:break-word;
          word-break:break-all;
          //overflow: hidden;/*这个参数根据需求来决定要不要*/
        }
        .circle_l {
          margin-left: 10px;
        }
      }
      #question {
        cursor: pointer;
      }
    }
  }
}
.setproblem {
  width: 100%;
  height: 8vh;
  background-color: #ab2828;
  position: relative;
  //margin-top: -7.3vw;
  z-index: 999;
}
.setproblem textarea {
  color: #999999;
  padding: 10px;
  box-sizing: border-box;
}
.setproblem button {
  width: 5.875rem;
  height: 2.5rem;
  line-height: 2.5rem;
  background: #3163C5;
  opacity: 1;
  border-radius: 4px;
  font-size: 24px;
  color: #ffffff;
  position: absolute;
  right: 5%;
  top: 30%;
  cursor: pointer;
  border: none;
}

.czkj-item-title {
  line-height: 25px;
  border-bottom: 1px solid #ccc;
  padding-bottom: 5px;
  margin-bottom: 5px;
}

.czkj-item-question {
  cursor: pointer;
  display: block;
  padding: 8px;
  position: relative;
  border-bottom: 1px dashed #ccc;
  line-height: 20px;
  min-height: 20px;
  overflow: hidden;
}

.czkj-question-msg {
  float: left;
  font-size: 24px;
  color: #3163C5;
}
.title {
  width: 98%;
  //position: absolute;
  margin: 0 auto !important;
  //line-height: 2%;
  //text-align: center;
  //left: 45%;
  height: 12vh;
  font-size: 32px;
  border: 4px rgba(47,82,143) solid;
  display: flex;
  align-items: center;
  img {
    margin: 0 30px;
  }
}
.title-hn {
  display: inline-block;
  //vertical-align: middle;
  //float: right;
  //margin-right: 46%;
}
.left {
  width: 30%;
  border: 3px rgba(47,82,143) solid;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
}
.left-child {
  width: 90%;
  height: 80%;
  border: 2px rgba(47,82,143) solid;
  margin-top: 5%;
}
.right {
  width: 70%;
  border: 3px rgba(47,82,143) solid;
  overflow-y: scroll;
}

.left-btn {
  width: 100%;
  height: 10%;
  //border: 2px pink solid;
  display: flex;
  justify-content: space-around;
}

</style>
