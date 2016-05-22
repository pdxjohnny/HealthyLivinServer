package net.carpoolme.auth;

import net.carpoolme.utils.JSONParser;
import net.carpoolme.utils.Parseable;

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
    public static final String DEFAULT_CHARSET = "UTF-8";

    // Env vars
    public static final String JWT_SECRET = "JWT_SECRET";

    public String CHARSET = DEFAULT_CHARSET;

    private JSONParser parser = new JSONParser();
    private String tokenSecret;

    public JWT(String mTokenSalt) {
        tokenSecret = mTokenSalt;
    }

    public JWT(String mTokenSalt, String charset) {
        tokenSecret = mTokenSalt;
        CHARSET = charset;
    }

    public static JWT fromEnv(String ifSecretNull) {
        String secret = null;
        try {
            secret = System.getenv(JWT_SECRET);
        } catch (NullPointerException | SecurityException ignored) {}
        if (secret == null) {
            secret = ifSecretNull;
            System.out.println("WARN: Secret env var was null, using provided default: " + secret);
        }
        return new JWT(secret);
    }

    public String toString(Parseable data) {
        String tokenHeaders = "{\"alg\": \"HS256\",\"typ\": \"JWT\"}";
        String tokenPayload = parser.toString(data);

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
