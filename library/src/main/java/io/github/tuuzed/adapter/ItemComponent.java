package io.github.tuuzed.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by LYH on 2017/2/22.
 *
 * @author LYH
 */

public interface ItemComponent<T, VH extends RecyclerView.ViewHolder> {

    void onBindViewHolder(VH holder, T item, int position);

    @NonNull
    VH onCreateViewHolder(ViewGroup parent);

}
