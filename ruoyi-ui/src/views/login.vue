<!--
<template>
  <div class="login">
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
      <h3 class="title">远程心电管理平台</h3>
      <el-form-item prop="username">
        <el-input
          v-model="loginForm.username"
          type="text"
          auto-complete="off"
          placeholder="账号"
        >
          <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input
          v-model="loginForm.password"
          type="password"
          auto-complete="off"
          placeholder="密码"
          @keyup.enter.native="handleLogin"
        >
          <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>
      <el-form-item prop="code" v-if="captchaOnOff">
        <el-input
          v-model="loginForm.code"
          auto-complete="off"
          placeholder="验证码"
          style="width: 63%"
          @keyup.enter.native="handleLogin"
        >
          <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon" />
        </el-input>
        <div class="login-code">
          <img :src="codeUrl" @click="getCode" class="login-code-img"/>
        </div>
      </el-form-item>
      <el-checkbox v-model="loginForm.rememberMe" style="margin:0px 0px 25px 0px;">记住密码</el-checkbox>
      <el-form-item style="width:100%;">
        <el-button
          :loading="loading"
          size="medium"
          type="primary"
          style="width:100%;"
          @click.native.prevent="handleLogin"
        >
          <span v-if="!loading">登 录</span>
          <span v-else>登 录 中...</span>
        </el-button>
        <div style="float: right;" v-if="register">
          <router-link class="link-type" :to="'/register'">立即注册</router-link>
        </div>
      </el-form-item>
    </el-form>
    &lt;!&ndash;  底部  &ndash;&gt;
&lt;!&ndash;    <div class="el-login-footer">&ndash;&gt;
&lt;!&ndash;      <span>Copyright © 2018-2022 ruoyi.vip All Rights Reserved.</span>&ndash;&gt;
&lt;!&ndash;    </div>&ndash;&gt;
  </div>
</template>

<script>
import { getCodeImg } from "@/api/login";
import Cookies from "js-cookie";
import { encrypt, decrypt } from '@/utils/jsencrypt'

export default {
  name: "Login",
  data() {
    return {
      codeUrl: "",
      loginForm: {
        username: "",
        password: "",
        rememberMe: false,
        code: "",
        uuid: ""
      },
      loginRules: {
        username: [
          { required: true, trigger: "blur", message: "请输入您的账号" }
        ],
        password: [
          { required: true, trigger: "blur", message: "请输入您的密码" }
        ],
        code: [{ required: true, trigger: "change", message: "请输入验证码" }]
      },
      loading: false,
      // 验证码开关
      captchaOnOff: true,
      // 注册开关
      register: false,
      redirect: undefined
    };
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect;
      },
      immediate: true
    }
  },
  created() {
    this.getCode();
    this.getCookie();
  },
  methods: {
    getCode() {
      getCodeImg().then(res => {
        this.captchaOnOff = res.captchaOnOff === undefined ? true : res.captchaOnOff;
        if (this.captchaOnOff) {
          this.codeUrl = "data:image/gif;base64," + res.img;
          this.loginForm.uuid = res.uuid;
        }
      });
    },
    getCookie() {
      const username = Cookies.get("username");
      const password = Cookies.get("password");
      const rememberMe = Cookies.get('rememberMe')
      this.loginForm = {
        username: username === undefined ? this.loginForm.username : username,
        password: password === undefined ? this.loginForm.password : decrypt(password),
        rememberMe: rememberMe === undefined ? false : Boolean(rememberMe)
      };
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true;
          if (this.loginForm.rememberMe) {
            Cookies.set("username", this.loginForm.username, { expires: 30 });
            Cookies.set("password", encrypt(this.loginForm.password), { expires: 30 });
            Cookies.set('rememberMe', this.loginForm.rememberMe, { expires: 30 });
          } else {
            Cookies.remove("username");
            Cookies.remove("password");
            Cookies.remove('rememberMe');
          }
          this.$store.dispatch("Login", this.loginForm).then(() => {
            this.$router.push({ path: this.redirect || "/" }).catch(()=>{});
          }).catch(() => {
            this.loading = false;
            if (this.captchaOnOff) {
              this.getCode();
            }
          });
        }
      });
    }
  }
};
</script>

