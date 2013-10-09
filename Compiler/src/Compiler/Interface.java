package Compiler;

import java.util.ArrayList;
import java.util.List;

public class Interface {
    /**
     * Name identifying this Compiler.Interface.
     */
    private String name = "<undefined>";

    /**
     * List of Operations this Compiler.Interface supports.
     */
    private final List<Operation> operations = new ArrayList<Operation>();

    /**
     * @return The name identifying this Compiler.Interface.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name identifying this Compiler.Interface.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The operations supported by this Compiler.Interface.
     */
    public Iterable<Operation> getOperations() {
        return operations;
    }

    /**
     * Adds a new operation to be supported by this Compiler.Interface.
     *
     * @param declaration The new operation to be supported.
     * @throws Exception
     */
    public void addOperation(Operation declaration) throws Exception {
        // Throw on duplicate operation name.
        for (Operation o : operations) {
            if (o.getName().equals(declaration.getName())) {
                throw new Exception("Duplicate operation " + declaration.getName());
            }
        }

        operations.add(declaration);
    }
}
