package com.hotfoot.rapid.myapplication.activity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.hotfoot.rapid.myapplication.Model.Products
import com.hotfoot.rapid.myapplication.R
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.grocery_item_layout.view.*

class DetailActivity : AppCompatActivity(), View.OnClickListener {
    var product:Products ?=null
    var count=1;
    var favorite=true;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        product = intent.getSerializableExtra("products") as? Products
        if(product!=null){
        setProducts()}
    }

    private fun setProducts() {
        tv_name.text = product?.name
        tv_amount.text = product?.price
        tv_description.text = product?.description
        tv_total.text = product?.quantity.toString()
        tv_count.text = product?.quantity.toString()
        image_favorite.setImageResource(R.drawable.ic_favorite_remove)
        if (product?.zoomThumb !== null) {
            Glide.with(this).load(product?.zoomThumb ).into(image_item)
        } else {
            image_item.setImageResource(R.drawable.ic_launcher_background)
        }
        image_favorite.setOnClickListener(this)
        backArrow.setOnClickListener(this)
        btn_add.setOnClickListener(this)
        btn_minus.setOnClickListener(this)
        btn_plus.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        when(view!!.id){
            R.id.backArrow->{
                finish()
            }
            R.id.image_favorite->{
                if(favorite){
                    favorite=false
                    image_favorite.setImageResource(R.drawable.ic_favorite_add)
                }else{
                    favorite=true
                    image_favorite.setImageResource(R.drawable.ic_favorite_remove)
                }
            }
            R.id.btn_add->{
               Toast.makeText(this,"Under Development!",Toast.LENGTH_SHORT).show()

            }
            R.id.btn_plus->{
                Toast.makeText(this,"Under Development!",Toast.LENGTH_SHORT).show()
            }
            R.id.btn_minus->{
                Toast.makeText(this,"Under Development!",Toast.LENGTH_SHORT).show()
            }
        }
    }
}