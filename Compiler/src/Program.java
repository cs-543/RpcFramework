import Compiler.*;

import java.io.FileInputStream;

public class Program {
    public static void main(String[] args) throws Exception {
        IdlCompiler compiler = new IdlCompiler(new FileInputStream("interface.idl"));
        Interface interface_ = compiler.getInterface();

        System.out.println("Parsed as:");
        new InterfaceEmitter(System.out).emit(interface_);

        System.out.print("\n--------------------------------\n");

        System.out.println("Java Interface:");
        new JavaInterfaceEmitter(System.out).emit(interface_);

        System.out.print("\n--------------------------------\n");

        System.out.println("Java Stub:");
        new JavaStubEmitter(System.out).emit(interface_);
    }
}
