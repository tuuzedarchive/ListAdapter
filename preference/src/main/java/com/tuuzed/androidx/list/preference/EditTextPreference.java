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
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Size;
import androidx.appcompat.app.AlertDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.tuuzed.androidx.list.adapter.CommonViewHolder;
import com.tuuzed.androidx.list.adapter.ListAdapter;
import com.tuuzed.androidx.list.preference.base.Preference2;
import com.tuuzed.androidx.list.preference.interfaces.PreferenceCallback;
import com.tuuzed.androidx.list.preference.interfaces.TextValidator;
import com.tuuzed.androidx.list.preference.internal.Utils;

public class EditTextPreference extends Preference2<EditTextPreference> {
    private int inputType = InputType.TYPE_CLASS_TEXT;
    @Nullable
    private CharSequence hint = null;
    @Nullable
    private CharSequence helperText = null;
    private boolean allowEmpty = false;
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
        listAdapter.bind(EditTextPreference.class, new ItemViewBinderFactory() {
        });
    }

    public abstract static class ItemViewBinderFactory extends Preference2.ItemViewBinderFactory<EditTextPreference> {
        @Override
        public int getLayoutRes() {
            return R.layout.preference_listitem_edittext;
        }

        @Override
        public void onBindViewHolder(@NonNull final CommonViewHolder holder, final EditTextPreference preference, final int position) {
            super.onBindViewHolder(holder, preference, position);
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
                @NonNull final CommonViewHolder holder,
                @NonNull final EditTextPreference preference,
                final int position
        ) {
            final View contentView = LayoutInflater.from(context).inflate(
                    R.layout.preference_dialog_edittext, null, false
            );
            final TextInputLayout textInputLayout = contentView.findViewById(R.id.textInputLayout);
            final TextInputEditText textInputEditText = contentView.findViewById(R.id.textInputEditText);
            final AlertDialog.Builder builder = new AlertDialog.Builder(context)
                    .setTitle(preference.getTitle())
                    .setView(contentView)
                    .setNegativeButton(android.R.string.cancel, null)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            doCallback(holder, preference, textInputEditText, position);
                        }
                    });
            final AlertDialog dialog = builder.create();
            dialog.show();
            @Nullable final Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            final Window window = dialog.getWindow();
            if (window != null) {
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            }
            textInputEditText.setText(preference.summary);
            textInputEditText.setInputType(preference.inputType);
            if (preference.maxLength != -1) {
                textInputLayout.setCounterEnabled(true);
                textInputLayout.setCounterMaxLength(preference.maxLength);
            }
            textInputEditText.setHint(preference.hint);
            if (preference.helperText != null) {
                textInputLayout.setHelperTextEnabled(true);
                textInputLayout.setHelperText(preference.helperText);
            }
            final String[] errorText = new String[1];
            textInputEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    onPreferenceChanged(positiveButton, preference, textInputLayout, textInputEditText, errorText);
                }
            });
            onPreferenceChanged(positiveButton, preference, textInputLayout, textInputEditText, errorText);
            // 强制显示输入法
            textInputEditText.requestFocus();
            textInputEditText.post(new Runnable() {
                @Override
                public void run() {
                    Utils.toggleSoftInput(textInputEditText, true);
                }
            });
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    Utils.toggleSoftInput(textInputEditText, false);
                }
            });
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    Utils.toggleSoftInput(textInputEditText, false);
                }
            });
        }

        protected void onPreferenceChanged(
                Button positiveButton,
                @NonNull EditTextPreference preference,
                @NonNull TextInputLayout textInputLayout,
                @NonNull TextInputEditText textInputEditText,
                @NonNull @Size(1) String[] errorText
        ) {
            final Editable text = textInputEditText.getText();
            // 空验证
            if (preference.allowEmpty) {
                if (positiveButton != null) positiveButton.setEnabled(true);
            } else {
                if (positiveButton != null) positiveButton.setEnabled(!TextUtils.isEmpty(text));
            }
            // 长度验证
            if (preference.maxLength != -1) {
                int length = TextUtils.isEmpty(text) ? 0 : text.length();
                if (positiveButton != null) positiveButton.setEnabled(length <= preference.maxLength);
            }
            // 自定义验证
            if (preference.textValidator != null) {
                boolean pass = preference.textValidator.test(text, errorText);
                if (pass) {
                    if (preference.helperText != null) {
                        textInputLayout.setHelperTextEnabled(true);
                        textInputLayout.setHelperText(preference.helperText);
                    }
                    if (positiveButton != null) positiveButton.setEnabled(true);
                } else {
                    textInputLayout.setErrorEnabled(true);
                    textInputLayout.setError(errorText[0]);
                    if (positiveButton != null) positiveButton.setEnabled(false);
                }
            }
        }

        protected void doCallback(
                @NonNull CommonViewHolder holder,
                @NonNull EditTextPreference preference,
                @NonNull TextInputEditText textInputEditText,
                int position
        ) {
            // old
            String oldSummary = preference.getSummary();
            // new
            Editable editable = textInputEditText.getText();
            String newSummary = editable == null ? "" : editable.toString();
            // set new
            preference.setSummary(newSummary);
            // callback
            if (preference.callback.invoke(preference, position)) {
                setPreference(holder, preference);
            } else {
                preference.setSummary(oldSummary);
            }
        }
    }

}
