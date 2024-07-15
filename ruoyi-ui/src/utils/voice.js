let resultText = "";

const CryptoJS = require('crypto-js');

export class Voc {
  appid = 'c8ab39fd'
  api_key = '7c7d3c06870677ba74e678dbb637a299'
  openState = '0'
  timeOutObj = null;
  oneState = 1;

  constructor() {
    this.ws = null;
    console.log("初始化成功");
    this.result = {
      message: null,
      err: null,
      middleMsg: null,
      overMsg: null,
    }
  }

  /**
   * 初始化websocket连接
   */
  init = (ts) => {
    if ("WebSocket" in window) {
      let url = this.linkUrl(ts)
      console.log('地址')
      console.log(url)
      this.ws = new WebSocket(url);
    } else if ("MozWebSocket" in window) {
      let url = this.linkUrl(ts)
      console.log('地址')
      console.log(url)
      this.ws = new MozWebSocket(url);
    } else {
      alert("浏览器不支持WebSocket");
      return;
    }
    this.ws.onopen = () => {
      console.log('连接讯飞科大成功');
    }
    this.ws.onmessage = (data, err) => {
      console.log('收到讯飞科大消息');
      console.log("触发renderResult函数")
      console.log("中的参数data为")
      console.log(data.data)
      this.renderResult(data.data)
      // console.log(JSON.parse(data))
      // if (err) return this.result.err(err)
      // this.result.message(JSON.parse(data))
    }
    this.ws.onclose = () => console.log('讯飞科大连接关闭')
    this.ws.onerror = (err) => {
      this.result.err(err)
      console.log('讯飞科大连接错误')
      this.ws.close()
    }
  }

  /**
   * 发送数据
   * @param data
   */
  send = (data) => {
    this.ws.send(data)
  }
  /**
   * 关闭连接
   */
  close = () => {
    //关闭连接，销毁实例
    console.log('关闭连接')
    this.ws.send('{"end": true}')
    this.ws.close()
    if(this.timeOutObj){
      clearTimeout(this.timeOutObj)
      this.timeOutObj = null
    }
  }
  /**
   * 设置获取最终的消息结果
   * @param func
   */
  onmessage = (func) => {
    this.result.message = func
  }
  onOverMsg = (func) => {
    this.result.overMsg = func
  }
  /**
   * 设置获取中间消息结果
   * @param func
   */
  onmiddlemessage = (func) => {
    this.result.middleMsg = func
  }
  /**
   * 设置错误消息
   * @param func
   */
  setErrorFunc = (func) => {
    this.result.err = func
  }

  renderResult(resultData) {
    console.log("renderResult")
    let th = this;
    let jsonData = JSON.parse(resultData);
    console.log("这是jsonData")
    console.log(jsonData)
    console.log("这是jsonData中的action")
    console.log(jsonData.action)
    if (jsonData.action == "started") {
      // 握手成功
      console.log("握手成功");
    } else if (jsonData.action == "result") {
      console.log("JSON.parse(jsonData.data)")
      const data = JSON.parse(jsonData.data)
      console.log(data)
      // 转写结果
      let resultTextTemp = ""
      data.cn.st.rt.forEach((j) => {
        j.ws.forEach((k) => {
          k.cw.forEach((l) => {
            resultTextTemp += l.w;
          });
        });
      });
      console.log("1这是resultTextTemp")
      console.log(resultTextTemp)
      const arr = ['，', '。', '？', '！', '；',]
      if (arr.includes(resultTextTemp[0]) && th.openState == 0) {
        resultTextTemp = resultTextTemp.slice(1)
      }
      console.log('中间结果：', resultTextTemp)
      if (resultTextTemp.includes('小雅')) {
        if(th.openState == 0 ){

        }
        th.openState = 1;
      }
      if (th.openState != 1) return;

      if(this.timeOutObj){
        clearTimeout(this.timeOutObj)
        this.timeOutObj = null
      }
      if(this.oneState&&resultTextTemp.includes('小雅')){
        this.timeOutObj = setTimeout(() => {
          console.log(th.result.overMsg)
          if(th.result.overMsg){
            th.result.overMsg()
            console.log('1触发发送')
          }
          th.openState =0;
        },10000)
        th.oneState = 0;
        resultTextTemp = resultTextTemp.slice(resultTextTemp.indexOf('小雅')+2)
        console.log("2这是resultTextTemp")
        console.log(resultTextTemp)
        if (arr.includes(resultTextTemp[0])) {
          resultTextTemp = resultTextTemp.slice(1)
          console.log("3这是resultTextTemp")
          console.log(resultTextTemp)
          if(resultTextTemp.length!=0){
            clearTimeout(this.timeOutObj)
            this.timeOutObj = null;
            this.timeOutObj = setTimeout(() => {
              if(th.result.overMsg){
                th.result.overMsg()
                console.log('2触发发送')
              }
              th.openState =0;
              th.oneState = 1;
            },3000)
          }
        }
      }else{
        this.timeOutObj = setTimeout(() => {
          if(th.result.overMsg){
            th.result.overMsg()
            console.log('4触发发送')
          }
          th.openState =0;
          th.oneState = 1;
        },3000)
      }
      if (this.result.middleMsg) {
        this.result.middleMsg(resultTextTemp)
      }
      if (data.cn.st.type == 0) {
        // 【最终】识别结果：
        resultText += resultTextTemp;
        console.log('最终结果：', resultText)
        if (this.result.message) {
          this.result.message(resultText)
        }
        resultTextTemp = ""
        resultText = '';
      }
    } else if (jsonData.action == "error") {
      // 连接发生错误
      console.log("出错了:", jsonData);
      if (this.result.err) {
        this.result.err(jsonData)
      }
    }
  }

