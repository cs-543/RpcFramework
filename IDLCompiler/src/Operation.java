import java.util.*;

public class Operation {
    private String locality;
    private String type;
    private String name = "<undefined>";

    private List<Parameter> parameters = new ArrayList<Parameter>();

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
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

    public Iterable<Parameter> getParameters() {
        return parameters;
    }

    public void addParameter(Parameter parameter) throws Exception {
        for (Parameter p : parameters) {
            if (p.getName().equals(parameter.getName())) {
                throw new Exception("Duplicate parameter " + parameter.getName());
            }
        }

        parameters.add(parameter);
    }
}
