import Rpc.Compiler.IdlCompiler;
import Rpc.Compiler.Interface;
import Rpc.Compiler.JavaInterfaceEmitter;
import Rpc.Compiler.JavaStubEmitter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Program {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Usage: compiler <path>");
            return;
        }

        File idlFile = new File(args[0]);

        IdlCompiler compiler = new IdlCompiler(new FileInputStream(idlFile));
        Interface interface_ = compiler.getInterface();

        File interfaceFile = new File(idlFile.getParentFile(), interface_.getName() + ".java");
        File stubFile = new File(idlFile.getParentFile(), interface_.getName() + "_Stub.java");

        new JavaInterfaceEmitter(new FileOutputStream(interfaceFile)).emit(interface_).close();
        new JavaStubEmitter(new FileOutputStream(stubFile)).emit(interface_).close();
    }
}
