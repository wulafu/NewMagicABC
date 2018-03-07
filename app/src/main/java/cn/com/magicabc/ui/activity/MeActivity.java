package cn.com.magicabc.ui.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.allen.library.SuperTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.magicabc.R;
import cn.com.magicabc.ui.base.BaseActivity;
import cn.com.magicabc.util.GuiUtils;

/**
 * Created by hellohome on 18/3/2.
 */

public class MeActivity extends BaseActivity {
    @BindView(R.id.ll)
    LinearLayout rl;
    @BindView(R.id.fab_circle)
    ImageView mFabCircle;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_myproduct)
    SuperTextView mTvMyproduct;
    @BindView(R.id.tv_myhaibao)
    SuperTextView mTvMyhaibao;
    @BindView(R.id.tv_myinvite)
    SuperTextView mTvMyinvite;
    @BindView(R.id.tv_mypay)
    SuperTextView mTvMypay;
    @BindView(R.id.tv_tralisten)
    SuperTextView mTvTralisten;
    @BindView(R.id.tv_classreport)
    SuperTextView mTvClassreport;
    @BindView(R.id.tv_setting)
    SuperTextView mTvSetting;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_me;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setupEnterAnimation(); // 入场动画
            setupExitAnimation(); // 退场动画
        } else {
            setUpView();
        }


    }

    // 退出动画
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupExitAnimation() {
        Fade fade = new Fade();
        fade.setDuration(300);
        getWindow().setReturnTransition(fade);
    }


    // 入场动画
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupEnterAnimation() {
        Transition transition = TransitionInflater.from(this)
                .inflateTransition(R.transition.arc_motion);
        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
        getWindow().setSharedElementEnterTransition(transition);
    }

    // 动画展示
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void animateRevealShow() {
        GuiUtils.animateRevealShow(
                this, rl,
                mFabCircle.getWidth() / 2, R.color.white,
                new GuiUtils.OnRevealAnimationListener() {
                    @Override
                    public void onRevealHide() {

                    }

                    @Override
                    public void onRevealShow() {
                        // setUpView();
                    }
                });
    }

    private void setUpView() {
        Animation animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        animation.setDuration(300);

        //  mTvContainer.setVisibility(View.VISIBLE);
        //  mIvClose.setVisibility(View.VISIBLE);
        rl.startAnimation(animation);

    }

    // 退出按钮
    public void backActivity(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            onBackPressed();
        } else {
            defaultBackPressed();
        }
    }

    // 退出事件
    @Override
    public void onBackPressed() {
        GuiUtils.animateRevealHide(
                this, rl,
                mFabCircle.getWidth() / 2, R.color.white,
                new GuiUtils.OnRevealAnimationListener() {
                    @Override
                    public void onRevealHide() {
                        defaultBackPressed();
                    }

                    @Override
                    public void onRevealShow() {

                    }
                });
    }

    // 默认回退
    private void defaultBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.tv_setting, R.id.tv_myproduct})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_setting:

                startActivity(new Intent(MeActivity.this, SettingActivity.class));

                break;
            case R.id.iv_search:

                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}