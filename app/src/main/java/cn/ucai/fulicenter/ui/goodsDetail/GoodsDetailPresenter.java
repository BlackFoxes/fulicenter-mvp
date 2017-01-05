package cn.ucai.fulicenter.ui.goodsDetail;

import android.content.Context;
import android.content.Intent;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.constants.I;
import cn.ucai.fulicenter.data.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.data.bean.MessageBean;
import cn.ucai.fulicenter.data.bean.User;
import cn.ucai.fulicenter.data.net.IModelGoodsDetail;
import cn.ucai.fulicenter.data.net.ModelGoodsDetail;
import cn.ucai.fulicenter.data.net.OnCompleteListener;
import cn.ucai.fulicenter.data.utils.L;

/**
 * Created by clawpo on 2017/1/3.
 */

public class GoodsDetailPresenter implements GoodsDetailContract.Presenter {
    GoodsDetailContract.View mView;
    IModelGoodsDetail model;


    int goodsId;
    boolean isCollected = false;


    public GoodsDetailPresenter(GoodsDetailActivity activity) {
        model = new ModelGoodsDetail();
        mView = activity;
    }

    @Override
    public void start() {

    }

    public void downloadGoodsDetail(Context context) {
        model.downloadGoodsDetail(context, goodsId, new OnCompleteListener<GoodsDetailsBean>() {
            @Override
            public void onSuccess(GoodsDetailsBean result) {
                if (result != null) {
                    mView.showGoodDetails(result);
                } else {
                    mView.finishPage();
                }
            }

            @Override
            public void onError(String error) {
                mView.finishPage();
                mView.showError(error);
            }
        });
    }

    @Override
    public void collectGoods(final Context context) {
        User user = FuLiCenterApplication.getUser();
        if(user==null){
            mView.showLogin();
        }else{
            if(isCollected){
                model.deleteCollect(context, user.getMuserName(), goodsId, new OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if(result!=null && result.isSuccess()){
                            isCollected = !isCollected;
                            mView.updateGoodsCollectStatus(isCollected);
                            mView.showError(result.getMsg());
                            context.sendStickyBroadcast(new Intent("update_collect").putExtra(I.Collect.GOODS_ID,goodsId));
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
            }else{
                model.addCollect(context, user.getMuserName(), goodsId, new OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if(result!=null && result.isSuccess()){
                            isCollected = !isCollected;
                            mView.updateGoodsCollectStatus(isCollected);
                            mView.showError(result.getMsg());
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
            }
        }
    }

    @Override
    public void setGoodsId(Context context,int id) {
        this.goodsId = id;
        L.e("details", "goodsid=" + goodsId);
        if (goodsId == 0) {
            mView.finishPage();
        }else{
            downloadGoodsDetail(context);
        }
    }

    @Override
    public void initCollectStatus(Context context) {
        User user = FuLiCenterApplication.getUser();
        if (user != null) {
            model.isColected(context, user.getMuserName(), goodsId, new OnCompleteListener<MessageBean>() {
                @Override
                public void onSuccess(MessageBean result) {
                    if (result != null && result.isSuccess()) {
                        isCollected = true;
                    }else{
                        isCollected = false;
                    }
                    mView.updateGoodsCollectStatus(isCollected);
                }

                @Override
                public void onError(String error) {
                    isCollected = false;
                    mView.updateGoodsCollectStatus(isCollected);
                }
            });
        }
        mView.updateGoodsCollectStatus(isCollected);
    }

    @Override
    public void addCart(final Context context) {
        User user = FuLiCenterApplication.getUser();
        if(user!=null){
            model.addCart(context, user.getMuserName(), goodsId, new OnCompleteListener<MessageBean>() {
                @Override
                public void onSuccess(MessageBean result) {
                    if(result!=null && result.isSuccess()){
                        mView.showError(context.getResources().getString(R.string.add_goods_success));
                    }else {
                        mView.showError(context.getResources().getString(R.string.add_goods_fail));
                    }
                }

                @Override
                public void onError(String error) {
                    mView.showError(context.getResources().getString(R.string.add_goods_fail));
                    L.e("error="+error);
                }
            });
        }else{
            mView.showLogin();
        }
    }

}
