import request from '@/utils/request'


export function listFwlog(query) {
  return request({
    url: '/fwLog/fwLog/list',
    method: 'get',
    params: query
  })
}


export function getFwlog(id) {
  return request({
    url: '/fwLog/fwLog/' + id,
    method: 'get'
  })
}


export function addFwlog(data) {
  return request({
    url: '/fwLog/fwLog',
    method: 'post',
    data: data
  })
}


export function updateFwlog(data) {
  return request({
    url: '/fwLog/fwLog',
    method: 'put',
    data: data
  })
}


export function delFwlog(id) {
  return request({
    url: '/fwLog/fwLog/' + id,
    method: 'delete'
  })
}
