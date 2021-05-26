package id.indosw.digiflazz.api.processor;

import android.app.Activity;

import id.indosw.digiflazz.api.controller.DepositRequestController;
import id.indosw.digiflazz.api.sign.SignMaker;

import static id.indosw.digiflazz.api.commandvalue.CommandValue.SIGN_DEPOSIT;

public class RequestDeposit {
    private final Activity activity;
    private String username;
    private String backendUrl;
    private String key;
    private String amount;
    private String bank;
    private String owner;

    public RequestDeposit(Activity activity){
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

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public void setOwnerName(String owner) {
        this.owner = owner;
    }

    public void startRequestDeposit(RequestDeposit.RequestListener requestListener){
        String signature = SignMaker.getSign(username, key, SIGN_DEPOSIT);
        DepositRequestController.getInstance().execute(this, backendUrl, username, key, amount, bank, owner, signature, requestListener);
    }

    public interface RequestListener {
        void onResponse(String response);
        void onErrorResponse(String message);
    }

    public Activity getActivity() {
        return activity;
    }
}
