package com.cs.mainfoodapp.screens.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cs.mainfoodapp.model.Category
import com.cs.mainfoodapp.databinding.CategoryItemBinding

class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {

    inner class CategoriesViewHolder(val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.idCategory == newItem.idCategory
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        return CategoriesViewHolder(
            CategoryItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val categoryItem = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this)
                .load(categoryItem.strCategoryThumb)
                .into(holder.binding.imgCategory)

            holder.binding.tvCategoryName.text = categoryItem.strCategory

            holder.itemView.setOnClickListener {
                onItemClick!!.invoke(categoryItem)
            }
        }

    }

    var onItemClick: ((Category) -> Unit)? = null


}