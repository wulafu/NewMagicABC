package cn.com.magicabc.ui.superviseclass;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYVideoProgressListener;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;

import java.util.HashMap;

import butterknife.BindView;
import cn.com.magicabc.R;
import cn.com.magicabc.ui.base.BaseActivity;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by hellohome on 18/3/7.
 */

public class SuperViseClassActivity extends BaseActivity {

    boolean isPlay;
    boolean isPause;
    @BindView(R.id.vedio_player)
    StandardGSYVideoPlayer mVedioPlayer;
    private OrientationUtils orientationUtils;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_supervise;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {


      final   ImageView imageView = new ImageView(this);
        Observable.just("http://live.hkstv.hk.lxdns.com/live/hks/playlist.m3u8")
                .map(new Func1<String, Bitmap>() {
                    @Override
                    public Bitmap call(String s) {
                        return getVideoThumbnail(s);
                    }
                }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Bitmap>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        imageView.setImageBitmap(bitmap);
                    }
                });
       // ImageLoader.loadImage(SuperViseClassActivity.this, imageView, "http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640");

        mVedioPlayer.getTitleTextView().setVisibility(View.GONE);
        mVedioPlayer.getBackButton().setVisibility(View.GONE);
        orientationUtils = new OrientationUtils(this, mVedioPlayer);
        orientationUtils.setEnable(false);
        GSYVideoOptionBuilder builder = new GSYVideoOptionBuilder();
        builder.setThumbImageView(imageView)
                .setIsTouchWiget(true)
                .setRotateViewAuto(true)
                .setRotateWithSystem(false)
                .setThumbPlay(true)
                .setLockLand(true)
                .setShowFullAnimation(true)
                .setNeedLockFull(true)
                .setNeedShowWifiTip(true)
                .setSeekRatio(1)
                .setUrl("http://live.hkstv.hk.lxdns.com/live/hks/playlist.m3u8")
                .setCacheWithPlay(false)
                .setStandardVideoAllCallBack(new SimpleListener() {
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        super.onPrepared(url, objects);
                        orientationUtils.setEnable(true);
                        isPlay = true;
                    }

                    @Override
                    public void onEnterFullscreen(String url, Object... objects) {
                        super.onEnterFullscreen(url, objects);
                        Log.d("hzj", "onEnterFullscreen: ");
                    }

                    @Override
                    public void onAutoComplete(String url, Object... objects) {
                        super.onAutoComplete(url, objects);
                        Log.d("hzj", "onAutoComplete: ");
                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                        Log.d("hzj", "onQuitFullscreen: ");
                        if (orientationUtils != null) {
                            orientationUtils.backToProtVideo();
                        }
                    }
                }).setLockClickListener(new LockClickListener() {
                    @Override
                    public void onClick(View view, boolean lock) {
                        if (orientationUtils != null) {
                            orientationUtils.setEnable(!lock);
                        }
                    }
                })
                .setGSYVideoProgressListener(new GSYVideoProgressListener() {
                    @Override
                    public void onProgress(int progress, int secProgress, int currentPosition, int duration) {
                    }
                })
                .build(mVedioPlayer);

        mVedioPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orientationUtils.resolveByClick();
                mVedioPlayer.startWindowFullscreen(SuperViseClassActivity.this, true, true);
            }
        });


    }

    /**
     * 根据是否全屏状态来获取GSYVideoPlayer
     *
     * @return
     */
    private GSYVideoPlayer getPlayer() {
        if (mVedioPlayer.getFullWindowPlayer() != null) {
            return mVedioPlayer.getFullWindowPlayer();
        }
        return mVedioPlayer;
    }

    @Override
    public void onBackPressed() {
        if (orientationUtils != null) {
            orientationUtils.backToProtVideo();
        }
        if (StandardGSYVideoPlayer.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }

    public Bitmap getVideoThumbnail(String url) {
        Bitmap bitmap = null;
        //MediaMetadataRetriever 是android中定义好的一个类，提供了统一
        //的接口，用于从输入的媒体文件中取得帧和元数据；
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        try {
            //根据文件路径获取缩略图
            mediaMetadataRetriever.setDataSource(url, new HashMap());
            //获得第一帧图片
            bitmap = mediaMetadataRetriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            mediaMetadataRetriever.release();
        }
        return bitmap;
    }


    @Override
    protected void onPause() {
        getPlayer().onVideoPause();
        super.onPause();
        isPause = true;
    }

    @Override
    protected void onResume() {
        getPlayer().onVideoResume();
        super.onResume();
        isPause = false;
    }

    @Override
    protected void onDestroy() {

        if (isPlay) {
            getPlayer().release();
        }
        if (orientationUtils != null) {
            orientationUtils.releaseListener();
        }
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (isPlay && !isPause) {
            mVedioPlayer.onConfigurationChanged(this, newConfig, orientationUtils);
        }
    }




}
