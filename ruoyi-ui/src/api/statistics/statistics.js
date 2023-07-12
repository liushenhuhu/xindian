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
