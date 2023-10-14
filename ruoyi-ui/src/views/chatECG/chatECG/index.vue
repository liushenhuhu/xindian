<template>
    <div class="mainbox">
      <div class="box">
        <div id="content" class="content">
        <!--    left      -->
          <div class="left">
            <div class="new">
              <div class="img_box">
                <img class="zhengda" src="@/assets/images/zhengda.png"/>
              </div>
              <div class="new_his" @click="conversationClickAdd" style="cursor: pointer">+&emsp;&emsp;新建对话框</div>
            </div>
            <div class="his">
              <div class="his_item" style="cursor: pointer" v-for="item in conversation" :key="item.conversationId" @click="conversationClickCut(item.conversationId)">
                <img class="mesimg2" src="@/assets/images/messge2.png"/>
                <div class="his_title"  >
                  <div class="tit" v-show="!item.show">{{item.title}}</div>
                  <el-input :ref="'input'+item.conversationId" :focus="focusStatus" v-show="item.show" type="text" v-model="item.title" @blur="blurId(item.conversationId)"></el-input>
                  <div class="time">{{item.createTime}}</div>
                </div>
                <img class="upimg" src="@/assets/images/updat.png" style="cursor: pointer" @click="updatatit(item.conversationId)"/>
                <img class="delimg" src="@/assets/images/delimg.png" style="cursor: pointer" @click.stop="conversationClickDel(item.conversationId)"/>
              </div>
<!--              <div class="his_item">-->
<!--                <img class="mesimg2" src="@/assets/images/messge2.png"/>-->
<!--                <div class="his_title">-->
<!--                  <div class="tit">新建会话1</div>-->
<!--                  <div class="time">2023-10-13 10:09:36</div>-->
<!--                </div>-->
<!--                <img class="delimg" src="@/assets/images/delimg.png"/>-->
<!--              </div>-->
            </div>
          </div>
        <!--    right      -->
          <div class="right" >
            <div class="right-title">
              <img class="xietong" src="@/assets/images/xietong.png"/>
            </div>
            <div class="message" id="right" ref="message">
              <div v-for="(item,index) in info" :key="index">
                <div class="info_r" v-if="item.type == 'leftinfo'">
                  <img src="@/assets/images/rotoimg.png" class="pic_ro" alt/>
                  <div class="con_r">
                    <div class="time_r">{{item.time}}</div>
                    <div class="con_text">{{item.content}}</div>
                    <div v-for="(item2,index) in item.question" :key="index">
                      <div class="con_que" @click="clickRobot(item2.content,item2.id)">
                        <div class="czkj-question-msg">
                          {{item2.index}}
                          {{item2.content}}
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="info_l" v-if="item.type == 'rightinfo'">
                  <div class="con_l">
                    <div class="time_l">{{item.time}}</div>
                    <span class="con_text">{{item.content}}</span>
                  </div>
                  <img src="@/assets/images/userimg.png" class="pic_l" />
                </div>
              </div>
              <div class="info_r" v-if="isLoading">
                <img src="@/assets/images/rotoimg.png" class="pic_ro" alt/>
                <div class="con_r con_text">
                  <div style="display:flex;height: 35px;align-items: center">
                    <loading/>
                  </div>
                </div>
              </div>
            </div>
            <div class="text-area">
              <div class="left-child">
                <img class="mesimg1" src="@/assets/images/messge1.png"/>
                <textarea
                  placeholder="请输入您的问题..."
                  style="height: 100%;width: 85%;resize:none;outline: none;border:0;font-size: 1.3vw;padding: 1vh"
                  id="text"
                  v-model="customerText"
                  @keyup.enter="sentMsg()"
                ></textarea>
                <img class="mesimg3" src="@/assets/images/messge3.png" @click="sentMsg()"/>
              </div>
            </div>

          </div>

        </div>
      </div>
    </div>
