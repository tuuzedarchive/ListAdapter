package com.tuuzed.androidx.list.preference;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.tuuzed.androidx.list.adapter.CommonViewHolder;
import com.tuuzed.androidx.list.adapter.ItemViewBinder;
import com.tuuzed.androidx.list.adapter.ListAdapter;
import com.tuuzed.androidx.list.preference.base.Preference2;
import com.tuuzed.androidx.list.preference.internal.Preference2Helper;

public class GeneralPreference extends Preference2<GeneralPreference> {

    public GeneralPreference(@NonNull String title, @NonNull String summary) {
        super(title, summary);
    }

    public static void bindTo(@NonNull ListAdapter listAdapter) {
        listAdapter.bind(GeneralPreference.class, new DefaultItemViewBinder());
    }

    public static final class DefaultItemViewBinder extends ItemViewBinderFactory<GeneralPreference, CommonViewHolder> {
        @NonNull
        @Override
        public CommonViewHolder createViewHolder(@NonNull View itemView) {
            return new CommonViewHolder(itemView);
        }
    }

    public abstract static class ItemViewBinderFactory<P extends GeneralPreference, VH extends RecyclerView.ViewHolder>
            extends ItemViewBinder.Factory<P, VH> {
        @Override
        public int getLayoutRes() {
            return R.layout.preference_listitem_general;
        }

        @Override
        public void onBindViewHolder(@NonNull final VH holder, final P preference, final int position) {
            super.onBindViewHolder(holder, preference, position);
            setPreference(holder, preference);
        }

        public void setPreference(@NonNull VH holder, @NonNull P preference) {
            Preference2Helper.setPreference(holder, preference);
        }

    }

}
