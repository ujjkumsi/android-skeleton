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

package com.mindfcuk.skeleton.db.model

import com.mindfcuk.skeleton.util.ListParcelPropConverter
import io.realm.*
import io.realm.annotations.Ignore
import io.realm.annotations.LinkingObjects
import io.realm.annotations.PrimaryKey
import org.parceler.Parcel
import org.parceler.ParcelPropertyConverter


/**
 * Created by Ujjwal on 18/01/18.
 */
open class Cat : RealmObject() {
    var name: String? = null
}

open class Dog : RealmObject() {
    var name: String? = null
    @LinkingObjects("dog")
    val owners: RealmResults<Person>? = null
}

open class Person(
        // You can put properties in the constructor as long as all of them are initialized with
        // default values. This ensures that an empty constructor is generated.
        // All properties are by default persisted.
        // Properties can be annotated with PrimaryKey or Index.
        // If you use non-nullable types, properties must be initialized with non-null values.
        @PrimaryKey var id: Long = 0,

        var name: String = "",

        var age: Int = 0,

        // Other objects in a one-to-one relation must also subclass RealmObject
        var dog: Dog? = null,

        // One-to-many relations is simply a RealmList of the objects which also subclass RealmObject
        var cats: RealmList<Cat> = RealmList(),

        // You can instruct Realm to ignore a field and not persist it.
        @Ignore var tempReference: Int = 0

) : RealmObject() {
    // The Kotlin compiler generates standard getters and setters.
    // Realm will overload them and code inside them is ignored.
    // So if you prefer you can also just have empty abstract methods.
}

@Parcel(implementations = arrayOf(CountryRealmProxy::class),
        value = Parcel.Serialization.FIELD,
        analyze = arrayOf(Country::class))
open class Country : RealmObject(), Comparable<Country> {

    @PrimaryKey
    var alpha2Code: String? = null
    var alpha3Code: String? = null
    var name: String? = null
    var nativeName: String? = null
    var region: String? = null
    var capital: String? = null
    @ParcelPropertyConverter(ListParcelPropConverter::class)
    var currencies: RealmList<RealmString>? = null
    @ParcelPropertyConverter(ListParcelPropConverter::class)
    var borders: RealmList<RealmString>? = null
    @ParcelPropertyConverter(ListParcelPropConverter::class)
    var languages: RealmList<RealmString>? = null
    @ParcelPropertyConverter(ListParcelPropConverter::class)
    var translations: RealmList<RealmStringMapEntry>? = null
    var population: Int? = null
    var lat: Float? = null
    var lng: Float? = null

    override fun compareTo(other: Country): Int {
        return if (name != null && other.name != null) {
            name!!.compareTo(other.name!!)
        } else {
            0
        }
    }
}

@Parcel(implementations = arrayOf(RealmStringRealmProxy::class),
        value = Parcel.Serialization.FIELD,
        analyze = arrayOf(RealmString::class))
open class RealmString : RealmObject() {
    var value: String? = null
}

@Parcel(implementations = arrayOf(RealmStringMapEntryRealmProxy::class),
        value = Parcel.Serialization.FIELD,
        analyze = arrayOf(RealmStringMapEntry::class))
open class RealmStringMapEntry : RealmObject() {
    var key: String? = null
    var value: String? = null
}

