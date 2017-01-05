package cn.ucai.fulicenter.ui.cart;

import android.content.Context;

import java.util.ArrayList;

import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.data.bean.CartBean;
import cn.ucai.fulicenter.data.bean.User;
import cn.ucai.fulicenter.data.net.IModelGoodsDetail;
import cn.ucai.fulicenter.data.net.ModelGoodsDetail;
import cn.ucai.fulicenter.data.net.OnCompleteListener;
import cn.ucai.fulicenter.data.utils.L;
import cn.ucai.fulicenter.data.utils.ResultUtils;

/**
 * Created by clawpo on 2017/1/3.
 */
public class CartPresenter implements CartContract.Presenter {
    CartContract.View mView;
    IModelGoodsDetail model;
    ArrayList<CartBean> mList = new ArrayList<>();
    String cartIds="";

    public CartPresenter(CartFragment fragment) {
        L.e("BoutiquePresenter","BoutiquePresenter mView.setPresenter");
        model = new ModelGoodsDetail();
        mView = fragment;
//        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void downloadCart(Context context) {
        User user = FuLiCenterApplication.getUser();
        if (user != null) {
            model.downloadCart(context, user.getMuserName(), new OnCompleteListener<String>() {
                @Override
                public void onSuccess(String s) {
                    ArrayList<CartBean> list = ResultUtils.getCartFromJson(s);
                    mView.refreshFinish();
                    if (list != null && list.size() > 0) {
                        mList.clear();
                        mList.addAll(list);
                        mView.adapterInitData(list);
                        mView.setCartLayout(true);
                    }else{
                        mView.setCartLayout(false);
                    }
                }

                @Override
                public void onError(String error) {
                    mView.setCartLayout(false);
                    mView.showError(error);
                }
            });
        }
    }

    @Override
    public void buy() {
        if(cartIds!=null && !cartIds.equals("") && cartIds.length()>0){
            mView.buyThing(cartIds);
        }else{
            mView.buyNothing();
        }
    }

    @Override
    public void deleteGoods(CartBean bean) {
        mList.remove(bean);
    }

    @Override
    public void sumPrice(){
        cartIds = "";
        int sumPrice = 0;
        int rankPrice = 0;
        if(mList!=null && mList.size()>0){
            for (CartBean c:mList){
                if(c.isChecked()){
                    cartIds += c.getId()+",";
                    sumPrice += getPrice(c.getGoods().getCurrencyPrice())*c.getCount();
                    rankPrice += getPrice(c.getGoods().getRankPrice())*c.getCount();
                }
            }
            mView.setPrice(sumPrice,rankPrice);
        }else{
            cartIds = "";
            mView.initPrice();
        }
//        mView.setCartLayout(mList!=null&&mList.size()>0);
    }
    private int getPrice(String price){
        price = price.substring(price.indexOf("￥")+1);
        return Integer.valueOf(price);
    }
}
