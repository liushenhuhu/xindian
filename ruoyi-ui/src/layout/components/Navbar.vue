<template>
  <div class="navbar">
    <hamburger id="hamburger-container" :is-active="sidebar.opened" class="hamburger-container" @toggleClick="toggleSideBar" />

    <breadcrumb id="breadcrumb-container" class="breadcrumb-container" v-if="!topNav"/>
    <top-nav id="topmenu-container" class="topmenu-container" v-if="topNav"/>

    <div class="right-menu">
      <template v-if="device!=='mobile'">
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
        info:null,
        messageId:null,
        title:null,
        t:null
      }
    }
  },
  mounted() {
    this.connect()
    // this.$messageWebsocket.websocketApi.initWebSocket(this.$store.state.login.userInfo.userInfo.id, this.openMessageTips);
  },
  methods: {
    toggleSideBar() {
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
      // console.log(this.notifications)
      delete this.notifications[id];
    },
    openMessageTips(message){
      let _this = this;
      // this.closeAllNotification();
      //将messageId和通知实例放入字典中
      if(message.t==="warning"){
        this.notifications[message.messageId]=this.$notify({
          title: message.title,
          position: 'bottom-right',
          showClose: false,
          dangerouslyUseHTMLString: true,
          message: this.$createElement('div', null,
            [
              this.$createElement('div', null, [this.$createElement('span', null, message.info)]),
              this.$createElement('div', null,
                [
                  this.$createElement(
                    'button',
                    {
                      style: {
                        padding: '10px 18px',
                        margin: '10px 0px 20px 170px',
                        textAlign: 'center',
                        textDecoration: 'none',
                        display: 'inline-block',
                        webkitTransitionDuration: '0.4s',
                        transitionDuration: '0.4s',
                        cursor: 'pointer',
                        backgroundColor: 'white',
                        color: 'black',
                        border: '2px solid #e7e7e7',
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
          type: 'warning',
        });
      }
      else if(message.t==="success"){
        this.notifications[message.messageId]=this.$notify({
          title: message.title,
          position: 'bottom-right',
          showClose: false,
          dangerouslyUseHTMLString: true,
          message: this.$createElement('div', null,
            [
              this.$createElement('div', null, [this.$createElement('span', null, message.info)]),
              this.$createElement('div', null,
                [
                  this.$createElement(
                    'button',
                    {
                      style: {
                        padding: '10px 18px',
                        margin: '10px 0px 20px 170px',
                        textAlign: 'center',
                        textDecoration: 'none',
                        display: 'inline-block',
                        webkitTransitionDuration: '0.4s',
                        transitionDuration: '0.4s',
                        cursor: 'pointer',
                        backgroundColor: 'white',
                        color: 'black',
                        border: '2px solid #e7e7e7',
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
          type: 'success',
        });
      }
      else if(message.t==="info"){
        this.notifications[message.messageId]=this.$notify.info({
          title: message.title,
          position: 'bottom-right',
          showClose: false,
          dangerouslyUseHTMLString: true,
          message: this.$createElement('div', null,
            [
              this.$createElement('div', null, [this.$createElement('span', null, message.info)]),
              this.$createElement('div', null,
                [
                  this.$createElement(
                    'button',
                    {
                      style: {
                        padding: '10px 18px',
                        margin: '10px 0px 20px 170px',
                        textAlign: 'center',
                        textDecoration: 'none',
                        display: 'inline-block',
                        webkitTransitionDuration: '0.4s',
                        transitionDuration: '0.4s',
                        cursor: 'pointer',
                        backgroundColor: 'white',
                        color: 'black',
                        border: '2px solid #e7e7e7',
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
      else {
        this.notifications[message.messageId]=this.$notify.error({
          title: message.title,
          position: 'bottom-right',
          showClose: false,
          dangerouslyUseHTMLString: true,
          message: this.$createElement('div', null,
            [
              this.$createElement('div', null, [this.$createElement('span', null, message.info)]),
              this.$createElement('div', null,
                [
                  this.$createElement(
                    'button',
                    {
                      style: {
                        padding: '10px 18px',
                        margin: '10px 0px 20px 170px',
                        textAlign: 'center',
                        textDecoration: 'none',
                        display: 'inline-block',
                        webkitTransitionDuration: '0.4s',
                        transitionDuration: '0.4s',
                        cursor: 'pointer',
                        backgroundColor: 'white',
                        color: 'black',
                        border: '2px solid #e7e7e7',
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
    // new_equipment(mesg){
    //   const h = this.$createElement;
    //   let _this = this;
    //   this.$notify({
    //     title: '新设备接入',
    //     message: h(
    //       "p",
    //       {
    //         style:"width: 250px;display: flex;justify-content: space-between;",
    //       },
    //       [
    //         h("span", null, mesg),
    //         h("button", {
    //             style: "color: #409EFF;cursor: pointer;",
    //             on: {
    //               click: _this.closeNotification.bind(_this, 1, 1, message),
    //             },
    //           },
    //           "确定"),
    //       ]
    //     ),
    //     position: 'bottom-right',
    //     duration: 0,
    //     type: 'warning',
    //     showClose:true,
    //   });
    // },
    onConnected(frame) {
      console.log("Connected: " + frame);
      //绑定交换机exchange_pushmsg是交换机的名字rk_pushmsg是绑定的路由key
      var exchange = "/exchange/exchange_pushmsg/queue";
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
      // this.new_equipment(frame.body)
      let res=JSON.parse(frame.body)
      this.mes.info=res.info
      this.mes.messageId=res.messageId
      this.mes.title=res.title
      this.mes.t=res.t
      this.openMessageTips(this.mes)
      // this.client.send("/exchange/exchange_pushmsg/queue", {"content-type":"text/plain"}, frame.body);
    },
    connect() {
      //这里填你rabbitMQ的连接ip地址直接替换localhost:15674就好其它的不用改
      this.client= Stomp.client("ws://219.155.7.235:15674/ws")
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
  background: #fff;
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
