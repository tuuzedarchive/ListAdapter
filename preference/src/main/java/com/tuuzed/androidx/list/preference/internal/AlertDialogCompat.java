package com.tuuzed.androidx.list.preference.internal;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.Window;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import com.tuuzed.androidx.list.preference.R;

public final class AlertDialogCompat {

    public static void setDialogWindowBackground(@NonNull Context context, Dialog dialog, @ColorInt int defColor) {
        if (dialog == null) {
            return;
        }
        final Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        Drawable drawable = null;
        try {
            drawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.preference_dialog_windown_bg, context.getTheme());
        } catch (Resources.NotFoundException e) {
            // pass
        }
        if (drawable == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setBackgroundDrawable(drawable);
        } else {
            int color = getWindowBackgroundColor(context, defColor);
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTintList(drawable, new ColorStateList(
                    new int[][]{new int[]{}},
                    new int[]{color})
            );
            window.setBackgroundDrawable(drawable);
        }
    }

    @ColorInt
    private static int getWindowBackgroundColor(@NonNull Context context, @ColorInt int defColor) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.windowBackground, typedValue, true);
        int[] attribute = new int[]{android.R.attr.windowBackground};
        TypedArray array = context.obtainStyledAttributes(typedValue.resourceId, attribute);
        int color = array.getColor(0, defColor);
        array.recycle();
        return color;
    }

}
