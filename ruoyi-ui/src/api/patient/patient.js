import request from '@/utils/request'

// 查询患者列表
export function listPatient(query) {
  return request({
    url: '/patient/patient/list',
    method: 'get',
    params: query,
    timeout: 100000
  })
}

// 查询患者详细
export function getPatient(patientId) {
  return request({
    url: '/patient/patient/' + patientId,
    method: 'get'
  })
}

// 查询患者详细
export function getPatientByPatientNumber(patientNumber) {
  return request({
    url: '/patient/patient/getInfoByPatientNumber/' + patientNumber,
    method: 'get'
  })
}

export function getPatientByCode(query) {
  return request({
    url: '/patient/patient/getPatientByCode/' ,
    method: 'get',
    params: query,
  })
}

// 查询患者详细
export function getPatientByPatientName(patientName) {
  return request({
    url: '/patient/patient/getInfoByPatientName/' + patientName,
    method: 'get'
  })
}

// 新增患者
export function addPatient(data) {
  return request({
    url: '/patient/patient',
    method: 'post',
    data: data
  })
}

// 修改患者
export function updatePatient(data) {
  return request({
    url: '/patient/patient',
    method: 'put',
    data: data
  })
}

// 删除患者
export function delPatient(patientId) {
  return request({
    url: '/patient/patient/' + patientId,
    method: 'delete'
  })
}

export function updateMonitoringStatus(data) {
  return request({
    url: '/patient/patient/updateMonitoringStatus',
    method: 'post',
    data: data,
    timeout: 100000
  })
}

export function addDict() {
  return request({
    url: '/patient/patient/addDict',
    method: 'get'
  })
}
