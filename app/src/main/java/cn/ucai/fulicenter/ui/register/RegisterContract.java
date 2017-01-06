package cn.ucai.fulicenter.ui.register;

import android.content.Context;

import cn.ucai.fulicenter.base.BasePresenter;
import cn.ucai.fulicenter.base.BaseView;

/**
 * Created by clawpo on 2017/1/6.
 */

public interface RegisterContract {
    interface Presenter extends BasePresenter{
        void login(Context context,String username,String nick,String password,String confirmPwd);
    }
    interface View extends BaseView<Presenter>{
        void showMessage(String msg);
        void showMessage(int msg);
        void showUserNameMessage(int msg);
        void showUserNickMessage(int msg);
        void showUserPasswordMessage(int msg);
        void showUserConfirmMessage(int msg);
        void showDialog();
        void dismissDialog();
        void registerSuccess();
    }
}
