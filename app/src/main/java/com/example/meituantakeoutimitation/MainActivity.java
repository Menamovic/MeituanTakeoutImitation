package com.example.meituantakeoutimitation;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    //@BindView(R.id.banner)
    Banner banner;
    //@BindView(R.id.indicator)
    RoundLinesIndicator indicator;
//    @BindView(R.id.swipeRefresh)
    //SwipeRefreshLayout refresh;

    private ListView mListView;
    // 店铺图标
    private int[] icons={R.drawable.jd, R.drawable.qq, R.drawable.qq_dizhu
            , R.drawable.sina, R.drawable.tmall, R.drawable.uc};
    // 店铺名称
    private String[] names={"京东商城", "QQ", "QQ斗地主", "新浪微博", "天猫", "UC浏览器"};
    // 月售数量
    private int[] sellNums={1, 2, 3, 4, 5, 6};
    // 人均价格
    private double[] averagePrices={1.0, 2.0, 3.0, 4.0, 5.0, 6.0};
    // 起送价格
    private double[] startSendingPrices={1.0, 2.0, 3.0, 4.0, 5.0, 6.0};
    // 配送费用
    private double[] sendingPrices={1.0, 2.0, 3.0, 4.0, 5.0, 6.0};
    // 店铺特色
    private String[] characteristic={"characteristic1", "characteristic2"
                    , "characteristic3", "characteristic4", "characteristic5"
                    , "characteristic6"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        banner = (Banner) findViewById(R.id.banner);
        indicator = (RoundLinesIndicator) findViewById(R.id.indicator);
        //refresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);

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
/*
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
*/
        banner.setAdapter(new ImageAdapter(DataBean.getTestData()));
        banner.setIndicator(new CircleIndicator(this));
        banner.setIndicatorGravity(IndicatorConfig.Direction.CENTER);

        mListView=(ListView)findViewById(R.id.shopList);
        mListView.setAdapter(new MyBaseAdapter());
    }

    class MyBaseAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return names.length;
        }

        @Override
        public Object getItem(int position) {
            return names [position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view=View.inflate(MainActivity.this
                                    , R.layout.shoplist_item, null);
//            TextView mTextView=(TextView) view.findViewById(R.id.tv_list);
//            ImageView imageView=(ImageView)view.findViewById(R.id.image);
            ImageButton imageButton = (ImageButton) view.findViewById(R.id.shop_image_button);
            Button btnName = (Button) view.findViewById(R.id.shop_name_button);
            Button btnSellNum = (Button) view.findViewById(R.id.sell_num_button);
            Button btnAveragePrice = (Button) view.findViewById(R.id.average_price_button);
            Button btnStartSendingPrice = (Button) view.findViewById(R.id.start_sending_price);
            Button btnSendingPrice = (Button) view.findViewById(R.id.sending_price);
            TextView tvCharacteristic = (TextView) view.findViewById(R.id.tv_characteristic);
            // 组装数据
//            mTextView.setText(names[position]);
//            imageView.setBackgroundResource(icons[position]);
            imageButton.setBackgroundResource(icons[position]);
            btnName.setText(names[position]);
            btnSellNum.setText(Integer.toString(sellNums[position]));
            btnAveragePrice.setText(Double.toString(averagePrices[position]));
            btnStartSendingPrice.setText(Double.toString(startSendingPrices[position]));
            btnSendingPrice.setText(Double.toString(sendingPrices[position]));
            tvCharacteristic.setText(characteristic[position]);
            // 组装完成后返回
            return view;
        }
    }

}