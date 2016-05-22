package net.carpoolme.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created by John Andersen on 5/22/16.
 */
public abstract class Parser extends BasicParser {
    public static final String DEFAULT_CHARSET = "UTF-8";
    public String CHARSET;

    public Parser() {
        this(DEFAULT_CHARSET);
    }

    public Parser(String mCHARSET) {
        CHARSET = mCHARSET;
    }

    public abstract String toString(Object[][] data);

    public abstract Object[][] parse(InputStream in);

    public String toString(Parseable data) {
        return toString(data.Marshal());
    }

    public boolean parse(InputStream in, Parseable data) {
        return data.Unmarshal(parse(in));
    }

    public Object[][] parse(String in) throws UnsupportedEncodingException {
        return parse(new ByteArrayInputStream(in.getBytes(CHARSET)));
    }
}
