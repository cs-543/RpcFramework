Executive summary:
Functional Requirements:
1.	Remote Method Invocation (RMI)
	Invoking a method residing at a server works the same as that of a local method. Can be determined according to IDL specifiers
2.	Interface Definition Language(IDL)	


Design description
As per the functional requirements, our solution to the first programming assignment consists of multiple parts: An Interface Definition Language (IDL) of which a test sample can be found in interface.idl in the main directory, a corresponding IDL compiler that generates stub codes 


Design decisions
In order to simplify the projects organization, five different subprojects exist within our submitted solution. There is one central library handling most of the logic, containing most of the logic of the entire project. The remaining four subprojects provide entrypoints to execute the compiler, the registry (naming service) the server and the client.

Testing
