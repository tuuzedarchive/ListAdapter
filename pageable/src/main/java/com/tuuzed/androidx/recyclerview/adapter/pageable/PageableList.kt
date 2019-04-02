package com.tuuzed.androidx.recyclerview.adapter.pageable

data class PageableList(
        var error: Boolean = true,
        var msg: String? = null,
        var payload: List<*> = emptyList<Any>(),
        var tr: Throwable? = null
)