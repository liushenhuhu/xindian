import request from '@/utils/request'

// 查询消息存储列表
export function listMessage(query) {
  return request({
    url: '/message/message/list',
    method: 'get',
    params: query
  })
}

// 查询消息存储详细
export function getMessage(id) {
  return request({
    url: '/message/message/' + id,
    method: 'get'
  })
}

// 新增消息存储
export function addMessage(data) {
  return request({
    url: '/message/message',
    method: 'post',
    data: data
  })
}

// 修改消息存储
export function updateMessage(data) {
  return request({
    url: '/message/message',
    method: 'put',
    data: data
  })
}

// 删除消息存储
export function delMessage(id) {
  return request({
    url: '/message/message/' + id,
    method: 'delete'
  })
}
