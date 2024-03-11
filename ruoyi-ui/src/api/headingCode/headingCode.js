import request from '@/utils/request'

// 查询善行设备管理列表
export function listHeadingCode(query) {
  return request({
    url: '/headingCode/headingCode/list',
    method: 'get',
    params: query
  })
}

// 查询善行设备管理详细
export function getHeadingCode(codeId) {
  return request({
    url: '/headingCode/headingCode/' + codeId,
    method: 'get'
  })
}

// 新增善行设备管理
export function addHeadingCode(data) {
  return request({
    url: '/headingCode/headingCode',
    method: 'post',
    data: data
  })
}

// 修改善行设备管理
export function updateHeadingCode(data) {
  return request({
    url: '/headingCode/headingCode',
    method: 'put',
    data: data
  })
}

// 删除善行设备管理
export function delHeadingCode(codeId) {
  return request({
    url: '/headingCode/headingCode/' + codeId,
    method: 'delete'
  })
}
