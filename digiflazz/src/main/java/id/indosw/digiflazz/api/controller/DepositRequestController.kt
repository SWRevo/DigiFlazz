package id.indosw.digiflazz.api.controller

import id.indosw.digiflazz.api.commandstring.Deposit
import id.indosw.digiflazz.api.endpoint.EndPointDigiflazz
import id.indosw.digiflazz.api.processor.RequestDeposit
import okhttp3.*
import java.io.IOException
import java.util.*

class DepositRequestController {
    fun execute(requestDeposit: RequestDeposit, backendUrl: String?, username: String?, key: String?, amount: String?, bank: String?, owner: String?, signature: String?, requestListener: RequestDeposit.RequestListener) {
        val cli = OkHttpClient().newBuilder().build()
        val rb: RequestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart(Deposit.URL_HOST, EndPointDigiflazz.DEPOSIT)
                .addFormDataPart(Deposit.USERNAME, username!!)
                .addFormDataPart(Deposit.KEY, key!!)
                .addFormDataPart(Deposit.BANK, bank!!)
                .addFormDataPart(Deposit.AMOUNT, amount!!)
                .addFormDataPart(Deposit.OWNER_NAME, owner!!)
                .addFormDataPart(Deposit.SIGN, signature!!)
                .build()
        val req: Request = Request.Builder()
                .url(backendUrl!!)
                .method("POST", rb)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build()
        cli.newCall(req).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                val message = e.message
                requestDeposit.activity.runOnUiThread { requestListener.onErrorResponse(message) }
                call.cancel()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val responseString = Objects.requireNonNull(response.body)!!.string()
                requestDeposit.activity.runOnUiThread { requestListener.onResponse(responseString) }
            }
        })
    }

    companion object {
        private var mInstance: DepositRequestController? = null
        val instance: DepositRequestController?
            get() {
                if (mInstance == null) {
                    mInstance = DepositRequestController()
                }
                return mInstance
            }
    }
}