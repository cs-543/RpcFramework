import java.util.ArrayList;
import java.util.List;

public class Interface {
    private String name = "<undefined>";
    private List<Operation> operations = new ArrayList<Operation>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Iterable<Operation> getOperations() {
        return operations;
    }

    public void addOperation(Operation declaration) throws Exception {
        for (Operation o : operations) {
            if (o.getName().equals(declaration.getName())) {
                throw new Exception("Duplicate operation " + declaration.getName());
            }
        }

        operations.add(declaration);
    }
}
