package cn.com.magicabc.ui.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;

/**
 * Fragment适配器
 * Created by wcy on 2015/11/26.
 */
public class MyViewPagerAdapter extends PagerAdapter {


    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }

}
