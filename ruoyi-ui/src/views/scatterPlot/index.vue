<template>
  <div class="app-container">
    <div class="fanhuitiao">
      <el-page-header @back="goBack" content="30天趋势图"></el-page-header>
      <el-col :span="1.5">
          <el-button
            style="margin-right: 1vw;"
            type="success"
            plain
            icon="el-icon-view"
            size="mini"
            @click="isShowNameClick"
          >{{isShowName.name}}
          </el-button>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getData"></right-toolbar>
      </el-col>

    </div>

    <el-form :model="queryParams" style="margin-top: 20px" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="开始时间" prop="startTime">
        <el-date-picker
                        v-model="queryParams.startTime"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择开始时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="结束时间" prop="endTime">
        <el-date-picker
                        v-model="queryParams.endTime"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择结束时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>



  <el-descriptions title="">
    <el-descriptions-item label="用户姓名">{{isShowName.status?this.queryParams.patientName:this.patientName}}</el-descriptions-item>
    <el-descriptions-item label="手机号">{{isShowName.status?this.queryParams.patientPhone:this.patientPhone}}</el-descriptions-item>
    <el-descriptions-item label="性别">{{patientSex}}</el-descriptions-item>
  </el-descriptions>



    <div class="main-flex" id="main">
      <div class="row">
        <el-card>
          <div class="chart" id="chart1" style="width: 100%; height:100%"></div>
        </el-card>
        <el-card>
          <div class="chart" id="chart2" ></div>
        </el-card>
      </div>
      <!-- <div class="row">
        
      </div> -->
      <div class="row">
        <el-card>
          <div class="chart" id="chart3"></div>
        </el-card>
        <el-card>
          <div class="chart" id="chart4"></div>
        </el-card>
      </div>
      <div class="row">
        <el-card>
          <div class="chart" id="chart5"></div>
        </el-card>
        <el-card>
          <div class="chart" id="chart6"></div>
        </el-card>
      </div>
      <div class="row">
        <el-card>
          <div class="chart" id="chart7"></div>
        </el-card>
        <el-card>
          <div class="chart" id="chart8"></div>
        </el-card>
      </div>
      <div class="row">
        <el-card>
          <div class="chart" id="chart9"></div>
        </el-card>
        <el-card>
          <div class="chart" id="chart10"></div>
        </el-card>
      </div>
      <div class="row">
        <el-card>
          <div class="chart" id="chart11"></div>
        </el-card>
        <el-card>
          <div class="chart" id="chart12"></div>
        </el-card>
      </div>
      <div class="row">
        <el-card>
          <div class="chart" id="chart13"></div>
        </el-card>
      </div>
    </div>
    <el-dialog title="密码验证" :visible.sync="dialogFormVisibleVerifyAuthority">
      <el-form :model="verifyForm" :rules="rules" ref="verifyForm">
        <el-form-item label="验证密码" prop="password">
          <el-input placeholder="请输入密码" v-model="verifyForm.password" show-password></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisibleVerifyAuthority = false">取 消</el-button>
        <el-button type="primary" @click="dialogFormVisibleVerify">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import * as echarts from "@/views/ECGScreen/detail/echarts.min"
import {getHrCount} from "@/api/scatterPlot/scatterPlot";
import {getVerify} from "@/api/verify/verify";

