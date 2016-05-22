/**
 * Created by John Andersen on 5/13/16.
 */

import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import java.security.SecureRandom;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;

import java.util.Base64;

import java.math.BigInteger;

public class User extends Object {
    public static final String TOKEN_FAILURE = "false";

    public static String CHARSET = "UTF-8";

    private String tokenSecret;
    private int id = 0;
    private String username = "Not Logged In";
    private String password = "";

    User() {
        this(new BigInteger(130, new SecureRandom()).toString(32), "UTF-8");
    }

    User(String mTokenSalt, String charset) {
        tokenSecret = mTokenSalt;
        CHARSET = charset;
    }

    private boolean login() {
        return true;
    }

    public boolean fromStream(InputStream in) {
//        java.util.Scanner input = new java.util.Scanner(in);
//        input.useDelimiter(", ");
//        // Parse in the values
//
//        if (input.hasNext()) {
//            name = input.next();
//        } else {
//            return false;
//        }
//
//        if (input.hasNext()) {
//            age = input.nextInt();
//        } else {
//            return false;
//        }
//
//        if (input.hasNext()) {
//            rate = input.nextFloat();
//        } else {
//            return false;
//        }
        return true;
    }

    @Override
    public String toString() {
        return Token();
    }

    public String Token() {
        if (!login()) {
            return TOKEN_FAILURE;
        }
        String tokenHeaders = "{\"alg\": \"HS256\",\"typ\": \"JWT\"}";
        String tokenPayload = String.format("{\"uid\": \"%d\",\"username\": \"%s\"}", id, username);

        // Base64 encode them all
        Base64.Encoder encoder = Base64.getUrlEncoder();
        try {
            tokenHeaders = encoder.encodeToString(tokenHeaders.getBytes(CHARSET));
            tokenPayload = encoder.encodeToString(tokenPayload.getBytes(CHARSET));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return TOKEN_FAILURE;
        }

        // Add the secret
        String tokenSignature = String.format("%s.%s", tokenHeaders, tokenPayload);
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(tokenSecret.getBytes(), "HmacSHA256");
            try {
                mac.init(secretKeySpec);
                tokenSignature = encoder.encodeToString(mac.doFinal(tokenSignature.getBytes()));
            } catch (InvalidKeyException e) {
                e.printStackTrace();
                return TOKEN_FAILURE;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return TOKEN_FAILURE;
        }

        // Form the token
        return tokenHeaders + "." + tokenPayload + "." + tokenSignature;
    }
}
