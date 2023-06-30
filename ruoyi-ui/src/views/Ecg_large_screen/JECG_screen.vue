<template>
  <el-scrollbar>
    <div style="padding: 10px 10px 0">
      <el-button type="primary" @click="refresh">刷新</el-button>
      <el-button @click="download">下载</el-button>
      <el-button @click="Export">导出pie</el-button>
    </div>
    <el-divider />
    <div id="echarts">
      <div class="about" id="main"></div>
      <div class="about" id="pie"></div>
    </div>
  </el-scrollbar>
</template>

<script setup>
import { ref, getCurrentInstance, onMounted } from "vue";
import html2canvas from "html2canvas";

const { appContext } = getCurrentInstance();
const { $echarts } = appContext.config.globalProperties;

onMounted(() => {
  setEcharts();
});

const pieMyChart1 = ref(null);
const setEcharts = () => {
  // 基于准备好的dom，初始化echarts实例
  var myChart = $echarts.init(document.getElementById("main"));
  // 绘制图表
  myChart.setOption({
    title: {
      text: "ECharts 入门示例",
    },
    tooltip: {},
    xAxis: {
      data: ["衬衫", "羊毛衫", "雪纺衫", "裤子", "高跟鞋", "袜子"],
      axisTick: {
        // 坐标轴刻度标签
        show: false,
      },
    },
    yAxis: {
      type: "value", // 类型数值轴
      min: 0,
      max: 40,
      splitNumber: 8, // 坐标轴的分割段数
      axisLine: {
        show: true,
      },
      axisTick: {
        show: false, // 隐藏刻度
      },
    },
    series: [
      {
        name: "销量",
        type: "bar",
        label: {
          show: true,
          fontSize: 16,
        },
        data: [5, 20, 36, 10, 10, 20],
        itemStyle: {
          borderRadius: 5,
          borderWidth: 1,
          borderType: "solid",
          borderColor: "#73c0de",
        },
      },
    ],
  });
  // 绘制图表
  const pieMyChart = $echarts.init(document.getElementById("pie"));
  pieMyChart1.value = pieMyChart;
  pieMyChart.setOption({
    title: {
      text: "ECharts 入门示例",
      titleStyle: {
        lineHeight: 18,
      },
      top: "0px",
    },
    tooltip: {},
    legend: {
      orient: "vertical",
      x: "left",
      data: ["A", "B", "C", "D", "E"],
      top: "80px",
    },
    toolbox: {
      show: true,
      orient: "vertical",
      left: "right",
      top: "center",
      feature: {
        saveAsImage: { show: true }, // 保存图表
      },
    },
    series: [
      {
        type: "pie",
        data: [
          {
            value: 335,
            name: "A",
          },
          {
            value: 234,
            name: "B",
          },
          {
            value: 548,
            name: "C",
          },
          {
            value: 500,
            name: "D",
          },
          {
            value: 500,
            name: "E",
          },
        ],
      },
    ],
  });
};

// 页面刷新
const refresh = () => {
  location.reload();
};

// 导出多张图表为一张图片
// dmo元素里的内容转换为canvas,并将canvas下载为图片
const download = () => {
  // 转换成canvas
  html2canvas(document.getElementById("echarts")).then(function (canvas) {
    var img = canvas
      .toDataURL("image/png")
      .replace("image/png", "image/octet-stream");
    // 创建a标签，实现下载
    var creatIMg = document.createElement("a");
    creatIMg.download = "图表.png"; // 设置下载的文件名，
    creatIMg.href = img; // 下载url
    document.body.appendChild(creatIMg);
    creatIMg.click();
    creatIMg.remove(); // 下载之后把创建的元素删除
  });
};

// 导出单个图表图片
function Export() {
  var img = new Image();
  img.src = pieMyChart1.value.getDataURL({
    type: "png",
    pixelRatio: 1, //放大2倍
    backgroundColor: "#fff",
  });
  img.onload = function () {
    var canvas = document.createElement("canvas");
    canvas.width = img.width;
    canvas.height = img.height;
    var ctx = canvas.getContext("2d");
    ctx.drawImage(img, 0, 0);
    var dataURL = canvas.toDataURL("image/png");

    var a = document.createElement("a");
    var event = new MouseEvent("click");
    a.download = "图片.png" || "下载图片名称";
    // 将生成的URL设置为a.href属性
    a.href = dataURL;
    // 触发a的单击事件
    a.dispatchEvent(event);
    a.remove();
  };
}
</script>

<style>
.el-scrollbar {
  height: calc(100vh - 59px);
}
.el-divider--horizontal {
  margin: 10px 0;
}
#echarts {
  padding: 20px;
  width: 600px;
}
#main {
  height: 300px;
  width: 600px;
}
#pie {
  height: 300px;
  width: 600px;
}
</style>
