import request from '@/utils/request'

// 查询商品信息列表
export function listProduct(query) {
  return request({
    url: '/product/product/web/webList',
    method: 'get',
    params: query
  })
}

// 查询商品信息详细
export function getProduct(productId) {
  return request({
    url: '/product/product/web/getProductInfo/' + productId,
    method: 'get'
  })
}
// 查询商品所有介绍图片
export function getImgs(productId) {
  return request({
    url: '/product/product/web/getImgs/' + productId,
    method: 'get'
  })
}

// 新增商品信息
export function addProduct(data) {
  return request({
    url: '/product/product/web/add',
    method: 'post',
    data: data
  })
}
// 新增商品信息和上传图片
export function addProductAndUpload(data) {
  return request({
    url: '/product/product/web/addUpload',
    method: 'post',
    data: data,
    headers: {
      "Content-Type": "multipart/form-data"
    }
  })
}
// 上传多张商品介绍图片
export function batchUploadFile(data,productId) {
  return request({
    url: '/product/product/web/batchUploadFile/'+ productId,
    method: 'post',
    data: data,
  })
}
// 修改商品信息
export function updateProduct(data) {
  return request({
    url: '/product/product/web/update',
    method: 'post',
    data: data,
    headers: {
      "Content-Type": "multipart/form-data"
    }
  })
}

// 删除商品信息
export function delProduct(productId) {
  return request({
    url: '/product/product/web/' + productId,
    method: 'delete'
  })
}


