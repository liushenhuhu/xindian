import request from '@/utils/request'

// 查询激活码列表
export function listTable(query) {
  return request({
    url: '/vipcode/vipcode/list',
    method: 'get',
    params: query
  })
}
// 新增激活码
export function insertCode(form,n) {
  return request({
    url: '/vipcode/vipcode/insertCode/'+n,
    method: 'post',
    params: form
  })
}
