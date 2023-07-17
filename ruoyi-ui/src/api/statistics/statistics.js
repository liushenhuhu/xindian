import request from '@/utils/request'

//查询所有医生
export function selectDoctor() {
  return request({
    url: '/statistics/statistics/doctor',
    method: 'get',
  })
}

// 查询
export function listStatistics(query) {
  return request({
    url: '/statistics/statistics/list',
    method: 'get',
    params: query
  })
}
// 查询就诊时长
export function dateList(query) {
  return request({
    url: '/statistics/statistics/dateList',
    method: 'get',
    params: query
  })
}

