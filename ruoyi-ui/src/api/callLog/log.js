import request from '@/utils/request'

// 查询【通话记录】列表
export function pagelist(query) {
  return request({
    url: '/callLog/callLog/list',
    method: 'get',
    params: query
  })
}
export function weblist(deviceSn) {
  return request({
    url: '/callLog/callLog/web/list/',
    method: 'get',
    params: deviceSn
  })
}
// 查询【通话记录】详细
export function getLog(id) {
  return request({
    url: '/callLog/callLog/' + id,
    method: 'get'
  })
}

// 新增【通话记录】
export function addLog(data) {
  return request({
    url: '/callLog/callLog',
    method: 'post',
    data: data
  })
}

// 修改【通话记录】
export function updateLog(data) {
  return request({
    url: '/callLog/callLog',
    method: 'put',
    data: data
  })
}

// 删除【通话记录】
export function delLog(id) {
  return request({
    url: '/callLog/callLog/' + id,
    method: 'delete'
  })
}
