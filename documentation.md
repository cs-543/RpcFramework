Executive summary
-----------------


1. Remote Method Invocation (RMI)
   Invoking a method residing at a server works the same as that of a local method. Can be determined according to IDL specifiers
2. Interface Definition Language(IDL)	
   TODO
3. IDL Compiler
   TODO
4. Parameter Passing
   TODO
5. Binding
   TODO
6. Marshalling
   TODO
7. Execution Semantics
   TODO
8. Concurrent operations
   TODO


Design description
------------------

As per the functional requirements, our solution to the first programming assignment consists of multiple parts: An Interface Definition Language (IDL) of which a test sample can be found in interface.idl in the main directory, a corresponding IDL compiler that generates stub codes ......................................


Design decisions
----------------

In order to simplify the projects organization, five different subprojects exist within our submitted solution. There is one central library handling most of the logic, containing most of the logic of the entire project. The remaining four subprojects provide entrypoints to execute the compiler, the registry (naming service) the server and the client....................


Serialization:
Due to usage of reflection in java, the serialization (Rpc/src/Rpc/{ObjectStreamWriter.java|ObjectStreamReader.java}) is 100% reflective and works with every type. Compatibility across platforms is also preserved as class and type names are provide with the actual contents. This has led to some verbosity but we feel in this scenario compatibility beats performance. Compared to complete XML serialization like JAXB, our implementation creates less output.

Testing
-------

....................
Where are the tests?
