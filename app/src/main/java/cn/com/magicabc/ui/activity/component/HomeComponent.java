package cn.com.magicabc.ui.activity.component;


import cn.com.magicabc.ApplicationComponent;
import cn.com.magicabc.MainActivity;
import cn.com.magicabc.ui.activity.module.HomeModule;
import cn.com.magicabc.util.ActivityScoped;
import dagger.Component;

/**
 * Created by beifeng on 2017/5/9.
 */
@ActivityScoped
@Component(modules = HomeModule.class, dependencies = ApplicationComponent.class)
public interface HomeComponent {
  void inject(MainActivity activity);
}
