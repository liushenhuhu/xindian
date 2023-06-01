<template>
  <div class="body">
    <div class="title">
      <div class="title1">
        <div class="button">院区数据</div>
        <div class="button">进入全屏</div>
        <div class="button">退出全屏</div>
      </div>
      <div class="title2">动态心电智能实时监控</div>
      <div class="title3">
        <el-pagination
          background
          :current-page="currentPage"
          :total="totalCount"
          layout="prev, pager, next"
          @current-change="handleCurrentChange">
        </el-pagination>
      </div>
    </div>

    <div class="mainbox">
      <div class="ecg">
        <div class="container">
          <div class="containcontent">
            <div class="chart" id="p1"></div>
            <div class="infleft" id="p1left">
              <div class="name"><b>心率:</b></div>
              <div class="hart" id="p1hr"></div>
            </div>
          </div>
          <div class="containMsg" id="p1left4">
            <div class="name effect" id="p1name">姓名:</div>
            <div class="name" id="p1sex">性别:</div>
            <div class="name" id="p1age">年龄：</div>
            <div class="name" id="p1hosp">所属医院：</div>
            <div class="name" id="device1">设备号:</div>
          </div>
          <div class="panel-footer"></div>
        </div>
        <div class="container">
          <div class="containcontent">
            <div class="chart" id="p2"></div>
            <div class="infleft" id="p2left">
              <div class="name"><b>心率:</b></div>
              <div class="hart" id="p2hr"></div>
            </div>
          </div>
          <div class="containMsg" id="p2left4">
            <div class="name effect" id="p2name">姓名:</div>
            <div class="name" id="p2sex">性别：</div>
            <div class="name" id="p2age">年龄：</div>
            <div class="name" id="p2hosp">所属医院：</div>
            <div class="name" id="device2">设备号:</div>
          </div>
          <div class="panel-footer"></div>
        </div>
        <div class="container">
          <div class="containcontent">
            <div class="chart" id="p3"></div>
            <div class="infleft" id="p3left">
              <div class="name"><b>心率:</b></div>
              <div class="hart" id="p3hr"></div>
            </div>
          </div>
          <div class="containMsg" id="p3left4">
            <div class="name effect" id="p3name">姓名:</div>
            <div class="name" id="p3sex">性别：</div>
            <div class="name" id="p3age">年龄：</div>
            <div class="name" id="p3hosp">所属医院：</div>
            <div class="name" id="device3">设备号:</div>
          </div>
          <div class="panel-footer"></div>
        </div>
        <div class="container">
          <div class="containcontent">
            <div class="chart" id="p4"></div>
            <div class="infleft" id="p4left">
              <div class="name"><b>心率:</b></div>
              <div class="hart" id="p4hr"></div>
            </div>
          </div>
          <div class="containMsg" id="p4left4">
            <div class="name effect" id="p4name">姓名:</div>
            <div class="name" id="p4sex">性别：</div>
            <div class="name" id="p4age">年龄：</div>
            <div class="name" id="p4hosp">所属医院：</div>
            <div class="name" id="device4">设备号:</div>
          </div>
          <div class="panel-footer"></div>
        </div>
      </div>
      <div class="ecg">
        <div class="container">
          <div class="containcontent">
            <div class="chart" id="p5"></div>
            <div class="infleft" id="p5left">
              <div class="name"><b>心率:</b></div>
              <div class="hart" id="p5hr"></div>
            </div>
          </div>
          <div class="containMsg" id="p5left5">
            <div class="name effect" id="p5name">姓名:</div>
            <div class="name" id="p5sex">性别：</div>
            <div class="name" id="p5age">年龄：</div>
            <div class="name" id="p5hosp">所属医院：</div>
            <div class="name" id="device5">设备号:</div>
          </div>
          <div class="panel-footer"></div>
        </div>
        <div class="container">
          <div class="containcontent">
            <div class="chart" id="p6"></div>
            <div class="infleft" id="p6left">
              <div class="name"><b>心率:</b></div>
              <div class="hart" id="p6hr"></div>
            </div>
          </div>
          <div class="containMsg" id="p6left6">
            <div class="name effect" id="p6name">姓名:</div>
            <div class="name" id="p6sex">性别：</div>
            <div class="name" id="p6age">年龄：</div>
            <div class="name" id="p6hosp">所属医院：</div>
            <div class="name" id="device6">设备号:</div>
          </div>
          <div class="panel-footer"></div>
        </div>
        <div class="container">
          <div class="containcontent">
            <div class="chart" id="p7"></div>
            <div class="infleft" id="p7left">
              <div class="name"><b>心率:</b></div>
              <div class="hart" id="p7hr"></div>
            </div>
          </div>
          <div class="containMsg" id="p7left7">
            <div class="name effect" id="p7name">姓名:</div>
            <div class="name" id="p7sex">性别：</div>
            <div class="name" id="p7age">年龄：</div>
            <div class="name" id="p7hosp">所属医院：</div>
            <div class="name" id="device7">设备号:</div>
          </div>
          <div class="panel-footer"></div>
        </div>
        <div class="container">
          <div class="containcontent">
            <div class="chart" id="p8"></div>
            <div class="infleft" id="p8left">
              <div class="name"><b>心率:</b></div>
              <div class="hart" id="p8hr"></div>
            </div>
          </div>
          <div class="containMsg" id="p8left8">
            <div class="name effect" id="p8name">姓名:</div>
            <div class="name" id="p8sex">性别：</div>
            <div class="name" id="p8age">年龄：</div>
            <div class="name" id="p8hosp">所属医院：</div>
            <div class="name" id="device8">设备号:</div>
          </div>
          <div class="panel-footer"></div>
        </div>
      </div>
      <div class="ecg">
        <div class="container">
          <div class="containcontent">
            <div class="chart" id="p9"></div>
            <div class="infleft" id="p9left">
              <div class="name"><b>心率:</b></div>
              <div class="hart" id="p9hr"></div>
            </div>
          </div>
          <div class="containMsg" id="p9left9">
            <div class="name effect" id="p9name">姓名:</div>
            <div class="name" id="p9sex">性别：</div>
            <div class="name" id="p9age">年龄：</div>
            <div class="name" id="p9hosp">所属医院：</div>
            <div class="name" id="device9">设备号:</div>
          </div>
          <div class="panel-footer"></div>
        </div>
        <div class="container">
          <div class="containcontent">
            <div class="chart" id="p10"></div>
            <div class="infleft" id="p10left">
              <div class="name"><b>心率:</b></div>
              <div class="hart" id="p10hr"></div>
            </div>
          </div>
          <div class="containMsg" id="p10left10">
            <div class="name effect" id="p10name">姓名:</div>
            <div class="name" id="p10sex">性别：</div>
            <div class="name" id="p10age">年龄：</div>
            <div class="name" id="p10hosp">所属医院：</div>
            <div class="name" id="device10">设备号:</div>
          </div>
          <div class="panel-footer"></div>
        </div>
        <div class="container">
          <div class="containcontent">
            <div class="chart" id="p11"></div>
            <div class="infleft" id="p11left">
              <div class="name"><b>心率:</b></div>
              <div class="hart" id="p11hr"></div>
            </div>
          </div>
          <div class="containMsg" id="p11left11">
            <div class="name effect" id="p11name">姓名:</div>
            <div class="name" id="p11sex">性别：</div>
            <div class="name" id="p11age">年龄：</div>
            <div class="name" id="p11hosp">所属医院：</div>
            <div class="name" id="device11">设备号:</div>
          </div>
          <div class="panel-footer"></div>
        </div>
        <div class="container">
          <div class="containcontent">
            <div class="chart" id="p12"></div>
            <div class="infleft" id="p12left">
              <div class="name"><b>心率:</b></div>
              <div class="hart" id="p12hr"></div>
            </div>
          </div>
          <div class="containMsg" id="p12left12">
            <div class="name effect" id="p12name">姓名:</div>
            <div class="name" id="p12sex">性别：</div>
            <div class="name" id="p12age">年龄：</div>
            <div class="name" id="p12hosp">所属医院：</div>
            <div class="name" id="device12">设备号:</div>
          </div>
          <div class="panel-footer"></div>
        </div>
      </div>
    </div>
  </div>
