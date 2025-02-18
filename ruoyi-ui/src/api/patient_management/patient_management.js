import request from '@/utils/request'

// 查询患者管理列表
export function listPatient_management(query) {
  return request({
    url: '/patient_management/patient_management/list',
    method: 'get',
    params: query
  })
}

// 查询患者管理详细
export function getPatient_management(pId) {
  return request({
    url: '/patient_management/patient_management/' + pId,
    method: 'get'
  })
}

// 新增患者管理
export function addPatient_management(data) {
  return request({
    url: '/patient_management/patient_management',
    method: 'post',
    data: data
  })
}

// 修改患者管理
export function updatePatient_management(data) {
  return request({
    url: '/patient_management/patient_management',
    method: 'put',
    data: data
  })
}

// 删除患者管理
export function delPatient_management(pId) {
  return request({
    url: '/patient_management/patient_management/' + pId,
    method: 'delete'
  })
}

export function download(path) {
  return request({
    url: '/download/' + path,
    method: 'get'
  })
}

export function updateStatus(data) {
  return request({
    url: '/patient_management/patient_management/updateStatus',
    method: 'post',
    data: data,
    timeout: 100000
  })
}

export function getUserInfo() {
  return request({
    url: '/patient_management/patient_management/getUserInfo',
    method: 'get'
  })
}
export function getInfoId(id) {
  return request({
    url: '/patient_management/patient_management/getInfoId?hospitalId='+id,
    method: 'get'
  })
}

export function getInfoIdByHospital(id,token) {
  return request({
    url: '/patient_management/patient_management/getInfoId?hospitalId='+id,
    method: 'get',
    headers: {
      isToken: false,
      "Authorization":`Bearer ${token}`
    },
  })
}

//发送短信
export function sendMsgToPatient(phone) {
  return request({
    url: '/patient_management/patient_management/sendMsg/' + phone,
    method: 'get'
  })
}

export function getSX_PDFByPId(pId) {
  return request({
    url: '/ecg/report/getSX_PDFByPId?pId='+pId,
    method: 'get'
  })
}
export function getPdf(data) {
  return request({
    url: '/pdf/jecgReport/getPdf',
    method: 'post',
    data: data,
  })
}
export function listDoc() {
  return request({
    url: '/patient_management/patient_management/listDoc',
    method: 'get'
  })
}
export function docUpdate(data) {
  return request({
    url: '/report/report/docUpdate',
    method: 'get',
    params:data
  })
}

export function listPatient_TimeList(query) {
  return request({
    url: '/patient_management/patient_management/listPatientTimeList',
    method: 'get',
    params: query
  })
}

export function getEcgType(query) {
  return request({
    url: '/patient_management/patient_management/getEcgType?ecgType='+query,
    method: 'get',
  })
}

export function redisAddData(query) {
  return request({
    url: '/patient_management/patient_management_redis/redisAddData?ecgType='+query,
    method: 'get',
    timeout: 100000
  })
}

