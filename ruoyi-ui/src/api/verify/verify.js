import request from '@/utils/request'

export function getVerify(data) {
  return request({
    url: '/verify/authority/verify',
    method: 'post',
    data: data
  })
}
