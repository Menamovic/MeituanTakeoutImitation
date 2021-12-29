package com.example.meituantakeoutimitation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;


public class GoodsAdapter extends BaseAdapter{

    private ArrayList<com.example.meituantakeoutimitation.GoodsItem> dataList;
    private com.example.meituantakeoutimitation.ShoppingCartActivity mContext;
    private NumberFormat nf;
    private LayoutInflater mInflater;

    public GoodsAdapter(ArrayList<com.example.meituantakeoutimitation.GoodsItem> dataList, com.example.meituantakeoutimitation.ShoppingCartActivity mContext) {
        this.dataList = dataList;
        this.mContext = mContext;
        nf = NumberFormat.getCurrencyInstance();
        nf.setMaximumFractionDigits(2);
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        if(dataList==null){
            return 0;
        }
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemViewHolder holder = null;
        if(convertView==null){

            // 获得菜品条目样式
            convertView = mInflater.inflate(R.layout.shopdetail_item, parent, false);

            holder = new ItemViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ItemViewHolder) convertView.getTag();
        }
        com.example.meituantakeoutimitation.GoodsItem item = dataList.get(position);
        holder.bindData(item);
        return convertView;
    }

    class ItemViewHolder implements View.OnClickListener {

        private TextView name, price, addToShoppingCar, rank, detailMonthSellNum, detailPraiseDegree;
        private com.example.meituantakeoutimitation.GoodsItem item;

        public ItemViewHolder(View itemView) {
            name = (TextView) itemView.findViewById(R.id.tv_detail_name);
            price = (TextView) itemView.findViewById(R.id.tv_detail_price);
            addToShoppingCar = (Button) itemView.findViewById(R.id.btn_add_to_shopping_car);
            rank = (TextView) itemView.findViewById(R.id.tv_detail_rank);
            detailMonthSellNum = (TextView) itemView.findViewById(R.id.tv_detail_month_sell_num);
            detailPraiseDegree = (TextView) itemView.findViewById(R.id.tv_detail_praise_degree);

            addToShoppingCar.setOnClickListener(this);
        }

        public void bindData(com.example.meituantakeoutimitation.GoodsItem item){

            this.item = item;
            name.setText(item.name);
            price.setText("￥" + Double.toString(item.price));
            rank.setText("门店销量第" + Integer.toString(item.rank) + "名");
            detailMonthSellNum.setText("月售" + Integer.toString(item.monthSellNum));
            detailPraiseDegree.setText("好评度" + Double.toString(item.praiseDegree));

        }

        @Override
        public void onClick(View v) {
            com.example.meituantakeoutimitation.ShoppingCartActivity activity = mContext;
            if (v.getId() == R.id.btn_add_to_shopping_car) {
                activity.add(item, false);
            }
        }
    }
}
