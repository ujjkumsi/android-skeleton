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

package com.mindfcuk.skeleton.di.component

import android.content.Context
import com.mindfcuk.skeleton.di.module.ApiModule
import com.mindfcuk.skeleton.di.module.AppModule
import com.mindfcuk.skeleton.di.scope.UserScope
import dagger.Component
import com.google.gson.Gson
import android.content.SharedPreferences
import com.mindfcuk.skeleton.SkeletonApplication
import com.mindfcuk.skeleton.db.SkeletonDb
import com.mindfcuk.skeleton.network.client.ApiClient
import okhttp3.OkHttpClient
import javax.inject.Named


/**
 * Created by Ujjwal on 03/01/18.
 */
@UserScope
@Component( modules = arrayOf(ApiModule::class, AppModule::class))
interface APIComponent {
    @Named("ApiHttpClient")
    fun okHttpClient(): OkHttpClient

    fun sharedPreferences(): SharedPreferences

    fun getGson(): Gson

    fun getApplication(): SkeletonApplication

    fun getContext(): Context

    fun getDatabase(): SkeletonDb

    fun getApiClient(): ApiClient

}