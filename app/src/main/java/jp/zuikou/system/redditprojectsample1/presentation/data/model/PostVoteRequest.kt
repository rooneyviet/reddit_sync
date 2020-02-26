package jp.zuikou.system.redditprojectsample1.presentation.data.model

import jp.zuikou.system.redditprojectsample1.domain.model.PostEntity

data class PostVoteRequest(var isUpvote: Boolean?,
                           val postId: String,
                           val clickedPosition: Int,
                           val postItem: PostEntity) {
}