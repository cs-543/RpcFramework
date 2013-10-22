package Rpc.Compiler;

import java.util.ArrayList;
import java.util.List;

class Operation {
    private boolean isAsync;
    private boolean isLocal;
    private ExecutionPolicy policy = ExecutionPolicy.AtMostOnce;
    private String type = "<undefined>";
    private String name = "<undefined>";
    private final List<Parameter> parameters = new ArrayList<Parameter>();

    public boolean isAsync() {
        return isAsync;
    }

    public void setAsync(boolean async) {
        isAsync = async;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }

    public ExecutionPolicy getPolicy() {
        return policy;
    }

    public void setPolicy(ExecutionPolicy policy) {
        this.policy = policy;
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
