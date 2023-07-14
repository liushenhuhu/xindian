import request from '@/utils/request'

// 查询医院列表
export function listHospital(query) {
  return request({
    url: '/hospital/hospitalList/list',
    method: 'get',
    params: query
  })
}

// 删除医院
export function delHospital(hospitalId) {
  return request({
    url: '/hospital/hospital/' + hospitalId,
    method: 'delete'
  })
}

