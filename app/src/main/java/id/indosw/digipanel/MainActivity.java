package id.indosw.digipanel;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import id.indosw.digiflazz.api.processor.RequestBalance;
import id.indosw.digiflazz.api.processor.RequestProduct;

public class MainActivity extends AppCompatActivity {

    private RequestProduct.RequestListener requestProductListner;
    private TextView tvResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResponse = findViewById(R.id.tvResponse);

        findViewById(R.id.btnRun).setOnClickListener(v -> {
            RequestProduct requestProduct = new RequestProduct(MainActivity.this);
            requestProduct.setBackendUrl(ConfigApi.BACKEND_URL_PRICELIST);
            requestProduct.setUserName(ConfigApi.USERNAME);
            requestProduct.setKey(ConfigApi.KEY);
            requestProduct.setSKU("");
            requestProduct.startRequestProduct(requestProductListner, true);
        });

        requestProductListner = new RequestProduct.RequestListener(){
            @Override
            public void onResponse(String response) {
                tvResponse.setText(response);
            }

            @Override
            public void onErrorResponse(String message) {

            }
        };
    }
}