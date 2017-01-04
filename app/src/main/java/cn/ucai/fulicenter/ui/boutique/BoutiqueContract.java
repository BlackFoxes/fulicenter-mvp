package cn.ucai.fulicenter.ui.boutique;

import android.content.Context;

import java.util.ArrayList;

import cn.ucai.fulicenter.base.BasePresenter;
import cn.ucai.fulicenter.base.BaseView;
import cn.ucai.fulicenter.data.bean.BoutiqueBean;

/**
 * Created by clawpo on 2017/1/3.
 */

public interface BoutiqueContract {
    interface Presenter extends BasePresenter{
        void downData(Context context);
    }

    interface View extends BaseView<Presenter>{
        void refreshing();
        void refreshFinish();
        void showError(String error);
        void adapterInitData(ArrayList<BoutiqueBean> list);
    }
}
