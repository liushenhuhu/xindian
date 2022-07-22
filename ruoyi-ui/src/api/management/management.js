import request from '@/utils/request'

// 查询患者管理列表
export function listManagement(query) {
  return request({
    url: '/management/management/list',
    method: 'get',
    params: query
  })
}

// 查询患者管理详细
export function getManagement(id) {
  return request({
    url: '/management/management/' + id,
    method: 'get'
  })
}

// 新增患者管理
export function addManagement(data) {
  return request({
    url: '/management/management',
    method: 'post',
    data: data
  })
}

// 修改患者管理
export function updateManagement(data) {
  return request({
    url: '/management/management',
    method: 'put',
    data: data
  })
}

// 删除患者管理
export function delManagement(id) {
  return request({
    url: '/management/management/' + id,
    method: 'delete'
  })
}
