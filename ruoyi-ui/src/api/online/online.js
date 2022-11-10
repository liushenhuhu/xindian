import request from '@/utils/request'


export function updateOnline1() {
  return request({
    url: '/getOnline/update1',
    method: 'get',
    timeout: 100000
  })
}


export function updateOnline2() {
  return request({
    url: '/getOnline/update2',
    method: 'get',
    timeout: 100000
  })
}

export function updateOnlineAll() {
  return request({
    url: '/getOnline/updateAll',
    method: 'get',
    timeout: 100000
  })
}
