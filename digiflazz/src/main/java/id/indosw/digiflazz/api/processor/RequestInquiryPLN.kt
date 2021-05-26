package id.indosw.digiflazz.api.processor

import android.app.Activity
import id.indosw.digiflazz.api.commandvalue.CommandValue.CMD_INQUIRY_PLN
import id.indosw.digiflazz.api.controller.InquiryPLNRequestController

class RequestInquiryPLN(val activity: Activity) {
    private var backendUrl: String? = null
    private var custNumber: String? = null
    fun setBackendUrl(backendUrl: String?) {
        this.backendUrl = backendUrl
    }

    fun setCustomerNumber(custNumber: String?) {
        this.custNumber = custNumber
    }

    fun startRequestInquiryPLN(requestListener: RequestListener?) {
        InquiryPLNRequestController.instance!!.execute(this, backendUrl, CMD_INQUIRY_PLN, custNumber, requestListener!!)
    }

    interface RequestListener {
        fun onResponse(response: String?)
        fun onErrorResponse(message: String?)
    }
}