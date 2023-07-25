import request from '@/utils/request'

//
export function proxyRequest(query) {
  return request({
    url: '/proxyRequest/proxyRequest?str='+query,
    method: 'get',
    //params: query
  })
}
