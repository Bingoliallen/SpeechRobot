package com.vma.speechrobot.widget.echarts;

import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import com.example.common.BaseAppProfile;
import com.example.common.utils.Spanny;
import com.github.abel533.echarts.Title;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Orient;
import com.github.abel533.echarts.code.SeriesType;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.code.X;
import com.github.abel533.echarts.code.Y;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Line;
import com.github.abel533.echarts.series.Pie;
import com.github.abel533.echarts.style.TextStyle;
import com.vma.speechrobot.R;
import com.vma.speechrobot.widget.VerticalCenterImageSpan;

import java.util.Arrays;

public class EchartOptionUtil {

  public static GsonOption pieOption;

  public static GsonOption getPieChartOpioons(Object[] list) {
    if (pieOption == null) {
      pieOption = new GsonOption();
      // 设置标题
      pieOption.title(new Title().text("客户等级占比").x(X.center));
      TextStyle titleStyle = new TextStyle();
      titleStyle.setColor("#8995a0");
      titleStyle.setFontSize(17);
      titleStyle.setFontWeight("normal");
      pieOption.title().textStyle(titleStyle);

      pieOption.tooltip().trigger(Trigger.item).formatter("{b}|{c}");

      pieOption.legend().setOrient(Orient.horizontal);
      pieOption.legend().y(Y.bottom);
      pieOption.legend(list);

      pieOption.setCalculable(true);

      pieOption.color("#3ec093", "#F7AD60", "#0091db", "#833ec0", "#f31668", "#f30000");

      Pie pie1 = new Pie();
      pie1.type(SeriesType.pie).radius("50%").center("50%", "50%");
      TextStyle itemTextStyle = new TextStyle();
      itemTextStyle.setFontSize(10);
      pie1.itemStyle().normal().label().show(true).formatter("{b}|{c}").textStyle(itemTextStyle);
      pie1.data(list);
      pieOption.series(pie1);
    } else {
      pieOption.series().get(0).setData(Arrays.asList(list));
    }
    return pieOption;
  }

