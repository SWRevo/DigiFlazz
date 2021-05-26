package id.indosw.digiflazz.api.controller

import id.indosw.digiflazz.api.commandstring.TopUp
import id.indosw.digiflazz.api.endpoint.EndPointDigiflazz
import id.indosw.digiflazz.api.processor.RequestTopUp
import okhttp3.*
import java.io.IOException
import java.util.*

class TopUpRequestController {
    fun execute(requestTopUp: RequestTopUp, backendUrl: String?, username: String?, key: String?, sku: String?, custNumber: String?, refId: String?, msg: String?, signature: String?, requestListener: RequestTopUp.RequestListener) {
        val cli = OkHttpClient().newBuilder().build()
        val rb: RequestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart(TopUp.URL_HOST, EndPointDigiflazz.TRANSAKSI)
                .addFormDataPart(TopUp.USERNAME, username!!)
                .addFormDataPart(TopUp.KEY, key!!)
                .addFormDataPart(TopUp.BUYER_SKU_CODE, sku!!)
                .addFormDataPart(TopUp.CUSTOMER_NUMBER, custNumber!!)
                .addFormDataPart(TopUp.REF_ID, refId!!)
                .addFormDataPart(TopUp.MESSAGE, msg!!)
                .addFormDataPart(TopUp.SIGN, signature!!)
                .build()
        val req: Request = Request.Builder()
                .url(backendUrl!!)
                .method("POST", rb)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build()
        cli.newCall(req).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                val message = e.message
                requestTopUp.activity.runOnUiThread { requestListener.onErrorResponse(message) }
                call.cancel()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val responseString = Objects.requireNonNull(response.body)!!.string()
                requestTopUp.activity.runOnUiThread { requestListener.onResponse(responseString) }
            }
        })
    }

    companion object {
        private var mInstance: TopUpRequestController? = null
        val instance: TopUpRequestController?
            get() {
                if (mInstance == null) {
                    mInstance = TopUpRequestController()
                }
                return mInstance
            }
    }
}