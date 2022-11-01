<template>
  <div class="wrap" style="background: #8c939d">
    <div id="pdfDom" style="padding: 10px;width: 800px ;background: #fff">
      <div id="app">
        <div class="page">
          <h1>{{ hospitalName }}</h1>
          <h1>动态心电图报告</h1>
          <div class="main">
            <div class="time">报告时间：{{ froms.reportTime }}</div>
            <div class="box1">

              <div class="box1-top">
                <h2>病人信息</h2>
              </div>
              <div class="box1-bottom clearfix">
                <div class="box1-bottom1">
                  <div class="box1-1">
                    姓名:
                    <strong>{{ froms.patientInfo.name }}</strong>
                  </div>
                  <div class="box1-1">
                    性别:
                    <strong>{{ froms.patientInfo.sex }}</strong>
                  </div>
                  <div class="box1-1">
                    年龄:
                    <strong>{{ froms.patientInfo.age }}</strong>
                  </div>
                  <div class="box1-1">
                    单号:
                    <strong>{{ froms.patientInfo.no }}</strong>
                  </div>
                </div>
                <div class="box1-bottom2">
                  <div class="box1-1">
                    门诊号:
                    <strong>{{ froms.patientInfo.clinicNumber }}</strong>
                  </div>
                  <div class="box1-1">
                    住院号:
                    <strong>{{ froms.patientInfo.hospitalNumber }}</strong>
                  </div>
                  <div class="box1-1">
                    科室:
                    <strong>{{ froms.patientInfo.department }}</strong>
                  </div>
                  <div class="box1-1">
                    床号:
                    <strong>{{ froms.patientInfo.bedNo }}</strong>
                  </div>
                </div>
                <div class="box1-bottom3">
                  <div class="box1-1">
                    ID:
                    <strong>{{ froms.patientInfo.ID }}</strong>
                  </div>
                  <div class="box1-1">
                    设备编号:
                    <strong>{{ froms.patientInfo.deviceID }}</strong>
                  </div>
                  <div class="box1-1">
                    记录日期:
                    <strong>{{ froms.patientInfo.recordDate }}</strong>
                  </div>
                  <div class="box1-1">
                    记录时长:
                    <strong>{{ froms.patientInfo.recordDuration }}</strong>
                  </div>
                </div>
              </div>
            </div>
            <div class="box2 clearfix">
              <div class="box2-top">
                <div class="box2-title">
                  <h2>心率</h2>
                </div>
                <div class="box2-left-bottom">
                  <div class="box2-1">
                    总心搏数:
                    <strong>{{ froms.heartRate.totalHeartRate }}</strong> 次
                  </div>
                  <div class="box2-1">
                    最慢心率:
                    <strong>{{ froms.heartRate.slowestHeartRate }}</strong> bpm
                  </div>
                  <div class="box2-1">
                    最快心率:
                    <strong>{{ froms.heartRate.fastestHeartRate }}</strong> bpm
                  </div>
                  <div class="box2-1">
                    平均心率:
                    <strong>{{ froms.heartRate.averageHeartRate }}</strong> bpm
                  </div>
                </div>
                <div class="box2-left-bottom">
                  <div class="box2-1">
                    心率标准差:
                    <strong>{{ froms.heartRate.standardDeviation }}</strong>
                  </div>
                  <div class="box2-1">
                    最长间期:
                    <strong>{{ froms.heartRate.longestInterval }}</strong> ms
                  </div>
                  <div class="box2-1">
                    最短间期:
                    <strong>{{ froms.heartRate.slowestInterval }}</strong> ms
                  </div>
                  <div class="box2-1"></div>
                </div>
              </div>
              <div class="box2-bottom">
                <div class="box2-title">
                  <h2>心动过缓/心动过速</h2>
                </div>
                <div class="box2-1-outer">
                  <div class="box2-right-bottom">
                    <div class="box2-1">
                      心动过缓总数:
                      <strong>{{ froms.heartbeat.bradycardiaTotal }}</strong> 次
                    </div>
                    <div class="box2-1">
                      心动过缓占比:
                      <strong>{{ froms.heartbeat.bradycardiaPercentage }}</strong>
                    </div>
                    <div class="box2-1">
                      心动过缓总持续时间:
                      <strong>{{ froms.heartbeat.bradycardiaTotalDuration }}</strong> s
                    </div>
                    <div class="box2-1">
                      最长心动过缓周期开始时间:
                      <strong>{{ froms.heartbeat.longestBradycardiaStartTime }}</strong>
                    </div>
                    <div class="box2-1">
                      最长心动过缓周期结束时间:
                      <strong>{{ froms.heartbeat.longestBradycardiaEndTime }}</strong>
                    </div>
                    <div class="box2-1">
                      最长心动过缓周期持续时间:
                      <strong>{{ froms.heartbeat.longestBradycardiaCycleDuration }}</strong> s
                    </div>
                    <div class="box2-1">
                      最长心动过缓心搏数:
                      <strong>{{ froms.heartbeat.longestBradycardia }}</strong> 次
                    </div>
                  </div>
                  <div class="box2-right-bottom">
                    <div class="box2-1">
                      心动过速总数:
                      <strong>{{ froms.heartbeat.tachycardiaTotal }}</strong> 次
                    </div>
                    <div class="box2-1">
                      心动过速占比:
                      <strong>{{ froms.heartbeat.tachycardiaPercentage }}</strong>
                    </div>
                    <div class="box2-1">
                      心动过速总持续时间:
                      <strong>{{ froms.heartbeat.tachycardiaTotalDuration }}</strong> s
                    </div>
                    <div class="box2-1">
                      最长心动过速周期开始时间:
                      <strong>{{ froms.heartbeat.longestTachycardiaStartTime }}</strong>
                    </div>
                    <div class="box2-1">
                      最长心动过速周期结束时间:
                      <strong>{{ froms.heartbeat.longestTachycardiaEndTime }}</strong>
                    </div>
                    <div class="box2-1">
                      最长心动过速周期持续时间:
                      <strong>{{ froms.heartbeat.longestTachycardiaCycleDuration }}</strong> s
                    </div>
                    <div class="box2-1">
                      最长心动过速心搏数:
                      <strong>{{ froms.heartbeat.longestTachycardia }}</strong> 次
                    </div>
                  </div>
                </div>

              </div>
            </div>
            <div class="box3">
              <span>停搏(>2.0秒)</span>&emsp;&emsp;总计&nbsp
              <strong>{{ froms.asystole.total }}</strong>;&nbsp;次，最长一次：&nbsp;&nbsp;
              <strong>{{ froms.asystole.longest }}</strong>&nbsp;&nbsp;秒，发生于：&nbsp;
              <strong>{{ froms.asystole.longestTime }}&nbsp;</strong>
            </div>
            <div class="box4 clearfix">
              <div class="box4-left">
                <div class="box4-top">
                  <h2>室性异位心率</h2>
                </div>
                <div class="box4-left-bottom">
                  <div class="flex-box">
                    <div class="box4-1 flex-box-child">
                      总数:
                      <strong>{{ froms.ventricularEctopicHeartRate.total }}</strong> 次
                    </div>
                    <div class="box4-1 flex-box-child">
                      室性百分比:
                      <strong>{{ froms.ventricularEctopicHeartRate.ventricularPercentage }}</strong>
                    </div>
                  </div>
                  <div class="flex-box">
                    <div class="box4-1 flex-box-child">
                      单发:
                      <strong>{{ froms.ventricularEctopicHeartRate.singleShot }}</strong> 次
                    </div>
                    <div class="box4-1 flex-box-child">
                      平均/小时:
                      <strong></strong>
                    </div>
                  </div>
                  <div class="flex-box">
                    <div class="box4-1 flex-box-child">
                      成对:
                      <strong>{{ froms.ventricularEctopicHeartRate.pair }}</strong> 次
                    </div>
                    <div class="box4-1 flex-box-child">
                      平均/1000:
                      <strong></strong>
                    </div>
                  </div>

                  <div class="box4-1">
                    短阵性室速:
                    <strong>{{ froms.ventricularEctopicHeartRate.burstVT }}</strong> 阵
                  </div>
                  <div class="box4-1">
                    RONT:
                    <strong>{{ froms.ventricularEctopicHeartRate.RONT }}</strong> 次
                  </div>
                  <div class="box4-1">
                    二联律:
                    <strong>{{ froms.ventricularEctopicHeartRate.doubletLaw }}</strong> 阵
                  </div>
                  <div class="box4-1">
                    三联律:
                    <strong>{{ froms.ventricularEctopicHeartRate.tripleLaw }}</strong> 阵
                  </div>
                  <div class="box4-1">
                    室性逸搏:
                    <strong>{{ froms.ventricularEctopicHeartRate.ventricularEscape }}</strong> 次
                  </div>
                  <div class="box4-1">
                    最快室速:
                    <strong>{{ froms.ventricularEctopicHeartRate.fastestVT }}</strong> bpm,&emsp;发生于
                  </div>
                  <div class="box4-1">
                    最长室速:
                    <strong>{{ froms.ventricularEctopicHeartRate.longestVT }}</strong> 次心搏,发生于
                  </div>
                </div>
              </div>
              <div class="box4-right">
                <div class="box4-top">
                  <h2>室上性异位心率</h2>
                </div>
                <div class="box4-right-bottom">
                  <div class="flex-box">
                    <div class="box4-1 flex-box-child">
                      总数:
                      <strong>{{ froms.supraventricularEctopicHeartRate.total }}</strong> 次
                    </div>
                    <div class="box4-1 flex-box-child">
                      室上性百分比:
                      <strong>{{ froms.supraventricularEctopicHeartRate.supraventricularPercentage }}</strong>
                    </div>
                  </div>
                  <div class="flex-box">
                    <div class="box4-1 flex-box-child">
                      单发:
                      <strong>{{ froms.supraventricularEctopicHeartRate.singleShot }}</strong> 次
                    </div>
                    <div class="box4-1 flex-box-child">
                      平均/小时:
                      <strong></strong>
                    </div>
                  </div>
                  <div class="flex-box">
                    <div class="box4-1 flex-box-child">
                      成对:
                      <strong>{{ froms.supraventricularEctopicHeartRate.pair }}</strong> 次
                    </div>
                    <div class="box4-1 flex-box-child">
                      平均/1000:
                      <strong></strong>
                    </div>
                  </div>
                  <div class="box4-1">
                    短阵性室上速:
                    <strong>{{ froms.supraventricularEctopicHeartRate.paroxysmalSupraventricularTachycardia }}</strong>
                    阵
                  </div>
                  <div class="box4-1">
                    二联律:
                    <strong>{{ froms.supraventricularEctopicHeartRate.doubletLaw }}</strong> 阵
                  </div>
                  <div class="box4-1">
                    三联律:
                    <strong>{{ froms.supraventricularEctopicHeartRate.tripleLaw }}</strong> 阵
                  </div>
                  <div class="box4-1">
                    最快室上速:
                    <strong>{{ froms.supraventricularEctopicHeartRate.fastestSupraventricularVelocity }}</strong> bpm,&emsp;发生于
                  </div>
                  <div class="box4-1">
                    最长室上速:
                    <strong>{{ froms.supraventricularEctopicHeartRate.fastestSupraventricularVelocity }}</strong>
                    次心搏,发生于
                  </div>
                </div>
              </div>
            </div>
            <div class="box5">
              <h2>心率变异性分析</h2>
              <div>
                <span>
                  SDNN:
                  <strong>{{ froms.heartRateVariabilityAnalysis.SDNN }}</strong>ms
                </span>
                <span>
                  rMSSD:
                  <strong>{{ froms.heartRateVariabilityAnalysis.rMSSD }}</strong>ms
                </span>
                <span>
                  NN20:
                  <strong>{{ froms.heartRateVariabilityAnalysis.NN20 }}</strong>ms
                </span>
                <span>
                  PNN20:
                  <strong>{{ froms.heartRateVariabilityAnalysis.PNN20 }}</strong>%
                </span>
                <span>
                  NN50:
                  <strong>{{ froms.heartRateVariabilityAnalysis.NN50 }}</strong>ms
                </span>
                <span>
                  PNN50:
                  <strong>{{ froms.heartRateVariabilityAnalysis.PNN50 }}</strong>%
                </span>
              </div>

            </div>
            <div class="box6">
              <div class="box6-top clearfix">
                <h2>房颤分析</h2>
                <span style="margin-left: 7vw;">
                  总数:
                  <strong>{{ froms.atrialFibrillationAnalysis.total }}</strong> 次
                </span>
              </div>
              <div class="box6-bottom">
                <div class="box6-1">
                  <div>
                    发生阵数:
                    <strong>{{ froms.atrialFibrillationAnalysis.number }}</strong> 阵
                  </div>
                  <div>
                    大于
                    <strong>1500ms</strong> 的心搏有
                    <strong>{{ froms.atrialFibrillationAnalysis.num_1500 }}</strong> 次
                  </div>
                  <div></div>
                </div>
                <div class="box6-1">
                  <div>
                    持续时间:
                    <strong>{{ froms.atrialFibrillationAnalysis.duration }}</strong>
                  </div>
                  <div>
                    大于
                    <strong>2000ms</strong> 的心搏有
                    <strong>{{ froms.atrialFibrillationAnalysis.num_2000 }}</strong> 次
                  </div>
                  <div></div>
                </div>
              </div>

            </div>
            <div class="box7">
              <div class="box7-top">
                <h2>起搏分析</h2>
                <span>
                  总数:
                  <strong>{{ froms.pacingAnalysis.total }}</strong> 次
                </span>
                <span>
                  百分比:
                  <strong>{{ froms.pacingAnalysis.pacingAnalysisPercentage }}</strong> %
                </span>
              </div>
              <div class="box7-1">
                <div>
                  心房起搏数:
                  <strong>{{ froms.pacingAnalysis.atrialPace }}</strong>
                </div>
                <div>
                  百分比:
                  <strong>{{ froms.pacingAnalysis.atrialPacePercentage }}</strong> %
                </div>
                <div>
                  双腔起搏:
                  <strong>{{ froms.pacingAnalysis.dual_chamberPacing }}</strong>
                </div>
                <div>
                  百分比:
                  <strong>{{ froms.pacingAnalysis.dual_chamberPacingPercentage }}</strong> %
                </div>
              </div>
              <div class="box7-1">
                <div>
                  心室起搏数:
                  <strong>{{ froms.pacingAnalysis.ventricularPace }}</strong>
                </div>
                <div>
                  百分比:
                  <strong>{{ froms.pacingAnalysis.ventricularPacePercentage }}</strong> %
                </div>
                <div>
                  错误起搏:
                  <strong>{{ froms.pacingAnalysis.wrongPacing }}</strong>
                </div>
                <div>
                  百分比:
                  <strong>{{ froms.pacingAnalysis.wrongPacingPercentage }}</strong> %
                </div>
              </div>
            </div>
            <div class="box8 clearfix">
              <h2>结论:</h2>
              <div class="box8-1">
                一丶总心搏
                <strong>{{ froms.heartRate.totalHeartRate }}</strong>次，
                平均心率
                <strong>{{ froms.heartRate.averageHeartRate }}</strong>bpm，
                最快心率
                <strong>{{ froms.heartRate.fastestHeartRate }}</strong>pm（
                <!--              <strong>{{froms.heartRate.fastestTime}}</strong>-->
                ），
                最慢心率
                <strong>{{ froms.heartRate.slowestHeartRate }}</strong>bpm（
                <!--              <strong>{{froms.heartRate.slowestTime}}</strong>-->
                ）；
              </div>
              <div class="box8-1">HRV:SD>50ms</div>
              <div class="box8-1">二丶诊断</div>
              <textarea
                placeholder="请输入诊断结果"
                class="box8-1-1"
                v-model="froms.textarea.text1"
              >
              </textarea>
              <!--              <div class="box8-1">1.窦性心律 心率动态变化正常</div>-->
              <!--              <div class="box8-1">2.最快心率156，仍为窦性</div>-->
              <!--              <div-->
              <!--                class="box8-1"-->
              <!--              >3.ST:ll、II、 avF、V5、V6导联于: 12:30-13:30,18:27-18:38,8:10-9:05分 (心率较快时)出现水平型压低0.10mv，最大压低0.20mv伴多数导联T波倒置，请结合临床</div>-->
              <!--              <div class="box8-1">4.心率变异性:正常</div>-->
              <!--              <div class="box8-1">5.建议治疗后复查</div>-->
              <div class="bottom">
                报告者:<input class="box8-2" v-model="doctorName"></input>
                日期:<input class="box8-2" v-if="diagnosisData!=null" v-model="diagnosisData"></input>
                <input class="box8-2" v-else v-model="dataTime"></input>

              </div>
            </div>
          </div>
        </div>
