import request from '@/utils/request'

// 查询病数据列表
export function listMedicalData(query) {
  return request({
    url: '/medicalData/medicalData/list',
    method: 'get',
    params: query
  })
}

// 查询病数据详细
export function getMedicalData(medicalDataId) {
  return request({
    url: '/medicalData/medicalData/' + medicalDataId,
    method: 'get'
  })
}

// 新增病数据
export function addMedicalData(data) {
  return request({
    url: '/medicalData/medicalData',
    method: 'post',
    data: data
  })
}

// 修改病数据
export function updateMedicalData(data) {
  return request({
    url: '/medicalData/medicalData',
    method: 'put',
    data: data
  })
}

// 删除病数据
export function delMedicalData(medicalDataId) {
  return request({
    url: '/medicalData/medicalData/' + medicalDataId,
    method: 'delete'
  })
}
