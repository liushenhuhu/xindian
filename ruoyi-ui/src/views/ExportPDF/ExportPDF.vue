<template>
  <div class="wrap"  style="background: #8c939d">
    <div id="pdfDom" style="padding: 10px;width: 800px ;background: #fff">
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
                <div class="box1-1">姓名:<strong>{{froms.patientInfo.name}}</strong></div>
                <div class="box1-1">性别:<strong>{{froms.patientInfo.sex}}</strong></div>
                <div class="box1-1">年龄:<strong>{{froms.patientInfo.age}}</strong></div>
                <div class="box1-1">单号:<strong>{{froms.patientInfo.no}}</strong></div>
              </div>
              <div class="box1-bottom2">
                <div class="box1-1">门诊号:<strong>{{froms.patientInfo.clinicNumber}}</strong></div>
                <div class="box1-1">住院号:<strong>{{froms.patientInfo.hospitalNumber}}</strong></div>
                <div class="box1-1">科室:<strong>{{froms.patientInfo.department}}</strong></div>
                <div class="box1-1">床号:<strong>{{froms.patientInfo.bedNo}}</strong></div>
              </div>
              <div class="box1-bottom3">
                <div class="box1-1">记录日期:<strong>{{froms.patientInfo.recordDate}}</strong></div>
                <div class="box1-1">记录时长:<strong>{{froms.patientInfo.recordDuration}}</strong></div>
              </div>
            </div>
          </div>
          <div class="box2 clearfix">
            <div class="box2-left">
              <div class="box2-top">
                <h2>心率</h2>
              </div>
              <div class="box2-left-bottom">
                <div class="box2-1">总心搏数:<strong>{{froms.heartRate.totalHeartRate}}次</strong></div>
                <div class="box2-1">最慢心率:<strong>{{froms.heartRate.slowestHeartRate}}</strong>   bpm,发生于<strong>{{froms.heartRate.slowestTime}}</strong></div>
                <div class="box2-1">最快心率:<strong>{{froms.heartRate.fastestHeartRate}}</strong>   bpm,发生于<strong>{{froms.heartRate.fastestTime}}</strong></div>
                <div class="box2-1">平均心率:<strong>{{froms.heartRate.averageHeartRate}}</strong>   bpm</div>
              </div>
            </div>
            <div class="box2-right">
              <div class="box2-top">
                <h2>心动过缓/心动过速</h2>
              </div>
              <div class="box2-right-bottom">
                <div class="box2-1">心动过缓总持续时间:<strong>{{froms.heartbeat.bradycardiaTotalDuration}}</strong></div>
                <div class="box2-1">心动过速总持续时间:<strong>{{froms.heartbeat.tachycardiaTotalDuration}}</strong></div>
                <div class="box2-1">最长心动过缓心搏数:<strong>{{froms.heartbeat.longestBradycardia}}</strong></div>
                <div class="box2-1">最长心动过速心搏数:<strong>{{froms.heartbeat.longestTachycardia}}</strong></div>
              </div>
            </div>
          </div>
          <div class="box3">
            <span>停搏(>2.0秒)</span>&emsp;&emsp;总计&nbsp<strong>{{froms.asystole.total}}</strong>;&nbsp;次，最长一次：&nbsp;&nbsp;<strong>{{froms.asystole.longest}}</strong>&nbsp;&nbsp;秒，发生于：&nbsp;<strong>{{froms.asystole.longestTime}}&nbsp;</strong>
          </div>
          <div class="box4 clearfix">
            <div class="box4-left">
              <div class="box4-top">
                <h2>室性异位心率</h2>
              </div>
              <div class="box4-left-bottom">
                <div class="flex-box">
                  <div class="box4-1 flex-box-child">总数: <strong>{{froms.ventricularEctopicHeartRate.total}}</strong> 次</div>
                  <div class="box4-1 flex-box-child">室性百分比: <strong>{{froms.ventricularEctopicHeartRate.ventricularPercentage}}</strong></div>
                </div>
                <div class="flex-box">
                  <div class="box4-1 flex-box-child">单发: <strong>{{froms.ventricularEctopicHeartRate.singleShot}}</strong> 次</div>
                  <div class="box4-1 flex-box-child">平均/小时: <strong>预留</strong></div>
                </div>
                <div class="flex-box">
                  <div class="box4-1 flex-box-child">成对: <strong>{{froms.ventricularEctopicHeartRate.pair}}</strong> 次</div>
                  <div class="box4-1 flex-box-child">平均/1000: <strong>预留</strong></div>
                </div>

                <div class="box4-1">短阵性室速: <strong>{{froms.ventricularEctopicHeartRate.burstVT}}</strong> 阵</div>
                <div class="box4-1">RONT: <strong>{{froms.ventricularEctopicHeartRate.RONT}}</strong> 次</div>
                <div class="box4-1">二联律: <strong>{{froms.ventricularEctopicHeartRate.doubletLaw}}</strong> 阵</div>
                <div class="box4-1">三联律: <strong>{{froms.ventricularEctopicHeartRate.tripleLaw}}</strong> 阵</div>
                <div class="box4-1">室性逸搏: <strong>{{froms.ventricularEctopicHeartRate.ventricularEscape}}</strong> 次</div>
                <div class="box4-1">最快室速: <strong>{{froms.ventricularEctopicHeartRate.fastestVT}}</strong> bpm,&emsp;发生于</div>
                <div class="box4-1">最长室速: <strong>{{froms.ventricularEctopicHeartRate.longestVT}}</strong> 次心搏,发生于</div>
              </div>
            </div>
            <div class="box4-right">
              <div class="box4-top">
                <h2>室上性异位心率</h2>
              </div>
              <div class="box4-right-bottom">
                <div class="flex-box">
                  <div class="box4-1 flex-box-child">总数: <strong>{{froms.supraventricularEctopicHeartRate.total}}</strong> 次</div>
                  <div class="box4-1 flex-box-child">室上性百分比: <strong>{{froms.supraventricularEctopicHeartRate.supraventricularPercentage}}</strong></div>
                </div>
                <div class="flex-box">
                  <div class="box4-1 flex-box-child">单发: <strong>{{froms.supraventricularEctopicHeartRate.singleShot}}</strong> 次</div>
                  <div class="box4-1 flex-box-child">平均/小时: <strong>预留</strong></div>
                </div>
                <div class="flex-box">
                  <div class="box4-1 flex-box-child">成对: <strong>{{froms.supraventricularEctopicHeartRate.pair}}</strong> 次</div>
                  <div class="box4-1 flex-box-child">平均/1000: <strong>预留</strong></div>
                </div>
                <div class="box4-1">短阵性室上速: <strong>{{froms.supraventricularEctopicHeartRate.paroxysmalSupraventricularTachycardia}}</strong> 阵</div>
                <div class="box4-1">二联律: <strong>{{froms.supraventricularEctopicHeartRate.doubletLaw}}</strong> 阵</div>
                <div class="box4-1">三联律: <strong>{{froms.supraventricularEctopicHeartRate.tripleLaw}}</strong> 阵</div>
                <div class="box4-1">最快室上速: <strong>{{froms.supraventricularEctopicHeartRate.fastestSupraventricularVelocity}}</strong> bpm,&emsp;发生于</div>
                <div class="box4-1">最长室上速: <strong>{{froms.supraventricularEctopicHeartRate.fastestSupraventricularVelocity}}</strong> 次心搏,发生于</div>
              </div>
            </div>
          </div>
          <div class="box5">
            <h2>心率变异性分析</h2>
            <div>
              <span>SDNN: <strong>{{froms.heartRateVariabilityAnalysis.SDNN}}</strong> ms</span>
              <span>SDANN: <strong>{{froms.heartRateVariabilityAnalysis.SDANN}}</strong> ms</span>
              <span>SDNN Index: <strong>{{froms.heartRateVariabilityAnalysis.SDNNIndex}}</strong></span>
              <span>rMSSD: <strong>{{froms.heartRateVariabilityAnalysis.rMSSD}}</strong> ms</span>
              <span>PNN50: <strong>{{froms.heartRateVariabilityAnalysis.PNN50}}</strong>%</span>
            </div>
          </div>
          <div class="box6">
            <div class="box6-top clearfix">
              <h2>房颤分析</h2>
              <span style="margin-left: 7vw;">总数: <strong>{{froms.atrialFibrillationAnalysis.total}}</strong> 次</span>
            </div>
            <div class="box6-1">
              <div>发生阵数: <strong>{{froms.atrialFibrillationAnalysis.number}} </strong> 阵</div>
              <div>大于 <strong>1500ms</strong> 的心搏有<strong>{{froms.atrialFibrillationAnalysis.num_1500}}</strong> 次</div>
              <div></div>
            </div>
            <div class="box6-1">
              <div>持续时间: <strong>{{froms.atrialFibrillationAnalysis.duration}} </strong></div>
              <div>大于 <strong>2000ms</strong> 的心搏有<strong>{{froms.atrialFibrillationAnalysis.num_2000}}</strong> 次</div>
              <div></div>
            </div>
          </div>
          <div class="box7">
            <div class="box7-top">
              <h2>起搏分析</h2>
              <span>总数: <strong>{{ froms.pacingAnalysis.total}}</strong> 次</span>
              <span>百分比: <strong>{{ froms.pacingAnalysis.pacingAnalysisPercentage}}</strong> %</span>
            </div>
            <div class="box7-1">
              <div>心房起搏数: <strong>{{ froms.pacingAnalysis.atrialPace}}</strong> </div>
              <div>百分比: <strong>{{ froms.pacingAnalysis.atrialPacePercentage}}</strong> %</div>
              <div>双腔起搏: <strong>{{ froms.pacingAnalysis.dual_chamberPacing}}</strong> </div>
              <div>百分比: <strong>{{ froms.pacingAnalysis.dual_chamberPacingPercentage}}</strong> %</div>
            </div>
            <div class="box7-1">
              <div>心室起搏数: <strong>{{ froms.pacingAnalysis.ventricularPace}}</strong> </div>
              <div>百分比: <strong>{{ froms.pacingAnalysis.ventricularPacePercentage}}</strong> %</div>
              <div>错误起搏: <strong>{{ froms.pacingAnalysis.wrongPacing}}</strong> </div>
              <div> 百分比: <strong>{{ froms.pacingAnalysis.wrongPacingPercentage}}</strong> %</div>
            </div>

          </div>
          <div class="box8 clearfix">
            <h2>结论:</h2>
            <div class="box8-1">
              一丶总心搏<strong>{{froms.heartRate.totalHeartRate}}</strong>次，
              平均心率<strong>{{froms.heartRate.averageHeartRate}}</strong>bpm，
              最快心率<strong>{{froms.heartRate.fastestHeartRate}}</strong>pm（<strong>{{froms.heartRate.fastestTime}}</strong>），
              最慢心率<strong>{{froms.heartRate.slowestHeartRate}}</strong>bpm（<strong>{{froms.heartRate.slowestTime}}</strong>）；
            </div>
            <div class="box8-1">HRV:SD>50ms</div>
            <div class="box8-1">二丶诊断</div>
            <div class="box8-1">1.窦性心律 心率动态变化正常</div>
            <div class="box8-1">2.最快心率156，仍为窦性</div>
            <div class="box8-1">3.ST:ll、II、 avF、V5、V6导联于: 12:30-13:30,18:27-18:38,8:10-9:05分 (心率较快时)出现水平型压低0.10mv，最大压低0.20mv伴多数导联T波倒置，请结合临床</div>
            <div class="box8-1">4.心率变异性:正常</div>
            <div class="box8-1">5.建议治疗后复查</div>
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
                <div class="box9-2">姓名:<strong>{{froms.patientInfo.name}}</strong></div>
                <div class="box9-2">性别:<strong>{{froms.patientInfo.sex}}</strong></div>
              </div>
              <div class="box9-1">
                <div class="box9-2">年龄:<strong>{{froms.patientInfo.age}}</strong></div>
                <div class="box9-2">单号:<strong>{{froms.patientInfo.no}}</strong></div>
              </div>
              <div class="box9-1">
                <div class="box9-2">住院号:<strong>{{froms.patientInfo.hospitalNumber}}</strong></div>
                <div class="box9-2">科室:<strong>{{froms.patientInfo.department}}</strong></div>
              </div>
            </div>
          </div>
          <div class="box10" id="myChart">
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
      </div>
    </div>
    <button type="button" style="margin-top: 20px;" @click="btnClick">导出PDF</button>

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
      froms:{
        patientInfo:{
          name:"李雨晴",
          sex:"女",
          age:"31",
          no:"0001",
          //门诊号
          clinicNumber:"00001",
          //住院号
          hospitalNumber:"00001",
          //科室
          department:"001",
          //床号
          bedNo:"01",
          recordDate:"2022-01-03 09:04:50",
          recordDuration:"24小时0分钟"
        },
        heartRate:{
          totalHeartRate:118644,
          slowestHeartRate:55,
          slowestTime:"05:11:44",
          fastestHeartRate:156,
          fastestTime:"13:03:16",
          averageHeartRate:85,
        },
        //停搏
        asystole:{
          total:0,
          longest:0,
          longestTime:"00:00"
        },
        //心动过缓/心动过速
        heartbeat:{
          //心动过缓总持续时间
          bradycardiaTotalDuration:"无数据",
          //心动过速总持续时间
          tachycardiaTotalDuration:"无数据",
          //最长心动过缓心搏数
          longestBradycardia:"无数据",
          //最长心动过速心搏数
          longestTachycardia:"无数据",
        },
        //室性异位心率
        ventricularEctopicHeartRate:{
          //总数
          total:0,
          //单发
          singleShot:0,
          //成对
          pair:0,
          //短阵性室速
          burstVT:0,
          RONT:0,
          //二联律
          doubletLaw:0,
          //三联律
          tripleLaw:0,
          //室性逸搏
          ventricularEscape:0,
          //最快室速
          fastestVT:0,
          //最长室速
          longestVT:0,
          //室性百分比
          ventricularPercentage:0,
          //平均/小时
          //平均/1000
        },
        //室上性异位心率
        supraventricularEctopicHeartRate:{
          //总数
          total:0,
          //单发
          singleShot:0,
          //成对
          pair:0,
          //短阵性室上速
          paroxysmalSupraventricularTachycardia:0,
          //二联律
          doubletLaw:0,
          //三联律
          tripleLaw:0,
          //最快室上速
          fastestSupraventricularVelocity:0,
          //最长室上速
          longestSupraventricularVelocity:0,
          //室上性百分比
          supraventricularPercentage:0,
          //平均/小时
          //平均/1000
        },
        //心率变异性分析
        heartRateVariabilityAnalysis:{
          SDNN:139,
          SDANN:130,
          SDNNIndex:56,
          rMSSD:37,
          PNN50:13.4
        },
        //房颤分析
        atrialFibrillationAnalysis:{
          //总数
          total:"无数据",
          //发生阵数
          number:"无数据",
          //大于1500
          num_1500:"无数据",
          //大于2000
          num_2000:"无数据",
          //持续时间
          duration:"无数据",
        },
        //起搏分析
        pacingAnalysis:{
          //起搏总数
          total:0,
          //起搏占百分比
          pacingAnalysisPercentage:0.0,
          //心房起搏数
          atrialPace:0,
          //心房起搏占百分比
          atrialPacePercentage:0.0,
          //双腔起搏
          dual_chamberPacing:0,
          //双腔起搏占百分比
          dual_chamberPacingPercentage:0.0,
          //心室起搏数
          ventricularPace:0,
          //心室起搏数占百分比
          ventricularPacePercentage:0.0,
          //错误起搏
          wrongPacing:0,
          //错误起搏占百分比
          wrongPacingPercentage:0.0,

        }


      }
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
  margin-bottom: 18vh;
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
  height: 100px;
  margin: 30px auto 0;
  border: 3px solid black;
  font-size:13px;
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
  height: 98px;
  margin: 0 auto;
  border: 3px solid black;
  font-size:13px;
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
  height: 30px;
  margin: 0 auto;
  padding: 5px 0;
  border: 3px solid black;
  font-size:13px;
}
.box3 span {
  font-weight: 700;
}

