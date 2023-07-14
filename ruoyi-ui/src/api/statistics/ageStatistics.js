import request from '@/utils/request'

//查询所有医生
export function selectAge() {
  return request({
    url: '/statistics/statistics/ageList',
    method: 'get',
  })
}
