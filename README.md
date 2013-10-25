File hierarchy
--------------

    Rpc          The RPC library that implements all the interesting features.
    Compiler     main() for the compiler.
    RpcRegistry  main() for the registry server (naming service I think?)
    RpcClient    main() for the client.
    RpcServer    main() for the server.

Operation flow
--------------

Compiler compiles the 'interface.idl' and outputs 'TestInterface.java' and
'TestInterface\_Stub.java'. The user code, that wants to use our wonderful RMI
framework will call the interface in 'TestInterface.java' and the
implementation of that interface is in 'TestInterface\_Stub.java'.



                      +-------------------------+
                      |name server (RpcRegistry)|
                      +-------------------------+
                    2. ^  / 3.                   ^  1.
                      /  v                        \
      +------------------+         4.              +----------------------+
      |client (RpcClient)| - - - - - - - - - - - > |RMI server (RpcServer)|
      +------------------+                         +----------------------+


First, you start the name server. Then, you start the RMI server. The RMI
server connects to the name server and tells on which host and port it is
located at(1).

The client first connects the name server(2) and asks where is the RMI server
based on name. The name server responds(3) and then client connects directly to
the target RMI server(4).

Naming scheme
-------------

The names are of the form:

    rpc://hostname:port/service_name

'hostname' and 'port' tell which host and port the server is on and
'service_name' is the name of the service. The client uses 'service_name' to
refer to a service.

