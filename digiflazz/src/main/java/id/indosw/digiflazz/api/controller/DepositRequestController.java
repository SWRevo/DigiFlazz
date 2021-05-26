package id.indosw.digiflazz.api.controller;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

import id.indosw.digiflazz.api.commandstring.Deposit;
import id.indosw.digiflazz.api.endpoint.EndPointDigiflazz;
import id.indosw.digiflazz.api.processor.RequestDeposit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DepositRequestController {

    private static DepositRequestController mInstance;

    public static DepositRequestController getInstance() {
        if(mInstance == null) {
            mInstance = new DepositRequestController();
        }
        return mInstance;
    }

    public void execute(RequestDeposit requestDeposit, String backendUrl, String username, String key, String amount, String bank, String owner, String signature, RequestDeposit.RequestListener requestListener) {
        OkHttpClient cli = new OkHttpClient().newBuilder().build();
        RequestBody rb = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart(Deposit.URL_HOST, EndPointDigiflazz.DEPOSIT)
                .addFormDataPart(Deposit.USERNAME , username)
                .addFormDataPart(Deposit.KEY , key)
                .addFormDataPart(Deposit.BANK, bank)
                .addFormDataPart(Deposit.AMOUNT , amount)
                .addFormDataPart(Deposit.OWNER_NAME , owner)
                .addFormDataPart(Deposit.SIGN , signature)
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
                requestDeposit.getActivity().runOnUiThread(() -> requestListener.onErrorResponse(message));
                call.cancel();
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String responseString = Objects.requireNonNull(response.body()).string();
                requestDeposit.getActivity().runOnUiThread(() -> requestListener.onResponse(responseString));
            }

        });
    }
}
