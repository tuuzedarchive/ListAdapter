package com.tuuzed.androidx.list.preference;

import android.view.View;
import androidx.annotation.NonNull;
import com.tuuzed.androidx.list.adapter.CommonViewHolder;
import com.tuuzed.androidx.list.adapter.ListAdapter;
import com.tuuzed.androidx.list.preference.base.CompoundButtonPreference2;

public class SwitchPreference extends CompoundButtonPreference2<SwitchPreference> {

    public SwitchPreference(@NonNull String title, @NonNull String summary) {
        super(title, summary);
    }


    public static void bindTo(@NonNull ListAdapter listAdapter) {
        listAdapter.bind(SwitchPreference.class, new DefaultItemViewBinder());
    }

    public static final class DefaultItemViewBinder extends CompoundButtonPreference2.ItemViewBinderFactory<SwitchPreference, CommonViewHolder> {
        @Override
        public int getLayoutRes() {
            return R.layout.preference_listitem_switch;
        }

        @NonNull
        @Override
        public CommonViewHolder createViewHolder(@NonNull View itemView) {
            return new CommonViewHolder(itemView);
        }
    }

}
