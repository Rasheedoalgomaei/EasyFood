package com.albaha.easyfood.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter

import com.albaha.easyfood.R
import com.albaha.easyfood.activites.MainActivity
import com.albaha.easyfood.activites.adaptrs.FavoritesMealsAdapter
import com.albaha.easyfood.databinding.FragmentFavoritesBinding
import com.albaha.easyfood.viewModel.HomeViewModel
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass.
 * Use the [FavoritesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavoritesFragment : Fragment() {

    lateinit var binding: FragmentFavoritesBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var favoritesAdapter: FavoritesMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        viewModel = (activity as MainActivity).viewModel


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentFavoritesBinding.inflate(LayoutInflater.from(context),container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        observFavirate()

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            )= true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val postion=viewHolder.adapterPosition
                val meal_deleted=favoritesAdapter.differ.currentList[postion]
                viewModel.deleteMeal(favoritesAdapter.differ.currentList[postion])

                Snackbar.make(requireView(),"Meal deleted",Snackbar.LENGTH_LONG)
                    .setAction(
                        "Undo",
                        View.OnClickListener {
                            viewModel.insertMeal(meal_deleted)
                        }
                    ).show()

            }

        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFavMeals)
    }

    private fun prepareRecyclerView() {
        favoritesAdapter= FavoritesMealsAdapter()
        binding.rvFavMeals.apply {
            layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter=favoritesAdapter

        }

    }

    private fun observFavirate() {
        viewModel.observerFavoritesMealsLiveData().observe(requireActivity(),Observer{meals->

            favoritesAdapter.differ.submitList(meals)

        })
    }

    companion object {

    }
}