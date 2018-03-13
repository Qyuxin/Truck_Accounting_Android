package com.xin.hcjz.ui.adapter;

import android.content.Context;
import android.widget.TextView;

import com.xin.hcjz.R;
import com.xin.hcjz.bean.OrderInfoBean;
import com.xin.hcjz.ui.base.MyBaseAdapter;

import java.util.List;

/**
 * Created by Y on 2018/3/7.
 */

public class TjDataAdapter_NoUse extends MyBaseAdapter<OrderInfoBean> {

    private TextView tvContentNumber;
//    private TextView tvTitleCom;
    private TextView tvContentCom;
//    private TextView tvTitleShsj;
    private TextView tvContentShsj;
//    private TextView tvTitleStart;
    private TextView tvContentStart;
//    private TextView tvTitleEnd;
    private TextView tvContentEnd;
//    private TextView tvTitlePrice;
    private TextView tvContentPrice;
//    private TextView tvTitleDesc;
    private TextView tvContentDesc;
//    private TextView tvTitleUpdown;
    private TextView tvContentUpdown;
    private TextView tvContentHaveOrderNumber;
//    private TextView tvTitleOrderNumber;
    private TextView tvContentOrderNumber;

    public TjDataAdapter_NoUse(Context context, List<OrderInfoBean> list, int layout) {
        super(context, list, layout);
    }

    @Override
    public void convert(ViewHolder holder, OrderInfoBean orderInfoBean, int position) {
        tvContentNumber = holder.findViewById(R.id.tv_content_number);
//        tvTitleCom = holder.findViewById(R.id.tv_title_com);
        tvContentCom = holder.findViewById(R.id.tv_content_com);
//        tvTitleShsj = holder.findViewById(R.id.tv_title_shsj);
        tvContentShsj = holder.findViewById(R.id.tv_content_shsj);
//        tvTitleStart = holder.findViewById(R.id.tv_title_start);
        tvContentStart = holder.findViewById(R.id.tv_content_start);
//        tvTitleEnd = holder.findViewById(R.id.tv_title_end);
        tvContentEnd = holder.findViewById(R.id.tv_content_end);
//        tvTitlePrice = holder.findViewById(R.id.tv_title_price);
        tvContentPrice = holder.findViewById(R.id.tv_content_price);
//        tvTitleDesc = holder.findViewById(R.id.tv_title_desc);
        tvContentDesc = holder.findViewById(R.id.tv_content_desc);
//        tvTitleUpdown = holder.findViewById(R.id.tv_title_updown);
        tvContentUpdown = holder.findViewById(R.id.tv_content_updown);
        tvContentHaveOrderNumber = holder.findViewById(R.id.tv_content_haveordernumber);
//        tvTitleOrderNumber = holder.findViewById(R.id.tv_title_ordernumber);
        tvContentOrderNumber = holder.findViewById(R.id.tv_content_ordernumber);

        tvContentNumber.setText(position + 1 + "");
        tvContentCom.setText(orderInfoBean.getCom());
        tvContentShsj.setText(orderInfoBean.getShrq() + "  " + orderInfoBean.getShsjd());
        tvContentStart.setText(orderInfoBean.getStart());
        tvContentEnd.setText(orderInfoBean.getEnd());
        tvContentPrice.setText(orderInfoBean.getPrice());
        tvContentDesc.setText(orderInfoBean.getDesc());
        tvContentUpdown.setText(orderInfoBean.getUpdown().equals("0")?"无":"上下货");
        tvContentHaveOrderNumber.setText(orderInfoBean.getOrderHavenumber().equals("0")?"无":"有");
        tvContentOrderNumber.setText(orderInfoBean.getOrderNumber());

    }
}
