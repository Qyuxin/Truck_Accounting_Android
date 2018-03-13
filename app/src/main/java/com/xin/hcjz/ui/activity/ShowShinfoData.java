package com.xin.hcjz.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xin.hcjz.R;
import com.xin.hcjz.bean.OrderInfoBean;
import com.xin.hcjz.bean.ShinfoBean;
import com.xin.hcjz.ui.adapter.ShinfoDataAdapter;
import com.xin.hcjz.ui.base.BaseActivity;
import com.xin.hcjz.utils.datautils.customutils.SharedPreferencesUtils;
import com.xin.hcjz.utils.datautils.date.DateUtils;
import com.xin.hcjz.utils.datautils.okhttp3.OkHttpHelper;
import com.xin.hcjz.utils.datautils.okhttp3.UrlUtils;
import com.xin.hcjz.utils.uiutils.Picker.PickerListener;
import com.xin.hcjz.utils.uiutils.Picker.PickerUtils;
import com.xin.hcjz.utils.uiutils.intent.IntentUtils;
import com.xin.hcjz.utils.uiutils.sweetalertdialog.SweetAlertDialogListener;
import com.xin.hcjz.utils.uiutils.sweetalertdialog.SweetAlertDialogUtils;
import com.xin.hcjz.utils.uiutils.toast.ToastUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.R.attr.filter;

/**
 * Created by Y on 2018/3/7.
 */

public class ShowShinfoData extends BaseActivity {

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
    @BindView(R.id.btn_com)
    Button btnCom;
    @BindView(R.id.btn_arrange)
    Button btnArrange;
    @BindView(R.id.ll_condition)
    LinearLayout llCondition;
    @BindView(R.id.lv_data)
    ListView lvData;
    private ShinfoDataAdapter adapter;
    private List<ShinfoBean> listDate = new ArrayList<>();

    private List<ShinfoBean> listDataTem = new ArrayList();

    @Override
    protected void initRootLayout() {
        setContentView(R.layout.activity_show_shinfo);
    }

    @Override
    protected void initView() {
        topTvTitle.setText("预留信息");
        topRlLeft.setVisibility(View.VISIBLE);
        topTvTitle.setClickable(true);
    }

    @Override
    protected void initData() {

        adapter = new ShinfoDataAdapter(this, listDate, R.layout.item_shinfo_data);
        lvData.setAdapter(adapter);
        lvData.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                ToastUtils.showToast("当前选中-->" + listDate.get(i).getEnd());
                showEditOrDelete(i);
                return true;
            }
        });

        getShinfos();
    }

    //长按显示编辑或删除选项
    private void showEditOrDelete(final int position) {
        SweetAlertDialogUtils.showWarningDialog(mySelf, "请选择操作", "修改信息", "删除信息", new SweetAlertDialogListener.onClickListener() {
            @Override
            public void onConfirm(SweetAlertDialog sweetAlertDialog) {
                //修改
//                ToastUtils.showToast("修改功能正在开发中...");
                SweetAlertDialogUtils.dismissDialog();
                Bundle bundle = new Bundle();
                bundle.putSerializable("shinfo", listDate.get(position));
                IntentUtils.gotoNextForResult(mySelf, AddShinfoActivity.class, bundle, 1);
            }

            @Override
            public void onCancel(SweetAlertDialog sweetAlertDialog) {
                //删除
                SweetAlertDialogUtils.showWarningDialog(mySelf, "确定删除吗？", "确定", "返回", new SweetAlertDialogListener.onClickListener() {
                    @Override
                    public void onConfirm(SweetAlertDialog sweetAlertDialog) {
                        SweetAlertDialogUtils.showProgressDialog(mySelf, "删除中，请稍等...");
                        OkHttpHelper.doGet(UrlUtils.getDelOrder(listDate.get(position).getId()), new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastUtils.showToast("网络异常！");
                                        SweetAlertDialogUtils.dismissDialog();
                                    }
                                });
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                final String result = response.body().string();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (result.equals("0")) {
                                            ToastUtils.showToast("删除失败！");
                                        } else if (result.equals("1")) {
                                            ToastUtils.showToast("删除成功！");
                                            listDate.remove(position);
                                            adapter.notifyDataSetChanged();
                                        } else {
                                            ToastUtils.showToast("删除" + result + "条数据成功！");
                                        }
                                        SweetAlertDialogUtils.dismissDialog();
                                    }
                                });
                            }
                        });
                    }

                    @Override
                    public void onCancel(SweetAlertDialog sweetAlertDialog) {
                        ToastUtils.showToast("取消操作");
                        SweetAlertDialogUtils.dismissDialog();
                    }
                });
            }
        });
    }


    //获取账单信息
    private void getShinfos() {
        SweetAlertDialogUtils.showProgressDialog(mySelf, "读取中，请稍候...");
        Map map = new HashMap();
        OkHttpHelper.doPost(UrlUtils.getGetShinfosUrlPost(), map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showToast("网络连接失败");
                        SweetAlertDialogUtils.dismissDialog();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                final List<ShinfoBean> list = new Gson().fromJson(result, new TypeToken<List<ShinfoBean>>() {
                }.getType());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listDate.clear();
                        for (ShinfoBean bean1 : list) {
                            listDate.add(bean1);
                        }
                        adapter.notifyDataSetChanged();
                        if (listDate.size() == 0) {
                            ToastUtils.showToast("无数据！");
                        }
                        SweetAlertDialogUtils.dismissDialog();
                    }
                });
            }
        });
    }


    //顶部监听
    @OnClick({R.id.top_rl_left, R.id.top_ib_left, R.id.top_rl_right, R.id.top_btn_right, R.id.top_tv_title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.top_rl_left:
            case R.id.top_ib_left:
                finish();
                break;
            case R.id.top_rl_right:
            case R.id.top_btn_right:
//                selectDate();
                break;
            case R.id.top_tv_title:
//                selectCom();
                if (llCondition.getVisibility() == View.VISIBLE) {
                    llCondition.setVisibility(View.GONE);
                } else {
                    llCondition.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    //搜索条件监听
    @OnClick({R.id.btn_com, R.id.btn_arrange})
    public void onViewClicked2(View view) {
        switch (view.getId()) {
            case R.id.btn_com:
                selectCom();
                break;
            case R.id.btn_arrange:
                changeArrange();
                break;
        }
    }

    //排序方式
    private void changeArrange() {
        listDataTem.clear();
        for (ShinfoBean bean : listDate) {
            listDataTem.add(bean);
        }
        listDate.clear();
        for (int i = listDataTem.size() - 1; i >= 0; i--) {
            listDate.add(listDataTem.get(i));
        }
        adapter.notifyDataSetChanged();

    }


    //选择厂名
    private void selectCom() {
        new MaterialDialog.Builder(mySelf)
                .title("请选择厂名")
                .items(new String[]{"盛世", "华光", "所有", "其他散户"})
                .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
//                        ToastUtils.showToast("点击了-->"+which+"=="+text);
                        if (text.toString().contains("其他")) {
                            conditionBean.setCom("other");
                        } else if (text.toString().equals("所有")) {
                            conditionBean.setCom(null);
                        } else {
                            conditionBean.setCom(text + "");
                        }
                        topTvTitle.setText(text + "");
                        getShinfos();

                        return true;
                    }
                })
                .positiveText("确定")
                .show();
    }

    private void filterCom(int mode){

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == 1) {
//                refreshAll();
                getShinfos();
            }
        }
    }


}
