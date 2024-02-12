package com.albaha.easyfood.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.albaha.easyfood.db.MealDatabase

class HomeViewModelFactory(
    val mealDatabase: MealDatabase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(mealDatabase) as T
            }
        throw IllegalArgumentException("Unknown ViewModel class")
        //return super.create(modelClass) as  T
    }


}