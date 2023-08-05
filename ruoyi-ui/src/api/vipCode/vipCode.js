import request from '@/utils/request'

// 查询激活码列表
export function listTable(query) {
  return request({
    url: '/vipcode/vipcode/web/list',
    method: 'get',
    params: query
  })
}
// 新增激活码
export function insertCode(form,n) {
  return request({
    url: '/vipcode/vipcode/web/insertCode/'+n,
    method: 'post',
    params: form
  })
}
//删除
export function detailById(id) {
  return request({
    url: '/vipcode/vipcode/web/detail/'+id,
    method: 'get',
  })
}
//修改
export function update(form) {
  return request({
    url: '/vipcode/vipcode',
    method: 'put',
    params: form
  })
}
//删除
export function remove(ids) {
  return request({
    url: '/vipcode/vipcode/web/'+ids,
    method: 'delete',
  })
}
