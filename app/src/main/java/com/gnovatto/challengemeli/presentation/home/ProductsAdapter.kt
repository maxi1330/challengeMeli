package com.gnovatto.challengemeli.presentation.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gnovatto.challengemeli.common.Utils
import com.gnovatto.challengemeli.databinding.ItemSearchBinding
import com.gnovatto.challengemeli.domain.model.ProductModel

class ProductsAdapter (private val listener: OnItemClickListener) : RecyclerView.Adapter<ProductsAdapter.MyViewHolder>() {

    private val productList = mutableListOf<ProductModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSearchBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding, this)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = productList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewProductList(data: List<ProductModel>) {
        productList.clear()
        productList.addAll(data)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setMoreProductList(data: List<ProductModel>) {
        productList.addAll(data)
        notifyDataSetChanged()
    }

    class MyViewHolder(val binding: ItemSearchBinding, private val adapter: ProductsAdapter) : RecyclerView.ViewHolder(binding.root) {

         fun bind (product: ProductModel) {
             binding.title.text = product.title
             binding.price.text = Utils.formatPrice(product.currencyId,product.price)
             Glide.with(itemView)
                 .load(product.thumbnail)
                 .into(binding.productImage)

             itemView.setOnClickListener {
                 val position = adapterPosition
                 if (position != RecyclerView.NO_POSITION) {
                     val product = adapter.productList[position]
                     adapter.listener.onItemClick(product)
                 }
             }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(product: ProductModel)
    }
}

