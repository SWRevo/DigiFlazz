package id.indosw.digiflazz.api.processor;

import android.app.Activity;

import id.indosw.digiflazz.api.controller.ProductRequestController;
import id.indosw.digiflazz.api.sign.SignMaker;

import static id.indosw.digiflazz.api.commandvalue.CommandValue.CMD_BALANCE_CHECK;
import static id.indosw.digiflazz.api.commandvalue.CommandValue.CMD_PRICELIST_PASCA;
import static id.indosw.digiflazz.api.commandvalue.CommandValue.CMD_PRICELIST_PRA;
import static id.indosw.digiflazz.api.commandvalue.CommandValue.SIGN_BALANCE_CHECK;
import static id.indosw.digiflazz.api.commandvalue.CommandValue.SIGN_PRICELIST;

public class RequestProduct {
    private final Activity activity;
    private String username;
    private String backendUrl;
    private String key;
    private String sku;

    public RequestProduct(Activity activity){
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

    public void setSKU(String sku) {
        this.sku = sku;
    }

    @SuppressWarnings("SpellCheckingInspection")
    public void startRequestProduct(RequestProduct.RequestListener requestListener, boolean prabayar){
        String signature = SignMaker.getSign(username, key, SIGN_PRICELIST);
        String cmdSet;
        if(prabayar){ cmdSet = CMD_PRICELIST_PRA;}
        else {cmdSet = CMD_PRICELIST_PASCA;}
        ProductRequestController.getInstance().execute(this, backendUrl, username, key, cmdSet, sku, signature, requestListener);
    }

    public interface RequestListener {
        void onResponse(String response);
        void onErrorResponse(String message);
    }

    public Activity getActivity() {
        return activity;
    }
}
