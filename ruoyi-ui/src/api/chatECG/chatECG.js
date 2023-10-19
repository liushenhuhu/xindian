import request from '@/utils/request'

//
export function proxyRequest(data) {
  //console.log(query)
  return request({
    url: '/proxyRequest/proxyRequest',
    method: 'post',
    data: data,
  })
}
export function getConversation() {
  //console.log(query)
  return request({
    url: '/proxyRequest/getConversation',
    method: 'get',
  })
}

export function deleteConversation(data) {
  //console.log(query)
  return request({
    url: '/proxyRequest/deleteConversation?conversationId='+data,
    method: 'delete',
  })
}


export function updateConversation(data) {
  return request({
    url: '/proxyRequest/updateConversation',
    method: 'put',
    data: data
  })
}


export function getChatQuizList(data) {
  //console.log(query)
  return request({
    url: '/proxyRequest/getChatQuizList?conversationId='+data,
    method: 'get',
  })
}
