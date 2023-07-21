// 查询
import request from "@/utils/request";

export function listTypeStatistics(query) {
  return request({
    url: '/statistics/statistics/typeList',
    method: 'get',
    params: query
  })
}
