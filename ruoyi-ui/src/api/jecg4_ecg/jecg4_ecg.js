import request from '@/utils/request'

// 发送短信时获取登录密码密码
export function getlogin_password(password) {
  return request({
    url: '/system/user/VerifyPassword',
    method: 'post',
    data: password,
  })
}
