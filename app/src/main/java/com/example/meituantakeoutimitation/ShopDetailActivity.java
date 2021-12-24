package com.example.meituantakeoutimitation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShopDetailActivity extends AppCompatActivity {

    private ListView mListView;
    // 菜品图标
    private int[] icons={R.drawable.jd, R.drawable.qq, R.drawable.qq_dizhu
            , R.drawable.sina, R.drawable.tmall, R.drawable.uc};
    // 菜品名称
    private String[] names={"京东商城", "QQ", "QQ斗地主", "新浪微博", "天猫", "UC浏览器"};
    // 销量排名
    private int[] sellRanks={1, 2, 3, 4, 5, 6};
    // 月售数量
    private int[] sellNums={1, 2, 3, 4, 5, 6};
    // 好评度
    private double[] praiseDegrees={1.0, 2.0, 3.0, 4.0, 5.0, 6.0};
    // 菜品价格
    private double[] prices={1.0, 2.0, 3.0, 4.0, 5.0, 6.0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopdetail);
        mListView=(ListView) findViewById(R.id.lv_shopDetail);
        mListView.setAdapter(new MyBaseAdapter());

        View view = (View) findViewById(R.id.shopDetail_titleBar);
        ImageButton backImageButton = (ImageButton) view.findViewById(R.id.ib_title_back);
        backImageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick (View view) {
                Intent intent = new Intent(ShopDetailActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
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
            View view=View.inflate(ShopDetailActivity.this, R.layout.shopdetail_item, null);
            // 获得组件
            ImageButton imageButton = (ImageButton) view.findViewById(R.id.detail_image_button);
            Button nameButton = (Button) view.findViewById(R.id.detail_name_button);
            TextView rankTextView = (TextView) view.findViewById(R.id.tv_detail_rank);
            TextView sellNumTextView = (TextView) view.findViewById(R.id.detail_sell_num);
            TextView praiseDegreeTextView = (TextView) view.findViewById(R.id.detail_praise_degree);
            TextView detailPriceTextView = (TextView) view.findViewById(R.id.detail_price);
            // 组装数据
            imageButton.setBackgroundResource(icons[position]);
            nameButton.setText(names[position]);
            rankTextView.setText(Integer.toString(sellRanks[position]));
            sellNumTextView.setText(Integer.toString(sellNums[position]));
            praiseDegreeTextView.setText(Double.toString(praiseDegrees[position]));
            detailPriceTextView.setText(Double.toString(prices[position]));
            // 返回视图
            return view;
        }
    }
}
