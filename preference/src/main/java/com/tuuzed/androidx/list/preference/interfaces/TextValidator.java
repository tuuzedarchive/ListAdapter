package com.tuuzed.androidx.list.preference.interfaces;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Size;

public interface TextValidator {

    boolean test(@Nullable CharSequence text, @NonNull @Size(1) String[] errorText);

}
