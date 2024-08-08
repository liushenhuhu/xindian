import request from '@/utils/request'

// 查询善行单导心电贴列表
export function listSx_single(query) {
  return request({
    url: '/sx_single/sx_single/list',
    method: 'get',
    params: query
  })
}

// 查询善行单导心电贴详细
export function getSx_single(sxReportId) {
  return request({
    url: '/sx_single/sx_single/' + sxReportId,
    method: 'get'
  })
}

// 新增善行单导心电贴
export function addSx_single(data) {
  return request({
    url: '/sx_single/sx_single',
    method: 'post',
    data: data
  })
}

// 修改善行单导心电贴
export function updateSx_single(data) {
  return request({
    url: '/sx_single/sx_single',
    method: 'put',
    data: data
  })
}

// 删除善行单导心电贴
export function delSx_single(sxReportId) {
  return request({
    url: '/sx_single/sx_single/' + sxReportId,
    method: 'delete'
  })
}
