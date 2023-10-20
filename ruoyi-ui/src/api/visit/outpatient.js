import request from '@/utils/request'

// 查询医院门诊 列表
export function listOutpatient(query) {
  return request({
    url: '/hospital/hospitalOutpatient/list',
    method: 'get',
    params: query
  })
}

// 查询医院门诊 详细
export function getOutpatient(id) {
  return request({
    url: '/hospital/hospitalOutpatient/' + id,
    method: 'get'
  })
}

// 新增医院门诊
export function addOutpatient(data) {
  return request({
    url: '/hospital/hospitalOutpatient',
    method: 'post',
    data: data
  })
}

// 修改医院门诊
export function updateOutpatient(data) {
  return request({
    url: '/hospital/hospitalOutpatient',
    method: 'put',
    data: data
  })
}

// 删除医院门诊
export function delOutpatient(id) {
  return request({
    url: '/hospital/hospitalOutpatient/' + id,
    method: 'delete'
  })
}
// 查询医院门诊列表
export function getOutpatientList() {
  return request({
    url: '/hospital/hospitalOutpatient/getOutpatientList' ,
    method: 'get'
  })
}
