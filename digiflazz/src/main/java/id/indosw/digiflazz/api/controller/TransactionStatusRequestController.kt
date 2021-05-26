package id.indosw.digiflazz.api.controller

import id.indosw.digiflazz.api.commandstring.StatusCheck
import id.indosw.digiflazz.api.commandvalue.CommandValue.CMD_STATUS_CHECK_PASCA
import id.indosw.digiflazz.api.endpoint.EndPointDigiflazz
import id.indosw.digiflazz.api.processor.RequestTransactionStatus
import okhttp3.*
import java.io.IOException
import java.util.*

class TransactionStatusRequestController {
    fun execute(
            requestTransactionStatus: RequestTransactionStatus,
            backendUrl: String?,
            username: String?,
            key: String?,
            pascaBayar: Boolean,
            sku: String?,
            custNumber: String?,
            refId: String?,
            signature: String?,
            requestListener: RequestTransactionStatus.RequestListener) {
        if (pascaBayar) {
            val cli = OkHttpClient().newBuilder().build()
            val rb: RequestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart(StatusCheck.URL_HOST, EndPointDigiflazz.TRANSAKSI)
                    .addFormDataPart(StatusCheck.USERNAME, username!!)
                    .addFormDataPart(StatusCheck.KEY, key!!)
                    .addFormDataPart(StatusCheck.BUYER_SKU_CODE, sku!!)
                    .addFormDataPart(StatusCheck.CUSTOMER_NUMBER, custNumber!!)
                    .addFormDataPart(StatusCheck.REF_ID, refId!!)
                    .addFormDataPart(StatusCheck.COMMANDS, CMD_STATUS_CHECK_PASCA)
                    .addFormDataPart(StatusCheck.SIGN, signature!!)
                    .build()
            val req: Request = Request.Builder()
                    .url(backendUrl!!)
                    .method("POST", rb)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .build()
            cli.newCall(req).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    val message = e.message
                    requestTransactionStatus.activity.runOnUiThread { requestListener.onErrorResponse(message) }
                    call.cancel()
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    val responseString = Objects.requireNonNull(response.body)!!.string()
                    requestTransactionStatus.activity.runOnUiThread { requestListener.onResponse(responseString) }
                }
            })
        } else {
            val cli = OkHttpClient().newBuilder().build()
            val rb: RequestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart(StatusCheck.URL_HOST, EndPointDigiflazz.TRANSAKSI)
                    .addFormDataPart(StatusCheck.USERNAME, username!!)
                    .addFormDataPart(StatusCheck.KEY, key!!)
                    .addFormDataPart(StatusCheck.BUYER_SKU_CODE, sku!!)
                    .addFormDataPart(StatusCheck.CUSTOMER_NUMBER, custNumber!!)
                    .addFormDataPart(StatusCheck.REF_ID, refId!!)
                    .addFormDataPart(StatusCheck.SIGN, signature!!)
                    .build()
            val req: Request = Request.Builder()
                    .url(backendUrl!!)
                    .method("POST", rb)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .build()
            cli.newCall(req).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    val message = e.message
                    requestTransactionStatus.activity.runOnUiThread { requestListener.onErrorResponse(message) }
                    call.cancel()
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    val responseString = Objects.requireNonNull(response.body)!!.string()
                    requestTransactionStatus.activity.runOnUiThread { requestListener.onResponse(responseString) }
                }
            })
        }
    }

    companion object {
        private var mInstance: TransactionStatusRequestController? = null
        val instance: TransactionStatusRequestController?
            get() {
                if (mInstance == null) {
                    mInstance = TransactionStatusRequestController()
                }
                return mInstance
            }
    }
}