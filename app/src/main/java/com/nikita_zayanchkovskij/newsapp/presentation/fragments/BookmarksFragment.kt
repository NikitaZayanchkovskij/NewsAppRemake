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
import com.nikita_zayanchkovskij.newsapp.databinding.BookmarksFragmentBinding
import com.nikita_zayanchkovskij.newsapp.domain.models.NewsItemToDisplay
import com.nikita_zayanchkovskij.newsapp.presentation.adapters.rc_view_adapters.AdapterNewsListForMainScreen
import com.nikita_zayanchkovskij.newsapp.presentation.extension_functions.detailedNewsInfoFragmentWasOpenedFromBookmarks
import com.nikita_zayanchkovskij.newsapp.presentation.extension_functions.openDetailedNewsInfoFragment
import com.nikita_zayanchkovskij.newsapp.presentation.view_model.detailed_news_info.DetailedNewsInfoViewModel
import com.nikita_zayanchkovskij.newsapp.presentation.view_model.news_in_bookmarks.NewsInBookmarksViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class BookmarksFragment: Fragment(), ISetDetailedNewsInfoToViewModel {
    private lateinit var binding: BookmarksFragmentBinding
    private val detailedNewsInfoViewModel: DetailedNewsInfoViewModel by activityViewModels()
    private val newsInBookmarksViewModel: NewsInBookmarksViewModel by activityViewModels()
    private val newsAdapter = AdapterNewsListForMainScreen(context = this, itemPressedListener = this)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = BookmarksFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRcViewAdapter()
        setNewsInBookmarksFromRoomToViewModel()
        observeNewsInBookmarks()
    }


    override fun setDetailedNewsInfoToViewModel(item: NewsItemToDisplay) {
        detailedNewsInfoViewModel.setValueToDetailedNewsItem(item)
        detailedNewsInfoFragmentWasOpenedFromBookmarks = true
        openDetailedNewsInfoFragment()
    }


    private fun setNewsInBookmarksFromRoomToViewModel() {
        newsInBookmarksViewModel.getAllNewsFromRoom()
    }


    private fun observeNewsInBookmarks() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                newsInBookmarksViewModel.newsInBookmarksFromRoom.collectLatest {
                    if (it.isNotEmpty()) {
                        newsAdapter.submitList(it)
                        binding.tvNoBookmarksYet.visibility = View.GONE
                    } else {
                        newsAdapter.submitList(it)
                        binding.tvNoBookmarksYet.visibility = View.VISIBLE
                    }
                }
            }
        }
    }


    private fun setUpRcViewAdapter() = with(binding) {
        rcViewBookmarks.layoutManager = LinearLayoutManager(activity as AppCompatActivity)
        rcViewBookmarks.adapter = newsAdapter
    }


    companion object {
        @JvmStatic
        fun newInstance() = BookmarksFragment()
    }
}