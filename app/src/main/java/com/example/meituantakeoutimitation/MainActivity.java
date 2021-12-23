package com.example.meituantakeoutimitation;

import android.os.Bundle;
import android.os.Handler;

import com.example.meituantakeoutimitation.adapter.ImageAdapter;
import com.example.meituantakeoutimitation.bean.DataBean;

import com.google.android.material.snackbar.Snackbar;

import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.indicator.RoundLinesIndicator;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.util.LogUtils;
import com.youth.banner.config.IndicatorConfig;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    //@BindView(R.id.banner)
    Banner banner;
    //@BindView(R.id.indicator)
    RoundLinesIndicator indicator;
//    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        banner = (Banner) findViewById(R.id.banner);
        indicator = (RoundLinesIndicator) findViewById(R.id.indicator);
        refresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);

        //自定义的图片适配器，也可以使用默认的BannerImageAdapter
        ImageAdapter adapter = new ImageAdapter(DataBean.getTestData());

        banner.setAdapter(adapter)
//              .setCurrentItem(0,false)
                .addBannerLifecycleObserver(this)//添加生命周期观察者
                .setIndicator(new CircleIndicator(this))//设置指示器
                .setOnBannerListener((data, position) -> {
                    Snackbar.make(banner, ((DataBean) data).title, Snackbar.LENGTH_SHORT).show();
                    LogUtils.d("position：" + position);
                });

        //和下拉刷新配套使用
        refresh.setOnRefreshListener(() -> {
            //模拟网络请求需要3秒，请求完成，设置setRefreshing 为false
            new Handler().postDelayed(() -> {
                refresh.setRefreshing(false);

                //给banner重新设置数据
                banner.setDatas(DataBean.getTestData());

                //对setDatas()方法不满意？你可以自己在adapter控制数据，参考setDatas()的实现修改
//                adapter.updateData(DataBean.getTestData());
//                banner.setCurrentItem(banner.getStartPosition(), false);
//                banner.setIndicatorPageChange();

            }, 3000);
        });

        refresh.setEnabled(true);
        banner.setAdapter(new ImageAdapter(DataBean.getTestData()));
        banner.setIndicator(new CircleIndicator(this));
        banner.setIndicatorGravity(IndicatorConfig.Direction.CENTER);
    }
}