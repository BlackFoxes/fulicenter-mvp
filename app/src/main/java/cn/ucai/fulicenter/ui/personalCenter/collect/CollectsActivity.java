package cn.ucai.fulicenter.ui.personalCenter.collect;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.constants.I;
import cn.ucai.fulicenter.data.bean.CollectBean;
import cn.ucai.fulicenter.data.utils.CommonUtils;
import cn.ucai.fulicenter.data.utils.L;
import cn.ucai.fulicenter.data.utils.MFGT;
import cn.ucai.fulicenter.ui.adapter.CollectsAdapter;
import cn.ucai.fulicenter.ui.view.DisplayUtils;
import cn.ucai.fulicenter.ui.view.SpaceItemDecoration;

@EActivity(R.layout.activity_collects)
public class CollectsActivity extends AppCompatActivity implements CollectContract.View {

    @ViewById(R.id.tv_refresh)
    TextView mTvRefresh;
    @ViewById(R.id.rv)
    RecyclerView mRv;
    @ViewById(R.id.srl)
    SwipeRefreshLayout mSrl;
    @Bean
    CollectsAdapter mAdapter;
    GridLayoutManager glm;
    CollectContract.Presenter mPresenter;


    @AfterViews void initView() {
        mPresenter = new CollectPresenter(this);
        DisplayUtils.initBackWithTitle(this, getResources().getString(R.string.collect_title));
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
        initData();
        setListener();
    }

    protected void setListener() {
        setPullUpListener();
        setPullDownListener();
        IntentFilter filter = new IntentFilter("update_collect");
        registerReceiver(mReceiver,filter);
    }

    private void setPullDownListener() {
        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSrl.setRefreshing(true);
                mTvRefresh.setVisibility(View.VISIBLE);
                mPresenter.initPageId();
                downloadCollects(I.ACTION_PULL_DOWN);
            }
        });
    }

    private void downloadCollects(final int action) {
        mPresenter.setAction(action);
        mPresenter.downloadCollects(this);
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
                    downloadCollects(I.ACTION_PULL_UP);
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

    protected void initData() {
        mPresenter.initData(this);
    }

    updateCollectReceiver mReceiver;

    @Override
    public void setPresenter(CollectContract.Presenter presenter) {

    }

    @Override
    public void refreshFinish() {
        mSrl.setRefreshing(false);
        mTvRefresh.setVisibility(View.GONE);
        mAdapter.setMore(true);
    }

    @Override
    public void adapterInitData(ArrayList<CollectBean> list) {
        mAdapter.initData(list);
    }

    @Override
    public void adapterAddData(ArrayList<CollectBean> list) {
        mAdapter.addData(list);
    }

    @Override
    public void adaterIsMode(boolean isMode) {
        mAdapter.setMore(isMode);
    }

    @Override
    public void refreshError(String error) {
        mSrl.setRefreshing(false);
        mTvRefresh.setVisibility(View.GONE);
        mAdapter.setMore(false);
        CommonUtils.showShortToast(error);
        L.e("error:" + error);
    }

    @Override
    public void finishPage() {
        MFGT.finish(this);
    }

    class updateCollectReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int goodsId = intent.getIntExtra(I.Collect.GOODS_ID,0);
            if(goodsId!=0){
                CollectBean bean = new CollectBean();
                bean.setGoodsId(goodsId);
//                mAdapter.remove(bean);
                L.e("delete..."+goodsId);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mReceiver!=null){
            unregisterReceiver(mReceiver);
        }
    }
}
