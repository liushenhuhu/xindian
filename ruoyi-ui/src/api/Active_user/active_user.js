import request from '@/utils/request'

// 获取微信小程序用户量与活跃度
export function userNumber(nian) {
  return request({
    url: '/patient/login/getCount',
    method: 'get',
    params: {
      year:nian
    }
  })
}
