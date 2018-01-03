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

package com.mindfcuk.skeleton.di.module

import android.content.Context
import javax.inject.Singleton
import dagger.Provides
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import com.google.gson.Gson
import com.mindfcuk.skeleton.SETTINGS
import com.mindfcuk.skeleton.SkeletonApplication
import dagger.Module
import android.arch.persistence.room.Room
import com.mindfcuk.skeleton.DATABASE
import com.mindfcuk.skeleton.db.SkeletonDb
import com.mindfcuk.skeleton.di.scope.UserScope




/**
 * Created by Ujjwal on 03/01/18.
 */
@Module
class AppModule(@get:Provides
                @get:Singleton
                internal var application: SkeletonApplication) {

    @Provides
    @Singleton
    internal fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    internal fun providesSharedPreferences(application: SkeletonApplication): SharedPreferences =
        application.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    internal fun getContext(application: SkeletonApplication): Context = application.applicationContext

    @Provides
    @UserScope
    fun getMTDatabase(context: Context): SkeletonDb {
        return Room.databaseBuilder(context, SkeletonDb::class.java, DATABASE).build()
    }


}