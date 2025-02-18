import request from '@/utils/request'

// 查询ano列表
export function listAno(query) {
  return request({
    url: '/ano/ano/list',
    method: 'get',
    params: query
  })
}


//去重userid查询
export function listAno2(query) {
  return request({
    url: '/ano/ano/list2',
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
export function delAno2(data) {
  return request({
    url: '/ano/ano/del',
    method: 'delete',
    data:data
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

export function re_assigned_ano(data) {
  return request({
    url: '/ano/ano/reAssignedAno',
    method: 'post',
    data: data,
    timeout: 200000
  })
}
//查询未分配的pid列表
export function getNotAssign(query) {
  return request({
    url: '/ano/ano/getNotAssign',
    method: 'get',
    params: query
  })
}
export function assignedAnoList(data) {
  return request({
    url: '/ano/ano/assignedAnoList',

    method: 'post',
    data: data,
    timeout: 200000
  })
}

export function addAnoAdmin(data) {
  return request({
    url: '/ano/admin',
    method: 'post',
    data: data,
    timeout: 200000
  })
}

export function getAnoList(query) {
  return request({
    url: '/ano/admin/list',
    method: 'get',
    params: query
  })
}

export function getAnoAdmin(query) {
  return request({
    url: '/ano/admin/getAnoAdmin',
    method: 'get',
    params: query
  })
}
export function delAnoAdmin(anoAdminId) {
  return request({
    url: '/ano/admin/'+anoAdminId,
    method: 'delete',
  })
}
