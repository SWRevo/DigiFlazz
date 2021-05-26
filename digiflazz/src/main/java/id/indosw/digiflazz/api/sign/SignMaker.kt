package id.indosw.digiflazz.api.sign

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object SignMaker {
    private fun encrypt(text: String): String {
        try {
            // Create MD5 Hash
            val digest = MessageDigest
                    .getInstance("MD5")
            digest.update(text.toByteArray())
            val messageDigest = digest.digest()

            // Create Hex String
            val hexString = StringBuilder()
            for (b in messageDigest) {
                val h = StringBuilder(Integer.toHexString(0xFF and b.toInt()))
                while (h.length < 2) h.insert(0, "0")
                hexString.append(h)
            }
            return hexString.toString()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return ""
    }

    @JvmStatic
    fun getSign(username: String, key: String, extensionSign: String): String {
        return encrypt(username + key + extensionSign)
    }
}