package com.tuuzed.androidx.list.preference;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface OptionToStringFunction<T> {
    @NonNull
    CharSequence invoke(@Nullable T option);
}
