<template>
  <el-row>
    <el-col :span="24">
    <div class="app-container">
<!--      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
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
      </el-form>-->
      <child
        chartID="chart2"
        width="48.75rem"
        height="28.63rem"
        :data1="columndata1"
        :data2="columndata2"
        title="年龄分布"
      ></child>

    </div>
    </el-col>
  </el-row>
</template>
<script>
import { selectAge } from "@/api/statistics/ageStatistics";
import Child from "@/views/statistics/ageStatistics/child";
import elementResizeDetectorMaker from "element-resize-detector";
export default {
  name: 'hello',
  components: {Child},
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
      // 双柱对比图
      columndata1: [
        {
          name: "百岁以上",
          value: 234,
          sum: 30,
        },
        {
          name: "90-99岁",
          value: 160,
          sum: 20,
        },
        {
          name: "80-89岁",
          value: 1500,
          sum: 20,
        },
        {
          name: "70-79岁",
          value: 1500,
          sum: 20,
        },
        {
          name: "60-69岁",
          value: 960,
          sum: 100,
        },
        {
          name: "45-59岁",
          value: 98,
          sum: 100,
        },
        {
          name: "18-44岁",
          value: 97,
          sum: 100,
        },
        {
          name: "6-17岁",
          value: 95,
          sum: 100,
        },
        {
          name: "0-5岁",
          value: 94,
          sum: 100,
        },
      ],
      columndata2: [
        {
          name: "百岁以上",
          value: 234,
          sum: 30,
        },
        {
          name: "90-99岁",
          value: 1600,
          sum: 20,
        },
        {
          name: "80-89岁",
          value: 105,
          sum: 20,
        },
        {
          name: "70-79岁",
          value: 105,
          sum: 20,
        },
        {
          name: "60-69岁",
          value: 960,
          sum: 100,
        },
        {
          name: "45-59岁",
          value: 98,
          sum: 100,
        },
        {
          name: "18-44岁",
          value: 97,
          sum: 100,
        },
        {
          name: "6-17岁",
          value: 95,
          sum: 10000,
        },
        {
          name: "0-5岁",
          value: 94,
          sum: 100,
        },
      ],
    }
  },
  created() {
    this.getList();
  },
  methods: {
    drawLine(id, option) {
      const erd = elementResizeDetectorMaker();
      let myChart = this.$echarts.init(document.getElementById(id));
      // 绘制图表配置
      // 窗口大小自适应方案
      myChart.setOption(option);
      setTimeout(function () {

        erd.listenTo(document.getElementById(id), element => {
          // var width = element.offsetWidth;
          // var height = element.offsetHeight;
          // console.log("Size: " + width + "x" + height);
          myChart.resize()
        });
      }, 200);
    },

    /** 查询 */
    getList() {
      this.loading = true;
      /*selectAge(this.queryParams).then(response => {
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
      });*/
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
  }
}
</script>
