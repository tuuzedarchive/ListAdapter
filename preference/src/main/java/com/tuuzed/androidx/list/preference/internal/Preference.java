package com.tuuzed.androidx.list.preference.internal;

import androidx.annotation.NonNull;

public abstract class Preference<P extends Preference<P>> {

    @NonNull
    protected String title;

    public Preference(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    @NonNull
    public P setTitle(@NonNull String title) {
        this.title = title;
        //noinspection unchecked
        return (P) this;
    }

}
