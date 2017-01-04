package cn.ucai.fulicenter.ui.splash;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.data.utils.L;
import cn.ucai.fulicenter.data.utils.MFGT;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by clawpo on 2017/1/4.
 */

@EActivity(R.layout.activity_splash)
public class SplashActivity extends AppCompatActivity implements SplashContract.View {
    SplashContract.Presenter mPresenter;

    @AfterViews void init(){
        mPresenter = new SplashPresenter(this);
    }

    @Override
    public void gotoMain() {
        L.e("SplashActivity","gotoMain");
        MFGT.gotoMain(this);
        MFGT.finish(this);
    }

    @Override
    public void setPresenter(@NonNull SplashContract.Presenter presenter) {
        L.e("SplashActivity","setPresenter");
        mPresenter = checkNotNull(presenter);
    }

    @Override
    protected void onStart() {
        L.e("SplashActivity","onStart mPresenter.saveUser");
        super.onStart();
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        mPresenter.saveUser(SplashActivity.this);
                    }
                }
                ,2000);
    }

    @Override
    protected void onResume() {
        L.e("SplashActivity","onResume,mPresenter.start()");
        super.onResume();
        mPresenter.start();
    }
}
