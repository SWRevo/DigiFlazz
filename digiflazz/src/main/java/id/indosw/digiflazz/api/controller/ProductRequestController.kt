package id.indosw.digiflazz.api.controller;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

import id.indosw.digiflazz.api.commandstring.BalanceCheck;
import id.indosw.digiflazz.api.commandstring.PriceList;
import id.indosw.digiflazz.api.endpoint.EndPointDigiflazz;
import id.indosw.digiflazz.api.processor.RequestProduct;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProductRequestController {
    private static ProductRequestController mInstance;

    public static synchronized ProductRequestController getInstance() {
        if(mInstance == null) {
            mInstance = new ProductRequestController();
        }
        return mInstance;
    }

    public void execute(RequestProduct requestProduct, String backendUrl, String username, String key, String cmd, String sku, String buildSign, RequestProduct.RequestListener requestListener){
        OkHttpClient cli = new OkHttpClient().newBuilder().build();
        RequestBody rb = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart(PriceList.URL_HOST, EndPointDigiflazz.DAFTAR_HARGA)
                .addFormDataPart(PriceList.USERNAME , username)
                .addFormDataPart(PriceList.KEY , key)
                .addFormDataPart(PriceList.COMMAND , cmd)
                .addFormDataPart(PriceList.CODE_PRODUCT , sku)
                .addFormDataPart(PriceList.SIGN , buildSign)
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
                requestProduct.getActivity().runOnUiThread(() -> requestListener.onErrorResponse(message));
                call.cancel();
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String responseString = Objects.requireNonNull(response.body()).string();
                requestProduct.getActivity().runOnUiThread(() -> requestListener.onResponse(responseString));
            }

        });
    }
}
