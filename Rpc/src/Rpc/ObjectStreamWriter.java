package Rpc;

import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ObjectStreamWriter {
    private PrintStream output;

    public ObjectStreamWriter(OutputStream output) {
        this.output = new PrintStream(output);
    }

    public void write(Object object) throws Exception {
        // Null reference.
        if (object == null) {
            output.append("<null>");
            return;
        }

        output.append("<" + object.getClass().getName() + ">");

        // Primitive types.
        if (isPrimitive(object.getClass())) {
            writePrimitive(object);
            return;
        }

        // Strings.
        if (object.getClass() == String.class) {
            writeString(object);
            return;
        }

        // Arrays.
        if (object.getClass().isArray()) {
            writeArray(object);
            return;
        }

        // Object.
        writeObject(object);
    }

    private void writePrimitive(Object primitive) {
        output.append("\"");
        output.append(primitive.toString());
        output.append("\"");
    }

    private void writeString(Object string) {
        output.append("\"");
        // TODO better escape mechanism.
        output.append(string.toString().replace("\"", "\\\""));
        output.append("\"");
    }

    private void writeArray(Object array) throws Exception {
        // Array boundary start.
        output.append("[");

        // Serialize each element.
        String delimiter = "";
        for (int i = 0; i < Array.getLength(array); ++i) {
            output.append(delimiter);
            delimiter = ",";

            write(Array.get(array, i));
        }

        // Array boundary end.
        output.append("]");
    }

    private void writeObject(Object object) throws Exception {
        // Object boundary start.
        output.append("{");

        // Serialize each field.
        String delimiter = "";
        for (Field f: object.getClass().getDeclaredFields()) {
            f.setAccessible(true);

            output.append(delimiter);
            delimiter = ",";
            write(f.getName());
            output.append(":");

            write(f.get(object));
        }

        // Object boundary end.
        output.append("}");
    }

    private boolean isPrimitive(Class<?> type) {
        return type.isPrimitive() ||
                type == Boolean.class ||
                type == Byte.class ||
                type == Character.class ||
                type == Short.class ||
                type == Integer.class ||
                type == Long.class ||
                type == Float.class ||
                type == Double.class;
    }
}
