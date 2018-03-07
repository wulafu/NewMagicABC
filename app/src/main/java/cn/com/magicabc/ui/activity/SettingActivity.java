package cn.com.magicabc.ui.activity;

import android.os.Bundle;
import android.widget.LinearLayout;

import butterknife.BindView;
import cn.com.magicabc.R;
import cn.com.magicabc.ui.base.BaseActivity;

/**
 * Created by hellohome on 18/3/2.
 */

public class SettingActivity extends BaseActivity{
    @BindView(R.id.ll)

    LinearLayout rl;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
