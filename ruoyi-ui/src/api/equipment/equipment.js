import request from '@/utils/request'

// 查询设备管理列表
export function listEquipment(query) {
  return request({
    url: '/equipment/equipment/list',
    method: 'get',
    params: query
  })
}

// 查询设备管理详细
export function getEquipment(equipmentId) {
  return request({
    url: '/equipment/equipment/' + equipmentId,
    method: 'get'
  })
}

// 新增设备管理
export function addEquipment(data) {
  return request({
    url: '/equipment/equipment',
    method: 'post',
    data: data
  })
}

// 修改设备管理
export function updateEquipment(data) {
  return request({
    url: '/equipment/equipment',
    method: 'put',
    data: data
  })
}

// 删除设备管理
export function delEquipment(equipmentId) {
  return request({
    url: '/equipment/equipment/' + equipmentId,
    method: 'delete'
  })
}
