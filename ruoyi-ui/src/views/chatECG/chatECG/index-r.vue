<template>
  <div class="mainbox">
    <div class="box">
      <div id="content" class="content">
        <!--    left      -->
        <div class="left">
          <div class="new">
            <div class="img_box">
              <img class="zhengda" src="@/assets/images/zhengda.png" />
            </div>
            <!--            <div class="new_his" @click="conversationClickAdd" style="cursor: pointer">-->
            <div
              class="new_his"
              @click="conversationClickAdd"
              :style="{ cursor: !iptDisabled ? 'pointer' : 'not-allowed' }"
            >
              +&emsp;&emsp;新建
            </div>
            <div
              class="new_his"
              @click="empty"
              :style="{ cursor: !iptDisabled ? 'pointer' : 'not-allowed' }"
            >
              清空
            </div>
          </div>
          <div class="his">
            <!-- 对话框 -->
            <div
              class="his_item"
              :style="{ cursor: !iptDisabled ? 'pointer' : 'not-allowed' }"
              v-for="(item, i) in conversation"
              :ref="'div-' + item.conversationId"
              :class="{ bgc: i === 0 }"
              :key="item.conversationId"
              @click="conversationClickCut(item.conversationId, 2)"
            >
              <!-- <img class="mesimg2" src="@/assets/images/messge2.png"/> -->
              <div class="his_title">
                <div
                  class="tit"
                  v-show="item.isCustom"
                  :style="{ cursor: !iptDisabled ? 'pointer' : 'not-allowed' }"
                >
                  {{ item.title }}
                </div>
                <input
                  :class="{ titUp: item.isHighlighted, tit: item.isCustom }"
                  :ref="'input' + item.conversationId"
                  type="hidden"
                  :value="item.title"
                  @blur="blurId(item.conversationId)"
                />
                <div class="time">{{ item.createTime }}</div>
              </div>
              <div style="display: flex">
                <img
                  class="upimg"
                  src="@/assets/images/updat.png"
                  :style="{ cursor: !iptDisabled ? 'pointer' : 'not-allowed' }"
                  @click.stop="updatatit(item.conversationId, i)"
                />
                <img
                  class="delimg"
                  src="@/assets/images/delimg.png"
                  :style="{ cursor: !iptDisabled ? 'pointer' : 'not-allowed' }"
                  @click.stop="conversationClickDel(item.conversationId)"
                />
              </div>
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
        <div class="right">
          <div class="right-title">
            <img class="xietong" src="@/assets/images/xietong.png" />
            <div class="right-title-button">
              <span>静音<i class="el-icon-close-notification" /></span>&nbsp;
              <span>
                <el-switch
                  v-model="audioPlayState"
                  active-color="#13ce66"
                  inactive-color="#ccc"
                >
                </el-switch>
              </span>
            </div>
          </div>
          <div class="message" id="right" ref="message">
            <div v-for="(item, index) in info" :key="index">
              <!-- AI回答 -->
              <div class="info_r" v-if="item.type == 'leftinfo'">
                <img src="@/assets/images/rotoimg.png" class="pic_ro" alt />
                <div class="con_r">
                  <div class="time_r">{{ item.time }}</div>
                  <div class="con_text">{{ item.content }}</div>
                  <div v-for="(item2, index) in item.question" :key="index">
                    <div
                      class="con_que"
                      @click="clickRobot(item2.content, item2.id)"
                    >
                      <div class="czkj-question-msg">
                        {{ item2.index }}
                        {{ item2.content }}
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="info_l" v-if="item.type == 'rightinfo'">
                <div class="con_l">
                  <div class="time_l">{{ item.time }}</div>
                  <span class="con_text">{{ item.content }}</span>
                </div>
                <img src="@/assets/images/userimg.png" class="pic_l" />
              </div>
            </div>
            <div class="info_r" v-if="isLoading">
              <img src="@/assets/images/rotoimg.png" class="pic_ro" alt />
              <div class="con_r con_text">
                <div style="display: flex; height: 35px; align-items: center">
                  <loading />
                </div>
              </div>
            </div>
          </div>
          <div class="text-area">
            <div class="left-child">
              <img class="mesimg1" src="@/assets/images/messge1.png" />
              <!--  -->
              <textarea
                :disabled="iptDisabled"
                placeholder="请输入您的问题..."
                style="
                  height: 100%;
                  width: 85%;
                  resize: none;
                  outline: none;
                  border: 0;
                  font-size: 1.3vw;
                  padding: 1vh;
                "
                id="text"
                v-model="customerText"
                @keyup.enter="sentMsg()"
              ></textarea>
              <div class="right-child">
                <div v-if="vocState == 0" class="mkf">
                  <img
                    v-if="iptDisabled"
                    class="mesimg3"
                    src="@/assets/images/microphone-0.png"
                  />
                  <!--                  开始录音-->
                  <img
                    v-else
                    class="mesimg3"
                    src="@/assets/images/microphone-0.png"
                    @click="recorderStart()"
                  />
                </div>
                <div v-else-if="vocState == 1" class="mkf mkf-s">
                  <!--                  停止录音-->
                  <img
                    class="mesimg3"
                    src="@/assets/images/microphone-1.png"
                    @click="recorderStop()"
                  />
                  <div class="mkftips" v-show="resultState">倾听中...</div>
                </div>
                <img
                  class="mesimg3"
                  src="@/assets/images/messge3.png"
                  @click="sentMsg()"
                />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import {
  deleteConversation,
  getChatQuizList,
  getConversation,
  proxyRequest,
  updateConversation,
} from "@/api/chatECG/chatECG";
import loading from "./loading";
import { delDoctor } from "@/api/doctor/doctor";
import Recorder from "js-audio-recorder";
import { Voc, PPlayer } from "@/utils/voice.js";
import $ from "jquery";
export default {
  name: "chatECG",
  components: {
    loading,
  },
  data() {
    return {
      iptDisabled: false,
      isLoading: false,
      customerText: "",
      info: [
        {
          type: "leftinfo",
          time: this.getTodayTime(),
          name: "robot",
          content: "您好，我是智能AI医生小雅，请问有什么问题可以帮助您？",
          question: [],
        },
      ],
      timer: null,
      robotQuestion: [],
      robotAnswer: [],
      queryParams: {
        text: "",
        history: "",
        conversationId: 1,
        createTime: null,
        title: null,
      },
      conversation: [],
      titleUpdata: false,
      title: "",
      focusStatus: false,
      hasBorderlessClass: true,
      isAddNewWin: false,
      recorder: null,
      voc: null,
      vocState: 0,
      audioPlayer: null,
      resultTextTemp: "",
      resultText: "",
      resultState: 0,
      audioLock: null,
      msgOverLock: null,
      newText: "",
      textInterval: null,
      audioPlayState: false,
      currentTime: 10000,
      isRead: false, //是否读完
      timeOut: null,
      readText: "",
      resultXun: false, //是否循环完
      audioDom: null,
    };
  },
  computed: {
    inputClasses() {
      // 使用计算属性动态生成类列表
      return {
        tit: this.hasBorderlessClass,
        titUp: !this.hasBorderlessClass, // 新添加的类
      };
    },
  },
  created() {
    // this.getConversation(1);
    this.conversationClickAdd();
    this.showTimer();
  },

  mounted() {
    console.log("chunkText: ", "123\n45");
    var that = this;
    // this.getStream()
    // console.log('process.env.port: ', process.env.VUE_APP_BASE_API);

    this.audioPlayer = new PPlayer();
    // console.log('this.audioPlayer: ', this.audioPlayer);

    this.audioDom = document.querySelector("audio");
    // var audioDomAll = document.querySelectorAll('audio')
    // console.log('audioDom: ', audioDomAll);

    this.audioDom.addEventListener("timeupdate", function () {
      // 当前播放时间发生改变时的操作
      // console.log('音频正在播放，当前播放时间：' + that.currentTime);
      if (that.currentTime == that.audioDom.currentTime) {
        console.log("播放结束");
        that.isRead = true;
      } else {
        that.currentTime = that.audioDom.currentTime;
        that.isRead = false;
      }
    });

    this.getMsg();
  },
  beforeDestroy() {
    var th = this;
    console.log("即将销毁");
    clearInterval(th.timeOut);
    this.audioDom.remove();
    this.endAll();
  },
  //keep-alive生命周期
  activated() {
    console.log(123);
  },
  deactivated() {
    console.log(456);
  },
  watch: {
    audioPlayState(newValue) {
      if (newValue) {
        this.audioPlayer.stopAudio();
      }
    },
  },

  methods: {
    daayin() {
      console.log("1");
    },
    // console.log('process.env.port: ', process.env.VUE_APP_CHAT);
    async getStream(data, lock, th) {
      var that = this;
      try {
        var params = {
          query: data.text,
          history: data.history != "" ? JSON.parse(data.history) : [],
        };
        let response = await fetch(
          process.env.VUE_APP_CHAT + "/knowledge_base_chat",
          {
            method: "post",
            headers: { "content-type": "application/json" },
            mode: "cors",
            // cache: "no-store",
            credentials: "include",
            body: JSON.stringify({
              ...params,
              knowledge_base_name: "samples",
              top_k: 5,
              score_threshold: 0.5,
              // cache: "no-store",
              stream: true,
              model_name: "Qwen-14B-Chat",
              temperature: 0.8,
              max_tokens: 0,
              prompt_name: "default",
            }),
          }
        );
        console.log("response", response);

        if (!response.ok) {
          throw new Error("Network response was not ok");
        }

        const reader = response.body.getReader();

        const textDecoder = new TextDecoder();
        let result = true;
        let output = "";
        let obj = {
          type: "leftinfo",
          time: "",
          name: "robot",
          original: "",
          content: "",
          question: [],
        };
        this.isLoading = false;
        this.info.push(obj);
        var flag = true;
        var readText = "";
        while (result) {
          const { done, value } = await reader.read();
          // console.log("value: ", value);

          if (done) {
            console.log("Stream ended");
            result = false;
            this.resultXun = true;
            break;
          }

          const chunkText = textDecoder.decode(value);
          console.log("chunkText: ", chunkText.split("\n"));
          // console.log("chunkText: ", "123<br>345");
          var lineText = "";
          chunkText.split("\n").forEach((item) => {
            // console.log("item: ", item);
            if (item != "" && item != "\r") {
              let txtVal = item
                .split(":")[2]
                .split("}")[0]
                .replace(/"/g, "")
                .replace(/\\n/g, "")
                .replace(/\[<span style='color/g, "");
              // console.log("txtVal111: ", txtVal);
              lineText += txtVal.trim();
            }
          });
          // let txtVal = chunkText
          //   .split(":")[2]
          //   .split("}")[0]
          //   .replace(/"/g, "")
          //   .replace(/\\n/g, "")
          //   .replace(/\[<span style='color/g, "");
          // console.log("chunkText: ", txtVal);
          this.appendRobotMsg(lineText);

          output += lineText.trim();
          // console.log("txtVal.trim(): ", lineText.trim());
          // readText += txtVal.trim();
          this.newText += lineText.trim();
          if (output.length > 10 && flag) {
            this.audioPlayer.send(output);
            flag = false;
            readText = output;
            this.readText = readText;
            // console.log('readText: ', readText);
          } else if (this.isRead) {
            let read = readText;
            // console.log('read: ', read);
            this.audioPlayer.send(output.substring(read.length, output.length));
            readText += output.substring(read.length, output.length);
            this.readText = readText;
            // this.resultXun=false
          }
        }
        this.iptDisabled = false;
        var that = this;
        this.info[this.info.length - 1].original += output;
        // console.log("Received chunk:", output);
        this.resultXun = true;
        if (readText.length == output.length) {
        } else {
          this.timeOut = setInterval(() => {
            // console.log("--123---", that.isRead)
            if (that.isRead) {
              // console.log('output.substring(readText.length, output.length): ', output.substring(readText.length, output.length));
              clearInterval(that.timeOut);
              that.timeOut = null;
              this.audioPlayer.send(
                output.substring(readText.length, output.length)
              );
            }
          }, 10);
        }

        proxyRequest({
          history: output,
          conversationId: this.queryParams.conversationId,
          text: this.queryParams.text,
          title: this.queryParams.title,
        });
      } catch (e) {
        console.log(e);
      }
    },

    getMsg(data, lock, th) {
      console.log("data: ", data);
      this.getStream(data);
      var params = {
        prompt: data.text,
        history: data.history != "" ? JSON.parse(data.history) : [],
      };

      console.log("params: ", JSON.stringify(params));

      $.ajax({
        type: "post",
        url: "/api",
        // asynsc: false,
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify(params),
        // {
        //   prompt: '你好',
        //   history: [{ role: "user", content: "你好" }, { role: "assistant", content: "你好，我是心电AI医生问答模型，一个由郑州大学互联网医疗与健康服务河南省协同创新中心开发的人工智能助手。很高兴为您服务。" }]
        // }
        success: function (response) {
          console.log("response: ", response);
          th.queryParams.history = JSON.stringify(response.history);
          th.customerText = "";
          let obj = {
            id: 1,
            content: response.response,
            index: 1,
          };
          console.log("----5----");
          th.robotAnswer.push(obj);
          // th.appendRobotMsg(response);
          console.log("----6----");
          if (th.audioLock === lock) {
            th.isLoading = false;
            if (th.msgOverLock === lock) {
              if (!th.audioPlayState) {
                th.audioPlayer.send(response.response);
              }
            }
          }
        },
      });
    },
    getMsg1(data, th) {
      this.getStream(data);
      var params = {
        prompt: data.text,
        history: data.history != "" ? JSON.parse(data.history) : [],
      };
      console.log("----------params----: ", JSON.stringify(params));

      $.ajax({
        type: "post",
        url: "/api",
        // asynsc: false,
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify(params),
        // {
        //   prompt: '你好',
        //   history: [{ role: "user", content: "你好" }, { role: "assistant", content: "你好，我是心电AI医生问答模型，一个由郑州大学互联网医疗与健康服务河南省协同创新中心开发的人工智能助手。很高兴为您服务。" }]
        // }
        success: function (response) {
          console.log(response);
          th.queryParams.history = JSON.stringify(response.history);
          th.customerText = "";
          let obj = {
            id: 1,
            content: response.response,
            index: 1,
          };
          th.robotAnswer.push(obj);
          console.log(
            "这是++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++" +
              this.robotAnswer
          );
          // th.appendRobotMsg(response);
          th.isLoading = false;
          if (!th.audioPlayState) {
            console.log("还是走了这里？");
            th.audioPlayer.send(response.response);
          }
        },
      });
    },
    endAll() {
      let th = this;
      try {
        console.log("清除一切");
        th.audioPlayer.stopAudio();
        if (this.recorder) {
          th.recorderStop();
        }
      } catch (e) {
        console.log("错误");
        console.log(e);
      }
    },

    recorderStart() {
      clearInterval(this.timeOut);
      // 1
      let ts = new Date().getTime() / 1000;
      ts = parseInt(ts);
      let th = this;
      this.voc = new Voc();
      // 初始化websocket连接
      this.voc.init(ts);

      // 4 设置最终消息结果的回调函数
      this.voc.onmessage((text) => {
        console.log("text: ", text);
        th.customerText += text;
        th.resultTextTemp = "";
        const arr = ["，", "。", "？", "！", "；"];
        if (arr.includes(th.customerText[0])) {
          th.customerText = th.customerText.slice(1);
        }
        console.log("结束消息");
      });

      // 5 设置结束消息的回调函数
      this.voc.onOverMsg(() => {
        th.sentMsg();
        th.resultState = 0;
        console.log("结束消息111");
        let audio = new Audio();
        // 结束时的发送音乐
        audio.src = require("@/assets/audio/msgEnd.mp3");
        audio.play();
      });

      // 3 设置中间消息结果的回调函数
      this.voc.onmiddlemessage((text) => {
        console.log("text11: ", text);
        th.resultTextTemp = text;
        console.log(th.resultState);
        if (!th.resultState) {
          //首次且仅触发一次
          //播放本地音频文件
          th.msgOverLock = Symbol();
          th.audioPlayer.stopAudio();

          let audio = new Audio();
          // 我在
          audio.src = require("@/assets/audio/msgOpen.mp3");
          audio.play();
          th.resultState = 1;
        }
        console.log("中间消息");
      });

      // 设置错误消息
      this.voc.setErrorFunc(() => {
        this.$message.info("语音网络连接失败，请稍后重试或使用输入框手动输入");
        setTimeout(() => {
          this.recorderStop();
        }, 50);
      });

      // 2 使用Recorder类来实现音频录制功能
      this.recorder = new Recorder({
        sampleBits: 16,
        sampleRate: 16000,
        numChannels: 1,
        compiling: true,
      });
      Recorder.getPermission().then(
        () => {
          this.vocState = 1;
          th.recorder.start();
          th.recorder.onprogress = (params) => {
            th.voc.send(
              new Int8Array(params.data[params.data.length - 1].buffer)
            );
          };
        },
        (err) => {
          th.$message({
            message: "请先允许网页使用麦克风",
            type: "info",
          });
          this.vocState = 0;
        }
      );
    },

    recorderStop() {
      console.log("执行关闭");
      this.vocState = 0;
      this.resultState = 0;
      //结束对讲
      let th = this;
      this.recorder.stop();
      if (this.voc) {
        this.voc.close();
        this.voc = null;
      }
      this.recorder.destroy().then(() => {
        th.recorder = null;
      });
      this.$forceUpdate();
    },
    formatDateToCustomFormat(date) {
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, "0");
      const day = String(date.getDate()).padStart(2, "0");
      const hours = String(date.getHours()).padStart(2, "0");
      const minutes = String(date.getMinutes()).padStart(2, "0");
      const seconds = String(date.getSeconds()).padStart(2, "0");
      return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
    },
    findMaxIdObject(array) {
      if (array.length === 0) {
        return null; // 空数组，没有对象可返回
      }
      let maxIdObject = array[0]; // 假设第一个对象具有最大的 id 值

      for (let i = 1; i < array.length; i++) {
        if (array[i].id > maxIdObject.id) {
          maxIdObject = array[i]; // 找到更大的 id 值，更新 maxIdObject
        }
      }

      return maxIdObject;
    },
    // 用户发送消息
    sentMsg() {
      let text = this.customerText.trim();
      if (text == "") return false;

      this.iptDisabled = true;
      //存在就停止录音
      if (this.recorder) {
        this.recorderStop();
      }
      // 清除定时器
      clearInterval(this.timeOut);
      console.log("----1----");
      // overTurn() 方法的作用是将 newText 的内容追加到消息列表中的最后一条消息上，并清空 newText。
      this.overTurn();
      console.log("----2----");
      let th = this;
      console.log("queryParams: =====", this.queryParams.history);
      // 停止播放当次音频
      this.audioPlayer.stopAudio();
      clearTimeout(this.timer);
      // 自动触发结束语
      this.showTimer();

      //把text中的小雅替换成空
      text = text.replace(/小雅/g, "");
      const arr = ["，", "。", "？", "！", "；"];
      if (arr.includes(text[0])) {
        text = text.slice(1);
      }
      if (text.length === 0) {
        return;
      }

      let lock = Symbol();
      this.audioLock = lock;
      th.msgOverLock = lock;
      this.queryParams.text = text;
      this.queryParams.createTime = this.formatDateToCustomFormat(new Date());

      // 判断是否新建窗口并处理会话信息
      if (this.isAddNewWin || this.conversation.length === 0) {
        let is = this.findMaxIdObject(this.conversation);
        console.log("--------------1-----------", is);
        if (is !== null) {
          this.queryParams.conversationId = is.conversationId + 1;
        } else {
          this.queryParams.conversationId = 1;
        }

        if (text !== "") {
          var obj = {
            type: "rightinfo",
            time: this.getTodayTime(),
            content: text,
          };
          console.log("----3----");
          this.overTurn();
          console.log("----4----");
          this.info.push(obj);
          console.log(3);
          this.queryParams.title = this.customerText;
          this.queryParams.text = this.customerText;
          this.customerText = "";
          this.isLoading = true;
          console.log("this.queryParams: ", this.queryParams);
          this.getStream(this.queryParams);

          this.$nextTick(() => {
            var contentHeight = document.getElementById("right");
            contentHeight.scrollTop = contentHeight.scrollHeight;
          });
        }
        if (this.conversation.length > 0) {
          this.$refs[
            "div-" + this.conversation[0].conversationId
          ][0].classList.remove("bgc");
          this.conversation.forEach((i, index) => {
            if (this.$refs["div-" + i.conversationId]) {
              this.$refs["div-" + i.conversationId][0].style.backgroundColor =
                "";
            }
          });
        }
        console.log("开始调用");
        //创建窗口标签
        let chat = {
          conversationId: this.queryParams.conversationId,
          createTime: this.queryParams.createTime,
          title: this.queryParams.title,
          isHighlighted: false,
          isCustom: true,
        };

        // this.queryParams.title = null;
        this.conversation.unshift(chat);
        this.isAddNewWin = false;

        // setTimeout(()=>{this.getConversation(2)}, 5000)
      } else {
        if (text !== "") {
          var obj = {
            type: "rightinfo",
            time: this.getTodayTime(),
            content: text,
          };
          this.info.push(obj);

          this.queryParams.text = this.customerText;
          this.queryParams.title = null;
          this.customerText = "";
          this.isLoading = true;
          this.getStream(this.queryParams);

          this.$nextTick(() => {
            var contentHeight = document.getElementById("right");
            contentHeight.scrollTop = contentHeight.scrollHeight;
          });
          this.queryParams.title = null;
        }
      }
    },

    turnText() {
      //info的最后一项
      this.textInterval = setInterval(() => {
        if (this.newText.length === 0) {
          clearInterval(this.textInterval);
          return;
        }
        this.info[this.info.length - 1].content += this.newText[0];
        this.newText = this.newText.slice(1);
        this.$nextTick(() => {
          let contentHeight = document.getElementById("right");
          contentHeight.scrollTop = contentHeight.scrollHeight;
        });
      }, 100);
    },
    overTurn() {
      console.log("这是this.newText");
      console.log(this.newText);
      if (this.newText.length == 0) return;
      clearInterval(this.textInterval);
      console.log("这是this.info");
      console.log(this.info);
      this.info[this.info.length - 1].content += this.newText;
      this.newText = "";
    },
    // 机器人回答消息

    appendRobotMsg(text) {
      clearTimeout(this.timer);
      this.showTimer();
      text = text.trim();
      // console.log('this.robotAnswer: ', this.robotAnswer);
      // this.info[this.info.length - 1].original += text
      this.turnText();
      this.overTurn();

      // }
      // this.$nextTick(() => {
      //   var contentHeight = document.getElementById("right");
      //   contentHeight.scrollTop = contentHeight.scrollHeight;
      // });
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
        text: "2",
      };
      let obj_r = {
        type: "rightinfo",
        time: this.getTodayTime(),
        name: "robot",
        content: val,
        question: [],
        text: "2",
      };
      console.log(obj_r);
      console.log(obj_l);
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
    // 触发自动结束语
    showTimer() {
      this.timer = setTimeout(this.endMsg, 10000 * 60);
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

    getConversation(v) {
      getConversation().then((r) => {
        console.log("获取了对话的值");
        console.log(r);
        this.conversation = r.data;
        this.conversation.forEach((item) => {
          Object.assign(item, { show: false });
        });
        if (v === 1) {
          if (r.data.length > 0) {
            this.conversationClickCut(r.data[0].conversationId, 1);
          }
          if (r.data.length === 0) {
            this.info = [
              {
                type: "leftinfo",
                time: this.getTodayTime(),
                name: "robot",
                content: "",
                question: [],
                text: "1",
              },
            ];
            let text = "您好，我是智能AI医生小雅，请问有什么问题可以帮助您？";
            let index = 0;
            const interval = setInterval(() => {
              if (index < text.length && this.info[0].text === "1") {
                this.info[0].content += text[index];
                index++;
              } else {
                clearInterval(interval);
              }
            }, 100); // 控制逐字显示速度，你可以根据需要调整
            this.robotAnswer = [];
          }
        }
      });
    },

    /**
     * 删除用户的会话窗口
     * @param val
     */
    conversationClickDel(val) {
      if (!this.iptDisabled) {
        this.$modal
          .confirm('是否确认删除会话窗口编号为"' + val + '"的数据项？')
          .then(function () {
            return deleteConversation(val);
          })
          .then(() => {
            this.getConversation(1);
            this.$modal.msgSuccess("删除成功");
          })
          .catch(() => {});
      }
    },

    /**
     * 查询用户指定的会话窗口的历史数据
     * @param val
     * @param id
     * @param event
     */
    conversationClickCut(val, id) {
      // console.log(event.target)
      // // console.log(this.$refs['div-'+val][0])
      // if (event.target === this.$refs['div-'+val][0] && this.isDisabled) {
      //   // 在这里执行你想要的操作
      //   console.log("点击了禁用的输入框");
      // }
      if (!this.iptDisabled) {
        this.isAddNewWin = false;
        if (id === 2) {
          this.$refs[
            "div-" + this.conversation[0].conversationId
          ][0].classList.remove("bgc");
        }

        this.conversation.forEach((i, index) => {
          if (this.$refs["div-" + val]) {
            if (i.conversationId === val) {
              this.$refs["div-" + val][0].style.backgroundColor = "#343541";
            } else {
              this.$refs["div-" + i.conversationId][0].style.backgroundColor =
                "";
            }
          }
        });

        this.queryParams.conversationId = val;

        getChatQuizList(val).then((r) => {
          this.info = [];
          for (let i = 0; i < r.data.length; i++) {
            let label = JSON.parse(r.data[i].messageContent).label;
            let value = JSON.parse(r.data[i].messageContent).value;

            let obj_l = {
              type: "leftinfo",
              time: r.data[i].responseTime,
              name: "robot",
              content: value,
              text: "2",
            };
            let obj_r = {
              type: "rightinfo",
              time: r.data[i].createTime,
              name: "robot",
              content: label,
              text: "2",
            };
            this.info.push(obj_r);
            this.info.push(obj_l);
          }
          this.$nextTick(() => {
            this.$refs.message.scrollTop = this.$refs.message.scrollHeight;
          });
        });
      }
    },

    /**
     * 新增窗口
     */
    conversationClickAdd() {
      if (!this.iptDisabled) {
        // 获取时间
        this.getConversation(2);
        this.info = [
          {
            type: "leftinfo",
            time: this.getTodayTime(),
            name: "robot",
            content: "",
            question: [],
            text: "1",
          },
        ];
        let text = "您好，我是智能AI医生小郑，请问有什么问题可以帮助您？";
        let index = 0;
        const interval = setInterval(() => {
          if (index < text.length && this.info[0].text === "1") {
            this.info[0].content += text[index];
            index++;
          } else {
            clearInterval(interval);
          }
        }, 100); // 控制逐字显示速度，你可以根据需要调整

        this.robotAnswer = [];

        this.isAddNewWin = true;

        this.$refs[
          "div-" + this.conversation[0].conversationId
        ][0].classList.remove("bgc");
        this.conversation.forEach((i, index) => {
          this.$refs["div-" + i.conversationId][0].style.backgroundColor = "";
        });
      }
    },
    empty() {
      if (!this.iptDisabled) {
        console.log(this.conversation);
        // 保存Vue实例的this
        const that = this;
        this.$modal
          .confirm("是否确认删除所有历史对话？")
          .then(() => {
            // 使用map生成Promise数组，然后用Promise.all等待所有删除操作完成
            const deletePromises = that.conversation.map((convo) =>
              deleteConversation(convo.conversationId)
            );
            return Promise.all(deletePromises);
          })
          .then(() => {
            that.getConversation();
            that.$modal.msgSuccess("删除成功");
          })
          .catch(() => {
            // 可以在这里处理取消操作或错误
          });
      }
    },
    //点击修改图标显示input
    updatatit(id, ins) {
      if (!this.iptDisabled) {
        this.conversation.forEach((i, index) => {
          if (i.conversationId === id) {
            i.isCustom = false;
            i.isHighlighted = true;
            this.$refs["input" + id][0].type = "text";
            this.$refs["input" + id][0].focus();
          }
        });
      }
    },
    //input失去焦点
    blurId(id) {
      this.conversation.forEach((i, index) => {
        this.$refs["input" + id][0].type = "hidden";
        if (i.conversationId === id) {
          i.isCustom = true;
          i.isHighlighted = false;
          if (i.title !== this.$refs["input" + id][0].value) {
            i.title = this.$refs["input" + id][0].value;
            updateConversation(i).then((r) => {
              console.log(r);
            });
          }
        }
      });
    },
  },
  props: {},
  destroyed() {},
};
</script>
<style lang="scss" scoped>
.bgc {
  background-color: #343541;
}

.mainbox {
  width: 100%;

  .box {
    width: 100%;
    height: calc(100vh - 84px);
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
      height: 97%;
      //overflow-y: scroll;
      font-size: 20px;
      width: 98%;
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
        margin-top: 1vh;

        .pic_ro {
          width: 3vw;
          height: 6vh;
          // 机器人回话的位置
          margin-left: 3vw;
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
          color: #5b5b5b;
          font-size: 18px;
        }
      }

      .info_l {
        // 自己问答位置
        margin-right: 3vw;
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
          color: #5b5b5b;
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
          word-wrap: break-word;
          word-break: break-all;

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
  background: #3163c5;
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
  color: #3163c5;
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
  border: 4px rgba(47, 82, 143) solid;
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
  width: 28%;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  font-size: 1.3vw;
  color: #ffffff;
  border: 2px solid #9a9999;
  border-radius: 15px;
  margin-right: 1vw;

  .new {
    height: 9%;
    width: 100%;
    display: flex;
    align-items: center;

    .img_box {
      background-color: #ffffff;
      border-radius: 50%;
      display: flex;
      justify-content: center;
      align-items: center;
      margin-left: 1vw;

      .zhengda {
        height: 7vh;
      }
    }

    .new_his {
      flex: 1;
      height: 100%;
      margin-left: 1vw;
      margin-right: 1vw;
      border: 2px solid #9a9999;
      border-radius: 1vw;
      display: flex;
      justify-content: center;
      align-items: center;
      font-size: 23px;
    }
  }

  .his {
    height: 85%;
    display: flex;
    flex-direction: column;
    align-items: center;
    overflow: hidden;
    overflow-y: scroll;
    user-select: none;

    .his_item {
      box-sizing: border-box;
      display: flex;
      justify-content: space-between;
      align-items: center;
      height: 10%;
      min-height: 10%;
      width: 96%;
      border: 2px solid #9a9999;
      border-radius: 0.5vw;
      margin-top: 1.5vh;
      margin-left: 1vh;

      .mesimg2 {
        margin-left: 1vw;
        width: 2.5vw;
        height: 2.2vw;
      }

      .his_title {
        width: 60%;
        margin: 0 1vw 0 0.5vw;

        .tit {
          width: 98%;
          margin: 1vh 0 0.5vh 0;
          overflow: hidden;
          border: none;
          background: transparent;
          outline: none;
          color: white;
          font-size: 16px;
          min-font-size: 16px;
          pointer-events: auto;
          cursor: pointer;

          white-space: nowrap;
          /*超出的空白区域不换行*/
          overflow: hidden;
          /*超出隐藏*/
          text-overflow: ellipsis;
        }

        .titUp {
          width: 100%;
          margin: 1vh 0 0.5vh 0;
          overflow: hidden;
        }

        .time {
          font-size: 0.9vw;
          color: #ababab;
        }

        ::v-deep .el-input--medium .el-input__inner {
          font-size: 1.3vw;
          height: auto;
          line-height: 0;
          padding: 2px 0 2px 0;
        }
      }

      .delimg {
        margin-right: 0.5vw;
        width: 1.8vw;
        height: 1.8vw;
      }

      .upimg {
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
  border: 2px #dfdfdf solid;
  margin: auto;
  position: relative;
  border-radius: 1vw;
}

.left-child .mesimg1 {
  width: 2.5vw;
  height: 2.2vw;
  margin: 0.8vh 1vw 0 0.5vw;
}

.left-child .right-child {
  width: 2.5vw;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
}

.right-child .mkf {
  height: 2.5vw;
  width: 2.5vw;
  border-radius: 50%;
  cursor: pointer;
  position: relative;
}

.mkftips {
  position: absolute;
  top: 0;
  left: 50%;
  //宽度自适应，被内容撑开
  width: calc(4em + 12px);
  text-align: center;
  transform: translate(-50%, -120%);
  background-color: rgb(107, 245, 46);
  color: #fff;
}

.right-child .mkf-s {
  background-color: #13ce66;
}

.right-child .mkf .mesimg3 {
  height: 100%;
  width: 100%;
}

.left-child .right-child .mesimg3 {
  width: 2.5vw;
  height: 2.2vw;
  //position: absolute;
  //bottom: 0.5vh;
  //right: 0.5vw;
}

.right {
  width: 70%;
  background-color: #ffffff;
  border-radius: 1.5vw;
  overflow: hidden;
}

.right .right-title {
  height: 12%;
  position: relative;
}

.right .right-title .xietong {
  height: 8vh;
  margin: 2vh 0 0 1vw;
}

.right .message {
  height: 65%;
  background-color: #ffffff;
  overflow-y: scroll;
}

.right .text-area {
  height: 22%;
  width: 100%;
  margin-top: 2vh;
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

.right-title-button {
  z-index: 10;
  height: 100%;
  position: absolute;
  right: 0;
  top: 0;
  padding-right: 16px;
  display: flex;
  align-items: center;
}
</style>
