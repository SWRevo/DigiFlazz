package id.indosw.digiflazz.api.controller;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

import id.indosw.digiflazz.api.commandstring.TopUp;
import id.indosw.digiflazz.api.endpoint.EndPointDigiflazz;
import id.indosw.digiflazz.api.processor.RequestTopUp;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TopUpRequestController {

    private static TopUpRequestController mInstance;

    public static TopUpRequestController getInstance() {
        if(mInstance == null) {
            mInstance = new TopUpRequestController();
        }
        return mInstance;
    }


    public void execute(RequestTopUp requestTopUp, String backendUrl, String username, String key, String sku, String custNumber, String refId, String msg, String signature, RequestTopUp.RequestListener requestListener) {
        OkHttpClient cli = new OkHttpClient().newBuilder().build();
        RequestBody rb = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart(TopUp.URL_HOST, EndPointDigiflazz.TRANSAKSI)
                .addFormDataPart(TopUp.USERNAME , username)
                .addFormDataPart(TopUp.KEY , key)
                .addFormDataPart(TopUp.BUYER_SKU_CODE, sku)
                .addFormDataPart(TopUp.CUSTOMER_NUMBER , custNumber)
                .addFormDataPart(TopUp.REF_ID , refId)
                .addFormDataPart(TopUp.MESSAGE , msg)
                .addFormDataPart(TopUp.SIGN , signature)
                .build();

        Request req = new Request.Builder()
                .url(backendUrl)
                .method("POST", rb)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        cli.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                final String message = e.getMessage();
                requestTopUp.getActivity().runOnUiThread(() -> requestListener.onErrorResponse(message));
                call.cancel();
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String responseString = Objects.requireNonNull(response.body()).string();
                requestTopUp.getActivity().runOnUiThread(() -> requestListener.onResponse(responseString));
            }

        });
    }
}
