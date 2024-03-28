import request from '@/utils/request'

// 查询预警类型统计列表
export function listCount(query) {
  return request({
    url: '/alert_log_count/count/list',
    method: 'get',
    params: query
  })
}

// 查询预警类型统计详细
export function getCount(countId) {
  return request({
    url: '/alert_log_count/count/' + countId,
    method: 'get'
  })
}

// 新增预警类型统计
export function addCount(data) {
  return request({
    url: '/alert_log_count/count',
    method: 'post',
    data: data
  })
}

// 修改预警类型统计
export function updateCount(data) {
  return request({
    url: '/alert_log_count/count',
    method: 'put',
    data: data
  })
}

// 删除预警类型统计
export function delCount(countId) {
  return request({
    url: '/alert_log_count/count/' + countId,
    method: 'delete'
  })
}
export function getLogType() {
  return request({
    url: '/alert_log_count/count/getLogType',
    method: 'get'
  })
}

