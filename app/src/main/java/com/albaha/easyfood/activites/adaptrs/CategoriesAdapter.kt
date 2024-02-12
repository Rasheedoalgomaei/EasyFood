package com.albaha.easyfood.activites.adaptrs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.albaha.easyfood.databinding.CategoryCardBinding
import com.albaha.easyfood.pojo.Category
import com.bumptech.glide.Glide

class CategoriesAdapter: RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {


    lateinit var onItemClick : ((Category) -> Unit)

    private  var categoryList = ArrayList<Category>()

    fun setCategoryList(list: ArrayList<Category>){
        this.categoryList=list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {

        return  CategoriesViewHolder(CategoryCardBinding.inflate(LayoutInflater.from(parent.context),parent,false));

    }

    override fun getItemCount(): Int {
       return categoryList.size

    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {

        Glide.with(holder.itemView)
            .load(categoryList[position].strCategoryThumb)
            .into(holder.binding.imgCategory)
        holder.binding.tvCategoryName.text=categoryList[position].strCategory

        holder.itemView.setOnClickListener {
            onItemClick.invoke(categoryList[position])
        }

    }

    inner class CategoriesViewHolder(val binding : CategoryCardBinding):RecyclerView.ViewHolder(binding.root)

}