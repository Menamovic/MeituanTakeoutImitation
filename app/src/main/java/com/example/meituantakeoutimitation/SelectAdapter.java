package com.example.meituantakeoutimitation;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.NumberFormat;

import androidx.recyclerview.widget.RecyclerView;


public class SelectAdapter extends RecyclerView.Adapter<SelectAdapter.ViewHolder>{
    private ShoppingCartActivity activity;
    private SparseArray<GoodsItem> dataList;
    private NumberFormat nf;
    private LayoutInflater mInflater;

    public SelectAdapter(ShoppingCartActivity activity, SparseArray<GoodsItem> dataList) {
        this.activity = activity;
        this.dataList = dataList;
        nf = NumberFormat.getCurrencyInstance();
        nf.setMaximumFractionDigits(2);
        mInflater = LayoutInflater.from(activity);
    }

    // 实例化layout布局文件
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_selected_goods,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GoodsItem item = dataList.valueAt(position);
        holder.bindData(item);
    }

    @Override
    public int getItemCount() {
        if(dataList==null) {
            return 0;
        }
        return dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private GoodsItem item;
        private TextView tvCost,tvCount,tvAdd,tvMinus,tvName;

        public ViewHolder(View itemView) {
            super(itemView);

            // 获取控件信息
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvCost = (TextView) itemView.findViewById(R.id.tvCost);
            tvCount = (TextView) itemView.findViewById(R.id.count);
            tvMinus = (TextView) itemView.findViewById(R.id.tvMinus);
            tvAdd = (TextView) itemView.findViewById(R.id.tvAdd);
            tvMinus.setOnClickListener(this);
            tvAdd.setOnClickListener(this);
        }

        // 购物车添加、删除菜品事件
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tvAdd:
                    activity.add(item, true);
                    break;
                case R.id.tvMinus:
                    activity.remove(item, true);
                    break;
                default:
                    break;
            }
        }

        // 获取菜品的信息，同步到购物车
        public void bindData(GoodsItem item){
            this.item = item;
            tvName.setText(item.name);
            tvCost.setText(nf.format(item.count*item.price));
            tvCount.setText(String.valueOf(item.count));
        }
    }
}
