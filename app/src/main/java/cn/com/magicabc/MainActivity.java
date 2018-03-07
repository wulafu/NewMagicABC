package cn.com.magicabc;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.magicabc.ui.activity.MessageActivity;
import cn.com.magicabc.ui.activity.SettingActivity;
import cn.com.magicabc.ui.activity.component.DaggerHomeComponent;
import cn.com.magicabc.ui.activity.module.HomeModule;
import cn.com.magicabc.ui.activity.persenter.HomePresenter;
import cn.com.magicabc.ui.base.BaseActivity;
import cn.com.magicabc.ui.fragment.LocalMusicFragment;
import cn.com.magicabc.ui.fragment.OnlineMusicFragment;
import cn.com.magicabc.ui.main.MainFragment;
import cn.com.magicabc.util.PermissionListener;


public class MainActivity extends BaseActivity implements PermissionListener {

  private static final String TAG = "main";

  @BindView(R.id.rl_me)
  RelativeLayout mRlme;  @BindView(R.id.iv_menu)
  ImageView mIvMenu;
  @BindView(R.id.iv_search) ImageView ivSearch;
  @BindView(R.id.viewpager) ViewPager mViewPager;
  private LocalMusicFragment localMusicFragment;
  private OnlineMusicFragment onlineMusicFragment;
  private MainFragment mMainFragment;

  @Inject
  HomePresenter homePersenter;

  @Override protected int getLayoutId() {
    return R.layout.activity_main_include;
  }

  @Override protected void afterCreate(Bundle savedInstanceState) {
    MyPagerAdapter adapter = new MyPagerAdapter();
    localMusicFragment = new LocalMusicFragment();
    onlineMusicFragment = new OnlineMusicFragment();
    onlineMusicFragment = new OnlineMusicFragment();
    mMainFragment = new MainFragment();
  //  onlineMusicFragment.setPresenter(homePersenter);
//    adapter.addFragment(mMainFragment);
    mViewPager.setAdapter(adapter);
    mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

      }

      @Override public void onPageSelected(int position) {

      }

      @Override public void onPageScrollStateChanged(int state) {

      }
    });

    //requestPermissions(new String[]{Manifest.permission.CALL_PHONE},this);
    DaggerHomeComponent.builder()
        .applicationComponent(BabyApplication.getApplication().getApplicationComponent())
        .homeModule(new HomeModule(onlineMusicFragment))
        .build()
        .inject(this);

  }

  @OnClick({ R.id.rl_me,R.id.iv_search}) public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.rl_me:
        //drawerLayout.openDrawer(GravityCompat.START);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
          ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, mIvMenu, mIvMenu.getTransitionName());
          startActivity(new Intent(MainActivity.this, SettingActivity.class),activityOptionsCompat.toBundle());
        } else {
          startActivity(new Intent(MainActivity.this, SettingActivity.class));
        }
        break;
      case R.id.iv_search:
        //drawerLayout.openDrawer(GravityCompat.START);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

          ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, ivSearch, ivSearch.getTransitionName());
          startActivity(new Intent(MainActivity.this, MessageActivity.class),activityOptionsCompat.toBundle());
        } else {
          startActivity(new Intent(MainActivity.this, MessageActivity.class));
        }
        break;
    }
  }

  @Override
  public void onGranted() {
   //ToastUtils.showLong("kepyi");


  }

  @Override
  public void onDenied(List<String> deniedPermissions) {

  }

  class  MyPagerAdapter extends PagerAdapter {



    @Override
    public int getCount() {
      return 2;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
      return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
   //   ImageView imageView = images.get(position);
      View inflate = View.inflate(MainActivity.this, R.layout.fragment_item, null);
       container.addView(inflate);
      return inflate;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
      container.removeView((View) object);
    }
  }
}

//HttpFactory.getHttpApiSingleton()
//    .getCategoryData("Android", 10, 1)
//    .compose(new HttpTransformer<HttpResult<List<GankEntity>>, List<GankEntity>>())
//    .doOnSubscribe(new Action0() {
//      @Override public void call() {
//        showLoadingView();
//      }
//    })
//    .subscribe(new Subscriber<List<GankEntity>>() {
//      @Override public void onCompleted() {
//        LogUtils.d(TAG, "Completed");
//        showContentView();
//      }
//
//      @Override public void onError(Throwable e) {
//        LogUtils.d(TAG, "OnError, Error is " + e.toString());
//        showErrorView();
//      }
//
//      @Override public void onNext(List<GankEntity> gankEntities) {
//
//      }
//    });
