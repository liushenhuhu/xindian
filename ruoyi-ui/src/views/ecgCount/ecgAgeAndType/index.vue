<template>
  <div>
<!--    <el-select class="el-select" v-model="value1" multiple placeholder="请选择">-->
<!--      <el-option-->
<!--        v-for="item in options"-->
<!--        :key="item.value"-->
<!--        :label="item.label"-->
<!--        :value="item.value">-->
<!--      </el-option>-->
<!--    </el-select>-->
<!--    <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery" style="margin-left: 1vw">搜索</el-button>-->
<!--    <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>-->
    <div id="cf-double-column" class="age" :style="{ width: '100%', height: '100%' }">
      <p class="title"><span class="title-left"></span>数据库统计男女比例图</p>
      <div id="chart" :style="{ width: '100%', height: '550px',margin:'0 auto'}"></div>
      <div class="footer-name">
        <p>男：{{menCount}}人</p>
        <p>女：{{womenCount}}人</p>
      </div>
    </div>
    <el-card class="box-card">
      <div id="myChart" class="myChart" > </div>
    </el-card>
  </div>

</template>

<script>
import elementResizeDetectorMaker from "element-resize-detector";
import {ageList, getAgeYoung,getLogType} from "@/api/ecgCount/ecgCount";
import * as echarts from 'echarts';


