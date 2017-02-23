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


    void onBindViewHolder(@NonNull VH holder, @NonNull T t);

    @NonNull
    VH onCreateViewHolder(@NonNull ViewGroup parent);

}
