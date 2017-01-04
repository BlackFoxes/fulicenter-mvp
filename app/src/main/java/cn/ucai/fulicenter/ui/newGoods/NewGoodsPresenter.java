package cn.ucai.fulicenter.ui.newGoods;

/**
 * Created by clawpo on 2017/1/3.
 */

public class NewGoodsPresenter implements NewGoodsContract.Presenter {
    NewGoodsContract.View mTaskView;

    public NewGoodsPresenter() {
        mTaskView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
