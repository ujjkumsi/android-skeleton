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

package com.mindfcuk.skeleton.db.repository
import com.mindfcuk.skeleton.db.model.Country
import io.realm.Sort
import io.realm.RealmResults
import javax.inject.Inject
import android.annotation.SuppressLint
import com.mindfcuk.skeleton.di.scope.PerApplication
import com.mindfcuk.skeleton.util.RealmResultObservable
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.realm.Realm
import javax.inject.Provider


/**
 * Responsibilities
 *
 */

@PerApplication
@SuppressLint("NewApi") // try-with-resources is backported by retrolambda
class RealmCountryRepo @Inject
constructor(private val realmProvider: Provider<Realm>) : ExampleRepo {

    private val favoriteChangeSubject: PublishSubject<String> = PublishSubject.create()

    override val favoriteChangeObservable: Observable<String> get() = favoriteChangeSubject

    override fun findAllSorted(sortField: String, sort: Sort, detached: Boolean): List<Country> {
        realmProvider.get().use({ realm ->
            val realmResults = realm.where(Country::class.java).findAllSorted(sortField, sort)

            return if (detached) {
                realm.copyFromRealm(realmResults)
            } else {
                realmResults
            }
        })
    }

    override fun findAllSortedWithChanges(sortField: String, sort: Sort): Observable<List<Country>> {
        realmProvider.get().use({ realm ->
            return RealmResultObservable.from(realm.where(Country::class.java).findAllSortedAsync(sortField, sort))
                    .filter(RealmResults<Country>::isLoaded)
                    .map { result -> result }
        })
    }

    override fun getByField(field: String, value: String, detached: Boolean): Country? {
        realmProvider.get().use({ realm ->
            var realmCountry = realm.where(Country::class.java).equalTo(field, value).findFirst()
            if (detached && realmCountry != null) {
                realmCountry = realm.copyFromRealm(realmCountry)
            }
            return realmCountry
        })
    }

    override fun save(country: Country) {
        realmProvider.get().use({ realm ->
            realm.executeTransaction({ r -> r.copyToRealmOrUpdate(country) })
            favoriteChangeSubject.onNext(country.alpha2Code?: "")
        })
    }

    override fun delete(realmCountry: Country) {
        if (realmCountry.isValid) {
            realmProvider.get().use({ realm ->
                val alpha2Code = realmCountry.alpha2Code

                realm.executeTransaction({ _ ->
                    realmCountry.borders!!.deleteAllFromRealm()
                    realmCountry.currencies!!.deleteAllFromRealm()
                    realmCountry.languages!!.deleteAllFromRealm()
                    realmCountry.translations!!.deleteAllFromRealm()
                    realmCountry.deleteFromRealm()
                })

                favoriteChangeSubject.onNext(alpha2Code ?: "")
            })
        }
    }

    override fun detach(country: Country): Country {
        if (country.isManaged) {
            realmProvider.get().use({ realm -> return realm.copyFromRealm(country) })
        } else {
            return country
        }
    }
}