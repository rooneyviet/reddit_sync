package jp.zuikou.system.redditprojectsample1.presentation.ui

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import jp.zuikou.system.redditprojectsample1.R
import jp.zuikou.system.redditprojectsample1.domain.model.PostEntity
import jp.zuikou.system.redditprojectsample1.presentation.data.datasource.NetworkState
import jp.zuikou.system.redditprojectsample1.presentation.data.model.PostVoteRequest

class PostsPagedListAdapter(private val retryCallback: () -> Unit,
                            private val mContext: Context,
                            private val clickItem: (post: PostEntity, image: ImageView) -> Unit,
                            private val upvoteDownvote: (postVoteRequest: PostVoteRequest) -> Unit,
                            private val imageLongPress: (imageUrl: String, isLongPress: Boolean, sharedImageView: ImageView) -> Unit,
                            private val imageClickPress: (imageUrl: String, sharedImageView: ImageView) -> Unit) : PagedListAdapter<PostEntity,
        RecyclerView.ViewHolder>(PostDiffCallback) {

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.list_item_post -> PostViewHolder.create(parent)
            R.layout.list_item_post_vote_on_the_right_layout -> PostViewHolder.create(parent, R.layout.list_item_post_vote_on_the_right_layout)
            R.layout.list_item_network_state -> NetworkStateViewHolder.create(parent, retryCallback)
            else -> throw IllegalArgumentException("unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.list_item_post -> (holder as PostViewHolder).bindTo(getItem(position), clickItem, upvoteDownvote, imageLongPress, imageClickPress, position, mContext)
            R.layout.list_item_post_vote_on_the_right_layout -> (holder as PostViewHolder).bindTo(getItem(position), clickItem, upvoteDownvote, imageLongPress, imageClickPress, position, mContext)
            R.layout.list_item_network_state -> (holder as NetworkStateViewHolder).bindTo(networkState)
        }
    }

    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.SUCCESS
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.list_item_network_state
        } else {
            R.layout.list_item_post_vote_on_the_right_layout
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    fun setNetworkState(newNetworkState: NetworkState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    companion object {
        val PostDiffCallback = object : DiffUtil.ItemCallback<PostEntity>() {
            override fun areItemsTheSame(oldItem: PostEntity, newItem: PostEntity): Boolean {
                return oldItem.remoteId == newItem.remoteId
            }

            override fun areContentsTheSame(oldItem: PostEntity, newItem: PostEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

}