package cn.ucai.fulicenter.ui.personalCenter;

import android.content.Context;

import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.data.bean.MessageBean;
import cn.ucai.fulicenter.data.bean.Result;
import cn.ucai.fulicenter.data.bean.User;
import cn.ucai.fulicenter.data.dao.UserDao;
import cn.ucai.fulicenter.data.net.IModelGoodsDetail;
import cn.ucai.fulicenter.data.net.IModelUser;
import cn.ucai.fulicenter.data.net.ModelGoodsDetail;
import cn.ucai.fulicenter.data.net.ModelUser;
import cn.ucai.fulicenter.data.net.OnCompleteListener;
import cn.ucai.fulicenter.data.utils.ResultUtils;

/**
 * Created by clawpo on 2017/1/5.
 */

public class PersonalPresenter implements PersonalContract.Presenter {
    PersonalContract.View mView;
    IModelGoodsDetail detailModel;
    IModelUser model;
    User user = null;

    public PersonalPresenter(PersonalFragment fragment) {
        mView = fragment;
        user = FuLiCenterApplication.getUser();
        model = new ModelUser();
        detailModel = new ModelGoodsDetail();
    }

    @Override
    public void start() {
        initUser();
        if (user!=null){
            mView.showUserInfo(user);
        }
    }

    @Override
    public void getCollectsCount(Context context) {
        detailModel.getCollectsCount(context, user.getMuserName(), new OnCompleteListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {
                if (result != null && result.isSuccess()) {
                    mView.setCollectCount(Integer.valueOf(result.getMsg()));
                } else {
                    mView.setCollectCount(0);
                }
            }

            @Override
            public void onError(String error) {
                mView.setCollectCount(0);
            }
        });
    }

    @Override
    public void syncUserInfo(final Context context) {
        model.syncUserInfo(context, user.getMuserName(), new OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                Result result = ResultUtils.getResultFromJson(s, User.class);
                if (result != null) {
                    User u = (User) result.getRetData();
                    if (!user.equals(u)) {
                        UserDao dao = new UserDao(context);
                        boolean b = dao.saveUser(u);
                        if (b) {
                            FuLiCenterApplication.setUser(u);
                            user = u;
                            mView.showUserInfo(user);
                        }
                    }
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    @Override
    public void initUser() {
        user = FuLiCenterApplication.getUser();
        if (user == null) {
            mView.showLogin();
        } else {
            mView.showUserInfo(user);
        }
    }
}
