package jp.zuikou.system.redditprojectsample1.presentation.data.model

data class PostVoteRequest(var isUpvote: Boolean?,
                           val postId: String,
                           val clickedPosition: Int) {
}