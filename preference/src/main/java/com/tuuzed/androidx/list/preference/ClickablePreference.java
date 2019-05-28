package com.tuuzed.androidx.list.preference;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.tuuzed.androidx.list.adapter.ItemViewBinder;
import com.tuuzed.androidx.list.adapter.ListAdapter;
import com.tuuzed.androidx.list.preference.internal.Preference2;

public class ClickablePreference extends Preference2 {

    @NonNull
    private PreferenceCallback<ClickablePreference> click = Preferences.defaultPreferenceCallback();

    public ClickablePreference(@NonNull String title, @NonNull String summary) {
        super(title, summary);
    }

    @NonNull
    public PreferenceCallback<ClickablePreference> getClick() {
        return click;
    }

    @NonNull
    public ClickablePreference setClick(@NonNull PreferenceCallback<ClickablePreference> click) {
        this.click = click;
        return this;
    }

    public static void bindTo(@NonNull ListAdapter listAdapter) {
        listAdapter.bind(ClickablePreference.class, new ViewBinder());
    }

    public static class ViewBinder implements ItemViewBinder<ClickablePreference, ViewHolder> {
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.preference_listitem_general, parent, false
            );
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, ClickablePreference preference, int position) {
            holder.setPreference(preference, position);
        }

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView preferenceTitle;
        private TextView preferenceSummary;
        private View preferenceItemLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            preferenceTitle = itemView.findViewById(R.id.preference_title);
            preferenceSummary = itemView.findViewById(R.id.preference_summary);
            preferenceItemLayout = itemView.findViewById(R.id.preference_item_layout);
        }

        public void setPreference(@NonNull final ClickablePreference preference, final int position) {
            preferenceTitle.setText(preference.getTitle());
            preferenceSummary.setText(preference.getSummary());
            preferenceItemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    preference.click.invoke(preference, position);
                }
            });
        }

    }

}