export default {
  name: "ScatterPlot",
  data() {
    return {
      patientSex:'',
      queryParams:{
        patientPhone:null,
        startTime:null,
        endTime:null,
        patientName:null
      },
      patientPhone:null,
      patientName:null,
      dialogFormVisibleVerifyAuthority:false,
      showSearch:true,
      chart1:null,
      chart2:null,
      chart3:null,
      chart4:null,
      chart5:null,
      chart6:null,
      chart7:null,
      chart8:null,
      chart9:null,
      chart10:null,
      chart11:null,
      chart12:null,
      chart13:null,
      ecgType:1,//1：静态单导  2：静态12导
      isShowName:{
        status:false,
        name:"显示姓名"
      },
      verifyForm:{
        password:null,
        status:false
      },
      // 表单校验
      rules: {
        password: [
          {required: true, message: "密码不能为空", trigger: "blur"}
        ]
      }
    }
  },
  created() {
    let currentURL = window.location.href;
    console.log(currentURL);
    // 使用URL对象解析URL
    let urlObject = new URL(currentURL);

    // 获取路径名部分
    let pathName = urlObject.pathname;

    // 提取/restingECG字符串并在前面加上斜杠
    let luyou = '/' + pathName.split('/')[1];
    // console.log(luyou);

  var getdata = JSON.parse(sessionStorage.getItem(luyou));
  if (getdata) {
    // console.log(getdata)


    // const limitTo11Digits = number => parseInt(number.toString().substr(0, 11), 10);
    // let limitedNumber = limitTo11Digits(getdata.row.patientPhone);


    var tel = getdata.row.patientPhone; // 获取电话号码
    let a = tel.toString().slice(0, 11); // 只保留前11位
    var telPrefix = tel.slice(0, 3); // 获取前3位数字或字符串
    var telHidden = telPrefix + '*'.repeat(Math.max(0, tel.length - 3)); // 用 * 代替剩余部分
    this.patientPhone = telHidden;

      this.ecgType=getdata.ecgType
      this.queryParams.patientPhone = a
      this.queryParams.patientName = getdata.row.patientName
      this.patientSex = getdata.row.patientSex

      // var tel = this.queryParams.patientPhone;
      // tel = "" + tel;
      // var ary = tel.split("");
      // var telPrefix = ary.slice(0, 3); // 获取前3位数字或字符串
      // var telHidden = telPrefix + '*'.repeat(Math.max(0, tel.length - 3)); // 用 * 代替剩余部分
      // // ary.splice(3,"********");
      // var tel1=telHidden.join("");
      // // console.log(tel1);


      // this.patientPhone = this.queryParams.patientPhone.slice(0, -4) + '****'
      // this.patientPhone = tel1


      this.patientName= '***'
  } else {
    //永远不会到达这里
    var tel = this.$route.query.row.patientPhone; // 获取电话号码
    let a = tel.toString().slice(0, 11); // 只保留前11位
    var telPrefix = tel.slice(0, 3); // 获取前3位数字或字符串
    var telHidden = telPrefix + '*'.repeat(Math.max(0, tel.length - 3)); // 用 * 代替剩余部分
    this.patientPhone = telHidden;

    
    // const limitTo11Digits = number => parseInt(number.toString().substr(0, 11), 10);
    // let limitedNumber = limitTo11Digits(this.$route.query.row.patientPhone);
    this.ecgType=this.$route.query.ecgType
    this.queryParams.patientPhone = a
    this.queryParams.patientName = this.$route.query.row.patientName
    this.patientSex =this.$route.query.row.patientSex


    

    // var tel = this.queryParams.patientPhone;
    // tel = "" + tel;
    // var ary = tel.split("");
    // ary.splice(3,4,"****");
    // var tel1=ary.join("");
    // this.patientPhone = this.queryParams.patientPhone.slice(0, -4) + '****'
    // this.patientPhone = tel1

    this.patientName= '***'
  }

  },
  mounted() {
    // this.getData()
    this.chart1 = echarts.init(document.getElementById('chart1'));
    this.chart2 = echarts.init(document.getElementById('chart2'));
    this.chart3 = echarts.init(document.getElementById('chart3'));
    this.chart4 = echarts.init(document.getElementById('chart4'));
    this.chart5 = echarts.init(document.getElementById('chart5'));
    this.chart6 = echarts.init(document.getElementById('chart6'));
    this.chart7 = echarts.init(document.getElementById('chart7'));
    this.chart8 = echarts.init(document.getElementById('chart8'));
    this.chart9 = echarts.init(document.getElementById('chart9'));
    this.chart10 = echarts.init(document.getElementById('chart10'));
    this.chart11 = echarts.init(document.getElementById('chart11'));
    this.chart12 = echarts.init(document.getElementById('chart12'));
    this.chart13 = echarts.init(document.getElementById('chart13'));
    window.addEventListener("resize", () => {
      this.chart1.resize()
      this.chart2.resize()
      this.chart3.resize()
      this.chart4.resize()
      this.chart5.resize()
      this.chart6.resize()
      this.chart7.resize()
      this.chart8.resize()
      this.chart9.resize()
      this.chart10.resize()
      this.chart11.resize()
      this.chart12.resize()
      this.chart13.resize()
    });
    // 获取目标元素
    const resizeBox = document.getElementById('main');

    // 创建 ResizeObserver 实例
    const resizeObserver = new ResizeObserver(entries => {
      for (let entry of entries) {
        // const {width, height} = entry.contentRect;
        // console.log(`尺寸变化：宽度=${width}, 高度=${height}`);
        // 在此处根据尺寸变化执行操作
        setTimeout(()=>{
          this.chart1.resize()
          this.chart2.resize()
          this.chart3.resize()
          this.chart4.resize()
          this.chart5.resize()
          this.chart6.resize()
          this.chart7.resize()
          this.chart8.resize()
          this.chart9.resize()
          this.chart10.resize()
          this.chart11.resize()
          this.chart12.resize()
          this.chart13.resize()
          let width=this.chart13.getWidth()
          this.chart13.resize( {height: width})
        },50)
      }
    });
    // 开始观察
    resizeObserver.observe(resizeBox);
    this.queryParams.endTime=this.GetDateStr(0)
    this.queryParams.startTime=this.GetDateStr(-30)
    this.getData()
  },
  methods: {
    isShowNameClick(){
      let isShowName =  sessionStorage.getItem('isShowName')
      if (this.verifyForm.status || isShowName){
        if (this.isShowName.status){
          this.isShowName.status = !this.isShowName.status;

          this.isShowName.name = "显示姓名"
        }else {
          this.isShowName.status =!this.isShowName.status;
          this.isShowName.name = "隐藏姓名"

        }
      }else {
        this.verifyForm.password=''
        this.dialogFormVisibleVerifyAuthority = true
      }

    },
    dialogFormVisibleVerify(){
      this.$refs["verifyForm"].validate(valid => {
        if (valid) {
          let obj = {
            accountPwd:this.verifyForm.password
          }
          getVerify(obj).then(r=>{
            this.$modal.msgSuccess("密码正确");
            this.verifyForm.status=true
            sessionStorage.setItem('isShowName',true)
            this.dialogFormVisibleVerifyAuthority = false
            this.isShowName.status =!this.isShowName.status;
            this.isShowName.name = "隐藏姓名"
          })
        }
      })
    },
    getData(){
      // this.queryParams.patientPhone = this.$route.query.row.patientPhone
      getHrCount(this.queryParams).then(res=>{
        this.setChart1(res.data.PR_interval)
        this.setChart2(res.data.P_time)
        this.setChart3(res.data.QRS_interval)
        this.setChart4(res.data.QTc)
        this.setChart5(res.data.RMSSD)
        this.setChart6(res.data.hr_mean)
        this.setChart7(res.data.P_amplitude)
        this.setChart8(res.data.R_amplitude)
        this.setChart9(res.data.T_amplitude)
        this.setChart10(res.data.T_time)
        this.setChart11(res.data.SDNN)
        this.setChart12(res.data.QT_interval)
        this.setChart13(res.data.nni)
      })
    },
    goBack() {
      this.$router.go(-1)
    },
    GetDateStr(AddDayCount){
      var dd = new Date();
      dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期
      var y = dd.getFullYear();
      var m = (dd.getMonth()+1)<10?"0"+(dd.getMonth()+1):(dd.getMonth()+1);//获取当前月份的日期，不足10补0
      var d = dd.getDate()<10?"0"+dd.getDate():dd.getDate();//获取当前几号，不足10补0
      return y+"-"+m+"-"+d;
    },
    setChart1(data){
      var option = {
        title:{
          text:'PR间期(ms)'
        },
        grid:{
          left:'2%',
          top:'16%',
          right:'5%',
          bottom:'2%',
          containLabel:true
        },
        tooltip:{
          show:true,
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          axisLabel:{
            formatter:function (value, index) {
              return value.split(' ')[0];
            }
          }
        },
        yAxis: {
          min:0,
          max:400,
        },
        series: [
          {
            symbolSize: 7,
            data:data,
            type: 'scatter',
            itemStyle: {
              color: "rgba(224, 67, 67, 1)"
            },
          }
        ]
      };
      this.chart1.clear()
      this.chart1.setOption(option);
      this.chart1.resize()
      this.chart1.on('click', (params)=>{
        this.$router.push({
          path:this.ecgType==1?'/staticECG':'/restingECG',
          query:{pId:params.data[2]}
        })
      })
    },
    setChart2(data){
      var option = {
        title:{
          text:'P波(ms)'
        },
        grid:{
          left:'2%',
          top:'16%',
          right:'5%',
          bottom:'2%',
          containLabel:true
        },
        tooltip:{
          show:true
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          axisLabel:{
            formatter:function (value, index) {
              return value.split(' ')[0];
            }
          }
        },
        yAxis: {
          min:0,
          max:200,
        },
        series: [
          {
            symbolSize: 7,
            data:data,
            type: 'scatter',
            itemStyle: {
              color: "rgba(224, 67, 67, 1)"
            },
          }
        ]
      };
      this.chart2.clear()
      this.chart2.setOption(option);
      this.chart2.resize()
      this.chart2.on('click', (params)=>{
        this.$router.push({
          path:this.ecgType==1?'/staticECG':'/restingECG',
          query:{pId:params.data[2]}
        })
      })
    },
    setChart3(data){
      var option = {
        title:{
          text:'QRS波群(ms)'
        },
        grid:{
          left:'2%',
          top:'16%',
          right:'5%',
          bottom:'2%',
          containLabel:true
        },
        tooltip:{
          show:true
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          axisLabel:{
            formatter:function (value, index) {
              return value.split(' ')[0];
            }
          }
        },
        yAxis: {
          min:0,
          max:200,
        },
        series: [
          {
            symbolSize: 7,
            type: 'scatter',
            data:data,
            itemStyle: {
              color: "rgba(224, 67, 67, 1)"
            },
          }
        ]
      };
      this.chart3.clear()
      this.chart3.setOption(option);
      this.chart3.resize()
      this.chart3.on('click', (params)=>{
        this.$router.push({
          path:this.ecgType==1?'/staticECG':'/restingECG',
          query:{pId:params.data[2]}
        })
      })
    },
    setChart4(data){
      var option = {
        title:{
          text:'QTc(ms)'
        },
        grid:{
          left:'2%',
          top:'16%',
          right:'5%',
          bottom:'2%',
          containLabel:true
        },
        tooltip:{
          show:true
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          axisLabel:{
            formatter:function (value, index) {
              return value.split(' ')[0];
            }
          }
        },
        yAxis: {
          min:0,
          max:400,
        },
        series: [
          {
            symbolSize: 7,
            data:data,
            type: 'scatter',
            itemStyle: {
              color: "rgba(224, 67, 67, 1)"
            },
          }
        ]
      };
      this.chart4.clear()
      this.chart4.setOption(option);
      this.chart4.resize()
      this.chart4.on('click', (params)=>{
        this.$router.push({
          path:this.ecgType==1?'/staticECG':'/restingECG',
          query:{pId:params.data[2]}
        })
      })
    },
    setChart5(data){
      var option = {
        title:{
          text:'HRV(ms)'
        },
        grid:{
          left:'2%',
          top:'16%',
          right:'5%',
          bottom:'2%',
          containLabel:true
        },
        tooltip:{
          show:true
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          axisLabel:{
            formatter:function (value, index) {
              return value.split(' ')[0];
            }
          }
        },
        yAxis: {
          min:0,
          max:400,
        },
        series: [
          {
            symbolSize: 7,
            data:data,
            type: 'scatter',
            itemStyle: {
              color: "rgba(224, 67, 67, 1)"
            },
          }
        ]
      };
      this.chart5.clear()
      this.chart5.setOption(option);
      this.chart5.resize()
      this.chart5.on('click', (params)=>{
        this.$router.push({
          path:this.ecgType==1?'/staticECG':'/restingECG',
          query:{pId:params.data[2]}
        })
      })
    },
    setChart6(data){
      var option = {
        title:{
          text:'心率(bpm)'
        },
        grid:{
          left:'2%',
          top:'16%',
          right:'5%',
          bottom:'2%',
          containLabel:true
        },
        tooltip:{
          show:true
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          axisLabel:{
            formatter:function (value, index) {
              return value.split(' ')[0];
            }
          }
        },
        yAxis: {
          min:0,
          max:200,
        },
        series: [
          {
            symbolSize: 7,
            data:data,
            type: 'scatter',
            itemStyle: {
              color: "rgba(224, 67, 67, 1)"
            },
          }
        ]
      };
      this.chart6.clear()
      this.chart6.setOption(option);
      this.chart6.resize()
      this.chart6.on('click', (params)=>{
        this.$router.push({
          path:this.ecgType==1?'/staticECG':'/restingECG',
          query:{pId:params.data[2]}
        })
      })
    },
    setChart7(data){
      var option = {
        title:{
          text:'P波振幅(mv)'
        },
        grid:{
          left:'2%',
          top:'16%',
          right:'5%',
          bottom:'2%',
          containLabel:true
        },
        tooltip:{
          show:true
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          axisLabel:{
            formatter:function (value, index) {
              return value.split(' ')[0];
            }
          }
        },
        yAxis: {
        },
        series: [
          {
            symbolSize: 7,
            data:data,
            type: 'scatter',
            itemStyle: {
              color: "rgba(224, 67, 67, 1)"
            },
          }
        ]
      };
      this.chart7.clear()
      this.chart7.setOption(option);
      this.chart7.resize()
      this.chart7.on('click', (params)=>{
        this.$router.push({
          path:this.ecgType==1?'/staticECG':'/restingECG',
          query:{pId:params.data[2]}
        })
      })
    },
    setChart8(data){
      var option = {
        title:{
          text:'R波振幅(mv)'
        },
        grid:{
          left:'2%',
          top:'16%',
          right:'5%',
          bottom:'2%',
          containLabel:true
        },
        tooltip:{
          show:true
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          axisLabel:{
            formatter:function (value, index) {
              return value.split(' ')[0];
            }
          }
        },
        yAxis: {
        },
        series: [
          {
            symbolSize: 7,
            data:data,
            type: 'scatter',
            itemStyle: {
              color: "rgba(224, 67, 67, 1)"
            },
          }
        ]
      };
      this.chart8.clear()
      this.chart8.setOption(option);
      this.chart8.resize()
      this.chart8.on('click', (params)=>{
        this.$router.push({
          path:this.ecgType==1?'/staticECG':'/restingECG',
          query:{pId:params.data[2]}
        })
      })
    },
    setChart9(data){
      var option = {
        title:{
          text:'T波振幅(mv)'
        },
        grid:{
          left:'2%',
          top:'16%',
          right:'5%',
          bottom:'2%',
          containLabel:true
        },
        tooltip:{
          show:true
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          axisLabel:{
            formatter:function (value, index) {
              return value.split(' ')[0];
            }
          }
        },
        yAxis: {
        },
        series: [
          {
            symbolSize: 7,
            data:data,
            type: 'scatter',
            itemStyle: {
              color: "rgba(224, 67, 67, 1)"
            },
          }
        ]
      };
      this.chart9.clear()
      this.chart9.setOption(option);
      this.chart9.resize()
      this.chart9.on('click', (params)=>{
        this.$router.push({
          path:this.ecgType==1?'/staticECG':'/restingECG',
          query:{pId:params.data[2]}
        })
      })
    },
    setChart10(data){
      var option = {
        title:{
          text:'T波时限(ms)'
        },
        grid:{
          left:'2%',
          top:'16%',
          right:'5%',
          bottom:'2%',
          containLabel:true
        },
        tooltip:{
          show:true
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          axisLabel:{
            formatter:function (value, index) {
              return value.split(' ')[0];
            }
          }
        },
        yAxis: {
        },
        series: [
          {
            symbolSize: 7,
            data:data,
            type: 'scatter',
            itemStyle: {
              color: "rgba(224, 67, 67, 1)"
            },
          }
        ]
      };
      this.chart10.clear()
      this.chart10.setOption(option);
      this.chart10.resize()
      this.chart10.on('click', (params)=>{
        this.$router.push({
          path:this.ecgType==1?'/staticECG':'/restingECG',
          query:{pId:params.data[2]}
        })
      })
    },
    setChart11(data){
      var option = {
        title:{
          text:'SDNN(ms)'
        },
        grid:{
          left:'2%',
          top:'16%',
          right:'5%',
          bottom:'2%',
          containLabel:true
        },
        tooltip:{
          show:true
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          axisLabel:{
            formatter:function (value, index) {
              return value.split(' ')[0];
            }
          }
        },
        yAxis: {
        },
        series: [
          {
            symbolSize: 7,
            data:data,
            type: 'scatter',
            itemStyle: {
              color: "rgba(224, 67, 67, 1)"
            },
          }
        ]
      };
      this.chart11.clear()
      this.chart11.setOption(option);
      this.chart11.resize()
      this.chart11.on('click', (params)=>{
        this.$router.push({
          path:this.ecgType==1?'/staticECG':'/restingECG',
          query:{pId:params.data[2]}
        })
      })
    },
    setChart12(data){
      var option = {
        title:{
          text:'QT间期(ms)'
        },
        grid:{
          left:'2%',
          top:'16%',
          right:'5%',
          bottom:'2%',
          containLabel:true
        },
        tooltip:{
          show:true
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          axisLabel:{
            formatter:function (value, index) {
              return value.split(' ')[0];
            }
          }
        },
        yAxis: {
          min:0,
          max:400,
        },
        series: [
          {
            symbolSize: 7,
            data:data,
            type: 'scatter',
            itemStyle: {
              color: "rgba(224, 67, 67, 1)"
            },
          }
        ]
      };
      this.chart12.clear()
      this.chart12.setOption(option);
      this.chart12.resize()
      this.chart12.on('click', (params)=>{
        this.$router.push({
          path:this.ecgType==1?'/staticECG':'/restingECG',
          query:{pId:params.data[2]}
        })
      })
    },
    setChart13(data){
      let datax=[]
      let datay=[]
      if(data && data.length!=0){
        // console.log(data)
        data.forEach(item=>{
         // JSON.parse(item[1]).forEach(arr=>{
         //   datax.push(arr)
         //   datay.push(arr)
         //  })
          let arr = JSON.parse(item[1])

          for (let i = 0; i < arr.length-1; i++) {
            datax.push(arr[i])

          }
          for (let i = 1; i < arr.length; i++) {
            datay.push(arr[i])
          }
        })
      }
      let arrData = []
      for (let i = 0; i < datax.length; i++) {
        arrData.push([datax[i],datay[i]])
      }
      // console.log(datax)
      // console.log(datay)
      var option = {
        title:{
          text:'nni(ms)'
        },
        grid:{
          left:'2%',
          top:'16%',
          right:'5%',
          bottom:'2%',
          containLabel:true
        },
        tooltip:{
          show:true
        },
        xAxis: {
          min:0,
          max:2000,
          axisLabel:{
            formatter:function(value){
              return value
            }
          }
        },
        yAxis: {
          min:0,
          max:2000,
          axisLabel:{
            formatter:function(value){
              return value
            }
          }
        },
        series: [
          {
            symbolSize: 7,
            data:arrData,
            type: 'scatter',
            itemStyle: {
              color: "rgba(224, 67, 67, 1)"
            },
            tooltip: {
              formatter: function (params) {
                return 'X: ' + params.value[0] + '<br>Y: ' + params.value[1]; // 显示坐标信息
              }
            }
          }
        ]
      };
      this.chart13.clear()
      this.chart13.setOption(option);
      this.chart13.resize()

    },
    /** 搜索按钮操作 */
    handleQuery() {
      console.log(this.queryParams)
      if (this.queryParams.endTime&&this.queryParams.startTime){
        const date1String = this.queryParams.startTime;
        const date2String = this.queryParams.endTime;

        const date1 = new Date(date1String);
        const date2 = new Date(date2String);

        if (date1 > date2) {
          console.log(`${date1String} 大于 ${date2String}`);
          this.$modal.msgError("开始时间不能比结束时间大");
          return
        } else if (date1 < date2) {
          console.log(`${date1String} 小于 ${date2String}`);
        } else {
          console.log(`${date1String} 等于 ${date2String}`);
        }
      }
      this.getData();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.queryParams.endTime=this.GetDateStr(0)
      this.queryParams.startTime=this.GetDateStr(-30)
      this.handleQuery();
    },
  },
}
</script>

<style lang="scss" scoped>
.fanhuitiao{
  display: flex;
  justify-content:space-between;
}
.main-flex{
  display: flex;
  flex-direction: column;
  .row{
    display:flex;
    justify-content: space-between;
    margin-bottom:25px;
    ::v-deep .el-card{
      width: 100%;
      min-height: 35vh;
      .el-card__body{
        height: 100%;
      }
    }
    .chart{
      width: 100%;
      height: 100%;
    }
  }
}
</style>
