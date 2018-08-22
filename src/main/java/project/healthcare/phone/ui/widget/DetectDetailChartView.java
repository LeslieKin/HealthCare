package project.healthcare.phone.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import me.lexjoy.chart.Axis;
import me.lexjoy.chart.Chart;
import me.lexjoy.chart.Legend;
import me.lexjoy.chart.Series;
import me.lexjoy.chart.option.AxisOption;
import me.lexjoy.chart.option.ChartOption;
import me.lexjoy.chart.option.LegendOption;
import me.lexjoy.chart.option.SeriesOption;
import me.lexjoy.chart.series.AreaSplineSeries;
import me.lexjoy.chart.series.data.FloatArraySeriesData;
import me.lexjoy.chart.series.option.AreaSplineOption;
import me.lexjoy.chart.widget.ChartView;
import project.healthcare.phone.R;
import project.healthcare.phone.constants.DetectType;


public class DetectDetailChartView extends ChartView {

  private final static String TAG="DetectDetailCharView";

  public DetectDetailChartView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public void setDetectType(int detectType) {

    ChartDelegator delegator = buildChartDelegator(detectType);

    if (delegator != null) {
      delegator.init(this.getChart());
    }

    this.chartDelegator = delegator;
    this.postInit();
  }

  private ChartDelegator buildChartDelegator(int detectType) {
    switch (detectType) {
    case DetectType.BLOOD_PRESSURE:
      return new BloodPressureChartDelegator();

    case DetectType.BLOOD_SUGAR:
      return new BloodSugarChartDelegator();

    case DetectType.BLOOD_OXYGEN:
      return new BloodOxygenChartDelegator();

    case DetectType.HEART_RATE:
      return new HeartRateChartDelegator();

    case DetectType.TEMP:
      return new TemperatureChartDelegator();

    case DetectType.WEIGHT:
      return new WeightChartDelegator();
    }
    return null;
  }

  /**
   * 血压：[收缩压，舒张压]([systolic, diastolic])
   * 
   * @param datas
   */
  public void updateData(float[]... datas) {
    if (this.chartDelegator != null) {
      this.chartDelegator.update(datas);
    }
  }

  @Override
  protected boolean isInitDelayed() {
    return true;
  }

  private ChartDelegator chartDelegator;

  private class ChartDelegator {

    public void init(Chart chart) {
      this.onInitChart(chart);
      chart.addXAxis(makeXAxis());
      chart.addYAxis(makeYAxis());
      chart.addLegend(makeLegend());
      chart.setChartOption(makeChartOption());
    }

    private Axis makeXAxis() {
      Axis axis = new Axis();
      AxisOption option = new AxisOption();
      option.lineColor = 0xff000000;
      option.tickColor = 0xff7f7f7f;
      option.labelColor = 0xff000000;
      option.gridLineColor = 0xfff4f4f4;
      option.maxPadding = 20;
      option.showFirstTick = false;
      option.showLastGridLine = false;
      option.labelTextSize = 18;
      option.labelMargin = 8;
      option.lineWidth = 2;
      option.showFirstLabel = false;
      option.lineColor = 0xff333333;
      option.tickWidth = 8;
      option.tickColor = 0xff333333;

      option.tickInterval = 1F;
      this.onInitXAxisOption(option);
      axis.setOption(option);
      return axis;
    }

    private Axis makeYAxis() {
      Axis axis = new Axis();

      // init axis option
      AxisOption option = new AxisOption();

      option.lineColor = 0xff000000;
      option.tickColor = 0xff7f7f7f;
      option.labelColor = 0xff000000;
      option.gridLineColor = 0xfff4f4f4;
      option.maxPadding = 20;
      option.showFirstTick = false;
      option.showLastGridLine = false;
      option.labelTextSize = 20;
      option.labelMargin = 10;
      option.unitTextSize = 20;
      option.unitMargin = 10;
      option.lineWidth = 2;
      option.lineColor = 0xff333333;
      option.tickWidth = 8;
      option.tickColor = 0xff333333;
      option.unitColor = 0xff67b32d;

      this.onInitYAxisOption(option);

      axis.setOption(option);

      return axis;
    }

    private Legend makeLegend() {
      Legend legend = new Legend();

      LegendOption option = new LegendOption();

      option.topMargin = 20;
      option.rightMargin = 20;
//      option.itemHeight = 20;
      option.itemHeight = 35;//图例大小
      option.itemTextSize = 18;
      option.itemMargin = 8;
      option.margin = 12;
      option.padding=15;

      legend.setOption(option);

      return legend;
    }

    private ChartOption makeChartOption() {
      ChartOption option = new ChartOption();
      Log.e(TAG, "makeChartOption: 二维平面背景");
      option.backgroundColor = 0xffffffff;
      option.margin = 20;
      option.leftMargin = 80;
      option.topMargin = 42;
      option.rightMargin = 5;
      option.bottomMargin = 40;

      this.onInitChartOption(option);

      return option; 
    }

    public void update(float[]... datas) {
      List<Series<?>> seriesList = this.seriesList;

      if (seriesList != null && datas != null) {
        Series<?> series;
        int size = Math.min(seriesList.size(), datas.length);

        for (int i = 0; i < size; ++i) {
          if ((series = seriesList.get(i)) != null) {
            series.setSeriesData(new FloatArraySeriesData(datas[i]));
          }
        }
      }
    }

