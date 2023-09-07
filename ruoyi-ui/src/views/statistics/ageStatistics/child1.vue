<template>
  <div id="cf-double-column" :style="{ width: '100%', height: '100%' }">
    <p class="title"><span class="title-left"></span>心电患者男女比例图</p>
    <div id="chart" :style="{ width: '60vw', height: '500px' }"></div>
    <div class="footer-name">
      <p>男：{{menCount}}人</p>
      <p>女：{{womenCount}}人</p>
    </div>
  </div>
</template>

<script>
import echarts from "echarts";
import elementResizeDetectorMaker from "element-resize-detector";
import {selectAge} from "@/api/statistics/ageStatistics";
export default {
  name: "CFDoubleColumn",
  data(){
    return {
      columndata1: [],
      columndata2: [],
      menCount: 0,
      womenCount: 0,
    }
  },
  created() {
    this.getList();
  },
  mounted() {
    this.getDoubleCloumn();
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
      var data1 = this.columndata1;
      var data2 = this.columndata2;
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
      let colorLeft = ["#b75d5d", "#7F0E0F"];
      let colorRight = ["#1c9898", "#105270"];
      let option = {
        // title: {
        //   // text: "男                      女",
        //   top: "bottom",
        //   left: "center",
        //   textStyle: {
        //     color: "black ", //标签文字颜色改为白色
        //   },
        // },
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
          // data: ['规模猪场', '中小猪场']
        },
        grid: [
          {
            show: false,
            left: "2%",
            top: "10%",
            bottom: "8%",
            width: "40%",
          },
          {
            show: false,
            left: "50%",
            top: "10%",
            bottom: "8%",
            width: "0%",
          },
          {
            show: false,
            right: "4%",
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
            type: "log",
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
            type: "log",
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
            gridIndex: 0,
            triggerEvent: true,
            show: true,
            inverse: true,
            data: getArrByKey(data1, "name"),
            axisLine: {
              show: false,
            },
            splitLine: {
              show: false,
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
            data: getArrByKey(data1, "name"),
          },
          {
            gridIndex: 2,
            triggerEvent: true,
            show: true,
            inverse: true,
            data: getArrByKey(data2, "name"),
            axisLine: {
              show: false,
            },
            splitLine: {
              show: false,
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
                barBorderRadius: [10, 0, 0, 10],
              },
            },

            label: {
              normal: {
                show: true,
                position: "insideLeft",
                textStyle: {
                  color: "#ffffff",
                  fontSize: 15,
                },
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
                barBorderRadius: [0, 10, 10, 0],
              },
            },
            label: {
              normal: {
                show: true,
                position: "insideRight",
                textStyle: {
                  color: "#ffffff",
                  fontSize: 15,
                },
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
      selectAge(this.queryParams).then(response => {
        //console.log(Object.values(response)[1]);
        this.columndata1 = Object.values(response)[0];
        this.columndata2 = Object.values(response)[1];
        this.getDoubleCloumn();
        console.log(this.columndata1);

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
    },
  }
}
</script>

<style lang="scss" scoped>
#cf-double-column {
  position: relative;
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
      text-align: right;
    }
    p:nth-of-type(2) {
      text-align: left;
    }
  }
}
</style>
