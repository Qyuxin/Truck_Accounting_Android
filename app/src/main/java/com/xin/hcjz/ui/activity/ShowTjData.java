package com.xin.hcjz.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xin.hcjz.R;
import com.xin.hcjz.bean.OrderConditionBean;
import com.xin.hcjz.bean.OrderInfoBean;
import com.xin.hcjz.ui.adapter.TjDataAdapter;
import com.xin.hcjz.ui.base.BaseActivity;
import com.xin.hcjz.utils.datautils.okhttp3.OkHttpHelper;
import com.xin.hcjz.utils.datautils.okhttp3.UrlUtils;
import com.xin.hcjz.utils.uiutils.toast.ToastUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.R.id.list;

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

    private TjDataAdapter adapter;
    private List<OrderInfoBean> listDate;

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
        getOrders();
        listDate = new ArrayList<>();
        adapter = new TjDataAdapter(this,listDate,R.layout.item_tj_data);
        lvData.setAdapter(adapter);
    }

    private void getOrders() {
        OrderConditionBean bean = new OrderConditionBean();
        Map map = new HashMap();
        map.put("orderCondition",new Gson().toJson(bean));
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


    @OnClick(R.id.top_rl_left)
    public void onViewClicked() {
        finish();
    }
}
