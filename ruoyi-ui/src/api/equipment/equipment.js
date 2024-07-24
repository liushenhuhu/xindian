import request from '@/utils/request'

// 查询设备列表
export function listEquipment(query) {
  return request({
    url: '/equipment/equipment/list',
    method: 'get',
    params: query
  })
}
export function ecg_use(query) {
  return request({
    url: '/equipment/equipment/ecg_use',
    method: 'get',
    params: query
  })
}
export function getEquipmentList() {
  return request({
    url: '/equipment/equipment/getEquipmentList',
    method: 'get',
  })
}
// 查询设备详细
export function getEquipment(equipmentId) {
  return request({
    url: '/equipment/equipment/' + equipmentId,
    method: 'get'
  })
}

// 新增设备
export function addEquipment(data) {
  return request({
    url: '/equipment/equipment',
    method: 'post',
    data: data
  })
}

// 修改设备
export function updateEquipment(data) {
  return request({
    url: '/equipment/equipment',
    method: 'put',
    data: data
  })
}

// 删除设备
export function delEquipment(equipmentId) {
  return request({
    url: '/equipment/equipment/' + equipmentId,
    method: 'delete'
  })
}

export function updateEquipmentStatus(data) {
  return request({
    url: '/equipment/equipment/updateEquipmentStatus',
    method: 'post',
    data: data,
    timeout: 100000
  })
}


