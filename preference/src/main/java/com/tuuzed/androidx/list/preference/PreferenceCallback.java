package com.tuuzed.androidx.list.preference;

public interface PreferenceCallback<T> {
    boolean invoke(T t, int position);
}
