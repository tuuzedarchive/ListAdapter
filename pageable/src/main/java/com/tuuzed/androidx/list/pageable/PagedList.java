package com.tuuzed.androidx.list.pageable;

import androidx.annotation.NonNull;

import java.util.Collections;
import java.util.List;

public class PagedList<T> {

    private static final Throwable defaultCause = new Throwable();
    public final boolean error;
    @NonNull
    public final Throwable cause;
    @NonNull
    public final List<T> payload;


    public static <T> PagedList<T> correct(@NonNull List<T> payload) {
        return new PagedList<>(false, defaultCause, payload);
    }

    public static <T> PagedList<T> wrong(@NonNull Throwable cause) {
        return new PagedList<>(true, cause, Collections.<T>emptyList());
    }

    private PagedList(boolean error, @NonNull Throwable cause, @NonNull List<T> payload) {
        this.error = error;
        this.cause = cause;
        this.payload = payload;
    }


    @NonNull
    @Override
    public String toString() {
        return "PagedList{" +
                "error=" + error +
                ", cause=" + cause +
                ", payload=" + payload +
                '}';
    }
}
