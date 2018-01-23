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

package com.mindfcuk.skeleton.ui.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.*
import com.mindfcuk.skeleton.di.module.ActivityModule
import com.mindfcuk.skeleton.di.component.ActivityComponent
import com.squareup.leakcanary.RefWatcher
import javax.inject.Inject
import android.support.v7.app.AppCompatActivity
import io.realm.Realm


/**
 * Created by Ujjwal on 22/01/18.
 */
//abstract class BaseActivity<B : ViewDataBinding, V : MvvmViewModel> : AppCompatActivity() {
//
//
//    // Inject a Realm instance into every Activity, since the instance
//    // is cached and reused for a thread (avoids create/destroy overhead)
//    @Inject protected var realm: Realm? = null
//
//    protected var binding: B? = null
//
//    @Inject protected var viewModel: V? = null
//
//    @Inject protected var refWatcher: RefWatcher? = null
//
//    private var mActivityComponent: ActivityComponent? = null
//
//    @CallSuper
//    override fun onSaveInstanceState(outState: Bundle?) {
//        super.onSaveInstanceState(outState)
//        viewModel?.saveInstanceState(outState)
//    }
//
//    @CallSuper
//    override fun onDestroy() {
//        super.onDestroy()
//        refWatcher?.watch(mActivityComponent)
//        refWatcher?.watch(viewModel)
//        viewModel?.detachView()
//        binding = null
//        viewModel = null
//        mActivityComponent = null
//        realm?.close()
//    }
//
//    protected fun activityComponent(): ActivityComponent {
//        if (mActivityComponent == null) {
//            mActivityComponent = DaggerActivityComponent.builder()
//                    .activityModule(ActivityModule(this))
//                    .appComponent(CountriesApp.getAppComponent())
//                    .build()
//        }
//        return mActivityComponent
//
//    }
//
//    /* Sets the content view, creates the binding and attaches the view to the view model */
//    protected fun setAndBindContentView(@Nullable savedInstanceState: Bundle, @LayoutRes layoutResID: Int) {
//        if (viewModel == null) {
//            throw IllegalStateException("viewModel must already be set via injection")
//        }
//        binding = DataBindingUtil.setContentView(this, layoutResID)
//        binding?.setVariable(BR.vm, viewModel)
//
//        try {
//
//            viewModel!!.attachView(this as MvvmView, savedInstanceState)
//        } catch (e: ClassCastException) {
//            if (viewModel !is NoOpViewModel) {
//                throw RuntimeException(javaClass.simpleName + " must implement MvvmView subclass as declared in " + viewModel!!.getClass().getSimpleName())
//            }
//        }
//
//    }
//
//    fun dimen(@DimenRes resId: Int): Int {
//        return resources.getDimension(resId).toInt()
//    }
//
//    fun color(@ColorRes resId: Int): Int {
//        return resources.getColor(resId)
//    }
//
//    fun integer(@IntegerRes resId: Int): Int {
//        return resources.getInteger(resId)
//    }
//
//    fun string(@StringRes resId: Int): String {
//        return resources.getString(resId)
//    }
//}