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

    private int nestLevel = 0;

    private char cachedChar;

    private boolean needsAdvance = true;

    public ObjectStreamReader(InputStream input) {
        this.input = new InputStreamReader(input);
    }

    private char currentChar() throws Exception {
        initializeIfNecessary();
        return cachedChar;
    }

    private void initializeIfNecessary() throws Exception {
        if ( needsAdvance ) {
            advance();
            needsAdvance = false;
        }
    }

    public Object readObject() throws Exception {
        initializeIfNecessary();

        expect('<');
        StringBuilder typebuilder = new StringBuilder();
        while ( currentChar() != '>' ) {
            typebuilder.append(currentChar());
            needsAdvance = true;
        }
        expect('>');

        String expected_type = typebuilder.toString();
        if ( expected_type.equals("null") ) {
            return null;
        }

        if ( currentChar() == '\"' ) {
            StringBuilder valuebuilder = new StringBuilder();
            needsAdvance = true;
            while ( currentChar() != '\"' ) {
                if ( currentChar() == '\\' ) {
                    needsAdvance = true;
                    valuebuilder.append( currentChar() );
                } else {
                    valuebuilder.append( currentChar() );
                }
                needsAdvance = true;
            }
            String value = valuebuilder.toString();
            needsAdvance = true;
            return convertValueToResult(expected_type, value);
        } else if ( currentChar() == '[' ) {
            needsAdvance = true;
            List elements = new ArrayList();
            while ( currentChar() != ']' ) {
                if ( currentChar() == ',' )
                    needsAdvance = true;
                elements.add(readObject());
            }
            needsAdvance = true;
            return elements.toArray();
        }

        // Okay, it's not a string or a primitive type or an array. It must be
        // some kind of object then.
        Class type = Class.forName( expected_type );
        Object result = type.newInstance();

        expect('{');

        while (currentChar() != '}') {
            if(currentChar() == ',')
                needsAdvance = true;
            // Read field name
            // Figure out type
            String fieldName = (String) readObject();

            Field field = type.getDeclaredField(fieldName);
            field.setAccessible(true);
            Class<?> fieldType = field.getType();
            Object value = null;

            expect(':');
            value = readObject();

            field.set(result, value);
        }

        expect('}');

        return result;
    }

    private void advance() throws Exception {
        cachedChar = nextChar();
    }

    private void expect(char c) throws Exception {
        if (currentChar() != c) {
            throw new Exception("Expected " + c);
        }

        needsAdvance = true;
    }

    private static Object convertValueToResult( String typename, String s )
                         throws Exception {
        Class type = Class.forName( typename );
        if (type == boolean.class || type == Boolean.class) {
            return Boolean.parseBoolean(s);
        }

        if (type == byte.class || type == Byte.class) {
            return Byte.parseByte(s);
        }

        if (type == char.class || type == Character.class) {
            if (s.length() != 1) {
                throw new Exception("Parse error");
            }

            return s.charAt(0);
        }

        if (type == short.class || type == Short.class) {
            return Short.parseShort(s);
        }

        if (type == int.class || type == Integer.class) {
            return Integer.parseInt(s);
        }

        if (type == long.class || type == Long.class) {
            return Long.parseLong(s);
        }

        if (type == float.class || type == Float.class) {
            return Float.parseFloat(s);
        }

        if (type == double.class || type == Double.class) {
            return Double.parseDouble(s);
        }

        // okay, it's not a primitive. Maybe it's a string?
        if (type == String.class) {
            return s;
        }

        // Should never reach this.
        throw new Exception("Unknown primitive type");
    }

    private char nextChar() throws Exception {
        if (bufferPosition == inBufferCount) {
            inBufferCount = input.read(buffer);
            bufferPosition = 0;
        }

        return buffer[bufferPosition++];
    }
}
