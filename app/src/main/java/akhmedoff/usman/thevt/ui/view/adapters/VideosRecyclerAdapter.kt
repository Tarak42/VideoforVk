package akhmedoff.usman.thevt.ui.view.adapters

import akhmedoff.usman.data.model.Video
import akhmedoff.usman.thevt.ui.view.holders.VideoViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.squareup.picasso.Picasso

class VideosRecyclerAdapter(
        private val clickListener: (Video, View) -> Unit,
        @LayoutRes private val layoutId: Int
) : PagedListAdapter<Video, VideoViewHolder>(VIDEO_COMPARATOR) {

    companion object {
        val VIDEO_COMPARATOR = object : DiffUtil.ItemCallback<Video>() {
            override fun areContentsTheSame(oldItem: Video, newItem: Video) =
                    oldItem.title == newItem.title && oldItem.views == newItem.views

            override fun areItemsTheSame(oldItem: Video, newItem: Video) =
                    oldItem.id == newItem.id && oldItem.ownerId == newItem.ownerId
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VideoViewHolder(
            LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
    ).apply {
        itemView.setOnClickListener {
            poster.transitionName = "transition_name_$adapterPosition"
            clickListener(getItem(adapterPosition)!!, poster)
        }
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }
}
