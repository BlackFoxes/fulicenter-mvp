package cn.ucai.fulicenter.ui.personalCenter;

import android.content.Context;

import cn.ucai.fulicenter.base.BasePresenter;
import cn.ucai.fulicenter.base.BaseView;
import cn.ucai.fulicenter.data.bean.User;

/**
 * Created by clawpo on 2017/1/3.
 */

public interface PersonalContract {
    interface Presenter extends BasePresenter{
        void getCollectsCount(Context context);
        void syncUserInfo(Context context);
        void initUser();
    }

    interface View extends BaseView<Presenter>{
        void showLogin();
        void showUserInfo(User user);
        void setCollectCount(int count);
    }
}
