package Compiler;

public class Parameter {
    private boolean isRef;
    private boolean isIn;
    private boolean isOut;
    private String type = "<undefined>";
    private String name = "<undefined>";

    public boolean isRef() {
        return isRef;
    }

    public void setRef(boolean ref) {
        isRef = ref;
    }

    public boolean isIn() {
        return isIn;
    }

    public void setIn(boolean in) {
        isIn = in;
    }

    public boolean isOut() {
        return isOut;
    }

    public void setOut(boolean out) {
        isOut = out;
    }

    public boolean isInOut() {
        return isIn && isOut;
    }

    public void setInOut(boolean inOut) {
        isIn = isOut = inOut;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
