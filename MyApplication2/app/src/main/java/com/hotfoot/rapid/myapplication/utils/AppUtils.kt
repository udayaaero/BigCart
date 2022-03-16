package com.hotfoot.rapid.myapplication.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import com.hotfoot.rapid.myapplication.MyApplication
import com.hotfoot.rapid.myapplication.R

import com.kaopiz.kprogresshud.KProgressHUD

object AppUtils {

   fun hasInternetConnection(application: MyApplication): Boolean {
        val connectivityManager = application.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
        return false

    }

    fun getProgressDialog(
        activity: Activity,
        toastMsg: String?,
        action: String?,
        cancelable: Boolean
    ): KProgressHUD? {
        return KProgressHUD.create(activity)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setWindowColor(activity.resources.getColor(android.R.color.transparent))
            .setAnimationSpeed(2)
            .setDimAmount(0.5f)
            .setLabel(toastMsg)
            .setDetailsLabel(action)
            .setCancellable(cancelable)
    }

    fun showDialogForLoading(mContext: Context?): Dialog {
        val progressDialog = Dialog(mContext!!)
        progressDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        progressDialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        val row: View =
            (mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
                R.layout.progress_dialog,
                null
            )
        progressDialog.window!!.setContentView(row)
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
        return progressDialog
    }

}

