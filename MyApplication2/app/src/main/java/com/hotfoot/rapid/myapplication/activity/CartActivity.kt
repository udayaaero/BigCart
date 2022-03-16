package com.hotfoot.rapid.myapplication.activity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButton
import com.hotfoot.rapid.myapplication.CallBack.AddItemClick
import com.hotfoot.rapid.myapplication.Model.Products
import com.hotfoot.rapid.myapplication.R
import com.hotfoot.rapid.myapplication.adapter.CartAdapter
import com.hotfoot.rapid.myapplication.roomDB.DbUtil
import com.hotfoot.rapid.myapplication.roomDB.ProductDB
import com.marcoscg.dialogsheet.DialogSheet
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.recyclerView
import kotlinx.coroutines.*
import java.util.*

class CartActivity : AppCompatActivity() , AddItemClick {
    var cartAdapter: CartAdapter? = null
    var quentity: Int = 0
    var totalAmount: Int = 0
    var subtotalAmount: Int = 0
    lateinit var heading: TextView
    lateinit var tv_amount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        recyclerView.layoutManager = LinearLayoutManager(this)
        heading = findViewById(R.id.heading)
        tv_amount = findViewById(R.id.tv_amount)
        home.setOnClickListener { finish() }
        close.setOnClickListener { finish() }
        btn_checkout.setOnClickListener {
            preventTwoClick(it)
            checkOutDialog() }

        getAllProducts()

    }


    private fun getAllProducts() {
        var list: List<Products>? = null
        CoroutineScope(Dispatchers.IO).launch {
            list = ProductDB.getInstance(this@CartActivity).productDao().getAllProduct()
            withContext(Dispatchers.Main) {
                setCartAdapter(list!!);
            }

        }
    }

    private fun setCartAdapter(list: List<Products>) {

        if (cartAdapter == null) {
            cartAdapter = CartAdapter(list, this)
            recyclerView?.adapter = cartAdapter
        } else {
            cartAdapter?.notifyDataSetChanged()
        }

        setSubTotal(list)
    }
    private fun setSubTotal(list: List<Products>){
        quentity=0
        totalAmount=0
        for (product in list) {
            quentity += product.quantity!!

            var pri: String = "0"
            subtotalAmount = 0;
            if (product.price?.contains("₹", ignoreCase = true) == true) {
                pri = product.price!!.replace("₹", "")
            }
            if (pri.contains(",", ignoreCase = true) == true) {
                pri = pri.replace(",", "")
            }
            subtotalAmount = pri.toInt() * product.quantity!!
            totalAmount += subtotalAmount
        }
        heading.text = "My Cart (" + quentity.toString() + ")"
        tv_amount.text = "₹ " + totalAmount.toString()

    }
    override fun addItemClick(products: Products) {
        getAllProducts(products);
    }

    private fun getAllProducts(addProducts: Products) {

        var list: List<Products>? = mutableListOf()
        CoroutineScope(Dispatchers.IO).launch {
            list = ProductDB.getInstance(this@CartActivity).productDao().getAllProduct()
            if (list != null && list!!.size > 0) {
                var productadd:Boolean=false
                for (product in list!!) {
                    if (product.id == addProducts.id) {
                        productadd=true
                        if (addProducts.quantity!! <= 0) {
                            ProductDB.getInstance(this@CartActivity).productDao()
                                .deleteProduct(addProducts.id)
                        } else {
                            ProductDB.getInstance(this@CartActivity).productDao()
                                .updateProductList(addProducts.id, addProducts.quantity)
                        }
                    }}
                if(!productadd) {
                    DbUtil.addProductList(this@CartActivity, addProducts)
                }
            } else {
                DbUtil.addProductList(this@CartActivity, addProducts)
            }
            delay(300)
            addTotal()

        }
    }

    private fun addTotal() {

        CoroutineScope(Dispatchers.IO).launch {
            val list: List<Products> = ProductDB.getInstance(this@CartActivity).productDao().getAllProduct()

            withContext(Dispatchers.Main) {

                if (cartAdapter != null) {
                    cartAdapter?.notifyDataSetChanged()
                }
                setSubTotal(list)
            }
        }

    }

    private fun checkOutDialog() {
        val dialogSheet = DialogSheet(this)
        val view = View.inflate(this, R.layout.custom__dialog_view, null)
        dialogSheet.setView(view)
        dialogSheet.setCancelable(true)
        val inflatedView: View = dialogSheet.getInflatedView()
        dialogSheet.show()


        val tv_sub_amount = view.findViewById<TextView>(R.id.tv_sub_amount)
        val tv_tax_amount = inflatedView.findViewById<TextView>(R.id.tv_tax_amount)
        val tv_service = inflatedView.findViewById<TextView>(R.id.tv_service)
        val tv_amount = inflatedView.findViewById<TextView>(R.id.tv_amount)
        val btn_finish = inflatedView.findViewById<MaterialButton>(R.id.btn_finish)

        tv_sub_amount.text ="₹ "+ totalAmount.toString()
        val tax: Double = totalAmount * (0.05)
        val taxString:String=getValue(tax)
        tv_tax_amount.text ="₹ "+ taxString
        tv_service.text ="₹ "+ "42"
        val overAllAmount: Int = totalAmount + tax.toInt() + 42
        tv_amount.text = "₹ "+overAllAmount.toString()

        btn_finish.setOnClickListener {
        Toast.makeText(this, "Thanks!", Toast.LENGTH_SHORT).show();
        }

    }
    private fun getValue(doubleValue: Double): String {
        return String.format(Locale.US, "%.2f", doubleValue)
    }
    fun preventTwoClick(view: View) {
        view.isEnabled = false
        view.postDelayed(
            { view.isEnabled = true },
            500
        )
    }
}