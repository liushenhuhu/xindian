import request from '@/utils/request'

// 查询6个散点图
export function getHrCount(query) {
  return request({
    url: '/patient_management/patient_management/getHrCount',
    method: 'get',
    params: query
  })
}