<style rel="stylesheet/scss" lang="scss">
.login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-image: url("../assets/images/login-background.jpg");
  background-size: cover;
}
.title {
  margin: 0px auto 30px auto;
  text-align: center;
  color: #707070;
}

.login-form {
  border-radius: 6px;
  background: #ffffff;
  width: 400px;
  padding: 25px 25px 5px 25px;
  .el-input {
    height: 38px;
    input {
      height: 38px;
    }
  }
  .input-icon {
    height: 39px;
    width: 14px;
    margin-left: 2px;
  }
}
.login-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}
.login-code {
  width: 33%;
  height: 38px;
  float: right;
  img {
    cursor: pointer;
    vertical-align: middle;
  }
}
.el-login-footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #fff;
  font-family: Arial;
  font-size: 12px;
  letter-spacing: 1px;
}
.login-code-img {
  height: 38px;
}
</style>
-->


<template>
  <div class="login">
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
      <span style="font-size: 45px;color: #F2F2F2">Login</span>
      <span style="color: #adadad; font-size: 20px; display: block;margin-top: 6%">欢迎来到心电<span style="color: red;font-weight: 700">AI</span>服务平台</span>
      <h4 style="text-align: center;color: #707070">&nbsp;</h4>

      <el-tabs v-model="activeName" @tab-click="loginMethod" class="tabs">
        <el-tab-pane class="tabPane" label="账号密码登录" name="first" >
          <!--   账号密码登录    -->
          <div class="content1" v-if="!captchaOnOff">
            <el-form-item prop="username">
              <el-input v-model="loginForm.username" type="text" auto-complete="off" placeholder="请输入您的账号">
                <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon"/>
              </el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input
                v-model="loginForm.password"
                type="password"
                auto-complete="off"
                placeholder="请输入您的密码"
                @keyup.enter.native="handleLogin">
                <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon"/>
              </el-input>
            </el-form-item>
          </div>
        </el-tab-pane>

        <el-tab-pane class="tabPane" label="验证码登录" name="second" >
          <!--   验证码登录    -->
          <div class="content2" v-if="captchaOnOff">
<!--            <el-form-item prop="code" >-->
<!--              <el-input-->
<!--                v-model="loginForm.code"-->
<!--                auto-complete="off"-->
<!--                placeholder="请输入验证码"-->
<!--                style="width: 63%"-->
<!--                @keyup.enter.native="handleLogin"-->
<!--              >-->
<!--                <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon"/>-->
<!--              </el-input>-->
<!--              <div class="login-code">-->
<!--                <img :src="codeUrl" @click="getCode" class="login-code-img"/>-->
<!--              </div>-->
<!--            </el-form-item>-->

            <el-form-item prop="mobile">
              <el-input v-model="loginForm.mobile" type="text" auto-complete="off" placeholder="请输入您的手机号">
                <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon"/>
              </el-input>
            </el-form-item>
            <el-form-item prop="smsCode">
              <el-input
                v-model="loginForm.smsCode"
                auto-complete="off"
                placeholder="请输入验证码"
                style="width: 63%"
                @keyup.enter.native="handleLogin"
              >
                <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon"/>
              </el-input>
              <div class="login-code">
                <el-button round @click.native.prevent="getSmsCode">{{ computeTime > 0 ? `(${computeTime}s)已发送` : '获取验证码' }}
                </el-button>
              </div>
            </el-form-item>
          </div>
        </el-tab-pane>
      </el-tabs>

      <el-row>
        <el-checkbox v-model="loginForm.rememberMe" style="margin:0px 0px 25px 0px;">记住密码</el-checkbox>
