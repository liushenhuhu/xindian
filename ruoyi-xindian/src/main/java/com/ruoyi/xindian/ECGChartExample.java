//package com.ruoyi.xindian;
//
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartUtils;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.plot.PlotOrientation;
//import org.jfree.chart.plot.XYPlot;
//import org.jfree.data.xy.XYSeries;
//import org.jfree.data.xy.XYSeriesCollection;
//import java.awt.Font;
//import java.awt.BasicStroke;
//import java.awt.Color;
//import java.io.File;
//import java.io.IOException;
//
//public class ECGChartExample {
//
//    public static void main(String[] args) throws IOException {
//        // 创建心电图数据集
//        XYSeries series = createDataset();
//
//        // 创建心电图
//        JFreeChart chart = ChartFactory.createXYLineChart(
//                "ECG Chart",                    // 图表标题
//                "Time",                         // X 轴标签
//                "Voltage",                      // Y 轴标签
//                new XYSeriesCollection(series), // 数据集
//                PlotOrientation.VERTICAL,       // 图表方向
//                true,                           // 是否生成图例
//                true,                           // 是否生成工具提示
//                false                           // 是否生成URL链接
//        );
//
//        // 设置图表样式
//        XYPlot plot = (XYPlot) chart.getPlot();
//        plot.setBackgroundPaint(Color.WHITE);
//        plot.setRangeGridlinePaint(Color.BLACK);
//        plot.setRangeGridlineStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{5}, 0));
//        plot.setDomainGridlinePaint(Color.BLACK);
//        plot.setDomainGridlineStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{5}, 0));
//        plot.getRenderer().setSeriesStroke(0, new BasicStroke(2.0f));
//
//        // 设置图表标题样式
//        chart.getTitle().setFont(new Font("宋体", Font.BOLD, 16));
//
//        // 设置图表大小，并保持格子是正方形
//        int width = 590; // A4纸宽度为210mm，转换为像素为590px
//        int height = 300;
//        chart.getLegend().setVisible(false);
//        plot.setS(new java.awt.Dimension(width, height));
//        plot.setFixedRangeAxisSpace(new org.jfree.chart.axis.AxisSpace()); // 去除上下留白
//        File file = new File("D:\\ecg\\DECGChart.png");
//        ChartUtils.saveChartAsPNG(file, chart, width, height);
//    }
//
//    private static XYSeries createDataset() {
//        XYSeries series = new XYSeries("ECG");
//
//        // 添加心电图数据点
//        // 这里只是一个示例，你需要根据实际数据进行替换
//        series.add(0, 0);
//        series.add(1, 1);
//        series.add(2, 0.5);
//        series.add(3, 0);
//        series.add(4, -0.5);
//        series.add(5, 0);
//
//        return series;
//    }
//}
