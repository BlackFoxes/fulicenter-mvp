package cn.ucai.fulicenter.ui.newGoods;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.constants.I;
import cn.ucai.fulicenter.data.bean.NewGoodsBean;
import cn.ucai.fulicenter.data.utils.CommonUtils;
import cn.ucai.fulicenter.data.utils.L;
import cn.ucai.fulicenter.ui.adapter.GoodsAdapter;
import cn.ucai.fulicenter.ui.view.SpaceItemDecoration;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by clawpo on 2017/1/3.
 */

@EFragment(R.layout.fragment_newgoods)
public class NewGoodsFragment extends Fragment implements NewGoodsContract.View {
    NewGoodsContract.Presenter mPresenter;

    @ViewById
    RecyclerView rv;
    @ViewById
    SwipeRefreshLayout srl;
    @ViewById
    TextView tv_refresh;
    GridLayoutManager glm;

    @Bean
    GoodsAdapter adapter;

    @AfterViews
    void bindAdapter(){
        mPresenter = new NewGoodsPresenter(this);
        srl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)
        );
        glm = new GridLayoutManager(getContext(), I.COLUM_NUM);
        rv.setLayoutManager(glm);
        rv.setHasFixedSize(true);
        rv.setAdapter(adapter);
        rv.addItemDecoration(new SpaceItemDecoration(12));
        initData(I.ACTION_DOWNLOAD);
        setPullUpListener();
        setPullDownListener();
    }

    void initData(final int action){
        //获取cat_id
        int catId=getActivity().getIntent().getIntExtra(I.NewAndBoutiqueGoods.CAT_ID,0);
        mPresenter.setAction(action);
        mPresenter.initPageId();
        mPresenter.downData(getContext(),catId);
    }
    private void setPullDownListener() {
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srl.setRefreshing(true);
                tv_refresh.setVisibility(View.VISIBLE);
                mPresenter.initPageId();
                initData(I.ACTION_PULL_DOWN);
            }
        });
    }
    private void setPullUpListener() {
        rv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastPosition = glm.findLastVisibleItemPosition();
                if(newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastPosition == adapter.getItemCount()-1
                        && adapter.isMore()){
                    mPresenter.setPageNext();
                    initData(I.ACTION_PULL_UP);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstPosition = glm.findFirstVisibleItemPosition();
                srl.setEnabled(firstPosition==0);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(@NonNull NewGoodsContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void refreshFinish() {
        srl.setRefreshing(false);
        tv_refresh.setVisibility(View.GONE);
        adapter.setMore(true);
    }

    @Override
    public void showError(String error) {
        srl.setRefreshing(false);
        tv_refresh.setVisibility(View.GONE);
        adapter.setMore(false);
        CommonUtils.showShortToast(error);
        L.e("error:"+error);
    }

    @Override
    public void adapterInitData(ArrayList<NewGoodsBean> list) {
        adapter.initData(list);
    }

    @Override
    public void adapterAddData(ArrayList<NewGoodsBean> list) {
        adapter.addData(list);
    }

    @Override
    public void adapterShowMore(boolean isMore) {
        adapter.setMore(isMore);
    }
}
