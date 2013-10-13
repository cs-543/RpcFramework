package Compiler;

import java.util.HashMap;
import java.util.Map;

class TypeBuilder {
    /**
     * Maps primitive types to their wrapping type.
     */
    private static final Map<String, String> wrappers = new HashMap<String, String>();

    static {
        wrappers.put("boolean", "Boolean");
        wrappers.put("byte", "Byte");
        wrappers.put("char", "Character");
        wrappers.put("double", "Double");
        wrappers.put("float", "Float");
        wrappers.put("int", "Integer");
        wrappers.put("long", "Long");
        wrappers.put("short", "Short");
        wrappers.put("void", "Void");
    }

    private TypeBuilder() {
    }

    public static String genericCallbackType(String type) {
        return genericType("Callback", type);
    }

    public static String genericOutType(String type) {
        return genericType("Out", type);
    }

    public static String genericInOutType(String type) {
        return genericType("InOut", type);
    }

    public static String genericType(String generic, String parameter) {
        return generic + "<" + wrapper(parameter) + ">";
    }

    public static String wrapper(String type) {
        if (wrappers.containsKey(type)) {
            return wrappers.get(type);
        }

        return type;
    }
}
