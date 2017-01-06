package cn.ucai.fulicenter.ui.register;

import android.content.Context;
import android.text.TextUtils;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.data.bean.Result;
import cn.ucai.fulicenter.data.net.IModelUser;
import cn.ucai.fulicenter.data.net.ModelUser;
import cn.ucai.fulicenter.data.net.OnCompleteListener;

/**
 * Created by clawpo on 2017/1/6.
 */

public class RegisterPresenter implements RegisterContract.Presenter {
    RegisterContract.View mView;
    IModelUser model;

    public RegisterPresenter(RegisterContract.View view) {
        mView = view;
        model = new ModelUser();
    }

    @Override
    public void start() {

    }

    @Override
    public void login(Context context, String username,
                      String nickname, String password,
                      String confirmPwd) {

        if(TextUtils.isEmpty(username)){
            mView.showUserNameMessage(R.string.user_name_connot_be_empty);
            return;
        }else if(!username.matches("[a-zA-Z]\\w{5,15}")){
            mView.showUserNameMessage(R.string.illegal_user_name);
            return;
        }else if(TextUtils.isEmpty(nickname)){
            mView.showUserNickMessage(R.string.nick_name_connot_be_empty);
            return;
        }else if(TextUtils.isEmpty(password)){
            mView.showUserPasswordMessage(R.string.password_connot_be_empty);
            return;
        }else if(TextUtils.isEmpty(confirmPwd)){
            mView.showUserConfirmMessage(R.string.confirm_password_connot_be_empty);
            return;
        }else if(!password.equals(confirmPwd)){
            mView.showUserConfirmMessage(R.string.two_input_password);
            return;
        }
        register(context,username,nickname,password);
    }

    private void register(Context context, String username, String nickname, String password) {
        mView.showDialog();
        model.register(context, username, nickname, password, new OnCompleteListener<Result>() {
            @Override
            public void onSuccess(Result result) {
                mView.dismissDialog();
                if(result==null){
                    mView.showMessage(R.string.register_fail);
                }else{
                    if(result.isRetMsg()){
                        mView.showMessage(R.string.register_success);
                        mView.registerSuccess();
                    }else{
                        mView.showUserNameMessage(R.string.register_fail_exists);
                    }
                }
            }

            @Override
            public void onError(String error) {
                mView.dismissDialog();
                mView.showMessage(error);
            }
        });
    }
}