<!--        <div class="page">-->
<!--          <div class="main">-->
<!--            <div class="box9 clearfix">-->
<!--              <div class="box9-top">-->
<!--                <h2>概要数据统计表</h2>-->
<!--              </div>-->
<!--              <div class="box9-bottom">-->
<!--                <div class="box9-1">-->
<!--                  <div class="box9-2">-->
<!--                    姓名:-->
<!--                    <strong>{{ froms.patientInfo.name }}</strong>-->
<!--                  </div>-->
<!--                  <div class="box9-2">-->
<!--                    性别:-->
<!--                    <strong>{{ froms.patientInfo.sex }}</strong>-->
<!--                  </div>-->
<!--                </div>-->
<!--                <div class="box9-1">-->
<!--                  <div class="box9-2">-->
<!--                    年龄:-->
<!--                    <strong>{{ froms.patientInfo.age }}</strong>-->
<!--                  </div>-->
<!--                  <div class="box9-2">-->
<!--                    单号:-->
<!--                    <strong>{{ froms.patientInfo.no }}</strong>-->
<!--                  </div>-->
<!--                </div>-->
<!--                <div class="box9-1">-->
<!--                  <div class="box9-2">-->
<!--                    住院号:-->
<!--                    <strong>{{ froms.patientInfo.hospitalNumber }}</strong>-->
<!--                  </div>-->
<!--                  <div class="box9-2">-->
<!--                    科室:-->
<!--                    <strong>{{ froms.patientInfo.department }}</strong>-->
<!--                  </div>-->
<!--                </div>-->
<!--              </div>-->
<!--            </div>-->
<!--            <div class="box10" id="myChart">图形数据</div>-->
<!--            <div class="box11 clearfix">-->
<!--              <el-table-->
<!--                :data="tableData"-->
<!--                style="width: 100%"-->
<!--                size="mini"-->
<!--                :cell-style="columnStyle">-->
<!--                <el-table-column-->
<!--                  prop="time"-->
<!--                  label="时间"-->
<!--                  width="48">-->
<!--                </el-table-column>-->
<!--                <el-table-column-->
<!--                  prop="heartBeatNumber"-->
<!--                  label="心搏数"-->
<!--                  width="48">-->
<!--                </el-table-column>-->

<!--                <el-table-column-->
<!--                  label="心律(bpm)"-->
<!--                >-->
<!--                  <el-table-column-->
<!--                    prop="heartRate.fastest"-->
<!--                    label="最高"-->
<!--                    width="42">-->
<!--                  </el-table-column>-->
<!--                  <el-table-column-->
<!--                    prop="heartRate.average"-->
<!--                    label="平均"-->
<!--                    width="40">-->
<!--                  </el-table-column>-->
<!--                  <el-table-column-->
<!--                    prop="heartRate.slowest"-->
<!--                    label="最低"-->
<!--                    width="40">-->
<!--                  </el-table-column>-->
<!--                </el-table-column>-->

<!--                <el-table-column label="室性异位">-->
<!--                  <el-table-column-->
<!--                    prop="ventricularEctopic.total"-->
<!--                    label="总数"-->
<!--                    width="40">-->
<!--                  </el-table-column>-->
<!--                  <el-table-column-->
<!--                    prop="ventricularEctopic.percentage"-->
<!--                    label="%"-->
<!--                    width="40">-->
<!--                  </el-table-column>-->
<!--                  <el-table-column-->
<!--                    prop="ventricularEctopic.singleShot"-->
<!--                    label="单发"-->
<!--                    width="40">-->
<!--                  </el-table-column>-->
<!--                  <el-table-column-->
<!--                    prop="ventricularEctopic.pair"-->
<!--                    label="成对"-->
<!--                    width="40">-->
<!--                  </el-table-column>-->
<!--                  <el-table-column-->
<!--                    prop="ventricularEctopic.VT"-->
<!--                    label="室速"-->
<!--                    width="40">-->
<!--                  </el-table-column>-->
<!--                  <el-table-column-->
<!--                    prop="ventricularEctopic.escape"-->
<!--                    label="逸搏"-->
<!--                    width="40">-->
<!--                  </el-table-column>-->
<!--                </el-table-column>-->

