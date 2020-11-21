package utils;

import java.io.OutputStream;

public class NullStream extends OutputStream {
    @Override
    public void write(int b) {
        /* do nothing */
    }
}
