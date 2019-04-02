package com.tuuzed.androidx.recyclerview.adapter

import android.view.View
import android.view.ViewGroup

typealias OnClick = (View) -> Unit
typealias OnLongClick = (View) -> Boolean

typealias  OnCreateViewHolder<VH> = (parent: ViewGroup, viewType: Int) -> VH
typealias OnBindViewHolder<T, VH> = (holder: VH, item: T, position: Int) -> Unit
