package cn.com.magicabc.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "JPush";


	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();

//		if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
//			String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
//			Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
//
//		} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
//			Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
//
//		} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
//			JSONObject json = null;
//			try {
//				json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
//				String type = json.getString("type");
//				//作业
//				if ("3".equals(type)) {
////					EventBus.getDefault().post(new MessageEvent("111"));
////					SharePrefUtil.saveBoolean(context, "isSee", true);
////					SharePrefUtil.saveInt(context,"bageNum",SharePrefUtil.getInt(context,"bageNum",0)+1);
//				}
//				if("4".equals(type)){
////					EventBus.getDefault().post(new MessageEvent("010"));
////					SharePrefUtil.saveBoolean(context, "isSeeN", true);
////					SharePrefUtil.saveInt(context,"bageNum",SharePrefUtil.getInt(context,"bageNum",0)+1);
//				}
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//
//			Log.d(TAG, ": ---type--" + bundle.getString(JPushInterface.EXTRA_EXTRA));
//
//
//
//
//			int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
//
//
//
//
//            int badgeCount = SharePrefUtil.getInt(context, "bageNum", 0);
//
//		//	Toast.makeText(context, "Error input", Toast.LENGTH_SHORT).show();
//			boolean success = ShortcutBadger.applyCount(context, badgeCount);
//			//Log.d("wulafu", "Set count=" + badgeCount + ", success=" + success);
//
//		//	Toast.makeText(context, "Set count=" + badgeCount + ", success=" + success, Toast.LENGTH_SHORT).show();
//		} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
//			Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
//			try {
//                JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
//                Log.d(TAG, ": ---type--" + bundle.getString(JPushInterface.EXTRA_EXTRA));
//                String type = json.getString("type");
//                Log.d(TAG, ": ---type--" + type);
//                Log.d(TAG, "onReceive: -------" + printBundle(bundle));
//             //  ToastUtil.showNormalShortToast(": ---type--" + json.toString());
//                if ("1".equals(type)) {
//                    Intent i = new Intent(context, MyInviteActivity.class);
//                    //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    context.startActivity(i);
//                } else if ("2".equals(type)) {
//                    Intent i1 = new Intent(context, PayedActivity1.class);
//                    i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    context.startActivity(i1);
//                } else if ("4".equals(type)){
//                    String noteRecordId = json.getString("id");
//                    String grade = json.getString("grade");
//                    String lesson = json.getString("lesson");
//				//	ToastUtil.showNormalShortToast(": ---type--" + type+"----id:"+noteRecordId+"----"+grade+"---"+lesson);
//                    Intent i2 = new Intent(context, CourseDayActivity.class);
//                    i2.putExtra("noticeId", noteRecordId);
//                    i2.putExtra("lesson", lesson);
//                    i2.putExtra("grade", grade);
//                    //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    i2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    context.startActivity(i2);
//                    Log.d(TAG, ": ----id-" + noteRecordId);
//                }else{
//					EventBus.getDefault().post(new MessageEvent("2"));
//
//				}
//
//			} catch (JSONException e) {
//				Log.e(TAG, "Get message extra JSON error!");
//			}
//
//			//打开自定义的Activity
//
//
//		} else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
//			Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
//			//在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
//
//		} else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
//			boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
//			Log.w(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
//		} else {
//			Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
//		}
		}

	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			}else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			} else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
				if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
					Log.i(TAG, "This message has no Extra data");
					continue;
				}

				try {
					JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
					Iterator<String> it =  json.keys();

					while (it.hasNext()) {
						String myKey = it.next().toString();
						sb.append("\nkey:" + key + ", value: [" +
								myKey + " - " +json.optString(myKey) + "]");
					}
				} catch (JSONException e) {
					Log.e(TAG, "Get message extra JSON error!");
				}

			} else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		return sb.toString();
	}
	

}
