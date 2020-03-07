package com.urtisi.baget.ui.feed.site

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.urtisi.baget.databinding.ItemFeedBinding
import com.urtisi.baget.util.RSSModel

class RSSAdapter(private var feedList: ArrayList<RSSModel>) : RecyclerView.Adapter<RSSAdapter.RSSHolder>() {

    private val MAX_DESC_LINES = 4

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RSSHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFeedBinding.inflate(inflater, parent, false)
        return RSSHolder(binding)
    }

    override fun getItemCount(): Int = feedList.size

    override fun onBindViewHolder(holder: RSSHolder, position: Int) {
        val feed = feedList[position]
        holder.bind(feed)

        /**
         * see full description/hide back in to 4 lines
         */
        val description = holder.binding.feedItemDescription
        description.setOnClickListener {
            if (description.ellipsize != null) {
                description.maxLines = Int.MAX_VALUE
                description.ellipsize = null
            } else {
                description.maxLines = MAX_DESC_LINES
                description.ellipsize = TextUtils.TruncateAt.END
            }
        }//TODO:handle scrolling of recyclerview
    }

    fun replaceData(arrayList: ArrayList<RSSModel>){
        feedList = arrayList
        notifyDataSetChanged()
    }


    class RSSHolder(var binding: ItemFeedBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(rss: RSSModel){
            binding.feedItem = rss
            binding.executePendingBindings()
        }

    }

}