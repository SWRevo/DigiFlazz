package id.indosw.digipanel;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import id.indosw.digiflazz.api.processor.RequestBalance;
import id.indosw.digiflazz.api.processor.RequestCheckBills;
import id.indosw.digiflazz.api.processor.RequestDeposit;
import id.indosw.digiflazz.api.processor.RequestInquiryPLN;
import id.indosw.digiflazz.api.processor.RequestPayBilling;
import id.indosw.digiflazz.api.processor.RequestProduct;
import id.indosw.digiflazz.api.processor.RequestTopUp;
import id.indosw.digiflazz.api.processor.RequestTransactionStatus;

public class MainActivity extends AppCompatActivity {

    private TextView tvResponse;

    private RequestProduct.RequestListener requestProductListner;
    private RequestTransactionStatus.RequestListener requestTransactionStatusListener;
    private RequestTopUp.RequestListener requestTopUpListener;
    private RequestPayBilling.RequestListener requestPayBillingListener;
    private RequestDeposit.RequestListener requestDepositListener;
    private RequestCheckBills.RequestListener requestCheckBillsListener;
    private RequestBalance.RequestListener requestBalanceListener;
    private RequestInquiryPLN.RequestListener requestInquiryPLNListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvResponse = findViewById(R.id.tvResponse);

        testRequestBalance();
        testRequestCheckBills();
        testRequestDeposit();
        testRequestInquiryPLN();
        testRequestPayBilling();
        testRequestProduct();
        testRequestTopUp();
        testRequestTransactionStatus();

