<template>
  <el-row>
    <el-col :span="24">
    <div class="app-container">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
        <el-form-item label="医生名称" prop="doctor_name">
          <el-select v-model="queryParams.doctorPhone" clearable placeholder="请选择">
            <el-option
              v-for="item in options"
              :label="item.doctorName"
              :value="item.doctorPhone">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        </el-form-item>
      </el-form>
      <div id="myChart" :style="{width: '1000px', height: '500px'}"></div>
    </div>
    </el-col>
  </el-row>
</template>
<script>
import { listStatistics, selectDoctor } from "@/api/statistics/statistics";
import item from "@/layout/components/Sidebar/Item";
export default {
  name: 'hello',
  data () {
    return {
      showSearch: true,
      statistics: [],
      options: [],
      countArr: [],
      queryParams: {
        doctorName: null,
        doctorPhone: null,
      },

    }
  },
  created() {
    this.getList();
    this.selectDoctor();
  },
  mounted(){
    this.drawLine();
  },
  methods: {
    drawLine() {
      // 基于准备好的dom，初始化echarts实例
      let myChart = this.$echarts.init(document.getElementById('myChart'))
      // 绘制图表
      myChart.setOption({
        title: {
          text: ''
        },
        tooltip: {},
        xAxis: {
          data: ["一月", "二月", "三月", "四月", "五月",
            "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"]
        },
        yAxis: {},
        series: [{
          name: '诊断次数',
          type: 'bar',
          data: this.countArr
        }],
        grid: {
          bottom: 50,
          top: 100,
          right: 0,
          left: 200,
        },
      });
    },
    selectDoctor() {
      selectDoctor().then(response => {
        this.options = response;
      })
    },
    /** 查询 */
    getList() {
      this.loading = true;
      listStatistics(this.queryParams).then(response => {
        //console.log(response.rows);
        let data = response.rows;
        let countArr = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
        for (let j = 0; j < data.length; j++) {
          countArr[data[j].month - 1] = parseInt(data[j].count);
        }
        this.countArr = countArr;
        this.drawLine();
        //console.log(countArr);
        this.loading = false;
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
  }
}
</script>
