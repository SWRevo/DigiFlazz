package id.indosw.digiflazz.api.processor

import android.app.Activity
import id.indosw.digiflazz.api.commandvalue.CommandValue.SIGN_DEPOSIT
import id.indosw.digiflazz.api.controller.DepositRequestController
import id.indosw.digiflazz.api.sign.SignMaker.getSign

class RequestDeposit(val activity: Activity) {
    private var username: String? = null
    private var backendUrl: String? = null
    private var key: String? = null
    private var amount: String? = null
    private var bank: String? = null
    private var owner: String? = null
    fun setBackendUrl(backendUrl: String?) {
        this.backendUrl = backendUrl
    }

    fun setUserName(username: String?) {
        this.username = username
    }

    fun setKey(key: String?) {
        this.key = key
    }

    fun setAmount(amount: String?) {
        this.amount = amount
    }

    fun setBank(bank: String?) {
        this.bank = bank
    }

    fun setOwnerName(owner: String?) {
        this.owner = owner
    }

    fun startRequestDeposit(requestListener: RequestListener?) {
        val signature = getSign(username!!, key!!, SIGN_DEPOSIT)
        DepositRequestController.instance!!.execute(this, backendUrl, username, key, amount, bank, owner, signature, requestListener!!)
    }

    interface RequestListener {
        fun onResponse(response: String?)
        fun onErrorResponse(message: String?)
    }
}