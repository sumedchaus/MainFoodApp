package com.cs.mainfoodapp.screens.fragments

import android.R
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.core.view.postDelayed
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.cs.mainfoodapp.databinding.FragmentTestBinding
import com.cs.mainfoodapp.screens.viewmodel.DetailMealViewModel


class TestFragment : Fragment() {

    private val viewModel: DetailMealViewModel by viewModels()
    private val arguments: TestFragmentArgs by navArgs()

    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var youtubeLink: String


    lateinit var binding: FragmentTestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTestBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

//        val data = arguments
//        mealId = data!!.getString(MEAL_ID).toString()
//        mealName = data.get(MEAL_NAME).toString()
//        mealThumb = data.getString(MEAL_THUMB).toString()
//        binding.instructionDetails.text = mealName


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mealName = arguments.mealName
        mealId = arguments.mealId
        mealThumb = arguments.mealThumb

        viewModel.getMealDetails(mealId)
        getMealInformationFromViewModel()
        setInformationToViews()
        onYoutubeImageClick()
    }

    private fun setInformationToViews() {
        binding.collapsingToolBar.title = mealName
        binding.collapsingToolBar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolBar.setExpandedTitleColor(resources.getColor(R.color.white))

        Glide.with(activity!!)
            .load(mealThumb)
            .into(binding.imgMealDetail)
    }

    private fun getMealInformationFromViewModel() {

        viewModel.detailMealLiveData.observe(viewLifecycleOwner, Observer { meal ->

            binding.tvCategory.text = "Category: ${meal.strCategory} "
            binding.tvArea.text = "Area: ${meal.strArea} "
            binding.instructionDetails.text = "Area: ${meal.strInstructions} "
            youtubeLink = meal.strYoutube!!
        })
    }

    private fun onYoutubeImageClick() {
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

}