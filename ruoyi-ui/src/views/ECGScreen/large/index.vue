<template>
  <div class="home" id="home" >
    <div class="title">
      <div class="button">
        <!--          <el-button size="mini" >院区数据</el-button>-->
        <el-button size="mini" @click="inScreen" plain>全屏切换</el-button>
        <!--          <el-button size="mini" @click="inScreen" plain>退出全屏</el-button>-->
      </div>
      <span >动态心电智能实时监控</span>
      <el-pagination
        @current-change="handleCurrentChange"
        layout="prev, pager, next"
        :total="1000">
      </el-pagination>
    </div>
    <!-- <div class="name">所属医院:<span>{{data0.hospName && data0.hospName.includes('*') ? data0.hospName : '*'.repeat(Math.max(data0.hospName ? data0.hospName.length - 2 : 0, 0)) + (data0.hospName ? data0.hospName.slice(-2) : '')}}</span></div> -->
    <div class="mainbox" >
      <div class="container">
        <div class="event" v-if="data0!=null&&JSON.stringify(data0)!='{}'">
          <div class="son">心肌梗塞<span id="span0-1" :style="dotColor(data0.GengSi)"></span></div>
          <div class="son">房颤房扑<span id="span0-2" :style="dotColor(data0.FangChan)"></span></div>
          <div class="son">心室率过低<span id="span0-3" :style="dotColor(data0.XSL_pr)"></span></div>
          <div class="son">室性心动过速<span id="span0-5"></span></div>
          <div class="son">室颤室扑<span id="span0-6" :style="dotColor(data0.ShiChan)"></span></div>
          <div class="son">窦室传导<span id="span0-7"></span></div>
          <div class="son">长RR间期<span id="span0-4" :style="dotColor(data0.RRGC_pr)"></span></div>
          <div class="son">QTc间期过长<span id="span0-8"></span></div>
        </div>
        <div class="containcontent">
          <div class="chart chartEvent" ref="chart" id="child_0"></div>
          <!--          <div class="chart" id="chart_0"></div>-->
          <div class="infleft" @click="jump(data0.deviceSn)">
            <div class="name">心率:</div>
            <div class="hart" ><span :style="color(data0.hr_mean)">{{data0.hr_mean}}</span></div>
          </div>
        </div>
        <div class="containMsg" @click="jump(data0.deviceSn)" >
          <div class="name effect">姓名:<span>{{data0.patientName}}</span></div>
          <div class="name" >性别:<span>{{data0.gender}}</span></div>
          <div class="name" >年龄:<span>{{data0.age}}</span></div>
          <div class="name">所属医院:<span>{{data0.hospName && data0.hospName.includes('*') ? data0.hospName : '*'.repeat(Math.max(data0.hospName ? data0.hospName.length - 2 : 0, 0)) + (data0.hospName ? data0.hospName.slice(-2) : '')}}</span></div>
          <!-- <div class="name" >所属医院:<span>{{data0.hospName}}</span></div> -->
          <div class="name" >设备号:<span>{{data0.deviceSn}}</span></div>
        </div>
        <div class="panel-footer"></div>
      </div>

      <div class="container">
        <div class="event" v-if="data1!=null&&JSON.stringify(data1)!='{}'">
          <div class="son">心肌梗塞<span id="span1-1" :style="dotColor(data1.GengSi)"></span></div>
          <div class="son">房颤房扑<span id="span1-2" :style="dotColor(data1.FangChan)"></span></div>
          <div class="son">心室率过低<span id="span1-3" :style="dotColor(data1.XSL_pr)"></span></div>
          <div class="son">室性心动过速<span id="span1-5"></span></div>
          <div class="son">室颤室扑<span id="span1-6" :style="dotColor(data1.ShiChan)"></span></div>
          <div class="son">窦室传导<span id="span1-7"></span></div>
          <div class="son">长RR间期<span id="span1-4" :style="dotColor(data1.RRGC_pr)"></span></div>
          <div class="son">QTc间期过长<span id="span1-8"></span></div>
        </div>
        <div class="containcontent">
          <div class="chart chartEvent" ref="chart" id="child_1"></div>
          <div class="infleft" @click="jump(data1.deviceSn)">
            <div class="name">心率:</div>
            <div class="hart" ><span :style="color(data1.hr_mean)">{{data1.hr_mean}}</span></div>
          </div>
        </div>
        <div class="containMsg" @click="jump(data1.deviceSn)" >
          <div class="name effect">姓名:<span>{{data1.patientName}}</span></div>
          <div class="name" >性别:<span>{{data1.gender}}</span></div>
          <div class="name" >年龄:<span>{{data1.age}}</span></div>
          <div class="name" >所属医院:<span>{{data1.hospName && data1.hospName.includes('*') ? data1.hospName : '*'.repeat(Math.max(data1.hospName ? data1.hospName.length - 2 : 0, 0)) + (data1.hospName ? data1.hospName.slice(-2) : '')}}</span></div>
          <div class="name" >设备号:<span>{{data1.deviceSn}}</span></div>
        </div>
        <div class="panel-footer"></div>
      </div>

      <div class="container"  >
        <div class="event" v-if="data2!=null&&JSON.stringify(data2)!='{}'">
          <div class="son">心肌梗塞<span id="span2-1" :style="dotColor(data2.GengSi)"></span></div>
          <div class="son">房颤房扑<span id="span2-2" :style="dotColor(data2.FangChan)"></span></div>
          <div class="son">心室率过低<span id="span2-3" :style="dotColor(data2.XSL_pr)"></span></div>
          <div class="son">室性心动过速<span id="span2-5"></span></div>
          <div class="son">室颤室扑<span id="span2-6" :style="dotColor(data2.ShiChan)"></span></div>
          <div class="son">窦室传导<span id="span2-7"></span></div>
          <div class="son">长RR间期<span id="span2-4" :style="dotColor(data2.RRGC_pr)"></span></div>
          <div class="son">QTc间期过长<span id="span2-8"></span></div>
        </div>
        <div class="containcontent">
          <div class="chart chartEvent" ref="chart" id="child_2"></div>
          <!--          <div class="chart" id="chart_0"></div>-->
          <div class="infleft" @click="jump(data2.deviceSn)">
            <div class="name">心率:</div>
            <div class="hart" ><span :style="color(data2.hr_mean)">{{data2.hr_mean}}</span></div>
          </div>
        </div>
        <div class="containMsg" @click="jump(data2.deviceSn)" >
          <div class="name effect">姓名:<span>{{data2.patientName}}</span></div>
          <div class="name" >性别:<span>{{data2.gender}}</span></div>
          <div class="name" >年龄:<span>{{data2.age}}</span></div>
          <div class="name" >所属医院:<span>{{data2.hospName && data2.hospName.includes('*') ? data2.hospName : '*'.repeat(Math.max(data2.hospName ? data2.hospName.length - 2 : 0, 0)) + (data2.hospName ? data2.hospName.slice(-2) : '')}}</span></div>
          <div class="name" >设备号:<span>{{data2.deviceSn}}</span></div>
        </div>
        <div class="panel-footer"></div>
      </div>

      <div class="container"  >
        <div class="event"  v-if="data3!=null&&JSON.stringify(data3)!='{}'">
          <div class="son">心肌梗塞<span id="span3-1" :style="dotColor(data3.GengSi)"></span></div>
          <div class="son">房颤房扑<span id="span3-2" :style="dotColor(data3.FangChan)"></span></div>
          <div class="son">心室率过低<span id="span3-3" :style="dotColor(data3.XSL_pr)"></span></div>
          <div class="son">室性心动过速<span id="span3-5"></span></div>
          <div class="son">室颤室扑<span id="span3-6" :style="dotColor(data3.ShiChan)"></span></div>
          <div class="son">窦室传导<span id="span3-7"></span></div>
          <div class="son">长RR间期<span id="span3-4" :style="dotColor(data3.RRGC_pr)"></span></div>
          <div class="son">QTc间期过长<span id="span3-8"></span></div>
        </div>
        <div class="containcontent">
          <div class="chart chartEvent" ref="chart" id="child_3"></div>
          <!--          <div class="chart" id="chart_0"></div>-->
          <div class="infleft" @click="jump(data3.deviceSn)">
            <div class="name">心率:</div>
            <div class="hart" ><span :style="color(data3.hr_mean)">{{data3.hr_mean}}</span></div>
          </div>
        </div>
        <div class="containMsg" @click="jump(data3.deviceSn)" >
          <div class="name effect">姓名:<span>{{data3.patientName}}</span></div>
          <div class="name" >性别:<span>{{data3.gender}}</span></div>
          <div class="name" >年龄:<span>{{data3.age}}</span></div>
          <div class="name" >所属医院:<span>{{data3.hospName && data3.hospName.includes('*') ? data3.hospName : '*'.repeat(Math.max(data3.hospName ? data3.hospName.length - 2 : 0, 0)) + (data3.hospName ? data3.hospName.slice(-2) : '')}}</span></div>
          <div class="name" >设备号:<span>{{data3.deviceSn}}</span></div>
        </div>
        <div class="panel-footer"></div>
      </div>

      <div class="container" >
        <div class="event"  v-if="data4!=null&&JSON.stringify(data4)!='{}'">
          <div class="son">心肌梗塞<span id="span4-1" :style="dotColor(data4.GengSi)"></span></div>
          <div class="son">房颤房扑<span id="span4-2" :style="dotColor(data4.FangChan)"></span></div>
          <div class="son">心室率过低<span id="span4-3" :style="dotColor(data4.XSL_pr)"></span></div>
          <div class="son">室性心动过速<span id="span4-5"></span></div>
          <div class="son">室颤室扑<span id="span4-6" :style="dotColor(data4.ShiChan)"></span></div>
          <div class="son">窦室传导<span id="span4-7"></span></div>
          <div class="son">长RR间期<span id="span4-4" :style="dotColor(data4.RRGC_pr)"></span></div>
          <div class="son">QTc间期过长<span id="span4-8"></span></div>
        </div>
        <div class="containcontent">
          <div class="chart chartEvent" ref="chart" id="child_4"></div>
          <!--          <div class="chart" id="chart_0"></div>-->
          <div class="infleft" @click="jump(data4.deviceSn)">
            <div class="name">心率:</div>
            <div class="hart" ><span :style="color(data4.hr_mean)">{{data4.hr_mean}}</span></div>
          </div>
        </div>
        <div class="containMsg" @click="jump(data4.deviceSn)" >
          <div class="name effect">姓名:<span>{{data4.patientName}}</span></div>
          <div class="name" >性别:<span>{{data4.gender}}</span></div>
          <div class="name" >年龄:<span>{{data4.age}}</span></div>
          <div class="name" >所属医院:<span>{{data4.hospName && data4.hospName.includes('*') ? data4.hospName : '*'.repeat(Math.max(data4.hospName ? data4.hospName.length - 2 : 0, 0)) + (data4.hospName ? data4.hospName.slice(-2) : '')}}</span></div>
          <div class="name" >设备号:<span>{{data4.deviceSn}}</span></div>
        </div>
        <div class="panel-footer"></div>
      </div>

      <div class="container" >
        <div class="event"  v-if="data5!=null&&JSON.stringify(data5)!='{}'">
          <div class="son">心肌梗塞<span id="span5-1" :style="dotColor(data5.GengSi)"></span></div>
          <div class="son">房颤房扑<span id="span5-2" :style="dotColor(data5.FangChan)"></span></div>
          <div class="son">心室率过低<span id="span5-3" :style="dotColor(data5.XSL_pr)"></span></div>
          <div class="son">室性心动过速<span id="span5-5"></span></div>
          <div class="son">室颤室扑<span id="span5-6" :style="dotColor(data5.ShiChan)"></span></div>
          <div class="son">窦室传导<span id="span5-7"></span></div>
          <div class="son">长RR间期<span id="span5-4" :style="dotColor(data5.RRGC_pr)"></span></div>
          <div class="son">QTc间期过长<span id="span5-8"></span></div>
        </div>
        <div class="containcontent">
          <div class="chart chartEvent" ref="chart" id="child_5"></div>
          <div class="infleft" @click="jump(data5.deviceSn)">
            <div class="name">心率:</div>
            <div class="hart" ><span :style="color(data5.hr_mean)">{{data5.hr_mean}}</span></div>
          </div>
        </div>
        <div class="containMsg" @click="jump(data5.deviceSn)" >
          <div class="name effect">姓名:<span>{{data5.patientName}}</span></div>
          <div class="name" >性别:<span>{{data5.gender}}</span></div>
          <div class="name" >年龄:<span>{{data5.age}}</span></div>
          <div class="name" >所属医院:<span>{{data5.hospName && data5.hospName.includes('*') ? data5.hospName : '*'.repeat(Math.max(data5.hospName ? data5.hospName.length - 2 : 0, 0)) + (data5.hospName ? data5.hospName.slice(-2) : '')}}</span></div>
          <div class="name" >设备号:<span>{{data5.deviceSn}}</span></div>
        </div>
        <div class="panel-footer"></div>
      </div>

      <div class="container" >
        <div class="event"  v-if="data6!=null&&JSON.stringify(data6)!='{}'">
          <div class="son">心肌梗塞<span id="span6-1" :style="dotColor(data6.GengSi)"></span></div>
          <div class="son">房颤房扑<span id="span6-2" :style="dotColor(data6.FangChan)"></span></div>
          <div class="son">心室率过低<span id="span6-3" :style="dotColor(data6.XSL_pr)"></span></div>
          <div class="son">室性心动过速<span id="span6-5"></span></div>
          <div class="son">室颤室扑<span id="span6-6" :style="dotColor(data6.ShiChan)"></span></div>
          <div class="son">窦室传导<span id="span6-7"></span></div>
          <div class="son">长RR间期<span id="span6-4" :style="dotColor(data6.RRGC_pr)"></span></div>
          <div class="son">QTc间期过长<span id="span6-8"></span></div>
        </div>
        <div class="containcontent">
          <div class="chart chartEvent" ref="chart" id="child_6"></div>
          <div class="infleft" @click="jump(data6.deviceSn)">
            <div class="name">心率:</div>
            <div class="hart" ><span :style="color(data6.hr_mean)">{{data6.hr_mean}}</span></div>
          </div>
        </div>
        <div class="containMsg" @click="jump(data6.deviceSn)" >
          <div class="name effect">姓名:<span>{{data6.patientName}}</span></div>
          <div class="name" >性别:<span>{{data6.gender}}</span></div>
          <div class="name" >年龄:<span>{{data6.age}}</span></div>
          <div class="name" >所属医院:<span>{{data6.hospName && data6.hospName.includes('*') ? data6.hospName : '*'.repeat(Math.max(data6.hospName ? data6.hospName.length - 2 : 0, 0)) + (data6.hospName ? data6.hospName.slice(-2) : '')}}</span></div>
          <div class="name" >设备号:<span>{{data6.deviceSn}}</span></div>
        </div>
        <div class="panel-footer"></div>
      </div>

      <div class="container" >
        <div class="event"  v-if="data7!=null&&JSON.stringify(data7)!='{}'">
          <div class="son">心肌梗塞<span id="span7-1" :style="dotColor(data7.GengSi)"></span></div>
          <div class="son">房颤房扑<span id="span7-2" :style="dotColor(data7.FangChan)"></span></div>
          <div class="son">心室率过低<span id="span7-3" :style="dotColor(data7.XSL_pr)"></span></div>
          <div class="son">室性心动过速<span id="span7-5"></span></div>
          <div class="son">室颤室扑<span id="span7-6" :style="dotColor(data7.ShiChan)"></span></div>
          <div class="son">窦室传导<span id="span7-7"></span></div>
          <div class="son">长RR间期<span id="span7-4" :style="dotColor(data7.RRGC_pr)"></span></div>
          <div class="son">QTc间期过长<span id="span7-8"></span></div>
        </div>
        <div class="containcontent">
          <div class="chart chartEvent" ref="chart" id="child_7"></div>
          <!--          <div class="chart" id="chart_0"></div>-->
          <div class="infleft" @click="jump(data7.deviceSn)">
            <div class="name">心率:</div>
            <div class="hart" ><span :style="color(data7.hr_mean)">{{data7.hr_mean}}</span></div>
          </div>
        </div>
        <div class="containMsg" @click="jump(data7.deviceSn)" >
          <div class="name effect">姓名:<span>{{data7.patientName}}</span></div>
          <div class="name" >性别:<span>{{data7.gender}}</span></div>
          <div class="name" >年龄:<span>{{data7.age}}</span></div>
          <div class="name" >所属医院:<span>{{data7.hospName && data7.hospName.includes('*') ? data7.hospName : '*'.repeat(Math.max(data7.hospName ? data7.hospName.length - 2 : 0, 0)) + (data7.hospName ? data7.hospName.slice(-2) : '')}}</span></div>
          <div class="name" >设备号:<span>{{data7.deviceSn}}</span></div>
        </div>
        <div class="panel-footer"></div>
      </div>

      <div class="container" >
        <div class="event"  v-if="data8!=null&&JSON.stringify(data8)!='{}'">
          <div class="son">心肌梗塞<span id="span8-1" :style="dotColor(data8.GengSi)"></span></div>
          <div class="son">房颤房扑<span id="span8-2" :style="dotColor(data8.FangChan)"></span></div>
          <div class="son">心室率过低<span id="span8-3" :style="dotColor(data8.XSL_pr)"></span></div>
          <div class="son">室性心动过速<span id="span8-5"></span></div>
          <div class="son">室颤室扑<span id="span8-6" :style="dotColor(data8.ShiChan)"></span></div>
          <div class="son">窦室传导<span id="span8-7"></span></div>
          <div class="son">长RR间期<span id="span8-4" :style="dotColor(data8.RRGC_pr)"></span></div>
          <div class="son">QTc间期过长<span id="span8-8"></span></div>
        </div>
        <div class="containcontent">
          <div class="chart chartEvent" ref="chart" id="child_8"></div>
          <!--          <div class="chart" id="chart_0"></div>-->
          <div class="infleft" @click="jump(data8.deviceSn)">
            <div class="name">心率:</div>
            <div class="hart" ><span :style="color(data8.hr_mean)">{{data8.hr_mean}}</span></div>
          </div>
        </div>
        <div class="containMsg" @click="jump(data8.deviceSn)" >
          <div class="name effect">姓名:<span>{{data8.patientName}}</span></div>
          <div class="name" >性别:<span>{{data8.gender}}</span></div>
          <div class="name" >年龄:<span>{{data8.age}}</span></div>
          <div class="name" >所属医院:<span>{{data8.hospName && data8.hospName.includes('*') ? data8.hospName : '*'.repeat(Math.max(data8.hospName ? data8.hospName.length - 2 : 0, 0)) + (data8.hospName ? data8.hospName.slice(-2) : '')}}</span></div>
          <div class="name" >设备号:<span>{{data8.deviceSn}}</span></div>
        </div>
        <div class="panel-footer"></div>
      </div>

      <!--      <div class="container" >-->
      <!--        <div class="event"  v-if="data9!=null&&JSON.stringify(data9)!='{}'">-->
      <!--          <div class="son">心肌梗塞<span id="span9-1" :style="dotColor(data9.GengSi)"></span></div>-->
      <!--          <div class="son">房颤房扑<span id="span9-2" :style="dotColor(data9.FangChan)"></span></div>-->
      <!--          <div class="son">心室率过低<span id="span9-3" :style="dotColor(data9.XSL_pr)"></span></div>-->
      <!--          <div class="son">长RR间期<span id="span9-4" :style="dotColor(data9.RRGC_pr)"></span></div>-->
      <!--          <div class="son">室性心动过速<span id="span9-5"></span></div>-->
      <!--          <div class="son">室颤室扑<span id="span9-6" :style="dotColor(data9.ShiChan)"></span></div>-->
      <!--          <div class="son">窦室传导<span id="span9-7"></span></div>-->
      <!--          <div class="son">QTc间期过长<span id="span9-8"></span></div>-->
      <!--        </div>-->
      <!--        <div class="containcontent">-->
      <!--          <div class="chart chartEvent" ref="chart" id="child_9"></div>-->
      <!--          <div class="infleft" @click="jump(data9.deviceSn)">-->
      <!--            <div class="name">心率:</div>-->
      <!--            <div class="hart" ><span :style="color(data9.hr_mean)">{{data9.hr_mean}}</span></div>-->
      <!--          </div>-->
      <!--        </div>-->
      <!--        <div class="containMsg" @click="jump(data9.deviceSn)" >-->
      <!--          <div class="name effect">姓名:<span>{{data9.patientName}}</span></div>-->
      <!--          <div class="name" >性别:<span>{{data9.gender}}</span></div>-->
      <!--          <div class="name" >年龄:<span>{{data9.age}}</span></div>-->
      <!--          <div class="name" >所属医院:<span>{{data9.hospName}}</span></div>-->
      <!--          <div class="name" >设备号:<span>{{data9.deviceSn}}</span></div>-->
      <!--        </div>-->
      <!--        <div class="panel-footer"></div>-->
      <!--      </div>-->

      <!--      <div class="container" >-->
      <!--        <div class="event"  v-if="data10!=null&&JSON.stringify(data10)!='{}'">-->
      <!--          <div class="son">心肌梗塞<span id="span10-1" :style="dotColor(data10.GengSi)"></span></div>-->
      <!--          <div class="son">房颤房扑<span id="span10-2" :style="dotColor(data10.FangChan)"></span></div>-->
      <!--          <div class="son">心室率过低<span id="span10-3" :style="dotColor(data10.XSL_pr)"></span></div>-->
      <!--          <div class="son">长RR间期<span id="span10-4" :style="dotColor(data10.RRGC_pr)"></span></div>-->
      <!--          <div class="son">室性心动过速<span id="span10-5"></span></div>-->
      <!--          <div class="son">室颤室扑<span id="span10-6" :style="dotColor(data10.ShiChan)"></span></div>-->
      <!--          <div class="son">窦室传导<span id="span10-7"></span></div>-->
      <!--          <div class="son">QTc间期过长<span id="span10-8"></span></div>-->
      <!--        </div>-->
      <!--        <div class="containcontent">-->
      <!--          <div class="chart chartEvent" ref="chart" id="child_10"></div>-->
      <!--          <div class="infleft" @click="jump(data10.deviceSn)">-->
      <!--            <div class="name">心率:</div>-->
      <!--            <div class="hart" ><span :style="color(data10.hr_mean)">{{data10.hr_mean}}</span></div>-->
      <!--          </div>-->
      <!--        </div>-->
      <!--        <div class="containMsg" @click="jump(data10.deviceSn)" >-->
      <!--          <div class="name effect">姓名:<span>{{data10.patientName}}</span></div>-->
      <!--          <div class="name" >性别:<span>{{data10.gender}}</span></div>-->
      <!--          <div class="name" >年龄:<span>{{data10.age}}</span></div>-->
      <!--          <div class="name" >所属医院:<span>{{data10.hospName}}</span></div>-->
      <!--          <div class="name" >设备号:<span>{{data10.deviceSn}}</span></div>-->
      <!--        </div>-->
      <!--        <div class="panel-footer"></div>-->
      <!--      </div>-->

      <!--      <div class="container" >-->
      <!--        <div class="event"  v-if="data11!=null&&JSON.stringify(data11)!='{}'">-->
      <!--          <div class="son">心肌梗塞<span id="span11-1" :style="dotColor(data11.GengSi)"></span></div>-->
      <!--          <div class="son">房颤房扑<span id="span11-2" :style="dotColor(data11.FangChan)"></span></div>-->
      <!--          <div class="son">心室率过低<span id="span11-3" :style="dotColor(data11.XSL_pr)"></span></div>-->
      <!--          <div class="son">长RR间期<span id="span11-4" :style="dotColor(data11.RRGC_pr)"></span></div>-->
      <!--          <div class="son">室性心动过速<span id="span11-5"></span></div>-->
      <!--          <div class="son">室颤室扑<span id="span11-6" :style="dotColor(data11.ShiChan)"></span></div>-->
      <!--          <div class="son">窦室传导<span id="span11-7"></span></div>-->
      <!--          <div class="son">QTc间期过长<span id="span11-8"></span></div>-->
      <!--        </div>-->
      <!--        <div class="containcontent">-->
      <!--          <div class="chart chartEvent" ref="chart" id="child_11"></div>-->
      <!--          <div class="infleft" @click="jump(data11.deviceSn)">-->
      <!--            <div class="name">心率:</div>-->
      <!--            <div class="hart" ><span :style="color(data11.hr_mean)">{{data11.hr_mean}}</span></div>-->
      <!--          </div>-->
      <!--        </div>-->
      <!--        <div class="containMsg" @click="jump(data11.deviceSn)" >-->
      <!--          <div class="name effect">姓名:<span>{{data11.patientName}}</span></div>-->
      <!--          <div class="name" >性别:<span>{{data11.gender}}</span></div>-->
      <!--          <div class="name" >年龄:<span>{{data11.age}}</span></div>-->
      <!--          <div class="name" >所属医院:<span>{{data11.hospName}}</span></div>-->
      <!--          <div class="name" >设备号:<span>{{data11.deviceSn}}</span></div>-->
      <!--        </div>-->
      <!--        <div class="panel-footer"></div>-->
      <!--      </div>-->

    </div>

  </div>
