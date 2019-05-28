package com.tuuzed.androidx.list.preference;

import android.content.Context;
import android.content.DialogInterface;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Size;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.tuuzed.androidx.list.adapter.ItemViewBinder;
import com.tuuzed.androidx.list.adapter.ListAdapter;
import com.tuuzed.androidx.list.preference.internal.Preference2;

import java.util.Collections;
import java.util.List;

public class SingleChoiceItemsPreference<T> extends Preference2 {
    @NonNull
    private List<T> options = Collections.emptyList();
    @Nullable
    private T checkedOption;
    @NonNull
    private OptionToStringFunction<T> optionToStringFunction = Preferences.defaultOptionToStringFunction();
    @NonNull
    private PreferenceCallback<SingleChoiceItemsPreference<T>> callback = Preferences.defaultPreferenceCallback();
    private boolean allowEmptySelection;
    private boolean needConfirm;

    public SingleChoiceItemsPreference(String title, String summary) {
        super(title, summary);
    }

    @NonNull
    public List<T> getOptions() {
        return options;
    }

    public SingleChoiceItemsPreference<T> setOptions(@NonNull List<T> options) {
        this.options = options;
        return this;
    }

    @Nullable
    public T getCheckedOption() {
        return checkedOption;
    }

    @NonNull
    public SingleChoiceItemsPreference<T> setCheckedOption(@Nullable T checkedOption) {
        this.checkedOption = checkedOption;
        return this;
    }

    @NonNull
    public OptionToStringFunction<T> getOptionToStringFunction() {
        return optionToStringFunction;
    }

    @NonNull
    public SingleChoiceItemsPreference<T> setOptionToStringFunction(@NonNull OptionToStringFunction<T> optionToStringFunction) {
        this.optionToStringFunction = optionToStringFunction;
        return this;
    }

    @NonNull
    public PreferenceCallback<SingleChoiceItemsPreference<T>> getCallback() {
        return callback;
    }

    @NonNull
    public SingleChoiceItemsPreference<T> setCallback(@NonNull PreferenceCallback<SingleChoiceItemsPreference<T>> callback) {
        this.callback = callback;
        return this;
    }

    public boolean isAllowEmptySelection() {
        return allowEmptySelection;
    }

    @NonNull
    public SingleChoiceItemsPreference<T> setAllowEmptySelection(boolean allowEmptySelection) {
        this.allowEmptySelection = allowEmptySelection;
        return this;
    }

    public boolean isNeedConfirm() {
        return needConfirm;
    }

    public SingleChoiceItemsPreference<T> setNeedConfirm(boolean needConfirm) {
        this.needConfirm = needConfirm;
        return this;
    }

    public static void bindTo(@NonNull ListAdapter listAdapter) {
        listAdapter.bind(SingleChoiceItemsPreference.class, new ViewBinder());
    }

    public static class ViewBinder implements ItemViewBinder<SingleChoiceItemsPreference, ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.preference_listitem_singlechoiceitems, parent, false
            );
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, SingleChoiceItemsPreference preference, int position) {
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

        public void setPreference(@NonNull final SingleChoiceItemsPreference preference, final int position) {
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
                @NonNull final SingleChoiceItemsPreference<T> preference,
                final int position
        ) {
            CharSequence[] items = new CharSequence[preference.options.size()];
            final int[] checkedItem = new int[]{-1};
            for (int i = 0; i < items.length; i++) {
                T option = preference.options.get(i);
                items[i] = preference.optionToStringFunction.invoke(option);
                if (preference.checkedOption == option) {
                    checkedItem[0] = i;
                }
            }
            final Button[] positiveButton = new Button[]{null};
            final AlertDialog.Builder builder = new AlertDialog.Builder(context)
                    .setTitle(preference.getTitle())
                    .setSingleChoiceItems(items, checkedItem[0], new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            checkedItem[0] = which;
                            // 需要再次确认
                            if (preference.needConfirm) {
                                onPreferenceChanged(positiveButton[0], preference, checkedItem[0]);
                            }
                            // 不需要再次确认
                            else {
                                doCallback(preference, position, checkedItem);
                                dialog.dismiss();
                            }
                        }
                    });
            if (preference.needConfirm) {
                builder.setNegativeButton(android.R.string.cancel, null)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                doCallback(preference, position, checkedItem);
                            }
                        });
            }
            final AlertDialog dialog = builder.create();
            if (preference.needConfirm) {
                dialog.setCanceledOnTouchOutside(true);
            }
            dialog.show();
            positiveButton[0] = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            // AlertDialogCompat.setDialogWindowBackground(context, dialog, Color.WHITE);
            final Window window = dialog.getWindow();
            if (window != null) {
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            }
            onPreferenceChanged(positiveButton[0], preference, checkedItem[0]);
        }

        private <T> void onPreferenceChanged(
                Button positiveButton,
                @NonNull final SingleChoiceItemsPreference<T> preference,
                int checkedItem
        ) {
            if (positiveButton != null) {
                if (preference.allowEmptySelection) {
                    positiveButton.setEnabled(true);
                } else {
                    positiveButton.setEnabled(checkedItem >= 0 && checkedItem < preference.options.size());
                }
            }
        }

        private <T> void doCallback(
                @NonNull final SingleChoiceItemsPreference<T> preference,
                final int position,
                @NonNull @Size(1) final int[] checkedItem
        ) {
            // old
            String oldSummary = preference.getSummary();
            T oldCheckedOption = preference.getCheckedOption();
            // new
            T newCheckedOption = null;
            if (checkedItem[0] > 0 && checkedItem[0] < preference.options.size()) {
                newCheckedOption = preference.options.get(checkedItem[0]);
            }
            String newSummary = preference.optionToStringFunction.invoke(newCheckedOption).toString();
            // set new
            preference.setSummary(newSummary);
            preference.setCheckedOption(newCheckedOption);
            // callback
            if (preference.callback.invoke(preference, position)) {
                setPreference(preference, position);
            } else {
                preference.setSummary(oldSummary);
                preference.setCheckedOption(oldCheckedOption);
            }
        }


    }


}
