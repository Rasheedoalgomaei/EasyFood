package com.albaha.easyfood.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.albaha.easyfood.R
import com.albaha.easyfood.db.MealDatabase
import com.albaha.easyfood.viewModel.HomeViewModel
import com.albaha.easyfood.viewModel.HomeViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    val viewModel:HomeViewModel by lazy {
        val mealDatabase=MealDatabase.getInstance(this)
        val homeViewModelProviderFactory =HomeViewModelFactory(mealDatabase)
        ViewModelProvider(this,homeViewModelProviderFactory)[HomeViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNavigation=findViewById<BottomNavigationView>(R.id.btm_nav)
       // val navController=Navigation.findNavController(this,R.id.nav_host_fragment)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
            val navController=navHostFragment!!.navController
            bottomNavigation.setupWithNavController(navController)

    }
}