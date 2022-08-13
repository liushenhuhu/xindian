<!--<template>-->
<!--  <div class="wrap"  style="background: #8c939d">-->
<!--    <div id="pdfDom" style="padding: 10px;width: 800px ;background: #00afff">-->
<!--      <div class="view-content">-->
<!--        <div id="myChart" :style="{width: '300px', height: '300px'}"></div>-->
<!--      </div>-->
<!--      <h2>郑州大学测试医院</h2>-->
<!--      <el-table-->
<!--        :data="tableData"-->
<!--        border>-->
<!--        <el-table-column prop="date" label="日期" width="250"></el-table-column>-->
<!--        <el-table-column prop="name" label="姓名" width="250"></el-table-column>-->
<!--        <el-table-column prop="address" label="地址"></el-table-column>-->
<!--      </el-table>-->
<!--      <el-image :src="logo"></el-image>-->
<!--    </div>-->
<!--    <button type="button" style="margin-top: 20px;" @click="btnClick">导出PDF</button>-->
<!--  </div>-->
<!--</template>-->
<template>
  <div id="app">
    <h1 style="margin-top:5vh;">通许第一医院</h1>
    <h1>动态心电图报告</h1>
    <div class="main">
      <div class="box1">
        <div class="box1-top">
          <h2>病人信息</h2>
        </div>
        <div class="box1-bottom clearfix">
          <div class="box1-bottom1">
            <div class="box1-1">姓名:</div>
            <div class="box1-1">性别:</div>
            <div class="box1-1">年龄:</div>
            <div class="box1-1">单号:</div>
          </div>
          <div class="box1-bottom2">
            <div class="box1-1">门诊号:</div>
            <div class="box1-1">住院号:</div>
            <div class="box1-1">科室:</div>
            <div class="box1-1">床号:</div>
          </div>
          <div class="box1-bottom3">
            <div class="box1-1">记录日期:</div>
            <div class="box1-1">记录时长:</div>
          </div>
        </div>
      </div>
      <div class="box2 clearfix">
        <div class="box2-left">
          <div class="box2-top">
            <h2>心率</h2>
          </div>
          <div class="box2-left-bottom">
            <div class="box2-1">总心搏数:</div>
            <div class="box2-1">最慢心率:</div>
            <div class="box2-1">最快心率:</div>
            <div class="box2-1">平均心率:</div>
          </div>
        </div>
        <div class="box2-right">
          <div class="box2-top">
            <h2>心动过缓/心动过速</h2>
          </div>
          <div class="box2-right-bottom">
            <div class="box2-1">心动过缓总持续时间:</div>
            <div class="box2-1">心动过速总持续时间:</div>
            <div class="box2-1">最长心动过缓心搏数:</div>
            <div class="box2-1">最长心动过速心搏数:</div>
          </div>
        </div>
      </div>
      <div class="box3">
        <span>停搏(>2.0秒)</span>&emsp;&emsp;总计&emsp;&emsp;  次，最长一次：&emsp;&emsp; 秒，发生于：
      </div>
      <div class="box4 clearfix">
        <div class="box4-left">
          <div class="box4-top">
            <h2>室性异位心率</h2>
          </div>
          <div class="box4-left-bottom">
            <div class="box4-1">总数:&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;次 &emsp;&emsp;室性百分比:</div>
            <div class="box4-1">单发:&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;次 &emsp;&emsp;平均/小时:</div>
            <div class="box4-1">成对:&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;次 &emsp;&emsp;平均/1000:</div>
            <div class="box4-1">短阵性室速:&emsp;&emsp;&emsp;阵</div>
            <div class="box4-1">RONT:&emsp;&emsp;&emsp;&emsp;&emsp;次</div>
            <div class="box4-1">二联律:&emsp;&emsp;&emsp;&emsp;&emsp;阵</div>
            <div class="box4-1">三联律:&emsp;&emsp;&emsp;&emsp;&emsp;阵</div>
            <div class="box4-1">室性逸搏:&emsp;&emsp;&emsp;&emsp;次</div>
            <div class="box4-1">最快室速:&emsp;&emsp;&emsp;&emsp;bpm,&emsp;发生于</div>
            <div class="box4-1">最长室速:&emsp;&emsp;&emsp;&emsp;次心搏,发生于</div>
          </div>
        </div>
        <div class="box4-right">
          <div class="box4-top">
            <h2>室上性异位心率</h2>
          </div>
          <div class="box4-right-bottom">
            <div class="box4-1">总数:&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;次 &emsp;&emsp;室性百分比:</div>
            <div class="box4-1">单发:&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;次 &emsp;&emsp;平均/小时:</div>
            <div class="box4-1">成对:&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;次 &emsp;&emsp;平均/1000:</div>
            <div class="box4-1">短阵性室上速:&emsp;&emsp;阵</div>
            <div class="box4-1">二联律:&emsp;&emsp;&emsp;&emsp;&emsp;阵</div>
            <div class="box4-1">三联律:&emsp;&emsp;&emsp;&emsp;&emsp;阵</div>
            <div class="box4-1">最快室上速:&emsp;&emsp;&emsp;bpm,发生于</div>
            <div class="box4-1">最长室上速:&emsp;&emsp;&emsp;次心搏,发生于</div>
          </div>
        </div>
      </div>
      <div class="box5">
        <h2>心率变异性分析</h2>
        <span>SDNN: ms&emsp;&emsp;SDANN: ms&emsp;&emsp; SDNN Index:&emsp;&emsp; rMSSD: ms&emsp;&emsp; PNN50:&emsp;&emsp;</span>
      </div>
      <div class="box6">
        <div class="box6-top clearfix">
          <h2>房颤分析</h2>
          <span style="margin-left: 10vw;">总数: &emsp;&emsp;次</span>
        </div>
        <div class="box6-1">发生阵数:&emsp;&emsp;阵 &emsp;&emsp;&emsp;大于 1500ms 的心搏有  &emsp;次</div>
        <div class="box6-1">持续时间:&emsp;&emsp;&emsp; &emsp;&emsp;&emsp;大于 2000ms 的心搏有  &emsp;次</div>
      </div>
      <div class="box7">
        <div class="box7-top">
          <h2>起搏分析</h2>
          <span>总数: &emsp;&emsp;次</span>
          <span>百分比: &emsp;&emsp;&emsp;%</span>
        </div>
        <div class="box7-1">心房起搏数:&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; 百分比: %&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;双腔起搏:&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; 百分比:%</div>
        <div class="box7-1">心室起搏数:&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; 百分比: %&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;错误起搏:&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; 百分比:%</div>
      </div>
      <div class="box8 clearfix">
        <h2>结论:</h2>
        <div class="box8-1">一丶总心搏xxxxx次，平均心率xxbpm，最快心率xxxpm（），最慢心率xxxbpm（）；</div>
        <div class="box8-1">HRV:SD>50ms</div>
        <div class="box8-1">二丶诊断</div>
        <div class="box8-1">1.xxxxxxxxxxx</div>
        <div class="box8-1">2.xxxxxxxxxxx</div>
        <div class="box8-1">3.xxxxxxxxxxx</div>
        <div class="box8-1">4.xxxxxxxxxxx</div>
        <div class="box8-1">5.xxxxxxxxxxx</div>
        <div class="bottom">报告者: <span class="box8-2"></span>日期: <span class="box8-2"></span></div>
      </div>
    </div>
    <div class="main">
      <div class="box9 clearfix">
        <div class="box9-top">
          <h2>概要数据统计表</h2>
        </div>
        <div class="box9-bottom">
          <div class="box9-1">
            <div class="box9-2">姓名:</div>
            <div class="box9-2">性别:</div>
          </div>
          <div class="box9-1">
            <div class="box9-2">年龄:</div>
            <div class="box9-2">单号:</div>
          </div>
          <div class="box9-1">
            <div class="box9-2">住院号:</div>
            <div class="box9-2">科室:</div>
          </div>
        </div>
      </div>
      <div class="box10">
        图形数据
      </div>
      <div class="box11 clearfix">
        <div class="box11-1">
          <span>时间</span>
        </div>
        <div class="box11-2">
          <span>心搏数</span>
        </div>
        <div class="box11-3">
          <span>心率</span>
          <div class="box11-3-1">
            <span>最高</span>
            <span>平均</span>
            <span>最低</span>
          </div>
        </div>
        <div class="box11-4">
          <span>室性异位</span>
          <div class="box11-4-1">
            <span>总数</span>
            <span>%</span>
            <span>单发</span>
            <span>成对</span>
            <span>室速</span>
            <span>逸搏</span>
          </div>
        </div>
        <div class="box11-5">
          <span>室上性异位</span>
          <div class="box11-5-1">
            <span>总数</span>
            <span>%</span>
            <span>单发</span>
            <span>成对室上速</span>
          </div>
        </div>
        <div class="box11-6">
          <span>停搏</span>
        </div>
      </div>
      <div class="box12">具体数据</div>
    </div>
    <button type="button" style="margin-top: 20px;" @click="btnClick">导出PDF</button>-->
  </div>
