package br.com.bancopan.topgames.core

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding

abstract class CoreVMActivity<T : CoreVM, V: ViewDataBinding> : CoreActivity() {

    protected lateinit var viewModel: T
    protected lateinit var binding: V

    protected fun setContentView(layoutResID: Int, vm: Class<T>) {
        viewModel = ViewModelProviders.of(this).get(vm)
        DataBindingUtil.setContentView<ViewDataBinding>(this, layoutResID)
        binding = DataBindingUtil.setContentView(this, layoutResID)
        binding.setLifecycleOwner(this)
        setViewModel()
    }

    protected abstract fun setViewModel()

}
