import request from '@/utils/request'

// 查询vip用户列表
export function listPatient(query) {
  return request({
    url: '/vipPatient/patient/list',
    method: 'get',
    params: query
  })
}

// 查询vip用户详细
export function getPatient(id) {
  return request({
    url: '/vipPatient/patient/' + id,
    method: 'get'
  })
}

// 新增vip用户
export function addPatient(data) {
  return request({
    url: '/vipPatient/patient',
    method: 'post',
    data: data
  })
}

// 修改vip用户
export function updatePatient(data) {
  return request({
    url: '/vipPatient/patient',
    method: 'put',
    data: data
  })
}

// 删除vip用户
export function delPatient(id) {
  return request({
    url: '/vipPatient/patient/' + id,
    method: 'delete'
  })
}