export default {
  data(){
    return {
      columndata1: [],
      columndata2: [],
      menCount: 0,
      womenCount: 0,
      ageYoungName: "",
      ageCount:0,
      options:[],
      value1:[],
      requestStatus:true
    }
  },
  created() {

  },
  mounted() {
    this.getList();
    // this.ageYoung()
    //this.getDoubleCloumn();
  },
  methods: {
    drawLine(option) {
      const erd = elementResizeDetectorMaker();
      let myChart = this.$echarts.init(document.getElementById('chart'));
      //myChart.clear();
      // 绘制图表配置
      // 窗口大小自适应方案
      myChart.setOption(option,true);
      setTimeout(function () {
        erd.listenTo(document.getElementById('chart'), element => {
          myChart.resize()
        });
      }, 200);
    },
    getDoubleCloumn() {
      var data1 = []
      var data2 = []
      var arr=["0-10岁","11-20岁","21-25岁","26-30岁","31-35岁","36-40岁","41-45岁","46-50岁",
        "51-55岁","56-60岁","61-65岁","66-70岁","71-75岁","76-80岁","81-85岁","85岁以上"]

      arr.forEach(item=>{
        var index1=this.columndata1.findIndex((i) => i.name === item)
        var index2=this.columndata2.findIndex((i) => i.name === item)
        if(index1==-1){
          data1.push(0)
        }else {
          data1.push(this.columndata1[index1].value)
        }
        if(index2==-1){
          data2.push(0)
        }else {
          data2.push(this.columndata2[index2].value)
        }
      })
      console.log(data1)
      console.log(data2)
      console.log(arr)
      let getArrByKey = (data, k) => {
        let key = k || "value";
        let res = [];
        if (data) {
          data.forEach(function (t) {
            res.push(t[key]);
          });
        }
        return res;
      };

      // [起始最深颜色,结束的浅颜色]
      let colorLeft = ["#51647a", "#364f6b"];
      let colorRight = ["#ea85a4", "#fc5185"];
      let option = {
        title: {
          top: "bottom",
          left: "center",
          textStyle: {
            color: "black ", //标签文字颜色改为白色
          },
        },
        legend: {
          top: "5%",
          right: "10%",
          itemWidth: 50,
          itemHeight: 22,
          itemGap: 40,
          orient: "horizontal",
          icon: "circle",
          textStyle: {
            color: "#ffffff",
            fontSize: 20,
          },
        },
        grid: [
          {
            left: "2%",
            top: "10%",
            bottom: "8%",
            width: "40%",
          },
          {
            left: "50%",
            top: "10%",
            bottom: "8%",
            width: "0%",
          },
          {
            right: "2%",
            top: "10%",
            bottom: "8%",
            width: "40%",
          },
        ],
        tooltip: {
          show: true,
          // 设置  是否百分比
          formatter: "{b} : {c}",
        },
        xAxis: [
          {
            type: "value",
            inverse: true,
            axisLine: {
              show: false,
            },
            axisTick: {
              show: false,
            },
            position: "bottom",
            axisLabel: {
              show: false,
            },
            splitLine: {
              show: false,
            },
          },
          {
            gridIndex: 1,
            show: false,
          },
          {
            gridIndex: 2,
            show: true,
            type: "value",
            inverse: false,
            axisLine: {
              show: false,
            },
            axisTick: {
              show: false,
            },
            position: "bottom",
            axisLabel: {
              show: true,
              textStyle: {
                color: "white",
              },
            },
            splitLine: {
              show: false,
            },
          },
        ],
        yAxis: [
          {
            type: "category",
            gridIndex: 0,
            triggerEvent: true,
            show: true,
            inverse: true,
            data: arr,
            axisLine: {
              show: false,
            },
            splitLine: {
              show: true,
            },
            axisTick: {
              show: false,
            },
            axisLabel: {
              show: false,
            },
          },
          {
            gridIndex: 1,
            type: "category",
            inverse: true,
            position: "left",
            axisLine: {
              show: false,
            },
            axisTick: {
              show: false,
            },
            axisLabel: {
              show: true,
              interval: 0,
              align: "auto",
              verticalAlign: "middle",
              textStyle: {
                color: "#000",
                fontSize: 16,
                align: "center",
              },
            },
            data: arr,
          },
          {
            gridIndex: 2,
            triggerEvent: true,
            type: "category",
            show: true,
            inverse: true,
            data: arr,
            axisLine: {
              show: false,
            },
            splitLine: {
              show: true,
            },
            axisTick: {
              show: false,
            },
            axisLabel: {
              show: false,
            },
          },
        ],
        series: [
          {
            // name: '规模猪场',
            type: "bar",
            gridIndex: 0,
            showBackground: true,
            backgroundStyle: {
              barBorderRadius: 30,
              color:'#f5f0f0',
              borderColor: "#7F0E0F",
              borderWidth: 1,
              borderType: "dotted"
            },
            xAxisIndex: 0,
            yAxisIndex: 0,
            data: data1,
            barWidth: 20,
            // barCategoryGap: '40%',
            itemStyle: {
              normal: {
                show: true,
                // 线性渐变，前四个参数分别是 x0, y0, x2, y2, 范围从 0 - 1，分别表示右,下,左,上。例如（0，0，0，1）表示从正上开始向下渐变；如果是（1，0，0，0），则是从正右开始向左渐变。
                // 相当于在图形包围盒中的百分比，如果最后一个参数传 true，则该四个值是绝对的像素位置
                color: new echarts.graphic.LinearGradient(
                  0, 0, 1, 0, [
                    {
                      offset: 0,
                      color: colorLeft[1], //指0%处的颜色
                    },
                    {
                      offset: 1,
                      color: colorLeft[0], //指100%处的颜色
                    },
                  ],
                  false
                ),
                barBorderRadius: [10, 10, 10, 10],

              },
            },
            label: {
              show: true,
              position: "left",
              textStyle: {
                color: "#000000",
                fontSize: "12",
              },
            },
          },
          {
            // name: '中小猪场',
            type: "bar",
            xAxisIndex: 2,
            yAxisIndex: 2,
            gridIndex: 2,
            showBackground: true,
            backgroundStyle: {
              barBorderRadius: 30,
              color:'#e5f0f4',
              borderColor: "#105270",
              borderWidth: 1,
              borderType: "dotted"
            },
            data: data2,
            barWidth: 20,
            // barCategoryGap: '40%',
            itemStyle: {
              normal: {
                show: true,
                // 线性渐变，前四个参数分别是 x0, y0, x2, y2, 范围从 0 - 1，分别表示右,下,左,上。例如（0，0，0，1）表示从正上开始向下渐变；如果是（1，0，0，0），则是从正右开始向左渐变。
                // 相当于在图形包围盒中的百分比，如果最后一个参数传 true，则该四个值是绝对的像素位置
                color: new echarts.graphic.LinearGradient(
                  0, 0, 1, 0,
                  [
                    {
                      offset: 0,
                      color: colorRight[0], //指0%处的颜色
                    },
                    {
                      offset: 1,
                      color: colorRight[1], //指100%处的颜色
                    },
                  ],
                  false
                ),
                barBorderRadius: [10, 10, 10, 10],

              },
            },
            label: {
              show: true,
              position: "right",
              textStyle: {
                color: "#000000",
                fontSize: "12",
              },
            },
          },
        ],
      };
      // 上面的图表自适应方法
      this.drawLine(option);
    },


    getList() {
      //this.loading = true;
      let myChart = this.$echarts.init(document.getElementById('chart'));
      myChart.clear()
      myChart.showLoading({
        text: 'loading',
        color: '#c23531',
        textColor: '#000',
        maskColor: 'rgba(255, 255, 255, 0.2)',
        zlevel: 0,
      });
      this.value1=[]
      if (this.$route.query.name){
        this.value1.push(this.$route.query.name)
      }
      if(this.requestStatus){
        ageList(this.value1).then(response => {
          this.requestStatus=true
          myChart.hideLoading()
          //console.log(Object.values(response)[1]);
          console.log(response);
          this.columndata1 = response.men;
          this.columndata2 = response.women;
          this.getDoubleCloumn();
          let getArrByKey = (data, k) => {
            let key = k || "value";
            let res = [];
            if (data) {
              data.forEach(function (t) {
                res.push(t[key]);
              });
            }
            return res;
          };
          let arr1 = getArrByKey(this.columndata1,'value');
          let arr2 = getArrByKey(this.columndata2,'value');

          Array.prototype.sum = function () {
            var sum = 0;
            for (var i = 0; i < this.length; i++) {
              sum += this[i];
            }
            return sum;
          };

          this.menCount = arr1.sum();
          this.womenCount = arr2.sum();
          //console.log(this.menCount)
          //this.loading = false;
        });
      }else {
        console.log("请勿重复提交")
      }

      getAgeYoung(this.value1).then(r=>{
        console.log(r)
        let data = r.data
        let countArr = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0,0,0,0,0,0];
        this.ageCount=0
        for (let j = 0; j < data.length; j++) {
          this.ageCount += parseInt(data[j].value)
          countArr[data[j].name] = parseInt(data[j].value);
        }
        this.countArr = countArr;
        this.ageYoungName = '儿童心电统计  总人数：'+this.ageCount
        console.log(this.ageYoungName)
        this.ageYoung();
      })
      getLogType().then(res=>{
        this.options=res.data
      })

    },

    ageYoung(){
      let myChart = this.$echarts.init(document.getElementById('myChart'))
      // 绘制图表
      let status = this;
      myChart.off('click');
      let option = {
        title: {
          text: this.ageYoungName
        },
        tooltip: {},
        xAxis: {
          data: ["零岁","一岁", "二岁", "三岁", "四岁", "五岁",
            "六岁", "七岁", "八岁", "九岁", "十岁", "十一岁", "十二岁", "十三岁", "十四岁", "十五岁", "十六岁", "十七岁"],
          axisLabel:{
            interval: 0,
            rotate : 40,
          }
        },
        yAxis: {
          type:'value',
          axisLine:{
            show:false
          }
        },
        series: [{
          //name: '诊断次数',
          type: 'bar',
          data: this.countArr,
          label: {//饼图文字的显示
            show: true, //默认  显示文字
            distance: 5.5,
            position: 'top',
            //verticalAlign: 'middle',
            color: '#009ac7',
            fontSize: 18
          },
          itemStyle:{
            normal:{
              color:'#73C0DE',
              barBorderRadius: [10, 10, 10, 10],
              shadowBlur: 8,
              shadowOffsetX: 3,
              shadowOffsetY: -2,
              shadowColor: "#73C0DE"
            }
          }
        }],
        grid: {
          bottom: '30%',
          top: '20%',
          right: 0,
          left: '0%',
        },
      }
      myChart.clear()
      myChart.setOption(option);
      const erd = elementResizeDetectorMaker();
      //myChart.clear();
      // 绘制图表配置
      // 窗口大小自适应方案
      myChart.setOption(option,true);
      setTimeout(function () {
        erd.listenTo(document.getElementById('myChart'), element => {
          myChart.resize()
        });
      }, 200);
      // myChart.on('click', function (params) {
      //   console.log(params)
      //   status.queryParams.month = params.name;
      //   status.show = false;
      //   document.getElementById("myChart").style.display='none';
      //   document.getElementById("table1").style.display='';
      //   status.getListData();
      // })

    },
    handleQuery() {
      console.log(this.value1)
      this.getList();
      this.requestStatus=false
    },
    resetQuery() {
      this.value1=[]
      this.handleQuery();
    },
  }
}
</script>

