package com.tuuzed.androidx.list.preference;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.tuuzed.androidx.list.adapter.CommonViewHolder;
import com.tuuzed.androidx.list.adapter.ItemViewBinder;
import com.tuuzed.androidx.list.adapter.ListAdapter;
import com.tuuzed.androidx.list.preference.base.Preference2;
import com.tuuzed.androidx.list.preference.interfaces.PreferenceCallback;
import com.tuuzed.androidx.list.preference.internal.Preference2Helper;

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
        listAdapter.bind(ClickablePreference.class, new DefaultItemViewBinder());
    }

    public static final class DefaultItemViewBinder extends ItemViewBinderFactory<ClickablePreference, CommonViewHolder> {
        @NonNull
        @Override
        public CommonViewHolder createViewHolder(@NonNull View itemView) {
            return new CommonViewHolder(itemView);
        }
    }


    public abstract static class ItemViewBinderFactory<P extends ClickablePreference, VH extends RecyclerView.ViewHolder>
            extends ItemViewBinder.Factory<P, VH> {
        @Override
        public int getLayoutRes() {
            return R.layout.preference_listitem_clickable;
        }

        @Override
        public void onBindViewHolder(@NonNull final VH holder, final P preference, final int position) {
            super.onBindViewHolder(holder, preference, position);
            setPreference(holder, preference);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    preference.getClick().invoke(preference, position);
                }
            });
        }


        public void setPreference(@NonNull VH holder, @NonNull P preference) {
            Preference2Helper.setPreference(holder, preference);
        }
    }

}
