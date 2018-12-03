package com.androidcat.yucaiedu.fragment;

import android.graphics.Color;
import android.os.Message;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
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
import com.androidcat.acnet.entity.response.MenuResponse;
import com.androidcat.acnet.manager.ClassesManager;
import com.androidcat.utilities.date.DateUtil;
import com.androidcat.utilities.listener.OnSingleClickListener;
import com.androidcat.yucaiedu.AppData;
import com.androidcat.yucaiedu.R;
import com.androidcat.yucaiedu.adapter.ClassMarkAdapter;
import com.androidcat.yucaiedu.adapter.ClockBuildingRoomAdapter;
import com.androidcat.yucaiedu.adapter.EastBuildingRoomAdapter;
import com.androidcat.yucaiedu.adapter.TsBuildingRoomAdapter;
import com.androidcat.yucaiedu.chart.PercentFormatter;
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
import java.util.Random;

public class RegularCheckFragment extends BaseFragment {

    TextView dateTv;
    TextView gradeTv;
    TextView buildingTv;
    TextView menuParent;
    TextView markMenu;
    int loc = 0;
    private RadioGroup menuRc;
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
    PieChart chartA;
    PieChart chartB;
    PieChart chartC;
    boolean isHistory;

    OptionsPopupWindow pwOptions;
    TimePopupWindow pwTime;
    private ArrayList<String> optionsItems = new ArrayList<String>();

    private String building = "钟楼";
    private List<Room> clockBuildingRooms = new ArrayList<>();
    private ClockBuildingRoomAdapter roomAdapter;
    private List<Room> tsBuildingRooms = new ArrayList<>();
    private TsBuildingRoomAdapter tsBuildingRoomAdapter;
    private List<Room> eastBuildingRooms = new ArrayList<>();
    private EastBuildingRoomAdapter eastBuildingRoomAdapter;

    private List<ClassMark> classMarks = new ArrayList<>();
    private ClassMarkAdapter classMarkAdapter;

    private String curMenu;
    private String curMark;
    private Room curRoom;

    ClassesManager classesManager;

    @Override
    public void handleEventMsg(Message msg) {
        super.handleEventMsg(msg);
        switch (msg.what){
            case OptMsgConst.GET_DICT_FAIL:
                dismissLoadingDialog();
                break;
            case OptMsgConst.GET_DICT_START:
                showProgressDialog("加载中");
                break;
            case OptMsgConst.GET_DICT_SUCCESS:
                dismissLoadingDialog();
                AppData.getAppData().menuResponse = (MenuResponse) msg.obj;
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
                default:
                    break;
            }
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

            if (radioGroup == menuRc){
                clearMark();
                checkMenu(checkedId);
            }
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
                    gradeTv.setClickable(false);
                    gradeTv.setBackgroundResource(R.color.transparent);
                    dateTv.setBackgroundResource(R.color.transparent);
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
        menuRc = mRootView.findViewById(R.id.menuRc);
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
        chartA = mRootView.findViewById(R.id.charta);
        chartB = mRootView.findViewById(R.id.chartb);
        chartC = mRootView.findViewById(R.id.chartc);
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
        menuRc.setOnCheckedChangeListener(onCheckedChangeListener);
        viewRg.setOnCheckedChangeListener(onCheckedChangeListener);
        markRg.setOnCheckedChangeListener(onCheckedChangeListener);
        dateTv.setOnClickListener(onClickListener);
        buildingPicker.setOnClickListener(onClickListener);
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
    }

