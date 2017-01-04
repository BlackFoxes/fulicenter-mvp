package cn.ucai.fulicenter.data.net;

import android.content.Context;

import cn.ucai.fulicenter.data.bean.BoutiqueBean;


/**
 * Created by clawpo on 2016/12/28.
 */

public interface IModelBoutique {
    void downloadBuotique(Context context, OnCompleteListener<BoutiqueBean[]> listener);
}
