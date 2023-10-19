import request from '@/utils/request'

// 查询医院专科 列表
export function listSpecial(query) {
  return request({
    url: '/hospital/hospitalSpecial/list',
    method: 'get',
    params: query
  })
}

// 查询医院专科 详细
export function getSpecial(id) {
  return request({
    url: '/hospital/hospitalSpecial/' + id,
    method: 'get'
  })
}

// 查询医院专科列表
export function getSpecialList() {
  return request({
    url: '/hospital/hospitalSpecial/getSpecialList' ,
    method: 'get'
  })
}
// 新增医院专科
export function addSpecial(data) {
  return request({
    url: '/hospital/hospitalSpecial',
    method: 'post',
    data: data
  })
}

// 修改医院专科
export function updateSpecial(data) {
  return request({
    url: '/hospital/hospitalSpecial',
    method: 'put',
    data: data
  })
}

// 删除医院专科
export function delSpecial(id) {
  return request({
    url: '/hospital/hospitalSpecial/' + id,
    method: 'delete'
  })
}
