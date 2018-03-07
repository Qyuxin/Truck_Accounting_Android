package com.xin.hcjz.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.xin.hcjz.R;
import com.xin.hcjz.cc.Session;
import com.xin.hcjz.ui.base.BaseActivity;
import com.xin.hcjz.utils.datautils.okhttp3.OkHttpHelper;
import com.xin.hcjz.utils.datautils.okhttp3.UrlUtils;
import com.xin.hcjz.utils.uiutils.edittext.EditTextUtils;
import com.xin.hcjz.utils.uiutils.intent.IntentUtils;
import com.xin.hcjz.utils.uiutils.sweetalertdialog.SweetAlertDialogUtils;
import com.xin.hcjz.utils.uiutils.toast.ToastUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Xin on 2018/2/27.
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_username)
    MaterialEditText etUsername;
    @BindView(R.id.et_pwd)
    MaterialEditText etPwd;

    @OnClick(R.id.btn_login)
    void login(){
        SweetAlertDialogUtils.showProgressDialog(mySelf,"登录中...");
        final String username = EditTextUtils.getTextByEditText(etUsername);
        String pwd = EditTextUtils.getTextByEditText(etPwd);
        String url = UrlUtils.getUserLoginUrl(username,pwd);

        OkHttpHelper.doGet(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SweetAlertDialogUtils.dismissDialog();
                        ToastUtils.showToast("网络连接失败，请重试");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            SweetAlertDialogUtils.dismissDialog();
                            if (result.equals("0")){
                                //账号密码错误
                                ToastUtils.showToast("登录失败,请检查账号密码是否正确！");
                            }else {
                                //登陆成功
                                Session.USERNAME = username;
                                ToastUtils.showToast("登录成功");
                                IntentUtils.gotoNext(mySelf,MainActivity.class,true);
                            }
                        } catch (Exception e) {
                            SweetAlertDialogUtils.dismissDialog();
                            ToastUtils.showToast("登录失败！！");
                        }
                    }
                });

            }
        });

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initRootLayout() {
        setContentView(R.layout.activity_login);
    }
}
