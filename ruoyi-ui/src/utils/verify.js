import request from '@/utils/request'

export const checkPassword = (pwd,callback)=>{
  request({
    url:"",
    method:"post",
    data:{
      // user:Cookie.get(''),
      password:pwd
    }
  }).then(res=>{
    // todo 密码验证
    if(true){
      callback(1)
    }else{
      callback(0)
    }
  })
}
