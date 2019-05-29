package com.tuuzed.androidx.list.preference;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.tuuzed.androidx.list.adapter.CommonViewHolder;
import com.tuuzed.androidx.list.adapter.ItemViewBinder;
import com.tuuzed.androidx.list.adapter.ListAdapter;
import com.tuuzed.androidx.list.preference.base.Preference2;
import com.tuuzed.androidx.list.preference.interfaces.ItemToStringFunction;
import com.tuuzed.androidx.list.preference.interfaces.ItemsLoaderFunction;
import com.tuuzed.androidx.list.preference.interfaces.PreferenceCallback;
import com.tuuzed.androidx.list.preference.internal.Preference2Helper;
import com.tuuzed.androidx.list.preference.internal.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MultiChoiceItemsPreference extends Preference2<MultiChoiceItemsPreference> {
    @NonNull
    private ItemsLoaderFunction<Object> itemsLoaderFunction = Preferences.defaultItemsLoaderFunction();
    @NonNull
    private List<Object> checkedItems = Collections.emptyList();
    @NonNull
    private ItemToStringFunction<Object> itemToStringFunction = Preferences.defaultItemToStringFunction();
    @NonNull
    private PreferenceCallback<MultiChoiceItemsPreference> callback = Preferences.defaultPreferenceCallback();
    @NonNull
    private String itemSeparator = ", ";
    private boolean allowEmptySelection;

    public MultiChoiceItemsPreference(@NonNull String title, @NonNull String summary) {
        super(title, summary);
    }

    @NonNull
    public ItemsLoaderFunction<Object> getItemsLoaderFunction() {
        return itemsLoaderFunction;
    }

    @NonNull
    public MultiChoiceItemsPreference setItemsLoaderFunction(@NonNull ItemsLoaderFunction<Object> itemsLoaderFunction) {
        this.itemsLoaderFunction = itemsLoaderFunction;
        return this;
    }

    @NonNull
    public List<Object> getCheckedItems() {
        return checkedItems;
    }

    @NonNull
    public MultiChoiceItemsPreference setCheckedItems(@NonNull List<Object> checkedItems) {
        this.checkedItems = checkedItems;
        return this;
    }

    @NonNull
    public ItemToStringFunction<Object> getItemToStringFunction() {
        return itemToStringFunction;
    }

    @NonNull
    public MultiChoiceItemsPreference setItemToStringFunction(@NonNull ItemToStringFunction<Object> itemToStringFunction) {
        this.itemToStringFunction = itemToStringFunction;
        return this;
    }

    @NonNull
    public PreferenceCallback<MultiChoiceItemsPreference> getCallback() {
        return callback;
    }

    @NonNull
    public MultiChoiceItemsPreference setCallback(@NonNull PreferenceCallback<MultiChoiceItemsPreference> callback) {
        this.callback = callback;
        return this;
    }

    @NonNull
    public String getItemSeparator() {
        return itemSeparator;
    }

    @NonNull
    public MultiChoiceItemsPreference setItemSeparator(@NonNull String itemSeparator) {
        this.itemSeparator = itemSeparator;
        return this;
    }

    public boolean isAllowEmptySelection() {
        return allowEmptySelection;
    }

    @NonNull
    public MultiChoiceItemsPreference setAllowEmptySelection(boolean allowEmptySelection) {
        this.allowEmptySelection = allowEmptySelection;
        return this;
    }

    public static void bindTo(@NonNull ListAdapter listAdapter) {
        listAdapter.bind(MultiChoiceItemsPreference.class, new DefaultItemViewBinder());
    }

    public static final class DefaultItemViewBinder extends ItemViewBinderFactory<MultiChoiceItemsPreference, CommonViewHolder> {
        @NonNull
        @Override
        public CommonViewHolder createViewHolder(@NonNull View itemView) {
            return new CommonViewHolder(itemView);
        }
    }

    public abstract static class ItemViewBinderFactory<P extends MultiChoiceItemsPreference, VH extends RecyclerView.ViewHolder>
            extends ItemViewBinder.Factory<P, VH> {
        @Override
        public int getLayoutRes() {
            return R.layout.preference_listitem_multichoiceitems;
        }

        @Override
        public void onBindViewHolder(@NonNull final VH holder, final P preference, final int position) {
            super.onBindViewHolder(holder, preference, position);
            setPreference(holder, preference);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Context context = v.getContext();
                    preference.getItemsLoaderFunction().invoke(new ItemsLoaderFunction.Callback<Object>() {
                        @Override
                        public void invoke(@NonNull List<Object> items) {
                            showInnerDialog(context, holder, preference, items, position);
                        }
                    });

                }
            });
        }


        public void setPreference(@NonNull VH holder, @NonNull P preference) {
            Preference2Helper.setPreference(holder, preference);
        }

        protected void showInnerDialog(
                @NonNull final Context context,
                @NonNull final VH holder,
                @NonNull final P preference,
                @NonNull final List<Object> items,
                final int position
        ) {
            final CharSequence[] displayItems = new CharSequence[items.size()];
            final boolean[] checkedItemStatusArray = new boolean[items.size()];
            for (int i = 0; i < displayItems.length; i++) {
                Object item = items.get(i);
                displayItems[i] = preference.getItemToStringFunction().invoke(item);
                checkedItemStatusArray[i] = preference.getCheckedItems().contains(item);
            }
            final Button[] positiveButton = new Button[]{null};
            final AlertDialog.Builder builder = new AlertDialog.Builder(context)
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
                            doCallback(holder, preference, position, items, checkedItemStatusArray);
                        }
                    });
            final AlertDialog dialog = builder.create();
            dialog.show();
            positiveButton[0] = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            final Window window = dialog.getWindow();
            if (window != null) {
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            }
            onPreferenceChanged(positiveButton[0], preference, checkedItemStatusArray);
        }

        protected void onPreferenceChanged(
                Button positiveButton,
                @NonNull final P preference,
                @NonNull boolean[] checkedItemStatusArray
        ) {
            if (positiveButton != null) {
                if (preference.isAllowEmptySelection()) {
                    positiveButton.setEnabled(true);
                } else {
                    positiveButton.setEnabled(Utils.hasTrue(checkedItemStatusArray));
                }
            }
        }

        protected void doCallback(
                @NonNull final VH holder,
                @NonNull final P preference,
                final int position,
                @NonNull final List<Object> items,
                @NonNull final boolean[] checkedItemStatusArray
        ) {
            // old
            String oldSummary = preference.getSummary();
            List<Object> oldCheckedItems = preference.getCheckedItems();
            // new
            final List<Object> newCheckedItems = new ArrayList<>();
            final StringBuilder sb = new StringBuilder();
            boolean first = true;
            for (int i = 0; i < checkedItemStatusArray.length; i++) {
                if (checkedItemStatusArray[i]) {
                    Object item = items.get(i);
                    newCheckedItems.add(item);
                    if (!first) {
                        sb.append(preference.getItemSeparator());
                    }
                    sb.append(preference.getItemToStringFunction().invoke(item));
                    first = false;
                }
            }
            String newSummary = sb.toString();
            // set new
            preference.setSummary(newSummary);
            preference.setCheckedItems(newCheckedItems);
            // callback
            if (preference.getCallback().invoke(preference, position)) {
                setPreference(holder, preference);
            } else {
                preference.setSummary(oldSummary);
                preference.setCheckedItems(oldCheckedItems);
            }
        }

    }


}
