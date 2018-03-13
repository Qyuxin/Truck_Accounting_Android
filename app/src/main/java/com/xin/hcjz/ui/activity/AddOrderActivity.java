package com.xin.hcjz.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.xin.hcjz.R;
import com.xin.hcjz.bean.OrderInfoBean;
import com.xin.hcjz.bean.DefaultInfoOfOrderBean;
import com.xin.hcjz.cc.Session;
import com.xin.hcjz.ui.base.BaseActivity;
import com.xin.hcjz.utils.datautils.date.DateUtils;
import com.xin.hcjz.utils.datautils.okhttp3.OkHttpHelper;
import com.xin.hcjz.utils.datautils.okhttp3.UrlUtils;
import com.xin.hcjz.utils.uiutils.edittext.EditTextUtils;
import com.xin.hcjz.utils.uiutils.sweetalertdialog.SweetAlertDialogListener;
import com.xin.hcjz.utils.uiutils.sweetalertdialog.SweetAlertDialogUtils;
import com.xin.hcjz.utils.uiutils.toast.ToastUtils;

import org.angmarch.views.NiceSpinner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.xin.hcjz.utils.datautils.date.DateUtils.getDays;
import static com.xin.hcjz.utils.datautils.date.DateUtils.getListYear;

/**
 * Created by huangju on 2018/2/27.
 */

public class AddOrderActivity extends BaseActivity {


    @BindView(R.id.top_tv_title)
    TextView topTvTitle;
    @BindView(R.id.top_ib_left)
    ImageButton topIbLeft;
    @BindView(R.id.top_rl_left)
    RelativeLayout topRlLeft;
    @BindView(R.id.top_ib_right)
    ImageButton topIbRight;
    @BindView(R.id.top_rl_right)
    RelativeLayout topRlRight;
    @BindView(R.id.et_com)
    MaterialEditText etCom;
    @BindView(R.id.et_start)
    MaterialEditText etStart;
    @BindView(R.id.et_end)
    MaterialEditText etEnd;
    @BindView(R.id.sp_end)
    NiceSpinner spEnd;
    @BindView(R.id.sp_year)
    NiceSpinner spYear;
    @BindView(R.id.sp_month)
    NiceSpinner spMonth;
    @BindView(R.id.sp_day)
    NiceSpinner spDay;
    @BindView(R.id.sp_period)
    NiceSpinner spPeriod;
    @BindView(R.id.et_desc)
    MaterialEditText etDesc;
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
    @BindView(R.id.et_orderNumber)
    MaterialEditText etOrderNumber;
    @BindView(R.id.root_ll)
    LinearLayout rootLl;
    @BindView(R.id.ll_end_sp)
    LinearLayout llEndSp;

    private static final int STATE_UPDATE = 100;
    private static final int STATE_ADD = 101;

    private int curState = STATE_ADD;
    private OrderInfoBean updateOrderInfoBean = null;

    @Override
    protected void initRootLayout() {
        setContentView(R.layout.activity_addorder);
    }

    @Override
    protected void initView() {
        topTvTitle.setText("添加账单");
        topRlLeft.setVisibility(View.VISIBLE);
        topRlRight.setVisibility(View.VISIBLE);

    }

