import request from '@/utils/request'

// 查询门诊预约医院列表
export function listSubscribe(query) {
  return request({
    url: '/hospital/subscribe/webList',
    method: 'get',
    params: query
  })
}

export function getVisitHospitalList() {
  return request({
    url: '/hospital/subscribe/getVisitHospitalList',
    method: 'get'
  })
}
// 查询门诊预约医院详细
export function getSubscribe(subscribeId) {
  return request({
    url: '/hospital/subscribe/' + subscribeId,
    method: 'get'
  })
}

// 新增门诊预约医院
export function addSubscribe(data) {
  return request({
    url: '/hospital/subscribe',
    method: 'post',
    data: data
  })
}

// 修改门诊预约医院
export function updateSubscribe(data) {
  return request({
    url: '/hospital/subscribe',
    method: 'put',
    data: data
  })
}

// 删除门诊预约医院
export function delSubscribe(subscribeId) {
  return request({
    url: '/hospital/subscribe/' + subscribeId,
    method: 'delete'
  })
}
