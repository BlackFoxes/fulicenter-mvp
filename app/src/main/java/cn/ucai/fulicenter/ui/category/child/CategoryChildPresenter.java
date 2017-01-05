package cn.ucai.fulicenter.ui.category.child;

import android.content.Context;

import java.util.ArrayList;

import cn.ucai.fulicenter.constants.I;
import cn.ucai.fulicenter.data.bean.CategoryChildBean;
import cn.ucai.fulicenter.data.bean.NewGoodsBean;
import cn.ucai.fulicenter.data.net.IModelCategory;
import cn.ucai.fulicenter.data.net.ModelCategory;
import cn.ucai.fulicenter.data.net.OnCompleteListener;
import cn.ucai.fulicenter.data.utils.ConvertUtils;
import cn.ucai.fulicenter.data.utils.L;

/**
 * Created by clawpo on 2017/1/3.
 */
public class CategoryChildPresenter implements CategoryChildContract.Presenter {
    CategoryChildContract.View mView;

    IModelCategory model;

    int mAction;
    int pageId = 1;
    int catId;
    String groupName;
    ArrayList<CategoryChildBean> mChildList;
    ArrayList<NewGoodsBean> mList;

    public CategoryChildPresenter(CategoryChildActivity activity) {
        model = new ModelCategory();
        mView = activity;
//        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void setCatId(int catId) {
        this.catId = catId;
    }

    @Override
    public void initPageId() {
        pageId=1;
    }

    @Override
    public void nextPage() {
        pageId++;
    }

    @Override
    public void setAction(int action) {
        mAction = action;
    }

    @Override
    public void downloadCategoryGoods(Context context) {
        model.downloadCategoryGoods(context, catId, pageId, new OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                mView.refreshFinish();
                L.e("result=" + result);
                if (result != null && result.length > 0) {
                    ArrayList<NewGoodsBean> list = ConvertUtils.array2List(result);
                    if (mAction == I.ACTION_DOWNLOAD || mAction == I.ACTION_PULL_DOWN) {
                        mView.initAdapterData(list);
                    } else {
                        mView.addAdapterData(list);
                    }
                    if (list.size() < I.PAGE_SIZE_DEFAULT) {
                        mView.setAdapterMore(false);
                    }
                } else {
                    mView.setAdapterMore(false);
                }
            }

            @Override
            public void onError(String error) {
                mView.showError(error);
            }
        });
    }
}
