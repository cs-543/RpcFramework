public class Parameter {
    private boolean isRef;
    private boolean isIn;
    private boolean isOut;
    private String type = "<undefined>";
    private String name = "<undefined>";

    public boolean isRef() {
        return isRef;
    }

    public void setRef(boolean reference) {
        this.isRef = reference;
    }

    public boolean isIn() {
        return isIn;
    }

    public void setIn(boolean in) {
        this.isIn = in;
    }

    public boolean isOut() {
        return isOut;
    }

    public void setOut(boolean out) {
        this.isOut = out;
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
