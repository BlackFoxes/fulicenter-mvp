package cn.ucai.fulicenter.ui.login;

import android.content.Context;

import cn.ucai.fulicenter.base.BasePresenter;
import cn.ucai.fulicenter.base.BaseView;

/**
 * Created by clawpo on 2017/1/6.
 */

public interface LoginContract {
    interface Presenter extends BasePresenter{
        void login(Context context,String username,String password);
    }
    interface View extends BaseView<Presenter>{
        void errorUserName();
        void errorPassword();
        void showDialog();
        void dismissDialog();
        void showMessage(String msg);
        void showMessage(int msg);
        void loginSuccess();
    }
}
