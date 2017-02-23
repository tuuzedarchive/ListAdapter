package io.github.tuuzed.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by LYH on 2017/2/22.
 *
 * @author LYH
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Items mItems;
    private ItemComponentPool mItemComponentPool;


    public RecyclerViewAdapter(@NonNull Items items) {
        this.mItems = items;
        mItemComponentPool = new ItemComponentPool();
    }

    public void register(@NonNull Class clazz, @NonNull ItemComponent itemView) {
        mItemComponentPool.putItemComponent(clazz, itemView);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Object item = mItems.get(viewType);
        ItemComponent itemComponent = mItemComponentPool.getItemComponent(item.getClass());
        if (itemComponent != null) {
            return itemComponent.onCreateViewHolder(parent);
        } else {
            throw new ItemComponentNotFoundException(item.getClass());
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Object item = mItems.get(position);
        ItemComponent itemComponent = mItemComponentPool.getItemComponent(item.getClass());
        if (itemComponent != null) {
            itemComponent.onBindViewHolder(holder, item);
        } else {
            throw new ItemComponentNotFoundException(item.getClass());
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
