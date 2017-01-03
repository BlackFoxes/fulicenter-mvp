package cn.ucai.todomvp.tasks;

import cn.ucai.todomvp.BasePresenter;
import cn.ucai.todomvp.BaseView;

/**
 * Created by clawpo on 2017/1/3.
 */

public interface TasksContract {
    interface Presenter extends BasePresenter{

    }

    interface View extends BaseView<Presenter>{

    }
}
