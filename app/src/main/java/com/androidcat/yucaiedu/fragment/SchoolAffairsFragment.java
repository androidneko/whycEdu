package com.androidcat.yucaiedu.fragment;

import android.content.Context;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.androidcat.acnet.consts.OptMsgConst;
import com.androidcat.acnet.entity.ClassItemList;
import com.androidcat.acnet.entity.EventLogItem;
import com.androidcat.acnet.entity.MarkClassItem;
import com.androidcat.acnet.entity.MarkHistoryItem;
import com.androidcat.acnet.entity.MarkItem;
import com.androidcat.acnet.entity.MarkRoomItem;
import com.androidcat.acnet.entity.MarkTeacherItem;
import com.androidcat.acnet.entity.RoomItemList;
import com.androidcat.acnet.entity.TeacherItemList;
import com.androidcat.acnet.entity.UnmarkClassItem;
import com.androidcat.acnet.entity.UnmarkRoomItem;
import com.androidcat.acnet.entity.UnmarkTeacherItem;
import com.androidcat.acnet.entity.response.MarkClassResponse;
import com.androidcat.acnet.entity.response.MarkRoomResponse;
import com.androidcat.acnet.entity.response.MarkTeacherResponse;
import com.androidcat.acnet.entity.response.QueryEventResponse;
import com.androidcat.acnet.entity.response.SaHistoryMenuList;
import com.androidcat.acnet.entity.response.SaHistoryResponse;
import com.androidcat.acnet.manager.ClassesManager;
import com.androidcat.utilities.LogUtil;
import com.androidcat.utilities.Utils;
import com.androidcat.utilities.date.DateUtil;
import com.androidcat.utilities.listener.OnSingleClickListener;
import com.androidcat.yucaiedu.AppData;
import com.androidcat.yucaiedu.R;
import com.androidcat.yucaiedu.adapter.FiltableAdapter;
import com.androidcat.yucaiedu.adapter.TobeMarkedAdapter;
import com.androidcat.yucaiedu.ui.listener.OnItemCheckedListener;
import com.anroidcat.acwidgets.FloatBar;
import com.bigkoo.pickerview.TimePopupWindow;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SchoolAffairsFragment extends BaseFragment {
    private static final String TAG = "SchoolAffairsFragment";
    private static String HEAD = "";

    private View saReportsView;
    private View saMarkView;
    private View eventView;
    private View markView;
    private View titleTab;
    RadioGroup viewRg;
    View clear;
    View done;
    EditText searchEt;
    private RadioGroup menuSa;
    private EditText eventEt;
    private TextView dateTv;
    private TextView viewTitleTv;
    private TextView viewSubTitleTv;
    private TextView editTitleTv;
    private TextView editSubTitleTv;
    private TextView itemNameTv;
    private TextView itemDescTv;
    private TextView listTitleTv;

    TextView communication;
    TextView development;
    TextView teachAndLearn;
    TextView teachActivities;
    TextView stuActivities;
    TextView goodTeacher;
    TextView badTeacher;
    TextView goodTeacherPro;
    TextView badTeacherPro;
    TextView goodTeacherTran;
    TextView badTeacherTran;
    TextView accident;
    TextView safety;
    TextView afterSchool;
    TextView leaving;
    TextView goodTeacherOut;
    TextView badTeacherOut;
    TextView goodTeacherOff;
    TextView badTeacherOff;
    TextView memo;

    GridView totalGrid;
    GridView goodGrid;
    GridView badGrid;

    private List<MarkItem> unMarkedItems = new ArrayList<>();
    private FiltableAdapter totalAdapter;
    private List<MarkItem> goodItems = new ArrayList<>();
    private TobeMarkedAdapter goodAdapter;
    private List<MarkItem> badItems = new ArrayList<>();
    private TobeMarkedAdapter badAdapter;

    private LayoutInflater mInflater;
    private FloatBar mFloatBar;
    private List<String> naviBars = new ArrayList<String>();

    private TimePopupWindow pwTime;
    private String curDate;
    private String curMenu;
    private String typeCode = "学术交流";
    private int floatBarIndex = 0;
    private MarkItem curItem;
    private ClassesManager classesManager;



    private RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
            if (radioGroup == menuSa){
                checkMenu(checkedId);
            }
            if (radioGroup == viewRg){
                if (checkedId == R.id.curLogRb){
                    saReportsView.setVisibility(View.GONE);
                    saMarkView.setVisibility(View.VISIBLE);
                    dateTv.setBackgroundResource(R.color.transparent);
                    dateTv.setClickable(false);
                }
                if (checkedId == R.id.historyRb){
                    saReportsView.setVisibility(View.VISIBLE);
                    saMarkView.setVisibility(View.GONE);
                    dateTv.setClickable(true);
                    dateTv.setBackgroundResource(R.drawable.shape_trans_radius3);
                    loadHistoryReport();
                }
            }
        }
    };

    OnItemCheckedListener onItemCheckedListener = new OnItemCheckedListener() {
        @Override
        public void onItemChecked(MarkItem item) {
            if (item instanceof UnmarkTeacherItem){
                itemNameTv.setText(((UnmarkTeacherItem) item).userName);
            }
            if (item instanceof UnmarkRoomItem){
                itemNameTv.setText(((UnmarkRoomItem) item).deptName);
            }
            if (item instanceof UnmarkClassItem){
                itemNameTv.setText(((UnmarkClassItem) item).deptName);
            }
            itemDescTv.setText(item.remark);
        }
    };

    private OnSingleClickListener onClickListener = new OnSingleClickListener() {
        @Override
        public void onSingleClick(View v) {
            switch (v.getId()) {
                case R.id.dateTv:
                    backgroundAlpha(1.0f);
                    pwTime.showAtLocation(dateTv, Gravity.BOTTOM, 0, 0,new Date());
                    break;
                case R.id.clear:
                    eventEt.setText("");
                    break;
                case R.id.done:
                    postEventMsg();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void handleEventMsg(Message msg) {
        super.handleEventMsg(msg);
        switch (msg.what){
            case OptMsgConst.POST_EVENT_FAIL:
                dismissLoadingDialog();
                break;
            case OptMsgConst.POST_EVENT_START:
                showProgressDialog("加载中");
                break;
            case OptMsgConst.POST_EVENT_SUCCESS:
                dismissLoadingDialog();
                showToast("提交成功");
                break;
            case OptMsgConst.GET_SA_LIST_FAIL:
                dismissLoadingDialog();
                showToast("加载失败");
                break;
            case OptMsgConst.GET_SA_LIST_START:
                showProgressDialog("加载中");
                break;
            case OptMsgConst.GET_SA_LIST_SUCCESS:
                dismissLoadingDialog();
                if (msg.arg1 == 1){
                    parseMarkTeacherItems((MarkTeacherResponse) msg.obj);
                }
                if (msg.arg1 == 2){
                    parseMarkOfficeItems((MarkRoomResponse) msg.obj);
                }
                if (msg.arg1 == 3){
                    parseMarkClassItems((MarkClassResponse) msg.obj);
                }
                break;
            case OptMsgConst.SA_MARK_FAIL:
                dismissLoadingDialog();
                showToast("提交失败!请确认网络畅通后重试。");
                break;
            case OptMsgConst.SA_MARK_START:
                showProgressDialog("正在打分");
                break;
            case OptMsgConst.SA_MARK_SUCCESS:
                dismissLoadingDialog();
                showToast("打分成功");
                searchEt.setText("");
                queryMarkItems(menuSa.getCheckedRadioButtonId());
                break;
            case OptMsgConst.MSG_SA_HISTORY_FAIL:
                dismissLoadingDialog();
                showToast("提交失败!请确认网络畅通后重试。");
                break;
            case OptMsgConst.MSG_SA_HISTORY_START:
                showProgressDialog("正在加载");
                break;
            case OptMsgConst.MSG_SA_HISTORY_SUCCESS:
                dismissLoadingDialog();
                setupReportView((SaHistoryResponse) msg.obj);
                break;
            case OptMsgConst.QUERY_EVENT_FAIL:
                dismissLoadingDialog();
                showToast("查询日志失败!请确认网络畅通后重试。");
                break;
            case OptMsgConst.QUERY_EVENT_START:
                showProgressDialog("正在查询");
                break;
            case OptMsgConst.QUERY_EVENT_SUCCESS:
                dismissLoadingDialog();
                setupEventView((QueryEventResponse) msg.obj);
                break;
            default:
                break;
        }
    }

    protected void naviBarSetup(List<String> items) {
        mFloatBar.fitScreenWidth(Utils.dp2px(getActivity(),280));
        NaviAdapter adapter =  new NaviAdapter(this.getActivity(), items);
        mFloatBar.setAdapter(adapter);
        mFloatBar.setOnFloatItemClickListener(new FloatBar.OnFloatItemClickListener() {

            @Override
            public void OnItemClick(View view, int index, Object mT) {
                typeCode = (String) mT;
                floatBarIndex = index;
                eventEt.setText("");
                queryEvent();
            }
        });

        if(items != null){
            //makeItemManager(headItems.get(0).getCMSID());
        }

    }

    @Override
    protected int getResID() {
        return R.layout.fragment_school_affairs;
    }

    @Override
    protected void intLayout() {
        mInflater = LayoutInflater.from(this.getActivity());
        saReportsView = mRootView.findViewById(R.id.saReportsView);
        saMarkView = mRootView.findViewById(R.id.saMarkView);
        mFloatBar = (FloatBar) mRootView.findViewById(R.id.navi_bar);
        markView = mRootView.findViewById(R.id.markView);
        eventView = mRootView.findViewById(R.id.eventView);
        titleTab = mRootView.findViewById(R.id.titleTab);
        menuSa = mRootView.findViewById(R.id.menuSa);
        viewRg = mRootView.findViewById(R.id.viewRg);
        eventEt = mRootView.findViewById(R.id.msgEt);
        viewTitleTv = mRootView.findViewById(R.id.viewTitleTv);
        viewSubTitleTv = mRootView.findViewById(R.id.viewSubTitleTv);
        editTitleTv = mRootView.findViewById(R.id.editTitleTv);
        editSubTitleTv = mRootView.findViewById(R.id.editSubTitleTv);
        clear = mRootView.findViewById(R.id.clear);
        done = mRootView.findViewById(R.id.done);
        totalGrid = mRootView.findViewById(R.id.totalGrid);
        goodGrid = mRootView.findViewById(R.id.goodGrid);
        badGrid = mRootView.findViewById(R.id.badGrid);
        itemNameTv = mRootView.findViewById(R.id.itemNameTv);
        itemDescTv = mRootView.findViewById(R.id.itemDescTv);
        searchEt = mRootView.findViewById(R.id.searchEt);
        dateTv = mRootView.findViewById(R.id.dateTv);
        listTitleTv = mRootView.findViewById(R.id.listTitleTv);

        communication = mRootView.findViewById(R.id.communication);
        development = mRootView.findViewById(R.id.development);
        teachAndLearn = mRootView.findViewById(R.id.teachAndLearn);
        teachActivities = mRootView.findViewById(R.id.teachActivities);
        stuActivities = mRootView.findViewById(R.id.stuActivities);
        goodTeacher = mRootView.findViewById(R.id.goodTeacher);
        badTeacher = mRootView.findViewById(R.id.badTeacher);
        goodTeacherPro = mRootView.findViewById(R.id.goodTeacherPro);
        badTeacherPro = mRootView.findViewById(R.id.badTeacherPro);
        goodTeacherTran = mRootView.findViewById(R.id.goodTeacherTran);
        badTeacherTran = mRootView.findViewById(R.id.badTeacherTran);
        accident = mRootView.findViewById(R.id.accident);
        safety = mRootView.findViewById(R.id.safety);
        afterSchool = mRootView.findViewById(R.id.afterSchool);
        leaving = mRootView.findViewById(R.id.leaving);
        goodTeacherOut = mRootView.findViewById(R.id.goodTeacherOut);
        badTeacherOut = mRootView.findViewById(R.id.badTeacherOut);
        goodTeacherOff = mRootView.findViewById(R.id.goodTeacherOff);
        badTeacherOff = mRootView.findViewById(R.id.badTeacherOff);
        memo = mRootView.findViewById(R.id.memo);
    }

    @Override
    protected void setListener() {
        pickerLintener();
        dateTv.setOnClickListener(onClickListener);
        //时间选择后回调
        pwTime.setOnTimeSelectListener(new TimePopupWindow.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                curDate = sdf.format(date);
                dateTv.setText(DateUtil.getYMDW(date));
                //切换即触发
                loadHistoryReport();
            }
        });
        pwTime.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
            }
        });
        clear.setOnClickListener(onClickListener);
        done.setOnClickListener(onClickListener);
        menuSa.setOnCheckedChangeListener(onCheckedChangeListener);
        viewRg.setOnCheckedChangeListener(onCheckedChangeListener);
        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String name = s.toString();
                searchItem(name);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    protected void initModule() {
        initData();
        classesManager = new ClassesManager(getActivity(),baseHandler);
        if(naviBars.size() == 0){
            naviBars.add("学术交流");
            naviBars.add("课程开发");
            naviBars.add("课堂教学");
            naviBars.add("教研活动");
            naviBars.add("学生活动");
            naviBarSetup(naviBars);
        }
        menuSa.check(R.id.eventRb);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, 1, 0, "规范管理");
        menu.add(0, 2, 0, "需改进");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //showToast("pos:"+item.getTitle());
        searchEt.setText("");
        if (item.getItemId() == 1){
            curItem.grade = 1;
            saMark();
        }else {
            curItem.grade = 0;
            showImproveDialog();
        }
        return true;
    }

    private void showImproveDialog(){

    }

    @Override
    public void iOnResume() {
        super.iOnResume();
        if(Utils.isNull(eventEt.getText().toString()) && menuSa.getCheckedRadioButtonId() == R.id.eventRb){
            queryEvent();
        }
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
    }

    void initData(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        curDate = sdf.format(new Date());
        dateTv.setText(DateUtil.getYMDW(new Date()));
        dateTv.setClickable(false);
        HEAD = curDate;
    }

    public void searchItem(String name){
        LogUtil.d(TAG,"searching name:"+name);
        FiltableAdapter adapter = (FiltableAdapter) totalGrid.getAdapter();
        if(adapter instanceof Filterable) {
            Filter filter = ((Filterable) adapter).getFilter();
            if (TextUtils.isEmpty(name)) {
                filter.filter(null);
            } else {
                filter.filter(name);
            }
        }
    }

    /**
     * 导航栏数据绑定
     */
    public class NaviAdapter extends ArrayAdapter<String> {
        List<String> list;
        /**
         * @param context
         * @param list
         */
        public NaviAdapter(Context context, List<String> list) {
            super(context, 0, list);
            this.list = list;
        }

        @Nullable
        @Override
        public String getItem(int position) {
            return this.list.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            String headItem = getItem(position);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.navbar_item_view, null);
            }
            TextView title_tv = (TextView) convertView.findViewById(R.id.title_tv);
            title_tv.setText(headItem);
            return convertView;
        }
    }

    void checkMenu(int menuId){
        curMenu = AppData.saMenuItmMap.get(menuId).dictLabel;
        editTitleTv.setText(curMenu);
        if (menuId == R.id.eventRb){
            eventEt.setText("");
            editSubTitleTv.setVisibility(View.VISIBLE);
            markView.setVisibility(View.GONE);
            titleTab.setVisibility(View.GONE);
            eventView.setVisibility(View.VISIBLE);
            mFloatBar.setVisibility(View.VISIBLE);
            typeCode = AppData.eventMenuMap.get(floatBarIndex);
            queryEvent();
        }else if( menuId == R.id.accidentRb || menuId == R.id.memoRb || menuId == R.id.leavingRb){
            eventEt.setText("");
            editSubTitleTv.setVisibility(View.GONE);
            markView.setVisibility(View.GONE);
            titleTab.setVisibility(View.GONE);
            mFloatBar.setVisibility(View.GONE);
            eventView.setVisibility(View.VISIBLE);
            if (menuId == R.id.accidentRb){
                typeCode = "意外伤害";
            }
            else if (menuId == R.id.memoRb){
                typeCode = "备注";
            }
            else if (menuId == R.id.leavingRb){
                typeCode = "离校巡查";
            }
            queryEvent();
        }else {
            mFloatBar.setVisibility(View.GONE);
            markView.setVisibility(View.VISIBLE);
            titleTab.setVisibility(View.VISIBLE);
            eventView.setVisibility(View.GONE);
            viewTitleTv.setText(AppData.saMenuItmMap.get(menuId).dictLabel);
            viewSubTitleTv.setText(AppData.saMenuItmMap.get(menuId).desc);
            listTitleTv.setText(AppData.saMenuItmMap.get(menuId).memo);
            itemNameTv.setText("");
            itemDescTv.setText("");
            queryMarkItems(menuId);
        }
    }

    void queryMarkItems(int menuId){
        if(menuId == R.id.clearRb){
            queryClassItems(menuId);
        }
        else if(menuId == R.id.workingRb){
            queryRoomItems(menuId);
        }
        else {
            queryTeacherItems(menuId);
        }
    }

    void queryEvent(){
        String loginName = AppData.getAppData().user.loginName;
        String token = AppData.getAppData().user.token;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        classesManager.queryEvent(loginName,token,date,typeCode);
    }

    void setupEventView(QueryEventResponse eventResponse){
        eventEt.setText("");
        eventEt.setText(eventResponse.content.memContent);
    }

    void postEventMsg(){
        String msg = eventEt.getText().toString();
        if (Utils.isNull(msg)){
            showToast("请输入要提交的内容");
            return;
        }
        String loginName = AppData.getAppData().user.loginName;
        String token = AppData.getAppData().user.token;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        String type = "";
        if (menuSa.getCheckedRadioButtonId() == R.id.eventRb){
            type = typeCode;
        }
        else if (menuSa.getCheckedRadioButtonId() == R.id.accidentRb){
            type = "意外伤害";
        }
        else if (menuSa.getCheckedRadioButtonId() == R.id.memoRb){
            type = "备注";
        }
        else if (menuSa.getCheckedRadioButtonId() == R.id.leavingRb){
            type = "离校巡查";
        }
        classesManager.postEvent(loginName,token,curMenu,date,type,msg);
    }

    void queryTeacherItems(int checkedId){
        String loginName = AppData.getAppData().user.loginName;
        String token = AppData.getAppData().user.token;
        classesManager.saTeacherList(loginName,token,AppData.saMenuItmMap.get(checkedId).dictLabel);
    }

    void queryRoomItems(int checkedId){
        String loginName = AppData.getAppData().user.loginName;
        String token = AppData.getAppData().user.token;
        classesManager.saRoomList(loginName,token,AppData.saMenuItmMap.get(checkedId).dictLabel);
    }

    void queryClassItems(int checkedId){
        String loginName = AppData.getAppData().user.loginName;
        String token = AppData.getAppData().user.token;
        classesManager.saClassList(loginName,token,AppData.saMenuItmMap.get(checkedId).dictLabel);
    }

    void saMark(){
        String loginName = AppData.getAppData().user.loginName;
        String token = AppData.getAppData().user.token;
        String type = "";
        String id = "";
        String commName = "";
        if (curItem instanceof MarkTeacherItem){
            type = "1";
            id = ((MarkTeacherItem) curItem).teacherId+"";
            commName = ((MarkTeacherItem) curItem).teacherName;
        }
        if (curItem instanceof UnmarkTeacherItem){
            type = "1";
            id = ((UnmarkTeacherItem) curItem).userId+"";
            commName = ((UnmarkTeacherItem) curItem).userName;
        }
        if (curItem instanceof UnmarkRoomItem){
            type = "2";
            id = ((UnmarkRoomItem) curItem).deptId + "";
            commName = ((UnmarkRoomItem) curItem).deptName;
        }
        if (curItem instanceof MarkRoomItem){
            type = "2";
            id = ((MarkRoomItem) curItem).teacherId + "";
            commName = ((MarkRoomItem) curItem).teacherName;
        }
        if (curItem instanceof UnmarkClassItem){
            type = "3";
            id = ((UnmarkClassItem) curItem).deptId + "";
            commName = ((UnmarkClassItem) curItem).deptName;
        }
        if (curItem instanceof MarkClassItem){
            type = "3";
            id = ((MarkRoomItem) curItem).teacherId + "";
            commName = ((MarkRoomItem) curItem).teacherName;
        }
        classesManager.saMark(loginName,token,curMenu,curItem.grade+"",type,id,commName);
    }

    void parseMarkTeacherItems(MarkTeacherResponse markTeacherResponse){
        TeacherItemList teacherItemList = markTeacherResponse.content;
        unMarkedItems.clear();
        goodItems.clear();
        badItems.clear();
        unMarkedItems.addAll(teacherItemList.users);
        if (teacherItemList.teacherScore.size() != 0){
            for(MarkItem item : teacherItemList.teacherScore){
                if (item.grade == 1){
                    goodItems.add(item);
                }else {
                    badItems.add(item);
                }
            }
        }

        setupView();
    }

    void parseMarkOfficeItems(MarkRoomResponse markRoomResponse){
        RoomItemList roomItemList = markRoomResponse.content;
        unMarkedItems.clear();
        goodItems.clear();
        badItems.clear();
        unMarkedItems.addAll(roomItemList.buildings);
        if (roomItemList.teacherScore.size() != 0){
            for(MarkItem item : roomItemList.teacherScore){
                if (item.grade == 1){
                    goodItems.add(item);
                }else {
                    badItems.add(item);
                }
            }
        }

        setupView();
    }

    void parseMarkClassItems(MarkClassResponse markClassResponse){
        ClassItemList classItemList = markClassResponse.content;
        unMarkedItems.clear();
        goodItems.clear();
        badItems.clear();
        unMarkedItems.addAll(classItemList.classes);
        if (classItemList.teacherScore.size() != 0){
            for(MarkItem item : classItemList.teacherScore){
                if (item.grade == 1){
                    goodItems.add(item);
                }else {
                    badItems.add(item);
                }
            }
        }

        setupView();
    }

    void setupView(){
        //unmarked
        totalAdapter = new FiltableAdapter(getActivity(),unMarkedItems);
        totalAdapter.onItemCheckedListener = onItemCheckedListener;
        totalGrid.setAdapter(totalAdapter);
        registerForContextMenu(totalGrid);
        totalGrid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                curItem = (MarkItem) adapterView.getAdapter().getItem(i);
                return false;
            }
        });

        //good
        goodAdapter = new TobeMarkedAdapter(getActivity(),goodItems);
        goodGrid.setAdapter(goodAdapter);
        registerForContextMenu(goodGrid);
        goodGrid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                curItem = (MarkItem) adapterView.getAdapter().getItem(i);
                return false;
            }
        });
        //bad
        badAdapter = new TobeMarkedAdapter(getActivity(),badItems);
        badGrid.setAdapter(badAdapter);
        registerForContextMenu(badGrid);
        badGrid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                curItem = (MarkItem) adapterView.getAdapter().getItem(i);
                return false;
            }
        });
    }

    void updateMarkView(){
        if (curItem == null) return;
        //unMarkedItems.remove(curItem);
        totalAdapter.removeItem(curItem);
        if (curItem.grade == 1){
            goodItems.add(curItem);
        }else {
            badItems.add(curItem);
        }
        totalAdapter.notifyDataSetChanged();
        goodAdapter.notifyDataSetChanged();
        badAdapter.notifyDataSetChanged();
    }

    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }

    void loadHistoryReport(){
        String loginName = AppData.getAppData().user.loginName;
        String token = AppData.getAppData().user.token;
        classesManager.saHistory(loginName,token,curDate);
    }

    void setupReportView(SaHistoryResponse saHistoryResponse){
        SaHistoryMenuList saHistoryMenuList = saHistoryResponse.content;
        List<EventLogItem> memorabilials = saHistoryMenuList.memorabilials;
        StringBuilder learnAndCommuniation = new StringBuilder();
        StringBuilder lessonDevelopment = new StringBuilder();
        StringBuilder teaching = new StringBuilder();
        StringBuilder teachingActivities = new StringBuilder();
        StringBuilder studentActivities = new StringBuilder();
        for (EventLogItem logItem : memorabilials){
            if("学术交流".equals(logItem.typeCode)){
                learnAndCommuniation.append(logItem.memContent);
                learnAndCommuniation.append(";");
            }
            if("课程开发".equals(logItem.typeCode)){
                lessonDevelopment.append(logItem.memContent);
                lessonDevelopment.append(";");
            }
            if("课堂教学".equals(logItem.typeCode)){
                teaching.append(logItem.memContent);
                teaching.append(";");
            }if("教研活动".equals(logItem.typeCode)){
                teachingActivities.append(logItem.memContent);
                teachingActivities.append(";");
            }
            if("学生活动".equals(logItem.typeCode)){
                studentActivities.append(logItem.memContent);
                studentActivities.append(";");
            }
        }
        List<EventLogItem> remark = saHistoryMenuList.remark;
        StringBuilder remarks = new StringBuilder();
        for (EventLogItem logItem : remark){
            remarks.append(logItem.memContent);
            remarks.append(";");
        }
        List<EventLogItem> accidentalInjury = saHistoryMenuList.accidentalInjury;
        StringBuilder accidents = new StringBuilder();
        for (EventLogItem logItem : accidentalInjury){
            accidents.append(logItem.memContent);
            accidents.append(";");
        }
        List<EventLogItem> leavingSchoolInspection = saHistoryMenuList.leavingSchoolInspection;
        StringBuilder leaving = new StringBuilder();
        for (EventLogItem logItem : leavingSchoolInspection){
            leaving.append(logItem.memContent);
            leaving.append(";");
        }

        List<MarkHistoryItem> specialtyTraining = saHistoryMenuList.specialtyTraining;
        StringBuilder trainingGood = new StringBuilder();
        StringBuilder trainingBad = new StringBuilder();
        for (MarkHistoryItem item : specialtyTraining){
            if (item.grade == 1){
                trainingGood.append(item.teacherName);
                trainingGood.append(";");
            }else {
                trainingBad.append(item.teacherName);
                trainingBad.append(";");
            }
        }

        List<MarkHistoryItem> teacherScoresls = saHistoryMenuList.teacherScoresls;
        StringBuilder dutyGood = new StringBuilder();
        StringBuilder dutyBad = new StringBuilder();
        for (MarkHistoryItem item : teacherScoresls){
            if (item.grade == 1){
                dutyGood.append(item.teacherName);
                dutyGood.append(";");
            }else {
                dutyBad.append(item.teacherName);
                dutyBad.append(";");
            }
        }

        List<MarkHistoryItem> schoolTeam = saHistoryMenuList.schoolTeam;
        StringBuilder teamGood = new StringBuilder();
        StringBuilder teamBad = new StringBuilder();
        for (MarkHistoryItem item : schoolTeam){
            if (item.grade == 1){
                teamGood.append(item.teacherName);
                teamGood.append(";");
            }else {
                teamBad.append(item.teacherName);
                teamBad.append(";");
            }
        }

        List<MarkHistoryItem> accessSecurity = saHistoryMenuList.accessSecurity;
        StringBuilder acBad = new StringBuilder();
        for (MarkHistoryItem item : accessSecurity){
            if (item.grade == 1){
            }else {
                acBad.append(item.teacherName);
                acBad.append(";");
            }
        }

        List<MarkHistoryItem> outdoorCleaning = saHistoryMenuList.outdoorCleaning;
        StringBuilder outGood = new StringBuilder();
        StringBuilder outBad = new StringBuilder();
        for (MarkHistoryItem item : outdoorCleaning){
            if (item.grade == 1){
                outGood.append(item.teacherName);
                outGood.append(";");
            }else {
                outBad.append(item.teacherName);
                outBad.append(";");
            }
        }

        List<MarkHistoryItem> schoolClearance = saHistoryMenuList.schoolClearance;
        StringBuilder clearBad = new StringBuilder();
        for (MarkHistoryItem item : schoolClearance){
            if (item.grade == 1){
            }else {
                clearBad.append(item.teacherName);
                clearBad.append(";");
            }
        }

        List<MarkHistoryItem> civilizedOffice = saHistoryMenuList.civilizedOffice;
        StringBuilder officeGood = new StringBuilder();
        StringBuilder officeBad = new StringBuilder();
        for (MarkHistoryItem item : civilizedOffice){
            if (item.grade == 1){
                officeGood.append(item.teacherName);
                officeGood.append(";");
            }else {
                officeBad.append(item.teacherName);
                officeBad.append(";");
            }
        }
        communication.setText(learnAndCommuniation.toString());
        development.setText(lessonDevelopment.toString());
        teachAndLearn.setText(teaching.toString());
        teachActivities.setText(teachingActivities.toString());
        stuActivities.setText(studentActivities.toString());
        goodTeacher.setText(dutyGood.toString());
        badTeacher.setText(dutyBad.toString());
        goodTeacherPro.setText(teamGood.toString());
        badTeacherPro.setText(teamBad.toString());
        goodTeacherTran.setText(trainingGood.toString());
        badTeacherTran.setText(trainingBad.toString());
        accident.setText(accidents.toString());
        safety.setText(acBad.toString());
        afterSchool.setText(clearBad.toString());
        SchoolAffairsFragment.this.leaving.setText(leaving.toString());
        goodTeacherOut.setText(outGood.toString());
        badTeacherOut.setText(outBad.toString());
        goodTeacherOff.setText(officeGood.toString());
        badTeacherOff.setText(officeBad.toString());
        memo.setText(remarks.toString());
    }

}
