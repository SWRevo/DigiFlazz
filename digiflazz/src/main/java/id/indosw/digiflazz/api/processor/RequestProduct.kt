package id.indosw.digiflazz.api.processor

import android.app.Activity
import id.indosw.digiflazz.api.commandvalue.CommandValue.CMD_PRICELIST_PASCA
import id.indosw.digiflazz.api.commandvalue.CommandValue.CMD_PRICELIST_PRA
import id.indosw.digiflazz.api.commandvalue.CommandValue.SIGN_PRICELIST
import id.indosw.digiflazz.api.controller.ProductRequestController
import id.indosw.digiflazz.api.sign.SignMaker.getSign

class RequestProduct(val activity: Activity) {
    private var username: String? = null
    private var backendUrl: String? = null
    private var key: String? = null
    private var sku: String? = null
    fun setBackendUrl(backendUrl: String?) {
        this.backendUrl = backendUrl
    }

    fun setUserName(username: String?) {
        this.username = username
    }

    fun setKey(key: String?) {
        this.key = key
    }

    fun setSKU(sku: String?) {
        this.sku = sku
    }

    fun startRequestProduct(requestListener: RequestListener?, prabayar: Boolean) {
        val signature = getSign(username!!, key!!, SIGN_PRICELIST)
        val cmdSet: String
        if (prabayar) {
            cmdSet = CMD_PRICELIST_PRA
        } else {
            cmdSet = CMD_PRICELIST_PASCA
        }
        ProductRequestController.instance!!.execute(this, backendUrl, username, key, cmdSet, sku, signature, requestListener!!)
    }

    interface RequestListener {
        fun onResponse(response: String?)
        fun onErrorResponse(message: String?)
    }
}