</template>


<script>
export default {
  name: "JECG_screen",
  data() {
    return {
      currentPage: 1,  // 当前页码，默认为第一页
      // pageSize: 10,  // 每页显示的条数，默认为10条
      totalCount: 1000,  // 总条数，假设为100条
      deviceURL: "https://server.mindyard.cn:84/get_device",//获取设备号
      decgURL: "https://server.mindyard.cn:84/detect_decg",//获取数据
      device1: {
        heart: "",//心率
        patientName: "",//患者姓名
        gender: "",//患者性别
        age: "",//年龄
        hospital: "",//医院
        device: "",//设备号
        ecgData: []//心电数据
      },
      device2: {
        heart: "",//心率
        patientName: "",//患者姓名
        gender: "",//患者性别
        age: "",//年龄
        hospital: "",//医院
        device: "",//设备号
        ecgData: []//心电数据
      },
      device3: {
        heart: "",//心率
        patientName: "",//患者姓名
        gender: "",//患者性别
        age: "",//年龄
        hospital: "",//医院
        device: "",//设备号
        ecgData: []//心电数据
      },
      device4: {
        heart: "",//心率
        patientName: "",//患者姓名
        gender: "",//患者性别
        age: "",//年龄
        hospital: "",//医院
        device: "",//设备号
        ecgData: []//心电数据
      },
      device5: {
        heart: "",//心率
        patientName: "",//患者姓名
        gender: "",//患者性别
        age: "",//年龄
        hospital: "",//医院
        device: "",//设备号
        ecgData: []//心电数据
      },
      device6: {
        heart: "",//心率
        patientName: "",//患者姓名
        gender: "",//患者性别
        age: "",//年龄
        hospital: "",//医院
        device: "",//设备号
        ecgData: []//心电数据
      },
      device7: {
        heart: "",//心率
        patientName: "",//患者姓名
        gender: "",//患者性别
        age: "",//年龄
        hospital: "",//医院
        device: "",//设备号
        ecgData: []//心电数据
      },
      device8: {
        heart: "",//心率
        patientName: "",//患者姓名
        gender: "",//患者性别
        age: "",//年龄
        hospital: "",//医院
        device: "",//设备号
        ecgData: []//心电数据
      },
      device9: {
        heart: "",//心率
        patientName: "",//患者姓名
        gender: "",//患者性别
        age: "",//年龄
        hospital: "",//医院
        device: "",//设备号
        ecgData: []//心电数据
      },
      device10: {
        heart: "",//心率
        patientName: "",//患者姓名
        gender: "",//患者性别
        age: "",//年龄
        hospital: "",//医院
        device: "",//设备号
        ecgData: []//心电数据
      },
      device11: {
        heart: "",//心率
        patientName: "",//患者姓名
        gender: "",//患者性别
        age: "",//年龄
        hospital: "",//医院
        device: "",//设备号
        ecgData: []//心电数据
      },
      device12: {
        heart: "",//心率
        patientName: "",//患者姓名
        gender: "",//患者性别
        age: "",//年龄
        hospital: "",//医院
        device: "",//设备号
        ecgData: []//心电数据
      },
    }
  },
  methods: {
    handleCurrentChange(val) {  // 处理当前页码发生变化的事件
      this.currentPage = val;  // 将数据中的当前页码更新为最新值
      console.log("当前页码", this.currentPage)
    }
  }
}
</script>


