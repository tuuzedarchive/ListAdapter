package com.tuuzed.androidx.list.preference.internal;

import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.tuuzed.androidx.list.adapter.CommonViewHolder;
import com.tuuzed.androidx.list.preference.R;
import com.tuuzed.androidx.list.preference.base.Preference2;

public final class Preference2Helper {

    public static void setPreference(@NonNull RecyclerView.ViewHolder holder, @NonNull Preference2 preference) {
        TextView title;
        TextView summary;
        if (holder instanceof CommonViewHolder) {
            title = ((CommonViewHolder) holder).find(R.id.preference_title);
            summary = ((CommonViewHolder) holder).find(R.id.preference_summary);
        } else {
            title = holder.itemView.findViewById(R.id.preference_title);
            summary = holder.itemView.findViewById(R.id.preference_summary);
        }
        if (title != null) {
            title.setText(preference.getTitle());
        }
        if (summary != null) {
            summary.setText(preference.getSummary());
        }
    }
}
