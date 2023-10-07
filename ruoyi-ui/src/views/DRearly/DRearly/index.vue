<template>
  <div class="app-container">

    <el-divider content-position="left">患者信息</el-divider>

  <el-row>
    <el-col :span="10"><div class="grid-content bg-purple-dark">患者名称：{{form.patientName}}</div></el-col>
    <el-col :span="14"><div class="grid-content bg-purple-dark">患者电话：{{form.patientPhone}}</div></el-col>
  </el-row>

  <el-row>
    <el-col :span="10"><div class="grid-content bg-purple-dark">患者身份证：{{form.patientCode}}</div><span></span></el-col>
    <el-col :span="14"><div class="grid-content bg-purple-dark">家属电话：{{form.familyPhone}}</div><span></span></el-col>
  </el-row>
    <el-row>
      <el-col :span="24"><div class="grid-content bg-purple-dark">患者性别：{{form.patientSex}}</div><span></span></el-col>
    </el-row>
    <el-divider content-position="left">单人预警统计图</el-divider>


  <div id="main" style="width: 600px;height:400px;"></div>
  </div>
</template>

<script>
import {list12Alert_log} from "@/api/DRearly/DRearly";

export default {
  name: 'hello',
  data () {
    return {
      form:{},
      opName:null,
      statistics: [],
      options: [],
      countArr: [],
      query:{
        pId:null,
        ecgType:null,
      }
    }
  },
  created() {
    this.getList();
  },
  mounted(){
    this.drawLine();
  },
  methods: {
    drawLine() {
      // 基于准备好的dom，初始化echarts实例
      let main = this.$echarts.init(document.getElementById('main'))

      let th = this
      var option = {
        tooltip: {
          trigger: 'item'
        },
        series : [
          {
            name: this.opName,
            type: 'pie',
            radius: '70%',
            roseType: 'angle',
            data: this.countArr,
            label: {//饼图文字的显示
              show: true, //默认  显示文字
              formatter: function (arg) {
                console.log(arg);
                return arg.name + '：预警' + arg.value + "次"
              }
            },
          }
        ]
      };
      main.off('click')
      // 绘制图表
      main.setOption(option);
      main.on('click', function(params) {
        th.$router.push({
          name: "log",
          params: {pId: th.form.pId,type:params.name,ecgType:th.$route.query.type}});
      });
    },
    /** 查询 */
    getList() {
      this.query.pId=this.$route.query.id
      this.query.ecgType=this.$route.query.type
      if (this.$route.query.type==='12'){
        this.opName = '单人12导预警'
      }
      if (this.$route.query.type==='single'){
        this.opName = '单人单导预警'
      }
      list12Alert_log(this.query).then(r=>{
        this.form=r.data
        if(r.data.alertLogList.length === 0){
          // 无数据时：展示暂无数据
          const dom = document.getElementById('main');
          if (this.$route.query.type==='12'){
            dom.innerHTML = '-12导预警暂无相关数据-';
          }
          if (this.$route.query.type==='single'){
            dom.innerHTML = '-单导预警暂无相关数据-';
          }
          dom.style.cssText = 'color: #999; border: none;float: right;margin-top: 5%;margin-right: 50%';
          dom.removeAttribute('_echarts_instance_');
        }else {
          this.countArr=r.data.alertLogList
          this.drawLine();
        }
    })

    },
    /** 搜索按钮操作 */

  }
}
</script>
<style scoped>
.el-row {
  margin: 2vw;
}
</style>