        findViewById(R.id.btnRun).setOnClickListener(v -> testRequestProduct());
    }

    private void testRequestTransactionStatus() {
        /**
        Untuk cek Transaction Status gunakan backend API cek tagihan
         URL : https://kopimanis.my.id/digiflazz/api-cektagihan.php
         Cek status dapat dilakukan dengan melakukan topup ulang dengan ref id yang sama pada transaksi sebelumnya.
         Jangan pernah mencoba untuk melakukan Cek Status terhadap transaksi yang sudah lewat 90 HARI
         Karena hal tersebut akan menyebabkan pembuatan transaksi BARU.
         */
        RequestTransactionStatus requestTransactionStatus = new RequestTransactionStatus(this);
        requestTransactionStatus.setBackendUrl("");
        requestTransactionStatus.setUserName("");
        requestTransactionStatus.setKey("");
        requestTransactionStatus.setSKU("");
        requestTransactionStatus.setRefId("");
        requestTransactionStatus.setCustomerNumber("");
        requestTransactionStatus.startRequestTransactionStatus(requestTransactionStatusListener, true);

        requestTransactionStatusListener = new RequestTransactionStatus.RequestListener() {
            @Override
            public void onResponse(String response) {
                tvResponse.setText(response);
            }

            @Override
            public void onErrorResponse(String message) {
                tvResponse.setText(message);
            }
        };
    }

    private void testRequestTopUp() {
        /**
         Untuk Request Top Up Pra bayar gunakan backend API
         URL : https://kopimanis.my.id/digiflazz/api-topupgolive.php
         RefId adalah kode unik yang anda buat, misalkan kombinasi DATE & TIME
         */
        RequestTopUp requestTopUp = new RequestTopUp(this);
        requestTopUp.setBackendUrl("");
        requestTopUp.setUserName("");
        requestTopUp.setKey("");
        requestTopUp.setCustomerNumber("");
        requestTopUp.setRefId("");
        requestTopUp.setSKU("");
        requestTopUp.setMessage("");
        requestTopUp.startRequestTopUp(requestTopUpListener);

        requestTopUpListener = new RequestTopUp.RequestListener() {
            @Override
            public void onResponse(String response) {
                tvResponse.setText(response);
            }

            @Override
            public void onErrorResponse(String message) {
                tvResponse.setText(message);
            }
        };
    }

    private void testRequestProduct() {
        /**
         Untuk cek list product gunakan backend API
         URL : https://kopimanis.my.id/digiflazz/api-productlist.php
         */
        RequestProduct requestProduct = new RequestProduct(MainActivity.this);
        requestProduct.setBackendUrl(ConfigApi.BACKEND_URL_PRICELIST);
        requestProduct.setUserName(ConfigApi.USERNAME);
        requestProduct.setKey(ConfigApi.KEY);
        requestProduct.setSKU("");
        requestProduct.startRequestProduct(requestProductListner, true);

        requestProductListner = new RequestProduct.RequestListener(){
            @Override
            public void onResponse(String response) {
                tvResponse.setText(response);
            }

            @Override
            public void onErrorResponse(String message) {
                tvResponse.setText(message);
            }
        };
    }

    private void testRequestPayBilling() {
        /**
         * Untuk Pay Billing / Bayar Tagihan gunakan backend API URL : https://kopimanis.my.id/digiflazz/api-bayartagihan.php
         * RefId adalah kode unik yang anda buat, misalkan kombinasi DATE & TIME
         * SKU : Kode produk Anda
         */

        RequestPayBilling requestPayBilling = new RequestPayBilling(this);
        requestPayBilling.setBackendUrl("");
        requestPayBilling.setUserName("");
        requestPayBilling.setKey("");
        requestPayBilling.setCustomerNumber("");
        requestPayBilling.setSKU("");
        requestPayBilling.setRefId("");
        requestPayBilling.startRequestPayBilling(requestPayBillingListener);

        requestPayBillingListener = new RequestPayBilling.RequestListener() {
            @Override
            public void onResponse(String response) {
                tvResponse.setText(response);
            }

            @Override
            public void onErrorResponse(String message) {
                tvResponse.setText(message);
            }
        };
    }

    private void testRequestInquiryPLN() {

        /**
         * Untuk Inquiry akun PLN gunakan backend API URL : https://kopimanis.my.id/digiflazz/api-inquirypln.php
         */

        RequestInquiryPLN requestInquiryPLN = new RequestInquiryPLN(this);
        requestInquiryPLN.setBackendUrl("");
        requestInquiryPLN.setCustomerNumber("");
        requestInquiryPLN.startRequestInquiryPLN(requestInquiryPLNListener);

        requestInquiryPLNListener = new RequestInquiryPLN.RequestListener() {
            @Override
            public void onResponse(String response) {
                tvResponse.setText(response);
            }

            @Override
            public void onErrorResponse(String message) {
                tvResponse.setText(message);
            }
        };
    }

    private void testRequestDeposit() {

        /**
         * Deposit adalah fitur yang membuat Anda dapat melakukan penarikan tiket deposit.
         * Untuk Deposit gunakan backend API URL : https://kopimanis.my.id/digiflazz/api-depositsaldo.php
         * Bank : Nama bank tujuan yang akan menjadi tujuan transfer Anda, Pilihan bank: BCA / MANDIRI / BRI
         * Amount : Jumlah deposit yang Anda inginkan
         * Owner Name : Nama pemilik rekening yang melakukan transfer deposit ke Digiflazz
         */

        RequestDeposit requestDeposit = new RequestDeposit(this);
        requestDeposit.setBackendUrl("");
        requestDeposit.setUserName("");
        requestDeposit.setKey("");
        requestDeposit.setAmount("");
        requestDeposit.setBank("");
        requestDeposit.setOwnerName("");
        requestDeposit.startRequestDeposit(requestDepositListener);

        requestDepositListener = new RequestDeposit.RequestListener() {
            @Override
            public void onResponse(String response) {
                tvResponse.setText(response);
            }

            @Override
            public void onErrorResponse(String message) {
                tvResponse.setText(message);
            }
        };
    }

    private void testRequestCheckBills() {

        /**
         * Untuk cek Tagihan gunakan backend API URL : https://kopimanis.my.id/digiflazz/api-cektagihan.php
         * SKU : kode product yang berhubungan dengan tagihan seperti PLN, PASCA BAYAR TELKOMSEL dsb
         */

        RequestCheckBills requestCheckBills = new RequestCheckBills(this);
        requestCheckBills.setBackendUrl("");
        requestCheckBills.setUserName("");
        requestCheckBills.setKey("");
        requestCheckBills.setCustomerNumber("");
        requestCheckBills.setSKU("");
        requestCheckBills.setRefId("");
        requestCheckBills.startRequestCheckBill(requestCheckBillsListener);

        requestCheckBillsListener = new RequestCheckBills.RequestListener() {
            @Override
            public void onResponse(String response) {
                tvResponse.setText(response);
            }

            @Override
            public void onErrorResponse(String message) {
                tvResponse.setText(message);
            }
        };
    }

    private void testRequestBalance() {
        /**
         * Untuk cek Saldo gunakan backend API URL : https://kopimanis.my.id/digiflazz/api-ceksaldo.php
         */
        RequestBalance requestBalance = new RequestBalance(this);
        requestBalance.setBackendUrl("");
        requestBalance.setUserName("");
        requestBalance.setKey("");
        requestBalance.startRequestBalance(requestBalanceListener);

        requestBalanceListener = new RequestBalance.RequestListener() {
            @Override
            public void onResponse(String response) {
                tvResponse.setText(response);
            }

            @Override
            public void onErrorResponse(String message) {
                tvResponse.setText(message);
            }
        };
    }
}