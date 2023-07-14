import request from '@/utils/request'

export function list12Alert_log(query) {
  return request({
    url: '/DREarly/dr12OrSingle',
    method: 'get',
    params: query,
    timeout: 200000
  })
}
export function listAlert_log(query) {
  return request({
    url: '/DREarly/earlyAll',
    method: 'get',
    params: query,
    timeout: 200000
  })
}
