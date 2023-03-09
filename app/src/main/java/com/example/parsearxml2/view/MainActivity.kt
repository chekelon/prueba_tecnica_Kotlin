package com.example.parsearxml2.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.parsearxml2.R
import com.example.parsearxml2.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var  binding: ActivityMainBinding

    private val jobsFragment = JobsFragment()
    private val homeFragment = HomeFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setFragment(homeFragment)

        setViewBottomNavigationListener()

    }

    private fun setFragment(fragmentToChange: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.layoutFragmentHolder, fragmentToChange)
            .commit()
    }

    private fun setViewBottomNavigationListener() {
        //val viewBottomNav = findViewById<BottomNavigationView>(R.id.viewBottomNavigation)
        binding.viewBottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> setFragment(homeFragment)
                R.id.jobs -> setFragment(jobsFragment)
            }
            true
        }
    }


}