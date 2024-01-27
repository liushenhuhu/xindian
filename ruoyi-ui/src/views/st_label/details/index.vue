<template>
  <div class="main">
    <div class="bottom">
      <div class="bottomLeft">
        <div class="xinDian"><span>日志id：{{logId}}</span> <span>诊断结果：{{diagnosticResult}}</span></div>
        <div class="echarts">
          <div class="container">
            <div class="chart" id="I"></div>
          </div>
          <div class="container">
            <div class="chart" id="II"></div>
          </div>
          <div class="container">
            <div class="chart" id="III"></div>
          </div>
          <div class="container">
            <div class="chart" id="aVR"></div>
          </div>
          <div class="container">
            <div class="chart" id="aVL"></div>
          </div>
          <div class="container">
            <div class="chart" id="aVF"></div>
          </div>
          <div class="container">
            <div class="chart" id="V1"></div>
          </div>
          <div class="container">
            <div class="chart" id="V2"></div>
          </div>
          <div class="container">
            <div class="chart" id="V3"></div>
          </div>
          <div class="container">
            <div class="chart" id="V4"></div>
          </div>
          <div class="container">
            <div class="chart" id="V5"></div>
          </div>
          <div class="container">
            <div class="chart" id="V6"></div>
          </div>
        </div>
      </div>
      <div class="topLeft">
        <div class="topMiddle">
          <el-button type="success" id="btn1" class="btn1" @click="clear()" style="background-color: ">清空</el-button>
          <el-button type="success" id="btn1" class="btn1" @click="submit()">提交</el-button>
        </div>
        <div class="selectTag">
          <div class="tagItem">
            <el-checkbox-group v-model="tagI" style="width:90%">
              <el-checkbox v-for="item in options" :label="item"></el-checkbox>
            </el-checkbox-group>
          </div>
          <div class="tagItem">
            <el-checkbox-group v-model="tagII" style="width:90%">
              <el-checkbox v-for="item in options" :label="item"></el-checkbox>
            </el-checkbox-group>
          </div>
          <div class="tagItem">
            <el-checkbox-group v-model="tagIII" style="width:90%">
              <el-checkbox v-for="item in options" :label="item"></el-checkbox>
            </el-checkbox-group>
          </div>
          <div class="tagItem">
            <el-checkbox-group v-model="tagaVR"  style="width:90%">
              <el-checkbox v-for="item in options" :label="item"></el-checkbox>
            </el-checkbox-group>
          </div>
          <div class="tagItem">
            <el-checkbox-group v-model="tagaVL"  style="width:90%">
              <el-checkbox v-for="item in options" :label="item"></el-checkbox>
            </el-checkbox-group>
          </div>
          <div class="tagItem">
            <el-checkbox-group v-model="tagaVF"  style="width:90%">
              <el-checkbox v-for="item in options" :label="item"></el-checkbox>
            </el-checkbox-group>
          </div>
          <div class="tagItem">
            <el-checkbox-group v-model="tagV1"  style="width:90%">
              <el-checkbox v-for="item in options" :label="item"></el-checkbox>
            </el-checkbox-group>
          </div>
          <div class="tagItem">
            <el-checkbox-group v-model="tagV2" style="width:90%">
              <el-checkbox v-for="item in options" :label="item"></el-checkbox>
            </el-checkbox-group>
          </div>
          <div class="tagItem">
            <el-checkbox-group v-model="tagV3"  style="width:90%">
              <el-checkbox v-for="item in options" :label="item"></el-checkbox>
            </el-checkbox-group>
          </div>
          <div class="tagItem">
            <el-checkbox-group v-model="tagV4"  style="width:90%">
              <el-checkbox v-for="item in options" :label="item"></el-checkbox>
            </el-checkbox-group>
          </div>
          <div class="tagItem">
            <el-checkbox-group v-model="tagV5" style="width:90%">
              <el-checkbox v-for="item in options" :label="item"></el-checkbox>
            </el-checkbox-group>
          </div>
          <div class="tagItem">
            <el-checkbox-group v-model="tagV6" style="width:90%">
              <el-checkbox v-for="item in options" :label="item"></el-checkbox>
            </el-checkbox-group>
          </div>
        </div>
        <div class="page">
