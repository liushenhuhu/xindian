<template>
  <div class="navbar">

    <hamburger id="hamburger-container" :is-active="sidebar.opened" class="hamburger-container" @toggleClick="toggleSideBar" />

    <breadcrumb id="breadcrumb-container" class="breadcrumb-container" v-if="!topNav"/>
    <top-nav id="topmenu-container" class="topmenu-container" v-if="topNav"/>
    <div class="right-menu">

      <template v-if="device!=='mobile'">
        <span style="vertical-align: text-bottom;margin-right: 10px;color: #FFFFFF;">版本号: V1.0.00</span>
        <search id="header-search" class="right-menu-item" />
<!--        <el-tooltip content="源码地址" effect="dark" placement="bottom">-->
<!--          <ruo-yi-git id="ruoyi-git" class="right-menu-item hover-effect" />-->
<!--        </el-tooltip>-->

<!--        <el-tooltip content="文档地址" effect="dark" placement="bottom">-->
<!--          <ruo-yi-doc id="ruoyi-doc" class="right-menu-item hover-effect" />-->
<!--        </el-tooltip>-->

        <screenfull id="screenfull" class="right-menu-item hover-effect" />

        <el-tooltip content="布局大小" effect="dark" placement="bottom">
          <size-select id="size-select" class="right-menu-item hover-effect" />
        </el-tooltip>

      </template>

      <el-dropdown class="avatar-container right-menu-item hover-effect" trigger="click">
        <div class="avatar-wrapper">
          <img :src="avatar" class="user-avatar">
          <i class="el-icon-caret-bottom" />
        </div>
        <el-dropdown-menu slot="dropdown">
          <router-link to="/user/profile">
            <el-dropdown-item>个人中心</el-dropdown-item>
          </router-link>
          <el-dropdown-item @click.native="setting = true">
            <span>布局设置</span>
          </el-dropdown-item>
          <el-dropdown-item divided @click.native="logout">
            <span>退出登录</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import {mapGetters} from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import TopNav from '@/components/TopNav'
import Hamburger from '@/components/Hamburger'
import Screenfull from '@/components/Screenfull'
import SizeSelect from '@/components/SizeSelect'
import Search from '@/components/HeaderSearch'
import RuoYiGit from '@/components/RuoYi/Git'
import RuoYiDoc from '@/components/RuoYi/Doc'
import Stomp from "stompjs";
import $ from 'jquery'

