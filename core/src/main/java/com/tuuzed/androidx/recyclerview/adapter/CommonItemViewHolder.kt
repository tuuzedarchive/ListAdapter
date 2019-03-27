package com.tuuzed.androidx.recyclerview.adapter

import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.SparseArray
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.*
import androidx.recyclerview.widget.RecyclerView

class CommonItemViewHolder : RecyclerView.ViewHolder {

    private val mViewHolder: SparseArray<View>

    constructor(itemView: View) : super(itemView) {
        mViewHolder = SparseArray()
    }

    constructor(itemView: View, initialCapacity: Int) : super(itemView) {
        mViewHolder = SparseArray(initialCapacity)
    }

    @IntDef(View.VISIBLE, View.INVISIBLE, View.GONE)
    @Retention(AnnotationRetention.SOURCE)
    internal annotation class Visibility

    fun visibility(@IdRes id: Int, @Visibility visibility: Int): CommonItemViewHolder {
        find(id, View::class.java).visibility = visibility
        return this
    }


    fun text(@IdRes id: Int, text: CharSequence): CommonItemViewHolder {
        findTextView(id).text = text
        return this
    }

    fun text(@IdRes id: Int, text: CharSequence, type: TextView.BufferType): CommonItemViewHolder {
        findTextView(id).setText(text, type)
        return this
    }

    fun text(@IdRes id: Int, text: CharArray, start: Int, len: Int): CommonItemViewHolder {
        findTextView(id).setText(text, start, len)
        return this
    }

    fun text(@IdRes id: Int, @StringRes resId: Int): CommonItemViewHolder {
        findTextView(id).setText(resId)
        return this
    }

    fun text(@IdRes id: Int, @StringRes resId: Int, type: TextView.BufferType): CommonItemViewHolder {
        findTextView(id).setText(resId, type)
        return this
    }

    fun textColor(@IdRes id: Int, @ColorInt color: Int): CommonItemViewHolder {
        findTextView(id).setTextColor(color)
        return this
    }

    fun textColor(@IdRes id: Int, colors: ColorStateList): CommonItemViewHolder {
        findTextView(id).setTextColor(colors)
        return this
    }

    fun background(@IdRes id: Int, background: Drawable): CommonItemViewHolder {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            find(id, View::class.java).background = background
        } else {
            @Suppress("DEPRECATION")
            find(id, View::class.java).setBackgroundDrawable(background)
        }
        return this
    }

    fun backgroundResource(@IdRes id: Int, @DrawableRes resId: Int): CommonItemViewHolder {
        find(id, View::class.java).setBackgroundResource(resId)
        return this
    }

    fun backgroundColor(@IdRes id: Int, @ColorInt color: Int): CommonItemViewHolder {
        find(id, View::class.java).setBackgroundColor(color)
        return this
    }

    fun image(@IdRes id: Int, @DrawableRes resId: Int): CommonItemViewHolder {
        findImageView(id).setImageResource(resId)
        return this
    }

    fun image(@IdRes id: Int, drawable: Drawable): CommonItemViewHolder {
        findImageView(id).setImageDrawable(drawable)
        return this
    }

    fun image(@IdRes id: Int, bitmap: Bitmap): CommonItemViewHolder {
        findImageView(id).setImageBitmap(bitmap)
        return this
    }

    fun click(@IdRes id: Int, onClick: OnClick?): CommonItemViewHolder {
        if (onClick == null) {
            find<View>(id).setOnClickListener(null)
        } else {
            find<View>(id).setOnClickListener { onClick(it) }
        }
        return this
    }

    fun longClick(@IdRes id: Int, onLongClick: OnLongClick?): CommonItemViewHolder {
        if (onLongClick == null) {
            find<View>(id).setOnLongClickListener(null)
        } else {
            find<View>(id).setOnLongClickListener { onLongClick(it) }
        }
        return this
    }

    fun <T : View> find(@IdRes id: Int, type: Class<T>): T = find(id)

    fun <T : View> find(@IdRes id: Int): T {
        var view: View? = mViewHolder.get(id)
        if (view == null) {
            view = itemView.findViewById(id)
            if (view == null) throw IllegalArgumentException("find# id not find")
            mViewHolder.put(id, view)
        }
        @Suppress("UNCHECKED_CAST")
        return view as T
    }


    private fun findTextView(@IdRes id: Int) = find(id, TextView::class.java)
    private fun findImageView(@IdRes id: Int) = find(id, ImageView::class.java)
}