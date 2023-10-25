import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listRelation(query) {
  return request({
    url: '/hospital/hospitalSpecialRelation/list',
    method: 'get',
    params: query
  })
}

export function getHospitalSpecialList(query) {
  return request({
    url: '/hospital/hospitalSpecialRelation/getHospitalSpecialList',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getRelation(id) {
  return request({
    url: '/hospital/hospitalSpecialRelation/' + id,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addRelation(data) {
  return request({
    url: '/hospital/hospitalSpecialRelation',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateRelation(data) {
  return request({
    url: '/hospital/hospitalSpecialRelation',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delRelation(id) {
  return request({
    url: '/hospital/hospitalSpecialRelation/' + id,
    method: 'delete'
  })
}
