package com.androidcat.yucaiedu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.androidcat.acnet.entity.MarkItem;
import com.androidcat.acnet.entity.MarkTeacherItem;
import com.androidcat.acnet.entity.User;
import com.androidcat.yucaiedu.R;
import com.androidcat.yucaiedu.entity.TeacherItem;
import com.androidcat.yucaiedu.ui.listener.OnItemCheckedListener;

import java.util.ArrayList;
import java.util.List;

public class FiltableAdapter extends BaseAdapter implements Filterable {
    private Context mContext;
    /**
     * Contains the list of objects that represent the data of this Adapter.
     * Adapter数据源
     */
    private List<MarkItem> mDatas;
    public OnItemCheckedListener onItemCheckedListener;
    private LayoutInflater mInflater;

    //过滤相关
    /**
     * This lock is also used by the filter
     * (see {@link #getFilter()} to make a synchronized copy of
     * the original array of data.
     * 过滤器上的锁可以同步复制原始数据。
     */
    private final Object mLock = new Object();

    // A copy of the original mObjects array, initialized from and then used instead as soon as
    // the mFilter ArrayFilter is used. mObjects will then only contain the filtered values.
    //对象数组的备份，当调用ArrayFilter的时候初始化和使用。此时，对象数组只包含已经过滤的数据。
    private ArrayList<MarkItem> mOriginalValues;
    private ArrayFilter mFilter;

    public FiltableAdapter(Context context, List<MarkItem> datas) {
        mContext = context;
        mDatas = datas;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDatas.size() > 0 ? mDatas.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            vh = new FiltableAdapter.ViewHolder();
            convertView = mInflater.inflate(R.layout.item_marker, null);
            vh.markerIv = convertView.findViewById(R.id.markerIv);
            vh.markerRb = convertView.findViewById(R.id.markerRb);
            convertView.setTag(vh);
        } else {
            vh = (FiltableAdapter.ViewHolder) convertView.getTag();
        }
        //set data
        final MarkTeacherItem markItem = (MarkTeacherItem) mOriginalValues.get(position);
        vh.markerIv.setBackgroundResource(markItem.sex==1?R.mipmap.teacher_female:R.mipmap.teacher_male);
        vh.markerRb.setText(markItem.userName);
        if (markItem.isChecked){
            vh.markerRb.setChecked(true);
        }else {
            vh.markerRb.setChecked(false);
        }

        //当RadioButton被选中时，将其状态记录进States中，并更新其他RadioButton的状态使它们不被选中
        vh.markerRb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(markItem.isChecked) return;
                for (MarkItem item : mOriginalValues){
                    item.isChecked = false;
                }
                markItem.isChecked = true;
                if(onItemCheckedListener != null)
                    onItemCheckedListener.onItemChecked(markItem);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    public class ViewHolder {
        ImageView markerIv;
        RadioButton markerRb;
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ArrayFilter();
        }
        return mFilter;
    }

    /**
     * 过滤数据的类
     */
    /**
     * <p>An array filter constrains the content of the array adapter with
     * a prefix. Each item that does not start with the supplied prefix
     * is removed from the list.</p>
     * <p/>
     * 一个带有首字母约束的数组过滤器，每一项不是以该首字母开头的都会被移除该list。
     */
    private class ArrayFilter extends Filter {
        //执行刷选
        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();//过滤的结果
            //原始数据备份为空时，上锁，同步复制原始数据
            if (mOriginalValues == null) {
                synchronized (mLock) {
                    mOriginalValues = new ArrayList<>(mDatas);
                }
            }
            //当首字母为空时
            if (prefix == null || prefix.length() == 0) {
                ArrayList<MarkItem> list;
                synchronized (mLock) {//同步复制一个原始备份数据
                    list = new ArrayList<>(mOriginalValues);
                }
                results.values = list;
                results.count = list.size();//此时返回的results就是原始的数据，不进行过滤
            } else {
                String prefixString = prefix.toString().toLowerCase();//转化为小写

                ArrayList<MarkItem> values;
                synchronized (mLock) {//同步复制一个原始备份数据
                    values = new ArrayList<>(mOriginalValues);
                }
                final int count = values.size();
                final ArrayList<MarkItem> newValues = new ArrayList<>();

                for (int i = 0; i < count; i++) {
                    final MarkTeacherItem value = (MarkTeacherItem) values.get(i);//从List<User>中拿到User对象
//                    final String valueText = value.toString().toLowerCase();
                    final String valueText = value.userName.toString().toLowerCase();//User对象的name属性作为过滤的参数
                    // First match against the whole, non-splitted value
                    if (valueText.startsWith(prefixString) || valueText.indexOf(prefixString.toString()) != -1) {//第一个字符是否匹配
                        newValues.add(value);//将这个item加入到数组对象中
                    } else {//处理首字符是空格
                        final String[] words = valueText.split(" ");
                        final int wordCount = words.length;

                        // Start at index 0, in case valueText starts with space(s)
                        for (int k = 0; k < wordCount; k++) {
                            if (words[k].startsWith(prefixString)) {//一旦找到匹配的就break，跳出for循环
                                newValues.add(value);
                                break;
                            }
                        }
                    }
                }
                results.values = newValues;//此时的results就是过滤后的List<User>数组
                results.count = newValues.size();
            }
            return results;
        }

        //刷选结果
        @Override
        protected void publishResults(CharSequence prefix, FilterResults results) {
            //noinspection unchecked
            mDatas = (List<MarkItem>) results.values;//此时，Adapter数据源就是过滤后的Results
            if (results.count > 0) {
                notifyDataSetChanged();//这个相当于从mDatas中删除了一些数据，只是数据的变化，故使用notifyDataSetChanged()
            } else {
                /**
                 * 数据容器变化 ----> notifyDataSetInValidated

                 容器中的数据变化  ---->  notifyDataSetChanged
                 */
                notifyDataSetInvalidated();//当results.count<=0时，此时数据源就是重新new出来的，说明原始的数据源已经失效了
            }
        }
    }
}
