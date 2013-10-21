package Rpc.Registry;

import java.net.InetAddress;

class ServiceInfo {
    private InetAddress address;
    private int port;

    public ServiceInfo(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }

    public InetAddress getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }
}
