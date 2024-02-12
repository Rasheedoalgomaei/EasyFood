package com.albaha.easyfood.viewModel

import android.widget.ViewSwitcher.ViewFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.albaha.easyfood.db.MealDatabase

class MealViewModelFactory(val mealDatabase: MealDatabase)
    :ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(MealViewModel::class.java)) {
            return MealViewModel(mealDatabase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
        //return super.create(modelClass) as  T
    }
}