</template>
<script>
import {deleteConversation, getChatQuizList, getConversation, proxyRequest} from "@/api/chatECG/chatECG";
import loading from "./loading"
import {delDoctor} from "@/api/doctor/doctor";
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
        conversationId:1,
        createTime:null,
        title:null,
      },
      conversation:[],
      titleUpdata:false,
      title:'',
      focusStatus:false
    };
  },
  created() {
    this.getConversation(1)
    this.showTimer();
  },
  watch: {},
  methods: {
    formatDateToCustomFormat(date) {
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, '0');
      const day = String(date.getDate()).padStart(2, '0');
      const hours = String(date.getHours()).padStart(2, '0');
      const minutes = String(date.getMinutes()).padStart(2, '0');
      const seconds = String(date.getSeconds()).padStart(2, '0');

      return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
    },
    // 用户发送消息
    sentMsg() {
      //console.log("queryParams: ====="+this.queryParams.history);
      clearTimeout(this.timer);
      this.showTimer();
      let text = this.customerText.trim();
      this.queryParams.text = text;
      this.queryParams.createTime = this.formatDateToCustomFormat(new Date())
      if (this.info.length===1){
        this.queryParams.conversationId = this.conversation.length+1
        this.queryParams.title = text
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
            this.queryParams.history = JSON.stringify(response.history);
            this.customerText = "";
            let obj = {
              id: 1,
              content: response.response,
              index: 1
            }
            this.robotAnswer.push(obj)
            this.appendRobotMsg(response);
            this.isLoading=false;
          })
          this.$nextTick(() => {
            var contentHeight = document.getElementById("right");
            contentHeight.scrollTop = contentHeight.scrollHeight;
          });
        }
        console.log("开始调用")
        let chat = {
        conversationId:this.queryParams.conversationId,
        createTime:this.queryParams.createTime,
        title: this.queryParams.title
        }
        this.queryParams.title = null
        this.conversation.unshift(chat)
        // setTimeout(()=>{this.getConversation(2)}, 5000)
      }else {
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
            console.log(response)
            this.queryParams.history = JSON.stringify(response.history);
            this.customerText = "";
            let obj = {
              id: 1,
              content: response.response,
              index: 1
            }
            this.robotAnswer.push(obj)
            this.appendRobotMsg(response);
            this.isLoading=false;
          })
          this.$nextTick(() => {
            var contentHeight = document.getElementById("right");
            contentHeight.scrollTop = contentHeight.scrollHeight;
          });
        }
      }

    },
    // 机器人回答消息
    appendRobotMsg(text) {
      clearTimeout(this.timer);
      this.showTimer();
      text.response = text.response.trim();
      let answerText = "";
      let flag;
      for (let i = 0; i < this.robotAnswer.length; i++) {
        //console.log(this.robotAnswer[i].content)
        if (this.robotAnswer[i].content.indexOf(text.response) != -1) {
          flag = true;
          answerText = this.robotAnswer[i].content;
          break;
        }
      }
      if (flag) {
        let obj = {
          type: "leftinfo",
          time: text.responseTime,
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
      console.log(obj_r)
      console.log(obj_l)
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


    getConversation(v){
      getConversation().then(r=>{
        console.log(r)
        this.conversation = r.data
        this.conversation.forEach(item=>{
          Object.assign(item,{show:false})
        })
        console.log(v)
        if (v===1){
          if (r.data.length>0){
            this.conversationClickCut(r.data[0].conversationId)
          }
          if (r.data.length===0){
            this.info=[
              {
                type: "leftinfo",
                time: this.getTodayTime(),
                name: "robot",
                content:
                  "您好，我是智能AI医生小郑，请问有什么问题可以帮助您？",
                question: [],
              },
            ]
            this.robotAnswer=[]
          }
        }
      })
    },

    /**
     * 删除用户的会话窗口
     * @param val
     */
    conversationClickDel(val){
      this.$modal.confirm('是否确认删除会话窗口编号为"' + val + '"的数据项？').then(function() {
        return deleteConversation(val);
      }).then(() => {
        this.getConversation(1)
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});

    },

    /**
     * 查询用户指定的会话窗口的历史数据
     * @param val
     */
    conversationClickCut(val){
      this.queryParams.conversationId=val

      getChatQuizList(val).then(r=>{
        this.info=[]
        for (let i = 0; i < r.data.length; i++) {

          let label = JSON.parse(r.data[i].messageContent).label
          let value = JSON.parse(r.data[i].messageContent).value

          let obj_l = {
            type: "leftinfo",
            time: r.data[i].responseTime,
            name: "robot",
            content: value,
          };
          let obj_r = {
            type: "rightinfo",
            time: r.data[i].createTime,
            name: "robot",
            content: label,
          };
          this.info.push(obj_r);
          this.info.push(obj_l);
        }
        this.$nextTick(() => {
          this.$refs.message.scrollTop = this.$refs.message.scrollHeight;
        })
      })
    },

    /**
     * 新增窗口
     */
    conversationClickAdd(){
      this.getConversation(2)
      this.info=[
        {
          type: "leftinfo",
          time: this.getTodayTime(),
          name: "robot",
          content:
            "您好，我是智能AI医生小郑，请问有什么问题可以帮助您？",
          question: [],
        },
        ]
      this.robotAnswer=[]

    },
    //点击修改图标显示input
    updatatit(id){
      this.conversation.forEach((i,index)=>{
        if(i.conversationId==id){
          i.show=true
          this.title=i.title
        }
      })
      console.log(this.conversation)
      this.$nextTick(()=>{
        this.focusStatus=true
        this.$refs[`input${id}`][0].focus()
      })
      console.log(this)

    },
    //input失去焦点
    blurId(id){
      console.log(11111111111111)
      this.conversation.forEach(i=>{
        if(i.conversationId==id){
          i.show=false
          this.title=''
        }
      })
    }
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
    //background-image: url("../../../assets/images/body.png");
    //background-repeat:no-repeat;
    //background-size:100% 110%;
    //background-position: center;
    background-color: #000000;
    z-index: 0;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    #content {
      height: 90%;
      //overflow-y: scroll;
      font-size: 20px;
      width: 95%;
      display: flex;
      .con_text {
        width: fit-content;
        color: #333;
        margin-bottom: 5px;
        display: flex;
        justify-content: flex-end;
        background-color: #e2e2e2;
        border-radius: 6px;
        padding: 10px;
      }
      .con_que {
        color: #1c88ff;
        height: 30px;
        line-height: 30px;
        cursor: pointer;
      }
      .info_r {
        display: flex;
        align-items: center;
        margin-top: 1vh;
        .pic_ro{
          width: 3vw;
          height: 6vh;
          margin-left: 6vw;
        }
        .con_r {
          display: inline-block;
          max-width: 60%;
          //width: 55%;
          min-height: 55px;

          /* min-height: 20px; */
        }
        .time_r {
          text-align: right;
          //margin-left: 5vw;
          color: #5B5B5B;
          font-size: 18px;
        }
      }
      .info_l {
        margin-right: 5vw;
        margin-top: 1vh;
        display: flex;
        align-items: center;
        justify-content: right;
        color: #ffffff;
        .pic_l {
          width: 3vw;
          height: 6vh;
          margin-top: 2vh;
        }
        .time_l {
          text-align: left;
          color: #5B5B5B;
          font-size: 18px;
        }
        .con_l {
          display: inline-block;
          max-width: 60%;
          min-height: 20%;
          border-radius: 6px;
          text-align: left;
          color: #fff;
          margin-right: 5px;
          word-wrap:break-word;
          word-break:break-all;
          //overflow: hidden;/*这个参数根据需求来决定要不要*/
          .con_text {
            width: fit-content;
            color: #333;
            margin-bottom: 5px;
            display: flex;
            justify-content: flex-end;
            background-color: #e2e2e2;
            border-radius: 6px;
            padding: 10px;
            float: right;
          }
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
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  font-size: 1.3vw;
  color: #FFFFFF;
  border: 2px solid #9A9999;
  margin-right: 3vw;
  .new{
    height: 9%;
    width: 100%;
    display: flex;
    align-items: center;
    .img_box{
      background-color: #FFFFFF;
      border-radius: 50%;
      display: flex;
      justify-content: center;
      align-items: center;
      margin-left: 1vw;
      .zhengda{
        width: 3.5vw;
        height: 7vh;
      }
    }
    .new_his{
      flex: 1;
      height: 100%;
      margin-left: 1vw;
      margin-right: 1vw;
      border: 2px solid #9A9999;
      border-radius: 1vw;
      padding: 2.3vh 1vw;
    }
  }
  .his{
    height: 85%;
    display: flex;
    flex-direction: column;
    align-items: center;
    overflow: hidden;
    overflow-y: scroll;
    user-select:none;
    .his_item{
      box-sizing:border-box;
      display: flex;
      align-items: center;
      height: 20%;
      width: 90%;
      border: 2px solid #9A9999;
      border-radius: 1vw;
      margin-top: 2vh;
      .mesimg2{
        margin-left: 1vw;
        width: 2.5vw;
        height: 2.2vw;
      }
      .his_title{
        width: 14vw;
        margin: 0 1vw 0 0.5vw;
        .tit{
          margin: 1vh 0 0.5vh 0;
          overflow: hidden;
        }
        .time{
          font-size: 0.9vw;
          color: #ABABAB;
        }
        ::v-deep .el-input--medium .el-input__inner{
          font-size: 1.3vw;
          height: auto;
          line-height: 0;
          padding: 2px 0 2px 0;
        }
      }
      .delimg{
        margin-right: 1vw;
        width: 1.8vw;
        height: 1.8vw;
      }
      .upimg{
        width: 1.8vw;
        height: 1.8vw;
      }
    }
  }

}
.left-child {
  display: flex;
  width: 83%;
  height: 85%;
  border: 2px #DFDFDF solid;
  margin:auto;
  position: relative;
  border-radius: 1vw;
}
.left-child .mesimg1{
  width: 2.5vw;
  height: 2.2vw;
  margin: 0.8vh 1vw 0 0.5vw;
}
.left-child .mesimg3{
  width: 2.5vw;
  height: 2.2vw;
  position: absolute;
  bottom: 0.5vh;
  right: 0.5vw;
}
.right {
  width: 70%;
  background-color: #FFFFFF;
  border-radius: 1.5vw;
  overflow: hidden;
}
.right .right-title{
  height: 12%;
}
.right .right-title .xietong{
  height: 8vh;
  width: 20vw;
  margin: 2vh 0 0 1vw;
}
.right .message{
  height: 65%;
  background-color: #FFFFFF;
  overflow-y: scroll;
}
.right .text-area{
  height: 23%;
  width: 100%;
}
.left-btn {
  width: 100%;
  height: 10%;
  //border: 2px pink solid;
  display: flex;
  justify-content: space-around;
}
//滑动条
*::-webkit-scrollbar {
  width: 0.7vw;
  height: 4px;
  background: transparent;
}

*::-webkit-scrollbar-thumb {
  background: transparent;
  border-radius: 4px;
}

*:hover::-webkit-scrollbar-thumb {
  background: hsla(0, 1%, 53%, 0.4);
}

*:hover::-webkit-scrollbar-track {
  background: transparent;
}
</style>
