package com.tuuzed.androidx.list.preference;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.tuuzed.androidx.list.adapter.CommonViewHolder;
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

    public static class ViewBinder extends ItemViewBinder.Factory<ClickablePreference, ViewHolder> {

        @Override
        public int getLayoutRes() {
            return R.layout.preference_listitem_general;
        }

        @NonNull
        @Override
        public ViewHolder createViewHolder(@NonNull View itemView) {
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(
                @NonNull final ViewHolder holder,
                final ClickablePreference preference,
                final int position
        ) {
            holder.setPreference(preference);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    preference.click.invoke(preference, position);
                }
            });
        }

    }

    public static class ViewHolder extends CommonViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setPreference(@NonNull final ClickablePreference preference) {
            find(R.id.preference_title, TextView.class).setText(preference.getTitle());
            find(R.id.preference_summary, TextView.class).setText(preference.getSummary());
        }
    }

}
