<template>
  <div class="home">
    <div class="top">
      <el-card class="box-card0" shadow="hover" >
        <div class="item4">
          <div class="item">
            <div  class="title">
              <span><svg-icon icon-class="trading1" class-name="icon"></svg-icon>销量金额</span>
            </div>
            <span class="num"><svg-icon icon-class="money" class-name="icon"/>11111</span>
          </div>
          <div class="item">
            <div  class="title">
              <span><svg-icon icon-class="trading3" class-name="icon"></svg-icon>产品销量</span>
            </div>
            <span class="num">33333<svg-icon icon-class="rise" class-name="icon"/></span>
          </div>
          <div class="item">
            <div class="title">
              <span><svg-icon icon-class="trading2" class-name="icon"></svg-icon>交易金额</span>
            </div>
            <span class="num"><svg-icon icon-class="money" class-name="icon"/>44444</span>
          </div>
          <div class="item">
            <div  class="title">
              <span><svg-icon icon-class="trading4" class-name="icon"></svg-icon>Invoices</span>
            </div>
            <span class="num">333333<svg-icon icon-class="descend" class-name="icon"/></span>
          </div>
        </div>
      </el-card>
    </div>
    <div class="bottom">
      <el-card class="box-card1" shadow="hover">
        <div id="chart1" class="chart"></div>
      </el-card>
      <el-card class="box-card2" shadow="hover">
        <div id="chart2" class="chart"></div>
      </el-card>
    </div>
  </div>
</template>

<script>
import * as echarts from '@/views/ECGScreen/detail/echarts.min'
export default {
  name: "Index",
  data() {
    return {

    }
  },
  created() {

  },

  mounted() {
    this.setchart1()
    this.setchart2()
  },
  methods: {
    setchart1(){
      let myChart1 = echarts.init(document.getElementById('chart1'));
      let option = {
        title: {
          text: '近一周交易金额柱状图',
          textStyle: {
            fontSize:18,
          }
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        legend: {
          right: "5%"
        },
        grid: {
          left: '3%',
          right: '10%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'value',
          boundaryGap: [0, 0.01],
          axisLine: {
            show: false
          },
          axisTick: {
            show: false
          },
          splitLine: {
            show: false
          },
          axisLabel: {
            show: false
          }
        },
        yAxis: {
          type: 'category',
          axisLine: {
            show: false
          },
          axisTick: {
            show: false
          },
          data: ['Brazil', 'Indonesia', 'USA', 'India', 'China', 'World']
        },
        series: [
          {
            name: '2011',
            type: 'bar',
            data: [1820, 2349, 2034, 1090, 1344, 6230],
            label: {
              show: true,
              position: "right"
            },
            itemStyle : {
              normal: {
                color: "#80c2b6",
                borderRadius: [6, 6, 6, 6],
                shadowBlur: 3,
                shadowColor: "#b9dcd6"
              }
            },
          },
          {
            name: '2012',
            type: 'bar',
            data: [1932, 2343, 3100, 1254, 1311, 6807],
            label: {
              show: true,
              position: "right"
            },
            itemStyle : {
              normal: {
                color: "#4ab2bb",
                borderRadius: [6, 6, 6, 6],
                shadowBlur: 3,
                shadowColor: "#b4d3d7"
              }
            },
          }
        ]
      }
      myChart1.setOption(option,true)
      setTimeout(()=>{
        myChart1.resize();
      })
      window.addEventListener('resize', () => {
        myChart1.resize();
      });
    },
    setchart2(){
      let myChart2 = echarts.init(document.getElementById('chart2'));
      let option = {
        title: {
          text: '近一周产品销量趋势图',
          textStyle: {
            fontSize:18,
          }
        },
        grid: {
          left: '2%',
          right: '2%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
          axisTick: {
            show: false
          },
          splitLine: {
            show: false
          },
          axisLine: {
            show: false
          }

        },
        yAxis: {
          type: 'value',
          axisTick: {
            show: false
          },
          splitLine: {
            show: false
          },
          axisLabel: {
            show: false
          }
        },
        series: [
          {
            data: [150, 230, 224, 218, 135, 147, 260],
            type: 'line',
            label: {
              show: true,
              position: "top"
            },
            areaStyle: {
              normal: {//自定义颜色，渐变色填充折线图区域
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1,//变化度
                  //渐变色
                  [{
                    offset: 0,
                    color: '#B7D3FD'
                  }, {
                    offset: 1,
                    color: '#dfdfe6'
                  }]),
              },
            }
          }
        ]
      };
      myChart2.setOption(option,true)
      setTimeout(()=>{
        myChart2.resize();
      })
      window.addEventListener('resize', () => {
        myChart2.resize();
      });
    }
  }
}
</script>

<style lang='scss'>
::v-deep  .el-card__body{
  height: 100%;
}
.icon{
  display: inline-block;
  width: 200px;
  height: 200px;
  margin-right: 10px;
  margin-left: 8px;
}
.home{
  background-color: #F2F2F2;
  width: 100%;
  padding: 10px 20px;
  height: calc(100vh - 84px);
  .top{
    display: flex;
    .box-card0 {
      width: 100%;
      //height: 25vh;
      margin:0 auto;
      .item4{
        display: flex;
        justify-content: space-between;
        height: 100%;
        .item{
          display: flex;
          flex-direction: column;
          justify-content: space-between;
          height: 100%;
          text-align: center;
          flex: 1;
          .title{
            font-size: 1.3vw;
            .icon{
              font-size: 1.6vw;
            }
          }
          .num{
            font-size: 1.5vw;
            margin: 1.5vh 0 2.5vh 2vh;
          }
        }
      }
    }
  }
  .bottom{
    display: flex;
    justify-content: space-between;
    margin-top: 20px;
    .box-card1 {
      width: 35%;
      //margin:10px auto;
      .chart{
        width: 100%;
        height: 50vh;
      }
    }
    .box-card2 {
      width: 64%;
      //margin:10px auto;
      .chart{
        width: 100%;
        height: 50vh;
      }
    }
  }

}

</style>
