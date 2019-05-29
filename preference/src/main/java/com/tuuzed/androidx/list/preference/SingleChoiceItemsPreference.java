package com.tuuzed.androidx.list.preference;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Size;
import androidx.appcompat.app.AlertDialog;
import com.tuuzed.androidx.list.adapter.CommonViewHolder;
import com.tuuzed.androidx.list.adapter.ListAdapter;
import com.tuuzed.androidx.list.preference.base.Preference2;
import com.tuuzed.androidx.list.preference.interfaces.ItemToStringFunction;
import com.tuuzed.androidx.list.preference.interfaces.ItemsLoaderFunction;
import com.tuuzed.androidx.list.preference.interfaces.PreferenceCallback;
import com.tuuzed.androidx.list.preference.internal.Utils;

import java.util.List;

public class SingleChoiceItemsPreference<T> extends Preference2<SingleChoiceItemsPreference<T>> {
    @NonNull
    private ItemsLoaderFunction<T> itemsLoaderFunction = Preferences.defaultItemsLoaderFunction();
    @Nullable
    private T checkedItem;
    @NonNull
    private ItemToStringFunction<T> itemToStringFunction = Preferences.defaultItemToStringFunction();
    @NonNull
    private PreferenceCallback<SingleChoiceItemsPreference<T>> callback = Preferences.defaultPreferenceCallback();
    private boolean allowEmptySelection;
    private boolean needConfirm;

    public SingleChoiceItemsPreference(String title, String summary) {
        super(title, summary);
    }

    @NonNull
    public ItemsLoaderFunction<T> getItemsLoaderFunction() {
        return itemsLoaderFunction;
    }

    @NonNull
    public SingleChoiceItemsPreference<T> setItemsLoaderFunction(@NonNull ItemsLoaderFunction<T> itemsLoaderFunction) {
        this.itemsLoaderFunction = itemsLoaderFunction;
        return this;
    }

    @Nullable
    public T getCheckedItem() {
        return checkedItem;
    }

    @NonNull
    public SingleChoiceItemsPreference<T> setCheckedItem(@Nullable T checkedItem) {
        this.checkedItem = checkedItem;
        return this;
    }

    @NonNull
    public ItemToStringFunction<T> getItemToStringFunction() {
        return itemToStringFunction;
    }

    @NonNull
    public SingleChoiceItemsPreference<T> setItemToStringFunction(@NonNull ItemToStringFunction<T> itemToStringFunction) {
        this.itemToStringFunction = itemToStringFunction;
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

    @NonNull
    public SingleChoiceItemsPreference<T> setNeedConfirm(boolean needConfirm) {
        this.needConfirm = needConfirm;
        return this;
    }

    public static void bindTo(@NonNull ListAdapter listAdapter) {
        //noinspection unchecked
        listAdapter.bind(SingleChoiceItemsPreference.class, new ItemViewBinderFactory() {
        });
    }

    public abstract static class ItemViewBinderFactory<T>
            extends Preference2.ItemViewBinderFactory<SingleChoiceItemsPreference<T>> {
        @Override
        public int getLayoutRes() {
            return R.layout.preference_listitem_singlechoiceitems;
        }

        @Override
        public void onBindViewHolder(@NonNull final CommonViewHolder holder, final SingleChoiceItemsPreference<T> preference, final int position) {
            super.onBindViewHolder(holder, preference, position);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Context context = v.getContext();
                    preference.itemsLoaderFunction.invoke(new ItemsLoaderFunction.Callback<T>() {
                        @Override
                        public void invoke(@NonNull List<T> items) {
                            showInnerDialog(context, holder, preference, items, position);
                        }
                    });
                }
            });
        }


        protected void showInnerDialog(
                @NonNull final Context context,
                @NonNull final CommonViewHolder holder,
                @NonNull final SingleChoiceItemsPreference<T> preference,
                @NonNull final List<T> items,
                final int position
        ) {
            CharSequence[] displayItems = new CharSequence[items.size()];
            final int[] checkedItemIndex = new int[]{-1};
            for (int i = 0; i < displayItems.length; i++) {
                T item = items.get(i);
                displayItems[i] = preference.itemToStringFunction.invoke(item);
                if (Utils.equals(preference.checkedItem, item)) {
                    checkedItemIndex[0] = i;
                }
            }
            final Button[] positiveButton = new Button[]{null};

            final AlertDialog.Builder builder = new AlertDialog.Builder(context)
                    .setTitle(preference.getTitle())
                    .setSingleChoiceItems(displayItems, checkedItemIndex[0], new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            checkedItemIndex[0] = which;
                            // 需要再次确认
                            if (preference.needConfirm) {
                                onPreferenceChanged(positiveButton[0], preference, items, checkedItemIndex[0]);
                            }
                            // 不需要再次确认
                            else {
                                doCallback(holder, preference, position, items, checkedItemIndex);
                                dialog.dismiss();
                            }
                        }
                    });
            if (preference.needConfirm) {
                builder.setNegativeButton(android.R.string.cancel, null)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                doCallback(holder, preference, position, items, checkedItemIndex);
                            }
                        });
            }
            final AlertDialog dialog = builder.create();
            if (preference.needConfirm) {
                dialog.setCanceledOnTouchOutside(true);
            }
            dialog.show();
            positiveButton[0] = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            final Window window = dialog.getWindow();
            if (window != null) {
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            }
            onPreferenceChanged(positiveButton[0], preference, items, checkedItemIndex[0]);
        }

        protected void onPreferenceChanged(
                Button positiveButton,
                @NonNull final SingleChoiceItemsPreference<T> preference,
                @NonNull final List<T> items,
                int checkedItemIndex
        ) {
            if (positiveButton != null) {
                if (preference.allowEmptySelection) {
                    positiveButton.setEnabled(true);
                } else {
                    positiveButton.setEnabled(checkedItemIndex >= 0 && checkedItemIndex < items.size());
                }
            }
        }

        protected void doCallback(
                @NonNull final CommonViewHolder holder,
                @NonNull final SingleChoiceItemsPreference<T> preference,
                final int position,
                @NonNull final List<T> items,
                @NonNull @Size(1) final int[] checkedItemIndex
        ) {
            // old
            String oldSummary = preference.getSummary();
            T oldCheckedItem = preference.checkedItem;
            // new
            T newCheckedItem = null;
            if (checkedItemIndex[0] > 0 && checkedItemIndex[0] < items.size()) {
                newCheckedItem = items.get(checkedItemIndex[0]);
            }
            String newSummary = preference.itemToStringFunction.invoke(newCheckedItem).toString();
            // set new
            preference.setSummary(newSummary);
            preference.setCheckedItem(newCheckedItem);
            // callback
            if (preference.callback.invoke(preference, position)) {
                setPreference(holder, preference);
            } else {
                preference.setSummary(oldSummary);
                preference.setCheckedItem(oldCheckedItem);
            }
        }
    }

}
