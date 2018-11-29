package com.androidcat.yucaiedu.fragment;

import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.androidcat.utilities.Utils;
import com.androidcat.yucaiedu.R;
import com.anroidcat.acwidgets.FloatBar;

import java.util.ArrayList;
import java.util.List;

public class SchoolAffairsFragment extends BaseFragment {

    private View eventView;
    private View markView;
    private View titleTab;
    private RadioGroup menuSa;

    private LayoutInflater mInflater;
    private FloatBar mFloatBar;
    private List<String> naviBars = new ArrayList<String>();

    private RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
            if (checkedId == R.id.eventRb){
                markView.setVisibility(View.GONE);
                titleTab.setVisibility(View.GONE);
                eventView.setVisibility(View.VISIBLE);
            }
            if (checkedId == R.id.dutyRb){
                markView.setVisibility(View.VISIBLE);
                titleTab.setVisibility(View.VISIBLE);
                eventView.setVisibility(View.GONE);
            }
        }
    };

    protected void naviBarSetup(List<String> items) {
        mFloatBar.fitScreenWidth(Utils.dp2px(getActivity(),222));
        NaviAdapter adapter =  new NaviAdapter(this.getActivity(), items);
        mFloatBar.setAdapter(adapter);
        mFloatBar.setOnFloatItemClickListener(new FloatBar.OnFloatItemClickListener() {

            @Override
            public void OnItemClick(View view, int index, Object mT) {
                //NewsSortHeadItem item = headItems.get(index);
                //makeItemManager(item.getCMSID());
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
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initModule() {
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

    /**
     * 导航栏数据绑定
     */
    public class NaviAdapter extends ArrayAdapter<String> {
        /**
         * @param context
         * @param list
         */
        public NaviAdapter(Context context, List<String> list) {
            super(context, 0, list);
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
}
