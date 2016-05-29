package net.carpoolme.storage;

import java.io.*;

/**
 * Created by John Andersen on 5/29/16.
 */
public class Serializer {
    public static InputStream toInputStream(Object object) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        } catch (IOException ignored) {}
        return null;
    }

    public static Object toObject(InputStream in) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(in);
            Object object = objectInputStream.readObject();
            objectInputStream.close();
            return object;
        } catch (IOException | ClassNotFoundException ignored) {
            ignored.printStackTrace();
        }
        return null;
    }
}
