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

        //自定义图片适配器
        ImageAdapter adapter = new ImageAdapter(DataBean.getTestData());

        // 配置轮播广告
        banner.setAdapter(adapter)
                .addBannerLifecycleObserver(this)//添加生命周期观察者
                .setIndicator(new CircleIndicator(this))//设置指示器
                .setOnBannerListener((data, position) -> {
                    Snackbar.make(banner, ((DataBean) data).title, Snackbar.LENGTH_SHORT).show();
                    LogUtils.d("position：" + position);
                });
        banner.setAdapter(new ImageAdapter(DataBean.getTestData()));
        banner.setIndicator(new CircleIndicator(this));
        banner.setIndicatorGravity(IndicatorConfig.Direction.CENTER);

        // 加载店铺信息
        View shop1 = (View) findViewById(R.id.shop1);
        ImageButton shop1_ib = (ImageButton) shop1.findViewById(R.id.shop_image_button);
        shop1_ib.setBackgroundResource(icons[0]);
        Button shop1_name = (Button) shop1.findViewById(R.id.shop_name_button);
        shop1_name.setText(names[0]);
        Button shop1_sellNum = (Button) shop1.findViewById(R.id.sell_num_button);
        shop1_sellNum.setText(Integer.toString(sellNums[0]));
        Button shop1_averagePrice = (Button) shop1.findViewById(R.id.average_price_button);
        shop1_averagePrice.setText(Double.toString(averagePrices[0]));
        Button shop1_startSendingPrice = (Button) shop1.findViewById(R.id.start_sending_price_button);
        shop1_startSendingPrice.setText(Double.toString(startSendingPrices[0]));
        Button shop1_sendingPrice = (Button) shop1.findViewById(R.id.sending_price_button);
        shop1_sendingPrice.setText(Double.toString(sendingPrices[0]));
        TextView shop1_characteristic = (TextView) shop1.findViewById(R.id.tv_characteristic);
        shop1_characteristic.setText(characteristic[0]);

        View shop2 = (View) findViewById(R.id.shop2);
        ImageButton shop2_ib = (ImageButton) shop2.findViewById(R.id.shop_image_button);
        shop2_ib.setBackgroundResource(icons[1]);
        Button shop2_name = (Button) shop2.findViewById(R.id.shop_name_button);
        shop2_name.setText(names[1]);
        Button shop2_sellNum = (Button) shop2.findViewById(R.id.sell_num_button);
        shop2_sellNum.setText(Integer.toString(sellNums[1]));
        Button shop2_averagePrice = (Button) shop2.findViewById(R.id.average_price_button);
        shop2_averagePrice.setText(Double.toString(averagePrices[1]));
        Button shop2_startSendingPrice = (Button) shop2.findViewById(R.id.start_sending_price_button);
        shop2_startSendingPrice.setText(Double.toString(startSendingPrices[1]));
        Button shop2_sendingPrice = (Button) shop2.findViewById(R.id.sending_price_button);
        shop2_sendingPrice.setText(Double.toString(sendingPrices[1]));
        TextView shop2_characteristic = (TextView) shop2.findViewById(R.id.tv_characteristic);
        shop2_characteristic.setText(characteristic[1]);

        View shop3 = (View) findViewById(R.id.shop3);
        ImageButton shop3_ib = (ImageButton) shop3.findViewById(R.id.shop_image_button);
        shop3_ib.setBackgroundResource(icons[2]);
        Button shop3_name = (Button) shop3.findViewById(R.id.shop_name_button);
        shop3_name.setText(names[2]);
        Button shop3_sellNum = (Button) shop3.findViewById(R.id.sell_num_button);
        shop3_sellNum.setText(Integer.toString(sellNums[2]));
        Button shop3_averagePrice = (Button) shop3.findViewById(R.id.average_price_button);
        shop3_averagePrice.setText(Double.toString(averagePrices[2]));
        Button shop3_startSendingPrice = (Button) shop3.findViewById(R.id.start_sending_price_button);
        shop3_startSendingPrice.setText(Double.toString(startSendingPrices[2]));
        Button shop3_sendingPrice = (Button) shop3.findViewById(R.id.sending_price_button);
        shop3_sendingPrice.setText(Double.toString(sendingPrices[2]));
        TextView shop3_characteristic = (TextView) shop3.findViewById(R.id.tv_characteristic);
        shop3_characteristic.setText(characteristic[2]);

        View shop4 = (View) findViewById(R.id.shop4);
        ImageButton shop4_ib = (ImageButton) shop4.findViewById(R.id.shop_image_button);
        shop4_ib.setBackgroundResource(icons[3]);
        Button shop4_name = (Button) shop4.findViewById(R.id.shop_name_button);
        shop4_name.setText(names[3]);
        Button shop4_sellNum = (Button) shop4.findViewById(R.id.sell_num_button);
        shop4_sellNum.setText(Integer.toString(sellNums[3]));
        Button shop4_averagePrice = (Button) shop4.findViewById(R.id.average_price_button);
        shop4_averagePrice.setText(Double.toString(averagePrices[3]));
        Button shop4_startSendingPrice = (Button) shop4.findViewById(R.id.start_sending_price_button);
        shop4_startSendingPrice.setText(Double.toString(startSendingPrices[3]));
        Button shop4_sendingPrice = (Button) shop4.findViewById(R.id.sending_price_button);
        shop4_sendingPrice.setText(Double.toString(sendingPrices[3]));
        TextView shop4_characteristic = (TextView) shop4.findViewById(R.id.tv_characteristic);
        shop4_characteristic.setText(characteristic[3]);

        View shop5 = (View) findViewById(R.id.shop5);
        ImageButton shop5_ib = (ImageButton) shop5.findViewById(R.id.shop_image_button);
        shop5_ib.setBackgroundResource(icons[4]);
        Button shop5_name = (Button) shop5.findViewById(R.id.shop_name_button);
        shop5_name.setText(names[4]);
        Button shop5_sellNum = (Button) shop5.findViewById(R.id.sell_num_button);
        shop5_sellNum.setText(Integer.toString(sellNums[4]));
        Button shop5_averagePrice = (Button) shop5.findViewById(R.id.average_price_button);
        shop5_averagePrice.setText(Double.toString(averagePrices[4]));
        Button shop5_startSendingPrice = (Button) shop5.findViewById(R.id.start_sending_price_button);
        shop5_startSendingPrice.setText(Double.toString(startSendingPrices[4]));
        Button shop5_sendingPrice = (Button) shop5.findViewById(R.id.sending_price_button);
        shop5_sendingPrice.setText(Double.toString(sendingPrices[4]));
        TextView shop5_characteristic = (TextView) shop5.findViewById(R.id.tv_characteristic);
        shop5_characteristic.setText(characteristic[4]);

        View shop6 = (View) findViewById(R.id.shop6);
        ImageButton shop6_ib = (ImageButton) shop6.findViewById(R.id.shop_image_button);
        shop6_ib.setBackgroundResource(icons[5]);
        Button shop6_name = (Button) shop6.findViewById(R.id.shop_name_button);
        shop6_name.setText(names[5]);
        Button shop6_sellNum = (Button) shop6.findViewById(R.id.sell_num_button);
        shop6_sellNum.setText(Integer.toString(sellNums[5]));
        Button shop6_averagePrice = (Button) shop6.findViewById(R.id.average_price_button);
        shop6_averagePrice.setText(Double.toString(averagePrices[5]));
        Button shop6_startSendingPrice = (Button) shop6.findViewById(R.id.start_sending_price_button);
        shop6_startSendingPrice.setText(Double.toString(startSendingPrices[5]));
        Button shop6_sendingPrice = (Button) shop6.findViewById(R.id.sending_price_button);
        shop6_sendingPrice.setText(Double.toString(sendingPrices[5]));
        TextView shop6_characteristic = (TextView) shop6.findViewById(R.id.tv_characteristic);
        shop6_characteristic.setText(characteristic[5]);
    }
}