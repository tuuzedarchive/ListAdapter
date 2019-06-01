package com.tuuzed.androidx.list.preference.interfaces;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Size;

public interface Validator<T> {

    boolean test(@Nullable T input, @NonNull @Size(1) String[] errorText);

}
