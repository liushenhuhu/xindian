<template>
  <div class="home" id="home" >
      <div class="title">
        <div class="button">
<!--          <el-button size="mini" >院区数据</el-button>-->
          <el-button size="mini" @click="inScreen" plain>全屏切换</el-button>
<!--          <el-button size="mini" @click="inScreen" plain>退出全屏</el-button>-->
        </div>
        <span >动态心电智能实时监控</span>
        <el-pagination
          @current-change="handleCurrentChange"
          layout="prev, pager, next"
          :total="1000">
        </el-pagination>
      </div>

    <div class="mainbox">

      <div class="container"  v-for="(item,index) in data" :key="index">
        <div class="containcontent">
          <div class="chart chartEvent" ref="chart" :id="getId(index)"></div>
<!--          <div class="chart" id="chart_0"></div>-->
          <div class="infleft" @click="jump(item.deviceSn)">
            <div class="name">心率:</div>
            <div class="hart" ><span :style="color(item.hr_mean)">{{item.hr_mean}}</span></div>
          </div>
        </div>
        <div class="containMsg" @click="jump(item.deviceSn)" >
          <div class="name effect">姓名:<span>{{item.patientName}}</span></div>
          <div class="name" >性别:<span>{{item.gender}}</span></div>
          <div class="name" >年龄:<span>{{item.age}}</span></div>
          <div class="name" >所属医院:<span>{{item.hospName}}</span></div>
          <div class="name" >设备号:<span>{{item.deviceSn}}</span></div>
        </div>
        <div class="panel-footer"></div>
      </div>

      <div class="container" v-if="data.length<12" v-for="index of (12-data.length)" :key="index+data.length">
        <div class="containcontent">
          <div class="chart"></div>
          <div class="infleft">
            <div class="name">心率:</div>
            <div class="hart"><span></span></div>
          </div>
        </div>
        <div class="containMsg">
          <div class="name effect">姓名:<span></span></div>
          <div class="name" >性别:<span></span></div>
          <div class="name" >年龄:<span></span></div>
          <div class="name" >所属医院:<span></span></div>
          <div class="name" >设备号:<span></span></div>
        </div>
        <div class="panel-footer"></div>
      </div>
    </div>

  </div>
</template>

