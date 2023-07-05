package com.example.weekendtimer.ui.meals

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.weekendtimer.data.local.Meal
import com.example.weekendtimer.databinding.ItemMealBinding

class MealsAdapter : RecyclerView.Adapter<MealsAdapter.MealViewHolder>() {

    private var mealList: List<Meal> = emptyList()

    inner class MealViewHolder(private val binding: ItemMealBinding) : RecyclerView.ViewHolder(binding.root) {

        fun displayMeal(meal: Meal) = with(binding) {
            tvMeal.text = meal.strMeal
            ivMeal.load(meal.strMealThumb)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MealViewHolder(
            ItemMealBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.displayMeal(mealList[position])
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    fun setData(meals: List<Meal>) {
        this.mealList = meals
        notifyDataSetChanged()
    }
}