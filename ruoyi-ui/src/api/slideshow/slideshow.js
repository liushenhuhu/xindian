import request from '@/utils/request'

// 查询首页轮播图列表
export function listSlideshow(query) {
  return request({
    url: '/slideshow/slideshow/list',
    method: 'get',
    params: query
  })
}

// 查询首页轮播图详细
export function getSlideshow(slideshowId) {
  return request({
    url: '/slideshow/slideshow/' + slideshowId,
    method: 'get'
  })
}

// 新增首页轮播图
export function addSlideshow(data) {
  return request({
    url: '/slideshow/slideshow',
    method: 'post',
    data: data
  })
}

// 修改首页轮播图
export function updateSlideshow(data) {
  return request({
    url: '/slideshow/slideshow',
    method: 'put',
    data: data
  })
}

// 删除首页轮播图
export function delSlideshow(slideshowId) {
  return request({
    url: '/slideshow/slideshow/' + slideshowId,
    method: 'delete'
  })
}
