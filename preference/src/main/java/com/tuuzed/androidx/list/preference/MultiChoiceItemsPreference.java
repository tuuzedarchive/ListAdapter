package com.tuuzed.androidx.list.preference;

import android.content.Context;
import android.content.DialogInterface;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.tuuzed.androidx.list.adapter.ItemViewBinder;
import com.tuuzed.androidx.list.adapter.ListAdapter;
import com.tuuzed.androidx.list.preference.internal.Preference2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MultiChoiceItemsPreference<T> extends Preference2 {
    @NonNull
    private List<T> options = Collections.emptyList();
    @NonNull
    private List<T> checkedOptions = Collections.emptyList();
    @NonNull
    private OptionToStringFunction<T> optionToStringFunction = Preferences.defaultOptionToStringFunction();
    @NonNull
    private PreferenceCallback<MultiChoiceItemsPreference<T>> callback = Preferences.defaultPreferenceCallback();
    @NonNull
    private String optionSeparator = ", ";
    private boolean allowEmptySelection;


    public MultiChoiceItemsPreference(String title, String summary) {
        super(title, summary);
    }

    @NonNull
    public List<T> getOptions() {
        return options;
    }

    public MultiChoiceItemsPreference<T> setOptions(@NonNull List<T> options) {
        this.options = options;
        return this;
    }

    @NonNull
    public List<T> getCheckedOptions() {
        return checkedOptions;
    }

    @NonNull
    public MultiChoiceItemsPreference<T> setCheckedOptions(@NonNull List<T> checkedOptions) {
        this.checkedOptions = checkedOptions;
        return this;
    }

    @NonNull
    public OptionToStringFunction<T> getOptionToStringFunction() {
        return optionToStringFunction;
    }

    @NonNull
    public MultiChoiceItemsPreference<T> setOptionToStringFunction(@NonNull OptionToStringFunction<T> optionToStringFunction) {
        this.optionToStringFunction = optionToStringFunction;
        return this;
    }

    @NonNull
    public PreferenceCallback<MultiChoiceItemsPreference<T>> getCallback() {
        return callback;
    }

    @NonNull
    public MultiChoiceItemsPreference<T> setCallback(@NonNull PreferenceCallback<MultiChoiceItemsPreference<T>> callback) {
        this.callback = callback;
        return this;
    }

    @NonNull
    public String getOptionSeparator() {
        return optionSeparator;
    }

    @NonNull
    public MultiChoiceItemsPreference<T> setOptionSeparator(@NonNull String optionSeparator) {
        this.optionSeparator = optionSeparator;
        return this;
    }

    public boolean isAllowEmptySelection() {
        return allowEmptySelection;
    }

    @NonNull
    public MultiChoiceItemsPreference<T> setAllowEmptySelection(boolean allowEmptySelection) {
        this.allowEmptySelection = allowEmptySelection;
        return this;
    }

    public static void bindTo(@NonNull ListAdapter listAdapter) {
        listAdapter.bind(MultiChoiceItemsPreference.class, new ViewBinder());
    }

    public static class ViewBinder implements ItemViewBinder<MultiChoiceItemsPreference, ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.preference_listitem_multichoiceitems, parent, false
            );
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, MultiChoiceItemsPreference preference, int position) {
            holder.setPreference(preference, position);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView preferenceTitle;
        private TextView preferenceSummary;
        private View preferenceItemLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            preferenceTitle = itemView.findViewById(R.id.preference_title);
            preferenceSummary = itemView.findViewById(R.id.preference_summary);
            preferenceItemLayout = itemView.findViewById(R.id.preference_item_layout);
        }

        public void setPreference(@NonNull final MultiChoiceItemsPreference preference, final int position) {
            preferenceTitle.setText(preference.getTitle());
            preferenceSummary.setText(preference.getSummary());
            preferenceItemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Context context = v.getContext();
                    showInnerDialog(context, preference, position);
                }
            });


        }

        private <T> void showInnerDialog(
                @NonNull final Context context,
                @NonNull final MultiChoiceItemsPreference<T> preference,
                final int position
        ) {
            final CharSequence[] items = new CharSequence[preference.options.size()];
            final boolean[] checkedItems = new boolean[preference.options.size()];
            for (int i = 0; i < items.length; i++) {
                T option = preference.options.get(i);
                items[i] = preference.optionToStringFunction.invoke(option);
                checkedItems[i] = preference.checkedOptions.contains(option);
            }
            final Button[] positiveButton = new Button[]{null};
            AlertDialog dialog = new AlertDialog.Builder(context)
                    .setTitle(preference.getTitle())
                    .setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                            checkedItems[which] = isChecked;
                            onPreferenceChanged(positiveButton[0], preference, checkedItems);
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, null)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            doCallback(preference, position, checkedItems);
                        }
                    })
                    .create();
            dialog.show();
            positiveButton[0] = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            // AlertDialogCompat.setDialogWindowBackground(context, dialog, Color.WHITE);
            final Window window = dialog.getWindow();
            if (window != null) {
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            }
            onPreferenceChanged(positiveButton[0], preference, checkedItems);
        }

        private <T> void onPreferenceChanged(
                Button positiveButton,
                @NonNull final MultiChoiceItemsPreference<T> preference,
                @NonNull boolean[] checkedItems
        ) {
            if (positiveButton != null) {
                if (preference.allowEmptySelection) {
                    positiveButton.setEnabled(true);
                } else {
                    positiveButton.setEnabled(hasTrue(checkedItems));
                }
            }
        }

        private <T> void doCallback(
                @NonNull final MultiChoiceItemsPreference<T> preference,
                final int position,
                @NonNull final boolean[] checkedItems
        ) {
            // old
            String oldSummary = preference.getSummary();
            List<T> oldCheckedOptions = preference.getCheckedOptions();
            // new
            final List<T> newCheckedOptions = new ArrayList<>();
            final StringBuilder sb = new StringBuilder();
            boolean first = true;
            for (int i = 0; i < checkedItems.length; i++) {
                if (checkedItems[i]) {
                    T option = preference.options.get(i);
                    newCheckedOptions.add(option);
                    if (!first) {
                        sb.append(preference.optionSeparator);
                    }
                    sb.append(preference.optionToStringFunction.invoke(option));
                    first = false;
                }
            }
            String newSummary = sb.toString();
            // set new
            preference.setSummary(newSummary);
            preference.setCheckedOptions(newCheckedOptions);
            // callback
            if (preference.callback.invoke(preference, position)) {
                setPreference(preference, position);
            } else {
                preference.setSummary(oldSummary);
                preference.setCheckedOptions(oldCheckedOptions);
            }
        }

    }


    private static boolean hasTrue(boolean[] array) {
        if (array == null) return false;
        for (boolean it : array) {
            if (it) return true;
        }
        return false;
    }


}
