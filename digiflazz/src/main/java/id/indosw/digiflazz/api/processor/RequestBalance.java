package id.indosw.digiflazz.api.processor;

import android.app.Activity;

import id.indosw.digiflazz.api.controller.BalanceRequestController;
import id.indosw.digiflazz.api.sign.SignMaker;

import static id.indosw.digiflazz.api.commandvalue.CommandValue.CMD_BALANCE_CHECK;
import static id.indosw.digiflazz.api.commandvalue.CommandValue.SIGN_BALANCE_CHECK;

public class RequestBalance {

    private final Activity activity;
    private String username;
    private String backendUrl;
    private String key;

    public RequestBalance(Activity activity){
        this.activity = activity;
    }

    public void setBackendUrl(String backendUrl){
        this.backendUrl = backendUrl;
    }

    public void setUserName(String username){
        this.username = username;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void startRequestBalance(RequestListener requestListener){
        String signature = SignMaker.getSign(username, key, SIGN_BALANCE_CHECK);
        BalanceRequestController.getInstance().execute(this, backendUrl, username, key, CMD_BALANCE_CHECK, signature, requestListener);
    }

    public interface RequestListener {
        void onResponse(String response);
        void onErrorResponse(String message);
    }

    public Activity getActivity() {
        return activity;
    }
}
