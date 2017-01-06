package cn.ucai.fulicenter.ui.personalCenter.updateNick;

import android.content.Context;

import cn.ucai.fulicenter.base.BasePresenter;
import cn.ucai.fulicenter.base.BaseView;
import cn.ucai.fulicenter.data.bean.User;

/**
 * Created by clawpo on 2017/1/6.
 */

public interface UpdateNickContract {
    interface Presenter extends BasePresenter{
        void initData();
        void checkNick(Context context,String nick);
    }
    interface View extends BaseView<Presenter>{
        void showDialog();
        void dismissDialog();
        void showMessage(String msg);
        void showMessage(int msg);
        void finishPage();
        void initUserData(User user);
        void updateSuccess();
    }
}
