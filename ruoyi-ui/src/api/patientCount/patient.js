import request from '@/utils/request'

// 查询用户服务列表
export function listPatient(query) {
  return request({
    url: '/patientCount/patientCount/list',
    method: 'get',
    params: query
  })
}

// 查询用户服务详细
export function getPatient(id) {
  return request({
    url: '/patientCount/patientCount/' + id,
    method: 'get'
  })
}

// 新增用户服务
export function addPatient(data) {
  return request({
    url: '/patientCount/patientCount',
    method: 'post',
    data: data
  })
}

// 修改用户服务
export function updatePatient(data) {
  return request({
    url: '/patientCount/patientCount',
    method: 'put',
    data: data
  })
}

// 删除用户
export function delPatient(id) {
  return request({
    url: '/patientCount/patientCount' + id,
    method: 'delete'
  })
}
