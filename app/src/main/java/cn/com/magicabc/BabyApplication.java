package cn.com.magicabc;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.StrictMode;
import android.support.multidex.MultiDex;


import cn.com.magicabc.util.LogUtils;
import cn.com.magicabc.util.SharePrefUtil;
import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.ShareSDK;


/**
 * Created by bei on 2017/4/17.
 * 自定义Application
 */

public class BabyApplication extends Application {

  private static BabyApplication mContext;

  private static Handler mMainThreadHandler;
  private ApplicationComponent applicationComponent;
  @Override
  protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
    MultiDex.install(this);
  }

  @Override public void onCreate() {
    super.onCreate();

    mContext = this;
    mMainThreadHandler = new Handler();

    //设置是否打印日志
    LogUtils.setIsLog(BuildConfig.LOG_DEBUG);
    //  debug严苛模式
    if (android.support.design.BuildConfig.DEBUG) {
      StrictMode.setThreadPolicy(
          new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
      StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
    }
    ShareSDK.initSDK(this);
    JPushInterface.setDebugMode(false); // 设置开启日志,发布时请关闭日志
    JPushInterface.init(this); // 初始化 JPush
    //创建AVSDK 控制器类


    String     registerRationId = JPushInterface.getRegistrationID(this);
    SharePrefUtil.saveString(mContext,"registerRationId",registerRationId);
    //  初始化dragger2 applicationComponent
    applicationComponent =DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
  }

  public static BabyApplication getApplication() {
    return mContext;
  }

  public static Handler getMainThreadHandler() {
    return mMainThreadHandler;
  }

  public ApplicationComponent getApplicationComponent() {
    return applicationComponent;
  }
}
