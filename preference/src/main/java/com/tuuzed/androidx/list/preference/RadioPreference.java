package com.tuuzed.androidx.list.preference;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.tuuzed.androidx.list.adapter.ListAdapter;
import com.tuuzed.androidx.list.preference.internal.CompoundButtonPreference;

public class RadioPreference extends CompoundButtonPreference<RadioPreference> {

    public RadioPreference(@NonNull String title, @NonNull String summary) {
        super(title, summary);
    }

    public static void bindTo(@NonNull ListAdapter listAdapter) {
        listAdapter.bind(RadioPreference.class, new ViewBinder());
    }

    public static class ViewBinder extends CompoundButtonPreference.ViewBinder<RadioPreference> {
        @NonNull
        @Override
        public CompoundButtonPreference.ViewHolder<RadioPreference> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.preference_listitem_radio, parent, false
            );
            return new CompoundButtonPreference.ViewHolder<>(itemView);
        }
    }
}
