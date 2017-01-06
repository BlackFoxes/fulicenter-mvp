package cn.ucai.fulicenter.ui.personalCenter.settings;

import android.content.Context;

import cn.ucai.fulicenter.base.BasePresenter;
import cn.ucai.fulicenter.base.BaseView;
import cn.ucai.fulicenter.data.bean.User;

/**
 * Created by clawpo on 2017/1/6.
 */

public interface SettingsContract {
    interface Presenter extends BasePresenter{
        void updateAvatar(Context context);
        void syncUserInfo();
        void logout(Context context);
        String getUserName();
    }
    interface View extends BaseView<Presenter>{
        void showDialog();
        void dismissDialog();
        void showMessage(String message);
        void showUserInfo(User user);
        void showLogin();
        void finishPage();
    }
}
