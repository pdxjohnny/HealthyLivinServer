package net.carpoolme.utils;

import net.carpoolme.healthylivin.User;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Created by John Andersen on 5/22/16.
 */
public class JWT {
    public static final String JWT_FAILURE = "false";
    public static final String JWT_CHARSET = "UTF-8";

    // Env vars
    public static final String JWT_SECRET = "JWT_SECRET";

    public String CHARSET;

    private String tokenSecret;

    JWT(String mTokenSalt, String charset) {
        tokenSecret = mTokenSalt;
        CHARSET = charset;
    }

    public static JWT fromEnv() {
        return new JWT(System.getenv(JWT_SECRET), JWT_CHARSET);
    }

    public String toString(User user) {
        String tokenHeaders = "{\"alg\": \"HS256\",\"typ\": \"JWT\"}";
        String tokenPayload = String.format("{\"uid\": \"%d\",\"username\": \"%s\"}", user.getId(), user.getUsername());

        // Base64 encode them all
        Base64.Encoder encoder = Base64.getUrlEncoder();
        try {
            tokenHeaders = encoder.encodeToString(tokenHeaders.getBytes(CHARSET));
            tokenPayload = encoder.encodeToString(tokenPayload.getBytes(CHARSET));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return JWT_FAILURE;
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
                return JWT_FAILURE;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return JWT_FAILURE;
        }

        // Form the token
        return tokenHeaders + "." + tokenPayload + "." + tokenSignature;
    }
}
