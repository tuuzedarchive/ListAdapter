package com.tuuzed.androidx.list.preference.interfaces;

import androidx.annotation.NonNull;

import java.util.List;

public interface ItemsLoaderFunction<T> {

    void invoke(@NonNull Callback<T> callback);

    interface Callback<T> {
        void invoke(@NonNull List<T> items);
    }

}
