package cn.com.magicabc.ui.word;


import cn.com.magicabc.ApplicationComponent;
import cn.com.magicabc.util.ActivityScoped;
import dagger.Component;

/**
 * Created by beifeng on 2017/5/9.
 */
@ActivityScoped
@Component(modules = WordModule.class, dependencies = ApplicationComponent.class)
public interface WordComponent {
  void inject(WordActivity activity);
}