<!--        <div class="sms-login">
          <el-button
            size="mini"
            type="text"
            @click.native.prevent="loginMethod"
          >
            <span v-if="isSmsLogin">账号密码登录</span>
            <span v-else>短信登录</span>
          </el-button>
        </div>-->
      </el-row>

      <el-form-item style="width:100%;">
        <el-button
          :loading="loading"
          size="medium"
          type="primary"
          style="width:100%;"
          @click.native.prevent="handleLogin"
        >
          <span v-if="!loading">登 录</span>
          <span v-else>登 录 中...</span>
        </el-button>
      </el-form-item>
    </el-form>

  </div>
</template>

<script>
import {getCodeImg, getSmsCode, smsLogin} from "@/api/login";
import Cookies from "js-cookie";
import {encrypt, decrypt} from '@/utils/jsencrypt';
import Stomp from 'stompjs';

export default {
  name: "Login",
  data() {
    return {
      client:null,
      codeUrl: "",
      cookiePassword: "",
      computeTime: 0,
      loginForm: {
        username: "",
        password: "",
        rememberMe: false,
        code: "",
        uuid: "",
        mobile: "",
        smsCode: ""
      },
      loginRules: {
        username: [
          {required: true, trigger: "blur", message: "用户名不能为空"}
        ],
        mobile: [
          {required: false, trigger: "blur", message: "手机号不能为空"}
        ],
        password: [
          {required: true, trigger: "blur", message: "密码不能为空"}
        ],
        code: [{required: true, trigger: "change", message: "验证码不能为空"}],
        // smsCode: [{ required: true, trigger: "blur", message: "验证码不能为空" }]
      },
      loading: false,
      // 验证码开关
      captchaOnOff: false,
      // 手机登陆开关
      isSmsLogin: false,
      //注册开关
      redirect: undefined,
      activeName: 'first',   //默认tab
    };
  },
  watch: {
    $route: {
      handler: function (route) {
        this.redirect = route.query && route.query.redirect;
      },
      immediate: true
    }
  },
  created() {
    this.getCode();
    this.getCookie();
  },
  methods: {
    loginMethod(tab) {
      if(tab.index==="0"){
        this.loginRules.mobile[0].required = false;
        this.captchaOnOff = false
      }
      if(tab.index==="1"){
        this.loginRules.mobile[0].required = true;
        this.captchaOnOff = true
      }
    },
    getSmsCode() {
      if (!this.computeTime) {
        this.$refs.loginForm.validate(valid => {
          if (valid) {
            getSmsCode(this.loginForm.mobile).then(res => {
              if (res.code === 200) {
                this.$message({
                  message: '验证码已发送',
                  type: 'success'
                });
                this.loginForm.uuid = res.uuid;
                this.computeTime = 60;
                this.timer = setInterval(() => {
                  this.computeTime--;
                  if (this.computeTime <= 0) {
                    clearInterval(this.timer)
                  }
                }, 1000);
              }
            })
          }
        })
      }
    },
    getCode() {
      getCodeImg().then(res => {
        this.codeUrl = "data:image/gif;base64," + res.img;
        this.loginForm.uuid = res.uuid;
      });
    },
    getCookie() {
      const username = Cookies.get("username");
      const password = Cookies.get("password");
      const rememberMe = Cookies.get('rememberMe')
      this.loginForm = {
        username: username === undefined ? this.loginForm.username : username,
        password: password === undefined ? this.loginForm.password : decrypt(password),
        rememberMe: rememberMe === undefined ? false : Boolean(rememberMe)
      };
    },
    new_equipment(mesg){
      this.$notify({
        title: '新设备接入',
        message: mesg,
        position: 'bottom-right'
      });
    },
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
      this.new_equipment(frame.body)
      this.client.send("/exchange/exchange_pushmsg/queue", {"content-type":"text/plain"}, frame.body);
    },
    connect() {
      //这里填你rabbitMQ的连接ip地址直接替换localhost:15674就好其它的不用改
      this.client= Stomp.client("ws://localhost:15674/ws")
      //填写你rabbitMQ登录的用户名和密码
      var headers = {
        "login": "guest",
        "passcode": "guest",
        //虚拟主机，默认“/”
        "host": "/"
      };
      //创建连接,放入连接成功和失败回调函数
      this.client.connect(headers, this.onConnected, this.onFailed);
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          console.log("captchaOnOff"+this.captchaOnOff)
          if (this.captchaOnOff) {
            this.loading = true;
            if (this.loginForm.rememberMe) {
              Cookies.set("mobile", this.loginForm.mobile, {expires: 30});
              Cookies.set('rememberMe', this.loginForm.rememberMe, {expires: 30});
            } else {
              Cookies.remove("mobile");
              Cookies.remove('rememberMe');
            }


            this.$store.dispatch("SmsLogin", this.loginForm).then(() => {
              this.$router.push({path: this.redirect || "/"}).catch(() => {
              });
            }).catch(() => {
              this.loading = false;
            });
          } else {
            this.loading = true;
            if (this.loginForm.rememberMe) {
              Cookies.set("username", this.loginForm.username, {expires: 30});
              Cookies.set("password", encrypt(this.loginForm.password), {expires: 30});
              Cookies.set('rememberMe', this.loginForm.rememberMe, {expires: 30});
            } else {
              Cookies.remove("username");
              Cookies.remove("password");
              Cookies.remove('rememberMe');
            }

            this.$store.dispatch("Login", this.loginForm).then(() => {
              this.$router.push({path: this.redirect || "/"}).catch(() => {
              });
            }).catch(() => {
              this.loading = false;
              this.getCode();
            });
          }
        }
      });
    }
  }
};
</script>

