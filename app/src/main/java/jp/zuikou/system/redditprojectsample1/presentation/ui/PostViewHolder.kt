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
import jp.zuikou.system.redditprojectsample1.util.extension.clickOnAuthenContent
import jp.zuikou.system.redditprojectsample1.util.extension.gone
import jp.zuikou.system.redditprojectsample1.util.extension.loadImage
import jp.zuikou.system.redditprojectsample1.util.extension.visible
import kotlinx.android.synthetic.main.list_item_post.view.commentNumberText
import kotlinx.android.synthetic.main.list_item_post.view.downvoteImage
import kotlinx.android.synthetic.main.list_item_post.view.imageViewThumb
import kotlinx.android.synthetic.main.list_item_post.view.textViewAuthor
import kotlinx.android.synthetic.main.list_item_post.view.textViewCategory
import kotlinx.android.synthetic.main.list_item_post.view.textViewTime
import kotlinx.android.synthetic.main.list_item_post.view.textViewTitle
import kotlinx.android.synthetic.main.list_item_post.view.upvoteImage
import kotlinx.android.synthetic.main.list_item_post.view.votesNumberText
import kotlinx.android.synthetic.main.list_item_post_vote_on_the_right_layout.view.*
import timber.log.Timber
import java.text.DateFormat

class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bindTo(
        post: PostEntity?,
        clickItem: (post: PostEntity, image: ImageView) -> Unit,
        upvoteDownvote: (postVoteRequest: PostVoteRequest) -> Unit,
        position: Int
    ) {
        if (post == null) return

        itemView.clickOnAuthenContent({
             clickItem.invoke(post, itemView.imageViewThumb)
        })
        itemView.textViewTitle.text = post.title
        itemView.textViewAuthor.text = post.author
        itemView.textViewTime.text = DateFormat.getDateInstance(DateFormat.FULL).format(post.createdUtc)
        itemView.imageViewThumb.transitionName = post.remoteId
        itemView.textViewCategory.text = post.subredditNamePrefixed
        itemView.votesNumberText.text = post.score.toString()
        itemView.commentNumberText.text = post.numComments.toString() + " comments"

        itemView.downvoteImage.clickOnAuthenContent ({
            post.name?.let {name->
                val isDownvote = if((post.likes!=null && post.likes==false)){
                    null
                } else {
                    false
                }
                upvoteDownvote.invoke(PostVoteRequest(isDownvote, name, position))
            }
        })

        itemView.upvoteImage.clickOnAuthenContent( {
            post.name?.let {name->
                val isUpvote = if((post.likes!=null && post.likes==true)){
                    null
                } else {
                    true
                }
                upvoteDownvote.invoke(PostVoteRequest(isUpvote, name, position))
            }
        })

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
                it.last()
            } else {
                itemView.imageViewThumb.gone()
                null
            }
        }?: run{
            itemView.imageViewThumb.gone()
            null
        }

        if(imageUrl?.url != null && imageUrl.height!=null && imageUrl.width!=null){
            itemView.imageViewThumb.load(imageUrl.url, imageUrl.width, imageUrl.height)
        }

        if(post.isGif){
            Timber.d("PAFAPFJPSP "+post.redditVideoEntity?.dashUrl)
            itemView.masterExoPlayer.visibility = View.VISIBLE
            itemView.masterExoPlayer.url = post.redditVideoEntity?.dashUrl
        } else {
            itemView.masterExoPlayer.visibility = View.GONE
        }

    }


    companion object {
        fun create(parent: ViewGroup, postLayoutId: Int = R.layout.list_item_post): PostViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(postLayoutId, parent, false)
            return PostViewHolder(view)
        }
    }

}