.box4{
  width: 90%;
  height: 200px;
  margin: 0 auto;
  border: 3px solid black;
  font-size:13px;
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
  height: 50px;
  margin: 0 auto;
  padding: 5px 5px;
  border: 3px solid black;
  font-size:13px;
}
.box5 h2 {
  text-align: left;
}
.box5 div{
  display: flex;
}
.box5 span{
  flex: 5;
}



.box6{
  width: 90%;
  height: 70px;
  margin: 0 auto;
  padding: 5px 5px;
  border: 3px solid black;
  font-size:13px;
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
.box6-1{
  display: flex;
}

.box6-1 div{
  flex: 20%;
}

.box7{
  width: 90%;
  height: 70px;
  margin: 0 auto;
  padding: 5px 5px;
  border: 3px solid black;
  font-size:13px;
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
.box7-1{
  display: flex;
}
.box7-1 div{
  flex: 4;
}


.box8{
  width: 90%;
  height: 320px;
  margin: 0 auto;
  padding: 5px 15px;
  border: 3px solid black;
  font-size:13px;
}
.box8 h2 {
  text-align: left;
}
.box8-1 {
  margin: 5px 0;
}
.box8 .bottom {
  margin-top: 3vm;
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
  font-size:13px;

}
.box9-bottom {
  display: flex;
  justify-content: space-between;
  padding: 0 5vw;
}

.box10 {
  width: 90%;
  height: 150px;
  margin: 0 auto;
  padding: 5px 15px;
  border: 3px solid black;
  font-size:13px;
}

.box11 {
  width: 90%;
  margin: 0 auto;
  padding: 5px 15px;
  border: 3px solid black;
  font-size:13px;
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
  margin-left: 1vw;
}
.box12 {
  width: 90%;
  margin: 0 auto;
  padding: 5px 15px;
  border: 3px solid black;
  font-size:13px;
}

.flex-box{
  display: flex;
}
.flex-box-child{
  flex: 2;
}
</style>
