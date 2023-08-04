import request from '@/utils/request'

// 查询医生列表
export function listDoctor(query) {
  return request({
    url: '/doctor/doctor/list',
    method: 'get',
    params: query
  })
}

export function listDoctorName() {
  return request({
    url: '/doctor/doctor/nameList',
    method: 'get',
    dataType:"JSON",
  })
}

// 查询医生详细
export function getDoctor(doctorId) {
  return request({
    url: '/doctor/doctor/' + doctorId,
    method: 'get'
  })
}

// 新增医生
export function addDoctor(data) {
  return request({
    url: '/doctor/doctor',
    method: 'post',
    data: data
  })
}

// 修改医生
export function updateDoctor(data) {
  return request({
    url: '/doctor/doctor',
    method: 'put',
    data: data
  })
}

// 删除医生
export function delDoctor(doctorId) {
  return request({
    url: '/doctor/doctor/' + doctorId,
    method: 'delete'
  })
}

export function hospitalCodeFind(doctorId) {
  return request({
    url: '/department/department/hospitalCodeFind/' + doctorId,
    method: 'get'
  })
}