<style rel="stylesheet/scss" lang="scss">
/* 导航条下方边框阴影*/
.tabs .el-tabs__nav-wrap.is-top::after {
  content: "";
  left: 0;
  //bottom: 0;
  height: 2px;
  background-color: rgba(0,0,0,0) !important;
  z-index: 1;
}

#tab-second.el-tabs__item.is-top {
  color: white;
}
#tab-second.el-tabs__item.is-active {
  color: #2e7dc9;
}
#tab-first.el-tabs__item.is-top {
  color: white;
}
#tab-first.el-tabs__item.is-active {
  color: #2e7dc9;
}

.tabs .el-input__inner {
    width: 92%;
    height: 100%;
    //border: 0px;
    //border-bottom: 1px solid black;
    border-radius: 8px;
    background-color: rgb(8 19 64);
    color: white;
    font-size: 14px;
    //margin-left: 0.3vw
}


.login {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  height: 100%;
  background-image: url("../assets/images/backg1.png");
  background-size: cover;
}

.title {
  margin: 0px auto 30px auto;
  text-align: center;
  color: #707070;
}

.login-form {
  opacity: 1;
  background: rgba(3, 11, 43, 1);
  box-shadow:inset 0px 0px 20px  rgba(67, 118, 176, 1);

  border-radius: 6px;
  //background: #ffffff;
  width: 400px;
  padding: 25px 25px 5px 25px;
  height: 530px;
  margin-right: 12%;
  .el-input {
    height: 38px;

    input {
      height: 38px;
    }
  }

  .input-icon {
    height: 39px;
    width: 14px;
    margin-left: 2px;
  }
}

.login-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}

.login-code {
  width: 33%;
  height: 38px;
  float: right;

  img {
    cursor: pointer;
    vertical-align: middle;
  }
}

.sms-login {
  width: 25%;
  height: 30px;
  float: right;


}

.el-login-footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #fff;
  font-family: Arial;
  font-size: 12px;
  letter-spacing: 1px;
}

.login-code-img {
  height: 38px;
}
</style>
