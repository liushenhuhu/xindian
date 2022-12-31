import request from '@/utils/request'



// 查询标注分配详细
export function getLists(query) {
  return request({
    url: '/mark_info/mark_info/list',
    method: 'get',
    params: query
  })
}
//查询user
export function getUsers() {
  return request({
    url: '/mark_info/mark_info/users',
    method: 'get'
  })
}
