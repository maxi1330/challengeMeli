package com.gnovatto.challengemeli.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gnovatto.challengemeli.common.extensions.formatPrice
import com.gnovatto.challengemeli.databinding.ItemSearchBinding
import com.gnovatto.challengemeli.domain.model.ProductModel

class ProductsAdapter (private val listener: OnItemClickListener) : RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {

    private val productList = mutableListOf<ProductModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSearchBinding.inflate(inflater, parent, false)
        return ProductViewHolder(binding, this)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentItem = productList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    fun setNewProductList(data: List<ProductModel>) {
        productList.clear()
        productList.addAll(data)
        notifyItemRangeChanged(0,data.size)
    }

    fun setMoreProductList(data: List<ProductModel>) {
        productList.addAll(data)
        notifyItemRangeInserted(productList.size, data.size)
    }

    class ProductViewHolder(val binding: ItemSearchBinding, private val adapter: ProductsAdapter) : RecyclerView.ViewHolder(binding.root) {

         fun bind (product: ProductModel) {
             binding.title.text = product.title
             binding.price.text = product.price.formatPrice(product.currencyId)
             Glide.with(itemView)
                 .load(product.thumbnail)
                 .into(binding.productImage)

             itemView.setOnClickListener {
                 val position = adapterPosition
                 if (position != RecyclerView.NO_POSITION) {
                     val productItem = adapter.productList[position]
                     adapter.listener.onItemClick(productItem)
                 }
             }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(product: ProductModel)
    }
}

