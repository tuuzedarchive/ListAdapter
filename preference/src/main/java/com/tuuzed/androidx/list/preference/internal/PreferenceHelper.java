package com.tuuzed.androidx.list.preference.internal;

import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.tuuzed.androidx.list.adapter.CommonViewHolder;
import com.tuuzed.androidx.list.preference.R;
import com.tuuzed.androidx.list.preference.base.Preference;

public final class PreferenceHelper {

    public static void setPreference(@NonNull RecyclerView.ViewHolder holder, @NonNull Preference preference) {
        TextView title;
        if (holder instanceof CommonViewHolder) {
            title = ((CommonViewHolder) holder).find(R.id.preference_title);
        } else {
            title = holder.itemView.findViewById(R.id.preference_title);
        }
        if (title != null) {
            title.setText(preference.getTitle());
        }
    }
}
