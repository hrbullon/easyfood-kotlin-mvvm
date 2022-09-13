package com.example.easyfood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easyfood.databinding.CategoryItemBinding
import com.example.easyfood.pojo.Category

class CategoriesAdapter():RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    private var categoriesList = ArrayList<Category>()
    var onItemClick: ((Category) -> Unit)? = null

    fun setCategoryList(categoryList: ArrayList<Category>){
        this.categoriesList = categoryList
        notifyDataSetChanged()
    }

    inner class CategoryViewHolder(val binding: CategoryItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {

        categoriesList[position].apply {

            Glide.with(holder.itemView)
                .load(this.strCategoryThumb)
                .into(holder.binding.imgCategoryItem)

            holder.binding.tvCategoryName.text = this.strCategory

            holder.itemView.setOnClickListener {
                onItemClick!!.invoke(categoriesList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return this.categoriesList.size
    }

}