</template>

<script>
import {
  get_device,
  list,
  getlist
} from "@/api/ECGScreen/equipment";
import * as echarts from '../detail/echarts.min'
import 'default-passive-events'
import screenfull from 'screenfull'
import {getUserInfo, getInfoId, getInfoIdByHospital} from "@/api/patient_management/patient_management";
import Cookies from "js-cookie";
import {getToken} from "@/utils/auth";
let hospName='所有'
export default {
  data() {
    return {
      // 路径
      // 原先的路径
      // url:'https://server.mindyard.cn:84/detect_decg',
      // 提高心电大屏的显示速度
      url: 'https://server.mindyard.cn:84/detect_decg_bgScreen',
      test:1,
      // 版本号
      isFullFlag:false,
      total:null,//总设备数
      pagenum:null,//总页数
      pages:1,//当前页
      arr:[],//设备号列表
      data0:{},
      data1: {},//前10秒数据
      data2:{},
      data3:{},
      data4:{},
      data5:{},
      data6:{},
      data7:{},
      data8:{},
      data9:{},
      data10:{},
      data11:{},
      newData0:{},
      newData1:{}, //最新10秒数据
      newData2:{},
      newData3:{},
      newData4:{},
      newData5:{},
      newData6:{},
      newData7:{},
      newData8:{},
      newData9:{},
      newData10:{},
      newData11:{},
      p0Iy:[],
      p0V1y:[],
      p1Iy:[],
      p1V1y:[],
      p2Iy:[],
      p2V1y:[],
      p3Iy:[],
      p3V1y:[],
      p4Iy:[],
      p4V1y:[],
      p5Iy:[],
      p5V1y:[],
      p6Iy:[],
      p6V1y:[],
      p7Iy:[],
      p7V1y:[],
      p8Iy:[],
      p8V1y:[],
      p9Iy:[],
      p9V1y:[],
      p10Iy:[],
      p10V1y:[],
      p11Iy:[],
      p11V1y:[],
      tag:1,//每个请求10秒数据  1表示前5秒的数据显示 2表示后5秒的数据显示
      ts:0,//时间段
      lodaing:{},
      currentpage:[],//一维数组，存放12台设备
      timer0:null,
      timer1:null,
      timer2:null,
      timer3:null,
      timer4:null,
      timer5:null,
      timer6:null,
      timer7:null,
      timer8:null,
      timer9:null,
      timer10:null,
      timer11:null,
      time:null,//时间 X轴
      index:0,
      tishii:true
    };
  },
  async created() {
    this.openLoading();

    let hospitalId =this.$route.query.hospitalId
    let token = this.$route.query.token
    if (token){
      this.$store.dispatch("UpdateToken", token).then(() => {
      })
    }
    if(hospitalId&&hospitalId!==1){
      await getInfoIdByHospital(hospitalId,token).then(user => {
        hospName=user.data.hospitalName
      })
    }
    //清空数据
    this.clearList()
    //请求设备列表
    this.get_device(hospName,this.tishi);
  },
  mounted() {
    this.$store.dispatch('app/toggleSideBarHide', true);
    document.querySelector('.navbar').style.display = "none";
    document.querySelector('.tags-view-container').style.display = "none"
    document.querySelector('.app-main').style.paddingTop = "0";
  },

  watch:{
    $route(to,from){
      if(this.$route.path!=='/large_screen'){
        this.clearIntervallist()
        // console.log("路由变化")
      }
    }
  },
  async activated() {
    let hospitalId =this.$route.query.hospitalId
    console.log('activated')
    let token = this.$route.query.token
    if (token){
      this.$store.dispatch("UpdateToken", token).then(() => {
      })
    }
    if(hospitalId&&hospitalId!==1){
      await getInfoIdByHospital(hospitalId,token).then(user => {
        hospName=user.data.hospitalName
      })
    }
    this.clearList()
    this.get_device(hospName);
  },
  // beforeRouteLeave(to,from,next){
  //   this.clearIntervallist()
  //   next()
  // },
  deactivated(){//keep-alive的隐藏的钩子函数
    console.log("deactivated")
    this.index=0
    this.clearList();
    this.clearIntervallist()
  },
  beforeDestroy(){
    console.log('beforeDestroy')
    this.index=0
    this.clearList();
    this.clearIntervallist()
  },
  // beforeRouteEnter (to, from, next) {
  //   next(vm => {
  //     vm.get_device(hospName);
  //   })
  // },
  computed: {
    color() {
      return hr_mean => {
        if(hr_mean>100||hr_mean<60){
          return 'color:red;background-color: rgba(255, 0, 0,0.3);';
        }
        return ;
      };
    },
    dotColor(){
      return num => {
        if(Number(num)>=0.55){
          return 'background-color: red;';
        }
        if(Number(num)>=0.3&&Number(num)<=0.55){
          return 'background-color: #E1C943;';
        }
        return ;
      };
    }

  },

  methods: {
    // 提示框方法
    tishi(length){
      this.$message.success({
        message: '设备列表获取成功',
        type: 'success',
        duration: 2500,
        center:true,
        onClose:()=> {
          this.$message.success({
            message: '共有' + length + "台设备链接",
            type: 'success',
            duration: 4000,
            center:true
          })
        }
      })
    },
    //请求设备列表
    async get_device(hospName,tishii){
      // console.log(this.timer0)
      // console.log("医院名称:"+hospName)
      this.index++
      if(this.index!==1){
        return
      }
      await this.$http.post('https://server.mindyard.cn:84/get_device',
        JSON.stringify({
          "ts": 0,
          "hospName":hospName}),{
          contentType: "application/json",
          dataType: "json",
          headers:{
            "user":"zzu",
            "password":"zzu123"
          },
        }
      ).then(res=>{
        this.arr=res.data.result.dev_list;
        // console.log(this.arr)
        //分页
        let length =  res.data.result.dev_list.length;//总设备个数
        this.total=length;
        this.pagenum = (length%9==0) ? (length/9) : length/9+1;//总页数
        for (let i = 0; i < length; i+=9) {
          this.currentpage.push(this.arr.slice(i,i+9))
        }
        // this.$message.success({
        //   message: '设备列表获取成功',
        //   type: 'success',
        //   duration: 2500,
        //   center:true,
        //   onClose:()=> {
        //     this.$message.success({
        //       message: '共有' + length + "台设备链接",
        //       type: 'success',
        //       duration: 4000,
        //       center:true
        //     })
        //   }
        // })
        if (tishii) {
          this.tishi(length)
        }
      }).catch(err=>{
        // console.log("请求错误"+err)
        this.closeFullScreen()
      })
      //当前页小于总页数时请求数据
      if(this.pagenum>=this.pages){
        const num=this.currentpage[this.pages-1].length
        if(num>=1){
          this.list1()
        }
        // else{
        //   this.get_device(hospName);
        // }
        if(num>=2){
          this.list2()
        }
        if(num>=3){
          this.list3()
        }
        if(num>=4){
          this.list4()
        }
        if(num>=5){
          this.list5()
        }
        if(num>=6){
          this.list6()
        }
        if(num>=7){
          this.list7()
        }
        if(num>=8){
          this.list8()
        }
        if(num>=9){
          this.list9()
        }
        // if(num>=10){
        //   this.list10()
        // }
        // if(num>=11){
        //   this.list11()
        // }
        // if(num>=12){
        //   this.list12()
        // }
      }
      setTimeout(()=>{
        var child_0=echarts.init(document.getElementById('child_0'))
        var child_1=echarts.init(document.getElementById('child_1'))
        var child_2=echarts.init(document.getElementById('child_2'))
        var child_3=echarts.init(document.getElementById('child_3'))
        var child_4=echarts.init(document.getElementById('child_4'))
        var child_5=echarts.init(document.getElementById('child_5'))
        var child_6=echarts.init(document.getElementById('child_6'))
        var child_7=echarts.init(document.getElementById('child_7'))
        var child_8=echarts.init(document.getElementById('child_8'))
        var erd = elementResizeDetectorMaker()
        erd.listenTo(document.getElementById("containcontent"), (element)=>{
          child_0.resize();
          child_1.resize();
          child_2.resize();
          child_3.resize();
          child_4.resize();
          child_5.resize();
          child_6.resize();
          child_7.resize();
          child_8.resize();
        })
      })

      this.closeFullScreen()
    },
    async list1(){
      if(this.currentpage[this.pages-1][0]!==null) {
        const p=this.pages
        await this.$http.post(this.url,
          JSON.stringify({
            "deviceSn": this.currentpage[this.pages - 1][0],
            "ts": 0,
          }), {
            contentType: "application/json",
            dataType: "json",
            headers: {
              "user": "zzu",
              "password": "zzu123"
            },
          }
        ).then(res => {
          if(p!==this.pages){
            return
          }
          res.data.result.hr_mean = res.data.result.hr_mean.toFixed()
          this.data0 = res.data.result
          this.p0Iy=res.data.result.data.II;
          this.p0V1y=res.data.result.data.V1;
          let chart =echarts.init(document.getElementById('child_0'))
          chart.clear();
          chart.setOption(this.chart(this.data0, 0,this.p0Iy,this.p0V1y))

          let tag=1 //第一轮请求
          let ts=1//时间 顺序
          let code=200 //请求成功
          //删除定时器
          if(this.timer0){
            window.clearInterval(this.timer0)
            this.timer0=null
          }
          this.timer0=window.setInterval(()=>{
            //离开路由定时器删除
            if(this.$route.path!=='/large_screen'){
              window.clearInterval(this.timer0)
              this.timer0=null
            }
            //自适应
            chart.resize()
            //第一次请求 请求下个时间段保存到newData
            if(tag===1){
              this.$http.post(this.url,
                JSON.stringify({
                  "deviceSn": this.currentpage[this.pages - 1][0],
                  "ts": ts,
                }), {
                  contentType: "application/json",
                  dataType: "json",
                  headers: {
                    "user": "zzu",
                    "password": "zzu123"
                  },
                }
              ).then(res=>{
                if(!res.data.result){
                  this.newData0={}
                  code=201
                  return
                }
                //请求成功
                code=200
                res.data.result.hr_mean = res.data.result.hr_mean.toFixed()
                this.newData0=res.data.result
                ts++
                tag++
              }).catch(err=>{
                this.newData0={}
                // console.log("请求错误"+err)
              })
              //请求成功
              if(code!==201){
                // console.log(tag)
                //this.setColor(0)
                chart.clear();
                //绘图 前5s
                chart.setOption(this.chart(this.data0, 0,this.p0Iy,this.p0V1y))
              }
            }
            else {
              if(this.newData0){
                this.data0=this.newData0
                this.p0Iy= this.newData0.data.II
                this.p0V1y=this.newData0.data.V1
                tag--
                //this.setColor(0)
                chart.clear();
                //后5s
                chart.setOption(this.chart(this.data0, 1250,this.p0Iy,this.p0V1y))
              }
            }
          },5000)
          // console.log("timer00="+this.timer0)
        }).catch(err=>{
          // console.log("请求错误"+err)
          this.get_device(hospName);
        })
      }
    },
    async list2(){
      if(this.currentpage[this.pages-1][1]!==null) {
        const p=this.pages
        await this.$http.post(this.url,
          JSON.stringify({
            "deviceSn": this.currentpage[this.pages - 1][1],
            "ts": 0,
          }), {
            contentType: "application/json",
            dataType: "json",
            headers: {
              "user": "zzu",
              "password": "zzu123"
            },
          }
        ).then(res => {
          if(p!==this.pages){
            // console.log("之前页面的请求"+p)
            return
          }
          res.data.result.hr_mean = res.data.result.hr_mean.toFixed()
          this.data1 = res.data.result
          this.p1Iy=res.data.result.data.II;
          this.p1V1y=res.data.result.data.V1;
          let chart =   echarts.init(document.getElementById('child_1'))
          chart.clear();
          chart.setOption(this.chart(this.data1, 0,this.p1Iy,this.p1V1y))
          //this.setColor(1)
          let tag=1
          let ts=1
          let code=200
          if(this.timer1){
            window.clearInterval(this.timer1)
            this.timer1=null
          }
          this.timer1 = window.setInterval(() => {
            if(this.$route.path!=='/large_screen'){
              window.clearInterval(this.timer1)
              this.timer1=null
            }
            chart.resize()
            if (tag === 1) {
              this.$http.post(this.url,
                JSON.stringify({
                  "deviceSn": this.currentpage[this.pages - 1][1],
                  "ts": ts,
                }), {
                  contentType: "application/json",
                  dataType: "json",
                  headers: {
                    "user": "zzu",
                    "password": "zzu123"
                  },
                }
              ).then(res => {
                if(!res.data.result){
                  this.newData1={}
                  code=201
                  return
                }
                code=200
                res.data.result.hr_mean = res.data.result.hr_mean.toFixed()
                this.newData1 = res.data.result
                ts++
                tag++
              }).catch(err => {
                // console.log("请求错误" + err)
              })
              if(code!==201){
                chart.clear();
                chart.setOption(this.chart(this.data1, 0, this.p1Iy, this.p1V1y))
                //this.setColor(1)
              }
            } else {
              this.data1 = this.newData1
              this.p1Iy = this.newData1.data.II
              this.p1V1y = this.newData1.data.V1
              tag--
              chart.clear();
              chart.setOption(this.chart(this.data1, 1250, this.p1Iy, this.p1V1y))
              //this.setColor(1)
            }
          }, 5000)

        }).catch(err=>{
          // console.log("请求错误"+err)
          this.get_device(hospName);
        })
      }

    },
    async list3(){
      if(this.currentpage[this.pages-1][2]!==null) {
        const p=this.pages
        await this.$http.post(this.url,
          JSON.stringify({
            "deviceSn": this.currentpage[this.pages - 1][2],
            "ts": 0,
          }), {
            contentType: "application/json",
            dataType: "json",
            headers: {
              "user": "zzu",
              "password": "zzu123"
            },
          }
        ).then(res => {
          if(p!==this.pages){
            // console.log("之前页面的请求"+p)
            return
          }
          res.data.result.hr_mean = res.data.result.hr_mean.toFixed()
          this.data2 = res.data.result
          this.p2Iy=res.data.result.data.II;
          this.p2V1y=res.data.result.data.V1;
          let chart =   echarts.init(document.getElementById('child_2'))
          //this.setColor(2)
          chart.clear();
          chart.setOption(this.chart(this.data2, 0,this.p2Iy,this.p2V1y))
          let tag=1
          let ts=1
          let code=200
          if(this.timer2){
            window.clearInterval(this.timer2)
            this.timer2=null
          }
          this.timer2 = window.setInterval(() => {
            if(this.$route.path!=='/large_screen'){
              window.clearInterval(this.timer2)
              this.timer2=null
            }
            chart.resize()
            if (tag === 1) {
              this.$http.post(this.url,
                JSON.stringify({
                  "deviceSn": this.currentpage[this.pages - 1][2],
                  "ts": ts,
                }), {
                  contentType: "application/json",
                  dataType: "json",
                  headers: {
                    "user": "zzu",
                    "password": "zzu123"
                  },
                }
              ).then(res => {
                if(!res.data.result){
                  this.newData2
                  code=201
                  return
                }
                code=200
                res.data.result.hr_mean = res.data.result.hr_mean.toFixed()
                this.newData2 = res.data.result
                ts++
                tag++
              }).catch(err => {
                // console.log("请求错误" + err)
              })
              if(code!==201){
                //this.setColor(2)
                chart.clear();
                chart.setOption(this.chart(this.data2, 0, this.p2Iy, this.p2V1y))
              }

            } else {
              this.data2 = this.newData2
              this.p2Iy = this.newData2.data.II
              this.p2V1y = this.newData2.data.V1
              tag--
              //this.setColor(2)
              chart.clear();
              chart.setOption(this.chart(this.data2, 1250, this.p2Iy, this.p2V1y))
            }
            //console.log(this.p2Iy)
          }, 5000)
        }).catch(err=>{
          // console.log("请求错误"+err)
          this.get_device(hospName);
        })
      }

    },
    async list4(){
      if(this.currentpage[this.pages-1][3]!==null) {
        const p=this.pages
        await this.$http.post(this.url,
          JSON.stringify({
            "deviceSn": this.currentpage[this.pages - 1][3],
            "ts": 0,
          }), {
            contentType: "application/json",
            dataType: "json",
            headers: {
              "user": "zzu",
              "password": "zzu123"
            },
          }
        ).then(res => {
          if(p!==this.pages){
            // console.log("之前页面的请求"+p)
            return
          }
          res.data.result.hr_mean = res.data.result.hr_mean.toFixed()
          this.data3 = res.data.result
          this.p3Iy=res.data.result.data.II;
          this.p3V1y=res.data.result.data.V1;
          let chart =   echarts.init(document.getElementById('child_3'))
          //this.setColor(3)
          chart.clear();
          chart.setOption(this.chart(this.data3, 0,this.p3Iy,this.p3V1y))
          let tag=1
          let ts=1
          let code=200
          if(this.timer3){
            window.clearInterval(this.timer3)
            this.timer3=null
          }
          this.timer3 = window.setInterval(() => {
            if(this.$route.path!=='/large_screen'){
              window.clearInterval(this.timer3)
              this.timer3=null
            }
            chart.resize()
            if (tag === 1) {
              this.$http.post(this.url,
                JSON.stringify({
                  "deviceSn": this.currentpage[this.pages - 1][3],
                  "ts": ts,
                }), {
                  contentType: "application/json",
                  dataType: "json",
                  headers: {
                    "user": "zzu",
                    "password": "zzu123"
                  },
                }
              ).then(res => {
                if(!res.data.result){
                  code=201
                  return
                }
                code=200
                res.data.result.hr_mean = res.data.result.hr_mean.toFixed()
                this.newData3 = res.data.result
                ts++
                tag++
              }).catch(err => {
                // console.log("请求错误" + err)
              })
              if(code!==201){
                //this.setColor(3)
                chart.clear();
                chart.setOption(this.chart(this.data3, 0, this.p3Iy, this.p3V1y))
              }
            } else {
              this.data3 = this.newData3
              this.p3Iy = this.newData3.data.II
              this.p3V1y = this.newData3.data.V1
              tag--
              //this.setColor(3)
              chart.clear();
              chart.setOption(this.chart(this.data3, 1250, this.p3Iy, this.p3V1y))
            }
          }, 5000)

        }).catch(err=>{
          // console.log("请求错误"+err)
          this.get_device(hospName);
        })
      }

    },
    async list5(){
      if(this.currentpage[this.pages-1][4]!==null) {
        const p=this.pages
        await this.$http.post(this.url,
          JSON.stringify({
            "deviceSn": this.currentpage[this.pages - 1][4],
            "ts": 0,
          }), {
            contentType: "application/json",
            dataType: "json",
            headers: {
              "user": "zzu",
              "password": "zzu123"
            },
          }
        ).then(res => {
          if(p!==this.pages){
            // console.log("之前页面的请求"+p)
            return
          }
          res.data.result.hr_mean = res.data.result.hr_mean.toFixed()
          this.data4 = res.data.result
          this.p4Iy=res.data.result.data.II;
          this.p4V1y=res.data.result.data.V1;
          let chart =   echarts.init(document.getElementById('child_4'))
          //this.setColor(4)
          chart.clear();
          chart.setOption(this.chart(this.data4, 0,this.p4Iy,this.p4V1y))
          let tag=1
          let ts=1
          let code=200
          if(this.timer4){
            window.clearInterval(this.timer4)
            this.timer4=null
          }
          this.timer4 = window.setInterval(() => {
            if(this.$route.path!=='/large_screen'){
              window.clearInterval(this.timer4)
              this.timer4=null
            }
            chart.resize()
            if (tag === 1) {
              this.$http.post(this.url,
                JSON.stringify({
                  "deviceSn": this.currentpage[this.pages - 1][4],
                  "ts": ts,
                }), {
                  contentType: "application/json",
                  dataType: "json",
                  headers: {
                    "user": "zzu",
                    "password": "zzu123"
                  },
                }
              ).then(res => {
                if(!res.data.result){
                  code=201
                  return
                }
                code=200
                res.data.result.hr_mean = res.data.result.hr_mean.toFixed()
                this.newData4 = res.data.result
                ts++
                tag++
              }).catch(err => {
                // console.log("请求错误" + err)
              })
              if(code!==201){
                //this.setColor(4)
                chart.clear();
                chart.setOption(this.chart(this.data4, 0, this.p4Iy, this.p4V1y))
              }

            } else {
              this.data4 = this.newData4
              this.p4Iy = this.newData4.data.II
              this.p4V1y = this.newData4.data.V1
              tag--
              //this.setColor(4)
              chart.clear();
              chart.setOption(this.chart(this.data4, 1250, this.p4Iy, this.p4V1y))
            }
          }, 5000)
        }).catch(err=>{
          // console.log("请求错误"+err)
          this.get_device(hospName);
        })
      }

    },
    async list6(){
      if(this.currentpage[this.pages-1][5]!==null) {
        const p=this.pages
        await this.$http.post(this.url,
          JSON.stringify({
            "deviceSn": this.currentpage[this.pages - 1][5],
            "ts": 0,
          }), {
            contentType: "application/json",
            dataType: "json",
            headers: {
              "user": "zzu",
              "password": "zzu123"
            },
          }
        ).then(res => {
          if(p!==this.pages){
            // console.log("之前页面的请求"+p)
            return
          }
          res.data.result.hr_mean = res.data.result.hr_mean.toFixed()
          this.data5 = res.data.result
          this.p5Iy=res.data.result.data.II;
          this.p5V1y=res.data.result.data.V1;
          let chart =   echarts.init(document.getElementById('child_5'))
          //this.setColor(5)
          chart.clear();
          chart.setOption(this.chart(this.data5, 0,this.p5Iy,this.p5V1y))
          let tag=1
          let ts=1
          let code=200
          if(this.timer5){
            window.clearInterval(this.timer5)
            this.timer5=null
          }
          this.timer5 = window.setInterval(() => {
            if(this.$route.path!=='/large_screen'){
              window.clearInterval(this.timer5)
              this.timer5=null
            }
            chart.resize()
            if (tag === 1) {
              this.$http.post(this.url,
                JSON.stringify({
                  "deviceSn": this.currentpage[this.pages - 1][5],
                  "ts": ts,
                }), {
                  contentType: "application/json",
                  dataType: "json",
                  headers: {
                    "user": "zzu",
                    "password": "zzu123"
                  },
                }
              ).then(res => {
                if(!res.data.result){
                  code=201
                  return
                }
                code=200
                res.data.result.hr_mean = res.data.result.hr_mean.toFixed()
                this.newData5 = res.data.result
                ts++
                tag++
              }).catch(err => {
                // console.log("请求错误" + err)
              })
              if(code!==201){
                //this.setColor(5)
                chart.clear();
                chart.setOption(this.chart(this.data5, 0, this.p5Iy, this.p5V1y))
              }
            } else {
              this.data5 = this.newData5
              this.p5Iy = this.newData5.data.II
              this.p5V1y = this.newData5.data.V1
              tag--
              //this.setColor(5)
              chart.clear();
              chart.setOption(this.chart(this.data5, 1250, this.p5Iy, this.p5V1y))
            }
          }, 5000)
        }).catch(err=>{
          // console.log("请求错误"+err)
          this.get_device(hospName);
        })
      }

    },
    async list7(){
      if(this.currentpage[this.pages-1][6]!==null) {
        const p=this.pages
        await this.$http.post(this.url,
          JSON.stringify({
            "deviceSn": this.currentpage[this.pages - 1][6],
            "ts": 0,
          }), {
            contentType: "application/json",
            dataType: "json",
            headers: {
              "user": "zzu",
              "password": "zzu123"
            },
          }
        ).then(res => {
          if(p!==this.pages){
            // console.log("之前页面的请求"+p)
            return
          }
          res.data.result.hr_mean = res.data.result.hr_mean.toFixed()
          this.data6 = res.data.result
          this.p6Iy=res.data.result.data.II;
          this.p6V1y=res.data.result.data.V1;
          let chart =   echarts.init(document.getElementById('child_6'))
          //this.setColor(6)
          chart.clear();
          chart.setOption(this.chart(this.data6, 0,this.p6Iy,this.p6V1y))
          let tag=1
          let ts=1
          let code=200
          if(this.timer6){
            window.clearInterval(this.timer6)
            this.timer6=null
          }
          this.timer6 = window.setInterval(() => {
            if(this.$route.path!=='/large_screen'){
              window.clearInterval(this.timer6)
              this.timer6=null
            }
            chart.resize()
            if (tag === 1) {
              this.$http.post(this.url,
                JSON.stringify({
                  "deviceSn": this.currentpage[this.pages - 1][6],
                  "ts": ts,
                }), {
                  contentType: "application/json",
                  dataType: "json",
                  headers: {
                    "user": "zzu",
                    "password": "zzu123"
                  },
                }
              ).then(res => {
                if(!res.data.result){
                  code=201
                  return
                }
                code=200
                res.data.result.hr_mean = res.data.result.hr_mean.toFixed()
                this.newData6 = res.data.result
                ts++
                tag++
              }).catch(err => {
                // console.log("请求错误" + err)
              })
              if(code!==201){
                //this.setColor(6)
                chart.clear();
                chart.setOption(this.chart(this.data6, 0, this.p6Iy, this.p6V1y))
              }

            } else {
              this.data6 = this.newData6
              this.p6Iy = this.newData6.data.II
              this.p6V1y = this.newData6.data.V1
              tag--
              //this.setColor(6)
              chart.clear();
              chart.setOption(this.chart(this.data6, 1250, this.p6Iy, this.p6V1y))
            }
          }, 5000)

        }).catch(err=>{
          // console.log("请求错误"+err)
          this.get_device(hospName);
        })
      }

    },
    async list8(){
      if(this.currentpage[this.pages-1][7]!==null) {
        const p=this.pages
        await this.$http.post(this.url,
          JSON.stringify({
            "deviceSn": this.currentpage[this.pages - 1][7],
            "ts": 0,
          }), {
            contentType: "application/json",
            dataType: "json",
            headers: {
              "user": "zzu",
              "password": "zzu123"
            },
          }
        ).then(res => {
          if(p!==this.pages){
            return
          }
          res.data.result.hr_mean = res.data.result.hr_mean.toFixed()
          this.data7 = res.data.result
          this.p7Iy=res.data.result.data.II;
          this.p7V1y=res.data.result.data.V1;
          let chart =   echarts.init(document.getElementById('child_7'))
          //this.setColor(7)
          chart.clear();
          chart.setOption(this.chart(this.data7, 0,this.p7Iy,this.p7V1y))
          let tag=1
          let ts=1
          let code=200
          if(this.timer7){
            window.clearInterval(this.timer7)
            this.timer7=null
          }
          this.timer7 = window.setInterval(() => {
            if(this.$route.path!=='/large_screen'){
              window.clearInterval(this.timer7)
              this.timer7=null
            }
            chart.resize()
            if (tag === 1) {
              this.$http.post(this.url,
                JSON.stringify({
                  "deviceSn": this.currentpage[this.pages - 1][7],
                  "ts": ts,
                }), {
                  contentType: "application/json",
                  dataType: "json",
                  headers: {
                    "user": "zzu",
                    "password": "zzu123"
                  },
                }
              ).then(res => {
                if(!res.data.result){
                  code=201
                  return
                }
                code=200
                res.data.result.hr_mean = res.data.result.hr_mean.toFixed()
                this.newData7 = res.data.result
                ts++
                tag++
              }).catch(err => {
                // console.log("请求错误" + err)
              })
              if(code!==201){
                //this.setColor(7)
                chart.clear();
                chart.setOption(this.chart(this.data7, 0, this.p7Iy, this.p7V1y))
              }

            } else {
              this.data7 = this.newData7
              this.p7Iy = this.newData7.data.II
              this.p7V1y = this.newData7.data.V1
              tag--
              //this.setColor(7)
              chart.clear();
              chart.setOption(this.chart(this.data7, 1250, this.p7Iy, this.p7V1y))
            }
          }, 5000)

        }).catch(err=>{
          // console.log("请求错误"+err)
          this.get_device(hospName);
        })
      }

    },
    async list9(){
      if(this.currentpage[this.pages-1][8]!==null) {
        const p=this.pages
        await this.$http.post(this.url,
          JSON.stringify({
            "deviceSn": this.currentpage[this.pages - 1][8],
            "ts": 0,
          }), {
            contentType: "application/json",
            dataType: "json",
            headers: {
              "user": "zzu",
              "password": "zzu123"
            },
          }
        ).then(res => {
          if(p!==this.pages){
            return
          }
          res.data.result.hr_mean = res.data.result.hr_mean.toFixed()
          this.data8 = res.data.result
          this.p8Iy=res.data.result.data.II;
          this.p8V1y=res.data.result.data.V1;
          let chart =   echarts.init(document.getElementById('child_8'))

          chart.clear();
          chart.setOption(this.chart(this.data8, 0,this.p8Iy,this.p8V1y))
          let tag=1
          let ts=1
          let code=200
          if(this.timer8){
            window.clearInterval(this.timer8)
            this.timer8=null
          }
          this.timer8 = window.setInterval(() => {
            if(this.$route.path!=='/large_screen'){
              window.clearInterval(this.timer8)
              this.timer8=null
            }
            chart.resize()
            if (tag === 1) {
              this.$http.post(this.url,
                JSON.stringify({
                  "deviceSn": this.currentpage[this.pages - 1][8],
                  "ts": ts,
                }), {
                  contentType: "application/json",
                  dataType: "json",
                  headers: {
                    "user": "zzu",
                    "password": "zzu123"
                  },
                }
              ).then(res => {
                if(!res.data.result){
                  code=201
                  return
                }
                code=200
                res.data.result.hr_mean = res.data.result.hr_mean.toFixed()
                this.newData8 = res.data.result
                ts++
                tag++
              }).catch(err => {
                // console.log("请求错误" + err)
              })
              if(code!==201){
                //this.setColor(8)
                chart.clear();
                chart.setOption(this.chart(this.data8, 0, this.p8Iy, this.p8V1y))
              }

            } else {
              this.data8 = this.newData8
              this.p8Iy = this.newData8.data.II
              this.p8V1y = this.newData8.data.V1
              tag--
              //this.setColor(8)
              chart.clear();
              chart.setOption(this.chart(this.data8, 1250, this.p8Iy, this.p8V1y))
            }
          }, 5000)

        }).catch(err=>{
          // console.log("请求错误"+err)
          this.get_device(hospName);
        })
      }

    },
    async list10(){
      if(this.currentpage[this.pages-1][9]!==null) {
        const p=this.pages
        await this.$http.post(this.url,
          JSON.stringify({
            "deviceSn": this.currentpage[this.pages - 1][9],
            "ts": 0,
          }), {
            contentType: "application/json",
            dataType: "json",
            headers: {
              "user": "zzu",
              "password": "zzu123"
            },
          }
        ).then(res => {
          if(p!==this.pages){
            return
          }
          res.data.result.hr_mean = res.data.result.hr_mean.toFixed()
          this.data9 = res.data.result
          this.p9Iy=res.data.result.data.II;
          this.p9V1y=res.data.result.data.V1;
          let chart =   echarts.init(document.getElementById('child_9'))
          //this.setColor(9)
          chart.clear();
          chart.setOption(this.chart(this.data9, 0,this.p9Iy,this.p9V1y))
          let tag=1
          let ts=1
          let code=200
          if(this.timer9){
            window.clearInterval(this.timer9)
            this.timer9=null
          }
          this.timer9 = window.setInterval(() => {
            if(this.$route.path!=='/large_screen'){
              window.clearInterval(this.timer9)
              this.timer9=null
            }
            chart.resize()
            if (tag === 1) {
              this.$http.post(this.url,
                JSON.stringify({
                  "deviceSn": this.currentpage[this.pages - 1][9],
                  "ts": ts,
                }), {
                  contentType: "application/json",
                  dataType: "json",
                  headers: {
                    "user": "zzu",
                    "password": "zzu123"
                  },
                }
              ).then(res => {
                if(!res.data.result){
                  code=201
                  return
                }
                code=200
                res.data.result.hr_mean = res.data.result.hr_mean.toFixed()
                this.newData9 = res.data.result
                ts++
                tag++
              }).catch(err => {
                // console.log("请求错误" + err)
              })
              if(code!==201){
                //this.setColor(9)
                chart.clear();
                chart.setOption(this.chart(this.data9, 0, this.p9Iy, this.p9V1y))
              }

            } else {
              this.data9 = this.newData9
              this.p9Iy = this.newData9.data.II
              this.p9V1y = this.newData9.data.V1
              tag--
              //this.setColor(9)
              chart.clear();
              chart.setOption(this.chart(this.data9, 1250, this.p9Iy, this.p9V1y))
            }
          }, 5000)

        }).catch(err=>{
          // console.log("请求错误"+err)
          this.get_device(hospName);
        })
      }

    },
    async list11(){
      if(this.currentpage[this.pages-1][10]!==null) {
        const p=this.pages
        await this.$http.post(this.url,
          JSON.stringify({
            "deviceSn": this.currentpage[this.pages - 1][10],
            "ts": 0,
          }), {
            contentType: "application/json",
            dataType: "json",
            headers: {
              "user": "zzu",
              "password": "zzu123"
            },
          }
        ).then(res => {
          if(p!==this.pages){
            return
          }
          res.data.result.hr_mean = res.data.result.hr_mean.toFixed()
          this.data10 = res.data.result
          this.p10Iy=res.data.result.data.II;
          this.p10V1y=res.data.result.data.V1;
          let chart =   echarts.init(document.getElementById('child_10'))
          //this.setColor(10)
          chart.clear();
          chart.setOption(this.chart(this.data10, 0,this.p10Iy,this.p10V1y))
          let tag=1
          let ts=1
          let code=200
          if(this.timer10){
            window.clearInterval(this.timer10)
            this.timer10=null
          }
          this.timer10 = window.setInterval(() => {
            if(this.$route.path!=='/large_screen'){
              window.clearInterval(this.timer10)
              this.timer10=null
            }
            chart.resize()
            if (tag === 1) {
              this.$http.post(this.url,
                JSON.stringify({
                  "deviceSn": this.currentpage[this.pages - 1][10],
                  "ts": ts,
                }), {
                  contentType: "application/json",
                  dataType: "json",
                  headers: {
                    "user": "zzu",
                    "password": "zzu123"
                  },
                }
              ).then(res => {
                if(!res.data.result){
                  code=201
                  return
                }
                code=200
                res.data.result.hr_mean = res.data.result.hr_mean.toFixed()
                this.newData10 = res.data.result
                ts++
                tag++
              }).catch(err => {
                // // console.log("请求错误" + err)
              })
              if(code!==201){
                //this.setColor(10)
                chart.clear();
                chart.setOption(this.chart(this.data10, 0, this.p1Iy, this.p1V1y))
              }

            } else {
              this.data10 = this.newData10
              this.p10Iy = this.newData10.data.II
              this.p10V1y = this.newData10.data.V1
              tag--
              //this.setColor(10)
              chart.clear();
              chart.setOption(this.chart(this.data10, 1250, this.p10Iy, this.p10V1y))
            }
          }, 5000)
        }).catch(err=>{
          // // console.log("请求错误"+err)
          this.get_device(hospName);
        })
      }

    },
    async list12(){
      if(this.currentpage[this.pages-1][11]!==null) {
        const p=this.pages
        await this.$http.post(this.url,
          JSON.stringify({
            "deviceSn": this.currentpage[this.pages - 1][11],
            "ts": 0,
          }), {
            contentType: "application/json",
            dataType: "json",
            headers: {
              "user": "zzu",
              "password": "zzu123"
            },
          }
        ).then(res => {
          if(p!==this.pages){
            return
          }
          res.data.result.hr_mean = res.data.result.hr_mean.toFixed()
          this.data11 = res.data.result
          this.p11Iy=res.data.result.data.II;
          this.p11V1y=res.data.result.data.V1;
          let chart =   echarts.init(document.getElementById('child_11'))
          //this.setColor(11)
          chart.clear();
          chart.setOption(this.chart(this.data11, 0,this.p11Iy,this.p11V1y))
          let tag=1
          let ts=1
          let code=200
          if(this.timer11){
            window.clearInterval(this.timer11)
            this.timer11=null
          }
          this.timer11 = window.setInterval(() => {
            if(this.$route.path!=='/large_screen'){
              window.clearInterval(this.timer11)
              this.timer11=null
            }
            chart.resize()
            if (tag === 1) {
              this.$http.post(this.url,
                JSON.stringify({
                  "deviceSn": this.currentpage[this.pages - 1][11],
                  "ts": ts,
                }), {
                  contentType: "application/json",
                  dataType: "json",
                  headers: {
                    "user": "zzu",
                    "password": "zzu123"
                  },
                }
              ).then(res => {
                if(!res.data.result){
                  code=201
                  return
                }
                code=200
                res.data.result.hr_mean = res.data.result.hr_mean.toFixed()
                this.newData11 = res.data.result
                ts++
                tag++
              }).catch(err => {
                // // console.log("请求错误" + err)
              })
              if(code!==201){
                //this.setColor(11)
                chart.clear();
                chart.setOption(this.chart(this.data11, 0, this.p11Iy, this.p11V1y))
              }
            } else {
              this.data11 = this.newData11
              this.p11Iy = this.newData11.data.II
              this.p11V1y = this.newData11.data.V1
              tag--
              //this.setColor(11)
              chart.clear();
              chart.setOption(this.chart(this.data11, 1250, this.p11Iy, this.p11V1y))
            }
          }, 5000)

        }).catch(err=>{
          // // console.log("请求错误"+err)
          this.get_device(hospName);
        })
      }

    },
    //清空定时器
    clearIntervallist(){
      // // console.log("清除定时任务")
      window.clearInterval(this.timer0);
      window.clearInterval(this.timer1);
      window.clearInterval(this.timer2);
      window.clearInterval(this.timer3)
      window.clearInterval(this.timer4);
      window.clearInterval(this.timer5);
      window.clearInterval(this.timer6);
      window.clearInterval(this.timer7);
      window.clearInterval(this.timer8);
      // window.clearInterval(this.timer9);
      // window.clearInterval(this.timer10);
      // window.clearInterval(this.timer11);
      this.timer0=null;
      this.timer1=null;
      this.timer2=null;
      this.timer3=null;
      this.timer4=null;
      this.timer5=null;
      this.timer6=null;
      this.timer7=null;
      this.timer8=null;
      // this.timer9=null;
      // this.timer10=null;
      // this.timer11=null;
    },
    //清空数据
    clearList(){

      this.data0={}
      this.data1={}
      this.data2={}
      this.data3={}
      this.data4={}
      this.data5={}
      this.data6={}
      this.data7={}
      this.data8={}
      this.data9={}
      this.data10={}
      this.data11={}
      this.newData0={}
      this.newData1={}
      this.newData2={}
      this.newData3={}
      this.newData4={}
      this.newData5={}
      this.newData6={}
      this.newData7={}
      this.newData8={}
      // this.newData9={}
      // this.newData10={}
      // this.newData11={}
      this.p0Iy=[]
      this.p0V1y=[]
      this.p1Iy=[]
      this.p1V1y=[]
      this.p2Iy=[]
      this.p2V1y=[]
      this.p3Iy=[]
      this.p3V1y=[]
      this.p4Iy=[]
      this.p4V1y=[]
      this.p5Iy=[]
      this.p5V1y=[]
      this.p6Iy=[]
      this.p6V1y=[]
      this.p7Iy=[]
      this.p7V1y=[]
      this.p8Iy=[]
      this.p8V1y=[]
      // this.p9Iy=[]
      // this.p9V1y=[]
      // this.p10Iy=[]
      // this.p10V1y=[]
      // this.p11Iy=[]
      // this.p11V1y=[]
      this.ts=0
      echarts.init(document.getElementById('child_0')).dispose()
      echarts.init(document.getElementById('child_1')).dispose()
      echarts.init(document.getElementById('child_2')).dispose()
      echarts.init(document.getElementById('child_3')).dispose()
      echarts.init(document.getElementById('child_4')).dispose()
      echarts.init(document.getElementById('child_5')).dispose()
      echarts.init(document.getElementById('child_6')).dispose()
      echarts.init(document.getElementById('child_7')).dispose()
      echarts.init(document.getElementById('child_8')).dispose()
      // echarts.init(document.getElementById('child_9')).dispose()
      // echarts.init(document.getElementById('child_10')).dispose()
      // echarts.init(document.getElementById('child_11')).dispose()
    },
    //换页
    handleCurrentChange(pages){
      this.clearIntervallist()
      this.clearList()
      this.pages=pages
      this.openLoading()
      // // console.log("当前页"+this.pages)
      console.log(this.pages)
      console.log(this.pagenum)
      console.log(this.currentpage)
      if(this.pagenum>=this.pages){
        const num=this.currentpage[this.pages-1].length
        if(num>=1){
          this.list1()
        }
        if(num>=2){
          this.list2()
        }
        if(num>=3){
          this.list3()
        }
        if(num>=4){
          this.list4()
        }
        if(num>=5){
          this.list5()
        }
        if(num>=6){
          this.list6()
        }
        if(num>=7){
          this.list7()
        }
        if(num>=8){
          this.list8()
        }
        if(num>=9){
          this.list9()
        }
        // if(num>=10){
        //   this.list10()
        // }
        // if(num>=11){
        //   this.list11()
        // }
        // if(num>=12){
        //   this.list12()
        // }
      }
      this.closeFullScreen()
    },
    //生成x轴
    timex(){
      let timex = (function () {
        let now = new Date();
        let res = [];
        let lenth = 1250;
        while (lenth--) {
          res.push(now.toLocaleTimeString());
          now = new Date(now.valueOf() - 4);
        }
        return res;
      })();
      const datenow = new Date()
      for (let b = 0; b < 1250; b++) {
        timex.unshift((new Date(datenow.valueOf() - (b * 4))).toLocaleTimeString());//datenow.valueOf()返回datenow数组的值
        timex.pop();
      }
      return timex
    },
    chart(data,j,pIy,pV1y){
      this.time=this.timex()
      let p1Iy=[]
      let p1V1y=[]
      let color1='#92c2ff'
      let color2='#6EDDF1'
      for (let i = j; i < 1250+j; i++) {
        p1Iy.push(pIy[i])
        p1V1y.push(pV1y[i]-1)
      }
      //变色
      if(Number(data.GengSi) >=0.55||Number(data.FangChan)>=0.55
        ||Number(data.XSL_pr)>=0.55||Number(data.RRGC_pr)>=0.55||Number(data.ShiChan)>=0.55){
        color1='#ff0000'
        color2='#ff6600'
      }
      // p1Iy=p1Iy.reverse()
      // p1V1y=p1V1y.reverse()
      let option=({
        animation: true,
        animationDuration: 4750,
        animationEasing: "linear",
        animationEasingUpdate: 'linear',
        animationDurationUpdate: 5050,
        animationDelayUpdate: 0,
        animationThreshold: 5100,
        title: {
          text: '电位（mV）',
          textStyle: {
            fontSize: 13,
            color:"#66b3ff"
          },
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross'
          }
        },
        grid: {
          left: 17,
          right: 15,
          top: 20,
          bottom: 16
        },
        legend: {
          data: ['II导联', 'V1导联'],
          textStyle: {color: "#66b3ff"} /*图例(legend)说明文字的颜色*/ ,
          left: "right",
        },
        xAxis: {
          boundaryGap: true,
          data: this.time,
          axisLabel: { //修改坐标系字体颜色
            interval: 248,
            show: true,
            textStyle: {
              fontSize: 9,
              color: "#92c2ff",
            },
          },
          splitLine: {
            interval: 249,
            show: true,
            lineStyle: {opacity: 0.5,
              //color: "#ff0000"
            },
          } /*网格线*/
        },
        yAxis: {
          min: 1,
          max: -2,
          boundaryGap: false,
          splitNumber: 3,
          axisLabel: { //修改坐标系字体颜色
            show: false,
            textStyle: {
              color: "#8DB6DB"
            }
          },
          splitLine: {
            lineStyle: {
              opacity: 0.5,
              //color: "#ff0000"
            },
            show: true
          } /*网格线*/
        },
        series: [
          {
            sampling: 'average',
            itemStyle: {
              normal: {
                lineStyle: {//color: '#92c2ff' /*折线的颜色*/
                  color: color1 },
                color: color1 /*图例(legend)的颜色,不是图例说明文字的颜色*/
              }
            },
            symbol: "none",
            /*去掉小圆点*/
            name: 'II导联',
            emphasis: {focus: 'series'},
            type: 'line',
            data: p1Iy,
            smooth: 0
          },
          {
            itemStyle: {
              normal: {
                lineStyle: {color: color2 /*折线的颜色*/},
                color: color2 /*图例(legend)的颜色,不是图例说明文字的颜色*/
              }
            },
            symbol: "none",
            /*去掉小圆点*/
            name: 'V1导联',
            emphasis: {focus: 'series'},
            type: 'line',
            data: p1V1y,
            smooth: 0
          }]
      })
      return option

    },
    //加载中
    openLoading() {
      this.loading = this.$loading({
        lock: true,
        text: '加载中',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)',
        target:document.querySelector("#home")
      });
    },
    //进入详情
    jump(deviceSn){
      if(deviceSn){
        this.$router.push(
          {
            //添加需要传值到那个页面的路径
            path:'/large_screen_detail',
            //携带需要传递的参数
            query:{deviceSn:deviceSn,token : getToken()}
          })
      }
    },
    closeFullScreen(){
      this.loading.close();
    },
    //全屏
    inScreen(){
      this.isFullFlag=!this.isFullFlag
      const element = document.getElementById('home');//指定全屏区域元素
      if(screenfull.isEnabled && !screenfull.isFullscreen){
        // screenfull.request(element);
        screenfull.request(element)
        return
      }
      screenfull.toggle(element)

    },

  },
};
</script>

