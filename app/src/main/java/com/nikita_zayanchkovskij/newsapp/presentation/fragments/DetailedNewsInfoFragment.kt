package com.nikita_zayanchkovskij.newsapp.presentation.fragments


import android.content.Intent
import android.net.Uri
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
import com.bumptech.glide.Glide
import com.nikita_zayanchkovskij.newsapp.R
import com.nikita_zayanchkovskij.newsapp.databinding.DetailedNewsInfoFragmentBinding
import com.nikita_zayanchkovskij.newsapp.domain.models.NewsItemToDisplay
import com.nikita_zayanchkovskij.newsapp.domain.use_cases.format_date.FormatDateUseCase
import com.nikita_zayanchkovskij.newsapp.presentation.extension_functions.closeDetailedNewsInfoFragment
import com.nikita_zayanchkovskij.newsapp.presentation.extension_functions.detailedNewsInfoFragmentWasOpenedFromBookmarks
import com.nikita_zayanchkovskij.newsapp.presentation.extension_functions.openBookmarksFragment
import com.nikita_zayanchkovskij.newsapp.presentation.extension_functions.showToast
import com.nikita_zayanchkovskij.newsapp.presentation.view_model.detailed_news_info.DetailedNewsInfoViewModel
import com.nikita_zayanchkovskij.newsapp.presentation.view_model.news_in_bookmarks.NewsInBookmarksViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class DetailedNewsInfoFragment: Fragment() {
    private lateinit var binding: DetailedNewsInfoFragmentBinding
    private val detailedNewsInfoViewModel: DetailedNewsInfoViewModel by activityViewModels()
    private val newsInBookmarksViewModel: NewsInBookmarksViewModel by activityViewModels()
    private var uriToTheFullNewsArticle: String = ""
    private var detailedNewsItemFromViewModel: NewsItemToDisplay? = null
    private var detailedNewsItemFromRoom: NewsItemToDisplay? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DetailedNewsInfoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpToolBar()
        observeDetailedNewsInfoFromViewModel()
        checkIfNewsItemIsInBookmarks()
        buttons()
    }


    private fun setUpToolBar() = with(binding) {
        tbDetailedNewsInfo.setNavigationOnClickListener {
            if (detailedNewsInfoFragmentWasOpenedFromBookmarks == true) {
                closeDetailedNewsInfoFragment(this@DetailedNewsInfoFragment)
                openBookmarksFragment()
                detailedNewsInfoFragmentWasOpenedFromBookmarks = false
            } else {
                closeDetailedNewsInfoFragment(this@DetailedNewsInfoFragment)
                detailedNewsInfoFragmentWasOpenedFromBookmarks = false
            }
        }
    }


    private fun checkIfNewsItemIsInBookmarks() = with(binding) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                if (detailedNewsItemFromViewModel != null) {
                    detailedNewsItemFromRoom = newsInBookmarksViewModel
                        .getNewsItemFromRoomByTitle(detailedNewsItemFromViewModel!!.title)

                    if (detailedNewsItemFromRoom != null) {
                        imBtAddToBookmarks.setImageResource(R.drawable.ic_bookmarks_filled_state)
                    } else {
                        imBtAddToBookmarks.setImageResource(R.drawable.ic_bookmarks_not_filled_state)
                    }
                }
            }
        }
    }


    private fun observeDetailedNewsInfoFromViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailedNewsInfoViewModel.detailedNewsItem.collectLatest {
                    if (it != null) {
                        detailedNewsItemFromViewModel = it
                        fillInViewElements(it)
                    }
                }
            }
        }
    }


    private fun fillInViewElements(item: NewsItemToDisplay) = with(binding) {
        tvNewsTitle.text = item.title

        Glide
            .with(this@DetailedNewsInfoFragment)
            .load(item.urlToImage)
            .placeholder(R.drawable.ic_image_placeholder)
            .into(imNewsImage)

        val sourceAndAuthor = "${getString(R.string.source)}: ${item.sourceName}, " +
                              "${getString(R.string.author)}: ${item.author}"
        tvSourceAndAuthor.text = sourceAndAuthor

        tvWebUrl.text = item.url
        uriToTheFullNewsArticle = item.url

        val publishedAt = FormatDateUseCase.formatDate(item.publishedAt)
        tvDateOfPublishing.text = publishedAt

        tvFullDescription.text = item.content
    }


    private fun buttons() = with(binding) {

        imBtAddToBookmarks.setOnClickListener {

            if (detailedNewsItemFromViewModel != null) {

                if (detailedNewsItemFromRoom == null) {
                    newsInBookmarksViewModel
                        .saveNewsItemToRoom(detailedNewsItemFromViewModel!!).invokeOnCompletion {
                            checkIfNewsItemIsInBookmarks()
                            showToast(
                                activity as AppCompatActivity,
                                toastText = getString(R.string.news_was_added_to_bookmarks)
                            )
                        }

                } else {
                    newsInBookmarksViewModel
                        .deleteNewsItemFromRoom(detailedNewsItemFromRoom!!).invokeOnCompletion {
                            checkIfNewsItemIsInBookmarks()
                            showToast(
                                activity as AppCompatActivity,
                                toastText = getString(R.string.news_was_removed_from_bookmarks)
                            )
                        }
                }
            }
        }

        btReadFullArticle.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uriToTheFullNewsArticle))
            startActivity(browserIntent)
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = DetailedNewsInfoFragment()
    }
}