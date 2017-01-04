package cn.ucai.fulicenter.ui.newGoods;

import android.content.Context;

import java.util.ArrayList;

import cn.ucai.fulicenter.base.BasePresenter;
import cn.ucai.fulicenter.base.BaseView;
import cn.ucai.fulicenter.data.bean.NewGoodsBean;

/**
 * Created by clawpo on 2017/1/3.
 */

public interface NewGoodsContract {
    interface Presenter extends BasePresenter{
        void setAction(int action);
        int getAction();
        void initPageId();
        void setPageNext();
        void downData(Context context, int catId);
    }

    interface View extends BaseView<Presenter>{
        void refreshFinish();
        void showError(String error);
        void adapterInitData(ArrayList<NewGoodsBean> list);
        void adapterAddData(ArrayList<NewGoodsBean> list);
        void adapterShowMore(boolean isMore);
    }
}
