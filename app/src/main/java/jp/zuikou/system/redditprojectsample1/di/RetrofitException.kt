package jp.zuikou.system.redditprojectsample1.di

import jp.zuikou.system.redditprojectsample1.util.SharedPreferenceSingleton
import jp.zuikou.system.redditprojectsample1.util.rx.NoInternetConnectionEvent
import jp.zuikou.system.redditprojectsample1.util.rx.RxBus
import jp.zuikou.system.redditprojectsample1.util.rx.UnAuthenEvent
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.HttpURLConnection

class RetrofitException private constructor(
    message: String?,
    /**
     * The request URL which produced the error.
     */
    val url: String?,
    /**
     * Response object containing status code, headers, body, etc.
     */
    val response: Response<*>?,
    /**
     * The event kind which triggered this error.
     */
    val kind: Kind,
    exception: Throwable
) : RuntimeException(message, exception) {

    override fun toString(): String {
        return super.toString() + " : " + kind + " : " + url + " : " + response?.errorBody()?.string()
    }

    /**
     * Identifies the event kind which triggered a [RetrofitException].
     */
    enum class Kind {
        /**
         * An [IOException] occurred while communicating to the server.
         */
        NETWORK,
        /**
         * A non-200 HTTP status code was received from the server.
         */
        HTTP,
        /**
         * An internal error occurred while attempting to execute a request. It is best practice to
         * re-throw this exception so your application crashes.
         */
        UNEXPECTED,
        UNAUTHORIZED
    }

    companion object {
        fun httpError(url: String, response: Response<*>, httpException: HttpException): RetrofitException {
            val message = response.code().toString() + " " + response.message()
            return RetrofitException(message, url, response, Kind.HTTP, httpException)
        }

        fun networkError(exception: IOException): RetrofitException {
            return RetrofitException(exception.message, null, null, Kind.NETWORK, exception)
        }

        fun unexpectedError(exception: Throwable): RetrofitException {
            return RetrofitException(exception.message, null, null, Kind.UNEXPECTED, exception)
        }
        fun unauthorizedError(exception: Throwable): RetrofitException {
            return RetrofitException(exception.message, null, null, Kind.UNAUTHORIZED, exception)
        }

        fun asRetrofitException(throwable: Throwable): RetrofitException {
            if (throwable is RetrofitException) {
                RxBus.send(NoInternetConnectionEvent("RetrofitException"))
                return throwable
            }
            // We had non-200 http error
            if (throwable is HttpException) {
                RxBus.send(NoInternetConnectionEvent("HttpException No Internet"))
                val response = throwable.response()
                if(response.code() == HttpURLConnection.HTTP_UNAUTHORIZED && !SharedPreferenceSingleton.isAccessTokenSaved()){
                    RxBus.send(UnAuthenEvent("HTTP_UNAUTHORIZED UNAUTHORIZED!!!"))
                    return unauthorizedError(throwable)
                }
                return httpError(response.raw().request().url().toString(), response, throwable)
            }
            // A network error happened
            return if (throwable is IOException) {
                RxBus.send(NoInternetConnectionEvent("IOException JUST LOGIN!!!"))
                networkError(throwable)
            } else {
                RxBus.send(NoInternetConnectionEvent("ELSE EXCEPTION"))
                unexpectedError(throwable)
            }
            // We don't know what happened. We need to simply convert to an unknown error
        }
    }
}