package cn.ucai.todomvp;

/**
 * Created by clawpo on 2017/1/3.
 */

public interface BaseView<T> {
    void setPresenter(T presenter);
}
