import request from '@/utils/request'

// 新增
export function ecgBeatLabelAdd(data) {
  return request({
    url: '/ecgData/ecgBeatLabelAdd',
    method: 'post',
    data: data
  })
}


export function ecgWaveLabelPut(data) {
  return request({
    url: '/ecgData/ecgWaveLabelPut',
    method: 'post',
    data: data
  })
}
export function put12BeatLabel(data) {
  return request({
    url: '/ecgData/put12BeatLabel',
    method: 'post',
    data: data
  })
}


export function put12WaveLabel(data) {
  return request({
    url: '/ecgData/put12WaveLabel',
    method: 'post',
    data: data
  })
}
export function getTerm() {
  return request({
    url: '/term/term/list',
    method: 'get',
  })
}
export function addOrUpdateTerm(data) {
  return request({
    url: '/term/term',
    method: 'put',
    data: data
  })
}


