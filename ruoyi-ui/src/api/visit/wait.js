import request from '@/utils/request'

// 查询门诊候诊列表
export function listWait(query) {
  return request({
    url: '/hospital/wait/list',
    method: 'get',
    params: query
  })
}

export function accomplishStatus(query) {
  return request({
    url: '/hospital/wait/accomplishStatus',
    method: 'get',
    params: query
  })
}
// 查询门诊候诊详细
export function getWait(waitId) {
  return request({
    url: '/hospital/wait/' + waitId,
    method: 'get'
  })
}

// 新增门诊候诊
export function addWait(data) {
  return request({
    url: '/hospital/wait',
    method: 'post',
    data: data
  })
}

// 修改门诊候诊
export function updateWait(data) {
  return request({
    url: '/hospital/wait',
    method: 'put',
    data: data
  })
}

// 删除门诊候诊
export function delWait(waitId) {
  return request({
    url: '/hospital/wait/' + waitId,
    method: 'delete'
  })
}
