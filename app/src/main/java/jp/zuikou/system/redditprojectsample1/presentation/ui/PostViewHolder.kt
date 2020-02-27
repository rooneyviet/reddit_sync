package jp.zuikou.system.redditprojectsample1.presentation.ui

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.ui.PlayerView
import im.ene.toro.ToroPlayer
import im.ene.toro.ToroUtil
import im.ene.toro.exoplayer.ExoPlayerViewHelper
import im.ene.toro.media.PlaybackInfo
import im.ene.toro.widget.Container
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


class PostViewHolder(view: View) : RecyclerView.ViewHolder(view), ToroPlayer,
    ToroPlayer.EventListener {
    private var playerView: PlayerView = view.findViewById(R.id.masterExoPlayer)

    private var helper: ExoPlayerViewHelper? = null

    private var mediaUri: Uri? = null
    private var posterView: ImageView = view.findViewById(R.id.posterView)

    init {
        playerView.removeView(posterView)
        requireNotNull(playerView.overlayFrameLayout?.addView(posterView))
    }


    fun bindTo(
        post: PostEntity?,
        clickItem: (post: PostEntity, image: ImageView) -> Unit,
        upvoteDownvote: (postVoteRequest: PostVoteRequest) -> Unit,
        position: Int,
        mContext: Context
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
                upvoteDownvote.invoke(PostVoteRequest(isDownvote, name, position, post))
            }
        })

        itemView.upvoteImage.clickOnAuthenContent( {
            post.name?.let {name->
                val isUpvote = if((post.likes!=null && post.likes==true)){
                    null
                } else {
                    true
                }
                upvoteDownvote.invoke(PostVoteRequest(isUpvote, name, position, post))
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

        if((post.isGif ) || (post.redditVideoEntity!=null)){
            Timber.d("PAFAPFJPSP "+post.redditVideoEntity?.fallbackUrl)
            itemView.masterExoPlayer.visibility = View.VISIBLE
            mediaUri = Uri.parse(post.redditVideoEntity?.fallbackUrl)
        } else {
            itemView.masterExoPlayer.visibility = View.INVISIBLE
            mediaUri = null
        }
        if(imageUrl?.url != null && imageUrl.height!=null && imageUrl.width!=null){
            itemView.posterView.load(imageUrl.url, imageUrl.width, imageUrl.height)
        }

        /*itemView.masterExoPlayer.setOnClickListener {
            val isPlaying = itemView.masterExoPlayer.playerView?.player?.playbackState == Player.STATE_READY && itemView.masterExoPlayer.playerView?.player?.playWhenReady ?: false

            itemView.masterExoPlayer.playerView?.player?.playWhenReady = !isPlaying
        }*/
    }


    override fun isPlaying(): Boolean =
        helper != null && helper!!.isPlaying;


    override fun getPlayerView(): View = playerView


    override fun pause() {
        helper?.apply {
            pause()
        }
    }

    override fun wantsToPlay(): Boolean =
        ToroUtil.visibleAreaOffset(this, itemView.parent) >= 0.75;


    override fun play() {
        helper?.apply {
            play();
        }
    }

    override fun getCurrentPlaybackInfo(): PlaybackInfo =
        helper?.latestPlaybackInfo ?: PlaybackInfo()

    override fun release() {
        if (helper != null) {
            helper?.removePlayerEventListener(this)
            helper?.release();
            helper = null;
        }
    }

    override fun initialize(container: Container, playbackInfo: PlaybackInfo) {

        if (helper == null) {
            if (mediaUri != null) {
                helper = ExoPlayerViewHelper(this, mediaUri!!)
            }
        }

        if (helper != null) {
            helper?.addPlayerEventListener(this)
            helper?.initialize(container, playbackInfo)
        }
    }

    override fun getPlayerOrder(): Int = adapterPosition

    override fun onBuffering() {

    }

    override fun onFirstFrameRendered() {
        posterView.visibility = View.GONE
    }

    override fun onPlaying() {
        posterView.visibility = View.GONE
    }

    override fun onPaused() {
        //posterView.visibility = View.VISIBLE
    }

    override fun onCompleted() {
        posterView.visibility = View.VISIBLE;
    }

    companion object {
        fun create(parent: ViewGroup, postLayoutId: Int = R.layout.list_item_post): PostViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(postLayoutId, parent, false)
            return PostViewHolder(view)
        }
    }

}