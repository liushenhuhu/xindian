import request from '@/utils/request'

// 查询医院管理列表
export function listHospital_manangment(query) {
  return request({
    url: '/hospital_management/hospital_manangment/list',
    method: 'get',
    params: query
  })
}

// 查询医院管理详细
export function getHospital_manangment(id) {
  return request({
    url: '/hospital_management/hospital_manangment/' + id,
    method: 'get'
  })
}

// 新增医院管理
export function addHospital_manangment(data) {
  return request({
    url: '/hospital_management/hospital_manangment',
    method: 'post',
    data: data
  })
}

// 修改医院管理
export function updateHospital_manangment(data) {
  return request({
    url: '/hospital_management/hospital_manangment',
    method: 'put',
    data: data
  })
}

// 删除医院管理
export function delHospital_manangment(id) {
  return request({
    url: '/hospital_management/hospital_manangment/' + id,
    method: 'delete'
  })
}
