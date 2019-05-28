package com.tuuzed.androidx.list.preference;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.tuuzed.androidx.list.adapter.ItemViewBinder;
import com.tuuzed.androidx.list.adapter.ListAdapter;
import com.tuuzed.androidx.list.preference.internal.Preference2;

public class GeneralPreference extends Preference2 {

    public GeneralPreference(@NonNull String title, @NonNull String summary) {
        super(title, summary);
    }

    public static void bindTo(@NonNull ListAdapter listAdapter) {
        listAdapter.bind(GeneralPreference.class, new ViewBinder());
    }

    public static class ViewBinder extends ItemViewBinder.Factory<GeneralPreference, ViewHolder> {

        public ViewBinder() {
            super(R.layout.preference_listitem_general);
        }

        @NonNull
        @Override
        public ViewHolder createViewHolder(@NonNull View itemView) {
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, GeneralPreference preference, int position) {
            holder.setPreference(preference);
        }

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView preferenceTitle;
        public final TextView preferenceSummary;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            preferenceTitle = itemView.findViewById(R.id.preference_title);
            preferenceSummary = itemView.findViewById(R.id.preference_summary);
        }

        public void setPreference(@NonNull GeneralPreference preference) {
            preferenceTitle.setText(preference.getTitle());
            preferenceSummary.setText(preference.getSummary());
        }

    }

}
