package com.gnovatto.challengemeli.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gnovatto.challengemeli.common.extensions.formatPrice
import com.gnovatto.challengemeli.databinding.ItemSearchBinding
import com.gnovatto.challengemeli.domain.model.ProductModel

/**
 * Adaptador para mostrar una lista de productos en un RecyclerView.
 *
 * Este adaptador se encarga de gestionar los elementos de la lista de productos y de
 * inflar las vistas correspondientes para cada elemento.
 *
 * @param listener El objeto que implementa la interfaz OnItemClickListener y maneja los eventos de clic.
 */
class ProductsAdapter(private val listener: OnItemClickListener) :
    RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {

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

    /**
     * Establece una nueva lista de productos.
     *
     * @param data La nueva lista de productos.
     */
    fun setNewProductList(data: List<ProductModel>) {
        productList.clear()
        productList.addAll(data)
        notifyItemRangeChanged(0, data.size)
    }

    /**
     * Agrega más productos a la lista existente en el adaptador.
     *
     * @param data La lista de productos a agregar.
     */
    fun setMoreProductList(data: List<ProductModel>) {
        productList.addAll(data)
        notifyItemRangeInserted(productList.size, data.size)
    }

    class ProductViewHolder(val binding: ItemSearchBinding, private val adapter: ProductsAdapter) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Vincula los datos del producto al ViewHolder.
         *
         * @param product El objeto que contiene los datos del producto.
         */
        fun bind(product: ProductModel) {
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

    /**
     * Interfaz para manejar los eventos de clic en los items del adaptador.
     */
    interface OnItemClickListener {
        fun onItemClick(product: ProductModel)
    }
}

