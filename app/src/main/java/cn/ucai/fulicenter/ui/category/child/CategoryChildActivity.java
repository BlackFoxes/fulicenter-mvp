package cn.ucai.fulicenter.ui.category.child;

import android.graphics.drawable.Drawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.constants.I;
import cn.ucai.fulicenter.data.bean.CategoryChildBean;
import cn.ucai.fulicenter.data.bean.NewGoodsBean;
import cn.ucai.fulicenter.data.utils.CommonUtils;
import cn.ucai.fulicenter.data.utils.L;
import cn.ucai.fulicenter.data.utils.MFGT;
import cn.ucai.fulicenter.ui.adapter.GoodsAdapter;
import cn.ucai.fulicenter.ui.view.CatChildFilterButton;
import cn.ucai.fulicenter.ui.view.SpaceItemDecoration;

@EActivity(R.layout.activity_category_child)
public class CategoryChildActivity extends AppCompatActivity implements CategoryChildContract.View {

    @ViewById(R.id.tv_refresh)
    TextView mTvRefresh;
    @ViewById(R.id.rv)
    RecyclerView mRv;
    @ViewById(R.id.srl)
    SwipeRefreshLayout mSrl;
    @ViewById(R.id.btn_sort_price)
    Button mBtnSortPrice;
    @ViewById(R.id.btn_sort_addtime)
    Button mBtnSortAddtime;
    @ViewById(R.id.btnCatChildFilter)
    CatChildFilterButton mBtnCatChildFilter;
    @Bean
    GoodsAdapter mAdapter;
    @Extra(I.CategoryChild.CAT_ID)
    int catId;
    @Extra(I.CategoryGroup.NAME)
    String groupName;
    @Extra(I.CategoryChild.ID)
    ArrayList<CategoryChildBean> mChildList;
    GridLayoutManager glm;
    boolean addTimeAsc = false;
    boolean priceAsc = false;
    int sortBy = I.SORT_BY_ADDTIME_DESC;

    CategoryChildContract.Presenter mPresenter;


    @AfterViews void init(){
        if (catId == 0) {
            finish();
        }else{
            mPresenter = new CategoryChildPresenter(this);
            mSrl.setColorSchemeColors(
                    getResources().getColor(R.color.google_blue),
                    getResources().getColor(R.color.google_green),
                    getResources().getColor(R.color.google_red),
                    getResources().getColor(R.color.google_yellow)
            );
            glm = new GridLayoutManager(this, I.COLUM_NUM);
            mRv.setLayoutManager(glm);
            mRv.setHasFixedSize(true);
            mRv.setAdapter(mAdapter);
            mRv.addItemDecoration(new SpaceItemDecoration(12));
            mBtnCatChildFilter.setText(groupName);
            mPresenter.setCatId(catId);
            downloadCategoryGoods(I.ACTION_DOWNLOAD);
            mBtnCatChildFilter.setOnCatFilterClickListener(groupName,mChildList);

            setPullUpListener();
            setPullDownListener();
        }
    }
    private void setPullDownListener() {
        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSrl.setRefreshing(true);
                mTvRefresh.setVisibility(View.VISIBLE);
                mPresenter.initPageId();
                downloadCategoryGoods(I.ACTION_PULL_DOWN);
            }
        });
    }
    private void setPullUpListener() {
        mRv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastPosition = glm.findLastVisibleItemPosition();
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastPosition == mAdapter.getItemCount() - 1
                        && mAdapter.isMore()) {
                    mPresenter.nextPage();
                    downloadCategoryGoods(I.ACTION_PULL_UP);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstPosition = glm.findFirstVisibleItemPosition();
                mSrl.setEnabled(firstPosition == 0);
            }
        });
    }

    private void downloadCategoryGoods(int action) {
        mPresenter.setAction(action);
        mPresenter.downloadCategoryGoods(this);
    }

    @Click void backClickArea(){
        MFGT.finish(this);
    }

    @Click({R.id.btn_sort_price, R.id.btn_sort_addtime})
    public void onClick(View view) {
        Drawable right;
        switch (view.getId()) {
            case R.id.btn_sort_price:
                if (priceAsc) {
                    sortBy = I.SORT_BY_PRICE_ASC;
                    right = getResources().getDrawable(R.mipmap.arrow_order_up);
                } else {
                    sortBy = I.SORT_BY_PRICE_DESC;
                    right = getResources().getDrawable(R.mipmap.arrow_order_down);
                }
                right.setBounds(0, 0, right.getIntrinsicWidth(), right.getIntrinsicHeight());
                mBtnSortPrice.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, right, null);
                priceAsc = !priceAsc;
                break;
            case R.id.btn_sort_addtime:
                if (addTimeAsc) {
                    sortBy = I.SORT_BY_ADDTIME_ASC;
                    right = getResources().getDrawable(R.mipmap.arrow_order_up);
                } else {
                    sortBy = I.SORT_BY_ADDTIME_DESC;
                    right = getResources().getDrawable(R.mipmap.arrow_order_down);
                }
                right.setBounds(0, 0, right.getIntrinsicWidth(), right.getIntrinsicHeight());
                mBtnSortAddtime.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, right, null);
                addTimeAsc = !addTimeAsc;
                break;
        }
        mAdapter.setSoryBy(sortBy);
    }

    @Override
    public void setPresenter(CategoryChildContract.Presenter presenter) {

    }

    @Override
    public void showError(String error) {
        mSrl.setRefreshing(false);
        mTvRefresh.setVisibility(View.GONE);
        mAdapter.setMore(false);
        CommonUtils.showShortToast(error);
        L.e("error:" + error);
    }

    @Override
    public void setAdapterMore(boolean isMore) {
        mAdapter.setMore(isMore);
    }

    @Override
    public void refreshFinish() {
        mSrl.setRefreshing(false);
        mTvRefresh.setVisibility(View.GONE);
        mAdapter.setMore(true);
    }

    @Override
    public void initAdapterData(ArrayList<NewGoodsBean> list) {
        mAdapter.initData(list);
    }

    @Override
    public void addAdapterData(ArrayList<NewGoodsBean> list) {
        mAdapter.addData(list);
    }
}
