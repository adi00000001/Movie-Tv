package com.google.movietv.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.android.material.tabs.TabLayoutMediator
import com.google.movietv.R
import com.google.movietv.databinding.ActivityMainBinding
import com.google.movietv.ui.adapters.ViewPagerAdapter
import com.google.movietv.ui.map.TheatresFragment
import com.google.movietv.ui.movies.MovieListFragment


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpTabWithFragments()

    }


    @SuppressLint("NotifyDataSetChanged")
    private fun setUpTabWithFragments() = with(binding) {
        val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        adapter.addFragment(MovieListFragment(), "Movie List", R.drawable.ic_movie)
        adapter.addFragment(TheatresFragment(), "Theatres", R.drawable.ic_map)
        adapter.notifyDataSetChanged()
        viewPager.adapter = adapter
        viewPager.isUserInputEnabled = false
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.customView = TextView(this@MainActivity).apply {
                text = adapter.getPageTitle(position)
                gravity = Gravity.CENTER
                setTextColor(getColor(R.color.tab_text))
            }
            viewPager.setCurrentItem(tab.position, true)
        }.attach()
    }
}