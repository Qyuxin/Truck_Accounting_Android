package com.xin.hcjz.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Y on 2018/2/27.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder unbinder = null;

    protected Activity mySelf = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRootLayout();
        mySelf = this;
        unbinder = ButterKnife.bind(this);
        initView();
        initData();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    /**
     * 初始化布局
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化根布局
     */
    protected abstract void initRootLayout();
}
