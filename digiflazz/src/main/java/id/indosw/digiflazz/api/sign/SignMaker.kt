package id.indosw.digiflazz.api.sign;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SignMaker {
    public static String encrypt(final String text) {
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest
                    .getInstance("MD5");
            digest.update(text.getBytes());
            byte[] messageDigest = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                StringBuilder h = new StringBuilder(Integer.toHexString(0xFF & b));
                while (h.length() < 2)
                    h.insert(0, "0");
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getSign(String username, String key, String extensionSign){
        return encrypt(username + key + extensionSign);
    }
}
