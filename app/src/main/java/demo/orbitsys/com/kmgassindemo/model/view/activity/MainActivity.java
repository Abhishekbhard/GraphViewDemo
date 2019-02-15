package demo.orbitsys.com.kmgassindemo.model.view.activity;

import android.content.ClipData;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;

import demo.orbitsys.com.kmgassindemo.model.view.fragment.NavMenuFragment;
import demo.orbitsys.com.kmgassindemo.model.adapter.NavigationItemAdapter;
import demo.orbitsys.com.kmgassindemo.R;
import demo.orbitsys.com.kmgassindemo.util.ApplicationController;
import demo.orbitsys.com.kmgassindemo.util.DayAxisValueFormatter;
import demo.orbitsys.com.kmgassindemo.util.InitializeDrawerLayout;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationItemAdapter.ILoadChart {

    private static final String TAG = MainActivity.class.getSimpleName();
    ImageView ivNav;
    Animation animation;
    DrawerLayout drawerLayout;
    private LinearLayout nav_linear_layout;
    private int CONTAIER_SIZE = 6;
    private final int count = 12;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ApplicationController.setFullScreen(this);
        initView();


        clickListener();
        animation = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        InitializeDrawerLayout initializeDrawerLayout = new NavMenuFragment();
        initializeDrawerLayout.initializeDrawerLayout(drawerLayout);
        drawerLayout.requestLayout();
        nav_linear_layout.bringToFront();

        loadDrawerContent();


    }

    @Override
    protected void onResume() {
        super.onResume();
        ApplicationController.setFullScreen(this);

    }

    private void initView() {
        ivNav = findViewById(R.id.ivNav);
        drawerLayout = findViewById(R.id.drawer_layout);
        nav_linear_layout = (LinearLayout) findViewById(R.id.nav_linear_layout);

        findViewById(R.id.topleft).setOnDragListener(new MyDragListener());
        findViewById(R.id.topright).setOnDragListener(new MyDragListener());
        findViewById(R.id.bottomleft).setOnDragListener(new MyDragListener());
        findViewById(R.id.bottomright).setOnDragListener(new MyDragListener());
        findViewById(R.id.centerLeft).setOnDragListener(new MyDragListener());
        findViewById(R.id.centerRight).setOnDragListener(new MyDragListener());


    }

    public void loadDrawerContent() {
        Fragment fragment = new NavMenuFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_frame_container, fragment);
        fragmentTransaction.commit();

    }

    private void clickListener() {
        ivNav.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.ivNav:
                view.startAnimation(animation);

                if (!drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    drawerLayout.openDrawer(Gravity.LEFT);
                    nav_linear_layout.bringToFront();
                    drawerLayout.requestLayout();
                } else {
                    drawerLayout.closeDrawers();
                }

                break;
        }

    }

    @Override
    public void loadChart(String chartType) {

        if (checkSpaceAvailable()) {
            switch (chartType) {
                case "line":
                    setLineData(20, 100);
                    break;

                case "barwithpolyLine":
                    setBarData();
                    break;

                case "pie":
                    setPieData(5, 20);
                    break;

                case "barMultiDataSet":
                    setMultiDataSet();
                    break;
                case "horizontalBarChart":
                    setHorizontalBarChart();
                    break;
                case "harlfPichart":
                    setHalfPi();
                    break;
                default:
                    break;
            }
        }


    }


    private boolean checkSpaceAvailable() {
        if (CONTAIER_SIZE != 0) {
            return true;
        }


        RelativeLayout chartContainer = findViewById(R.id.overlaylayout);
        chartContainer.setVisibility(View.GONE);

        Toast.makeText(MainActivity.this, "Space not Available....", Toast.LENGTH_SHORT).show();
        return false;
    }

    private final class MyTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                        view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }

    class MyDragListener implements View.OnDragListener {

        Drawable normalShape = getResources().getDrawable(R.drawable.shape);

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    //  v.setBackgroundDrawable(enterShape);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackgroundDrawable(normalShape);
                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    View view = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();
                    owner.removeView(view);
                    FrameLayout container = (FrameLayout) v;

                    Log.e(TAG, "onDrag: " + view.getLayoutParams().height + " " + view.getLayoutParams().width);

                    ViewGroup.LayoutParams params = view.getLayoutParams();
                    params.width = container.getWidth();
                    params.height = container.getHeight();

                    view.setLayoutParams(params);
                    if (container.getChildCount() < 1) {
                        container.addView(view);
                        view.setOnTouchListener(null);
                        view.setVisibility(View.VISIBLE);
                        CONTAIER_SIZE--;

                    }

                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setBackgroundDrawable(normalShape);
                default:
                    break;
            }
            return true;
        }
    }


    private void setBarData() {

        CombinedChart chart = new CombinedChart(MainActivity.this);
        chart.getDescription().setEnabled(false);
        chart.setBackgroundColor(Color.WHITE);
        chart.setDrawGridBackground(false);
        chart.setDrawBarShadow(false);
        chart.setHighlightFullBarEnabled(false);
        chart.setOnTouchListener(new MyTouchListener());
        chart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.LINE
        });
        RelativeLayout chartContainer = findViewById(R.id.overlaylayout);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        chart.setLayoutParams(params);
        chartContainer.addView(chart);
        chart.animateX(500);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        ValueFormatter xAxisFormatter = new DayAxisValueFormatter(chart);
        xAxis.setValueFormatter(xAxisFormatter);
        CombinedData data = new CombinedData();

        data.setData(generateLineData());
        data.setData(generateBarData());


        chart.setData(data);
        chart.invalidate();


        // create chart on container dynamic

