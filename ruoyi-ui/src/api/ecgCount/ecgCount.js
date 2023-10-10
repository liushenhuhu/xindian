import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listCount(query) {
  return request({
    url: '/ecgCount/ecgCount/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getCount(id) {
  return request({
    url: '/ecgCount/ecgCount/' + id,
    method: 'get'
  })
}
// 查询【请填写功能名称】详细
export function getLogType() {
  return request({
    url: '/ecgCount/ecgCount/getLogType',
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addCount(data) {
  return request({
    url: '/ecgCount/ecgCount',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateCount(data) {
  return request({
    url: '/ecgCount/ecgCount',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delCount(id) {
  return request({
    url: '/ecgCount/ecgCount/' + id,
    method: 'delete'
  })
}

export function ageList() {
  return request({
    url: '/ecgCount/ecgCount/ageList',
    method: 'get'
  })
}

export function getAgeYoung() {
  return request({
    url: '/ecgCount/ecgCount/getAgeYoung',
    method: 'get'
  })
}

export function getTypeList() {
  return request({
    url: '/ecgCount/ecgCount/getTypeList',
    method: 'get'
  })
}
export function getZCXDTCount() {
  return request({
    url: '/ecgCount/ecgCount/getZCXDTCount',
    method: 'get'
  })
}
