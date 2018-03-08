package cn.com.magicabc.ui.main;

import android.os.Bundle;

import java.util.List;

import cn.com.magicabc.R;
import cn.com.magicabc.ui.activity.contract.HomeContract;
import cn.com.magicabc.ui.base.BaseFragment;
import cn.com.magicabc.ui.bean.HomeWorkBean;

/**
 * Created by hellohome on 18/3/6.
 */

public class MainFragment extends BaseFragment implements HomeContract.HomeView{
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showContent() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void displayData(List<HomeWorkBean> gankEntities) {

    }
}
