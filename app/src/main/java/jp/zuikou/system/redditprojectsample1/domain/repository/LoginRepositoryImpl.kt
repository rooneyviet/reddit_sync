package jp.zuikou.system.redditprojectsample1.domain.repository

import io.reactivex.Single
import jp.zuikou.system.redditprojectsample1.data.datasource.LoginDatasource
import jp.zuikou.system.redditprojectsample1.domain.model.AccessTokenEntity

class LoginRepositoryImpl (private val loginDatasource: LoginDatasource): LoginRepository {
    override fun getAccessToken(code: String?): Single<AccessTokenEntity> =
        loginDatasource.getAccessToken(code)

}