package com.cs.mainfoodapp.screens.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.cs.mainfoodapp.utils.Resource
import com.bumptech.glide.Glide
import com.cs.mainfoodapp.R
import com.cs.mainfoodapp.model.Meal
import com.cs.mainfoodapp.databinding.FragmentHomeBinding
import com.cs.mainfoodapp.screens.adapters.CategoriesAdapter
import com.cs.mainfoodapp.screens.adapters.MostPopularMealAdapter
import com.cs.mainfoodapp.screens.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    private val TAG = "HomeFragment"

    private lateinit var popularItemAdapter: MostPopularMealAdapter
    private lateinit var categoryListAdapter: CategoriesAdapter

    lateinit var randomMeal: Meal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        popularItemAdapter = MostPopularMealAdapter()
        categoryListAdapter = CategoriesAdapter()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post(object : Runnable {
            override fun run() {
                viewModel.getRandomMealList()
                mainHandler.postDelayed(this, 5000)
            }
        })

        // get randomMeals
        getRandomMeals()
        onRandomMealClick()
        //get popular items
        viewModel.getPopularItems()
        getPopularItemsList()
        preparePopularItemsRecycleView()
        popularItemClickListener()
        //category List
        viewModel.getCategories()
        getCategoriesList()
        categoryListItemsRecyclerView()
        onCategoryClick()


    }


    private fun getRandomMeals() {
        viewModel.randomMealList.observe(viewLifecycleOwner, Observer { response ->

            when (response) {
                is Resource.Success -> {
                    hideProgressBar()

                    this.randomMeal = response.data!!.meals[0]
                    Glide.with(this)
                        .load(response.data.meals[0].strMealThumb)
                        .into(binding.imgRandomMeal)
                }

                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(TAG, "An error occured: $message")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })

    }

    private fun onRandomMealClick(){
        binding.randomMealCard.setOnClickListener {

            val bundle = Bundle().apply {
                // use same key as u provide in the nav_graph
                putString("mealId",randomMeal.idMeal)
                putString("mealName",randomMeal.strMeal)
                putString("mealThumb",randomMeal.strMealThumb)
            }
            findNavController().navigate(R.id.action_homeFragment_to_testFragment, bundle)
        }
    }


    //get popular Items View
    private fun getPopularItemsList() {
        //get popular Items
        viewModel.popularItemsLiveData.observe(viewLifecycleOwner, Observer { popularMeals ->
            popularItemAdapter.differ.submitList(popularMeals)
        })
    }

    // get popular Items recycler view
    private fun preparePopularItemsRecycleView() {
        binding.recViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

            adapter = popularItemAdapter
        }
    }

    //click event on popular items
    private fun popularItemClickListener() {
        popularItemAdapter.setOnItemClickListener { meal ->
        val bundle = Bundle().apply {
            // use same key as u provide in the nav_graph
            putString("mealId",meal.idMeal)
            putString("mealName",meal.strMeal)
            putString("mealThumb",meal.strMealThumb)
        }
        findNavController().navigate(R.id.action_homeFragment_to_testFragment, bundle)

        }
    }


    //get category list
    private fun getCategoriesList() {
        viewModel.categoryListLiveData.observe(viewLifecycleOwner, Observer { categoryList ->
            categoryListAdapter.differ.submitList(categoryList)

        })
    }
    // create category list recycler view

    private fun categoryListItemsRecyclerView() {
        binding.recViewCategories.apply {
            layoutManager = GridLayoutManager(activity, 3)
            adapter = categoryListAdapter
        }
    }

    private fun onCategoryClick() {
        categoryListAdapter.onItemClick = { category ->

            val bundle = Bundle().apply {
                // use same key as u provide in the nav_graph
                putString("categoryName",category.strCategory)
            }
            findNavController().navigate(R.id.action_homeFragment_to_detailCategoryMealsFragment, bundle)
        }
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }


}