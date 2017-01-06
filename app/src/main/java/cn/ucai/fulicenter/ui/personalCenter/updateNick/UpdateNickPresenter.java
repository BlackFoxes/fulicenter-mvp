package cn.ucai.fulicenter.ui.personalCenter.updateNick;

import android.content.Context;
import android.text.TextUtils;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.constants.I;
import cn.ucai.fulicenter.data.bean.Result;
import cn.ucai.fulicenter.data.bean.User;
import cn.ucai.fulicenter.data.dao.UserDao;
import cn.ucai.fulicenter.data.net.IModelUser;
import cn.ucai.fulicenter.data.net.ModelUser;
import cn.ucai.fulicenter.data.net.OnCompleteListener;
import cn.ucai.fulicenter.data.utils.ResultUtils;

/**
 * Created by clawpo on 2017/1/6.
 */

public class UpdateNickPresenter implements UpdateNickContract.Presenter {
    UpdateNickContract.View mView;
    IModelUser model;
    User user = null;

    public UpdateNickPresenter(UpdateNickContract.View view) {
        mView = view;
        model = new ModelUser();
    }

    @Override
    public void initData() {
        user = FuLiCenterApplication.getUser();
        if(user!=null){
            mView.initUserData(user);
        }else{
            mView.finishPage();
        }
    }

    public void updateNick(final Context context, String nick) {
        mView.showDialog();
        model.updateNick(context, user.getMuserName(), nick, new OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                Result result = ResultUtils.getResultFromJson(s,User.class);
                if(result==null){
                    mView.showMessage(R.string.update_fail);
                }else{
                    if(result.isRetMsg()){
                        User u = (User) result.getRetData();
                        UserDao dao = new UserDao(context);
                        boolean isSuccess = dao.updateUser(u);
                        if(isSuccess){
                            FuLiCenterApplication.setUser(u);
                            mView.updateSuccess();
                        }else{
                            mView.showMessage(R.string.user_database_error);
                        }
                    }else{
                        if(result.getRetCode()== I.MSG_USER_SAME_NICK){
                            mView.showMessage(R.string.update_nick_fail_unmodify);
                        }else if(result.getRetCode()==I.MSG_USER_UPDATE_NICK_FAIL){
                            mView.showMessage(R.string.update_fail);
                        }else{
                            mView.showMessage(R.string.update_fail);
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

    @Override
    public void checkNick(Context context, String nick) {
        if(user!=null){
            if(nick.equals(user.getMuserNick())){
                mView.showMessage(R.string.update_nick_fail_unmodify);
            }else if(TextUtils.isEmpty(nick)){
                mView.showMessage(R.string.nick_name_connot_be_empty);
            }else{
                updateNick(context,nick);
            }
        }else{
            mView.finishPage();
        }
    }

    @Override
    public void start() {

    }
}
