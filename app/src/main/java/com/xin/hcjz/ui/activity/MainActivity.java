package com.xin.hcjz.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.xin.hcjz.R;
import com.xin.hcjz.ui.base.BaseActivity;
import com.xin.hcjz.ui.fragment.Frag_Jz;
import com.xin.hcjz.ui.fragment.Frag_Mine;
import com.xin.hcjz.ui.fragment.Frag_Tj;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Y on 2017/3/23.
 */
public class MainActivity extends BaseActivity{


    @BindView(R.id.top_tv_title)
    TextView topTvTitle;
    @BindView(R.id.top_ib_left)
    ImageButton topIbLeft;
    @BindView(R.id.top_ib_right)
    ImageButton topIbRight;
    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView(R.id.ib_jz)
    ImageButton ibJz;
    @BindView(R.id.tv_jz)
    TextView tvJz;
    @BindView(R.id.ll_bottom_jz)
    LinearLayout llBottomJz;
    @BindView(R.id.ib_tj)
    ImageButton ibTj;
    @BindView(R.id.tv_tj)
    TextView tvTj;
    @BindView(R.id.ll_bottom_tj)
    LinearLayout llBottomTj;
    @BindView(R.id.ib_mine)
    ImageButton ibMine;
    @BindView(R.id.tv_mine)
    TextView tvMine;
    @BindView(R.id.ll_bottom_mine)
    LinearLayout llBottomMine;
    
    //初始化3个Fragment
    private Fragment tab01;
    private Fragment tab02;
    private Fragment tab03;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSelect(0);//默认显示
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }


    @Override
    protected void initRootLayout() {
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }


    /*
     * 将图片设置为亮色的；切换显示内容的fragment
     * */
    private void setSelect(int i) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();//创建一个事务
        hideFragment(transaction);//我们先把所有的Fragment隐藏了，然后下面再开始处理具体要显示的Fragment
        switch (i) {
            case 0:
                if (tab01 == null) {
                    tab01 = new Frag_Jz();
                /*
                 * 将Fragment添加到活动中，public abstract FragmentTransaction add (int containerViewId, Fragment fragment)
				*containerViewId即为Optional identifier of the container this fragment is to be placed in. If 0, it will not be placed in a container.
				 * */
                    transaction.add(R.id.fl_content, tab01);//将Fragment添加到Activity中
                } else {
                    transaction.show(tab01);
                }
                //设置按下图片及文字
                ibJz.setImageResource(R.drawable.bottom_index_icon_checked);
                tvJz.setTextColor(getResources().getColor(R.color.mainBlue));
                break;
            case 1:
                if (tab02 == null) {
                    tab02 = new Frag_Tj();
                    transaction.add(R.id.fl_content, tab02);
                } else {
                    transaction.show(tab02);
                }
                ibTj.setImageResource(R.drawable.bottom_alarm_icon_checked);
                tvTj.setTextColor(getResources().getColor(R.color.mainBlue));
                break;
            case 2:
                if (tab03 == null) {
                    tab03 = new Frag_Mine();
                    transaction.add(R.id.fl_content, tab03);
                } else {
                    transaction.show(tab03);
                }
                ibMine.setImageResource(R.drawable.bottom_mine_icon_checked);
                tvMine.setTextColor(getResources().getColor(R.color.mainBlue));
                break;
            
            default:
                break;
        }
        transaction.commit();//提交事务
    }

    /*
     * 隐藏所有的Fragment
     * */
    private void hideFragment(FragmentTransaction transaction) {
        if (tab01 != null) {
            transaction.hide(tab01);
        }
        if (tab02 != null) {
            transaction.hide(tab02);
        }
        if (tab03 != null) {
            transaction.hide(tab03);
        }

    }

    private void resetBottomUI() {
        //初始化图片
        ibJz.setImageResource(R.drawable.bottom_index_icon_unchecked);
        ibTj.setImageResource(R.drawable.bottom_alarm_icon_unchecked);
        ibMine.setImageResource(R.drawable.bottom_mine_icon_unchecked);

        tvJz.setTextColor(Color.parseColor("#555555"));
        tvTj.setTextColor(Color.parseColor("#555555"));
        tvMine.setTextColor(Color.parseColor("#555555"));
    }

    @OnClick({R.id.ib_jz, R.id.tv_jz, R.id.ll_bottom_jz, R.id.ib_tj, R.id.tv_tj, R.id.ll_bottom_tj, R.id.ib_mine, R.id.tv_mine, R.id.ll_bottom_mine})
    public void onViewClicked(View view) {
        resetBottomUI();
        switch (view.getId()) {
            case R.id.ib_jz:
            case R.id.tv_jz:
            case R.id.ll_bottom_jz:
                setSelect(0);
                break;
            case R.id.ib_tj:
            case R.id.tv_tj:
            case R.id.ll_bottom_tj:
                setSelect(1);
                break;
            case R.id.ib_mine:
            case R.id.tv_mine:
            case R.id.ll_bottom_mine:
                setSelect(2);
                break;
        }
    }
}