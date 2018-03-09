package com.xin.hcjz.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xin.hcjz.R;
import com.xin.hcjz.bean.OrderConditionBean;
import com.xin.hcjz.bean.OrderInfoBean;
import com.xin.hcjz.ui.adapter.SimpleTjDataAdapter;
import com.xin.hcjz.ui.base.BaseActivity;
import com.xin.hcjz.utils.datautils.date.DateUtils;
import com.xin.hcjz.utils.datautils.okhttp3.OkHttpHelper;
import com.xin.hcjz.utils.datautils.okhttp3.UrlUtils;
import com.xin.hcjz.utils.uiutils.Picker.PickerListener;
import com.xin.hcjz.utils.uiutils.Picker.PickerUtils;
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

/**
 * Created by Y on 2018/3/7.
 */

public class ShowTjDataSimple extends BaseActivity {
    @BindView(R.id.lv_data)
    ListView lvData;
    @BindView(R.id.top_tv_title)
    TextView topTvTitle;
    @BindView(R.id.top_ib_left)
    ImageButton topIbLeft;
    @BindView(R.id.top_rl_left)
    RelativeLayout topRlLeft;
    @BindView(R.id.top_rl_right)
    RelativeLayout topRlRight;
    @BindView(R.id.top_btn_right)
    Button topBtnRight;
    @BindView(R.id.top_ib_right)
    ImageButton topIbRight;

    private SimpleTjDataAdapter adapter;
    private List<OrderInfoBean> listDate;


    private OrderConditionBean conditionBean = new OrderConditionBean();

    @Override
    protected void initRootLayout() {
        setContentView(R.layout.activity_tj_data);
    }

    @Override
    protected void initView() {
        topTvTitle.setText("账单统计");
        topRlLeft.setVisibility(View.VISIBLE);
        topTvTitle.setClickable(true);
    }

    @Override
    protected void initData() {
        String month = getIntent().getStringExtra("month");
        String com = getIntent().getStringExtra("com");
        if (month != null) {
            if (month.equals("cur")) {
                //当月
                String curMonth = DateUtils.getStringYM(DateUtils.getYMDHMS());
                conditionBean.setShrqStart(curMonth);
                conditionBean.setShrqEnd(curMonth + "-31");
                topRlRight.setVisibility(View.VISIBLE);
                topIbRight.setVisibility(View.GONE);
                topBtnRight.setVisibility(View.VISIBLE);
                topBtnRight.setText(curMonth);
            } else {
                //所有月
            }
        }
        if (!TextUtils.isEmpty(com)) {
            topTvTitle.setText(com);
            conditionBean.setCom(com);
        }

        listDate = new ArrayList<>();
        adapter = new SimpleTjDataAdapter(this, listDate, R.layout.item_tj_data2);
        lvData.setAdapter(adapter);
        lvData.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                ToastUtils.showToast("当前选中-->" + listDate.get(i).getEnd());
                showEditOrDelete(i);
                return true;
            }
        });

        getOrders();
    }

    private void showEditOrDelete(final int position) {
        SweetAlertDialogUtils.showWarningDialog(mySelf, "请选择操作", "修改账单", "删除账单", new SweetAlertDialogListener.onClickListener() {
            @Override
            public void onConfirm(SweetAlertDialog sweetAlertDialog) {
                //修改
                ToastUtils.showToast("修改功能正在开发中...");
                SweetAlertDialogUtils.dismissDialog();
            }

            @Override
            public void onCancel(SweetAlertDialog sweetAlertDialog) {
                //删除
                SweetAlertDialogUtils.showWarningDialog(mySelf, "确定删除吗？", "确定", "返回", new SweetAlertDialogListener.onClickListener() {
                    @Override
                    public void onConfirm(SweetAlertDialog sweetAlertDialog) {
                        SweetAlertDialogUtils.showProgressDialog(mySelf, "删除中，请稍等...");
                        OkHttpHelper.doGet(UrlUtils.getDelOrder(listDate.get(position).getOrderNo()), new Callback() {
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
    private void getOrders() {
        SweetAlertDialogUtils.showProgressDialog(mySelf, "读取中，请稍候...");
        Map map = new HashMap();
        map.put("orderCondition", new Gson().toJson(conditionBean));
        OkHttpHelper.doPost(UrlUtils.getGetOrdersUrlPost(), map, new Callback() {
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
                final List<OrderInfoBean> list = new Gson().fromJson(result, new TypeToken<List<OrderInfoBean>>() {
                }.getType());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listDate.clear();
                        for (OrderInfoBean bean1 : list) {
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


    @OnClick({R.id.top_rl_left, R.id.top_ib_left, R.id.top_rl_right, R.id.top_btn_right, R.id.top_tv_title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.top_rl_left:
            case R.id.top_ib_left:
                finish();
                break;
            case R.id.top_rl_right:
            case R.id.top_btn_right:
                selectDate();
                break;
            case R.id.top_tv_title:
                selectCom();
                break;
        }
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
                        getOrders();
                        return true;
                    }
                })
                .positiveText("确定")
                .show();
    }

    //选择日期
    private void selectDate() {
        new PickerUtils().onYearMonthPicker(mySelf, new PickerListener.onYearMonthListener() {
            @Override
            public void onYearMonthListener(final String year, final String month) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String selectMonth = year + "-" + DateUtils.format2(month);
                        topBtnRight.setText(selectMonth);
                        conditionBean.setShrqStart(selectMonth);
                        conditionBean.setShrqEnd(selectMonth + "-31");
                        getOrders();
                    }
                });
            }
        });
    }
}
