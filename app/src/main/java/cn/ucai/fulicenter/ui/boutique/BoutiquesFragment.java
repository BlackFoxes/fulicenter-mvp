package cn.ucai.fulicenter.ui.boutique;

import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.data.bean.BoutiqueBean;
import cn.ucai.fulicenter.data.utils.CommonUtils;
import cn.ucai.fulicenter.data.utils.L;
import cn.ucai.fulicenter.ui.adapter.BoutiqueAdapter;
import cn.ucai.fulicenter.ui.view.SpaceItemDecoration;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by clawpo on 2016/12/27.
 */

@EFragment(R.layout.fragment_newgoods)
public class BoutiquesFragment extends Fragment implements BoutiqueContract.View {
    @ViewById(R.id.tv_refresh)
    TextView mTvRefresh;
    @ViewById(R.id.rv)
    RecyclerView mRv;
    @ViewById(R.id.srl)
    SwipeRefreshLayout mSrl;
    LinearLayoutManager llm;
    @Bean
    BoutiqueAdapter mAdapter;

    BoutiqueContract.Presenter mPresenter;

    @AfterViews void init(){
        mPresenter = new BoutiquePresenter(this);
        L.e("boutique","init presenter="+mPresenter);
        mSrl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)
        );
        llm = new LinearLayoutManager(getContext());
        mRv.setLayoutManager(llm);
        mRv.setHasFixedSize(true);
        mRv.setAdapter(mAdapter);
        mRv.addItemDecoration(new SpaceItemDecoration(12));
        initData();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    private void initData() {
        mPresenter.downData(getContext());
        setPullDownListener();
    }

    private void setPullDownListener() {
        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshing();
                initData();
            }
        });
    }

    @Override
    public void refreshing() {
        mSrl.setRefreshing(true);
        mTvRefresh.setVisibility(View.VISIBLE);
    }

    @Override
    public void refreshFinish() {
        mSrl.setRefreshing(false);
        mTvRefresh.setVisibility(View.GONE);
    }

    @Override
    public void showError(String error) {
        mSrl.setRefreshing(false);
        mTvRefresh.setVisibility(View.GONE);
        CommonUtils.showShortToast(error);
        L.e("error:"+error);
    }

    @Override
    public void adapterInitData(ArrayList<BoutiqueBean> list) {
        mAdapter.initData(list);
    }

    @Override
    public void setPresenter(BoutiqueContract.Presenter presenter) {
        L.e("boutique","setPresenter presenter="+presenter);
        mPresenter = checkNotNull(presenter);
    }
}