<style scoped lang="scss">
::v-deep .app-main{
  padding-top: 0!important;
}
::v-deep section{
  padding-top: 0!important;
}

::v-deep .el-pagination{
  width: 400px;
}
::v-deep .el-pagination .el-pager li{
  background-color: rgba(0,0,0,0);
  color:#4cc9f0 ;
  border: 1px solid #4cc9f0;
}
::v-deep .el-pagination .el-pager li:hover{
  background-color: #4cc9f0;
  color: white;
}
::v-deep .el-pager li.active{
  background-color: #4cc9f0;
  color: white;
}
::v-deep .el-pagination button{
  background-color: rgba(0,0,0,0);
  color:#4cc9f0 ;
  padding: 0;
  border: 1px solid #4cc9f0;
}
::v-deep .el-pagination button:hover{
  background-color: #4cc9f0;
  color: white;
}
.home{
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  .title {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    width: 100%;
    min-height:90px;
    height: 6vh;
    background-image: url("~@/assets/images/head.png");
    background-repeat: no-repeat;
    background-position: center center;
    background-attachment: scroll;
    background-size: cover;
    background-color:rgb(3,4,74);
    margin-bottom: 0;
    span{
      font-size: 45px;
      color: #6EDDF1;
      letter-spacing:6px;
      padding-left: 16vw;
    }
    .button{
      display: inline-block;
      .el-button{
        width: 70px;
        height: 30px;
        padding: 0;
        margin-left: 3px;
        margin-top: 3px;
        text-align: center;
        color: #4cc9f0;
        border-color: #4cc9f0;
        background-color: rgba(0,0,0,0);
      }
      .el-button:hover{
        color: white;
        background-color: #6EDDF1;
      }
    }
  }
}
.mainbox{
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: space-around;
  align-content: space-around;
  width:100%;
  min-height: 90vh;
  flex: 1;
  background-color: rgb(3,4,74);
  .container {
    width: 33%;
    height: 20%;
    min-height: 29.5vh;//21
    position: relative;
    border: 1px solid rgba(25, 186, 139, 0.17);
    background: url("~@/assets/images/container.png");
    padding: 0.3vw 0.1vw;
    margin-bottom: .5vh;
    position: relative;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    .event{
      display: flex;
      font-size: 1vw;
      flex-wrap: wrap;
      justify-content: center;
      color:#6EDDF1;
      .son{
        width: 25%;
        text-align: center;
        font-size: 0.8vw;
        span{
          display: inline-block;
          width: 0.7vw;
          height: 0.7vw;
          margin-left: 2px;
          border-radius: 50%;
          background-color: #62be38;
        }
      }

    }
    .containcontent{
      display: flex;
      flex-direction: row;
      justify-content: center;
      align-items: center;
      height: 20vh;
      .chart{
        width: 100%;
        height: 19vh;
      }
      .chartEvent{

      }
      .infleft{
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        margin-top: 15px;
        width: calc(20% - 15px);
        .hart{
          text-align: center;
          span{
            color: #6EDDF1;
            font-size: 3vw;
            border-radius: 6px;
            background-color: rgba(110,221,241,0.3);
          }
        }
        .name{
          min-width: 78px;
          text-align: center;
          font-size: 1.5vw;
          color: #6EDDF1;
          font-weight: bold;
          margin-bottom: 10px;
        }
      }
      .infleft:hover{
        cursor: pointer;
        .name{
          color: #bfe7ec;
          font-size: 1.6vw;
        }
      }
    }
    .containMsg{
      display: flex;
      flex-direction: row;
      justify-content: space-around;
      .name{
        font-size: 0.8vw;
        color: #6EDDF1;
        font-weight: bold;
        span{
          color: white;
        }
      }
    }
    .containMsg:hover{
      cursor: pointer;
      .name{
        font-size: 0.9vw;
        color: #bfe7ec;
        font-weight: bold;
      }
    }
    &::before {
      position: absolute;
      top: 0;
      left: 0;
      content: "";
      width: .5208vw;
      height: .5208vw;
      border-top: .1vw solid #02a6b5;
      border-left: .1vw solid #02a6b5;
    }
    &::after {
      position: absolute;
      top: 0;
      right: 0;
      content: "";
      width: .5208vw;
      height: .5208vw;
      border-top: .1vw solid #02a6b5;
      border-right: .1px solid #02a6b5;
    }
    .panel-footer {
      position: absolute;
      left: 0;
      bottom: 0;
      width: 100%;
      &::before {
        position: absolute;
        bottom: 0;
        left: 0;
        content: "";
        width: .5208vw;
        height: .5208vw;
        border-bottom: .1vw solid #02a6b5;
        border-left: .1vw solid #02a6b5;
      }
      &::after {
        position: absolute;
        bottom: 0;
        right: 0;
        content: "";
        width: .5208vw;
        height: .5208vw;
        border-bottom: .1vw solid #02a6b5;
        border-right: .1vw solid #02a6b5;
      }
    }

  }

}

</style>

