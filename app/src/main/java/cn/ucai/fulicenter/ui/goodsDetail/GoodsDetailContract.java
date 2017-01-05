package cn.ucai.fulicenter.ui.goodsDetail;

import android.content.Context;

import cn.ucai.fulicenter.base.BasePresenter;
import cn.ucai.fulicenter.base.BaseView;
import cn.ucai.fulicenter.data.bean.GoodsDetailsBean;

/**
 * Created by clawpo on 2017/1/3.
 */

public interface GoodsDetailContract {
    interface Presenter extends BasePresenter{
        void collectGoods(Context context);
        void setGoodsId(Context context,int goodsId);
        void initCollectStatus(Context context);
        void addCart(Context context);
    }

    interface View extends BaseView<Presenter>{
        void finishPage();
        void showGoodDetails(GoodsDetailsBean bean);
        void showError(String error);
        void showLogin();
        void updateGoodsCollectStatus(boolean isCollect);
    }
}
