package cn.ucai.fulicenter.ui.personalCenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by clawpo on 2017/1/3.
 */

public class PersonalCenterFragment extends Fragment implements PersonalCenterContract.View {
    PersonalCenterContract.Presenter mPresenter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(PersonalCenterContract.Presenter presenter) {

    }
}
