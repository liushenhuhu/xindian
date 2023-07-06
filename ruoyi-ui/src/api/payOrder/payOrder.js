import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listInfo(query) {
  return request({
    url: '/webOrder/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getInfo(id) {
  return request({
    url: '/webOrder/query/' + id,
    method: 'get'
  })
}


export function address(regionParentId,level) {
  return request({
    url: '/webOrder/address?regionParentId=' + regionParentId+'&level='+level,
    method: 'get'
  })
}
// 新增【请填写功能名称】
export function addInfo(data) {
  return request({
    url: '/system/info',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateInfo(data) {
  return request({
    url: '/system/info',
    method: 'put',
    data: data
  })
}



export function updateAddressInfo(data) {
  console.log(data)
  return request({
    url: '/webOrder/addressAdd',
    method: 'post',
    data: data
  })
}

// 删除【请填写功能名称】
export function delInfo(id) {
  return request({
    url: '/system/info/' + id,
    method: 'delete'
  })
}
