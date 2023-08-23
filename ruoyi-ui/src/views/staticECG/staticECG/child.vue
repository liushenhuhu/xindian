<!--图片标记-->
<template>
  <div
    class="container"
    v-loading="isLoading"
    element-loading-text="数据加载中..."
    element-loading-background="rgba(0, 0, 0, 0.8)"
    v-show="drawShow"
  >
    <!--顶部工具栏-->
    <div class="top-tool">
      <!--左上角盒子-->
      <div class="top-left-div">
        <div hidden
          v-for="item in colorList"
          :key="item.color"
          :class="[
            currentColor.color == item.color ? 'color-div-a' : 'color-div',
          ]"
          :style="{ backgroundColor: item.color }"
          @click="changeColor(item)"
        ></div>
        <!--拾色器按钮-->
        <div class="getcolor-div" hidden title="拾色器">
          <img :src="colorImg" />
          <input id="colorInp" type="color" class="color-inp" />
        </div>

        <el-radio-group style="margin:auto;" v-model="radio1">
          <el-radio-button label="N">正常</el-radio-button>
          <el-radio-button label="S">房早</el-radio-button>
          <el-radio-button label="V">室早</el-radio-button>
          <el-radio-button label="X">干扰</el-radio-button>
        </el-radio-group>
      </div>
      <!--右上角盒子-->
      <div class="top-right-div">
        <el-tooltip
          v-for="item in modelList"
          :key="item.model"
          class="item"
          effect="dark"
          :content="item.name + '：' + item.msg"
          placement="bottom"
        >
          <el-button
            :class="[
              currentModel.model == item.model ? 'model-btn-a' : 'model-btn',
            ]"
            type="primary"
            @click="changeModel(item)"
          ><img :src="item.img"
          /></el-button>
        </el-tooltip>

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
      <div class="canvas-div">
        <!--画布-->
        <canvas
          id="myCanvas"
          :height="canvasSize.height"
          :width="canvasSize.width"
          :style="{
            cursor: isDrag ? 'grab' : 'default',
            top: canvasPosition.y + 'px',
            left: canvasPosition.x + 'px',
          }"
          @mousewheel="handleMouseWheel"
          @contextmenu="handleContextmenu"
          @mousedown="handleMousedown"
          @mouseup="handleMouseup"
          @mousemove="handleMousemove"

        ></canvas>
      </div>
    </div>

    <!--信息盒子-->
    <div
      v-if="false"
      v-show="isMsg"
      class="msg-div"
      :style="{
        top: mousePosition.y - 40 + 'px',
        left: mousePosition.x - 120 + 'px',
      }"
    >
      x:{{ canvasMousePosition.x }},y:{{ canvasMousePosition.y }}
    </div>
  </div>