  public static GsonOption getLineChartOptions(Object[] xAxis, Object[][] yAxis) {
    GsonOption option = new GsonOption();

   /* Spanny taskSumSpanny = new Spanny();
    taskSumSpanny.append("拨号数据",
            new VerticalCenterImageSpan(BaseAppProfile.getApplication(), R.drawable.home_1));

    option.title(taskSumSpanny.toString());
    TextStyle titleStyle = new TextStyle();
    titleStyle.setColor("#8995a0");
    titleStyle.setFontSize(12);
    titleStyle.setFontWeight("normal");
    option.title().textStyle(titleStyle);*/
      TextStyle titleStyle1 = new TextStyle();
      titleStyle1.setFontSize(8);
    option.legend().setOrient(Orient.horizontal);
    option.legend().y(Y.bottom);
      option.legend().setItemWidth(5);
      option.legend().setItemHeight(2);
      option.legend().setTextStyle(titleStyle1);
    option.legend("呼出总数","接通数量","无法接通","拒接");
    option.tooltip().trigger(Trigger.axis);


    TextStyle titleStyle = new TextStyle();
    titleStyle.setFontSize(6);
    ValueAxis valueAxis = new ValueAxis();
    valueAxis.axisLabel().setTextStyle(titleStyle);


    option.yAxis(valueAxis);


    CategoryAxis categorxAxis = new CategoryAxis();
    categorxAxis.axisLine().onZero(false);

    categorxAxis.data(xAxis);
     titleStyle.setFontSize(6);
   categorxAxis.axisLabel().setTextStyle(titleStyle);
    option.xAxis(categorxAxis);

    Line line = new Line();
    line.smooth(false).name("呼出总数").data(yAxis[0]).itemStyle().normal().lineStyle()
        .shadowColor("rgba(0,0,0,0.4)");
    Line line1 = new Line();
    line1.smooth(false).name("接通数量").data(yAxis[1]).itemStyle().normal().lineStyle()
            .shadowColor("rgba(0,0,0,0.4)");
    Line line2 = new Line();
    line2.smooth(false).name("无法接通").data(yAxis[2]).itemStyle().normal().lineStyle()
            .shadowColor("rgba(144, 238 ,144, 0.5)");
    Line line3 = new Line();
    line3.smooth(false).name("拒接").data(yAxis[3]).itemStyle().normal().lineStyle()
            .shadowColor("rgba(255,252,153,0.5)");
    option.series(line,line1,line2,line3);
    return option;
  }

/*
  public void test() {
    //例子：http://echarts.baidu.com/doc/example/line.html
    EnhancedOption option = new EnhancedOption();
    option.tooltip().trigger(Trigger.axis);
    option.legend("邮件营销", "联盟广告", "直接访问", "搜索引擎");
    option.toolbox().show(true);
    option.calculable(true);
    option.xAxis(new CategoryAxis().boundaryGap(false).data("周一", "周二", "周三", "周四", "周五", "周六", "周日"));
    option.yAxis(new ValueAxis());
    option.series(new Line().smooth(true).name("邮件营销").stack("总量").symbol(Symbol.none).data(120, 132, 301, 134, new LineData(90, Symbol.droplet, 5), 230, 210));


    //实现不了js的这个效果
    //line.itemStyle.normal.areaStyle = new AreaStyle();
    LineData lineData = new LineData(201, Symbol.star, 15);
    lineData.itemStyle().normal().label().show(true).textStyle().fontSize(20).fontFamily("微软雅黑").fontWeight("bold");
    option.series(new Line().smooth(true).name("联盟广告").stack("总量").symbol("image://http://echarts.baidu.com/doc/asset/ico/favicon.png").symbolSize(8).data(120, 82, lineData, new LineData(134, Symbol.none), 190, new LineData(230, Symbol.emptypin, 8), 110));

       *//* line = new Line();
        line.name = "邮件营销";
        line.stack = "总量";
        line.symbol = Symbol.none;
        line.smooth = true;
        //实现不了js的这个效果
        //line.itemStyle.normal.areaStyle = new AreaStyle();
        line.addData(120, 132, 301, 134,new LineData(90,Symbol.droplet,5),230,210);
        option.series.add(line);

        line = new Line();
        line.name = "邮件营销";
        line.stack = "总量";
        line.symbol = Symbol.none;
        line.smooth = true;
        //实现不了js的这个效果
        //line.itemStyle.normal.areaStyle = new AreaStyle();
        line.addData(120, 132, 301, 134,new LineData(90,Symbol.droplet,5),230,210);
        option.series.add(line);*//*

    option.exportToHtml("line.html");
    option.view();
  }*/

  public static GsonOption getPieChartOpioons(Object[] list,String radius1,String radius2) {
         GsonOption pieOption = new GsonOption();
          // 设置标题
          /*pieOption.title(new Title().text("客户等级占比").x(X.center));
          TextStyle titleStyle = new TextStyle();
          titleStyle.setColor("#ffffff");
          titleStyle.setFontSize(12);
          titleStyle.setFontWeight("normal");
          pieOption.title().textStyle(titleStyle);*/

          pieOption.tooltip().trigger(Trigger.item).formatter("{b}|{c}");

          pieOption.legend().setOrient(Orient.horizontal);
          pieOption.legend().y(Y.bottom);
          //pieOption.legend().top(1);
         TextStyle titleStyle = new TextStyle();
          titleStyle.setFontSize(8);
          pieOption.legend().setTextStyle(titleStyle);
          pieOption.legend(list);

          pieOption.setCalculable(true);

          pieOption.color("#3ec093", "#f7ad60", "#0091db", "#3bc1c3", "#ea5f5f", "#a858ef");

          Pie pie1 = new Pie();
          pie1.type(SeriesType.pie).radius(radius1,radius2);
          TextStyle itemTextStyle = new TextStyle();
          itemTextStyle.setFontSize(10);
          pie1.itemStyle().normal().label().show(true).formatter("{b}|{c}").textStyle(itemTextStyle);
          pie1.data(list);
          pieOption.series(pie1);

      return pieOption;
  }




}