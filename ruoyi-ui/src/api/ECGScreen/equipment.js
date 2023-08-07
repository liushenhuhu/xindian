import request from '@/utils/request'

// 查询所有设备连接信息
export function list(currentpage,ts) {
  return request({
    url: 'https://server.mindyard.cn:84/detect_decg',
    method: 'post',
    contentType: "application/json",
    dataType: "json",
    headers: {
      "user": "zzu",
      "password": "zzu123"
    },
    data: JSON.stringify({
        "deviceSn": currentpage,//设备号
        "ts": ts,//时间段
      }

    ),
  })
}
export function get_device() {
  return request({
    url: 'https://server.mindyard.cn:84/get_device',
    method: 'post',
    contentType: "application/json",
    dataType: "json",
    headers: {
      "user": "zzu",
      "password": "zzu123"
    },
    async: false,
    data: JSON.stringify({
      "ts": 0,
      "hospName":"所有"
      }
    ),
  })
}



