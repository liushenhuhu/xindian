import request from '@/utils/request'

// 查询报告管理列表
export function listInform(query) {
  return request({
    url: '/inform/inform/list',
    method: 'get',
    params: query
  })
}

// 查询报告管理详细
export function getInform(informId) {
  return request({
    url: '/inform/inform/' + informId,
    method: 'get'
  })
}

// 新增报告管理
export function addInform(data) {
  return request({
    url: '/inform/inform',
    method: 'post',
    data: data
  })
}

// 修改报告管理
export function updateInform(data) {
  return request({
    url: '/inform/inform',
    method: 'put',
    data: data
  })
}

// 删除报告管理
export function delInform(informId) {
  return request({
    url: '/inform/inform/' + informId,
    method: 'delete'
  })
}
