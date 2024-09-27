import request from '@/utils/request'

// 查询设备归属激活列表
export function listActivate(query) {
  return request({
    url: '/equipment/activate/list',
    method: 'get',
    params: query
  })
}

// 查询设备归属激活详细
export function getActivate(equipmentId) {
  return request({
    url: '/equipment/activate/' + equipmentId,
    method: 'get'
  })
}

// 新增设备归属激活
export function addActivate(data) {
  return request({
    url: '/equipment/activate',
    method: 'post',
    data: data
  })
}

// 修改设备归属激活
export function updateActivate(data) {
  return request({
    url: '/equipment/activate',
    method: 'put',
    data: data
  })
}

// 删除设备归属激活
export function delActivate(equipmentId) {
  return request({
    url: '/equipment/activate/' + equipmentId,
    method: 'delete'
  })
}
