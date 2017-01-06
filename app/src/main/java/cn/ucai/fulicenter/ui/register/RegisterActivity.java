package cn.ucai.fulicenter.ui.register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.constants.I;
import cn.ucai.fulicenter.data.net.IModelUser;
import cn.ucai.fulicenter.data.net.ModelUser;
import cn.ucai.fulicenter.data.utils.CommonUtils;
import cn.ucai.fulicenter.data.utils.MFGT;
import cn.ucai.fulicenter.ui.view.DisplayUtils;

@EActivity(R.layout.activity_register)
public class RegisterActivity extends AppCompatActivity implements RegisterContract.View {

    @ViewById(R.id.username)
    EditText mUsername;
    @ViewById(R.id.nick)
    EditText mNick;
    @ViewById(R.id.password)
    EditText mPassword;
    @ViewById(R.id.confirm_password)
    EditText mConfirmPassword;
    @ViewById(R.id.btn_register)
    Button mBtnRegister;

    @Bean(ModelUser.class)
    IModelUser model;

    String username;
    String nickname;
    String password;
    RegisterContract.Presenter mPresenter;
    ProgressDialog pd;

    @AfterViews void init(){
        mPresenter = new RegisterPresenter(this);
        DisplayUtils.initBackWithTitle(this, "账户注册");
    }

    @Click(R.id.btn_register)
    public void checkedInput() {
        username = mUsername.getText().toString().trim();
        nickname = mNick.getText().toString().trim();
        password = mPassword.getText().toString().trim();
        String confirmPwd = mConfirmPassword.getText().toString().trim();
        mPresenter.login(this,username,nickname,password,confirmPwd);
    }

    @Click void backClickArea(){
        MFGT.finish(this);
    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {

    }

    @Override
    public void showMessage(String msg) {
        CommonUtils.showLongToast(msg);
    }

    @Override
    public void showMessage(int msg) {
        CommonUtils.showLongToast(msg);
    }

    @Override
    public void showUserNameMessage(int msg) {
        CommonUtils.showShortToast(msg);
        mUsername.requestFocus();
    }

    @Override
    public void showUserNickMessage(int msg) {
        CommonUtils.showShortToast(msg);
        mNick.requestFocus();
    }

    @Override
    public void showUserPasswordMessage(int msg) {
        CommonUtils.showShortToast(msg);
        mPassword.requestFocus();
    }

    @Override
    public void showUserConfirmMessage(int msg) {
        CommonUtils.showShortToast(msg);
        mConfirmPassword.requestFocus();
    }

    @Override
    public void showDialog() {
        pd = new ProgressDialog(this);
        pd.setMessage(getResources().getString(R.string.registering));
        pd.show();
    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public void registerSuccess() {
        setResult(RESULT_OK,new Intent().putExtra(I.User.USER_NAME,username));
        MFGT.finish(RegisterActivity.this);
    }
}
