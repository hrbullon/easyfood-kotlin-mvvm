package com.example.easyfood.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.easyfood.R
import com.example.easyfood.databinding.ActivityMealBinding
import com.example.easyfood.fragments.HomeFragment
import com.example.easyfood.viewModel.MealViewModel

class MealActivity : AppCompatActivity() {

    private lateinit var mealId:String
    private lateinit var mealName:String
    private lateinit var mealThumb:String
    private lateinit var youtubeLink:String

    private lateinit var binding: ActivityMealBinding
    private lateinit var mealViewModel:MealViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal)

        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Connect HomeFragment with HomeViewModel
        mealViewModel = ViewModelProvider(this)[MealViewModel::class.java]

        getMealInformationFromIntent()
        setInformationInViews()

        loadingCase()
        mealViewModel.getMealDetail(mealId)

        observerMealDetailsLiveData()

        onYoutubeImageClick()
    }

    private fun onYoutubeImageClick() {
        binding.btnYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private fun observerMealDetailsLiveData() {
        mealViewModel.observerMealDetailsLiveData().observe(this)
        { meal ->
            onResponseCase()
            binding.tvCategory.text = "Category: ${meal.strCategory}"
            binding.tvArea.text = "Area: ${meal.strArea}"
            binding.tvInstructionsText.text = "Category: ${meal.strInstructions}"

            youtubeLink = meal.strYoutube
        }
    }

    private fun setInformationInViews() {

        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor( ContextCompat.getColor(this, R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor( ContextCompat.getColor(this, R.color.white) )
    }

    private fun getMealInformationFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

    private fun loadingCase(){
        binding.progressBar.visibility = View.VISIBLE
        binding.fabFavorites.visibility = View.INVISIBLE
        binding.tvCategory.visibility = View.INVISIBLE
        binding.tvArea.visibility = View.INVISIBLE
        binding.tvInstructions.visibility = View.INVISIBLE
        binding.btnYoutube.visibility = View.INVISIBLE

    }

    private fun onResponseCase(){
        binding.progressBar.visibility = View.INVISIBLE
        binding.fabFavorites.visibility = View.VISIBLE
        binding.tvCategory.visibility = View.VISIBLE
        binding.tvArea.visibility = View.VISIBLE
        binding.tvInstructions.visibility = View.VISIBLE
        binding.btnYoutube.visibility = View.VISIBLE
    }
}