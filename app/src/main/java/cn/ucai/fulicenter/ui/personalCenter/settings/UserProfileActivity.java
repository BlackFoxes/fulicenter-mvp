package cn.ucai.fulicenter.ui.personalCenter.settings;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.constants.I;
import cn.ucai.fulicenter.data.bean.User;
import cn.ucai.fulicenter.data.utils.CommonUtils;
import cn.ucai.fulicenter.data.utils.ImageLoader;
import cn.ucai.fulicenter.data.utils.L;
import cn.ucai.fulicenter.data.utils.MFGT;
import cn.ucai.fulicenter.data.utils.OnSetAvatarListener;
import cn.ucai.fulicenter.ui.view.DisplayUtils;

@EActivity(R.layout.activity_user_profile)
public class UserProfileActivity extends AppCompatActivity implements SettingsContract.View {

    @ViewById(R.id.iv_user_profile_avatar)
    ImageView mIvUserProfileAvatar;
    @ViewById(R.id.tv_user_profile_name)
    TextView mTvUserProfileName;
    @ViewById(R.id.tv_user_profile_nick)
    TextView mTvUserProfileNick;

    ProgressDialog pd;

    OnSetAvatarListener mOnSetAvatarListener;
    SettingsContract.Presenter mPresenter;

    @AfterViews void initView() {
        mPresenter = new SettingsPresenter(this);
        DisplayUtils.initBackWithTitle(this,getResources().getString(R.string.user_profile));
        mPresenter.syncUserInfo();
    }

    @Click({R.id.layout_user_profile_avatar, R.id.layout_user_profile_username, R.id.layout_user_profile_nickname, R.id.btn_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_user_profile_avatar:
                mOnSetAvatarListener = new OnSetAvatarListener(this, R.id.layout_upload_avatar,
                        mPresenter.getUserName(), I.AVATAR_TYPE_USER_PATH);
                break;
            case R.id.layout_user_profile_username:
                CommonUtils.showLongToast(R.string.username_connot_be_modify);
                break;
            case R.id.layout_user_profile_nickname:
                MFGT.gotoUpdateNick(this);
                break;
            case R.id.btn_logout:
                mPresenter.logout(this);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.syncUserInfo();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        L.e("onActivityResult,requestCode="+requestCode+",resultCode="+resultCode);
        if(resultCode!=RESULT_OK){
            return;
        }
        if(requestCode== I.REQUEST_CODE_NICK){
            CommonUtils.showLongToast(R.string.update_user_nick_success);
        }else if(requestCode==OnSetAvatarListener.REQUEST_CROP_PHOTO){
            updateAvatar();
        }else {
            mOnSetAvatarListener.setAvatar(requestCode, data, mIvUserProfileAvatar);
        }
    }

    private void updateAvatar() {
        mPresenter.updateAvatar(this);
    }


    @Override
    public void setPresenter(SettingsContract.Presenter presenter) {

    }

    @Override
    public void showDialog() {
        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage(getResources().getString(R.string.update_user_avatar));
        pd.show();
    }

    @Override
    public void dismissDialog() {
        pd.dismiss();
    }

    @Override
    public void showMessage(String message) {
        CommonUtils.showLongToast(message);
        L.e("error="+message);
    }

    @Override
    public void showUserInfo(User user) {
        if(user!=null){
            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user),this,mIvUserProfileAvatar);
            mTvUserProfileName.setText(user.getMuserName());
            mTvUserProfileNick.setText(user.getMuserNick());
        }else{
            MFGT.finish(this);
        }
    }

    @Override
    public void showLogin() {
        MFGT.gotoLogin(this);
        MFGT.finish(this);
    }

    @Override
    public void finishPage() {
        MFGT.finish(this);
    }
}