<script>
import {
  get_device,
  list,
  getlist
} from "@/api/ECGScreen/equipment";
import 'default-passive-events'
import screenfull from 'screenfull'
import card from "element-ui/packages/card";
export default {
  name: "Index",
  data() {
    return {
      // 版本号
      isFullFlag:false,
      total:null,//总设备数
      pagenum:null,//总页数
      pages:1,//当前页
      arr:[],//设备号列表
      data:[],//前10秒数据
      newData:[],//最新10秒数据
      p1Iy:[],
      p1V1y:[],
      tag:1,//每个请求10秒数据  1表示前5秒的数据显示 2表示后5秒的数据显示
      ts:0,//时间段
      lodaing:{},
      currentpage:[],//一维数组，存放12台设备
      timer:null,
      time:null,//时间 X轴
    };
  },
  created() {
    this.get_device();
  },
  activated() {
    this.get_device();
  },
  mounted() {
    this.openLoading();


    // this.chart(0)
    // 监听页面全屏
    // window.addEventListener("fullscreenchange", (e)=> {
    //   if(screenfull.isFullscreen){
    //     this.isFullFlag = true
    //     console.log("进入全屏")
    //   }else{
    //     this.isFullFlag = false
    //     console.log("退出")
    //   }
    // })
  },
  beforeRouteLeave(to, from, next){
    if (this.timer) {
      clearInterval(this.timer);
    }
    next();
  },

  computed: {
    color() {
      return hr_mean => {
        if(hr_mean>=100||hr_mean<=60){
          return 'color:red;background-color: rgba(255, 0, 0,0.3);';
        }
        return ;
      };

    },
  },
  watch:{
    data(val, oldVal){
      console.log("data数据更新")
      this.$nextTick(()=>{
        this.data.forEach((item,index)=>{
          let chart = this.$echarts.init(document.getElementById('child_'+index))
          if(this.p1Iy[index]){
            chart.clear();
            chart.setOption(this.chart(index,1250))
          }

        })
      })

    }
  },
  methods: {
    getId:function (val){
      return 'child_'+val
    },
     async get_device(){
       await get_device().then(res=>{
         this.arr=res.result.dev_list;
         console.log(this.arr)
         let length =  res.result.dev_list.length;//总设备个数
         this.total=length;
         this.pagenum = (length%12==0) ? (length/12) : length/12+1;//总页数
           for (let i = 0; i < length; i+=12) {
             this.currentpage.push(this.arr.slice(i,i+12))
           }
          this.$message.success({
            message: '设备列表获取成功',
            type: 'success',
            duration: 2500,
            center:true,
            onClose:()=> {
              this.$message.success({
                message: '共有' + length + "台设备链接",
                type: 'success',
                duration: 4000,
                center:true
              })
            }
          })
        })
       await this.list()
    },

    async list(){
      this.data=[]
      this.p1Iy=[]
      this.p1V1y=[]
      if(this.pages>this.pagenum){
        return this.closeFullScreen()
      }
      if(this.currentpage[this.pages-1].length!==0) {
        // for (let index = 0; index < this.currentpage[this.pages - 1].length; index++) {
        //   list(this.currentpage[this.pages - 1][index], this.ts).then(res => {
        //     const that = this
        //     this.timer = setInterval(function () {
        //       that.setchart()
        //     }, 5050)
        //     this.closeFullScreen()
        //   })
        // }
        // for (let index = 0; index < this.currentpage[this.pages - 1].length; index++){
        //   list(this.currentpage[this.pages-1][index],this.ts).then(res=>{
        //     res.result.hr_mean=res.result.hr_mean.toFixed()
        //     this.data.push(res.result)
        //     this.p1Iy.push(res.result.data.II);
        //     this.p1V1y.push(res.result.data.V1);
        //   })
        // }
        let promiseList=[]
        for (let index = 0; index < this.currentpage[this.pages - 1].length; index++) {
          promiseList.push(new Promise((resolve, reject) => {
            list(this.currentpage[this.pages-1][index],this.ts).then(res=>{
               res.result.hr_mean=res.result.hr_mean.toFixed()
              resolve(res.result)
             })
          }));
        }

        Promise.all(promiseList).then((rspList)=> {
          rspList.map((val)=> {
            this.data.push(val)
            this.p1Iy.push(val.data.II);
            this.p1V1y.push(val.data.V1);
          });
          console.log(this.data)
          const that=this
          this.timer=setInterval(function (){
            that.setchart()
          },5050)
          this.closeFullScreen()
        })

      }


    },

    async handleCurrentChange(pages){
      clearInterval(this.timer)
      this.tag=1
      this.pages=pages
      this.openLoading()
      console.log("当前页"+this.pages)
      this.data=[]
      this.p1Iy=[]
      this.p1V1y=[]
      await this.list()
    },

    async setchart(){
        if(this.tag===1){
          this.ts++
          this.newData=[]
          if(this.currentpage[this.pages-1]!==null){
              for (let i=0;i<this.currentpage[this.pages-1].length;i++){
                list(this.currentpage[this.pages-1][i],this.ts).then((res)=>{
                  res.result.hr_mean=res.result.hr_mean.toFixed()
                  this.newData.push(res.result)
                })
              }
              // this.currentpage[this.pages-1].forEach((item,index)=>{
              //   list(this.currentpage[this.pages-1][index],this.ts).then((res)=>{
              //     res.result.hr_mean=res.result.hr_mean.toFixed()
              //     this.newData.push(res.result)
              //   })
              // })
              this.tag++
          }
          this.$nextTick(()=>{
            this.data.forEach((item,index)=>{
              let chart = this.$echarts.init(document.getElementById('child_'+index))
              if(this.p1Iy[index]!==null){
                chart.clear();
                chart.setOption(this.chart(index,0))
              }
            })
          })
        }else {

          /**
           * new array
           * old array
           * new.device => old.index
           * new.splic(index,i,data)
           */
          for(let a=0;a<this.data.length;a++){
            for(let b=0;b<this.newData.length;b++){
              if(this.data[a].deviceSn===this.newData[b].deviceSn){
                this.data.splice(a,1,this.newData[b])
                this.p1Iy.splice(a,1,this.newData[b].data.II)
                this.p1V1y.splice(a,1,this.newData[b].data.V1)
              }
            }
          }
          this.tag--
        }

    },
    timex(){
      let timex = (function () {
        let now = new Date();
        let res = [];
        let lenth = 1250;
        while (lenth--) {
          res.push(now.toLocaleTimeString());
          now = new Date(now.valueOf() - 4);
        }
        return res;
      })();
      const datenow = new Date()
      for (let b = 0; b < 1250; b++) {
        timex.unshift((new Date(datenow.valueOf() - (b * 4))).toLocaleTimeString());//datenow.valueOf()返回datenow数组的值
        timex.pop();
      }
      return timex
    },
    chart(id,j){
      if(id===0){
        this.time=this.timex()
      }

      let p1Iy=[]
      let p1V1y=[]
      console.log(this.p1Iy)
      console.log(this.data)
      for (let i = j; i < 1250+j; i++) {
        p1Iy.push(this.p1Iy[id][i])
        p1V1y.push(this.p1V1y[id][i]-1)
      }
      // p1Iy=p1Iy.reverse()
      // p1V1y=p1V1y.reverse()
      let option=({
        animation: true,
        animationDuration: 4750,
        animationEasing: "linear",
        animationEasingUpdate: 'linear',
        animationDurationUpdate: 5050,
        animationDelayUpdate: 0,
        animationThreshold: 5100,
        title: {
        text: '电位（mV）',
          textStyle: {
          fontSize: 13,
            color:"#66b3ff"
        },
      },
        tooltip: {
          trigger: 'axis',
            axisPointer: {
            type: 'cross'
          }
        },
        grid: {
          left: 17,
            right: 15,
            top: 20,
            bottom: 20
        },
        legend: {
          data: ['II导联', 'V1导联'],
            textStyle: {color: "#66b3ff"} /*图例(legend)说明文字的颜色*/ ,
          left: "right",
        },
        xAxis: {
          boundaryGap: true,
            data: this.time,
            axisLabel: { //修改坐标系字体颜色
            interval: 248,
              show: true,
              textStyle: {
              fontSize: 9,
                color: "#92c2ff",
            },
          },
          splitLine: {
            interval: 249,
              show: true,
              lineStyle: {opacity: 0.5,},
          } /*网格线*/
        },
        yAxis: {
          min: 1,
            max: -2,
            boundaryGap: false,
            splitNumber: 3,
            axisLabel: { //修改坐标系字体颜色
            show: false,
              textStyle: {color: "#8DB6DB"}
          },
          splitLine: {
            lineStyle: {opacity: 0.5,},
            show: true
          } /*网格线*/
        },
        series: [
          {
            sampling: 'average',
            itemStyle: {
              normal: {
                lineStyle: {color: '#92c2ff' /*折线的颜色*/},
                color: "#66b3ff" /*图例(legend)的颜色,不是图例说明文字的颜色*/
              }
            },
            symbol: "none",
            /*去掉小圆点*/
            name: 'II导联',
            emphasis: {focus: 'series'},
            type: 'line',
            data: p1Iy,
            smooth: 0
          },
          {
            itemStyle: {
              normal: {
                lineStyle: {color: '#6EDDF1' /*折线的颜色*/},
                color: "#6EDDF1" /*图例(legend)的颜色,不是图例说明文字的颜色*/
              }
            },
            symbol: "none",
            /*去掉小圆点*/
            name: 'V1导联',
            emphasis: {focus: 'series'},
            type: 'line',
            data: p1V1y,
            smooth: 0
          }]
      })
      return option

    },

    //加载中
    openLoading() {
      this.loading = this.$loading({
        lock: true,
        text: '加载中',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)',
        target:document.querySelector("#home")
      });
    },
    jump(deviceSn){
      this.$router.push(
        {
          //添加需要传值到那个页面的路径
          path:'/Screen/detail',
          //携带需要传递的参数
          query:{deviceSn:deviceSn}
        })
    },
    closeFullScreen(){
      this.loading.close();
    },
    inScreen(){
      this.isFullFlag=!this.isFullFlag
      const element = document.getElementById('home');//指定全屏区域元素
      if(screenfull.isEnabled && !screenfull.isFullscreen){
        // screenfull.request(element);
        screenfull.request(element)
        return
      }
        screenfull.toggle(element)

    },

  },
};
</script>

