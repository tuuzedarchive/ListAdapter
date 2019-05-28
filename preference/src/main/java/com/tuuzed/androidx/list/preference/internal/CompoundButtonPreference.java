package com.tuuzed.androidx.list.preference.internal;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.tuuzed.androidx.list.adapter.ItemViewBinder;
import com.tuuzed.androidx.list.preference.PreferenceCallback;
import com.tuuzed.androidx.list.preference.Preferences;
import com.tuuzed.androidx.list.preference.R;

public abstract class CompoundButtonPreference<P extends CompoundButtonPreference> extends Preference2 {

    protected boolean checked;
    @NonNull
    protected PreferenceCallback<P> callback = Preferences.defaultPreferenceCallback();


    public CompoundButtonPreference(@NonNull String title, @NonNull String summary) {
        super(title, summary);
    }

    public boolean isChecked() {
        return checked;
    }

    @NonNull
    public P setChecked(boolean checked) {
        this.checked = checked;
        //noinspection unchecked
        return (P) this;
    }

    @NonNull
    public PreferenceCallback<P> getCallback() {
        return callback;
    }

    @NonNull
    public P setCallback(@NonNull PreferenceCallback<P> callback) {
        this.callback = callback;
        //noinspection unchecked
        return (P) this;
    }

    public static class ViewBinder<P extends CompoundButtonPreference<P>> extends ItemViewBinder.Factory<P, ViewHolder> {
        public ViewBinder(int layoutRes) {
            super(layoutRes);
        }

        @NonNull
        @Override
        public ViewHolder createViewHolder(@NonNull View itemView) {
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, P p, int position) {
            //noinspection unchecked
            holder.setPreference(p, position);
        }
    }

    public static class ViewHolder<P extends CompoundButtonPreference> extends RecyclerView.ViewHolder {
        public final TextView preferenceTitle;
        public final TextView preferenceSummary;
        public final CompoundButton preferenceCompoundButton;
        public final View preferenceItemLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            preferenceTitle = itemView.findViewById(R.id.preference_title);
            preferenceSummary = itemView.findViewById(R.id.preference_summary);
            preferenceCompoundButton = itemView.findViewById(R.id.preference_compound_button);
            preferenceItemLayout = itemView.findViewById(R.id.preference_item_layout);
        }

        public void setPreference(@NonNull final P preference, final int position) {
            preferenceTitle.setText(preference.getTitle());
            preferenceSummary.setText(preference.getSummary());
            preferenceCompoundButton.setChecked(preference.checked);
            preferenceItemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doCallback(preference, position);
                }
            });
        }

        protected void doCallback(@NonNull P preference, int position) {
            boolean oldChecked = preference.checked;
            preference.setChecked(!preference.checked);
            //noinspection unchecked
            if (preference.callback.invoke(preference, position)) {
                setPreference(preference, position);
            } else {
                preference.setChecked(oldChecked);
            }
        }
    }

}
