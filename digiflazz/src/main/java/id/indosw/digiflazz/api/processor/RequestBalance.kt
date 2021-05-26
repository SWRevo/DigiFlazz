package id.indosw.digiflazz.api.processor

import android.app.Activity
import id.indosw.digiflazz.api.commandvalue.CommandValue.CMD_BALANCE_CHECK
import id.indosw.digiflazz.api.commandvalue.CommandValue.SIGN_BALANCE_CHECK
import id.indosw.digiflazz.api.controller.BalanceRequestController
import id.indosw.digiflazz.api.sign.SignMaker.getSign

class RequestBalance(val activity: Activity) {
    private var username: String? = null
    private var backendUrl: String? = null
    private var key: String? = null
    fun setBackendUrl(backendUrl: String?) {
        this.backendUrl = backendUrl
    }

    fun setUserName(username: String?) {
        this.username = username
    }

    fun setKey(key: String?) {
        this.key = key
    }

    fun startRequestBalance(requestListener: RequestListener?) {
        val signature = getSign(username!!, key!!, SIGN_BALANCE_CHECK)
        BalanceRequestController.instance!!.execute(this, backendUrl, username, key, CMD_BALANCE_CHECK, signature, requestListener!!)
    }

    interface RequestListener {
        fun onResponse(response: String?)
        fun onErrorResponse(message: String?)
    }
}