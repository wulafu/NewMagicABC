package cn.com.magicabc.ui.guide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.magicabc.R;
import cn.com.magicabc.ui.base.BaseActivity;
import cn.com.magicabc.ui.login.LoginActivity;
import cn.com.magicabc.ui.register.RegisterActivity;
import cn.com.magicabc.util.UIUtils;


/**
 * 欢迎界面
 */
public class GuideActivity extends BaseActivity {


    @BindView(R.id.vp_guide)
    ViewPager mVpGuide;
    @BindView(R.id.ll_guide_points)
    LinearLayout mLlGuidePoints;
    @BindView(R.id.iv_guide_redPoint)
    ImageView mIvGuideRedPoint;
    @BindView(R.id.tv_register)
    TextView mTvRegister;
    @BindView(R.id.tv_login)
    TextView mTvLogin;
    private List<ImageView> images;



    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        int[] imgIds = new int[] { R.drawable.a1, R.drawable.a2,};
        // 初始化图片
        images = new ArrayList<>();
        for (int i = 0; i < imgIds.length; i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);// 自动拉伸图片宽高，匹配父容器
            // imageView.setImageResource(imgIds[i]);
            imageView.setBackgroundResource(imgIds[i]);// 会自动缩放图片，匹配父容器
            images.add(imageView);

            ImageView point = new ImageView(getApplicationContext());
            point.setBackgroundResource(R.drawable.point_normal_1);
            // 提供一个10dp的 宽度像素
            int dp2px = UIUtils
                    .dp2px(16);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(dp2px, dp2px);
            if (i != 0) {
                params.leftMargin = dp2px;// 设置左边距
            }
            point.setLayoutParams(params);
            mLlGuidePoints.addView(point);
        }
        // 设置Adapter
        mVpGuide.setAdapter(new MyPagerAdapter());
        // 监听ViewPager滑动事件
        mVpGuide.setOnPageChangeListener(new MyOnPageChangeListener());

    }
    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        // ViewPager滑动时调用
        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {

            // 红点移动的距离：手指移动的距离/屏幕宽度 * 灰点间距
            int redPointX = (int) ((positionOffset + position) * UIUtils
                    .dp2px(16))*2;
            // 移动红点
            RelativeLayout.LayoutParams parms = (RelativeLayout.LayoutParams) mIvGuideRedPoint
                    .getLayoutParams();
            parms.leftMargin = redPointX;
            mIvGuideRedPoint.setLayoutParams(parms);
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_register, R.id.tv_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_register:
                Intent intent = new Intent();
                intent.setClass(this, RegisterActivity.class);
                intent.putExtras(getIntent());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            case R.id.tv_login:
                Intent intent1= new Intent();
                intent1.setClass(this, LoginActivity.class);
                intent1.putExtras(getIntent());
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent1);
                break;
        }
    }
    class  MyPagerAdapter extends PagerAdapter {



        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = images.get(position);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
