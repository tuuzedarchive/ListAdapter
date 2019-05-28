package com.tuuzed.androidx.list.preference.internal;

import androidx.annotation.NonNull;

public abstract class Preference2<P extends Preference2<P>> extends Preference<P> {

    @NonNull
    protected String summary;

    public Preference2(@NonNull String title, @NonNull String summary) {
        super(title);
        this.summary = summary;
    }

    @NonNull
    public String getSummary() {
        return summary;
    }

    public P setSummary(@NonNull String summary) {
        this.summary = summary;
        //noinspection unchecked
        return (P) this;
    }
}
