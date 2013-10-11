package Compiler;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract representation of a compiled RPC interface.
 */
public class Interface {
    /**
     * Name identifying this Interface.
     */
    private String name = "<undefined>";

    /**
     * List of Operations this Interface supports.
     */
    private final List<Operation> operations = new ArrayList<Operation>();

    /**
     * @return The name identifying this Interface.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name identifying this Interface.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The operations supported by this Interface.
     */
    public Iterable<Operation> getOperations() {
        return operations;
    }

    /**
     * Adds a new operation to be supported by this Interface.
     *
     * @param operation The new operation to be supported.
     * @throws Exception
     */
    public void addOperation(Operation operation) throws Exception {
        // Throw on duplicate operation name.
        for (Operation o : operations) {
            if (o.getName().equals(operation.getName())) {
                throw new Exception("Duplicate operation " + operation.getName());
            }
        }

        operations.add(operation);
    }
}
