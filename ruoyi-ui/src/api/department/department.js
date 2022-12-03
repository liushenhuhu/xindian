import request from '@/utils/request'

// 查询科室列表
export function listDepartment(query) {
  return request({
    url: '/department/department/list',
    method: 'get',
    params: query
  })
}

// 查询科室详细
export function getDepartment(departmentId) {
  return request({
    url: '/department/department/' + departmentId,
    method: 'get'
  })
}

// 新增科室
export function addDepartment(data) {
  return request({
    url: '/department/department',
    method: 'post',
    data: data
  })
}

// 修改科室
export function updateDepartment(data) {
  return request({
    url: '/department/department',
    method: 'put',
    data: data
  })
}

// 删除科室
export function delDepartment(departmentId) {
  return request({
    url: '/department/department/' + departmentId,
    method: 'delete'
  })
}
