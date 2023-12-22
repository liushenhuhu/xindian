import request from '@/utils/request'

// 查询标注数据审核列表
export function listAudit(query) {
  return request({
    url: '/label/audit/list',
    method: 'get',
    params: query
  })
}

// 查询标注数据审核详细
export function getAudit(logId) {
  return request({
    url: '/label/audit/' + logId,
    method: 'get'
  })
}

// 新增标注数据审核
export function addAudit(data) {
  return request({
    url: '/label/audit',
    method: 'post',
    data: data
  })
}

// 修改标注数据审核
export function updateAudit(data) {
  return request({
    url: '/label/audit',
    method: 'put',
    data: data
  })
}

// 删除标注数据审核
export function delAudit(logId) {
  return request({
    url: '/label/audit/' + logId,
    method: 'delete'
  })
}
