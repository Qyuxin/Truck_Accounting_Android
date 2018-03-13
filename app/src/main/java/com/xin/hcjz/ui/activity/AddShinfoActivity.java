package com.xin.hcjz.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.xin.hcjz.R;
import com.xin.hcjz.bean.ShinfoBean;
import com.xin.hcjz.ui.base.BaseActivity;
import com.xin.hcjz.utils.datautils.okhttp3.OkHttpHelper;
import com.xin.hcjz.utils.datautils.okhttp3.UrlUtils;
import com.xin.hcjz.utils.uiutils.sweetalertdialog.SweetAlertDialogListener;
import com.xin.hcjz.utils.uiutils.sweetalertdialog.SweetAlertDialogUtils;
import com.xin.hcjz.utils.uiutils.toast.ToastUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by huangju on 2018/2/27.
 */

public class AddShinfoActivity extends BaseActivity {


    private static final int STATE_UPDATE = 100;
    private static final int STATE_ADD = 101;
    @BindView(R.id.top_tv_title)
    TextView topTvTitle;
    @BindView(R.id.top_ib_left)
    ImageButton topIbLeft;
    @BindView(R.id.top_rl_left)
    RelativeLayout topRlLeft;
    @BindView(R.id.top_ib_right)
    ImageButton topIbRight;
    @BindView(R.id.top_btn_right)
    Button topBtnRight;
    @BindView(R.id.top_rl_right)
    RelativeLayout topRlRight;
    @BindView(R.id.et_com)
    MaterialEditText etCom;
    @BindView(R.id.et_start)
    MaterialEditText etStart;
    @BindView(R.id.btn_copy)
    Button btnCopy;
    @BindView(R.id.tv_title_end)
    TextView tvTitleEnd;
    @BindView(R.id.et_end)
    MaterialEditText etEnd;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.rb_updown_no)
    RadioButton rbUpdownNo;
    @BindView(R.id.rb_updown_yes)
    RadioButton rbUpdownYes;
    @BindView(R.id.rg_updown)
    RadioGroup rgUpdown;
    @BindView(R.id.et_price)
    MaterialEditText etPrice;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.rb_havenumber_no)
    RadioButton rbHavenumberNo;
    @BindView(R.id.rb_havenumber_yes)
    RadioButton rbHavenumberYes;
    @BindView(R.id.rg_havenumber)
    RadioGroup rgHavenumber;
    @BindView(R.id.root_ll)
    LinearLayout rootLl;

    private int curState = STATE_ADD;
    private ShinfoBean shinfoBean = null;

    @Override
    protected void initRootLayout() {
        setContentView(R.layout.activity_addshinfo);
    }

    @Override
    protected void initView() {
        topTvTitle.setText("添加送货信息");
        topRlLeft.setVisibility(View.VISIBLE);
        topRlRight.setVisibility(View.VISIBLE);

    }


    @Override
    protected void initData() {

        shinfoBean = (ShinfoBean) getIntent().getSerializableExtra("shinfo");
        if (shinfoBean != null) {
            curState = STATE_UPDATE;
        }
        switch (curState) {
            case STATE_ADD:
                break;
            case STATE_UPDATE:
                initUpdateData(shinfoBean);
                break;
        }
    }

    //更新账单 赋值
    private void initUpdateData(ShinfoBean bean) {
        etCom.setText(bean.getCom());
        etStart.setText(bean.getStart());
        etEnd.setText(bean.getEnd());
        if (bean.getUpdown().equals("0")) {
            rgUpdown.check(R.id.rb_updown_no);
        } else {
            rgUpdown.check(R.id.rb_updown_yes);
        }
        etPrice.setText(bean.getPrice());
        if (bean.getHavenumber().equals("0")) {
            rgHavenumber.check(R.id.rb_havenumber_no);
        } else {
            rgHavenumber.check(R.id.rb_havenumber_yes);
        }
    }


    //顶部监听
    @OnClick({R.id.top_ib_left, R.id.top_rl_left, R.id.top_ib_right, R.id.top_rl_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.top_ib_left:
            case R.id.top_rl_left:
                finish();
                break;
            case R.id.top_ib_right:
            case R.id.top_rl_right:
                doSave();
                break;
        }
    }


    //保存操作，上传或修改
    private void doSave() {
        String todo = "上传";
        String url = UrlUtils.getAddShinfoUrlPost();
        if (curState == STATE_UPDATE) {
            todo = "修改";
            url = UrlUtils.getUpdateShinfoUrlPost();
        }
        final String finalTodo = todo;
        final String finalUrl = url;
        SweetAlertDialogUtils.showWarningDialog(mySelf, "确认" + finalTodo + "吗？", "确认" + finalTodo, "返回修改", new SweetAlertDialogListener.onClickListener() {
            @Override
            public void onConfirm(final SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
                sweetAlertDialog.showCancelButton(false);
                sweetAlertDialog.showContentText(false);
                Map<String, String> map = new HashMap<String, String>();
                map.put("shinfo", new Gson().toJson(getAllData()));
                OkHttpHelper.doPost(finalUrl, map, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showToast(finalTodo + "失败，请检查网络连接后重试！");
                                sweetAlertDialog.dismiss();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String result = response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (result.equals("1")) {
                                    //上传成功
                                    ToastUtils.showToast(finalTodo + "成功！");
                                    sweetAlertDialog.dismiss();
                                    if (curState == STATE_UPDATE) {
                                        setResult(1);
                                    }
                                    finish();
                                } else {
                                    //上传失败
                                    ToastUtils.showToast(finalTodo + "失败，请重试-->" + result);
                                    sweetAlertDialog.dismiss();
                                }
                            }
                        });
                    }
                });
            }

            @Override
            public void onCancel(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
            }
        });
    }

    //表单监听
    @OnClick({R.id.et_start, R.id.btn_copy})
    public void onDataClicked(View view) {
        switch (view.getId()) {
            case R.id.et_start:
//                ToastUtils.showToast("获取终点：当前起点-->" + EditTextUtils.getTextByEditText(etStart));
//                ShinfoBean bean = getAllData();
//                String jsonString = new Gson().toJson(bean);
//                System.out.println(bean.toString());
//                System.out.println(jsonString);
                break;
            case R.id.btn_copy:
                etStart.setText(etCom.getText().toString().trim());
                break;
        }
    }

    private ShinfoBean getAllData() {
        String com = etCom.getText().toString().trim();
        String start = etStart.getText().toString().trim();
        String end = etEnd.getText().toString().trim();
        String updown = rbUpdownNo.isChecked() ? "0" : "1";
        String price = etPrice.getText().toString().trim();
        String havenumber = rbHavenumberNo.isChecked() ? "0" : "1";

        ShinfoBean bean = new ShinfoBean();
        bean.setCom(com);
        bean.setStart(start);
        bean.setEnd(end);
        bean.setUpdown(updown);
        bean.setPrice(price);
        bean.setHavenumber(havenumber);

        if (curState == STATE_UPDATE) {
            bean.setId(shinfoBean.getId());
        }


        return bean;
    }

}
