import request from '@/utils/request'

//
export function proxyRequest(query) {
  return request({
    url: '/proxyRequest/proxyRequest',
    method: 'get',
    params: query
  })
}
