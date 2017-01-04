package cn.ucai.fulicenter.ui.goodsDetail;

/**
 * Created by clawpo on 2017/1/3.
 */

public class GoodsDetailPresenter implements GoodsDetailContract.Presenter {
    GoodsDetailContract.View mTaskView;

    public GoodsDetailPresenter() {
        mTaskView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