    protected void addSeries(Series<?> series) {
      if (this.seriesList == null) {
        this.seriesList = new ArrayList<Series<?>>();
      }
      this.seriesList.add(series);
    }

    protected void onInitChart(Chart chart) {
      chart.addSeries(this.makeFirstSeries());
    }

    protected void onInitXAxisOption(AxisOption option) {
    }

    protected void onInitYAxisOption(AxisOption option) {
    }

    protected void onInitChartOption(ChartOption option) {
    }

    protected Series<?> makeFirstSeries() {
      AreaSplineSeries series = new AreaSplineSeries();

      // init series option
      SeriesOption option = new SeriesOption();

//      option.color = 0xff678afe;
      option.color = 0xff4cdafb;
      //option.color = ColorPicker.pickColor(2);
      option.name = this.firstSeriesName;
      option.lineWidth = 2;//线段宽度
      option.pointWidth = 8;//样本点宽度

      series.setOption(option);

      // init series type option
      AreaSplineOption areaOption = new AreaSplineOption();

      //areaOption.areaColor = 0xff128c03;
      areaOption.opacity = 0.06F;

      series.setTypeOption(areaOption);

      this.addSeries(series);
      return series;
    }

    protected Series<?> makeSecondSeries() {
      AreaSplineSeries series = new AreaSplineSeries();

      // init series option
      SeriesOption option = new SeriesOption();

      //option.color = 0xff71f0ff;
      //option.color = ColorPicker.pickColor(2);
      //option.color = 0xff678afe;
      option.color = 0xff6bd90a;
      option.name = this.secondSeriesName;
      option.lineWidth = 2;
      option.pointWidth = 8;

      series.setOption(option);

      // init series type option
      AreaSplineOption areaOption = new AreaSplineOption();

      //areaOption.areaColor = 0xff128c03;
      areaOption.opacity = 0.06F;

      series.setTypeOption(areaOption);

      this.addSeries(series);
      return series;
    }

    protected void setFirstSeriesName(String seriesName) {
      this.firstSeriesName = seriesName;
    }

    protected void setSecondSeriesName(String seriesName) {
      this.secondSeriesName = seriesName;
    }

    private List<Series<?>> seriesList;

    private String firstSeriesName;
    private String secondSeriesName;
  }

  private class BloodPressureChartDelegator extends ChartDelegator {
    @Override
    protected void onInitChart(Chart chart) {
      this.setFirstSeriesName(getString(R.string.systolic));
      this.setSecondSeriesName(getString(R.string.diastolic));
      chart.addSeries(this.makeFirstSeries ()); // systolic
      chart.addSeries(this.makeSecondSeries()); // diastolic
    }

    @Override
    protected void onInitYAxisOption(AxisOption option) {
      option.maxStop = 300F;
      option.minStop = 160F;
      option.min = 0F;
      option.tickInterval = 20F;

      option.unit = "mmHg";
    }
  }

  private class BloodSugarChartDelegator extends ChartDelegator {
    @Override
    protected void onInitChart(Chart chart) {
      this.setFirstSeriesName(getString(R.string.blood_sugar));
      super.onInitChart(chart);
    }

    @Override
    protected void onInitYAxisOption(AxisOption option) {
      option.maxStop = 30F;
      option.minStop = 10F;
      option.min = 0F;
      option.tickInterval = 1F;

      option.unit = "mmol/L";
    }
  }

  private class BloodOxygenChartDelegator extends ChartDelegator {
    @Override
    protected void onInitChart(Chart chart) {
      this.setFirstSeriesName(getString(R.string.blood_oxygen));
      super.onInitChart(chart);
    }

    @Override
    protected void onInitYAxisOption(AxisOption option) {
      option.max = 100F;
      option.min = 0F;
      option.tickInterval = 10F;

      option.unit = "%";
    }
  }

  private class HeartRateChartDelegator extends ChartDelegator {
    @Override
    protected void onInitChart(Chart chart) {
      this.setFirstSeriesName(getString(R.string.heart_rate));
      super.onInitChart(chart);
    }

    @Override
    protected void onInitYAxisOption(AxisOption option) {
      option.maxStop = 300F;
      option.minStop = 100F;
      option.min = 0F;
      option.tickInterval = 10F;

      option.unit = "bpm";
    }
  }

  private class TemperatureChartDelegator extends ChartDelegator {
    @Override
    protected void onInitChart(Chart chart) {
      this.setFirstSeriesName(getString(R.string.temp));
      super.onInitChart(chart);
    }

    @Override
    protected void onInitYAxisOption(AxisOption option) {
      option.maxStop = 160F;
      option.minStop = 40F;
      option.maxStart = 0F;
      option.tickInterval = 5F;

      option.unit = "℃";
    }
  }

  private class WeightChartDelegator extends ChartDelegator {
    @Override
    protected void onInitChart(Chart chart) {
      this.setFirstSeriesName(getString(R.string.weight));
      super.onInitChart(chart);
    }

    @Override
    protected void onInitYAxisOption(AxisOption option) {
      option.maxStop = 200F;
      option.minStop = 80F;
      option.min = 0F;
      option.tickInterval = 10F;

      option.unit = "kg";
    }
  }

}
