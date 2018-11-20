package com.androidcat.yucaiedu.fragment;

import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.androidcat.utilities.LogUtil;
import com.androidcat.utilities.date.DateUtil;
import com.androidcat.utilities.listener.OnSingleClickListener;
import com.androidcat.yucaiedu.R;
import com.bigkoo.pickerview.OptionsPopupWindow;
import com.bigkoo.pickerview.TimePopupWindow;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@ActivityFragmentInject(
        contentViewId = R.layout.fragment_analyze,
        hasNavigationView = false)
public class AnalyzeFragment extends BaseFragment{
    private static final String TAG = "AnalyzeFragment";

    private LineChart chart;
    private TextView dateTv;
    private TextView classTv;
//    private List<TextMenu> menu = new ArrayList<TextMenu>();
//    private List<List<TextMenu>> subMenu = new ArrayList<List<TextMenu>>();
//    private ExpandableAdapter adapter;
//    private ExpandableListView menuList;

    TimePopupWindow pwTime;
    OptionsPopupWindow pwOptions2;
    private ArrayList<String> options2Items = new ArrayList<String>();
    private ArrayList<String> options4Items=new ArrayList<>();

    @Override
    protected void toHandleMessage(Message msg) {

    }

    @Override
    protected void findViewAfterViewCreate() {
        dateTv = mRootView.findViewById(R.id.dateTv);
        classTv = mRootView.findViewById(R.id.classTv);
        chart = mRootView.findViewById(R.id.chart1);
        //chart.setOnChartValueSelectedListener(getActivity());

        chart.setDrawGridBackground(false);
        chart.getDescription().setEnabled(false);
        chart.setDrawBorders(false);

        chart.getAxisLeft().setEnabled(false);
        chart.getAxisRight().setDrawAxisLine(false);
        chart.getAxisRight().setDrawGridLines(false);
        chart.getXAxis().setDrawAxisLine(false);
        chart.getXAxis().setDrawGridLines(false);

        // enable touch gestures
        chart.setTouchEnabled(true);

        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(false);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
    }

    private final int[] colors = new int[] {
            ColorTemplate.VORDIPLOM_COLORS[0],
            ColorTemplate.VORDIPLOM_COLORS[1],
            ColorTemplate.VORDIPLOM_COLORS[2]
    };

    @Override
    protected void initDataAfterFindView() {
        LogUtil.d(TAG,"initDataAfterFindView");
        dateTv.setText(DateUtil.getYMDW(new Date()));
        classTv.setText(5+"年级  "+6 + "班");
        pickerLintener();
        setListener();
        setChartData();
    }

    @Override
    protected void initDataBeforeViewCreate() {
        initData();
    }

    /**
     * 自定义picker控件事件
     */
    private void pickerLintener() {
        options2Items.clear();
        options4Items.clear();
        pwTime = new TimePopupWindow(getActivity(), TimePopupWindow.Type.YEAR_MONTH_DAY);
        pwTime.setTime(new Date());
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        pwTime.setRange(2000, year);

        pwOptions2 = new OptionsPopupWindow(getActivity());
        //选项1
        for (int i = 1; i < 7; i++) {
            options2Items.add("" + i);
        }
        for (int i = 1; i < 10; i++){
            options4Items.add(""+i);
        }
        ArrayList<ArrayList<String>> lists=new ArrayList<>();
        lists.add(options4Items);
        pwOptions2.setPicker(options2Items, lists, false);
        pwOptions2.setCyclic(false);
        pwOptions2.setLabels("年级","班");
        //设置默认选中的三级项目
        pwOptions2.setSelectOptions(4, 5);
    }

    protected void setListener() {
        dateTv.setOnClickListener(onClickListener);
        classTv.setOnClickListener(onClickListener);
        //时间选择后回调
        pwTime.setOnTimeSelectListener(new TimePopupWindow.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                // TODO: 2018/11/19
                dateTv.setText(DateUtil.getYMDW(date));
            }
        });
        pwTime.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
            }
        });

        //监听确定选择按钮
        pwOptions2.setOnoptionsSelectListener(new OptionsPopupWindow.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String grade = options2Items.get(options1);
                String claz = options4Items.get(option2);
                classTv.setText(grade+"年级  "+claz + "班");
            }
        });

        pwOptions2.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
            }
        });
    }

    private void initData() {
        //添加父布局数据
//        menu.add(new TextMenu("校常规统计"));
//        menu.add(new TextMenu("校务统计"));
//        //添加子布局数据
//        for (int j = 0; j < 2; j++) {
//            List<TextMenu> childitem = new ArrayList<TextMenu>();
//            if (j<1){
//                for (int i = 0; i < 5; i++) {
//                    childitem.add(new TextMenu("子数据" + i));
//                }
//            }
//            subMenu.add(childitem);
//        }
    }

    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }

    private OnSingleClickListener onClickListener = new OnSingleClickListener() {
        @Override
        public void onSingleClick(View v) {
            switch (v.getId()) {
                case R.id.classTv:
                    backgroundAlpha(1.0f);
                    pwOptions2.showAtLocation(classTv, Gravity.BOTTOM, 0, 0);
                    pwOptions2.setSelectOptions(4,5);
                    break;
                case R.id.dateTv:
                    backgroundAlpha(1.0f);
                    pwTime.showAtLocation(dateTv, Gravity.BOTTOM, 0, 0,new Date());
                    break;
                default:
                    break;
            }
        }
    };

    private void setChartData(){
        chart.resetTracking();

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        for (int z = 0; z < 3; z++) {

            ArrayList<Entry> values = new ArrayList<>();

            for (int i = 0; i < 7; i++) {
                double val = new Random().nextInt(100);
                values.add(new Entry(i, (float) val));
            }

            LineDataSet d = new LineDataSet(values, "DataSet " + (z + 1));
            d.setLineWidth(2.5f);
            d.setCircleRadius(4f);

            int color = colors[z % colors.length];
            d.setColor(color);
            d.setCircleColor(color);
            dataSets.add(d);
        }

        // make the first DataSet dashed
        ((LineDataSet) dataSets.get(0)).enableDashedLine(10, 10, 0);
        ((LineDataSet) dataSets.get(0)).setColors(ColorTemplate.VORDIPLOM_COLORS);
        ((LineDataSet) dataSets.get(0)).setCircleColors(ColorTemplate.VORDIPLOM_COLORS);

        LineData data = new LineData(dataSets);
        chart.setData(data);
        chart.invalidate();
    }
}
