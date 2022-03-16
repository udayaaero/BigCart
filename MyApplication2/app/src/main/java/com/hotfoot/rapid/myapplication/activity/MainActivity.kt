package com.hotfoot.rapid.myapplication.activity

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.hotfoot.rapid.myapplication.CallBack.AddItemClick
import com.hotfoot.rapid.myapplication.CallBack.ItemClick
import com.hotfoot.rapid.myapplication.Model.GroceryResponse
import com.hotfoot.rapid.myapplication.Model.Products
import com.hotfoot.rapid.myapplication.R
import com.hotfoot.rapid.myapplication.adapter.GroceryAdapter
import com.hotfoot.rapid.myapplication.network.ApiHelper
import com.hotfoot.rapid.myapplication.roomDB.DbUtil
import com.hotfoot.rapid.myapplication.roomDB.ProductDB
import com.hotfoot.rapid.myapplication.utils.AppUtils
import com.hotfoot.rapid.myapplication.utils.Resource
import com.hotfoot.rapid.myapplication.viewModel.GroceryViewModel
import com.hotfoot.rapid.myapplication.viewModel.ViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.grocery_item_layout.view.*
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity(), ItemClick, AddItemClick {
    lateinit var groceryViewModel: GroceryViewModel
    var groceryAdapter: GroceryAdapter? = null
    var totalCartCount: Int = 0;
    private var dialog: Dialog? = null
    private val RECOGNIZER_REQ_CODE = 1234
    val productList = mutableListOf<Products>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewModel()
    }

    private fun setupViewModel() {
        val apiHelper = ApiHelper()
        val factory = ViewModelProviderFactory(application, apiHelper)
        groceryViewModel = ViewModelProvider(this, factory).get(GroceryViewModel::class.java)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        backArrow.setOnClickListener { finish() }

        mic.setOnClickListener{startMic()}
        cart.setOnClickListener {
            if (totalCartCount <= 0) {
                Toast.makeText(this, "Please add Cart!", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this@MainActivity, CartActivity::class.java)
                startActivity(intent)
            }
        }

        apiCall()
    }

    private fun apiCall() {
        dialog = AppUtils.showDialogForLoading(this)
        groceryViewModel.getGroceryList()
        groceryViewModel.grocery.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        response.data?.let { repositoryResponse ->
                            setGroceryAdapter(response.data)
                        }
                        hideProgressBar()
                    }

                    is Resource.Error -> {
                        if (response.message.equals(getString(R.string.no_internet_connection))) {

                        }
                        hideProgressBar()

                        response.message?.let { message ->
                            Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                    is Resource.Loading -> {
                        showProgressBar()
                    }
                }
            }
        })
    }

    private fun setGroceryAdapter(data: GroceryResponse) {

        if (groceryAdapter == null) {
            groceryAdapter = GroceryAdapter(this, data.products, this, this)
            recyclerView?.adapter = groceryAdapter
        } else {
            groceryAdapter?.notifyDataSetChanged()
        }
    }

    private fun hideProgressBar() {
        try {
            if (dialog!!.isShowing) {
                dialog!!.dismiss()
            }
        } catch (e: Exception) {
        }

    }

    private fun showProgressBar() {
        try {
            dialog!!.show()
        } catch (e: Exception) {
        }

    }
    private fun startMic() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        startActivityForResult(intent, RECOGNIZER_REQ_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1 -> {
                if (resultCode == RESULT_OK && null != data) {
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)!![0]
                    searchView.setQuery(result,true)
                }
            }
        }
    }
    override fun ItemClick(product: Products, position: Int) {
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra("products", product)
        startActivity(intent)
    }

    override fun addItemClick(products: Products) {
        getAllProducts(products);
    }

    override fun onResume() {
        super.onResume()
        getAllProducts(null);
        if (groceryAdapter != null) {
            groceryAdapter?.notifyDataSetChanged()
        }
    }

    private fun getAllProducts(addProducts: Products?) {

        var list: List<Products>? = mutableListOf()
        CoroutineScope(Dispatchers.IO).launch {
            list = ProductDB.getInstance(this@MainActivity).productDao().getAllProduct()
            if (list != null && list!!.size > 0) {
                var productadd:Boolean=false
                for (product in list!!) {
                    if (product.id == addProducts?.id) {
                        productadd=true
                        if (addProducts?.quantity!! <= 0) {
                            ProductDB.getInstance(this@MainActivity).productDao()
                                .deleteProduct(addProducts.id)
                        } else {
                            ProductDB.getInstance(this@MainActivity).productDao()
                                .updateProductList(addProducts.id, addProducts.quantity)
                        }
                    }}
                if(!productadd) {
                    addProducts?.let { DbUtil.addProductList(this@MainActivity, it) }
                    }
            } else {
                addProducts?.let { DbUtil.addProductList(this@MainActivity, it) }
            }
            delay(300)
            addCount()

        }
    }

    private fun addCount() {
        totalCartCount = 0
        CoroutineScope(Dispatchers.IO).launch {
            val list: List<Products> = ProductDB.getInstance(this@MainActivity).productDao().getAllProduct()
                for (product in list) {
                    totalCartCount += product.quantity!!
                }
               withContext(Dispatchers.Main) {
                    if (totalCartCount > 0) {
                        tv_count.text = totalCartCount.toString()
                        cart.setImageResource(R.drawable.ic_cart_added)
                    } else {
                        tv_count.text = ""
                        cart.setImageResource(R.drawable.ic_cart_empty)
                    }
                }
            }

    }

}