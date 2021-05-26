package id.indosw.networking.kt

import android.annotation.SuppressLint
import com.google.gson.Gson
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Suppress("unused")
class RequestNetworkController {
    private var client: OkHttpClient? = null
    @Suppress("UNUSED_ANONYMOUS_PARAMETER")
    private fun getClient(): OkHttpClient? {
        if (client == null) {
            val builder = OkHttpClient.Builder()
            try {
                val trustAllCerts = arrayOf<TrustManager>(
                        object : X509TrustManager {
                            @SuppressLint("TrustAllX509TrustManager")
                            @Throws(CertificateException::class)
                            override fun checkClientTrusted(
                                    chain: Array<X509Certificate>,
                                    authType: String
                            ) {
                            }

                            @SuppressLint("TrustAllX509TrustManager")
                            @Throws(CertificateException::class)
                            override fun checkServerTrusted(
                                    chain: Array<X509Certificate>,
                                    authType: String
                            ) {
                            }

                            override fun getAcceptedIssuers(): Array<X509Certificate> {
                                return arrayOf()
                            }
                        }
                )
                val sslContext = SSLContext.getInstance("TLS")
                sslContext.init(null, trustAllCerts, SecureRandom())
                val sslSocketFactory = sslContext.socketFactory
                builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                builder.connectTimeout(SOCKET_TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
                builder.readTimeout(READ_TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
                builder.writeTimeout(READ_TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
                builder.hostnameVerifier { hostname, session -> true }
            } catch (e: Exception) {
            }
            client = builder.build()
        }
        return client
    }

    fun execute(
            requestNetwork: RequestNetwork,
            method: String,
            url: String,
            tag: String?,
            requestListener: RequestNetwork.RequestListener
    ) {
        val reqBuilder = Request.Builder()
        val headerBuilder = Headers.Builder()
        if (requestNetwork.headers.size > 0) {
            val headers = requestNetwork.headers
            for ((key, value) in headers) {
                headerBuilder.add(key, value.toString())
            }
        }
        try {
            if (requestNetwork.requestType == REQUEST_PARAM) {
                if (method == GET) {
                    val httpBuilder: HttpUrl.Builder = try {
                        url.toHttpUrlOrNull()!!.newBuilder()
                    } catch (ne: NullPointerException) {
                        throw NullPointerException("unexpected url: $url")
                    }
                    if (requestNetwork.params.size > 0) {
                        val params = requestNetwork.params
                        for ((key, value) in params) {
                            httpBuilder.addQueryParameter(key, value.toString())
                        }
                    }
                    reqBuilder.url(httpBuilder.build()).headers(headerBuilder.build()).get()
                } else {
                    val formBuilder = FormBody.Builder()
                    if (requestNetwork.params.size > 0) {
                        val params = requestNetwork.params
                        for ((key, value) in params) {
                            formBuilder.add(key, value.toString())
                        }
                    }
                    val reqBody: RequestBody = formBuilder.build()
                    reqBuilder.url(url).headers(headerBuilder.build()).method(method, reqBody)
                }
            } else {
                val reqBody = Gson().toJson(requestNetwork.params)
                        .toRequestBody("application/json".toMediaTypeOrNull())
                if (method == GET) {
                    reqBuilder.url(url).headers(headerBuilder.build()).get()
                } else {
                    reqBuilder.url(url).headers(headerBuilder.build()).method(method, reqBody)
                }
            }
            val req = reqBuilder.build()
            getClient()!!.newCall(req).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    requestNetwork.activity.runOnUiThread {
                        requestListener.onErrorResponse(
                                tag!!,
                                e.message!!
                        )
                    }
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    val responseBody = response.body!!.string().trim { it <= ' ' }
                    requestNetwork.activity.runOnUiThread {
                        val b = response.headers
                        val map = HashMap<String, Any?>()
                        for (s in b.names()) {
                            map[s] = if (b[s] != null) b[s] else "null"
                        }
                        requestListener.onResponse(tag!!, responseBody, map)
                    }
                }
            })
        } catch (e: Exception) {
            requestListener.onErrorResponse(tag!!, e.message!!)
        }
    }

    companion object {
        const val GET = "GET"
        const val POST = "POST"
        const val PUT = "PUT"
        const val DELETE = "DELETE"
        const val REQUEST_PARAM = 0
        const val REQUEST_BODY = 1
        private const val SOCKET_TIMEOUT = 15000
        private const val READ_TIMEOUT = 25000
        private var mInstance: RequestNetworkController? = null

        @get:Synchronized
        val instance: RequestNetworkController?
            get() {
                if (mInstance == null) {
                    mInstance = RequestNetworkController()
                }
                return mInstance
            }
    }
}