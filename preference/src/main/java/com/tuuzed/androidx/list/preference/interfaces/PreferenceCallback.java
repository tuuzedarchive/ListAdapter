package com.tuuzed.androidx.list.preference.interfaces;

public interface PreferenceCallback<P> {
    boolean invoke(P preference, int position);
}
