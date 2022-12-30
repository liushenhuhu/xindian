import request from '@/utils/request'

// 查询标注分配列表
export function listLog_user(query) {
  return request({
    url: '/log_user/log_user/list',
    method: 'get',
    params: query
  })
}

// 查询标注分配详细
export function getLog_user(logId) {
  return request({
    url: '/log_user/log_user/' + logId,
    method: 'get'
  })
}

// 新增标注分配
export function addLog_user(data) {
  return request({
    url: '/log_user/log_user',
    method: 'post',
    data: data
  })
}

// 修改标注分配
export function updateLog_user(data) {
  return request({
    url: '/log_user/log_user',
    method: 'put',
    data: data
  })
}

// 删除标注分配
export function delLog_user(logId) {
  return request({
    url: '/log_user/log_user/' + logId,
    method: 'delete'
  })
}
