package cn.ucai.fulicenter.ui.category;

import android.content.Context;

import java.util.ArrayList;

import cn.ucai.fulicenter.base.BasePresenter;
import cn.ucai.fulicenter.base.BaseView;
import cn.ucai.fulicenter.data.bean.CategoryChildBean;
import cn.ucai.fulicenter.data.bean.CategoryGroupBean;

/**
 * Created by clawpo on 2017/1/3.
 */

public interface CategoryContract {
    interface Presenter extends BasePresenter{
        void downloadCategoryGroup(Context context);
    }

    interface View extends BaseView<Presenter>{
        void adapterInitData(ArrayList<CategoryGroupBean> groupList,
                             ArrayList<ArrayList<CategoryChildBean>> childList);
    }
}
