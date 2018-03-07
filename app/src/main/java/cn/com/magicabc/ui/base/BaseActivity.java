package cn.com.magicabc.ui.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.com.magicabc.R;
import cn.com.magicabc.util.ActivityUtils;
import cn.com.magicabc.util.PermissionListener;
import cn.com.magicabc.util.ProgressDialogUtils;
import cn.com.magicabc.util.ToastUtils;
import cn.com.magicabc.widget.NetworkStateView;

/**
 * Created by bei on 2017/4/17.
 * Activity基类
 */

public abstract class BaseActivity extends AppCompatActivity
    implements NetworkStateView.OnRefreshListener {

  private Unbinder unbinder;

  private ProgressDialogUtils progressDialog;

  private NetworkStateView networkStateView;

  private static PermissionListener mPermissionListener;
  private static final int CODE_REQUEST_PERMISSION = 1;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getLayoutId());
    unbinder = ButterKnife.bind(this);
    ActivityUtils.addActivity(this);
    initDialog();
    afterCreate(savedInstanceState);
    setSystemBarTransparent();//  状态栏透明
  }

  @SuppressLint("InflateParams") @Override public void setContentView(@LayoutRes int layoutResID) {
    View view = getLayoutInflater().inflate(R.layout.activity_base, null);
    //设置填充activity_base布局
    super.setContentView(view);

    if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
      view.setFitsSystemWindows(true);
    }
    //加载子类Activity的布局
    initDefaultView(layoutResID);
  }

  /**
   * 初始化默认布局的View
   *
   * @param layoutResId 子View的布局id
   */
  private void initDefaultView(int layoutResId) {
    networkStateView = (NetworkStateView) findViewById(R.id.nsv_state_view);
    FrameLayout container = (FrameLayout) findViewById(R.id.fl_activity_child_container);
    View childView = LayoutInflater.from(this).inflate(layoutResId, null);
    container.addView(childView, 0);

    Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
    if (mToolbar == null) {
      throw new IllegalStateException("Layout is required to include a Toolbar with id 'toolbar'");
    }
    setSupportActionBar(mToolbar);
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
  }

  protected abstract int getLayoutId();

  protected abstract void afterCreate(Bundle savedInstanceState);

  private void initDialog() {
    progressDialog = new ProgressDialogUtils(this, R.style.dialog_transparent_style);
  }

  /**
   * 显示加载中的布局
   */
  public void showLoadingView() {
    networkStateView.showLoading();
  }

  /**
   * 显示加载完成后的布局(即子类Activity的布局)
   */
  public void showContentView() {
    networkStateView.showSuccess();
  }

  /**
   * 显示没有网络的布局
   */
  public void showNoNetworkView() {
    networkStateView.showNoNetwork();
    networkStateView.setOnRefreshListener(this);
  }

  /**
   * 显示没有数据的布局
   */
  public void showEmptyView() {
    networkStateView.showEmpty();
    networkStateView.setOnRefreshListener(this);
  }

  /**
   * 显示数据错误，网络错误等布局
   */
  public void showErrorView() {
    networkStateView.showError();
    networkStateView.setOnRefreshListener(this);
  }

  @Override public void onRefresh() {
    onNetworkViewRefresh();
  }

  /**
   * 重新请求网络
   */
  public void onNetworkViewRefresh() {
  }

  /**
   * 显示加载的ProgressDialog
   */
  public void showProgressDialog() {
    progressDialog.showProgressDialog();
  }

  /**
   * 显示有加载文字ProgressDialog，文字显示在ProgressDialog的下面
   *
   * @param text 需要显示的文字
   */
  public void showProgressDialogWithText(String text) {
    progressDialog.showProgressDialogWithText(text);
  }

  /**
   * 显示加载成功的ProgressDialog，文字显示在ProgressDialog的下面
   *
   * @param message 加载成功需要显示的文字
   * @param time 需要显示的时间长度(以毫秒为单位)
   */
  public void showProgressSuccess(String message, long time) {
    progressDialog.showProgressSuccess(message, time);
  }

  /**
   * 显示加载成功的ProgressDialog，文字显示在ProgressDialog的下面
   * ProgressDialog默认消失时间为1秒(1000毫秒)
   *
   * @param message 加载成功需要显示的文字
   */
  public void showProgressSuccess(String message) {
    progressDialog.showProgressSuccess(message);
  }

  /**
   * 显示加载失败的ProgressDialog，文字显示在ProgressDialog的下面
   *
   * @param message 加载失败需要显示的文字
   * @param time 需要显示的时间长度(以毫秒为单位)
   */
  public void showProgressFail(String message, long time) {
    progressDialog.showProgressFail(message, time);
  }

  /**
   * 显示加载失败的ProgressDialog，文字显示在ProgressDialog的下面
   * ProgressDialog默认消失时间为1秒(1000毫秒)
   *
   * @param message 加载成功需要显示的文字
   */
  public void showProgressFail(String message) {
    progressDialog.showProgressFail(message);
  }

  /**
   * 隐藏加载的ProgressDialog
   */
  public void dismissProgressDialog() {
    progressDialog.dismissProgressDialog();
  }

  /**
   * 申请权限
   *
   * @param permissions 需要申请的权限(数组)
   * @param listener 权限回调接口
   */
  public  void requestPermissions(String[] permissions, PermissionListener listener) {
    Activity activity = ActivityUtils.getTopActivity();
    if (null == activity) {
      return;
    }

    mPermissionListener = listener;
    List<String> permissionList = new ArrayList<>();
    ToastUtils.showLong("-"+permissions.length);
    for (String permission : permissions) {
      //权限没有授权
      if (ContextCompat.checkSelfPermission(activity, permission)
          != PackageManager.PERMISSION_GRANTED) {
        permissionList.add(permission);
      }
    }

    if (!permissionList.isEmpty()) {
      ActivityCompat.requestPermissions(activity,
          permissionList.toArray(new String[permissionList.size()]), CODE_REQUEST_PERMISSION);
    } else {
      mPermissionListener.onGranted();
    }
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    switch (requestCode) {
      case CODE_REQUEST_PERMISSION:
        if (grantResults.length > 0) {
          List<String> deniedPermissions = new ArrayList<>();
          for (int i = 0; i < grantResults.length; i++) {
            int result = grantResults[i];
            if (result != PackageManager.PERMISSION_GRANTED) {
              String permission = permissions[i];
              deniedPermissions.add(permission);
            }
          }

          if (deniedPermissions.isEmpty()) {
            mPermissionListener.onGranted();
          } else {
            mPermissionListener.onDenied(deniedPermissions);
            showFaiingDialog();
          }
        }
        break;

      default:
        break;
    }
  }
  public void showFaiingDialog()
  {

    new AlertDialog.Builder(this)
            .setTitle("消息")
            .setMessage("当前应用无此权限，该功能暂时无法使用。如若需要，请单击确定按钮进行权限授权！")
            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {

                return;
              }
            })
            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                startSettings();
              }
            }).show();

  }

  private void startSettings() {

    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
    intent.setData(Uri.parse("package:" + getPackageName()));
    startActivity(intent);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    unbinder.unbind();
    ActivityUtils.removeActivity(this);
   // ImmersionBar.with(this).destroy(); //必须调用该方法，防止内存泄漏
  }

  private void setSystemBarTransparent() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      // LOLLIPOP解决方案
      getWindow().getDecorView()
          .setSystemUiVisibility(
              View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
      getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
      getWindow().setStatusBarColor(Color.TRANSPARENT);
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      // KITKAT解决方案
      getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);


    }

