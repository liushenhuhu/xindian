<template>
  <div class="main">
    <div class="showbox" id="jump" v-show="show" @contextmenu.prevent>
      <div class="noName" v-show="lead">
        <el-radio-group  v-model="radio">
          <el-radio-button @click.native.prevent="clickitem('P1')" label="P1">P1</el-radio-button>
          <el-radio-button @click.native.prevent="clickitem('P2')" label="P2">P2</el-radio-button>
          <el-radio-button @click.native.prevent="clickitem('P3')" label="P3">P3</el-radio-button>
          <el-radio-button @click.native.prevent="clickitem('R1')" label="R1">R1</el-radio-button>
          <el-radio-button @click.native.prevent="clickitem('R2')" label="R2">R2</el-radio-button>
          <el-radio-button @click.native.prevent="clickitem('R3')" label="R3">R3</el-radio-button>
          <el-radio-button @click.native.prevent="clickitem('T1')" label="T1">T1</el-radio-button>
          <el-radio-button @click.native.prevent="clickitem('T2')" label="T2">T2</el-radio-button>
          <el-radio-button @click.native.prevent="clickitem('T3')" label="T3">T3</el-radio-button>
        </el-radio-group>
        <!--        <el-button :class="{btn3:true,istap:tap,nottap:!tap.P}" @click="labelSelectionP()">P</el-button>-->
        <!--        <el-button :class="{btn3:true,istap:tap,nottap:!tap.Q}" @click="labelSelectionQ()">Q</el-button>-->
        <!--        <el-button :class="{btn3:true,istap:tap,nottap:!tap.R}" @click="labelSelectionR()">R</el-button>-->
        <!--        <el-button :class="{btn3:true,istap:tap,nottap:!tap.S}" @click="labelSelectionS()">S</el-button>-->
        <!--        <el-button :class="{btn3:true,istap:tap,nottap:!tap.T}" @click="labelSelectionT()">T</el-button>-->
        <!--        <el-button :class="{btn3:true,istap:tap,nottap:!tap.noise}" @click="labelSelectionnoise()">选框</el-button>-->
        <!--        <el-button class="btn3" @click="labelSelectionAllNoise()">AllNoise</el-button>-->
        <el-button class="btn3" @click="clearData()">清空</el-button>
        <el-button class="btn3" @click="submitData()">提交</el-button>
        <el-popover
          placement="bottom-start"
          title="提示"
          width="220"
          class="popo"
          trigger="hover">
          <p class="tipck">1. 选择标点类型，在框内单击左键标点</p>
          <p class="tipck">2. 右键单击点可以进行删除</p>
          <p class="tipck">3. 标点完成后点击提交</p>
          <p class="tipck">也可选择清空所有数据</p>
          <div slot="reference" ><i class="el-icon-info icon"></i></div>
        </el-popover>
      </div>
      <div id="chartjump"></div>
      <div id="rightMenu" class="menu" style="display: none;">
        <el-button class="button" @click="del">删除</el-button>
      </div>
      <button class="btn" style="right: 0;top: 0;" @click="Off">关闭</button>
    </div>
    <div class="bottom">
      <div class="bottomLeft">
        <div class="xinDian"><span>日志id：{{logId}}</span> <span>诊断结果：{{diagnosticResult}}</span></div>
        <div class="echarts">
          <div class="container">
            <div class="chart" id="I" @dblclick="showchart('I',data.I)"></div>
          </div>
          <div class="container">
            <div class="chart" id="II" @dblclick="showchart('II',data.II)"></div>
          </div>
          <div class="container">
            <div class="chart" id="III" @dblclick="showchart('III',data.III)"></div>
          </div>
          <div class="container">
            <div class="chart" id="aVR"  @dblclick="showchart('aVR',data.aVR)"></div>
          </div>
          <div class="container">
            <div class="chart" id="aVL" @dblclick="showchart('aVL',data.aVL)"></div>
          </div>
          <div class="container">
            <div class="chart" id="aVF" @dblclick="showchart('aVF',data.aVF)"></div>
          </div>
          <div class="container">
            <div class="chart" id="V1"  @dblclick="showchart('V1',data.V1)"></div>
          </div>
          <div class="container">
            <div class="chart" id="V2"  @dblclick="showchart('V2',data.V2)"></div>
          </div>
          <div class="container">
            <div class="chart" id="V3"  @dblclick="showchart('V3',data.V4)"></div>
          </div>
          <div class="container">
            <div class="chart" id="V4"  @dblclick="showchart('V4',data.V4)"></div>
          </div>
          <div class="container">
            <div class="chart" id="V5"  @dblclick="showchart('V5',data.V5)"></div>
          </div>
          <div class="container">
            <div class="chart" id="V6"  @dblclick="showchart('V6',data.V6)"></div>
          </div>
        </div>
      </div>
      <div class="topLeft">
        <div class="topMiddle">
          <div class="page">
            <el-button class="next"  @click="prev" type="primary" :loading="loading">上一个</el-button>
            <el-button class="next"  @click="next" :loading="loading">下一个</el-button>
          </div>
          <el-button type="success" id="btn1" class="btn1" @click="clearTag()" style="margin-right: 20px">清空</el-button>
          <el-button type="success" id="btn1" class="btn1" @click="submit()">提交</el-button>
        </div>
        <div class="selectTag">
          <div class="tag-title">
            <el-button v-for="(item,index) in options" class="allBtn" @click="selectAll(item,index)">{{item}}</el-button>
          </div>
          <div class="tagItem" style="border-top: 0">
            <el-checkbox-group v-model="tagI" style="width:100%" >
              <el-checkbox v-for="item in options" :label="item"><br/></el-checkbox>
            </el-checkbox-group>
          </div>
          <div class="tagItem">
            <el-checkbox-group v-model="tagII" style="width:100%">
              <el-checkbox v-for="item in options" :label="item"><br/></el-checkbox>
            </el-checkbox-group>
          </div>
          <div class="tagItem">
            <el-checkbox-group v-model="tagIII" style="width:100%">
              <el-checkbox v-for="item in options" :label="item"><br/></el-checkbox>
            </el-checkbox-group>
          </div>
          <div class="tagItem">
            <el-checkbox-group v-model="tagaVR"  style="width:100%">
              <el-checkbox v-for="item in options" :label="item"><br/></el-checkbox>
            </el-checkbox-group>
          </div>
          <div class="tagItem">
            <el-checkbox-group v-model="tagaVL"  style="width:100%">
              <el-checkbox v-for="item in options" :label="item"><br/></el-checkbox>
            </el-checkbox-group>
          </div>
          <div class="tagItem">
            <el-checkbox-group v-model="tagaVF"  style="width:100%">
              <el-checkbox v-for="item in options" :label="item"><br/></el-checkbox>
            </el-checkbox-group>
          </div>
          <div class="tagItem">
            <el-checkbox-group v-model="tagV1"  style="width:100%">
              <el-checkbox v-for="item in options" :label="item"><br/></el-checkbox>
            </el-checkbox-group>
          </div>
          <div class="tagItem">
            <el-checkbox-group v-model="tagV2" style="width:100%">
              <el-checkbox v-for="item in options" :label="item"><br/></el-checkbox>
            </el-checkbox-group>
          </div>
          <div class="tagItem">
            <el-checkbox-group v-model="tagV3"  style="width:100%">
              <el-checkbox v-for="item in options" :label="item"><br/></el-checkbox>
            </el-checkbox-group>
          </div>
          <div class="tagItem">
            <el-checkbox-group v-model="tagV4"  style="width:100%">
              <el-checkbox v-for="item in options" :label="item"><br/></el-checkbox>
            </el-checkbox-group>
          </div>
          <div class="tagItem">
            <el-checkbox-group v-model="tagV5" style="width:100%">
              <el-checkbox v-for="item in options" :label="item"><br/></el-checkbox>
            </el-checkbox-group>
          </div>
          <div class="tagItem">
            <el-checkbox-group v-model="tagV6" style="width:100%">
              <el-checkbox v-for="item in options" :label="item"><br/></el-checkbox>
            </el-checkbox-group>
          </div>
        </div>

      </div>
    </div>
  </div>
