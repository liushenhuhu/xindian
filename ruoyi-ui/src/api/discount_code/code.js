import request from '@/utils/request'

// 查询医师优惠列表
export function listCode(query) {
  return request({
    url: '/discount_code/code/list',
    method: 'get',
    params: query
  })
}

// 查询医师优惠详细
export function getCode(promotionId) {
  return request({
    url: '/discount_code/code/' + promotionId,
    method: 'get'
  })
}

// 新增医师优惠
export function addCode(data) {
  return request({
    url: '/discount_code/code',
    method: 'post',
    data: data
  })
}

// 修改医师优惠
export function updateCode(data) {
  return request({
    url: '/discount_code/code',
    method: 'put',
    data: data
  })
}

// 删除医师优惠
export function delCode(promotionId) {
  return request({
    url: '/discount_code/code/' + promotionId,
    method: 'delete'
  })
}
