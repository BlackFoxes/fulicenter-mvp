package cn.ucai.fulicenter.ui.personalCenter.settings;

import android.content.Context;

import java.io.File;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.constants.I;
import cn.ucai.fulicenter.data.bean.Result;
import cn.ucai.fulicenter.data.bean.User;
import cn.ucai.fulicenter.data.dao.SharePrefrenceUtils;
import cn.ucai.fulicenter.data.net.IModelUser;
import cn.ucai.fulicenter.data.net.ModelUser;
import cn.ucai.fulicenter.data.net.OnCompleteListener;
import cn.ucai.fulicenter.data.utils.L;
import cn.ucai.fulicenter.data.utils.OnSetAvatarListener;
import cn.ucai.fulicenter.data.utils.ResultUtils;

/**
 * Created by clawpo on 2017/1/6.
 */

public class SettingsPresenter implements SettingsContract.Presenter {
    SettingsContract.View mView;
    IModelUser model;
    User user = null;

    public SettingsPresenter(SettingsContract.View view) {
        mView = view;
        model = new ModelUser();
    }

    @Override
    public void start() {

    }

    @Override
    public void updateAvatar(final Context context) {
        //file=/storage/emulated/0/Android/data/cn.ucai.fulicenter/files/Pictures/a952700
        //file=/storage/emulated/0/Android/data/cn.ucai.fulicenter/files/Pictures/user_avatar/a952700.jpg
        File file = new File(OnSetAvatarListener.getAvatarPath(context,
                user.getMavatarPath()+"/"+user.getMuserName()
                        + I.AVATAR_SUFFIX_JPG));
        L.e("file="+file.exists());
        L.e("file="+file.getAbsolutePath());
        mView.showDialog();
        model.updateAvatar(context, user.getMuserName(), file, new OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                L.e("s="+s);
                Result result = ResultUtils.getResultFromJson(s,User.class);
                L.e("result="+result);
                if(result==null){
                    mView.showMessage(context.getString(R.string.update_user_avatar_fail));
                }else{
                    User u = (User) result.getRetData();
                    if(result.isRetMsg()){
                        FuLiCenterApplication.setUser(u);
                        mView.showUserInfo(u);
                        mView.showMessage(context.getString(R.string.update_user_avatar_success));
                    }else{
                        mView.showMessage(context.getString(R.string.update_user_avatar_fail));
                    }
                }
                mView.dismissDialog();
            }

            @Override
            public void onError(String error) {
                mView.dismissDialog();
                mView.showMessage(context.getString(R.string.update_user_avatar_fail));
            }
        });
    }

    @Override
    public void syncUserInfo() {
        user = FuLiCenterApplication.getUser();
        mView.showUserInfo(user);
    }

    @Override
    public void logout(Context context) {
        if(user!=null){
            SharePrefrenceUtils.getInstence(context).removeUser();
            FuLiCenterApplication.setUser(null);
            mView.showLogin();
        }else {
            mView.finishPage();
        }
    }

    @Override
    public String getUserName() {
        user = FuLiCenterApplication.getUser();
        return user.getMuserName();
    }
}
