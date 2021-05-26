package id.indosw.digiflazz.api.processor

import android.app.Activity
import id.indosw.digiflazz.api.commandvalue.CommandValue.CMD_BILLING_CHECK
import id.indosw.digiflazz.api.controller.CheckBillRequestController
import id.indosw.digiflazz.api.sign.SignMaker.getSign

class RequestCheckBills(val activity: Activity) {
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

    fun startRequestCheckBill(requestListener: RequestListener?) {
        val signature = getSign(username!!, key!!, refId!!)
        CheckBillRequestController.instance!!.execute(this, backendUrl, username, key, CMD_BILLING_CHECK, sku, custNumber, refId, signature, requestListener!!)
    }

    interface RequestListener {
        fun onResponse(response: String?)
        fun onErrorResponse(message: String?)
    }
}