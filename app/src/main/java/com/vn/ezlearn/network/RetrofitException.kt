package com.vn.ezlearn.network

import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException

/**
 * Created by pham.quy.hai on 9/9/16.
 */

internal class RetrofitException private constructor(message: String,
                                                     /** The request URL which produced the error.  */
                                                     val url: String?,
                                                     /** Response object containing status code, headers, body, etc.  */
                                                     private val response: Response<*>?,
                                                     /** The event kind which triggered this error.  */
                                                     val kind: Kind,
                                                     exception: Throwable?,
                                                     /** The Retrofit this request was executed on  */
                                                     private val retrofit: Retrofit?) :
        RuntimeException(message, exception) {
    private var error: Error? = null

    /** Identifies the event kind which triggered a [RetrofitException].  */
    private enum class Kind {
        /** An [IOException] occurred while communicating to the server.  */
        NETWORK,
        /** A non-200 HTTP status code was received from the server.  */
        HTTP,
        /**
         * An internal error occurred while attempting to execute a request. It is best practice to
         * re-throw this exception so your application crashes.
         */
        UNEXPECTED
    }

    fun getError(): Error? {
        try {
            error = getErrorBodyAs(Error::class.java)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return error
    }

    /**
     * HTTP response body converted to specified `typeQuestion`. `null` if there is no
     * response.
     *
     * @throws IOException if unable to convert the body to the specified `typeQuestion`.
     */
    @Throws(IOException::class)
    private fun <T> getErrorBodyAs(type: Class<T>): T? {
        if (response?.errorBody() == null) {
            return null
        }
        val converter = retrofit!!.responseBodyConverter<T>(type, arrayOfNulls(0))
        return converter.convert(response.errorBody())
    }

    companion object {
        fun httpError(url: String, response: Response<*>, retrofit: Retrofit): RetrofitException {
            val message = response.code().toString() + " " + response.message()
            return RetrofitException(message, url, response, Kind.HTTP, null, retrofit)
        }

        fun networkError(exception: IOException): RetrofitException {
            return RetrofitException(exception.message.toString(), null, null,
                    Kind.NETWORK,
                    exception, null)
        }

        fun unexpectedError(exception: Throwable): RetrofitException {
            return RetrofitException(exception.message.toString(), null, null,
                    Kind.UNEXPECTED,
                    exception, null)
        }
    }
}
