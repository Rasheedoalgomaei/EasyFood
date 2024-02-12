package com.albaha.easyfood.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.albaha.easyfood.R
import com.albaha.easyfood.activites.adaptrs.CategoryMealsAdapter
import com.albaha.easyfood.databinding.ActivityCategoryMealsBinding
import com.albaha.easyfood.fragments.HomeFragment
import com.albaha.easyfood.pojo.CategoryList
import com.albaha.easyfood.pojo.MealsByCategory
import com.albaha.easyfood.viewModel.CategoryMealsViewModel

class CategoryMealsActivity : AppCompatActivity() {
    lateinit var binding:ActivityCategoryMealsBinding
    lateinit var categoryMealsViewModel: CategoryMealsViewModel
    lateinit var categoryMealsAdapter: CategoryMealsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        categoryMealsAdapter=CategoryMealsAdapter()
        recyclerViewMealsCategoryPrepare()

        categoryMealsViewModel= ViewModelProviders.of(this)[CategoryMealsViewModel::class.java]
        categoryMealsViewModel.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)
        observerMealsCategoryLiveData()

    }

    private fun observerMealsCategoryLiveData() {
        categoryMealsViewModel.observeMealsLiveData().observe(this,object : Observer<List<MealsByCategory>>{
            override fun onChanged(value: List<MealsByCategory>){
                val list=value
                binding.tvCategoryCount.text="Count Item:(${list.size})"
                categoryMealsAdapter.setMealList(value as ArrayList<MealsByCategory>)
            }

        })
    }

    fun recyclerViewMealsCategoryPrepare(){
        binding.rvMeals.apply {
            layoutManager=GridLayoutManager(this@CategoryMealsActivity,2,LinearLayoutManager.VERTICAL,false)
            adapter=categoryMealsAdapter
        }
    }
}