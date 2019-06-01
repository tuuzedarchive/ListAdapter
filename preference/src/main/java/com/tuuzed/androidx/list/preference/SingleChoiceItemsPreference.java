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
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.tuuzed.androidx.list.adapter.CommonViewHolder;
import com.tuuzed.androidx.list.adapter.ItemViewBinder;
import com.tuuzed.androidx.list.adapter.ListAdapter;
import com.tuuzed.androidx.list.preference.base.Preference2;
import com.tuuzed.androidx.list.preference.interfaces.ItemToStringFunction;
import com.tuuzed.androidx.list.preference.interfaces.ItemsLoaderFunction;
import com.tuuzed.androidx.list.preference.interfaces.PreferenceCallback;
import com.tuuzed.androidx.list.preference.internal.Preference2Helper;
import com.tuuzed.androidx.list.preference.internal.Utils;

import java.util.List;

public class SingleChoiceItemsPreference extends Preference2<SingleChoiceItemsPreference> {
    @NonNull
    private ItemsLoaderFunction<Object> itemsLoaderFunction = Preferences.defaultItemsLoaderFunction();
    @Nullable
    private Object checkedItem;
    @NonNull
    private ItemToStringFunction<Object> itemToStringFunction = Preferences.defaultItemToStringFunction();
    @NonNull
    private PreferenceCallback<SingleChoiceItemsPreference> callback = Preferences.defaultPreferenceCallback();
    private boolean allowEmptySelection;
    private boolean waitDismiss;

    public SingleChoiceItemsPreference(@NonNull String title, @NonNull String summary) {
        super(title, summary);
    }

    @NonNull
    public ItemsLoaderFunction<Object> getItemsLoaderFunction() {
        return itemsLoaderFunction;
    }

    @NonNull
    public SingleChoiceItemsPreference setItemsLoaderFunction(@NonNull ItemsLoaderFunction<Object> itemsLoaderFunction) {
        this.itemsLoaderFunction = itemsLoaderFunction;
        return this;
    }

    @Nullable
    public Object getCheckedItem() {
        return checkedItem;
    }

    @NonNull
    public SingleChoiceItemsPreference setCheckedItem(@Nullable Object checkedItem) {
        this.checkedItem = checkedItem;
        return this;
    }

    @NonNull
    public ItemToStringFunction<Object> getItemToStringFunction() {
        return itemToStringFunction;
    }

    @NonNull
    public SingleChoiceItemsPreference setItemToStringFunction(@NonNull ItemToStringFunction<Object> itemToStringFunction) {
        this.itemToStringFunction = itemToStringFunction;
        return this;
    }

    @NonNull
    public PreferenceCallback<SingleChoiceItemsPreference> getCallback() {
        return callback;
    }

    @NonNull
    public SingleChoiceItemsPreference setCallback(@NonNull PreferenceCallback<SingleChoiceItemsPreference> callback) {
        this.callback = callback;
        return this;
    }

    public boolean isAllowEmptySelection() {
        return allowEmptySelection;
    }

    @NonNull
    public SingleChoiceItemsPreference setAllowEmptySelection(boolean allowEmptySelection) {
        this.allowEmptySelection = allowEmptySelection;
        return this;
    }

    public boolean isWaitDismiss() {
        return waitDismiss;
    }

    @NonNull
    public SingleChoiceItemsPreference setWaitDismiss(boolean waitDismiss) {
        this.waitDismiss = waitDismiss;
        return this;
    }

    public static void bindTo(@NonNull ListAdapter listAdapter) {
        listAdapter.bind(SingleChoiceItemsPreference.class, new DefaultItemViewBinder());
    }

    public static final class DefaultItemViewBinder extends ItemViewBinderFactory<SingleChoiceItemsPreference, CommonViewHolder> {
        @NonNull
        @Override
        public CommonViewHolder createViewHolder(@NonNull View itemView) {
            return new CommonViewHolder(itemView);
        }
    }


    public abstract static class ItemViewBinderFactory<P extends SingleChoiceItemsPreference, VH extends RecyclerView.ViewHolder>
            extends ItemViewBinder.Factory<P, VH> {
        @Override
        public int getLayoutRes() {
            return R.layout.preference_listitem_singlechoiceitems;
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


        public void showInnerDialog(
                @NonNull final Context context,
                @NonNull final VH holder,
                @NonNull final P preference,
                @NonNull final List<Object> items,
                final int position
        ) {
            CharSequence[] displayItems = new CharSequence[items.size()];
            final int[] checkedItemIndex = new int[]{-1};
            for (int i = 0; i < displayItems.length; i++) {
                Object item = items.get(i);
                displayItems[i] = preference.getItemToStringFunction().invoke(item);
                if (Utils.equals(preference.getCheckedItem(), item)) {
                    checkedItemIndex[0] = i;
                }
            }
            final Button[] positiveButton = new Button[]{null};
            AlertDialog.Builder builder = null;
            try {
                builder = new MaterialAlertDialogBuilder(context);
            } catch (Exception e) {
                // pass
            }
            if (builder == null) {
                builder = new AlertDialog.Builder(context);
            }
            builder.setTitle(preference.getTitle())
                    .setSingleChoiceItems(displayItems, checkedItemIndex[0], new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            checkedItemIndex[0] = which;
                            // 需要再次确认
                            if (preference.isWaitDismiss()) {
                                onPreferenceChanged(positiveButton[0], preference, items, checkedItemIndex[0]);
                            }
                            // 不需要再次确认
                            else {
                                doCallback(holder, preference, position, items, checkedItemIndex);
                                dialog.dismiss();
                            }
                        }
                    });
            if (preference.isWaitDismiss()) {
                builder.setNegativeButton(android.R.string.cancel, null)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                doCallback(holder, preference, position, items, checkedItemIndex);
                            }
                        });
            }
            final AlertDialog dialog = builder.create();
            dialog.show();
            positiveButton[0] = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            final Window window = dialog.getWindow();
            if (window != null) {
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            }
            onPreferenceChanged(positiveButton[0], preference, items, checkedItemIndex[0]);
        }

        public void onPreferenceChanged(
                Button positiveButton,
                @NonNull final P preference,
                @NonNull final List<Object> items,
                int checkedItemIndex
        ) {
            if (positiveButton != null) {
                if (preference.isAllowEmptySelection()) {
                    positiveButton.setEnabled(true);
                } else {
                    positiveButton.setEnabled(checkedItemIndex >= 0 && checkedItemIndex < items.size());
                }
            }
        }

        public void doCallback(
                @NonNull final VH holder,
                @NonNull final P preference,
                final int position,
                @NonNull final List<Object> items,
                @NonNull @Size(1) final int[] checkedItemIndex
        ) {
            // old
            String oldSummary = preference.getSummary();
            Object oldCheckedItem = preference.getCheckedItem();
            // new
            Object newCheckedItem = null;
            if (checkedItemIndex[0] >= 0 && checkedItemIndex[0] < items.size()) {
                newCheckedItem = items.get(checkedItemIndex[0]);
            }
            String newSummary = preference.getItemToStringFunction().invoke(newCheckedItem).toString();
            // set new
            preference.setSummary(newSummary);
            preference.setCheckedItem(newCheckedItem);
            // callback
            if (preference.getCallback().invoke(preference, position)) {
                setPreference(holder, preference);
            } else {
                preference.setSummary(oldSummary);
                preference.setCheckedItem(oldCheckedItem);
            }
        }
    }

}
