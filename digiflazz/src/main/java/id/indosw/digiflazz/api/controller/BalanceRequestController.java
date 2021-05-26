package id.indosw.digiflazz.api.controller;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

import id.indosw.digiflazz.api.commandstring.BalanceCheck;
import id.indosw.digiflazz.api.endpoint.EndPointDigiflazz;
import id.indosw.digiflazz.api.processor.RequestBalance;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BalanceRequestController {

    private static BalanceRequestController mInstance;

    public static synchronized BalanceRequestController getInstance() {
        if(mInstance == null) {
            mInstance = new BalanceRequestController();
        }
        return mInstance;
    }

    public void execute(RequestBalance requestBalance, String backendUrl, String username, String key, String cmd, String buildSign, RequestBalance.RequestListener requestListener){
        OkHttpClient cli = new OkHttpClient().newBuilder().build();
        RequestBody rb = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart(BalanceCheck.URL_HOST, EndPointDigiflazz.CEK_SALDO)
                .addFormDataPart(BalanceCheck.USERNAME , username)
                .addFormDataPart(BalanceCheck.KEY , key)
                .addFormDataPart(BalanceCheck.COMMAND , cmd)
                .addFormDataPart(BalanceCheck.SIGN , buildSign)
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
                requestBalance.getActivity().runOnUiThread(() -> requestListener.onErrorResponse(message));
                call.cancel();
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String responseString = Objects.requireNonNull(response.body()).string();
                requestBalance.getActivity().runOnUiThread(() -> requestListener.onResponse(responseString));
            }

        });
    }
}
