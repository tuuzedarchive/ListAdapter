package com.tuuzed.androidx.list.preference;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.tuuzed.androidx.list.adapter.ItemViewBinder;
import com.tuuzed.androidx.list.adapter.ListAdapter;
import com.tuuzed.androidx.list.preference.internal.Preference;

public class CategoryPreference extends Preference {

    public CategoryPreference(@NonNull String title) {
        super(title);
    }

    public static void bindTo(@NonNull ListAdapter listAdapter) {
        listAdapter.bind(CategoryPreference.class, new ViewBinder());
    }

    public static class ViewBinder implements ItemViewBinder<CategoryPreference, ViewHolder> {
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.preference_listitem_category, parent, false
            );
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, CategoryPreference preference, int position) {
            holder.setPreference(preference);
        }

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView preferenceTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            preferenceTitle = itemView.findViewById(R.id.preference_title);
        }

        public void setPreference(@NonNull CategoryPreference preference) {
            preferenceTitle.setText(preference.getTitle());
        }

    }

}