  linkUrl = (ts) => {
    ts = ts.toString()
    // let ts = new Date.now() / 1000
    //获取触发函数时的时间戳
    return `wss://rtasr.xfyun.cn/v1/ws?appid=${this.appid}&ts=${ts}&signa=${this.encrypt(ts)}&vadMdn=2&pd=medical`
  }

  encrypt = (ts) => {
    let baseString = this.appid + ts;
    //先对baseString进行MD5加密，然后使用APIKey对结果进行HMAC-SHA1加密，之后进行BASE64编码
    let cypto = require('crypto');
    let md5 = cypto.createHash('md5');
    md5.update(baseString);
    let md5Str = md5.digest('hex');
    let hmac = cypto.createHmac('sha1', this.api_key);
    hmac.update(md5Str);
    let result = hmac.digest('base64');
    return result;
  }
  // encrypt = (ts) => {
  //   if(typeof(ts)!= 'string'){
  //     ts = ts.toString()
  //   }
  //   let th = this;
  //   //使用CryptoJS进行MD5加密
  //   let md5 = CryptoJS.MD5(th.appid + ts);
  //   console.log(md5)
  //   //使用CryptoJS进行HMAC-SHA1加密
  //   let hmac = CryptoJS.HmacSHA1(md5, th.api_key);
  //   console.log(hmac)
  //   //使用CryptoJS进行BASE64编码
  //   let result = CryptoJS.enc.Base64.stringify(hmac);
  //   console.log(result)
  //   return result;
  // }
}

// import PCMPlayer from 'pcm-player'
// import '@/utils/AudioPlay/index.umd.js'
// import AudioPlayer from '@/utils/AudioPlay/index.umd.js'
//导入js文件,使用其中的audioPlayer
// import AudioPlayer from './AudioPlay/index.umd.js'


export class PPlayer {
  player = null;
  ws = null;
  config = {
    hostUrl: "wss://tts-api.xfyun.cn/v2/tts",
    host: location.host,
    uri: "/v2/tts",
    appId: "571c7913",
    apiSecret: "ZjI2YjE1OThmMmMyNTEwNDI1MThiNzll",
    apiKey: "39ab85012a6888c5239539b6d59d537b",
  }

  constructor() {
    try {
      this.player = new AudioPlayer()
    } catch (e) {
      console.log('导入出错')
      console.log(e)
    }
    // this.player.onPlay = () => {
    //   console.log('开始播放音频')
    // }
    // this.player.onStop = (audioDatas) =>{
    //   console.log( audioDatas )
    // }
  }

