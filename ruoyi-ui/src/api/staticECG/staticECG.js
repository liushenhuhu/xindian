import request from '@/utils/request'

// 新增
export function addXArr(data) {
  return request({
    url: '/mark/addStatic',
    method: 'post',
    data: data
  })
}


export function checkByTagId(data) {
  return request({
    url: '/mark/checkMarkByTagId',
    method: 'post',
    data: data
  })
}

