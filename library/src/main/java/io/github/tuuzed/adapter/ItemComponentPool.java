package io.github.tuuzed.adapter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LYH on 2017/2/22.
 *
 * @author LYH
 */

class ItemComponentPool {
    private Map<String, ItemComponent> mItemComponentPool;

    ItemComponentPool() {
        mItemComponentPool = new HashMap<>();
    }

    ItemComponent getItemComponent(Class key) {
        return mItemComponentPool.get(key.getName());
    }

    void putItemComponent(Class key, ItemComponent itemComponent) {
        mItemComponentPool.put(key.getName(), itemComponent);
    }

}
