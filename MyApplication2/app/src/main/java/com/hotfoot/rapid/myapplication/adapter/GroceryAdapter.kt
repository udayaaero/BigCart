package com.hotfoot.rapid.myapplication.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hotfoot.rapid.myapplication.CallBack.AddItemClick
import com.hotfoot.rapid.myapplication.CallBack.ItemClick
import com.hotfoot.rapid.myapplication.Model.Products
import com.hotfoot.rapid.myapplication.R
import kotlinx.android.synthetic.main.grocery_item_layout.view.*


class GroceryAdapter(private val activity: Activity,private val data: List<Products>,private val itemClick: ItemClick,private val addItemClick: AddItemClick) : RecyclerView.Adapter<GroceryAdapter.RepositoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder =
        RepositoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.grocery_item_layout, parent, false))

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(data[position],position)
    }
    override fun getItemCount(): Int = data.size

  inner  class RepositoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var count=1;
        var favorite=true;
        fun bind(product: Products, position: Int) {
            itemView.apply {
                tv_name.text = product.name
                tv_amount.text = product.price
                image_favorite.setImageResource(R.drawable.ic_favorite_remove)
                if (product.thumb !== null) {
                    Glide.with(this).load(product.thumb ).into(image_item)
                } else {
                    image_item.setImageResource(R.drawable.ic_launcher_background)
                }
                image_favorite.setOnClickListener{
                    if(favorite){
                        favorite=false
                        image_favorite.setImageResource(R.drawable.ic_favorite_add)
                    }else{
                        favorite=true
                        image_favorite.setImageResource(R.drawable.ic_favorite_remove)
                    }
                }
                btn_add.setOnClickListener{
                    count=1
                    add_layout.visibility=View.VISIBLE
                    btn_add.visibility=View.GONE
                    product.quantity=count
                    addItemClick.addItemClick(product)
                }
                btn_plus.setOnClickListener{
                    count++
                    tv_count.text = count.toString()
                    product.quantity=count

                    addItemClick.addItemClick(product)

                }
                btn_minus.setOnClickListener{
                    count--
                    if(count<=0){
                        add_layout.visibility=View.GONE
                        btn_add.visibility=View.VISIBLE
                        tv_count.text = "1"
                        product.quantity=count
                        addItemClick.addItemClick(product)
                    }else{
                        product.quantity=count
                        addItemClick.addItemClick(product)
                        tv_count.text = count.toString()
                    }
                }
                image_item.setOnClickListener{
                    itemClick.ItemClick(product ,position)
                }

            }
        }
    }


}