<style scoped lang="scss">
::v-deep .el-pagination{
  width: 400px;
}
::v-deep .el-pagination .el-pager li{
  background-color: rgba(0,0,0,0);
  color:#4cc9f0 ;
  border: 1px solid #4cc9f0;
}
::v-deep .el-pagination .el-pager li:hover{
  background-color: #4cc9f0;
  color: white;
}
::v-deep .el-pager li.active{
  background-color: #4cc9f0;
  color: white;
}
::v-deep .el-pagination button{
  background-color: rgba(0,0,0,0);
  color:#4cc9f0 ;
  padding: 0;
  border: 1px solid #4cc9f0;
}
::v-deep .el-pagination button:hover{
  background-color: #4cc9f0;
  color: white;
}
.home{
  width: 100%;
  height: 100%;
  .title {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    width: 100%;
    min-height:90px;
    height: 6vh;
    background-image: url("~@/assets/images/head.png");
    background-repeat: no-repeat;
    background-position: center center;
    background-attachment: scroll;
    background-size: cover;
    background-color:rgb(3,4,74);
    margin-bottom: 0;
    span{
      font-size: 45px;
      color: #6EDDF1;
      letter-spacing:6px;
      padding-left: 16vw;
    }
    .button{
      display: inline-block;
      .el-button{
        width: 70px;
        height: 30px;
        padding: 0;
        margin-left: 3px;
        margin-top: 3px;
        text-align: center;
        color: #4cc9f0;
        border-color: #4cc9f0;
        background-color: rgba(0,0,0,0);
      }
      .el-button:hover{
        color: white;
        background-color: #6EDDF1;
      }
    }
  }
}
.mainbox{
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: space-around;
  align-content: space-around;
  width:100%;
  min-height: 90vh;
  background-color: rgb(3,4,74);
  .container {
    width: 33%;
    height: 20%;
    min-height: 21vh;
    position: relative;
    border: 1px solid rgba(25, 186, 139, 0.17);
    background: url("~@/assets/images/container.png");
    padding: 0.3vw 0.1vw;
    margin-bottom: .5vw;
    position: relative;
    .containcontent{
      display: flex;
      flex-direction: row;
      justify-content: center;
      align-items: center;
      height: 80%;
      .chart{
        width: 100%;
        height: 18vh;
      }
      .infleft{
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        margin-top: 15px;
        width: calc(20% - 15px);
        .hart{
          text-align: center;
          span{
            color: #6EDDF1;
            font-size: 3vw;
            border-radius: 6px;
            background-color: rgba(110,221,241,0.3);
          }
        }
        .name{
          min-width: 78px;
          text-align: center;
          font-size: 1.5vw;
          color: #6EDDF1;
          font-weight: bold;
          margin-bottom: 10px;
        }
      }
      .infleft:hover{
        cursor: pointer;
        .name{
          color: #bfe7ec;
          font-size: 1.6vw;
        }
      }
    }
    .containMsg{
      display: flex;
      flex-direction: row;
      justify-content: space-around;
      .name{
        font-size: 0.8vw;
        color: #6EDDF1;
        font-weight: bold;
        span{
          color: white;
        }
      }
    }
    .containMsg:hover{
      cursor: pointer;
      .name{
        font-size: 0.9vw;
        color: #bfe7ec;
        font-weight: bold;
      }
    }
    &::before {
      position: absolute;
      top: 0;
      left: 0;
      content: "";
      width: .5208vw;
      height: .5208vw;
      border-top: .1vw solid #02a6b5;
      border-left: .1vw solid #02a6b5;
    }
    &::after {
      position: absolute;
      top: 0;
      right: 0;
      content: "";
      width: .5208vw;
      height: .5208vw;
      border-top: .1vw solid #02a6b5;
      border-right: .1px solid #02a6b5;
    }
    .panel-footer {
      position: absolute;
      left: 0;
      bottom: 0;
      width: 100%;
      &::before {
        position: absolute;
        bottom: 0;
        left: 0;
        content: "";
        width: .5208vw;
        height: .5208vw;
        border-bottom: .1vw solid #02a6b5;
        border-left: .1vw solid #02a6b5;
      }
      &::after {
        position: absolute;
        bottom: 0;
        right: 0;
        content: "";
        width: .5208vw;
        height: .5208vw;
        border-bottom: .1vw solid #02a6b5;
        border-right: .1vw solid #02a6b5;
      }
    }

  }

}

</style>

