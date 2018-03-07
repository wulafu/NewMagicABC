package cn.com.magicabc.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import cn.com.magicabc.BabyApplication;
import cn.com.magicabc.BuildConfig;
import cn.com.magicabc.ui.base.BaseActivity;

/**
 * 系统版本信息类
 *
 * @author tangjun
 */
public class DeviceUtils {

  private static final String TAG = "DeviceUtils";
  // private static final long NO_STORAGE_ERROR = -1L;
  private static final long CANNOT_STAT_ERROR = -2L;

  /**
   * >=2.2
   */
  public static boolean hasFroyo() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
  }

  /**
   * >=2.3
   */
  public static boolean hasGingerbread() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
  }

  /**
   * >=3.0 LEVEL:11
   */
  public static boolean hasHoneycomb() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
  }

  /**
   * >=3.1
   */
  public static boolean hasHoneycombMR1() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
  }

  /**
   * >=4.0 14
   */
  public static boolean hasICS() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
  }

  public static int getSDKVersionInt() {
    return Build.VERSION.SDK_INT;
  }

  @SuppressWarnings("deprecation") public static String getSDKVersion() {
    return Build.VERSION.SDK;
  }

  /**
   * 判断是否是平板电脑
   */
  public static boolean isTablet(Context context) {
    return (context.getResources().getConfiguration().screenLayout
        & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
  }

  public static boolean isHoneycombTablet(Context context) {
    return hasHoneycomb() && isTablet(context);
  }

  @SuppressWarnings("deprecation") public static int getScreenHeight(Context context) {
    Display display =
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
    return display.getHeight();
  }

  public static int getFullScreenHeight(Context context) {
    int dpi = 0;
    Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
    DisplayMetrics dm = new DisplayMetrics();
    @SuppressWarnings("rawtypes") Class c;
    try {
      c = Class.forName("android.view.Display");
      @SuppressWarnings("unchecked") Method method =
          c.getMethod("getRealMetrics", DisplayMetrics.class);
      method.invoke(display, dm);
      dpi = dm.heightPixels;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return dpi;
  }

  public static int getScreenMenuHeight(Context context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      return getFullScreenHeight(context) - getScreenHeight(context);
    }
    return 0;
  }

  /**
   * 获取屏幕宽度
   */
  @SuppressWarnings("deprecation") public static int getScreenWidth(Context context) {
    Display display =
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
    return display.getWidth();
  }

  /**
   * 获得设备屏幕密度
   */
  public static float getScreenDensity(Context context) {
    DisplayMetrics metrics = context.getApplicationContext().getResources().getDisplayMetrics();
    return metrics.density;
  }

  public static int[] getScreenSize(int w, int h, Context context) {
    int phoneW = getScreenWidth(context);
    int phoneH = getScreenHeight(context);

    if (w * phoneH > phoneW * h) {
      phoneH = phoneW * h / w;
    } else if (w * phoneH < phoneW * h) {
      phoneW = phoneH * w / h;
    }

    return new int[] { phoneW, phoneH };
  }

  public static int[] getScreenSize(int w, int h, int phoneW, int phoneH) {
    if (w * phoneH > phoneW * h) {
      phoneH = phoneW * h / w;
    } else if (w * phoneH < phoneW * h) {
      phoneW = phoneH * w / h;
    }
    return new int[] { phoneW, phoneH };
  }

  /**
   * 设置屏幕亮度
   */
  public static void setBrightness(final Activity context, float f) {
    WindowManager.LayoutParams lp = context.getWindow().getAttributes();
    lp.screenBrightness = f;
    if (lp.screenBrightness > 1.0f) {
      lp.screenBrightness = 1.0f;
    } else if (lp.screenBrightness < 0.01f) lp.screenBrightness = 0.01f;
    context.getWindow().setAttributes(lp);
  }

  /**
   * 检测磁盘状态
   */
  // public static int getStorageStatus(boolean mayHaveSd) {
  // long remaining = mayHaveSd ? getAvailableStorage() : NO_STORAGE_ERROR;
  // if (remaining == NO_STORAGE_ERROR) {
  // return CommonStatus.STORAGE_STATUS_NONE;
  // }
  // return remaining < CommonConstants.LOW_STORAGE_THRESHOLD ?
  // CommonStatus.STORAGE_STATUS_LOW : CommonStatus.STORAGE_STATUS_OK;
  // }
  public static long getAvailableStorage() {
    try {
      String storageDirectory = Environment.getExternalStorageDirectory().toString();
      StatFs stat = new StatFs(storageDirectory);
      return (long) stat.getAvailableBlocks() * (long) stat.getBlockSize();
    } catch (RuntimeException ex) {
      // if we can't stat the filesystem then we don't know how many
      // free bytes exist. It might be zero but just leave it
      // blank since we really don't know.
      return CANNOT_STAT_ERROR;
    }
  }

  /**
   * 隐藏软键盘
   */
  public static void hideSoftInput(Context ctx) {
    if (ctx != null) {
      InputMethodManager imm =
          ((InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE));
      View view = ((Activity) ctx).getCurrentFocus();
      if (view != null) {
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
      }
    }
  }

  /**
   * 显示软键盘
   */
  public static void showSoftInput(Context ctx, View view) {
    // InputMethodManager.SHOW_FORCED);
    InputMethodManager imm =
        (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
  }

  public static String getCpuInfo() {
    String cpuInfo = "";
    try {
      if (new File("/proc/cpuinfo").exists()) {
        FileReader fr = new FileReader("/proc/cpuinfo");
        BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
        cpuInfo = localBufferedReader.readLine();
        localBufferedReader.close();

        if (cpuInfo != null) {
          cpuInfo = cpuInfo.split(":")[1].trim().split(" ")[0];
        }
      }
    } catch (IOException e) {
    } catch (Exception e) {
    }
    return cpuInfo;
  }

  public static void startApkActivity(final Context ctx, String packageName) {
    PackageManager pm = ctx.getPackageManager();
    PackageInfo pi;
    try {
      pi = pm.getPackageInfo(packageName, 0);
      Intent intent = new Intent(Intent.ACTION_MAIN, null);
      intent.addCategory(Intent.CATEGORY_LAUNCHER);
      intent.setPackage(pi.packageName);

      List<ResolveInfo> apps = pm.queryIntentActivities(intent, 0);

      ResolveInfo ri = apps.iterator().next();
      if (ri != null) {
        String className = ri.activityInfo.name;
        intent.setComponent(new ComponentName(packageName, className));
        ctx.startActivity(intent);
      }
    } catch (NameNotFoundException e) {

    }
  }

  /**
   * 计算视频宽度
   */
  public static int getVideoWidth(Context ctx) {
    return Math.min(ctx.getResources().getDisplayMetrics().widthPixels,
        ctx.getResources().getDisplayMetrics().heightPixels);
  }

  /**
   * 计算屏幕高度
   */
  public static int getDeviceWidth(Context ctx) {
    return Math.min(ctx.getResources().getDisplayMetrics().widthPixels,
        ctx.getResources().getDisplayMetrics().heightPixels);
  }

  public static int getDeviceHeight(Context ctx) {
    return Math.max(ctx.getResources().getDisplayMetrics().widthPixels,
        ctx.getResources().getDisplayMetrics().heightPixels);
  }

  public static int getWindowWidth(Context ctx) {
    WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
    return wm.getDefaultDisplay().getWidth();
  }

  /**
   * 计算视频高度 16：9
   */
  public static int getVideoHeight(Context ctx) {
    return (getVideoWidth(ctx) * 9) / 16;
  }

  /**
   * 软键盘是否已经打开
   */
  protected boolean isHardKeyboardOpen(Context ctx) {
    return ctx.getResources().getConfiguration().hardKeyboardHidden
        == Configuration.HARDKEYBOARDHIDDEN_NO;
  }

  /**
   * 获取应用程序的IMEI号
   */
  public static String getIMEI(Context context) {
    TelephonyManager telecomManager =
        (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    String imei = telecomManager.getDeviceId();

    return imei;
  }

  /**
   * 获取设备的系统版本号
   */
  public static int getDeviceSDK() {
    int sdk = Build.VERSION.SDK_INT;
    return sdk;
  }

  /**
   * 获取设备的型号
   */
  public static String getDeviceName() {
    String model = Build.MODEL;
    return model;
  }

  /**
   * 获取 显示信息
   */
  public static DisplayMetrics getDisplayMetrics(Context context) {
    DisplayMetrics dm = context.getResources().getDisplayMetrics();
    return dm;
  }

  //屏幕尺寸工具
  public final static int PX = TypedValue.COMPLEX_UNIT_PX;
  public final static int DIP = TypedValue.COMPLEX_UNIT_DIP;
  public final static int SP = TypedValue.COMPLEX_UNIT_SP;

  /**
   * @param unit 单位 </br>0 px</br>1 dip</br>2 sp
   * @param value size 大小
   */
  public static float getDimensionPixelSize(int unit, float value, Context context) {
    DisplayMetrics metrics = new DisplayMetrics();
    WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    wm.getDefaultDisplay().getMetrics(metrics);
    switch (unit) {
      case PX:
        return value;
      case DIP:
      case SP:
        return TypedValue.applyDimension(unit, value, metrics);
      default:
        throw new IllegalArgumentException("unknow unix");
    }
  }

  /**
   * 根据手机的屏幕属性从 dip 的单位 转成为 px(像素)
   */
  public static int dip2px(Context context, float value) {
    DisplayMetrics metrics = context.getResources().getDisplayMetrics();
    return (int) (value * metrics.density);
  }

  /**
   * 根据手机的屏幕属性从 px(像素) 的单位 转成为 dip
   */
  public static float px2dip(Context context, float value) {
    DisplayMetrics metrics = context.getResources().getDisplayMetrics();
    return value / metrics.density;
  }

  /**
   * 根据手机的屏幕属性从 sp的单位 转成为px(像素)
   */
  public static float sp2px(Context context, float value) {
    DisplayMetrics metrics = context.getResources().getDisplayMetrics();
    return value * metrics.scaledDensity;
  }

  /**
   * 根据手机的屏幕属性从 px(像素) 的单位 转成为 sp
   */
  public static float px2sp(Context context, float value) {
    DisplayMetrics metrics = context.getResources().getDisplayMetrics();
    return value / metrics.scaledDensity;
  }

  /**
   * 打印 显示信息
   */
  public static DisplayMetrics printDisplayInfo(Context context) {
    DisplayMetrics dm = getDisplayMetrics(context);
    if (BuildConfig.DEBUG) {
      StringBuilder sb = new StringBuilder();
      sb.append("_______  显示信息:  ");
      sb.append("\ndensity         :").append(dm.density);
      sb.append("\ndensityDpi      :").append(dm.densityDpi);
      sb.append("\nheightPixels    :").append(dm.heightPixels);
      sb.append("\nwidthPixels     :").append(dm.widthPixels);
      sb.append("\nscaledDensity   :").append(dm.scaledDensity);
      sb.append("\nxdpi            :").append(dm.xdpi);
      sb.append("\nydpi            :").append(dm.ydpi);
    //  LogUtils.LOGI(sb.toString());
    }
    return dm;
  }

  /**
   * 获取系统状态栏的高度
   */
  public static int getStatusBar(Context context) {
    int statusHeight = 0;
    Rect outRect = new Rect();
    try {
      if (context instanceof Activity) {
        ((Activity) context).getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
      }
    } catch (ClassCastException e) {
      e.toString();
    }
    statusHeight = outRect.top;
    if (0 == statusHeight) {
      Class<?> localClass;
      try {
        localClass = Class.forName("com.android.internal.R$dimen");
        Object localObject = localClass.newInstance();
        int i5 =
            Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
        statusHeight = context.getResources().getDimensionPixelSize(i5);
      } catch (Exception e) {
        e.printStackTrace();
      }
      //            Class<?> localClass;
      //            try {
      //                localClass = Class.forName("com.android.internal.R$dimen");
      //                Object localObject = localClass.newInstance();
      //                int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
      //                statusHeight = context.getResources().getDimensionPixelSize(i5);
      //            } catch (Exception e) {
      //                e.printStackTrace();
      //            }
      //        }
    }
    return statusHeight;
  }

  /**
   * 获取版本号(内部识别号)versionCode
   */
  public static int getVersionCode(Context context) {
    try {
      PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
      return pi.versionCode;
    } catch (NameNotFoundException e) {
      e.printStackTrace();
      return 0;
    }
  }

  /**
   * versionName
   */
  public static String getVersionName(Context context)//获取版本号
  {
    try {
      PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
      return pi.versionName;
    } catch (NameNotFoundException e) {
      return "0.0";
    }
  }

  /**
   * 获取设备厂商，如Xiaomi
   */
  public static String getManufacturer() {
    String MANUFACTURER = Build.MANUFACTURER;
    return MANUFACTURER;
  }

  /**
   * 判断应用是否已经启动
   *
   * @param context 一个context
   * @param packageName 要判断应用的包名
   * @return boolean
   */
  public static boolean isAppAlive(Context context, String packageName) {
    ActivityManager activityManager =
        (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    List<ActivityManager.RunningAppProcessInfo> processInfos =
        activityManager.getRunningAppProcesses();
    for (int i = 0; i < processInfos.size(); i++) {
      if (processInfos.get(i).processName.equals(packageName)) {
        Log.i("NotificationLaunch",
            String.format("the %s is running, isAppAlive return true", packageName));
        return true;
      }
    }
    Log.i("NotificationLaunch",
        String.format("the %s is not running, isAppAlive return false", packageName));
    return false;
  }

  /**
   * 返回app运行状态
   * 1:程序在前台运行
   * 2:程序在后台运行
   * 3:程序未启动
   * 注意：需要配置权限<uses-permission android:name="android.permission.GET_TASKS" />
   */
  public int getAppSatus(Context context, String pageName) {

    ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(20);

    //判断程序是否在栈顶
    if (list.get(0).topActivity.getPackageName().equals(pageName)) {
      return 1;
    } else {
      //判断程序是否在栈里
      for (ActivityManager.RunningTaskInfo info : list) {
        if (info.topActivity.getPackageName().equals(pageName)) {
          return 2;
        }
      }
      return 3;//栈里找不到，返回3
    }
  }

  //在7.0中宽和高如果设置得过大，弹出的PopupWindow会覆盖当前的视窗而覆盖整个手机屏幕，并不是在anchorView的下方弹出来。

  /**
   * 获取状态栏高度
   */
  public static int getStatusBarHeight() {
    //try
    //{
    //    Resources resource = BaiDaiApp.mContext.getResources();
    //    int resourceId = resource.getIdentifier("status_bar_height", "dimen", "Android");
    //    if (resourceId != 0)
    //    {
    //        return resource.getDimensionPixelSize(resourceId);
    //    }
    //} catch (Exception e)
    //{
    //}
    //return 0;
    /**
     * 获取状态栏高度——方法1
     * */
    int statusBarHeight1 = -1;
    //获取status_bar_height资源的ID
    int resourceId =
        BabyApplication.getApplication().getResources().getIdentifier("status_bar_height", "dimen", "android");
    if (resourceId > 0) {
      //根据资源ID获取响应的尺寸值
      return BabyApplication.getApplication().getResources().getDimensionPixelSize(resourceId);
    } else {
      return 0;
    }
  }

  /**
   * 获取actionbar的像素高度，默认使用android官方兼容包做actionbar兼容
   *
   * @return
   */
  public static int getActionBarHeight(Context context) {

    int actionBarHeight = ((BaseActivity)context).getSupportActionBar().getHeight();
    if (actionBarHeight != 0) {
      return actionBarHeight;
    }

    final TypedValue tv = new TypedValue();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
      if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
        actionBarHeight = TypedValue.complexToDimensionPixelSize(
                tv.data, context.getResources().getDisplayMetrics());
      }
    }
    else {
      // 使用android.support.v7.appcompat包做actionbar兼容的情况
      if (context.getTheme()
              .resolveAttribute(
                      android.support.v7.appcompat.R.attr.actionBarSize,
                      tv, true)) {
        actionBarHeight = TypedValue.complexToDimensionPixelSize(
                tv.data, context.getResources().getDisplayMetrics());
      }
    }

    return actionBarHeight;
  }

  public static boolean isNavigationBarShow(Activity context){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
      Display display = context.getWindowManager().getDefaultDisplay();
      Point size = new Point();
      Point realSize = new Point();
      display.getSize(size);
      display.getRealSize(realSize);
      return realSize.y!=size.y;
    }else {
      boolean menu = ViewConfiguration.get(context).hasPermanentMenuKey();
      boolean back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
      if(menu || back) {
        return false;
      }else {
        return true;
      }
    }
  }

  public static int getNavigationBarHeight(Activity activity) {
    if (!isNavigationBarShow(activity)){
      return 0;
    }
    Resources resources = activity.getResources();
    int resourceId = resources.getIdentifier("navigation_bar_height",
        "dimen", "android");
    //获取NavigationBar的高度
    int height = resources.getDimensionPixelSize(resourceId);
    return height;
  }

  /**
   * 当底部有导航栏时 获取屏幕的真实高度（包含底部导航栏)
   * @param activity
   * @return
   */
  public static int getRealSceenHeight(Activity activity) {
    return activity.getWindowManager().getDefaultDisplay().getHeight()+getNavigationBarHeight(activity);
  }

}
