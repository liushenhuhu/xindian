import request from '@/utils/request'

// 登录方法
export function login(username, password, code, uuid) {
  const data = {
    username,
    password,
    code,
    uuid
  }
  return request({
    url: '/login',
    headers: {
      isToken: false
    },
    method: 'post',
    data: data
  })
}

// 注册方法
export function register(data) {
  return request({
    url: '/register',
    headers: {
      isToken: false
    },
    method: 'post',
    data: data
  })
}

// 获取用户详细信息
export function getInfo() {
  return request({
    url: '/getInfo',
    method: 'get'
  })
}

// 退出方法
export function logout() {
  return request({
    url: '/logout',
    method: 'post'
  })
}

// 获取验证码
export function getCodeImg() {
  return request({
    url: '/captchaImage',
    headers: {
      isToken: false
    },
    method: 'get',
    timeout: 20000
  })
}


// 短信登录方法
export function smsLogin(mobile, smsCode, uuid) {
  const data = {
    mobile,
    smsCode,
    uuid
  }
  return request({
    url: '/sms/login',
    method: 'post',
    data: data
  })
}


// 发送短信验证码
export function getSmsCode(mobile) {
  const data = {
    mobile
  }
  return request({
    url: '/sms/code',
    method: 'post',
    data: data
  })
}
