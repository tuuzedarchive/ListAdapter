package com.tuuzed.androidx.list.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ListItemDivider extends RecyclerView.ItemDecoration {

    public static final int HORIZONTAL = LinearLayoutManager.HORIZONTAL;
    public static final int VERTICAL = LinearLayoutManager.VERTICAL;

    @IntDef({HORIZONTAL, VERTICAL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Orientation {
    }

    @NonNull
    private Context context;
    @Px
    private int size;
    @Nullable
    private Drawable divider;
    @Px
    private int paddingStart;
    @Px
    private int paddingEnd;

    @Orientation
    private int orientation;

    public ListItemDivider(
            @NonNull Context context,
            @Orientation int orientation,
            @Px int size,
            @Nullable Drawable divider,
            @Px int paddingStart,
            @Px int paddingEnd
    ) {
        this.context = context;
        this.size = size;
        this.orientation = orientation;
        if (divider != null) {
            this.divider = divider;
        } else {
            this.divider = getDefaultListDivider();
        }
        this.paddingStart = paddingStart;
        this.paddingEnd = paddingEnd;
    }

    @Override
    public void getItemOffsets(
            @NonNull Rect outRect,
            @NonNull View view,
            @NonNull RecyclerView parent,
            @NonNull RecyclerView.State state
    ) {
        super.getItemOffsets(outRect, view, parent, state);
        switch (orientation) {
            case HORIZONTAL:
                outRect.bottom = size;
                break;
            case VERTICAL:
                outRect.right = size;
                break;
        }
    }

    @Override
    public void onDraw(
            @NonNull Canvas c,
            @NonNull RecyclerView parent,
            @NonNull RecyclerView.State state
    ) {
        super.onDraw(c, parent, state);
        if (divider == null) {
            return;
        }
        switch (orientation) {
            case HORIZONTAL:
                drawHorizontalDivider(divider, c, parent, state);
                break;
            case VERTICAL:
                drawVerticalDivider(divider, c, parent, state);
                break;
        }
    }

    private void drawHorizontalDivider(
            @NonNull Drawable divider,
            @NonNull Canvas c,
            @NonNull RecyclerView parent,
            @NonNull RecyclerView.State state
    ) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int left = child.getLeft() + paddingStart;
            int right = child.getRight() - paddingEnd;
            int top = child.getBottom();
            int bottom = top + size;
            divider.setBounds(left, top, right, bottom);
            divider.draw(c);
        }
    }

    private void drawVerticalDivider(
            @NonNull Drawable divider,
            @NonNull Canvas c,
            @NonNull RecyclerView parent,
            @NonNull RecyclerView.State state
    ) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int left = child.getLeft();
            int right = left + size;
            int top = child.getTop() + paddingStart;
            int bottom = child.getBottom() - paddingEnd;
            divider.setBounds(left, top, right, bottom);
            divider.draw(c);
        }
    }

    @Nullable
    private Drawable getDefaultListDivider() {
        TypedArray a = context.obtainStyledAttributes(new int[]{android.R.attr.listDivider});
        Drawable drawable = a.getDrawable(0);
        a.recycle();
        return drawable;
    }


    @NonNull
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        @Px
        private int size = 1;
        @Nullable
        private Drawable divider = null;
        @Px
        private int paddingStart = 0;
        @Px
        private int paddingEnd = 0;
        @Orientation
        private int orientation = HORIZONTAL;

        @NonNull
        public Builder setSize(int size) {
            this.size = size;
            return this;
        }

        @NonNull
        public Builder setDivider(@Nullable Drawable divider) {
            this.divider = divider;
            return this;
        }

        @NonNull
        public Builder setPaddingStart(@Px int paddingStart) {
            this.paddingStart = paddingStart;
            return this;
        }

        @NonNull
        public Builder setPaddingEnd(@Px int paddingEnd) {
            this.paddingEnd = paddingEnd;
            return this;
        }

        @NonNull
        public Builder setOrientation(@Orientation int orientation) {
            this.orientation = orientation;
            return this;
        }

        @NonNull
        public ListItemDivider build(@NonNull Context context) {
            return new ListItemDivider(context, orientation, size, divider, paddingStart, paddingEnd);
        }
    }


}
