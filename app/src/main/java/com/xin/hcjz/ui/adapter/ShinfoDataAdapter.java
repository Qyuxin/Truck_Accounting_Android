package com.xin.hcjz.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xin.hcjz.R;
import com.xin.hcjz.bean.OrderInfoBean;
import com.xin.hcjz.bean.ShinfoBean;
import com.xin.hcjz.ui.activity.ShowTjData;
import com.xin.hcjz.ui.base.MyBaseAdapter;

import java.util.List;

/**
 * Created by Y on 2018/3/7.
 */

public class ShinfoDataAdapter extends MyBaseAdapter<ShinfoBean> {

    private TextView tvContentNumber;
    private TextView tvContentCom;
    private TextView tvContentStart;
    private TextView tvContentEnd;
    private TextView tvContentPrice;
    private TextView tvContentUpdown;
    private TextView tvContentHaveOrderNumber;
    private LinearLayout llDesc;

    public ShinfoDataAdapter(Context context, List<ShinfoBean> list, int layout) {
        super(context, list, layout);
    }

    @Override
    public void convert(ViewHolder holder, ShinfoBean bean, int position) {

        tvContentNumber = holder.findViewById(R.id.tv_content_number);
        tvContentCom = holder.findViewById(R.id.tv_content_com);
        tvContentStart = holder.findViewById(R.id.tv_content_start);
        tvContentEnd = holder.findViewById(R.id.tv_content_end);
        tvContentPrice = holder.findViewById(R.id.tv_content_price);
        tvContentUpdown = holder.findViewById(R.id.tv_content_updown);
        tvContentHaveOrderNumber = holder.findViewById(R.id.tv_content_haveordernumber);
        llDesc = holder.findViewById(R.id.ll_desc);

        tvContentNumber.setText(position + 1 + ".    ");
        tvContentCom.setText(bean.getCom());
        tvContentStart.setText(bean.getStart());
        tvContentEnd.setText(bean.getEnd());
        tvContentPrice.setText(bean.getPrice() + "元    ");
        tvContentUpdown.setText(bean.getUpdown().equals("0") ? "没有上下货" : "上下货");
        tvContentHaveOrderNumber.setText(bean.getHavenumber().equals("0") ? "无回单" : "有回单");


    }
}
