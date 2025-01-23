package com.example.productbrowser.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.models.Product
import com.example.productbrowser.R
import com.example.productbrowser.databinding.ProductCardBinding

class ProductsAdapter(diffCallback: DiffUtil.ItemCallback<Product>) :
    PagingDataAdapter<Product, ProductsAdapter.ProductViewHolder>(diffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        return ProductViewHolder(ProductCardBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

        class ProductViewHolder(private val binding: ProductCardBinding) : RecyclerView.ViewHolder(binding.root){

            val productPic:ImageView = binding.imgIv
            val productName = binding.titleTv
            val productPrice = binding.priceTv
            fun bind(item: Product?) {
                productPrice.text = "$"+ item?.price.toString()
                productName.text = item?.product_name
                Glide.with(binding.root.context)
                    .load(item?.image)
                    .placeholder(R.drawable.baseline_image_24)
                    .error(R.drawable.baseline_image_24)
                    .into(productPic)
            }

        }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}
    object ProductComparator : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            // Id is unique.
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }