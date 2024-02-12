package com.albaha.easyfood.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.albaha.easyfood.R
import com.albaha.easyfood.activites.MainActivity
import com.albaha.easyfood.activites.adaptrs.FavoritesMealsAdapter
import com.albaha.easyfood.databinding.FragmentSearchMealBinding
import com.albaha.easyfood.viewModel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchMealFragment : Fragment() {

    lateinit var viewModel: HomeViewModel
    lateinit var binding: FragmentSearchMealBinding
    lateinit var mealsAdapter: FavoritesMealsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel =(activity as MainActivity).viewModel

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentSearchMealBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var searchJob: Job?=null

        binding.editSearch.addTextChangedListener {searchQuery ->
            searchJob?.cancel()
            searchJob=lifecycleScope.launch {
                delay(500)
                viewModel.searchMeal(searchQuery.toString())
            }
        }




        prepareRecyclerView()

        observeSearchMeal()

    }

    private fun prepareRecyclerView() {
        mealsAdapter=FavoritesMealsAdapter()
        binding.rvSearchMeal.apply {
            layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter=mealsAdapter
        }
    }

    private fun searchMeal(mealName:String) {
       mealName?.let {
           viewModel.searchMeal(mealName)
       }
    }

    private fun observeSearchMeal() {
        viewModel.observerSearchList().observe(viewLifecycleOwner, Observer {
          it?.let {
              mealsAdapter.differ.submitList(it)
          }

           it.forEach {
               Log.d("SearchMeal",it.strMeal.toString())
           }
        })
    }


}