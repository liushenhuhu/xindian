import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listInvoice(query) {
  return request({
    url: '/invoice/invoice/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getInvoice(orderId) {
  return request({
    url: '/invoice/invoice/' + orderId,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addInvoice(data) {
  return request({
    url: '/invoice/invoice',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateInvoice(data) {
  return request({
    url: '/invoice/invoice',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delInvoice(orderId) {
  return request({
    url: '/system/invoice/' + orderId,
    method: 'delete'
  })
}

export function uploadInvoice(data,id) {
  return request({
    url: '/invoice/invoice/upload/'+id,
    method: 'post',
    data:data,
    headers: {
      "Content-Type": "multipart/form-data"
    }
  })
}
