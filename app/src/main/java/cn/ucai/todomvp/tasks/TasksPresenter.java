package cn.ucai.todomvp.tasks;

/**
 * Created by clawpo on 2017/1/3.
 */

public class TasksPresenter implements TasksContract.Presenter {
    TasksContract.View mTaskView;

    public TasksPresenter() {
        mTaskView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
