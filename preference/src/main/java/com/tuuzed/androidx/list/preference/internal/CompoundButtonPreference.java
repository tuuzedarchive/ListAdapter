package com.tuuzed.androidx.list.preference.internal;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.tuuzed.androidx.list.adapter.CommonViewHolder;
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

    public abstract static class ViewBinder<P extends CompoundButtonPreference<P>> extends ItemViewBinder.Factory<P, ViewHolder<P>> {
        @NonNull
        @Override
        public ViewHolder<P> createViewHolder(@NonNull View itemView) {
            return new ViewHolder<>(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder<P> holder, final P preference, final int position) {
            holder.setPreference(preference);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doCallback(holder, preference, position);
                }
            });
        }

        protected void doCallback(@NonNull final ViewHolder<P> holder, final P preference, final int position) {
            boolean oldChecked = preference.checked;
            preference.setChecked(!preference.checked);
            if (preference.callback.invoke(preference, position)) {
                holder.setPreference(preference);
            } else {
                preference.setChecked(oldChecked);
            }
        }
    }

    public static class ViewHolder<P extends CompoundButtonPreference> extends CommonViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setPreference(@NonNull final P preference) {
            find(R.id.preference_title, TextView.class).setText(preference.getTitle());
            find(R.id.preference_summary, TextView.class).setText(preference.getSummary());
            find(R.id.preference_compound_button, CompoundButton.class).setChecked(preference.checked);
        }

    }

}
