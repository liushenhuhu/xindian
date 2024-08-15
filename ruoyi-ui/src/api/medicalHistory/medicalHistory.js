import request from '@/utils/request'

// 查询病史列表
export function listMedicalHistory(query) {
  return request({
    url: '/medicalHistory/medicalHistory/list',
    method: 'get',
    params: query
  })
}

// 查询病史详细
export function getMedicalHistory(medicalHistoryId) {
  return request({
    url: '/medicalHistory/medicalHistory/' + medicalHistoryId,
    method: 'get'
  })
}

export function getMedicalHistoryByPhone(phone) {
  return request({
    url: '/medicalHistory/medicalHistory/getByPatientPhone/' + phone,
    method: 'get'
  })
}

// 新增病史
export function addMedicalHistory(data) {
  return request({
    url: '/medicalHistory/medicalHistory',
    method: 'post',
    data: data
  })
}

// 修改病史
export function updateMedicalHistory(data) {
  return request({
    url: '/medicalHistory/medicalHistory',
    method: 'put',
    data: data
  })
}
export function updateWeekReport(data) {
  return request({
    url: '/weekReport/report',
    method: 'put',
    data: data
  })
}
export function getWeekReport(medicalHistoryId) {
  return request({
    url: '/weekReport/report/' + medicalHistoryId,
    method: 'get'
  })
}


// 删除病史
export function delMedicalHistory(medicalHistoryId) {
  return request({
    url: '/medicalHistory/medicalHistory/' + medicalHistoryId,
    method: 'delete'
  })
}
