package cn.ucai.fulicenter.ui.personalCenter.updateNick;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.data.bean.User;
import cn.ucai.fulicenter.data.utils.CommonUtils;
import cn.ucai.fulicenter.data.utils.MFGT;
import cn.ucai.fulicenter.ui.view.DisplayUtils;

@EActivity(R.layout.activity_update_nick)
public class UpdateNickActivity extends AppCompatActivity implements UpdateNickContract.View {


    @ViewById(R.id.et_update_user_name)
    EditText mEtUpdateUserName;
    UpdateNickContract.Presenter mPresenter;
    ProgressDialog pd;

    @AfterViews void initView() {
        mPresenter = new UpdateNickPresenter(this);
        DisplayUtils.initBackWithTitle(this,getResources().getString(R.string.update_user_nick));
        mPresenter.initData();
    }

    @Click(R.id.btn_save)
    public void checkNick() {
        String nick = mEtUpdateUserName.getText().toString().trim();
        mPresenter.checkNick(this,nick);
    }

    @Override
    public void setPresenter(UpdateNickContract.Presenter presenter) {

    }

    @Override
    public void showDialog() {
        pd = new ProgressDialog(this);
        pd.setMessage(getResources().getString(R.string.update_user_nick));
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
    public void finishPage() {
        MFGT.finish(this);
    }

    @Override
    public void initUserData(User user) {
        mEtUpdateUserName.setText(user.getMuserNick());
        mEtUpdateUserName.setSelectAllOnFocus(true);
    }

    @Override
    public void updateSuccess() {
        setResult(RESULT_OK);
        MFGT.finish(UpdateNickActivity.this);
    }
}