//        FrameLayout fl=new FrameLayout(MainActivity.this);
//


    }

    private LineData generateLineData() {

        LineData d = new LineData();

        ArrayList<Entry> entries = new ArrayList<>();

        for (int index = 0; index < count; index++)
            entries.add(new Entry(index + 0.5f, getRandom(15, 5)));

        LineDataSet set = new LineDataSet(entries, "Line DataSet");
        set.setColor(Color.rgb(240, 238, 70));
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.rgb(240, 238, 70));
        set.setCircleRadius(5f);
        set.setFillColor(Color.rgb(240, 238, 70));
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.rgb(240, 238, 70));

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);

        return d;
    }

    private BarData generateBarData() {

        ArrayList<BarEntry> entries1 = new ArrayList<>();
        ArrayList<BarEntry> entries2 = new ArrayList<>();

        for (int index = 0; index < count; index++) {
            entries1.add(new BarEntry(0, getRandom(25, 25)));

            // stacked
            // entries2.add(new BarEntry(0, new float[]{getRandom(13, 12), getRandom(13, 12)}));
        }

        BarDataSet set1 = new BarDataSet(entries1, "Bar 1");
        set1.setColor(Color.rgb(104, 241, 175));

        set1.setValueTextColor(Color.rgb(60, 220, 78));
        set1.setValueTextSize(10f);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);

        BarDataSet set2 = new BarDataSet(entries2, "");
        //set2.setStackLabels(new String[]{"Stack 1", "Stack 2"});
        set2.setColor(Color.rgb(164, 228, 251));

        //set2.setColors( Color.rgb(23, 197, 255),Color.rgb(60, 20, 78));
        set2.setValueTextColor(Color.rgb(61, 165, 255));
        set2.setValueTextSize(10f);
        set2.setAxisDependency(YAxis.AxisDependency.LEFT);

        float groupSpace = 0.06f;
        float barSpace = 0.02f; // x2 dataset
        float barWidth = 0.45f; // x2 dataset
        // (0.45 + 0.02) * 2 + 0.06 = 1.00 -> interval per "group"

        BarData d = new BarData(set1, set2);
        d.setBarWidth(barWidth);

        // make this BarData object grouped
        d.groupBars(0, groupSpace, barSpace); // start at x = 0

        return d;
    }

    private void setLineData(int count, float range) {

        LineChart chart = new LineChart(MainActivity.this);
        chart.setTouchEnabled(false);
        chart.setOnTouchListener(new MyTouchListener());

        RelativeLayout chartContainer = findViewById(R.id.overlaylayout);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        final ArrayList<String> xAxisLabel = new ArrayList<>();

        chart.setLayoutParams(params);
        chartContainer.addView(chart);
        XAxis xAxis = chart.getXAxis();

        ValueFormatter xAxisFormatter = new DayAxisValueFormatter(chart);
        xAxis.setValueFormatter(xAxisFormatter);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        chart.animateX(500);


        ArrayList<Entry> values1 = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * (range / 2f)) + 50;
            values1.add(new Entry(i, val));
        }

        ArrayList<Entry> values2 = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * range) + 450;
            values2.add(new Entry(i, val));
        }

        ArrayList<Entry> values3 = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * range) + 500;
            values3.add(new Entry(i, val));
        }

        LineDataSet set1, set2, set3;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set2 = (LineDataSet) chart.getData().getDataSetByIndex(1);
            set3 = (LineDataSet) chart.getData().getDataSetByIndex(2);
            set1.setValues(values1);
            set2.setValues(values2);
            set3.setValues(values3);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values1, "");
            //set1.setColor(getResources().getColor(R.color.colorAccent));

            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            // set1.setColor(ColorTemplate.getHoloBlue());
            set1.setDrawCircles(false);
            set1.setDrawFilled(true);
            set1.setLineWidth(2f);
            // set1.setFillAlpha(65);
            //set1.setFillColor(ColorTemplate.getHoloBlue());
            // set1.setHighLightColor(Color.rgb(4, 117, 117));
            set1.setDrawCircleHole(false);
            //set1.setFillFormatter(new MyFillFormatter(0f));
            //set1.setDrawHorizontalHighlightIndicator(false);
            //set1.setVisible(false);
            //set1.setCircleHoleColor(Color.WHITE);

            // create a dataset and give it a type
            set2 = new LineDataSet(values2, "DataSet 2");
            set2.setAxisDependency(YAxis.AxisDependency.RIGHT);
            set2.setColor(Color.RED);
            set2.setDrawCircles(false);
            set2.setDrawFilled(true);
            set2.setLineWidth(2f);
            set2.setFillAlpha(65);
            set2.setFillColor(Color.RED);
            set2.setDrawCircleHole(false);
            set2.setHighLightColor(Color.rgb(244, 117, 117));
            //set2.setFillFormatter(new MyFillFormatter(900f));

            set3 = new LineDataSet(values3, "DataSet 3");
            set3.setAxisDependency(YAxis.AxisDependency.RIGHT);
            set3.setColor(Color.YELLOW);
            set3.setDrawCircles(false);
            set3.setLineWidth(2f);
            set3.setFillAlpha(65);
            set3.setDrawFilled(true);
            set3.setFillColor(ColorTemplate.colorWithAlpha(Color.YELLOW, 200));
            set3.setDrawCircleHole(false);
            set3.setHighLightColor(Color.rgb(244, 117, 117));

            // create a data object with the data sets
            LineData data = new LineData(set1, set2, set3);
            data.setValueTextColor(Color.WHITE);
            data.setValueTextSize(9f);

            // set data
            chart.setData(data);
        }
        chart.invalidate();


    }


    private void setPieData(int count, float range) {

        PieChart chart = new PieChart(MainActivity.this);
        chart.setTouchEnabled(false);
        chart.setOnTouchListener(new MyTouchListener());
        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);
        chart.setExtraOffsets(20.f, 0.f, 20.f, 0.f);
        chart.setDrawCenterText(true);

        chart.setDragDecelerationFrictionCoef(0.95f);
        RelativeLayout chartContainer = findViewById(R.id.overlaylayout);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        chart.setLayoutParams(params);
        chart.getDescription().setText("Description of my chart");
        chart.animateX(500);
        chartContainer.addView(chart);


        Log.e(TAG, "setBarData: ");
        float start = 1f;

        ArrayList<PieEntry> entries = new ArrayList<>();

        for (int i = (int) start; i < start + count; i++) {
            entries.add(new PieEntry((float) ((Math.random() * range) + range / 100)));
        }

        PieDataSet set1;


        // add a lot of colors


        final int[] MY_COLORS = {Color.rgb(192, 0, 0), Color.rgb(255, 0, 0), Color.rgb(255, 192, 0),
                Color.rgb(127, 127, 127), Color.rgb(146, 208, 80), Color.rgb(0, 176, 80), Color.rgb(79, 129, 189)};
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : MY_COLORS)
            colors.add(c);


        set1 = new PieDataSet(entries, "The year 2019");
        //  colors.add(ColorTemplate.getHoloBlue());
        set1.setValueLinePart1OffsetPercentage(80.f);
        set1.setValueLinePart1Length(0.2f);
        set1.setValueLinePart2Length(0.4f);
        //dataSet.setUsingSliceColorAsValueLineColor(true);

        //dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        set1.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        set1.setSliceSpace(3f);
        set1.setIconsOffset(new MPPointF(0, 60));
        set1.setSelectionShift(2f);
        set1.setDrawIcons(false);
        set1.setColors(MY_COLORS);
        PieData data = new PieData(set1);
        data.setValueTextSize(10f);


        chart.setData(data);
        chart.invalidate();


    }


    protected float getRandom(float range, float start) {
        return (float) (Math.random() * range) + start;
    }

    private void setMultiDataSet() {
        BarChart barChart = new BarChart(this);
        barChart.getDescription().setEnabled(false);
        barChart.setBackgroundColor(Color.WHITE);
        barChart.setDrawGridBackground(false);
        barChart.setDrawBarShadow(false);
        barChart.setHighlightFullBarEnabled(false);
        barChart.setOnTouchListener(new MyTouchListener());

        RelativeLayout chartContainer = findViewById(R.id.overlaylayout);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


        barChart.setLayoutParams(params);
        chartContainer.addView(barChart);
        barChart.animateX(500);

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<BarEntry> barEntries1 = new ArrayList<>();
        ArrayList<BarEntry> barEntries2 = new ArrayList<>();

        barEntries.add(new BarEntry(0, 98.21f));
        barEntries.add(new BarEntry(1, 420.22f));
        barEntries.add(new BarEntry(2, 758));
        barEntries.add(new BarEntry(3, 307.97f));
        barEntries.add(new BarEntry(4, 1000.96f));
        barEntries.add(new BarEntry(5, 400.4f));
        barEntries.add(new BarEntry(6, 588.58f));

        barEntries1.add(new BarEntry(0, 250));
        barEntries1.add(new BarEntry(1, 791));
        barEntries1.add(new BarEntry(2, 630));
        barEntries1.add(new BarEntry(3, 782));
        barEntries1.add(new BarEntry(4, 271.54f));
        barEntries1.add(new BarEntry(5, 500));
        barEntries1.add(new BarEntry(6, 217.36f));

        barEntries2.add(new BarEntry(0, 900));
        barEntries2.add(new BarEntry(1, 691));
        barEntries2.add(new BarEntry(2, 103));
        barEntries2.add(new BarEntry(3, 382));
        barEntries2.add(new BarEntry(4, 271f));
        barEntries2.add(new BarEntry(5, 500));
        barEntries2.add(new BarEntry(6, 117f));


        BarDataSet barDataSet = new BarDataSet(barEntries, "DATA SET 1");
        barDataSet.setColor(Color.parseColor("#F44336"));
        BarDataSet barDataSet1 = new BarDataSet(barEntries1, "DATA SET 2");
        barDataSet1.setColors(Color.parseColor("#9C27B0"));
        BarDataSet barDataSet2 = new BarDataSet(barEntries2, "DATA SET 3");
        barDataSet1.setColors(Color.parseColor("#e241f4"));


        String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun"};
        BarData data = new BarData(barDataSet, barDataSet1, barDataSet2);
        barChart.setData(data);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(months));
        barChart.getAxisLeft().setAxisMinimum(0);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setCenterAxisLabels(false);
        xAxis.setGranularityEnabled(true);

        float barSpace = 0.02f;
        float groupSpace = 0.2f;

        //IMPORTANT *****
        data.setBarWidth(0.2f);
        barChart.getXAxis().setAxisMinimum(0);
        barChart.getXAxis().setAxisMaximum(0 + barChart.getBarData().getGroupWidth(groupSpace, barSpace) * 5);
        barChart.groupBars(0, groupSpace, barSpace);

    }

    private void setHorizontalBarChart() {
        HorizontalBarChart barChart = new HorizontalBarChart(this);
        barChart.getDescription().setEnabled(false);
        barChart.setBackgroundColor(Color.WHITE);
        barChart.setDrawGridBackground(false);
        barChart.setDrawBarShadow(false);
        barChart.setHighlightFullBarEnabled(false);
        barChart.setOnTouchListener(new MyTouchListener());

        RelativeLayout chartContainer = findViewById(R.id.overlaylayout);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


        barChart.setLayoutParams(params);
        chartContainer.addView(barChart);
        barChart.animateX(500);
        XAxis xl = barChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);

        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        float barWidth = 9f;
        float spaceForBar = 10f;
        ArrayList<BarEntry> values = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * 12);
            values.add(new BarEntry(i * spaceForBar, val
            ));
        }

        BarDataSet set1;

        if (barChart.getData() != null &&
                barChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) barChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values, "DataSet 1");
            set1.setColors(ColorTemplate.MATERIAL_COLORS);

            set1.setDrawIcons(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            //data.setValueTypeface(tfLight);
            data.setBarWidth(barWidth);
            barChart.setData(data);
        }


    }

    private void setHalfPi() {
        PieChart chart = new PieChart(MainActivity.this);
        chart.setTouchEnabled(false);
        chart.setOnTouchListener(new MyTouchListener());
        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 100, 5, 5);
        chart.setExtraOffsets(20.f, 10.f, 20.f, 0.f);
        chart.setDrawCenterText(true);
        chart.setMaxAngle(180f); // HALF CHART
        chart.setRotationAngle(180f);
        chart.setCenterTextOffset(0, -20);


        chart.setDragDecelerationFrictionCoef(0.95f);
        RelativeLayout chartContainer = findViewById(R.id.overlaylayout);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        chart.setLayoutParams(params);
        //chart.getDescription().setText("Description of my chart");
        chart.animateX(500);
        chartContainer.addView(chart);
        setData(4, 100, chart);
    }

    private void setData(int count, float range, PieChart chart) {

        ArrayList<PieEntry> values = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            values.add(new PieEntry((float) ((Math.random() * range) + range / 5)));
        }

        PieDataSet dataSet = new PieDataSet(values, "Election Results");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        chart.setData(data);

        chart.invalidate();
    }


}