</template>
<script>
import markImg from "@/assets/images/lableBackImg.jpg"; //初始背景
let ctx = ""; //画布上下文
export default {
  props: {},
  components: {},
  data() {
    return {
      drawShow:false,
      canvasData: [], //画布中图形数据
      isLoading: false, //加载状态
      colorImg: require("@/assets/images/color.png"), //拾色器图标
      imgSize: { height: "", width: "" }, //图片原始尺寸
      imgZoom: 1, //图片缩放倍数(默认一倍)
      canvasSize: { height: "", width: "" }, //画布尺寸
      canvasPosition: { x: "", y: "" }, //画布位置
      mousePosition: { x: "", y: "" }, //屏幕中鼠标位置
      canvasMouseStart: { x: "", y: "" }, //画布中鼠标开始点击位置
      canvasMousePosition: { x: "", y: "" }, //画布中鼠标位置
      isMouseDown: false, //鼠标按下状态
      isDrag: false, //拖拽状态
      dragStart: { x: "", y: "" }, //开始拖拽的位置
      isMsg: false, //信息盒子状态
      radio1: 'N',
      //颜色列表
      colorList: [
        { color: "#000000" },
      ],
      currentColor: {}, //当前选中颜色
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
      currentModel: {}, //当前模式
      currentDrawData: {}, //当前绘图数据
      base64: "", //截图数据
      polygonTempList: [], //多边形临时列表
      currentBgImg: "", //当前加载的背景图
      count: 0,  //点击排序
      file: "",
      chartsData: [],
      typesData: [],
      lableData: [],    //标记数据
      time: "",
      x1: ""
    };
  },
  computed: {},
  watch: {},
  created() {

  },
  mounted() {

  },
  methods: {
    openDrawShow(file){
      this.loadBgImg(file).then((img) => {
        this.currentBgImg = img; //存下加载完成后的背景图
        this.initCanvas(img); //图片加载完后初始化画布
        this.clearCanvas(); //清空画布
        this.recoverysize(); //复原
      });
      //加载base64
      let img = new Image();
      img.src = this.file;
      let myCanvas = document.getElementById('myCanvas').getContext('2d');
      img.onload = () => {
        myCanvas.drawImage(img, 0, 0);
      };
      this.drawShow=true;
    },
    //必须异步加载图片
    loadBgImg(markImg) {
      let img = new Image(); //创建img标签
      img.src = markImg; //添加src
      return new Promise((resolve, reject) => {
        img.onload = () => {
          resolve(img); //返回标签
        };
        img.onerror = (err) => {
          reject(err);
        };
      });
    },

    //初始化画布
    async initCanvas(img) {
      await (this.canvasSize = { height: img.height, width: img.width }); //通过图片尺寸设置画布尺寸
      this.imgSize = { height: img.height, width: img.width }; //记录下图片原始尺寸
      ctx = document.getElementById("myCanvas").getContext("2d"); //获取上下文
      await ctx.drawImage(img, 0, 0, img.width, img.height); //在canvas中绘制图片(图片、起始位置、绘图尺寸)
      let canvasDiv = document.getElementsByClassName("canvas-div")[0];
      this.canvasPosition = {
        x: canvasDiv.offsetWidth / 2 - img.width / 2,
        y: canvasDiv.offsetHeight / 2 - img.height / 2 - 25,
      }; //背景居中
      //监听拾色器值的改变
      document
        .getElementById("colorInp")
        .addEventListener("input", this.getColorVal, false);
      this.currentModel = this.modelList[0]; //默认绘图模式
      this.currentColor = this.colorList[0]; //默认选中第一种颜色
    },

    //切换画笔颜色
    changeColor(data) {
      this.currentColor = data;
    },

    //获取拾色器颜色值
    getColorVal(e) {
      this.currentColor = { color: e.target.value }; //改变当前颜色
    },

    //切换画笔模式
    changeModel(data) {
      /*if (this.currentModel.model == "polygon") {

        this.drawToArr();
      }*/
      this.currentModel = data;
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
    //关闭窗口
    clickClose(){
      this.drawShow = false;
      this.currentBgImg = "";
      this.closeCanvas();
    },
    //清空画布
    clearCanvas() {
      this.canvasData = []; //清空图形数据
      this.currentDrawData = {}; //清空当前绘图数据
      this.chartsData = [];
      this.lableData = [];
      //this.polygonTempList = {}; //清空多边形临时列表
      document.getElementById("myCanvas").getContext("2d").clearRect(0, 0, this.canvasSize.width, this.canvasSize.height); //清除画布图形
      document.getElementById("myCanvas").getContext("2d").drawImage(
        this.currentBgImg,
        0,
        0,
        this.canvasSize.width,
        this.canvasSize.height
      );//重绘背景
    },
    closeCanvas() {
      this.canvasData = []; //清空图形数据
      this.currentDrawData = {}; //清空当前绘图数据
      this.chartsData = [];
      this.lableData = [];
      //this.polygonTempList = {}; //清空多边形临时列表
      document.getElementById("myCanvas").getContext("2d").clearRect(0, 0, this.canvasSize.width, this.canvasSize.height); //清除画布图形
    },
    //提交坐标数据
    async clickSubmit(){
      // this.isLoading = true;
      // if(this.canvasData && this.canvasData.length > 0){
      //   var datas = this.canvasData;
      //   datas.forEach(item => {
      //     delete item.type;
      //   })
      //   //console.log(datas);
      //   addLable(datas).then(response => {
      //     this.$modal.msgSuccess("坐标提交成功!");
      //     this.recoverysize();
      //     this.isLoading = false;
      //   })
      // }else {
      //   this.$modal.msgWarning("请标记后提交！");
      //   this.isLoading = false;
      // }
      /*let ctxx = document.getElementById('myCanvas')
      var MIME_TYPE = "image/png";
      var imgURL = ctxx.toDataURL(MIME_TYPE);

      var dlLink = document.createElement('a');
      dlLink.download = 'testesteste';
      dlLink.href = imgURL;
      dlLink.dataset.downloadurl = [MIME_TYPE, dlLink.download, dlLink.href].join(':');
      document.body.appendChild(dlLink);
      dlLink.click();
      document.body.removeChild(dlLink);*/
    },

    //复原
    async recoverysize() {
      await (this.imgZoom = 1); //复原图片缩放倍数
      await (this.canvasSize = this.imgSize); //复原尺寸
      let canvasDiv = document.getElementsByClassName("canvas-div")[0];
      this.canvasPosition = {};
      await (this.canvasPosition = {
        x: canvasDiv.offsetWidth / 2 - this.imgSize.width / 2,
        y: canvasDiv.offsetHeight / 2 - this.imgSize.height / 2 - 25,
        /*x: canvasDiv.offsetWidth / 2 - this.img.width / 2,
        y: canvasDiv.offsetHeight / 2 - this.img.height / 2 - 25,*/

      }); //复原位置
      await ctx.drawImage(
        this.currentBgImg,
        0,
        0,
        this.imgSize.width,
        this.imgSize.height
      ); //重绘背景
      await this.drawToArr(); //重绘标记
      await (this.base64 = document
        .getElementById("myCanvas")
        .toDataURL("image/png")); //获取canvas的base64
    },


    //阻止默认右键冒泡事件，去除右键菜单
    handleContextmenu(e) {
      e.preventDefault();
    },

    //鼠标滚动事件(wheelDelta值上滚为负下滚为正)
    async handleMouseWheel(e) {
      let el = document.getElementById("myCanvas");
      let oldX = el.offsetLeft; //旧位置
      let oldY = el.offsetTop;
      await this.changeCanvas(e, oldX, oldY); //改变画布
      //使用改变后的此村绘制图片
      await ctx.drawImage(
        this.currentBgImg,
        0,
        0,
        this.canvasSize.width,
        this.canvasSize.height
      );
      await this.drawToArr(); //重绘列表数据
    },

    //滚动时改变画布
    changeCanvas(e, oldX, oldY) {
      let zoomSpeed = 1.2; //缩放速度
      e.wheelDelta < 0 && (zoomSpeed = 2 - zoomSpeed); //判断放大与缩小
      let posX = e.offsetX; //获取鼠标定点的位置（鼠标在图片上的位置）
      let posY = e.offsetY;
      let oldImgZoom = this.imgZoom; //记录下旧的图片缩放倍数
      this.imgZoom = this.imgZoom * zoomSpeed; //更新缩放倍数
      let minZoom = 0.5; //最小缩放倍数
      let maxZoom = 2; //最大缩放倍数
      this.imgZoom > maxZoom && (this.imgZoom = maxZoom); //限制缩放倍数
      this.imgZoom < minZoom && (this.imgZoom = minZoom);
      zoomSpeed = this.imgZoom / oldImgZoom; //更新缩放速度
      let height = Math.round(this.imgSize.height * this.imgZoom); //计算画布新宽高(原始宽高乘缩放倍数)
      let width = Math.round(this.imgSize.width * this.imgZoom);
      let newX = oldX + Math.round(posX * (1 - zoomSpeed)); //计算画布新位置(旧位置加偏移量)
      let newY = oldY + Math.round(posY * (1 - zoomSpeed));
      this.canvasSize = { height: height, width: width }; //更新画布尺寸
      this.canvasPosition = { x: newX, y: newY }; //更新画布位置
    },

    //鼠标按下事件
    handleMousedown(e) {
      if (e.button == 0) {
        this.isDrag = true; //左键按下打开拖拽
        let el = document.getElementById("myCanvas");
        this.dragStart = {
          x: el.offsetLeft - e.screenX,
          y: el.offsetTop - e.screenY,
        }; //记录下开始拖拽的偏移量
        return;
      }
      if (e.button !== 2) {
        return; //右键绘图
      }
      this.isMouseDown = true; //打开鼠标状态
      //获取画布中鼠标开始点击位置
      this.canvasMouseStart = {
        x: e.offsetX,
        y: e.offsetY,
      };
    },

    //鼠标移动事件
     handleMousemove(e) {
      if (this.isDrag) {
        this.mouseDrag(e); //处理拖拽
        return;
      }
      this.isMsg = true;
      //获取屏幕中鼠标位置(显示实时坐标)
      this.mousePosition = {
        x: e.pageX,
        y: e.pageY,
      };
      //获取画布中鼠标位置
      this.canvasMousePosition = {
        x: e.offsetX,
        y: e.offsetY,
      };
      //判断是否超出边界
      if (e.offsetX < 0 || e.offsetY < 0 || e.offsetX == 0 || e.offsetY == 0) {
        this.isMsg = false;
        this.isMouseDown = false; //关闭鼠标状态
      }
      //判断鼠标状态是否打开
      if (!this.isMouseDown) {
        return;
      }
      ctx.clearRect(0, 0, this.canvasSize.width, this.canvasSize.height); //清除画布后立刻重新绘制视觉上形成动画
      ctx.drawImage(
        this.currentBgImg,
        0,
        0,
        this.canvasSize.width,
        this.canvasSize.height
      ); //在canvas中绘制图片
       this.drawToArr(); //重绘列表数据
      //绘制点
      if (this.currentModel.model == "point") {
        return;
      }
    },

    //鼠标拖拽
    mouseDrag(e) {
      this.canvasPosition.x = e.screenX + this.dragStart.x; //计算出画布位置
      this.canvasPosition.y = e.screenY + this.dragStart.y;
    },

    //通过保存的大列表绘制图形
    async drawToArr() {
      //遍历大列表开始绘制
      await this.canvasData.forEach((e) => {
        //绘制点
        if (e.type == "point") {
          //console.log(e.lables[0].time)
          ctx.beginPath();
          ctx.font = "bold 15px Arial";
          ctx.fillText (e.data[0][2],e.data[0][0] * this.imgZoom-3,e.data[0][1] * this.imgZoom-26);
          ctx.font = "bold 20px Arial";
          ctx.fillText (e.data[0][3],(e.data[0][0] - 120) * this.imgZoom,(e.data[0][1] + 20) * this.imgZoom);
          /*e.lables.forEach((e) => {
            console.log("e.X:======"+e.X)
            ctx.fillText (e.time,e.X * this.imgZoom,e.Y * this.imgZoom);
          })*/

          ctx.arc(
            e.data[0][0] * this.imgZoom,
            e.data[0][1] * this.imgZoom,
            3,
            0,
            2 * Math.PI
          );
          ctx.fillStyle = e.color;
          ctx.strokeStyle = e.color;
          ctx.fill();
          ctx.stroke();
        }
      });
      // 遍历标记点，开始绘制
      /*await this.lableData.forEach((e) => {
        //console.log("e:========"+e.X)
        let d = (e.X * this.imgZoom - this.chartsData[this.chartsData.length - 2].X * this.imgZoom)/2;
        ctx.fillText (e.time,(e.X - d) * this.imgZoom,e.Y * this.imgZoom);
        //let d = (this.canvasMousePosition.x - this.chartsData[this.chartsData.length - 2].X)*this.imgZoom/2;
        //ctx.fillText (e.time,this.canvasMousePosition.x - d,this.canvasMousePosition.y );
      })*/
    },

    //鼠标松开事件
    handleMouseup(e) {
      if (e.button == 0) {
        this.isDrag && (this.isDrag = false); //左键松开关闭拖拽
      }
      if (e.button !== 2) {
        return; //非右键松开
      }
      //点模式的松开
      if (this.currentModel.model == "point") {
        this.drawPoint(e); //用鼠标松开时的位置画点
        this.currentDrawData.data.forEach((e) => {
          e[0] = e[0] / this.imgZoom; //将坐标数据还原至真实数据
          e[1] = e[1] / this.imgZoom;
          e[2] = this.radio1;
          e[3] = this.time;
          //e[4] = this.x1
        });
        this.canvasData.push(this.currentDrawData); //添加当前绘图数据至大列表
        //console.log(this.canvasData)
      }
      this.isMouseDown = false; //关闭鼠标状态
      this.canvasMousePosition = {
        x: e.offsetX, //更新位置
        y: e.offsetY,
      };
    },

    //画点
    drawPoint(e) {
      //获取画布中鼠标坐标
      this.canvasMousePosition = {
        x: e.offsetX,
        y: e.offsetY,
      };

      //标记类型
      let str = this.radio1;
      ctx.font = "bold 15px Arial";
      ctx.fillText (str, this.canvasMousePosition.x-3,this.canvasMousePosition.y-26,[300]);
      //计算两点之间的参数
      //console.log(this.chartsData[this.chartsData.length - 1])
      if(this.chartsData[this.chartsData.length - 1] === undefined){
          let obj = {
            'X': e.offsetX,
            'Y': e.offsetY
          };
          this.chartsData.push(obj);
      }else{
        //console.log("length:"+this.chartsData[this.chartsData.length - 1].X)
        let obj = {
          'X': e.offsetX,
          'Y': e.offsetY
        };
        this.chartsData.push(obj);
        //ctx.lineTo(e.offsetX, e.offsetY);  //起点坐标
        //ctx.lineTo(this.chartsData[this.chartsData.length - 1].X, this.chartsData[this.chartsData.length - 1].Y);  //下一个坐标
        //ctx.stroke();
        // 显示毫秒值
        this.time = this.calculateMS(Math.ceil(e.offsetX),Math.ceil(this.chartsData[this.chartsData.length - 2].X));
        let time = this.calculateMS(Math.ceil(e.offsetX),Math.ceil(this.chartsData[this.chartsData.length - 2].X));
        //let d = (e.offsetX * this.imgZoom - this.chartsData[this.chartsData.length - 2].X)/2;
        //this.x1 = (e.offsetX - d);
        /*let obj1 = {
          "X": e.offsetX - d,
          "Y": e.offsetY,
          "time": time
        }*/
        //this.lableData.push(obj1);
        ctx.font = "bold 20px Arial";
        ctx.fillText (parseFloat(time).toFixed(3),this.canvasMousePosition.x - 120,this.canvasMousePosition.y+20 );
      }
      ctx.beginPath(); //新建路径
      //绘制圆点   arc参数为：x,y，半径、起始角、终止角
      ctx.arc(
        this.canvasMousePosition.x,
        this.canvasMousePosition.y,
        3,
        0,
        2 * Math.PI
      );
      ctx.fillStyle = this.currentColor.color; //填充颜色
      ctx.strokeStyle = this.currentColor.color; //线条颜色
      ctx.fill(); //填充
      ctx.stroke(); //绘制线条
      //生成点绘图数据
      this.currentDrawData = {
        type: "point",
        //color: this.currentColor.color,
        data: [[e.offsetX , e.offsetY]],
        lables: this.lableData
      };
    },
    calculateMS (x1,x2){
      //大屏  一个小方格之间距离为18    一个小方格表示0.04s
      let a = x1 - x2;
      let b = a/18;
      let c = b * 0.04;
      return c.toFixed(3);
    }
  },

};
</script>
<style scoped lang="scss">
.container {
  user-select: none;
  height: 80%;
  width: 90%;
  position: fixed;
  top:50%;
  left:50%;
  transform: translate(-45%,-50%);
  background: #ffffff;
  z-index:1000;
  .top-tool {
    height: 50px;
    display: flex;
    justify-content: space-between;
    background-color: rgba(39, 55, 70, 1);
    .top-left-div {
      display: flex;
      .color-div-a,
      .color-div {
        border-radius: 50%;
        height: 40px;
        width: 40px;
        margin: 5px 10px;
        &:hover {
          cursor: pointer;
          box-shadow: 0px 0px 10px #fff;
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
        padding: auto;
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
    .canvas-div {
      width: 100%;
      height: 100%;
      overflow: hidden;
      position: absolute;
      #myCanvas {
        background-color: rgb(255, 255, 255);
        position: absolute;
      }
    }
  }
  .msg-div {
    position: absolute;
    z-index: 10;
    background-color: rgba(255, 255, 255, 0.5);
    color: #ffffff;
    padding: 5px 20px;
  }


}
</style>