<!--          <el-button class="next"  @click="prev" type="primary" :loading="loading">上一个</el-button>-->
<!--          <el-button class="next"  @click="next" :loading="loading">下一个</el-button>-->
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import * as echarts from "@/views/ECGScreen/detail/echarts.min";
import de from "element-ui/src/locale/lang/de";
import {getStLabel, updateStLabel} from "@/api/stLabel/stLabel";
export default {
  name: "lookLog1",
  computed: {
    de() {
      return de
    }
  },
  data() {
    return {
      radio:'',
      //通过字典将radio和light一一对应
      logId:'',
      diagnosticResult:"",
      options:[ "T波改变", "ST段改变", "ST-T改变", "U波倒置","Q波", "QT间期延长",],
      timex:[],
      show:false,
      seriesdata:[
        {yAxis: -3}, {yAxis: -2.5}, {yAxis: -2}, {yAxis: -1.5}, {yAxis: -1}, {yAxis: -0.5}, {yAxis: 0}, {yAxis: 0.5}, {yAxis: 1}, {yAxis: 1.5}, {yAxis: 2}, {yAxis: 2.5}, {yAxis: 3}],
      seriesdata1:[
        {yAxis: -1}, {yAxis: -0.5}, {yAxis: 0}, {yAxis: 0.5}, {yAxis: 1},],
      data:{},
      markArea:[],
      title:'',
      arr:[],//要删除的点所在的区间
      isSuspected:false,
      pageNum:1,
      index:0,
      loading:false,
      tagI:[],
      tagII:[],
      tagIII:[],
      tagaVR:[],
      tagaVL:[],
      tagaVF:[],
      tagV1:[],
      tagV2:[],
      tagV3:[],
      tagV4:[],
      tagV5:[],
      tagV6:[],
    };
  },
  created() {
      this.logId=this.$route.query.logId
  },
  mounted() {
    this.getMessage()
  },
  methods: {
    //获取心电数据
    getMessage() {

      var Iy = [];
      var IIy = [];
      var IIIy = [];
      var aVLy = [];
      var aVFy = [];
      var aVRy = [];
      var V1y = [];
      var V2y = [];
      var V3y = [];
      var V4y = [];
      var V5y = [];
      var V6y = [];
      var timex=[];
      var chartI = echarts.init(document.getElementById('I'));
      var chartII = echarts.init(document.getElementById('II'));
      var chartIII = echarts.init(document.getElementById('III'));
      var chartV1 = echarts.init(document.getElementById('V1'));
      var chartV2 = echarts.init(document.getElementById('V2'));
      var chartV3 = echarts.init(document.getElementById('V3'));
      var chartV4 = echarts.init(document.getElementById('V4'));
      var chartV5 = echarts.init(document.getElementById('V5'));
      var chartV6 = echarts.init(document.getElementById('V6'));
      var chartaVR = echarts.init(document.getElementById('aVR'));
      var chartaVL = echarts.init(document.getElementById('aVL'));
      var chartaVF = echarts.init(document.getElementById('aVF'));
      getStLabel(this.logId).then(res => {
        this.diagnosticResult=res.data.diagnosticResult
        for (let key in JSON.parse(res.data.labelJson)) {
          this[`${'tag' + key}`]=JSON.parse(res.data.labelJson)[key].split(',')
        }
        for (var k = 0; k <2001; k++) {
          timex.push(k / 100+"秒")
        }
        this.timex=timex
        Iy=res.data.ecgDataJson[0]
        IIy=res.data.ecgDataJson[1]
        IIIy=res.data.ecgDataJson[2]
        aVLy=res.data.ecgDataJson[4]
        aVRy=res.data.ecgDataJson[3]
        aVFy=res.data.ecgDataJson[5]
        V1y=res.data.ecgDataJson[6]
        V2y=res.data.ecgDataJson[7]
        V3y=res.data.ecgDataJson[8]
        V4y=res.data.ecgDataJson[9]
        V5y=res.data.ecgDataJson[10]
        V6y=res.data.ecgDataJson[11]
        for (var i = 0; i < 2000; i+=20) {
          this.seriesdata.push({xAxis: i})
        }
        var seriesdata=this.seriesdata
        chartI.clear()
        chartI.setOption({
          animation: false,
          animationDuration: 9900,
          animationEasing: "linear",
          animationEasingUpdate: 'linear',
          animationDurationUpdate: 10000,
          animationDelayUpdate: 0,
          animationThreshold: 10000,
          backgroundColor: "#ffffff",
          title: {
            text: 'I导联',
            textStyle: {
              fontSize: 12,
              color: "#000000"
            },
            left: 30
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross'
            }
          },
          grid: {
            left: 20,
            right: 0,
            top: 2,
            bottom: 2
          },
          xAxis: {
            type:'category',
            boundaryGap: false,
            data: timex,
            axisTick: {
              show: false
            },
            axisLabel: { //修改坐标系字体颜色
              show: false,
              interval: 4,
              textStyle: {
                color: "#000000"
              }
            },
            splitLine: {
              show: true,

              lineStyle: {
                color: 'pink',
                width: 0.5, //网格的宽度
                type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
              },
            } /*网格线*/
          },
          yAxis: {
            min: -1,
            max: 1,
            boundaryGap: true,
            interval: 0.1,
            // splitNumber: 20,
            axisLabel: { //修改坐标系字体颜色
              show: false,
              textStyle: {
                color: "#000000"
              }
            },
            splitLine: {
              lineStyle: {
                color: "pink",
                width: 1, //网格的宽度
                type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
              },
              show: true
            } /*网格线*/
          },
          series: {
            markLine: {
              animation: false,
              symbol: "none",
              silent: true,
              lineStyle: {
                type: "solid",
                color: '#b33939',
                width: 0.5,
                // opacity: 0.5,
              },
              label: {
                position: 'start', // 表现内容展示的位置
                color: '#b33939'  // 展示内容颜色
              },
              data: seriesdata
            },
            /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
            itemStyle: {
              normal: {
                lineStyle: {
                  width:1.5,
                  color: '#000000' /*折线的颜色*/
                },
                color: "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/
              }
            },
            symbol: "none",
            /*去掉小圆点*/
            name: 'I导联',
            type: 'line',
            data: Iy,
            smooth: 0 //显示为平滑的曲线*/
          }
        })
        chartII.clear()
        chartII.setOption({
          animation: false,
          animationDuration: 9900,
          animationEasing: "linear",
          animationEasingUpdate: 'linear',
          animationDurationUpdate: 10000,
          animationDelayUpdate: 0,
          animationThreshold: 10000,
          backgroundColor: "#ffffff",
          title: {
            text: 'II导联',
            textStyle: {
              fontSize: 12,
              color: "#000000"
            },
            left: 30
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross'
            }
          },
          grid: {
            left: 20,
            right: 0,
            top: 2,
            bottom: 2
          },

          xAxis: {
            type:'category',
            boundaryGap: false,
            data: timex,
            axisTick: {
              show: false
            },
            axisLabel: { //修改坐标系字体颜色
              show: false,
              interval: 4,
              textStyle: {
                color: "#000000"
              }
            },
            splitLine: {
              show: true,

              lineStyle: {
                color: 'pink',
                width: 0.5, //网格的宽度
                type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
              },
            } /*网格线*/
          },
          yAxis: {
            min: -1,
            max: 1,
            boundaryGap: true,
            interval: 0.1,
            axisLabel: { //修改坐标系字体颜色
              show: false,
              textStyle: {
                color: "#000000"
              }
            },
            splitLine: {
              lineStyle: {
                color: "pink",
                width: 1, //网格的宽度
                type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
              },
              show: true
            } /*网格线*/
          },
          series: {
            markLine: {
              animation: false,
              symbol: "none",
              silent: true,
              lineStyle: {
                type: "solid",
                color: '#b33939',
                width: 0.5,

              },
              label: {
                position: 'start', // 表现内容展示的位置
                color: '#b33939'  // 展示内容颜色
              },
              data: seriesdata
            },
            /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
            itemStyle: {
              normal: {
                lineStyle: {
                  width:1.5,
                  color: '#000000' /*折线的颜色*/
                },
                color: "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/
              }
            },
            symbol: "none",
            /*去掉小圆点*/
            name: 'II导联',
            type: 'line',
            data: IIy,
            smooth: 0 //显示为平滑的曲线*/
          }
        })
        chartV1.clear()
        chartV1.setOption({
          animation: false,
          animationDuration: 9900,
          animationEasing: "linear",
          animationEasingUpdate: 'linear',
          animationDurationUpdate: 10000,
          animationDelayUpdate: 0,
          animationThreshold: 10000,
          backgroundColor: "#ffffff",
          title: {
            text: 'V1导联',
            textStyle: {
              fontSize: 12,
              color: "#000000"
            },
            left: 30
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross'
            }
          },
          grid: {
            left: 20,
            right: 0,
            top: 2,
            bottom: 2
          },
          xAxis: {
            type:'category',
            boundaryGap: false,
            data: timex,
            axisTick: {
              show: false
            },
            axisLabel: { //修改坐标系字体颜色
              show: false,
              interval: 4,
              textStyle: {
                color: "#000000"
              }
            },
            splitLine: {
              show: true,
              lineStyle: {
                color: 'pink',
                width: 0.5, //网格的宽度
                type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
              },
            } /*网格线*/
          },
          yAxis: {
            min: -1,
            max: 1,
            boundaryGap: true,
            interval: 0.1,
            // splitNumber: 20,
            axisLabel: { //修改坐标系字体颜色
              show: false,
              textStyle: {
                color: "#000000"
              }
            },
            splitLine: {
              lineStyle: {
                color: "pink",
                width: 1, //网格的宽度
                type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
              },
              show: true
            } /*网格线*/
          },
          series: {
            markLine: {
              animation: false,
              symbol: "none",
              silent: true,
              lineStyle: {
                type: "solid",
                color: '#b33939',
                width: 0.5,
                // opacity: 0.5,
              },
              label: {
                position: 'start', // 表现内容展示的位置
                color: '#b33939'  // 展示内容颜色
              },
              data: seriesdata
            },
            /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
            itemStyle: {
              normal: {
                lineStyle: {
                  width:1.5,
                  color: '#000000' /*折线的颜色*/
                },
                color: "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/
              }
            },
            symbol: "none",
            /*去掉小圆点*/
            name: 'V1导联',
            type: 'line',
            data: V1y,
            smooth: 0 //显示为平滑的曲线*/
          }
        })
        chartV2.clear()
        chartV2.setOption({
          animation: false,
          animationDuration: 9900,
          animationEasing: "linear",
          animationEasingUpdate: 'linear',
          animationDurationUpdate: 10000,
          animationDelayUpdate: 0,
          animationThreshold: 10000,
          backgroundColor: "#ffffff",
          title: {
            text: 'V2导联',
            textStyle: {
              fontSize: 12,
              color: "#000000",
            },
            left: 30
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross'
            }
          },
          grid: {
            left: 20,
            right: 0,
            top: 2,
            bottom: 2
          },
          xAxis: {
            type:'category',
            boundaryGap: false,
            data: timex,
            axisTick: {
              show: false
            },
            axisLabel: { //修改坐标系字体颜色
              show: false,
              interval: 4,
              textStyle: {
                color: "#000000"
              }
            },
            splitLine: {
              show: true,
              lineStyle: {
                color: 'pink',
                width: 0.5, //网格的宽度
                type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
              },
            } /*网格线*/
          },
          yAxis: {
            min: -1,
            max: 1,
            boundaryGap: true,
            interval: 0.1,
            axisLabel: { //修改坐标系字体颜色
              show: false,
              textStyle: {
                color: "#000000"
              }
            },
            splitLine: {
              lineStyle: {
                color: "pink",
                width: 1, //网格的宽度
                type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
              },
              show: true
            } /*网格线*/
          },
          series: {
            markLine: {
              animation: false,
              symbol: "none",
              silent: true,
              lineStyle: {
                type: "solid",
                color: '#b33939',
                width: 0.5,

              },
              label: {
                position: 'start', // 表现内容展示的位置
                color: '#b33939'  // 展示内容颜色
              },
              data: seriesdata
            },
            /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
            itemStyle: {
              normal: {
                lineStyle: {
                  width:1.5,
                  color: '#000000' /*折线的颜色*/
                },
                color: "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/
              }
            },
            symbol: "none",
            /*去掉小圆点*/
            name: 'V2导联',
            type: 'line',
            data: V2y,
            smooth: 0 //显示为平滑的曲线*/
          }
        })
        chartV3.clear()
        chartV3.setOption({
          animation: false,
          animationDuration: 9900,
          animationEasing: "linear",
          animationEasingUpdate: 'linear',
          animationDurationUpdate: 10000,
          animationDelayUpdate: 0,
          animationThreshold: 10000,
          backgroundColor: "#ffffff",
          title: {
            text: 'V3导联',
            textStyle: {
              fontSize: 12,
              color: "#000000"
            },
            left: 30
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross'
            }
          },
          grid: {
            left: 20,
            right: 0,
            top: 2,
            bottom: 2
          },
          xAxis: {
            type:'category',
            boundaryGap: false,
            data: timex,
            axisTick: {
              show: false
            },
            axisLabel: { //修改坐标系字体颜色
              show: false,
              interval: 4,
              textStyle: {
                color: "#000000"
              }
            },
            splitLine: {
              show: true,
              lineStyle: {
                color: 'pink',
                width: 0.5, //网格的宽度
                type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
              },
            } /*网格线*/
          },
          yAxis: {
            min: -1,
            max: 1,
            boundaryGap: true,
            interval: 0.1,
            axisLabel: { //修改坐标系字体颜色
              show: false,
              textStyle: {
                color: "#000000"
              }
            },
            splitLine: {
              lineStyle: {
                color: "pink",
                width: 1, //网格的宽度
                type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
              },
              show: true
            } /*网格线*/
          },
          series: {
            markLine: {
              animation: false,
              symbol: "none",
              silent: true,
              lineStyle: {
                type: "solid",
                color: '#b33939',
                width: 0.5,
              },
              label: {
                position: 'start', // 表现内容展示的位置
                color: '#b33939'  // 展示内容颜色
              },
              data: seriesdata
            },
            /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
            itemStyle: {
              normal: {
                lineStyle: {
                  width:1.5,
                  color: '#000000' /*折线的颜色*/
                },
                color: "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/
              }
            },
            symbol: "none",
            /*去掉小圆点*/
            name: 'V3导联',
            type: 'line',
            data: V3y,
            smooth: 0 //显示为平滑的曲线*/
          }
        })
        chartV4.clear()
        chartV4.setOption({
          animation: false,
          animationDuration: 9900,
          animationEasing: "linear",
          animationEasingUpdate: 'linear',
          animationDurationUpdate: 10000,
          animationDelayUpdate: 0,
          animationThreshold: 10000,
          backgroundColor: "#ffffff",
          title: {
            text: 'V4导联',
            textStyle: {
              fontSize: 12,
              color: "#000000"
            },
            left: 30
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross'
            }
          },
          grid: {
            left: 20,
            right: 0,
            top: 2,
            bottom: 2
          },

          xAxis: {
            type:'category',
            boundaryGap: false,
            data: timex,
            axisTick: {
              show: false
            },
            axisLabel: { //修改坐标系字体颜色
              show: false,
              interval: 4,
              textStyle: {
                color: "#000000"
              }
            },
            splitLine: {
              show: true,
              lineStyle: {
                color: 'pink',
                width: 0.5, //网格的宽度
                type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
              },
            } /*网格线*/
          },
          yAxis: {
            min: -1,
            max: 1,
            boundaryGap: true,
            interval: 0.1,
            // splitNumber: 20,
            axisLabel: { //修改坐标系字体颜色
              show: false,
              textStyle: {
                color: "#000000"
              }
            },
            splitLine: {
              lineStyle: {
                color: "pink",
                width: 1, //网格的宽度
                type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
              },
              show: true
            } /*网格线*/
          },
          series: {
            markLine: {
              animation: false,
              symbol: "none",
              silent: true,
              lineStyle: {
                type: "solid",
                color: '#b33939',
                width: 0.5,
              },
              label: {
                position: 'start', // 表现内容展示的位置
                color: '#b33939'  // 展示内容颜色
              },
              data: seriesdata
            },
            /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
            itemStyle: {
              normal: {
                lineStyle: {
                  width:1.5,
                  color: '#000000' /*折线的颜色*/
                },
                color: "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/
              }
            },
            symbol: "none",
            /*去掉小圆点*/
            name: 'V4导联',
            type: 'line',
            data: V4y,
            smooth: 0 //显示为平滑的曲线*/
          }
        })
        chartV5.clear()
        chartV5.setOption({
          animation: false,
          animationDuration: 9900,
          animationEasing: "linear",
          animationEasingUpdate: 'linear',
          animationDurationUpdate: 10000,
          animationDelayUpdate: 0,
          animationThreshold: 10000,
          backgroundColor: "#ffffff",
          title: {
            text: 'V5导联',
            textStyle: {
              fontSize: 12,
              color: "#000000"
            },
            left: 30
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross'
            }
          },
          grid: {
            left: 20,
            right: 0,
            top: 2,
            bottom: 2
          },
          xAxis: {
            type:'category',
            boundaryGap: false,
            data: timex,
            axisTick: {
              show: false
            },
            axisLabel: { //修改坐标系字体颜色
              show: false,
              interval: 4,
              textStyle: {
                color: "#000000"
              }
            },
            splitLine: {
              show: true,
              lineStyle: {
                color: 'pink',
                width: 0.5, //网格的宽度
                type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
              },
            } /*网格线*/
          },
          yAxis: {
            min: -1,
            max: 1,
            boundaryGap: true,
            interval: 0.1,
            axisLabel: { //修改坐标系字体颜色
              show: false,
              textStyle: {
                color: "#000000"
              }
            },
            splitLine: {
              lineStyle: {
                color: "pink",
                width: 1, //网格的宽度
                type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
              },
              show: true
            } /*网格线*/
          },
          series: {
            markLine: {
              animation: false,
              symbol: "none",
              silent: true,
              lineStyle: {
                type: "solid",
                color: '#b33939',
                width: 0.5,

              },
              label: {
                position: 'start', // 表现内容展示的位置
                color: '#b33939'  // 展示内容颜色
              },
              data: seriesdata
            },
            /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
            itemStyle: {
              normal: {
                lineStyle: {
                  width:1.5,
                  color: '#000000' /*折线的颜色*/
                },
                color: "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/
              }
            },
            symbol: "none",
            /*去掉小圆点*/
            name: 'V5导联',
            type: 'line',
            data: V5y,
            smooth: 0 //显示为平滑的曲线*/
          }
        })
        chartV6.clear()
        chartV6.setOption({
          animation: false,
          animationDuration: 9900,
          animationEasing: "linear",
          animationEasingUpdate: 'linear',
          animationDurationUpdate: 10000,
          animationDelayUpdate: 0,
          animationThreshold: 10000,
          backgroundColor: "#ffffff",
          title: {
            text: 'V6导联',
            textStyle: {
              fontSize: 12,
              color: "#000000"

            },
            left: 30
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross'
            }
          },
          grid: {
            left: 20,
            right: 0,
            top: 2,
            bottom: 2
          },
          xAxis: {
            type:'category',
            boundaryGap: false,
            data: timex,
            axisTick: {
              show: false
            },
            axisLabel: { //修改坐标系字体颜色
              show: true,
              interval: 4,
              textStyle: {
                color: "#000000"
              }
            },
            splitLine: {
              show: true,
              lineStyle: {
                color: 'pink',
                width: 0.5, //网格的宽度
                type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
              },
            } /*网格线*/
          },
          yAxis: {
            min: -1,
            max: 1,
            boundaryGap: true,
            interval: 0.1,
            axisLabel: { //修改坐标系字体颜色
              show: false,
              textStyle: {
                color: "#000000"
              }
            },
            splitLine: {
              lineStyle: {
                color: "pink",
                width: 1, //网格的宽度
                type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
              },
              show: true
            } /*网格线*/
          },
          series: [{
            markLine: {
              animation: false,
              symbol: "none",
              silent: true,
              lineStyle: {
                type: "solid",
                color: '#b33939',
                width: 0.5,
              },
              label: {
                position: 'start', // 表现内容展示的位置
                color: '#b33939'  // 展示内容颜色
              },
              data: seriesdata
            },
            /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
            itemStyle: {
              normal: {
                lineStyle: {
                  width:1.5,
                  color: '#000000' /*折线的颜色*/
                },
                color: "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/
              }
            },
            symbol: "none",
            /*去掉小圆点*/
            name: '当前电位',
            type: 'line',
            data: V6y,
            smooth: 0 //显示为平滑的曲线*/
          },]
        })
        chartIII.clear()
        chartIII.setOption({
          animation: false,
          animationDuration: 9900,
          animationEasing: "linear",
          animationEasingUpdate: 'linear',
          animationDurationUpdate: 10000,
          animationDelayUpdate: 0,
          animationThreshold: 10000,
          backgroundColor: "#ffffff",
          title: {
            text: 'III导联',
            textStyle: {
              fontSize: 12,
              color: "#000000"
            },
            left: 30
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross'
            }
          },
          grid: {
            left: 20,
            right: 0,
            top: 2,
            bottom: 2
          },

          xAxis: {
            type:'category',
            boundaryGap: false,
            data: timex,
            axisTick: {
              show: false
            },
            axisLabel: { //修改坐标系字体颜色
              show: false,
              interval: 4,
              textStyle: {
                color: "#000000"
              }
            },
            splitLine: {
              show: true,
              lineStyle: {
                color: 'pink',
                width: 0.5, //网格的宽度
                type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
              },
            } /*网格线*/
          },
          yAxis: {
            min: -1,
            max: 1,
            boundaryGap: true,
            interval: 0.1,
            // splitNumber: 20,
            axisLabel: { //修改坐标系字体颜色
              show: false,
              textStyle: {
                color: "#000000"
              }
            },
            splitLine: {
              lineStyle: {
                color: "pink",
                width: 1, //网格的宽度
                type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
              },
              show: true
            } /*网格线*/
          },
          series: {
            markLine: {
              animation: false,
              symbol: "none",
              silent: true,
              lineStyle: {
                type: "solid",
                color: '#b33939',
                width: 0.5,
              },
              label: {
                position: 'start', // 表现内容展示的位置
                color: '#b33939'  // 展示内容颜色
              },
              data: seriesdata
            },
            /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
            itemStyle: {
              normal: {
                lineStyle: {
                  width:1.5,
                  color: '#000000' /*折线的颜色*/
                },
                color: "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/
              }
            },
            symbol: "none",
            /*去掉小圆点*/
            name: 'III导联',
            type: 'line',
            data: IIIy,
            smooth: 0 //显示为平滑的曲线*/
          }
        });
        chartaVL.clear();
        chartaVL.setOption({
          animation: false,
          animationDuration: 9900,
          animationEasing: "linear",
          animationEasingUpdate: 'linear',
          animationDurationUpdate: 10000,
          animationDelayUpdate: 0,
          animationThreshold: 10000,
          backgroundColor: "#ffffff",
          title: {
            text: 'aVL导联',
            textStyle: {
              fontSize: 12,
              color: "#000000"
            },
            left: 30
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross'
            }
          },
          grid: {
            left: 20,
            right: 0,
            top: 2,
            bottom: 2
          },
          xAxis: {
            type:'category',
            boundaryGap: false,
            data: timex,
            axisTick: {
              show: false
            },
            axisLabel: { //修改坐标系字体颜色
              show: false,
              interval: 4,
              textStyle: {
                color: "#000000"
              }
            },
            splitLine: {
              show: true,
              lineStyle: {
                color: 'pink',
                width: 0.5, //网格的宽度
                type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
              },
            } /*网格线*/
          },
          yAxis: {
            min: -1,
            max: 1,
            boundaryGap: true,
            interval: 0.1,
            axisLabel: { //修改坐标系字体颜色
              show: false,
              textStyle: {
                color: "#000000"
              }
            },
            splitLine: {
              lineStyle: {
                color: "pink",
                width: 1, //网格的宽度
                type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
              },
              show: true
            } /*网格线*/
          },
          series: {
            markLine: {
              animation: false,
              symbol: "none",
              silent: true,
              lineStyle: {
                type: "solid",
                color: '#b33939',
                width: 0.5,

              },
              label: {
                position: 'start', // 表现内容展示的位置
                color: '#b33939'  // 展示内容颜色
              },
              data: seriesdata
            },
            /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
            itemStyle: {
              normal: {
                lineStyle: {
                  width:1.5,
                  color: '#000000' /*折线的颜色*/
                },
                color: "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/
              }
            },
            symbol: "none",
            /*去掉小圆点*/
            name: 'aVL导联',
            type: 'line',
            data: aVLy,
            smooth: 0 //显示为平滑的曲线*/
          }
        });
        chartaVF.clear()
        chartaVF.setOption({
          animation: false,
          animationDuration: 9900,
          animationEasing: "linear",
          animationEasingUpdate: 'linear',
          animationDurationUpdate: 10000,
          animationDelayUpdate: 0,
          animationThreshold: 10000,
          backgroundColor: "#ffffff",
          title: {
            text: 'aVF导联',
            textStyle: {
              fontSize: 12,
              color: "#000000"
            },
            left: 30
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross'
            }
          },
          grid: {
            left: 20,
            right: 0,
            top: 2,
            bottom: 2
          },
          xAxis: {
            type:'category',
            boundaryGap: false,
            data: timex,
            axisTick: {
              show: false
            },
            axisLabel: { //修改坐标系字体颜色
              show: false,
              interval: 4,
              textStyle: {
                color: "#000000"
              }
            },
            splitLine: {
              show: true,
              lineStyle: {
                color: 'pink',
                width: 0.5, //网格的宽度
                type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
              },
            } /*网格线*/
          },
          yAxis: {
            min: -1,
            max: 1,
            boundaryGap: true,
            interval: 0.1,
            axisLabel: { //修改坐标系字体颜色
              show: false,
              textStyle: {
                color: "#000000"
              }
            },
            splitLine: {
              lineStyle: {
                color: "pink",
                width: 1, //网格的宽度
                type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
              },
              show: true
            } /*网格线*/
          },
          series: {
            markLine: {
              animation: false,
              symbol: "none",
              silent: true,
              lineStyle: {
                type: "solid",
                color: '#b33939',
                width: 0.5,

              },
              label: {
                position: 'start', // 表现内容展示的位置
                color: '#b33939'  // 展示内容颜色
              },
              data: seriesdata
            },
            /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
            itemStyle: {
              normal: {
                lineStyle: {
                  width:1.5,
                  color: '#000000' /*折线的颜色*/
                },
                color: "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/
              }
            },
            symbol: "none",
            /*去掉小圆点*/
            name: 'aVF导联',
            type: 'line',
            data: aVFy,
            smooth: 0 //显示为平滑的曲线*/
          }
        });
        chartaVR.clear();
        chartaVR.setOption({
          animation: false,
          animationDuration: 9900,
          animationEasing: "linear",
          animationEasingUpdate: 'linear',
          animationDurationUpdate: 10000,
          animationDelayUpdate: 0,
          animationThreshold: 10000,
          backgroundColor: "#ffffff",
          title: {
            text: 'aVR导联',
            textStyle: {
              fontSize: 12,
              color: "#000000"
            },
            left: 30
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross'
            }
          },
          grid: {
            left: 20,
            right: 0,
            top: 2,
            bottom: 2
          },
          xAxis: {
            type:'category',
            boundaryGap: false,
            data: timex,
            axisTick: {
              show: false
            },
            axisLabel: { //修改坐标系字体颜色
              show: false,
              interval: 4,
              textStyle: {
                color: "#000000"
              }
            },
            splitLine: {
              show: true,
              lineStyle: {
                color: 'pink',
                width: 0.5, //网格的宽度
                type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
              },
            } /*网格线*/
          },
          yAxis: {
            min: -1,
            max: 1,
            boundaryGap: true,
            interval: 0.1,
            // splitNumber: 20,
            axisLabel: { //修改坐标系字体颜色
              show: false,
              textStyle: {
                color: "#000000"
              }
            },
            splitLine: {
              lineStyle: {
                color: "pink",
                width: 1, //网格的宽度
                type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
              },
              show: true
            } /*网格线*/
          },
          series: {
            markLine: {
              animation: false,
              symbol: "none",
              silent: true,
              lineStyle: {
                type: "solid",
                color: '#b33939',
                width: 0.5,
              },
              label: {
                position: 'start', // 表现内容展示的位置
                color: '#b33939'  // 展示内容颜色
              },
              data: seriesdata
            },
            /*itemStyle: {normal: {areaStyle: {type: 'default'}}},*/
            itemStyle: {
              normal: {
                lineStyle: {
                  width:1.5,
                  color: '#000000' /*折线的颜色*/
                },
                color: "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/
              }
            },
            symbol: "none",
            /*去掉小圆点*/
            name: 'aVR导联',
            type: 'line',
            data: aVRy,
            smooth: 0 //显示为平滑的曲线*/
          }
        });
        window.addEventListener('resize', ()=>{
          chartI.resize();
          chartII.resize();
          chartIII.resize();
          chartaVL.resize();
          chartaVR.resize();
          chartaVF.resize();
          chartV1.resize();
          chartV2.resize();
          chartV3.resize();
          chartV4.resize();
          chartV5.resize();
          chartV6.resize();
        });
      });

    },
    async prev(){
      this.loading=true
      if(this.pageNum==1&&this.index==0){
        this.$message.warning("已经是第一页！！！")
        this.loading=false
        return
      }
      this.index--
      if(this.index<0){
        if(this.pageNum>1){
          this.pageNum--
        }
        await this.getLogUserList()
        this.index=9
      }
      console.log(this.logUserList[this.index])
      this.logId=this.logUserList[this.index].logId
      this.message.user_id = this.logUserList[this.index].userId
      let anoStatus=''
      if(this.anoStatus!=null){
        anoStatus=`&anoStatus=${this.anoStatus}`
      }
      var newUrl = this.$route.path + `?logId=${this.message.logid}&userId=${this.message.user_id}&pageNum=${this.pageNum}`+anoStatus
      window.history.replaceState('', '', newUrl)
      this.getMessage()
    },
    async next(){
      this.loading=true
      this.index++
      if(this.index>=this.logUserList.length){
        if((this.pageNum-1)*10+this.logUserList.length>=this.logUserListTotal){
          this.$message.warning("已经是最后一页！！！")
          this.index--
          this.loading=false
          return
        }
        this.pageNum++
        await this.getLogUserList()
        this.index=0
      }
      console.log(this.logUserList[this.index])
      this.message.logid=this.logUserList[this.index].logId
      this.message.user_id = this.logUserList[this.index].userId
      let anoStatus=''
      if(this.anoStatus!=null){
        anoStatus=`&anoStatus=${this.anoStatus}`
      }
      var newUrl = this.$route.path + `?logId=${this.message.logid}&userId=${this.message.user_id}&pageNum=${this.pageNum}`+anoStatus
      window.history.replaceState('', '', newUrl)
      this.getMessage()
    },
    clear(){
      this.tagI=[]
      this.tagII=[]
      this.tagIII=[]
      this.tagIII=[]
      this.tagaVL=[]
      this.tagaVR=[]
      this.tagaVF=[]
      this.tagV1=[]
      this.tagV2=[]
      this.tagV3=[]
      this.tagV4=[]
      this.tagV5=[]
      this.tagV6=[]
    },
    submit() {
      console.log(this.tagI,this.tagII,this.tagIII,this.tagaVL,this.tagaVR,this.tagaVF,this.tagV1,this.tagV2,this.tagV3,this.tagV4,this.tagV5,this.tagV6)
      var labelJson = {
        "I": this.tagI.toString(),
        "II": this.tagII.toString(),
        "III": this.tagIII.toString(),
        "aVL": this.tagaVL.toString(),
        "aVR": this.tagaVR.toString(),
        "aVF": this.tagaVF.toString(),
        "V1": this.tagV1.toString(),
        "V2": this.tagV2.toString(),
        "V3": this.tagV3.toString(),
        "V4": this.tagV4.toString(),
        "V5": this.tagV5.toString(),
        "V6": this.tagV6.toString()
      }
      var obj={
          logId:this.logId,
          labelJson: JSON.stringify(labelJson),
          isLabel: 1,
      }
      updateStLabel(obj).then(response => {
        this.$modal.msgSuccess("提交成功");
      });
    },

  },
};
</script>

