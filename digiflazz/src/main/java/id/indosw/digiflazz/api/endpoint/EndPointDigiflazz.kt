package id.indosw.digiflazz.api.endpoint

object EndPointDigiflazz {
    @JvmField
    var CEK_SALDO = "https://api.digiflazz.com/v1/cek-saldo"
    @JvmField
    var DAFTAR_HARGA = "https://api.digiflazz.com/v1/price-list"
    @JvmField
    var DEPOSIT = "https://api.digiflazz.com/v1/deposit"

    /*
    Untuk Top Up , Cek status, cek tagihan, Inquiry PLN & bayar tagihan menggunakan end point TRANSAKSI
     */
    @JvmField
    var TRANSAKSI = "https://api.digiflazz.com/v1/transaction"
}