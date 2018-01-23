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

import io.reactivex.Observable
import io.realm.RealmResults
import io.realm.RealmChangeListener
import io.reactivex.ObservableEmitter
import io.realm.RealmObject
import io.reactivex.ObservableOnSubscribe



/**
 * Created by Ujjwal on 23/01/18.
 */
class RealmResultObservable<T : RealmObject> private constructor(private val realmResults: RealmResults<T>) : ObservableOnSubscribe<RealmResults<T>> {

    @Throws(Exception::class)
    override fun subscribe(emitter: ObservableEmitter<RealmResults<T>>) {
        val changeListener = RealmChangeListener<RealmResults<T>> { emitter.onNext(it) }
        realmResults.addChangeListener(changeListener)
        emitter.setCancellable { realmResults.removeChangeListener(changeListener) }
    }

    companion object {

        fun <T : RealmObject> from(realmResults: RealmResults<T>): Observable<RealmResults<T>> {
            return Observable.create(RealmResultObservable(realmResults))
        }
    }
}