    @Override
    public void iOnResume() {
        super.iOnResume();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, 1, 0, "A");
        menu.add(0, 2, 0, "B");
        menu.add(0, 3, 0, "C");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        showToast("pos:"+item.getTitle());
        return super.onContextItemSelected(item);
    }

    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }

    private void initData(){
        if (classesManager == null){
            classesManager = new ClassesManager(getActivity(),baseHandler);
        }
        classesManager.getMenuRc(AppData.getAppData().user.loginName,AppData.getAppData().user.token);
        classesManager.getBuildings(AppData.getAppData().user.loginName,AppData.getAppData().user.token);
        //
        loadEmptyRooms();

        //tongji
        if (classMarks.size() == 0){
            for(int i = 1;i < 16;i++){
                ClassMark classMark = new ClassMark();
                classMark.clsName = "五("+i+")";
                int a = new Random().nextInt(3);
                if (a%3 == 1) classMark.score = 1;
                if (a%3 == 2) classMark.score = 3;
                if (a%3 == 0) classMark.score = 5;
                classMarks.add(classMark);
            }
        }
        if (classMarkAdapter == null){
            classMarkAdapter = new ClassMarkAdapter(getActivity(),classMarks);
            statisticGrid.setAdapter(classMarkAdapter);
        }
        registerForContextMenu(statisticGrid);
        statisticGrid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //selected pos
                return false;
            }
        });
        //
        menuRc.check(R.id.recitingRb);
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
        classesManager.postMark(loginName,token,date,curRoom.deptId,curMenu,mark);
    }

    private void setupCharts(){
        setChartStyle(chartA);
        setChartStyle(chartB);
        setChartStyle(chartC);
        setData(chartA,30);
        setData(chartB,40);
        setData(chartC,30);
    }

    private void animateCharts(){
        chartA.animateX(2000, Easing.EaseInOutQuad);
        chartB.animateX(2000, Easing.EaseInOutQuad);
        chartC.animateX(2000, Easing.EaseInOutQuad);
    }

    private void setChartStyle(PieChart chart){
        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 45, 5);
        chart.setDragDecelerationFrictionCoef(0.95f);
        if(chart == chartA)
            chart.setCenterText("A");
        if(chart == chartB)
            chart.setCenterText("B");
        if(chart == chartC)
            chart.setCenterText("C");
        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(Color.TRANSPARENT);

        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);

        chart.setHoleRadius(38f);
        chart.setTransparentCircleRadius(61f);

        chart.setDrawCenterText(true);
        chart.setCenterTextSize(20);
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

    private void setData(PieChart chart,float range) {
        ArrayList<PieEntry> entries = new ArrayList<>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        entries.add(new PieEntry(range,"A"));
        entries.add(new PieEntry(100-range,"其他"));
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

    void loadMenu(){
        List<MenuItm> menuItmList = AppData.getAppData().menuResponse.content;
        for (int i =0;i<menuItmList.size();i++){
            MenuItm menuItm = menuItmList.get(i);
            if (menuItm != null){
                if (i==0){
                    AppData.rcMenuItmMap.put(R.id.recitingRb,menuItm);
                    ((RadioButton)mRootView.findViewById(R.id.recitingRb)).setText(menuItm.dictLabel);
                }
                if (i==1){
                    AppData.rcMenuItmMap.put(R.id.readingRb,menuItm);
                    ((RadioButton)mRootView.findViewById(R.id.readingRb)).setText(menuItm.dictLabel);
                }
                if (i==2){
                    AppData.rcMenuItmMap.put(R.id.meetingRb,menuItm);
                    ((RadioButton)mRootView.findViewById(R.id.meetingRb)).setText(menuItm.dictLabel);
                }
                if (i==3){
                    AppData.rcMenuItmMap.put(R.id.exerciseRb,menuItm);
                    ((RadioButton)mRootView.findViewById(R.id.exerciseRb)).setText(menuItm.dictLabel);
                }
                if (i==4){
                    AppData.rcMenuItmMap.put(R.id.eyeExerciseRb,menuItm);
                    ((RadioButton)mRootView.findViewById(R.id.eyeExerciseRb)).setText(menuItm.dictLabel);
                }
                if (i==5){
                    AppData.rcMenuItmMap.put(R.id.restTimeRb,menuItm);
                    ((RadioButton)mRootView.findViewById(R.id.restTimeRb)).setText(menuItm.dictLabel);
                }
                if (i==6){
                    AppData.rcMenuItmMap.put(R.id.goodThingsRb,menuItm);
                    ((RadioButton)mRootView.findViewById(R.id.goodThingsRb)).setText(menuItm.dictLabel);
                }
                if (i==7){
                    AppData.rcMenuItmMap.put(R.id.healthRb,menuItm);
                    ((RadioButton)mRootView.findViewById(R.id.healthRb)).setText(menuItm.dictLabel);
                }
                if (i==8){
                    AppData.rcMenuItmMap.put(R.id.energyRb,menuItm);
                    ((RadioButton)mRootView.findViewById(R.id.energyRb)).setText(menuItm.dictLabel);
                }
                if (i==9){
                    AppData.rcMenuItmMap.put(R.id.morningRb,menuItm);
                    ((RadioButton)mRootView.findViewById(R.id.morningRb)).setText(menuItm.dictLabel);
                }
                if (i==10){
                    AppData.rcMenuItmMap.put(R.id.noonRb,menuItm);
                    ((RadioButton)mRootView.findViewById(R.id.noonRb)).setText(menuItm.dictLabel);
                }
                if (i==11){
                    AppData.rcMenuItmMap.put(R.id.afternoonRb,menuItm);
                    ((RadioButton)mRootView.findViewById(R.id.afternoonRb)).setText(menuItm.dictLabel);
                }
                if (i==12){
                    AppData.rcMenuItmMap.put(R.id.tsQueueRb,menuItm);
                    ((RadioButton)mRootView.findViewById(R.id.tsQueueRb)).setText(menuItm.dictLabel);
                }
                if (i==13){
                    AppData.rcMenuItmMap.put(R.id.afterSchoolRb,menuItm);
                    ((RadioButton)mRootView.findViewById(R.id.afterSchoolRb)).setText(menuItm.dictLabel);
                }
            }
        }

        menuRc.check(R.id.recitingRb);
    }

    void checkMenu(int menuId){
        curMenu = AppData.rcMenuItmMap.get(menuId).dictLabel;
        menuParent.setText(AppData.rcMenuItmMap.get(menuId).parent);
        markMenu.setText(curMenu);
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
