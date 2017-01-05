package cn.ucai.fulicenter.ui.category;

import android.support.v4.app.Fragment;
import android.widget.ExpandableListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.data.bean.CategoryChildBean;
import cn.ucai.fulicenter.data.bean.CategoryGroupBean;
import cn.ucai.fulicenter.data.utils.L;
import cn.ucai.fulicenter.ui.adapter.CategoryAdapter;


/**
 * Created by clawpo on 2016/12/27.
 */

@EFragment(R.layout.fragment_category)
public class CategoryFragment extends Fragment implements CategoryContract.View {

    @ViewById(R.id.elv_category)
    ExpandableListView mElvCategory;
    @Bean
    CategoryAdapter mAdapter;

    CategoryContract.Presenter mPresenter;

    @AfterViews void init(){
        mPresenter = new CategoryPresenter(this);
        mElvCategory.setGroupIndicator(null);
        mElvCategory.setAdapter(mAdapter);
        downloadGroup();
    }

    private void downloadGroup() {
        mPresenter.downloadCategoryGroup(getContext());
    }

    @Override
    public void adapterInitData(ArrayList<CategoryGroupBean> groupList, ArrayList<ArrayList<CategoryChildBean>> childList) {
        mAdapter.initData(groupList,childList);
    }

    @Override
    public void setPresenter(CategoryContract.Presenter presenter) {

    }

    @Click void layout_category_fragment(){
        L.e("category","adapter.size="+mAdapter.getGroupCount());
        if(mAdapter.getGroupCount()==0){
            downloadGroup();
        }
    }
}
