package cn.ucai.fulicenter.ui.personalCenter;

/**
 * Created by clawpo on 2017/1/3.
 */

public class PersonalCenterPresenter implements PersonalCenterContract.Presenter {
    PersonalCenterContract.View mTaskView;

    public PersonalCenterPresenter() {
        mTaskView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
