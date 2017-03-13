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

    public void register(@NonNull Class clazz, @NonNull ItemComponent itemView) {
        mItemComponentPool.putItemComponent(clazz, itemView);
    }

    public RecyclerViewAdapter(@NonNull Items items) {
        this.mItems = items;
        mItemComponentPool = new ItemComponentPool();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemComponent itemComponent = mItemComponentPool.getItemComponent(mItems.get(viewType).getClass());
        if (itemComponent != null) {
            return itemComponent.onCreateViewHolder(parent);
        }
        throw new NotFindItemComponent();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Object item = mItems.get(position);
        ItemComponent itemComponent = mItemComponentPool.getItemComponent(item.getClass());
        if (itemComponent != null) {
            itemComponent.onBindViewHolder(holder, item, position);
        } else {
            throw new NotFindItemComponent();
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
