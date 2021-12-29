package com.example.meituantakeoutimitation;

import android.util.SparseArray;

public class SelectGoods {
    private static SparseArray<GoodsItem> shoppingCar = new SparseArray<>();

    public SparseArray<GoodsItem> getShoppingCar() {
        return this.shoppingCar;
    }

    public void addShoppingCar(GoodsItem item) {
        shoppingCar.append(item.id, item);
    }
}
