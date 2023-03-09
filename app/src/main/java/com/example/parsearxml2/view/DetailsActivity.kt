package com.example.parsearxml2.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import com.example.parsearxml2.databinding.ActivityDetailsBinding
import com.example.parsearxml2.model.Job
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvDescripcionDetails.setMovementMethod(ScrollingMovementMethod())

        getExtras()?.let {
            binding.tvTitleDetails.text=it.title
            binding.tvCompanyDetails.text=it.company
            binding.tvDescripcionDetails.text=it.description
        }?:finish()
    }

    private fun getExtras(): Job?=intent.extras?.getParcelable("extra")
}