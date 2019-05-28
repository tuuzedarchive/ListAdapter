package com.tuuzed.androidx.list.preference;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.tuuzed.androidx.list.adapter.ListAdapter;
import com.tuuzed.androidx.list.preference.internal.CompoundButtonPreference;

public class CheckboxPreference extends CompoundButtonPreference<CheckboxPreference> {

    public CheckboxPreference(@NonNull String title, @NonNull String summary) {
        super(title, summary);
    }

    public static void bindTo(@NonNull ListAdapter listAdapter) {
        listAdapter.bind(CheckboxPreference.class, new ViewBinder());
    }

    public static class ViewBinder extends CompoundButtonPreference.ViewBinder<CheckboxPreference> {
        @NonNull
        @Override
        public CompoundButtonPreference.ViewHolder<CheckboxPreference> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.preference_listitem_checkbox, parent, false
            );
            return new CompoundButtonPreference.ViewHolder<>(itemView);
        }
    }
}
