package id.indosw.digiflazz.api.controller

import id.indosw.digiflazz.api.commandstring.BalanceCheck
import id.indosw.digiflazz.api.endpoint.EndPointDigiflazz
import id.indosw.digiflazz.api.processor.RequestBalance
import okhttp3.*
import java.io.IOException
import java.util.*

class BalanceRequestController {
    fun execute(requestBalance: RequestBalance, backendUrl: String?, username: String?, key: String?, cmd: String?, buildSign: String?, requestListener: RequestBalance.RequestListener) {
        val cli = OkHttpClient().newBuilder().build()
        val rb: RequestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart(BalanceCheck.URL_HOST, EndPointDigiflazz.CEK_SALDO)
                .addFormDataPart(BalanceCheck.USERNAME, username!!)
                .addFormDataPart(BalanceCheck.KEY, key!!)
                .addFormDataPart(BalanceCheck.COMMAND, cmd!!)
                .addFormDataPart(BalanceCheck.SIGN, buildSign!!)
                .build()
        val req: Request = Request.Builder()
                .url(backendUrl!!)
                .method("POST", rb)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build()
        cli.newCall(req).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                val message = e.message
                requestBalance.activity.runOnUiThread { requestListener.onErrorResponse(message) }
                call.cancel()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val responseString = Objects.requireNonNull(response.body)!!.string()
                requestBalance.activity.runOnUiThread { requestListener.onResponse(responseString) }
            }
        })
    }

    companion object {
        private var mInstance: BalanceRequestController? = null

        @get:Synchronized
        val instance: BalanceRequestController?
            get() {
                if (mInstance == null) {
                    mInstance = BalanceRequestController()
                }
                return mInstance
            }
    }
}