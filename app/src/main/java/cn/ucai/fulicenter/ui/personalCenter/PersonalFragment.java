package cn.ucai.fulicenter.ui.personalCenter;

import android.support.v4.app.Fragment;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.HashMap;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.data.bean.User;
import cn.ucai.fulicenter.data.utils.ImageLoader;
import cn.ucai.fulicenter.data.utils.MFGT;

/**
 * Created by clawpo on 2016/12/27.
 */

@EFragment(R.layout.fragment_personal_center)
public class PersonalFragment extends Fragment implements PersonalContract.View {

    @ViewById(R.id.iv_user_avatar)
    ImageView mIvUserAvatar;
    @ViewById(R.id.tv_user_name)
    TextView mTvUserName;

    @ViewById(R.id.center_user_order_lis)
    GridView mCenterUserOrderLis;
    @ViewById(R.id.tv_collect_count)
    TextView mTvCollectCount;

    PersonalContract.Presenter mPresenter;

    @AfterViews void init(){
        mPresenter = new PersonalPresenter(this);
        mPresenter.initUser();
        initOrderList();
    }


    private void initOrderList() {
        ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> order1 = new HashMap<String, Object>();
        order1.put("order", R.drawable.order_list1);
        data.add(order1);
        HashMap<String, Object> order2 = new HashMap<String, Object>();
        order2.put("order", R.drawable.order_list2);
        data.add(order2);
        HashMap<String, Object> order3 = new HashMap<String, Object>();
        order3.put("order", R.drawable.order_list3);
        data.add(order3);
        HashMap<String, Object> order4 = new HashMap<String, Object>();
        order4.put("order", R.drawable.order_list4);
        data.add(order4);
        HashMap<String, Object> order5 = new HashMap<String, Object>();
        order5.put("order", R.drawable.order_list5);
        data.add(order5);
        SimpleAdapter adapter = new SimpleAdapter(getContext(), data, R.layout.simple_adapter,
                new String[]{"order"}, new int[]{R.id.iv_order});
        mCenterUserOrderLis.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
        mPresenter.syncUserInfo(getContext());
        mPresenter.getCollectsCount(getContext());
    }

    @Click({R.id.tv_center_settings, R.id.center_user_info})
    public void gotoSettings() {
        MFGT.gotoSettings(getActivity());
    }

    @Click(R.id.layout_center_collect)
    public void gotoCollectsList(){
        MFGT.gotoCollects(getActivity());
    }

    @Override
    public void setPresenter(PersonalContract.Presenter presenter) {

    }

    @Override
    public void showLogin() {
        MFGT.gotoLogin(getContext());
    }

    @Override
    public void showUserInfo(User user) {
        ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), getContext(), mIvUserAvatar);
        mTvUserName.setText(user.getMuserNick());
    }

    @Override
    public void setCollectCount(int count) {
        mTvCollectCount.setText(String.valueOf(count));
    }
}
