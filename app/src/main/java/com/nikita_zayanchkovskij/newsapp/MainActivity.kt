package com.nikita_zayanchkovskij.newsapp


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.nikita_zayanchkovskij.newsapp.databinding.ActivityMainBinding
import com.nikita_zayanchkovskij.newsapp.presentation.adapters.view_pager2_adapter.ViewPager2Adapter
import com.nikita_zayanchkovskij.newsapp.presentation.extension_functions.closeBookmarksFragment
import com.nikita_zayanchkovskij.newsapp.presentation.extension_functions.openBookmarksFragment
import com.nikita_zayanchkovskij.newsapp.presentation.fragments.BusinessNewsFragment
import com.nikita_zayanchkovskij.newsapp.presentation.fragments.EntertainmentNewsFragment
import com.nikita_zayanchkovskij.newsapp.presentation.fragments.HealthNewsFragment
import com.nikita_zayanchkovskij.newsapp.presentation.fragments.MainTopHeadlinesFragment
import com.nikita_zayanchkovskij.newsapp.presentation.fragments.ScienceNewsFragment
import com.nikita_zayanchkovskij.newsapp.presentation.fragments.SportsNewsFragment
import com.nikita_zayanchkovskij.newsapp.presentation.fragments.TechnologyNewsFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val fragmentsListForTabLayout: ArrayList<Fragment> = arrayListOf(
        MainTopHeadlinesFragment.newInstance(),
        BusinessNewsFragment.newInstance(), EntertainmentNewsFragment.newInstance(),
        HealthNewsFragment.newInstance(), ScienceNewsFragment.newInstance(),
        SportsNewsFragment.newInstance(), TechnologyNewsFragment.newInstance()
    )
    private lateinit var fragmentsTitlesListForTabLayout: ArrayList<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpViewPager2Adapter()
        fillInFragmentsTitlesListForTabLayout()
        setUpTabLayoutMediator()
        bottomNavigationMenuButtons()
    }


    private fun setUpViewPager2Adapter() = with(binding) {
        val adapter = ViewPager2Adapter(this@MainActivity, fragmentsListForTabLayout)
        viewPager2.adapter = adapter
    }


    private fun fillInFragmentsTitlesListForTabLayout() {
        fragmentsTitlesListForTabLayout = arrayListOf(getString(R.string.top_headlines),
            getString(R.string.category_business), getString(R.string.category_entertainment),
            getString(R.string.category_health), getString(R.string.category_science),
            getString(R.string.category_sports), getString(R.string.category_technology)
        )
    }


    private fun setUpTabLayoutMediator() = with(binding) {
        TabLayoutMediator(tabLayout, viewPager2) { tabItem, position ->
            tabItem.text = fragmentsTitlesListForTabLayout[position]
        }.attach()
    }


    private fun bottomNavigationMenuButtons() = with(binding) {

        bottomNavView.setOnItemSelectedListener { menuButton ->

            when (menuButton.itemId) {

                R.id.home_screen -> {
                    closeBookmarksFragment()
                    showViewElements()
                }

                R.id.bookmarks_screen -> {
                    hideViewElements()
                    openBookmarksFragment()
                }
            }
            true
        }
    }


    private fun hideViewElements() = with(binding) {
        tabLayout.visibility = View.GONE
        viewPager2.visibility = View.GONE
    }


    private fun showViewElements() = with(binding) {
        tabLayout.visibility = View.VISIBLE
        viewPager2.visibility = View.VISIBLE
    }
}