<style scoped>
.body {
  /*background-color: #03044A;*/
}

.title {
  /*border: 1px solid #000000;*/
  width: 100%;
  display: flex;
  justify-content: space-around;
  background-image: url("../../assets/images/head.png");
}

.title1 {
  display: flex;
  justify-content: space-around;
}

.title2 {
  font-size: 3vw;
  color: #000000;
  text-align: center;
}

.title3 {
  padding-top: 0.5vw;
}

.button {
  width: 4vw;
  height: 2vw;
  font-size: 0.7vw;
  line-height: 2vw;
  text-align: center;
  border: 1px solid #000000;
  margin: 0.5vw;
}

.mainbox {
  width: 100%;
  height: 91%;
  display: flex;
  flex-direction: row;
  justify-content: space-around;
}

.ecg {
  /*width: 32vw;*/
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
}

.container {
  width: 29.5vw;
  height: 13vw;
  display: flex;
  flex-direction: column;
  position: relative;
  border: 1px solid rgba(25, 186, 139, 0.17);
  background: url(../../assets/images/container.png);
  padding: 0.1vw 0.1vw;
  margin-bottom: 0.5vw;
}

.containcontent {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: row;
}

.chart {
  width: 90%;
  height: 100%;
}

.infleft {
  align-self: center;
  width: 15%;
  height: 80%;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  font-size: 0.8vw;
  color: #6EDDF1;
}

.infleft .name {
  font-size: 1.5vw;
  text-align: center;
  /*cursor: pointer;*/
}

.infleft .hart {
  font-size: 3vw;
  text-align: center;
}

.containMsg {
  display: flex;
  flex-direction: row;
  justify-content: left;
  flex-wrap: nowrap;
  font-size: 0.8vw;
  color: #6EDDF1;
  white-space: nowrap;
  overflow: hidden;
}

.containMsg .name {
  font-size: 0.8vw;
  /*cursor: pointer;*/
  margin: 1vh 0.8vw 1vh 0;
}

.effect:hover {
  font-size: 0.9vw;
  color: #02a6b5;
  cursor: pointer;
  /*-webkit-text-fill-color: transparent;*/
  /*-webkit-text-stroke:1px #c0faf9;*/
  /*text-shadow: 0px 0px 2px #00FFFF,0px 0px 2px #00FFFF,0px 0px 2px #00FFFF;*/

}


</style>