<style lang="scss" scoped>
.el-select{
  width: 20vw;
  margin-top: 2vh;
  margin-left: 5vw;
}
#cf-double-column {
  position: relative;
  width: 100%;
  height: 100%;
  background: #ffffff;
  border-radius: 0.5rem;
  .title {
    position: absolute;
    top: 0;
    left: 1rem;
    font-size: 1.13rem;
    font-weight: bold;
    color: #333333;
    .title-left {
      display: inline-block;
      width: 0.25rem;
      height: 1rem;
      background: linear-gradient(180deg, #3ea2ff 0%, #2746fc 100%);
      border-radius: 0.13rem;
      margin-right: 1rem;
    }
  }
  .footer-name {
    position: relative;
    bottom: 0;
    color: #333333;
    font-size: 0.92rem;
    width: 100%;
    p {
      display: inline-block;
      width: 26%;
      padding: 0 3.5rem;
      margin: 0;
      color: #1e1e1e;
      font-weight: 700;
    }
    p:nth-of-type(1) {
      margin-left:25%
    }
    p:nth-of-type(2) {
      margin-left:10%
    }
  }


}
.box-card {
  width: 90%;
  height: 45vh;
  margin:0 auto;
  margin-top: 20px;
  margin-bottom: 10px;
}
.myChart{
  height: 50vh;
  width: 100%;
  margin-top: 20px;
  margin:0 auto;
}
</style>