  /**
   * 初始化websocket连接
   * @param text
   */
  init = (text) => {
    // this.player.reset()
    this.player.init()
    this.ws = new WebSocket(this.getUrl());
    this.ws.onopen = () => {
      // this.player.start({
      //   autoPlay:true,
      //   sampleRate:16000,
      //   resumePlayDuration:1000
      // })
      this.ws.send(JSON.stringify(text))
    }
    // this.ws.addEventListener('message', (data) => {
    //   var audioData = new Unit8Array(data.audio)
    //   this.player.feed(audioData) // 将PCM音频数据写入pcm-player
    // })
    this.ws.onmessage = (e) => {
      let jsonData = JSON.parse(e.data);
      // 合成失败
      if (jsonData.code !== 0) {
        console.error(jsonData);
        return;
      }
      // this.player.postMessage({
      //   type: "base64",
      //   data: jsonData.data.audio,
      //   isLastData: jsonData.data.status === 2,
      // });
      this.player.add(jsonData.data.audio)
      if (jsonData.code === 0 && jsonData.data.status === 2) {
        this.ws.close();
      }
    };
    this.ws.onerr = (err) => {
      console.log('ws错误' + err)
    }
    this.ws.onclose = () => {
      console.log('ws关闭');
    }
  }
  /**
   * 发送文本，处理音频数据
   * @param text
   */
  send = (text) => {
    let params = {
      common: {
        app_id: this.config.appId,
      },
      business: {
        aue: "lame",
        sfl: 1,
        auf: "audio/L16;rate=16000",
        vcn: "xiaoyan",
        bgs: 0,
        tte: "UTF8",
      },
      data: {
        status: 2,
        text: encodeText(text, "base64"),
      },
    };
    this.init(params)
  }
  /**
   * 停止播放当次音频
   */
  stopAudio = () => {
    // this.player.pause()
    this.player.pause()
  }
  /**
   * 清除音频数据
   */
  clearAudio = () => {
    this.player.init()
  }
  replayAudio = () => {
    this.player.replay()
  }
  destroy = () => {
    // this.player.destroy()
  }

  getUrl = () => {
    let date = new Date().toUTCString()
    let wssUrl = this.config.hostUrl + "?authorization=" + this.getAuthStr(date) + "&date=" + date + "&host=" + this.config.host
    return wssUrl;
  }

  getAuthStr = (date) => {
    let signatureOrigin = `host: ${this.config.host}\ndate: ${date}\nGET ${this.config.uri} HTTP/1.1`
    let signatureSha = CryptoJS.HmacSHA256(signatureOrigin, this.config.apiSecret)
    let signature = CryptoJS.enc.Base64.stringify(signatureSha)
    let authorizationOrigin = `api_key="${this.config.apiKey}", algorithm="hmac-sha256", headers="host date request-line", signature="${signature}"`
    let authStr = CryptoJS.enc.Base64.stringify(CryptoJS.enc.Utf8.parse(authorizationOrigin))
    return authStr
  }
}

//创建一个class对象，对象中具有方法process，用于处理base64音频数据，将音频添加到播放列表中，如果音频播放列表不为空，则播放音频，在播放音频的同时依旧可以添加音频数据到播放列表中
class AudioPlayer {
  //创建一个audio节点对象
  ad = new Audio();
  status = 'stop';
  sourceBuffer = null;
  mediaSource = null;
  queueState = 0;
  appendQueue = [];

  constructor() {
    let th = this;
    this.ad.autoplay = true;
    document.body.appendChild(this.ad);
    this.mediaSource = new MediaSource();
    this.ad.src = URL.createObjectURL(this.mediaSource);
    this.mediaSource.addEventListener('sourceopen', () => {
      th.sourceBuffer = th.mediaSource.addSourceBuffer('audio/mpeg');
      console.log('sourceBuffer')
      console.log(th.sourceBuffer)
      th.sourceBuffer.onupdatestart = () => {
      }
      th.sourceBuffer.onupdate = () => {
      }
      th.sourceBuffer.onupdateend = () => {
        if (th.appendQueue.length > 0) {
          th.sourceBuffer.appendBuffer(th.appendQueue.shift());
        } else {
          th.queueState = 0;
        }
      }
      th.sourceBuffer.onerror = () => {
        console.log('出错')
      }
    })
    try {
    } catch (e) {
      console.log("出错")
      console.log(e)
    }
    console.log('节点')
    console.log(this.ad)
    //设置音频播放的方式为自动播放
    //监听音频播放结束事件
    // this.ad.addEventListener("canplay", () => {
    //   console.log('处于可以播放状态')
    //   window.URL.revokeObjectURL(this.ad.src);
    // });
    // this.ad.addEventListener('ended',()=>{
    //   //播放列表中有音频数据
    //   if(this.adList.length>0){
    //     //取出播放列表中的音频数据
    //     let src = this.adList.shift();
    //     //设置音频数据
    //     this.ad.src = window.URL.createObjectURL(src);
    //     //播放音频
    //     this.ad.play();
    //   }else {
    //     this.ad.src = ''
    //   }
    // });
  }

