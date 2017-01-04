package cn.ucai.fulicenter.data.net;

import android.content.Context;

import cn.ucai.fulicenter.data.bean.CategoryChildBean;
import cn.ucai.fulicenter.data.bean.CategoryGroupBean;
import cn.ucai.fulicenter.data.bean.NewGoodsBean;


/**
 * Created by clawpo on 2016/12/28.
 */

public interface IModelCategory {
    void downloadCategoryGroup(Context context, OnCompleteListener<CategoryGroupBean[]> listener);
    void downloadCategoryChild(Context context, int parentId, OnCompleteListener<CategoryChildBean[]> listener);
    void downloadCategoryGoods(Context context, int catId, int pageId, OnCompleteListener<NewGoodsBean[]> listener);
}
