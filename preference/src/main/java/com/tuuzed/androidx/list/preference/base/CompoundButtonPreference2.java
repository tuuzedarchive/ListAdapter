package com.tuuzed.androidx.list.preference.base;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.tuuzed.androidx.list.adapter.CommonViewHolder;
import com.tuuzed.androidx.list.adapter.ItemViewBinder;
import com.tuuzed.androidx.list.preference.Preferences;
import com.tuuzed.androidx.list.preference.R;
import com.tuuzed.androidx.list.preference.interfaces.PreferenceCallback;

public abstract class CompoundButtonPreference2<P extends CompoundButtonPreference2<P>> {
    @NonNull
    protected String title;
    @NonNull
    protected String summary;

    protected boolean checked;
    @NonNull
    protected PreferenceCallback<P> callback = Preferences.defaultPreferenceCallback();

    public CompoundButtonPreference2(@NonNull String title, @NonNull String summary) {
        this.title = title;
        this.summary = summary;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    @NonNull
    public P setTitle(@NonNull String title) {
        this.title = title;
        //noinspection unchecked
        return (P) this;
    }

    @NonNull
    public String getSummary() {
        return summary;
    }

    @NonNull
    public P setSummary(@NonNull String summary) {
        this.summary = summary;
        //noinspection unchecked
        return (P) this;
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


    public static abstract class ItemViewBinderFactory<P extends CompoundButtonPreference2<P>>
            extends ItemViewBinder.Factory<P, CommonViewHolder> {
        @NonNull
        @Override
        public CommonViewHolder createViewHolder(@NonNull View itemView) {
            return new CommonViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull final CommonViewHolder holder, final P preference, final int position) {
            super.onBindViewHolder(holder, preference, position);
            setPreference(holder, preference);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doCallback(holder, preference, position);
                }
            });
        }

        protected void doCallback(@NonNull final CommonViewHolder holder, final P preference, final int position) {
            boolean oldChecked = preference.checked;
            preference.setChecked(!preference.checked);
            if (preference.callback.invoke(preference, position)) {
                setPreference(holder, preference);
            } else {
                preference.setChecked(oldChecked);
            }
        }

        protected void setPreference(@NonNull CommonViewHolder holder, @NonNull P preference) {
            holder.find(R.id.preference_title, TextView.class).setText(preference.getTitle());
            holder.find(R.id.preference_summary, TextView.class).setText(preference.getSummary());
            holder.find(R.id.preference_compound_button, CompoundButton.class).setChecked(preference.checked);
        }
    }

}
