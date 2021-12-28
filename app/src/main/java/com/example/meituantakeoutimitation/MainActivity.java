package com.example.meituantakeoutimitation;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.meituantakeoutimitation.adapter.ImageAdapter;
import com.example.meituantakeoutimitation.bean.DataBean;

import com.google.android.material.snackbar.Snackbar;

import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.indicator.RoundLinesIndicator;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.util.LogUtils;
import com.youth.banner.config.IndicatorConfig;

import org.json.JSONObject;
import org.xutils.common.util.LogUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    // 广告轮播类对象
    Banner banner;

    // 广告轮播圆点
    RoundLinesIndicator indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // 获取控件信息
        banner = (Banner) findViewById(R.id.banner);
        indicator = (RoundLinesIndicator) findViewById(R.id.indicator);

        // 自定义图片适配器
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
        setShopsInfo();
    }

    // 跳转到店铺详情界面
    public void onClickToDetail(View view) {
        Intent intent = new Intent(MainActivity.this, ShopDetailActivity.class);
        startActivity(intent);
    }

    public String getJson(String fileName) {

        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {

            //获取assets资源管理器
            AssetManager assetManager = this.getApplicationContext().getAssets();

            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    // 加载店铺信息
    public void setShopsInfo() {
        try {

            // 解析JSON文件
            JSONObject jsonObject = new JSONObject(getJson("shop.json"));
            JSONObject shop01 = jsonObject.getJSONObject("shop01");
            String imageUrl = shop01.getString("imageUrl");
            String shopName = shop01.getString("name");
            String monthSellNum = shop01.getString("monthSellNum");
            String averagePrice = shop01.getString("averagePrice");
            String startSendingPrice = shop01.getString("startSendingPrice");
            String inSendingPrice = shop01.getString("inSendingPrice");
            String characteristic = shop01.getString("characteristic");

            // 加载店铺图片
            View shop1 = (View) findViewById(R.id.shop1);
            ImageView shop1_ib = (ImageView) shop1.findViewById(R.id.shop_image_button);
            Glide.with(this).load(imageUrl).into(shop1_ib);

            // 店铺名称
            Button shop1_name = (Button) shop1.findViewById(R.id.shop_name_button);
            shop1_name.setText(shopName);

            // 月售数量
            Button shop1_sellNum = (Button) shop1.findViewById(R.id.sell_num_button);
            shop1_sellNum.setText("月售数量" + monthSellNum);

            // 人均价格
            Button shop1_averagePrice = (Button) shop1.findViewById(R.id.average_price_button);
            shop1_averagePrice.setText("人均价格" + averagePrice);

            // 起送价格
            Button shop1_startSendingPrice = (Button) shop1.findViewById(R.id.start_sending_price_button);
            shop1_startSendingPrice.setText("起送价格" + startSendingPrice);

            // 配送费用
            Button shop1_sendingPrice = (Button) shop1.findViewById(R.id.sending_price_button);
            if (inSendingPrice.equals("0")) {
                shop1_sendingPrice.setText("免配送费");
            }
            else {
                shop1_sendingPrice.setText("配送费用" + inSendingPrice);
            }

            // 店铺特色
            TextView shop1_characteristic = (TextView) shop1.findViewById(R.id.tv_characteristic);
            shop1_characteristic.setText("店铺特色: " + characteristic);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}