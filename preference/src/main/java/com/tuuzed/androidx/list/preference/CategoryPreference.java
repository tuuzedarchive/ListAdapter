package com.tuuzed.androidx.list.preference;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.tuuzed.androidx.list.adapter.CommonViewHolder;
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

    public static class ViewBinder extends ItemViewBinder.Factory<CategoryPreference, ViewHolder> {

        public ViewBinder() {
            super(R.layout.preference_listitem_category);
        }

        @NonNull
        @Override
        public ViewHolder createViewHolder(@NonNull View itemView) {
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, CategoryPreference preference, int position) {
            holder.setPreference(preference);
        }

    }

    public static class ViewHolder extends CommonViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setPreference(@NonNull CategoryPreference preference) {
            find(R.id.preference_title, TextView.class).setText(preference.getTitle());
        }
    }

}