<style scoped lang="scss">

body,html{
  color: #00afff;
  box-sizing: border-box;
}
.main {
  width: 100%;
  background: #f1faff;
}

.top {
  height: 9vw;
  width: 100%;
  //border: 4px solid #62be38;
  display: flex;
  flex-direction: row;
  flex-wrap: nowrap;
  justify-content: flex-start;
}

.topLeft {
  width:20%;
  //height: 100%;
  border-left: 1px solid #136d87;
  display: flex;
  flex-direction: column;
}
.selectTag{
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
}
.tagItem{
  height: 7.5vh;
  border-top: 1px solid #136d87;
  display: flex;
  justify-content: center;
  align-items: center;
}
.topMiddle {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 10px;
  //border: 1px solid rgba(0, 0, 0,);
}

.topRight {
  width: 100%;
  height: 100%;
}

.page{
  display: flex;
  justify-content: center;

}
::v-deep .next{
  background-color: rgba(255, 255, 255, 0);
  color: #136d87;
  border:1px solid #136d87;
  width: 100px;
  margin-right: 10px;
}
.btn1{
  font-size: 1vw;
  color: #136d87;
  background-color: rgba(255, 255, 255, 0);
  padding: 10px;
}
.btn1:hover{
  color: white;
  background-color: #4cc9f0;
}
form input {
  width: 3vw;
  height: 2vw;
  margin: 1vw;
}


