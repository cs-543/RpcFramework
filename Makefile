CLASSPATH := .:$(realpath ./Rpc/src/)

.PHONY: release clean

release:
	echo $(CLASSPATH)
	CLASSPATH=$(CLASSPATH) $(MAKE) -e -C Rpc/src
	CLASSPATH=$(CLASSPATH) $(MAKE) -e -C Compiler/src
	CLASSPATH=$(CLASSPATH) $(MAKE) -e -C RpcClient/src
	CLASSPATH=$(CLASSPATH) $(MAKE) -e -C RpcServer/src
	CLASSPATH=$(CLASSPATH) $(MAKE) -e -C RpcRegistry/src

