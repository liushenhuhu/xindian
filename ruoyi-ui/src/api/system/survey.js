import request from '@/utils/request'

// 查询wSuryvey列表
export function listSurvey(query) {
  return request({
    url: '/survey/list',
    method: 'get',
    params: query
  })
}

// 查询wSuryvey详细
export function getSurvey(id) {
  return request({
    url: '/survey/' + id,
    method: 'get'
  })
}

// 新增wSuryvey
export function addSurvey(data) {
  return request({
    url: '/survey',
    method: 'post',
    data: data
  })
}

// 修改wSuryvey
export function updateSurvey(data) {
  return request({
    url: '/survey',
    method: 'put',
    data: data
  })
}

// 删除wSuryvey
export function delSurvey(id) {
  return request({
    url: '/survey/' + id,
    method: 'delete'
  })
}
