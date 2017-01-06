package cn.ucai.fulicenter.ui.personalCenter.collect;

import android.content.Context;

import java.util.ArrayList;

import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.constants.I;
import cn.ucai.fulicenter.data.bean.CollectBean;
import cn.ucai.fulicenter.data.bean.User;
import cn.ucai.fulicenter.data.net.IModelGoodsDetail;
import cn.ucai.fulicenter.data.net.ModelGoodsDetail;
import cn.ucai.fulicenter.data.net.OnCompleteListener;
import cn.ucai.fulicenter.data.utils.ConvertUtils;

/**
 * Created by clawpo on 2017/1/6.
 */

public class CollectPresenter implements CollectContract.Presenter {
    CollectContract.View mView;
    IModelGoodsDetail model;
    ArrayList<CollectBean> mList;
    int pageId = 1;
    User user = null;
    int action;

    public CollectPresenter(CollectContract.View view) {
        mView = view;
        model = new ModelGoodsDetail();
    }

    @Override
    public void start() {

    }

    @Override
    public void downloadCollects(Context context) {
        model.downloadCollects(context, user.getMuserName(), pageId, new OnCompleteListener<CollectBean[]>() {
            @Override
            public void onSuccess(CollectBean[] result) {
                mView.refreshFinish();
                if (result != null && result.length > 0) {
                    ArrayList<CollectBean> list = ConvertUtils.array2List(result);
                    if (action == I.ACTION_DOWNLOAD || action == I.ACTION_PULL_DOWN) {
                        mView.adapterInitData(list);
                    } else {
                        mView.adapterAddData(list);
                    }
                    if (list.size() < I.PAGE_SIZE_DEFAULT) {
                        mView.adaterIsMode(false);
                    }
                } else {
                    mView.adaterIsMode(false);
                }
            }

            @Override
            public void onError(String error) {
                mView.refreshError(error);
            }
        });
    }

    @Override
    public void setAction(int action) {
        this.action = action;
    }

    @Override
    public void initPageId() {
        pageId = 1;
    }

    @Override
    public void nextPage() {
        pageId++;
    }

    @Override
    public void initData(Context context) {
        user = FuLiCenterApplication.getUser();
        if(user==null){
            mView.finishPage();
        }else {
            action = I.ACTION_DOWNLOAD;
            downloadCollects(context);
        }
    }
}
