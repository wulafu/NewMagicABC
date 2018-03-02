package cn.com.magicabc;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.magicabc.ui.activity.MessageActivity;
import cn.com.magicabc.ui.activity.SettingActivity;
import cn.com.magicabc.ui.activity.component.DaggerHomeComponent;
import cn.com.magicabc.ui.activity.module.HomeModule;
import cn.com.magicabc.ui.activity.persenter.HomePresenter;
import cn.com.magicabc.ui.adapter.FragmentAdapter;
import cn.com.magicabc.ui.base.BaseActivity;
import cn.com.magicabc.ui.fragment.LocalMusicFragment;
import cn.com.magicabc.ui.fragment.OnlineMusicFragment;
import cn.com.magicabc.util.PermissionListener;
import cn.com.magicabc.util.ToastUtils;


public class MainActivity extends BaseActivity implements PermissionListener {

  private static final String TAG = "main";
  @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;
  @BindView(R.id.navigation_view) NavigationView navigation_view;
  @BindView(R.id.iv_menu) ImageView ivMenu;
  @BindView(R.id.iv_search) ImageView ivSearch;
  @BindView(R.id.tv_local_music) TextView tvLocalMusic;
  @BindView(R.id.tv_online_music) TextView tvOnlineMusic;
  @BindView(R.id.viewpager) ViewPager mViewPager;
  private LocalMusicFragment localMusicFragment;
  private OnlineMusicFragment onlineMusicFragment;

  @Inject
  HomePresenter homePersenter;

  @Override protected int getLayoutId() {
    return R.layout.activity_main;
  }

  @Override protected void afterCreate(Bundle savedInstanceState) {
    FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
    localMusicFragment = new LocalMusicFragment();
    onlineMusicFragment = new OnlineMusicFragment();
  //  onlineMusicFragment.setPresenter(homePersenter);
    adapter.addFragment(onlineMusicFragment);
    mViewPager.setAdapter(adapter);
    mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

      }

      @Override public void onPageSelected(int position) {
        if (position == 0) {
          tvLocalMusic.setSelected(true);
          tvOnlineMusic.setSelected(false);
        } else {
          tvLocalMusic.setSelected(false);
          tvOnlineMusic.setSelected(true);
        }
      }

      @Override public void onPageScrollStateChanged(int state) {

      }
    });
    tvLocalMusic.setSelected(true);
requestPermissions(new String[]{Manifest.permission.CALL_PHONE},this);
    DaggerHomeComponent.builder()
        .applicationComponent(BabyApplication.getApplication().getApplicationComponent())
        .homeModule(new HomeModule(onlineMusicFragment))
        .build()
        .inject(this);

  }

  @OnClick({ R.id.iv_menu,R.id.iv_search}) public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.iv_menu:
        //drawerLayout.openDrawer(GravityCompat.START);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
          ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, ivMenu, ivMenu.getTransitionName());
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
ToastUtils.showLong("wwww");

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
