package br.com.bancopan.topgames.core

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding

abstract class CoreVMActivity<T : CoreVM> : CoreActivity() {

    protected lateinit var viewModel: T


    protected fun setContentView(layoutResID: Int, vm: Class<T>) {
//        this.setContentView(layoutResID)
        setViewModel(vm)


        ////        viewModel = ViewModelProviders.of(this).get(GameListVM::class.java)

        viewModel = ViewModelProviders.of(this).get(vm)
        DataBindingUtil.setContentView<ViewDataBinding>(this, layoutResID)
        binding = DataBindingUtil.setContentView(this, layoutResID)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)


    }

    protected fun setViewModel(vm: Class<T>) {
        viewModel = ViewModelProviders.of(this).get(vm)
    }

}
