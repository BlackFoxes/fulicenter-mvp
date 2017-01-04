package cn.ucai.fulicenter.ui.boutique;

import android.content.Context;

import java.util.ArrayList;

import cn.ucai.fulicenter.data.bean.BoutiqueBean;
import cn.ucai.fulicenter.data.net.IModelBoutique;
import cn.ucai.fulicenter.data.net.ModelBoutique;
import cn.ucai.fulicenter.data.net.OnCompleteListener;
import cn.ucai.fulicenter.data.utils.ConvertUtils;
import cn.ucai.fulicenter.data.utils.L;

/**
 * Created by clawpo on 2017/1/3.
 */
public class BoutiquePresenter implements BoutiqueContract.Presenter {
    BoutiqueContract.View mView;

    IModelBoutique model;

    public BoutiquePresenter(BoutiquesFragment fragment) {
        L.e("BoutiquePresenter","BoutiquePresenter mView.setPresenter");
        model = new ModelBoutique();
        mView = fragment;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void downData(Context context) {
        model.downloadBuotique(context, new OnCompleteListener<BoutiqueBean[]>() {
            @Override
            public void onSuccess(BoutiqueBean[] result) {
                mView.refreshFinish();
                if(result!=null && result.length>0){
                    ArrayList<BoutiqueBean> list = ConvertUtils.array2List(result);
                    mView.adapterInitData(list);
                }
            }

            @Override
            public void onError(String error) {
                mView.showError(error);
            }
        });
    }
}
