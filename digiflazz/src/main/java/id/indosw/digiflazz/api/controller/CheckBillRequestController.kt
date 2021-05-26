package id.indosw.digiflazz.api.controller

import id.indosw.digiflazz.api.commandstring.BillingCheck
import id.indosw.digiflazz.api.endpoint.EndPointDigiflazz
import id.indosw.digiflazz.api.processor.RequestCheckBills
import okhttp3.*
import java.io.IOException
import java.util.*

class CheckBillRequestController {
    fun execute(
            requestCheckBills: RequestCheckBills,
            backendUrl: String?,
            username: String?,
            key: String?,
            cmdBillingCheck: String?,
            sku: String?,
            custNumber: String?,
            refId: String?,
            signature: String?,
            requestListener: RequestCheckBills.RequestListener) {
        val cli = OkHttpClient().newBuilder().build()
        val rb: RequestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart(BillingCheck.URL_HOST, EndPointDigiflazz.TRANSAKSI)
                .addFormDataPart(BillingCheck.USERNAME, username!!)
                .addFormDataPart(BillingCheck.KEY, key!!)
                .addFormDataPart(BillingCheck.BUYER_SKU_CODE, sku!!)
                .addFormDataPart(BillingCheck.CUSTOMER_NUMBER, custNumber!!)
                .addFormDataPart(BillingCheck.REF_ID, refId!!)
                .addFormDataPart(BillingCheck.COMMANDS, cmdBillingCheck!!)
                .addFormDataPart(BillingCheck.SIGN, signature!!)
                .build()
        val req: Request = Request.Builder()
                .url(backendUrl!!)
                .method("POST", rb)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build()
        cli.newCall(req).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                val message = e.message
                requestCheckBills.activity.runOnUiThread { requestListener.onErrorResponse(message) }
                call.cancel()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val responseString = Objects.requireNonNull(response.body)!!.string()
                requestCheckBills.activity.runOnUiThread { requestListener.onResponse(responseString) }
            }
        })
    }

    companion object {
        private var mInstance: CheckBillRequestController? = null
        val instance: CheckBillRequestController?
            get() {
                if (mInstance == null) {
                    mInstance = CheckBillRequestController()
                }
                return mInstance
            }
    }
}