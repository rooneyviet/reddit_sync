package jp.zuikou.system.redditprojectsample1.data.datasource

import io.reactivex.Single
import jp.zuikou.system.redditprojectsample1.domain.model.AccessTokenEntity

interface LoginDatasource {
    fun getAccessToken(code: String?): Single<AccessTokenEntity>
}