package cn.ucai.fulicenter.data.net;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import cn.ucai.fulicenter.data.bean.NewGoodsBean;


public interface IModelNewGoods {
    void downData(Context context, int catId, int pageId, OnCompleteListener<NewGoodsBean[]> listener);


    void dowanGoodsAvatar(Context context, String goodsThumb, ImageView iv, int state, ViewGroup parent);

}
