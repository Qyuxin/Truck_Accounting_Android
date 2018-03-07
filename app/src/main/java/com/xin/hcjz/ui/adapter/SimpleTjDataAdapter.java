package com.xin.hcjz.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xin.hcjz.R;
import com.xin.hcjz.bean.OrderInfoBean;
import com.xin.hcjz.ui.base.MyBaseAdapter;

import java.util.List;

/**
 * Created by Y on 2018/3/7.
 */

public class SimpleTjDataAdapter extends MyBaseAdapter<OrderInfoBean> {

    private TextView tvContentNumber;
    private TextView tvContentShsj;
    private TextView tvContentStart;
    private TextView tvContentEnd;
    private TextView tvContentPrice;
    private TextView tvContentDesc;
    private TextView tvContentUpdown;
    private TextView tvContentHaveOrderNumber;
    private TextView tvContentOrderNumber;
    private LinearLayout llDesc;

    public SimpleTjDataAdapter(Context context, List<OrderInfoBean> list, int layout) {
        super(context, list, layout);
    }

    @Override
    public void convert(ViewHolder holder, OrderInfoBean orderInfoBean, int position) {
        tvContentNumber = holder.findViewById(R.id.tv_content_number);
        tvContentShsj = holder.findViewById(R.id.tv_content_shsj);
        tvContentStart = holder.findViewById(R.id.tv_content_start);
        tvContentEnd = holder.findViewById(R.id.tv_content_end);
        tvContentPrice = holder.findViewById(R.id.tv_content_price);
        tvContentDesc = holder.findViewById(R.id.tv_content_desc);
        tvContentUpdown = holder.findViewById(R.id.tv_content_updown);
        tvContentHaveOrderNumber = holder.findViewById(R.id.tv_content_haveordernumber);
        tvContentOrderNumber = holder.findViewById(R.id.tv_content_ordernumber);
        llDesc = holder.findViewById(R.id.ll_desc);

        tvContentNumber.setText(position + 1 + ".    ");
        tvContentShsj.setText(orderInfoBean.getShrq() + "  " + orderInfoBean.getShsjd());
        tvContentStart.setText(orderInfoBean.getStart());
        tvContentEnd.setText(orderInfoBean.getEnd());
        tvContentPrice.setText(orderInfoBean.getPrice() + "元    ");
        tvContentUpdown.setText(orderInfoBean.getUpdown().equals("0") ? "没有上下货" : "上下货");
        tvContentHaveOrderNumber.setText(orderInfoBean.getOrderHavenumber().equals("0") ? "无回单" : "有回单");
        tvContentOrderNumber.setText(TextUtils.isEmpty(orderInfoBean.getOrderNumber()) ? "" : "    单号：" + orderInfoBean.getOrderNumber());

        if (TextUtils.isEmpty(orderInfoBean.getDesc())) {
            llDesc.setVisibility(View.GONE);
        } else {
            llDesc.setVisibility(View.VISIBLE);
            tvContentDesc.setText(orderInfoBean.getDesc());
        }

    }
}
