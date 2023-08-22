import request from '@/utils/request'

// 查询报告列表
export function getreportcount(query) {
  return request({
    url: '/statistics/statistics/getreportcount',
    method: 'get',
    params: query
  })
}


