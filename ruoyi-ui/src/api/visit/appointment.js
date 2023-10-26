import request from '@/utils/request'

// 查询挂号预约列表
export function listVisitAppointment(query) {
  return request({
    url: '/hospital/visitAppointment/list',
    method: 'get',
    params: query
  })
}

// 查询挂号预约详细
export function getVisitAppointment(appointmentId) {
  return request({
    url: '/hospital/visitAppointment/' + appointmentId,
    method: 'get'
  })
}

// 新增挂号预约
export function addVisitAppointment(data) {
  return request({
    url: '/hospital/visitAppointment',
    method: 'post',
    data: data
  })
}

// 修改挂号预约
export function updateVisitAppointment(data) {
  return request({
    url: '/hospital/visitAppointment',
    method: 'put',
    data: data
  })
}

// 删除挂号预约
export function delVisitAppointment(appointmentId) {
  return request({
    url: '/hospital/visitAppointment/' + appointmentId,
    method: 'delete'
  })
}
