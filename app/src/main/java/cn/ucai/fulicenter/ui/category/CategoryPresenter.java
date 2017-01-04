package cn.ucai.fulicenter.ui.category;

import android.content.Context;

import java.util.ArrayList;

import cn.ucai.fulicenter.data.bean.CategoryChildBean;
import cn.ucai.fulicenter.data.bean.CategoryGroupBean;
import cn.ucai.fulicenter.data.net.IModelCategory;
import cn.ucai.fulicenter.data.net.ModelCategory;
import cn.ucai.fulicenter.data.net.OnCompleteListener;
import cn.ucai.fulicenter.data.utils.ConvertUtils;
import cn.ucai.fulicenter.data.utils.L;

/**
 * Created by clawpo on 2017/1/3.
 */
public class CategoryPresenter implements CategoryContract.Presenter {
    CategoryContract.View mView;

    IModelCategory model;
    int groupCount;
    ArrayList<CategoryGroupBean> mGroupList = new ArrayList<>();
    ArrayList<ArrayList<CategoryChildBean>> mChildList = new ArrayList<>();

    public CategoryPresenter(CategoryFragment fragment) {
        model = new ModelCategory();
        mView = fragment;
//        mView.setPresenter(this);
    }

    @Override
    public void downloadCategoryGroup(final Context context) {
        model.downloadCategoryGroup(context, new OnCompleteListener<CategoryGroupBean[]>() {
            @Override
            public void onSuccess(CategoryGroupBean[] result) {
                if(result!=null && result.length>0){
                    ArrayList<CategoryGroupBean> groupList = ConvertUtils.array2List(result);
                    mGroupList.addAll(groupList);
                    for (int i=0;i<groupList.size();i++){
                        mChildList.add(new ArrayList<CategoryChildBean>());
                        CategoryGroupBean g = groupList.get(i);
                        downloadCategoryChild(context,g.getId(),i);
                    }
                }
            }

            @Override
            public void onError(String error) {
                L.e("error="+error);
            }
        });
    }

    private void downloadCategoryChild(Context context,int id,final int index) {
        model.downloadCategoryChild(context,id, new OnCompleteListener<CategoryChildBean[]>() {
            @Override
            public void onSuccess(CategoryChildBean[] result) {
                groupCount++;
                if(result!=null && result.length>0) {
                    ArrayList<CategoryChildBean> childList = ConvertUtils.array2List(result);
                    mChildList.set(index,childList);
                }
                if(groupCount==mGroupList.size()){
                    mView.adapterInitData(mGroupList,mChildList);
                }

            }

            @Override
            public void onError(String error) {
                L.e("error="+error);
            }
        });
    }

    @Override
    public void start() {

    }
}
