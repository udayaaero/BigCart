package com.hotfoot.rapid.myapplication.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hotfoot.rapid.myapplication.Model.GroceryResponse
import com.hotfoot.rapid.myapplication.MyApplication
import com.hotfoot.rapid.myapplication.R
import com.hotfoot.rapid.myapplication.network.ApiHelper
import com.hotfoot.rapid.myapplication.utils.AppUtils
import com.hotfoot.rapid.myapplication.utils.Event
import com.hotfoot.rapid.myapplication.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class GroceryViewModel(app: Application, private val apiHelper: ApiHelper) : AndroidViewModel(app) {

    private val grocery_Response = MutableLiveData<Event<Resource<GroceryResponse>>>()
    val grocery: LiveData<Event<Resource<GroceryResponse>>> = grocery_Response


    fun getGroceryList() = viewModelScope.launch(Dispatchers.IO) {
        getGrocery()
    }


    private suspend fun getGrocery() {
        grocery_Response.postValue(Event(Resource.Loading()))
        try {
            if (AppUtils.hasInternetConnection(getApplication<MyApplication>())) {
                val response = apiHelper.getGrocery()
                grocery_Response.postValue(handleResponse(response))
            } else {
                grocery_Response.postValue(Event(Resource.Error(getApplication<MyApplication>().getString(R.string.no_internet_connection))))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> {
                    grocery_Response.postValue(
                        Event(Resource.Error(
                            getApplication<MyApplication>().getString(
                                R.string.no_internet_connection
                            )
                        ))
                    )
                }
                else -> {
                    grocery_Response.postValue(
                        Event(Resource.Error(
                            getApplication<MyApplication>().getString(
                                R.string.try_again
                            )
                        ))
                    )
                }
            }
        }
    }
    private fun handleResponse(response: Response<GroceryResponse>): Event<Resource<GroceryResponse>>{
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Event(Resource.Success(resultResponse))
            }
        }
        return Event(Resource.Error(response.message()))
    }
}