.bottom {
  height: 100%;
  width: 98.5%;
  border: 1px solid #136d87;
  display: flex;
  flex-direction: row;
  flex-wrap: nowrap;
  margin: 1vw;
  //padding: 1vw;
  justify-content: flex-start;
}

.bottomLeft {
  width: 80%;
  height: 100%;
  border-top: none;
}

.xinDian {
  width: 100%;
  font-size: 1vw;
  font-weight: 700;
  color: #136d87;
  padding: 10px;
  display: flex;
  flex-direction: column;
  //border-top: 1px solid #136d87;
}
.xinDian:first-child{
  border-right: 1px solid #136d87;
}
.echarts {
  width: 100%;
  height: 95%;
  border-top: 1px solid #136d87;
  border-right: 1px solid #136d87;
}

.bottomLeft .echarts .container {
  width: 100%;
  height: 100%;
  //border: 1px solid rgba(25, 186, 139, 0.17);
  position: relative;
  .btn{
    background-color: #91dff8;
  }
  .btn:hover{
    background-color: #3ec9f7;
  }
}

.bottomLeft .echarts .container .chart {
  height: 7.5vh;
  width: 100%;
  //background-color: #ffffff;
}

.btn {
  height: 4vh;
  width: 4vw;
  color: #136d87;
  border: 1px solid #136d87;
  border-radius: 1vw;
  background-color: transparent;
  text-transform: uppercase;
  font-weight: 500;
  position: absolute;
  top: 0;
  right: 2%;
}
::v-deep .el-checkbox-group{
  //display: flex;
  //justify-content: space-between;
  //flex-wrap: wrap;
  //align-items: stretch;
  width: 100%;
  text-align: left;
  .el-checkbox{
    width: 50%;
    margin-right: 0;
  }
}
</style>





























