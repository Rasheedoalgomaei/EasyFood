package com.albaha.easyfood.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.albaha.easyfood.activites.MealActivity
import com.albaha.easyfood.db.MealDatabase
import com.albaha.easyfood.pojo.Meal
import com.albaha.easyfood.pojo.MealList
import com.albaha.easyfood.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel(val mealDatabase: MealDatabase):ViewModel(){






}