//    ImmersionBar.with(this)
//            .transparentStatusBar()  //透明状态栏，不写默认透明色
//            .transparentNavigationBar()  //透明导航栏，不写默认黑色(设置此方法，fullScreen()方法自动为true)
//            .transparentBar()             //透明状态栏和导航栏，不写默认状态栏为透明色，导航栏为黑色（设置此方法，fullScreen()方法自动为true）
//            .statusBarColor(R.color.colorPrimary)     //状态栏颜色，不写默认透明色
//            .navigationBarColor(R.color.colorPrimary) //导航栏颜色，不写默认黑色
//            .barColor(R.color.colorPrimary)  //同时自定义状态栏和导航栏颜色，不写默认状态栏为透明色，导航栏为黑色
//            .statusBarAlpha(0.3f)  //状态栏透明度，不写默认0.0f
//            .navigationBarAlpha(0.4f)  //导航栏透明度，不写默认0.0F
//            .barAlpha(0.1f)  //状态栏和导航栏透明度，不写默认0.0f
//            .statusBarDarkFont(true)   //状态栏字体是深色，不写默认为亮色
//
//            .flymeOSStatusBarFontColor(R.color.black)  //修改flyme OS状态栏字体颜色
//            .fullScreen(true)      //有导航栏的情况下，activity全屏显示，也就是activity最下面被导航栏覆盖，不写默认非全屏
//            .hideBar(BarHide.FLAG_HIDE_BAR)  //隐藏状态栏或导航栏或两者，不写默认不隐藏
////            .addViewSupportTransformColor(toolbar)  //设置支持view变色，可以添加多个view，不指定颜色，默认和状态栏同色，还有两个重载方法
////            .titleBar(view)    //解决状态栏和布局重叠问题，任选其一
////            .titleBarMarginTop(view)     //解决状态栏和布局重叠问题，任选其一
////            .statusBarView(view)  //解决状态栏和布局重叠问题，任选其一
//
//            .fitsSystemWindows(true)    //解决状态栏和布局重叠问题，任选其一，默认为false，当为true时一定要指定statusBarColor()，不然状态栏为透明色，还有一些重载方法
//            .statusBarColor(R.color.green)
//            .supportActionBar(true) //支持ActionBar使用
////            .statusBarColorTransform(R.color.orange)  //状态栏变色后的颜色
////            .navigationBarColorTransform(R.color.orange) //导航栏变色后的颜色
////            .barColorTransform(R.color.orange)  //状态栏和导航栏变色后的颜色
////            .removeSupportView(toolbar)  //移除指定view支持
//            .removeSupportAllView() //移除全部view支持
//            .navigationBarEnable(true)   //是否可以修改导航栏颜色，默认为true
//            .navigationBarWithKitkatEnable(true)  //是否可以修改安卓4.4和emui3.1手机导航栏颜色，默认为true
//            .fixMarginAtBottom(true)   //已过时，当xml里使用android:fitsSystemWindows="true"属性时,解决4.4和emui3.1手机底部有时会出现多余空白的问题，默认为false，非必须
//            .addTag("tag")  //给以上设置的参数打标记
//            .getTag("tag")  //根据tag获得沉浸式参数
//            .reset()  //重置所以沉浸式参数
//            .keyboardEnable(true)  //解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
//            .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)  //单独指定软键盘模式
////            .setOnKeyboardListener(new OnKeyboardListener() {    //软键盘监听回调
////              @Override
////              public void onKeyboardChange(boolean isPopup, int keyboardHeight) {
////                LogUtils.e(isPopup);  //isPopup为true，软键盘弹出，为false，软键盘关闭
////              }
////            })
//            .init();  //必须调用方可沉浸式
  }
}
