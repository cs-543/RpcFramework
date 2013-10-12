import Compiler.*;

import java.io.FileInputStream;
import java.io.*;

public class Program {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            printUsage();
            return;
        }

        File idlFile = new File(args[0]);

        IdlCompiler compiler = new IdlCompiler(new FileInputStream(idlFile));
        Interface interface_ = compiler.getInterface();

        File interfaceFile = new File(idlFile.getParentFile(), interface_.getName() + ".java");
        File stubFile = new File(idlFile.getParentFile(), interface_.getName() + "_Stub.java");


        /*
        System.out.println("Parsed as:");
        new InterfaceEmitter(System.out).emit(interface_);
        */

        new JavaInterfaceEmitter(new FileOutputStream(interfaceFile)).emit(interface_);
        new JavaStubEmitter(new FileOutputStream(stubFile)).emit(interface_);
    }

    private static void printUsage() {
        System.out.println("Usage: compiler <path>");
    }
}
