package cn.ucai.fulicenter.ui.goodsDetail;

import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.constants.I;
import cn.ucai.fulicenter.data.bean.AlbumsBean;
import cn.ucai.fulicenter.data.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.data.utils.CommonUtils;
import cn.ucai.fulicenter.data.utils.L;
import cn.ucai.fulicenter.data.utils.MFGT;
import cn.ucai.fulicenter.ui.view.FlowIndicator;
import cn.ucai.fulicenter.ui.view.SlideAutoLoopView;

/**
 * Created by clawpo on 2017/1/3.
 */

@EActivity(R.layout.activity_goods_detail)
public class GoodsDetailActivity extends AppCompatActivity implements GoodsDetailContract.View {


    @ViewById(R.id.backClickArea)
    LinearLayout mBackClickArea;
    @ViewById(R.id.tv_good_name_english)
    TextView mTvGoodNameEnglish;
    @ViewById(R.id.tv_good_name)
    TextView mTvGoodName;
    @ViewById(R.id.tv_good_price_shop)
    TextView mTvGoodPriceShop;
    @ViewById(R.id.tv_good_price_current)
    TextView mTvGoodPriceCurrent;
    @ViewById(R.id.salv)
    SlideAutoLoopView mSalv;
    @ViewById(R.id.indicator)
    FlowIndicator mIndicator;
    @ViewById(R.id.wv_good_brief)
    WebView mWvGoodBrief;
    @ViewById(R.id.iv_good_collect)
    ImageView mIvGoodCollect;


    GoodsDetailContract.Presenter mPresenter;

    @AfterViews
    void initData(){
        mPresenter = new GoodsDetailPresenter(this);
        mPresenter.setGoodsId(this,getIntent().getIntExtra(I.GoodsDetails.KEY_GOODS_ID, 0));
    }

    @Override
    public void showGoodDetails(GoodsDetailsBean details) {
        mTvGoodNameEnglish.setText(details.getGoodsEnglishName());
        mTvGoodName.setText(details.getGoodsName());
        mTvGoodPriceCurrent.setText(details.getCurrencyPrice());
        mTvGoodPriceShop.setText(details.getShopPrice());
        mSalv.startPlayLoop(mIndicator, getAlbumImgUrl(details), getAlbumImgCount(details));
        mWvGoodBrief.loadDataWithBaseURL(null, details.getGoodsBrief(), I.TEXT_HTML, I.UTF_8, null);
    }

    private int getAlbumImgCount(GoodsDetailsBean details) {
        if (details.getProperties() != null && details.getProperties().length > 0) {
            return details.getProperties()[0].getAlbums().length;
        }
        return 0;
    }

    private String[] getAlbumImgUrl(GoodsDetailsBean details) {
        String[] urls = new String[]{};
        if (details.getProperties() != null && details.getProperties().length > 0) {
            AlbumsBean[] albums = details.getProperties()[0].getAlbums();
            urls = new String[albums.length];
            for (int i = 0; i < albums.length; i++) {
                urls[i] = albums[i].getImgUrl();
            }
        }
        return urls;
    }

    @Click
    void backClickArea(){
        L.e("detail","backClickArea");
        MFGT.finish(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isCollected();
    }
    private void isCollected() {
        mPresenter.initCollectStatus(this);
    }

    @Override
    public void updateGoodsCollectStatus(boolean isCollected) {
        if (isCollected) {
            mIvGoodCollect.setImageResource(R.mipmap.bg_collect_out);
        } else {
            mIvGoodCollect.setImageResource(R.mipmap.bg_collect_in);
        }
    }

    @Click void iv_good_cart(){
        mPresenter.addCart(this);
    }

    @Click void iv_good_collect(){
        mPresenter.collectGoods(this);
    }

    @Override
    public void setPresenter(GoodsDetailContract.Presenter presenter) {

    }

    @Override
    public void finishPage(){
        L.e("detail","override finishPage");
        MFGT.finish(this);
    }

    @Override
    public void showError(String error){
        L.e("details,error=" + error);
        CommonUtils.showShortToast(error);
    }

    @Override
    public void showLogin() {
        MFGT.gotoLogin(this);
    }
}