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
import android.view.ViewGroup
import android.view.LayoutInflater
import com.mindfcuk.skeleton.di.module.FragmentModule
import android.support.v4.app.Fragment
import com.mindfcuk.skeleton.BR
import com.mindfcuk.skeleton.di.component.FragmentComponent
import com.squareup.leakcanary.RefWatcher
import javax.inject.Inject


/**
 * Created by Ujjwal on 22/01/18.
 */
abstract class BaseFragment<B : ViewDataBinding, V : MvvmViewModel> : Fragment() {

    protected var binding: B? = null
    @Inject protected var viewModel: V? = null

    @Inject internal var refWatcher: RefWatcher? = null

    private var mFragmentComponent: FragmentComponent? = null

    @CallSuper
    fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel?.saveInstanceState(outState)

    }

    @CallSuper
    fun onDestroyView() {
        super.onDestroyView()
        viewModel?.detachView()
        refWatcher?.watch(viewModel)
        binding = null
        viewModel = null
    }

    @CallSuper
    fun onDestroy() {
        super.onDestroy()
        refWatcher?.watch(this)
        refWatcher?.watch(mFragmentComponent)
        mFragmentComponent = null
    }


    protected fun fragmentComponent(): FragmentComponent {
        if (mFragmentComponent == null) {
            mFragmentComponent = DaggerFragmentComponent.builder()
                    .fragmentModule(FragmentModule(this))
                    .activityComponent((activity as BaseActivity<*, *>).activityComponent())
                    .build()
        }
        return mFragmentComponent
    }

    /* Sets the content view, creates the binding and attaches the view to the view model */
    protected fun setAndBindContentView(@NonNull inflater: LayoutInflater, @Nullable container: ViewGroup, @Nullable savedInstanceState: Bundle, @LayoutRes layoutResID: Int): View {
        if (viewModel == null) {
            throw IllegalStateException("viewModel must already be set via injection")
        }
        binding = DataBindingUtil.inflate(inflater, layoutResID, container, false)
        binding!!.setVariable(BR.vm, viewModel)

        try {

            viewModel?.attachView(this as MvvmView, savedInstanceState)
        } catch (e: ClassCastException) {
            if (viewModel !is NoOpViewModel) {
                throw RuntimeException(getClass().getSimpleName() + " must implement MvvmView subclass as declared in " + viewModel?.getClass().getSimpleName())
            }
        }

        return binding!!.getRoot()
    }

    fun dimen(@DimenRes resId: Int): Int {
        return getResources().getDimension(resId)
    }

    fun color(@ColorRes resId: Int): Int {
        return getResources().getColor(resId)
    }

    fun integer(@IntegerRes resId: Int): Int {
        return getResources().getInteger(resId)
    }

    fun string(@StringRes resId: Int): String {
        return getResources().getString(resId)
    }
}