package com.hotfoot.rapid.myapplication.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hotfoot.rapid.myapplication.network.ApiHelper


public class ViewModelProviderFactory(
    private val app: Application,
    private val apiHelper: ApiHelper
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(GroceryViewModel::class.java)) {
            return GroceryViewModel(app, apiHelper) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }


}