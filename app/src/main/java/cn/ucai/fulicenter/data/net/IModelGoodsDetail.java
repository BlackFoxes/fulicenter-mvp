package cn.ucai.fulicenter.data.net;

import android.content.Context;

import cn.ucai.fulicenter.data.bean.CollectBean;
import cn.ucai.fulicenter.data.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.data.bean.MessageBean;


/**
 * Created by clawpo on 2016/12/28.
 */

public interface IModelGoodsDetail {
    void downloadGoodsDetail(Context context, int goodsId, OnCompleteListener<GoodsDetailsBean> listener);
    void deleteCollect(Context context, String username, int goodsId, OnCompleteListener<MessageBean> listener);
    void addCollect(Context context, String username, int goodsId, OnCompleteListener<MessageBean> listener);
    void isColected(Context context, String username, int goodsId, OnCompleteListener<MessageBean> listener);
    void addCart(Context context, String username, int goodsId, OnCompleteListener<MessageBean> listener);
    void getCollectsCount(Context context, String username, OnCompleteListener<MessageBean> listener);
    void downloadCollects(Context context, String username, int pageId, OnCompleteListener<CollectBean[]> listener);
    void updateCart(Context context, int cartId, int count, OnCompleteListener<MessageBean> listener);
    void deleteCart(Context context, int cartId, OnCompleteListener<MessageBean> listener);
    void downloadCart(Context context, String username, OnCompleteListener<String> listener);
}
