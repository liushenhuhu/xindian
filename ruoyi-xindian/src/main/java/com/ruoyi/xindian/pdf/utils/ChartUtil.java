package com.ruoyi.xindian.pdf.utils;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.text.ParseException;
import java.util.List;

public class ChartUtil {
    private static final Color[] BAR_COLORS = new Color[]{new Color(79, 129, 189), new Color(192, 80, 77), new Color(155, 187, 89),};

    public static final Color[] LINE_COLORS = new Color[]{new Color(237, 123, 46), new Color(90, 154, 213), new Color(164, 164, 164),};

    private static final Color[] PIE_COLORS = new Color[]{new Color(75, 172, 198), new Color(128, 100, 162), new Color(155, 187, 89), new Color(192, 80, 77), new Color(79, 129, 189), new Color(44, 77, 117), new Color(247, 150, 70), new Color(165, 165, 165),};


    static StandardChartTheme initChartTheme() {
        StandardChartTheme currentTheme = new StandardChartTheme("JFree");
        // 横轴纵轴标题文字大小
        currentTheme.setLargeFont(new Font("宋体", Font.BOLD, 15));
        // 横轴纵轴数值文字大小
        currentTheme.setRegularFont(new Font("宋体", Font.PLAIN, 10));
        currentTheme.setExtraLargeFont(new Font("宋体", Font.BOLD, 10));
        // 背景颜色
        currentTheme.setPlotBackgroundPaint(new Color(255, 255, 204, 0));
        // 边框线条
//        currentTheme.setPlotOutlinePaint(new Color(0, 0, 0, 0));
        // 网格线条
//        currentTheme.setRangeGridlinePaint(new Color(78, 74, 74));
        return currentTheme;
    }

    /**
     * 线图
     */
    public static JFreeChart lineChart(String title, String categoryAxisLabel, String valueAxisLabel, List<Float> dataSet) throws ParseException {
        ChartFactory.setChartTheme(initChartTheme());
        XYSeriesCollection dataset = new XYSeriesCollection();
        // 创建一个系列
        XYSeries series = new XYSeries("Heart Rate");
        // 添加数据点到系列
        if (dataSet != null) {
            for (int i = 0; i < dataSet.size(); i++) {
                series.add(i, dataSet.get(i));
            }
        }
        // 将系列添加到数据集中
        dataset.addSeries(series);
//        // 创建心率数据
//        XYSeries series = new XYSeries("Heart Rate");
//
//        // 假设这里是你的心率数据
//        for (int i = 0; i < dataSet.size(); i++) {
//            series.add(i, dataSet.get(i));
//        }
//        // 创建数据集并添加数据
//        XYSeriesCollection dataset1 = new XYSeriesCollection(series);
//        dataset1.addSeries(series);
        // 创建图表
        JFreeChart chart = ChartFactory.createXYLineChart(
                "",  // 图表标题
                "",      // 横轴标签
                valueAxisLabel,        // 纵轴标签
                dataset,             // 数据集
                PlotOrientation.VERTICAL,
                false,                // 是否创建图例
                false,                // 是否生成工具提示
                false               // 是否生成URL链接
        );
        // 设置图表的背景颜色
        chart.setBackgroundPaint(Color.white);

        // 获取绘图区域对象并修改设置
        XYPlot plot = chart.getXYPlot();
        plot.getDomainAxis().setVisible(false);
        //纵轴标签旋转
        ValueAxis rangeAxis = plot.getRangeAxis();
        rangeAxis.setLabelAngle(Math.PI / 2);
        // 设置纵坐标标签字体大小
        rangeAxis.setLabelFont(new Font("SansSerif", Font.PLAIN, 30));
        //替换刻度
        // 自定义NumberAxis

        // 设置横轴范围
        NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
        xAxis.setAutoTickUnitSelection(false);
        xAxis.setRange(0, 24 * 60);   // 从0到24小时
        xAxis.setTickUnit(new NumberTickUnit(60)); // 设置刻度单位为1小时
        xAxis.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 20));

