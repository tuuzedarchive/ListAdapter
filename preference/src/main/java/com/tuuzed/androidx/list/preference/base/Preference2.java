package com.tuuzed.androidx.list.preference.base;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.tuuzed.androidx.list.adapter.CommonViewHolder;
import com.tuuzed.androidx.list.adapter.ItemViewBinder;
import com.tuuzed.androidx.list.preference.R;

public abstract class Preference2<P extends Preference2<P>> {
    @NonNull
    protected String title;
    @NonNull
    protected String summary;

    public Preference2(@NonNull String title, @NonNull String summary) {
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


    public static abstract class ItemViewBinderFactory<P extends Preference2<P>>
            extends ItemViewBinder.Factory<P, CommonViewHolder> {
        @NonNull
        @Override
        public CommonViewHolder createViewHolder(@NonNull View itemView) {
            return new CommonViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull CommonViewHolder holder, P preference, int position) {
            super.onBindViewHolder(holder, preference, position);
            setPreference(holder, preference);
        }

        protected void setPreference(@NonNull CommonViewHolder holder, P preference) {
            holder.find(R.id.preference_title, TextView.class).setText(preference.getTitle());
            holder.find(R.id.preference_summary, TextView.class).setText(preference.getSummary());
        }
    }

}
