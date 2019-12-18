package jp.zuikou.system.redditprojectsample1.presentation.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import jp.zuikou.system.redditprojectsample1.domain.model.PostEntity

interface PostRepositoryPresent: PaginationRepository<PostEntity> {

    fun getList(subReddit: String? = null, type: String? = null): LiveData<PagedList<PostEntity>>
    //fun resetList()
}