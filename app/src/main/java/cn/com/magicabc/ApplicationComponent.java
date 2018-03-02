package cn.com.magicabc;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by beifeng on 2017/5/9.
 * 全局application 依赖的moulds
 */
@Singleton @Component(modules = { ApplicationModule.class }) public interface ApplicationComponent {
  //   获取全局app
  BabyApplication getApplication();

  //   获取全局content
  Context getContent();
}
