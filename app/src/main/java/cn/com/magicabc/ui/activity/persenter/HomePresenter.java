package cn.com.magicabc.ui.activity.persenter;

import android.content.Context;

import java.util.List;

import javax.inject.Inject;

import cn.com.magicabc.ApplicationModule;
import cn.com.magicabc.http.HttpFactory;
import cn.com.magicabc.http.HttpResult;
import cn.com.magicabc.http.HttpTransformer;
import cn.com.magicabc.ui.activity.contract.HomeContract;
import cn.com.magicabc.ui.activity.persenter.component.DaggerPresenterComponent;
import cn.com.magicabc.ui.bean.GankEntity;
import cn.com.magicabc.util.ActivityScoped;
import cn.com.magicabc.util.LogUtils;
import rx.Subscriber;
import rx.functions.Action0;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by beifeng on 2017/5/10.
 * 首页p层
 */
@ActivityScoped
public class HomePresenter implements HomeContract.Presenter {
  private CompositeSubscription subscription;
  private HomeContract.HomeView view;
  private final Context context;
  String TAG = "homePresenter";

  @Inject HomePresenter(Context context, HomeContract.HomeView view) {

    this.context = context;
    this.view = view;
    subscription = new CompositeSubscription();
    view.setPresenter(this);
    DaggerPresenterComponent.builder()
        .applicationModule(new ApplicationModule(context))
        .build()
        .inject(this);
  }

  @Override public void loadData() {
    HttpFactory.getHttpApiSingleton()
        .getCategoryData("Android", 10, 1)
        .compose(new HttpTransformer<HttpResult<List<GankEntity>>, List<GankEntity>>())
        .doOnSubscribe(new Action0() {
          @Override public void call() {
            view.showLoading();
          }
        })
        .subscribe(new Subscriber<List<GankEntity>>() {
          @Override public void onCompleted() {
            LogUtils.d(TAG, "Completed");
            view.showContent();
          }

          @Override public void onError(Throwable e) {
            LogUtils.d(TAG, "OnError, Error is " + e.toString());
            view.showError();
          }

          @Override public void onNext(List<GankEntity> gankEntities) {
          //  view.showContent();
            view.displayData(gankEntities);

          }
        });
  }

  @Override public void subscribe() {
    loadData();
  }

  @Override public void unSubscribe() {

  }
}
