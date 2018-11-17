package com.androidcat.yucaiedu.adapter;

import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.ITabView;

/**
 * Created by androidcat on 2018/11/16.
 */

public class HomeTabAdapter implements TabAdapter {
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public ITabView.TabBadge getBadge(int position) {
        return null;
    }

    @Override
    public ITabView.TabIcon getIcon(int position) {
        return null;
    }

    @Override
    public ITabView.TabTitle getTitle(int position) {
        return null;
    }

    @Override
    public int getBackground(int position) {
        return 0;
    }
}
