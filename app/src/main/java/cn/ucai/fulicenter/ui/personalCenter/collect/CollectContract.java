package cn.ucai.fulicenter.ui.personalCenter.collect;

import android.content.Context;

import java.util.ArrayList;

import cn.ucai.fulicenter.base.BasePresenter;
import cn.ucai.fulicenter.base.BaseView;
import cn.ucai.fulicenter.data.bean.CollectBean;

/**
 * Created by clawpo on 2017/1/6.
 */

public interface CollectContract {
    interface Presenter extends BasePresenter{
        void downloadCollects(Context context);
        void setAction(int action);
        void initPageId();
        void nextPage();
        void initData(Context context);
    }
    interface View extends BaseView<Presenter>{
        void refreshFinish();
        void adapterInitData(ArrayList<CollectBean> list);
        void adapterAddData(ArrayList<CollectBean> list);
        void adaterIsMode(boolean isMode);
        void refreshError(String error);
        void finishPage();
    }
}
