import request from '@/utils/request'

// 查询地址管理列表
export function listShipAddress(query) {
  return request({
    url: '/shipAddress/shipAddress/webList',
    method: 'get',
    params: query
  })
}

// 查询地址管理详细
export function getShipAddress(addressId) {
  return request({
    url: '/shipAddress/shipAddress/' + addressId,
    method: 'get'
  })
}

// 新增地址管理
export function addShipAddress(data) {
  return request({
    url: '/shipAddress/shipAddress/add',
    method: 'post',
    data: data
  })
}

// 修改地址管理
export function updateShipAddress(data) {
  return request({
    url: '/shipAddress/shipAddress',
    method: 'put',
    data: data
  })
}

// 删除地址管理
export function delShipAddress(addressId) {
  return request({
    url: '/shipAddress/shipAddress/' + addressId,
    method: 'put'
  })
}
