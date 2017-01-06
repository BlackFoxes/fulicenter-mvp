package cn.ucai.fulicenter.data.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.constants.I;
import cn.ucai.fulicenter.data.bean.BoutiqueBean;
import cn.ucai.fulicenter.data.bean.CategoryChildBean;
import cn.ucai.fulicenter.ui.MainActivity_;
import cn.ucai.fulicenter.ui.boutique.child.BoutiqueChildActivity_;
import cn.ucai.fulicenter.ui.category.child.CategoryChildActivity_;
import cn.ucai.fulicenter.ui.goodsDetail.GoodsDetailActivity_;
import cn.ucai.fulicenter.ui.login.LoginActivity_;
import cn.ucai.fulicenter.ui.personalCenter.collect.CollectsActivity_;
import cn.ucai.fulicenter.ui.personalCenter.settings.UserProfileActivity_;
import cn.ucai.fulicenter.ui.personalCenter.updateNick.UpdateNickActivity_;
import cn.ucai.fulicenter.ui.register.RegisterActivity_;

/**
 * Created by clawpo on 2016/12/27.
 */

public class MFGT {
    public static void finish(Activity activity){
        activity.finish();
        activity.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }
    public static void startActivity(Activity activity, Intent intent){
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }
    public static void startActivity(Context context,Class<?> cls){
        Intent intent = new Intent(context,cls);
        startActivity(context,intent);
    }

    public static void startActivity(Context context,Intent intent){
        context.startActivity(intent);
        ((Activity)context).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public static void gotoMain(Activity activity){
        startActivity(activity, MainActivity_.class);
    }

    public static void gotoGoodsDetailsActivity(Context context, int goodsId) {
        Intent intent = new Intent();
        intent.setClass(context, GoodsDetailActivity_.class);
        intent.putExtra(I.GoodsDetails.KEY_GOODS_ID,goodsId);
        startActivity(context,intent);
    }

    public static void gotoLogin(Context context){
        LoginActivity_.intent(context).startForResult(I.REQUEST_CODE_LOGIN)
                .withAnimation(R.anim.push_left_in, R.anim.push_left_out);
    }
    public static void gotoBoutiqueChildActivity(Context context, BoutiqueBean bean){
        BoutiqueChildActivity_.intent(context).extra(I.Boutique.CAT_ID,bean).start()
                .withAnimation(R.anim.push_left_in, R.anim.push_left_out);
    }

    public static void gotoCategoryChildActivity(Context context, int catId, String groupName, ArrayList<CategoryChildBean> list){
        Intent intent = new Intent();
        intent.setClass(context, CategoryChildActivity_.class);
        intent.putExtra(I.CategoryChild.CAT_ID,catId);
        intent.putExtra(I.CategoryGroup.NAME,groupName);
        intent.putExtra(I.CategoryChild.ID,list);
        startActivity(context,intent);
    }
    public static void gotoRegister(Activity context){
        RegisterActivity_.intent(context)
                .startForResult(I.REQUEST_CODE_REGISTER)
                .withAnimation(R.anim.push_left_in, R.anim.push_left_out);
    }

    public static void gotoSettings(Activity activity) {
        UserProfileActivity_.intent(activity).start()
                .withAnimation(R.anim.push_left_in, R.anim.push_left_out);
    }

    public static void gotoUpdateNick(Activity activity) {
        UpdateNickActivity_.intent(activity).startForResult(I.REQUEST_CODE_NICK)
                .withAnimation(R.anim.push_left_in, R.anim.push_left_out);
    }
    public static void gotoCollects(Activity context){
        CollectsActivity_.intent(context).start()
                .withAnimation(R.anim.push_left_in, R.anim.push_left_out);
    }
    public static void gotoBuy(Context context,String cartIds){
//        OrderActivity_.intent(context).extra(I.Cart.ID,cartIds)
//                .start().withAnimation(R.anim.push_left_in, R.anim.push_left_out);
    }
}
