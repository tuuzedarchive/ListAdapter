package com.tuuzed.androidx.list.preference;

import android.view.View;
import androidx.annotation.NonNull;
import com.tuuzed.androidx.list.adapter.CommonViewHolder;
import com.tuuzed.androidx.list.adapter.ListAdapter;
import com.tuuzed.androidx.list.preference.base.CompoundButtonPreference2;

public class CheckboxPreference extends CompoundButtonPreference2<CheckboxPreference> {

    public CheckboxPreference(@NonNull String title, @NonNull String summary) {
        super(title, summary);
    }

    public static void bindTo(@NonNull ListAdapter listAdapter) {
        listAdapter.bind(CheckboxPreference.class, new DefaultItemViewBinder());
    }

    public static final class DefaultItemViewBinder extends CompoundButtonPreference2.ItemViewBinderFactory<CheckboxPreference, CommonViewHolder> {
        @Override
        public int getLayoutRes() {
            return R.layout.preference_listitem_checkbox;
        }

        @NonNull
        @Override
        public CommonViewHolder createViewHolder(@NonNull View itemView) {
            return new CommonViewHolder(itemView);
        }
    }


}
