package com.xin.hcjz.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.xin.hcjz.R;
import com.xin.hcjz.ui.activity.ShowTjData;
import com.xin.hcjz.ui.activity.ShowTjDataSimple;
import com.xin.hcjz.ui.base.BaseFragment;
import com.xin.hcjz.utils.uiutils.intent.IntentUtils;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Xin on 2018/1/18.
 */

public class Frag_Tj extends BaseFragment {

    @BindView(R.id.ll_tj_all)
    LinearLayout llTjAll;
    @BindView(R.id.ll_tj_curMonth)
    LinearLayout llTjCurMonth;
    @BindView(R.id.ll_tj_shengshi)
    LinearLayout llTjShengshi;

    @Override
    protected void initRootLayout(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.frag_tj, container, false);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.ll_tj_all, R.id.ll_tj_curMonth, R.id.ll_tj_shengshi})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.ll_tj_all:
                bundle.putString("month", "all");
                IntentUtils.gotoNext(mySelf, ShowTjData.class, bundle);
                break;
            case R.id.ll_tj_curMonth:
                bundle.putString("month", "cur");
                IntentUtils.gotoNext(mySelf, ShowTjDataSimple.class, bundle);
                break;
            case R.id.ll_tj_shengshi:
                bundle.putString("month", "cur");
                bundle.putString("com", "盛世");
                IntentUtils.gotoNext(mySelf, ShowTjDataSimple.class, bundle);
                break;
        }
    }
}
