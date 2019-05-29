package com.tuuzed.androidx.list.preference;

import android.view.View;
import androidx.annotation.NonNull;
import com.tuuzed.androidx.list.adapter.CommonViewHolder;
import com.tuuzed.androidx.list.adapter.ListAdapter;
import com.tuuzed.androidx.list.preference.base.CompoundButtonPreference2;

public class RadioPreference extends CompoundButtonPreference2<RadioPreference> {

    public RadioPreference(@NonNull String title, @NonNull String summary) {
        super(title, summary);
    }

    public static void bindTo(@NonNull ListAdapter listAdapter) {
        listAdapter.bind(RadioPreference.class, new DefaultItemViewBinder());
    }

    public static final class DefaultItemViewBinder extends CompoundButtonPreference2.ItemViewBinderFactory<RadioPreference, CommonViewHolder> {
        @Override
        public int getLayoutRes() {
            return R.layout.preference_listitem_radio;
        }

        @NonNull
        @Override
        public CommonViewHolder createViewHolder(@NonNull View itemView) {
            return new CommonViewHolder(itemView);
        }
    }

}
