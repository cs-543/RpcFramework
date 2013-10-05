import Rpc.Compiler.*;

public class Program {
    public static void main(String[] args) throws Exception {
        /*
        Rpc.Compiler.Rpc.Compiler compiler = new Rpc.Compiler.Rpc.Compiler(new FileInputStream("interface.idl"));
        */

        Interface exampleInterface;
        Operation operation;
        Parameter parameter;

        exampleInterface = new Interface();
        exampleInterface.setName("RemoteInterface");

        // ---- someLocalOperation
        operation = new Operation();
        operation.setName("someLocalOperation");
        operation.setLocality("local");
        operation.setType("void");

        parameter = new Parameter();
        parameter.setRef(true);
        parameter.setIn(false);
        parameter.setOut(false);
        parameter.setType("int");
        parameter.setName("arg0");

        operation.addParameter(parameter);

        parameter = new Parameter();
        parameter.setRef(false);
        parameter.setIn(true);
        parameter.setOut(true);
        parameter.setType("string");
        parameter.setName("arg1");

        operation.addParameter(parameter);
        exampleInterface.addOperation(operation);


        // ---- someRemoteOperation
        operation = new Operation();
        operation.setName("someRemoteOperation");
        operation.setLocality("remote");
        operation.setType("int");

        exampleInterface.addOperation(operation);

        System.out.println("Parsed as:");
        new InterfaceEmitter(System.out).emit(exampleInterface);

        System.out.print("\n--------------------------------\n");

        System.out.println("Java Interface");
        new JavaInterfaceEmitter(System.out).emit(exampleInterface);

        System.out.print("\n--------------------------------\n");

        System.out.println("Java Stub");
        new JavaStubEmitter(System.out).emit(exampleInterface);


    }
}