</template>
<script>
// 导出页面为PDF格式
import html2Canvas from 'html2canvas'
import JsPDF from 'jspdf'
import logoImg from '@/assets/logo/logo.png'

export default {
  name: "ExportPDF",
  data() {
    return {
      logo: logoImg,
      exportPDFtitle: "页面导出PDF文件名",
      tableData: [
        {
          date: '2016-05-06',
          name: '王小虎',
          address: '重庆市九龙坡区火炬大道'
        }, {
          date: '2016-05-07',
          name: '王小虎',
          address: '重庆市九龙坡区火炬大道'
        },{
          date: '2016-05-03',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-02',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-04',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-01',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-08',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-06',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-06',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        },
      ]
    };
  },
  mounted() {
    this.drawLine()
  },
  methods: {

    //echarts测试
    drawLine() {
      // 基于准备好的dom，初始化echarts实例
      let myChart = this.$echarts.init(document.getElementById('myChart'))
      // 绘制图表配置
      let option = {
        tooltip: {},
        xAxis: {
          data: ["衬衫", "羊毛衫", "雪纺衫", "裤子", "高跟鞋", "袜子"]
        },
        yAxis: {},
        series: [{
          name: '销量',
          type: 'bar',
          data: [5, 20, 36, 10, 10, 20]
        }]
      };
      // 窗口大小自适应方案
      myChart.setOption(option);
      setTimeout(function () {
        window.onresize = function () {
          myChart.resize();
        }
      }, 200)
    },

    btnClick(){
      // 当下载pdf时，若不在页面顶部会造成PDF样式不对,所以先回到页面顶部再下载
      let top = document.getElementById('pdfDom');
      if (top != null) {
        top.scrollIntoView();
        top = null;
      }
      let title = this.exportPDFtitle;
      html2Canvas(document.querySelector('#pdfDom'), {
        allowTaint: true
      }).then(function (canvas) {
        // 获取canvas画布的宽高
        let contentWidth = canvas.width;
        let contentHeight = canvas.height;
        // 一页pdf显示html页面生成的canvas高度;
        let pageHeight = contentWidth / 592.28 * 841.89;
        // 未生成pdf的html页面高度
        let leftHeight = contentHeight;
        // 页面偏移
        let position = 0;
        // html页面生成的canvas在pdf中图片的宽高（本例为：横向a4纸[841.89,592.28]，纵向需调换尺寸）
        let imgWidth = 592.28;
        let imgHeight = 592.28/contentWidth * contentHeight;
        let pageData = canvas.toDataURL('image/jpeg', 1.0);
        let PDF = new JsPDF('', 'pt', 'a4');
        // 两个高度需要区分: 一个是html页面的实际高度，和生成pdf的页面高度
        // 当内容未超过pdf一页显示的范围，无需分页
        if (leftHeight < pageHeight) {
          PDF.addImage(pageData, 'JPEG', 0, 0, imgWidth, imgHeight)
        } else {
          while (leftHeight > 0) {
            PDF.addImage(pageData, 'JPEG', 0, position, imgWidth, imgHeight)
            leftHeight -= pageHeight;
            position -= 841.89;
            // 避免添加空白页
            if (leftHeight > 0) {
              PDF.addPage();
            }
          }
        }
        PDF.save(title + '.pdf')
      })
    },
  },
};

