package com.androidcat.yucaiedu.fragment;

import android.content.Context;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.androidcat.acnet.consts.OptMsgConst;
import com.androidcat.acnet.entity.MarkItem;
import com.androidcat.acnet.entity.Room;
import com.androidcat.acnet.entity.response.BuildingsResponse;
import com.androidcat.acnet.entity.response.MenuResponse;
import com.androidcat.acnet.manager.ClassesManager;
import com.androidcat.utilities.Utils;
import com.androidcat.utilities.listener.OnSingleClickListener;
import com.androidcat.yucaiedu.AppData;
import com.androidcat.yucaiedu.R;
import com.androidcat.yucaiedu.adapter.ClockBuildingRoomAdapter;
import com.androidcat.yucaiedu.adapter.EastBuildingRoomAdapter;
import com.androidcat.yucaiedu.adapter.TobeMarkedAdapter;
import com.androidcat.yucaiedu.adapter.TsBuildingRoomAdapter;
import com.androidcat.yucaiedu.ui.listener.OnItemCheckedListener;
import com.anroidcat.acwidgets.FloatBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class SchoolAffairsFragment extends BaseFragment {

    private View eventView;
    private View markView;
    private View titleTab;
    View clear;
    View done;
    EditText searchEt;
    private RadioGroup menuSa;
    private EditText eventEt;
    private TextView viewTitleTv;
    private TextView viewSubTitleTv;
    private TextView editTitleTv;
    private TextView editSubTitleTv;
    private TextView itemNameTv;
    private TextView itemDescTv;

    GridView totalGrid;
    GridView goodGrid;
    GridView badGrid;

    private List<MarkItem> totalItems = new ArrayList<>();
    private TobeMarkedAdapter totalAdapter;
    private List<MarkItem> goodItems = new ArrayList<>();
    private TobeMarkedAdapter goodAdapter;
    private List<MarkItem> badItems = new ArrayList<>();
    private TobeMarkedAdapter badAdapter;

    private LayoutInflater mInflater;
    private FloatBar mFloatBar;
    private List<String> naviBars = new ArrayList<String>();

    private String curMenu;
    private String typeCode;
    private ClassesManager classesManager;

    private RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
            if (radioGroup == menuSa){
                checkMenu(checkedId);
            }

        }
    };

    OnItemCheckedListener onItemCheckedListener = new OnItemCheckedListener() {
        @Override
        public void onItemChecked(MarkItem item) {
            //itemNameTv.setText(item.name);
            //itemDescTv.setText(item.desc);
        }
    };

    private OnSingleClickListener onClickListener = new OnSingleClickListener() {
        @Override
        public void onSingleClick(View v) {
            switch (v.getId()) {
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
                if (msg.arg1 == 0){

                }
                if (msg.arg1 == 1){

                }
                if (msg.arg1 == 2){

                }
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
                eventEt.setText("");
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
        mFloatBar = (FloatBar) mRootView.findViewById(R.id.navi_bar);
        markView = mRootView.findViewById(R.id.markView);
        eventView = mRootView.findViewById(R.id.eventView);
        titleTab = mRootView.findViewById(R.id.titleTab);
        menuSa = mRootView.findViewById(R.id.menuSa);
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
    }

    @Override
    protected void setListener() {
        clear.setOnClickListener(onClickListener);
        done.setOnClickListener(onClickListener);

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
        menuSa.setOnCheckedChangeListener(onCheckedChangeListener);
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
        showToast("pos:"+item.getTitle());
        return super.onContextItemSelected(item);
    }

    void initData(){
        //total
        /*if (totalItems.size() == 0){
            for(int i = 1;i < 19;i++){
                MarkItem room = new MarkItem();
                room.name = i+"老师";
                room.gender = new Random().nextInt(10)%2==0?0:1;
                room.desc = "负责"+i+"号楼"+i+"房间区域";
                totalItems.add(room);
            }
        }
        if (totalAdapter == null){
            totalAdapter = new TobeMarkedAdapter(getActivity(),totalItems);
            totalGrid.setAdapter(totalAdapter);
        }
        totalAdapter.onItemCheckedListener = onItemCheckedListener;
        registerForContextMenu(totalGrid);
        totalGrid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                return false;
            }
        });

        //good
        if (goodItems.size() == 0){
            for(int i = 1;i < 13;i++){
                MarkItem room = new MarkItem();
                room.name = i+"老师";
                room.gender = new Random().nextInt(10)%2==0?0:1;
                room.desc = "负责"+i+"号楼"+i+"房间区域";
                goodItems.add(room);
            }
        }
        if (goodAdapter == null){
            goodAdapter = new TobeMarkedAdapter(getActivity(),goodItems);
        }
        goodGrid.setAdapter(goodAdapter);
        //bad
        if (badItems.size() == 0){
            for(int i = 1;i < 3;i++){
                MarkItem room = new MarkItem();
                room.name = i+"老师";
                room.gender = new Random().nextInt(10)%2==0?0:1;
                room.desc = "负责"+i+"号楼"+i+"房间区域";
                badItems.add(room);
            }
        }
        if (badAdapter == null){
            badAdapter = new TobeMarkedAdapter(getActivity(),badItems);
        }
        badGrid.setAdapter(badAdapter);*/
    }

    public void searchItem(String name){
//        totalItems.clear();
//        List<MarkItem> shopClient = DataSupport.where("client_name like ?", "%" + name + "%").find(MarkItem.class);
//        for (MarkItem shopClient1 : shopClient) {
//            Map<String, Object> map = new HashMap<String, Object>();
//            map.put("name", shopClient1.getClient_name());
//            map.put("number", shopClient1.getClient_phone());
//            listItems.add(map);
//        }
//        personalList.clear();
//        personalList.addAll(listItems);
//        adapter.notifyDataSetChanged();
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
        }else if( menuId == R.id.accidentRb || menuId == R.id.memoRb){
            eventEt.setText("");
            editSubTitleTv.setVisibility(View.GONE);
            markView.setVisibility(View.GONE);
            titleTab.setVisibility(View.GONE);
            mFloatBar.setVisibility(View.GONE);
            eventView.setVisibility(View.VISIBLE);
        }else {
            mFloatBar.setVisibility(View.GONE);
            markView.setVisibility(View.VISIBLE);
            titleTab.setVisibility(View.VISIBLE);
            eventView.setVisibility(View.GONE);

            if(menuId == R.id.afterSchoolRb){
                queryClassItems(menuId);
            }
            else if(menuId == R.id.workingRb){
                queryRoomItems(menuId);
            }
            else {
                queryTeacherItems(menuId);
            }
        }
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
}
