package cn.com.magicabc.ui.activity.contract;


import java.util.List;

import cn.com.magicabc.ui.activity.view.BaseView;
import cn.com.magicabc.ui.base.BasePresenter;
import cn.com.magicabc.ui.bean.GankEntity;

/**
 * Created by beifeng on 2017/5/9.
 * main页面 统一管理view和Presenter 的类
 */

public interface HomeContract {
  interface HomeView extends BaseView<Presenter> {
    void displayData(List<GankEntity> gankEntities);
  }

  interface Presenter extends BasePresenter {
    void loadData();
  }
}