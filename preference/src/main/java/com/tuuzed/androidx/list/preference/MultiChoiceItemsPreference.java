package com.tuuzed.androidx.list.preference;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
    private ItemsLoaderFunction<T> itemsLoaderFunction = Preferences.defaultItemsLoaderFunction();
    @NonNull
    private List<T> checkedItems = Collections.emptyList();
    @NonNull
    private ItemToStringFunction<T> itemToStringFunction = Preferences.defaultItemToStringFunction();
    @NonNull
    private PreferenceCallback<MultiChoiceItemsPreference<T>> callback = Preferences.defaultPreferenceCallback();
    @NonNull
    private String itemSeparator = ", ";
    private boolean allowEmptySelection;


    public MultiChoiceItemsPreference(String title, String summary) {
        super(title, summary);
    }

    @NonNull
    public ItemsLoaderFunction<T> getItemsLoaderFunction() {
        return itemsLoaderFunction;
    }

    @NonNull
    public MultiChoiceItemsPreference<T> setItemsLoaderFunction(@NonNull ItemsLoaderFunction<T> itemsLoaderFunction) {
        this.itemsLoaderFunction = itemsLoaderFunction;
        return this;
    }

    @NonNull
    public List<T> getCheckedItems() {
        return checkedItems;
    }

    @NonNull
    public MultiChoiceItemsPreference<T> setCheckedItems(@NonNull List<T> checkedItems) {
        this.checkedItems = checkedItems;
        return this;
    }

    @NonNull
    public ItemToStringFunction<T> getItemToStringFunction() {
        return itemToStringFunction;
    }

    @NonNull
    public MultiChoiceItemsPreference<T> setItemToStringFunction(@NonNull ItemToStringFunction<T> itemToStringFunction) {
        this.itemToStringFunction = itemToStringFunction;
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
    public String getItemSeparator() {
        return itemSeparator;
    }

    @NonNull
    public MultiChoiceItemsPreference<T> setItemSeparator(@NonNull String itemSeparator) {
        this.itemSeparator = itemSeparator;
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
        //noinspection unchecked
        listAdapter.bind(MultiChoiceItemsPreference.class, new ViewBinder());
    }

    public static class ViewBinder<T> extends ItemViewBinder.Factory<MultiChoiceItemsPreference<T>, ViewHolder<T>> {

        public ViewBinder() {
            super(R.layout.preference_listitem_multichoiceitems);
        }

        @NonNull
        @Override
        public ViewHolder<T> createViewHolder(@NonNull View itemView) {
            return new ViewHolder<>(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder<T> holder, MultiChoiceItemsPreference<T> preference, int position) {
            holder.setPreference(preference, position);
        }
    }

    public static class ViewHolder<T> extends RecyclerView.ViewHolder {

        public final TextView preferenceTitle;
        public final TextView preferenceSummary;
        public final View preferenceItemLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            preferenceTitle = itemView.findViewById(R.id.preference_title);
            preferenceSummary = itemView.findViewById(R.id.preference_summary);
            preferenceItemLayout = itemView.findViewById(R.id.preference_item_layout);
        }

        public void setPreference(@NonNull final MultiChoiceItemsPreference<T> preference, final int position) {
            preferenceTitle.setText(preference.getTitle());
            preferenceSummary.setText(preference.getSummary());
            preferenceItemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Context context = v.getContext();
                    preference.itemsLoaderFunction.invoke(new ItemsLoaderFunction.Callback<T>() {
                        @Override
                        public void invoke(@NonNull List<T> items) {
                            showInnerDialog(context, preference, items, position);

                        }
                    });
                }
            });


        }

        protected void showInnerDialog(
                @NonNull final Context context,
                @NonNull final MultiChoiceItemsPreference<T> preference,
                @NonNull final List<T> items,
                final int position
        ) {
            final CharSequence[] displayItems = new CharSequence[items.size()];
            final boolean[] checkedItemStatusArray = new boolean[items.size()];
            for (int i = 0; i < displayItems.length; i++) {
                T item = items.get(i);
                displayItems[i] = preference.itemToStringFunction.invoke(item);
                checkedItemStatusArray[i] = preference.checkedItems.contains(item);
            }
            final Button[] positiveButton = new Button[]{null};
            final AlertDialog dialog = new AlertDialog.Builder(context)
                    .setTitle(preference.getTitle())
                    .setMultiChoiceItems(displayItems, checkedItemStatusArray, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                            checkedItemStatusArray[which] = isChecked;
                            onPreferenceChanged(positiveButton[0], preference, checkedItemStatusArray);
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, null)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            doCallback(preference, position, items, checkedItemStatusArray);
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
            onPreferenceChanged(positiveButton[0], preference, checkedItemStatusArray);
        }

        protected void onPreferenceChanged(
                Button positiveButton,
                @NonNull final MultiChoiceItemsPreference<T> preference,
                @NonNull boolean[] checkedItemStatusArray
        ) {
            if (positiveButton != null) {
                if (preference.allowEmptySelection) {
                    positiveButton.setEnabled(true);
                } else {
                    positiveButton.setEnabled(hasTrue(checkedItemStatusArray));
                }
            }
        }

        protected void doCallback(
                @NonNull final MultiChoiceItemsPreference<T> preference,
                final int position,
                @NonNull final List<T> items,
                @NonNull final boolean[] checkedItemStatusArray
        ) {
            // old
            String oldSummary = preference.getSummary();
            List<T> oldCheckedItems = preference.checkedItems;
            // new
            final List<T> newCheckedItems = new ArrayList<>();
            final StringBuilder sb = new StringBuilder();
            boolean first = true;
            for (int i = 0; i < checkedItemStatusArray.length; i++) {
                if (checkedItemStatusArray[i]) {
                    T item = items.get(i);
                    newCheckedItems.add(item);
                    if (!first) {
                        sb.append(preference.itemSeparator);
                    }
                    sb.append(preference.itemToStringFunction.invoke(item));
                    first = false;
                }
            }
            String newSummary = sb.toString();
            // set new
            preference.setSummary(newSummary);
            preference.setCheckedItems(newCheckedItems);
            // callback
            if (preference.callback.invoke(preference, position)) {
                setPreference(preference, position);
            } else {
                preference.setSummary(oldSummary);
                preference.setCheckedItems(oldCheckedItems);
            }
        }

        protected boolean hasTrue(boolean[] array) {
            if (array == null) return false;
            for (boolean it : array) {
                if (it) return true;
            }
            return false;
        }


    }


}
