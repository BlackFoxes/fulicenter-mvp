package cn.ucai.fulicenter.ui.splash;

import android.content.Context;

import cn.ucai.fulicenter.base.BasePresenter;
import cn.ucai.fulicenter.base.BaseView;

/**
 * Created by clawpo on 2017/1/4.
 */

public interface SplashContract {
    interface Presenter extends BasePresenter{
        void saveUser(Context context);
    }
    interface View extends BaseView<Presenter>{
        void gotoMain();
    }
}
