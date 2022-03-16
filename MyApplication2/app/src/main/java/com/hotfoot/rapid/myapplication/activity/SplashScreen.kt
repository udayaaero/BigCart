package com.hotfoot.rapid.myapplication.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.hotfoot.rapid.myapplication.Model.Products
import com.hotfoot.rapid.myapplication.R
import com.hotfoot.rapid.myapplication.roomDB.ProductDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        deleteAllProducts()
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }

    private fun deleteAllProducts() {
          CoroutineScope(Dispatchers.IO).launch {
             ProductDB.getInstance(this@SplashScreen).productDao().deleteTable() }
    }
}