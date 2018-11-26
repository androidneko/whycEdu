package com.androidcat.yucaiedu.fragment;

import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.androidcat.acnet.entity.Room;
import com.androidcat.utilities.date.DateUtil;
import com.androidcat.utilities.listener.OnSingleClickListener;
import com.androidcat.utilities.persistence.SharePreferencesUtil;
import com.androidcat.yucaiedu.R;
import com.androidcat.yucaiedu.adapter.RoomAdapter;
import com.bigkoo.pickerview.OptionsPopupWindow;
import com.bigkoo.pickerview.TimePopupWindow;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@ActivityFragmentInject(
        contentViewId = R.layout.fragment_regular_check,
        hasNavigationView = false)
public class RegularCheckFragment extends BaseFragment{

    private TextView dateTv;
    private TextView buildingTv;
    View buildingView;
    GridView clockGrid;
    OptionsPopupWindow pwOptions;
    TimePopupWindow pwTime;
    private ArrayList<String> optionsItems = new ArrayList<String>();

    private String building = "钟楼";
    private List<Room> rooms = new ArrayList<>();
    private RoomAdapter roomAdapter;
    private OnSingleClickListener onClickListener = new OnSingleClickListener() {
        @Override
        public void onSingleClick(View v) {
            switch (v.getId()) {
                case R.id.buildingView:
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

    @Override
    protected void toHandleMessage(Message msg) {

    }

    @Override
    protected void findViewAfterViewCreate() {
        dateTv = mRootView.findViewById(R.id.dateTv);
        buildingTv = mRootView.findViewById(R.id.buildingTv);
        buildingView = mRootView.findViewById(R.id.buildingView);
        clockGrid = mRootView.findViewById(R.id.clockBuildingGrid);
    }

    @Override
    protected void initDataAfterFindView() {
        dateTv.setText(DateUtil.getYMDW(new Date()));
        buildingTv.setText("钟楼");
        pickerLintener();
        setListener();
        initData();
    }

    @Override
    protected void initDataBeforeViewCreate() {

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
        optionsItems.add("教学中心");
        optionsItems.add("东楼");
        pwOptions.setPicker(optionsItems);
        //设置默认选中的三级项目
        pwOptions.setSelectOptions(0);
    }

    protected void setListener() {
        dateTv.setOnClickListener(onClickListener);
        buildingView.setOnClickListener(onClickListener);
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
                RegularCheckFragment.this.building = building;
                buildingTv.setText(building);
            }
        });

        pwOptions.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
            }
        });
    }

    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }

    private void initData(){
        if (rooms.size() == 0){
            for(int i = 1;i < 19;i++){
                Room room = new Room();
                room.name = i+"班";
                rooms.add(room);
            }
        }
        if (roomAdapter == null){
            roomAdapter = new RoomAdapter(getActivity(),rooms);
        }
        clockGrid.setAdapter(roomAdapter);
    }
}
