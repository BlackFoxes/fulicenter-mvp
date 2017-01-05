package cn.ucai.fulicenter.ui.boutique.child;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
import cn.ucai.fulicenter.data.bean.BoutiqueBean;
import cn.ucai.fulicenter.data.bean.NewGoodsBean;
import cn.ucai.fulicenter.data.utils.CommonUtils;
import cn.ucai.fulicenter.data.utils.L;
import cn.ucai.fulicenter.data.utils.MFGT;
import cn.ucai.fulicenter.ui.adapter.GoodsAdapter;
import cn.ucai.fulicenter.ui.view.SpaceItemDecoration;

@EActivity(R.layout.activity_boutique_child)
public class BoutiqueChildActivity extends AppCompatActivity implements BoutiqueChildContract.View {

    @ViewById(R.id.tv_common_title)
    TextView mTvCommonTitle;
    @ViewById
    RecyclerView rv;
    @ViewById
    SwipeRefreshLayout srl;
    @ViewById
    TextView tv_refresh;
    GridLayoutManager glm;

    @Bean
    GoodsAdapter adapter;

    @Extra(I.Boutique.CAT_ID)
    BoutiqueBean boutique;
    BoutiqueChildContract.Presenter mPresenter;

    @AfterViews void init(){
        mPresenter = new BoutiqueChildPresenter(this);
        mPresenter.setBoutique(this,boutique);
        if(boutique != null){
            srl.setColorSchemeColors(
                    getResources().getColor(R.color.google_blue),
                    getResources().getColor(R.color.google_green),
                    getResources().getColor(R.color.google_red),
                    getResources().getColor(R.color.google_yellow)
            );
            glm = new GridLayoutManager(this, I.COLUM_NUM);
            rv.setLayoutManager(glm);
            rv.setHasFixedSize(true);
            rv.setAdapter(adapter);
            rv.addItemDecoration(new SpaceItemDecoration(12));
            mTvCommonTitle.setText(boutique.getTitle());
            setPullUpListener();
            setPullDownListener();
        }

    }
    void initData(final int action){
        //获取cat_id
        mPresenter.setAction(action);
        mPresenter.downData(this);
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
    @Click void backClickArea(){
        MFGT.finish(this);
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

    @Override
    public void finishPage() {
        finish();
    }

    @Override
    public void setPresenter(BoutiqueChildContract.Presenter presenter) {

    }
}
