package Rpc;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ObjectStreamReader {
    private InputStreamReader input;

    private final char[] buffer = new char[4096];

    private int bufferPosition;

    private int inBufferCount;

    private char currentChar;

    private int nestLevel = 0;

    public ObjectStreamReader(InputStream input) {
        this.input = new InputStreamReader(input);
    }

    public <T> T readObject(Class<T> type) throws Exception {
        T result = type.newInstance();

        advance();
        expect('{');

        while (currentChar != '}') {
            // Read field name
            // Figure out type
            String fieldName = readString();

            Field field = type.getDeclaredField(fieldName);
            field.setAccessible(true);
            Class<?> fieldType = field.getType();
            Object value = null;

            expect(':');

            switch (currentChar) {
                case '"':
                    if (fieldType == String.class) {
                        value = readString();
                    } else {
                        value = readPrimitive(fieldType);
                    }
                    break;
                case '[':
                    value = readArray(fieldType.getComponentType());
                    break;
                case '{':
                    //readObject();
                    break;
                default:
                    throw new Exception("Unexpected " + currentChar);
            }

            field.set(result, value);
        }

        expect('}');

        return result;
    }

    private Object readPrimitive(Class<?> type) throws Exception {
        expect('"');

        StringBuilder builder = new StringBuilder();

        while (currentChar != '"') {
            builder.append(currentChar);
            advance();
        }

        expect('"');

        String s = builder.toString();

        if (type == Boolean.class) {
            return Boolean.parseBoolean(s);
        }

        if (type == Byte.class) {
            return Byte.parseByte(s);
        }

        if (type == Character.class) {
            if (s.length() != 1) {
                throw new Exception("Parse error");
            }

            return s.charAt(0);
        }

        if (type == Short.class) {
            return Short.parseShort(s);
        }

        if (type == Integer.class) {
            return Integer.parseInt(s);
        }

        if (type == Float.class) {
            return Float.parseFloat(s);
        }

        if (type == Double.class) {
            return Double.parseDouble(s);
        }

        // Should never reach this.
        throw new Exception("Unknown primitive type");
    }

    private String readString() throws Exception {
        expect('"');

        StringBuilder builder = new StringBuilder();
        // TODO handle escaped strings
        while (currentChar != '"') {
            builder.append(currentChar);
            advance();
        }

        expect('"');

        return builder.toString();
    }

    private Object readArray(Class<?> type) throws Exception {
        expect('[');

        List elements = new ArrayList();

        while (currentChar != ']') {
            if (type.isPrimitive()) {
                elements.add(readPrimitive(type));
            }
        }

        expect(']');

        return elements.toArray();
    }

    private char advance() throws Exception {
        currentChar = nextChar();
        return currentChar;
    }

    private void expect(char c) throws Exception {
        if (currentChar != c) {
            throw new Exception("Expected " + c);
        }

        advance();
    }

    private char nextChar() throws Exception {
        if (bufferPosition == inBufferCount) {
            inBufferCount = input.read(buffer);
            bufferPosition = 0;
        }

        return buffer[bufferPosition++];
    }
}
