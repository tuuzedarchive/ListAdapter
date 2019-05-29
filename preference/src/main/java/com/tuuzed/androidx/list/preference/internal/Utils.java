package com.tuuzed.androidx.list.preference.internal;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import androidx.annotation.NonNull;

public class Utils {

    @SuppressWarnings("EqualsReplaceableByObjectsCall")
    public static boolean equals(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }

    public static boolean hasTrue(boolean[] array) {
        if (array == null) return false;
        for (boolean it : array) {
            if (it) return true;
        }
        return false;
    }

    public static int getTrueCount(boolean[] array) {
        if (array == null) return 0;
        int count = 0;
        for (boolean it : array) {
            if (it) ++count;
        }
        return count;
    }

    public static void toggleSoftInput(@NonNull EditText editText, boolean show) {
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