<!--                <el-table-column label="室性上异位">-->
<!--                  <el-table-column-->
<!--                    prop="supraventricularEctopic.total"-->
<!--                    label="总数"-->
<!--                    width="40">-->
<!--                  </el-table-column>-->
<!--                  <el-table-column-->
<!--                    prop="supraventricularEctopic.percentage"-->
<!--                    label="%"-->
<!--                    width="40">-->
<!--                  </el-table-column>-->
<!--                  <el-table-column-->
<!--                    prop="supraventricularEctopic.singleShot"-->
<!--                    label="单发"-->
<!--                    width="40">-->
<!--                  </el-table-column>-->
<!--                  <el-table-column-->
<!--                    prop="supraventricularEctopic.pair"-->
<!--                    label="成对"-->
<!--                    width="40">-->
<!--                  </el-table-column>-->
<!--                  <el-table-column-->
<!--                    prop="supraventricularEctopic.supraventricularTachycardia"-->
<!--                    label="室上速"-->
<!--                    width="40">-->
<!--                  </el-table-column>-->
<!--                </el-table-column>-->
<!--                <el-table-column-->
<!--                  prop="asystole"-->
<!--                  label="停搏"-->
<!--                  width="38">-->
<!--                </el-table-column>-->
<!--              </el-table>-->
<!--              &lt;!&ndash;            <div class="box11-1">&ndash;&gt;-->
<!--              &lt;!&ndash;              <span>时间</span>&ndash;&gt;-->
<!--              &lt;!&ndash;            </div>&ndash;&gt;-->
<!--              &lt;!&ndash;            <div class="box11-2">&ndash;&gt;-->
<!--              &lt;!&ndash;              <span>心搏数</span>&ndash;&gt;-->
<!--              &lt;!&ndash;            </div>&ndash;&gt;-->
<!--              &lt;!&ndash;            <div class="box11-3">&ndash;&gt;-->
<!--              &lt;!&ndash;              <span>心率</span>&ndash;&gt;-->
<!--              &lt;!&ndash;              <div class="box11-3-1">&ndash;&gt;-->
<!--              &lt;!&ndash;                <span>最高</span>&ndash;&gt;-->
<!--              &lt;!&ndash;                <span>平均</span>&ndash;&gt;-->
<!--              &lt;!&ndash;                <span>最低</span>&ndash;&gt;-->
<!--              &lt;!&ndash;              </div>&ndash;&gt;-->
<!--              &lt;!&ndash;            </div>&ndash;&gt;-->
<!--              &lt;!&ndash;            <div class="box11-4">&ndash;&gt;-->
<!--              &lt;!&ndash;              <span>室性异位</span>&ndash;&gt;-->

<!--              &lt;!&ndash;              </div>&ndash;&gt;-->
<!--              &lt;!&ndash;            </div>&ndash;&gt;-->
<!--              &lt;!&ndash;            <div class="box11-5">&ndash;&gt;-->
<!--              &lt;!&ndash;              <span>室上性异位</span>&ndash;&gt;-->
<!--              &lt;!&ndash;              <div class="box11-5-1">&ndash;&gt;-->
<!--              &lt;!&ndash;                <span>总数</span>&ndash;&gt;-->
<!--              &lt;!&ndash;                <span>%</span>&ndash;&gt;-->
<!--              &lt;!&ndash;                <span>单发</span>&ndash;&gt;-->
<!--              &lt;!&ndash;                <span>成对室上速</span>&ndash;&gt;-->
<!--              &lt;!&ndash;              </div>&ndash;&gt;-->
<!--              &lt;!&ndash;            </div>&ndash;&gt;-->
<!--              &lt;!&ndash;            <div class="box11-6">&ndash;&gt;-->
<!--              &lt;!&ndash;              <span>停搏</span>&ndash;&gt;-->
<!--              &lt;!&ndash;            </div>&ndash;&gt;-->
<!--            </div>-->
<!--            <div class="box12" id="container" style="height: 300px"></div>-->
<!--            <div class="box12" id="line1" style="height: 300px"></div>-->

<!--          </div>-->
<!--        </div>-->

        <div class="page">
          <div class="main">
            <div class="box9 clearfix">
              <div class="box9-top">
                <h2>概要数据统计表</h2>
              </div>
              <div class="box9-bottom">
                <div class="box9-1">
                  <div class="box9-2">
                    姓名:
                    <strong>{{ froms.patientInfo.name }}</strong>
                  </div>
                  <div class="box9-2">
                    性别:
                    <strong>{{ froms.patientInfo.sex }}</strong>
                  </div>
                </div>
                <div class="box9-1">
                  <div class="box9-2">
                    年龄:
                    <strong>{{ froms.patientInfo.age }}</strong>
                  </div>
                  <div class="box9-2">
                    单号:
                    <strong>{{ froms.patientInfo.no }}</strong>
                  </div>
                </div>
                <div class="box9-1">
                  <div class="box9-2">
                    住院号:
                    <strong>{{ froms.patientInfo.hospitalNumber }}</strong>
                  </div>
                  <div class="box9-2">
                    科室:
                    <strong>{{ froms.patientInfo.department }}</strong>
                  </div>
                </div>
              </div>
            </div>
<!--            <div class="box12" id="line2" style="height: 300px"></div>-->
            <div class="box12" id="line3" style="height: 225px"></div>
            <div class="box12" id="line4" style="height: 225px"></div>
            <div class="box12" id="line5" style="height: 225px"></div>
            <div class="box12" id="line6" style="height: 225px"></div>
          </div>
        </div>
<!--        <div class="page">-->
<!--          <div class="main">-->
<!--            <div class="box9 clearfix">-->
<!--              <div class="box9-top">-->
<!--                <h2>概要数据统计表</h2>-->
<!--              </div>-->
<!--              <div class="box9-bottom">-->
<!--                <div class="box9-1">-->
<!--                  <div class="box9-2">-->
<!--                    姓名:-->
<!--                    <strong>{{ froms.patientInfo.name }}</strong>-->
<!--                  </div>-->
<!--                  <div class="box9-2">-->
<!--                    性别:-->
<!--                    <strong>{{ froms.patientInfo.sex }}</strong>-->
<!--                  </div>-->
<!--                </div>-->
<!--                <div class="box9-1">-->
<!--                  <div class="box9-2">-->
<!--                    年龄:-->
<!--                    <strong>{{ froms.patientInfo.age }}</strong>-->
<!--                  </div>-->
<!--                  <div class="box9-2">-->
<!--                    单号:-->
<!--                    <strong>{{ froms.patientInfo.no }}</strong>-->
<!--                  </div>-->
<!--                </div>-->
<!--                <div class="box9-1">-->
<!--                  <div class="box9-2">-->
<!--                    住院号:-->
<!--                    <strong>{{ froms.patientInfo.hospitalNumber }}</strong>-->
<!--                  </div>-->
<!--                  <div class="box9-2">-->
<!--                    科室:-->
<!--                    <strong>{{ froms.patientInfo.department }}</strong>-->
<!--                  </div>-->
<!--                </div>-->
<!--              </div>-->
<!--            </div>-->
<!--&lt;!&ndash;            <div class="box12" id="line5" style="height: 300px"></div>&ndash;&gt;-->
<!--            <div class="box12" id="line6" style="height: 300px"></div>-->
<!--            <div class="box12" id="line7" style="height: 300px"></div>-->
<!--          </div>-->
<!--        </div>-->
        <div class="page">
          <div class="main">
            <div class="box9 clearfix">
              <div class="box9-top">
                <h2>概要数据统计表</h2>
              </div>
              <div class="box9-bottom">
                <div class="box9-1">
                  <div class="box9-2">
                    姓名:
                    <strong>{{ froms.patientInfo.name }}</strong>
                  </div>
                  <div class="box9-2">
                    性别:
                    <strong>{{ froms.patientInfo.sex }}</strong>
                  </div>
                </div>
                <div class="box9-1">
                  <div class="box9-2">
                    年龄:
                    <strong>{{ froms.patientInfo.age }}</strong>
                  </div>
                  <div class="box9-2">
                    单号:
                    <strong>{{ froms.patientInfo.no }}</strong>
                  </div>
                </div>
                <div class="box9-1">
                  <div class="box9-2">
                    住院号:
                    <strong>{{ froms.patientInfo.hospitalNumber }}</strong>
                  </div>
                  <div class="box9-2">
                    科室:
                    <strong>{{ froms.patientInfo.department }}</strong>
                  </div>
                </div>
              </div>
            </div>
            <div class="box13 clearfix">
              <div class="box13-left">
                <div id="scatter" style="width:98% ;height:98%;float:right"></div>
              </div>
              <div class="box13-right">
                <div id="bar" style="width:98% ;height:98%"></div>
              </div>
            </div>
            <div class="box13 clearfix">
              <div class="box13-left">
                <div id="bar1" style="width:98% ;height:98%;float:right"></div>
              </div>
              <div class="box13-right">
                <div id="bar2" style="width:98% ;height:98%"></div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <el-button type="primary" round style="margin-top: 20px; margin-left: 1% ;margin-bottom: 15px" @click="btnClear">
      清除所有缓并刷新
    </el-button>
    <el-button type="primary" round style="margin-top: 20px; margin-left: 2% ;margin-bottom: 15px"
               @click="btnClearSelf">清除本页缓存并刷新
    </el-button>
    <el-button type="primary" round style="margin-top: 20px; margin-left: 2% ;margin-bottom: 15px" @click="btnClick">
      导出PDF
    </el-button>
    <el-button type="primary" round style="margin-top: 20px; margin-left: 2% ;margin-bottom: 15px" @click="btnClickPDF">
      上传PDF
    </el-button>
    <el-button type="primary" round style="margin-top: 20px; margin-left: 2% ;margin-bottom: 15px" @click="btnUpload">
      保存诊断结果
    </el-button>

  </div>

</template>

<script>
// 导出页面为PDF格式
import html2Canvas from "html2canvas";
import JsPDF from "jspdf";
import echarts from 'echarts'
import $ from 'jquery';
import {Loading} from 'element-ui';
import {addReport, getReportByPId, listReport, updateReport} from "@/api/report/report";
import {pdfDownload, pdfDownload2} from "../../api/pdf/pdf";

