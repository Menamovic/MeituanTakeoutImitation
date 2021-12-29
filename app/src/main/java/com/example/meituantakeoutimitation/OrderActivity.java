package com.example.meituantakeoutimitation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class OrderActivity extends AppCompatActivity {

    ShoppingCartActivity shoppingCarActivity;
    SparseArray<GoodsItem> shoppingCar;
    String[] names;
    double[] prices;
    int[] numbs;
    private SelectGoods selectGoods;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_order);

        //实例化购物车对象
        selectGoods = new SelectGoods();
        Log.d("selectGoods.size()", Integer.toString(selectGoods.getShoppingCar().size()));
        shoppingCar = selectGoods.getShoppingCar();

        // 订单物品名称
        names = new String[shoppingCar.size()];

        // 订单物品数量
        numbs = new int[shoppingCar.size()];

        // 订单物品价格
        prices = new double[shoppingCar.size()];

        Log.d("shoppingCar.size()", Integer.toString(shoppingCar.size()));
        for (int i = 0; i < shoppingCar.size(); i++) {
            GoodsItem item = shoppingCar.valueAt(i);
            names[i] = item.name;
            Log.d("names", names[i]);
            numbs[i] = item.count;
            prices[i] = item.price;
        }

        // 初始化标题栏
        initTitle();

        // 填充ListView
        mListView = (ListView) findViewById(R.id.order_list_view);
        mListView.setAdapter(new MyBaseAdapter());
    }

    private void initTitle() {
        View view = (View) findViewById(R.id.order_title);
        TextView tv = (TextView) view.findViewById(R.id.tv_title);
        tv.setText("订 单");
        ImageButton ibBack = (ImageButton) view.findViewById(R.id.ib_title_back);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderActivity.this, ShoppingCartActivity.class);
                startActivity(intent);
                finish();
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
            return names[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view=View.inflate(OrderActivity.this,R.layout.order_items,null);
            ImageView orderItemImage = (ImageView) view.findViewById(R.id.iv_order_item_image);
            TextView orderItemName = (TextView) view.findViewById(R.id.tv_order_item_name);
            TextView orderItemNum = (TextView) view.findViewById(R.id.tv_order_item_num);
            TextView orderItemPrice = (TextView) view.findViewById(R.id.tv_order_item_price);
            //组件一拿到，开始组装
            //imageView.setBackgroundResource(icons[position]);
            orderItemName.setText(names[position]);
            orderItemNum.setText("X" + Integer.toString(numbs[position]));
            orderItemPrice.setText("￥" + Double.toString(prices[position]));
            //组装玩开始返回
            return view;
        }
    }
}
