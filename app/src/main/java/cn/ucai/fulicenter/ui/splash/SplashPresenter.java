package cn.ucai.fulicenter.ui.splash;

import android.content.Context;

import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.data.bean.User;
import cn.ucai.fulicenter.data.dao.SharePrefrenceUtils;
import cn.ucai.fulicenter.data.dao.UserDao;
import cn.ucai.fulicenter.data.utils.L;

/**
 * Created by clawpo on 2017/1/4.
 */

public class SplashPresenter implements SplashContract.Presenter {
    SplashContract.View mView;

    public SplashPresenter(SplashActivity activity) {
        L.e("SplashPresenter","SplashPresenter");
        mView = activity;
        mView.setPresenter(this);
    }

    @Override
    public void saveUser(Context context) {
        L.e("SplashPresenter","saveUser");
        User user = FuLiCenterApplication.getUser();
        String username = SharePrefrenceUtils.getInstence(context).getUser();
        if(user==null && username!=null) {
            UserDao dao = new UserDao(context);
            user = dao.getUser(username);
            if(user!=null){
                FuLiCenterApplication.setUser(user);
            }
        }
        L.e("splash","user="+user);
        mView.gotoMain();
    }

    @Override
    public void start() {
        L.e("SplashPresenter","start");
    }
}
