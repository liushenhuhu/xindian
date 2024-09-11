import request from '@/utils/request'

// 查询报告自动提交列表
export function listHosp_rela_doctor(query) {
  return request({
    url: '/rela_doctor/hosp_rela_doctor/list',
    method: 'get',
    params: query
  })
}

// 查询报告自动提交详细
export function getHosp_rela_doctor(id) {
  return request({
    url: '/rela_doctor/hosp_rela_doctor/' + id,
    method: 'get'
  })
}

// 新增报告自动提交
export function addHosp_rela_doctor(data) {
  return request({
    url: '/rela_doctor/hosp_rela_doctor',
    method: 'post',
    data: data
  })
}

// 修改报告自动提交
export function updateHosp_rela_doctor(data) {
  return request({
    url: '/rela_doctor/hosp_rela_doctor',
    method: 'put',
    data: data
  })
}

// 删除报告自动提交
export function delHosp_rela_doctor(id) {
  return request({
    url: '/rela_doctor/hosp_rela_doctor/' + id,
    method: 'delete'
  })
}
