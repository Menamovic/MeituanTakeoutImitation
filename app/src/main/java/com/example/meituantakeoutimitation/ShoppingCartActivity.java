package com.example.meituantakeoutimitation;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.flipboard.bottomsheet.BottomSheetLayout;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ShoppingCartActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView imgCart;
    private ViewGroup anim_mask_layout;
    private RecyclerView rvType,rvSelected;
    private TextView tvCount,tvCost,tvSubmit,tvTips;
    private BottomSheetLayout bottomSheetLayout;
    private View bottomSheet;

    private ArrayList<GoodsItem> dataList,typeList;
    private SparseArray<GoodsItem> selectedList;
    private SparseIntArray groupSelect;

    private GoodsAdapter myAdapter;
    private com.example.meituantakeoutimitation.SelectAdapter selectAdapter;

    private NumberFormat nf;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_car);

        nf = NumberFormat.getCurrencyInstance();
        nf.setMaximumFractionDigits(2);
        mHandler = new Handler(getMainLooper());
        dataList = GoodsItem.getGoodsList();
        selectedList = new SparseArray<>();

        // 初始化店铺详情标题栏
        initTitleBar();
        // 初始化店铺详情及背景图
        initShopDetailAndBackground();

        initView();
    }

    private void initTitleBar() {

        // 返回店铺主界面图标按钮
        View view = (View) findViewById(R.id.shopDetail_titleBar);
        ImageButton ibBackToMain = (ImageButton) view.findViewById(R.id.ib_title_back);
        ibBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShoppingCartActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // 标题
        TextView tvDetailTitle = (TextView) view.findViewById(R.id.tv_title);
        tvDetailTitle.setText("店铺详情");
    }

    private void initShopDetailAndBackground() {

        // 解析JSON数据
        try {
            JSONObject jsonObject = new JSONObject(getJson("shopDetail01.json"));
            String imageUrl = jsonObject.getString("detailImageUrl");
            String imageUrl2 = jsonObject.getString("detailImageUrl2");

            // 获取背景控件信息，并填充数据
            //ImageView detailAdImage = (ImageView) findViewById(R.id.detail_ad_image);
            //Log.d("imageUrl", imageUrl);
            //Glide.with(this).load(imageUrl).into(detailAdImage);
            ImageView detailShopImage = (ImageView) findViewById(R.id.iv_detail_shop_image);
            Glide.with(this).load(imageUrl2).into(detailShopImage);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initView() {
        tvCount = (TextView) findViewById(R.id.tvCount);
        tvCost = (TextView) findViewById(R.id.tvCost);
        tvTips = (TextView) findViewById(R.id.tvTips);
        tvSubmit  = (TextView) findViewById(R.id.tvSubmit);

        imgCart = (ImageView) findViewById(R.id.imgCart);
        anim_mask_layout = (RelativeLayout) findViewById(R.id.containerLayout);
        bottomSheetLayout = (BottomSheetLayout) findViewById(R.id.bottomSheetLayout);

        ListView listView = (ListView) findViewById(R.id.itemListView);

        myAdapter = new GoodsAdapter(dataList,this);
        listView.setAdapter(myAdapter);

        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 存储当前选择菜品
                SelectGoods selectGoods = new SelectGoods();
                for (int i = 0; i < selectedList.size(); i++) {
                    GoodsItem item = selectedList.valueAt(i);
                    Log.d("itemId", Integer.toString(item.id));
                    selectGoods.addShoppingCar(item);
                }

                Intent intent = new Intent(ShoppingCartActivity.this, OrderActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bottom:
                showBottomSheet();
                break;
            case R.id.clear:
                clearCart();
                break;
            case R.id.tvSubmit:
                Toast.makeText(ShoppingCartActivity.this, "结算", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    //添加商品
    public void add(GoodsItem item, boolean refreshGoodList) {
        GoodsItem temp = selectedList.get(item.id);
        if(temp==null){
            item.count=1;
            selectedList.append(item.id, item);
        }else{
            temp.count++;
        }
        Log.d("selectedSize", Integer.toString(selectedList.size()));
        update(refreshGoodList);
    }

    //移除商品
    public void remove(GoodsItem item,boolean refreshGoodList) {
        GoodsItem temp = selectedList.get(item.id);
        if(temp!=null) {
            if(temp.count<2) {
                selectedList.remove(item.id);
            }else{
                item.count--;
            }
        }
        update(refreshGoodList);
    }

    //刷新布局 总价、购买数量等
    private void update(boolean refreshGoodList) {
        int size = selectedList.size();
        int count =0;
        double cost = 0;
        for(int i=0;i<size;i++) {
            GoodsItem item = selectedList.valueAt(i);
            count += item.count;
            cost += item.count*item.price;
        }

        if (count < 1) {
            tvCount.setVisibility(View.GONE);
        } else {
            tvCount.setVisibility(View.VISIBLE);
        }

        tvCount.setText(String.valueOf(count));

        if (cost > 10) {
            tvTips.setVisibility(View.GONE);
            tvSubmit.setVisibility(View.VISIBLE);
        } else {
            tvSubmit.setVisibility(View.GONE);
            tvTips.setVisibility(View.VISIBLE);
        }

        tvCost.setText(nf.format(cost));

        if(myAdapter!=null && refreshGoodList) {
            myAdapter.notifyDataSetChanged();
        }
        if(selectAdapter!=null) {
            selectAdapter.notifyDataSetChanged();
        }
        if(bottomSheetLayout.isSheetShowing() && selectedList.size()<1) {
            bottomSheetLayout.dismissSheet();
        }
    }

    //清空购物车
    public void clearCart(){
        selectedList.clear();
        update(true);

    }

    //根据商品id获取当前商品的采购数量
    public int getSelectedItemCountById(int id){
        GoodsItem temp = selectedList.get(id);
        if(temp==null){
            return 0;
        }
        return temp.count;
    }

    private View createBottomSheetView() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_bottom_sheet,(ViewGroup) getWindow().getDecorView(),false);
        rvSelected = (RecyclerView) view.findViewById(R.id.selectRecyclerView);
        rvSelected.setLayoutManager(new LinearLayoutManager(this));
        TextView clear = (TextView) view.findViewById(R.id.clear);
        clear.setOnClickListener(this);
        selectAdapter = new com.example.meituantakeoutimitation.SelectAdapter(this,selectedList);
        rvSelected.setAdapter(selectAdapter);
        return view;
    }

    private void showBottomSheet() {
        if(bottomSheet==null) {
            bottomSheet = createBottomSheetView();
        }
        if(bottomSheetLayout.isSheetShowing()) {
            bottomSheetLayout.dismissSheet();
        }else {
            if(selectedList.size()!=0) {
                bottomSheetLayout.showWithSheetView(bottomSheet);
            }
        }
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
}
