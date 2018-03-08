package cn.com.magicabc.ui;

import android.os.Bundle;

import cn.com.magicabc.R;
import cn.com.magicabc.ui.base.BaseActivity;

/**
 * Created by hellohome on 18/3/2.
 */

public class WorkListActivity extends BaseActivity{

    @Override
    protected int getLayoutId() {
        return R.layout.activity_worklist;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
