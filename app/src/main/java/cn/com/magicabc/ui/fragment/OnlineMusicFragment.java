package cn.com.magicabc.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import cn.com.magicabc.R;
import cn.com.magicabc.ui.activity.CommentAdapter;
import cn.com.magicabc.ui.activity.WordLessonRecyclerViewAdapter;
import cn.com.magicabc.ui.activity.contract.HomeContract;
import cn.com.magicabc.ui.base.BaseFragment;
import cn.com.magicabc.ui.bean.GankEntity;
import cn.com.magicabc.util.ToastUtils;

/**
 * Created by beifeng on 2017/5/5.
 */

public class OnlineMusicFragment extends BaseFragment implements HomeContract.HomeView {
  CommentAdapter commentAdapter;
  WordLessonRecyclerViewAdapter wordLessonRecyclerViewAdapter;
  @BindView(R.id.rv_list) RecyclerView rv_list;
  private HomeContract.Presenter presenter;

  @Override protected int getLayoutId() {
    return R.layout.fragment_localmusic;
  }

  @Override protected void afterCreate(Bundle savedInstanceState) {
    rv_list.setLayoutManager(new LinearLayoutManager(getActivity()));
    commentAdapter = new CommentAdapter(getActivity());
     wordLessonRecyclerViewAdapter = new WordLessonRecyclerViewAdapter(getActivity());
    rv_list.setAdapter(wordLessonRecyclerViewAdapter);
  }

  @Override public void onResume() {
    super.onResume();
    presenter.subscribe();
  }

  @Override public void setPresenter(HomeContract.Presenter presenter) {
    this.presenter = presenter;
  }

  @Override public void showLoading() {
    showLoadingView();
  }

  @Override public void showContent() {
    showContentView();
  }

  @Override public void showError() {
    showErrorView();
  }

  @Override public void displayData(List<GankEntity> gankEntities) {
    ToastUtils.showLong(""+gankEntities.size());


    wordLessonRecyclerViewAdapter.addAllData(gankEntities);

  }
}
