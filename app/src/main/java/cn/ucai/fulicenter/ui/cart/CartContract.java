package cn.ucai.fulicenter.ui.cart;

import android.content.Context;

import java.util.ArrayList;

import cn.ucai.fulicenter.base.BasePresenter;
import cn.ucai.fulicenter.base.BaseView;
import cn.ucai.fulicenter.data.bean.CartBean;

/**
 * Created by clawpo on 2017/1/3.
 */

public interface CartContract {
    interface Presenter extends BasePresenter{
        void downloadCart(Context context);
        void sumPrice();
        void buy();
        void deleteGoods(CartBean bean);
    }

    interface View extends BaseView<Presenter>{
        void refreshFinish();
        void refreshing();
        void setCartLayout(boolean hasCart);
        void showError(String error);
        void adapterInitData(ArrayList<CartBean> list);
        void buyThing(String cartIds);
        void buyNothing();
        void initPrice();
        void setPrice(int sumPrice,int rankPrice);
    }
}
