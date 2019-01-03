package com.androidcat.yucaiedu.fragment;

import android.graphics.Color;
import android.os.Message;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.androidcat.acnet.consts.OptMsgConst;
import com.androidcat.acnet.entity.Building;
import com.androidcat.acnet.entity.ClassMark;
import com.androidcat.acnet.entity.MenuItm;
import com.androidcat.acnet.entity.Room;
import com.androidcat.acnet.entity.response.BuildingsResponse;
import com.androidcat.acnet.entity.response.ClassMarkListResponse;
import com.androidcat.acnet.entity.response.MenuResponse;
import com.androidcat.acnet.manager.ClassesManager;
import com.androidcat.utilities.date.DateUtil;
import com.androidcat.utilities.listener.OnSingleClickListener;
import com.androidcat.utilities.persistence.SpUtil;
import com.androidcat.yucaiedu.AppData;
import com.androidcat.yucaiedu.R;
import com.androidcat.yucaiedu.adapter.ClassMarkAdapter;
import com.androidcat.yucaiedu.adapter.ClockBuildingRoomAdapter;
import com.androidcat.yucaiedu.adapter.EastBuildingRoomAdapter;
import com.androidcat.yucaiedu.adapter.MyExtendableListViewAdapter;
import com.androidcat.yucaiedu.adapter.TsBuildingRoomAdapter;
import com.androidcat.yucaiedu.chart.PercentFormatter;
import com.androidcat.yucaiedu.ui.listener.OnMenuCheckedListener;
import com.androidcat.yucaiedu.ui.listener.OnRoomCheckedListener;
import com.bigkoo.pickerview.OptionsPopupWindow;
import com.bigkoo.pickerview.TimePopupWindow;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RegularCheckFragment extends BaseFragment {

    TextView dateTv;
    TextView gradeTv;
    TextView buildingTv;
    TextView menuParent;
    TextView markMenu;
    int loc = 0;
    //private RadioGroup menuRc;
    private RadioGroup viewRg;
    private RadioGroup markRg;
    View buildingView;
    View buildingPicker;
    View clockBuilding;
    View tsCenter;
    View eastBuilding;
    GridView clockGrid;
    GridView tsGrid;
    GridView eastGrid;
    GridView statisticGrid;
    View markView;
    ImageView markHeart;

    //当日统计
    View statisticsView;
    //PieChart chartA;
    PieChart chartB;
    //PieChart chartC;
    boolean isHistory;
    ExpandableListView expandableListView;
    MyExtendableListViewAdapter menuAdapter = new MyExtendableListViewAdapter();

    OptionsPopupWindow pwOptions;
    OptionsPopupWindow pwOptions2;
    TimePopupWindow pwTime;
    private ArrayList<String> optionsItems = new ArrayList<String>();
    private ArrayList<String> gradeItems = new ArrayList<String>();

    private String building = "钟楼";
    private String curGrade = "一年级";
    private List<Room> clockBuildingRooms = new ArrayList<>();
    private ClockBuildingRoomAdapter roomAdapter;
    private List<Room> tsBuildingRooms = new ArrayList<>();
    private TsBuildingRoomAdapter tsBuildingRoomAdapter;
    private List<Room> eastBuildingRooms = new ArrayList<>();
    private EastBuildingRoomAdapter eastBuildingRoomAdapter;

    private List<ClassMark> classMarks = new ArrayList<>();
    private ClassMarkAdapter classMarkAdapter;

    private MenuItm curMenu = AppData.childMenu.get(0).get(0);
    private ClassMark curClass;
    private Room curRoom;
    private String curDate;

    ClassesManager classesManager;
    int statisticsTodayA = 0;
    int statisticsTodayB = 0;
    int statisticsTodayC = 0;

    @Override
    public void handleEventMsg(Message msg) {
        super.handleEventMsg(msg);
        switch (msg.what){
            case OptMsgConst.GET_DICT_FAIL:
                //dismissLoadingDialog();
                break;
            case OptMsgConst.GET_DICT_START:
                //showProgressDialog("加载中");
                break;
            case OptMsgConst.GET_DICT_SUCCESS:
               // dismissLoadingDialog();
                AppData.getAppData().menuResponse = (MenuResponse) msg.obj;
                SpUtil.setObject(AppData.getAppData().menuResponse);
                //loadMenu();
                break;
            case OptMsgConst.MSG_BUILDINGS_FAIL:
                dismissLoadingDialog();
                break;
            case OptMsgConst.MSG_BUILDINGS_START:
                showProgressDialog("加载中");
                break;
            case OptMsgConst.MSG_BUILDINGS_SUCCESS:
                dismissLoadingDialog();
                AppData.getAppData().buildingsResponse = (BuildingsResponse) msg.obj;
                loadBuildings();
                break;
            case OptMsgConst.POST_MARK_FAIL:
                dismissLoadingDialog();
                showToast("打分失败，请确保网络通畅后重试");
                break;
            case OptMsgConst.POST_MARK_START:
                showProgressDialog("正在打分");
                break;
            case OptMsgConst.POST_MARK_SUCCESS:
                dismissLoadingDialog();
                showToast("打分成功");
                AppData.getAppData().buildingsResponse = (BuildingsResponse) msg.obj;
                loadBuildings();
                if (viewRg.getCheckedRadioButtonId() == R.id.statisticRb){
                    loadCurrStatistics();
                }
                break;
            case OptMsgConst.MSG_MARK_RECORD_FAIL:
                dismissLoadingDialog();
                showToast("加载当日统计失败，请确保网络通畅后重试");
                break;
            case OptMsgConst.MSG_MARK_RECORD_START:
                showProgressDialog("正在统计");
                break;
            case OptMsgConst.MSG_MARK_RECORD_SUCCESS:
                dismissLoadingDialog();
                setupStatistics((ClassMarkListResponse) msg.obj);
                break;
            default:
                break;
        }
    }

    private OnSingleClickListener onClickListener = new OnSingleClickListener() {
        @Override
        public void onSingleClick(View v) {
            switch (v.getId()) {
                case R.id.buildingPicker:
                    backgroundAlpha(1.0f);
                    pwOptions.showAtLocation(buildingTv, Gravity.BOTTOM, 0, 0);
                    pwOptions.setSelectOptions(0);
                    if ("钟楼".equals(building)) {
                        pwOptions.setSelectOptions(0);
                    }else if("教学中心".equals(building)){
                        pwOptions.setSelectOptions(1);
                    }else if("东楼".equals(building)){
                        pwOptions.setSelectOptions(2);
                    }
                    break;
                case R.id.dateTv:
                    backgroundAlpha(1.0f);
                    pwTime.showAtLocation(dateTv, Gravity.BOTTOM, 0, 0,new Date());
                    break;
                case R.id.gradeTv:
                    backgroundAlpha(1.0f);
                    pwOptions2.showAtLocation(gradeTv, Gravity.BOTTOM, 0, 0);
                    pwOptions2.setSelectOptions(0);
                    if ("一年级".equals(curGrade)) {
                        pwOptions.setSelectOptions(0);
                    }else if("二年级".equals(curGrade)){
                        pwOptions2.setSelectOptions(1);
                    }else if("三年级".equals(curGrade)){
                        pwOptions2.setSelectOptions(2);
                    }else if("四年级".equals(curGrade)){
                        pwOptions2.setSelectOptions(3);
                    }else if("五年级".equals(curGrade)){
                        pwOptions2.setSelectOptions(4);
                    }else if("六年级".equals(curGrade)){
                        pwOptions2.setSelectOptions(5);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private OnMenuCheckedListener onMenuCheckedListener = new OnMenuCheckedListener() {
        @Override
        public void onMenuChecked(MenuItm item) {
            curMenu = item;
            checkMenu();
        }
    };

    private RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
            if(-1 == checkedId) {
                //此处是清除选中的回调。
                return;
            }
            View checkedView = mRootView.findViewById(checkedId);
            if (checkedView != null && checkedView instanceof RadioButton) {
                if (!((RadioButton) checkedView).isChecked()) {
                    //此处是某个选中按钮被取消的回调，在调用check方法修改选中的时候会触发
                    return;
                }
            }

            /*if (radioGroup == menuRc){
                clearMark();
                checkMenu(checkedId);
            }*/
            if (radioGroup == viewRg){
                if (checkedId == R.id.regularRb){
                    buildingPicker.setVisibility(View.VISIBLE);
                    buildingView.setVisibility(View.VISIBLE);
                    statisticsView.setVisibility(View.GONE);
                    gradeTv.setVisibility(View.GONE);
                    gradeTv.setBackgroundResource(R.color.transparent);
                    dateTv.setBackgroundResource(R.color.transparent);
                    dateTv.setClickable(false);
                }
                if (checkedId == R.id.statisticRb){
                    buildingPicker.setVisibility(View.GONE);
                    buildingView.setVisibility(View.GONE);
                    statisticsView.setVisibility(View.VISIBLE);
                    gradeTv.setVisibility(View.VISIBLE);
                    dateTv.setClickable(false);
                    dateTv.setBackgroundResource(R.color.transparent);
                    gradeTv.setClickable(true);
                    gradeTv.setBackgroundResource(R.drawable.shape_trans_radius3);
                    loadCurrStatistics();
                }
                if (checkedId == R.id.historyRb){
                    buildingPicker.setVisibility(View.GONE);
                    buildingView.setVisibility(View.GONE);
                    statisticsView.setVisibility(View.VISIBLE);
                    gradeTv.setVisibility(View.VISIBLE);
                    dateTv.setClickable(true);
                    gradeTv.setClickable(true);
                    gradeTv.setBackgroundResource(R.drawable.shape_trans_radius3);
                    dateTv.setBackgroundResource(R.drawable.shape_trans_radius3);
                    isHistory = true;
                }
            }
            //打分
            if(radioGroup == markRg){
                if (checkedId == R.id.aRb && checkIfRoomChecked()){
                    markHeart.setBackgroundResource(R.drawable.marka);
                    postMark(checkedId);
                }
                if (checkedId == R.id.bRb && checkIfRoomChecked()){
                    markHeart.setBackgroundResource(R.drawable.markb);
                    postMark(checkedId);
                }
                if (checkedId == R.id.cRb && checkIfRoomChecked()){
                    markHeart.setBackgroundResource(R.drawable.markb);
                    postMark(checkedId);
                }
            }
        }
    };

    @Override
    protected int getResID() {
        return R.layout.fragment_regular_check;
    }

    @Override
    protected void intLayout() {
        statisticsView = mRootView.findViewById(R.id.rcStatistics);
        buildingView = mRootView.findViewById(R.id.rcBuilding);
        markView = mRootView.findViewById(R.id.markView);
        markRg = mRootView.findViewById(R.id.markTab);
        viewRg = mRootView.findViewById(R.id.viewRg);
        //menuRc = mRootView.findViewById(R.id.menuRc);
        expandableListView = mRootView.findViewById(R.id.expend_list);
        dateTv = mRootView.findViewById(R.id.dateTv);
        gradeTv = mRootView.findViewById(R.id.gradeTv);
        menuParent = mRootView.findViewById(R.id.menuParent);
        markMenu = mRootView.findViewById(R.id.markMenu);
        buildingTv = mRootView.findViewById(R.id.buildingTv);
        buildingPicker = mRootView.findViewById(R.id.buildingPicker);
        clockGrid = mRootView.findViewById(R.id.clockBuildingGrid);
        clockBuilding = mRootView.findViewById(R.id.clockBuilding);
        tsCenter = mRootView.findViewById(R.id.tsCenter);
        eastBuilding = mRootView.findViewById(R.id.eastBuilding);
        markHeart = mRootView.findViewById(R.id.markHeart);
        tsGrid = mRootView.findViewById(R.id.tsCenterHeaderBuilding);
        eastGrid = mRootView.findViewById(R.id.eastBuildingGrid);
        statisticGrid = mRootView.findViewById(R.id.statisticGrid);
        //chartA = mRootView.findViewById(R.id.charta);
        chartB = mRootView.findViewById(R.id.chartb);
        //chartC = mRootView.findViewById(R.id.chartc);
        setupCharts();
    }

    @Override
    protected void initModule() {
        initData();
        dateTv.setText(DateUtil.getYMDW(new Date()));
        dateTv.setClickable(false);
        gradeTv.setClickable(false);
        buildingTv.setText("钟楼");
        viewRg.check(R.id.regularRb);
        markRg.clearCheck();
    }

    @Override
    protected void setListener() {
        pickerLintener();
        expandableListView.setAdapter(menuAdapter);
        menuAdapter.setOnMenuCheckedListener(onMenuCheckedListener);
        //设置分组的监听
        /*expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });*/
        //设置子项布局监听
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                return true;
            }
        });
        //控制他只能打开一个组
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                int count = new MyExtendableListViewAdapter().getGroupCount();
                for(int i = 0;i < count;i++){
                    if (i!=groupPosition){
                        expandableListView.collapseGroup(i);
                    }
                }
            }
        });
        //menuRc.setOnCheckedChangeListener(onCheckedChangeListener);
        viewRg.setOnCheckedChangeListener(onCheckedChangeListener);
        markRg.setOnCheckedChangeListener(onCheckedChangeListener);
        dateTv.setOnClickListener(onClickListener);
        gradeTv.setOnClickListener(onClickListener);
        buildingPicker.setOnClickListener(onClickListener);
        //时间选择后回调
        pwTime.setOnTimeSelectListener(new TimePopupWindow.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                curDate = sdf.format(date);
                dateTv.setText(DateUtil.getYMDW(date));
                //切换即触发
                loadHistoryStatistics();
            }
        });
        pwTime.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
            }
        });

        //监听确定选择按钮
        pwOptions.setOnoptionsSelectListener(new OptionsPopupWindow.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String building = optionsItems.get(options1);
                loc = options1;
                RegularCheckFragment.this.building = building;
                buildingTv.setText(building);
                switchBuilding();
            }
        });
        pwOptions.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
            }
        });

        //监听年级确定选择按钮
        pwOptions2.setOnoptionsSelectListener(new OptionsPopupWindow.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String grade = gradeItems.get(options1);
                RegularCheckFragment.this.curGrade = grade;
                gradeTv.setText(grade);
                onGradeChange();
            }
        });
        pwOptions2.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
            }
        });
    }

    /**
     * 自定义picker控件事件
     */
    private void pickerLintener() {
        pwTime = new TimePopupWindow(getActivity(), TimePopupWindow.Type.YEAR_MONTH_DAY);
        pwTime.setTime(new Date());
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        pwTime.setRange(2000, year);

        //楼栋选择
        optionsItems.clear();
        pwOptions = new OptionsPopupWindow(getActivity());
        //选项1
        optionsItems.add("钟楼");
        optionsItems.add("学教中心");
        optionsItems.add("东楼");
        pwOptions.setPicker(optionsItems);
        //设置默认选中的三级项目
        pwOptions.setSelectOptions(0);

        //年级选择
        gradeItems.clear();
        pwOptions2 = new OptionsPopupWindow(getActivity());
        //选项1
        gradeItems.add("一年级");
        gradeItems.add("二年级");
        gradeItems.add("三年级");
        gradeItems.add("四年级");
        gradeItems.add("五年级");
        gradeItems.add("六年级");
        pwOptions2.setPicker(gradeItems);
        //设置默认选中的三级项目
        pwOptions2.setSelectOptions(0);
    }

    @Override
    public void iOnResume() {
        super.iOnResume();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if(viewRg.getCheckedRadioButtonId() == R.id.historyRb){
            return;
        }
        menu.add(0, 3, 1, "A");
        menu.add(0, 2, 2, "B");
        menu.add(0, 1, 3, "C");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //showToast("pos:"+item.getTitle());
        String loginName = AppData.getAppData().user.loginName;
        String token = AppData.getAppData().user.token;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        String mark = item.getItemId()+"";
        classesManager.postMark(loginName,token,date,curClass.roomId,curMenu.dictLabel,mark);
        return true;
    }

    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }

    private void initData(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        this.curDate = date;
        if (classesManager == null){
            classesManager = new ClassesManager(getActivity(),baseHandler);
        }
        classesManager.getMenuRc(AppData.getAppData().user.loginName,AppData.getAppData().user.token);
        classesManager.getGradeList(AppData.getAppData().user.loginName,AppData.getAppData().user.token);
        classesManager.getBuildings(AppData.getAppData().user.loginName,AppData.getAppData().user.token,curDate,curMenu.dictLabel);
        //
        loadEmptyRooms();
        //
        //menuRc.check(R.id.recitingRb);
        expandableListView.expandGroup(0);
    }

    void onGradeChange(){
        if (viewRg.getCheckedRadioButtonId() == R.id.statisticRb){
            loadCurrStatistics();
        }
        if (viewRg.getCheckedRadioButtonId() == R.id.historyRb){
            loadHistoryStatistics();
        }
    }

    private void loadCurrStatistics(){
        pwTime.setTime(new Date());
        String loginName = AppData.getAppData().user.loginName;
        String token = AppData.getAppData().user.token;
        int classGradeId = AppData.gradeMap.get(gradeTv.getText().toString());
        String timetable = curMenu.dictLabel;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        curDate = sdf.format(new Date());
        dateTv.setText(DateUtil.getYMDW(new Date()));
        classesManager.classMarkList(loginName,token,classGradeId,timetable,curDate);
    }

    private void loadHistoryStatistics(){
        String loginName = AppData.getAppData().user.loginName;
        String token = AppData.getAppData().user.token;
        int classGradeId = AppData.gradeMap.get(gradeTv.getText().toString());
        String timetable = curMenu.dictLabel;
        classesManager.classMarkList(loginName,token,classGradeId,timetable,curDate);
    }

    private void setupStatistics(ClassMarkListResponse classMarkListResponse){
        classMarks.clear();
        classMarks = classMarkListResponse.content;
        if (classMarks!=null && classMarks.size()>0){
            statisticsTodayA = classMarks.get(0).statisticsTodayA;
            statisticsTodayB = classMarks.get(0).statisticsTodayB;
            statisticsTodayC = classMarks.get(0).statisticsTodayC;
        }
        classMarkAdapter = new ClassMarkAdapter(getActivity(),classMarks);
        statisticGrid.setAdapter(classMarkAdapter);
        registerForContextMenu(statisticGrid);
        statisticGrid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(viewRg.getCheckedRadioButtonId() == R.id.historyRb){
                    return false;
                }
                //selected pos
                curClass = (ClassMark) adapterView.getAdapter().getItem(i);
                return false;
            }
        });

        //setData(chartA,statisticsTodayA,(statisticsTodayB+statisticsTodayC));
        setData(chartB,statisticsTodayA,statisticsTodayB,statisticsTodayC);
        //setData(chartC,statisticsTodayC,(statisticsTodayB+statisticsTodayA));
    }

    void switchBuilding(){
        roomAdapter.clearCheck();
        eastBuildingRoomAdapter.clearCheck();
        tsBuildingRoomAdapter.clearCheck();
        clearMark();
        if (loc == 0){
            clockBuilding.setVisibility(View.VISIBLE);
            tsCenter.setVisibility(View.GONE);
            eastBuilding.setVisibility(View.GONE);
        }
        if (loc == 1){
            clockBuilding.setVisibility(View.GONE);
            tsCenter.setVisibility(View.VISIBLE);
            eastBuilding.setVisibility(View.GONE);
            animateCharts();
        }
        if (loc == 2){
            clockBuilding.setVisibility(View.GONE);
            tsCenter.setVisibility(View.GONE);
            eastBuilding.setVisibility(View.VISIBLE);
            animateCharts();
        }
    }

    private void postMark(int checkedId){
        if (viewRg.getCheckedRadioButtonId() != R.id.regularRb){
            return;
        }
        if (curRoom == null){
            return;
        }
        String loginName = AppData.getAppData().user.loginName;
        String token = AppData.getAppData().user.token;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        String mark = AppData.markMenuMap.get(checkedId)+"";
        classesManager.postMark(loginName,token,date,curRoom.deptId,curMenu.dictLabel,mark);
    }

    private void setupCharts(){
        //setChartStyle(chartA);
        setChartStyle(chartB);
        //setChartStyle(chartC);
    }

    private void animateCharts(){
        //chartA.animateX(2000, Easing.EaseInOutQuad);
        chartB.animateX(2000, Easing.EaseInOutQuad);
        //chartC.animateX(2000, Easing.EaseInOutQuad);
    }

    private void setChartStyle(PieChart chart){
        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 45, 5);
        chart.setDragDecelerationFrictionCoef(0.95f);
        /*if(chart == chartA)
            chart.setCenterText("A");
        if(chart == chartB)
            chart.setCenterText("B");
        if(chart == chartC)
            chart.setCenterText("C");*/
        chart.setDrawHoleEnabled(false);
        //chart.setHoleColor(Color.TRANSPARENT);

        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);

        //chart.setHoleRadius(38f);
        chart.setTransparentCircleRadius(61f);

        //chart.setDrawCenterText(true);
        //chart.setCenterTextSize(20);
        chart.setRotationAngle(0);
        // enable rotation of the chart by touch
        chart.setRotationEnabled(true);
        chart.setHighlightPerTapEnabled(true);
        chart.setUsePercentValues(true);
        //chart.spin(2000, 0, 360,Easing.EaseInOutQuad);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setXEntrySpace(5f);
        l.setYEntrySpace(0f);
        l.setYOffset(3f);

        // entry label styling
        chart.setEntryLabelColor(Color.WHITE);
    }

    private void setData(PieChart chart,int aMark,int bMark,int cMark) {
        ArrayList<PieEntry> entries = new ArrayList<>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        String label = "";
        /*if (chart == chartA){
            label = "A";
        }if (chart == chartB){
            label = "B";
        }
        if (chart == chartC){
            label = "C";
        }*/

        entries.add(new PieEntry(aMark,"A"));
        entries.add(new PieEntry(bMark,"B"));
        entries.add(new PieEntry(cMark,"C"));
        PieDataSet dataSet = new PieDataSet(entries, "");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(8f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(chart));
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        chart.setData(data);
        // undo all highlights
        //chart.highlightValues({30});
        chart.invalidate();
        chart.animateY(2000);
    }

    void clearMark(){
        markRg.clearCheck();
        markHeart.setBackgroundResource(0);
    }

    boolean checkIfRoomChecked(){
        if ("钟楼".equals(building) && roomAdapter.checkedRoom == null) {
            clearMark();
            showToast("请先选择教室");
            return false;
        }else if("教学中心".equals(building) && tsBuildingRoomAdapter.checkedRoom == null){
            clearMark();
            showToast("请先选择教室");
            return false;
        }else if("东楼".equals(building) && eastBuildingRoomAdapter.checkedRoom == null){
            clearMark();
            showToast("请先选择教室");
            return false;
        }
        return true;
    }

    void checkMenu(){
        markMenu.setText(curMenu.dictLabel);
        if (viewRg.getCheckedRadioButtonId() == R.id.regularRb){
            menuParent.setText(curMenu.parent);
        }
        if (viewRg.getCheckedRadioButtonId() == R.id.statisticRb){
            loadCurrStatistics();
        }
        if (viewRg.getCheckedRadioButtonId() == R.id.historyRb){
            loadHistoryStatistics();
        }
    }

    void loadBuildings(){
        List<Building> buildings = AppData.getAppData().buildingsResponse.content.get(0).children;
        for (Building building : buildings){
            if ("钟楼".equals(building.deptName)){
                roomAdapter = new ClockBuildingRoomAdapter(getActivity(),building.children);
                clockGrid.setAdapter(roomAdapter);
                roomAdapter.onRoomCheckedListener = onRoomCheckedListener;
            }
            if ("学教中心".equals(building.deptName)){
                tsBuildingRoomAdapter = new TsBuildingRoomAdapter(getActivity(),building.children);
                tsGrid.setAdapter(tsBuildingRoomAdapter);
                tsBuildingRoomAdapter.onRoomCheckedListener = onRoomCheckedListener;
            }
            if ("东楼".equals(building.deptName)){
                eastBuildingRoomAdapter = new EastBuildingRoomAdapter(getActivity(),building.children);
                eastGrid.setAdapter(eastBuildingRoomAdapter);
                eastBuildingRoomAdapter.onRoomCheckedListener = onRoomCheckedListener;
            }
        }
    }

    OnRoomCheckedListener onRoomCheckedListener = new OnRoomCheckedListener() {
        @Override
        public void onRoomChecked(Room room) {
            clearMark();
            curRoom = room;
        }
    };


    public static final int[] LIBERTY_COLORS = {
            Color.rgb(207, 248, 246), Color.rgb(148, 212, 212), Color.rgb(136, 180, 187),
            Color.rgb(118, 174, 175), Color.rgb(42, 109, 130)
    };

    public static final int[] COLORFUL_COLORS = {
            Color.rgb(193, 37, 82), Color.rgb(255, 102, 0), Color.rgb(245, 199, 0),
            Color.rgb(106, 150, 31), Color.rgb(179, 100, 53)
    };

    void loadEmptyRooms(){
        //钟楼
        if (clockBuildingRooms.size() == 0){
            for(int i = 1;i < 19;i++){
                Room room = new Room();
                room.deptName = "空闲";
                clockBuildingRooms.add(room);
            }
        }
        if (roomAdapter == null){
            roomAdapter = new ClockBuildingRoomAdapter(getActivity(),clockBuildingRooms);
            clockGrid.setAdapter(roomAdapter);
        }
        roomAdapter.onRoomCheckedListener = onRoomCheckedListener;
        //教学中心
        if (tsBuildingRooms.size() == 0){
            for(int i = 1;i < 57;i++){
                Room room = new Room();
                room.deptName = "空闲";
                tsBuildingRooms.add(room);
            }
        }
        if (tsBuildingRoomAdapter == null){
            tsBuildingRoomAdapter = new TsBuildingRoomAdapter(getActivity(),tsBuildingRooms);
        }
        tsGrid.setAdapter(tsBuildingRoomAdapter);
        tsBuildingRoomAdapter.onRoomCheckedListener = onRoomCheckedListener;
        //栋楼
        if (eastBuildingRooms.size() == 0){
            for(int i = 1;i < 37;i++){
                Room room = new Room();
                room.deptName = "空闲";
                eastBuildingRooms.add(room);
            }
        }
        if (eastBuildingRoomAdapter == null){
            eastBuildingRoomAdapter = new EastBuildingRoomAdapter(getActivity(),eastBuildingRooms);
        }
        eastGrid.setAdapter(eastBuildingRoomAdapter);
        eastBuildingRoomAdapter.onRoomCheckedListener = onRoomCheckedListener;
    }
}
