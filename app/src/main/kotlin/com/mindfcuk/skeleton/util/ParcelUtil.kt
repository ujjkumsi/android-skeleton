/*******************************************************************************
 * Copyright 2018 mindfcuk
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 ******************************************************************************/

package com.mindfcuk.skeleton.util

import org.parceler.Parcels
import android.os.Bundle
import android.os.Parcelable
import android.support.annotation.NonNull
import android.support.annotation.Nullable


/**
 * Created by Ujjwal on 20/01/18.
 */
object ParcelUtil {

    /* Gets a Parcelable wrapped with Parceler from a Bundle and returns null
     * if the bundle does not contain a value for key. */
    @Nullable
    fun <T> getParcelable(@NonNull bundle: Bundle, @NonNull key: String): T? {

        return getParcelable<T>(bundle, key, null)
    }

    /* Gets a Parcelable wrapped with Parceler from a Bundle and returns defaultObject
     * if the bundle does not contain a value for key. */
    @NonNull
    fun <T> getParcelable(@NonNull bundle: Bundle, @NonNull key: String, @NonNull defaultObject: T?): T? {
        return if (bundle.containsKey(key)) {
            Parcels.unwrap<T>(bundle.getParcelable<Parcelable>(key))
        } else {
            defaultObject
        }
    }
}