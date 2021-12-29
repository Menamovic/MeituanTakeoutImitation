package com.example.meituantakeoutimitation;

import android.content.Context;
import android.content.res.AssetManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class GoodsItem extends ShoppingCartActivity {

    public int id;
    public String name;
    public double price;
    public int rank;
    public int monthSellNum;
    public double praiseDegree;
    public int count;

    public GoodsItem(int id, String name, double price, int rank, int monthSellNum, double praiseDegree) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.rank = rank;
        this.monthSellNum = monthSellNum;
        this.praiseDegree = praiseDegree;
    }

    private static ArrayList<GoodsItem> goodsList;
//    private static ArrayList<GoodsItem> typeList;

    // 根据店铺编号，并通过解析json文件获得菜品信息
    private static void initData(){
        goodsList = new ArrayList<>();
        GoodsItem item = null, item2 = null, item3 = null;
//        }
        try {

            // 商品1
            int id = 1;
            String name = "香酥鸡柳";
            double price = 6.0;
            int rank = 1;
            int monthSellNum = 160;
            double praiseDegree = 0.96;
            item = new GoodsItem(id, name, price, rank, monthSellNum, praiseDegree);
            goodsList.add(item);

            // 商品2
            int id2 = 2;
            String name2 = "脆骨烤肠";
            double price2 = 3.0;
            int rank2 = 2;
            int monthSellNum2 = 147;
            double praiseDegree2 = 0.93;
            item2 = new GoodsItem(id2, name2, price2, rank2, monthSellNum2, praiseDegree2);
            goodsList.add(item2);

            // 商品3
            int id3 = 3;
            String name3 = "扬州炒饭";
            double price3 = 6.0;
            int rank3 = 3;
            int monthSellNum3 = 160;
            double praiseDegree3 = 0.96;
            item3 = new GoodsItem(id3, name3, price3, rank3, monthSellNum3, praiseDegree3);
            goodsList.add(item3);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<GoodsItem> getGoodsList() {
        if(goodsList==null){
            initData();
        }
        return goodsList;
    }
}