</script>

<style>
*{
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
.clearfix::after {
  content: "";
  display: block;
  height: 0;
  clear: both;
  visibility: hidden;
}
.clearfix {
  /* IE6、7 专有 */
  *zoom: 1;
}

.main{
  margin-bottom: 10vh;
}
h1 {
  text-align: center;
  margin-bottom: 5px;
}
h2 {
  text-align: center;
}



.box1 {
  width: 90%;
  margin: 30px auto 0;
  border: 3px solid black;
  font-size:20px;
}
.box1-top {
  background-color: rgb(240, 240, 240);
  border-bottom: 3px solid black;
}
.box1-bottom {
  display: flex;
  justify-content: space-between;
}
.box1-bottom1 {
  float: left;
  padding-left: 3vw;
}
.box1-bottom2 {
  float: left;
  margin: 0;
}
.box1-bottom3 {
  float: right;
}



.box2 {
  width: 90%;
  margin: 0 auto;
  border: 3px solid black;
  font-size:20px;
}
.box2-left {
  float: left;
  width: 50%;
}
.box2-right {
  float: right;
  width: 50%;
  border-left: 3px solid black;
}
.box2-top h2{
  text-align: left;
  padding-left: 2vw;
  background-color: rgb(240, 240, 240);
  border-bottom: 3px solid black;
}
.box2-left-bottom,
.box2-right-bottom {
  padding-left: 2vw;
}

.box3 {
  width: 90%;
  margin: 0 auto;
  padding: 5px 0;
  border: 3px solid black;
  font-size:20px;
}
.box3 span {
  font-weight: 700;
}

.box4{
  width: 90%;
  margin: 0 auto;
  border: 3px solid black;
  font-size:20px;
}
.box4-left {
  float: left;
  width: 50%;
  border-right: 3px solid black;
}
.box4-right {
  float: right;
  width: 50%;
  /* border-left: 3px solid black; */
}
.box4-top h2{
  text-align: left;
  padding-left: 2vw;
  background-color: rgb(240, 240, 240);
  border-bottom: 3px solid black;
}
.box4-left-bottom,
.box4-right-bottom {
  padding-left: 2vw;
}


.box5 {
  width: 90%;
  margin: 0 auto;
  padding: 5px 5px;
  border: 3px solid black;
  font-size:20px;
}
.box5 h2 {
  text-align: left;
}


.box6{
  width: 90%;
  margin: 0 auto;
  padding: 5px 5px;
  border: 3px solid black;
  font-size:20px;
}
.box6-top {
  height: 30%;
  display: flex;
  align-items: center;
}
.box6-top h2,
.box6-top span {
  float: left;
}


.box7{
  width: 90%;
  margin: 0 auto;
  padding: 5px 5px;
  border: 3px solid black;
  font-size:20px;
}
.box7-top {
  height: 30%;
  display: flex;
  align-items: center;
}
.box7-top span {
  margin-left: 10vw;
}
.box7-top h2,
.box7-top span {
  float: left;
}



.box8{
  width: 90%;
  margin: 0 auto;
  padding: 5px 15px;
  border: 3px solid black;
  font-size:20px;
}
.box8 h2 {
  text-align: left;
}
.box8-1 {
  margin: 5px 0;
}
.box8 .bottom {
  margin-top: 5vw;
  margin-bottom: 1vw;
  text-align: right;
}
.box8-2{
  display: inline-block;
  width: 10vw;
  height: 3vh;
  border-bottom: 3px solid black;
  margin-right: 3vw;
}


.box9 {
  width: 90%;
  margin: 0 auto;
  padding: 5px 15px;
  border: 3px solid black;
  font-size:20px;

}
.box9-bottom {
  display: flex;
  justify-content: space-between;
  padding: 0 5vw;
}

.box10 {
  width: 90%;
  margin: 0 auto;
  padding: 5px 15px;
  border: 3px solid black;
  font-size:20px;
}

.box11 {
  width: 90%;
  margin: 0 auto;
  padding: 5px 15px;
  border: 3px solid black;
  font-size:20px;
  display: flex;
  justify-content: space-between;
}
.box11-1,
.box11-2,
.box11-3,
.box11-4,
.box11-5,
.box11-6 {
  float: left;
  text-align: center;
}
.box11-3 span,
.box11-4 span,
.box11-5 span {
  margin-left: 2vw;
}
.box12 {
  width: 90%;
  margin: 0 auto;
  padding: 5px 15px;
  border: 3px solid black;
  font-size:20px;
}
</style>
