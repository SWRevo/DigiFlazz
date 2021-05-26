package id.indosw.networking.kt

import android.app.Activity
import java.util.*

class RequestNetwork(val activity: Activity) {
    var params = HashMap<String, Any?>()
        private set
    var headers = HashMap<String, Any?>()
    var requestType = 0
        private set

    fun setParams(params: HashMap<String, Any?>, requestType: Int) {
        this.params = params
        this.requestType = requestType
    }

    fun startRequestNetwork(
            method: String,
            url: String,
            tag: String?,
            requestListener: RequestListener
    ) {
        RequestNetworkController.instance!!
                .execute(this, method, url, tag, requestListener)
    }

    interface RequestListener {
        fun onResponse(tag: String, response: String, responseHeaders: HashMap<String, Any?>)
        fun onErrorResponse(tag: String, message: String)
    }
}