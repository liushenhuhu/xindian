import request from '@/utils/request'

// 查询vip用户列表
export function listPatient(query) {
  return request({
    url: '/patientCount/patientCount/list',
    method: 'get',
    params: query
  })
}

// 查询vip用户详细
export function getPatient(id) {
  return request({
    url: '/patientCount/patientCount/' + id,
    method: 'get'
  })
}

// 新增vip用户
export function addPatient(data) {
  return request({
    url: '/patientCount/patientCount',
    method: 'post',
    data: data
  })
}

// 修改vip用户
export function updatePatient(data) {
  return request({
    url: '/patientCount/patientCount',
    method: 'put',
    data: data
  })
}

// 删除vip用户
export function delPatient(id) {
  return request({
    url: '/patientCount/patientCount' + id,
    method: 'delete'
  })
}
