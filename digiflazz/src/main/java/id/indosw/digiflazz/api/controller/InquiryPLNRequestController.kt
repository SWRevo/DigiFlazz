package id.indosw.digiflazz.api.controller

import id.indosw.digiflazz.api.commandstring.InquiryPLN
import id.indosw.digiflazz.api.endpoint.EndPointDigiflazz
import id.indosw.digiflazz.api.processor.RequestInquiryPLN
import okhttp3.*
import java.io.IOException
import java.util.*

class InquiryPLNRequestController {
    fun execute(
            requestInquiryPLN: RequestInquiryPLN,
            backendUrl: String?,
            cmdInquiryPln: String?,
            custNumber: String?,
            requestListener: RequestInquiryPLN.RequestListener) {
        val cli = OkHttpClient().newBuilder().build()
        val rb: RequestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart(InquiryPLN.URL_HOST, EndPointDigiflazz.TRANSAKSI)
                .addFormDataPart(InquiryPLN.CUSTOMER_NUMBER, custNumber!!)
                .addFormDataPart(InquiryPLN.COMMANDS, cmdInquiryPln!!)
                .build()
        val req: Request = Request.Builder()
                .url(backendUrl!!)
                .method("POST", rb)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build()
        cli.newCall(req).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                val message = e.message
                requestInquiryPLN.activity.runOnUiThread { requestListener.onErrorResponse(message) }
                call.cancel()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val responseString = Objects.requireNonNull(response.body)!!.string()
                requestInquiryPLN.activity.runOnUiThread { requestListener.onResponse(responseString) }
            }
        })
    }

    companion object {
        private var mInstance: InquiryPLNRequestController? = null
        val instance: InquiryPLNRequestController?
            get() {
                if (mInstance == null) {
                    mInstance = InquiryPLNRequestController()
                }
                return mInstance
            }
    }
}