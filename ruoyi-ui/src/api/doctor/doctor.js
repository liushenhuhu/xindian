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
    data: data,
    headers: {
      "Content-Type": "multipart/form-data"
    }
  })
}

// 修改医生
export function updateDoctor(data) {
  return request({
    url: '/doctor/doctor',
    method: 'put',
    data: data,
    headers: {
      "Content-Type": "multipart/form-data"
    }
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
export function hospitalCodeName(name) {
  return request({
    url: '/department/department/hospitalCodeName/' + name,
    method: 'get'
  })
}


export function docList() {
  return request({
    url: '/doctor/doctor/docList',
    method: 'get',
    params: null
  })
}

export function getHospitalVisitDoc(id) {
  return request({
    url: '/doctor/doctor/getHospitalVisitDoc?hospitalId='+id,
    method: 'get',
  })
}
