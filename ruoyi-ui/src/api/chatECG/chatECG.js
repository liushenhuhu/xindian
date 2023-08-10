import request from '@/utils/request'

//
export function proxyRequest(query) {
  return request({
    url: '/proxyRequest/proxyRequest',
    method: 'post',
    data: query
  })
}
