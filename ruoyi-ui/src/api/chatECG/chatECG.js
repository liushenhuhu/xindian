import request from '@/utils/request'

//
export function proxyRequest(data) {
  //console.log(query)
  return request({
    url: '/proxyRequest/proxyRequest',
    method: 'post',
    params: data,
  })
}
