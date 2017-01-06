package cn.ucai.fulicenter.ui.login;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.data.utils.CommonUtils;
import cn.ucai.fulicenter.data.utils.MFGT;
import cn.ucai.fulicenter.ui.view.DisplayUtils;

@EActivity(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    @ViewById(R.id.username)
    EditText mUsername;
    @ViewById(R.id.password)
    EditText mPassword;

    String username;
    String password;
    LoginContract.Presenter mPresenter;
    ProgressDialog pd;

    @AfterViews void init(){
        mPresenter = new LoginPresenter(this);
        DisplayUtils.initBackWithTitle(this,getResources().getString(R.string.login));
    }

    @Click({R.id.btn_login, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                checkedInput();
                break;
            case R.id.btn_register:
                MFGT.gotoRegister(this);
                break;
        }
    }

    private void checkedInput() {
        username = mUsername.getText().toString().trim();
        password = mPassword.getText().toString().trim();
        mPresenter.login(this,username,password);
    }

    @Click void backClickArea(){
        MFGT.finish(this);
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {

    }

    @Override
    public void errorUserName() {
        CommonUtils.showLongToast(R.string.user_name_connot_be_empty);
        mUsername.requestFocus();
    }

    @Override
    public void errorPassword() {
        CommonUtils.showLongToast(R.string.password_connot_be_empty);
        mPassword.requestFocus();
    }

    @Override
    public void showDialog() {
        pd = new ProgressDialog(this);
        pd.setMessage(getResources().getString(R.string.logining));
        pd.show();
    }

    @Override
    public void dismissDialog() {
        pd.dismiss();
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
    public void loginSuccess() {
        MFGT.finish(LoginActivity.this);
    }
}
