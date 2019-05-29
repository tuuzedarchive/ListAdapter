package com.tuuzed.androidx.list.preference;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Size;
import androidx.appcompat.app.AlertDialog;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.tuuzed.androidx.list.adapter.CommonViewHolder;
import com.tuuzed.androidx.list.adapter.ItemViewBinder;
import com.tuuzed.androidx.list.adapter.ListAdapter;
import com.tuuzed.androidx.list.preference.internal.Preference2;
import com.tuuzed.androidx.list.preference.internal.Utils;

public class EditTextPreference extends Preference2 {
    private int inputType = InputType.TYPE_CLASS_TEXT;
    @Nullable
    private CharSequence hint = null;
    @Nullable
    private CharSequence helperText = null;
    private boolean allowEmpty = false;
    private int minLength = -1;
    private int maxLength = -1;
    @Nullable
    private TextValidator textValidator = null;
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

    @Nullable
    public TextValidator getTextValidator() {
        return textValidator;
    }

    @NonNull
    public EditTextPreference setTextValidator(@Nullable TextValidator textValidator) {
        this.textValidator = textValidator;
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
        public void onBindViewHolder(@NonNull final ViewHolder holder, final EditTextPreference preference, final int position) {
            holder.setPreference(preference);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Context context = v.getContext();
                    showInnerDialog(context, holder, preference, position);
                }
            });
        }

        @SuppressLint("InflateParams")
        protected void showInnerDialog(
                @NonNull final Context context,
                @NonNull final ViewHolder holder,
                @NonNull final EditTextPreference preference,
                final int position
        ) {
            final View contentView = LayoutInflater.from(context).inflate(
                    R.layout.preference_dialog_edittext, null, false
            );
            final MaterialEditText editText = contentView.findViewById(R.id.editText);
            final AlertDialog dialog = new AlertDialog.Builder(context)
                    .setTitle(preference.getTitle())
                    .setView(contentView)
                    .setNegativeButton(android.R.string.cancel, null)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            doCallback(holder, preference, editText, position);
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
            final String[] errorText = new String[1];
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    onPreferenceChanged(positiveButton, preference, editText, errorText);
                }
            });
            onPreferenceChanged(positiveButton, preference, editText, errorText);
            // 强制显示输入法
            editText.requestFocus();
            editText.post(new Runnable() {
                @Override
                public void run() {
                    Utils.toggleSoftInput(editText, true);
                }
            });
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    Utils.toggleSoftInput(editText, false);
                }
            });
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    Utils.toggleSoftInput(editText, false);
                }
            });
        }

        protected void onPreferenceChanged(
                Button positiveButton,
                @NonNull EditTextPreference preference,
                @NonNull MaterialEditText editText,
                @NonNull @Size(1) String[] errorText
        ) {
            final Editable text = editText.getText();
            if (preference.allowEmpty) {
                if (positiveButton != null) positiveButton.setEnabled(true);
            } else {
                if (positiveButton != null) positiveButton.setEnabled(!TextUtils.isEmpty(text));
            }
            if (preference.textValidator != null) {
                boolean pass = preference.textValidator.test(text, errorText);
                if (pass) {
                    editText.setHelperText(preference.helperText);
                    editText.setHelperTextColor(Color.GRAY);
                    if (positiveButton != null) positiveButton.setEnabled(true);
                } else {
                    editText.setHelperTextColor(Color.RED);
                    editText.setHelperText(errorText[0]);
                    if (positiveButton != null) positiveButton.setEnabled(false);
                }
            }
        }

        protected void doCallback(
                @NonNull ViewHolder holder,
                @NonNull EditTextPreference preference,
                @NonNull MaterialEditText editText,
                int position
        ) {
            // old
            String oldSummary = preference.getSummary();
            // new
            Editable editable = editText.getText();
            String newSummary = editable == null ? "" : editable.toString();
            // set new
            preference.setSummary(newSummary);
            // callback
            if (preference.callback.invoke(preference, position)) {
                holder.setPreference(preference);
            } else {
                preference.setSummary(oldSummary);
            }
        }

    }

    public static class ViewHolder extends CommonViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setPreference(@NonNull final EditTextPreference preference) {
            find(R.id.preference_title, TextView.class).setText(preference.getTitle());
            find(R.id.preference_summary, TextView.class).setText(preference.getSummary());
        }

    }


}