export default {
  name: "ExportPDF",
  data() {
    return {
      exportPDFtitle: this.$route.query.patientName + "心电报告_"+this.$route.query.pId,
      hospitalName: this.$route.query.hospitalName,
      pId: null,
      _th: null,
      ecg_type: null,
      dataTime: "",
      diagnosisData: null,
      doctorName: "",
      froms: {
        textarea: {
          text1: "1.",

        },
        reportTime: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.报告时间,
        patientInfo: {
          name: this.$route.query.patientName,
          //name: (JSON.parse(sessionStorage.getItem(this.$route.query.pId+"data"))).result.姓名,
          sex: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.性别,
          age: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.年龄,
          no: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.门诊号,
          //门诊号
          clinicNumber: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.门诊号,
          //住院号
          hospitalNumber: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.住院号,
          //科室
          department: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.科室,
          //床号
          bedNo: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.床号,
          ID: this.$route.query.pId,
          deviceID: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.设备编号,
          recordDate: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.上传时间,
          recordDuration: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.记录时长
        },
        heartRate: {
          totalHeartRate: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.总心搏数,
          slowestHeartRate: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.最小心率,
          // slowestTime: "05:11:44",
          fastestHeartRate: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.最大心率,
          //fastestTime: "13:03:16",
          averageHeartRate: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.平均心率,
          //标准差
          standardDeviation: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.心率标准差,
          //最长间期
          longestInterval: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.最长间期,
          //最短间期
          slowestInterval: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.最短间期

        },
        //停搏
        asystole: {
          total: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.停搏总计,
          longest: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.最长一次,
          longestTime: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.发生于
        },
        //心动过缓/心动过速
        heartbeat: {
          bradycardiaTotal: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.心动过缓总数,
          bradycardiaPercentage: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.心动过缓占比,
          //心动过缓总持续时间
          bradycardiaTotalDuration: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.心动过缓总持续时间,
          longestBradycardiaStartTime: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.最长心动过缓周期开始时间,
          longestBradycardiaEndTime: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.最长心动过缓周期结束时间,
          longestBradycardiaCycleDuration: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.最长心动过缓周期持续时间,
          longestBradycardia: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.最长心动过缓心搏数,

          tachycardiaTotal: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.心动过速总数,
          tachycardiaPercentage: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.心动过速占比,
          //心动过速总持续时间
          tachycardiaTotalDuration: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.心动过速总持续时间,
          longestTachycardiaStartTime: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.最长心动过速周期开始时间,
          longestTachycardiaEndTime: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.最长心动过速周期结束时间,
          longestTachycardiaCycleDuration: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.最长心动过速周期持续时间,
          longestTachycardia: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.最长心动过速心搏数,

        },

        //室性异位心率
        ventricularEctopicHeartRate: {
          //总数
          total: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.室性异位心律总数,
          //单发
          singleShot: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.室性异位心律单发,
          //成对
          pair: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.室性异位心律成对,
          //短阵性室速
          burstVT: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.室性异位心律短振性室速,
          RONT: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.室性异位心律RONT,
          //二联律
          doubletLaw: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.室性异位心律二联律,
          //三联律
          tripleLaw: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.室性异位心律三联率,
          //室性逸搏
          ventricularEscape: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.室性异位心律室性逸搏,
          //最快室速
          fastestVT: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.室性异位心律最快室速,
          //最长室速
          longestVT: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.室性异位心律最长室速,
          //室性百分比
          ventricularPercentage: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.室性异位心律室性百分比
          //平均/小时
          //平均/1000
        },
        //室上性异位心率

        // '室上性异位心律室性逸搏': None,
        // '室上性异位心律最快室上速发生于': None,
        // '室上性异位心律最长室上速发生于': None,
        // '室上性异位心律平均/小时': None,
        // '室上性异位心律平均/1000': None,
        supraventricularEctopicHeartRate: {
          //总数
          total: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.室上性异位心律总数,
          //单发
          singleShot: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.室上性异位心律单发,
          //成对
          pair: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.室上性异位心律成对,
          //短阵性室上速
          paroxysmalSupraventricularTachycardia: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.室上性异位心律短振性室上速,
          //二联律
          doubletLaw: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.室上性异位心律二联律,
          //三联律
          tripleLaw: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.室上性异位心律三联率,
          //最快室上速
          fastestSupraventricularVelocity: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.室上性异位心律最快室上速,
          //最长室上速
          longestSupraventricularVelocity: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.室上性异位心律最长室上速,
          //室上性百分比
          supraventricularPercentage: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.室上性异位心律室上性百分比
          //平均/小时
          //平均/1000
        },
        //心率变异性分析
        heartRateVariabilityAnalysis: {
          SDNN: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.sdnn,
          NN20: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.nn20,
          PNN20: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.pnn20,
          NN50: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.nn50,
          rMSSD: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.rmssd,
          PNN50: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.pnn50
        },
        //房颤分析
        atrialFibrillationAnalysis: {
          //总数
          total: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.房颤总数,
          //发生阵数
          number: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.房颤发生阵数,
          //大于1500
          num_1500: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.房颤大于1500ms,
          //大于2000
          num_2000: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.房颤大于2000ms,
          //持续时间
          duration: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.房颤持续时间
        },
        //起搏分析
        pacingAnalysis: {
          //起搏总数
          total: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.起搏总数,
          //起搏占百分比
          pacingAnalysisPercentage: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.起搏总数百分比,
          //心房起搏数
          atrialPace: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.起搏心房起搏数,
          //心房起搏占百分比
          atrialPacePercentage: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.起搏心房起搏数百分比,
          //双腔起搏
          dual_chamberPacing: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.起搏双腔起搏,
          //双腔起搏占百分比
          dual_chamberPacingPercentage: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.起搏双腔起搏百分比,
          //心室起搏数
          ventricularPace: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.起搏心室起搏数,
          //心室起搏数占百分比
          ventricularPacePercentage: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.起搏心室起搏数百分比,
          //错误起搏
          wrongPacing: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.起搏错误起搏,
          //错误起搏占百分比
          wrongPacingPercentage: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.起搏错误起搏百分比
        },

      },
      tableData: [
        {
          time: "-",
          heartBeatNumber: "-",
          heartRate: {
            fastest: "-",
            average: "-",
            slowest: "-"
          },
          //室性异位
          ventricularEctopic: {
            //总数
            total: "-",
            //百分比
            percentage: "-",
            //单发
            singleShot: "-",
            //成对
            pair: "-",
            //室速
            VT: "-",
            //逸搏
            escape: "-",
          },
          supraventricularEctopic: {
            //总数
            total: "-",
            //室上性百分比
            percentage: "-",
            //单发
            singleShot: "-",
            //成对
            pair: "-",
            //室上速
            supraventricularTachycardia: "-",
          },
          asystole: "-"
        },
        {
          time: "-",
          heartBeatNumber: "-",
          heartRate: {
            fastest: "-",
            average: "-",
            slowest: "-"
          },
          //室性异位
          ventricularEctopic: {
            //总数
            total: "-",
            //百分比
            percentage: "-",
            //单发
            singleShot: "-",
            //成对
            pair: "-",
            //室速
            VT: "-",
            //逸搏
            escape: "-",
          },
          supraventricularEctopic: {
            //总数
            total: "-",
            //室上性百分比
            percentage: "-",
            //单发
            singleShot: "-",
            //成对
            pair: "-",
            //室上速
            supraventricularTachycardia: "-",
          },
          asystole: "-"
        },

      ]
    };
  },
  created() {
    console.log("创建")
    var pId = this.$route.query.pId;
    var ecg_type = this.$route.query.ecg_type;
    if (pId) {
      this.ecg_type = ecg_type
      this.pId = pId;
      getReportByPId(this.pId).then(response => {
        console.log(response.data)
        this.froms.textarea.text1 = response.data.diagnosisConclusion
        this.doctorName = response.data.diagnosisDoctor
        this.diagnosisData = response.data.reportTime
        console.log("-------------------------------")
        console.log(this.diagnosisData)
      });
      var show = sessionStorage.getItem(pId + "show");
      if (!show) {
        this.get();

        // this.upload_pdf()

        this.dataTime = response.data.reportTime
      }

    }
  },
  mounted() {
    //this.line();
    //this.line1();
    //this.line2();
    this.line3();
    this.line4();
    this.line5();
    this.line6();
    this.drawscatter();
    this.drawBar();
    this.drawBar1();
    this.drawBar2();
    this.getDate();
  },
  computed() {

  },
  methods: {
    columnStyle({row, column, rowIndex, columnIndex}) {
      return 'padding:0;border:0'
    },
    get() {
      const loading = this.$loading({
        lock: true,//lock的修改符--默认是false
        text: '请勿刷新页面，正在获取数据...',//显示在加载图标下方的加载文案
        spinner: 'el-icon-loading',//自定义加载图标类名
        background: 'rgba(0, 0, 0, 0.7)',//遮罩层颜色
        target: document.querySelector('#table')//loadin覆盖的dom元素节点
      });

      var _th = this
      console.log("执行")
      console.log(this.pId)
      console.log(this.ecg_type)
      console.log(JSON.stringify(_th.ecg_type))
      $.ajax({
        type: "post",
        url: "http://219.155.7.235:5003/get_analysis_decg",
        asynsc: false,
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({
          pId: this.pId,
          ecg_type: this.ecg_type,
        }),

        success: function (data) {
          console.log(_th.pId)
          console.log(data)
          console.log(_th.ecg_type)
          sessionStorage.removeItem("data");
          sessionStorage.setItem(_th.pId + 'data', JSON.stringify(data))
          sessionStorage.setItem(_th.pId + 'show', true)
          loading.close()
          window.location.reload("#pdfDom");
        },
        error: function (data) {
          console.log("数据请求错误，请先点击生成报告。")
          loading.close()
          console.log("错误")
          console.log(_th.pId)
          console.log(_th.ecg_type)
          console.log(data)
        }
      })
    },

    line() {
      var obj = {
        "data": (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.GraphHeartsTime
      };
      var dom = document.getElementById('container');
      var myChart = echarts.init(dom, null, {
        renderer: 'canvas',
        useDirtyRect: false
      });
      var app = {};
      var option;
      // $.get('line2.json', function (data) {
      myChart.setOption(
        (option = {
          title: {
            text: '心率24小时变化图',
            left: '1%'
          },
          tooltip: {
            trigger: 'axis'
          },
          grid: {
            left: '5%',
            right: '15%',
            bottom: '10%'
          },
          xAxis: {
            //show:false,
            // data: data.map(function (item) {
            //   return item[0];
            // })
            // data:obj.data[0]
            data: obj.data.map(function (item) {
              return item[0];
            })
          },
          yAxis: {
            show: false,
            //y轴动态规划
            // scale:true
          },
          // toolbox: {
          //     right: 10,
          //     feature: {
          //         dataZoom: {
          //             yAxisIndex: 'none'
          //         },
          //         restore: {},
          //         saveAsImage: {}
          //     }
          // },
          dataZoom: [
            {
              startValue: 0,
            },
            {
              type: 'inside'
            }
          ],
          visualMap: {
            top: 50,
            right: 10,
            pieces: [
              {
                gt: 0,
                lte: 60,
                color: '#3867d6'
              },
              {
                gt: 60,
                lte: 100,
                color: '#34ace0'
              },
              {
                gt: 100,
                lte: 120,
                color: '#fa8231'
              },
              {
                gt: 120,
                lte: 150,
                color: '#FD0100'
              },
              {
                gt: 150,
                lte: 180,
                color: '#AA069F'
              },

            ],
            outOfRange: {
              color: '#999'
            }
          },
          series: {
            name: '心电24小时',
            type: 'line',
            // smooth:'true',
            data: obj.data.map(function (item) {
              return item[1];
            }),
            // data: data.map(function (item) {
            //   return item[1];
            // }),
            markLine: {
              silent: true,
              lineStyle: {

                color: '#aaa69d',
                width: 1
              },
              label: {
                position: 'start', // 表现内容展示的位置
                color: '#8C8C8C'  // 展示内容颜色
              },

              data: [
                {
                  yAxis: 60
                },
                {
                  yAxis: 100
                },
                {
                  yAxis: 120
                },
                {
                  yAxis: 150
                },
                {
                  yAxis: 180
                }
              ]
            }
          }
        })
      );
      // });

      if (option && typeof option === 'object') {
        myChart.setOption(option);
      }
      window.addEventListener('resize', myChart.resize);
    },
    //5次心搏均值心率
    line1() {
      var obj = {
        "data": (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.GraphHeartsTime_mean_5
      };

      var dom = document.getElementById('line1');
      var myChart = echarts.init(dom, null, {
        renderer: 'canvas',
        useDirtyRect: false
      });
      var app = {};
      var option;
      // $.get('line2.json', function (data) {
      myChart.setOption(
        (option = {
          title: {
            text: '5次心搏均值心率变化图',
            left: '1%'
          },
          tooltip: {
            trigger: 'axis'
          },
          grid: {
            left: '5%',
            right: '15%',
            bottom: '10%'
          },
          xAxis: {
            //show:false,
            // data: data.map(function (item) {
            //   return item[0];
            // })
            // data:obj.data[0]
            data: obj.data.map(function (item) {
              return item[0];
            })
          },
          yAxis: {
            show: false,
            // scale:true
          },
          // toolbox: {
          //     right: 10,
          //     feature: {
          //         dataZoom: {
          //             yAxisIndex: 'none'
          //         },
          //         restore: {},
          //         saveAsImage: {}
          //     }
          // },
          dataZoom: [
            {
              startValue: 0,
            },
            {
              type: 'inside'
            }
          ],
          visualMap: {
            top: 50,
            right: 10,
            pieces: [
              {
                gt: 0,
                lte: 60,
                color: '#3867d6'
              },
              {
                gt: 60,
                lte: 100,
                color: '#34ace0'
              },
              {
                gt: 100,
                lte: 120,
                color: '#fa8231'
              },
              {
                gt: 120,
                lte: 150,
                color: '#FD0100'
              },
            ],
            outOfRange: {
              color: '#AA069F'
            }
          },
          series: {
            name: '5次心搏均值心率',
            type: 'line',
            // smooth:'true',
            data: obj.data.map(function (item) {
              return item[1];
            }),
            // data: data.map(function (item) {
            //   return item[1];
            // }),
            markLine: {
              silent: true,
              lineStyle: {

                color: '#aaa69d',
                width: 1
              },
              label: {
                position: 'start', // 表现内容展示的位置
                color: '#8C8C8C'  // 展示内容颜色
              },

              data: [
                {
                  yAxis: 60
                },
                {
                  yAxis: 100
                },
                {
                  yAxis: 120
                },
                {
                  yAxis: 150
                },
                {
                  yAxis: 180
                }
              ]
            }
          }
        })
      );
      // });

      if (option && typeof option === 'object') {
        myChart.setOption(option);
      }
      window.addEventListener('resize', myChart.resize);
    },
    //10次心搏均值心率
    line2() {
      var obj = {
        "data": (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.GraphHeartsTime_mean_10
      };

      var dom = document.getElementById('line2');
      var myChart = echarts.init(dom, null, {
        renderer: 'canvas',
        useDirtyRect: false
      });
      var app = {};
      var option;
      // $.get('line2.json', function (data) {
      myChart.setOption(
        (option = {
          title: {
            text: '10次心搏均值心率变化图',
            left: '1%'
          },
          tooltip: {
            trigger: 'axis'
          },
          grid: {
            left: '5%',
            right: '15%',
            bottom: '10%'
          },
          xAxis: {
            //show:false,
            // data: data.map(function (item) {
            //   return item[0];
            // })
            // data:obj.data[0]
            data: obj.data.map(function (item) {
              return item[0];
            })
          },
          yAxis: {
            show: false,
            // scale:true
          },
          // toolbox: {
          //     right: 10,
          //     feature: {
          //         dataZoom: {
          //             yAxisIndex: 'none'
          //         },
          //         restore: {},
          //         saveAsImage: {}
          //     }
          // },
          dataZoom: [
            {
              startValue: 0,
            },
            {
              type: 'inside'
            }
          ],
          visualMap: {
            top: 50,
            right: 10,
            pieces: [
              {
                gt: 0,
                lte: 60,
                color: '#3867d6'
              },
              {
                gt: 60,
                lte: 100,
                color: '#34ace0'
              },
              {
                gt: 100,
                lte: 120,
                color: '#fa8231'
              },
              {
                gt: 120,
                lte: 150,
                color: '#FD0100'
              },
            ],
            outOfRange: {
              color: '#AA069F'
            }
          },
          series: {
            name: '10次心搏均值心率',
            type: 'line',
            // smooth:'true',
            data: obj.data.map(function (item) {
              return item[1];
            }),
            // data: data.map(function (item) {
            //   return item[1];
            // }),
            markLine: {
              silent: true,
              lineStyle: {

                color: '#aaa69d',
                width: 1
              },
              label: {
                position: 'start', // 表现内容展示的位置
                color: '#8C8C8C'  // 展示内容颜色
              },

              data: [
                {
                  yAxis: 60
                },
                {
                  yAxis: 100
                },
                {
                  yAxis: 120
                },
                {
                  yAxis: 150
                },
                {
                  yAxis: 180
                }
              ]
            }
          }
        })
      );
      // });

      if (option && typeof option === 'object') {
        myChart.setOption(option);
      }
      window.addEventListener('resize', myChart.resize);
    },
    //0-6
    line3() {
      var obj = {
        "data": (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.GraphHeartsTime
      };

      var dom = document.getElementById('line3');
      var myChart = echarts.init(dom, null, {
        renderer: 'canvas',
        useDirtyRect: false
      });
      var app = {};
      var option;
      var data = obj.data
      var time=data.map(function (item) {
        return item[0];
      })
      var datatime=[];
      for (var i=0;i<time.length;i++)
      {
        datatime.push(parseInt(time[i].slice(0,2))*60+parseInt(time[i].slice(3,5)))
      }
      var temp1;
      var temp2;
      for(i=0;i<datatime.length-1;i++){
        for(var j=0;j<datatime.length- 1 - i;j++){
          if(datatime[j]>datatime[j+1])
          {
            temp1=datatime[j]
            temp2=data[j];
            datatime[j]=datatime[j+1]
            data[j]=data[j+1]
            datatime[j+1]=temp1
            data[j+1]=temp2
          }
        }
      }
      console.log(datatime)
      time=data.map(function (item) {
        return item[0];
      })
      var datax=[];
      var datay=[];
      for (var i=0;i<=6*60;i++){
        datax.push(i)
      }
      var data2=data
      for(i=0;i<data2.length;i++)
      {
        data2[i][0]=datatime[i];
      }
      var option1=[]
      var temp
      for(i=0;i<24;i++)
      {
        if(i<10)
        {
          temp='0'+i+":00"
        }
        else
        {
          temp=i+":00"
        }
        option1.push(
          {
            xAxis: i*60,
            label: {
              position: 'start', // 表现内容展示的位置
              color: '#8C8C8C' , // 展示内容颜色
              formatter: temp
            },
          },
        )
      }
      myChart.setOption(
        (option = {
          title: {
            text: '0-6时心率变化图',
            left: '1%'
          },
          tooltip: {
            trigger: 'axis',

          },
          grid: {
            left: '5%',
            right: '15%',
            bottom: '10%'
          },
          xAxis: {
            //type: 'time',
            show:false,
            // data: data.map(function (item) {
            //   return item[0];
            // })
            // boundaryGap:true,
            // data:timex
            data: datax
          },
          yAxis: {
            show: false,
            // scale:true
            data:datay,
          },
          // toolbox: {
          //     right: 10,
          //     feature: {
          //         dataZoom: {
          //             yAxisIndex: 'none'
          //         },
          //         restore: {},
          //         saveAsImage: {}
          //     }
          // },
          dataZoom: [
            {
              startValue: 0,
            },
            {
              type: 'inside'
            }
          ],
          visualMap: {
            top: 50,
            right: 10,
            pieces: [
              {
                gt: 0,
                lte: 60,
                color: '#3867d6'
              },
              {
                gt: 60,
                lte: 100,
                color: '#34ace0'
              },
              {
                gt: 100,
                lte: 120,
                color: '#fa8231'
              },
              {
                gt: 120,
                lte: 150,
                color: '#FD0100'
              },

            ],
            outOfRange: {
              color: '#AA069F'
            }
          },
          series: {
            name: '0-6时心率变化图',
            type: 'scatter',       //type: scatter表示散点图
            // smooth:'true',
            data: data2,
            // data: data.map(function (item) {
            //   return item[1];
            // }),
            markLine: {
              silent: true,
              symbol: ['none', 'none'],
              lineStyle: {
                color: '#aaa69d',
                width: 1
              },
              label: {
                position: 'start', // 表现内容展示的位置
                color: '#8C8C8C'  // 展示内容颜色
              },
              data:option1,
              // [
              //     {
              //         xAxis: 1,
              //         label: {
              //             position: 'start', // 表现内容展示的位置
              //             color: '#8C8C8C' , // 展示内容颜色
              //             formatter: time[1]
              //         },
              //     },
              //     {
              //         xAxis: 10,
              //         label: {
              //             position: 'start', // 表现内容展示的位置
              //             color: '#8C8C8C' , // 展示内容颜色
              //             formatter: time[10]
              //         },
              //     },
              //     {
              //         xAxis: 40,
              //         label: {
              //             position: 'start', // 表现内容展示的位置
              //             color: '#8C8C8C' , // 展示内容颜色
              //             formatter: time[40]
              //         },
              //     },
              //     {
              //         xAxis: 66,
              //         label: {
              //             position: 'start', // 表现内容展示的位置
              //             color: '#8C8C8C' , // 展示内容颜色
              //             formatter: time[66]
              //         },
              //     },
              //     {
              //         xAxis: 66,
              //         label: {
              //             position: 'start', // 表现内容展示的位置
              //             color: '#8C8C8C' , // 展示内容颜色
              //             formatter: time[66]
              //         },
              //     },
              //     {
              //         xAxis: 99,
              //         label: {
              //             position: 'start', // 表现内容展示的位置
              //             color: '#8C8C8C' , // 展示内容颜色
              //             formatter: time[99]
              //         },
              //     },
              // ]
            }
          }
        })
      );

      if (option && typeof option === 'object') {
        myChart.setOption(option);
      }
      window.addEventListener('resize', myChart.resize);
    },
    //6-12
    line4() {
      var obj = {
        "data": (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.GraphHeartsTime
      };

      var dom = document.getElementById('line4');
      var myChart = echarts.init(dom, null, {
        renderer: 'canvas',
        useDirtyRect: false
      });
      var app = {};
      var option;
      var data = obj.data
      var time=data.map(function (item) {
        return item[0];
      })
      var datatime=[];
      for (var i=0;i<time.length;i++)
      {
        datatime.push(parseInt(time[i].slice(0,2))*60+parseInt(time[i].slice(3,5))-6*60)
      }
      var temp1;
      var temp2;
      for(i=0;i<datatime.length-1;i++){
        for(var j=0;j<datatime.length- 1 - i;j++){
          if(datatime[j]>datatime[j+1])
          {
            temp1=datatime[j]
            temp2=data[j];
            datatime[j]=datatime[j+1]
            data[j]=data[j+1]
            datatime[j+1]=temp1
            data[j+1]=temp2
          }
        }
      }
      console.log(datatime)
      time=data.map(function (item) {
        return item[0];
      })
      var datax=[];
      var datay=[];
      for (var i=6*60;i<=12*60;i++){
        datax.push(i)
      }
      // //   for (var i=0;i<24*60;i++){
      // //     datay.push(undefined)
      // // }
      var data2=data
      for(i=0;i<data2.length;i++)
      {
        data2[i][0]=datatime[i];
      }
      var option1=[]
      var temp
      for(i=6;i<=12;i++)
      {
        if(i<10)
        {
          temp='0'+i+":00"
        }
        else
        {
          temp=i+":00"
        }
        option1.push(
          {
            xAxis: (i-6)*60,
            label: {
              position: 'start', // 表现内容展示的位置
              color: '#8C8C8C' , // 展示内容颜色
              formatter: temp
            },
          },
        )
      }
      myChart.setOption(
        (option = {
          title: {
            text: '6-12时心率变化图',
            left: '1%'
          },
          tooltip: {
            trigger: 'axis',
            // formatter: function (params) {
            //   console.log(params); //通过对象的点语法拿到自己想要的内容,通过连接+,将想要的字符串进行自行的拼接,用<br/>来换行
            //   let res = "";
            //   res = params.name + "<br/>" + "￥" + params.data;
            //   return `<div class="tooltip">${res}</div>`; //最后返回拼接好的值
            // },
          },
          grid: {
            left: '5%',
            right: '15%',
            bottom: '10%'
          },
          xAxis: {
            //type: 'time',
            show:false,
            // data: data.map(function (item) {
            //   return item[0];
            // })
            // boundaryGap:true,
            // data:timex
            data: datax
          },
          yAxis: {
            show: false,
            // scale:true
            data:datay,
          },

          dataZoom: [
            {
              startValue: 0,
            },
            {
              type: 'inside'
            }
          ],
          visualMap: {
            top: 50,
            right: 10,
            pieces: [
              {
                gt: 0,
                lte: 60,
                color: '#3867d6'
              },
              {
                gt: 60,
                lte: 100,
                color: '#34ace0'
              },
              {
                gt: 100,
                lte: 120,
                color: '#fa8231'
              },
              {
                gt: 120,
                lte: 150,
                color: '#FD0100'
              },

            ],
            outOfRange: {
              color: '#AA069F'
            }
          },
          series: {
            name: '6-12时心率变化图',
            type: 'scatter',       //type: scatter表示散点图
            // smooth:'true',
            data: data2,
            // data: data.map(function (item) {
            //   return item[1];
            // }),
            markLine: {
              silent: true,
              symbol: ['none', 'none'],
              lineStyle: {
                color: '#aaa69d',
                width: 1
              },
              label: {
                position: 'start', // 表现内容展示的位置
                color: '#8C8C8C'  // 展示内容颜色
              },
              data:option1,
              // [
              //     {
              //         xAxis: 1,
              //         label: {
              //             position: 'start', // 表现内容展示的位置
              //             color: '#8C8C8C' , // 展示内容颜色
              //             formatter: time[1]
              //         },
              //     },
              //     {
              //         xAxis: 10,
              //         label: {
              //             position: 'start', // 表现内容展示的位置
              //             color: '#8C8C8C' , // 展示内容颜色
              //             formatter: time[10]
              //         },
              //     },
              //     {
              //         xAxis: 40,
              //         label: {
              //             position: 'start', // 表现内容展示的位置
              //             color: '#8C8C8C' , // 展示内容颜色
              //             formatter: time[40]
              //         },
              //     },
              //     {
              //         xAxis: 66,
              //         label: {
              //             position: 'start', // 表现内容展示的位置
              //             color: '#8C8C8C' , // 展示内容颜色
              //             formatter: time[66]
              //         },
              //     },
              //     {
              //         xAxis: 66,
              //         label: {
              //             position: 'start', // 表现内容展示的位置
              //             color: '#8C8C8C' , // 展示内容颜色
              //             formatter: time[66]
              //         },
              //     },
              //     {
              //         xAxis: 99,
              //         label: {
              //             position: 'start', // 表现内容展示的位置
              //             color: '#8C8C8C' , // 展示内容颜色
              //             formatter: time[99]
              //         },
              //     },
              // ]
            }
          }
        })
      );

      if (option && typeof option === 'object') {
        myChart.setOption(option);
      }
      window.addEventListener('resize', myChart.resize);
    },
    //12-18
    line5() {
      var obj = {
        "data": (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.GraphHeartsTime
      };

      var dom = document.getElementById('line5');
      var myChart = echarts.init(dom, null, {
        renderer: 'canvas',
        useDirtyRect: false
      });
      var app = {};
      var option;
      var data = obj.data
      var time=data.map(function (item) {
        return item[0];
      })
      var datatime=[];
      for (var i=0;i<time.length;i++)
      {
        datatime.push(parseInt(time[i].slice(0,2))*60+parseInt(time[i].slice(3,5))-12*60)
      }
      var temp1;
      var temp2;
      for(i=0;i<datatime.length-1;i++){
        for(var j=0;j<datatime.length- 1 - i;j++){
          if(datatime[j]>datatime[j+1])
          {
            temp1=datatime[j]
            temp2=data[j];
            datatime[j]=datatime[j+1]
            data[j]=data[j+1]
            datatime[j+1]=temp1
            data[j+1]=temp2
          }
        }
      }
      console.log(datatime)
      time=data.map(function (item) {
        return item[0];
      })
      var datax=[];
      var datay=[];
      for (var i=12*60;i<=18*60;i++){
        datax.push(i)
      }
      // //   for (var i=0;i<24*60;i++){
      // //     datay.push(undefined)
      // // }
      var data2=data
      for(i=0;i<data2.length;i++)
      {
        data2[i][0]=datatime[i];
      }
      var option1=[]
      var temp
      for(i=12;i<=18;i++)
      {
        if(i<10)
        {
          temp='0'+i+":00"
        }
        else
        {
          temp=i+":00"
        }
        option1.push(
          {
            xAxis: (i-12)*60,
            label: {
              position: 'start', // 表现内容展示的位置
              color: '#8C8C8C' , // 展示内容颜色
              formatter: temp
            },
          },
        )
      }
      myChart.setOption(
        (option = {
          title: {
            text: '12-18时心率变化图',
            left: '1%'
          },
          tooltip: {
            trigger: 'axis',
          },
          grid: {
            left: '5%',
            right: '15%',
            bottom: '10%'
          },
          xAxis: {
            //type: 'time',
            show:false,
            // data: data.map(function (item) {
            //   return item[0];
            // })
            // boundaryGap:true,
            // data:timex
            data: datax
          },
          yAxis: {
            show: false,
            // scale:true
            data:datay,
          },

          dataZoom: [
            {
              startValue: 0,
            },
            {
              type: 'inside'
            }
          ],
          visualMap: {
            top: 50,
            right: 10,
            pieces: [
              {
                gt: 0,
                lte: 60,
                color: '#3867d6'
              },
              {
                gt: 60,
                lte: 100,
                color: '#34ace0'
              },
              {
                gt: 100,
                lte: 120,
                color: '#fa8231'
              },
              {
                gt: 120,
                lte: 150,
                color: '#FD0100'
              },

            ],
            outOfRange: {
              color: '#AA069F'
            }
          },
          series: {
            name: '12-18时心率变化图',
            type: 'scatter',       //type: scatter表示散点图
            // smooth:'true',
            data: data2,
            // data: data.map(function (item) {
            //   return item[1];
            // }),
            markLine: {
              silent: true,
              symbol: ['none', 'none'],
              lineStyle: {
                color: '#aaa69d',
                width: 1
              },
              label: {
                position: 'start', // 表现内容展示的位置
                color: '#8C8C8C'  // 展示内容颜色
              },
              data:option1,
              // [
              //     {
              //         xAxis: 1,
              //         label: {
              //             position: 'start', // 表现内容展示的位置
              //             color: '#8C8C8C' , // 展示内容颜色
              //             formatter: time[1]
              //         },
              //     },
              //     {
              //         xAxis: 10,
              //         label: {
              //             position: 'start', // 表现内容展示的位置
              //             color: '#8C8C8C' , // 展示内容颜色
              //             formatter: time[10]
              //         },
              //     },
              //     {
              //         xAxis: 40,
              //         label: {
              //             position: 'start', // 表现内容展示的位置
              //             color: '#8C8C8C' , // 展示内容颜色
              //             formatter: time[40]
              //         },
              //     },
              //     {
              //         xAxis: 66,
              //         label: {
              //             position: 'start', // 表现内容展示的位置
              //             color: '#8C8C8C' , // 展示内容颜色
              //             formatter: time[66]
              //         },
              //     },
              //     {
              //         xAxis: 66,
              //         label: {
              //             position: 'start', // 表现内容展示的位置
              //             color: '#8C8C8C' , // 展示内容颜色
              //             formatter: time[66]
              //         },
              //     },
              //     {
              //         xAxis: 99,
              //         label: {
              //             position: 'start', // 表现内容展示的位置
              //             color: '#8C8C8C' , // 展示内容颜色
              //             formatter: time[99]
              //         },
              //     },
              // ]
            }
          }
        })
      );

      if (option && typeof option === 'object') {
        myChart.setOption(option);
      }
      window.addEventListener('resize', myChart.resize);
    },
    //18-24
    line6(){
      var obj = {
        "data": (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.GraphHeartsTime
      };

      var dom = document.getElementById('line6');
      var myChart = echarts.init(dom, null, {
        renderer: 'canvas',
        useDirtyRect: false
      });
      var app = {};
      var option;
      var data = obj.data
      var time=data.map(function (item) {
        return item[0];
      })
      var datatime=[];
      for (var i=0;i<time.length;i++)
      {
        datatime.push(parseInt(time[i].slice(0,2))*60+parseInt(time[i].slice(3,5))-18*60)
      }
      var temp1;
      var temp2;
      for(i=0;i<datatime.length-1;i++){
        for(var j=0;j<datatime.length- 1 - i;j++){
          if(datatime[j]>datatime[j+1])
          {
            temp1=datatime[j]
            temp2=data[j];
            datatime[j]=datatime[j+1]
            data[j]=data[j+1]
            datatime[j+1]=temp1
            data[j+1]=temp2
          }
        }
      }
      console.log(datatime)
      time=data.map(function (item) {
        return item[0];
      })
      var datax=[];
      var datay=[];
      for (var i=18*60;i<=24*60;i++){
        datax.push(i)
      }
      // //   for (var i=0;i<24*60;i++){
      // //     datay.push(undefined)
      // // }
      var data2=data
      for(i=0;i<data2.length;i++)
      {
        data2[i][0]=datatime[i];
      }
      var option1=[]
      var temp
      for(i=18;i<=24;i++)
      {
        if(i<10)
        {
          temp='0'+i+":00"
        }
        else
        {
          temp=i+":00"
        }
        option1.push(
          {
            xAxis: (i-18)*60,
            label: {
              position: 'start', // 表现内容展示的位置
              color: '#8C8C8C' , // 展示内容颜色
              formatter: temp
            },
          },
        )
      }
      myChart.setOption(
        (option = {
          title: {
            text: '18-24时心率变化图',
            left: '1%'
          },
          tooltip: {
            trigger: 'axis',
          },
          grid: {
            left: '5%',
            right: '15%',
            bottom: '10%'
          },
          xAxis: {
            //type: 'time',
            show:false,
            // data: data.map(function (item) {
            //   return item[0];
            // })
            // boundaryGap:true,
            // data:timex
            data: datax
          },
          yAxis: {
            show: false,
            // scale:true
            data:datay,
          },

          dataZoom: [
            {
              startValue: 0,
            },
            {
              type: 'inside'
            }
          ],
          visualMap: {
            top: 50,
            right: 10,
            pieces: [
              {
                gt: 0,
                lte: 60,
                color: '#3867d6'
              },
              {
                gt: 60,
                lte: 100,
                color: '#34ace0'
              },
              {
                gt: 100,
                lte: 120,
                color: '#fa8231'
              },
              {
                gt: 120,
                lte: 150,
                color: '#FD0100'
              },

            ],
            outOfRange: {
              color: '#AA069F'
            }
          },
          series: {
            name: '18-24时心率变化图',
            type: 'scatter',       //type: scatter表示散点图
            // smooth:'true',
            data: data2,
            // data: data.map(function (item) {
            //   return item[1];
            // }),
            markLine: {
              silent: true,
              symbol: ['none', 'none'],
              lineStyle: {
                color: '#aaa69d',
                width: 1
              },
              label: {
                position: 'start', // 表现内容展示的位置
                color: '#8C8C8C'  // 展示内容颜色
              },
              data:option1,
              // [
              //     {
              //         xAxis: 1,
              //         label: {
              //             position: 'start', // 表现内容展示的位置
              //             color: '#8C8C8C' , // 展示内容颜色
              //             formatter: time[1]
              //         },
              //     },
              //     {
              //         xAxis: 10,
              //         label: {
              //             position: 'start', // 表现内容展示的位置
              //             color: '#8C8C8C' , // 展示内容颜色
              //             formatter: time[10]
              //         },
              //     },
              //     {
              //         xAxis: 40,
              //         label: {
              //             position: 'start', // 表现内容展示的位置
              //             color: '#8C8C8C' , // 展示内容颜色
              //             formatter: time[40]
              //         },
              //     },
              //     {
              //         xAxis: 66,
              //         label: {
              //             position: 'start', // 表现内容展示的位置
              //             color: '#8C8C8C' , // 展示内容颜色
              //             formatter: time[66]
              //         },
              //     },
              //     {
              //         xAxis: 66,
              //         label: {
              //             position: 'start', // 表现内容展示的位置
              //             color: '#8C8C8C' , // 展示内容颜色
              //             formatter: time[66]
              //         },
              //     },
              //     {
              //         xAxis: 99,
              //         label: {
              //             position: 'start', // 表现内容展示的位置
              //             color: '#8C8C8C' , // 展示内容颜色
              //             formatter: time[99]
              //         },
              //     },
              // ]
            }
          }
        })
      );

      if (option && typeof option === 'object') {
        myChart.setOption(option);
      }
      window.addEventListener('resize', myChart.resize);
    },

    //散点图
    drawscatter() {
      var obj = {
        "data": (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.GraphLorenz
      }
      var dom = document.getElementById('scatter');
      var myChart = echarts.init(dom, null, {
        renderer: 'canvas',
        useDirtyRect: false
      });
      var app = {};

      var option;

      option = {
        title: {
          text: 'Lorenz散点图-全部心搏',
          left: '1%',
        },
        grid: {left: '18%',},
        xAxis: {},
        yAxis: {},
        series: [
          {
            symbolSize: 2,
            data: obj.data,
            name: "Lorenz-散点图 全部心搏",
            type: 'scatter'

          }
        ]
      };

      if (option && typeof option === 'object') {
        myChart.setOption(option);
      }

      window.addEventListener('resize', myChart.resize);
    },

    drawBar() {
      var dom = document.getElementById('bar');
      var myChart = echarts.init(dom, null, {
        renderer: 'canvas',
        useDirtyRect: false
      });
      var app = {};

      var option;

      option = {
        title: {
          text: '心率直方图',
          left: '1%',
        },
        grid: {left: '18%',},
        tooltip: {
          trigger: 'axis'
        },
        xAxis: {
          type: 'category',
          data: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.GraphHeartRatesFrequency

        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.GraphHeartRatesFrequency_Count,
            type: 'bar'
          }
        ]
      };

      if (option && typeof option === 'object') {
        myChart.setOption(option);
      }

      window.addEventListener('resize', myChart.resize);
    },
    //NN间期直方图
    drawBar1() {
      var dom = document.getElementById('bar1');
      var myChart = echarts.init(dom, null, {
        renderer: 'canvas',
        useDirtyRect: false
      });
      var app = {};

      var option;

      option = {
        title: {
          text: 'NN间期直方图',
          left: '1%',
        },
        grid: {left: '18%',},
        tooltip: {
          trigger: 'axis'
        },
        xAxis: {
          type: 'category',
          data: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.GraphNNinterFrequency,
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.GraphNNinterFrequency_Count,
            type: 'bar'
          }
        ]
      };

      if (option && typeof option === 'object') {
        myChart.setOption(option);
      }

      window.addEventListener('resize', myChart.resize);
    },
//NN间期差值直方图
    drawBar2() {
      var dom = document.getElementById('bar2');
      var myChart = echarts.init(dom, null, {
        renderer: 'canvas',
        useDirtyRect: false
      });
      var app = {};

      var option;

      option = {
        title: {
          text: 'NN间期差值直方图',
          left: '1%',
        },
        grid: {left: '18%',},
        tooltip: {
          trigger: 'axis'
        },
        xAxis: {
          type: 'category',
          data: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.GraphNNinterSubFrequency

        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: (JSON.parse(sessionStorage.getItem(this.$route.query.pId + "data"))).result.GraphNNinterSubFrequency_Count,
            type: 'bar'
          }
        ]
      };

      if (option && typeof option === 'object') {
        myChart.setOption(option);
      }

      window.addEventListener('resize', myChart.resize);
    },
    btnClear() {
      sessionStorage.clear();
    },
    btnClearSelf() {
      sessionStorage.removeItem(this.$route.query.pId + 'data');
      sessionStorage.removeItem(this.$route.query.pId + 'show');
      window.location.reload();
    },
    btnClick() {
      var _self = this
      // 当下载pdf时，若不在页面顶部会造成PDF样式不对,所以先回到页面顶部再下载
      let top = document.getElementById("pdfDom");
      if (top != null) {
        top.scrollIntoView();
        top = null;
      }
      let title = this.exportPDFtitle;
      html2Canvas(document.querySelector("#pdfDom"), {
        //清晰度，越大越好
        scale: 2,
        allowTaint: true
      }).then(function (canvas) {
        // 获取canvas画布的宽高
        let contentWidth = canvas.width;
        let contentHeight = canvas.height;
        // 一页pdf显示html页面生成的canvas高度;
        let pageHeight = (contentWidth / 592.28) * 841.89;
        // 未生成pdf的html页面高度
        let leftHeight = contentHeight;
        // 页面偏移
        let position = 0;
        // html页面生成的canvas在pdf中图片的宽高（本例为：横向a4纸[841.89,592.28]，纵向需调换尺寸）
        let imgWidth = 592.28;
        let imgHeight = (592.28 / contentWidth) * contentHeight;
        let pageData = canvas.toDataURL("image/jpeg", 1.0);
        let PDF = new JsPDF("", "pt", "a4");
        // 两个高度需要区分: 一个是html页面的实际高度，和生成pdf的页面高度
        // 当内容未超过pdf一页显示的范围，无需分页
        if (leftHeight < pageHeight) {
          PDF.addImage(pageData, "JPEG", 0, 0, imgWidth, imgHeight);
        } else {
          while (leftHeight > 0) {
            PDF.addImage(pageData, "JPEG", 0, position, imgWidth, imgHeight);
            leftHeight -= pageHeight;
            position -= 841.89;
            // 避免添加空白页
            if (leftHeight > 0) {
              PDF.addPage();
            }
          }
        }
        PDF.save(title + ".pdf");
/*        // 将pdf输入为base格式的字符串
        var buffer = PDF.output("datauristring")
        // 将base64格式的字符串转换为file文件
        var myfile = _self.dataURLtoFile(buffer, title + ".pdf")
        name = _self.upload_pdf(myfile)*/


      });
      sessionStorage.removeItem(this.$route.query.pId + 'data');
      sessionStorage.removeItem(this.$route.query.pId + 'show');
    },

    btnClickPDF() {
      var _self = this
      // 当下载pdf时，若不在页面顶部会造成PDF样式不对,所以先回到页面顶部再下载
      let top = document.getElementById("pdfDom");
      if (top != null) {
        top.scrollIntoView();
        top = null;
      }
      let title = this.exportPDFtitle;
      html2Canvas(document.querySelector("#pdfDom"), {
        //清晰度，越大越好
        scale: 2,
        allowTaint: true
      }).then(function (canvas) {
        // 获取canvas画布的宽高
        let contentWidth = canvas.width;
        let contentHeight = canvas.height;
        // 一页pdf显示html页面生成的canvas高度;
        let pageHeight = (contentWidth / 592.28) * 841.89;
        // 未生成pdf的html页面高度
        let leftHeight = contentHeight;
        // 页面偏移
        let position = 0;
        // html页面生成的canvas在pdf中图片的宽高（本例为：横向a4纸[841.89,592.28]，纵向需调换尺寸）
        let imgWidth = 592.28;
        let imgHeight = (592.28 / contentWidth) * contentHeight;
        let pageData = canvas.toDataURL("image/jpeg", 1.0);
        let PDF = new JsPDF("", "pt", "a4");
        // 两个高度需要区分: 一个是html页面的实际高度，和生成pdf的页面高度
        // 当内容未超过pdf一页显示的范围，无需分页
        if (leftHeight < pageHeight) {
          PDF.addImage(pageData, "JPEG", 0, 0, imgWidth, imgHeight);
        } else {
          while (leftHeight > 0) {
            PDF.addImage(pageData, "JPEG", 0, position, imgWidth, imgHeight);
            leftHeight -= pageHeight;
            position -= 841.89;
            // 避免添加空白页
            if (leftHeight > 0) {
              PDF.addPage();
            }
          }
        }
        // PDF.save(title + ".pdf");
        // 将pdf输入为base格式的字符串
        var buffer = PDF.output("datauristring")
        // 将base64格式的字符串转换为file文件
        var myfile = _self.dataURLtoFile(buffer, title + ".pdf")
        name = _self.upload_pdf(myfile)

      });
      sessionStorage.removeItem(this.$route.query.pId + 'data');
      sessionStorage.removeItem(this.$route.query.pId + 'show');
    },

    //上传pdf
    upload_pdf(file) {
      // var url ='';

      var _self = this
      // 当下载pdf时，若不在页面顶部会造成PDF样式不对,所以先回到页面顶部再下载
      let top = document.getElementById("pdfDom");
      if (top != null) {
        top.scrollIntoView();
        top = null;
      }
      let title = this.exportPDFtitle;
      html2Canvas(document.querySelector("#pdfDom"), {
        //清晰度，越大越好
        scale: 2,
        allowTaint: true
      }).then(function (canvas) {
        // 获取canvas画布的宽高
        let contentWidth = canvas.width;
        let contentHeight = canvas.height;
        // 一页pdf显示html页面生成的canvas高度;
        let pageHeight = (contentWidth / 592.28) * 841.89;
        // 未生成pdf的html页面高度
        let leftHeight = contentHeight;
        // 页面偏移
        let position = 0;
        // html页面生成的canvas在pdf中图片的宽高（本例为：横向a4纸[841.89,592.28]，纵向需调换尺寸）
        let imgWidth = 592.28;
        let imgHeight = (592.28 / contentWidth) * contentHeight;
        let pageData = canvas.toDataURL("image/jpeg", 1.0);
        let PDF = new JsPDF("", "pt", "a4");
        // 两个高度需要区分: 一个是html页面的实际高度，和生成pdf的页面高度
        // 当内容未超过pdf一页显示的范围，无需分页
        if (leftHeight < pageHeight) {
          PDF.addImage(pageData, "JPEG", 0, 0, imgWidth, imgHeight);
        } else {
          while (leftHeight > 0) {
            PDF.addImage(pageData, "JPEG", 0, position, imgWidth, imgHeight);
            leftHeight -= pageHeight;
            position -= 841.89;
            // 避免添加空白页
            if (leftHeight > 0) {
              PDF.addPage();
            }
          }
        }
        //PDF.save(title + ".pdf");
        // 将pdf输入为base格式的字符串
        var buffer = PDF.output("datauristring")
        // 将base64格式的字符串转换为file文件
        var myfile = _self.dataURLtoFile(buffer, title + ".pdf")
        //name = _self.upload_pdf(myfile)
        var formdata = new FormData()
        formdata.append("file", myfile); // 文件对象
        console.log("上传pdf-1")
        //多个参数的情况
        // formdata.append("name", name);
        var msg = '';
        // 之后ajax传递数据
        pdfDownload2(formdata).then(res => {
          console.log(res);
          if (res.code === 200) {
            this.$modal.msgSuccess("上传成功");
          } else {
            this.$modal.msgError("上传失败");
          }
        })

      });

    },
    //将base64转换为文件对象
    dataURLtoFile(dataurl, filename) {
      var arr = dataurl.split(',');
      var mime = arr[0].match(/:(.*?);/)[1];
      var bstr = atob(arr[1]);
      var n = bstr.length;
      var u8arr = new Uint8Array(n);
      while (n--) {
        u8arr[n] = bstr.charCodeAt(n);
      }
      //转换成file对象
      return new File([u8arr], filename, {type: mime});
      //转换成成blob对象
      //return new Blob([u8arr],{type:mime});
    },

    getDate() {
      var str = new Date();
      console.log("++++++++++++++")
      console.log(this.diagnosisData)
      if (this.diagnosisData != null) {
        this.dataTime = this.diagnosisData
      } else {
        this.dataTime = str.getFullYear() + "-"
          + (str.getMonth() + 1) + "-" + str.getDate();

        console.log("---------------------")
        console.log(this.dataTime);
      }

    },

    //保存结果
    btnUpload() {
      var form = {
        pId: this.pId,
        diagnosisStatus: '未确定',
        reportType: this.ecg_type,
        diagnosisConclusion: this.froms.textarea.text1,
        reportTime: this.dataTime,
        diagnosisDoctor: this.doctorName,
      }

      getReportByPId(this.pId).then(res => {

        if (res.data == null) {
          addReport(form).then(response => {
            this.$modal.msgSuccess("新增成功");
            this.getList();
            console.log("新增成功！")
          });
        } else {
          form["reportId"] = res.data.reportId
          console.log(form)
          updateReport(form).then(response => {
            this.$modal.msgSuccess("修改成功");
            this.getList();
            console.log("修改成功！")
          })
        }
      })
    }

  },
}
</script>

<style lang="scss" scoped>
* {
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

.page {
  padding-top: 50px;
  padding-bottom: 50px;
  margin-top: 5px;
  width: 794px;
  //height: 1127px;
  //border: 1px solid #0000ff;
  height: 1124px;
}

.main .time {
  font-size: 6px;
  margin-top: 10px;
  margin-left: 65%;
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
  margin: 0 auto 0;
  border: 1px solid black;
  font-size: 13px;
}

.box1-top {
  background-color: rgb(240, 240, 240);
  border-bottom: 1px solid black;
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
  height: 200px;
  margin: 0 auto;
  border: 1px solid black;
  font-size: 13px;
}

.box2-top {
  width: 100%;

}

.box2-right {
  width: 100%;
  border-left: 1px solid black;
}

.box2-title h2 {
  text-align: left;
  padding-left: 2vw;
  background-color: rgb(240, 240, 240);
  border-bottom: 1px solid black;
}

.box2-bottom {
  border-top: 1px solid black;
}

.box2-left-bottom,
.box2-1-outer {
  padding-left: 2vw;
  display: flex;
}

.box2-right-bottom {
  flex: 3;
}

.box2-left-bottom .box2-1 {
  flex: 4
}

.box3 {
  width: 90%;
  height: 20px;
  margin: 0 auto;
  padding: 1px 0;
  border: 1px solid black;
  font-size: 13px;
}

.box3 span {
  font-weight: 700;
}

.box4 {
  width: 90%;
  height: 200px;
  margin: 0 auto;
  border: 1px solid black;
  font-size: 13px;
}

.box4-left {
  float: left;
  width: 50%;
  border-right: 1px solid black;
}

.box4-right {
  float: right;
  width: 50%;
  /* border-left: 3px solid black; */
}

.box4-top h2 {
  text-align: left;
  padding-left: 2vw;
  background-color: rgb(240, 240, 240);
  border-bottom: 1px solid black;
}

.box4-left-bottom,
.box4-right-bottom {
  padding-left: 2vw;
}

.box5 {
  width: 90%;
  height: 43px;
  margin: 0 auto;
  padding: 2px 5px;
  border: 1px solid black;
  font-size: 13px;
}

.box5 h2 {
  text-align: left;
}

.box5 div {
  display: flex;
}

.box5 span {
  flex: 5;
}

.box6 {
  width: 90%;
  height: 60px;
  margin: 0 auto;
  padding: 2px 5px;
  border: 1px solid black;
  font-size: 13px;
}

.box6-top {
  padding-top: 5px;
  padding-bottom: 5px;
  height: 35%;
  display: flex;
  align-items: center;
}

.box6-top h2,
.box6-top span {
  float: left;
}

.box6-1 {
  display: flex;
}

.box6-1 div {
  flex: 20%;
}

.box7 {
  width: 90%;
  height: 60px;
  margin: 0 auto;
  padding: 5px 5px;
  border: 1px solid black;
  font-size: 13px;
}

.box7-top {
  height: 35%;
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

.box7-1 {
  display: flex;
}

.box7-1 div {
  flex: 4;
}

.box8 {
  width: 90%;
  height: 260px;
  margin: 0 auto;
  padding: 5px 15px;
  border: 1px solid black;
  font-size: 13px;
}

.box8 h2 {
  text-align: left;
}

.box8-1 {
  margin: 5px 0;
}

.box8-1-1 {
  width: 682px;
  height: 120px;
  border: none;
  resize: none;
  cursor: pointer;
  //outline:none;
  font-size: 13px;
  font-family: "Helvetica Neue";
  word-wrap: break-word;
}

.box8 .bottom {
  position: relative;
  top: 0;
  bottom: 0px;
  right: 3px;
  margin-top: 3px;
  margin-bottom: 1px;
  text-align: right;

}

.box8-2 {
  display: inline-block;
  width: 100px;
  height: 20px;
  border: none;
  border-bottom: 1px solid black;
  margin-right: 10px;
  outline: none
}

.box9 {
  width: 90%;
  margin: 0 auto;
  padding: 5px 15px;
  border: 1px solid black;
  font-size: 13px;
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
  border: 1px solid black;
  font-size: 13px;
}

.box11 {
  width: 90%;
  margin: 0 auto;
  /*padding: 5px 15px;*/
  border: 1px solid black;
  /*font-size: 13px;*/
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
  border: 1px solid black;
  font-size: 13px;
}

.box13 {
  width: 90%;
  height: 350px;
  margin: 0 auto;
  padding: 5px 10px;
  border: 1px solid black;
  font-size: 13px;
}

.box13-left {
  float: left;
  width: 50%;
  height: 100%;
}

.box13-right {
  float: right;
  width: 50%;
  height: 100%;
  border-left: 1px solid black;
}

.flex-box {
  display: flex;
}

.flex-box-child {
  flex: 2;
}

::v-deep el-table-column {
  background: #00afff;
  padding: 0;
}

</style>
