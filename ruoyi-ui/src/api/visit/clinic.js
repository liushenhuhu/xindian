import request from '@/utils/request'

// 查询诊室信息 列表
export function listClinic(query) {
  return request({
    url: '/hospital/hospitalClinic/list',
    method: 'get',
    params: query
  })
}

// 查询诊室信息 详细
export function getClinic(id) {
  return request({
    url: '/hospital/hospitalClinic/' + id,
    method: 'get'
  })
}

// 新增诊室信息
export function addClinic(data) {
  return request({
    url: '/hospital/hospitalClinic',
    method: 'post',
    data: data
  })
}

// 修改诊室信息
export function updateClinic(data) {
  return request({
    url: '/hospital/hospitalClinic',
    method: 'put',
    data: data
  })
}

// 删除诊室信息
export function delClinic(id) {
  return request({
    url: '/hospital/hospitalClinic/' + id,
    method: 'delete'
  })
}
