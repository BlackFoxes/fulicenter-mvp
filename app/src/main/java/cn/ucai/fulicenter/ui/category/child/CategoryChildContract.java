package cn.ucai.fulicenter.ui.category.child;

import android.content.Context;

import java.util.ArrayList;

import cn.ucai.fulicenter.base.BasePresenter;
import cn.ucai.fulicenter.base.BaseView;
import cn.ucai.fulicenter.data.bean.NewGoodsBean;

/**
 * Created by clawpo on 2017/1/3.
 */

public interface CategoryChildContract {
    interface Presenter extends BasePresenter{
        void setCatId(int catId);
        void initPageId();
        void nextPage();
        void setAction(int action);
        void downloadCategoryGoods(Context context);
    }

    interface View extends BaseView<Presenter>{
        void showError(String error);
        void setAdapterMore(boolean isMore);
        void refreshFinish();
        void initAdapterData(ArrayList<NewGoodsBean> list);
        void addAdapterData(ArrayList<NewGoodsBean> list);
    }
}
