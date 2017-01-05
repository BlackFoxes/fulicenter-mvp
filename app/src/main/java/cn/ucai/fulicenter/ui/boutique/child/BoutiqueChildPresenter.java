package cn.ucai.fulicenter.ui.boutique.child;

import android.content.Context;

import java.util.ArrayList;

import cn.ucai.fulicenter.constants.I;
import cn.ucai.fulicenter.data.bean.BoutiqueBean;
import cn.ucai.fulicenter.data.bean.NewGoodsBean;
import cn.ucai.fulicenter.data.net.IModelNewGoods;
import cn.ucai.fulicenter.data.net.ModelNewGoods;
import cn.ucai.fulicenter.data.net.OnCompleteListener;
import cn.ucai.fulicenter.data.utils.ConvertUtils;

/**
 * Created by clawpo on 2017/1/3.
 */
public class BoutiqueChildPresenter implements BoutiqueChildContract.Presenter {
    BoutiqueChildContract.View mView;

    IModelNewGoods model;

    int mAction;
    int pageId = 1;

    BoutiqueBean boutique;

    public BoutiqueChildPresenter(BoutiqueChildActivity activity) {
        model = new ModelNewGoods();
        mView = activity;
//        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void setBoutique(Context context,BoutiqueBean boutique) {
        if (boutique==null){
            mView.finishPage();
        }else {
            this.boutique = boutique;
            initPageId();
            downData(context);
        }
    }

    @Override
    public void setAction(int action) {
        mAction = action;
    }

    @Override
    public int getAction() {
        return mAction;
    }

    @Override
    public void initPageId() {
        pageId = 1;
    }

    @Override
    public void setPageNext() {
        pageId++;
    }

    @Override
    public void downData(Context context) {
        model.downData(context, boutique.getId(), pageId, new OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                mView.refreshFinish();
                if(result!=null && result.length>0){
                    ArrayList<NewGoodsBean> list = ConvertUtils.array2List(result);
                    if(mAction== I.ACTION_DOWNLOAD || mAction == I.ACTION_PULL_DOWN) {
                        mView.adapterInitData(list);
                    }else{
                        mView.adapterAddData(list);
                    }
                    if(list.size()<I.PAGE_SIZE_DEFAULT){
                        mView.adapterShowMore(false);
                    }
                }else{
                    mView.adapterShowMore(false);
                }
            }

            @Override
            public void onError(String error) {
                mView.showError(error);
            }
        });
    }
}
