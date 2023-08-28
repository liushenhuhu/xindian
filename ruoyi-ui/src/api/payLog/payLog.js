import request from '@/utils/request'

// 查询支付日志列表
export function listInfo(query) {
  return request({
    url: '/payLog/payLog/list',
    method: 'get',
    params: query
  })
}

// 查询支付日志详细
export function getInfo(id) {
  return request({
    url: '/payLog/payLog/' + id,
    method: 'get'
  })
}

// 新增支付日志
export function addInfo(data) {
  return request({
    url: '/payLog/payLog',
    method: 'post',
    data: data
  })
}

// 修改支付日志
export function updateInfo(data) {
  return request({
    url: '/payLog/payLog',
    method: 'put',
    data: data
  })
}

// 删除支付日志
export function delInfo(id) {
  return request({
    url: '/payLog/payLog/' + id,
    method: 'delete'
  })
}
