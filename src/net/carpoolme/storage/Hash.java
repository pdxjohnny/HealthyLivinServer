package net.carpoolme.storage;

import net.carpoolme.utils.JSONParser;

import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by John Andersen on 6/1/16.
 */
public class Hash {
    public static String sha256(Object[][] object) throws NoSuchAlgorithmException {
        System.out.println(new JSONParser().toString(object));
        DigestInputStream in = new DigestInputStream(Serializer.toInputStream(object), MessageDigest.getInstance("SHA-256"));
        try {
            while (in.read() != -1) {
                // Put it all though the hash function
                // This should never cause an IExceptions because we have just serialized the object
            }
        } catch (IOException ignored) {}

        // Create the output
        String result = "";
        for (final byte b : in.getMessageDigest().digest()) {
            result += String.format("%02x", b);
        }

        return result;
    }
}
