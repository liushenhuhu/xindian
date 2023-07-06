import request from '@/utils/request'

// 查询订单列表
export function listOrder(id) {
  return request({
    url: '/webOrder/ListOrderId?id='+id,
    method: 'get',
  })
}
