package com.example.weekendtimer.ui.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.weekendtimer.data.local.Category
import com.example.weekendtimer.databinding.ItemCategoryBinding

// This class is a custom adapter for a RecyclerView that displays Category objects.
class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    // This is a list of Category objects that this adapter displays.
    private var categoryList: List<Category> = emptyList()

    // ViewHolder for this adapter. Each item in the RecyclerView is displayed in one ViewHolder.
    inner class CategoryViewHolder(private val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {

        // Method to bind the data from a Category object to the UI elements in the ViewHolder.
        fun displayCategory(category: Category) = with(binding) {
            tvCategory.text = category.strCategory // Set the category name text view to the category's name.
            ivCategory.load(category.strCategoryThumb) // Load the category thumbnail image into the image view.
        }
    }

    // This method is called when the RecyclerView needs a new ViewHolder.
    // The returned ViewHolder will be used to display items of the adapter using onBindViewHolder().
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CategoryViewHolder(
            ItemCategoryBinding.inflate(inflater, parent, false)
        )
    }

    // This method is called to display the data for one list item at the specified position.
    // This method binds the Category data to the ViewHolder.
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.displayCategory(categoryList[position]) // Call the displayCategory method with the current Category.
    }

    // This method returns the size of the data set (the number of items in the Category list).
    override fun getItemCount(): Int {
        return categoryList.size
    }

    // Method to update the data in the adapter.
    fun setData(categories: List<Category>) {
        this.categoryList = categories // Set the Category list to the new data.
        notifyDataSetChanged() // Notify the adapter that the data has changed so it can update the RecyclerView.
    }
}
