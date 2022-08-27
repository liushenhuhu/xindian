<template>
  <div class="wrap" >
    <div id="pdfDom" style="padding: 10px;">
      <div id="app">
        <div class="page">
          <div class="header">
            <div class="header-1">
              <div class="header-1-1">
                姓名: {{data.name}}
              </div>
              <div class="header-1-1">
                性别: {{ data.gender }}
              </div>
              <div class="header-1-1">
                年龄: {{ data.age }} 岁
              </div>
              <div class="header-1-1">
                送检科室: -
              </div>
              <div class="header-1-1">
                申请单号: -
              </div>
            </div>
            <div class="header-1">
              <div class="header-1-1">
                门诊号: -
              </div>
              <div class="header-1-1">
                住院号: -
              </div>
              <div class="header-1-1">
                病人编号: -
              </div>
              <div class="header-1-1">
                HR: - bpm
              </div>
              <div class="header-1-1">
                PR: - ms
              </div>
            </div>
            <div class="header-1">
              <div class="header-1-1">
               QRS: - ms
              </div>
              <div class="header-1-1">
                QT/QTc: - ms/ - ms
              </div>
              <div class="header-1-1">
                P/QRS/T: -/-/- deg
              </div>
              <div class="header-1-1">
                PV5/SV1: -/- mV
              </div>
              <div class="header-1-1">
                RV5+SV1: - mV
              </div>
            </div>
            <div class="header-1" style="flex: 1.5">
              <div class="header-1-1">
                诊断
              </div>
              <div class="header-1-1">
                <div
                  type="textarea"
                  contenteditable="true"
                  placeholder="请输入诊断结果"
                  class="box8-1-1"
                >{{ data.result }}
                </div>
              </div>
            </div>

          </div>
          <div class="body">
            <div class="body-1">
              <div id="I" class="line" ></div>
              <div id="II" class="line"></div>
              <div id="III" class="line"></div>
              <div id="aVR" class="line"></div>
              <div id="aVL" class="line"></div>
              <div id="aVF" class="line"></div>
            </div>
            <div class="body-1">
              <div id="V1" class="line" ></div>
              <div id="V2" class="line"></div>
              <div id="V3" class="line"></div>
              <div id="V4" class="line"></div>
              <div id="V5" class="line"></div>
              <div id="V6" class="line"></div>
            </div>
          </div>

        </div>
      </div>

    </div>
    <button type="button" style="margin-top: 20px;" @click="btnClick">导出PDF</button>
  </div>
</template>

<script>
import html2Canvas from 'html2canvas'
import JsPDF from 'jspdf'
import echarts from 'echarts'
import $ from 'jquery';

