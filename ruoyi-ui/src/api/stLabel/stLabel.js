import request from '@/utils/request'

// 查询ST标注列表
export function listStLabel(query) {
  return request({
    url: '/stLabel/stLabel/list',
    method: 'get',
    params: query
  })
}

// 查询ST标注详细
export function getStLabel(logId) {
  return request({
    url: '/stLabel/stLabel/' + logId,
    method: 'get'
  })
}

// 新增ST标注
export function addStLabel(data) {
  return request({
    url: '/stLabel/stLabel',
    method: 'post',
    data: data
  })
}

// 修改ST标注
export function updateStLabel(data) {
  return request({
    url: '/stLabel/stLabel',
    method: 'put',
    data: data
  })
}

// 删除ST标注
export function delStLabel(logId) {
  return request({
    url: '/stLabel/stLabel/' + logId,
    method: 'delete'
  })
}
