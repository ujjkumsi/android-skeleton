/*******************************************************************************
 * Copyright 2018 Ujjwal Singh, Sumit Pareek, Neeraj Sharma
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

package com.mindfcuk.skeleton

import android.app.Application
import com.facebook.stetho.Stetho
import timber.log.Timber
import timber.log.Timber.DebugTree
import android.util.Log
import io.realm.Realm


/**
 * Created by Ujjwal on 03/01/18.
 */

class SkeletonApplication: Application(){

    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)

        Realm.init(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(CrashReportingTree())
        }
    }

    /** A tree which logs important information for crash reporting.  */
    private class CrashReportingTree : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return
            }

            //TODO Add crashlytics and other library for analytics

            if (t != null) {
                if(priority == Log.ERROR) {
                    //add crashlytics
                }else if (priority == Log.WARN) {
                    //add crashlytics
                }else if(priority == Log.INFO){
                    //add analytics or mixpanel
                }
            }
        }
    }
}