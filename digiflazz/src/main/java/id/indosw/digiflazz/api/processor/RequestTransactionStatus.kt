package id.indosw.digiflazz.api.processor

import android.app.Activity
import id.indosw.digiflazz.api.controller.TransactionStatusRequestController
import id.indosw.digiflazz.api.sign.SignMaker.getSign

class RequestTransactionStatus(val activity: Activity) {
    private var username: String? = null
    private var backendUrl: String? = null
    private var key: String? = null
    private var refId: String? = null
    private var sku: String? = null
    private var custNumber: String? = null
    fun setBackendUrl(backendUrl: String?) {
        this.backendUrl = backendUrl
    }

    fun setUserName(username: String?) {
        this.username = username
    }

    fun setKey(key: String?) {
        this.key = key
    }

    fun setRefId(refId: String?) {
        this.refId = refId
    }

    fun setSKU(sku: String?) {
        this.sku = sku
    }

    fun setCustomerNumber(custNumber: String?) {
        this.custNumber = custNumber
    }

    fun startRequestTransactionStatus(requestListener: RequestListener?, pascaBayar: Boolean) {
        val signature = getSign(username!!, key!!, refId!!)
        TransactionStatusRequestController.instance!!.execute(this, backendUrl, username, key, pascaBayar, sku, custNumber, refId, signature, requestListener!!)
    }

    interface RequestListener {
        fun onResponse(response: String?)
        fun onErrorResponse(message: String?)
    }
}