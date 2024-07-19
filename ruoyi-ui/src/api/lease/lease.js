import request from '@/utils/request'

// 查询租赁列表
export function listLease(query) {
  return request({
    url: '/lease/lease/list',
    method: 'get',
    params: query
  })
}

// 查询租赁详细
export function getLease(leaseId) {
  return request({
    url: '/lease/lease/' + leaseId,
    method: 'get'
  })
}

// 新增租赁
export function addLease(data) {
  return request({
    url: '/lease/lease',
    method: 'post',
    data: data
  })
}

// 修改租赁
export function updateLease(data) {
  return request({
    url: '/lease/lease',
    method: 'put',
    data: data
  })
}

// 删除租赁
export function delLease(leaseId) {
  return request({
    url: '/lease/lease/' + leaseId,
    method: 'delete'
  })
}
