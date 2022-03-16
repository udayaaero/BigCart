package com.hotfoot.rapid.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hotfoot.rapid.myapplication.CallBack.AddItemClick
import com.hotfoot.rapid.myapplication.CallBack.ItemClick
import com.hotfoot.rapid.myapplication.Model.Products
import com.hotfoot.rapid.myapplication.R
import kotlinx.android.synthetic.main.cart_item_layout.view.*
import kotlinx.android.synthetic.main.grocery_item_layout.view.*
import kotlinx.android.synthetic.main.grocery_item_layout.view.btn_minus
import kotlinx.android.synthetic.main.grocery_item_layout.view.btn_plus
import kotlinx.android.synthetic.main.grocery_item_layout.view.image_item
import kotlinx.android.synthetic.main.grocery_item_layout.view.tv_amount
import kotlinx.android.synthetic.main.grocery_item_layout.view.tv_count
import kotlinx.android.synthetic.main.grocery_item_layout.view.tv_name


class CartAdapter(private val data: List<Products>,private val addItemClick: AddItemClick) : RecyclerView.Adapter<CartAdapter.RepositoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder =
        RepositoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cart_item_layout, parent, false))

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(data[position],position)
    }
    override fun getItemCount(): Int  = data.size

  inner  class RepositoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var subtotalAmount:Int=0;
      var count:Int=0;
        fun bind( product:Products,position: Int) {


            itemView.apply {
                tv_name.text = product.name
                tv_amount.text = product.price
                tv_count.text = product.quantity.toString()
                var pri:String="0";
                if(product.price!=null && product.quantity!=null){

                    if(product.price?.contains("₹", ignoreCase = true) == true){
                        pri= product.price!!.replace("₹","")}
                    if(pri.contains(",", ignoreCase = true) == true){
                        pri= pri.replace(",","")
                    }
                    subtotalAmount= pri.toInt() * product.quantity!!
                    product.subTotal=pri.toInt()
                    tv_total_amount.text = "₹ "+subtotalAmount
                }

                if (product.thumb !== null) {
                    Glide.with(this).load(product.thumb ).into(image_item)
                } else {
                    image_item.setImageResource(R.drawable.ic_launcher_background)
                }

                btn_plus.setOnClickListener{
                    count= tv_count.text.toString().toInt()
                    count++
                    tv_count.text = count.toString()
                    product.quantity=count
                    addItemClick.addItemClick(product)
                }
                btn_minus.setOnClickListener{
                    count= tv_count.text.toString().toInt()
                    if(count<=0){
                        tv_count.text = count.toString()
                        product.quantity=count
                        addItemClick.addItemClick(product)
                    }else{
                        count--
                        tv_count.text = count.toString()
                        product.quantity=count
                        addItemClick.addItemClick(product)
                    }
                }


            }
        }
    }


}

