package com.tuuzed.androidx.list.preference;

import android.view.View;
import androidx.annotation.NonNull;
import com.tuuzed.androidx.list.adapter.CommonViewHolder;
import com.tuuzed.androidx.list.adapter.ListAdapter;
import com.tuuzed.androidx.list.preference.base.Preference2;
import com.tuuzed.androidx.list.preference.interfaces.PreferenceCallback;

public class ClickablePreference extends Preference2<ClickablePreference> {

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
        listAdapter.bind(ClickablePreference.class, new Preference2.ItemViewBinderFactory<ClickablePreference>() {
            @Override
            public int getLayoutRes() {
                return R.layout.preference_listitem_general;
            }

            @Override
            public void onBindViewHolder(@NonNull CommonViewHolder holder, final ClickablePreference preference,
                                         final int position) {
                super.onBindViewHolder(holder, preference, position);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        preference.click.invoke(preference, position);
                    }
                });
            }
        });
    }

}
