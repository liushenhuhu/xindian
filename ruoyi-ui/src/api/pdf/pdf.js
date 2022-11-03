import request from '@/utils/request'


export function pdfDownload(data) {
  return request({
    url: '/pdf/upload',
    method: 'post',
    data: data,
    timeout: 10000
  })
}


export function pdfDownload2(data) {
  return request({
    url: '/pdf/file/uploadPdf',
    method: 'post',
    data: data,
    timeout: 10000
  })
}