  //添加音频数据到播放列表中
  add(data) {
    //将音频数据添加到播放列表中
    // this.adList.push(base64ToBlob(src,'mp3'));
    // if(this.status == "stop"){
    //   console.log('尝试播放')
    //   this.status = "play"
    //   this.play()
    // }
    const binaryData = atob(data);
    let th = this;
    const arrayBuffer = new Uint8Array(binaryData.length);
    for (let i = 0; i < binaryData.length; i++) {
      arrayBuffer[i] = binaryData.charCodeAt(i);
    }
    const blob = new Blob([arrayBuffer], {type: 'audio/mpeg'});
    const url = URL.createObjectURL(blob);
    try {
      if (th.queueState == 0 && th.appendQueue.length == 0 && th.sourceBuffer) {
        this.sourceBuffer.appendBuffer(arrayBuffer);
        this.queueState = 1;
      } else {
        console.log('添加到队列')
        th.appendQueue.push(arrayBuffer);
      }
    } catch (e) {
      console.log('出错')
      console.log(e)
    }
    // this.sourceBuffer.appendBuffer(arrayBuffer);
  }

  init = () => {
    //消除本次音频数据，开启新的音频并播放
    let th = this;
    //如果sourceBuffer内拥有数据则执行
    th.mediaSource.endOfStream();
    th.mediaSource = new MediaSource();
    th.ad.src = URL.createObjectURL(th.mediaSource);
    th.mediaSource.addEventListener('sourceopen', () => {
      th.sourceBuffer = th.mediaSource.addSourceBuffer('audio/mpeg');
      th.sourceBuffer.onupdatestart = () => {
      }
      th.sourceBuffer.onupdate = () => {
      }
      th.sourceBuffer.onupdateend = () => {
        if (th.appendQueue.length > 0) {
          th.sourceBuffer.appendBuffer(th.appendQueue.shift());
        } else {
          th.queueState = 0;
        }
      }
      th.sourceBuffer.onerror = () => {
        console.log('出错')
      }
      if (th.appendQueue.length > 0) {
        th.sourceBuffer.appendBuffer(th.appendQueue.shift());
      }
    })
    th.ad.play();
  }

  //暂停音频
  pause() {
    this.ad.pause()
  }

  play() {
    this.ad.play()
  }

  replay() {
    this.ad.currentTime = 0;
    this.ad.play()
  }

  reset() {
    this.init()
  }

  destroy() {
    this.init();
  }

  onplay(func) {
    this.ad.onplay = () => {
      func()
    }
  }

  onstop(func) {
    this.ad.onstop = (audioDatas) => {
      func(audioDatas)
    }
  }

}

function encodeText(text, type) {
  if (type === "unicode") {
    let buf = new ArrayBuffer(text.length * 4);
    let bufView = new Uint16Array(buf);
    for (let i = 0, strlen = text.length; i < strlen; i++) {
      bufView[i] = text.charCodeAt(i);
    }
    let binary = "";
    let bytes = new Uint8Array(buf);
    let len = bytes.byteLength;
    for (let i = 0; i < len; i++) {
      binary += String.fromCharCode(bytes[i]);
    }
    return window.btoa(binary);
  } else if (type == 'base64') {
    // return Base64.encode(text);
    //使用CryptoJS进行base64编码
    return CryptoJS.enc.Base64.stringify(CryptoJS.enc.Utf8.parse(text));
  }
}

function base64ToBlob(base64, fileType) {
  let typeHeader = 'data:application/' + fileType + ';base64,'; // 定义base64 头部文件类型
  let audioSrc = typeHeader + base64; // 拼接最终的base64
  let arr = audioSrc.split(',');
  let array = arr[0].match(/:(.*?);/);
  let mime = (array && array.length > 1 ? array[1] : type) || type;
  // 去掉url的头，并转化为byte
  let bytes = window.atob(arr[1]);
  // 处理异常,将ascii码小于0的转换为大于0
  let ab = new ArrayBuffer(bytes.length);
  // 生成视图（直接针对内存）：8位无符号整数，长度1个字节
  let ia = new Uint8Array(ab);
  for (let i = 0; i < bytes.length; i++) {
    ia[i] = bytes.charCodeAt(i);
  }
  return new Blob([ab], {
    type: mime
  });
}

