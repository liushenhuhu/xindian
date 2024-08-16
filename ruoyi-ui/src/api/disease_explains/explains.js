import request from '@/utils/request'

// 查询心电图报告解析说明列表
export function listExplains(query) {
  return request({
    url: '/disease_explains/explains/list',
    method: 'get',
    params: query
  })
}

// 查询心电图报告解析说明详细
export function getExplains(diseaseId) {
  return request({
    url: '/disease_explains/explains/' + diseaseId,
    method: 'get'
  })
}

// 新增心电图报告解析说明
export function addExplains(data) {
  return request({
    url: '/disease_explains/explains',
    method: 'post',
    data: data
  })
}

// 修改心电图报告解析说明
export function updateExplains(data) {
  return request({
    url: '/disease_explains/explains',
    method: 'put',
    data: data
  })
}

// 删除心电图报告解析说明
export function delExplains(diseaseId) {
  return request({
    url: '/disease_explains/explains/' + diseaseId,
    method: 'delete'
  })
}
