package com.xin.hcjz.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by huangju on 2018/2/27.
 */

public abstract class BaseFragment extends Fragment {

    private Unbinder unbinder = null;

    protected View root;
    protected Activity mySelf;
    protected Fragment myFra;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initRootLayout(inflater,container);

        unbinder = ButterKnife.bind(this, root);
        mySelf = getActivity();
        myFra = this;

        initView();
        initData();
        return root;
    }

    protected abstract void initRootLayout(LayoutInflater inflater, ViewGroup container);

    protected abstract void initView();

    protected abstract void initData();



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
