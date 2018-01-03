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

import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import okhttp3.OkHttpClient
import com.google.gson.Gson
import com.mindfcuk.skeleton.di.scope.UserScope
import dagger.Module
import java.util.concurrent.TimeUnit
import javax.inject.Named
import com.mindfcuk.skeleton.network.client.ApiClient


/**
 * Created by Ujjwal on 03/01/18.
 */
@UserScope
@Module
class ApiModule(internal var baseUrl: String) {


    @Provides
    @UserScope
    @Named("ApiHttpClient")
    internal fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

    @Provides
    @UserScope
    @Named("ApiRetrofit")
    fun provideRetrofit(gson: Gson, @Named("ApiHttpClient") okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()

    @Provides
    @UserScope
    fun getApiClient(@Named("ApiRetrofit") retrofit: Retrofit): ApiClient = retrofit.create(ApiClient::class.java)

}