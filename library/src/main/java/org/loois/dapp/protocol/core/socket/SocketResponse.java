package org.loois.dapp.protocol.core.socket;

public class SocketResponse<T> {

    public String error;
    public String code;
    public T data;

    @Override
    public String toString() {
        return "SocketResponse{" +
                "error='" + error + '\'' +
                ", code='" + code + '\'' +
                ", data=" + data +
                '}';
    }
}
