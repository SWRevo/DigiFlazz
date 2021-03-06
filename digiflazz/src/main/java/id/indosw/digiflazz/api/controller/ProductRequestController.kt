package id.indosw.digiflazz.api.controller

import id.indosw.digiflazz.api.commandstring.PriceList
import id.indosw.digiflazz.api.endpoint.EndPointDigiflazz
import id.indosw.digiflazz.api.processor.RequestProduct
import okhttp3.*
import java.io.IOException
import java.util.*

class ProductRequestController {
    fun execute(requestProduct: RequestProduct, backendUrl: String?, username: String?, key: String?, cmd: String?, sku: String?, buildSign: String?, requestListener: RequestProduct.RequestListener) {
        val cli = OkHttpClient().newBuilder().build()
        val rb: RequestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart(PriceList.URL_HOST, EndPointDigiflazz.DAFTAR_HARGA)
                .addFormDataPart(PriceList.USERNAME, username!!)
                .addFormDataPart(PriceList.KEY, key!!)
                .addFormDataPart(PriceList.COMMAND, cmd!!)
                .addFormDataPart(PriceList.CODE_PRODUCT, sku!!)
                .addFormDataPart(PriceList.SIGN, buildSign!!)
                .build()
        val req: Request = Request.Builder()
                .url(backendUrl!!)
                .method("POST", rb)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build()
        cli.newCall(req).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                val message = e.message
                requestProduct.activity.runOnUiThread { requestListener.onErrorResponse(message) }
                call.cancel()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val responseString = Objects.requireNonNull(response.body)!!.string()
                requestProduct.activity.runOnUiThread { requestListener.onResponse(responseString) }
            }
        })
    }

    companion object {
        private var mInstance: ProductRequestController? = null

        @get:Synchronized
        val instance: ProductRequestController?
            get() {
                if (mInstance == null) {
                    mInstance = ProductRequestController()
                }
                return mInstance
            }
    }
}