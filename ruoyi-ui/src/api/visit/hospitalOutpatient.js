import request from '@/utils/request'

// 查询医院门诊关系 列表
export function listRelation(query) {
  return request({
    url: '/hospital/hospitalOutpatientRelation/list',
    method: 'get',
    params: query
  })
}

export function getHospitalOutpatientList(query) {
  return request({
    url: '/hospital/hospitalOutpatientRelation/getHospitalOutpatientList',
    method: 'get',
    params: query
  })
}


// 查询医院门诊关系 详细
export function getRelation(id) {
  return request({
    url: '/hospital/hospitalOutpatientRelation/' + id,
    method: 'get'
  })
}

// 新增医院门诊关系
export function addRelation(data) {
  return request({
    url: '/hospital/hospitalOutpatientRelation',
    method: 'post',
    data: data
  })
}

// 修改医院门诊关系
export function updateRelation(data) {
  return request({
    url: '/hospital/hospitalOutpatientRelation',
    method: 'put',
    data: data
  })
}

// 删除医院门诊关系
export function delRelation(id) {
  return request({
    url: '/hospital/hospitalOutpatientRelation/' + id,
    method: 'delete'
  })
}
