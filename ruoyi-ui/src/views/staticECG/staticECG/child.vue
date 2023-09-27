<!--图片标记-->
<template>
  <div
    class="container"
    v-loading="isLoading"
    element-loading-text="数据加载中..."
    element-loading-background="rgba(0, 0, 0, 0.8)"
    v-show="drawShow" @contextmenu.prevent
  >
    <!--顶部工具栏-->
    <div class="top-tool">
      <!--左上角盒子-->
      <div class="top-left-div">
        <el-radio-group style="margin:auto;" v-model="radio1">
          <el-radio-button label="N">正常</el-radio-button>
          <el-radio-button label="S">房早</el-radio-button>
          <el-radio-button label="V">室早</el-radio-button>
          <el-radio-button label="X">干扰</el-radio-button>
        </el-radio-group>
        <el-popover
          style="margin:auto;"
          placement="bottom-start"
          title="提示"
          width="220"
          trigger="hover">
          <p class="tipck">1. 左上角按钮可以选择类型</p>
<!--          <p class="tipck">2. 左键按下,可以拖拽心电图;</p>-->
          <p class="tipck">2. 左键单击，进行标点</p>
          <p class="tipck">3. 右键单击，进行删除</p>
          <p class="tipck">4. 也可点击右上角清空所有点</p>
          <p class="tipck">5. 标点完成后，点击右上角提交</p>
          <el-button slot="reference"><i class="el-icon-info icon"></i></el-button>
        </el-popover>
      </div>
      <!--右上角盒子-->
      <div class="top-right-div">
        <!--清空画布-->
        <el-tooltip
          class="item"
          effect="dark"
          content="清空标记"
          placement="bottom"
        >
          <el-button type="primary" class="clear-btn" @click="clickClear">
            <i class="el-icon-delete"></i>
          </el-button>
        </el-tooltip>

        <!-- 提交数据 -->
        <el-tooltip
          class="item"
          effect="dark"
          content="提交标记信息"
          placement="bottom"
        >
          <el-button type="primary" class="download-btn" @click="clickSubmit">
            <i class="el-icon-finished"></i>
          </el-button>
        </el-tooltip>
        <!--关闭标注窗口-->
        <el-tooltip
          class="item"
          effect="dark"
          content="关闭窗口"
          placement="bottom"
        >
          <el-button type="primary" class="clear-btn" @click="clickClose">
            <i class="el-icon-close"></i>
          </el-button>
        </el-tooltip>
      </div>
    </div>

    <!--画布盒子-->
    <div class="middle-div">
      <div class="canvas-div" id="chart"></div>
      <div id="rightMenu" class="menu" style="display: none;">
        <el-button class="button" @click="del">删除</el-button>
      </div>
    </div>


  </div>
