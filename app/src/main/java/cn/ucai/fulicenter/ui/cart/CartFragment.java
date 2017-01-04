package cn.ucai.fulicenter.ui.cart;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.constants.I;
import cn.ucai.fulicenter.data.bean.CartBean;
import cn.ucai.fulicenter.data.utils.CommonUtils;
import cn.ucai.fulicenter.data.utils.L;
import cn.ucai.fulicenter.data.utils.MFGT;
import cn.ucai.fulicenter.ui.adapter.CartAdapter;
import cn.ucai.fulicenter.ui.view.SpaceItemDecoration;

/**
 * Created by clawpo on 2016/12/27.
 */

@EFragment(R.layout.fragment_cart)
public class CartFragment extends Fragment implements CartContract.View {
    private static final String TAG = CartFragment.class.getSimpleName();
    @ViewById(R.id.tv_refresh)
    TextView mTvRefresh;
    @ViewById(R.id.rv)
    RecyclerView mRv;
    @ViewById(R.id.srl)
    SwipeRefreshLayout mSrl;
    LinearLayoutManager llm;
    @Bean
    CartAdapter mAdapter;
    @ViewById(R.id.tv_cart_sum_price)
    TextView mTvCartSumPrice;
    @ViewById(R.id.tv_cart_save_price)
    TextView mTvCartSavePrice;
    @ViewById(R.id.layout_cart)
    RelativeLayout mLayoutCart;
    @ViewById(R.id.tv_nothing)
    TextView mTvNothing;

    CartContract.Presenter mPresenter;

    updateCartReceiver mReceiver;
//    String cartIds="";
    @AfterViews void init(){
        mPresenter = new CartPresenter(this);
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
        setCartLayout(false);

        setListener();
        downloadCart();
    }
    protected void setListener() {
        setPullDownListener();
        IntentFilter filter = new IntentFilter(I.BROADCAST_UPDATA_CART);
        mReceiver = new updateCartReceiver();
        getContext().registerReceiver(mReceiver,filter);
    }

    private void setPullDownListener() {
        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshing();
                downloadCart();
            }
        });
    }


    private void downloadCart() {
        mPresenter.downloadCart(getContext());
    }

    @Override
    public void setCartLayout(boolean hasCart) {
        mLayoutCart.setVisibility(hasCart?View.VISIBLE:View.GONE);
        mTvNothing.setVisibility(hasCart?View.GONE:View.VISIBLE);
        mRv.setVisibility(hasCart?View.VISIBLE:View.GONE);
        mPresenter.sumPrice();
    }

    @Override
    public void showError(String error) {
        mSrl.setRefreshing(false);
        mTvRefresh.setVisibility(View.GONE);
        CommonUtils.showShortToast(error);
        L.e("error:" + error);
    }

    @Override
    public void adapterInitData(ArrayList<CartBean> list) {
        mAdapter.initData(list);
    }

    @Override
    public void buyThing(String cartIds) {
        MFGT.gotoBuy(getContext(),cartIds);
    }

    @Click(R.id.tv_cart_buy)
    public void buy() {
        mPresenter.buy();
    }

    @Override
    public void buyNothing() {
        CommonUtils.showLongToast(R.string.order_nothing);
    }

    @Override
    public void initPrice() {
        mTvCartSumPrice.setText("合计:￥0");
        mTvCartSavePrice.setText("节省:￥0");
    }

    @Override
    public void setPrice(int sumPrice, int rankPrice) {
        mTvCartSumPrice.setText("合计:￥"+Double.valueOf(rankPrice));
        mTvCartSavePrice.setText("节省:￥"+Double.valueOf(sumPrice-rankPrice));
    }


    @Override
    public void setPresenter(CartContract.Presenter presenter) {

    }

    @Override
    public void refreshFinish() {
        mSrl.setRefreshing(false);
        mTvRefresh.setVisibility(View.GONE);
    }

    @Override
    public void refreshing() {
        mSrl.setRefreshing(true);
        mTvRefresh.setVisibility(View.VISIBLE);
    }

    class updateCartReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            L.e(TAG,"updateCartReceiver...");
            mPresenter.sumPrice();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mReceiver!=null){
            getContext().unregisterReceiver(mReceiver);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        L.e(TAG,"onResume.......");
        downloadCart();
    }
}