export default {
  components: {
    Breadcrumb,
    TopNav,
    Hamburger,
    Screenfull,
    SizeSelect,
    Search,
    RuoYiGit,
    RuoYiDoc
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'avatar',
      'device'
    ]),
    setting: {
      get() {
        return this.$store.state.settings.showSettings
      },
      set(val) {
        this.$store.dispatch('settings/changeSetting', {
          key: 'showSettings',
          value: val
        })
      }
    },
    topNav: {
      get() {
        return this.$store.state.settings.topNav
      }
    }
  },
  data() {
    return {
      client:null,
      notifications: {},
      mes:{
        content:null,
        messageId:null,
        title:null,
        level:null,
        logId:null,
        logType:null
      }
    }
  },
  mounted() {
    this.connect()
    // this.$messageWebsocket.websocketApi.initWebSocket(this.$store.state.login.userInfo.userInfo.id, this.openMessageTips);
  },
  methods: {
    toggleSideBar() {
      setTimeout(()=>{
        $(window).trigger("resize");
      },500)
      this.$store.dispatch('app/toggleSideBar')
    },
    async logout() {
      this.$confirm('确定注销并退出系统吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$store.dispatch('LogOut').then(() => {
          location.href = '/index';
          this.client.close;
        })
      }).catch(() => {});
    },
    closeNotification(id){
      // console.log(id)
      this.notifications[id].close();
      console.log(this.notifications)
      delete this.notifications[id];
    },
    openMessageTips(message){
      let _this = this;
      //将messageId和通知实例放入字典中
      if(message.level==="warning"){
        this.notifications[message.messageId]=this.$notify({
          title: message.title,
          position: 'bottom-right',
          showClose: false,
          dangerouslyUseHTMLString: true,
          message: this.$createElement('div', null,
            [
              this.$createElement('div', null, [this.$createElement('span', null, message.content)]),
              // this.$createElement('div', null, message.content),
              this.$createElement('div', null,
                [
                  this.$createElement(
                    'button',
                    {
                      style: {
                        borderRadius: '15px',
                        padding: '10px 15px',
                        margin: '25px 0px 0px 10px',
                        textAlign: 'center',
                        textDecoration: 'none',
                        display: 'inline-block',
                        webkitTransitionDuration: '0.4s',
                        transitionDuration: '0.4s',
                        cursor: 'pointer',
                        backgroundColor: 'white',
                        color: 'black',
                        border: '2px solid #e7e7e7'
                      },
                      on: {
                        click: _this.closeNotification.bind(_this, message.messageId)
                      }
                    },
                    "确定"
                  ),
                  this.$createElement('p', {
                    style: 'text-align: right;width:195px;dispaly:block;margin-top: 8px;color:#43c39d;cursor: pointer;',
                    on: {
                      click: _this.clickNotify.bind(_this, message.messageId,message.logId,message.logType)
                    },
                  }, '点击查看详情'),
                ]
              )
            ]
          ),
          duration: 0,
          type: 'warning',
        });
      }
      else if(message.level==="success"){
        this.notifications[message.messageId]=this.$notify({
          title: message.title,
          position: 'bottom-right',
          showClose: false,
          dangerouslyUseHTMLString: true,
          message: this.$createElement('div', null,
            [
              this.$createElement('div', null, [this.$createElement('span', null, message.content)]),
            ]
          ),
          duration: 4000,
          type: 'success',
        });
      }
      else if(message.level==="info"){
        this.notifications[message.messageId]=this.$notify.info({
          title: message.title,
          position: 'bottom-right',
          showClose: true,
          dangerouslyUseHTMLString: true,
          message: this.$createElement('div', null,
            [
              this.$createElement('div', null, [this.$createElement('span', null, message.content)]),
            ]
          ),
          duration: 4000,
        });
      }
      else {
        this.notifications[message.messageId]=this.$notify.error({
          title: message.title,
          position: 'bottom-right',
          showClose: false,
          dangerouslyUseHTMLString: true,
          message: this.$createElement('div', null,
            [
              this.$createElement('div', null, [this.$createElement('span', null, message.content)]),
              this.$createElement('div', null,
                [
                  this.$createElement(
                    'button',
                    {
                      style: {
                        borderRadius: '15px',
                        padding: '10px 15px',
                        margin: '25px 0px 0px 10px',
                        textAlign: 'center',
                        textDecoration: 'none',
                        display: 'inline-block',
                        webkitTransitionDuration: '0.4s',
                        transitionDuration: '0.4s',
                        cursor: 'pointer',
                        backgroundColor: 'white',
                        color: 'black',
                        border: '2px solid #e7e7e7'
                      },
                      on: {
                        click: _this.closeNotification.bind(_this, message.messageId)
                      }
                    },
                    "确定"
                  )
                ]
              )
            ]
          ),
          duration: 0,
        });
      }
    },
    clickNotify(id,logId,logType) {
      // console.log(row.logId);
      // console.log(message.messageId)
      this.closeNotification(id)
      // this.$router.push({
      //   name: "lookLog",
      //   params: {logId: logId, logType: logType}
      // });
      this.$router.push({
        path: "/lookLog1",// name: "lookLog"
        query: {logId: logId, logType: logType}
      });
    },
    onConnected(frame) {
      console.log("Connected: " + frame);
      //绑定交换机exchange_pushmsg是交换机的名字 queue是绑定的路由key
      var exchange = "/exchange/exchange_pushmsg/queue";
      // var exchange = "/exchange/patientMessage/132";
      //创建随机队列用上面的路由key绑定交换机,放入收到消息后的回调函数和失败的回调函数
      this.client.subscribe(exchange, this.responseCallback, this.onFailed);
      console.log(frame)
    },
    onFailed(frame) {
      console.log("Failed: " + frame);
    },
    responseCallback(frame) {
      console.log("得到的消息 msg=>" + frame.body);
      console.log(frame)
      //接收到服务器推送消息，向服务器定义的接收消息routekey路由rk_recivemsg发送确认消息
      let res=JSON.parse(frame.body)
      console.log(res)
      this.mes.content=res.content
      this.mes.messageId=res.messageId
      this.mes.title=res.title
      this.mes.level=res.level
      this.mes.logId=res.logId
      this.mes.logType=res.logType
      this.openMessageTips(this.mes)
    },
    connect() {
      //这里填你rabbitMQ的连接ip地址直接替换localhost:15674就好其它的不用改
      this.client= Stomp.client("wss://ecg.mindyard.cn:84/ws")
      // this.client= Stomp.client("ws://202.102.249.124:15674/ws")
      // this.client= Stomp.client("ws://219.155.7.235:5672")
      // this.client= Stomp.client("ws://219.155.7.235:15674/ws")
      // this.client= Stomp.client("ws://localhost:15674/ws")
      //填写你rabbitMQ登录的用户名和密码
      var headers = {
        "login": "zzu123",
        "passcode": "zzu@2022",
        //虚拟主机，默认“/”
        "host": "VHOST"
      };
      //创建连接,放入连接成功和失败回调函数
      this.client.connect(headers, this.onConnected, this.onFailed);
    }
  }
}
</script>

<style lang="scss" scoped>
.navbar {
  height: 50px;
  overflow: hidden;
  position: relative;
  background: #1D434A;
  box-shadow: 0 1px 4px rgba(0,21,41,.08);

  .hamburger-container {
    line-height: 46px;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: background .3s;
    -webkit-tap-highlight-color:transparent;

    &:hover {
      background: rgba(0, 0, 0, .025)
    }
  }

  .breadcrumb-container {
    float: left;
  }

  .topmenu-container {
    position: absolute;
    left: 50px;
  }

  .errLog-container {
    display: inline-block;
    vertical-align: top;
  }

  .right-menu {
    float: right;
    height: 100%;
    line-height: 50px;

    &:focus {
      outline: none;
    }

    .right-menu-item {
      display: inline-block;
      padding: 0 8px;
      height: 100%;
      font-size: 18px;
      color: #5a5e66;
      vertical-align: text-bottom;
      //计算a+b
      &.hover-effect {
        cursor: pointer;
        transition: background .3s;

        &:hover {
          background: rgba(0, 0, 0, .025)
        }
      }
    }

    .avatar-container {
      margin-right: 30px;

      .avatar-wrapper {
        margin-top: 5px;
        position: relative;

        .user-avatar {
          cursor: pointer;
          width: 40px;
          height: 40px;
          border-radius: 10px;
        }

        .el-icon-caret-bottom {
          cursor: pointer;
          position: absolute;
          right: -20px;
          top: 25px;
          font-size: 12px;
        }
      }
    }
  }
}
</style>