</template>
<script>
import markImg from "@/assets/images/lableBackImg.jpg"; //初始背景
import {checkByTagId, addXArr} from "@/api/staticECG/staticECG";
import * as echarts from "@/views/ECGScreen/detail/echarts.min";
import $ from "jquery";
import {enableTopologicalTravel} from "echarts/lib/util/component";
let ctx = ""; //画布上下文
export default {
  props: {},
  components: {},
  data() {
    return {
      drawShow:false,
      isLoading: false, //加载状态
      //模式列表
      modelList: [
        {
          model: "point",
          name: "点模式",
          img: require("@/assets/images/point.png"),
          msg: "右键点击绘制",
        }/*,
        {
          model: "polygon",
          name: "多边形模式",
          img: require("@/assets/images/polygon.png"),
          msg: "右键点击绘制，左键双击自动闭合",
        }*/
      ],
      radio1:'N',
      colorList: [
        { color: "#000000" },
      ],
      arrList1:[],
      arrList2:[],
      arrList3:[],
      arrList4:[],
      arrList5:[],
      arrList6:[],
      arrList7:[],
      arrList:{
        pId: null,
        data:null},
      pId:null,
      level:null,
      chart:null,
      delX:{key:null,value:null},//想要删除的点
      seriesdata:[{xAxis: 0},
        {xAxis: 25},
        {xAxis: 50},
        {xAxis: 75},
        {xAxis: 100},
        {xAxis: 125},
        {xAxis: 150},
        {xAxis: 175},
        {xAxis: 200},
        {xAxis: 225},
        {xAxis: 250},
        {xAxis: 275},
        {xAxis: 300},
        {xAxis: 325},
        {xAxis: 350},
        {xAxis: 375},
        {xAxis: 400},
        {xAxis: 425},
        {xAxis: 450},
        {xAxis: 475},
        {xAxis: 500},
        {xAxis: 525},
        {xAxis: 550},
        {xAxis: 575},
        {xAxis: 600},
        {xAxis: 625},
        {xAxis: 650},
        {xAxis: 675},
        {xAxis: 700},
        {xAxis: 725},
        {xAxis: 750},
        {xAxis: 775},
        {xAxis: 800},
        {xAxis: 825},
        {xAxis: 850},
        {xAxis: 875},
        {xAxis: 900},
        {xAxis: 925},
        {xAxis: 950},
        {xAxis: 975},
        {xAxis: 1000},
        {yAxis: -2},{yAxis: -1.5},{yAxis: -1}, {yAxis: -0.5}, {yAxis: 0}, {yAxis: 0.5}, {yAxis: 1},{yAxis: 1.5},{yAxis: 2},

      ],//标线
      x:[],//x轴值
      pointdata:[],//点
      graphic:[],//文本
      data:[],//导联数据
      queryParam:{
        pId: '',
        level: '',
      }
    };
  },
  // computed: {
  //   //排序
  //   arrList(){
  //     return {
  //       pId: this.pId,
  //       data:JSON.stringify([...this.arrList1,...this.arrList2,...this.arrList3,...this.arrList4,...this.arrList5,...this.arrList6,...this.arrList7])
  //     }
  //   }
  // },
  created() {
  },
  mounted() {
    this.chart = echarts.init(document.getElementById('chart'));
  },
  methods: {

    async getchart(data,pIds,level) {
      console.log("第几个",level)
      this.data=data
      this.drawShow=true
      this.pId = pIds;
      this.level = level;
      this.x.length=0
      this.pointdata.length=0
      this.arrList.pId=pIds
      this.queryParam.pId = pIds;
      this.queryParam.level = level;
      for (var i = 0; i <= 600; i++) {
        this.x.push(i);
      }
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
            type: 'inside',   // 鼠标滚轮缩放
            start: 0,
            end: 100
          },
          {
            show: true,       // 滑动条组件
            type: 'slider',
            y: '90%',
            start: 0,
            end: 100
          }
        ],
        grid: {
          left: '3%',
          right: '3%',
          top: '2%',
          bottom: '13%'
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
          data: this.x,
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
          min: -2,
          max: 2,
          boundaryGap: true,
          interval: 0.1,
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
            data: this.seriesdata,
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
          smooth: 0 //显示为平滑的曲线*/
        },
      };
      this.chart.clear()
      this.chart.setOption(detailoption,true)
      checkByTagId(this.queryParam).then(res=>{
        if(res.length>0){
          this.arrList.data=res[0].data
          //回显
          console.log(JSON.parse(this.arrList.data))
          if(this[`${'arrList' + this.level}`].length==0){
            this[`${'arrList' + this.level}`]=JSON.parse(this.arrList.data).filter(i=>{
              let a=i.x-600*(level-1)
              return a>=0 && a<600
            })
          }

          console.log(this[`${'arrList' + this.level}`])
          //添加所有点
          this.pointdata.length=0
          var length=this[`${'arrList' + this.level}`].length
          for (let i = 0; i < length; i++) {
            var colorList= {N:'#fe0101',S:'#ff7000',V:'#ff00cf',X:'#0021da',}
            let pointdata={
              name: this[`${'arrList' + this.level}`][i].type,
              xAxis:this[`${'arrList' + this.level}`][i].x-600*(level-1),
              yAxis: this[`${'arrList' + this.level}`][i].y,
              itemStyle: {
                color:colorList[this[`${'arrList' + this.level}`][i].type]
              },
              label: {
                color: '#ffffff',
                show: true,
                formatter: this[`${'arrList' + this.level}`][i].type,
                fontSize:13
              },
            }
            this.pointdata.push(pointdata)
          }
          console.log(this.pointdata)
          //添加文本
          this.addtext()
          //重绘
          this.redraw()
        }
      })
      setTimeout(()=>{
        this.chart.resize();
      })
      $(window).resize(()=>{
        this.chart.resize();
      });
      var width=window.screen.width
      var height=window.screen.height
      this.chart.off('contextmenu')
      this.chart.on('contextmenu',(params)=>{
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
      this.chart.getZr().off('click')
      this.chart.getZr().on('click',params=>{
          $('#rightMenu').css({
            'display': 'none',
          });
          const pointInPixel= [params.offsetX, params.offsetY];
          //console.log(pointInPixel)
          if (this.chart.containPixel('grid',pointInPixel)) {
            this.xIndex = this.chart.convertFromPixel({seriesIndex: 0}, [params.offsetX, params.offsetY])[0];
            this.yIndex = this.chart.convertFromPixel({seriesIndex: 0}, [params.offsetX, params.offsetY])[1];
            /*事件处理代码书写位置*/
            // console.log(this.xIndex)
            // console.log(this.radio1)
            // console.log(this.data[this.xIndex])
            this.addValue({x: this.xIndex,y: this.data[this.xIndex], type: this.radio1})
            //添加点
            this.addpoint()
            //添加文本
            this.addtext()
            setTimeout(()=>{
              //重绘
              this.redraw()
            });
          }
      })
    },
    //添加标点
    addpoint(){
      let i=this.pointdata.findIndex(it=>it.x==this.xIndex)
      if(i!=-1){
        return
      }
      var colorList= {N:'#fe0101',S:'#ff7000',V:'#ff00cf',X:'#0021da',}
      let pointdata={
        name: this.radio1,
        xAxis:this.xIndex,
        yAxis: this.data[this.xIndex],
        itemStyle: {
          color:colorList[this.radio1]
        },
        label: {
          color: '#ffffff',
          show: true,
          formatter: this.radio1,
          fontSize:13
        },
      }
      this.pointdata.push(pointdata)
    },
    //重绘所有点之间的文本
    addtext(){
      this.graphic.length=0
      var length=this[`${'arrList' + this.level}`].length
      //console.log(length)
      for (let i = 0; i < length-1; i++) {
        var x1=this[`${'arrList' + this.level}`][i].x-600*(this.level-1)
        var x2=this[`${'arrList' + this.level}`][i+1].x-600*(this.level-1)
        //console.log(x1,x2)
        var time=((x2-x1)/25*0.2).toFixed(2); //时间 s
        var heart=(60/time).toFixed(1) //心率
        var x=this.chart.convertToPixel({seriesIndex: 0}, [(x2-x1)/2+x1, 0.75])
        //console.log(this.chart.convertToPixel({seriesIndex: 0}, [(x2-x1)/2+x1, 0.5]))
        let text={
          type:'text',
          x: x[0]-15,
          y:x[1],
          z: 999,
          style:{
            text: time+`\n(${heart})`,
            fill: '#000000',
            fontWeight: 400,
            fontSize: 15
          }
        }
        this.graphic.push(text)
      }
      //console.log(this.graphic)
    },
    //按x从小到大插入值
    addValue(params) {
      params.x=params.x+600*(this.level-1)
      let i=this[`${'arrList' + this.level}`].findIndex(it=>it.x==params.x)
      if(i!=-1){
        return
      }
      let idx =  this[`${'arrList' + this.level}`].findIndex(it=>it.x>params.x)
      this[`${'arrList' + this.level}`].splice(idx===-1?this[`${'arrList' + this.level}`].length:idx,0,params)
    },
    //点击清空
    clickClear() {
      this.$confirm("确定清空当前标记数据", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.clearCanvas(); //清空画布
          this.$message({
            type: "success",
            message: "清空成功!",
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "取消清空",
          });
        });
    },
    clearCanvas(){
      this.pointdata.length=0
      this[`${'arrList' + this.level}`].length=0
      this.graphic.length=0
      setTimeout(()=>{
        this.redraw()
      });
    },
    //关闭窗口
    clickClose(){
      this.drawShow = false;
      this.currentBgImg = "";
    },
    //重绘
    redraw(){
      var chartOption = this.chart.getOption();
      chartOption.graphic = [];

      this.chart.setOption(chartOption, true);
        this.chart.setOption({
          series:{
            markPoint:{
              symbol: "pin",
              symbolSize: 25,
              animation:false,
              data: this.pointdata,
            }
          },
          graphic:this.graphic
        })
      //console.log(this.graphic)
    },
    del(){
      var length1=this.pointdata.length
      //删除点data
      for (let i = 0; i < length1; i++) {
        if(this.pointdata[i].xAxis===this.delX.value){
          this.pointdata.splice(i,1)
          break
        }
      }

      var length2=this[`${'arrList' + this.level}`].length
      for (let i = 0; i < length2; i++) {
        let x=this[`${'arrList' + this.level}`][i].x-600*(this.level-1)
        if(x===this.delX.value){
          this[`${'arrList' + this.level}`].splice(i,1)
          break
        }
      }
      this.addtext()
      setTimeout(()=>{
        this.redraw()
      });
      $('#rightMenu').css({
        'display': 'none',
      });
    },
    //提交坐标数据
    clickSubmit(){
      this.arrList={
        pId: this.pId,
        data:JSON.stringify([...this.arrList1,...this.arrList2,...this.arrList3,...this.arrList4,...this.arrList5,...this.arrList6,...this.arrList7])
      }
      //console.log(this.arrList)
      this.isLoading = true;
      if(this.arrList.data.length > 0){
        addXArr(this.arrList).then(response => {
          this.$modal.msgSuccess("坐标提交成功!");
          //this.recoverysize();
          this.isLoading = false;
        })
        console.log(JSON.parse(this.arrList.data))
        // this.isLoading = false;
      }else {
        this.$modal.msgWarning("请标记后提交！");
        this.isLoading = false;
      }
    },

  },

};
</script>
<style scoped lang="scss">
.container {
  user-select: none;
  height: 50%;
  width: 100%;
  position: absolute;
  top:50%;
  left:50%;
  transform: translate(-50%,-80%);
  background: #ffffff;
  z-index:1;
  .top-tool {
    height: 50px;
    display: flex;
    justify-content: space-between;
    background-color: rgba(39, 55, 70, 1);
    .top-left-div {
      display: flex;
      .icon{
        font-size: 30px;
        color: white;
      }
      .el-button{
        padding: 0;
        border: 0;
        background-color: rgba(207,218,238,0);
      }
      .color-div-a,
      .color-div {
        border-radius: 50%;
        height: 40px;
        width: 40px;
        margin: 5px 10px;
        &:hover {
          cursor: pointer;
          box-shadow: 0px 0px 10px lightgray;
        }
      }
      .color-div-a {
        box-shadow: 0px 0px 10px #fff;
      }
      .getcolor-div {
        position: relative;
        margin: 5px 10px;
        img {
          position: absolute;
          top: 0;
          height: 40px;
          width: 40px;
          border-radius: 50%;
        }
        input {
          z-index: 10;
          border-radius: 50%;
          position: absolute;
          top: 0;
          background-color: transparent;
          height: 40px;
          width: 40px;
          opacity: 0;
          border: 0px;
          cursor: pointer;
        }
      }
    }
    .top-right-div {
      margin: 0px;
      display: flex;
      .model-btn,
      .model-btn-a {
        background-color: #455769;
        border: 0px;
        padding: 9px 15px;
        margin: auto 10px;
        img {
          height: 20px;
          width: 20px;
        }
      }
      .model-btn-a {
        border: 0px;
        background-color: rgba(255, 255, 255, 0.5);
      }
      .download-btn,
      .clear-btn {
        margin: auto 10px;
      }
      .upload-btn {
        margin: auto 10px;
        height: 40px;
        width: 50px;
        padding: 0px;
        position: relative;
        input {
          position: absolute;
          left: 0;
          top: 0;
          z-index: 10;
          width: 100%;
          height: 100%;
          opacity: 0;
          border: 0px;
        }
      }
    }
  }
  .middle-div {
    width: 100%;
    height: calc(100% - 50px);
    display: flex;
    justify-content: center;
    align-items: center;
    .canvas-div {
      width: 100%;
      height: 100%;

      overflow: hidden;
    }
  }
  .msg-div {
    position: absolute;
    z-index: 10;
    background-color: rgba(255, 255, 255, 0.5);
    color: #ffffff;
    padding: 5px 20px;
  }
  .menu{
    /*这个样式不写，右键弹框会一直显示在画布的左下角*/
    position: absolute;
    background: rgba(255, 255, 255);
    border-radius: 5px;
    left: -99999px;
    top: -999999px;
    padding: 0.1vw;
    border-radius: 0.5vw;
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
  }

}
</style>
