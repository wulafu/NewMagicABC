package cn.com.magicabc.ui.forgetpasswd;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.magicabc.R;
import cn.com.magicabc.ui.base.BaseActivity;
import cn.com.magicabc.ui.register.RegisterInfoActivity;
import cn.com.magicabc.widget.ClearEditText;

import static cn.com.magicabc.R.id.iv_back;

/**
 * Created by hellohome on 18/3/6.
 */

public class ForgetPasswdActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.et_phoneNum)
    ClearEditText mEtPhoneNum;
    @BindView(R.id.et_password)
    ClearEditText mEtPassword;
    @BindView(R.id.et_code)
    ClearEditText mEtCode;
    @BindView(R.id.tv_register)
    TextView mTvRegister;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forgetpasswd;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mTvTitle.setText("忘记密码");
    }
    @OnClick({R.id.tv_register,R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_register:
                Intent intent = new Intent();
                intent.setClass(this, RegisterInfoActivity.class);
                intent.putExtras(getIntent());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            case R.id.iv_back:
                onBackPressed();
                break;

        }
    }

}
