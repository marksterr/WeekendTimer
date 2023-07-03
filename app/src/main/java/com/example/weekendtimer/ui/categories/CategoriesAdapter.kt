package com.example.weekendtimer.ui.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.weekendtimer.data.local.Category
import com.example.weekendtimer.databinding.ItemCategoryBinding

class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    private var categoryList: List<Category> = emptyList()

    inner class CategoryViewHolder(private val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun displayCategory(category: Category) = with(binding) {
            tvCategory.text = category.strCategory
            ivCategory.load(category.strCategoryThumb)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CategoryViewHolder(
            ItemCategoryBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.displayCategory(categoryList[position])
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    fun setData(categories: List<Category>) {
        this.categoryList = categories
        notifyDataSetChanged()
    }
}