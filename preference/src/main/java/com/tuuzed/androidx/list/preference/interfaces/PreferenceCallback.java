package com.tuuzed.androidx.list.preference.interfaces;

public interface PreferenceCallback<T> {
    boolean invoke(T t, int position);
}
