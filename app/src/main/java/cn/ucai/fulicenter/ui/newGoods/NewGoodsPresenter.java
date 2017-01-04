package cn.ucai.fulicenter.ui.newGoods;

import android.content.Context;

import java.util.ArrayList;

import cn.ucai.fulicenter.constants.I;
import cn.ucai.fulicenter.data.bean.NewGoodsBean;
import cn.ucai.fulicenter.data.net.IModelNewGoods;
import cn.ucai.fulicenter.data.net.ModelNewGoods;
import cn.ucai.fulicenter.data.net.OnCompleteListener;
import cn.ucai.fulicenter.data.utils.ConvertUtils;

/**
 * Created by clawpo on 2017/1/3.
 */
public class NewGoodsPresenter implements NewGoodsContract.Presenter {
    NewGoodsContract.View mView;

    IModelNewGoods model;

    int mAction;
    int pageId;

    public NewGoodsPresenter(NewGoodsFragment fragment) {
        model = new ModelNewGoods();
        mView = fragment;
//        mView.setPresenter(this);
    }

    @Override
    public void start() {

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
    public void downData(Context context, int catId) {
        model.downData(context, catId, pageId, new OnCompleteListener<NewGoodsBean[]>() {
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