    //初始化控件
    private void initViewDate() {

        spYear.attachDataSource(getListYear());
        spMonth.attachDataSource(DateUtils.getListMonth());
        spDay.attachDataSource(getDays(spYear.getText().toString(), spMonth.getText().toString()));
        spPeriod.attachDataSource(DateUtils.getListPeriod());

        spMonth.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                refreshDays();
//                String year = spYear.getText().toString();
//                String month = ((AppCompatTextView)view).getText().toString();
//                List<String> listDays = DateUtils.getDays(year,month);
//                if (!listDays.contains(spDay.getText().toString())){
//                    spDay.setSelectedIndex(0);
//                }
            }
        });
        spYear.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                refreshDays();
            }
        });


    }

    //赋值默认日期为当前时间
    private void initDefaultDate() {

        int[] ints = DateUtils.getYMDHMS();
        spMonth.setSelectedIndex(ints[1] - 1);
        spDay.setSelectedIndex(ints[2] - 1);

        if (ints[3] >= 0 && ints[3] < 6) {
            spPeriod.setSelectedIndex(0);
        } else if (ints[3] >= 6 && ints[3] < 12) {
            spPeriod.setSelectedIndex(1);
        } else if (ints[3] >= 12 && ints[3] < 18) {
            spPeriod.setSelectedIndex(2);
        } else {
            spPeriod.setSelectedIndex(3);
        }

    }

    //根据年月刷新天数
    private void refreshDays() {
//        String year = spYear.getText().toString();
//        String month = spMonth.getText().toString();
        String year = getListYear().get(spYear.getSelectedIndex());
        String month = DateUtils.getListMonth().get(spMonth.getSelectedIndex());
        List<String> listDays = DateUtils.getDays(year, month);
        if (!listDays.contains(spDay.getText().toString())) {
            spDay.setSelectedIndex(0);
        }
    }

    @Override
    protected void initData() {

        initViewDate();
        updateOrderInfoBean = (OrderInfoBean) getIntent().getSerializableExtra("orderInfo");
        if (updateOrderInfoBean != null) {
            curState = STATE_UPDATE;
        }
        switch (curState) {
            case STATE_ADD:
                initAddData();
                break;
            case STATE_UPDATE:
                initUpdateData(updateOrderInfoBean);
                break;
        }
    }

    //更新账单 赋值
    private void initUpdateData(OrderInfoBean bean) {
        String[] date = bean.getShrq().split("-");
        etCom.setText(bean.getCom());
        etStart.setText(bean.getStart());
        llEndSp.setVisibility(View.GONE);
        etEnd.setVisibility(View.VISIBLE);
        etEnd.setText(bean.getEnd());
        spYear.setText(date[0]);
        int yearIndex = DateUtils.getListYear().indexOf(date[0]);
        if (yearIndex != -1) {
            spYear.setSelectedIndex(yearIndex);
        } else {
            spYear.setSelectedIndex(DateUtils.getListYear().size() - 1);
            spYear.setText(date[0]);
        }
        spMonth.setSelectedIndex(Integer.parseInt(date[1]) - 1);
        spDay.setSelectedIndex(Integer.parseInt(date[2]) - 1);
        int periodIndex = DateUtils.getListPeriod().indexOf(bean.getShsjd());
        if (periodIndex >= 0) {
            spPeriod.setSelectedIndex(periodIndex);
        }
        spPeriod.setText(bean.getShsjd());
        etDesc.setText(bean.getDesc());
        if (bean.getUpdown().equals("0")) {
            rgUpdown.check(R.id.rb_updown_no);
        } else {
            rgUpdown.check(R.id.rb_updown_yes);
        }
        etPrice.setText(bean.getPrice());
        if (bean.getOrderHavenumber().equals("0")) {
            rgHavenumber.check(R.id.rb_havenumber_no);
        } else {
            rgHavenumber.check(R.id.rb_havenumber_yes);
        }
        etOrderNumber.setText(bean.getOrderNumber());
    }

    private void initAddData() {
        initDefaultDate();
        String com = getIntent().getStringExtra("com");
        if (com != null) {
            //华光或盛世
            etCom.setText(com);
            etStart.setText(com);

            setListener();

            getEndsByStart();

        }
    }

    private void setListener() {

        spEnd.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getUAPBySAE(((AppCompatTextView) view).getText().toString().trim());
            }
        });
    }

    private void getEndsByStart() {
        if (EditTextUtils.getTextByEditText(etStart).equals("")) {
            ToastUtils.showToast("请输入起点");
            return;
        }
        OkHttpHelper.doGet(UrlUtils.getEndsByStartUrl(EditTextUtils.getTextByEditText(etStart)), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToastUtils.showToast("获取失败，请检查网络！");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                final List<String> listEnds = new Gson().fromJson(result, new TypeToken<List<String>>() {
                }.getType());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        List<String> dataset = new ArrayList<>();
                        for (String end : listEnds) {
                            dataset.add(end);
                        }
                        if (dataset.size() == 0) {
                            ToastUtils.showToast("暂无该起点的数据，请手动输入！");
                            llEndSp.setVisibility(View.GONE);
                            etEnd.setVisibility(View.VISIBLE);
                        } else {
                            etEnd.setVisibility(View.GONE);
                            llEndSp.setVisibility(View.VISIBLE);
                            spEnd.attachDataSource(dataset);
//                        spEnd.setText("请选择终点");
//                        new Timer().schedule(new TimerTask() {
//                            @Override
//                            public void run() {
                            getUAPBySAE(spEnd.getText().toString());
                        }
//                            }
//                        },0);

                    }
                });
            }
        });
    }

    private void getUAPBySAE(String end) {
        String start = EditTextUtils.getTextByEditText(etStart);
//        String end = spEnd.getText().toString().trim();
        System.out.println(start + "||" + end);
        OkHttpHelper.doGet(UrlUtils.getDefaultInfoByPosition(start, end), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToastUtils.showToast("获取失败，请检查网络！");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                System.out.println("获取价格-->"+response.body().string());
                final List<DefaultInfoOfOrderBean> list = new Gson().fromJson(response.body().string(), new TypeToken<List<DefaultInfoOfOrderBean>>() {
                }.getType());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        ToastUtils.showToast("获取价格成功"+list.size());
                        if (list.size() > 0) {
                            DefaultInfoOfOrderBean bean = list.get(0);
                            if (bean.getUpdown() != null && bean.getUpdown().equals("1")) {
                                rgUpdown.check(rbUpdownYes.getId());
                            } else {
                                rgUpdown.check(rbUpdownNo.getId());
                            }
                            if (bean.getPrice() != null) {
                                etPrice.setText(bean.getPrice());
                            } else {
                                etPrice.setText("");
                            }
                            if (bean.getHaveNumber() != null && bean.getHaveNumber().equals("1")) {
                                rgHavenumber.check(rbHavenumberYes.getId());
                            } else {
                                rgHavenumber.check(rbHavenumberNo.getId());
                            }

                        }
                    }
                });

            }
        });
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
//                ToastUtils.showToast("保存数据");
//                ToastUtils.showToast("当前spinner-->" + spEnd.getText().toString().trim() + "<--");
                break;
        }
    }

    @OnClick({R.id.tv_title_end, R.id.btn_searchEndByStart})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_title_end:
                changeEtAndSp(llEndSp, etEnd);
                break;

            case R.id.btn_searchEndByStart:
                getEndsByStart();
                break;
        }
    }

    private void changeEtAndSp(View view1, View view2) {
        if (view1.getVisibility() == View.VISIBLE) {
            view1.setVisibility(View.GONE);
            view2.setVisibility(View.VISIBLE);
        } else {
            view1.setVisibility(View.VISIBLE);
            view2.setVisibility(View.GONE);
        }
    }

    //保存操作，上传或修改
    private void doSave() {
        String todo = "上传";
        String url = UrlUtils.getAddOrderUrlPost();
        if (curState == STATE_UPDATE) {
            todo = "修改";
            url = UrlUtils.getUpdateOrderUrlPost();
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
                map.put("orderInfo", new Gson().toJson(getAllData()));
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
    @OnClick({R.id.et_start})
    public void onDataClicked(View view) {
        switch (view.getId()) {
            case R.id.et_start:
//                ToastUtils.showToast("获取终点：当前起点-->" + EditTextUtils.getTextByEditText(etStart));
                OrderInfoBean bean = getAllData();
                String jsonString = new Gson().toJson(bean);
                System.out.println(bean.toString());
                System.out.println(jsonString);
        }
    }

    private OrderInfoBean getAllData() {
        String com = etCom.getText().toString().trim();
        String start = etStart.getText().toString().trim();
        String end;
        if (llEndSp.getVisibility() == View.VISIBLE) {
            end = spEnd.getText().toString().trim();
        } else {
            end = etEnd.getText().toString().trim();
        }
        String shrq = spYear.getText().toString() + DateUtils.format2(spMonth.getText().toString()) + DateUtils.format2(spDay.getText().toString());
        String period = spPeriod.getText().toString();
        String desc = etDesc.getText().toString().trim();
        String updown = rbUpdownNo.isChecked() ? "0" : "1";
        String price = etPrice.getText().toString().trim();
        String havenumber = rbHavenumberNo.isChecked() ? "0" : "1";
        String ordernumber = etOrderNumber.getText().toString().trim();

        OrderInfoBean bean = new OrderInfoBean();
        bean.setCom(com);
        bean.setStart(start);
        bean.setEnd(end);
        bean.setShrq(shrq);
        bean.setShsjd(period);
        bean.setDesc(desc);
        bean.setUpdown(updown);
        bean.setPrice(price);
        bean.setOrderHavenumber(havenumber);
        bean.setOrderNumber(ordernumber);

        if (curState == STATE_ADD) {
            bean.setOrderNo(System.currentTimeMillis() + "");
        } else {
            bean.setOrderNo(updateOrderInfoBean.getOrderNo());
        }
        bean.setScry(Session.USERNAME);
        bean.setScsj(DateUtils.getStringYMDHMS(DateUtils.getYMDHMS()));

        return bean;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