</template>

<script>
import * as echarts from "@/views/ECGScreen/detail/echarts.min";
import de from "element-ui/src/locale/lang/de";
import {getStLabel, listStLabel, updateStLabel} from "@/api/stLabel/stLabel";
import $ from "jquery";
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
      data:{},
      chartjump:null,
      xIndex:null,
      delX:{key:null,value:null},//想要删除的点
      area:[],//想要删除的区间
      markArea:[],
      title:'',
      subData:{
        P1:[],P2:[],P3:[],
        R1:[],R2:[],R3:[],
        T1:[],T2:[],T3:[],
      },//提交标注信息
      pointdata:[],//画点的option
      point:[[],{P:[], Q:[], R:[], S:[], T:[]}],
      arr:[],//要删除的点所在的区间

      lead:false,
      logId:'',
      diagnosticResult:"",
      options:[ "T波改变", "ST段改变", "ST-T改变", "U波倒置","Q波", "QT间期延长",],
      optionsStatus:[false,false,false,false,false,false,],
      timex:[],
      show:false,
      seriesdata:[
        {yAxis: -3}, {yAxis: -2.5}, {yAxis: -2}, {yAxis: -1.5}, {yAxis: -1}, {yAxis: -0.5}, {yAxis: 0}, {yAxis: 0.5}, {yAxis: 1}, {yAxis: 1.5}, {yAxis: 2}, {yAxis: 2.5}, {yAxis: 3}],
      seriesdata1:[
        {yAxis: -1}, {yAxis: -0.5}, {yAxis: 0}, {yAxis: 0.5}, {yAxis: 1},],

      isSuspected:false,
      pageNum:1,
      isLabel:0,
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
      stLabelList:[],
      total:0,
    };
  },
  created() {
    this.logId=this.$route.query.logId
    this.isLabel=this.$route.query.isLabel
    this.pageNum=this.$route.query.pageNum
    this.getList()
  },
  mounted() {
    this.getMessage()
    this.chartjump = echarts.init(document.getElementById('chartjump'));
  },
  methods: {
    //获取心电数据
    getMessage() {
      this.loading=false
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
        //赋值已标注
        this.clearTag()
        for (let key in JSON.parse(res.data.labelJson)) {
          this[`${'tag' + key}`]=JSON.parse(res.data.labelJson)[key].split(',')
        }
        for (var k = 0; k <1001; k++) {
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
        this.data.I=res.data.ecgDataJson[0]
        this.data.II=res.data.ecgDataJson[1]
        this.data.III=res.data.ecgDataJson[2]
        this.data.aVL=res.data.ecgDataJson[4]
        this.data.aVR=res.data.ecgDataJson[3]
        this.data.aVF=res.data.ecgDataJson[5]
        this.data.V1=res.data.ecgDataJson[6]
        this.data.V2=res.data.ecgDataJson[7]
        this.data.V3=res.data.ecgDataJson[8]
        this.data.V4=res.data.ecgDataJson[9]
        this.data.V5=res.data.ecgDataJson[10]
        this.data.V6=res.data.ecgDataJson[11]
        for (var i = 0; i < 1000; i+=20) {
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
    async getList() {
      let obj={
        pageNum:this.pageNum,
        isLabel:this.isLabel
      }
      await listStLabel(obj).then(response => {
        this.stLabelList = response.rows;
        this.total = response.total;
        this.stLabelList.forEach((item,index)=>{
          if(this.logId==item.logId){
            this.index=index
          }
        })
        console.log(this.index,this.pageNum)
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
        await this.getList()
        this.index=9
      }
      this.logId=this.stLabelList[this.index].logId
      let isLabel=''
      if(this.anoStatus){
        isLabel=`&isLabel=${this.isLabel}`
      }
      var newUrl = this.$route.path + `?logId=${this.logId}&pageNum=${this.pageNum}`+isLabel
      window.history.replaceState('', '', newUrl)
      this.getMessage()
    },
    async next(){
      this.loading=true
      this.index++
      console.log(this.index,this.stLabelList.length)
      if(this.index>=this.stLabelList.length){
        if((this.pageNum-1)*10+this.stLabelList.length>=this.total){
          this.$message.warning("已经是最后一页！！！")
          this.index--
          this.loading=false
          return
        }
        this.pageNum++
        await this.getList()
        this.index=0
      }
      this.logId=this.stLabelList[this.index].logId
      let isLabel=''
      if(this.anoStatus){
        isLabel=`&isLabel=${this.isLabel}`
      }
      var newUrl = this.$route.path + `?logId=${this.logId}&pageNum=${this.pageNum}`+isLabel
      window.history.replaceState('', '', newUrl)
      this.getMessage()
    },
    selectAll(val,index){
      if(this.tagI.includes(val)&& this.tagII.includes(val)&&
      this.tagIII.includes(val)&& this.tagaVR.includes(val)&&
      this.tagaVL.includes(val)&& this.tagaVF.includes(val)&&
      this.tagV1.includes(val)&& this.tagV2.includes(val)&&
      this.tagV3.includes(val)&& this.tagV4.includes(val)&&
      this.tagV5.includes(val)&& this.tagV6.includes(val)){
        this.optionsStatus[index]=true
      }
      this.optionsStatus[index]=!this.optionsStatus[index]
      if(this.optionsStatus[index]){
        //全选
        this.tagI.push(val)
        this.tagII.push(val)
        this.tagIII.push(val)
        this.tagaVR.push(val)
        this.tagaVL.push(val)
        this.tagaVF.push(val)
        this.tagV1.push(val)
        this.tagV2.push(val)
        this.tagV3.push(val)
        this.tagV4.push(val)
        this.tagV5.push(val)
        this.tagV6.push(val)
      }else {
        //取消全选
        this.tagI=this.tagI.filter(item => item !== val);
        this.tagII=this.tagII.filter(item => item !== val);
        this.tagIII=this.tagIII.filter(item => item !== val);
        this.tagaVR=this.tagaVR.filter(item => item !== val);
        this.tagaVL=this.tagaVL.filter(item => item !== val);
        this.tagaVF=this.tagaVF.filter(item => item !== val);
        this.tagV1=this.tagV1.filter(item => item !== val);
        this.tagV2=this.tagV2.filter(item => item !== val);
        this.tagV3=this.tagV3.filter(item => item !== val);
        this.tagV4=this.tagV4.filter(item => item !== val);
        this.tagV5=this.tagV5.filter(item => item !== val);
        this.tagV6=this.tagV6.filter(item => item !== val);
      }
    },
    clearTag(){
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
      // console.log(this.tagI,this.tagII,this.tagIII,this.tagaVL,this.tagaVR,this.tagaVF,this.tagV1,this.tagV2,this.tagV3,this.tagV4,this.tagV5,this.tagV6)

      var labelJson = {
        I: this.distinct(this.tagI).toString(),
        II: this.distinct(this.tagII).toString(),
        III: this.distinct(this.tagIII).toString(),
        aVL: this.distinct(this.tagaVL).toString(),
        aVR: this.distinct(this.tagaVR).toString(),
        aVF: this.distinct(this.tagaVF).toString(),
        V1: this.distinct(this.tagV1).toString(),
        V2: this.distinct(this.tagV2).toString(),
        V3: this.distinct(this.tagV3).toString(),
        V4: this.distinct(this.tagV4).toString(),
        V5: this.distinct(this.tagV5).toString(),
        V6: this.distinct(this.tagV6).toString()
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
    //去重
    distinct(arr) {
      for (var i = 0, len = arr.length; i < len; i++) {
        for (var i2 = i + 1; i2 < len; i2++) {
          if (arr[i] === arr[i2]) {
            arr.splice(i2, 1); // 删除重复的数据
            i2--; // 删除数据后index需要前移一位
            len = arr.length; // 删除数据后重新获取数组长度
          }
        }
      }
      return arr;
    },
    clickitem(e){
      e === this.radio ? this.radio = '' : this.radio = e
    },
    clearData(){
      this.pointdata=[]
      this.subData={
        P1:[],P2:[],P3:[],
        R1:[],R2:[],R3:[],
        T1:[],T2:[],T3:[],
      }
      this.chartjump.setOption({
        series:{
          markPoint:{
            symbol: "pin",
            symbolSize: 24,
            animation:false,
            data: [],
          }
        }
      });
    },
    submitData(){
      this.query.waveLabel=JSON.stringify(this.subData)
      console.log(this.subData)
      // addLabel(this.query).then(res=>{
      //   this.$modal.msgSuccess("标注提交成功");
      // }).catch(err=>{})
    },
    del(){
      console.log(this.delX.key,this.delX.value)
      this.pointdata.some((item,index)=>{
        if(item.xAxis==this.delX.value){
          this.pointdata.splice(index,1)
          return true
        }
      })
      this.subData[this.delX.key].some((item,index)=>{
        if(item==this.delX.value){
          this.subData[this.delX.key].splice(index,1)
          console.log("删除成功")
          return true
        }
      })
      this.delX={key:null,value:null}
      this.chartjump.setOption({
        series:{
          markPoint:{
            symbol: "pin",
            symbolSize: 24,
            animation:false,
            data: this.pointdata,
          }
        }
      })
      $('#rightMenu').css({
        'display': 'none',
      });

    },
    Off(){
      for (let tapKey in this.tap) {
        this.tap[tapKey]=false
      }
      this.lead=false
      this.show=false
    },
    showchart(title, data) {
      this.title=title
      if(title=='II'){
        this.lead=true
      }
      let y=[]
      for (let i = -2; i < 2.1; i+=0.1) {
        y.push(i)
      }
      var timex=this.timex.concat()
      for (var k = 1001; k <2001; k++) {
        timex.push(k / 100+"秒")
      }
      var seriesdata=this.seriesdata.concat()
      for (var i = 1000; i < 2001; i+=20) {
        seriesdata.push({xAxis: i})
      }
      //console.log(y)
      let detailoption = {
        animation: false,
        backgroundColor: "#ffffff",
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross'
          }
        },
        toolbox:{
          show:false
        },
        dataZoom: [
          {
            show: true,       // 滑动条组件
            type: 'slider',
            brushSelect:false,
            // y: '90%',
            startValue: 0,
            endValue:487,
            // minSpan: 52.5,
            // maxSpan: 52.6,
            minValueSpan:201,
            maxValueSpan:487
          },
          {
            show: true,       // 滑动条组件
            type: 'slider',
            orient: 'vertical',
            brushSelect:false,
            startValue: -3,
            endValue:3,
            minValueSpan:2.13,
            maxValueSpan:6
          },
          {
            type: 'inside',
            orient: 'vertical',// 鼠标滚轮缩放
            start: 0,
            end: 100
          },
          {
            type: 'inside',   // 鼠标滚轮缩放
            start: 0,
            end: 100
          },
        ],
        grid: {
          left: '3%',
          right: '3%',
          top: '5%',
          bottom: '10%'
        },
        legend: {
          show: false,
          data: ['当前电位'],
          textStyle: {color: "#000000"} /*图例(legend)说明文字的颜色*/,
          left: "right",
        },
        xAxis: {
          type:'category',
          boundaryGap: false,
          data: timex,
          axisTick: {
            show: false
          },
          axisLabel: { //修改坐标系字体颜色
            interval: 4,
            show: false,
            textStyle: {
              color: "#000000"
            }
          },
          splitLine: {
            show: true,
            lineStyle: {
              color: 'pink',
              width: 1, //网格的宽度
              type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
            },
          } /*网格线*/
        },
        yAxis: {
          min: -3,
          max: 3,
          //type:'value',
          boundaryGap: false,
          //interval: 0.1,
          splitNumber: 41,
          minInterval:0.1,
          //data:y,
          axisLabel: { //修改坐标系字体颜色
            show: false,
            textStyle: {
              color: "#000000"
            }
          },
          splitLine: {
            show: true,
            lineStyle: {
              color: "pink",
              width: 1, //网格的宽度
              type: 'solid' //网格是实实线，可以修改成虚线以及其他的类型
            },
          } /*网格线*/
        },
        series: {
          id: 'series1',
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
              show: true,
              position: 'start', // 表现内容展示的位置
              color: '#b33939'  // 展示内容颜色
            },
            data: seriesdata,
          },
          itemStyle: {
            normal: {
              lineStyle: {
                width:1.5,
                color: '#000000',/*折线的颜色*/
              },
              color: "#000000" /*图例(legend)的颜色,不是图例说明文字的颜色*/
            }
          },
          symbol: "none",
          name: '当前电位',
          type: 'line',
          data: data,
          smooth: true //显示为平滑的曲线*/
        },
      };
      this.chartjump.clear()
      this.chartjump.setOption(detailoption)
      setTimeout(()=>{
        this.chartjump.resize()
      })
      //console.log(this.subData)
      this.markArea.length=0
      this.pointdata.length=0
      var colorList= {
        P1:'#fe0101',P2:'#fe0101',P3:'#fe0101',
        R1:'#ff00cf',R2:'#ff00cf',R3:'#ff00cf',
        T1:'#0021da',T2:'#0021da',T3:'#0021da',
      }
      //回显
      if(this.lead){
        console.log("有数据")
        console.log(this.subData)
        for (const key in this.subData) {
          for (let j = 0; j < this.subData[key].length; j++) {
            let pointdata={
              name: key,
              xAxis:this.subData[key][j],
              yAxis: data[this.subData[key][j]],
              itemStyle: {
                color:colorList[key]
              },
              label: {
                color: '#ffffff',
                show: true,
                formatter: key,
                fontSize:9
              },
            }
            this.pointdata.push(pointdata)
          }
        }
        //console.log(this.pointdata)
        this.chartjump.setOption({
          series:{
            markPoint:{
              symbol: "pin",
              symbolSize: 24,
              animation:false,
              data: this.pointdata,
            }
          }
        })
      }else {
        this.pointdata=[]
        this.chartjump.setOption({
          series:{
            markPoint:{
              symbol: "pin",
              symbolSize: 24,
              animation:false,
              data: this.pointdata,
            }
          }
        })
      }
      var width=window.screen.width
      var height=window.screen.height
      this.chartjump.off('contextmenu')
      this.chartjump.on('contextmenu',(params)=>{
        console.log(params)
        $('#rightMenu').css({
          'display': 'block',
          'left': params.event.offsetX/width*100 + 'vw',
          'top' : params.event.offsetY/height*100+11 +'vh'
        });
        if(params.componentType==="markPoint"){
          this.delX.value=params.data.xAxis
          this.delX.key=params.data.name
        }
        console.log(this.delX)
      });
      this.chartjump.getZr().off('click')
      this.chartjump.getZr().on('click',params=>{
        $('#rightMenu').css({
          'display': 'none',
        });
        const pointInPixel= [params.offsetX, params.offsetY];
        // console.log(pointInPixel)
        if (this.chartjump.containPixel('grid',pointInPixel)) {
          this.xIndex=this.chartjump.convertFromPixel({seriesIndex:0},[params.offsetX, params.offsetY])[0];
          //this.yIndex=this.chartjump.convertFromPixel({seriesIndex:0},[params.offsetX, params.offsetY])[1];
          console.log(this.xIndex)
          let temp=false
          this.pointdata.forEach(i=>{
            if(this.xIndex==i.xAxis){
              console.log("已存在")
              temp=true
              return
            }
          })
          if(temp){
            return
          }
          if(this.radio==''){
            return
          }
          this.subData[this.radio].push(this.xIndex)
          //console.log(this.subData)
          let pointdata={
            name: this.radio,
            xAxis:this.xIndex,
            yAxis: data[this.xIndex],
            itemStyle: {
              color:colorList[this.radio]
            },
            label: {
              color: '#ffffff',
              show: true,
              formatter: this.radio,
              fontSize:9
            },
          }
          this.pointdata.push(pointdata)
          this.chartjump.setOption({
            series:{
              markPoint:{
                symbol: "pin",
                symbolSize: 24,
                animation:false,
                data: this.pointdata,
              }
            }
          })
        }
      });
      this.show=true
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
  width:35%;
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
  position: relative;
}
.tag-title{
  font-size: 1vw;
  width: 100%;
  position: absolute;
  top: 0;
  display: flex;
  justify-content: space-around;

  //border-bottom: 1px solid #136d87;
}
.allBtn{
  flex: 1;
  margin: 0;
  padding: 5px 0;
  font-size: .9vw;
  background-color: #F1FAFF;
  border: 1px solid #136d87;
  z-index: 999;
  color: #136d87;
}
.tagItem{
  height:10vh;
  border-top: 1px solid #136d87;
  display: flex;
  justify-content: center;
  align-items: center;
}
.tagItem:first-child{
  border-top: 0;
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
  width: 65%;
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
  height: 10vh;
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
  display: flex;
  //justify-content: space-between;
  //flex-wrap: wrap;
  width: 100%;
  height: 100%;
  text-align: left;
  .el-checkbox{
    flex:1;
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;
    text-align: center;
    margin-right: 0;
    border-right: 1px solid #136d87;
  }
  .el-checkbox:last-child{
    border: 0;
  }
  .el-checkbox__inner {
    width: 28px;
    height: 28px;
    border: 1px solid #136d87;
  }
  .el-checkbox__label{
    padding: 0;
  }
  .el-checkbox__inner::after {
    border: 3px solid #fff;
    border-left: 0;
    border-top: 0;
    left: 11px;
    top: 6px;
  }
  .el-checkbox__input.is-checked .el-checkbox__inner::after {
    transform: rotate(50deg) scaleY(1.4);
  }
}
.showbox {
  //display: none;
  user-select:none;
  position: absolute;
  width: 100%;
  height:65%;
  border: 1px solid #6EDDF1;
  top: 35%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 2000;
  background-color: rgb(255, 255, 255);
  span{
    display: inline-block;
  }
}
.noName{
  //display: inline-flex;

  //width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;

}
.btn3 {
  color: #b33939;
  border: 1px solid #b33939;
  border-radius: .5em;
  background-color: transparent;
  text-transform: uppercase;
  font-weight: 500;
  //flex: 1;
  height: 4vh;
  font-size: 1vw;
  text-align: center;
  padding: 0;
  margin: 0.5vw;
  width: 4vw;
}
.popo{
  height: 100%;
}
.icon{
  font-size: 2vw;
}
#chartjump {
  /*position: absolute;*/
  width: 100%;
  height: 100%;
  //top: 50%;
  //left: 50%;
  //transform: translate(-50%, -50%);
  border: 1px solid #000000;
  /*background-color: #06732b;*/
}
.menu{
  /*这个样式不写，右键弹框会一直显示在画布的左下角*/
  position: absolute;
  background: rgba(255, 255, 255);
  left: -99999px;
  top: -999999px;
  padding: 0.1vw;
  border-radius: 0.5vw;
  border: 1px solid #000000;
  .button{
    height: 0.5vh;
    width: 0.5vw;
    display: inline-flex;
    text-align: center;
    border: none;
    font-size: 0.5vw;
    font-weight: 700;
    justify-content: center;
    align-items: center;
  }
  .button:hover{
    color: #FFFFFF;
    background-color: #df0202;
  }
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
</style>





























