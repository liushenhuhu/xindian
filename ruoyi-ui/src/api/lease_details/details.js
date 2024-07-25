import request from '@/utils/request'

// 查询租赁详情列表
export function listDetails(query) {
  return request({
    url: '/lease_details/details/list',
    method: 'get',
    params: query
  })
}

// 查询租赁详情详细
export function getDetails(leaseDetailsId) {
  return request({
    url: '/lease_details/details/' + leaseDetailsId,
    method: 'get'
  })
}

// 新增租赁详情
export function addDetails(data) {
  return request({
    url: '/lease_details/details',
    method: 'post',
    data: data
  })
}

// 修改租赁详情
export function updateDetails(data) {
  return request({
    url: '/lease_details/details',
    method: 'put',
    data: data
  })
}

// 删除租赁详情
export function delDetails(leaseDetailsId) {
  return request({
    url: '/lease_details/details/' + leaseDetailsId,
    method: 'delete'
  })
}
