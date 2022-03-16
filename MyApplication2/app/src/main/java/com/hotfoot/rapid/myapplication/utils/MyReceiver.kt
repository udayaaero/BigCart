package com.hotfoot.rapid.myapplication.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.hotfoot.rapid.myapplication.CallBack.MyBroadcastListener

open class MyReceiver() : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {
        context.sendBroadcast(Intent("BROADCAST_INTENT"))
    }

}