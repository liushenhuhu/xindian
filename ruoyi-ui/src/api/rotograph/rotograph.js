import request from '@/utils/request'

// 查询轮播广告列表
export function listRotograph(query) {
  return request({
    url: '/rotograph/rotograph/list',
    method: 'get',
    params: query
  })
}

// 查询轮播广告详细
export function getRotograph(id) {
  return request({
    url: '/rotograph/rotograph/' + id,
    method: 'get'
  })
}

// 新增轮播广告
export function addRotograph(data) {
  return request({
    url: '/rotograph/rotograph',
    method: 'post',
    data: data
  })
}

// 修改轮播广告
export function updateRotograph(data) {
  return request({
    url: '/rotograph/rotograph',
    method: 'put',
    data: data
  })
}

// 删除轮播广告
export function delRotograph(id) {
  return request({
    url: '/rotograph/rotograph/' + id,
    method: 'delete'
  })
}