export default {
  name: "index",
  data() {
    return {
      exportPDFtitle: "页面导出PDF文件名",
      pId:null,
      data:{
        name:(JSON.parse(sessionStorage.getItem(this.$route.query.pId+"data"))).result.patientName,
        gender:(JSON.parse(sessionStorage.getItem(this.$route.query.pId+"data"))).result.gender,
        age:(JSON.parse(sessionStorage.getItem(this.$route.query.pId+"data"))).result.age,
        result:(JSON.parse(sessionStorage.getItem(this.$route.query.pId+"data"))).result.result
      }
    };
  },
  created() {
    console.log("创建")
     var pId = this.$route.query.pId;
    console.log(pId)
    if(pId){
       this.pId=pId;
      var show =sessionStorage.getItem(pId+"show");
      if (!show){
        this.get();
      }
    }
  },
  mounted() {
    this.I()
    this.II()
    this.III()
    this.aVR()
    this.aVL()
    this.aVF()
    this.V1()
    this.V2()
    this.V3()
    this.V4()
    this.V5()
    this.V6()

  },
  methods: {
    get(){
      const loading = this.$loading({
        lock: true,//lock的修改符--默认是false
        text: '请勿刷新页面，正在获取数据，请耐心等待1-3分钟...',//显示在加载图标下方的加载文案
        spinner: 'el-icon-loading',//自定义加载图标类名
        background: 'rgba(0, 0, 0, 0.7)',//遮罩层颜色
        target: document.querySelector('#table')//loadin覆盖的dom元素节点
      });
      var _th = this
      console.log("执行")
      console.log(this.pId)
      $.ajax({
        type: "post",
        url: "http://219.155.7.235:5003/detect_ecg",
        asynsc: false,
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({
          p_id: this.pId
        }),
        success: function (data) {
          console.log(_th.pId)
          console.log(data)
          sessionStorage.removeItem("data");
          sessionStorage.setItem(_th.pId+'data' ,JSON.stringify(data))
          sessionStorage.setItem(_th.pId+'show' ,true)
          loading.close()
          window.location.reload("#pdfDom");
        },
        error:function (data)
        {
          alert("数据请求错误,请刷新页面或联系管理员")
          loading.close()
          console.log("错误")
          console.log(_th.pId)
          console.log(data)
        }
      })
    },


    I(){
      var data = (JSON.parse(sessionStorage.getItem(this.pId+"data"))).result.data.I
      var x = new Array();
      for(var i = 0;i<data.length;i++){
        x.push(i);
      }
      var ecgBc = echarts.init(document.getElementById("I"));
      var option = {
        title:{
          text:"I",
          top: 30,
          left: 20,
        },
        grid: {
          left: '5',
          right: '0',
          top:'0',
          bottom:'0',
          containLabel: false
        },
        xAxis: {
          type : 'category',
          data: x,
          axisLabel: {
            show: false,
            interval: 4,
          },
          axisTick: {
            show: false
          },
          axisLine: {
            show: false
          },
          splitLine:{
            show: true, //让网格显示
            lineStyle: {//网格样式
              color:['pink'], //网格的颜色
              width:1, //网格的宽度
              type:'solid' //网格是实实线，可以修改成虚线以及其他的类型
            }
          }
        },
        yAxis : {
          type : 'value',
          axisLabel: {
            show: false,
          },
          axisTick: {
            show: false
          },
          axisLine: {
            show: false
          },
          //  splitNumber: 3, // 横线数
          interval: 0.1, // 刻度间隔
          splitLine:{
            show: true, //让网格显示
            lineStyle: {//网格样式
              color:['pink'], //网格的颜色
              width:1, //网格的宽度
              type:'solid' //网格是实实线，可以修改成虚线以及其他的类型
            }
          },
          max:0.8,
          min:-0.7
        },

        series : [
          {
            type: 'line',
            smooth: true,
            showSymbol : false,
            data: data,
            lineStyle : {
              normal : {
                color : "#000000",
                width : 1
              }
            }
          }
        ],
        graphic: [{
          type: 'image',
          left: '20',
          right: '2',
          top:'15',
          bottom:'20',
          z: -2,
          bounding: 'raw',
          style: {
            image: '../tools/images/ecg_long.png',
            height: 405
          }
        }]
      };
      ecgBc.setOption(option);
      $(window).resize(function(){
        ecgBc.resize();
      });
    },
    II(){
      var data = (JSON.parse(sessionStorage.getItem(this.pId+"data"))).result.data.II
      var x = new Array();
      for(var i = 0;i<data.length;i++){
        x.push(i);
      }
      var ecgBc = echarts.init(document.getElementById("II"));
      var option = {
        title:{
          text:"II",
          top: 30,
          left: 20,
        },
        grid: {
          left: '5',
          right: '0',
          top:'0',
          bottom:'0',
          containLabel: false
        },
        xAxis: {
          type : 'category',
          data: x,
          axisLabel: {
            show: false,
            interval: 4,
          },
          axisTick: {
            show: false
          },
          axisLine: {
            show: false
          },
          splitLine:{
            show: true, //让网格显示
            lineStyle: {//网格样式
              color:['pink'], //网格的颜色
              width:1, //网格的宽度
              type:'solid' //网格是实实线，可以修改成虚线以及其他的类型
            }
          }
        },
        yAxis : {
          type : 'value',
          axisLabel: {
            show: false,
          },
          axisTick: {
            show: false
          },
          axisLine: {
            show: false
          },
          //  splitNumber: 3, // 横线数
          interval: 0.1, // 刻度间隔
          splitLine:{
            show: true, //让网格显示
            lineStyle: {//网格样式
              color:['pink'], //网格的颜色
              width:1, //网格的宽度
              type:'solid' //网格是实实线，可以修改成虚线以及其他的类型
            }
          },
          max:0.8,
          min:-0.7
        },

        series : [
          {
            type: 'line',
            smooth: true,
            showSymbol : false,
            data: data,
            lineStyle : {
              normal : {
                color : "#000000",
                width : 1
              }
            }
          }
        ],
        graphic: [{
          type: 'image',
          left: '20',
          right: '2',
          top:'15',
          bottom:'20',
          z: -2,
          bounding: 'raw',
          style: {
            image: '../tools/images/ecg_long.png',
            height: 405
          }
        }]
      };
      ecgBc.setOption(option);
      $(window).resize(function(){
        ecgBc.resize();
      });
    },
    III(){
      var data = (JSON.parse(sessionStorage.getItem(this.pId+"data"))).result.data.III
      var x = new Array();
      for(var i = 0;i<data.length;i++){
        x.push(i);
      }
      var ecgBc = echarts.init(document.getElementById("III"));
      var option = {
        title:{
          text:"III",
          top: 30,
          left: 20,
        },
        grid: {
          left: '5',
          right: '0',
          top:'0',
          bottom:'0',
          containLabel: false
        },
        xAxis: {
          type : 'category',
          data: x,
          axisLabel: {
            show: false,
            interval: 4,
          },
          axisTick: {
            show: false
          },
          axisLine: {
            show: false
          },
          splitLine:{
            show: true, //让网格显示
            lineStyle: {//网格样式
              color:['pink'], //网格的颜色
              width:1, //网格的宽度
              type:'solid' //网格是实实线，可以修改成虚线以及其他的类型
            }
          }
        },
        yAxis : {
          type : 'value',
          axisLabel: {
            show: false,
          },
          axisTick: {
            show: false
          },
          axisLine: {
            show: false
          },
          //  splitNumber: 3, // 横线数
          interval: 0.1, // 刻度间隔
          splitLine:{
            show: true, //让网格显示
            lineStyle: {//网格样式
              color:['pink'], //网格的颜色
              width:1, //网格的宽度
              type:'solid' //网格是实实线，可以修改成虚线以及其他的类型
            }
          },
          max:0.8,
          min:-0.7
        },

        series : [
          {
            type: 'line',
            smooth: true,
            showSymbol : false,
            data: data,
            lineStyle : {
              normal : {
                color : "#000000",
                width : 1
              }
            }
          }
        ],
        graphic: [{
          type: 'image',
          left: '20',
          right: '2',
          top:'15',
          bottom:'20',
          z: -2,
          bounding: 'raw',
          style: {
            image: '../tools/images/ecg_long.png',
            height: 405
          }
        }]
      };
      ecgBc.setOption(option);
      $(window).resize(function(){
        ecgBc.resize();
      });
    },
    aVR(){
      var data = (JSON.parse(sessionStorage.getItem(this.pId+"data"))).result.data.aVR
      var x = new Array();
      for(var i = 0;i<data.length;i++){
        x.push(i);
      }
      var ecgBc = echarts.init(document.getElementById("aVR"));
      var option = {
        title:{
          text:"aVR",
          top: 30,
          left: 20,
        },
        grid: {
          left: '5',
          right: '0',
          top:'0',
          bottom:'0',
          containLabel: false
        },
        xAxis: {
          type : 'category',
          data: x,
          axisLabel: {
            show: false,
            interval: 4,
          },
          axisTick: {
            show: false
          },
          axisLine: {
            show: false
          },
          splitLine:{
            show: true, //让网格显示
            lineStyle: {//网格样式
              color:['pink'], //网格的颜色
              width:1, //网格的宽度
              type:'solid' //网格是实实线，可以修改成虚线以及其他的类型
            }
          }
        },
        yAxis : {
          type : 'value',
          axisLabel: {
            show: false,
          },
          axisTick: {
            show: false
          },
          axisLine: {
            show: false
          },
          //  splitNumber: 3, // 横线数
          interval: 0.1, // 刻度间隔
          splitLine:{
            show: true, //让网格显示
            lineStyle: {//网格样式
              color:['pink'], //网格的颜色
              width:1, //网格的宽度
              type:'solid' //网格是实实线，可以修改成虚线以及其他的类型
            }
          },
          max:0.8,
          min:-0.7
        },

        series : [
          {
            type: 'line',
            smooth: true,
            showSymbol : false,
            data: data,
            lineStyle : {
              normal : {
                color : "#000000",
                width : 1
              }
            }
          }
        ],
        graphic: [{
          type: 'image',
          left: '20',
          right: '2',
          top:'15',
          bottom:'20',
          z: -2,
          bounding: 'raw',
          style: {
            image: '../tools/images/ecg_long.png',
            height: 405
          }
        }]
      };
      ecgBc.setOption(option);
      $(window).resize(function(){
        ecgBc.resize();
      });
    },
    aVL(){
      var data = (JSON.parse(sessionStorage.getItem(this.pId+"data"))).result.data.aVL
      var x = new Array();
      for(var i = 0;i<data.length;i++){
        x.push(i);
      }
      var ecgBc = echarts.init(document.getElementById("aVL"));
      var option = {
        title:{
          text:"aVL",
          top: 30,
          left: 20,
        },
        grid: {
          left: '5',
          right: '0',
          top:'0',
          bottom:'0',
          containLabel: false
        },
        xAxis: {
          type : 'category',
          data: x,
          axisLabel: {
            show: false,
            interval: 4,
          },
          axisTick: {
            show: false
          },
          axisLine: {
            show: false
          },
          splitLine:{
            show: true, //让网格显示
            lineStyle: {//网格样式
              color:['pink'], //网格的颜色
              width:1, //网格的宽度
              type:'solid' //网格是实实线，可以修改成虚线以及其他的类型
            }
          }
        },
        yAxis : {
          type : 'value',
          axisLabel: {
            show: false,
          },
          axisTick: {
            show: false
          },
          axisLine: {
            show: false
          },
          //  splitNumber: 3, // 横线数
          interval: 0.1, // 刻度间隔
          splitLine:{
            show: true, //让网格显示
            lineStyle: {//网格样式
              color:['pink'], //网格的颜色
              width:1, //网格的宽度
              type:'solid' //网格是实实线，可以修改成虚线以及其他的类型
            }
          },
          max:0.8,
          min:-0.7
        },

        series : [
          {
            type: 'line',
            smooth: true,
            showSymbol : false,
            data: data,
            lineStyle : {
              normal : {
                color : "#000000",
                width : 1
              }
            }
          }
        ],
        graphic: [{
          type: 'image',
          left: '20',
          right: '2',
          top:'15',
          bottom:'20',
          z: -2,
          bounding: 'raw',
          style: {
            image: '../tools/images/ecg_long.png',
            height: 405
          }
        }]
      };
      ecgBc.setOption(option);
      $(window).resize(function(){
        ecgBc.resize();
      });
    },
    aVF(){
      var data = (JSON.parse(sessionStorage.getItem(this.pId+"data"))).result.data.aVF
      var x = new Array();
      for(var i = 0;i<data.length;i++){
        x.push(i);
      }
      var ecgBc = echarts.init(document.getElementById("aVF"));
      var option = {
        title:{
          text:"aVF",
          top: 30,
          left: 20,
        },
        grid: {
          left: '5',
          right: '0',
          top:'0',
          bottom:'0',
          containLabel: false
        },
        xAxis: {
          type : 'category',
          data: x,
          axisLabel: {
            show: false,
            interval: 4,
          },
          axisTick: {
            show: false
          },
          axisLine: {
            show: false
          },
          splitLine:{
            show: true, //让网格显示
            lineStyle: {//网格样式
              color:['pink'], //网格的颜色
              width:1, //网格的宽度
              type:'solid' //网格是实实线，可以修改成虚线以及其他的类型
            }
          }
        },
        yAxis : {
          type : 'value',
          axisLabel: {
            show: false,
          },
          axisTick: {
            show: false
          },
          axisLine: {
            show: false
          },
          //  splitNumber: 3, // 横线数
          interval: 0.1, // 刻度间隔
          splitLine:{
            show: true, //让网格显示
            lineStyle: {//网格样式
              color:['pink'], //网格的颜色
              width:1, //网格的宽度
              type:'solid' //网格是实实线，可以修改成虚线以及其他的类型
            }
          },
          max:0.8,
          min:-0.7
        },

        series : [
          {
            type: 'line',
            smooth: true,
            showSymbol : false,
            data: data,
            lineStyle : {
              normal : {
                color : "#000000",
                width : 1
              }
            }
          }
        ],
        graphic: [{
          type: 'image',
          left: '20',
          right: '2',
          top:'15',
          bottom:'20',
          z: -2,
          bounding: 'raw',
          style: {
            image: '../tools/images/ecg_long.png',
            height: 405
          }
        }]
      };
      ecgBc.setOption(option);
      $(window).resize(function(){
        ecgBc.resize();
      });
    },
    V1(){
      var data = (JSON.parse(sessionStorage.getItem(this.pId+"data"))).result.data.V1
      var x = new Array();
      for(var i = 0;i<data.length;i++){
        x.push(i);
      }
      var ecgBc = echarts.init(document.getElementById("V1"));
      var option = {
        title:{
          text:"V1",
          top: 30,
          left: 20,
        },
        grid: {
          left: '5',
          right: '0',
          top:'0',
          bottom:'0',
          containLabel: false
        },
        xAxis: {
          type : 'category',
          data: x,
          axisLabel: {
            show: false,
            interval: 4,
          },
          axisTick: {
            show: false
          },
          axisLine: {
            show: false
          },
          splitLine:{
            show: true, //让网格显示
            lineStyle: {//网格样式
              color:['pink'], //网格的颜色
              width:1, //网格的宽度
              type:'solid' //网格是实实线，可以修改成虚线以及其他的类型
            }
          }
        },
        yAxis : {
          type : 'value',
          axisLabel: {
            show: false,
          },
          axisTick: {
            show: false
          },
          axisLine: {
            show: false
          },
          //  splitNumber: 3, // 横线数
          interval: 0.1, // 刻度间隔
          splitLine:{
            show: true, //让网格显示
            lineStyle: {//网格样式
              color:['pink'], //网格的颜色
              width:1, //网格的宽度
              type:'solid' //网格是实实线，可以修改成虚线以及其他的类型
            }
          },
          max:0.8,
          min:-0.7
        },

        series : [
          {
            type: 'line',
            smooth: true,
            showSymbol : false,
            data: data,
            lineStyle : {
              normal : {
                color : "#000000",
                width : 1
              }
            }
          }
        ],
        graphic: [{
          type: 'image',
          left: '20',
          right: '2',
          top:'15',
          bottom:'20',
          z: -2,
          bounding: 'raw',
          style: {
            image: '../tools/images/ecg_long.png',
            height: 405
          }
        }]
      };
      ecgBc.setOption(option);
      $(window).resize(function(){
        ecgBc.resize();
      });
    },
    V2(){
      var data = (JSON.parse(sessionStorage.getItem(this.pId+"data"))).result.data.V2
      var x = new Array();
      for(var i = 0;i<data.length;i++){
        x.push(i);
      }
      var ecgBc = echarts.init(document.getElementById("V2"));
      var option = {
        title:{
          text:"V2",
          top: 30,
          left: 20,
        },
        grid: {
          left: '5',
          right: '0',
          top:'0',
          bottom:'0',
          containLabel: false
        },
        xAxis: {
          type : 'category',
          data: x,
          axisLabel: {
            show: false,
            interval: 4,
          },
          axisTick: {
            show: false
          },
          axisLine: {
            show: false
          },
          splitLine:{
            show: true, //让网格显示
            lineStyle: {//网格样式
              color:['pink'], //网格的颜色
              width:1, //网格的宽度
              type:'solid' //网格是实实线，可以修改成虚线以及其他的类型
            }
          }
        },
        yAxis : {
          type : 'value',
          axisLabel: {
            show: false,
          },
          axisTick: {
            show: false
          },
          axisLine: {
            show: false
          },
          //  splitNumber: 3, // 横线数
          interval: 0.1, // 刻度间隔
          splitLine:{
            show: true, //让网格显示
            lineStyle: {//网格样式
              color:['pink'], //网格的颜色
              width:1, //网格的宽度
              type:'solid' //网格是实实线，可以修改成虚线以及其他的类型
            }
          },
          max:0.8,
          min:-0.7
        },

        series : [
          {
            type: 'line',
            smooth: true,
            showSymbol : false,
            data: data,
            lineStyle : {
              normal : {
                color : "#000000",
                width : 1
              }
            }
          }
        ],
        graphic: [{
          type: 'image',
          left: '20',
          right: '2',
          top:'15',
          bottom:'20',
          z: -2,
          bounding: 'raw',
          style: {
            image: '../tools/images/ecg_long.png',
            height: 405
          }
        }]
      };
      ecgBc.setOption(option);
      $(window).resize(function(){
        ecgBc.resize();
      });
    },
    V3(){
      var data = (JSON.parse(sessionStorage.getItem(this.pId+"data"))).result.data.V3
      var x = new Array();
      for(var i = 0;i<data.length;i++){
        x.push(i);
      }
      var ecgBc = echarts.init(document.getElementById("V3"));
      var option = {
        title:{
          text:"V3",
          top: 30,
          left: 20,
        },
        grid: {
          left: '5',
          right: '0',
          top:'0',
          bottom:'0',
          containLabel: false
        },
        xAxis: {
          type : 'category',
          data: x,
          axisLabel: {
            show: false,
            interval: 4,
          },
          axisTick: {
            show: false
          },
          axisLine: {
            show: false
          },
          splitLine:{
            show: true, //让网格显示
            lineStyle: {//网格样式
              color:['pink'], //网格的颜色
              width:1, //网格的宽度
              type:'solid' //网格是实实线，可以修改成虚线以及其他的类型
            }
          }
        },
        yAxis : {
          type : 'value',
          axisLabel: {
            show: false,
          },
          axisTick: {
            show: false
          },
          axisLine: {
            show: false
          },
          //  splitNumber: 3, // 横线数
          interval: 0.1, // 刻度间隔
          splitLine:{
            show: true, //让网格显示
            lineStyle: {//网格样式
              color:['pink'], //网格的颜色
              width:1, //网格的宽度
              type:'solid' //网格是实实线，可以修改成虚线以及其他的类型
            }
          },
          max:0.8,
          min:-0.7
        },

        series : [
          {
            type: 'line',
            smooth: true,
            showSymbol : false,
            data: data,
            lineStyle : {
              normal : {
                color : "#000000",
                width : 1
              }
            }
          }
        ],
        graphic: [{
          type: 'image',
          left: '20',
          right: '2',
          top:'15',
          bottom:'20',
          z: -2,
          bounding: 'raw',
          style: {
            image: '../tools/images/ecg_long.png',
            height: 405
          }
        }]
      };
      ecgBc.setOption(option);
      $(window).resize(function(){
        ecgBc.resize();
      });
    },
    V4(){
      var data = (JSON.parse(sessionStorage.getItem(this.pId+"data"))).result.data.V4
      var x = new Array();
      for(var i = 0;i<data.length;i++){
        x.push(i);
      }
      var ecgBc = echarts.init(document.getElementById("V4"));
      var option = {
        title:{
          text:"V4",
          top: 30,
          left: 20,
        },
        grid: {
          left: '5',
          right: '0',
          top:'0',
          bottom:'0',
          containLabel: false
        },
        xAxis: {
          type : 'category',
          data: x,
          axisLabel: {
            show: false,
            interval: 4,
          },
          axisTick: {
            show: false
          },
          axisLine: {
            show: false
          },
          splitLine:{
            show: true, //让网格显示
            lineStyle: {//网格样式
              color:['pink'], //网格的颜色
              width:1, //网格的宽度
              type:'solid' //网格是实实线，可以修改成虚线以及其他的类型
            }
          }
        },
        yAxis : {
          type : 'value',
          axisLabel: {
            show: false,
          },
          axisTick: {
            show: false
          },
          axisLine: {
            show: false
          },
          //  splitNumber: 3, // 横线数
          interval: 0.1, // 刻度间隔
          splitLine:{
            show: true, //让网格显示
            lineStyle: {//网格样式
              color:['pink'], //网格的颜色
              width:1, //网格的宽度
              type:'solid' //网格是实实线，可以修改成虚线以及其他的类型
            }
          },
          max:0.8,
          min:-0.7
        },

        series : [
          {
            type: 'line',
            smooth: true,
            showSymbol : false,
            data: data,
            lineStyle : {
              normal : {
                color : "#000000",
                width : 1
              }
            }
          }
        ],
        graphic: [{
          type: 'image',
          left: '20',
          right: '2',
          top:'15',
          bottom:'20',
          z: -2,
          bounding: 'raw',
          style: {
            image: '../tools/images/ecg_long.png',
            height: 405
          }
        }]
      };
      ecgBc.setOption(option);
      $(window).resize(function(){
        ecgBc.resize();
      });
    },
    V5(){
      var data = (JSON.parse(sessionStorage.getItem(this.pId+"data"))).result.data.V5
      var x = new Array();
      for(var i = 0;i<data.length;i++){
        x.push(i);
      }
      var ecgBc = echarts.init(document.getElementById("V5"));
      var option = {
        title:{
          text:"V5",
          top: 30,
          left: 20,
        },
        grid: {
          left: '5',
          right: '0',
          top:'0',
          bottom:'0',
          containLabel: false
        },
        xAxis: {
          type : 'category',
          data: x,
          axisLabel: {
            show: false,
            interval: 4,
          },
          axisTick: {
            show: false
          },
          axisLine: {
            show: false
          },
          splitLine:{
            show: true, //让网格显示
            lineStyle: {//网格样式
              color:['pink'], //网格的颜色
              width:1, //网格的宽度
              type:'solid' //网格是实实线，可以修改成虚线以及其他的类型
            }
          }
        },
        yAxis : {
          type : 'value',
          axisLabel: {
            show: false,
          },
          axisTick: {
            show: false
          },
          axisLine: {
            show: false
          },
          //  splitNumber: 3, // 横线数
          interval: 0.1, // 刻度间隔
          splitLine:{
            show: true, //让网格显示
            lineStyle: {//网格样式
              color:['pink'], //网格的颜色
              width:1, //网格的宽度
              type:'solid' //网格是实实线，可以修改成虚线以及其他的类型
            }
          },
          max:0.8,
          min:-0.7
        },

        series : [
          {
            type: 'line',
            smooth: true,
            showSymbol : false,
            data: data,
            lineStyle : {
              normal : {
                color : "#000000",
                width : 1
              }
            }
          }
        ],
        graphic: [{
          type: 'image',
          left: '20',
          right: '2',
          top:'15',
          bottom:'20',
          z: -2,
          bounding: 'raw',
          style: {
            image: '../tools/images/ecg_long.png',
            height: 405
          }
        }]
      };
      ecgBc.setOption(option);
      $(window).resize(function(){
        ecgBc.resize();
      });
    },
    V6(){
      var data = (JSON.parse(sessionStorage.getItem(this.pId+"data"))).result.data.V6
      var x = new Array();
      for(var i = 0;i<data.length;i++){
        x.push(i);
      }
      var ecgBc = echarts.init(document.getElementById("V6"));
      var option = {
        title:{
          text:"V6",
          top: 30,
          left: 20,
        },
        grid: {
          left: '5',
          right: '0',
          top:'0',
          bottom:'0',
          containLabel: false
        },
        xAxis: {
          type : 'category',
          data: x,
          axisLabel: {
            show: false,
            interval: 4,
          },
          axisTick: {
            show: false
          },
          axisLine: {
            show: false
          },
          splitLine:{
            show: true, //让网格显示
            lineStyle: {//网格样式
              color:['pink'], //网格的颜色
              width:1, //网格的宽度
              type:'solid' //网格是实实线，可以修改成虚线以及其他的类型
            }
          }
        },
        yAxis : {
          type : 'value',
          axisLabel: {
            show: false,
          },
          axisTick: {
            show: false
          },
          axisLine: {
            show: false
          },
          //  splitNumber: 3, // 横线数
          interval: 0.1, // 刻度间隔
          splitLine:{
            show: true, //让网格显示
            lineStyle: {//网格样式
              color:['pink'], //网格的颜色
              width:1, //网格的宽度
              type:'solid' //网格是实实线，可以修改成虚线以及其他的类型
            }
          },
          max:0.8,
          min:-0.7
        },

        series : [
          {
            type: 'line',
            smooth: true,
            showSymbol : false,
            data: data,
            lineStyle : {
              normal : {
                color : "#000000",
                width : 1
              }
            }
          }
        ],
        graphic: [{
          type: 'image',
          left: '20',
          right: '2',
          top:'15',
          bottom:'20',
          z: -2,
          bounding: 'raw',
          style: {
            image: '../tools/images/ecg_long.png',
            height: 405
          }
        }]
      };
      ecgBc.setOption(option);
      $(window).resize(function(){
        ecgBc.resize();
      });
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
        let pageHeight = contentWidth / 841.89 * 592.28;
        // 未生成pdf的html页面高度
        let leftHeight = contentHeight;
        // 页面偏移
        let position = 0;
        // html页面生成的canvas在pdf中图片的宽高（本例为：横向a4纸[841.89,592.28]，纵向需调换尺寸）
        let imgWidth = 841.89;
        let imgHeight = 841.89 / contentWidth * contentHeight;
        let pageData = canvas.toDataURL('image/jpeg', 1.0);
        let PDF = new JsPDF('l', 'pt', 'a4');
        // 两个高度需要区分: 一个是html页面的实际高度，和生成pdf的页面高度
        // 当内容未超过pdf一页显示的范围，无需分页
        if (leftHeight < pageHeight) {
          PDF.addImage(pageData, 'JPEG', 0, 0, imgWidth, imgHeight)
        } else {
          while (leftHeight > 0) {
            PDF.addImage(pageData, 'JPEG', 0, position, imgWidth, imgHeight)
            leftHeight -= pageHeight;
            position -= 592.28;
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

<style lang="scss" scoped>
*{
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.page{
  padding-top: 50px ;
  padding-bottom: 50px;
  margin-top: 10px;
  margin-bottom: 10px;
  width: 100%;
  height: 1345px;
  border: 3px solid #0000ff;
}
.header{
  display: flex;
  justify-content: space-between;
  margin-bottom: 3vh;
}

.header-1{
  float: left;
  padding-left: 3vw;
}

.body{
  display: flex;
}
.body .body-1{
  flex: 1;
}

.line{
  height:112.5px;
  margin: 0;
  padding: 0
}
</style>
