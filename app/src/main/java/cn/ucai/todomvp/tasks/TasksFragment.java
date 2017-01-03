package cn.ucai.todomvp.tasks;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by clawpo on 2017/1/3.
 */

public class TasksFragment extends Fragment implements TasksContract.View {
    TasksContract.Presenter mPresenter;
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
    public void setPresenter(TasksContract.Presenter presenter) {

    }
}
