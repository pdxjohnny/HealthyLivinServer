package net.carpoolme.storage;

import java.io.*;

/**
 * Created by John Andersen on 5/29/16.
 */
public interface Serializer {
    InputStream toInputStream(Object object);

    Object toObject(InputStream in);
}
