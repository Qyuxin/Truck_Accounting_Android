package com.xin.hcjz.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xin.hcjz.R;
import com.xin.hcjz.cc.Session;
import com.xin.hcjz.debug.DebugConfig;
import com.xin.hcjz.ui.activity.LoginActivity;
import com.xin.hcjz.ui.base.BaseFragment;
import com.xin.hcjz.utils.uiutils.intent.IntentUtils;
import com.xin.hcjz.utils.uiutils.sweetalertdialog.SweetAlertDialogListener;
import com.xin.hcjz.utils.uiutils.sweetalertdialog.SweetAlertDialogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * Created by Xin on 2018/1/18.
 */

public class Frag_Mine extends BaseFragment {

    @BindView(R.id.ll_out)
    RelativeLayout llOut;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_name2)
    TextView tvName2;
    @BindView(R.id.ll_changePwd)
    LinearLayout llChangePwd;
    @BindView(R.id.ll_version_update)
    LinearLayout llVersionUpdate;
    @BindView(R.id.activity_personal)
    RelativeLayout activityPersonal;
    @BindView(R.id.tv_test_mode)
    TextView tvTestMode;

    @Override
    protected void initRootLayout(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.frag_mine, container, false);
    }

    @Override
    protected void initView() {
        tvName.setText(Session.USERNAME);
        tvName2.setText(Session.USERNAME);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.ll_out, R.id.ll_test_mode, R.id.tv_test_mode})
    void click(View view) {
        switch (view.getId()) {
            case R.id.ll_out:
                doLogout();
                break;
            case R.id.ll_test_mode:
            case R.id.tv_test_mode:
                break;
        }
    }


    //退出登录
    void doLogout() {
        SweetAlertDialogUtils.showWarningDialog(mySelf, "确认退出吗？", "确认退出", "点错返回", new SweetAlertDialogListener.onClickListener() {
            @Override
            public void onConfirm(SweetAlertDialog sweetAlertDialog) {
                Session.USERNAME = null;
                IntentUtils.gotoNext(mySelf, LoginActivity.class, true);
                sweetAlertDialog.dismissWithAnimation();
            }

            @Override
            public void onCancel(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
            }
        });
    }

}
