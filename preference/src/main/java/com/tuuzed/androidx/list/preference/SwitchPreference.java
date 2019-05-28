package com.tuuzed.androidx.list.preference;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.tuuzed.androidx.list.adapter.ListAdapter;
import com.tuuzed.androidx.list.preference.internal.CompoundButtonPreference;

public class SwitchPreference extends CompoundButtonPreference<SwitchPreference> {

    public SwitchPreference(@NonNull String title, @NonNull String summary) {
        super(title, summary);
    }

    public static void bindTo(@NonNull ListAdapter listAdapter) {
        listAdapter.bind(SwitchPreference.class, new ViewBinder());
    }

    public static class ViewBinder extends CompoundButtonPreference.ViewBinder<SwitchPreference> {
        @NonNull
        @Override
        public CompoundButtonPreference.ViewHolder<SwitchPreference> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.preference_listitem_switch, parent, false
            );
            return new CompoundButtonPreference.ViewHolder<>(itemView);
        }
    }

}
