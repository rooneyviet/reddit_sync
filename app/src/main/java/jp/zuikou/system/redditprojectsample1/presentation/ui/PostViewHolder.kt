package jp.zuikou.system.redditprojectsample1.presentation.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import jp.zuikou.system.redditprojectsample1.R
import jp.zuikou.system.redditprojectsample1.domain.model.PostEntity
import jp.zuikou.system.redditprojectsample1.extension.load
import jp.zuikou.system.redditprojectsample1.presentation.data.model.PostVoteRequest
import jp.zuikou.system.redditprojectsample1.util.extension.gone
import jp.zuikou.system.redditprojectsample1.util.extension.loadImage
import jp.zuikou.system.redditprojectsample1.util.extension.visible
import kotlinx.android.synthetic.main.list_item_post.view.*
import java.text.DateFormat

class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bindTo(
        post: PostEntity?,
        clickItem: (post: PostEntity, image: ImageView) -> Unit,
        upvoteDownvote: (postVoteRequest: PostVoteRequest) -> Unit,
        position: Int
    ) {
        if (post == null) return

        itemView.setOnClickListener { clickItem.invoke(post, itemView.imageViewThumb) }
        itemView.textViewTitle.text = post.title
        itemView.textViewAuthor.text = post.author
        itemView.textViewTime.text = DateFormat.getDateInstance(DateFormat.FULL).format(post.createdUtc)
        itemView.imageViewThumb.transitionName = post.remoteId
        itemView.textViewCategory.text = post.subredditNamePrefixed
        itemView.votesNumberText.text = post.score.toString()
        itemView.commentNumberText.text = post.numComments.toString() + " comments"

        itemView.downvoteImage.setOnClickListener {
            post.subredditId?.let {subredditId->
                upvoteDownvote.invoke(PostVoteRequest(false, subredditId, position))
            }
        }

        itemView.upvoteImage.setOnClickListener {
            post.name?.let {name->
                upvoteDownvote.invoke(PostVoteRequest(true, name, position))
            }
        }

        post.likes?.let {
            if(it) {
                itemView.downvoteImage.loadImage(R.drawable.downvote)
                itemView.upvoteImage.loadImage(R.drawable.upvote_clicked)
            } else {
                itemView.downvoteImage.loadImage(R.drawable.downvote_clicked)
                itemView.upvoteImage.loadImage(R.drawable.upvote)
            }
        }?: kotlin.run {
            itemView.downvoteImage.loadImage(R.drawable.downvote)
            itemView.upvoteImage.loadImage(R.drawable.upvote)
        }
        val imageUrl = post.imagePreview?.let {
            itemView.imageViewThumb.visible()
            if(!it.isNullOrEmpty()){
                it.last().url
            } else {
                itemView.imageViewThumb.gone()
                ""
            }
        }?: run{
            itemView.imageViewThumb.gone()
            ""
        }

        itemView.imageViewThumb.load(imageUrl)
    }


    companion object {
        fun create(parent: ViewGroup, postLayoutId: Int = R.layout.list_item_post): PostViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(postLayoutId, parent, false)
            return PostViewHolder(view)
        }
    }

}