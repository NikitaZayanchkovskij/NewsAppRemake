package com.nikita_zayanchkovskij.newsapp.presentation.adapters.rc_view_adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nikita_zayanchkovskij.newsapp.R
import com.nikita_zayanchkovskij.newsapp.domain.repositories.ISetDetailedNewsInfoToViewModel
import com.nikita_zayanchkovskij.newsapp.databinding.NewsMainScreenItemBinding
import com.nikita_zayanchkovskij.newsapp.domain.models.NewsItemToDisplay


class AdapterNewsListForMainScreen(
    private val context: Fragment,
    private val itemPressedListener: ISetDetailedNewsInfoToViewModel
): ListAdapter<NewsItemToDisplay, AdapterNewsListForMainScreen.ArticlesViewHolder>(ArticlesComparator()) {


    class ArticlesViewHolder(view: View,
        private val context: Fragment,
        private val itemPressedListener: ISetDetailedNewsInfoToViewModel
    ): RecyclerView.ViewHolder(view) {
        private val binding = NewsMainScreenItemBinding.bind(view)

        fun setData(item: NewsItemToDisplay) = with(binding) {

            Glide
                .with(context)
                .load(item.urlToImage)
                .placeholder(R.drawable.ic_image_placeholder)
                .into(imNews)

            tvTitle.text = item.title
            tvAuthorOrSource.text = item.sourceName
            tvDescription.text = item.description

            itemView.setOnClickListener {
                itemPressedListener.setDetailedNewsInfoToViewModel(item)
            }
        }
    }


    class ArticlesComparator: DiffUtil.ItemCallback<NewsItemToDisplay>() {

        override fun areItemsTheSame(oldItem: NewsItemToDisplay, newItem: NewsItemToDisplay): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: NewsItemToDisplay, newItem: NewsItemToDisplay): Boolean {
            return oldItem == newItem
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesViewHolder {
        val view = LayoutInflater
            .from(parent.context).inflate(R.layout.news_main_screen_item, parent, false)

        return ArticlesViewHolder(view, context, itemPressedListener)
    }


    override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) {
        holder.setData(getItem(position))
    }
}