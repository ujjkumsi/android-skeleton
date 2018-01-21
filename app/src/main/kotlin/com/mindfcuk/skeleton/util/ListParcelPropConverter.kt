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

import android.os.Parcel
import android.os.Parcelable
import org.parceler.Parcels
import io.realm.RealmObject
import io.realm.RealmList
import org.parceler.TypeRangeParcelConverter



/**
 * Created by Ujjwal on 20/01/18.
 */
class ListParcelPropConverter : TypeRangeParcelConverter<RealmList<out RealmObject>, RealmList<out RealmObject>> {

    override
    fun toParcel(input: RealmList<out RealmObject>?, parcel: Parcel) {
        if (input == null) {
            parcel.writeInt(NULL)
        } else {
            parcel.writeInt(input.size)
            for (item in input) {
                parcel.writeParcelable(Parcels.wrap(item), 0)
            }
        }
    }

    override
    fun fromParcel(parcel: Parcel):  RealmList<out RealmObject> {
        val size = parcel.readInt()
        val list = RealmList<RealmObject>()

        for (i in 0 until size) {
            val parcelable = parcel.readParcelable<Parcelable>(javaClass.classLoader)
            list.add(Parcels.unwrap<Any>(parcelable) as RealmObject)
        }

        return list
    }

    companion object {
        private val NULL = -1
    }
}