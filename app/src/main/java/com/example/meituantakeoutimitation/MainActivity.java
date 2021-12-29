package com.example.meituantakeoutimitation;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
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
import org.w3c.dom.Text;
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

    // 标题栏
    View mainTitle;

    // 主标题栏待隐藏返回按钮
    ImageButton needHide;

    // 标题
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // 获取控件信息
        banner = (Banner) findViewById(R.id.banner);
        indicator = (RoundLinesIndicator) findViewById(R.id.indicator);
        mainTitle = (View) findViewById(R.id.main_title_bar);
        needHide = (ImageButton) mainTitle.findViewById(R.id.ib_title_back);
        title = (TextView) mainTitle.findViewById(R.id.tv_title);

        // 隐藏返回按钮
        needHide.setVisibility(View.INVISIBLE);

        // 设置标题
        title.setText("店铺");

        // 自定义图片适配器
        ImageAdapter adapter = new ImageAdapter(DataBean.getTestData3());

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
        setShopInfo(1);
        setShopInfo(2);
        setShopInfo(3);
        setShopInfo(4);
        setShopInfo(5);
        setShopInfo(6);
    }

    // 跳转到店铺详情界面
    public void onClickToDetail(View view) {
        Intent intent = new Intent(MainActivity.this, ShoppingCartActivity.class);
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
    public void setShopInfo(int id) {
        try {

            // 解析JSON文件
            JSONObject jsonObject = new JSONObject(getJson("shop.json"));
            JSONObject shop = jsonObject.getJSONObject("shop0" + id);
            String imageUrl = shop.getString("imageUrl0" + id);
            String shopName = shop.getString("name0" + id);
            String monthSellNum = shop.getString("monthSellNum0" + id);
            String averagePrice = shop.getString("averagePrice0" + id);
            String startSendingPrice = shop.getString("startSendingPrice0" + id);
            String inSendingPrice = shop.getString("inSendingPrice0" + id);
            String sendingTime = shop.getString("sendingTime0" + id);
            String distance = shop.getString("distance0" + id);
            String characteristic = shop.getString("characteristic0" + id);

            // 加载店铺图片
            View curShop = (View) findViewById(R.id.shop1);
            switch (id) {
                case 1 :
                    curShop = (View) findViewById(R.id.shop1);
                    break;
                case 2 :
                    curShop = (View) findViewById(R.id.shop2);
                    break;
                case 3:
                    curShop = (View) findViewById(R.id.shop3);
                    break;
                case 4 :
                    curShop = (View) findViewById(R.id.shop4);
                    break;
                case 5 :
                    curShop = (View) findViewById(R.id.shop5);
                    break;
                case 6 :
                    curShop = (View) findViewById(R.id.shop6);
                    break;
            }
            ImageView shop_ib = (ImageView) curShop.findViewById(R.id.shop_image_button);
            Glide.with(this).load(imageUrl).into(shop_ib);

            // 店铺名称
            Button shop_name = (Button) curShop.findViewById(R.id.shop_name_button);
            shop_name.setText(shopName);

            // 月售数量
            Button shop_sellNum = (Button) curShop.findViewById(R.id.sell_num_button);
            shop_sellNum.setText("月售数量" + monthSellNum);

            // 人均价格
            Button shop_averagePrice = (Button) curShop.findViewById(R.id.average_price_button);
            shop_averagePrice.setText("人均价格" + averagePrice);

            // 起送价格
            Button shop_startSendingPrice = (Button) curShop.findViewById(R.id.start_sending_price_button);
            shop_startSendingPrice.setText("起送价格" + startSendingPrice);

            // 配送费用
            Button shop_sendingPrice = (Button) curShop.findViewById(R.id.sending_price_button);
            if (inSendingPrice.equals("0")) {
                shop_sendingPrice.setText("免配送费");
            }
            else {
                shop_sendingPrice.setText("配送费用" + inSendingPrice);
            }

            // 配送时间
            Button shop_sendingTime = (Button) curShop.findViewById(R.id.sending_time_button);
            shop_sendingTime.setText(sendingTime);

            // 配送距离
            Button shop_distance = (Button) curShop.findViewById(R.id.distance_button);
            shop_distance.setText(distance);

            // 店铺特色
            TextView shop_characteristic = (TextView) curShop.findViewById(R.id.tv_characteristic);
            shop_characteristic.setText(characteristic);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}