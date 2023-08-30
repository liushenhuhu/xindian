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
        <el-popover
          style="margin:auto;"
          placement="bottom-start"
          title="提示"
          width="220"
          trigger="hover">
          <p class="tipck">1. 左上角按钮可以选择类型;</p>
          <p class="tipck">2. 左键按下,可以拖拽心电图;</p>
          <p class="tipck">3. 左键单击点，可以删除点;</p>
          <p class="tipck">4. 右键单击可以描点。</p>
          <el-button slot="reference"><i class="el-icon-info icon"></i></el-button>
        </el-popover>
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

          @contextmenu="handleContextmenu"
          @mousedown.left="handleMousedown"
          @mouseup.left="handleMouseup"
          @mousemove="handleMousemove"
          @click.left="handleClickLeft"
          @click.right="handleClickRight"
        ></canvas>
        <!--    @mousewheel="handleMouseWheel"    -->
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
import {checkByTagId, addXArr} from "@/api/staticECG/staticECG";
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
      imgZoom: 1.3, //图片缩放倍数(默认一倍)
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
      x1: "",
      pId: "",
      level: "",
      xArr: [],
      yArr: [],
      types: [],
      subArr: {
        pId: '',
        xArr: '',
        yArr: '',
        types: '',
        level: ''
      },
      //tagId: '',
      queryParam: {
        pId: '',
        level: ''
      },
    };
  },
  computed: {},
  watch: {

  },
  created() {

  },
  mounted() {

  },
  methods: {
    openDrawShow(file,pIds,level){
      this.pId = pIds;
      this.level = level;
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
      //查询是否标记过数据，若标记，回显
      this.queryParam.pId = pIds;
      this.queryParam.level = level;
      checkByTagId(this.queryParam).then(response => {
        //console.log(this.yArr)
        if(response.length > 0){
          for (let i = 0; i < JSON.parse(response[0].xArr).length; i++) {
            var x = this.computerPlace(JSON.parse(response[0].xArr)[i]);
            let obj = {
             // 'X': (x - this.canvasSize.width*(this.level-1)),
              'X': x,
              'Y': JSON.parse(response[0].yArr)[i] / this.imgZoom
            }
            this.chartsData.push(obj);
            this.currentDrawData = {
              data: [[x*this.imgZoom, JSON.parse(response[0].yArr)[i] *this.imgZoom, JSON.parse(response[0].types)[i]]]
            }
            this.canvasData.push(this.currentDrawData);
          }
          //console.log(this.canvasData)
          this.canvasData.forEach((e3,index) => {
            console.log(e3)
            this.drawPoints(e3.data[0][0],e3.data[0][1],index,e3.data[0][2]);
          })
          //将数据回显
          this.xArr = JSON.parse(response[0].xArr);
          this.yArr = JSON.parse(response[0].yArr);
          this.types = JSON.parse(response[0].types);
        }
      });
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
      await (this.canvasSize = { height: img.height*1.4, width: img.width*1.4 }); //通过图片尺寸设置画布尺寸
      this.imgSize = { height: img.height*1.4, width: img.width*1.4 }; //记录下图片原始尺寸
      ctx = document.getElementById("myCanvas").getContext("2d"); //获取上下文
      await ctx.drawImage(img, 0, 0, img.width*1.4, img.height*1.4); //在canvas中绘制图片(图片、起始位置、绘图尺寸)
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
      this.xArr = [];
      this.yArr = [];
      this.types = [];
      //this.pId = "";
      //this.subArr = [];
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
      this.xArr = [];
      this.yArr = [];
      this.types = [];
      //this.pId = "";
      //this.subArr = [];
      //this.polygonTempList = {}; //清空多边形临时列表
      document.getElementById("myCanvas").getContext("2d").clearRect(0, 0, this.canvasSize.width, this.canvasSize.height); //清除画布图形
    },
    //提交坐标数据
    async clickSubmit(){
      this.isLoading = true;
      if(this.canvasData.length > 0){
        //console.log("提交====="+this.yArr)
        this.subArr.pId = this.pId;
        this.subArr.level = this.level;
        this.subArr.xArr = JSON.stringify(this.xArr);
        this.subArr.yArr = JSON.stringify(this.yArr);
        this.subArr.types = JSON.stringify(this.types);
        //console.log("pId===="+this.pId);
        //console.log("xArr===="+this.xArr);
        //console.log("subArr===="+this.subArr);
        //console.log(this.subArr);
        addXArr(this.subArr).then(response => {
          this.$modal.msgSuccess("坐标提交成功!");
          //this.recoverysize();
          this.isLoading = false;
        })
      }else {
        this.$modal.msgWarning("请标记后提交！");
        this.isLoading = false;
      }
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
    //鼠标右击事件
    handleClickLeft(e1){
      //判断鼠标是否在标记点
      this.canvasData.forEach((e2,index) => {
        if( e1.offsetX >= e2.data[0][0]-3 && e1.offsetX <= e2.data[0][0]+3 && e1.offsetY <= e2.data[0][1]+3 && e1.offsetY >= e2.data[0][1]-3){
          if(window.confirm('确认删除当前标记点吗？')){
            this.canvasData.splice(index,1);
            this.chartsData.splice(index,1);
            this.xArr.splice(index,1);
            this.yArr.splice(index,1);
            this.types.splice(index,1);
            //删除点  重新绘制列表数据
            this.drawToArrs();
            return true;
          }else {
            return false;
          }
        }
      })
    },
    drawPoints(x,y,index,type) {
      //console.log(type);
      //标记类型
      //console.log('test')
      console.log(x,y)
      let str = type;
      ctx.font = "bold 15px Arial";
      ctx.fillText (str, x-3,y-26,[300]);
      //计算两点之间的参数
      if(this.chartsData[index - 1] === undefined){
        //console.log("====undefined")
      }else{
        //取两点之间的中点距离,显示值
        let place = 0;
        for (let i = 0; i < this.chartsData.length; i++) {
          if(i === index){
            place = (this.chartsData[i].X - this.chartsData[i - 1].X) / 2;
          }
        }
        // 显示毫秒值
        let time = this.calculateMS(Math.ceil(x),Math.ceil(this.chartsData[index - 1].X));
        //console.log(time)
        ctx.font = "bold 20px Arial";
        ctx.fillText (parseFloat(time).toFixed(3),x - place,y+20 );
      }
      ctx.beginPath(); //新建路径
      //绘制圆点   arc参数为：x,y，半径、起始角、终止角
      ctx.arc(
        x,
        y,
        3,
        0,
        2 * Math.PI
      );
      ctx.fillStyle = this.currentColor.color; //填充颜色
      ctx.strokeStyle = this.currentColor.color; //线条颜色
      ctx.fill(); //填充
      ctx.stroke(); //绘制线条
    },

    //通过保存的大列表绘制图形
    drawToArrs() {
      if(this.canvasData.length === 0){
        this.canvasData = []; //清空图形数据
        this.currentDrawData = {}; //清空当前绘图数据
        this.chartsData = [];
        this.lableData = [];
      }
      document.getElementById("myCanvas").getContext("2d").clearRect(0, 0, this.canvasSize.width, this.canvasSize.height); //清除画布图形
      document.getElementById("myCanvas").getContext("2d").drawImage(
        this.currentBgImg,
        0,
        0,
        this.canvasSize.width,
        this.canvasSize.height
      );//重绘背景
      //console.log("123:"+this.canvasData.length)
      //console.log("456:"+this.chartsData)
      this.canvasData.forEach((e3,index) => {
        this.drawPoints(e3.data[0][0],e3.data[0][1],index,e3.data[0][2]);
      })
    },
    //鼠标按下事件
    handleMousedown(e) {
      //console.log(e)
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
      if(e.button===2){
        return
      }
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

    //鼠标右键点击事件
    handleClickRight(e){
      let multiple = 0;
      if(this.level === 1){
        multiple = 0;
      }
      if(this.level === 2){
        multiple = 600;
      }
      if(this.level === 3){
        multiple = 1200;
      }
      if(this.level === 4){
        multiple = 1800;
      }
      if(this.level === 5){
        multiple = 2400;
      }
      if(this.level === 6){
        multiple = 3000;
      }
      if(this.level === 7){
        multiple = 3600;
      }
      if(this.level === 8){
        multiple = 4200;
      }
      if(this.level === 9){
        multiple = 4800;
      }

      //点模式的松开
      if (this.currentModel.model == "point") {
        let x = e.offsetX;
        let minArr = [];
        let maxArr = [];
        let minIndexArr = [];
        let maxIndexArr = [];
        for (let i = 0; i < this.chartsData.length; i++) {
          if(this.chartsData[i].X > x){
            minArr.push(this.chartsData[i].X);
            minIndexArr.push(i);
          }
          if(this.chartsData[i].X < x){
            maxArr.push(this.chartsData[i].X);
            maxIndexArr.push(i);
          }
        }

        let minX = 0;
        let maxX = 0;
        let minIndex = 0;
        let maxIndex = 0;
        let obj = {
          'X': e.offsetX,
          'Y': e.offsetY
        };

        if(minArr.length>0 && maxArr.length>0){
          minX = Math.min.apply(Math,minArr);
          maxX = Math.max.apply(Math,maxArr);
          for (let i = 0; i < this.chartsData.length; i++) {
            //console.log("chartsData="+this.chartsData[i]);
            //console.log("minX="+minX);
            if(this.chartsData[i].X === minX){
              //console.log(this.chartsData[i]);
              //console.log(minX)
              minIndex = i;
            }
          }
          //console.log("minArr:"+minArr)
          //console.log("minIndex:"+minIndex)
          //console.log("minX:"+minX)
          //console.log("中间")
          this.chartsData.splice(minIndex,0,obj)
          this.currentDrawData = {
            type: "point",
            data: [[e.offsetX , e.offsetY]],
            lables: this.lableData
          };
          this.canvasData.splice(minIndex,0,this.currentDrawData);
          //点x轴位置
          let indexs = this.computerNum(e.offsetX)+multiple;
          this.xArr.push(indexs);
          this.yArr.push(e.offsetY);
          this.types.push(this.radio1);
          //console.log("qqqq:"+minIndex)
          this.drawToArrs();
          //this.drawPointCenter(e,minIndex);
        }else if(maxArr.length>0){
          //console.log("右")
          //点x轴位置
          let indexs = this.computerNum(e.offsetX)+multiple;
          this.xArr.push(indexs);
          this.yArr.push(e.offsetY);
          this.types.push(this.radio1);
          this.drawPointRight(e); //用鼠标松开时的位置画点
          this.currentDrawData.data.forEach((e) => {
            e[0] = e[0] / this.imgZoom; //将坐标数据还原至真实数据
            e[1] = e[1] / this.imgZoom;
            e[2] = this.radio1;
            e[3] = this.time;
            //e[4] = this.x1
            this.canvasData.push(this.currentDrawData); //添加当前绘图数据至大列表
          });
        } else if(minArr.length>0){
          //console.log("左")
          //点x轴位置
          let indexs = this.computerNum(e.offsetX)+multiple;
          this.xArr.push(indexs);
          this.yArr.push(e.offsetY);
          this.types.push(this.radio1);
          this.drawPointLeft(e); //用鼠标松开时的位置画点
          this.currentDrawData.data.forEach((e) => {
            e[0] = e[0] / this.imgZoom; //将坐标数据还原至真实数据
            e[1] = e[1] / this.imgZoom;
            e[2] = this.radio1;
            e[3] = this.time;
            //e[4] = this.x1
            this.canvasData.splice(0,0,this.currentDrawData); //添加当前绘图数据至大列表
          });
        }else {
          //点x轴位置
          let indexs = this.computerNum(e.offsetX)+multiple;
          this.xArr.push(indexs);
          this.yArr.push(e.offsetY);
          this.types.push(this.radio1);
          //console.log("aaaaaaaaaaaaa======"+indexs);
          this.drawPoint(e); //用鼠标松开时的位置画点
          this.currentDrawData.data.forEach((e) => {
            e[0] = e[0] / this.imgZoom; //将坐标数据还原至真实数据
            e[1] = e[1] / this.imgZoom;
            e[2] = this.radio1;
            e[3] = this.time;
            //e[4] = this.x1
            this.canvasData.push(this.currentDrawData); //添加当前绘图数据至大列表
          });
        }
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
          //console.log(e.data[0][0])
          ctx.beginPath();
          ctx.font = "bold 15px Arial";
          ctx.fillText (e.data[0][2],e.data[0][0] * this.imgZoom-3,e.data[0][1] * this.imgZoom-26);
          ctx.font = "bold 20px Arial";
          ctx.fillText (e.data[0][3],(e.data[0][0] - 180) * this.imgZoom,(e.data[0][1] + 20) * this.imgZoom);

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
    },

    //鼠标松开事件
    handleMouseup(e) {
      if (e.button == 0) {
        this.isDrag && (this.isDrag = false); //左键松开关闭拖拽
      }
      if (e.button !== 2) {
        return; //非右键松开
      }

      this.isMouseDown = false; //关闭鼠标状态
      this.canvasMousePosition = {
        x: e.offsetX, //更新位置
        y: e.offsetY,
      };
    },
    //计算点 Index
    computerNum(x){
      let a = x/(this.canvasSize.width/599);
      //let b = a*5;
      //console.log(b)
      return a;
    },
    //计算位置
    computerPlace(x){
      let a = (x%600)*(this.canvasSize.width/599);
      //let b = a/5;
      return a;
    },
    //画点
    drawPoint(e) {
      //获取画布中鼠标坐标
      this.canvasMousePosition = {
        x: e.offsetX,
        y: e.offsetY,
      };

      //计算点位置
      /*let pointIndex = this.computerNum(e.offsetX);
      console.log("pointIndex===="+Math.round(pointIndex));*/
      //标记类型
      let str = this.radio1;
      ctx.font = "bold 15px Arial";
      ctx.fillText (str, this.canvasMousePosition.x-3,this.canvasMousePosition.y-26,[300]);
      //计算两点之间的参数
      if(this.chartsData[this.chartsData.length - 1] === undefined){
        let obj = {
          'X': e.offsetX,
          'Y': e.offsetY
        };
        this.chartsData.push(obj);
      }else{
        let obj = {
          'X': e.offsetX,
          'Y': e.offsetY
        };
        this.chartsData.push(obj);

        //取两点之间的中点距离,显示值
        let place = 0;
        for (let i = 0; i < this.chartsData.length; i++) {
          //console.log("删除前"+this.chartsData[i])
          if(i === this.chartsData.length-1){
            place = (this.chartsData[i].X - this.chartsData[i - 1].X) / 2;
          }
        }

        // 显示毫秒值
        this.time = this.calculateMS(Math.ceil(e.offsetX),Math.ceil(this.chartsData[this.chartsData.length - 2].X));
        let time = this.calculateMS(Math.ceil(e.offsetX),Math.ceil(this.chartsData[this.chartsData.length - 2].X));
        ctx.font = "bold 20px Arial";
        ctx.fillText (parseFloat(time).toFixed(3),this.canvasMousePosition.x - place,this.canvasMousePosition.y+20 );
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
    //画点
    drawPointRight(e) {
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
      if(this.chartsData[this.chartsData.length - 1] === undefined){
        let obj = {
          'X': e.offsetX,
          'Y': e.offsetY
        };
        this.chartsData.push(obj);
      }else{
        let obj = {
          'X': e.offsetX,
          'Y': e.offsetY
        };
        this.chartsData.push(obj);

        //取两点之间的中点距离,显示值
        let place = (this.chartsData[this.chartsData.length-1].X - this.chartsData[this.chartsData.length-2].X) / 2;
        /*for (let i = 0; i < this.chartsData.length; i++) {
          //console.log("删除前"+this.chartsData[i])
          if(i === this.chartsData.length-1){
            place = (this.chartsData[i].X - this.chartsData[i - 1].X) / 2;
          }
        }*/

        // 显示毫秒值
        this.time = this.calculateMS(Math.ceil(e.offsetX),Math.ceil(this.chartsData[this.chartsData.length - 2].X));
        let time = this.calculateMS(Math.ceil(e.offsetX),Math.ceil(this.chartsData[this.chartsData.length - 2].X));
        ctx.font = "bold 20px Arial";
        ctx.fillText (parseFloat(time).toFixed(3),e.offsetX - place,this.canvasMousePosition.y+20 );
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
    //画点
    drawPointLeft(e) {
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
      if(this.chartsData[this.chartsData.length - 1] === undefined){
        let obj = {
          'X': e.offsetX,
          'Y': e.offsetY
        };
        this.chartsData.splice(0,0,obj);
      }else{
        let obj = {
          'X': e.offsetX,
          'Y': e.offsetY
        };
        this.chartsData.splice(0,0,obj);
        //取两点之间的中点距离,显示值
        let place = (this.chartsData[1].X - e.offsetX) / 2;
        //console.log(place)
        /*for (let i = 0; i < this.chartsData.length; i++) {
          //console.log("删除前"+this.chartsData[i])
          if(i === this.chartsData.length-1){
            place = (this.chartsData[i].X - this.chartsData[i - 1].X) / 2;
          }
        }*/

        // 显示毫秒值
        this.time = this.calculateMS(Math.ceil(e.offsetX),Math.ceil(this.chartsData[1].X));
        let time = this.calculateMS(Math.ceil(this.chartsData[1].X),Math.ceil(e.offsetX));
        ctx.font = "bold 20px Arial";
        ctx.fillText (parseFloat(time).toFixed(3),this.chartsData[1].X - place,e.offsetY+20 );
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
    //画点
    drawPointCenter(e,index) {
      this.canvasMousePosition = {
        x: e.offsetX,
        y: e.offsetY,
      };
      //标记类型
      let str = this.radio1;
      ctx.font = "bold 15px Arial";
      ctx.fillText (str, this.canvasMousePosition.x-3,this.canvasMousePosition.y-26,[300]);
      //计算两点之间的参数
      /*let obj = {
        'X': e.offsetX,
        'Y': e.offsetY
      };
      this.chartsData.splice(0,0,obj);*/
      //console.log(index)
      //  右
      let place1 = (this.chartsData[index+1].X - e.offsetX) / 2;
      // 显示毫秒值
      let time1 = this.calculateMS(Math.ceil(this.chartsData[index+1].X),Math.ceil(e.offsetX));
      ctx.font = "bold 20px Arial";
      ctx.fillText (parseFloat(time1).toFixed(3),this.chartsData[index+1].X - place1,e.offsetY+20 );
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

      //计算两点之间的参数
      /*let obj = {
        'X': e.offsetX,
        'Y': e.offsetY
      };
      this.chartsData.splice(0,0,obj);*/
      //  左
      let place2 = (e.offsetX - this.chartsData[index-1].X) / 2;
      // 显示毫秒值
      let time2 = this.calculateMS(Math.ceil(this.chartsData[index-1].X),Math.ceil(e.offsetX));
      ctx.font = "bold 20px Arial";
      ctx.fillText (parseFloat(time2).toFixed(3),e.offsetX - place2,e.offsetY+20 );
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
      /*this.currentDrawData = {
        type: "point",
        //color: this.currentColor.color,
        data: [[e.offsetX , e.offsetY]],
        lables: this.lableData
      };*/
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
  width: 85%;
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
