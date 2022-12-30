import request from '@/utils/request'

// 查询预警日志列表
export function listAlert_log(query) {
  return request({
    url: '/alert_log/alert_log/list',
    method: 'get',
    params: query,
    timeout: 200000
  })
}

// 查询预警日志详细
export function getAlert_log(logId) {
  return request({
    url: '/alert_log/alert_log/' + logId,
    method: 'get'
  })
}

// 新增预警日志
export function addAlert_log(data) {
  return request({
    url: '/alert_log/alert_log',
    method: 'post',
    data: data
  })
}

// 修改预警日志
export function updateAlert_log(data) {
  return request({
    url: '/alert_log/alert_log',
    method: 'put',
    data: data
  })
}

// 删除预警日志
export function delAlert_log(logId) {
  return request({
    url: '/alert_log/alert_log/' + logId,
    method: 'delete'
  })
}


// export function assigned_ano(data) {
//   return request({
//     url: '/alert_log/alert_log/assignedAno',
//     method: 'post',
//     data: data,
//     timeout: 200000
//   })
// }
