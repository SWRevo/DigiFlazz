# Un-Official Library Of DigiFlazz Implementation
[![](https://jitpack.io/v/SWRevo/DigiFlazz.svg)](https://jitpack.io/#SWRevo/DigiFlazz)

![Sample screenshot](https://digiflazz.com/images/logo/main.png)

## Implementation in Android Studio or Sketchware
## Gradle
Add the following to your `build.gradle` to use:
```
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.SWRevo:DigiFlazz:v1.1.0'
}
```

## Usage

## KOTLIN

```java

    //Field Listener
    private var requestProductListner: RequestProduct.RequestListener? = null
    private var requestTransactionStatusListener: RequestTransactionStatus.RequestListener? = null
    private var requestTopUpListener: RequestTopUp.RequestListener? = null
    private var requestPayBillingListener: RequestPayBilling.RequestListener? = null
    private var requestDepositListener: RequestDeposit.RequestListener? = null
    private var requestCheckBillsListener: RequestCheckBills.RequestListener? = null
    private var requestBalanceListener: RequestBalance.RequestListener? = null
    private var requestInquiryPLNListener: RequestInquiryPLN.RequestListener? = null
    
    //Call & Get Response
    private fun testRequestTransactionStatus() {
        /**
         * Untuk cek Transaction Status gunakan backend API cek tagihan
         * URL : https://kopimanis.my.id/digiflazz/api-cektagihan.php
         * Cek status dapat dilakukan dengan melakukan topup ulang dengan ref id yang sama pada transaksi sebelumnya.
         * Jangan pernah mencoba untuk melakukan Cek Status terhadap transaksi yang sudah lewat 90 HARI
         * Karena hal tersebut akan menyebabkan pembuatan transaksi BARU.
         */
        val requestTransactionStatus = RequestTransactionStatus(this)
        requestTransactionStatus.setBackendUrl("")
        requestTransactionStatus.setUserName("")
        requestTransactionStatus.setKey("")
        requestTransactionStatus.setSKU("")
        requestTransactionStatus.setRefId("")
        requestTransactionStatus.setCustomerNumber("")
        requestTransactionStatus.startRequestTransactionStatus(requestTransactionStatusListener, true)
        requestTransactionStatusListener = object : RequestTransactionStatus.RequestListener {
            override fun onResponse(response: String?) {
                tvResponse!!.text = response
            }

            override fun onErrorResponse(message: String?) {
                tvResponse!!.text = message
            }
        }
    }

    private fun testRequestTopUp() {
        /**
         * Untuk Request Top Up Pra bayar gunakan backend API
         * URL : https://kopimanis.my.id/digiflazz/api-topupgolive.php
         * RefId adalah kode unik yang anda buat, misalkan kombinasi DATE & TIME
         */
        val requestTopUp = RequestTopUp(this)
        requestTopUp.setBackendUrl("")
        requestTopUp.setUserName("")
        requestTopUp.setKey("")
        requestTopUp.setCustomerNumber("")
        requestTopUp.setRefId("")
        requestTopUp.setSKU("")
        requestTopUp.setMessage("")
        requestTopUp.startRequestTopUp(requestTopUpListener)
        requestTopUpListener = object : RequestTopUp.RequestListener {
            override fun onResponse(response: String?) {
                tvResponse!!.text = response
            }

            override fun onErrorResponse(message: String?) {
                tvResponse!!.text = message
            }
        }
    }

    private fun testRequestProduct() {
        /**
         * Untuk cek list product gunakan backend API
         * URL : https://kopimanis.my.id/digiflazz/api-productlist.php
         */
        val requestProduct = RequestProduct(this)
        requestProduct.setBackendUrl(ConfigApi.BACKEND_URL_PRICELIST)
        requestProduct.setUserName(ConfigApi.USERNAME)
        requestProduct.setKey(ConfigApi.KEY)
        requestProduct.setSKU("")
        requestProduct.startRequestProduct(requestProductListner, true)
        requestProductListner = object : RequestProduct.RequestListener {
            override fun onResponse(response: String?) {
                tvResponse!!.text = response
            }

            override fun onErrorResponse(message: String?) {
                tvResponse!!.text = message
            }
        }
    }

    private fun testRequestPayBilling() {
        /**
         * Untuk Pay Billing / Bayar Tagihan gunakan backend API URL : https://kopimanis.my.id/digiflazz/api-bayartagihan.php
         * RefId adalah kode unik yang anda buat, misalkan kombinasi DATE & TIME
         * SKU : Kode produk Anda
         */
        val requestPayBilling = RequestPayBilling(this)
        requestPayBilling.setBackendUrl("")
        requestPayBilling.setUserName("")
        requestPayBilling.setKey("")
        requestPayBilling.setCustomerNumber("")
        requestPayBilling.setSKU("")
        requestPayBilling.setRefId("")
        requestPayBilling.startRequestPayBilling(requestPayBillingListener)
        requestPayBillingListener = object : RequestPayBilling.RequestListener {
            override fun onResponse(response: String?) {
                tvResponse!!.text = response
            }

            override fun onErrorResponse(message: String?) {
                tvResponse!!.text = message
            }
        }
    }

    private fun testRequestInquiryPLN() {
        /**
         * Untuk Inquiry akun PLN gunakan backend API URL : https://kopimanis.my.id/digiflazz/api-inquirypln.php
         */
        val requestInquiryPLN = RequestInquiryPLN(this)
        requestInquiryPLN.setBackendUrl("")
        requestInquiryPLN.setCustomerNumber("")
        requestInquiryPLN.startRequestInquiryPLN(requestInquiryPLNListener)
        requestInquiryPLNListener = object : RequestInquiryPLN.RequestListener {
            override fun onResponse(response: String?) {
                tvResponse!!.text = response
            }

            override fun onErrorResponse(message: String?) {
                tvResponse!!.text = message
            }
        }
    }

    private fun testRequestDeposit() {
        /**
         * Deposit adalah fitur yang membuat Anda dapat melakukan penarikan tiket deposit.
         * Untuk Deposit gunakan backend API URL : https://kopimanis.my.id/digiflazz/api-depositsaldo.php
         * Bank : Nama bank tujuan yang akan menjadi tujuan transfer Anda, Pilihan bank: BCA / MANDIRI / BRI
         * Amount : Jumlah deposit yang Anda inginkan
         * Owner Name : Nama pemilik rekening yang melakukan transfer deposit ke Digiflazz
         */
        val requestDeposit = RequestDeposit(this)
        requestDeposit.setBackendUrl("")
        requestDeposit.setUserName("")
        requestDeposit.setKey("")
        requestDeposit.setAmount("")
        requestDeposit.setBank("")
        requestDeposit.setOwnerName("")
        requestDeposit.startRequestDeposit(requestDepositListener)
        requestDepositListener = object : RequestDeposit.RequestListener {
            override fun onResponse(response: String?) {
                tvResponse!!.text = response
            }

            override fun onErrorResponse(message: String?) {
                tvResponse!!.text = message
            }
        }
    }

    private fun testRequestCheckBills() {
        /**
         * Untuk cek Tagihan gunakan backend API URL : https://kopimanis.my.id/digiflazz/api-cektagihan.php
         * SKU : kode product yang berhubungan dengan tagihan seperti PLN, PASCA BAYAR TELKOMSEL dsb
         */
        val requestCheckBills = RequestCheckBills(this)
        requestCheckBills.setBackendUrl("")
        requestCheckBills.setUserName("")
        requestCheckBills.setKey("")
        requestCheckBills.setCustomerNumber("")
        requestCheckBills.setSKU("")
        requestCheckBills.setRefId("")
        requestCheckBills.startRequestCheckBill(requestCheckBillsListener)
        requestCheckBillsListener = object : RequestCheckBills.RequestListener {
            override fun onResponse(response: String?) {
                tvResponse!!.text = response
            }

            override fun onErrorResponse(message: String?) {
                tvResponse!!.text = message
            }
        }
    }

    private fun testRequestBalance() {
        /**
         * Untuk cek Saldo gunakan backend API URL : https://kopimanis.my.id/digiflazz/api-ceksaldo.php
         */
        val requestBalance = RequestBalance(this)
        requestBalance.setBackendUrl("")
        requestBalance.setUserName("")
        requestBalance.setKey("")
        requestBalance.startRequestBalance(requestBalanceListener)
        requestBalanceListener = object : RequestBalance.RequestListener {
            override fun onResponse(response: String?) {
                tvResponse!!.text = response
            }

            override fun onErrorResponse(message: String?) {
                tvResponse!!.text = message
            }
        }
    }

```

## JAVA

```java

    //Field Listener
    private RequestProduct.RequestListener requestProductListner;
    private RequestTransactionStatus.RequestListener requestTransactionStatusListener;
    private RequestTopUp.RequestListener requestTopUpListener;
    private RequestPayBilling.RequestListener requestPayBillingListener;
    private RequestDeposit.RequestListener requestDepositListener;
    private RequestCheckBills.RequestListener requestCheckBillsListener;
    private RequestBalance.RequestListener requestBalanceListener;
    private RequestInquiryPLN.RequestListener requestInquiryPLNListener;
    
    //Call & Get Response
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

```

## License

Copyright 2021 IndoSW & [DigiFlazz](https://digiflazz.com/tentang-kami)
