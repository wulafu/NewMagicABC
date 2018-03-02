package cn.com.magicabc.ui.activity.module;


import cn.com.magicabc.ui.activity.contract.HomeContract;
import dagger.Module;
import dagger.Provides;

/**
 * Created by beifeng on 2017/5/9.
 * 此处用来初始化 @Inject   HomePresenter(Context context, HomePageContract.View view)中的view
 */
@Module public class HomeModule {

  private HomeContract.HomeView view;

  public HomeModule(HomeContract.HomeView view) {
    this.view = view;
  }

  @Provides HomeContract.HomeView provideHomeContractView() {
    return view;
  }
}
