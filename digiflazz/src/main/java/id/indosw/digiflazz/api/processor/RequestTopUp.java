package id.indosw.digiflazz.api.processor;

import android.app.Activity;

import id.indosw.digiflazz.api.controller.TopUpRequestController;
import id.indosw.digiflazz.api.sign.SignMaker;

public class RequestTopUp {
    private final Activity activity;
    private String username;
    private String backendUrl;
    private String key;
    private String refId;
    private String sku;
    private String custNumber;
    private String msg;

    public RequestTopUp(Activity activity){
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

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public void setSKU(String sku) {
        this.sku = sku;
    }

    public void setCustomerNumber(String custNumber) {
        this.custNumber = custNumber;
    }

    public void setMessage(String msg) {
        this.msg = msg;
    }

    public void startRequestTopUp(RequestTopUp.RequestListener requestListener){
        String signature = SignMaker.getSign(username, key, refId);
        TopUpRequestController.getInstance().execute(this, backendUrl, username, key, sku, custNumber, refId, msg, signature, requestListener);
    }

    public interface RequestListener {
        void onResponse(String response);
        void onErrorResponse(String message);
    }

    public Activity getActivity() {
        return activity;
    }
}
