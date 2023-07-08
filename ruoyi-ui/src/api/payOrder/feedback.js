import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listFeedback(query) {
  return request({
    url: '/feedback/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getFeedback(id) {
  return request({
    url: '/feedback/' + id,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addFeedback(data) {
  return request({
    url: '/feedback',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateFeedback(data) {
  return request({
    url: '/feedback',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delFeedback(feedbackiId) {
  return request({
    url: '/feedback/' + feedbackiId,
    method: 'delete'
  })
}
export function Payback(data) {
  return request({
    url: '/api/v1/refund',
    method: 'get',
    params:data
  })
}
