package id.indosw.digiflazz.api.controller

import id.indosw.digiflazz.api.commandstring.PayBills
import id.indosw.digiflazz.api.endpoint.EndPointDigiflazz
import id.indosw.digiflazz.api.processor.RequestPayBilling
import okhttp3.*
import java.io.IOException
import java.util.*

class PayBillingRequestController {
    fun execute(
            requestPayBilling: RequestPayBilling,
            backendUrl: String?,
            username: String?,
            key: String?,
            cmdPayBilling: String?,
            sku: String?,
            custNumber: String?,
            refId: String?,
            signature: String?,
            requestListener: RequestPayBilling.RequestListener) {
        val cli = OkHttpClient().newBuilder().build()
        val rb: RequestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart(PayBills.URL_HOST, EndPointDigiflazz.TRANSAKSI)
                .addFormDataPart(PayBills.USERNAME, username!!)
                .addFormDataPart(PayBills.KEY, key!!)
                .addFormDataPart(PayBills.BUYER_SKU_CODE, sku!!)
                .addFormDataPart(PayBills.CUSTOMER_NUMBER, custNumber!!)
                .addFormDataPart(PayBills.REF_ID, refId!!)
                .addFormDataPart(PayBills.COMMANDS, cmdPayBilling!!)
                .addFormDataPart(PayBills.SIGN, signature!!)
                .build()
        val req: Request = Request.Builder()
                .url(backendUrl!!)
                .method("POST", rb)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build()
        cli.newCall(req).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                val message = e.message
                requestPayBilling.activity.runOnUiThread { requestListener.onErrorResponse(message) }
                call.cancel()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val responseString = Objects.requireNonNull(response.body)!!.string()
                requestPayBilling.activity.runOnUiThread { requestListener.onResponse(responseString) }
            }
        })
    }

    companion object {
        private var mInstance: PayBillingRequestController? = null
        val instance: PayBillingRequestController?
            get() {
                if (mInstance == null) {
                    mInstance = PayBillingRequestController()
                }
                return mInstance
            }
    }
}