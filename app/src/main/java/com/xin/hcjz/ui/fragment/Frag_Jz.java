package com.xin.hcjz.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.xin.hcjz.R;
import com.xin.hcjz.ui.activity.AddOrderActivity;
import com.xin.hcjz.ui.base.BaseFragment;
import com.xin.hcjz.utils.uiutils.intent.IntentUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by Xin on 2018/1/18.
 */

public class Frag_Jz extends BaseFragment {

    @BindView(R.id.ll_com_shengshi)
    LinearLayout llComShengshi;
    @BindView(R.id.ll_com_huaguang)
    LinearLayout llComHuaguang;
    @BindView(R.id.ll_com_other)
    LinearLayout llComOther;
    Unbinder unbinder;

    @Override
    protected void initRootLayout(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.frag_jz, container, false);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.ll_com_shengshi, R.id.ll_com_huaguang, R.id.ll_com_other})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.ll_com_shengshi:
                bundle.putString("com", "盛世");
                IntentUtils.gotoNext(mySelf, AddOrderActivity.class, bundle);
                break;
            case R.id.ll_com_huaguang:
                bundle.putString("com", "华光");
                IntentUtils.gotoNext(mySelf, AddOrderActivity.class,bundle);
                break;
            case R.id.ll_com_other:
                bundle.putString("com", "");
                IntentUtils.gotoNext(mySelf, AddOrderActivity.class,bundle);
                break;
        }
    }
}
