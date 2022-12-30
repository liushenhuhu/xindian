import request from '@/utils/request'

// 查询ano列表
export function listAno(query) {
  return request({
    url: '/ano/ano/list',
    method: 'get',
    params: query
  })
}

// 查询ano详细
export function getAno(userId) {
  return request({
    url: '/ano/ano/' + userId,
    method: 'get'
  })
}

// 新增ano
export function addAno(data) {
  return request({
    url: '/ano/ano',
    method: 'post',
    data: data
  })
}

// 修改ano
export function updateAno(data) {
  return request({
    url: '/ano/ano',
    method: 'put',
    data: data
  })
}

// 删除ano
export function delAno(userId) {
  return request({
    url: '/ano/ano/' + userId,
    method: 'delete'
  })
}

export function assigned_ano(data) {
  return request({
    url: '/ano/ano/assignedAno',
    method: 'post',
    data: data,
    timeout: 200000
  })
}
