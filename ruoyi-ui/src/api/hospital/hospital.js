import request from '@/utils/request'

// 查询医院列表
export function listHospital(query) {
  return request({
    url: '/hospital/hospital/list',
    method: 'get',
    params: query
  })
}

// 查询医院详细
export function getHospital(hospitalId) {
  return request({
    url: '/hospital/hospital/' + hospitalId,
    method: 'get'
  })
}

// 查询医院详细
export function getHospitalByHospitalCode(hospitalCode) {
  return request({
    url: '/hospital/hospital/getInfoByHospitalCode' + hospitalCode,
    method: 'get'
  })
}

// 新增医院
export function addHospital(data) {
  return request({
    url: '/hospital/hospital',
    method: 'post',
    data: data
  })
}

// 修改医院
export function updateHospital(data) {
  return request({
    url: '/hospital/hospital',
    method: 'put',
    data: data
  })
}

// 删除医院
export function delHospital(hospitalId) {
  return request({
    url: '/hospital/hospital/' + hospitalId,
    method: 'delete'
  })
}
