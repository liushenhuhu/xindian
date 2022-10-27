import request from '@/utils/request'


export function pdfDownload(data) {
  return request({
    url: '/pdf/upload',
    method: 'post',
    data: data
  })
}


