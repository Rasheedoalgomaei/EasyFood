package com.albaha.easyfood.activites.adaptrs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.albaha.easyfood.databinding.MealItemBinding
import com.albaha.easyfood.pojo.MealsByCategory
import com.bumptech.glide.Glide

class CategoryMealsAdapter: RecyclerView.Adapter<CategoryMealsAdapter.CategoryMealsViewHolder>() {

    private var mealList=ArrayList<MealsByCategory>()

    fun setMealList(mealsList:ArrayList<MealsByCategory>){
        this.mealList=mealsList
        notifyDataSetChanged()
    }


    inner class CategoryMealsViewHolder(val binding: MealItemBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealsViewHolder {
        return CategoryMealsViewHolder(MealItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    override fun onBindViewHolder(holder: CategoryMealsViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealList[position].strMealThumb)
            .into(holder.binding.imgMeal)
        holder.binding.tvItem.text=mealList[position].strMeal
    }


}