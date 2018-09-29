package jsonlenium.build.io

import groovy.json.JsonSlurper

import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

class JsonReader {
    private final int MARK_SIZE = 3

    Object parse(File file) {
        parse(file, StandardCharsets.UTF_8)
    }

    Object parse(File file, Charset charset) {
        if (charset == StandardCharsets.UTF_8) {
            readWithoutUtf8Bom(file)
        } else {
            read(file, charset)
        }
    }

    Object read(File file, Charset charset) {
        new JsonSlurper().parse(file, charset.toString())
    }

    Object readWithoutUtf8Bom(File file) throws IOException {
        InputStream is
        try {
            // When BOM is attached, move to position three bytes from the beginning
            is = new FileInputStream(file)
            if (!is.markSupported()) {
                is = new BufferedInputStream(is)
            }

            is.mark(MARK_SIZE)
            if (is.available() >= MARK_SIZE) {
                byte[] b = [0, 0, 0]
                is.read(b, 0, 3)
                if (b[0] != (byte) 0xEF || b[1] != (byte) 0xBB || b[2] != (byte) 0xBF) {
                    is.reset()
                }
            }
            new JsonSlurper().parse(is)
        } finally {
            try {
                if (is != null) {
                    is.close()
                }
            } catch (IOException ignore) {
                // nothing to do
            }
        }
    }
}
