package com.nikita_zayanchkovskij.newsapp.presentation.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.nikita_zayanchkovskij.newsapp.domain.repositories.ISetDetailedNewsInfoToViewModel
import com.nikita_zayanchkovskij.newsapp.databinding.EntertainmentNewsFragmentBinding
import com.nikita_zayanchkovskij.newsapp.domain.models.NewsItemToDisplay
import com.nikita_zayanchkovskij.newsapp.presentation.adapters.rc_view_adapters.AdapterNewsListForMainScreen
import com.nikita_zayanchkovskij.newsapp.presentation.extension_functions.openDetailedNewsInfoFragment
import com.nikita_zayanchkovskij.newsapp.presentation.view_model.detailed_news_info.DetailedNewsInfoViewModel
import com.nikita_zayanchkovskij.newsapp.presentation.view_model.news_lists_for_main_screen.EntertainmentNewsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class EntertainmentNewsFragment: Fragment(), ISetDetailedNewsInfoToViewModel {
    private lateinit var binding: EntertainmentNewsFragmentBinding
    private val entertainmentNewsViewModel: EntertainmentNewsViewModel by activityViewModels()
    private val detailedNewsInfoViewModel: DetailedNewsInfoViewModel by activityViewModels()
    private val newsAdapter = AdapterNewsListForMainScreen(context = this, itemPressedListener = this)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = EntertainmentNewsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerViewAdapter()
        receiveNewsData()
        observeNewsData()
    }


    override fun setDetailedNewsInfoToViewModel(item: NewsItemToDisplay) {
        detailedNewsInfoViewModel.setValueToDetailedNewsItem(item)
        openDetailedNewsInfoFragment()
    }


    private fun setUpRecyclerViewAdapter() = with(binding) {
        rcViewEntertainmentNews.layoutManager = LinearLayoutManager(activity as AppCompatActivity)
        rcViewEntertainmentNews.adapter = newsAdapter
    }


    private fun receiveNewsData() {
        lifecycleScope.launch {
            entertainmentNewsViewModel.receiveEntertainmentNews()
        }
    }


    private fun observeNewsData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                entertainmentNewsViewModel.entertainmentNewsList.collectLatest { newsListState ->
                    checkIfLoading(newsListState.isLoading)
                    checkIfError(newsListState.error)
                    newsAdapter.submitList(newsListState.newsList)
                }
            }
        }
    }


    private fun checkIfLoading(loadingState: Boolean) = with(binding) {
        if (loadingState == true) progressBar.visibility = View.VISIBLE
        else progressBar.visibility = View.GONE
    }


    private fun checkIfError(errorMessage: String?) = with(binding) {
        if (errorMessage != null) {
            tvError.text = errorMessage
            tvError.visibility = View.VISIBLE
            tvTryAgain.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        } else {
            tvError.visibility = View.GONE
            tvTryAgain.visibility = View.GONE
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = EntertainmentNewsFragment()
    }
}