package com.tuuzed.androidx.list.preference.base;

import android.view.View;
import android.widget.CompoundButton;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.tuuzed.androidx.list.adapter.CommonViewHolder;
import com.tuuzed.androidx.list.adapter.ItemViewBinder;
import com.tuuzed.androidx.list.preference.Preferences;
import com.tuuzed.androidx.list.preference.R;
import com.tuuzed.androidx.list.preference.interfaces.PreferenceCallback;
import com.tuuzed.androidx.list.preference.internal.Preference2Helper;

public abstract class CompoundButtonPreference2<P extends CompoundButtonPreference2<P>> extends Preference2<P> {

    private boolean checked;
    @NonNull
    private PreferenceCallback<P> callback = Preferences.defaultPreferenceCallback();

    public CompoundButtonPreference2(@NonNull String title, @NonNull String summary) {
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


    public static abstract class ItemViewBinderFactory<P extends CompoundButtonPreference2<P>, VH extends RecyclerView.ViewHolder>
            extends ItemViewBinder.Factory<P, VH> {

        @Override
        public void onBindViewHolder(@NonNull final VH holder, final P preference, final int position) {
            super.onBindViewHolder(holder, preference, position);
            setPreference(holder, preference);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doCallback(holder, preference, position);
                }
            });
        }


        public void setPreference(@NonNull VH holder, @NonNull P preference) {
            Preference2Helper.setPreference(holder, preference);
            CompoundButton compoundButton;
            if (holder instanceof CommonViewHolder) {
                compoundButton = ((CommonViewHolder) holder).find(R.id.preference_compound_button);
            } else {
                compoundButton = holder.itemView.findViewById(R.id.preference_compound_button);
            }
            if (compoundButton != null) {
                compoundButton.setChecked(preference.isChecked());
            }
        }

        public void doCallback(@NonNull final VH holder, final P preference, final int position) {
            boolean oldChecked = preference.isChecked();
            preference.setChecked(!preference.isChecked());
            if (preference.getCallback().invoke(preference, position)) {
                setPreference(holder, preference);
            } else {
                preference.setChecked(oldChecked);
            }
        }
    }

}
