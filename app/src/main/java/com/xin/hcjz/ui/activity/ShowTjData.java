package com.xin.hcjz.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.text.TextUtils;
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
import com.xin.hcjz.bean.OrderConditionBean;
import com.xin.hcjz.bean.OrderInfoBean;
import com.xin.hcjz.ui.adapter.TjDataAdapter;
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

/**
 * Created by Y on 2018/3/7.
 */

public class ShowTjData extends BaseActivity {
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
    @BindView(R.id.ll_condition)
    LinearLayout llCondition;
    @BindView(R.id.btn_everyNumber)
    Button btnEveryNumber;

    private TjDataAdapter adapter;
    private List<OrderInfoBean> listDate;

    private List<OrderInfoBean> listDataTem = new ArrayList();


    private OrderConditionBean conditionBean = new OrderConditionBean();


    public int count = 10;//每页数量

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
        count = SharedPreferencesUtils.getTjEveryNumber();
        btnEveryNumber.setText(count+"条/页");
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
        adapter = new TjDataAdapter(this, listDate, R.layout.item_tj_data);
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

    //长按显示编辑或删除选项
    private void showEditOrDelete(final int position) {
        SweetAlertDialogUtils.showWarningDialog(mySelf, "请选择操作", "修改账单", "删除账单", new SweetAlertDialogListener.onClickListener() {
            @Override
            public void onConfirm(SweetAlertDialog sweetAlertDialog) {
                //修改
//                ToastUtils.showToast("修改功能正在开发中...");
                SweetAlertDialogUtils.dismissDialog();
                Bundle bundle = new Bundle();
                bundle.putSerializable("orderInfo", listDate.get(position));
                IntentUtils.gotoNextForResult(mySelf, AddOrderActivity.class, bundle, 1);
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
                selectDate();
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
    @OnClick({R.id.btn_com, R.id.btn_arrange, R.id.btn_everyNumber})
    public void onViewClicked2(View view) {
        switch (view.getId()) {
            case R.id.btn_com:
                selectCom();
                break;
            case R.id.btn_arrange:
                changeArrange();
                break;
            case R.id.btn_everyNumber:
                showInputEveryNumber();
                break;
        }
    }

    //排序方式
    private void changeArrange() {
        listDataTem.clear();
        for (OrderInfoBean bean : listDate) {
            listDataTem.add(bean);
        }
        listDate.clear();
        for (int i = listDataTem.size() - 1; i >= 0; i--) {
            listDate.add(listDataTem.get(i));
        }
        adapter.notifyDataSetChanged();

    }

    //输入每页显示的数量
    private void showInputEveryNumber() {
        new MaterialDialog.Builder(mySelf)
                .title("请输入每页显示的数量")
//                .content("包含输入框的diaolog")
//                                .widgetColor(Color.BLUE)//输入框光标的颜色
                .inputType(InputType.TYPE_CLASS_PHONE)//可以输入的类型-电话号码
                //前2个一个是hint一个是预输入的文字
                .input("每页显示的数量", "", new MaterialDialog.InputCallback() {

                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        try {
                            count = Integer.parseInt(input.toString());
                            if (count<0){
                                throw new Exception();
                            }
                            ToastUtils.showToast("每页显示" + input + "条数据");
                            btnEveryNumber.setText(input + "条/页");
                            adapter.notifyDataSetChanged();
                            SharedPreferencesUtils.putTjEveryNumber(count);//保存到本地
                        } catch (Exception e) {
                            ToastUtils.showToast("输入有误，请重新输入！");
                        }
//                        Log.i("yqy", "输入的是：" + input);
                    }
                })

//                .onPositive(new MaterialDialog.SingleButtonCallback() {
//                    @Override
//                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                        if (dialog.getInputEditText().length() <= 10) {
//
//                            dialog.getActionButton(DialogAction.POSITIVE).setEnabled(false);
//                            Log.i("yqy", "<=10：" + dialog.getInputEditText().length());
//                        } else {
//                            dialog.getActionButton(DialogAction.POSITIVE).setEnabled(true);
//                            Log.i("yqy", ">10：" + dialog.getInputEditText().length());
//                        }
//                    }
//                })
                .show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == 1) {
//                refreshAll();
                getOrders();
            }
        }
    }

//    private void refreshAll() {
//        refreshData();
//        refreshUI();
//    }

//    private void refreshData() {
//
//    }
//
//    private void refreshUI() {
//
//    }
}
