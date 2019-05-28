package com.tuuzed.androidx.list.preference;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.tuuzed.androidx.list.adapter.ItemViewBinder;
import com.tuuzed.androidx.list.adapter.ListAdapter;
import com.tuuzed.androidx.list.preference.internal.Preference2;

public class EditTextPreference extends Preference2 {
    private int inputType = InputType.TYPE_CLASS_TEXT;
    @Nullable
    private CharSequence hint = null;
    @Nullable
    private CharSequence helperText = null;
    private boolean allowEmpty = false;
    private int minLength = -1;
    private int maxLength = -1;
    @NonNull
    private PreferenceCallback<EditTextPreference> callback = Preferences.defaultPreferenceCallback();


    public EditTextPreference(@NonNull String title, @NonNull String summary) {
        super(title, summary);
    }

    public int getInputType() {
        return inputType;
    }

    @NonNull
    public EditTextPreference setInputType(int inputType) {
        this.inputType = inputType;
        return this;
    }

    @Nullable
    public CharSequence getHint() {
        return hint;
    }

    @NonNull
    public EditTextPreference setHint(@Nullable CharSequence hint) {
        this.hint = hint;
        return this;
    }

    @Nullable
    public CharSequence getHelperText() {
        return helperText;
    }

    @NonNull
    public EditTextPreference setHelperText(@Nullable CharSequence helperText) {
        this.helperText = helperText;
        return this;
    }

    public boolean isAllowEmpty() {
        return allowEmpty;
    }

    @NonNull
    public EditTextPreference setAllowEmpty(boolean allowEmpty) {
        this.allowEmpty = allowEmpty;
        return this;
    }

    public int getMinLength() {
        return minLength;
    }

    @NonNull
    public EditTextPreference setMinLength(int minLength) {
        this.minLength = minLength;
        return this;
    }

    public int getMaxLength() {
        return maxLength;
    }

    @NonNull
    public EditTextPreference setMaxLength(int maxLength) {
        this.maxLength = maxLength;
        return this;
    }

    @NonNull
    public PreferenceCallback<EditTextPreference> getCallback() {
        return callback;
    }

    @NonNull
    public EditTextPreference setCallback(@NonNull PreferenceCallback<EditTextPreference> callback) {
        this.callback = callback;
        return this;
    }

    public static void bindTo(@NonNull ListAdapter listAdapter) {
        listAdapter.bind(EditTextPreference.class, new ViewBinder());
    }

    public static class ViewBinder extends ItemViewBinder.Factory<EditTextPreference, ViewHolder> {

        public ViewBinder() {
            super(R.layout.preference_listitem_edittext);
        }

        @NonNull
        @Override
        public ViewHolder createViewHolder(@NonNull View itemView) {
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, EditTextPreference preference, int position) {
            holder.setPreference(preference, position);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView preferenceTitle;
        public final TextView preferenceSummary;
        public final View preferenceItemLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            preferenceTitle = itemView.findViewById(R.id.preference_title);
            preferenceSummary = itemView.findViewById(R.id.preference_summary);
            preferenceItemLayout = itemView.findViewById(R.id.preference_item_layout);
        }

        public void setPreference(@NonNull final EditTextPreference preference, final int position) {
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

        @SuppressLint("InflateParams")
        protected void showInnerDialog(
                @NonNull final Context context,
                @NonNull final EditTextPreference preference,
                final int position
        ) {
            final View contentView = LayoutInflater.from(context).inflate(R.layout.preference_dialog_edittext, null, false);
            final MaterialEditText editText = contentView.findViewById(R.id.editText);
            final AlertDialog dialog = new AlertDialog.Builder(context)
                    .setTitle(preference.getTitle())
                    .setView(contentView)
                    .setNegativeButton(android.R.string.cancel, null)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            doCallback(preference, editText, position);
                        }
                    })
                    .create();
            dialog.show();
            @Nullable final Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            // AlertDialogCompat.setDialogWindowBackground(context, dialog, Color.WHITE);
            final Window window = dialog.getWindow();
            if (window != null) {
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            }
            editText.setText(preference.summary);
            editText.setInputType(preference.inputType);
            if (preference.maxLength != -1) {
                editText.setMaxCharacters(preference.maxLength);
            }
            if (preference.minLength != -1) {
                editText.setMinCharacters(preference.minLength);
            }
            editText.setHint(preference.hint);
            editText.setHelperText(preference.helperText);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    onPreferenceChanged(positiveButton, preference, editText);
                }
            });
            onPreferenceChanged(positiveButton, preference, editText);
            // 强制显示输入法
            editText.requestFocus();
            editText.post(new Runnable() {
                @Override
                public void run() {
                    toggleSoftInput(editText, true);
                }
            });
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    toggleSoftInput(editText, false);
                }
            });
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    toggleSoftInput(editText, false);
                }
            });
        }

        protected void onPreferenceChanged(
                Button positiveButton,
                @NonNull EditTextPreference preference,
                @NonNull MaterialEditText editText
        ) {
            if (positiveButton != null) {
                if (preference.allowEmpty) {
                    positiveButton.setEnabled(true);
                } else {
                    positiveButton.setEnabled(!TextUtils.isEmpty(editText.getText()));
                }
            }
        }


        protected void doCallback(@NonNull EditTextPreference preference, MaterialEditText editText, int position) {
            // old
            String oldSummary = preference.getSummary();
            // new
            Editable editable = editText.getText();
            String newSummary = editable == null ? "" : editable.toString();
            // set new
            preference.setSummary(newSummary);
            // callback
            if (preference.callback.invoke(preference, position)) {
                setPreference(preference, position);
            } else {
                preference.setSummary(oldSummary);
            }
        }

        protected void toggleSoftInput(@NonNull EditText editText, boolean show) {
            final Context context = editText.getContext();
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                if (show) {
                    imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
                } else {
                    imm.hideSoftInputFromWindow(editText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }

    }


}
