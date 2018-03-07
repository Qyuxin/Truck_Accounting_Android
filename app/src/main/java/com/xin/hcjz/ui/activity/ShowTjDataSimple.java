package com.xin.hcjz.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.xin.hcjz.utils.uiutils.toast.ToastUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
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
        topTvTitle.setText("账单");
        topRlLeft.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {
        String month = getIntent().getStringExtra("month");
        String com = getIntent().getStringExtra("com");
        if (month!=null){
            if (month.equals("cur")){
                //当月
                String curMonth = DateUtils.getStringYM(DateUtils.getYMDHMS());
                conditionBean.setShrqStart(curMonth);
                conditionBean.setShrqEnd(curMonth+"-31");
                topRlRight.setVisibility(View.VISIBLE);
                topIbRight.setVisibility(View.GONE);
                topBtnRight.setVisibility(View.VISIBLE);
                topBtnRight.setText(curMonth);
            }else {
                //所有月
            }
        }
        if (!TextUtils.isEmpty(com)){
            conditionBean.setCom(com);
        }

        listDate = new ArrayList<>();
        adapter = new SimpleTjDataAdapter(this,listDate,R.layout.item_tj_data2);
        lvData.setAdapter(adapter);

        getOrders();
    }

    private void getOrders() {
        Map map = new HashMap();
        map.put("orderCondition",new Gson().toJson(conditionBean));
        OkHttpHelper.doPost(UrlUtils.getGetOrdersUrlPost(), map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showToast("网络连接失败");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                final List<OrderInfoBean> list = new Gson().fromJson(result,new TypeToken<List<OrderInfoBean>>(){}.getType());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listDate.clear();
                        for (OrderInfoBean bean1:list){
                            listDate.add(bean1);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }


    @OnClick({R.id.top_rl_left,R.id.top_ib_left})
    public void onViewClicked() {
        finish();
    }
}
