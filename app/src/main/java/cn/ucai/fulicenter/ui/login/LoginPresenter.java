package cn.ucai.fulicenter.ui.login;

import android.content.Context;
import android.text.TextUtils;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.constants.I;
import cn.ucai.fulicenter.data.bean.Result;
import cn.ucai.fulicenter.data.bean.User;
import cn.ucai.fulicenter.data.dao.SharePrefrenceUtils;
import cn.ucai.fulicenter.data.dao.UserDao;
import cn.ucai.fulicenter.data.net.IModelUser;
import cn.ucai.fulicenter.data.net.ModelUser;
import cn.ucai.fulicenter.data.net.OnCompleteListener;
import cn.ucai.fulicenter.data.utils.ResultUtils;

/**
 * Created by clawpo on 2017/1/6.
 */

public class LoginPresenter implements LoginContract.Presenter {
    LoginContract.View mView;
    IModelUser model;

    public LoginPresenter(LoginContract.View view) {
        mView = view;
        model = new ModelUser();
    }

    @Override
    public void start() {

    }

    @Override
    public void login(final Context context, String username, String password) {
        checkedInput(username,password);
        mView.showDialog();
        model.login(context, username, password, new OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                Result result = ResultUtils.getResultFromJson(s,User.class);
                if(result==null){
                    mView.showMessage(R.string.login_fail);
                }else{
                    if(result.isRetMsg()){
                        User user = (User) result.getRetData();
                        UserDao dao = new UserDao(context);
                        boolean isSuccess = dao.saveUser(user);
                        if(isSuccess){
                            SharePrefrenceUtils.getInstence(context).saveUser(user.getMuserName());
                            FuLiCenterApplication.setUser(user);
                            mView.loginSuccess();
                        }else{
                            mView.showMessage(R.string.user_database_error);
                        }
                    }else{
                        if(result.getRetCode()== I.MSG_LOGIN_UNKNOW_USER){
                            mView.showMessage(R.string.login_fail_unknow_user);
                        }else if(result.getRetCode()==I.MSG_LOGIN_ERROR_PASSWORD){
                            mView.showMessage(R.string.login_fail_error_password);
                        }else{
                            mView.showMessage(R.string.login_fail);
                        }
                    }
                }
                mView.dismissDialog();
            }

            @Override
            public void onError(String error) {
                mView.dismissDialog();
                mView.showMessage(error);
            }
        });
    }

    private void checkedInput(String username, String password) {
        if(TextUtils.isEmpty(username)){
            mView.errorUserName();
            return;
        }else if(TextUtils.isEmpty(password)){
            mView.errorPassword();
            return;
        }
    }
}
