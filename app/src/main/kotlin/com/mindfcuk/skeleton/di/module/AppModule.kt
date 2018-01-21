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

package com.mindfcuk.skeleton.di.module

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.mindfcuk.skeleton.BuildConfig
import com.mindfcuk.skeleton.di.qualifiers.AppContext
import io.realm.RealmConfiguration
import dagger.Provides
import com.mindfcuk.skeleton.di.scope.PerApplication
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import dagger.Module
import io.realm.Realm
import java.lang.ref.WeakReference


/**
 * Created by Ujjwal on 18/01/18.
 */
@Module
class AppModule(private val mApp: Application) {

    @Provides
    @PerApplication
    @AppContext
    internal fun provideAppContext(): Context? {
        return WeakReference<Context>(mApp).get()
    }

    @Provides
    @PerApplication
    internal fun provideResources(): Resources? {
        return WeakReference<Resources>(mApp.resources).get()
    }


    @Provides
    @PerApplication
    internal fun provideRefWatcher(): RefWatcher {
        return LeakCanary.install(mApp)
    }

    @Provides
    @PerApplication
    internal fun provideRealmConfiguration(): RealmConfiguration {
        var builder = RealmConfiguration.Builder()
        if (BuildConfig.DEBUG) {
            builder = builder.deleteRealmIfMigrationNeeded()
        }
        return builder.build()
    }

    @Provides
    internal fun provideRealm(realmConfiguration: RealmConfiguration): Realm {
        return Realm.getInstance(realmConfiguration)
    }

}