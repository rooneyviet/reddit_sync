package jp.zuikou.system.redditprojectsample1.domain.repository

import io.reactivex.Single
import jp.zuikou.system.redditprojectsample1.domain.model.AccessTokenEntity

interface LoginRepository {
    fun getAccessToken(code: String?): Single<AccessTokenEntity>
}