//        xAxis.setVerticalTickLabels(true); // 设置刻度标签垂直显示
        // 设置纵轴范围
        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        yAxis.setRange(1, 299);
        yAxis.setTickUnit(new NumberTickUnit(50)); // 设置刻度单位为50
        yAxis.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 20));
        //横轴在上方
        plot.setDomainAxisLocation(0, AxisLocation.TOP_OR_LEFT);
        return chart;
    }

    /**
     * 柱状图
     */
    public static JFreeChart barChart(String title, String categoryAxisLabel, String valueAxisLabel, DefaultCategoryDataset dataset, int maxY, int minY, int LY) {
        ChartFactory.setChartTheme(initChartTheme());

        JFreeChart chart = ChartFactory.createBarChart(title, categoryAxisLabel, valueAxisLabel, dataset, PlotOrientation.VERTICAL, true, true, false);

        CategoryPlot plot = chart.getCategoryPlot();
        ValueAxis rangeAxis = plot.getRangeAxis();
        rangeAxis.setLabelAngle(Math.PI / 2);
        //不显示横轴
//        plot.getDomainAxis().setVisible(false);
        //不显示横轴标题
        plot.getDomainAxis().setLabel("");

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        // 纯色显示
        renderer.setBarPainter(new StandardBarPainter());
//        // 柱子上显示小数字
//        renderer.setDefaultItemLabelsVisible(true);
//        renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        // 设置柱子间隔
        renderer.setItemMargin(0.0);
        // 设置纵坐标标签字体大小
        rangeAxis.setLabelFont(new Font("SansSerif", Font.PLAIN, 15));
        //设置间距
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setLowerMargin(0.01);
        domainAxis.setUpperMargin(0.01);
        //设置横纵字体大小
        domainAxis.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 20));
        //设置纵轴范围
        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        if (maxY == 0) {
            yAxis.setRange(0, 10);
            yAxis.setTickUnit(new NumberTickUnit(2)); // 设置刻度单位为50
        } else {
            yAxis.setRange(minY, maxY);
            yAxis.setTickUnit(new NumberTickUnit(LY)); // 设置刻度单位为50
        }
        yAxis.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 20));

        // 设置柱子颜色
        for (int i = 0; i < dataset.getRowKeys().size(); i++) {
            renderer.setSeriesPaint(i, BAR_COLORS[i]);
        }
        chart.removeLegend();
        return chart;
    }


    /**
     * NN柱状图
     */
    public static JFreeChart NNChart(String title, String categoryAxisLabel, String valueAxisLabel, DefaultCategoryDataset dataset, int maxY, int minY, int LY) {
        ChartFactory.setChartTheme(initChartTheme());

        JFreeChart chart = ChartFactory.createBarChart(title, categoryAxisLabel, valueAxisLabel, dataset, PlotOrientation.VERTICAL, true, true, false);

        CategoryPlot plot = chart.getCategoryPlot();
        ValueAxis rangeAxis = plot.getRangeAxis();
        rangeAxis.setLabelAngle(Math.PI / 2);
        TextTitle chartTitle = chart.getTitle();
        chartTitle.setFont(new Font("SansSerif", Font.BOLD, 25));
        //不显示横轴
//        plot.getDomainAxis().setVisible(false);
        //不显示横轴标题
//        plot.getDomainAxis().setLabel("");

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        // 纯色显示
        renderer.setBarPainter(new StandardBarPainter());
//        // 柱子上显示小数字
//        renderer.setDefaultItemLabelsVisible(true);
//        renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        // 设置柱子间隔
        renderer.setItemMargin(0.0);
        renderer.setMaximumBarWidth(0.05);
        // 设置纵坐标标签字体大小
        rangeAxis.setLabelFont(new Font("SansSerif", Font.PLAIN, 20));
        //设置间距
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setLowerMargin(0.01);
        domainAxis.setUpperMargin(0.01);
        domainAxis.setLabelFont(new Font("SansSerif", Font.PLAIN, 20));
        //设置横纵字体大小
        domainAxis.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 20));
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);


        //设置纵轴范围
        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        if (maxY == 0) yAxis.setRange(0, 10);
        else yAxis.setRange(minY, maxY + LY - 1);
        yAxis.setTickUnit(new NumberTickUnit(LY)); // 设置刻度单位为50
        yAxis.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 20));

        // 设置柱子颜色
        for (int i = 0; i < dataset.getRowKeys().size(); i++) {
            renderer.setSeriesPaint(i, BAR_COLORS[i]);
        }
        chart.removeLegend();
        return chart;
    }
}


